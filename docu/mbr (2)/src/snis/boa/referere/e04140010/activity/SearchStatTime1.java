/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04140010.activity.SearchStatTime1.java
 * 파일설명		: 경주 주회타임  분석(평균 경주 주회타임)
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-03 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04140010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchStatTime1 extends SnisActivity {
    public SearchStatTime1()
    {
    }

    public String runActivity(PosContext ctx)
    {
        SearchStatTimeSum(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchStatTimeSum(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();

    	sbQuery.append("   /* e04140010.activity.SearchStatTime1 */	                        		   ");
    	sbQuery.append("\n SELECT '3' AS TURN_CNT,                                      			   ");
    	sbQuery.append("\n        TO_CHAR(A.AVG_RACE_TM,'000000.00')			AVG_RACE_TM			   ");
        sbQuery.append("\n       ,TO_CHAR(B.RANK_RACE_TM,'000000.00')			RANK_RACE_TM           ");
        sbQuery.append("\n FROM (   SELECT ROUND(AVG(A.RACE_TM),2) AVG_RACE_TM                         ");
        sbQuery.append("\n            FROM TBEB_ORGAN A, TBEB_RACE_DAY_ORD B, TBEB_RACE C                           ");
        sbQuery.append("\n           WHERE A.STND_YEAR = B.STND_YEAR                                   ");
        sbQuery.append("\n             AND A.MBR_CD    = B.MBR_CD                                      ");
        sbQuery.append("\n             AND A.TMS       = B.TMS                                         ");
        sbQuery.append("\n             AND A.DAY_ORD   = B.DAY_ORD                                     ");
        sbQuery.append("\n             AND A.STND_YEAR = C.STND_YEAR                                   ");                       
        sbQuery.append("\n             AND A.MBR_CD = C.MBR_CD                                         ");
        sbQuery.append("\n             AND A.TMS  = C.TMS                                              ");
        sbQuery.append("\n             AND A.DAY_ORD = C.DAY_ORD                                       ");
        sbQuery.append("\n             AND A.RACE_NO = C.RACE_NO                                       ");
        sbQuery.append("\n             AND C.TURN_CNT = 3                                              ");        
        sbQuery.append("\n             AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                 ");
        sbQuery.append("\n       ) A,                                                                  ");
        sbQuery.append("\n       ( SELECT ROUND(AVG(A.RACE_TM),2) RANK_RACE_TM                         ");
        sbQuery.append("\n           FROM  TBEB_ORGAN A, TBEB_RACE_DAY_ORD B, TBEB_RACE C              ");
        sbQuery.append("\n          WHERE A.STND_YEAR = B.STND_YEAR                                    ");
        sbQuery.append("\n            AND A.MBR_CD    = B.MBR_CD                                       ");
        sbQuery.append("\n            AND A.TMS       = B.TMS                                          ");
   	 	sbQuery.append("\n            AND A.DAY_ORD   = B.DAY_ORD                                      ");
        sbQuery.append("\n             AND A.STND_YEAR = C.STND_YEAR                                   ");                       
        sbQuery.append("\n             AND A.MBR_CD = C.MBR_CD                                         ");
        sbQuery.append("\n             AND A.TMS  = C.TMS                                              ");
        sbQuery.append("\n             AND A.DAY_ORD = C.DAY_ORD                                       ");
        sbQuery.append("\n             AND A.RACE_NO = C.RACE_NO                                       ");
        sbQuery.append("\n             AND C.TURN_CNT = 3                                              ");        
   	 	sbQuery.append("\n    	      AND B.RACE_DAY   BETWEEN ? AND ?								   ");
   	 	sbQuery.append("\n            AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                  ");
   	 	sbQuery.append("\n            AND A.RANK = 1                                                   ");
   	 	sbQuery.append("\n        ) B                                                                  ");
   	 	sbQuery.append("\n UNION ALL                                                                  ");
    	sbQuery.append("\n SELECT '2' AS TURN_CNT,                                      			   ");
    	sbQuery.append("\n        TO_CHAR(A.AVG_RACE_TM,'000000.00')			AVG_RACE_TM			   ");
        sbQuery.append("\n       ,TO_CHAR(B.RANK_RACE_TM,'000000.00')			RANK_RACE_TM           ");
        sbQuery.append("\n FROM (   SELECT ROUND(AVG(A.RACE_TM),2) AVG_RACE_TM                         ");
        sbQuery.append("\n            FROM TBEB_ORGAN A, TBEB_RACE_DAY_ORD B, TBEB_RACE C              ");
        sbQuery.append("\n           WHERE A.STND_YEAR = B.STND_YEAR                                   ");
        sbQuery.append("\n             AND A.MBR_CD    = B.MBR_CD                                      ");
        sbQuery.append("\n             AND A.TMS       = B.TMS                                         ");
        sbQuery.append("\n             AND A.DAY_ORD   = B.DAY_ORD                                     ");
        sbQuery.append("\n             AND A.STND_YEAR = C.STND_YEAR                                   ");                       
        sbQuery.append("\n             AND A.MBR_CD = C.MBR_CD                                         ");
        sbQuery.append("\n             AND A.TMS  = C.TMS                                              ");
        sbQuery.append("\n             AND A.DAY_ORD = C.DAY_ORD                                       ");
        sbQuery.append("\n             AND A.RACE_NO = C.RACE_NO                                       ");
        sbQuery.append("\n             AND C.TURN_CNT = 2                                              ");        
        sbQuery.append("\n             AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                 ");
        sbQuery.append("\n       ) A,                                                                  ");
        sbQuery.append("\n       ( SELECT ROUND(AVG(A.RACE_TM),2) RANK_RACE_TM                         ");
        sbQuery.append("\n           FROM  TBEB_ORGAN A, TBEB_RACE_DAY_ORD B, TBEB_RACE C              ");
        sbQuery.append("\n          WHERE A.STND_YEAR = B.STND_YEAR                                    ");
        sbQuery.append("\n            AND A.MBR_CD    = B.MBR_CD                                       ");
        sbQuery.append("\n            AND A.TMS       = B.TMS                                          ");
   	 	sbQuery.append("\n            AND A.DAY_ORD   = B.DAY_ORD                                      ");
   	 	sbQuery.append("\n    	      AND B.RACE_DAY   BETWEEN ? AND ?								   ");
        sbQuery.append("\n             AND A.STND_YEAR = C.STND_YEAR                                   ");                       
        sbQuery.append("\n             AND A.MBR_CD = C.MBR_CD                                         ");
        sbQuery.append("\n             AND A.TMS  = C.TMS                                              ");
        sbQuery.append("\n             AND A.DAY_ORD = C.DAY_ORD                                       ");
        sbQuery.append("\n             AND A.RACE_NO = C.RACE_NO                                       ");
        sbQuery.append("\n             AND C.TURN_CNT = 2                                              ");        
   	 	sbQuery.append("\n            AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                  ");
   	 	sbQuery.append("\n            AND A.RANK = 1                                                   ");
   	 	sbQuery.append("\n        ) B                                                                  ");
    	
    	
    	
    	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
             
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime1";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
 
    }

}


