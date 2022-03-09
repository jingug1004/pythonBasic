/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04110020.activity.SearchStatTime3.java
 * 파일설명		: 진입코스별 스타트타임 현황
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-04 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04110020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchStatTime3 extends SnisActivity {
    public SearchStatTime3()
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

    	sbQuery.append("\n SELECT  '1'                               					   GBN ");                                                   
    	sbQuery.append("\n        ,'기간 평균 스타트타임'           					 	       GBN_NM ");      
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_1 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_2 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_3 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_4 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_5 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_6 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_7 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_8 ");   
    	sbQuery.append("\n FROM    TBEB_ORGAN  A, ");
    	sbQuery.append("\n       ( SELECT A.RACER_NO  RACER_NO ");
		sbQuery.append("\n 	       FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B ");
		sbQuery.append("\n 	       WHERE  A.RACER_NO           = B.RACER_NO ");
		sbQuery.append("\n 	       AND    A.RACER_PERIO_NO     = NVL(?,A.RACER_PERIO_NO) ");
		sbQuery.append("\n         AND    SUBSTR(GET_DEC(B.RES_NO),7,1) = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1)) ");
		sbQuery.append("\n       ) B ");
        sbQuery.append("\n WHERE A.STND_YEAR = ? ");
    	sbQuery.append("\n AND A.MBR_CD    = ? ");
    	sbQuery.append("\n AND A.RACE_DAY  BETWEEN ? AND ? ");
    	sbQuery.append("\n AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD) ");
		sbQuery.append("\n AND A.RACER_NO  	   = B.RACER_NO ");
    	sbQuery.append("\n UNION ALL ");
    	sbQuery.append("\n SELECT  '2'           		                                   GBN ");         	
    	sbQuery.append("\n        ,'누적평균' 			                                   GBN_NM ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_1 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_2 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_3 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_4 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_5 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_6 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_7 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/NVL(SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, 1))),1),'0.00') COURSE1_8 ");      
    	sbQuery.append("\n FROM    TBEB_ORGAN  A, ");
    	sbQuery.append("\n       ( SELECT A.RACER_NO  RACER_NO ");
		sbQuery.append("\n         FROM  TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B ");
		sbQuery.append("\n 	       WHERE  A.RACER_NO = B.RACER_NO ");
		sbQuery.append("\n 	       AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO) ");
		sbQuery.append("\n 	       AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1)) ");
		sbQuery.append("\n       ) B ");
        sbQuery.append("\n WHERE A.STND_YEAR = ? ");
    	sbQuery.append("\n AND A.MBR_CD    = ? ");
    	sbQuery.append("\n AND A.RACER_GRD_CD   = NVL(?,A.RACER_GRD_CD) ");
		sbQuery.append("\n AND A.RACER_NO  	   = B.RACER_NO ");
    	sbQuery.append("\n UNION ALL ");
    	sbQuery.append("\n SELECT  '4'                                      			 GBN ");                                                                         
    	sbQuery.append("\n        ,'출발위반'                                		   	     GBN_NM ");       
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_1 , 0, 0 , 1 )))        COURSE1_1 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_2 , 0, 0 , 1 )))        COURSE1_2 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_3 , 0, 0 , 1 )))        COURSE1_3 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_4 , 0, 0 , 1 )))        COURSE1_4 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_5 , 0, 0 , 1 )))        COURSE1_5 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_6 , 0, 0 , 1 )))        COURSE1_6 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_7 , 0, 0 , 1 )))        COURSE1_7 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_8 , 0, 0 , 1 )))        COURSE1_8 ");
    	sbQuery.append("\n FROM  ( SELECT  CASE WHEN A.ENTRY_COURSE = 1   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_1 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 2   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_2 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 3   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_3 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 4   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_4 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 5   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_5 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 6   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_6 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 7   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_7 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 8   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_8 ");
    	sbQuery.append("\n         FROM    TBEB_ORGAN A, ");
    	sbQuery.append("\n               ( SELECT * ");
    	sbQuery.append("\n   	  	       FROM   TBED_RACE_VOIL_ACT ");                                                        
    	sbQuery.append("\n   	  	       WHERE  VOIL_CD   IN ('110','120') ) B,	");
    	sbQuery.append("\n 	             ( SELECT A.RACER_NO  RACER_NO ");
		sbQuery.append("\n                 FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B ");
		sbQuery.append("\n                 WHERE  A.RACER_NO = B.RACER_NO ");
		sbQuery.append("\n 	               AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO) ");
		sbQuery.append("\n                 AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))) C ");
        sbQuery.append("\n    	   WHERE A.STND_YEAR = ? ");
    	sbQuery.append("\n         AND A.MBR_CD    = ? ");
    	sbQuery.append("\n         AND A.RACE_DAY  BETWEEN ? AND ? ");
    	sbQuery.append("\n         AND A.STND_YEAR = B.STND_YEAR(+) ");
    	sbQuery.append("\n         AND A.MBR_CD    = B.MBR_CD(+) ");
    	sbQuery.append("\n         AND A.TMS       = B.TMS(+) ");
    	sbQuery.append("\n         AND A.DAY_ORD   = B.DAY_ORD(+) ");
    	sbQuery.append("\n         AND A.RACE_NO   = B.RACE_NO(+) ");
    	sbQuery.append("\n         AND A.RACER_NO  = B.RACER_NO(+) ");
    	sbQuery.append("\n 		   AND A.RACER_GRD_CD = NVL(?,A.RACER_GRD_CD) ");
		sbQuery.append("\n 	       AND A.RACER_NO  	  = C.RACER_NO ");
    	sbQuery.append("\n       )  A ");
    	sbQuery.append("\n UNION ALL ");
    	sbQuery.append("\n SELECT  '5'                                      			GBN	");                                                                         
    	sbQuery.append("\n        ,'출발위반누적'                                			GBN_NM ");             
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_1 , 0, 0 , 1 )))       COURSE1_1 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_2 , 0, 0 , 1 )))       COURSE1_2 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_3 , 0, 0 , 1 )))       COURSE1_3 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_4 , 0, 0 , 1 )))       COURSE1_4 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_5 , 0, 0 , 1 )))       COURSE1_5 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_6 , 0, 0 , 1 )))       COURSE1_6 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_7 , 0, 0 , 1 )))       COURSE1_7 ");
    	sbQuery.append("\n        ,TO_CHAR(SUM(DECODE( A.COURSE1_8 , 0, 0 , 1 )))       COURSE1_8 ");
    	sbQuery.append("\n FROM  ( SELECT  CASE WHEN A.ENTRY_COURSE = 1   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_1 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 2   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_2 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 3   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_3 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 4   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_4 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 5   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_5 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 6   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_6 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 7   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_7 ");
    	sbQuery.append("\n                ,CASE WHEN A.ENTRY_COURSE = 8   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_8 ");
    	sbQuery.append("\n         FROM    TBEB_ORGAN A, ");
    	sbQuery.append("\n               ( SELECT * ");
    	sbQuery.append("\n   	  	       FROM   TBED_RACE_VOIL_ACT ");                                                        
    	sbQuery.append("\n   	  	       WHERE  VOIL_CD   IN ('110','120') ) B, ");
    	sbQuery.append("\n 	             ( SELECT A.RACER_NO  RACER_NO ");
		sbQuery.append("\n                 FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B ");
		sbQuery.append("\n                 WHERE  A.RACER_NO = B.RACER_NO ");
		sbQuery.append("\n 	               AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO) ");
		sbQuery.append("\n 	               AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))) C ");
        sbQuery.append("\n    	   WHERE A.STND_YEAR = ? ");
    	sbQuery.append("\n         AND A.MBR_CD    = ? ");
    	sbQuery.append("\n         AND A.STND_YEAR = B.STND_YEAR(+) ");
    	sbQuery.append("\n         AND A.MBR_CD    = B.MBR_CD(+) ");
    	sbQuery.append("\n         AND A.TMS       = B.TMS(+) ");
    	sbQuery.append("\n         AND A.DAY_ORD   = B.DAY_ORD(+) ");
    	sbQuery.append("\n         AND A.RACE_NO   = B.RACE_NO(+) ");
    	sbQuery.append("\n         AND A.RACER_NO  = B.RACER_NO(+) ");
    	sbQuery.append("\n 		   AND A.RACER_GRD_CD   = NVL(?,A.RACER_GRD_CD) ");
		sbQuery.append("\n 	       AND A.RACER_NO  	   = C.RACER_NO ");
    	sbQuery.append("\n       )  A ");
    	sbQuery.append("\n UNION ALL ");
    	sbQuery.append("\n SELECT  A.GBN      GBN ");
    	sbQuery.append("\n        ,A.GBN_NM   GBN_NM ");
    	sbQuery.append("\n		  ,TO_CHAR(ROUND(( A.COURSE1_1 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_1 ");	
    	sbQuery.append("\n	      ,TO_CHAR(ROUND(( A.COURSE1_2 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_2 ");
    	sbQuery.append("\n	      ,TO_CHAR(ROUND(( A.COURSE1_3 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_3 ");	
    	sbQuery.append("\n	      ,TO_CHAR(ROUND(( A.COURSE1_4 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_4 ");	
    	sbQuery.append("\n	      ,TO_CHAR(ROUND(( A.COURSE1_5 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_5 ");	
    	sbQuery.append("\n	      ,TO_CHAR(ROUND(( A.COURSE1_6 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_6 ");	
    	sbQuery.append("\n	      ,TO_CHAR(ROUND(( A.COURSE1_7 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_7 ");	
    	sbQuery.append("\n	      ,TO_CHAR(ROUND(( A.COURSE1_8 / DECODE((COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ),0,1,(COURSE1_1 + COURSE1_2 + COURSE1_3 + COURSE1_4 + COURSE1_5 + COURSE1_6 + COURSE1_7 + COURSE1_8 ))) * 100,2)) COURSE1_8 ");	
    	sbQuery.append("\n FROM  ( SELECT  '6'                                    	  	GBN ");
    	sbQuery.append("\n                ,'비율(%)'                                	    GBN_NM ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_1 , 0, 0 , 1 ))        COURSE1_1 ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_2 , 0, 0 , 1 ))        COURSE1_2 ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_3 , 0, 0 , 1 ))        COURSE1_3 ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_4 , 0, 0 , 1 ))        COURSE1_4 ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_5 , 0, 0 , 1 ))        COURSE1_5 ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_6 , 0, 0 , 1 ))        COURSE1_6 ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_7 , 0, 0 , 1 ))        COURSE1_7 ");
    	sbQuery.append("\n                ,SUM(DECODE( A.COURSE1_8 , 0, 0 , 1 ))        COURSE1_8 ");
    	sbQuery.append("\n         FROM  ( SELECT  CASE WHEN A.ENTRY_COURSE = 1   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_1 ");
    	sbQuery.append("\n                        ,CASE WHEN A.ENTRY_COURSE = 2   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_2 ");
    	sbQuery.append("\n                        ,CASE WHEN A.ENTRY_COURSE = 3   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_3 ");
    	sbQuery.append("\n                        ,CASE WHEN A.ENTRY_COURSE = 4   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_4 ");
    	sbQuery.append("\n                        ,CASE WHEN A.ENTRY_COURSE = 5   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_5 ");
    	sbQuery.append("\n                        ,CASE WHEN A.ENTRY_COURSE = 6   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_6 ");
    	sbQuery.append("\n                        ,CASE WHEN A.ENTRY_COURSE = 7   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_7 ");
    	sbQuery.append("\n                        ,CASE WHEN A.ENTRY_COURSE = 8   AND (B.VOIL_CD = '110' OR B.VOIL_CD = '120') THEN 1 ELSE 0  END COURSE1_8 ");
    	sbQuery.append("\n                 FROM    TBEB_ORGAN A, ");
    	sbQuery.append("\n                       ( SELECT * ");
    	sbQuery.append("\n   	  	               FROM TBED_RACE_VOIL_ACT ");                                                                  
    	sbQuery.append("\n   	  	               WHERE VOIL_CD   IN ('110','120') ) B, ");
    	sbQuery.append("\n 	                     ( SELECT A.RACER_NO  RACER_NO ");
		sbQuery.append("\n                         FROM  TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B ");
		sbQuery.append("\n 	                       WHERE  A.RACER_NO = B.RACER_NO ");
		sbQuery.append("\n                         AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO) ");
		sbQuery.append("\n                         AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))) C ");
       sbQuery.append("\n    	           WHERE A.STND_YEAR = ? ");
    	sbQuery.append("\n                 AND A.MBR_CD    = ? ");
    	sbQuery.append("\n                 AND A.STND_YEAR = B.STND_YEAR(+) ");
    	sbQuery.append("\n                 AND A.MBR_CD    = B.MBR_CD(+) ");
    	sbQuery.append("\n                 AND A.TMS       = B.TMS(+) ");
    	sbQuery.append("\n                 AND A.DAY_ORD   = B.DAY_ORD(+) ");
    	sbQuery.append("\n                 AND A.RACE_NO   = B.RACE_NO(+) ");
    	sbQuery.append("\n                 AND A.RACER_NO  = B.RACER_NO(+) ");
    	sbQuery.append("\n 		           AND A.RACER_GRD_CD   = NVL(?,A.RACER_GRD_CD) ");
		sbQuery.append("\n 	               AND A.RACER_NO  	   = C.RACER_NO ");
    	sbQuery.append("\n               )  A ");
    	sbQuery.append("\n       ) A ");
    	sbQuery.append("\n ORDER BY GBN,GBN_NM ");
    	    	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("FROMDT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TODT          		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
         
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("FROMDT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TODT          		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
        
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
             
        PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String    sResultKey = "dsOutStatTime3";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
        
        getMaxRaceRegNo(ctx);
    }
    
    private void getMaxRaceRegNo(PosContext ctx){
    	StringBuffer sbQuery = new StringBuffer();
    	sbQuery.append(" 	SELECT  MAX(A.RACE_REG_NO) MAX_RACE_REG_NO_3			                       "); 
    	sbQuery.append(" 	FROM    TBEB_ORGAN A                                                       ");                    
    	sbQuery.append(" 		 WHERE A.STND_YEAR = ?                                                 ");          
    	sbQuery.append(" 		   AND A.MBR_CD    = ?                                                 ");         
    	sbQuery.append(" 		   AND A.RACE_DAY  BETWEEN ? AND ?                                     ");         
    	sbQuery.append(" 		   AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                         ");
    	
    	PosParameter param = new PosParameter();
    	int i = 0;
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("FROMDT         		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("TODT          		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String sResultKey = "MAX_RACE_REG_NO_3";
    	String sDefault = "0";
        ctx.put(sResultKey, Util.nullToStr((String)rowset.getAllRow()[0].getAttribute(sResultKey), sDefault));
        Util.addResultKey(ctx, sResultKey);
    }

}


