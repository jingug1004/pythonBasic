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


public class Sellst {

	protected PosLog logger;
	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";

	public Sellst() {
	}

	public Sellst(PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao,
			String session_user_id) {
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;

		logger = PosLogFactory.getLogger(getClass());
	}

	public int insertSellst(ArrayList aList) {
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
			

			arrQuery[i] = " INSERT INTO TBJI_PC_SELLST (  /* Sellst.insertSellst */ "+
			"      MEET_CD,								 "+
			"      STND_YEAR,							 	 "+
			"      TMS,								 	 "+
			"      DAY_ORD,								 "+
			"      SELL_CD,								 "+
			"      COMM_NO,								 "+
			"      DIV_NO,								 "+
			"      RACE_NO,								 "+
			"      SESSION_NO,						 	 "+
			"      PERF_NO,								 "+
			"      COUNT1,								 "+
			"      AMOUNT1,								 "+
			"      COUNT2,								 "+
			"      AMOUNT2,								 "+
			"      COUNT3,								 "+
			"      AMOUNT3,								 "+
			"      COUNT4, 								 "+
			"      AMOUNT4,								 "+
			"      COUNT5,								 "+
			"      AMOUNT5,								 "+
			"      COUNT6,								 "+
			"      AMOUNT6,								 "+
			"      COUNT7,								 "+
			"      AMOUNT7,								 "+
			"      COUNT8,								 "+
			"      AMOUNT8,								 "+
			"      INST_ID,								 "+
			"      INST_DT,								 "+
			"      UPDT_ID,								 "+
			"      UPDT_DT          			 "+
			" 			) VALUES (             "+
			"  '" + SavePCFileUpload.searchMeet((String) hMap.get("column1"))
					+ "' "+
			", '" + sStndYear + "' "+
			", '" + SavePCFileUpload.searchTms((String) hMap.get("column1"))
					+ "' "+
			", '" + SavePCFileUpload.searchDay((String) hMap.get("column1"))
					+ "' "+
			", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))
					+ "' "+
			", '"
					+ SavePCFileUpload.searchoutCode((String) hMap.get("column4"))
					+ "' "+
			", '"
					+ SavePCFileUpload.searchoutCode((String) hMap.get("column5"))
					+ "' "+
			", '"
					+ SavePCFileUpload.searchRaceNo((String) hMap.get("column3"))
					+ "' "+
			", '" + sSessionNo + "' "+
			", '" + hMap.get("column2") + "' "+
			", '" + hMap.get("column6") + "' "+
			", '" + hMap.get("column7") + "' "+
			", '" + hMap.get("column8") + "' "+
			", '" + hMap.get("column9") + "' "+
			", '" + hMap.get("column10") + "' "+
			", '" + hMap.get("column11") + "' "+
			", '" + hMap.get("column12") + "' "+
			", '" + hMap.get("column13") + "' "+
			", '" + hMap.get("column14") + "' "+
			", '" + hMap.get("column15") + "' "+
			", '" + hMap.get("column16") + "' "+
			", '" + hMap.get("column17") + "' "+
			", '" + hMap.get("column18") + "' "+
			", '" + hMap.get("column19") + "' "+
			", '" + hMap.get("column20") + "' "+
			", '" + hMap.get("column21") + "' "+
			", '" + user_id + "' "+
			", SYSDATE"+
			", '" + user_id + "' "+
			", SYSDATE ) ";

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
	 * PC Sellst���� �Է�
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	public int insertSellst(PosGenericDao dao, PosRecord record,
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
				.getAttribute("column4"))); // 6.COMM_NO(��ǥ��)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column5"))); // 7.DIV_NO(�߸�����)
		param.setValueParamter(i++, record.getAttribute("column3")); // 8.RACE_NO(���ֹ�ȣ)
		param.setValueParamter(i++, SessionNo); // 9.SESSION_NO(���ǹ�ȣ)
		param.setValueParamter(i++, record.getAttribute("column2")); // 10.PERF_NO(�����ս���ȣ)
		param.setValueParamter(i++, record.getAttribute("column6")); // 11.COUNT1(1�����ż�)
		param.setValueParamter(i++, record.getAttribute("column7")); // 12.AMOUNT1(1�����ݾ�)
		param.setValueParamter(i++, record.getAttribute("column8")); // 13.COUNT2(2�����ż�)
		param.setValueParamter(i++, record.getAttribute("column9")); // 14.AMOUNT2(2�����ݾ�)
		param.setValueParamter(i++, record.getAttribute("column10")); // 15.COUNT3(3�����ż�)
		param.setValueParamter(i++, record.getAttribute("column11")); // 16.AMOUNT3(3�����ݾ�)
		param.setValueParamter(i++, record.getAttribute("column12")); // 17.COUNT4(4�����ż�)
		param.setValueParamter(i++, record.getAttribute("column13")); // 18.AMOUNT4(4�����ݾ�)
		param.setValueParamter(i++, record.getAttribute("column14")); // 19.COUNT5(5�����ż�)
		param.setValueParamter(i++, record.getAttribute("column15")); // 20.AMOUNT5(5�����ݾ�)
		param.setValueParamter(i++, record.getAttribute("column16")); // 21.COUNT6(6�����ż�)
		param.setValueParamter(i++, record.getAttribute("column17")); // 22.AMOUNT6(6�����ݾ�)
		param.setValueParamter(i++, record.getAttribute("column18")); // 23.COUNT7(7�����ż�)
		param.setValueParamter(i++, record.getAttribute("column19")); // 24.AMOUNT7(7�����ݾ�)
		param.setValueParamter(i++, record.getAttribute("column20")); // 25.COUNT8(8�����ż�)
		param.setValueParamter(i++, record.getAttribute("column21")); // 26.AMOUNT8(8�����ݾ�)
		param.setValueParamter(i++, UserID); // 27.�ۼ���ID
		param.setValueParamter(i++, UserID); // 28.������ID

		int dmlcount = dao.update("sdl_pc_sellst_ins", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * PC Sellst���� ����
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @throws none
	 */
	public void deleteSellst(PosGenericDao dao, PosRecord record,
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
				.searchoutCode((String) record.getAttribute("column4")));// 6.COMM_NO(��ǥ��)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column5")));// 7.DIV_NO(�߸�����)
		param.setWhereClauseParameter(i++, record.getAttribute("column3")); // 8.RACE_NO(���ֹ�ȣ)

		dao.update("sdl_pc_sellst_del", param);
	}

}
