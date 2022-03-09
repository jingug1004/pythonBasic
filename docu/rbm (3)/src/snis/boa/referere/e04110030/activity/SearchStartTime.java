/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04110030.activity.SearchRaceVoilAct.java
 * 파일설명		: 스타트타임조회
 * 작성자			: 김경현
 * 버전			: 1.0.0
 * 생성일자		: 2013-07-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04110030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchStartTime extends SnisActivity {
	private int paramIndex = 0;
    public SearchStartTime()
    {
    }
    public String runActivity(PosContext ctx)
    {
    	getStartTime(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    public void getStartTime(PosContext ctx) 
    {
    	String	sResultKey = "dsOutRace";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd      = (String) ctx.get("MBR_CD");
        String	tmsFrom	   = (String) ctx.get("TMS_FROM");
        String	tmsTo	   = (String) ctx.get("TMS_TO");
        Integer	minTms =  new Integer((String) ctx.get("MIN_TMS"));
        Integer	maxTms =  new Integer((String) ctx.get("MAX_TMS"));
        PosParameter param = new PosParameter();
        paramIndex = 0;

        StringBuffer sb = new StringBuffer();
    	
        sb.append("SELECT TOR.RACER_NO /*SearchStartTime.getStartTime*/ ");
        sb.append("		, TRM.RACER_PERIO_NO                                "); 
        sb.append("		, TRM.RACER_GRD_CD                                  "); 
        sb.append("		, TRM.NM_KOR                 RACER_NM             	"); 
        sb.append("		, TOR.RACE_CNT                                      "); 
        sb.append("		, TST.AVG_STAR_TM									");
        sb.append("		, TST.MAX_STAR_TM                                   ");
        sb.append("		, TST.MIN_STAR_TM                                   ");
        sb.append("		, TRVA.FLYING_CNT											");
        sb.append("		, TRVA.LATE_CNT												");
        genQueryCol(sb, param, minTms.intValue(),  maxTms.intValue());
        sb.append(" FROM															");
        sb.append("TBEC_RACER_MASTER TRM,											");
        sb.append("( SELECT        													");
        sb.append("          TOR.RACER_NO                                   ");
        sb.append("        , COUNT(*)                      RACE_CNT         ");
        sb.append("	 FROM    														");
        sb.append("         TBEB_RACE TR											");
        sb.append("       , TBEB_ORGAN TOR											");
        sb.append("  WHERE    TR.STND_YEAR    = TOR.STND_YEAR						");
        sb.append("  AND      TR.MBR_CD       = TOR.MBR_CD							");
        sb.append("  AND      TR.TMS          = TOR.TMS								");
        sb.append("  AND      TR.DAY_ORD      = TOR.DAY_ORD							");
        sb.append("  AND      TR.RACE_NO      = TOR.RACE_NO							");
        sb.append("  AND      TR.RACE_STTS_CD = '001'								");
        sb.append("  AND      TOR .ABSE_CD     <> '003'								");
        sb.append("  AND      TOR .IMMT_CLS_CD <> '003'								");
        sb.append("  AND      TOR.STND_YEAR    =  ? /**P*/							");
        sb.append("  AND      TOR.MBR_CD       =  ? /**P*/							");
        sb.append("  AND      TOR.RACE_DAY BETWEEN ? AND ? /**P*/					");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        param.setWhereClauseParameter(paramIndex++, tmsFrom);
        param.setWhereClauseParameter(paramIndex++, tmsTo);
        sb.append("  GROUP BY TOR.RACER_NO) TOR,									");
        sb.append("(																");
        sb.append("  SELECT                                       					");                                                                                                                
        sb.append(" 		  TOR.RACER_NO                           				");                                                                                                           
        sb.append(" 		, NVL(ROUND(max(DECODE(TOR.ST_MTHD_CD, '001', NULL,TOR.STAR_TM)), 2), 0) max_STAR_TM  		");
        sb.append(" 		, NVL(ROUND(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, TOR.STAR_TM))/FC_ZERO_TO_ONE(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, 1))), 2), 0) AVG_STAR_TM 		");
        sb.append(" 		, NVL(ROUND(min(DECODE(TOR.ST_MTHD_CD, '001', NULL,TOR.STAR_TM)), 2), 0) min_STAR_TM        ");                                                                                                        
        sb.append("  FROM   TBEB_ORGAN        TOR                            		");                                                                                                    
        sb.append("  WHERE  TOR .STND_YEAR    = ?                                   ");                                                                                       
        sb.append("  AND    TOR .RACE_DAY    <= (                                   ");                                                                                               
        sb.append("                              SELECT                             ");                                                                                         
        sb.append("                                   MAX(TRDO.RACE_DAY) RACE_DAY   ");                                                                                      
        sb.append("                              FROM   TBEB_RACE_DAY_ORD TRDO      ");                                                                                        
        sb.append("                              WHERE  TRDO.STND_YEAR = ?          ");                                                                                         
        sb.append("                              AND    TRDO.MBR_CD    = ?          ");                                                                                         
        sb.append("                              AND    TRDO.RACE_DAY BETWEEN ? AND ?  ");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        param.setWhereClauseParameter(paramIndex++, tmsFrom);
        param.setWhereClauseParameter(paramIndex++, tmsTo);
        sb.append("                             )                                   "); 
        sb.append("  GROUP BY TOR.RACER_NO     										");
        sb.append(") TST,															");
        sb.append("(																");
        sb.append("  SELECT racer_no,												");
        sb.append("  		sum(decode(voil_cd, '110',1)) flying_cnt,				");
        sb.append("  		sum(decode(voil_cd, '120',1)) late_cnt					");
        sb.append("  		FROM  TBED_RACE_VOIL_ACT								");
        sb.append("  		WHERE stnd_year = ?										");
        sb.append("  		AND MBR_CD = ?											");
        sb.append("  		AND RACE_DAY BETWEEN ? AND ?							");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        param.setWhereClauseParameter(paramIndex++, tmsFrom);
        param.setWhereClauseParameter(paramIndex++, tmsTo);
        sb.append("  		GROUP BY racer_no										");
        sb.append(") TRVA,															");
        sb.append("(																");
        sb.append("  SELECT STND_YEAR, racer_no	    								");
        genQueryTms(sb, param, minTms.intValue(),  maxTms.intValue());
        sb.append("  FROM (  														");
        sb.append("  	SELECT stnd_year, racer_no, tms, round(avg(star_tm),2) avg_tm");
        sb.append("  	FROM TBEB_organ												");
        sb.append("  	WHERE stnd_year = ?											");
        sb.append("  	AND mbr_cd = ?												");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        sb.append("  	GROUP BY stnd_year, racer_no, TMS							");
        sb.append("  ) ST															");
        sb.append("  GROUP BY STND_YEAR, RACER_NO									");
        sb.append(") ST																");
        sb.append("WHERE															");
        sb.append("TOR.RACER_NO     = TRM.RACER_NO									");
        sb.append("AND TRM.RACER_NO = TST.RACER_NO									");
        sb.append("AND TRM.RACER_NO = TRVA.RACER_NO(+)								");
        sb.append("AND TRM.RACER_NO = ST.RACER_NO									");
        //선수별
        if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
        	sb.append("ORDER BY TOR.RACER_NO										");
        } else if ("2".equals(ctx.get("SEARCH_COND".trim()))) {
        //등급별
        	sb.append("ORDER BY TRM.RACER_GRD_CD, TRM.RACER_NO						");
        } else if ("3".equals(ctx.get("SEARCH_COND".trim()))) {
        //기수별
        	sb.append("ORDER BY TO_NUMBER(TRM.RACER_PERIO_NO), TRM.RACER_NO					");
        }
        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sb.toString(), param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    /**
     * 컬럼 Alias 생성
     * @param tms
     * @return
     */
    public void genQueryCol(StringBuffer sb, PosParameter param, int minTms, int maxTms){
    	for(int i=minTms; i<=maxTms; i++){
    	    sb.append("\n, ").append("ST.TMS_").append(i);
    	}
    }
    
    /**
     * 회차 부분 QUERY
     * @param sb
     * @param param
     * @param minTms
     * @param maxTms
     */
    public void genQueryTms(StringBuffer sb, PosParameter param, int minTms, int maxTms){
    	for(int i=minTms; i<=maxTms; i++){
    		sb.append("\n, ").append("         MAX(CASE WHEN tms=").append(i).append(" THEN avg_tm ELSE 0 END) AS TMS_").append(i);
    	}
    }

}

