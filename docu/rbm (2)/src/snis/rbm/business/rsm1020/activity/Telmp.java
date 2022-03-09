/*================================================================================
 * �ý���			: Telmp ���� ���ε�
 * �ҽ����� �̸�	: snis.rbm.business.rsm1020.activity.Telmp.java
 * ���ϼ���		: PC���� ���ε�
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-10-17
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm1020.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;


public class Telmp {
	protected PosLog logger;
	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";

	public Telmp() {
	}

	public Telmp(PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao,
			String session_user_id) {
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;

		logger = PosLogFactory.getLogger(getClass());
	}

	public int insertTelmp(ArrayList aList) {
		int intResult = 0; // �����

		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String RACE_DAY = Util.getCtxStr(ctx, "RACE_DAY");
		String MEET_GUBUN = Util.getCtxStr(ctx, "MEET_GUBUN");
		
		String sMeetCd = "";
		
//		ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
//		FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����
//		aList = fReader.alReadFile(sFilePath); // ���� ������ �о����

		// ������ �о�鿩 DB INSERT
		String[] arrQuery = new String[aList.size()];
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);
			
			if ("003".equals(MEET_GUBUN)) {
				sMeetCd = MEET_GUBUN;
			} else {
				sMeetCd = SavePCFileUpload.searchMeet((String) hMap.get("column5"));
			}

			arrQuery[i] = 

				" INSERT INTO TBJI_PC_TELMP (  /* Telmp.insertTelmp */	\n"+
				"      MEET_CD,								 	\n"+
				"      STND_YEAR,								\n"+
				"      TMS,								 			\n"+
				"      DAY_ORD,								 	\n"+
				"      SELL_CD,								 	\n"+
				"      COMM_NO,								 	\n"+
				"      DIV_NO,								 	\n"+
				"      WIN_NO,								 	\n"+
				"		 WIN_TYPE,								\n"+
				"		 TELLER_ID,								\n"+
				"		 TELLER_TYPE,							\n"+
				"      SESSION_NO,							\n"+
				"      PERF_NO,								 	\n"+
				"      SOLD_NUM,								\n"+
				"      CASH_NUM,								\n"+
				"      CNCL_NUM,								\n"+
				"      SOLD_AMT,								\n"+
				"      CASH_AMT,								\n"+
				"      CNCL_AMT,								\n"+
				"      INST_ID,								 	\n"+
				"      INST_DT,								 	\n"+
				"      UPDT_ID,								 	\n"+
				"      UPDT_DT,          			 	\n"+
				"      RACE_DT          			 	\n"+
				" 			) VALUES (             	"+
				"  '" + sMeetCd + "' \n"+
				", '" + sStndYear + "' "+
				", '" + SavePCFileUpload.searchTms((String) hMap.get("column5"))
						+ "' \n"+
				", '" + SavePCFileUpload.searchDay((String) hMap.get("column5"))+ "' \n"+
				
				", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))
						+ "' \n"+
				", '"
						+ SavePCFileUpload.searchoutCode((String) hMap.get("column1"))
						+ "' \n"+
				", '"
						+ SavePCFileUpload.searchoutCode((String) hMap.get("column2"))
						+ "' \n"+
				", '" + hMap.get("column3") + "' \n"+
				", '" + hMap.get("column4") + "' \n"+
				", '" + hMap.get("column7") + "' \n"+
				", '" + hMap.get("column8") + "' \n"+
				", '" + sSessionNo + "' \n"+
				", '" + hMap.get("column6") + "' \n"+
				", '" + hMap.get("column9") + "' \n"+
				", '" + hMap.get("column10") + "' \n"+
				", '" + hMap.get("column11") + "' \n"+
				", '" + hMap.get("column12") + "' \n"+
				", '" + hMap.get("column13") + "' \n"+
				", '" + hMap.get("column14") + "' \n"+
				", '" + user_id + "' \n"+
				", SYSDATE \n"+
				", '" + user_id + "' \n"+
				", SYSDATE  \n"+
				", '"+RACE_DAY+"' ) \n";

