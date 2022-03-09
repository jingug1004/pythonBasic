/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030010.activity.SearchStandardRaceCount.java
 * 파일설명		: 기준출주횟수조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 등급별기준출주횟수를 조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchStandardRaceCount extends SnisActivity
{    
	
    public SearchStandardRaceCount()
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
        
        // 등급별기준출주횟수 조회
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        rowset = this.getDao("boadao").find("tbeb_stnd_race_cnt_fb001", param);

        if ( !rowset.hasNext() )
        {
            rowset = this.getDao("boadao").find("tbeb_stnd_race_cnt_fb002", param);
        }
        
        String sResultKey = "dsOutStndRaceCnt";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);

    
        // 년도,분기별 경주수 조회
        PosParameter paramRace = new PosParameter();
        i = 0;
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        rowset = this.getDao("boadao").find("tbeb_stnd_race_cnt_fb003", paramRace);

        sResultKey = "dsOutRaceInfo";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
