/*================================================================================
 * 시스템		: 학사관리 
 * 소스파일 이름	: snis.can.cyclestd.d02000004.activity.SearchWeekEduSchd.java
 * 파일설명		: 주차구간의 주간교육일정을 조회한다. 
 * 작성자		: 최문규 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-23
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000021.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* 주간교육일정을을 조회 한다. 
* @auther 최문규  
* @version 1.0
*/
public class SearchEstmItemTot extends SnisActivity
{    
    public SearchEstmItemTot()
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
    	//사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	getEstmItemTot(ctx);
						        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 기수별 주차구간 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getEstmItemTot(PosContext ctx)
    {
    	String	sResultKey = "dsEstmItemTot";
        String	racerPerioNo    = (String) ctx.get("racerPerioNo");
        String	round    =  (String) ctx.get("round");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, racerPerioNo);
        param.setWhereClauseParameter(paramIndex++, round);
		
        PosRowSet rowSet =   this.getDao("candao").find("tbdn_total_d06000021_fn001", param);
        logger.logInfo("###########################>"+rowSet.count());
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
}

