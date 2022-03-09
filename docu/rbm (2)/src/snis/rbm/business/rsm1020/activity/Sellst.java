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
		int intResult = 0; // 결과값
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY");

//		ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
//		FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스
//		aList = fReader.alReadFile(sFilePath); // 파일 데이터 읽어들임

		// 파일을 읽어들여 DB INSERT
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
	 * PC Sellst파일 입력
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	public int insertSellst(PosGenericDao dao, PosRecord record,
			String sStndYear, String UserID, String SessionNo) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, SavePCFileUpload.searchMeet((String) record
				.getAttribute("column1"))); // 1.Meet(경기장코드)
		param.setValueParamter(i++, sStndYear); // 2.StndYear(기준년도)
		param.setValueParamter(i++, SavePCFileUpload.searchTms((String) record
				.getAttribute("column1"))); // 3.TMS(회차)
		param.setValueParamter(i++, SavePCFileUpload.searchDay((String) record
				.getAttribute("column1"))); // 4.DAY_ORD(일차)
		param.setValueParamter(i++, SavePCFileUpload.searchSell((String) record
				.getAttribute("column0"))); // 5.SellCd(발매처)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column4"))); // 6.COMM_NO(투표소)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column5"))); // 7.DIV_NO(발매지점)
		param.setValueParamter(i++, record.getAttribute("column3")); // 8.RACE_NO(경주번호)
		param.setValueParamter(i++, SessionNo); // 9.SESSION_NO(세션번호)
		param.setValueParamter(i++, record.getAttribute("column2")); // 10.PERF_NO(퍼포먼스번호)
		param.setValueParamter(i++, record.getAttribute("column6")); // 11.COUNT1(1구간매수)
		param.setValueParamter(i++, record.getAttribute("column7")); // 12.AMOUNT1(1구간금액)
		param.setValueParamter(i++, record.getAttribute("column8")); // 13.COUNT2(2구간매수)
		param.setValueParamter(i++, record.getAttribute("column9")); // 14.AMOUNT2(2구간금액)
		param.setValueParamter(i++, record.getAttribute("column10")); // 15.COUNT3(3구간매수)
		param.setValueParamter(i++, record.getAttribute("column11")); // 16.AMOUNT3(3구간금액)
		param.setValueParamter(i++, record.getAttribute("column12")); // 17.COUNT4(4구간매수)
		param.setValueParamter(i++, record.getAttribute("column13")); // 18.AMOUNT4(4구간금액)
		param.setValueParamter(i++, record.getAttribute("column14")); // 19.COUNT5(5구간매수)
		param.setValueParamter(i++, record.getAttribute("column15")); // 20.AMOUNT5(5구간금액)
		param.setValueParamter(i++, record.getAttribute("column16")); // 21.COUNT6(6구간매수)
		param.setValueParamter(i++, record.getAttribute("column17")); // 22.AMOUNT6(6구간금액)
		param.setValueParamter(i++, record.getAttribute("column18")); // 23.COUNT7(7구간매수)
		param.setValueParamter(i++, record.getAttribute("column19")); // 24.AMOUNT7(7구간금액)
		param.setValueParamter(i++, record.getAttribute("column20")); // 25.COUNT8(8구간매수)
		param.setValueParamter(i++, record.getAttribute("column21")); // 26.AMOUNT8(8구간금액)
		param.setValueParamter(i++, UserID); // 27.작성자ID
		param.setValueParamter(i++, UserID); // 28.수정자ID

		int dmlcount = dao.update("sdl_pc_sellst_ins", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * PC Sellst파일 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */
	public void deleteSellst(PosGenericDao dao, PosRecord record,
			String sStndYear) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) record
				.getAttribute("column1"))); // 1.Meet(경기장코드)
		param.setWhereClauseParameter(i++, sStndYear); // 2.StndYear(기준년도)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchTms((String) record
				.getAttribute("column1"))); // 3.TMS(회차)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchDay((String) record
				.getAttribute("column1"))); // 4.DAY_ORD(일차)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) record
				.getAttribute("column0"))); // 5.SellCd(발매처)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column4")));// 6.COMM_NO(투표소)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column5")));// 7.DIV_NO(발매지점)
		param.setWhereClauseParameter(i++, record.getAttribute("column3")); // 8.RACE_NO(경주번호)

		dao.update("sdl_pc_sellst_del", param);
	}

}
