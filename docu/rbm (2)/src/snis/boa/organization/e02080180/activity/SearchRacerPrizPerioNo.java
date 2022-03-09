/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030020.activity.SearchRacerPrizPerioNo.java
 * 파일설명		: 기수별 성적 상금 조회 
 * 작성자			: 김성희
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02080180.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 기수별 성적 상금 조회하는 클래스이다.
* @auther 김성희
* @version 1.0
*/
public class SearchRacerPrizPerioNo extends SnisActivity
{    
	
    public SearchRacerPrizPerioNo()
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
    	
        searchRacerPriz(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 기수별 성적 상금 조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public void searchRacerPriz(PosContext ctx)
    {
        String	sResultKey = "dsOutRacerPrizPerioNo";
        String	strDt   = (String) ctx.get("STR_DT");
        String	endDt   = (String) ctx.get("END_DT");
        int  MAX_PERIO_NO=  new Integer((String) ctx.get("MAX_RACER_PERIO_NO")).intValue();
        
        PosParameter param = new PosParameter();
        int paramIndex = 0;
        StringBuffer sb = new StringBuffer();
        sb.append(" -- 기수별 성적 상금  (동적 query를 java단에서 생성함) \n");
        sb.append(" SELECT   \n");
        sb.append("     TRP.STND_YEAR,   \n");
        for(int i=1; i<=MAX_PERIO_NO; i++){
        	sb.append("     FLOOR(SUM(CASE WHEN TRM.RACER_PERIO_NO = "+i+" THEN RANK_AMT + RUN_AMT + EVENT_AMT + WAIT_AMT + SAFY_AMT + STR_AMT+ WIN_AMT + ETC_AMT ELSE 0 END)/CASE WHEN COUNT(DISTINCT CASE WHEN TRM.RACER_PERIO_NO="+i+" THEN TRP.RACER_NO ELSE NULL END)=0 THEN 1 ELSE COUNT(DISTINCT CASE WHEN TRM.RACER_PERIO_NO="+i+" THEN TRP.RACER_NO ELSE NULL END) END) AS PRIZ_AMT_"+i+", \n");
        }
        sb.append("     FLOOR(SUM(RANK_AMT + RUN_AMT + EVENT_AMT + WAIT_AMT + SAFY_AMT + STR_AMT+ WIN_AMT + ETC_AMT)/count(distinct TRP.RACER_NO)) AS PRIZ_AMT_AVG \n");
        sb.append(" FROM  TBEB_RACE_TMS TRT,  -- 경주 회차 TABLE \n");
        sb.append("     TBEG_RACE_PRIZ TRP,   -- 경주별 상금 TABLE \n");
        sb.append("     TBEC_RACER_MASTER TRM   -- 선수 MASTER \n");
        sb.append(" WHERE TRP.STND_YEAR = TRT.STND_YEAR \n");
        sb.append(" AND TRP.TMS = TRT.TMS \n");
        sb.append(" AND TRP.RACER_NO = TRM.RACER_NO \n");
        sb.append(" AND TRP.MBR_CD = TRT.MBR_CD \n");
        sb.append(" AND TRT.TMS_PRIZ_FINISH_YN = 'Y' \n");
        sb.append(" AND (TRT.STND_YEAR,TRP.MBR_CD, TRP.TMS) IN (SELECT STND_YEAR, MBR_CD, TMS FROM TBEB_RACE_DAY_ORD WHERE  RACE_DAY BETWEEN ? AND ?)     \n"); 
        sb.append(" GROUP BY TRP.STND_YEAR \n");
        sb.append(" ORDER BY TRP.STND_YEAR DESC\n");

        param.setWhereClauseParameter(paramIndex++ , strDt);
        param.setWhereClauseParameter(paramIndex++ , endDt);
        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sb.toString(), param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
}
