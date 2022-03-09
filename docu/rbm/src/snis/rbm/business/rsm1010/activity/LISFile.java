/*================================================================================
 * 시스템			: LIS 파일 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1010.activity.LISFile.java
 * 파일설명		: LIS 파일 업로드
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1010.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;

public class LISFile {
	private PosContext ctx = null;
	private PosRecord record = null;
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";

	public LISFile() {
	}

	public LISFile(PosContext ctx, PosRecord record,RbmJdbcDao rbmjdbcdao, String session_user_id) {
		this.ctx = ctx;
		this.record = record;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;

	}
	public LISFile(RbmJdbcDao rbmjdbcdao, String session_user_id) { 
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;

	}
	
	protected PosRow[] searchDasLIS(PosGenericDao dao, PosRecord record) {
		PosParameter param = new PosParameter();
		String RACE_DAY = (String)record.getAttribute("RACE_DAY");
		int i = 0;
		param.setWhereClauseParameter(i++, RACE_DAY);
		param.setWhereClauseParameter(i++, RACE_DAY);
		
		PosRowSet prs = null;		
		prs = dao.find("rsm1010_s07",param);		// 사용한다.
		PosRow pr[] = prs.getAllRow();
		return pr;
	}

	// das 와 비교 검증 (실시간 검증 안됨, Das 데이터가 늦게 올라와서 프로그램으로는 불가능함)
	protected String searchVeriDasX(HashMap hMap, PosRow [] pr) {
		
		String sResult = ""; // 결과값 
		String strBrnchCode = strBrnchCode((String)hMap.get("DIVISION"));	// LIS 파일 지점코드
		String sGROSS_SALES	=  (String)hMap.get("GROSS SALES");				// LIS 파일 현금수입
		String sCANCELS	=  (String) hMap.get("CANCELS");					// LIS 파일 취소 금액
		String sTOT_SALES	=  (String) hMap.get("TOT SALES");				// LIS 파일 실수입액
		String sCASHES	=  (String) hMap.get("CASHES");						// LIS 파일 지출액		
		String sNET_INCOME	=  (String)  hMap.get("NET INCOME");			// LIS 파일 실입금액
		String sBALANCE = (String)hMap.get("BALANCE");						// LIS 파일 현금수입
		
		int intMachCount =0;	// 검증 카운트
		
		for(int i=0;i<pr.length;i++)
		{
			String COMM_NO			= (String)pr[i].getAttribute("COMM_NO");			// 지점 코드
			String DAS_GROSS_SALE	= (String)pr[i].getAttribute("DAS_GROSS_SALE");		// DAS 현금수입
			String DAS_CANCEL			= (String)pr[i].getAttribute("DAS_CANCEL");		// 취소 금액
			String DAS_TOT_SALE			= (String)pr[i].getAttribute("DAS_TOT_SALE");	// 실수입액
			String DAS_CASHE			= (String)pr[i].getAttribute("DAS_CASHE");		// 지출액
			String DAS_NET_INCOME		= (String)pr[i].getAttribute("DAS_NET_INCOME");	// 실입금액
			String DAS_BALANCE			= (String)pr[i].getAttribute("DAS_BALANCE");	// 현금수입
			
			if(!COMM_NO.equals(strBrnchCode))
				continue;
			
			
	        if(COMM_NO.equals(strBrnchCode)&&DAS_GROSS_SALE.equals(sGROSS_SALES)&& DAS_CANCEL.equals(sCANCELS)&& DAS_TOT_SALE.equals(sTOT_SALES)&&DAS_CASHE.equals(sCASHES)&&DAS_NET_INCOME.equals(sNET_INCOME)&&DAS_BALANCE.equals(sBALANCE))
	        {	        	
	        	intMachCount++;
	        }
		}		
		
		
		
		if(intMachCount==0) // 1개라도 다르면 해당 지점은 불일치 
		{
			sResult="002";
		}
		else
		{
			sResult="001";
		}
		return sResult;
	}
	
	
	// LIS FILE 저장 -- parameter modified by TOMMY(2013.8.30)
//	public int saveLISFile(ArrayList aList, PosRow [] pr) {
	public int saveLISFile(ArrayList aList) {
		int intResult = 0; // 결과값

		String MEET_CD   = (String)record.getAttribute("MEET_CD");
		String STND_YEAR = (String)record.getAttribute("STND_YEAR");
		String DT        = (String)record.getAttribute("DT");
		String FILE_PATH = (String)record.getAttribute("FILE_PATH");
		String FILE_NM   = (String)record.getAttribute("FILE_NM");
		
		// 파일을 읽어들여 DB INSERT
		
		ArrayList alDBInsert = new ArrayList();
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);
			
			if(!"F".equals(strBrnchCode((String)hMap.get("DIVISION"))))
			{
				alDBInsert.add(hMap);
			}
		}

		String[] arrQuery = new String[alDBInsert.size()];
		
		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);
			
			arrQuery[i] = " INSERT INTO TBRD_LIS_FILE ( /* LISFile.saveLISFile */ "
					+ " MEET_CD,	"
					+ " STND_YEAR, 	"
					+ " DT,		"
					+ " SELL_CD, 	"
					+ " BRNC_CD, 	"
					+ " DIVISION, 	"
					+ " GROSS_SALES,	"
					+ " CANCELS, 	"
					+ " TOT_SALES, 	"
					+ " CASHES,	"
					+ " NET_INCOME, 	"
					+ " DRAWS, 	"
					+ " RETURNS,	"
					+ " BALANCE, 	"
					
				//	+ " VERI,"
				//	+ " VERI_DT,"

					+ " FILE_PATH, 	"
					+ " FILE_NM, 	"
					+ " INST_ID,	"
					+ " INST_DT, 	"
					+ " UPDT_ID, 	"
					+ " UPDT_DT	"
					+ " ) VALUES ( " + " '"
					+ MEET_CD
					+ "',	"
					+ " '"
					+ STND_YEAR
					+ "', 	"
					+ " '"
					+ DT
					+ "',		"
					+ "'"+ hMap.get("SELL_CD")+ "', 	"
					+ "'"+ strBrnchCode((String)hMap.get("DIVISION"))+ "', 	"
					+ "'"+ hMap.get("DIVISION")+ "', 	"
					+ "'"
					+ hMap.get("GROSS SALES")
					+ "' ,	"
					+ "'"
					+ hMap.get("CANCELS")
					+ "', 	"
					+ "'"
					+ hMap.get("TOT SALES")
					+ "', 	"
					+ "'"
					+ hMap.get("CASHES")
					+ "',	"
					+ "'"
					+ hMap.get("NET INCOME")
					+ "', 	"
					+ "'"
					+ hMap.get("DRAWS")
					+ "'  , 	"
					+ "'"
					+ hMap.get("RETURNS")
					+ "'  ,	"
					+ "'"+ hMap.get("BALANCE")+ "',	"
					
				//	+ "'"+ searchVeriDasX(hMap, pr)+ "'  , 	" // 검증
				//	+ "SYSDATE  , 	"
					
					+ " '"
					+ FILE_PATH
					+ "', 	"
					+ " '"
					+ FILE_NM
					+ "', 	"
					+ "'"+user_id+"',"
					+ " SYSDATE, 	"
					+ "'"+user_id+"', "
					+ " SYSDATE ) \n ";
			
			//logger.logDebug("["+i+"]"+arrQuery[i]);
		
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


	// LIS FILE 저장
	public int saveAllLISFile(ArrayList aList, HashMap reco) {
		int intResult = 0; // 결과값

		String MEET_CD   = (String)reco.get("MEET_CD");
		String STND_YEAR = (String)reco.get("STND_YEAR");
		String DT        = (String)reco.get("DT");
		String FILE_PATH = (String)reco.get("FILE_PATH");
		String FILE_NM   = (String)reco.get("FILE_NM");
		
		// 파일을 읽어들여 DB INSERT
		
		ArrayList alDBInsert = new ArrayList();
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);
			
			if(!"F".equals(strBrnchCodeAll((String)hMap.get("DIVISION"),hMap)))
			{
				alDBInsert.add(hMap);
			}
		}

		String[] arrQuery = new String[alDBInsert.size()];
		
		for (int i = 0; i < alDBInsert.size(); i++) {

			hMap = (HashMap) alDBInsert.get(i);
			
			arrQuery[i] = " INSERT INTO TBRD_LIS_FILE ( /* LISFile.saveAllLISFile */ "
					+ " MEET_CD,	"
					+ " STND_YEAR, 	"
					+ " DT,		"
					+ " SELL_CD, 	"
					+ " BRNC_CD, 	"
					+ " DIVISION, 	"
					+ " GROSS_SALES,	"
					+ " CANCELS, 	"
					+ " TOT_SALES, 	"
					+ " CASHES,	"
					+ " NET_INCOME, 	"
					+ " DRAWS, 	"
					+ " RETURNS,	"
					+ " BALANCE, 	"
					
				//	+ " VERI,"
				//	+ " VERI_DT,"

					+ " FILE_PATH, 	"
					+ " FILE_NM, 	"
					+ " INST_ID,	"
					+ " INST_DT, 	"
					+ " UPDT_ID, 	"
					+ " UPDT_DT	"
					+ " ) VALUES ( " + " '"
					+ MEET_CD
					+ "',	"
					+ " '"
					+ STND_YEAR
					+ "', 	"
					+ " '"
					+ DT
					+ "',		"
					+ "'"+ hMap.get("SELL_CD")+ "', 	"
					+ "'"+ strBrnchCodeAll((String)hMap.get("DIVISION"),hMap)+ "', 	"
					+ "'"+ hMap.get("DIVISION")+ "', 	"
					+ "'"
					+ hMap.get("GROSS SALES")
					+ "' ,	"
					+ "'"
					+ hMap.get("CANCELS")
					+ "', 	"
					+ "'"
					+ hMap.get("TOT SALES")
					+ "', 	"
					+ "'"
					+ hMap.get("CASHES")
					+ "',	"
					+ "'"
					+ hMap.get("NET INCOME")
					+ "', 	"
					+ "'"
					+ hMap.get("DRAWS")
					+ "'  , 	"
					+ "'"
					+ hMap.get("RETURNS")
					+ "'  ,	"
					+ "'"+ hMap.get("BALANCE")+ "',	"
					
				//	+ "'"+ searchVeriDasX(hMap, pr)+ "'  , 	" // 검증
				//	+ "SYSDATE  , 	"
					
					+ " '"
					+ FILE_PATH
					+ "', 	"
					+ " '"
					+ FILE_NM
					+ "', 	"
					+ "'"+user_id+"',"
					+ " SYSDATE, 	"
					+ "'"+user_id+"', "
					+ " SYSDATE ) \n ";
			
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
	 * LIS 파일 데이터 삭제
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @throws none
	 */

	public void deleteLISFile(PosGenericDao dao, PosRecord record) {
		PosParameter param = new PosParameter();
		String STND_YEAR = (String)record.getAttribute("STND_YEAR");
		String DT = (String)record.getAttribute("DT");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		
		int i = 0;
		param.setWhereClauseParameter(i++, STND_YEAR);
		param.setWhereClauseParameter(i++, DT);

		dao.update("rsm1010_d01", param); // LIS FILE 데이터 삭제
		// 사용한다.
	}


	/**
	 * 지점 코드 반환
	 */

	public String strBrnchCode(String strBrncNm) {
		
		String strResult = "";
		String MEET_CD = (String)record.getAttribute("MEET_CD");
		
		/* 2013.5.31 지점명칭변경
		String strArrBrnchNm[] = {"SUWON", "SANGBONG", "ILSAN", "BUNDANG", "DONGDAEMUN",
				"JANGAN",		"SANBON",	"BUCHEON",	"GWANAK",		"GILEUM",
				"DANGSAN",		"YUSEONG",	"INCHEON",	"SIHEUNG",		"NONHYEON",    
				"CHEONAN",		"OLYMPIC",  "UIJEONGBU","ATL WINDOWS",	"BOONDANG",
				"GIREUM",		"YUSEONG",	"OLYMPIC",	"SECOND FLOOR",	"THIRD FLOOR",
				"FOURTH FLOOR",	"VIP",		"SAM",		"A PART"};
		*/
		String strArrBrnchNm[] = {"SUWON", "JUNGNANG", "ILSAN", "BUNDANG", "DONGDAEMUN",
				"JANGAN",		"SANBON",	"BUCHEON",	"GWANAK",		"SEONGBUK",
				"YEONGDEUNGPO",		"DAEJEON",	"INCHEON",	"SIHEUNG",		"GANGNAM",    
				"CHEONAN",		"OLYMPIC",  "UIJEONGBU","ATL WINDOWS",	"BOONDANG",
				"GIREUM",		"DAEJEON",	"OLYMPIC",	"SECOND FLOOR",	"THIRD FLOOR",
				"FOURTH FLOOR",	"VIP",		"SAM",		"A PART"};
		
		
		/*
				
		strArrBrnchNm[0]="SUWON"; 
		strArrBrnchNm[1]="JUNGNANG"; 
		strArrBrnchNm[2]="ILSAN";
		strArrBrnchNm[3]="BUNDANG";		
		strArrBrnchNm[4]="DONGDAEMUN"; 		 
		strArrBrnchNm[5]="JANGAN"; 
		strArrBrnchNm[6]="SANBON"; 
		strArrBrnchNm[7]="BUCHEON"; 
		strArrBrnchNm[8]="GWANAK"; 
		strArrBrnchNm[9]="SEONGBUK";
		strArrBrnchNm[10]="YEONGDEUNGPO"; 		
		strArrBrnchNm[11]="YOUSEONG";	
		strArrBrnchNm[12]="INCHEON"; 
		strArrBrnchNm[13]="SIHEUNG"; 
		strArrBrnchNm[14]="GANGNAM"; 
		strArrBrnchNm[15]="CHEONAN"; 
		strArrBrnchNm[16]="OLYMPHIC";
		strArrBrnchNm[17]="UIJEONGBU";
		strArrBrnchNm[18]="ATL WINDOWS";
		strArrBrnchNm[19]="BOONDANG";
		strArrBrnchNm[20]="GIREUM";
		strArrBrnchNm[21]="DAEJEON";
		strArrBrnchNm[22]="OLYMPIC";
		
		strArrBrnchNm[23]=""; // 전체
		
		// 광명 본장		
		strArrBrnchNm[24]="SECOND FLOOR";	// 광명 2층
		strArrBrnchNm[25]="THIRD FLOOR";	// 광명 3층
		strArrBrnchNm[26]="FOURTH FLOOR";	// 광명 4층
		strArrBrnchNm[27]="VIP";	// 광명 VIP
		strArrBrnchNm[28]="SAM";	// 광명 SAM
		
		// 미사리 본장		
		strArrBrnchNm[29]="A PART";	// 미사리
		
		*/
		

		/*
		if("003".equals(MEET_CD))
		{
			strArrBrnchNm[19]="ASSOCIATION2";
		}
		else
		{
			strArrBrnchNm[19]="ASSOCIATION1";
		}
		
	*/
		
		
		String [] strArrBrnchCd =  {"11","12","13","14","15",
									"16","17","18","19","20",
									"21","22","23","24","25",    
									"26","27","28","06","14",
									"20","22","27","01","02",
									"03","04","08","98"};
		/*      
		strArrBrnchCd[0] = "11"; 	// SUWON (경정 X)        
		strArrBrnchCd[1] = "12"; 	// JUNGNANG                 
		strArrBrnchCd[2] = "13"; 	// ILSAN                 
		strArrBrnchCd[3] = "14"; 	// BOONDANG                 
		strArrBrnchCd[4] = "15"; 	// DONGDAEMUN (경정 X)      
		
		strArrBrnchCd[5] = "16"; 	// JANGAN                 
		strArrBrnchCd[6] = "17"; 	// SANBON (경정 X)        
		strArrBrnchCd[7] = "18"; 	// BUCHEON (경정 X)        
		strArrBrnchCd[8] = "19"; 	// GWANAK                 
		strArrBrnchCd[9] = "20"; 	// SEONGBUK                 
		
		strArrBrnchCd[10] = "21"; 	// YEONGDEUNGPO (경정 X)
		strArrBrnchCd[11] = "22"; 	// YOUSEONG                 
		strArrBrnchCd[12] = "23"; 	// INCHEON                 
		strArrBrnchCd[13] = "24"; 	// SIHEUNG                 
		strArrBrnchCd[14] = "25"; 	// GANGNAM                 
		
		strArrBrnchCd[15] = "26"; 	// CHEONAN                 
		strArrBrnchCd[16] = "27"; 	// OLYMPHIC               
		strArrBrnchCd[17] = "28"; 	// UIJEONGBU
		strArrBrnchCd[18] = "06";	// ATL WINDOWS
		strArrBrnchCd[19] = "14"; 	// BOONDANG    
		
		strArrBrnchCd[20] = "20"; 	// SEONGBUK
		strArrBrnchCd[21] = "22"; 	// YOUSEONG
		strArrBrnchCd[22] = "27"; 	// OLYMPHIC
		
		strArrBrnchCd[23] = ""; 	// 전체

		
		// 광명 본장		
		strArrBrnchCd[24]="01";	// 광명 2층
		strArrBrnchCd[25]="02";	// 광명 3층
		
		strArrBrnchCd[26]="03";	// 광명 4층
		strArrBrnchCd[27]="04";	// 광명 VIP
		strArrBrnchCd[28]="08";	// SAM
		
		// 미사리 본장
		strArrBrnchCd[29]="98";	// 미사리
		
		*/
		
		int intMachCount =0 ;
	
		for( int i=0;i<strArrBrnchNm.length;i++)
		{
		
			if(strBrncNm.trim().indexOf(strArrBrnchNm[i])!=-1 )
			{	
				
				strResult = strArrBrnchCd[i];
				intMachCount++;
			}
		}
		if(intMachCount==0)
		{
			strResult="F";
		}
		
		
		return strResult;
	}
	

	public String strBrnchCodeAll(String strBrncNm,HashMap reco) {
		
		String strResult = "";
		String MEET_CD = (String)reco.get("MEET_CD");
		
		String strArrBrnchNm[] = {"SUWON", "JUNGNANG", "ILSAN", "BUNDANG", "DONGDAEMUN",
				"JANGAN",		"SANBON",	"BUCHEON",	"GWANAK",		"SEONGBUK",
				"YEONGDEUNGPO",		"DAEJEON",	"INCHEON",	"SIHEUNG",		"GANGNAM",    
				"CHEONAN",		"OLYMPIC",  "UIJEONGBU","ATL WINDOWS",	"BOONDANG",
				"GIREUM",		"DAEJEON",	"OLYMPIC",	"SECOND FLOOR",	"THIRD FLOOR",
				"FOURTH FLOOR",	"VIP",		"SAM",		"A PART"};
		
		 
		
		
		String [] strArrBrnchCd =  {"11","12","13","14","15",
									"16","17","18","19","20",
									"21","22","23","24","25",    
									"26","27","28","06","14",
									"20","22","27","01","02",
									"03","04","08","98"};
	 
		
		int intMachCount =0 ;
	
		for( int i=0;i<strArrBrnchNm.length;i++)
		{
		
			if(strBrncNm.trim().indexOf(strArrBrnchNm[i])!=-1 )
			{	
				
				strResult = strArrBrnchCd[i];
				intMachCount++;
			}
		}
		if(intMachCount==0)
		{
			strResult="F";
		}
		
		
		return strResult;
	}
	
}
