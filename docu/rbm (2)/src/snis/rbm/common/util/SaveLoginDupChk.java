package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

public class SaveLoginDupChk extends SnisActivity{
	public SaveLoginDupChk(){
	}
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        
    	insertLoginDupChk(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
	 * 중복로그인 방지용 정보 입력
	 * 
	 * @return
	 */
	private int insertLoginDupChk(PosContext ctx){
    	String sUserId = SESSION_USER_ID;
        String sSessionID = (String)ctx.get("JSESSIONID");
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, sUserId);									
        param.setValueParamter(i++, sSessionID);								

        int dmlcount = this.getDao("rbmdao").update("user_login_chk_save", param);
        return dmlcount;
    }	
}
