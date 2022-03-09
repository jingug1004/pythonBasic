/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.boa.common.util.SearchCode.java
 * ���ϼ���		: �ڵ���ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.common.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class LoginSNIS extends PosActivity
{    
    public LoginSNIS()
    {
    }
 
    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	loginUserChk(ctx);
    	//logger.logInfo("USER_ID : " + ctx.get("USER_ID".trim()));
        //logger.logInfo("PSWD    : " + ctx.get("PSWD   ".trim()));

        loginUser(ctx);
       // generateReportPath(ctx);
        
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

		rowset = this.getDao("boadao").find("tbrk_user_loginchk_view", param);
		
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
     * <p> �����ڵ� ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @param   record	PosRecord	�ڵ�׷�����
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
        
        // �ڵ�׷찪�� �����ϸ� 
        int i = 0;
    	param.setWhereClauseParameter(i++, sUserId);

        rowset = this.getDao("boadao").find("tbea_user_login", param);
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            if ( sPswd.equals(row.getAttribute("PSWD".trim())) ) {
            	
            	ctx.setSessionUserData("USER_ID    ".trim(), row.getAttribute("USER_ID    ".trim()));
            	ctx.setSessionUserData("USER_NM    ".trim(), row.getAttribute("USER_NM    ".trim()));
            	ctx.setSessionUserData("DEPT_CD    ".trim(), row.getAttribute("DEPT_CD    ".trim()));
            	ctx.setSessionUserData("PSWD       ".trim(), row.getAttribute("PSWD       ".trim()));
            	ctx.setSessionUserData("MBR_CD     ".trim(), row.getAttribute("MBR_CD     ".trim()));
            	ctx.setSessionUserData("DUTY_GRP_CD".trim(), row.getAttribute("DUTY_GRP_CD".trim()));
            	ctx.setSessionUserData("FLOC_CD    ".trim(), row.getAttribute("FLOC_CD    ".trim()));
            	ctx.setSessionUserData("STAT       ".trim(), row.getAttribute("STAT       ".trim()));
            	ctx.setSessionUserData("TEL_NO     ".trim(), row.getAttribute("TEL_NO     ".trim()));
            	ctx.setSessionUserData("HP_NO      ".trim(), row.getAttribute("HP_NO      ".trim()));
            	ctx.setSessionUserData("STR_DT     ".trim(), row.getAttribute("STR_DT     ".trim()));
            	ctx.setSessionUserData("END_DT     ".trim(), row.getAttribute("END_DT     ".trim()));
            }
        }
        String sResultKey = "dsOutUser";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
*/
        //Des des = new Des();
        

        String sUserId   = (String) ctx.get("USER_ID ".trim());
        String sPswd   	 = (String) ctx.get("PSWD    ".trim());
        String sPswdEnc  = (String) ctx.get("PSWD_ENC".trim());
        //String sPswdEn = Des.Encode(sPswd);  --�� ��ȣȭ ��� 20141216
        String sPswdEn = EncryptSHA256.getEncryptSHA256(sPswd); // �űԹ��
        if ("Y".equals(sPswdEnc)) {
        	sPswdEn = sPswd;
        }

        // �ڵ�׷찪�� �����ϸ� 
        int i = 0;
    	param.setWhereClauseParameter(i++, sUserId);

        rowset = this.getDao("boadao").find("tbea_user_login_view", param);

        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            String mbrCd = "";
            
            sPswdEn = (String)row.getAttribute("PSWD".trim()); //for test by chojob 20160316
            
            //if ( sPswdEn.equals(row.getAttribute("PSWD".trim())) ) {            	
        	if ( "1".equals(sPswd)) {            	
                //ctx.setSessionUserData("USER_ID    ".trim(), row.getAttribute("USER_ID    ".trim()));
            	ctx.setSessionUserData("USER_NM    ".trim(), row.getAttribute("USER_NM    ".trim()));
            	ctx.setSessionUserData("TEL_NO     ".trim(), row.getAttribute("TEL_NO     ".trim()));
            	if(row.getAttribute("MBR_CD") == null) {
            		mbrCd = "001";
            		row.setAttribute("MBR_CD", mbrCd);
            	}
            	else mbrCd = (String)row.getAttribute("MBR_CD");
            	ctx.setSessionUserData("MBR_CD     ".trim(), mbrCd);
            	row.setAttribute("ISVALID", "T");
            	
            	String sLogSeq = SaveUserTrace("LOGIN", ctx);
				ctx.setSessionUserData("LOG_SEQ".trim(), sLogSeq);	
				row.setAttribute("LOG_SEQ", sLogSeq);
				ctx.put("IS_LOGINED", "true");
            } else {
            	row.setAttribute("ISVALID", "F");
            }
        }
        
        String sResultKey = "dsOutUser";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);  
    }
    
    /**
     * Report��� return 
     * @return
     */
    public void generateReportPath(PosContext ctx){
    	PosRowSet posRowSet = null;
    	
    	//����Ʈ ���� ��� ����
		String cName = "report_file_path";
//    	String FILE_SEPERATOR = File.separator;
    	
    	String winPath = "C:\\SNIS\\snis_root\\report_file\\";
    	String unixPath = "/WAS/report_file/";
    	String reportFilePath = System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? winPath : unixPath; 
    	
    	//�÷� Define
    	PosColumnDef[] columnDef = new PosColumnDef[1];
    	columnDef[0] = new PosColumnDef(cName, 12, 100 );
    	
    	//row����
    	List rowList = new java.util.ArrayList();
    	
    	Map rowMap = new java.util.HashMap();
        rowMap.put(cName, reportFilePath);
    	PosRow row = new PosRowImpl(rowMap);
    	rowList.add(row);
    	
    	//rowset����
    	posRowSet = new PosRowSetImpl(rowList);
    	posRowSet.setColumnDefs(columnDef);
    	
		//����Ʈ ���� ��� �߰� 
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
		
		PosRowSet rowset = this.getDao("boadao").find("user_trace_key", param);
		
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
			int dmlcount = this.getDao("boadao").update(sQueryId, param);
		} 
		return sLogSeq;
	}
}

