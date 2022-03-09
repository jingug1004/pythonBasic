/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010050.activity.SearchEquipInsp.java
 * 파일설명		: 확정 검사를 조회 한다.. 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010050.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;
import com.posdata.glue.dao.vo.*;

/**
* 장비 확정검사 조회 
* @auther 김성희 
* @version 1.0
*/
public class SearchEquipInsp extends SnisActivity
{    
	String EQUIP_DRWLT_CMPL_YN = "X";
    public SearchEquipInsp()
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
    	getEquipInsp(ctx);
    	getEquipInspDetail(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 장비 확정검사 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getEquipInsp(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipInsp";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        Integer	dayOrd =  new Integer((String) ctx.get("DAY_ORD"));
        rowset = getEquipInspRowSet(stndYear, mbrCd, tms, dayOrd);
        
        
       if(rowset!= null && rowset.hasNext()){
    		PosRow[] pr = rowset.getAllRow();
    		if(logger.isDebugEnabled())logger.logDebug("pr = " + pr);
    		if(pr!= null && pr.length>0){
    			if(logger.isDebugEnabled())logger.logDebug("pr[0] = " + pr[0]);
    			EQUIP_DRWLT_CMPL_YN = (String)(pr[0].getAttribute("INSP_PRS_STAT_CD"));
    			if(logger.isDebugEnabled())logger.logDebug("INSP_PRS_STAT_CD = " + EQUIP_DRWLT_CMPL_YN);
    		}
    	}
    	
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * 확정검사 상세 내역 조회 
     * @param ctx
     */
    private void getEquipInspDetail(PosContext ctx){
    	PosRowSet    rowset;
        String	sResultKey = "dsOutEquipInspDetail";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        Integer	dayOrd =  new Integer((String) ctx.get("DAY_ORD"));
        rowset = (new SearchEquipInspDetail()).getEquipInspDetailRowSet(stndYear, mbrCd, tms, dayOrd, EQUIP_DRWLT_CMPL_YN);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * 확정검사 결과 조회 
     * @param stndYear 기준년도
     * @param tms	회차 
     * @param dayOrd	일차 
     * @return
     */
    public PosRowSet getEquipInspRowSet(String stndYear, String mbrCd, Integer tms, Integer dayOrd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, dayOrd);
        return  this.getDao("boadao").find("tbef_equip_insp_ff001", param);
    }
    
}

