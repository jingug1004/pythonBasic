/*================================================================================
 * �ý���			: Satlit ���� ���ε�
 * �ҽ����� �̸�	: snis.rbm.business.rsm1020.activity.Satlit.java
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


public class Satlit {
	protected PosLog logger;
	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";

	public Satlit() {
	}

	public Satlit(PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao,
			String session_user_id) {
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;

		logger = PosLogFactory.getLogger(getClass());
	}

	public int insertSatlit(ArrayList aList) {
		int intResult = 0; // �����

		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY");

//		ArrayList aList = new ArrayList(); // ���� �����Ϳ� ArrayList
//		FileReader fReader = new FileReader(); // ���� ������ �д� Ŭ����
//		aList = fReader.alReadFile(sFilePath); // ���� ������ �о����
		
		
		

		// ������ �о�鿩 DB INSERT
		String[] arrQuery = new String[aList.size()];
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);
			

			

			arrQuery[i] = " INSERT INTO TBJI_PC_SATLIT (  /* Satlit.insertSatlit */ \n"+
			"             MEET_CD,         \n"+
			"             STND_YEAR,       \n"+
			"             TMS,             \n"+
			"             DAY_ORD,         \n"+
			"             SELL_CD,         \n"+
			"             COMM_NO,         \n"+
			"             DIV_NO,          \n"+
			"             RACE_NO,         \n"+
			"             POOL_CD,         \n"+
			"             SESSION_NO,      \n"+
			"             PERF_NO,         \n"+
			"             TOTAL_AMT,       \n"+
			"             REFUND,          \n"+
			"             NET_AMT,			   \n"+
			"             INST_ID,         \n"+
			"             INST_DT,         \n"+
			"             UPDT_ID,         \n"+
			"             UPDT_DT          \n"+
			" 			) VALUES (             \n"+
			"'" + SavePCFileUpload.searchMeet((String) hMap.get("column1"))
					+ "' \n"+
			", '" + sStndYear + "' \n"+
			", '" + SavePCFileUpload.searchTms((String) hMap.get("column1"))
					+ "' \n"+
			", '" + SavePCFileUpload.searchDay((String) hMap.get("column1"))
					+ "' \n"+
			", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))
					+ "' \n"+
			", '"
					+ SavePCFileUpload.searchoutCode((String) hMap.get("column3"))
					+ "' \n"+
			", '"
					+ SavePCFileUpload.searchoutCode((String) hMap.get("column4"))
					+ "' \n"+
			", '"
					+ SavePCFileUpload.searchRaceNo((String) hMap.get("column5"))
					+ "' \n"+
			", '" + "00"
					+ Util.nullToStr((String) hMap.get("column6")) + "' \n"+
			", '" + sSessionNo + "' \n"+
			", '" + Util.nullToStr((String) hMap.get("column2"))+ "' \n"+
			", '" + Util.nullToStr((String) hMap.get("column7"))+ "' \n"+
			", '" + Util.nullToStr((String) hMap.get("column8"))+ "' \n"+
			", '" + Util.nullToStr((String) hMap.get("column9"))+ "' \n"+
			", '" + user_id + "' \n"+
			", SYSDATE \n"+
			", '" + user_id + "' \n"+
			", SYSDATE ) \n";

			// logger.logDebug("["+i+"]"+arrQuery[i]);

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
	 * PC Satlit���� �Է�
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	public int insertSatlit(PosGenericDao dao, PosRecord record,
			String sStndYear, String UserID, String SessionNo) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, SavePCFileUpload.searchMeet((String) record
				.getAttribute("column1"))); // 1.Meet(������ڵ�)
		param.setValueParamter(i++, sStndYear); // 2.StndYear(���س⵵)
		param.setValueParamter(i++, SavePCFileUpload.searchTms((String) record
				.getAttribute("column1"))); // 3.TMS(ȸ��)
		param.setValueParamter(i++, SavePCFileUpload.searchDay((String) record
				.getAttribute("column1"))); // 4.DAY_ORD(����)
		param.setValueParamter(i++, SavePCFileUpload.searchSell((String) record
				.getAttribute("column0"))); // 5.SellCd(�߸�ó)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column3"))); // 6.COMM_NO(��ǥ�ҹ�ȣ)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column4"))); // 7.DIV_NO(�߸�(����)��ȣ)
		param.setValueParamter(i++, record.getAttribute("column5")); // 8.RACE_NO(���ֹ�ȣ)
		param.setValueParamter(i++, "00" + record.getAttribute("column6")); // 9.POOL_CD(�½Ĺ�ȣ)
		param.setValueParamter(i++, SessionNo); // 10.SESSION_NO(���ǹ�ȣ)
		param.setValueParamter(i++, record.getAttribute("column2")); // 11.PERF_NO(�����ս���ȣ)
		param.setValueParamter(i++, record.getAttribute("column7")); // 12.TOTAL_AMT(�Ѹ����)
		param.setValueParamter(i++, record.getAttribute("column8")); // 13.REFUND(ȯ�ұݾ�)
		param.setValueParamter(i++, record.getAttribute("column9")); // 14.NET_AMT(�������)
		param.setValueParamter(i++, UserID); // 15.�ۼ���ID
		param.setValueParamter(i++, UserID); // 16.������ID

		int dmlcount = dao.update("sdl_pc_satlit_ins", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * PC Satlit���� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @throws none
	 */
	public void deleteSatlit(PosGenericDao dao, PosRecord record,
			String sStndYear) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) record
				.getAttribute("column1"))); // 1.Meet(������ڵ�)
		param.setWhereClauseParameter(i++, sStndYear); // 2.StndYear(���س⵵)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchTms((String) record
				.getAttribute("column1"))); // 3.TMS(ȸ��)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchDay((String) record
				.getAttribute("column1"))); // 4.DAY_ORD(����)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) record
				.getAttribute("column0"))); // 5.SellCd(�߸�ó)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column3")));// 6.COMM_NO(��ǥ�ҹ�ȣ)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column4")));// 7.DIV_NO(�߸�(����)��ȣ)
		param.setWhereClauseParameter(i++, record.getAttribute("column5")); // 8.RACE_NO(���ֹ�ȣ)
		param.setWhereClauseParameter(i++, "00"
				+ record.getAttribute("column6")); // 9.POOL_CD(�½Ĺ�ȣ)

		dao.update("sdl_pc_satlit_del", param);
	}

}
