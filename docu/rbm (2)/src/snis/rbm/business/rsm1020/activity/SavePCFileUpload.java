/*================================================================================
 * 시스템			: 경주특이사항
 * 소스파일 이름	: snis.rbm.business.rsm1010.activity.SavePCFileUpload.java
 * 파일설명		: PC파일 업로드
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1020.activity;

import java.util.ArrayList;

import com.posdata.glue.bean.PosBeanFactory;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePCFileUpload extends SnisActivity {
	// 파일종류별 데이터셋명 세팅
	protected String sDsGita = "dsGita";
	protected String sDsKtax = "dsKtax";
	protected String sDsPayoffs = "dsPayoffs";
	protected String sDsResults = "dsResults";

	// 기준정보 파라메터
	public static String sStndYear = "";
	public static String sMeetGubun = "";
	public static String sDT = "";

	public static String sRaceDay = "";
	public static String sSessionNo = "";
	public static String sGtms = ""; // 광명 회차
	public static String sGday = ""; // 광명 일차
	public static String sCtms = ""; // 창원 회차
	public static String sCday = ""; // 창원 일차
	public static String sMtms = ""; // 경정 회차
	public static String sMday = ""; // 경정 일차
	public static String sBtms = ""; // 부산 회차
	public static String sBday = ""; // 부산 일차
	public static String sSellCd = "01"; // 광명

	// 파일종류 코드 세팅
	public static String sGita = "001";
	public static String sKtax = "002";
	public static String sPayoffs = "003";
	public static String sResults = "004";
	public static String sSatlit = "005";
	public static String sSellst = "006";
	public static String sTelmp = "007";
	public static String sWinnmp = "008";

	// New MeetCd 세팅
	public static String sGmeetCd = "001"; // 광명
	public static String sCmeetCd = "002"; // 창원
	public static String sMmeetCd = "003"; // 경정(미사리)
	public static String sBmeetCd = "004"; // 부산

	// New SellCd 세팅
	public static String sGsellCd = "01"; // 광명
	public static String sCsellCd = "02"; // 창원
	public static String sMsellCd = "03"; // 경정(미사리)
	public static String sBsellCd = "04"; // 부산

	// Query ID

	public static String sPayoQuery = "rsm1020_d04";
	public static String sResuQuery = "rsm1020_d05";

	public static String sSatlQuery = "sdl_pc_satlit_del";
	public static String sSatlSumQuery = "sdl_pc_satlit_sum_del";
	public static String sSatlQueryMyCat = "mycat_satlit_del";
	public static String sSellQuery = "sdl_pc_sellst_del";
	public static String sSellNEQuery = "sdl_pc_sellst_ne_del";
	public static String sSellQueryMyCat = "mycat_sellst_del";
	public static String sTelmQuery = "sdl_pc_telmp_del";
	public static String sTelmNEQuery = "sdl_pc_telmp_ne_del";
	public static String sWinnQuery = "sdl_pc_winnmp_del";
	public static String sWinnNEQuery = "sdl_pc_winnmp_ne_del";
	public static String sSdlDTQueryMyCat = "rsm1020_d07"; // MyCat-TBES_SDL_DT삭제

	// dao 세팅
	public static String rbmdao = "rbmdao"; // 경륜내부시스템
	public static String rbmjdbcdao = "rbmjdbcdao"; // 

	public String[] aMeet = new String[] { "1", "2", "3", "4" };

	public SavePCFileUpload() {

	}

	/**
	 * <p>
	 * SaveStates Activity를 실행시키기 위한 메소드
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
	 * @return SUCCESS String sucess 문자열
	 * @throws none
	 */
	public String runActivity(PosContext ctx) {

		// 사용자 정보 확인
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
			Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
			return PosBizControlConstants.SUCCESS;
		}
		saveState(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext 저장소
	 * @return none
	 * @throws none
	 */
	protected void saveState(PosContext ctx) {
		int nSaveCount = 0;
		int nDeleteCount = 0;
		int nSize = 0;

		sStndYear = (String) ctx.get("STND_YEAR"); // 년도 StndYear
		sDT = (String) ctx.get("DT"); // 일자
		sMeetGubun = (String) ctx.get("MEET_GUBUN"); // 구분 MeetGubun
		sRaceDay = (String) ctx.get("RACE_DAY"); // 경주일 RaceDay
		sSessionNo = (String) ctx.get("SESSION_NO"); // SessionNo
		sGtms = (String) ctx.get("G_TMS"); // 광명 회차 Gtms
		sGday = (String) ctx.get("G_DAY"); // 광명 일차 Gday
		sCtms = (String) ctx.get("C_TMS"); // 창원 회차 Ctms
		sCday = (String) ctx.get("C_DAY"); // 창원 일차 Cday
		sMtms = (String) ctx.get("M_TMS"); // 경정 회차 Mtms
		sMday = (String) ctx.get("M_DAY"); // 경정 일차 Mday
		sBtms = (String) ctx.get("B_TMS"); // 부산 회차 Btms
		sBday = (String) ctx.get("B_DAY"); // 부산 일차 Bday

		// 존재하는 경륜, 경정 코드 배열 생성

		if (sMeetGubun.equals("003"))
			rbmdao = "rbmdao"; // "boaintradao";
		if (sMeetGubun.equals("003"))
			sSellCd = sMsellCd;

		// dsPcUp 파일 업로드 부분
		PosDataset ds = (PosDataset) ctx.get("dsPcUp");
		
		
		String strFILE_VERI = "002";

		int nGita = 0;
		int nKtax = 0;

		int nPayoffs = 0;
		int nResults = 0;
		int nSatlit = 0;
		int nSellst = 0;
		int nTelmp = 0;
		int nWinnmp = 0;

		// 파일 업로드 확인용 카운트
		int nGitaCount = 0;
		int nKtaxCount = 0;

		int nPayoffsCount = 0;
		int nResultsCount = 0;
		int nSatlitCount = 0;
		int nSellstCount = 0;
		int nTelmpCount = 0;
		int nWinnmpCount = 0;

		PosRecord record = null;
		String sDT = "";

		
		// 이전에 입력 되었던 파일 카운트 세기
		PosRow[] prFileCnt = searchPCFileCount(this.getDao("rbmdao"), ctx);
		for (int i = 0; i < prFileCnt.length; i++) {
			
			strFILE_VERI = (String) prFileCnt[i].getAttribute("FILE_VERI");
			nGita = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("GITA_FILE_ROW_CNT"));
			nKtax = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("KTAX_FILE_ROW_CNT"));
			nPayoffs = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("PAYO_FILE_ROW_CNT"));
			nResults = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("RESU_FILE_ROW_CNT"));
			nSatlit = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("SATL_FILE_ROW_CNT"));
			nSellst = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("SELL_FILE_ROW_CNT"));
			nTelmp = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("TELM_FILE_ROW_CNT"));
			nWinnmp = Integer.parseInt((String) prFileCnt[i]
					.getAttribute("WINN_FILE_ROW_CNT"));
		}

		for (int i = 0; i < ds.getRecordCount(); i++) {
			record = ds.getRecord(i);

			// 1. GITA 파일 업로드
			String FILE_UPLOAD_NAME = record.getAttribute("FILE_UPLOAD_NAME") == null ? ""
					: (String) record.getAttribute("FILE_UPLOAD_NAME");

			if ("001".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nGita = Util.nullToInt((String) record.getAttribute("ROWCNT"));
				sDT = (String) record.getAttribute("DT");

				// GITA 파일 데이터 입력
				Gita objGita = new Gita(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 1-2. TBJI_PC_GITA 테이블에 데이터 입력
				int dmlcount = objGita.insertGita();

				// 1-2-1. TBJI_PC_GITA의 그린카드 발매지점 변경
				updateGitaGrn(sStndYear, sDT);
				
				// 1-3. TBJI_PC_FILE 테이블을 조건(시행처, 기준년도,일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sGita, record);

				// 1-4. TBJI_PC_FILE 테이블에 이력 입력.
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sGita, nGita, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// 입력 카운트 반환시킴
				nGitaCount	= dmlcount;	// 파일 입력 여부 판단 카운트
				nGita		= dmlcount;	// 반환시킬 카운트
				objGita = null;

			}

			// 2. KTAX 파일 업로드
			if ("002".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nKtax = Util.nullToInt((String) record.getAttribute("ROWCNT"));
				// KTAX 파일 데이터 입력
				Ktax objKtax = new Ktax(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 2-1. TBRD_CFM_CNTNT 테이블에 데이터 입력
				// 1차 확정 비교에서 상세 내역 비교로 바뀜 -> 다시 1차 확정 비교로 바뀜
				// int dmlTbrdCfmCntnt = objKtax.insertTbrdCfmCntnt();

				// 2-2. TBJI_PC_TAX 테이블에 데이터 입력
				int dmlcount = objKtax.insertKtax();
				
				// 2-2-1. TBJI_PC_GITA의 그린카드 발매지점 변경
				updateTaxGrn(sStndYear, sDT);


				// 2-3. TBJI_PC_FILE 테이블을 조건(시행처, 기준년도,일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sKtax, record);

				// 2-4 TBJI_PC_FILE 테이블에 이력입력
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sKtax, nKtax, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// 입력 카운트 반환시킴
				nKtaxCount = dmlcount;	// 파일 입력 여부 판단 카운트
				nKtax		= dmlcount;	// 반환시킬 카운트
				objKtax = null;

			}

			// 3. PAYOFFS 파일 업로드
			if ("003".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nPayoffs = Util.nullToInt((String) record
						.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
				FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스

				// 3-1. PAYOFFS 파일에 데이터가 있는지 확인
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {

					// 3-2. TBJI_PC_PAYOFFS 테이블에 해당 파일 데이터 먼저 삭제
					deletePCFileContents("rsm1020_d08", sRaceDay);
				}

				Payoffs objPayoffs = new Payoffs(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 3-3. TBJI_PC_PAYOFFS 테이블에 데이터 입력
				int dmlcount = objPayoffs.insertPayoffs(aList);

				// 3-4. TBJI_PC_FILE 테이블을 조건(시행처,기준년도, 일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sPayoffs, record);

				// 3-5. TBJI_PC_FILE 테이블에 이력 입력
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sPayoffs, nPayoffs, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// 입력 카운트 반환시킴

				nPayoffsCount	= dmlcount;	// 파일 입력 여부 판단 카운트
				nPayoffs		= dmlcount;	// 반환시킬 카운트
				aList = null;
				fReader = null;
				objPayoffs = null;
			}

			// 4. RESULTS 파일 업로드
			if ("004".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nResults = Util.nullToInt((String) record
						.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
				FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스

				// 4-1. RESULTS 파일에 데이터가 있는지 확인
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {
					// 4-2. TBJI_PC_RESULT 테이블에 해당 파일 데이터 먼저 삭제
					deletePCFileContents("rsm1020_d09", sRaceDay);
				}

				Results objResults = new Results(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 4-3. TBJI_PC_RESULT 테이블에 데이터 입력
				int dmlcount = objResults.insertResults(aList);

				// 4-4. TBJI_PC_FILE 테이블을 조건(시행처,기준년도, 일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sResults, record);

				// 4-5. TBJI_PC_FILE 테이블에 이력 입력
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sResults, nResults, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// 입력 카운트 반환시킴

				nResultsCount	= dmlcount;	// 파일 입력 여부 판단 카운트
				nResults		= dmlcount;	// 반환시킬 카운트
				aList = null;
				fReader = null;
				objResults = null;

			}

			// 5. SATLIT 파일 업로드
			if ("005".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nSatlit = Util
						.nullToInt((String) record.getAttribute("ROWCNT"));
				// 5-1. SATLIT 파일에 데이터가 있는지 확인
				ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
				FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH")); // SATLIT 파일 읽기
				if (aList.size() > 0) {
					// 5-2. TBJI_PC_SATLIT 테이블에 해당 파일 데이터 먼저 삭제
					deletePCFileContents("rsm1020_d10", sRaceDay);

				}

				Satlit objSatlit = new Satlit(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 5-3. TBJI_PC_SATLIT 테이블에 데이터 입력
				int dmlcount = objSatlit.insertSatlit(aList);

				// 5-4. TBJI_PC_FILE 테이블을 조건(시행처,기준년도, 일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sSatlit, record);

				// 5-5. TBJI_PC_FILE 테이블에 이력 입력
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sSatlit, nSatlit, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// MyCat Data insert
				// MyCat 생성자의 파라미터는 사용 안됨
				MyCat objMyCat = new MyCat(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				

				// 5-6. TBJI_PC_SATLIT 테이블을 조건(일자,그린카드 )으로 이력 삭제
				deletePCFileContents("rsm1020_d16", sRaceDay);

				// 5-7. VW_MYCAT_SALES -> TBJI_PC_SATLIT 데이터 복사
				objMyCat.insertSatlitMyCat(this.getDao(rbmdao), sRaceDay,
						sSessionNo);

				// 5-8. TBES_SDL_DT_BAK 에서 MyCat 데이터 삭제
				deletePCFileContents("rsm1020_d22", sRaceDay);

				// 5-8. TBES_SDL_DT -> TBES_SDL_DT_BAK MyCat 데이터 복사
				insertData("rsm1020_i06", sRaceDay);

				// 5-9. TBES_SDL_DT 에서 MyCat 데이터 삭제
				deletePCFileContents("rsm1020_d06", sRaceDay);

				// 5-8. VW_MYCAT_SALES -> TBES_SDL_DT 데이터 복사
				objMyCat.insertTbesSdlDTMyCat(this.getDao(rbmdao), sRaceDay);

				// 5-9. TBJI_PC_SATLIT_SUM 에 합계데이터 삭제
				deletePCFileContents("rsm1020_d14", sRaceDay);

				// 5-10. TBJI_PC_SATLIT -> TBJI_PC_SATLIT_SUM 에 합계데이터 입력
				insertData("sdl_pc_satlit_sum_ins", sRaceDay);

				// 5-11. TBES_SDL_DT_SUM 데이터 삭제
				deletePCFileContents("rsm1020_d21", sRaceDay);

				// 5-12. TBES_SDL_DT_SUM 에 TBES_SDL_DT 에 있는 마이켓, 광복, 서면 제외한
				// 데이터 입력
				insertData("rsm1020_i20", sRaceDay);

				// 5-13. TBES_SDL_DT_SUM 에 TBES_SDL_DT 에 있는 마이켓, 광복, 서면 데이터
				// 데이터 입력
				insertData("rsm1020_i21", sRaceDay);

				// 입력 카운트 반환시킴
				nSatlit = dmlcount;
				nSatlitCount = dmlcount;
				aList = null;
				fReader = null;
				objSatlit = null;
				objMyCat = null;
			}

			// 6. SELLST 파일 업로드
			if ("006".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nSellst = Util
						.nullToInt((String) record.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
				FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH")); 

				// 6-1. SELLST 파일에 데이터가있는지 확인
				if (aList.size() > 0) {

					// 6-2. TBJI_PC_SELLST 테이블에 해당 파일 데이터 먼저 삭제
					deletePCFileContents("rsm1020_d11", sRaceDay);
				}

				Sellst objSellst = new Sellst(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 6-3. TBJI_PC_SELLST 테이블에 데이터 입력
				int dmlcount = objSellst.insertSellst(aList);

				// 6-4. TBJI_PC_FILE 테이블을 조건(시행처,기준년도, 일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sSellst, record);

				// 6-5. TBJI_PC_FILE 테이블에 이력 입력.
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sSellst, nSellst, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// MyCat Data insert
				// MyCat 생성자의 파라미터는 사용 안됨
				MyCat objMyCat = new MyCat(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 6-6 TBJI_PC_SELLST MyCat 데이터 삭제

				deletePCFileContents("rsm1020_d17", sRaceDay);

				// 6-7 VW_MYCAT_SALES -> TBJI_PC_SELLST MyCat 데이터 복사
				objMyCat.insertSellstMyCat(this.getDao(rbmdao), sRaceDay,
						sSessionNo);

				// 입력 카운트 반환시킴
				nSellst = dmlcount;
				nSellstCount = dmlcount;
				aList = null;
				fReader = null;
				objSellst = null;
				objMyCat = null;
			}

			// 7. TELMP 파일 업로드
			if ("007".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nTelmp = Util.nullToInt((String) record.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
				FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스

				// 7-1. TELMP 파일에 데이터가 있는지 확인
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {

					// 7-2. TBJI_PC_TELMP 테이블에 해당 파일 데이터 먼저 삭제
					deleteTelmpFileContents("rsm1020_d15", sRaceDay);
				}

				Telmp objTelmp = new Telmp(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);

				// 7-3. TBJI_PC_TELMP 테이블에 데이터 입력
				int dmlcount = objTelmp.insertTelmp(aList);

				// 7-4. TBJI_PC_FILE 테이블을 조건(시행처,기준년도, 일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sTelmp, record);

				// 7-5. TBJI_PC_FILE 테이블에 이력 입력
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sTelmp, nTelmp, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// 입력 카운트 반환시킴
				nTelmp = dmlcount;
				nTelmpCount = dmlcount;
				aList = null;
				fReader = null;
				objTelmp = null;
			}

			// 8. WINNMP 파일 업로드
			if ("008".equals(record.getAttribute("FILE_CD"))
					&& !"".equals(FILE_UPLOAD_NAME)) {
				nWinnmp = Util
						.nullToInt((String) record.getAttribute("ROWCNT"));
				ArrayList aList = new ArrayList(); // 원본 데이터용 ArrayList
				FileReader fReader = new FileReader(); // 파일 데이터 읽는 클래스

				// 8-1. WINNMP 파일에 데이터가있는지 확인
				aList = fReader.alReadFile((String) record
						.getAttribute("FILE_PATH"));
				if (aList.size() > 0) {

					// 8-2. TBJI_PC_WINNMP 테이블에 해당 파일 데이터 먼저 삭제
					deletePCFileContents("rsm1020_d13", sRaceDay);
				}

				Winnmp objWinnmp = new Winnmp(ctx, (String) record
						.getAttribute("FILE_PATH"), this
						.getRbmDao("rbmjdbcdao"), SESSION_USER_ID, sMeetGubun);

				// 8-3. TBJI_PC_WINNMP 테이블에 데이터 입력
				int dmlcount = objWinnmp.insertWinnmp(aList);

				// 8-4. TBJI_PC_FILE 테이블을 조건(시행처,기준년도, 일자, 파일코드)으로 이력 삭제
				deleteFileUpHistory(sWinnmp, record);

				// 8-5. TBJI_PC_FILE 테이블에 이력 입력
				// 지급조서에 확정되어 있으면 값이 입력 안되기 때문에 클라이언트 카운트값 입력
				insertFileUpHistory(sWinnmp, nWinnmp, record, (String) record
						.getAttribute("FILE_PATH"), (String) record
						.getAttribute("FILE_UPLOAD_NAME"));

				// 입력 카운트 반환시킴
				nWinnmp = dmlcount;
				nWinnmpCount = dmlcount;
				aList = null;
				fReader = null;
				objWinnmp = null;
			}
			continue;
		}

		// 파일을 업로드 했는지 확인용(검증 성공, 실패 구분 할 때 사용)

		int nFileCount = 0;
		int[] nArrFileCount = new int[8];
		nArrFileCount[0] = nGitaCount;
		nArrFileCount[1] = nKtaxCount;
		nArrFileCount[2] = nPayoffsCount;
		nArrFileCount[3] = nResultsCount;
		nArrFileCount[4] = nSatlitCount;
		nArrFileCount[5] = nSellstCount;
		nArrFileCount[6] = nTelmpCount;
		nArrFileCount[7] = nWinnmpCount;

		for (int i = 0; i < nArrFileCount.length; i++) {
			nFileCount += nArrFileCount[i];
		}
		
		

		// 파일 업로드 검증 수정
		String sDsName = "";
		String sRtnVeri = "002";

		// 파일 올린게 있을 때 실행

		if (nFileCount > 1) {

			// 파일 검증 실행
			SaveVeri objVeri = new SaveVeri(ctx, this.getRbmDao("rbmjdbcdao"),
					this.getDao(rbmdao), SESSION_USER_ID);

			// 파일 업로드 확인
			strFILE_VERI = objVeri.selectVeriFile(nArrFileCount);

			// 6.1 1차 검증 검증 사항(TBES_SDL_DT,TBES_SDL_PT)
			String sVeriDTPTFst = objVeri.strVeriDTPTFst();

			// 6.2 1차 검증 검증 사항 (TBES_SDL_PT,TBJI_PC_PAYOFFS)
			String sVeriPTPayoffFst = objVeri.strVeriPTPayoffFst();

			// 6.3 2차 검증 SDL_DT SATLIT 검증
			String sVeriDTSatlitSnd = objVeri.strVeriDTSatlitSnd();

			// 6.4 2차 검증 확인(SDL_DT TBJI_PC_SELLST)
			String sVeriDTSellstSnd = objVeri.strVeriDTSellstSnd();

			// 6.5 2차 검증 SDL_DT SATLIT 검증
			// TELMP에는 그린카드 판매처별 분리가 안되어(전부 SELL_CD='01') : 그린카드는 SELL_CD='01'로 설정후 비교(2014.04.03)
			String sVeriDTTelmpSnd = objVeri.strVeriDTTelmpSnd();

			String sVeriDTPTPayoffFst = "002";

			String sFstVeri = "002";
			String sSndVeri = "002";
			if ("001".equals(sVeriDTPTFst) && "001".equals(sVeriPTPayoffFst)) {
				sFstVeri = "001";
			}
			if ("001".equals(sVeriDTSatlitSnd)
					&& "001".equals(sVeriDTSellstSnd)
					&& "001".equals(sVeriDTTelmpSnd)) {
				sSndVeri = "001";
			}
			
			if ("001".equals(sVeriDTPTFst) && "001".equals(sVeriPTPayoffFst)
					&& "001".equals(sVeriDTSatlitSnd)
					&& "001".equals(sVeriDTSellstSnd)
					&& "001".equals(sVeriDTTelmpSnd)) {
				sVeriDTPTPayoffFst = "001";
			}

			// 6.3 파일 검증, 1차 검증 검증 입력
			objVeri.insertVeriFst(record, SESSION_USER_ID, strFILE_VERI,
					sFstVeri, sSndVeri, sVeriDTPTPayoffFst);
			

			sRtnVeri = sVeriDTPTPayoffFst;
		}

		

		int nTempCnt = 0;

		// 파일 올린게 없을 때 실행
		sDsName = "dsPcUpList";

		if (ctx.get(sDsName) != null && nFileCount == 0) {

			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				record = ds.getRecord(i);
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
						|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {

					strFILE_VERI = (String) record.getAttribute("FILE_VERI");

					nTempCnt = savePcFileVeri(record); // 6.4 파일 업로드 수정
					nSaveCount = nSaveCount + nTempCnt;
					
					sRtnVeri = (String) record.getAttribute("VERI");
					
					continue;

				}
			}
		}

		Util.setReturnParam(ctx, "FILE_VERI", strFILE_VERI);

		Util.setReturnParam(ctx, "GITA_FILE_ROW_CNT", nGita + "");
		Util.setReturnParam(ctx, "KTAX_FILE_ROW_CNT", nKtax + "");
		Util.setReturnParam(ctx, "PAYO_FILE_ROW_CNT", nPayoffs + "");
		Util.setReturnParam(ctx, "RESU_FILE_ROW_CNT", nResults + "");
		Util.setReturnParam(ctx, "SATL_FILE_ROW_CNT", nSatlit + "");
		Util.setReturnParam(ctx, "SELL_FILE_ROW_CNT", nSellst + "");
		Util.setReturnParam(ctx, "TELM_FILE_ROW_CNT", nTelmp + "");
		Util.setReturnParam(ctx, "WINN_FILE_ROW_CNT", nWinnmp + "");

		
		Util.setReturnParam(ctx, "RESULT", "T"); // 결과
		Util.setReturnParam(ctx, "RESULT_VERI", sRtnVeri); // 결과
		
		
		
	}

	/**
	 * <p>
	 * 파일 업로드 검증 수정
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	protected int savePcFileVeri(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;
		param.setValueParamter(i++, record.getAttribute("FILE_VERI")); // 파일 검증
		param.setValueParamter(i++, SESSION_USER_ID); // 사용자ID(등록자)
		param.setValueParamter(i++, sStndYear); // 기준년도
		param.setValueParamter(i++, sDT); // 일자
		int dmlcount = this.getDao("rbmdao").update("rsm1020_u03", param);
		return dmlcount;
	}

	/**
	 * <p>
	 * 기존 PC 파일내용 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */
	protected void deletePCFileContents(String QueryId, String strDay) {

		PosParameter param = new PosParameter();
		int i = 0;

		param.setWhereClauseParameter(i++, strDay); // 일자

		this.getDao(rbmdao).update(QueryId, param);
	}

	protected void deleteTelmpFileContents(String QueryId, String strDay) {

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, strDay); // 일자

		this.getDao(rbmdao).update(QueryId, param);
	}

	

	/*
	 * RACE_DAY 로 조회해서 입력
	 */
	public void insertData(String queryID, String sRaceDay) {
		PosParameter param = new PosParameter();

		param.setWhereClauseParameter(0, sRaceDay); // 경주일

		this.getDao(rbmdao).update(queryID, param);
	}

	/**
	 * <p>
	 * PC 파일업로드 내역 입력
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	protected int insertFileUpHistory(String FileCd, int FILE_ROW_CNT,
			PosRecord record, String sFilePath, String sFileNm) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, sMeetGubun); // MEET_CD 경기장코드
		param.setValueParamter(i++, sStndYear); // STND_YEAR 기준년도
		// param.setValueParamter(i++, sDT); // RACE_DT 경주일
		param.setValueParamter(i++, record.getAttribute("DT")); // DT 경주일
		param.setValueParamter(i++, FileCd); // FILE_CD 파일종류
		param.setValueParamter(i++, FILE_ROW_CNT + ""); // 입력 갯수
		param.setValueParamter(i++, sFilePath); // FILE_PATH 파일 경로
		param.setValueParamter(i++, sFileNm); // FILE_NM 파일 이름
		param.setValueParamter(i++, sRaceDay); // RACE_DT 경주일
		param.setValueParamter(i++, searchGloInfo(sMeetGubun, "01")); // TMS
		param.setValueParamter(i++, searchGloInfo(sMeetGubun, "02")); // DAY_ORD
		param.setValueParamter(i++, sSellCd); // 발매처
		param.setValueParamter(i++, sSessionNo); // 세션번호
		param.setValueParamter(i++, SESSION_USER_ID); // 작성자ID
		param.setValueParamter(i++, SESSION_USER_ID); // 수정자ID

		int dmlcount = this.getDao(rbmdao).update("rsm1020_i01", param);

		return dmlcount;
	}

	/**
	 * <p>
	 * PC 파일업로드 내역 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */
	protected void deleteFileUpHistory(String FileCd, PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setWhereClauseParameter(i++, sMeetGubun); // MEET_CD 시행처
		param.setWhereClauseParameter(i++, sStndYear); // STND_YEAR 기준년도
		param.setWhereClauseParameter(i++, record.getAttribute("DT")); // 일자
		param.setWhereClauseParameter(i++, FileCd); // 파일종류

		this.getDao(rbmdao).update("rsm1020_d01", param);
	}

	public String searchGloInfo(String MeetGubun, String Gubun) {
		String sReturnVal;

		if ("01".equals(Gubun)) { // 회차
			if ("001".equals(sMeetGubun)) {
				sReturnVal = sGtms;
			} else {
				sReturnVal = sMtms;
			}
		} else { // 일차
			if ("001".equals(sMeetGubun)) {
				sReturnVal = sGday;
			} else {
				sReturnVal = sMday;
			}
		}

		return sReturnVal;
	}

	/**
	 * <p>
	 * 경기장코드 조회
	 * </p>
	 * 
	 * @param String
	 *            MeetCd 구 경륜장코드
	 * @return rMeetCd String 신 경륜장코드
	 * @throws none
	 */
	public static String searchMeet(String MeetCd) {
		String rMeetCd = "";

		if ("1".equals(MeetCd)) {
			rMeetCd = sGmeetCd;
		} else if ("2".equals(MeetCd)) {
			rMeetCd = sCmeetCd;
		} else if ("3".equals(MeetCd)) {
			rMeetCd = sMmeetCd;
		} else if ("4".equals(MeetCd)) {
			rMeetCd = sBmeetCd;
		}

		return rMeetCd;
	}

	/**
	 * <p>
	 * 발매처 조회
	 * </p>
	 * 
	 * @param String
	 *            AsscCd 구 Association Code
	 * @return rSellCd String 신 SELL_CD
	 * @throws none
	 */
	public static String searchSell(String AssoCd) {
		String rSellCd = "";

		if ("1".equals(AssoCd)) {
			rSellCd = sGsellCd;
		} else if ("2".equals(AssoCd)) {
			rSellCd = sCsellCd;
		} else if ("3".equals(AssoCd)) {
			rSellCd = sMsellCd;
		} else if ("4".equals(AssoCd)) {
			rSellCd = sBsellCd;
		}

		return rSellCd;
	}

	/**
	 * <p>
	 * 회차조회
	 * </p>
	 * 
	 * @param String
	 *            MeetCd 경륜장코드
	 * @return rTms String 회차
	 * @throws none
	 */
	public static String searchTms(String MeetCd) {
		String rTms = "";

		if ("1".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sGtms;
		} else if ("2".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sCtms;
		} else if ("3".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sMtms;
		} else if ("4".equals(MeetCd) || "001".equals(MeetCd)) {
			rTms = sBtms;
		}

		// rTms가 Null이고 구분이 경륜일 경우
		if (rTms.equals("") && "001".equals(sMeetGubun)) {
			rTms = sGtms;
			
		} else if (rTms.equals("") && "003".equals(sMeetGubun)) {
			rTms = sMtms;
			
		}
		
		if (rTms.equals("") && "1".equals(MeetCd)) rTms = "91"; //교차만 진행된 경우 경륜 임시회차 for telmp
		if (rTms.equals("") && "3".equals(MeetCd)) rTms = "93"; //교차만 진행된 경우 경정 임시회차 for telmp
		
		if (rTms.equals("") && "001".equals(MeetCd)) rTms = "91"; //교차만 진행된 경우 경륜 임시회차 for winmp
		if (rTms.equals("") && "003".equals(MeetCd)) rTms = "93"; //교차만 진행된 경우 경정 임시회차 for winmp

	System.out.println("searchTms("+MeetCd+")="+rTms);	
			return rTms;
	}

	/**
	 * <p>
	 * 일차조회
	 * </p>
	 * 
	 * @param String
	 *            MeetCd 경륜장코드
	 * @return rDay String 일차
	 * @throws none
	 */
	public static String searchDay(String MeetCd) {
		String rDay = "";

		if ("1".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sGday;
		} else if ("2".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sCday;
		} else if ("3".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sMday;
		} else if ("4".equals(MeetCd) || "001".equals(MeetCd)) {
			rDay = sBday;
		}

		// rDay Null이고 구분이 경륜일 경우
		if (rDay.equals("") && "001".equals(sMeetGubun)) {
			rDay = sGday;
		} else if (rDay.equals("") && "003".equals(sMeetGubun)) {
			rDay = sMday;
		}
		
		if (rDay.equals("") && ("1".equals(MeetCd) || "3".equals(MeetCd))) { //교차만 진행된 경우 경륜/경정 임시일차 for Telmp
			 if ("".equals(sCday)) rDay = sMday;
			 else rDay = sCday;
		}
		if (rDay.equals("") && ("001".equals(MeetCd) || "003".equals(MeetCd))) { //교차만 진행된 경우 경륜/경정 임시일차 for Winmp
			 if ("".equals(sCday)) rDay = sMday;
			 else rDay = sCday;
		}
System.out.println("searchDay("+MeetCd+")="+rDay);			
		return rDay;
	}

	/**
	 * <p>
	 * 투표소, 발매(지점)코드 체크
	 * </p>
	 * 
	 * @param String
	 *            inCode 투표소코드/발매(지점)코드
	 * @return rDay outCode 투표소코드/발매(지점)코드
	 * @throws none
	 */
	public static String searchoutCode(String inCode) {
		String outCode = "";

		outCode = inCode;
		if (inCode.length() == 1) {
			outCode = "0" + inCode;
		}

		return outCode;
	}

	/**
	 * <p>
	 * Comm 코드 변환
	 * </p>
	 * 
	 * @param String
	 *            inCode 신코드번호
	 * @return rDay outCode 구코드번호
	 * @throws none
	 */
	public static String searchCommCode(String inCode) {
		String outCode = "";

		if (inCode.length() == 1) {
			inCode = "0" + inCode;
		}

		if ("11".equals(inCode))
			outCode = "07"; // 수원 SUWON
		else if ("12".equals(inCode))
			outCode = "07"; // 중랑 JUNGNANG
		else if ("13".equals(inCode))
			outCode = "07"; // 일산 ILSAN
		else if ("14".equals(inCode))
			outCode = "07"; // 분당 BUNDANG
		else if ("15".equals(inCode))
			outCode = "07"; // 동대문 DONGDAEMUN
		else if ("16".equals(inCode))
			outCode = "07"; // 장안 JANGAN
		else if ("17".equals(inCode))
			outCode = "07"; // 산본 SANBON
		else if ("18".equals(inCode))
			outCode = "07"; // 부천 BUCHEON
		else if ("19".equals(inCode))
			outCode = "07"; // 관악 GWANAK
		else if ("20".equals(inCode))
			outCode = "07"; // 성북 GIREUM
		else if ("21".equals(inCode))
			outCode = "07"; // 영등포 YEONGDEUNGPO
		else if ("22".equals(inCode))
			outCode = "07"; // 대전 DAEJEON
		else if ("23".equals(inCode))
			outCode = "07"; // 인천 INCHEON
		else if ("24".equals(inCode))
			outCode = "07"; // 시흥 SIHEUNG
		else if ("25".equals(inCode))
			outCode = "07"; // 강남 GANGNAM
		else if ("26".equals(inCode))
			outCode = "07"; // 천안 CHEONAN
		else if ("27".equals(inCode))
			outCode = "07"; // 올림픽 OLYMPIC
		else if ("28".equals(inCode))
			outCode = "07"; // 의정부 UIJEONGBU
		else
			outCode = inCode;

		return outCode;
	}

	/**
	 * <p>
	 * Div 코드 변환
	 * </p>
	 * 
	 * @param String
	 *            inCode 신Comm코드번호
	 * @return rDay outCode 구div 코드번호
	 * @throws none
	 */
	public static String searchDivCode(String commCd, String divCd) {
		String outCode = "";

		if (commCd.length() == 1) {
			commCd = "0" + commCd;
		}
		if (divCd.length() == 1) {
			divCd = "0" + divCd;
		}

		if ("11".equals(commCd))
			outCode = "01"; // 수원 SUWON
		else if ("12".equals(commCd))
			outCode = "02"; // 중랑 JUNGNANG
		else if ("13".equals(commCd))
			outCode = "03"; // 일산 ILSAN
		else if ("14".equals(commCd))
			outCode = "05"; // 분당 BUNDANG --> 기존코드와 체계변경
		else if ("15".equals(commCd))
			outCode = "04"; // 동대문 DONGDAEMUN --> 기존코드와 체계변경
		else if ("16".equals(commCd))
			outCode = "06"; // 장안 JANGAN
		else if ("17".equals(commCd))
			outCode = "07"; // 산본 SANBON
		else if ("18".equals(commCd))
			outCode = "08"; // 부천 BUCHEON
		else if ("19".equals(commCd))
			outCode = "09"; // 관악 GWANAK
		else if ("20".equals(commCd))
			outCode = "10"; // 성북 GIREUM
		else if ("21".equals(commCd))
			outCode = "11"; // 영등포 YEONGDEUNGPO
		else if ("22".equals(commCd))
			outCode = "12"; // 대전 DAEJEON
		else if ("23".equals(commCd))
			outCode = "13"; // 인천 INCHEON
		else if ("24".equals(commCd))
			outCode = "14"; // 시흥 SIHEUNG
		else if ("25".equals(commCd))
			outCode = "15"; // 강남 GANGNAM
		else if ("26".equals(commCd))
			outCode = "16"; // 천안 CHEONAN
		else if ("27".equals(commCd))
			outCode = "17"; // 올림픽 OLYMPIC
		else if ("28".equals(commCd))
			outCode = "18"; // 의정부 UIJEONGBU
		else
			outCode = divCd;

		return outCode;
	}

	public static boolean isBranch(String inCode) {

		if (inCode.length() == 1) {
			inCode = "0" + inCode;
		}

		if ("11".equals(inCode) || "12".equals(inCode) || "13".equals(inCode)
				|| "14".equals(inCode) || "15".equals(inCode)
				|| "16".equals(inCode) || "17".equals(inCode)
				|| "18".equals(inCode) || "19".equals(inCode)
				|| "20".equals(inCode) || "21".equals(inCode)
				|| "22".equals(inCode) || "23".equals(inCode)
				|| "24".equals(inCode) || "25".equals(inCode)
				|| "26".equals(inCode) || "27".equals(inCode)
				|| "28".equals(inCode))
			return true;
		else
			return false;
	}

	/**
	 * <p>
	 * 경주번호 2자리 세팅 체크
	 * </p>
	 * 
	 * @param String
	 *            inRaceNo 경주번호
	 * @return String outRaceNo 경주번호
	 * @throws none
	 */
	public static String searchRaceNo(String inRaceNo) {
		String outRaceNo = "";

		outRaceNo = inRaceNo;
		if (inRaceNo.length() == 1) {
			outRaceNo = "0" + inRaceNo;
		}

		return outRaceNo;
	}

	// 이전에 입력된 PC 파일 갯수 카운트
	protected PosRow[] searchPCFileCount(PosGenericDao dao, PosContext ctx) {
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sDT = (String) ctx.get("DT"); // 일자

		PosParameter param = new PosParameter();

		int i = 0;
		param.setWhereClauseParameter(i++, sStndYear); // 0: STND_YEAR 기준년도
		param.setWhereClauseParameter(i++, sStndYear); // 1: STND_YEAR 기준년도
		param.setWhereClauseParameter(i++, sStndYear); // 2: STND_YEAR 기준년도
		param.setWhereClauseParameter(i++, sDT); // 3: DT

		PosRowSet prs = null;
		prs = dao.find("rsm1020_s02", param);
		PosRow pr[] = prs.getAllRow();
		return pr;
	}

	
    /**
     * <p> 기타소득세(그린카드) 자료 변경 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateGitaGrn(String sPayYear,String sPayDt) 
    {
    	// 그린카드db에서 가져온 해당 일자 기타소득세액 정보를 삭제한다. 
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, sPayYear+sPayDt);	//지급년월일        
        int dmlcount = this.getDao("rbmdao").update("rsm1020_d02", param);

    	// 그린카드db에서 해당 일자의 기타소득세액을 가져온다.
        param = new PosParameter();
        i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//수정자 사번
        param.setValueParamter(i++, sPayYear+sPayDt);	//지급년월일        
        dmlcount += this.getDao("rbmdao").update("rsm1020_i22", param);

        // 그린카드db에서 가져온 정보를 이용하여 지점을 세분화해 준다.
        param = new PosParameter();
        i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//수정자 사번
        param.setValueParamter(i++, sPayYear+sPayDt);	//지급년월일        
        dmlcount += this.getDao("rbmdao").update("rsm1020_u01", param);

        return dmlcount;
    }

    /**
     * <p> 기타소득세(그린카드) 자료 변경 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateTaxGrn(String sPayYear,String sPayDt) 
    {
    	// 그린카드db에서 가져온 해당 일자 기타소득세액 정보를 삭제한다. 
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//수정자 사번
        param.setValueParamter(i++, sPayYear+sPayDt);	//지급년월일        
        int dmlcount = this.getDao("rbmdao").update("rsm1020_u02", param);

        return dmlcount;
    }

}
