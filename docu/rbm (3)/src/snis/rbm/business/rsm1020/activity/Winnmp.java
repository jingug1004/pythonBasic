/*================================================================================
 * 시스템			: Winnmp 파일 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1020.activity.Winnmp.java
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


public class Winnmp {
	protected PosLog logger;
	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";
	private String MeetCd = "";

	public Winnmp() {
	}

	public Winnmp(PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao,
			String session_user_id, String PMeetCd) {
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;
		this.MeetCd = PMeetCd;

		logger = PosLogFactory.getLogger(getClass());
	}

	public int insertWinnmp(ArrayList aList) {
		int intResult = 0; // 결과값

		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY");
		
		String RACE_DAY = Util.getCtxStr(ctx, "RACE_DAY");

//		ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
//		FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스
//		aList = fReader.alReadFile(sFilePath); // 파일 데이터 읽어들임

		// 파일을 읽어들여 DB INSERT
		String[] arrQuery = new String[aList.size()];
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);
			

			arrQuery[i] = " INSERT INTO TBJI_PC_WINNMP ( 	/* Winnmp.insertWinnmp */	"+
			"      MEET_CD,		 							"+
			"      STND_YEAR,		 							"+
			"      TMS,		 								"+
			"      DAY_ORD,		 							"+
			"      SELL_CD,		 							"+
			"      COMM_NO,		 							"+
			"      DIV_NO,		 							"+
			"      WIN_NO, 			 						"+
			"	     WIN_TYPE,	   		 						"+
			"	     TELLER_ID, 			 					"+
			"	     TELLER_TYPE,		 						"+
			"      SOLD_NUM,		 							"+
			"      CASH_NUM,		 							"+
			"      CNCL_NUM,		 							"+
			"      SOLD_AMT,		 							"+
			"      CASH_AMT,		 							"+
			"      CNCL_AMT,		 							"+
			"      LEAD_CNT,		 							"+
			"      RECE_CNT,		 							"+
			"      LEAD_AMT,		 							"+
			"      RECE_AMT,		 							"+
			"      CASH_LEAD_CNT,		 						"+
			"      CASH_RECE_CNT,		 						"+
			"      CASH_LEAD_AMT,		 						"+
			"      CASH_RECE_AMT,		 						"+
			"      ACCNT_RECE_CNT,		 					"+
			"      ACCNT_DRAW_CNT,		 					"+
			"      ACCNT_RECE_AMT,		 					"+
			"      ACCNT_DRAW_AMT,		 					"+
			"      ACCNT_OPEN_CNT,		 					"+
			"      ACCNT_CNCL_CNT,		 					"+
			"      ACCNT_OPEN_AMT,		 					"+
			"      ACCNT_CNCL_AMT,		 					"+
			"      FRST_TM_STAMT,		 						"+
			"      LAST_TM_STAMT,		 						"+			
			"      INST_ID,		 							"+
			"      INST_DT,		 							"+
			"      UPDT_ID,		 							"+
			"      UPDT_DT,          			 				"+
			"      RACE_DT          			 			"+
			" 			) VALUES (             				"+
			"  '" + MeetCd + "' "+
			", '" + sStndYear + "' "+
			", '" + SavePCFileUpload.searchTms(MeetCd) + "' "+ // TMS
			", '" + SavePCFileUpload.searchDay(MeetCd) + "' "+ // DAY_ORD
			", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))
					+ "' "+ // SELL_CD
			", '"
					+ SavePCFileUpload.searchoutCode((String) hMap.get("column1"))
					+ "' "+
			", '"
					+ SavePCFileUpload.searchoutCode((String) hMap.get("column2"))
					+ "' "+
			", '" + hMap.get("column3") + "' "+
			", '" + hMap.get("column4") + "' "+
			", '" + hMap.get("column5") + "' "+
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
			", '" + hMap.get("column22") + "' "+
			", '" + hMap.get("column23") + "' "+
			", '" + hMap.get("column24") + "' "+
			", '" + hMap.get("column25") + "' "+
			", '" + hMap.get("column26") + "' "+
			", '" + hMap.get("column27") + "' "+
			", '" + hMap.get("column28") + "' "+
			", '" + hMap.get("column29") + "' "+
			", '" + hMap.get("column30") + "' "+
			", '" + user_id + "' "+
			", SYSDATE"+
			", '" + user_id + "' "+
			", SYSDATE "+
			", '"+RACE_DAY+"' ) \n";

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
	 * PC Winnmp파일 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */
	public void deleteWinnmp(PosGenericDao dao, PosRecord record,
			String sStndYear, String MeetCd) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, MeetCd); // 1.Meet(경기장코드)
		param.setWhereClauseParameter(i++, sStndYear); // 2.StndYear(기준년도)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchTms(MeetCd)); // 3.TMS(회차)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchDay(MeetCd)); // 4.DAY_ORD(일차)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) record
				.getAttribute("column0"))); // 5.SellCd(발매처)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column1")));// 6.COMM_NO(투표소)
		param.setWhereClauseParameter(i++, SavePCFileUpload
				.searchoutCode((String) record.getAttribute("column2")));// 7.DIV_NO(발매지점)
		param.setWhereClauseParameter(i++, record.getAttribute("column3")); // 8.WIN_NO(창구번호)
		param.setWhereClauseParameter(i++, record.getAttribute("column4")); // 9.WIN_TYPE(창구형태)
		param.setWhereClauseParameter(i++, record.getAttribute("column5")); // 10.TELLER_ID(발매원번호)
		param.setWhereClauseParameter(i++, record.getAttribute("column6")); // 11.TELLER_TYPE(발매원형태)

		dao.update("sdl_pc_winnmp_del", param);
	}

}
