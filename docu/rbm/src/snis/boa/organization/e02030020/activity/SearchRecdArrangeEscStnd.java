/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030020.activity.SearchRecdArrangeEscStnd.java
 * 파일설명		: 등급평가주선보류기준조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 등급평가주선보류기준조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRecdArrangeEscStnd extends SnisActivity
{    
	
    public SearchRecdArrangeEscStnd()
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
    	
		logger.logInfo(ctx.get("STND_YEAR"));
		logger.logInfo(ctx.get("QURT_CD"  ));
	        
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 조회시작 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	PosParameter param = new PosParameter();
        PosRowSet rowset = null; 
        int i = 0;

        // 등급별출주횟수 조회
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        rowset = this.getDao("boadao").find("tbec_racer_master_fb010", param);

        String sResultKey = "dsOutRacerGrd";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);

        // 등급별 기수 조회
    	PosParameter paramPerio = new PosParameter();
        PosRowSet rowsetPerio = null; 
        i = 0;

/*
        paramPerio.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));
        rowsetPerio = this.getDao("boadao").find("tbec_racer_master_fb101", paramPerio);
*/
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));//등급심사 종료일 이전 등록된 선수만 계산. 신인제외. 2013.1.16
        paramPerio.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));

        rowsetPerio = this.getDao("boadao").find("tbec_arrange_esc_fc003", paramPerio);    

        sResultKey = "dsOutPerio";
        ctx.put(sResultKey, rowsetPerio);
        Util.addResultKey(ctx, sResultKey);

        PosParameter paramRacer = new PosParameter();
        PosRowSet rowsetRacer = null; 
        i = 0;

        // 선수리스트(성적) 조회
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        
        paramRacer.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("OPEN_DAY"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        
        rowsetRacer = this.getDao("boadao").find("tbec_racer_master_fb009", paramRacer);

        sResultKey = "dsOutRacer";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
    }
}
