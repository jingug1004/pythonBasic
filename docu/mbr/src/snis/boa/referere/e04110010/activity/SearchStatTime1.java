/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04110010.activity.SearchStatTime1.java
 * 파일설명		: 평균 스타트타임 현황
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-03 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04110010.activity;

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
    	int maxDayOrd = 3;	// 3일차 까지..
    	
    	PosParameter param = new PosParameter();
        Integer i = 0;

    	for(int d = 1; d <= maxDayOrd; d++){
    		sbQuery.append(getSql(Integer.toString(d)));
    		if(d != (maxDayOrd)){
    			sbQuery.append(" UNION ALL ");       
    		}
    		
    		setParam(ctx, param, i);
    	}
		
    	sbQuery.append(" 		ORDER BY TMS, DAY_ORD, RACE_NO     ");        
        
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime1";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
        Util.setSearchCount(ctx, rowset.getAllRow().length);
        
    }
    
    private void setParam(PosContext ctx, PosParameter param, Integer i){
    	param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
                
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
    }
    
    private StringBuffer getSql(String dayOrd){
   	 
    	StringBuffer sbQuery = new StringBuffer();
    	
    	sbQuery.append(" 	SELECT /* SearchStatTime1.getSql */				 \n");
    	sbQuery.append(" 		 A.TMS                        TMS				 \n");
    	sbQuery.append(" 	    ,A.DAY_ORD                    DAY_ORD    \n");
    	sbQuery.append("		,A.RACE_NO|| ' 경주'            RACE_NO  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_1, 2) COURSE1_1  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_2, 2) COURSE1_2  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_3, 2) COURSE1_3  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_4, 2) COURSE1_4  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_5, 2) COURSE1_5  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_6, 2) COURSE1_6  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_7, 2) COURSE1_7  \n");
    	sbQuery.append("		,ROUND(A.COURSE1_8, 2) COURSE1_8  \n");
    	sbQuery.append("		,ROUND(ROUND((NVL(A.COURSE1_1,0) + NVL(A.COURSE1_2,0) + NVL(A.COURSE1_3,0) +											    \n");
    	sbQuery.append(" 	                         NVL(A.COURSE1_4,0) + NVL(A.COURSE1_5,0) + NVL(A.COURSE1_6,0) +								    \n");
    	sbQuery.append(" 	                         NVL(A.COURSE1_7,0) + NVL(A.COURSE1_8,0) ) / B.CNT,2),2) COURSE1_SUM         \n");
    	sbQuery.append(" 	FROM (SELECT A.TMS                                 TMS                                                    \n");
    	sbQuery.append(" 	            ,A.DAY_ORD                             DAY_ORD                                                \n");
    	sbQuery.append(" 	            ,A.RACE_NO                             RACE_NO                                                \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,1,A.STAR_TM)) COURSE1_1                                            \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,2,A.STAR_TM)) COURSE1_2                                            \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,3,A.STAR_TM)) COURSE1_3                                            \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,4,A.STAR_TM)) COURSE1_4                                            \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,5,A.STAR_TM)) COURSE1_5                                            \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,6,A.STAR_TM)) COURSE1_6                                            \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,7,A.STAR_TM)) COURSE1_7                                            \n");
    	sbQuery.append(" 	            ,AVG(DECODE(A.ENTRY_COURSE,8,A.STAR_TM)) COURSE1_8                                            \n");
    	sbQuery.append(" 	      FROM  TBEB_ORGAN  A,( SELECT A.RACER_NO  RACER_NO                                                   \n");
    	sbQuery.append(" 								 FROM  TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                             \n");
    	sbQuery.append(" 								WHERE  A.RACER_NO = B.RACER_NO                                                              \n");
    	sbQuery.append(" 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                      \n");
    	sbQuery.append(" 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                \n");
    	sbQuery.append(" 							    ) B	                                                                                      \n");
    	sbQuery.append("          WHERE A.STND_YEAR = ?                                                                             \n");
    	sbQuery.append(" 	       AND A.MBR_CD     = ?                                                                               \n");
    	sbQuery.append(" 	       AND A.TMS        = ?                                                                               \n");
    	sbQuery.append(" 	       AND A.DAY_ORD    = " + dayOrd + "                                                                  \n");
    	sbQuery.append(" 		   AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    	    \n");
    	sbQuery.append(" 	       AND A.RACER_NO  	   = B.RACER_NO                                                                   \n");
    	sbQuery.append(" 	       GROUP BY A.STND_YEAR,MBR_CD, A.TMS, A.DAY_ORD, A.RACE_NO  ) A,                                     \n");
    	sbQuery.append(" 	    (SELECT A.TMS             TMS                                                                         \n");
    	sbQuery.append(" 	           ,A.DAY_ORD         DAY_ORD                                                                     \n");
    	sbQuery.append(" 	           ,A.RACE_NO         RACE_NO                                                                     \n");
    	sbQuery.append(" 	           ,COUNT(*)          CNT                                                                         \n");
    	sbQuery.append(" 	     FROM  TBEB_ORGAN A, (SELECT A.RACER_NO  RACER_NO                                                     \n");
    	sbQuery.append(" 								FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                             \n");
    	sbQuery.append(" 								WHERE  A.RACER_NO 			 = B.RACER_NO                                                       \n");
    	sbQuery.append(" 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                      \n");
    	sbQuery.append(" 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                \n");
    	sbQuery.append(" 							    ) B	                                                                                      \n");
    	sbQuery.append(" 	     WHERE A.STND_YEAR = ?                                                                                \n");
    	sbQuery.append(" 	       AND A.MBR_CD    = ?                                                                                \n");
    	sbQuery.append(" 	       AND A.TMS       = ?                                                                                \n");
    	sbQuery.append(" 	       AND A.DAY_ORD   = " + dayOrd + "                                                                   \n");
    	sbQuery.append(" 	       AND A.STAR_TM IS NOT NULL                                                                          \n");
    	sbQuery.append(" 		   AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    	    \n");
    	sbQuery.append(" 	       AND A.RACER_NO = B.RACER_NO                                                                        \n");
    	sbQuery.append(" 	       GROUP BY A.STND_YEAR,A.MBR_CD, A.TMS, A.DAY_ORD, A.RACE_NO  ) B                                    \n");
    	sbQuery.append(" 	  WHERE A.TMS      = B.TMS                                                                                \n");
    	sbQuery.append(" 	    AND A.DAY_ORD  = B.DAY_ORD                                                                            \n");
    	sbQuery.append(" 	    AND A.RACE_NO  = B.RACE_NO                                                                            \n");
    	sbQuery.append(" UNION ALL                                                                                                  \n");
    	sbQuery.append(" 		SELECT   A.TMS                                                                                          \n");
    	sbQuery.append(" 		        ,A.DAY_ORD                                                                                      \n");
    	sbQuery.append(" 		        ,'평균' RACE_NO                                                                                  \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_1), 2)  COURSE1_1                                                   \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_2), 2)  COURSE1_2                                                   \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_3), 2)  COURSE1_3                                                   \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_4), 2)  COURSE1_4                                                   \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_5), 2)  COURSE1_5                                                   \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_6), 2)  COURSE1_6                                                   \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_7), 2)  COURSE1_7                                                   \n");
    	sbQuery.append(" 		        ,ROUND(AVG(A.COURSE1_8), 2)  COURSE1_8                                                   \n");
    	sbQuery.append(" 		        ,ROUND(ROUND((NVL(AVG(A.COURSE1_1),0) + NVL(AVG(A.COURSE1_2),0) + NVL(AVG(A.COURSE1_3),0) +   \n");
    	sbQuery.append(" 		                        NVL(AVG(A.COURSE1_4),0) + NVL(AVG(A.COURSE1_5),0) + NVL(AVG(A.COURSE1_6),0) +   \n");
    	sbQuery.append(" 		                        NVL(AVG(A.COURSE1_7),0) + NVL(AVG(A.COURSE1_8),0))  /                           \n");
    	sbQuery.append(" 		                        DECODE((DECODE(AVG(A.COURSE1_1),NULL,0,1) + DECODE(AVG(A.COURSE1_2),NULL,0,1) + \n");
    	sbQuery.append(" 		                        DECODE(AVG(A.COURSE1_3),NULL,0,1) + DECODE(AVG(A.COURSE1_4),NULL,0,1) +         \n");
    	sbQuery.append(" 		                        DECODE(AVG(A.COURSE1_5),NULL,0,1) + DECODE(AVG(A.COURSE1_6),NULL,0,1) +         \n");
    	sbQuery.append(" 		                        DECODE(AVG(A.COURSE1_7),NULL,0,1) + DECODE(AVG(A.COURSE1_8),NULL,0,1)), 0, 1,   \n");
    	sbQuery.append(" 		                        (DECODE(AVG(A.COURSE1_1),NULL,0,1) + DECODE(AVG(A.COURSE1_2),NULL,0,1) +        \n");
    	sbQuery.append(" 		                        DECODE(AVG(A.COURSE1_3),NULL,0,1) + DECODE(AVG(A.COURSE1_4),NULL,0,1) +         \n");
    	sbQuery.append(" 		                        DECODE(AVG(A.COURSE1_5),NULL,0,1) + DECODE(AVG(A.COURSE1_6),NULL,0,1) +         \n");
    	sbQuery.append(" 		                        DECODE(AVG(A.COURSE1_7),NULL,0,1) + DECODE(AVG(A.COURSE1_8),NULL,0,1))), 2),2) COURSE1_SUM \n");
    	sbQuery.append(" 		FROM (   SELECT  A.TMS                         TMS                                         \n");
    	sbQuery.append(" 		        ,A.DAY_ORD                             DAY_ORD                                     \n");
    	sbQuery.append(" 		        ,A.RACE_NO                             RACE_NO                                     \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,1,A.STAR_TM)) COURSE1_1                                 \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,2,A.STAR_TM)) COURSE1_2                                 \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,3,A.STAR_TM)) COURSE1_3                                 \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,4,A.STAR_TM)) COURSE1_4                                 \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,5,A.STAR_TM)) COURSE1_5                                 \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,6,A.STAR_TM)) COURSE1_6                                 \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,7,A.STAR_TM)) COURSE1_7                                 \n");
    	sbQuery.append(" 		        ,AVG(DECODE(A.ENTRY_COURSE,8,A.STAR_TM)) COURSE1_8                                 \n");
    	sbQuery.append(" 		 FROM  TBEB_ORGAN A, (SELECT   A.RACER_NO  RACER_NO                                        \n");
    	sbQuery.append(" 								FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                \n");
    	sbQuery.append(" 								WHERE  A.RACER_NO 			 = B.RACER_NO                                          \n");
    	sbQuery.append(" 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                         \n");
    	sbQuery.append(" 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))   \n");
    	sbQuery.append(" 							    ) B	                                                                         \n");
    	sbQuery.append(" 		 WHERE A.STND_YEAR = ?                                                                     \n");
    	sbQuery.append(" 		   AND A.MBR_CD    = ?                                                                     \n");
    	sbQuery.append(" 		   AND A.TMS       = ?                                                                     \n");
    	sbQuery.append(" 		   AND A.DAY_ORD   = " + dayOrd + "                                                        \n");
    	sbQuery.append(" 		   AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                             \n");
    	sbQuery.append(" 	       AND A.RACER_NO  	   = B.RACER_NO                                                      \n");
    	sbQuery.append(" 		   GROUP BY A.STND_YEAR,A.MBR_CD,A.TMS,A.DAY_ORD,A.RACE_NO                                 \n");
    	sbQuery.append(" 		) A                                                                                        \n");
    	sbQuery.append(" 		GROUP BY A.TMS,A.DAY_ORD                                                                   \n");
    	
    	return sbQuery;
    }

}
