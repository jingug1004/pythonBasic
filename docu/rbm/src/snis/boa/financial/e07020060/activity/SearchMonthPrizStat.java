/*================================================================================
 * 시스템			: 상금관리 
 * 소스파일 이름	: snis.boa.financial.e07020060.activity.SearchMonthPrizStat.java
 * 파일설명		: 상금지급 통계 현황을 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-16
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07020060.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 상금지급 통계 현황을 조회 한다. 
* @auther 김성희  
* @version 1.0
*/
public class SearchMonthPrizStat extends SnisActivity
{    
    public SearchMonthPrizStat()
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
    	
		getMonthPrizPerio(ctx);
		getMonthPrizGrd(ctx);
		getMonthPrizAmt(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 기수별 평균 금액 비교  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getMonthPrizPerio(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPrizPerio";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	includeAllRegRacer   = (String) ctx.get("INCLUDE_ALL_REG_RACER");
        String	includeAllStatRacer   = (String) ctx.get("INCLUDE_ALL_STAT_RACER");
        
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, stndYear);
		param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, includeAllRegRacer);
		param.setWhereClauseParameter(paramIndex++, includeAllStatRacer);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_month_pay_ff101", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> 등급별 평균 금액 비교  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getMonthPrizGrd(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPrizGrd";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	includeAllRegRacer   = (String) ctx.get("INCLUDE_ALL_REG_RACER");
        String	includeAllStatRacer   = (String) ctx.get("INCLUDE_ALL_STAT_RACER");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, stndYear);
		param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, includeAllRegRacer);
		param.setWhereClauseParameter(paramIndex++, includeAllStatRacer);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_month_pay_ff102", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * <p> 기수별 평균 금액 비교  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getMonthPrizAmt(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPrizAmt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	includeAllRegRacer   = (String) ctx.get("INCLUDE_ALL_REG_RACER");
        String	includeAllStatRacer   = (String) ctx.get("INCLUDE_ALL_STAT_RACER");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, stndYear);
		param.setWhereClauseParameter(paramIndex++, payMonth);
		
		param.setWhereClauseParameter(paramIndex++, includeAllRegRacer);
		param.setWhereClauseParameter(paramIndex++, includeAllStatRacer);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_month_pay_ff103", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    
}

