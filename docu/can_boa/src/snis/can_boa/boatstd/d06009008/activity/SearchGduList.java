/*================================================================================
 * 시스템			: 학사관리 
 * 소스파일 이름	: snis.can_boa.boatstd.d06009008.activity.SearchGduList.java
 * 파일설명		: 인적자원을 조회 한다.. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-20
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06009008.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* 인적자원 조회 
* @auther 유재은 
* @version 1.0
*/
public class SearchGduList extends SnisActivity
{    
	public SearchGduList()
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

    	getEduList(ctx);
    	getTempAbs(ctx);
    	logger.logDebug("------------------>");
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 졸업자 및 이수자 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getEduList(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsEduList";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        //String	pGdu   = (String) ctx.get("pGdu");
        //rowset = getEduListRowSet(pRacerPerioNo, pGdu);
        rowset = getEduListRowSet(pRacerPerioNo);
            	
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * 확정검사 상세 내역 조회 
     * @param ctx
     */
    private void getTempAbs(PosContext ctx){
    	PosRowSet    rowset;
        String	sResultKey = "dsTempAbs";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        //String	pGdu   = (String) ctx.get("pGdu");
        //rowset = getTempAbsRowSet(pRacerPerioNo, pGdu);
        rowset = getTempAbsRowSet(pRacerPerioNo);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * 졸업자 및 이수자 조회 
     * @param pRacerPerioNo 기수
     * @param pGdu	졸업구분 
     * @return
     */
    public PosRowSet getEduListRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        //param.setWhereClauseParameter(i++, pGdu);
        
        return  this.getDao("candao").find("tbdn_cmpt_edu_outlr_fb001", param);
    }
    
    /**
     * 퇴소자 조회 
     * @param pRacerPerioNo 기수
     * @param pGdu	졸업구분 
     * @return
     */
    public PosRowSet getTempAbsRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        //param.setWhereClauseParameter(i++, pGdu);
        
        return  this.getDao("candao").find("tbdn_cmpt_edu_outlr_fb002", param);
    }
    
}

