/*================================================================================
 * 시스템			: 학사관리 
 * 소스파일 이름	: snis.can_boa.boatstd.d06009025.activity.SearchViolResult.java
 * 파일설명		: 종합승정시간을 조회 한다.. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06009025.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* 종합승정시간 조회 
* @auther 유재은 
* @version 1.0
*/
public class SearchViolResult extends SnisActivity
{    
	public SearchViolResult()
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
    	getEduList(ctx);
    	getTempAbs(ctx);
    	logger.logDebug("------------------>");
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 종합승정시간 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getEduList(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsCandList";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        rowset = getEduListRowSet(pRacerPerioNo);
            	
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * 평균/최대/최소 조회 
     * @param ctx
     */
    private void getTempAbs(PosContext ctx){
    	PosRowSet    rowset;
        String	sResultKey = "dsCandListResult";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        rowset = getTempAbsRowSet(pRacerPerioNo);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * 종합승정시간 조회 
     * @param pRacerPerioNo 기수
     * @return
     */
    public PosRowSet getEduListRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        return  this.getDao("candao").find("tbdn_cand_race_detlr_fb002", param);
    }
    
    /**
     * 평균/최대/최소 조회 
     * @param pRacerPerioNo 기수
     * @return
     */
    public PosRowSet getTempAbsRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        
        return  this.getDao("candao").find("tbdn_cand_race_detlr_fb003", param);
    }
    
}

