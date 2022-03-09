/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.rbm.common.util.updateUserTrace.java
 * ���ϼ���		: �α���
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-29
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
 * 
 * @auther ���缱
 * @version 1.0
 */
public class SavePersonalMnAccess extends SnisActivity {
	public SavePersonalMnAccess() {
	}

	/**
	 * <p>
	 * SaveStates Activity�� �����Ű�� ���� �޼ҵ�
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @return SUCCESS String sucess ���ڿ�
	 * @throws none
	 */
	public String runActivity(PosContext ctx) {

    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	String sJobType = (String) ctx.get("JOB_TYPE".trim());
    	
		SavePersonalMnAccess(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	private void SavePersonalMnAccess(PosContext ctx) {
        PosParameter param 		= new PosParameter();  
        String sQueryId    		= "personal_mn_access_insert";
    	
		String sLogSeq     		= (String) ctx.get("LOG_SEQ");
		String sFormId     		= (String) ctx.get("FORM_ID");
		//String sAccessType 		= (String) ctx.get("ACCESS_TYPE");
		String sAction     		= (String) ctx.get("ACTION");
		String sParam      		= (String) ctx.get("PARAM");
		String sUserIP			= (String) ctx.get("USER_IP");
		String sUserId      	= (String) ctx.get("USER_ID");
		
		logger.logInfo("SavePersonalAccess------------------>");
		logger.logInfo("FORM_ID["+sFormId+"]");
		//logger.logInfo("ACCESS_TYPE["+sAccessType+"]");
		logger.logInfo("ACTION["+sAction+"]");
		logger.logInfo("PARAM["+sParam+"]");
		logger.logInfo("USER_IP["+sUserIP+"]");
		logger.logInfo("USER_ID["+sUserId+"]");
		
    	int i = 0;		
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sFormId);			//
        
        String type = null;
        boolean existActionStr = sAction.indexOf("[") != -1 ? true : false;
        System.out.println("sAction = "+sAction);
        if(existActionStr){
        	type = sAction.substring( sAction.indexOf("[")+2, sAction.indexOf("[")+3);
        	if(type.equals("a")) type = "U";
        	else if(type.equals("e")) type = "S";
        	System.out.println("type is "+type);
        }
        param.setValueParamter(i++, type);			//
        
        param.setValueParamter(i++, sAction);			//
        param.setValueParamter(i++, sParam);			//
        param.setValueParamter(i++, sUserIP);			//
        param.setValueParamter(i++, sUserId);			//

        int nSaveCount = this.getDao("rbmdao").update(sQueryId, param);

        Util.setSaveCount  (ctx, nSaveCount     );		
        
	}	

}
