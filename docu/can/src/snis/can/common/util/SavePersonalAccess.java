package snis.can.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/*================================================================================
 * �ý���			: �������� ���� ���
 * �ҽ����� �̸�	: 
 * ���ϼ���		: �������� ���� ���
 * �ۼ���			: jdlee
 * ����			: 1.0.0
 * ��������		: 2014-05-04
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �̿���
* @version 1.0
*/
public class SavePersonalAccess extends SnisActivity
{    		
	public SavePersonalAccess()
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

    	// ����� ���� Ȯ��

    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
			
			logger.logDebug("################begin SavePersonalAccess");
			
			
    	savePersonalAccess(ctx);
    	
      return PosBizControlConstants.SUCCESS;
    }


    
    /**
	 * �������� ���� ���
	 * 
	 * @return
	 */
		private void savePersonalAccess(PosContext ctx) {
			PosParameter param = new PosParameter();  
			String sQueryId    = "personal_access_insert";
			
			String sUserId     = SESSION_USER_ID;
			String sLogSeq     = (String) ctx.get("LOG_SEQ");
			String sFormId     = (String) ctx.get("FORM_ID");
			String sAccessType = (String) ctx.get("ACCESS_TYPE");
			String sAction     = (String) ctx.get("ACTION");
			String sParam      = (String) ctx.get("PARAM");
			
			int i = 0;		
			param.setValueParamter(i++, sUserId);			//
			param.setValueParamter(i++, sLogSeq);			//
			param.setValueParamter(i++, sUserId);			//
			param.setValueParamter(i++, sLogSeq);			//
			param.setValueParamter(i++, sFormId);			//
			param.setValueParamter(i++, sAccessType);		//
			param.setValueParamter(i++, sAction);			//
			param.setValueParamter(i++, sParam);			//
			
			int nSaveCount = this.getDao("candao").update(sQueryId, param);
			
			Util.setSaveCount  (ctx, nSaveCount     );		
		
		}
    
}
