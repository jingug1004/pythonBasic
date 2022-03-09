/*================================================================================
 * 시스템			: Satlit 파일 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1020.activity.Satlit.java
 * 파일설명		: PC파일 업로드
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-17
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
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
	 * PC Satlit파일 입력
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	public int insertSatlit(PosGenericDao dao, PosRecord record,
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
				.getAttribute("column3"))); // 6.COMM_NO(투표소번호)
		param.setValueParamter(i++, SavePCFileUpload.searchoutCode((String) record
				.getAttribute("column4"))); // 7.DIV_NO(발매(지점)번호)
		param.setValueParamter(i++, record.getAttribute("column5")); // 8.RACE_NO(경주번호)
		param.setValueParamter(i++, "00" + record.getAttribute("column6")); // 9.POOL_CD(승식번호)
		param.setValueParamter(i++, SessionNo); // 10.SESSION_NO(세션번호)
		param.setValueParamter(i++, record.getAttribute("column2")); // 11.PERF_NO(퍼포먼스번호)
		param.setValueParamter(i++, record.getAttribute("column7")); // 12.TOTAL_AMT(총매출액)
		param.setValueParamter(i++, record.getAttribute("column8")); // 13.REFUND(환불금액)
		param.setValueParamter(i++, record.getAttribute("column9")); // 14.NET_AMT(순매출액)
		param.setValueParamter(i++, UserID); // 15.작성자ID
		param.setValueParamter(i++, UserID); // 16.수정자ID

		int dmlcount = dao.update("sdl_pc_satlit_ins", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * PC Satlit파일 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */
	public void deleteSatlit(PosGenericDao dao, PosRecord record,
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
				.searchoutCode((String) record.getAttribute("column3")));// 6.COMM_NO(투표소번호)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column4")));// 7.DIV_NO(발매(지점)번호)
		param.setWhereClauseParameter(i++, record.getAttribute("column5")); // 8.RACE_NO(경주번호)
		param.setWhereClauseParameter(i++, "00"
				+ record.getAttribute("column6")); // 9.POOL_CD(승식번호)

		dao.update("sdl_pc_satlit_del", param);
	}

}
