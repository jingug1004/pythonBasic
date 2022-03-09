/*================================================================================
 * 시스템			: 상금관리 
 * 소스파일 이름	: snis.boa.financial.e07030040.activity.SearchMonthPay.java
 * 파일설명		: 개인월별출력을 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-27
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07030040.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 개인월별출력을 조회 한다. 
* @auther 유재은  
* @version 1.0
*/
public class SearchMonthPay extends SnisActivity
{    
    public SearchMonthPay()
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
    	
		getComInfo(ctx);
		        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 개인월별출력  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPay";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	racerNo   = (String) ctx.get("RACER_NO");
        String	nmKor   = (String) ctx.get("NM_KOR");
        String	suppAmt10   = (String) ctx.get("SUPP_AMT_10");
        String	awardAmt   = (String) ctx.get("AWARD_AMT");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, suppAmt10);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, awardAmt);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, nmKor);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_tax_month_ff004", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
}

