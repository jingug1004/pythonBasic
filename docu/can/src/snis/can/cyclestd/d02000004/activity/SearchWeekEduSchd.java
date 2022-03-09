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
package snis.can.cyclestd.d02000004.activity;

import snis.can.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can.common.util.Util;

/**
* 주간교육일정을을 조회 한다. 
* @auther 김성희  
* @version 1.0
*/
public class SearchWeekEduSchd extends SnisActivity
{    
    public SearchWeekEduSchd()
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
    	
    	getWkPart(ctx);
						        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 기수별 주차구간 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getWkPart(PosContext ctx)
    {
    	String	sResultKey = "dsWkPart";
        String	perioNo    = (String) ctx.get("perioNo");
        String	yearVal    =  (String) ctx.get("yearVal");
        String	monthVal   =  (String) ctx.get("monthVal");
        String	weekPart   =  (String) ctx.get("weekPart");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, perioNo);
        param.setWhereClauseParameter(paramIndex++, yearVal);
		param.setWhereClauseParameter(paramIndex++, monthVal);
		param.setWhereClauseParameter(paramIndex++, weekPart);
		
        PosRowSet rowSet =   this.getDao("candao").find("tbdb_wk_sche_fb004", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
}

