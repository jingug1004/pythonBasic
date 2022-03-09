/*================================================================================
 * 시스템			: 지점 관리
 * 소스파일 이름	: snis.rbm.business.rbr1010.activity.SaveLastYear.java
 * 파일설명		: 전년도의  지점 사항을 현재 년도로 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr1050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class CopyRptPrepSitu extends SnisActivity {
	
	public CopyRptPrepSitu(){}

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
    	int nSaveCount   = 0; 
    	
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, ctx.get("RACE_DAY"));           // 1. 경주일 
        param.setValueParamter(i++, SESSION_USER_ID);               // 2. 등록/수정자 사번  
        param.setValueParamter(i++, ctx.get("RACE_DAY"));           // 3. 경주일 
        param.setValueParamter(i++, ctx.get("BRNC_CD"));            // 4. 지점코드
        param.setValueParamter(i++, ctx.get("MEET_CD"));            // 5. 경륜/경정 구분(시행처코드)
        param.setValueParamter(i++, ctx.get("BRNC_CD"));            // 4. 지점코드
        
        nSaveCount = this.getDao("rbmdao").update("rbr1050_m02", param);
  
        Util.setSaveCount  (ctx, nSaveCount );
    }
}
