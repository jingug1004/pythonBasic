/*================================================================================
 * 시스템			: 평가결과확정
 * 소스파일 이름	: snis.rbm.business.rev4010.activity.UpdateGrade.java
 * 파일설명		: 평가결과확정
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-24
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateCfm extends SnisActivity {
	
	public UpdateCfm(){}

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
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount = 0;

    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
        String sEvalNum  = (String)ctx.get("ESTM_NUM");
        String sCfmYn    = (String)ctx.get("RSLT_CFM_YN");
        
    	updateCfm(sEvalYear, sEvalNum, sCfmYn);	

        Util.setSaveCount(ctx, nSaveCount  );
    }
    
    /**
     * <p> 평과결과확정 </p>
     * @param   sEvalYear	평가년도
     * 			sEvalNum	평가회차
     * @return  dmlcount	int		update 개수
     * @throws  none
     */
    protected int updateCfm(String sEvalYear, String sEvalNum, String sCfmYn) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sCfmYn);  			// 평가결과확정여부
        param.setValueParamter(i++, SESSION_USER_ID);  	// 사용자 ID
        param.setValueParamter(i++, sEvalYear);         // 평가년도
        param.setValueParamter(i++, sEvalNum);          // 평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_u02", param);
        
        return dmlcount;
    }
}
