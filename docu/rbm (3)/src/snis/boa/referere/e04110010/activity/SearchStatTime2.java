/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04110010.activity.SearchStatTime2.java
 * 파일설명		: 스타트타임 초별 분포도 현황
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-04 
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

public class SearchStatTime2 extends SnisActivity {
    public SearchStatTime2()
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

    	  sbQuery.append("\n SELECT  ORD																																");
    	  sbQuery.append("\n        ,DAY_ORD                                                                                                                            ");
    	  sbQuery.append("\n        ,FLYING_CNT                                                                                                                         ");
    	  sbQuery.append("\n        ,COUNT1                                                                                                                             ");
    	  sbQuery.append("\n        ,COUNT2                                                                                                                             ");
    	  sbQuery.append("\n        ,COUNT3                                                                                                                             ");
    	  sbQuery.append("\n        ,COUNT4                                                                                                                             ");
    	  sbQuery.append("\n        ,COUNT5                                                                                                                             ");
    	  sbQuery.append("\n        ,COUNT6                                                                                                                             ");
    	  sbQuery.append("\n        ,COUNT7                                                                                                                             ");
    	  sbQuery.append("\n        ,LATE_CNT                                                                                                                           ");
    	  sbQuery.append("\n        ,NOT_CNT                                                                                                                            ");
    	  sbQuery.append("\n   FROM  (                                                                                                                                  ");
    	  sbQuery.append("\n     SELECT  '1' AS ORD                                                                                                                     ");
    	  sbQuery.append("\n             ,TO_CHAR(DAY_ORD)||'일' AS DAY_ORD                                                                                             ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.VOIL_CD , '110', 1 , 0 )) FLYING_CNT                                                                            ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.COUNT1 , 0, 0 , 1 )) COUNT1                                                                                     ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.COUNT2 , 0, 0 , 1 )) COUNT2                                                                                     ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.COUNT3 , 0, 0 , 1 )) COUNT3                                                                                     ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.COUNT4 , 0, 0 , 1 )) COUNT4                                                                                     ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.COUNT5 , 0, 0 , 1 )) COUNT5                                                                                     ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.COUNT6 , 0, 0 , 1 )) COUNT6                                                                                     ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.COUNT7 , 0, 0 , 1 )) COUNT7                                                                                     ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.VOIL_CD , '120', 1 , 0 )) LATE_CNT                                                                              ");
    	  sbQuery.append("\n             ,SUM(DECODE( A.VOIL_CD , '995', 1 , '996', 1 , '997', 1 , 0 )) NOT_CNT                          								");
    	  sbQuery.append("\n      FROM (  SELECT   A.DAY_ORD     DAY_ORD                                                                                                ");
    	  sbQuery.append("\n                      ,B.VOIL_CD     VOIL_CD                                                                                                ");
    	  sbQuery.append("\n                      ,CASE WHEN NVL(A.STAR_TM,0) >=0   AND NVL(A.STAR_TM,0) <=0.09 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT1 ");
    	  sbQuery.append("\n                      ,CASE WHEN NVL(A.STAR_TM,0) >=0.1 AND NVL(A.STAR_TM,0) <=0.19 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT2 ");
    	  sbQuery.append("\n                      ,CASE WHEN NVL(A.STAR_TM,0) >=0.2 AND NVL(A.STAR_TM,0) <=0.29 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT3 ");
    	  sbQuery.append("\n                      ,CASE WHEN NVL(A.STAR_TM,0) >=0.3 AND NVL(A.STAR_TM,0) <=0.39 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT4 ");
    	  sbQuery.append("\n                      ,CASE WHEN NVL(A.STAR_TM,0) >=0.4 AND NVL(A.STAR_TM,0) <=0.49 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT5 ");
    	  sbQuery.append("\n                      ,CASE WHEN NVL(A.STAR_TM,0) >=0.5 AND NVL(A.STAR_TM,0) <=0.99 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT6 ");
    	  sbQuery.append("\n                      ,CASE WHEN NVL(A.STAR_TM,0) >=1.0 AND NVL(A.STAR_TM,0) <=1.50 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT7 ");
    	  sbQuery.append("\n               FROM TBEB_ORGAN A,																											");
    	  sbQuery.append("\n									( SELECT * 																								");
    	  sbQuery.append("\n        							   FROM TBED_RACE_VOIL_ACT          																	");                                                                     
    	  sbQuery.append("\n          							   WHERE VOIL_CD   IN ('110','120','995','996','997')) B,												");
    	  sbQuery.append("\n 		                     (SELECT   A.RACER_NO  RACER_NO                                                                             	");
  		  sbQuery.append("\n 								FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                                         	");
  		  sbQuery.append("\n 								WHERE  A.RACER_NO 			 = B.RACER_NO                                                               	");
  		  sbQuery.append("\n 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                                  	");
  		  sbQuery.append("\n 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                                              	");
  		  sbQuery.append("\n 							    ) C                                                                                                     	");           
          sbQuery.append("\n              WHERE A.STND_YEAR = ?                                                                                                    		");
    	  sbQuery.append("\n                AND A.MBR_CD    = ?                                                                                                         ");
    	  sbQuery.append("\n                AND A.TMS       = ?                                                                                                       	");
    	  sbQuery.append("\n                AND A.STND_YEAR = B.STND_YEAR(+)                                                                                            ");
    	  sbQuery.append("\n                AND A.MBR_CD    = B.MBR_CD(+)                                                                                               ");
    	  sbQuery.append("\n                AND A.TMS       = B.TMS(+)                                                                                                  ");
    	  sbQuery.append("\n                AND A.DAY_ORD   = B.DAY_ORD(+)                                                                                              ");
    	  sbQuery.append("\n                AND A.RACE_NO   = B.RACE_NO(+)                                                                                              ");
    	  sbQuery.append("\n                AND A.RACER_NO  = B.RACER_NO(+)                                                                                             ");
    	  sbQuery.append("\n 		  		AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    								");
		  sbQuery.append("\n 	       		AND A.RACER_NO  	= C.RACER_NO                                                                                        	");
       	  sbQuery.append("\n          )  A                                                                                                                              ");
    	  sbQuery.append("\n           GROUP BY A.DAY_ORD                                                                                                               ");
    	  sbQuery.append("\n      UNION ALL                                                                                                                             ");
    	  sbQuery.append("\n     SELECT  '2' AS ORD                                                                                                                     ");
    	  sbQuery.append("\n            ,'계' AS DAY_ORD                                                                                                                ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.VOIL_CD , '110', 1 , 0 )) FLYING_CNT                                                                             ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.COUNT1 , 0, 0 , 1 )) COUNT1                                                                                      ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.COUNT2 , 0, 0 , 1 )) COUNT2                                                                                      ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.COUNT3 , 0, 0 , 1 )) COUNT3                                                                                      ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.COUNT4 , 0, 0 , 1 )) COUNT4                                                                                      ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.COUNT5 , 0, 0 , 1 )) COUNT5                                                                                      ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.COUNT6 , 0, 0 , 1 )) COUNT6                                                                                      ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.COUNT7 , 0, 0 , 1 )) COUNT7                                                                                      ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.VOIL_CD , '120', 1 , 0 )) LATE_CNT                                                                               ");
    	  sbQuery.append("\n            ,SUM(DECODE( A.VOIL_CD , '995', 1 , '996', 1 , '997', 1 , 0 )) NOT_CNT                          								");
    	  sbQuery.append("\n       FROM (     SELECT     B.VOIL_CD     VOIL_CD                                                                                          ");
    	  sbQuery.append("\n                             ,CASE WHEN NVL(A.STAR_TM,0) >=0   AND NVL(A.STAR_TM,0) <=0.09 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT1  ");
    	  sbQuery.append("\n                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.1 AND NVL(A.STAR_TM,0) <=0.19 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT2  ");
    	  sbQuery.append("\n                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.2 AND NVL(A.STAR_TM,0) <=0.29 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT3  ");
    	  sbQuery.append("\n                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.3 AND NVL(A.STAR_TM,0) <=0.39 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT4  ");
    	  sbQuery.append("\n                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.4 AND NVL(A.STAR_TM,0) <=0.49 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT5  ");
    	  sbQuery.append("\n                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.5 AND NVL(A.STAR_TM,0) <=0.99 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT6  ");
    	  sbQuery.append("\n                             ,CASE WHEN NVL(A.STAR_TM,0) >=1.0 AND NVL(A.STAR_TM,0) <=1.50 AND (A.STAR_TM IS NOT NULL ) THEN STAR_TM ELSE 0  END  COUNT7  ");
    	  sbQuery.append("\n                   FROM TBEB_ORGAN A,	                                                                                   					");
    	  sbQuery.append("\n									( SELECT * 																								");
    	  sbQuery.append("\n        							   FROM TBED_RACE_VOIL_ACT          																	");                                                                     
    	  sbQuery.append("\n          							   WHERE VOIL_CD   IN ('110','120','995','996','997') ) B,												");
    	  sbQuery.append("\n 		                     (SELECT   A.RACER_NO  RACER_NO                                                                             	");
  		  sbQuery.append("\n 								FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                                         	");
  		  sbQuery.append("\n 								WHERE  A.RACER_NO 			 = B.RACER_NO                                                               	");
  		  sbQuery.append("\n 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                                  	");
  		  sbQuery.append("\n 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                                              	");
  		  sbQuery.append("\n 							    ) C                                                                                                     	");           
          sbQuery.append("\n                  WHERE A.STND_YEAR = ?                                                                                                		");
    	  sbQuery.append("\n                    AND A.MBR_CD    = ?                                                                                                     ");
    	  sbQuery.append("\n                    AND A.TMS       = ?                                                                                                     ");
    	  sbQuery.append("\n                    AND A.STND_YEAR = B.STND_YEAR(+)                                                                                        ");
    	  sbQuery.append("\n                    AND A.MBR_CD    = B.MBR_CD(+)                                                                                           ");
    	  sbQuery.append("\n                    AND A.TMS       = B.TMS(+)                                                                                              ");
    	  sbQuery.append("\n                    AND A.DAY_ORD   = B.DAY_ORD(+)                                                                                          ");
    	  sbQuery.append("\n                    AND A.RACE_NO   = B.RACE_NO(+)                                                                                          ");
    	  sbQuery.append("\n                    AND A.RACER_NO  = B.RACER_NO(+)                                                                                         ");
    	  sbQuery.append("\n 		  			AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    							");
		  sbQuery.append("\n 	       			AND A.RACER_NO  	= C.RACER_NO                                                                                        ");
       	  sbQuery.append("\n                   )  A                                                                                                                     ");
    	  sbQuery.append("\n     UNION ALL                                                                                                                              ");
    	  sbQuery.append("\n        SELECT      A.ORD                                        ORD                                                                        ");
    	  sbQuery.append("\n                   ,A.DAY_ORD                                    DAY_ORD                                                                    ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.FLYING_CNT / SUM_CNT) * 100,2))    FLYING_CNT                                            ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT1 / SUM_CNT) * 100,2))        COUNT1                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT2 / SUM_CNT) * 100,2))        COUNT2                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT3 / SUM_CNT) * 100,2))        COUNT3                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT4 / SUM_CNT) * 100,2))        COUNT4                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT5 / SUM_CNT) * 100,2))        COUNT5                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT6 / SUM_CNT) * 100,2))        COUNT6                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT7 / SUM_CNT) * 100,2))        COUNT7                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.LATE_CNT / SUM_CNT) * 100,2))      LATE_CNT                                              ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.NOT_CNT  / SUM_CNT) * 100,2))      NOT_CNT                                               ");
    	  sbQuery.append("\n             FROM    ( SELECT  '3' AS ORD                                                                                                   ");
    	  sbQuery.append("\n                            ,'분포율(%)' AS DAY_ORD                                                                                          ");   
    	  sbQuery.append("\n                            ,SUM(DECODE( A.VOIL_CD , '110', 1 , 0 ))    FLYING_CNT                                                          ");
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT1 , 0, 0 , 1 ))         COUNT1                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT2 , 0, 0 , 1 ))         COUNT2                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT3 , 0, 0 , 1 ))         COUNT3                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT4 , 0, 0 , 1 ))         COUNT4                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT5 , 0, 0 , 1 ))         COUNT5                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT6 , 0, 0 , 1 ))         COUNT6                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT7 , 0, 0 , 1 ))         COUNT7                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.VOIL_CD , '120', 1 , 0 ))    LATE_CNT                                                            ");
    	  sbQuery.append("\n            				,SUM(DECODE( A.VOIL_CD , '995', 1 , '996', 1 , '997', 1 , 0 )) NOT_CNT                          				");
    	  sbQuery.append("\n            				,SUM(DECODE( A.COUNT1 , 0, 0 , 1 ))                                                                             ");
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT2 , 0, 0 , 1 ))                                                                             ");
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT3 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT4 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT5 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT6 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT7 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.VOIL_CD , '110', 1 , '120', 1 , '995', 1 , '996', 1 , '997', 1 , 0 )) SUM_CNT                    ");                          				
     	  sbQuery.append("\n                       FROM (     SELECT     B.VOIL_CD     VOIL_CD                                                                          ");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0   AND NVL(A.STAR_TM,0) <=0.09 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT1	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.1 AND NVL(A.STAR_TM,0) <=0.19 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT2	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.2 AND NVL(A.STAR_TM,0) <=0.29 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT3	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.3 AND NVL(A.STAR_TM,0) <=0.39 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT4	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.4 AND NVL(A.STAR_TM,0) <=0.49 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT5	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.5 AND NVL(A.STAR_TM,0) <=0.99 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT6	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=1.0 AND NVL(A.STAR_TM,0) <=1.50 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT7	");
    	  sbQuery.append("\n                                   FROM TBEB_ORGAN A,																						");
    	  sbQuery.append("\n									( SELECT * 																								");
    	  sbQuery.append("\n        							   FROM TBED_RACE_VOIL_ACT          																	");                                                                     
    	  sbQuery.append("\n          							   WHERE VOIL_CD   IN ('110','120','995','996','997') ) B,												");
    	  sbQuery.append("\n 		                     		(SELECT   A.RACER_NO  RACER_NO                                                                          ");
  		  sbQuery.append("\n 									  FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                                       ");
  		  sbQuery.append("\n 									  WHERE  A.RACER_NO 			 = B.RACER_NO                                                           ");
  		  sbQuery.append("\n 									    AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                              ");
  		  sbQuery.append("\n 										AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                                          ");
  		  sbQuery.append("\n 							    	 ) C                                                                                                    ");           
          sbQuery.append("\n                      			  WHERE A.STND_YEAR = ?                                                                                		"); 
    	  sbQuery.append("\n                                    AND A.MBR_CD    = ?                                                                                     ");
    	  sbQuery.append("\n                                    AND A.TMS       = ?                                                                                    	");
    	  sbQuery.append("\n                                    AND A.STND_YEAR = B.STND_YEAR(+)                                                                        ");
    	  sbQuery.append("\n                                    AND A.MBR_CD    = B.MBR_CD(+)                                                                           ");
    	  sbQuery.append("\n                                    AND A.TMS       = B.TMS(+)                                                                              ");
    	  sbQuery.append("\n                                    AND A.DAY_ORD   = B.DAY_ORD(+)                                                                          ");
    	  sbQuery.append("\n                                    AND A.RACE_NO   = B.RACE_NO(+)                                                                          ");
    	  sbQuery.append("\n                                    AND A.RACER_NO  = B.RACER_NO(+)                                                                         ");
    	  sbQuery.append("\n 		  							AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    			");
		  sbQuery.append("\n 	       							AND A.RACER_NO  	= C.RACER_NO                                                                        ");
       	  sbQuery.append("\n                                   )  A                                                                                                     ");
    	  sbQuery.append("\n                    ) A                                                                                                                     ");
    	  sbQuery.append("\n      UNION ALL                                                                                                                             ");
    	  sbQuery.append("\n        SELECT      A.ORD                                        ORD                                                                        ");
    	  sbQuery.append("\n                   ,A.DAY_ORD                                    DAY_ORD                                                                    ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.FLYING_CNT / SUM_CNT) * 100,2))    FLYING_CNT                                            ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT1 / SUM_CNT) * 100,2))        COUNT1                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT2 / SUM_CNT) * 100,2))        COUNT2                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT3 / SUM_CNT) * 100,2))        COUNT3                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT4 / SUM_CNT) * 100,2))        COUNT4                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT5 / SUM_CNT) * 100,2))        COUNT5                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT6 / SUM_CNT) * 100,2))        COUNT6                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT7 / SUM_CNT) * 100,2))        COUNT7                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.LATE_CNT / SUM_CNT) * 100,2))      LATE_CNT                                              ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.NOT_CNT  / SUM_CNT) * 100,2))      NOT_CNT                                               ");
    	  sbQuery.append("\n             FROM    ( SELECT  '4' AS ORD                                                                                                   ");
    	  sbQuery.append("\n                            ,'전회차 분포율(%)' AS DAY_ORD                                                                                     ");   
    	  sbQuery.append("\n                            ,SUM(DECODE( A.VOIL_CD , '110', 1 , 0 ))    FLYING_CNT                                                          ");
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT1 , 0, 0 , 1 ))         COUNT1                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT2 , 0, 0 , 1 ))         COUNT2                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT3 , 0, 0 , 1 ))         COUNT3                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT4 , 0, 0 , 1 ))         COUNT4                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT5 , 0, 0 , 1 ))         COUNT5                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT6 , 0, 0 , 1 ))         COUNT6                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT7 , 0, 0 , 1 ))         COUNT7                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.VOIL_CD , '120', 1 , 0 ))    LATE_CNT                                                            ");
    	  sbQuery.append("\n            				,SUM(DECODE( A.VOIL_CD , '995', 1 , '996', 1 , '997', 1 , 0 )) NOT_CNT                          				");
    	  sbQuery.append("\n            				,SUM(DECODE( A.COUNT1 , 0, 0 , 1 ))                                                                             ");
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT2 , 0, 0 , 1 ))                                                                             ");
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT3 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT4 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT5 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT6 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT7 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.VOIL_CD , '110', 1 , '120', 1 , '995', 1 , '996', 1 , '997', 1 , 0 )) SUM_CNT                    ");                          				
    	  sbQuery.append("\n                       FROM (     SELECT     B.VOIL_CD     VOIL_CD                                                                          ");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0   AND NVL(A.STAR_TM,0) <=0.09 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT1	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.1 AND NVL(A.STAR_TM,0) <=0.19 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT2	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.2 AND NVL(A.STAR_TM,0) <=0.29 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT3	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.3 AND NVL(A.STAR_TM,0) <=0.39 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT4	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.4 AND NVL(A.STAR_TM,0) <=0.49 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT5	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.5 AND NVL(A.STAR_TM,0) <=0.99 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT6	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=1.0 AND NVL(A.STAR_TM,0) <=1.50 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT7	");
    	  sbQuery.append("\n                                   FROM TBEB_ORGAN A,																						");
    	  sbQuery.append("\n									( SELECT * 																								");
    	  sbQuery.append("\n        							   FROM TBED_RACE_VOIL_ACT          																	");                                                                     
    	  sbQuery.append("\n          							   WHERE VOIL_CD   IN ('110','120','995','996','997') ) B,												");
    	  sbQuery.append("\n 		                     (SELECT   A.RACER_NO  RACER_NO                                                                             	");
  		  sbQuery.append("\n 								FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                                         	");
  		  sbQuery.append("\n 								WHERE  A.RACER_NO 			 = B.RACER_NO                                                               	");
  		  sbQuery.append("\n 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                                  	");
  		  sbQuery.append("\n 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                                              	");
  		  sbQuery.append("\n 							    ) C                                                                                                     	");           
          sbQuery.append("\n                                  WHERE A.STND_YEAR = ?                                                                                		"); 
    	  sbQuery.append("\n                                    AND A.MBR_CD    = ?                                                                                     ");
    	  sbQuery.append("\n                                    AND A.TMS       = TO_NUMBER(?) -1                                                                       ");
    	  sbQuery.append("\n                                    AND A.STND_YEAR = B.STND_YEAR(+)                                                                        ");
    	  sbQuery.append("\n                                    AND A.MBR_CD    = B.MBR_CD(+)                                                                           ");
    	  sbQuery.append("\n                                    AND A.TMS       = B.TMS(+)                                                                              ");
    	  sbQuery.append("\n                                    AND A.DAY_ORD   = B.DAY_ORD(+)                                                                          ");
    	  sbQuery.append("\n                                    AND A.RACE_NO   = B.RACE_NO(+)                                                                          ");
    	  sbQuery.append("\n                                    AND A.RACER_NO  = B.RACER_NO(+)                                                                         ");
    	  sbQuery.append("\n 		  							AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    			");
		  sbQuery.append("\n 	       							AND A.RACER_NO  	= C.RACER_NO                                                                        ");
       	  sbQuery.append("\n                                   )  A                                                                                                     ");
    	  sbQuery.append("\n                    ) A                                                                                                                     ");
    	  sbQuery.append("\n      UNION ALL                                                                                                                             ");
    	  sbQuery.append("\n        SELECT      A.ORD                                        ORD                                                                        ");
    	  sbQuery.append("\n                   ,A.DAY_ORD                                    DAY_ORD                                                                    ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.FLYING_CNT / SUM_CNT) * 100,2))    FLYING_CNT                                            ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT1 / SUM_CNT) * 100,2))        COUNT1                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT2 / SUM_CNT) * 100,2))        COUNT2                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT3 / SUM_CNT) * 100,2))        COUNT3                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT4 / SUM_CNT) * 100,2))        COUNT4                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT5 / SUM_CNT) * 100,2))        COUNT5                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT6 / SUM_CNT) * 100,2))        COUNT6                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.COUNT7 / SUM_CNT) * 100,2))        COUNT7                                                ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.LATE_CNT / SUM_CNT) * 100,2))      LATE_CNT                                              ");
    	  sbQuery.append("\n                   ,DECODE(SUM_CNT, 0, 0, ROUND((A.NOT_CNT  / SUM_CNT) * 100,2))      NOT_CNT                                               ");
    	  sbQuery.append("\n             FROM    ( SELECT  '5' AS ORD                                                                                                   ");
    	  sbQuery.append("\n                            ,'누적평균(%)' AS DAY_ORD                                                                                         ");   
    	  sbQuery.append("\n                            ,SUM(DECODE( A.VOIL_CD , '110', 1 , 0 ))     FLYING_CNT                                                         ");
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT1 , 0, 0 , 1 ))         COUNT1                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT2 , 0, 0 , 1 ))         COUNT2                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT3 , 0, 0 , 1 ))         COUNT3                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT4 , 0, 0 , 1 ))         COUNT4                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT5 , 0, 0 , 1 ))         COUNT5                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT6 , 0, 0 , 1 ))         COUNT6                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.COUNT7 , 0, 0 , 1 ))         COUNT7                                                              ");    
    	  sbQuery.append("\n                            ,SUM(DECODE( A.VOIL_CD , '120', 1 , 0 ))     LATE_CNT                                                           ");
    	  sbQuery.append("\n            				,SUM(DECODE( A.VOIL_CD , '995', 1 , '996', 1 , '997', 1 , 0 )) NOT_CNT                          				");
    	  sbQuery.append("\n            				,SUM(DECODE( A.COUNT1 , 0, 0 , 1 ))                                                                             ");
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT2 , 0, 0 , 1 ))                                                                             ");
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT3 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT4 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT5 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT6 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.COUNT7 , 0, 0 , 1 ))                                                                             "); 
    	  sbQuery.append("\n            				+SUM(DECODE( A.VOIL_CD , '110', 1 , '120', 1 , '995', 1 , '996', 1 , '997', 1 , 0 )) SUM_CNT                    ");                          				
     	  sbQuery.append("\n                       FROM (     SELECT     B.VOIL_CD     VOIL_CD                                                                          ");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0   AND NVL(A.STAR_TM,0) <=0.09 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT1	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.1 AND NVL(A.STAR_TM,0) <=0.19 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT2	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.2 AND NVL(A.STAR_TM,0) <=0.29 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT3	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.3 AND NVL(A.STAR_TM,0) <=0.39 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT4	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.4 AND NVL(A.STAR_TM,0) <=0.49 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT5	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=0.5 AND NVL(A.STAR_TM,0) <=0.99 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT6	");
    	  sbQuery.append("\n                                             ,CASE WHEN NVL(A.STAR_TM,0) >=1.0 AND NVL(A.STAR_TM,0) <=1.50 AND (A.STAR_TM IS NOT NULL) THEN STAR_TM ELSE 0  END COUNT7	");
    	  sbQuery.append("\n                                   FROM TBEB_ORGAN A,																						");
    	  sbQuery.append("\n									( SELECT * 																								");
    	  sbQuery.append("\n        							   FROM TBED_RACE_VOIL_ACT          																	");                                                                     
    	  sbQuery.append("\n          							   WHERE VOIL_CD   IN ('110','120','995','996','997') ) B,												");
    	  sbQuery.append("\n 		                     (SELECT   A.RACER_NO  RACER_NO                                                                             	");
  		  sbQuery.append("\n 								FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                                         	");
  		  sbQuery.append("\n 								WHERE  A.RACER_NO 			 = B.RACER_NO                                                               	");
  		  sbQuery.append("\n 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                                  	");
  		  sbQuery.append("\n 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                                              	");
  		  sbQuery.append("\n 							    ) C                                                                                                     	");           
          sbQuery.append("\n                                  WHERE A.STND_YEAR = ?                                                                                		"); 
    	  sbQuery.append("\n                                    AND A.MBR_CD    = ?                                                                                     ");
    	  sbQuery.append("\n                                    AND A.TMS       BETWEEN 1 AND ?                                                                         ");
    	  sbQuery.append("\n                                    AND A.STND_YEAR = B.STND_YEAR(+)                                                                        ");
    	  sbQuery.append("\n                                    AND A.MBR_CD    = B.MBR_CD(+)                                                                           ");
    	  sbQuery.append("\n                                    AND A.TMS       = B.TMS(+)                                                                              ");
    	  sbQuery.append("\n                                    AND A.DAY_ORD   = B.DAY_ORD(+)                                                                          ");
    	  sbQuery.append("\n                                    AND A.RACE_NO   = B.RACE_NO(+)                                                                          ");
    	  sbQuery.append("\n                                    AND A.RACER_NO  = B.RACER_NO(+)                                                                         ");
    	  sbQuery.append("\n 		  							AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    			");
		  sbQuery.append("\n 	       							AND A.RACER_NO  	= C.RACER_NO                                                                        ");
       	  sbQuery.append("\n                                   )  A                                                                                                     ");
    	  sbQuery.append("\n                    ) A                                                                                                                     ");
    	  sbQuery.append("\n         )                                                                                                                                  ");
    	  sbQuery.append("\n     ORDER BY ORD, DAY_ORD																													");
     	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS		          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS		          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS		          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS		          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS		          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String    sResultKey = "dsOutStatTime2";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
 
    }

}


