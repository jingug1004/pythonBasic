/*================================================================================
 * 시스템			: KTAX 파일 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1020.activity.Ktax.java
 * 파일설명		: PC파일 업로드
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-05
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
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;

public class Ktax {

	protected PosLog logger;
	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";

	public Ktax() {
	}

	public Ktax(PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao,
			String session_user_id) {
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;

		logger = PosLogFactory.getLogger(getClass());
	}

	// TBJI_PC_TAX 값 입력
	public int insertKtax() {
		int intResult = 0; // 결과값

		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sDT = (String) ctx.get("DT"); // 일자
		String sJigeupDt = sStndYear+sDT;
		

		ArrayList aList = new ArrayList();
		ArrayList alDBInsert = new ArrayList(); // 확정 제외된 데이터용 ArrayList
		FileReader fReader = new FileReader();

		aList = fReader.alReadFile(sFilePath);

		// 파일을 읽어들여 DB INSERT
		HashMap hMap =null;
		for (int i = 0; i < aList.size(); i++) {
			hMap = (HashMap) aList.get(i);
			if ( this.intTbrdFstCfmCntnt(rbmjdbcdao, hMap) == 0) // 지급조서 상세내역에 데이터가 없는 경우
			{
				alDBInsert.add(hMap);
			}
		}

		String[] arrQuery = new String[alDBInsert.size()];

		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);

			deleteKtax(rbmjdbcdao, hMap);
					

            /**
			arrQuery[i] = " INSERT INTO  TBJI_PC_TAX (    	"+
			"      MEET_CD,        			 				"+
			"      SELL_CD,        			 				"+
			"      TSN,        			 						"+
			"      PAY_YEAR,        			 						"+
			"      PERF_NO,        			 				"+
			"      COMM_NO,        			 				"+
			"      DIV_NO,        			 				"+
			"      REFUND,        			 				"+
			"      SELL_AMT,        			 			"+
			"      JIGEUP_AMT,        			 		"+
			"      COST,        			 					"+
			"      GITA1,        			 					"+
			"      GITA2,        			 					"+
			"      GITA_PAY,        			 			"+
			"      CUST_SSN,        			 			"+
			"      ETC,		        			 				"+
			"      JIGEUP_DT,        	"+
			"      INST_ID,        			 				"+
			"      INST_DT,        			 				"+
			"      UPDT_ID,        			 				"+
			"      UPDT_DT         			 				"+
			" 			) VALUES (             			"+
			"  '" + SavePCFileUpload.searchMeet((String) hMap.get("column1"))+ "' "+ // MEET_CD,
			", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))+ "' "+ // SELL_CD,
			", '" + hMap.get("column7") + "' "+ // TSN,
			", '" + sStndYear + "' "+ // PAY_YEAR,
			", '" + hMap.get("column2") + "' "+ // PERF_NO,
			", '"+ SavePCFileUpload.searchoutCode((String) hMap.get("column3"))+ "' "+ // COMM_NO,
			", '"+ SavePCFileUpload.searchoutCode((String) hMap.get("column4"))+ "' "+ // DIV_NO,
			", '" + hMap.get("column5") + "' "+ // REFUND,
			", '" + hMap.get("column6") + "' "+ // SELL_AMT,
			", '" + hMap.get("column8") + "' "+ // JIGEUP_AMT,
			", '" + hMap.get("column9") + "' "+ // COST,
			", '" + hMap.get("column10") + "' "+ // GITA1,
			", '" + hMap.get("column11") + "' "+ // GITA2,
			", '" + hMap.get("column12") + "' "+ // GITA_PAY,
			", FC_ENC ('" + hMap.get("column13") + "') "+ // CUST_SSN,
			", '" + hMap.get("column14") + "' "+ // ETC,
			", '" + sJigeupDt + "' "+ // JIGEUP_DT,
			", '" + user_id + "' "+ // INST_ID,
			", SYSDATE "+ // INST_DT,
			", '" + user_id + "' "+ // UPDT_ID,
			", SYSDATE ) "; // UPDT_DT
			**/

			int iWinAmt 	= Util.NVL(hMap.get("column12"),0) + Util.NVL(hMap.get("column19"),0) + Util.NVL(hMap.get("column26"),0) + Util.NVL(hMap.get("column33"),0) + Util.NVL(hMap.get("column40"),0) + Util.NVL(hMap.get("column47"),0);
			int iJegeupAmt 	= Util.NVL(hMap.get("column13"),0) + Util.NVL(hMap.get("column20"),0) + Util.NVL(hMap.get("column27"),0) + Util.NVL(hMap.get("column34"),0) + Util.NVL(hMap.get("column41"),0) + Util.NVL(hMap.get("column48"),0);
			int iGita1 		= Util.NVL(hMap.get("column14"),0) + Util.NVL(hMap.get("column21"),0) + Util.NVL(hMap.get("column28"),0) + Util.NVL(hMap.get("column35"),0) + Util.NVL(hMap.get("column42"),0) + Util.NVL(hMap.get("column49"),0);
			int iGita2		= Util.NVL(hMap.get("column15"),0) + Util.NVL(hMap.get("column22"),0) + Util.NVL(hMap.get("column29"),0) + Util.NVL(hMap.get("column36"),0) + Util.NVL(hMap.get("column43"),0) + Util.NVL(hMap.get("column50"),0);
			int iGitaPay 	= Util.NVL(hMap.get("column16"),0) + Util.NVL(hMap.get("column23"),0) + Util.NVL(hMap.get("column30"),0) + Util.NVL(hMap.get("column37"),0) + Util.NVL(hMap.get("column44"),0) + Util.NVL(hMap.get("column51"),0);
			
			arrQuery[i] = " " +
			" MERGE INTO TBJI_PC_TAX DST    /* Ktax.insertKtax */	 \n"+
			"       USING (                                          \n" +
			"              SELECT  '" + SavePCFileUpload.searchMeet((String) hMap.get("column1"))+ "' AS MEET_CD \n"+ 	// MEET_CD,
			"                     ,'" + SavePCFileUpload.searchSell((String) hMap.get("column0"))+ "' AS SELL_CD \n"+ 	// SELL_CD,
			"                     ,'" + hMap.get("column9") + "' AS TSN \n"+ 											// TSN(asc_tsn)
			"                     ,'" + sStndYear + "' AS PAY_YEAR \n"+ 												// PAY_YEAR
			"                     ,'" + hMap.get("column2") + "' AS PERF_NO \n"+ 										// PERF_NO(perf_no)
			"                     ,'"+ SavePCFileUpload.searchoutCode((String) hMap.get("column5"))+ "' AS COMM_NO \n"+ // COMM_NO(cash_comm_no)
			"                     ,'"+ SavePCFileUpload.searchoutCode((String) hMap.get("column6"))+ "' AS DIV_NO  \n"+ // DIV_NO(cash_div_no)
			"                     ,'" + hMap.get("column7") + "' AS REFUND     \n"+ 	// REFUND(cash_windows)
			"                     ,'" + hMap.get("column8") + "' AS SELL_AMT   \n"+ 	// SELL_AMT(sell_windows)
			"                     ,'" + iJegeupAmt 	+ "' AS JIGEUP_AMT \n"+ 			// JIGEUP_AMT(gita_pay+gita1+gita2)
			"                     ,'" + iWinAmt 	+ "' AS COST       \n"+ 			// COST(win_amt)
			"                     ,'" + iGita1 		+ "' AS GITA1      \n"+ 			// GITA1(gita1)
			"                     ,'" + iGita2		+ "' AS GITA2      \n"+ 			// GITA2(gita2)
			"                     ,'" + iGitaPay 	+ "' AS GITA_PAY   \n"+ 			// GITA_PAY(gita_pay)
			"                     ,FC_ENC ('" + hMap.get("column10") + "') AS CUST_SSN \n"+ // CUST_SSN(kssn)
			"                     ,'" + hMap.get("column18") + "' AS ETC \n"+ 				// ETC(details)
			"                     ,'" + sJigeupDt + "' AS JIGEUP_DT \n"+ 					// JIGEUP_DT,
			"                     ,'" + user_id   + "' AS INST_ID   \n"+ 					// INST_ID,
			"                     ,SYSDATE             AS INST_DT   \n"+ 					// INST_DT,
			"                     ,'" + user_id + "'   AS UPDT_ID   \n"+ 					// UPDT_ID,
			"                     ,SYSDATE             AS UPDT_DT   \n"+ 					// UPDT_DT
			"              FROM    DUAL \n" +
			"             ) SRC         \n" +
			"             ON (          \n" +
			"                    DST.PAY_YEAR = SRC.PAY_YEAR \n" +
			"                AND DST.MEET_CD  = SRC.MEET_CD  \n" +
			"                AND DST.SELL_CD  = SRC.SELL_CD  \n" +
			"                AND DST.TSN      = SRC.TSN      \n" +
			"             )                                  \n" +
			"             WHEN MATCHED THEN                  \n" +
			"                          UPDATE SET            \n" +
			"                                 DST.PERF_NO    = SRC.PERF_NO   \n" +
			"                                ,DST.COMM_NO    = SRC.COMM_NO   \n" +
			"                                ,DST.DIV_NO     = SRC.DIV_NO    \n" +
			"                                ,DST.REFUND     = SRC.REFUND    \n" +
			"                                ,DST.SELL_AMT   = SRC.SELL_AMT  \n" +
			"                                ,DST.JIGEUP_AMT = SRC.JIGEUP_AMT \n" +
			"                                ,DST.COST       = SRC.COST      \n" +
			"                                ,DST.GITA1      = SRC.GITA1     \n" +
			"                                ,DST.GITA2      = SRC.GITA2     \n" +
			"                                ,DST.GITA_PAY   = SRC.GITA_PAY  \n" +
			"                                ,DST.CUST_SSN   = SRC.CUST_SSN  \n" +
			"                                ,DST.ETC        = SRC.ETC       \n" +
			"                                ,DST.JIGEUP_DT  = SRC.JIGEUP_DT \n" +
			"                                ,DST.UPDT_ID    = SRC.UPDT_ID   \n" +
			"                                ,DST.UPDT_DT    = SRC.UPDT_DT   \n" +
			"            WHEN NOT MATCHED THEN   \n" +
			"                          INSERT  ( \n" +
			"                                   MEET_CD,       \n"+
			"                                   SELL_CD,       \n"+
			"                                   TSN,           \n"+
			"                                   PAY_YEAR,      \n"+
			"                                   PERF_NO,       \n"+
			"                                   COMM_NO,       \n"+
			"                                   DIV_NO,        \n"+
			"                                   REFUND,        \n"+
			"                                   SELL_AMT,      \n"+
			"                                   JIGEUP_AMT,    \n"+
			"                                   COST,          \n"+
			"                                   GITA1,         \n"+
			"                                   GITA2,         \n"+
			"                                   GITA_PAY,      \n"+
			"                                   CUST_SSN,      \n"+
			"                                   ETC,		   \n"+
			"                                   JIGEUP_DT,     \n"+
			"                                   INST_ID,       \n"+
			"                                   INST_DT        \n" +
			"                                ) VALUES ( 	   \n"+
			"                                   SRC.MEET_CD,   \n"+
			"                                   SRC.SELL_CD,   \n"+
			"                                   SRC.TSN,       \n"+
			"                                   SRC.PAY_YEAR,  \n"+
			"                                   SRC.PERF_NO,   \n"+
			"                                   SRC.COMM_NO,   \n"+
			"                                   SRC.DIV_NO,    \n"+
			"                                   SRC.REFUND,    \n"+
			"                                   SRC.SELL_AMT,  \n"+
			"                                   SRC.JIGEUP_AMT,\n"+
			"                                   SRC.COST,      \n"+
			"                                   SRC.GITA1,     \n"+
			"                                   SRC.GITA2,     \n"+
			"                                   SRC.GITA_PAY,  \n"+
			"                                   SRC.CUST_SSN,  \n"+
			"                                   SRC.ETC,	   \n"+
			"                                   SRC.JIGEUP_DT, \n"+
			"                                   SRC.INST_ID,   \n"+
			"                                   SRC.INST_DT    \n" +
			"                       			) ";

		}

		int[] insertCounts = rbmjdbcdao.executeBatch(arrQuery);
		int failure_count = 0;

		for (int i = 0; i < insertCounts.length; i++) {
			if (insertCounts[i] == -3) {
				failure_count++;
			}
		}

		if (failure_count == 0) {
			intResult = aList.size();
		} else {
			intResult = 0;
		}

		return intResult;
	}

	/**
	 * <p>
	 * PC Ktax파일 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */

	public void deleteKtax(PosGenericDao dao, HashMap hMap) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) hMap.get("column1")));// 1.Meet(경기장코드)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) hMap.get("column0")));// 5.SellCd(발매처)
		param.setWhereClauseParameter(i++, hMap.get("column9")); // 6.TSN(경주권번호)

		dao.update("rsm1020_d03", param);

	}

	// 지급조서관리_상세내역 (TBRD_DETL_CNTNT) 에서 확정된 데이터 인지 확인함
	public int intTbrdDetlCntnt(PosGenericDao dao, HashMap hMap) {
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) hMap.get("column1")));// 0.Meet(경기장코드)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) hMap.get("column0")));// 1.SellCd(발매처)
		param.setWhereClauseParameter(i++, hMap.get("column9")); // 2.TSN(경주권번호)
		param.setWhereClauseParameter(i++, sStndYear); // 3.기준년도

		// TBRD_DETL_CNTNT 에서 데이터 존재 여부 확인
		PosRowSet keyRecord = dao.find("intTbrdDetlCntnt", param);

		PosRow pr[] = keyRecord.getAllRow();

		int intCNT = Util.nullToInt(String.valueOf(pr[0].getAttribute("CNT")));

		return intCNT;
	}
	
	// 지급조서관리_확정내역 (TBRD_CFM_CNTNT) 에서 확정된 데이터 인지 확인함
	public int intTbrdFstCfmCntnt(PosGenericDao dao, HashMap hMap) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) hMap.get("column1")));// 1.Meet(경기장코드)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) hMap.get("column0")));// 5.SellCd(발매처)
		param.setWhereClauseParameter(i++, hMap.get("column9")); // 6.TSN(경주권번호)

		// TBRD_CFM_CNTNT 에서 데이터 존재 여부 확인
		PosRowSet keyRecord = dao.find("intTbrdFstCfmCntnt", param);

		PosRow pr[] = keyRecord.getAllRow();

		int intCNT = Util.nullToInt(String.valueOf(pr[0].getAttribute("CNT")));

		return intCNT;
	}

	public int insertTbrdCfmCntnt() {
		int intResult = 0; // 결과값

		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY");

		ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
		ArrayList alDBInsert = new ArrayList(); // 확정 제외된 데이터용 ArrayList
		FileReader File = new FileReader(); // 파일 데이터 읽는 클래스

		aList = File.alReadFile(sFilePath); // 파일 데이터 읽어들임

		// 파일을 읽어들여 DB INSERT
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {
			hMap = (HashMap) aList.get(i);
			if (intTbrdFstCfmCntnt(rbmjdbcdao, hMap) == 0) // TBRD_CFM_CNTNT 에서 데이터가 존재하지 않을 경우
			{
				alDBInsert.add(hMap);
			}
		}

		String[] arrQuery = new String[alDBInsert.size()]; // 입력시킬 쿼리 배열

		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);

			//deleteTbrdCfmCntnt(rbmjdbcdao, hMap);			
			
			arrQuery[i] = " INSERT INTO  TBRD_CFM_CNTNT (	/* Ktax.insertTbrdCfmCntnt */ "+
			" 	MEET_CD,                  	"+
			" 	SELL_CD,                 	"+
			" 	TSN_NO,                  	"+
			" 	PAY_YEAR,                 	"+
			" 	RES_NO,                   	"+
			" 	BRNC_CD,                  	"+
			" 	FST_CFM_CD,               	"+
			" 	SND_CFM_CD,               	"+
			" 	THR_CFM_CD,               	"+
			" 	INST_ID,                  	"+
			" 	INST_DT,                  	"+
			" 	UPDT_ID,                  	"+
			" 	UPDT_DT                   	"+
			" ) VALUES (  			"+
			"  '" + SavePCFileUpload.searchMeet((String) hMap.get("column1"))+ "' 		"+ 	// MEET_CD,
			", '" + SavePCFileUpload.searchSell((String) hMap.get("column0"))+ "' 		"+ 	// SELL_CD,
			", '" + hMap.get("column9") + "' 					"+ 							// TSN,
			", '" + sStndYear + "' 				"+ 											// PAY_YEAR,
			", FC_ENC ('" + hMap.get("column10") + "')"+ 									// RES_NO(kssn)
			", '" + SavePCFileUpload.searchoutCode((String) hMap.get("column5")) + "'"+ 	// COMM_NO(cash_comm_no)
			", '002'"+ // 1차확정코드 001: 확정, 002: 미확정
			", '002'"+ // 2차확정코드 001: 확정, 002: 미확정
			", '002'"+ // 3차확정코드 001: 확정, 002: 미확정
			", '" + user_id + "' "+ // INST_ID,
			", SYSDATE"+ // INST_DT,
			", '" + user_id + "' "+ // INST_ID,
			", SYSDATE ) "; // UPDT_DT
			
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
	 * TBRD_CFM_CNTNT 데이터 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */
	public void deleteTbrdCfmCntnt(PosGenericDao dao, HashMap hMap) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String) hMap.get("column1")));	// 1.Meet(경기장코드)
		param.setWhereClauseParameter(i++, SavePCFileUpload.searchSell((String) hMap.get("column0")));	// 5.SellCd(발매처)
		param.setWhereClauseParameter(i++, hMap.get("column9")); 										// 6.TSN(경주권번호)

		dao.update("deleteTbrdCfmCntnt", param); // PC(gita)파일 삭제와 공통으로 사용한다.
	}

}