//			 logger.logDebug("["+i+"]"+arrQuery[i]);

		}

		int[] insertCounts = rbmjdbcdao.executeBatch(arrQuery);
		int failure_count = 0;

		for (int i = 0; i < insertCounts.length; i++) {
			if (insertCounts[i] == -3) {
				failure_count++;
			}
		}

		if (failure_count == 0) {
			intResult = insertCounts.length;
		} else {
			intResult = 0;
		}

		return intResult;
	}

	/**
	 * <p>
	 * PC Telmp���� �Է�
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	public int insertTelmp(PosGenericDao dao, PosRecord record,
			String sStndYear, String UserID, String SessionNo) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, SavePCFileUpload.searchMeet((String) record
				.getAttribute("column5"))); // 1.Meet(������ڵ�)
		param.setValueParamter(i++, sStndYear); // 2.StndYear(���س⵵)
		param.setValueParamter(i++, SavePCFileUpload.searchTms((String) record
				.getAttribute("column5"))); // 3.TMS(ȸ��)
		param.setValueParamter(i++, SavePCFileUpload.searchDay((String) record
				.getAttribute("column5"))); // 4.DAY_ORD(����)
		param.setValueParamter(i++, SavePCFileUpload.searchSell((String) record
				.getAttribute("column0"))); // 5.SellCd(�߸�ó)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column1"))); // 6.COMM_NO(��ǥ��)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column2"))); // 7.DIV_NO(�߸�����)
		param.setValueParamter(i++, record.getAttribute("column3")); // 8.WIN_NO(â����ȣ)
		param.setValueParamter(i++, record.getAttribute("column4")); // 9.WIN_TYPE(â������)
		param.setValueParamter(i++, record.getAttribute("column7")); // 10.TELLER_ID(�߸ſ���ȣ)
		param.setValueParamter(i++, record.getAttribute("column8")); // 11.TELLER_TYPE(�߸ſ�����)
		param.setValueParamter(i++, SessionNo); // 12.SESSION_NO(���ǹ�ȣ)
		param.setValueParamter(i++, record.getAttribute("column6")); // 13.PERF_NO(�����ս���ȣ)
		param.setValueParamter(i++, record.getAttribute("column9")); // 14.SOLD_NUM(���ֱ��ǸŸż�)
		param.setValueParamter(i++, record.getAttribute("column10")); // 15.CASH_NUM(ȯ�޸ż�)
		param.setValueParamter(i++, record.getAttribute("column11")); // 16.CNCL_NUM(��Ҹż�)
		param.setValueParamter(i++, record.getAttribute("column12")); // 17.SOLD_AMT(�Ǹűݾ�)
		param.setValueParamter(i++, record.getAttribute("column13")); // 18.CASH_AMT(ȯ�ޱݾ�)
		param.setValueParamter(i++, record.getAttribute("column14")); // 19.CNCL_AMT(��ұݾ�)
		param.setValueParamter(i++, UserID); // 20.�ۼ���ID
		param.setValueParamter(i++, UserID); // 21.������ID

		int dmlcount = dao.update("sdl_pc_telmp_ins", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * PC Telmp���� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @throws none
	 */
	public void deleteTelmp(PosGenericDao dao, PosRecord record,
			String sStndYear) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) record
				.getAttribute("column5"))); // 1.Meet(������ڵ�)
		param.setWhereClauseParameter(i++, sStndYear); // 2.StndYear(���س⵵)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchTms((String) record
				.getAttribute("column5"))); // 3.TMS(ȸ��)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchDay((String) record
				.getAttribute("column5"))); // 4.DAY_ORD(����)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) record
				.getAttribute("column0"))); // 5.SellCd(�߸�ó)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column1")));// 6.COMM_NO(��ǥ��)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column2")));// 7.DIV_NO(�߸�����)
		param.setWhereClauseParameter(i++, record.getAttribute("column3")); // 8.WIN_NO(â����ȣ)
		param.setWhereClauseParameter(i++, record.getAttribute("column4")); // 9.WIN_TYPE(â������)
		param.setWhereClauseParameter(i++, record.getAttribute("column7")); // 10.TELLER_ID(�߸ſ���ȣ)
		param.setWhereClauseParameter(i++, record.getAttribute("column8")); // 11.TELLER_TYPE(�߸ſ�����)

		dao.update("sdl_pc_telmp_del", param);
	}

}
