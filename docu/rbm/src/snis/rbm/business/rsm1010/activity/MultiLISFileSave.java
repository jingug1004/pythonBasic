/*================================================================================
 * 시스템			: LIS 파일 데이터 멀티 업데이트
 * 소스파일 이름	: snis.rbm.business.rsm1010.activity.MultiLISFileSave.java
 * 파일설명		: LIS 파일 한꺼번에 저장
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-12-09
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1010.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.LISFileReader;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class MultiLISFileSave extends SnisActivity {
	public MultiLISFileSave() {
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
		String sDsName = "";
		String sFileCd = "002";
		String sResult = "F";
		PosDataset ds;
		PosRow[] pr = null;
		int nSize = 0;
		int nTempCnt = 0;
		PosRowSet prs = null;
		sDsName = "dsLISFile";

		ArrayList aList = null;
		String sFName = "";
		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);

			PosRecord record = ds.getRecord(0);

			// 1. LIS 파일 경로 입력
			String FILE_PATH = (String) record.getAttribute("FILE_PATH"); // 파일
			// 경로
			// 입력됨
			File file = null;
			File[] files = new File(FILE_PATH).listFiles();
			String MEET_CD = "";
			String STND_YEAR = "";
			String DT = "";
			HashMap hMap = null;
			for (int fileI = 0; fileI < files.length; fileI++) {
				file = files[fileI];

				sFName = file.getName().substring(0,
						file.getName().indexOf("."));

				// 1. 경륜 경정 코드 찾기
				PosParameter param = new PosParameter();
				param.setWhereClauseParameter(0, sFName);
				prs = this.getDao("rbmdao").find("rsm1010_s08", param);
				pr = prs.getAllRow();
				MEET_CD = (String) pr[0].getAttribute("MEET_CD");
				STND_YEAR = sFName.substring(0, 4);
				DT = sFName.substring(4, 8);
				
				// 2. 임시 테이블 (TBRD_LIS_FILE_TMP) 에서 해당 일자의 LIS 파일 데이터 먼저 삭제
				deleteLISFile(this.getDao("rbmdao"), sFName);
				System.out.println("MEET_CD : "+MEET_CD+" , STND_YEAR : "+STND_YEAR+" , DT : "+DT);

				// 3. LIS 파일 데이터 호출

				LISFileReader lisfReader = new LISFileReader();
				aList = lisfReader.readFile(file + "");

				// 4. 임시 테이블 (TBRD_LIS_FILE_TMP) 에 LIS 파일 데이터 입력
				saveLISFile(aList,MEET_CD,STND_YEAR,DT);
				
				// 5. DAS DB 와 비교 검증
				searchCompareDasAndLIS(this.getDao("rbmdao"), sFName);
				/*
				 * for (int j = 0; j < aList.size(); j++) { aList2 = (ArrayList)
				 * aList.get(j); for (int k = 0; k < aList2.size(); k++) { //
				 * LIS 해당 파일의 데이터 hMap = (HashMap) aList2.get(k);
				 *  // 경륜 경정 코드 찾기 System.out.println(hMap); } }
				 */

			}

			// 3. 해당 일자의 MEET_CD 값 생성

			// 3. 입력하려는 년도, 일자의 LIS FILE 데이터 삭제

			// int nDeleteCount = lisf.deleteLISFile(this.getDao("rbmdao"),
			// aList2);

			// 4. DAS 데이터에서 해당 LIS 파일 데이터와 매핑 해서 검증, 입력

			// int dmlLISFile = 0;
			// int intDmlCount = lisf.saveLISFile(aList2); // LIS 파일에 DAS

			// 입력하려는 년도, 일자에 해당하는 DAS 데이터 검색
			// pr = lisf.searchDasLIS(this.getDao("rbmdao"), record); // 데이터와 비교
			// 하면서
			// 저장

			nSaveCount = nSaveCount + nTempCnt;
			// aList = null;

			sResult = "T";

		}

		Util.setReturnParam(ctx, "FILE_CD", sFileCd); // 업로드 파일 결과
		Util.setReturnParam(ctx, "RESULT", sResult); // 결과
	}

	// LIS FILE 저장

	public int saveLISFile(ArrayList aList, String MEET_CD, String STND_YEAR,
			String DT) {
		int intResult = 0; // 결과값

		// 파일을 읽어들여 DB INSERT

		ArrayList alDBInsert = new ArrayList();
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);

			if (!"F".equals(strBrnchCode((String) hMap.get("DIVISION")))) {
				alDBInsert.add(hMap);
			}
		}

		String[] arrQuery = new String[alDBInsert.size()];

		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);

			arrQuery[i] = " INSERT INTO TBRD_LIS_FILE_TMP ( /* MultiLISFileSave.saveLISFile */ \n"
					+ " MEET_CD,	\n"
					+ " STND_YEAR, 	\n"
					+ " DT,		\n"
					+ " BRNC_CD, 	\n"
					+ " DIVISION, 	\n"
					+ " GROSS_SALES,	\n"
					+ " CANCELS, 	\n"
					+ " TOT_SALES, 	\n"
					+ " CASHES,	\n"
					+ " NET_INCOME, 	\n"
					+ " DRAWS, 	\n"
					+ " RETURNS,	\n"
					+ " BALANCE, 	\n"

					+ " VERI, \n"
					+ " VERI_DT, \n"

					+ " FILE_PATH, 	\n"
					+ " FILE_NM, 	\n"
					+ " INST_ID,	\n"
					+ " INST_DT, 	\n"
					+ " UPDT_ID, 	\n"
					+ " UPDT_DT	 \n"
					+ " ) VALUES ( " + 
					" '"+ MEET_CD+ "',	\n"
					+ " '"+ STND_YEAR+ "', 	\n"
					+ " '"+ DT+ "',		\n"
					+ " '"+ strBrnchCode((String) hMap.get("DIVISION"))+ "', 	\n"
					+ "'"
					+ hMap.get("DIVISION")+ "', 	\n"
					+ "'"
					+ hMap.get("GROSS SALES")+ "' ,	\n"
					+ "'"
					+ hMap.get("CANCELS")+ "', 	\n"
					+ "'"
					+ hMap.get("TOT SALES")+ "', 	\n"
					+ "'"
					+ hMap.get("CASHES")+ "',	\n"
					+ "'"
					+ hMap.get("NET INCOME")+ "', 	\n"
					+ "'"
					+ hMap.get("DRAWS")+ "'  , 	\n"
					+ "'"
					+ hMap.get("RETURNS")+ "'  ,	\n"
					+ "'"
					+ hMap.get("BALANCE")+ "',	\n"

					+ " null  , 	\n" // 검증
					+ "SYSDATE  , 	\n"
					+ " null, 	\n"
					+ " null, 	\n"
					+ "'SYSTEM',\n"
					+ " SYSDATE, 	\n" 
					+ "'SYSTEM', \n"
					+ " SYSDATE ) \n ";
			
		}


		int[] insertCounts = this.getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
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

	// DAS 데이터 와 비교 검증
	protected int searchCompareDasAndLIS(PosGenericDao dao, String RACE_DAY) {
		PosParameter param = new PosParameter();
		int i = 0;
		
		String STND_YEAR = RACE_DAY.substring(0, 4);
		String DT = RACE_DAY.substring(4, 8);
		
		param.setWhereClauseParameter(i++, STND_YEAR);
		param.setWhereClauseParameter(i++, DT);
		
		param.setWhereClauseParameter(i++, RACE_DAY);
		param.setWhereClauseParameter(i++, RACE_DAY);

		PosRowSet prs = null;
		prs = dao.find("rsm1010_s09", param); // 사용한다.
		PosRow pr[] = prs.getAllRow();
		
		

		String[] arrQuery = new String[pr.length];

		for (int prI = 0; prI < pr.length; prI++) {
			arrQuery[prI] = " UPDATE  TBRD_LIS_FILE_TMP		   \n"+ 
			" SET  VERI = '"+pr[prI].getAttribute("TF")+"'        --  검증		   \n"+ 
			"    ,UPDT_ID      = 'SYSTEM'        -- 수정자ID  \n"+ 
			"    ,UPDT_DT      = SYSDATE  -- 수정일시  \n"+             
			" WHERE  1=1				   \n"+ 
			" AND  MEET_CD   = '"+pr[prI].getAttribute("MEET_CD")+"'    -- 경륜 경정 구분 코드	   \n"+ 
			" AND  STND_YEAR = '"+pr[prI].getAttribute("STND_YEAR")+"'    -- 기준년도	   \n"+ 
			" AND  DT      = '"+pr[prI].getAttribute("DT")+"'   -- 일자		   \n"+ 
			" AND  BRNC_CD = '"+pr[prI].getAttribute("BRNC_CD")+"'   -- 지점 코드	   \n";

		}

		int[] insertCounts = getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
		int intResult = 0; // 결과값
		int failure_count = 0;

		if (failure_count == 0) {
			intResult = insertCounts.length;
		} else {
			intResult = 0;
		}

		return intResult;

		
	}

	public void deleteLISFile(PosGenericDao dao, String FILENAME) {
		PosParameter param = new PosParameter();
		String STND_YEAR = FILENAME.substring(0, 4);
		String DT = FILENAME.substring(4, 8);

		int i = 0;
		param.setWhereClauseParameter(i++, STND_YEAR);
		param.setWhereClauseParameter(i++, DT);

		dao.update("rsm1010_d02", param); // LIS FILE 데이터 삭제
		// 사용한다.
	}

	public String strBrnchCode(String strBrncNm) {

		String strResult = "";

		/* 2013.5.31 지점명칭변경
		String strArrBrnchNm[] = { "SUWON", "SANGBONG", "ILSAN", "BUNDANG",
				"DONGDAEMUN", "JANGAN", "SANBON", "BUCHEON", "GWANAK",
				"GILEUM", "DANGSAN", "YUSEONG", "INCHEON", "SIHEUNG",
				"NONHYEON", "CHEONAN", "OLYMPIC", "UIJEONGBU", "ATL WINDOWS",
				"BOONDANG", "GIREUM", "YUSEONG", "OLYMPIC", "SECOND FLOOR",
				"THIRD FLOOR", "FOURTH FLOOR", "VIP", "SAM", "A PART" };
		*/
		String strArrBrnchNm[] = { "SUWON", "JUNGNANG", "ILSAN", "BUNDANG",
				"DONGDAEMUN", "JANGAN", "SANBON", "BUCHEON", "GWANAK",
				"SEONGBUK", "YEONGDEUNGPO", "DAEJEON", "INCHEON", "SIHEUNG",
				"GANGNAM", "CHEONAN", "OLYMPIC", "UIJEONGBU", "ATL WINDOWS",
				"BOONDANG", "GIREUM", "DAEJEON", "OLYMPIC", "SECOND FLOOR",
				"THIRD FLOOR", "FOURTH FLOOR", "VIP", "SAM", "A PART" };

		String[] strArrBrnchCd = { "11", "12", "13", "14", "15",// "SUWON",
																// "JUNGNANG",
																// "ILSAN",
																// "BUNDANG",
																// "DONGDAEMUN",
				"16", "17", "18", "19", "20",// "JANGAN", "SANBON",
												// "BUCHEON", "GWANAK",
												// "SEONGBUK",
				"21", "22", "23", "24", "25",// "YEONGDEUNGPO", "DAEJEON",
												// "INCHEON", "SIHEUNG",
												// "GANGNAM",
				"26", "27", "28", "06", "14",// "CHEONAN", "OLYMPIC",
												// "UIJEONGBU","ATL WINDOWS",
												// "BOONDANG",
				"20", "22", "27", "01", "02",// "GIREUM", "DAEJEON",
												// "OLYMPIC", "SECOND FLOOR",
												// "THIRD FLOOR",
				"03", "04", "08", "98" }; // "FOURTH FLOOR", "VIP", "SAM", "A
											// PART"};

		int intMachCount = 0;

		for (int i = 0; i < strArrBrnchNm.length; i++) {

			if (strBrncNm.trim().indexOf(strArrBrnchNm[i]) != -1) {

				strResult = strArrBrnchCd[i];
				intMachCount++;
			}
		}
		if (intMachCount == 0) {
			strResult = "F";
		}

		return strResult;
	}

}
