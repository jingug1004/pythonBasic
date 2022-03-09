package snis.rbm.business.rsm1020.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;


public class SaveVeri {
	private PosContext ctx = null;
	private RbmJdbcDao rbmjdbcDao = null;
	private PosGenericDao rbmdao=null;
	public SaveVeri() {
	}

	public SaveVeri(PosContext ctx, RbmJdbcDao rbmjdbcDao,PosGenericDao rbmdao, String session_user_id) {
		this.ctx = ctx;
		this.rbmjdbcDao = rbmjdbcDao;
		this.rbmdao= rbmdao;
	}

	// 1차 검증  DT PT 검증
	public String strVeriDTPTFst() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // 구분 MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // 경주일

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_sVeriDTPTFst", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // 검증 오류 카운트
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0)
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	
	// 1차 검증  PT PAYOFF 검증
	public String strVeriPTPayoffFst() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // 구분 MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // 경주일

		PosParameter param = new PosParameter();
		int i = 0;
		
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_sVeriPTPayoffFst", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // 검증 오류 카운트
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	
	// 2차 검증 SDL_DT SATLIT 검증
	public String strVeriDTSatlitSnd() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // 구분 MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // 경주일

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_s05", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // 검증 오류 카운트
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	// 2차 검증 확인(SDL_DT TBJI_PC_SELLST)
	public String strVeriDTSellstSnd() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // 구분 MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // 경주일

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_s06", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // 검증 오류 카운트
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	// 2차 검증 확인(SDL_DT TBJI_PC_TELMP)
	public String strVeriDTTelmpSnd() {
		String rtnKey = "";

		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // 구분 MeetGubun
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY"); // 경주일

		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		param.setWhereClauseParameter(i++, sMeetGubun); // 개최지
		param.setWhereClauseParameter(i++, sJigeupDt); // 경주일
		
		
		
		PosRowSet keyRecord = this.rbmdao.find("rsm1020_s07", param);
		PosRow pr[] = keyRecord.getAllRow();
		
		int intCount =0; // 검증 오류 카운트
		for(int j=0;j<pr.length;j++)
		{
			String TF = String.valueOf(pr[j].getAttribute("TF"));
			if(!"001".equals(TF))
			{
				intCount++;
			}
		}
		if(intCount > 0|| pr.length==0 )
		{
			rtnKey="002";
		}
		else
		{
			rtnKey="001";
		}
		
		return rtnKey;
	}
	
	
	
	
	

	// 파일 검증 확인
	public String selectVeriFile(int [] nFileCount) {
		String sRaceDay = (String) ctx.get("RACE_DAY"); // 경주일 RaceDay
		
		String strFILE_VERI="002";
		int intFailCount=0;
		
		
		if(!"".equals(sRaceDay)&&sRaceDay!=null)
		{
			
			for(int i=0;i<nFileCount.length;i++)
			{
				if(nFileCount[i]==0)
				{
					intFailCount++;	
				}
			
			}
		}
		else
		{
			
			for(int i=0;i<2;i++)
			{
				if(nFileCount[i]==0)
				{
					intFailCount++;	
				}
			
			}
		}
		if(intFailCount==0)
		{
			strFILE_VERI ="001";
		}
		
		

		return strFILE_VERI;
	}
	
	// 파일 검증 확인
	public String selectVeriFile() {
		
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sDT = (String) ctx.get("DT"); // 일자
		
		

		int i = 0;
		
		PosParameter param = new PosParameter();
		param.setWhereClauseParameter(i++, sStndYear); // 0.기준년도
		param.setWhereClauseParameter(i++, sDT); // 1.날짜

		PosRowSet keyRecord = this.rbmjdbcDao.find("rsm1020_sVeriFile", param);

		PosRow pr[] = keyRecord.getAllRow();
		String RACE_DT = pr[0].getAttribute("RACE_DT")==null?"":pr[0].getAttribute("RACE_DT").toString() ; // 경주일
		
		
		int nFILE_001 = 0;
		int nFILE_002 = 0;
		int nFILE_003 = 0;
		int nFILE_004 = 0;
		
		int nFILE_005 = 0;
		int nFILE_006 = 0;
		int nFILE_007 = 0;
		int nFILE_008 = 0;
		String strFILE_VERI = "002";
		if (RACE_DT==null||"".equals(RACE_DT.trim())) { // 경주일이 없는 평일
			
			for (int j = 0; j < pr.length; j++) {
				String FILE_CD = (String)pr[j].getAttribute("FILE_CD");
				int FILE_CNT = Integer.parseInt(pr[j].getAttribute("FILE_CNT").toString());
				String FILE_NM	= (String)pr[j].getAttribute("FILE_NM");
				
				if ("001".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {// gita 에 값이 없을 경우
					nFILE_001++;
					
				}
				if ("002".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// ktax 에 값이 없을 경우
					nFILE_002++;
					
				}
			}
			if (nFILE_001 >0 && nFILE_002 > 0) {	// gita 와 ktax 에 값이 있다면 결과는 정상
				strFILE_VERI = "001";
			}
		} else {
			
			for (int j = 0; j < pr.length; j++) {
				String FILE_CD = (String)pr[j].getAttribute("FILE_CD");
				int FILE_CNT = Integer.parseInt(pr[j].getAttribute("FILE_CNT").toString());
				String FILE_NM	= (String)pr[j].getAttribute("FILE_NM");
				if ("001".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// gita 에 값이 없을 경우
					nFILE_001++;
				}
				if ("002".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// ktax 에 값이 없을 경우
					nFILE_002++;
				}
				if ("003".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// payoff 에 값이 없을 경우
					nFILE_003++;
				}
				if ("004".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// result 에 값이 없을 경우
					nFILE_004++;
				}
				
				if ("005".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// gita 에 값이 없을 경우
					nFILE_005++;
				}
				if ("006".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// ktax 에 값이 없을 경우
					nFILE_006++;
				}
				if ("007".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// payoff 에 값이 없을 경우
					nFILE_007++;
				}
				if ("008".equals(FILE_CD)&& FILE_CNT > 0 && !"".equals(FILE_NM)) {	// result 에 값이 없을 경우
					nFILE_008++;
				}
			}
			if (nFILE_001 > 0 && nFILE_002 > 0 && nFILE_003 > 0 && nFILE_004 > 0 && nFILE_005 > 0 && nFILE_006 > 0 && nFILE_007 > 0 && nFILE_008 > 0) {	// gita, ktax,payoff, result 에 값이 있다면 결과는 정상
				strFILE_VERI = "001";
			}
		}
		

		return strFILE_VERI;
	}

	// 1차 검증 입력
	public int insertVeriFst(PosRecord record,String SESSION_USER_ID,String sFile,String sFstVeri,String sSndVeri, String sVeri) {
//		String SESSION_USER_ID = Util.trim(ctx.getSessionUserData("USER_ID".trim()));
		
		String sMeetGubun = (String) ctx.get("MEET_GUBUN"); // 구분 MeetGubun
		String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sMeetCd = (String)record.getAttribute("MEET_CD") ;
//		String sDT = (String) ctx.get("DT"); // 일자
		PosParameter param = new PosParameter();

		int i = 0;

		param.setValueParamter(i++, sStndYear); // 0.기준년도
		param.setValueParamter(i++, sMeetGubun); // 1.구분값		
		param.setValueParamter(i++, SavePCFileUpload.searchTms(sMeetGubun)); // 2.구분값
		param.setValueParamter(i++, SavePCFileUpload.searchDay(sMeetGubun)); // 3.구분값
		
		param.setValueParamter(i++, record.getAttribute("DT")); 		// 4.날짜
		param.setValueParamter(i++, sFile); 	// 5.파일 업로드 결과
		
		param.setValueParamter(i++, sFstVeri); 	// 6. 1차 검증 결과
		param.setValueParamter(i++, sSndVeri); 	// 7. 2차 검증 결과
		param.setValueParamter(i++, sVeri); 	// 8. 검증 결과
		param.setValueParamter(i++, SESSION_USER_ID); // 9.로그인 사용자 아이디
		param.setValueParamter(i++, SESSION_USER_ID); // 10.로그인 사용자 아이디

		int dmlcount = this.rbmjdbcDao.update("rsm1020_mVeriFst", param);

		return dmlcount;
	}

}
