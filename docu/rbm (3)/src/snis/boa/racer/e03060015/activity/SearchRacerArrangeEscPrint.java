/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030020.activity.SearchEscRacer.java
 * 파일설명		: 성적별 주선제외 등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03060015.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRacerArrangeEscPrint extends SnisActivity
{    
	
    public SearchRacerArrangeEscPrint()
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	PosParameter paramRacerSummery = new PosParameter();
        
        int i = 0;
        paramRacerSummery.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerSummery.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerSummery.setWhereClauseParameter(i++, ctx.get("END_DT"));

        PosRowSet rowsetRacerSummery = this.getDao("boadao").find("tbec_arrange_esc_fc003", paramRacerSummery);    

        String sResultKey = "dsOutRacerSummery";
        ctx.put(sResultKey, rowsetRacerSummery);
        Util.addResultKey(ctx, sResultKey);

    	PosParameter paramRacerCancel = new PosParameter();
        
        i = 0;
        paramRacerCancel.setWhereClauseParameter(i++, ctx.get("STR_DT"));

        PosRowSet rowsetRacerCancel = this.getDao("boadao").find("tbec_arrange_esc_fc004", paramRacerCancel);    

        sResultKey = "dsOutRacerCancel";
        ctx.put(sResultKey, rowsetRacerCancel);
        Util.addResultKey(ctx, sResultKey);

    	PosParameter paramRacerEsc = new PosParameter();
        
        i = 0;
        paramRacerEsc.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerEsc.setWhereClauseParameter(i++, ctx.get("END_DT"));

        PosRowSet rowsetRacerEsc = this.getDao("boadao").find("tbec_arrange_esc_fc005", paramRacerEsc);    

        sResultKey = "dsOutRacerEsc";
        ctx.put(sResultKey, rowsetRacerEsc);
        Util.addResultKey(ctx, sResultKey);

    	PosParameter paramRacerEtc = new PosParameter();
        
        i = 0;
        paramRacerEtc.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerEtc.setWhereClauseParameter(i++, ctx.get("END_DT"));

        PosRowSet rowsetRacerEtc = this.getDao("boadao").find("tbec_arrange_esc_fc006", paramRacerEtc);    

        sResultKey = "dsOutRacerEtc";
        ctx.put(sResultKey, rowsetRacerEtc);
        Util.addResultKey(ctx, sResultKey);
    }
}
