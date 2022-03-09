/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02050030.activity.SaveRacerRecdInit.java
 * 파일설명		: 년도별선수성적초기화등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02050030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 년도별선수성적초기화등록하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveRacerRecdInit extends SnisActivity
{    

    public SaveRacerRecdInit()
    {
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
        // 년도별선수성적초기화삭제
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));

        int nDeleteCount = this.getDao("boadao").update("tbeb_racer_recd_accu_sum_db001", param);

        // 년도별선수성적초기화등록
        // 각 년도별 1월1일을 기준으로 하는 데이터 생성
        param = new PosParameter();
        
        i = 0;
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        
        int nSaveCount = this.getDao("boadao").update("tbeb_racer_recd_accu_sum_ib001", param);

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
}