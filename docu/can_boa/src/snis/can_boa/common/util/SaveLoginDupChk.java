package snis.can_boa.common.util;

import snis.can_boa.common.util.Util;
import snis.can_boa.common.util.SnisActivity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

public class SaveLoginDupChk extends SnisActivity{
	public SaveLoginDupChk(){
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
        
    	insertLoginDupChk(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
	 * �ߺ��α��� ������ ���� �Է�
	 * 
	 * @return
	 */
	private int insertLoginDupChk(PosContext ctx){
    	String sUserId = SESSION_USER_ID;
        String sSessionID = (String)ctx.get("JSESSIONID");
        
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!! user_id : " + sUserId);
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!! sSessionID : " + sSessionID);
        
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sUserId);									
        param.setValueParamter(i++, sSessionID);								

        int dmlcount = this.getDao("candao").update("user_login_chk_save", param);
        return dmlcount;
    }	
}
