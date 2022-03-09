/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.racer.e02060010.activity.SearchRaceResult.java
 * 파일설명		: 경주결과조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02060010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 경주결과조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRaceResult extends SnisActivity
{    
	
    public SearchRaceResult()
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
    * <p> 조회시작 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {   	
        PosRowSet rowset = null;
        
        // 경주결과조회
        PosParameter param = new PosParameter();
        int i = 0;
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	rowset = this.getDao("boadao").find("tbeb_race_result_fb001", param);

        String sResultKey = "dsOutRaceResult";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
