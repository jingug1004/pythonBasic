/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04060010.activity.SearchRaceVoilActCount.java
 * 파일설명		: 위반건수 집계현황
 * 작성자			: 정의태
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04060010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRaceVoilActCount extends SnisActivity {
    public SearchRaceVoilActCount()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	SearchRaceCountVoilAct(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchRaceCountVoilAct(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
    	
    	                                                                     	

if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항별
			sbQuery.append(" SELECT   DECODE(B.VOIL_JO,'69','-',B.VOIL_JO)												ITEM_NM					");		
			sbQuery.append("\n 		,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD) 								CNT                     ");
			sbQuery.append("\n         ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )  	GRAVITY                 ");
			sbQuery.append("\n         ,A.TOTAL                                       									TOT                     ");
			sbQuery.append("\n         ,B.FIRST_DAY_ORD                                        							FIRST_DAY_ORD_CNT       ");
			sbQuery.append("\n         ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )          								FIRST_DAY_ORD_GRAVITY   ");
			sbQuery.append("\n         ,B.SECOND_DAY_ORD                                     							SECOND_DAY_ORD_CNT      ");
			sbQuery.append("\n         ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)   									SECOND_DAY_ORD_GRAVITY  ");
			sbQuery.append("\n         ,B.THIRD_DAY_ORD                                        							THIRD_DAY_ORD_CNT       ");
			sbQuery.append("\n         ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)  										THIRD_DAY_ORD_GRAVITY   ");
			sbQuery.append("\n FROM                                                                                                             ");
			sbQuery.append("\n 		   (SELECT  COUNT(A.RACE_NO) 				 TOTAL                                                          ");
			sbQuery.append("\n 		  	  FROM  TBED_RACE_VOIL_ACT A                                                                            ");
			sbQuery.append("\n 		  	 WHERE  A.STND_YEAR   = ?                                                                           	");
			sbQuery.append("\n 			   AND 	A.MBR_CD      = ?                                                                            	");
			sbQuery.append("\n 			   AND 	A.RACE_DAY    BETWEEN ? AND ?                                                              		");
		
		if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		    sbQuery.append("\n   		   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')                                      		");
		}
			sbQuery.append("\n 			 ) A,                                                                                                   ");
			sbQuery.append("\n 		   (SELECT  A.VOIL_JO						 VOIL_JO                                                        ");
			sbQuery.append("\n 		            ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                                  ");
			sbQuery.append("\n 		            ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                                 ");
			sbQuery.append("\n 		            ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                                  ");
			sbQuery.append("\n 		  	  FROM  TBED_RACE_VOIL_ACT A                                                                            ");
			sbQuery.append("\n 		  	 WHERE  A.STND_YEAR   = ?                                                                          		");
			sbQuery.append("\n 			   AND 	A.MBR_CD      = ?                                                                           	");
			sbQuery.append("\n 			   AND 	A.RACE_DAY    BETWEEN ? AND ?                                                             		");
		if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
			sbQuery.append("\n   		   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')                                  			");
		}
			sbQuery.append("\n 			  GROUP BY   A.VOIL_JO ) B                                                                              ");
			
       	
} else if ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {             
 //판정별
			sbQuery.append(" SELECT     B.VOIL_CD																		ITEM_NM					");		
			sbQuery.append("\n 		   ,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD) 								CNT                     ");
			sbQuery.append("\n         ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )  	GRAVITY                 ");
			sbQuery.append("\n         ,A.TOTAL                                       									TOT                     ");
			sbQuery.append("\n         ,B.FIRST_DAY_ORD                                        							FIRST_DAY_ORD_CNT       ");
			sbQuery.append("\n         ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )          								FIRST_DAY_ORD_GRAVITY   ");
			sbQuery.append("\n         ,B.SECOND_DAY_ORD                                     							SECOND_DAY_ORD_CNT      ");
			sbQuery.append("\n         ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)   									SECOND_DAY_ORD_GRAVITY  ");
			sbQuery.append("\n         ,B.THIRD_DAY_ORD                                        							THIRD_DAY_ORD_CNT       ");
			sbQuery.append("\n         ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)  										THIRD_DAY_ORD_GRAVITY   ");
			sbQuery.append("\n FROM                                                                                                             ");
			sbQuery.append("\n 		   (SELECT  COUNT(A.RACE_NO) 				 TOTAL                                                          ");
			sbQuery.append("\n 		  	  FROM  TBED_RACE_VOIL_ACT A                                                                            ");
			sbQuery.append("\n 		  	 WHERE  A.STND_YEAR   = ?                                                                           	");
			sbQuery.append("\n 			   AND 	A.MBR_CD      = ?                                                                            	");
			sbQuery.append("\n 			   AND 	A.RACE_DAY    BETWEEN ? AND ?                                                              		");
		
		if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		    sbQuery.append("\n   		   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')                                      		");
		}
			sbQuery.append("\n 			 ) A,                                                                                                   ");
			sbQuery.append("\n 		   (SELECT  (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) VOIL_CD             ");
			sbQuery.append("\n 		            ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                                  ");
			sbQuery.append("\n 		            ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                                 ");
			sbQuery.append("\n 		            ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                                  ");
			sbQuery.append("\n 		  	  FROM  TBED_RACE_VOIL_ACT A                                                                            ");
			sbQuery.append("\n 		  	 WHERE  A.STND_YEAR   = ?                                                                          		");
			sbQuery.append("\n 			   AND 	A.MBR_CD      = ?                                                                           	");
			sbQuery.append("\n 			   AND 	A.RACE_DAY    BETWEEN ? AND ?                                                             		");
		if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
			sbQuery.append("\n   		   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')                      						");
		}
			sbQuery.append("\n 			  GROUP BY   A.VOIL_CD ) B                                                                              ");
} else if ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {             
	 //회차별	
			sbQuery.append("    SELECT    B.TMS || '회차'                       ITEM_NM    ");
			sbQuery.append("\n          ,B.RACE_NO                             CNT        ");
			sbQuery.append("\n          ,ROUND( (B.RACE_NO / A.TOTAL)*100 ,2 ) GRAVITY    ");
			sbQuery.append("\n  FROM                                                      ");
			sbQuery.append("\n            (SELECT  COUNT(A.RACE_NO)            TOTAL      ");
			sbQuery.append("\n               FROM  TBED_RACE_VOIL_ACT A                   ");
			sbQuery.append("\n              WHERE  A.STND_YEAR   = ?                 	  ");
			sbQuery.append("\n                AND  A.MBR_CD      = ?                      ");
			sbQuery.append("\n                AND  A.RACE_DAY    BETWEEN ? AND ?          ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
			    sbQuery.append("\n   		  AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')     ");
}
			sbQuery.append("\n            ) A,                                            ");
			sbQuery.append("\n  		   (SELECT  TMS                         TMS       ");
			sbQuery.append("\n  		            ,COUNT(A.RACE_NO)           RACE_NO   ");
			sbQuery.append("\n  		  	  FROM  TBED_RACE_VOIL_ACT A                  ");
			sbQuery.append("\n  		  	 WHERE  A.STND_YEAR   = ?                	  ");
			sbQuery.append("\n  			   AND 	A.MBR_CD      = ?                     ");
			sbQuery.append("\n  			   AND 	A.RACE_DAY    BETWEEN ? AND ?         ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
			sbQuery.append("\n   			   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')   ");
}			
			sbQuery.append("\n                GROUP BY   A.TMS ) B                        ");

} else if ( "4".equals(ctx.get("SEARCH_COND".trim()))  ) {             
	 //장소별	
	sbQuery.append("   SELECT    B.CIRCUIT_CNT_CD || B.ACDNT_LOC_CD                                                  ITEM_NM                           ");
	sbQuery.append("\n           ,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)                                 CNT                               ");
	sbQuery.append("\n           ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )      GRAVITY                           ");
	sbQuery.append("\n           ,A.TOTAL                                                                            TOT                               ");
	sbQuery.append("\n           ,B.FIRST_DAY_ORD                                                                    FIRST_DAY_ORD_CNT                 ");
	sbQuery.append("\n           ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )                                          FIRST_DAY_ORD_GRAVITY             ");
	sbQuery.append("\n           ,B.SECOND_DAY_ORD                                                                   SECOND_DAY_ORD_CNT                ");
	sbQuery.append("\n           ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)                                         SECOND_DAY_ORD_GRAVITY            ");
	sbQuery.append("\n           ,B.THIRD_DAY_ORD                                                                    THIRD_DAY_ORD_CNT                 ");
	sbQuery.append("\n           ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)                                          THIRD_DAY_ORD_GRAVITY             ");
	sbQuery.append("\n   FROM                                                                                                                          ");
	sbQuery.append("\n                (SELECT  COUNT(A.RACE_NO)                  TOTAL                                                                 ");
	sbQuery.append("\n                   FROM  TBED_RACE_VOIL_ACT A                                                                                    ");
	sbQuery.append("\n                  WHERE  A.STND_YEAR   = ?                                                                                 	   ");
	sbQuery.append("\n                  AND    A.MBR_CD      = ?                                                                                  	   ");
	sbQuery.append("\n                  AND    A.RACE_DAY    BETWEEN ? AND ?                                                                       	   ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
	    sbQuery.append("\n   		    AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')     ");
}
	sbQuery.append("\n                   ) A,                                                                                                          ");
	sbQuery.append("\n              (  SELECT  (SELECT  CD_NM  FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00045'  AND CD = A.CIRCUIT_CNT_CD)  CIRCUIT_CNT_CD   ");
	sbQuery.append("\n                        ,(SELECT  CD_NM  FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD)     ACDNT_LOC_CD     ");
	sbQuery.append("\n                        ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                                           ");
	sbQuery.append("\n                        ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                                          ");
	sbQuery.append("\n                        ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                                           ");
	sbQuery.append("\n                   FROM  TBED_RACE_VOIL_ACT A                                                                                    ");
	sbQuery.append("\n                  WHERE  A.STND_YEAR   = ?                                                                                       ");
	sbQuery.append("\n                  AND    A.MBR_CD      = ?                                                                                       ");
	sbQuery.append("\n                  AND    A.RACE_DAY    BETWEEN ? AND ?                                                                           ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		sbQuery.append("\n   			   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')      											   ");
}	
	sbQuery.append("\n                 GROUP BY     A.CIRCUIT_CNT_CD  , A.ACDNT_LOC_CD  ) B                                                            ");

} else if ( "5".equals(ctx.get("SEARCH_COND".trim()))  ) {             
//등급별	
	sbQuery.append("   SELECT    B.RACER_GRD_CD                                                                    ITEM_NM               ");
	sbQuery.append("\n         ,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)                                 CNT                   ");
	sbQuery.append("\n         ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )      GRAVITY               ");
	sbQuery.append("\n         ,A.TOTAL                                                                            TOT                   ");
	sbQuery.append("\n         ,B.FIRST_DAY_ORD                                                                    FIRST_DAY_ORD_CNT     ");
	sbQuery.append("\n         ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )                                          FIRST_DAY_ORD_GRAVITY ");
	sbQuery.append("\n         ,B.SECOND_DAY_ORD                                                                   SECOND_DAY_ORD_CNT    ");
	sbQuery.append("\n         ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)                                         SECOND_DAY_ORD_GRAVITY");
	sbQuery.append("\n         ,B.THIRD_DAY_ORD                                                                    THIRD_DAY_ORD_CNT     ");
	sbQuery.append("\n         ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)                                          THIRD_DAY_ORD_GRAVITY ");
	sbQuery.append("\n FROM                                                                                                              ");
	sbQuery.append("\n              (SELECT  COUNT(A.RACE_NO)                  TOTAL                                                     ");
	sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A                                                                        ");
	sbQuery.append("\n                WHERE  A.STND_YEAR   = ?                                                                           ");
	sbQuery.append("\n                AND    A.MBR_CD      = ?                                                                           ");
	sbQuery.append("\n                AND    A.RACE_DAY    BETWEEN ? AND ?                                                               ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
	    sbQuery.append("\n   		   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')  ");
}	
	sbQuery.append("\n                 ) A,                                                                                              ");
	sbQuery.append("\n            (  SELECT  B.RACER_GRD_CD     RACER_GRD_CD                                                             ");
	sbQuery.append("\n                      ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                               ");
	sbQuery.append("\n                      ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                              ");
	sbQuery.append("\n                      ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                               ");
	sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A, TBEC_RACER_MASTER B                                                   ");
	sbQuery.append("\n                WHERE  A.RACER_NO    = B.RACER_NO                                                                  ");
	sbQuery.append("\n                AND    A.STND_YEAR   = ?                                                                           ");
	sbQuery.append("\n                AND    A.MBR_CD      = ?                                                                           ");
	sbQuery.append("\n                AND    A.RACE_DAY    BETWEEN ? AND ?                                                                       ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		sbQuery.append("\n   		  AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')      								 	 ");
}	
	sbQuery.append("\n               GROUP BY     B.RACER_GRD_CD  ) B                                                                    ");

} else if ( "6".equals(ctx.get("SEARCH_COND".trim()))  ) {             
//	기수별	
		sbQuery.append("   SELECT    B.RACER_PERIO_NO                                                                  ITEM_NM               ");
		sbQuery.append("\n         ,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)                                 CNT                   ");
		sbQuery.append("\n         ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )      GRAVITY               ");
		sbQuery.append("\n         ,A.TOTAL                                                                            TOT                   ");
		sbQuery.append("\n         ,B.FIRST_DAY_ORD                                                                    FIRST_DAY_ORD_CNT     ");
		sbQuery.append("\n         ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )                                          FIRST_DAY_ORD_GRAVITY ");
		sbQuery.append("\n         ,B.SECOND_DAY_ORD                                                                   SECOND_DAY_ORD_CNT    ");
		sbQuery.append("\n         ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)                                         SECOND_DAY_ORD_GRAVITY");
		sbQuery.append("\n         ,B.THIRD_DAY_ORD                                                                    THIRD_DAY_ORD_CNT     ");
		sbQuery.append("\n         ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)                                          THIRD_DAY_ORD_GRAVITY ");
		sbQuery.append("\n FROM                                                                                                              ");
		sbQuery.append("\n              (SELECT  COUNT(A.RACE_NO)                  TOTAL                                                     ");
		sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A                                                                        ");
		sbQuery.append("\n                WHERE  A.STND_YEAR   = ?                                                                           ");
		sbQuery.append("\n                AND    A.MBR_CD      = ?                                                                           ");
		sbQuery.append("\n                AND    A.RACE_DAY    BETWEEN ? AND ?                                                               ");
	if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		    sbQuery.append("\n   		   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')     									 ");
	}	
		sbQuery.append("\n                 ) A,                                                                                              ");
		sbQuery.append("\n            (  SELECT  B.RACER_PERIO_NO     RACER_PERIO_NO                                                         ");
		sbQuery.append("\n                      ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                               ");
		sbQuery.append("\n                      ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                              ");
		sbQuery.append("\n                      ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                               ");
		sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A, TBEC_RACER_MASTER B                                                   ");
		sbQuery.append("\n                WHERE  A.RACER_NO    = B.RACER_NO                                                                  ");
		sbQuery.append("\n                AND    A.STND_YEAR   = ?                                                                           ");
		sbQuery.append("\n                AND    A.MBR_CD      = ?                                                                           ");
		sbQuery.append("\n                AND    A.RACE_DAY    BETWEEN ? AND ?                                                               ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		sbQuery.append("\n   			   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')      									 ");
}	
		sbQuery.append("\n               GROUP BY     B.RACER_PERIO_NO  ) B                                                                  ");

} else if ( "7".equals(ctx.get("SEARCH_COND".trim()))  ) {             
//	선수별		
	sbQuery.append("   SELECT    B.RACER_NM                                                                      ITEM_NM                ");
	 sbQuery.append("\n         ,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)                               CNT                    ");
	 sbQuery.append("\n         ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )    GRAVITY                ");
	 sbQuery.append("\n         ,A.TOTAL                                                                          TOT                    ");
	 sbQuery.append("\n         ,B.FIRST_DAY_ORD                                                                  FIRST_DAY_ORD_CNT      ");
	 sbQuery.append("\n         ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )                                        FIRST_DAY_ORD_GRAVITY  ");
	 sbQuery.append("\n         ,B.SECOND_DAY_ORD                                                                 SECOND_DAY_ORD_CNT     ");
	 sbQuery.append("\n         ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)                                       SECOND_DAY_ORD_GRAVITY ");
	 sbQuery.append("\n         ,B.THIRD_DAY_ORD                                                                  THIRD_DAY_ORD_CNT      ");
	 sbQuery.append("\n         ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)                                        THIRD_DAY_ORD_GRAVITY  ");
	 sbQuery.append("\n FROM                                                                                                             ");
	 sbQuery.append("\n            (SELECT  COUNT(A.RACE_NO)                  TOTAL                                                      ");
	 sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A                                                                       ");
	 sbQuery.append("\n                WHERE  A.STND_YEAR  = ?                                                                      	 ");
	 sbQuery.append("\n                AND    A.MBR_CD     = ?                                                                     	 	 ");
	 sbQuery.append("\n                AND    A.RACE_DAY   BETWEEN ? AND ?                                                               ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
	 sbQuery.append("\n   			   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')      									 ");
}	 
	 sbQuery.append("\n               ) A,                                                                                               ");
	 sbQuery.append("\n            (SELECT  (SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO = A.RACER_NO) RACER_NM                ");
	 sbQuery.append("\n                     ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                               ");
	 sbQuery.append("\n                     ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                              ");
	 sbQuery.append("\n                     ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                               ");
	 sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A                                                                       ");
	 sbQuery.append("\n                WHERE  A.STND_YEAR  = ?                                                                	     	 ");
	 sbQuery.append("\n                AND    A.MBR_CD     = ?                                                                     	 	 ");
	 sbQuery.append("\n                AND    A.RACE_DAY   BETWEEN ? AND ?                                                               ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
	sbQuery.append("\n   			   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')      									 ");
}
	 sbQuery.append("\n                GROUP BY   A.RACER_NO ) B                                                                         ");
	 sbQuery.append("\n ORDER BY B.RACER_NM																								 ");

} else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {             
//	일차별		
	sbQuery.append("   SELECT    B.TMS                                                                      	  ITEM_NM                ");
	 sbQuery.append("\n         ,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)                               CNT                    ");
	 sbQuery.append("\n         ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )    GRAVITY                ");
	 sbQuery.append("\n         ,A.TOTAL                                                                          TOT                    ");
	 sbQuery.append("\n         ,B.FIRST_DAY_ORD                                                                  FIRST_DAY_ORD_CNT      ");
	 sbQuery.append("\n         ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )                                        FIRST_DAY_ORD_GRAVITY  ");
	 sbQuery.append("\n         ,B.SECOND_DAY_ORD                                                                 SECOND_DAY_ORD_CNT     ");
	 sbQuery.append("\n         ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)                                       SECOND_DAY_ORD_GRAVITY ");
	 sbQuery.append("\n         ,B.THIRD_DAY_ORD                                                                  THIRD_DAY_ORD_CNT      ");
	 sbQuery.append("\n         ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)                                        THIRD_DAY_ORD_GRAVITY  ");
	 sbQuery.append("\n FROM                                                                                                             ");
	 sbQuery.append("\n            (SELECT  COUNT(A.RACE_NO)                  TOTAL                                                      ");
	 sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A                                                                       ");
	 sbQuery.append("\n                WHERE  A.STND_YEAR  = ?                                          	                           	 ");
	 sbQuery.append("\n                AND    A.MBR_CD     = ?                                                                     	 	 ");
	 sbQuery.append("\n                AND    A.RACE_DAY   BETWEEN ? AND ?                                                               ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
	 sbQuery.append("\n   			   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')      									 ");
}	 
	 sbQuery.append("\n               ) A,                                                                                               ");
	 sbQuery.append("\n            (SELECT  A.TMS TMS                																	 ");
	 sbQuery.append("\n                     ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                               ");
	 sbQuery.append("\n                     ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                              ");
	 sbQuery.append("\n                     ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                               ");
	 sbQuery.append("\n                 FROM  TBED_RACE_VOIL_ACT A                                                                       ");
	 sbQuery.append("\n                WHERE  A.STND_YEAR  = ?                                                            	         	 ");
	 sbQuery.append("\n                AND    A.MBR_CD     = ?                                                                     	 	 ");
	 sbQuery.append("\n                AND    A.RACE_DAY   BETWEEN ? AND ?                                                               ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
	sbQuery.append("\n   			   AND  A.VOIL_CD      NOT IN ('996','998','999','211','221')   									 ");
}
	 sbQuery.append("\n                GROUP BY   A.TMS ) B                                                                         	 ");
	 sbQuery.append("\n ORDER BY B.TMS																								 	 ");

} else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {             
//	사고별		 	 
	sbQuery.append("\n SELECT    B.ACDNT_TPE_CD                                              						ITEM_NM        				");
	sbQuery.append("\n          ,(B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)                                 CNT                         ");
	sbQuery.append("\n          ,ROUND( ((B.FIRST_DAY_ORD+B.SECOND_DAY_ORD+B.THIRD_DAY_ORD)/ A.TOTAL)*100 ,2 )      GRAVITY                     ");
	sbQuery.append("\n          ,A.TOTAL                                                                            TOT                         ");
	sbQuery.append("\n          ,B.FIRST_DAY_ORD                                                                    FIRST_DAY_ORD_CNT           ");
	sbQuery.append("\n          ,ROUND( (B.FIRST_DAY_ORD/A.TOTAL)*100 ,2 )                                          FIRST_DAY_ORD_GRAVITY       ");
	sbQuery.append("\n          ,B.SECOND_DAY_ORD                                                                   SECOND_DAY_ORD_CNT          ");
	sbQuery.append("\n          ,ROUND((B.SECOND_DAY_ORD/A.TOTAL) * 100 ,2)                                         SECOND_DAY_ORD_GRAVITY      ");
	sbQuery.append("\n          ,B.THIRD_DAY_ORD                                                                    THIRD_DAY_ORD_CNT           ");
	sbQuery.append("\n          ,ROUND((B.THIRD_DAY_ORD/A.TOTAL) * 100 ,2)                                          THIRD_DAY_ORD_GRAVITY       ");
	sbQuery.append("\n  FROM                                                                                                                    ");
	sbQuery.append("\n               (SELECT  COUNT(A.RACE_NO)                  TOTAL                                                           ");
	sbQuery.append("\n                  FROM  TBED_RACE_ACDNT_STTS A                                                                            ");
	sbQuery.append("\n                 WHERE  A.STND_YEAR   = ?                                                                           	    ");
	sbQuery.append("\n                 AND    A.MBR_CD      = ?                                                                                 ");
	sbQuery.append("\n                 AND    A.RACE_DAY    BETWEEN ? AND ?                                                                     ");
	sbQuery.append("\n                  ) A,                                                                                                    ");
	sbQuery.append("\n             (  SELECT  (SELECT  CD_NM  FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00062'  AND CD = A.ACDNT_TPE_CD)  ACDNT_TPE_CD ");
	sbQuery.append("\n                       ,SUM( DECODE(A.DAY_ORD,'1',1,0)) FIRST_DAY_ORD                                                     ");
	sbQuery.append("\n                       ,SUM( DECODE(A.DAY_ORD,'2',1,0)) SECOND_DAY_ORD                                                    ");
	sbQuery.append("\n                       ,SUM( DECODE(A.DAY_ORD,'3',1,0)) THIRD_DAY_ORD                                                     ");
	sbQuery.append("\n                  FROM  TBED_RACE_ACDNT_STTS A                                                                            ");
	sbQuery.append("\n                 WHERE  A.STND_YEAR   = ?                                                                            		");
	sbQuery.append("\n                 AND    A.MBR_CD      = ?                                                                             	");
	sbQuery.append("\n                 AND    A.RACE_DAY    BETWEEN ? AND ?                                                                     ");
	sbQuery.append("\n                GROUP BY   A.ACDNT_TPE_CD  ) B                                                                            ");	
//마지막 END IF
}
 
		PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS_FROM          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS_TO            ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS_FROM          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS_TO            ".trim()));
       
        if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
           
        	param.setWhereClauseParameter(i++, ctx.get("SEARCH_VALUE      ".trim()));
        
        }
         	       	
  
        
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutRace";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    }

}