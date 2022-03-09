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
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	insertLoginPswdChk(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
	 * 중복로그인 방지용 정보 입력
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
