package snis.can_boa.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class SaveLoginPswdChk extends PosActivity{
	public SaveLoginPswdChk(){
	}
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	insertLoginPswdChk(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
	 * �ߺ��α��� ������ ���� �Է�
	 * 
	 * @return
	 */
	private int insertLoginPswdChk(PosContext ctx){	
		PosParameter param = new PosParameter();
        PosRowSet    rowset;

        String sUserId  = (String) ctx.get("USER_ID ".trim());

        param.setValueParamter(0, sUserId);
    	param.setValueParamter(1, sUserId);
    	
        int dmlcount = this.getDao("candao").update("user_login_pswd_chk_save", param);
        return dmlcount;      
    }
	
}
