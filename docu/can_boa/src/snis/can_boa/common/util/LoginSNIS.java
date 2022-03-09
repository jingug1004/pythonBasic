 /*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.can.common.util.SearchCode.java
 * 파일설명		: 코드조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.common.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import snis.boa.common.util.Util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class LoginSNIS extends PosActivity
{    
    public LoginSNIS() 
    { 
    }

    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	loginUserChk(ctx);
    	//logger.logInfo("USER_ID : " + ctx.get("USER_ID".trim()));
        //logger.logInfo("PSWD    : " + ctx.get("PSWD   ".trim()));
        loginUser(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    private void loginUserChk(PosContext ctx) {
		// TODO Auto-generated method stub
    	PosParameter param = new PosParameter();
		PosRowSet rowset;
		
		String sUserId 		= (String) ctx.get("USER_ID ".trim());
		String sPswdChk   	 = (String) ctx.get("PSWD_CHK ".trim());
		
		int i = 0;
		param.setWhereClauseParameter(i++, sUserId);

		rowset = this.getDao("candao").find("tbrk_user_loginchk_view", param);
		
		/*if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            String mbrCd = "";
            
            sPswdChk = (String)row.getAttribute("PSWD_CHK".trim()); //for test by chojob 20160316
                        	
        	if ( "5".equals(sPswdChk)) {            	
                
            }
        }*/
		
		String sResultKey = "dsOutUser";
		ctx.put(sResultKey, rowset);
		Util.addResultKey(ctx, sResultKey);
	}
    
    /**
     * <p> 공통코드 조회 </p>
     * @param   ctx		PosContext	저장소
     * @param   record	PosRecord	코드그룹정보
     * @return  none
     * @throws  none
     */
    private void loginUser(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        PosRowSet    rowset;

        /*
        String sUserId = (String) ctx.get("USER_ID".trim());
        String sPswd   = (String) ctx.get("PSWD   ".trim());
        
        // 코드그룹값이 존재하면 
        int i = 0;
    	param.setWhereClauseParameter(i++, sUserId);

        rowset = this.getDao("candao").find("tbdm_user_login", param);
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            if ( sPswd.equals(row.getAttribute("PSWD".trim())) ) {
            	
     	       	ctx.setSessionUserData("USER_ID    ".trim(), row.getAttribute("USER_ID    ".trim()));
            	ctx.setSessionUserData("USER_NM    ".trim(), row.getAttribute("USER_NM    ".trim()));
            	ctx.setSessionUserData("PSWD       ".trim(), row.getAttribute("PSWD       ".trim()));
 //           	ctx.setSessionUserData("DUTY_GRP_CD".trim(), row.getAttribute("DUTY_GRP_CD".trim()));
            	ctx.setSessionUserData("DEPT_CD    ".trim(), row.getAttribute("DEPT_CD    ".trim()));
            	ctx.setSessionUserData("DEPT_NM    ".trim(), row.getAttribute("DEPT_NM    ".trim()));
            	ctx.setSessionUserData("FLOC       ".trim(), row.getAttribute("FLOC       ".trim()));
            	ctx.setSessionUserData("FGRADE     ".trim(), row.getAttribute("FGRADE     ".trim()));
            	ctx.setSessionUserData("TEL_NO     ".trim(), row.getAttribute("TEL_NO     ".trim()));
            	ctx.setSessionUserData("HP_NO      ".trim(), row.getAttribute("HP_NO      ".trim()));
 //           	ctx.setSessionUserData("STAT       ".trim(), row.getAttribute("STAT       ".trim()));
            	ctx.setSessionUserData("STR_DT     ".trim(), row.getAttribute("STR_DT     ".trim()));
            	ctx.setSessionUserData("END_DT     ".trim(), row.getAttribute("END_DT     ".trim()));
            }
        }
        
        String sResultKey = "dsOutUser";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    
 */     
    	
        String sUserId    = (String) ctx.get("USER_ID      ".trim());
        String sPswd      = (String) ctx.get("PSWD         ".trim());
        String sPswdEnc   = (String) ctx.get("PSWD_ENC".trim());           
        String sPswdEn    = EncryptSHA256.getEncryptSHA256(sPswd);
        if ("Y".equals(sPswdEnc)) {
        	sPswdEn    = sPswd;        	
        }
 
        
        // 코드그룹값이 존재하면 
        int i = 0;
    	param.setWhereClauseParameter(i++, sUserId);

        rowset = this.getDao("candao").find("tbdm_user_login_view", param);

        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            String mbrCd = "";
        	if ( sPswd.equals("1") ) {
            //if ( sPswdEn.equals(row.getAttribute("PSWD".trim())) ) {
                //ctx.setSessionUserData("USER_ID    ".trim(), row.getAttribute("USER_ID    ".trim()));
            	ctx.setSessionUserData("USER_NM    ".trim(), row.getAttribute("USER_NM    ".trim()));
            	ctx.setSessionUserData("TEL_NO     ".trim(), row.getAttribute("TEL_NO     ".trim()));
            	if(row.getAttribute("CAN_CD") == null) {
            		mbrCd = "001";
            		row.setAttribute("CAN_CD", mbrCd);
            	}
            	else mbrCd = (String)row.getAttribute("CAN_CD");
            	ctx.setSessionUserData("CAN_CD     ".trim(), mbrCd);
            	row.setAttribute("ISVALID", "T");
            	
				String sLogSeq = SaveUserTrace("LOGIN", ctx);
				ctx.setSessionUserData("LOG_SEQ".trim(), sLogSeq);	
				row.setAttribute("LOG_SEQ", sLogSeq);
				ctx.put("IS_LOGINED", "true");

            	//중복접속 여부 체크 로직 초기화
				String sSessionID = (String)ctx.get("JSESSIONID");
				if (sSessionID != null) SaveUserSession(sUserId, sSessionID);	
            	
            } else {
            	row.setAttribute("ISVALID", "F");
            }
        }
        
        String sResultKey = "dsOutUser";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);        
    }
    
    /**
     * Report경로 return 
     * @return
     */    public void generateReportPath(PosContext ctx){
    	PosRowSet posRowSet = null;
    	
    	//레포트 파일 경로 설정
		String cName = "report_file_path";
    	String FILE_SEPERATOR = File.separator;
    	
    	String winPath = "C:\\SNIS\\snis_root\\report_file\\";
    	String unixPath = "/WAS/report_file/";
    	String reportFilePath = System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? winPath : unixPath; 
    	
    	//컬럼 Define
    	PosColumnDef[] columnDef = new PosColumnDef[1];
    	columnDef[0] = new PosColumnDef(cName, 12, 100 );
    	
    	//row생성
    	List rowList = new java.util.ArrayList();
    	
    	Map rowMap = new java.util.HashMap();
        rowMap.put(cName, reportFilePath);
    	PosRow row = new PosRowImpl(rowMap);
    	rowList.add(row);
    	
    	//rowset생성
    	posRowSet = new PosRowSetImpl(rowList);
    	posRowSet.setColumnDefs(columnDef);
    	
		//레포트 파일 경로 추가 
        String dsOutReportKey = "dsOutReportFilePath";
        ctx.put(dsOutReportKey, posRowSet);
        Util.addResultKey(ctx, dsOutReportKey);
    }
    

	private String SaveUserTrace(String sJobGubn, PosContext ctx) {
		String sQueryId = "";
		String sLogSeq = "";
		int i = 0;
		
		String sUserIPAddr = (String) ctx.get("USER_IP".trim());
		String sOsVer = (String) ctx.get("OS_VERSION".trim());
		PosParameter param = new PosParameter();
		
		PosRowSet rowset = this.getDao("candao").find("user_trace_key", param);
		
		if (rowset.hasNext()) {
			PosRow row = rowset.next();
			sLogSeq = row.getAttribute("LOG_SEQ").toString();
			param.setWhereClauseParameter(i++, sLogSeq);
			param.setWhereClauseParameter(i++, (String) ctx.get("USER_ID"));
			param.setWhereClauseParameter(i++, sUserIPAddr);
			param.setWhereClauseParameter(i++, sOsVer);
 
			if ("LOGIN".equals(sJobGubn)) {
				sQueryId = "user_trace_insert";
			}
			int dmlcount = this.getDao("candao").update(sQueryId, param);
		} 
		return sLogSeq;
	}
	
	private int SaveUserSession(String sUserId, String sSessionId)
	{

        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!! user_id : " + sUserId);
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!! sSessionID : " + sSessionId);
        
		int i = 0;
		String sQueryId    = "user_login_chk_save";
		PosParameter param = new PosParameter();
		
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sSessionId);
		int dmlcount = this.getDao("candao").update(sQueryId, param);
		
		return dmlcount;
	}
}

