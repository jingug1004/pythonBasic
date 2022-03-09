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

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SnisActivity extends PosActivity
{
    public String SESSION_USER_ID     = "";
    public String SESSION_USER_NM     = "";
    public String SESSION_DEPT_CD     = "";
    public String SESSION_PSWD        = "";
    public String SESSION_DUTY_GRP_CD = "";
    public String SESSION_FLOC_CD     = "";
    public String SESSION_STAT        = "";
    public String SESSION_TEL_NO      = "";
    public String SESSION_HP_NO       = "";
    public String SESSION_STR_DT      = "";
    public String SESSION_END_DT      = "";
    public String SESSION_LOG_SEQ     = "";
	
	public SnisActivity()
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
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String setUserInfo(PosContext ctx)
    {
    	SESSION_USER_ID     = Util.trim(ctx.getSessionUserData("USER_ID    ".trim()));
    	SESSION_USER_NM     = Util.trim(ctx.getSessionUserData("USER_NM    ".trim()));
    	SESSION_DEPT_CD     = Util.trim(ctx.getSessionUserData("DEPT_CD    ".trim()));
    	SESSION_PSWD        = Util.trim(ctx.getSessionUserData("PSWD       ".trim()));
    	SESSION_DUTY_GRP_CD = Util.trim(ctx.getSessionUserData("DUTY_GRP_CD".trim()));
    	SESSION_FLOC_CD     = Util.trim(ctx.getSessionUserData("FLOC_CD    ".trim()));
    	SESSION_STAT        = Util.trim(ctx.getSessionUserData("STAT       ".trim()));
    	SESSION_TEL_NO      = Util.trim(ctx.getSessionUserData("TEL_NO     ".trim()));
    	SESSION_HP_NO       = Util.trim(ctx.getSessionUserData("HP_NO      ".trim()));
    	SESSION_STR_DT      = Util.trim(ctx.getSessionUserData("STR_DT     ".trim()));
    	SESSION_END_DT      = Util.trim(ctx.getSessionUserData("END_DT     ".trim()));
    	SESSION_LOG_SEQ     = Util.trim(ctx.getSessionUserData("LOG_SEQ    ".trim()));
    	
        if ( SESSION_USER_ID.equals("") ) {
    		if ( ctx.get("gdsUser") != null ) {
    	    	PosDataset dsUser = (PosDataset) ctx.get("gdsUser");
    	    	
    	    	PosRecord record = dsUser.getRecord(0);
    	        logger.logInfo(record);

    	        SESSION_USER_ID     = Util.trim(record.getAttribute("USER_ID    ".trim()));
    	        SESSION_USER_NM     = Util.trim(record.getAttribute("USER_NM    ".trim()));
    	        SESSION_DEPT_CD     = Util.trim(record.getAttribute("DEPT_CD    ".trim()));
    	        SESSION_PSWD        = Util.trim(record.getAttribute("PSWD       ".trim()));
    	        SESSION_DUTY_GRP_CD = Util.trim(record.getAttribute("DUTY_GRP_CD".trim()));
    	        SESSION_FLOC_CD     = Util.trim(record.getAttribute("FLOC_CD    ".trim()));
    	        SESSION_STAT        = Util.trim(record.getAttribute("STAT       ".trim()));
    	        SESSION_TEL_NO      = Util.trim(record.getAttribute("TEL_NO     ".trim()));
    	        SESSION_HP_NO       = Util.trim(record.getAttribute("HP_NO      ".trim()));
    	        SESSION_STR_DT      = Util.trim(record.getAttribute("STR_DT     ".trim()));
    	        SESSION_END_DT      = Util.trim(record.getAttribute("END_DT     ".trim()));
    	        SESSION_LOG_SEQ     = Util.trim(ctx.getSessionUserData("LOG_SEQ    ".trim()));
    		}
    	}
        
        logger.logInfo("SESSION_USER_ID     : " + SESSION_USER_ID    );
        logger.logInfo("SESSION_USER_NM     : " + SESSION_USER_NM    );
        logger.logInfo("SESSION_DEPT_CD     : " + SESSION_DEPT_CD    );
        logger.logInfo("SESSION_PSWD        : " + SESSION_PSWD       );
        logger.logInfo("SESSION_DUTY_GRP_CD : " + SESSION_DUTY_GRP_CD);
        logger.logInfo("SESSION_FLOC_CD     : " + SESSION_FLOC_CD    );
        logger.logInfo("SESSION_STAT        : " + SESSION_STAT       );
        logger.logInfo("SESSION_TEL_NO      : " + SESSION_TEL_NO     );
        logger.logInfo("SESSION_HP_NO       : " + SESSION_HP_NO      );
        logger.logInfo("SESSION_STR_DT      : " + SESSION_STR_DT     );
        logger.logInfo("SESSION_END_DT      : " + SESSION_END_DT     );
		logger.logInfo("SESSION_LOG_SEQ     : " + SESSION_LOG_SEQ);

    	if ( SESSION_USER_ID.equals("") ) {
            return PosBizControlConstants.FAILURE;
    	}

		// ����� �������� ����
		String[] nameParam = (String[])ctx.getAllRequestParameters().get("ActionName");
		updateUserTrace(SESSION_LOG_SEQ, nameParam[0]);

        return PosBizControlConstants.SUCCESS;
    }
    

	/*
	 * ������ ���缱 2012.05.03 ������� ���������� ���� 
	 */
	private int updateUserTrace(String sLogSeq, String sActionName) {
		if ("SaveUserTrace".equals(sActionName) || "saveLoginDupChk".equals(sActionName)) return 0;	// �ڱ� �ڽ��� �������� ����
		
		int i = 0;
		PosParameter param = new PosParameter();
		param.setWhereClauseParameter(i++, sActionName);
		param.setWhereClauseParameter(i++, sLogSeq);

		int dmlcount = 0;
		
		dmlcount = this.getDao("boadao").update("user_trace_update", param);
		return dmlcount;
	}
}

