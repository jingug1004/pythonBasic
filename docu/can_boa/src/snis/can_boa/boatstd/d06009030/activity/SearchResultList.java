/*================================================================================
 * 시스템			: 학사관리 
 * 소스파일 이름	: snis.can_boa.boatstd.d06009030.activity.SearchResultList.java
 * 파일설명		: 부분별승정시간을 조회 한다.. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06009030.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* 부분별승정시간 조회 
* @auther 유재은 
* @version 1.0
*/
public class SearchResultList extends SnisActivity
{   
	private int paramIndex = 0;
	public SearchResultList()
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
//    	사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	getEduList(ctx);
    	//getTempAbs(ctx);
    	logger.logDebug("------------------>");
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 부분별승정시간 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getEduList(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsCandListResult";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        String	pGdu   = (String) ctx.get("pGdu");
       // rowset = getEduListRowSet(pRacerPerioNo,pGdu);
            	
        //ctx.put(sResultKey, rowset);
        //Util.addResultKey(ctx, sResultKey);
        
        getTempAbs(ctx);
    }
    
    /**
     * 부분별승정일자 조회 
     * @param pRacerPerioNo 기수
     * * @param pGdu 승정구분
     * @return
     */
    public PosRowSet getEduListRowSet(String pRacerPerioNo, String pGdu)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pGdu);
        return  this.getDao("candao").find("tbdn_cand_race_detlpart_fb001", param);
    }
    
    /**
     * 부분별승정시간 조회 
     * @param ctx
     */
    private void getTempAbs(PosContext ctx){
    	
    	PosRowSet    rowset;
        String	sResultKey = "dsCandList";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        String	pGdu   = (String) ctx.get("pGdu");
        
        PosRowSet prs = getEduListRowSet(pRacerPerioNo,pGdu);
        PosParameter param = new PosParameter();
        paramIndex = 0;

        StringBuffer sb = new StringBuffer("-- 부분별승정시간 현황  \n");
        
        sb.append(" SELECT    \n");
        sb.append(" 	  B.CAND_NO,    \n");
        sb.append(" 	  B.NM_KOR,    \n");
        //sb.append(" 	  NVL((TOTAL_BOAT_TIME - MOD(TOTAL_BOAT_TIME,60))/60,0) ||'분 ' || NVL(MOD(TOTAL_BOAT_TIME,60),0) ||'초'  AS TOTAL_BOAT_TIME    \n");
        sb.append(" 	  NVL(TOTAL_BOAT_TIME,0) ||'분 ' AS TOTAL_BOAT_TIME    \n");
        //동적구성
        genDay(sb, param, prs);
        //sb.append(" 	  NVL(DAY_1,0) AS DAY_1,    \n");
        
        sb.append("FROM(    \n");
        sb.append("     SELECT     \n");
        sb.append("           RACER_PERIO_NO, 	    \n");
        sb.append("           CAND_NO, 	    \n");
        sb.append("           SUM(TOTAL_BOAT_TIME) AS TOTAL_BOAT_TIME 	    \n");
        //동적구성
        genDaySub(sb, param, prs);
        //sb.append(" 	      SUM(DAY_1) AS DAY_1,    \n");
        
        
        sb.append("     FROM(    \n");
        sb.append("     	 SELECT     \n");  
        sb.append("           		RACER_PERIO_NO, 	    \n");
        sb.append("           		CAND_NO, 	    \n");
        //sb.append("           		NVL(SUM(to_number(SUBSTR(boat_time,0,2))* 60 +to_number(SUBSTR(boat_time,3,2))),0) AS TOTAL_BOAT_TIME 	    \n");
        sb.append("           		NVL(SUM(to_number(boat_time)),0) AS TOTAL_BOAT_TIME 	    \n");
        //동적구성
        genDaySubT(sb, param, prs);
        //sb.append(" 	      	    CASE WHEN DT = '20080103' THEN NVL(SUM(to_number(SUBSTR(boat_time,0,2))* 60 +to_number(SUBSTR(boat_time,3,2))),0)    \n");
        //sb.append("           		ELSE 0 END AS DAY_1	    \n");
        
        
        sb.append("          FROM  TBDN_CAND_RACE_DETL    \n");
        sb.append("          WHERE RACER_PERIO_NO =?   \n");
        param.setWhereClauseParameter(paramIndex++, pRacerPerioNo);
        sb.append("          AND   GBN = ?    \n");
        param.setWhereClauseParameter(paramIndex++, pGdu);
        sb.append("          GROUP BY RACER_PERIO_NO,CAND_NO, DT    \n");
        sb.append("         )    \n");
        
        sb.append("      GROUP BY RACER_PERIO_NO, CAND_NO    \n");
        sb.append("  ) A, TBDN_CAND_IDENT B    \n");
        sb.append(" WHERE  A.RACER_PERIO_NO(+) = B.RACER_PERIO_NO    \n");
        sb.append(" AND    A.CAND_NO(+) = B.CAND_NO    \n");
        sb.append(" AND    B.RACER_PERIO_NO = ?    \n");
        param.setWhereClauseParameter(paramIndex++, pRacerPerioNo);
        sb.append(" ORDER BY B.CAND_NO    \n");

        PosRowSet rowSet =   this.getDao("candao").findByQueryStatement(sb.toString(), param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    /**
     * 부분별승정시간 요일별 시간 QUERY
     * @param sb
     * @param param
     * @param basicDayOrd
     */
    public void genDay(StringBuffer sb, PosParameter param, PosRowSet prs){
    	
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    		//sb.append("     ,NVL(DAY_1,0) AS DAY_").append(i).append(" \n");
	    		sb.append("     ,NVL(DAY_").append(i).append(",0) AS DAY_").append(i).append(" \n");
	    	}
    	}
    }
    
    public void genDaySub(StringBuffer sb, PosParameter param, PosRowSet prs){
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    			sb.append("     ,SUM(DAY_").append(i).append(") AS DAY_").append(i).append(" \n");
	    	}
    	}
    }
    
    public void genDaySubT(StringBuffer sb, PosParameter param, PosRowSet prs){
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    			//sb.append("     ,CASE WHEN DT = ? THEN NVL(SUM(to_number(SUBSTR(boat_time,0,2))* 60 +to_number(SUBSTR(boat_time,3,2))),0) ELSE 0 END AS DAY_").append(i).append(" \n");
	    			sb.append("     ,CASE WHEN DT = ? THEN NVL(SUM(to_number(boat_time)),0) ELSE 0 END AS DAY_").append(i).append(" \n");
	    			param.setWhereClauseParameter(paramIndex++, (String)pr[i-1].getAttribute("DT"));
	    	}
    	}
    }
}

