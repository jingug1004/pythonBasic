/*================================================================================
 * 시스템			: 상금관리 
 * 소스파일 이름	: snis.boa.financial.e07020010.activity.SearchRankAmtList.java
 * 파일설명		: 월별예상상금을 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-01
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07020010.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 월별예상상금을 조회 한다. 
* @auther 유재은  
* @version 1.0
*/
public class SearchRankAmtList extends SnisActivity
{    
    public SearchRankAmtList()
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
		getRacerComInfo(ctx);
		getRacerEventComInfo(ctx);
		getMonthPay(ctx);
		getSafyList(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 월별예상상금  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutAmtlList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
        
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff001", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> 월별예상상금 (출전수당)  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getRacerComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutRunList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff002", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> 월별예상상금 (이벤트경주수당)  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getRacerEventComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutEventList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_event_rankamt_ff002", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * <p> 월별예상상금 (무사고수당) </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getMonthPay(PosContext ctx)
    {
    	String	sResultKey = "dsOutStrList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff003", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> 월별예상상금 (안전수당)  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getSafyList(PosContext ctx)
    {
    	String	sResultKey = "dsOutSafyList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff004", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
}

