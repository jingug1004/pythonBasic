package snis.can.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class SaveLoginPswdReset extends PosActivity{
	public SaveLoginPswdReset(){
	}
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	insertLoginPswdReset(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
	 * �ߺ��α��� ������ ���� �Է�
	 * 
	 * @return
	 */
	private int insertLoginPswdReset(PosContext ctx){
		PosParameter param = new PosParameter();
        PosRowSet    rowset;

        String sUserId  = (String) ctx.get("USER_ID ".trim());
                
        int i = 0;       
        param.setWhereClauseParameter(i++, sUserId);

        int dmlcount = this.getDao("candao").update("user_login_pswd_reset_save", param);
        return dmlcount;      
    }
	
}
