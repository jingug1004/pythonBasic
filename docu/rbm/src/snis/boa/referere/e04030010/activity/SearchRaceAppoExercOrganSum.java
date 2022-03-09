/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04030010.activity.SearchRaceAppoExercOrganSum.java
 * 파일설명		: 소개항주측정현황 조회
 * 작성자			: 정의태
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04030010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRaceAppoExercOrganSum extends SnisActivity {
    public SearchRaceAppoExercOrganSum()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	SearchAppoExercOrganRaceSum(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /* 2014.5.29 3일차도 출주표 순서대로 조회가 되도록 수정했음(신재선)..OR조건에 3일 추가
     * 아래 루틴을 보면 쓸데없이 복잡하다. 0일차만 다르게 하고 나머지는 같은 SQL을 사용하면 될 거 같다. */
    protected void SearchAppoExercOrganRaceSum(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
 
		if  ( "1".equals(ctx.get("DAY_ORD".trim())) || "2".equals(ctx.get("DAY_ORD".trim()))  || "3".equals(ctx.get("DAY_ORD".trim()))) {
		    	sbQuery.append("   SELECT  B.STND_YEAR      	STND_YEAR												");
		    	sbQuery.append("\n        ,B.MBR_CD         	MBR_CD                                                  ");
		    	sbQuery.append("\n        ,B.TMS            	TMS                                                     ");
		    	sbQuery.append("\n        ,B.DAY_ORD        	DAY_ORD                                                 ");
		    	sbQuery.append("\n        ,B.RACE_NO        	RACE_NO                                                 ");
		    	sbQuery.append("\n        ,B.RACE_REG_NO    	RACE_REG_NO                                             ");
		    	sbQuery.append("\n        ,B.RACER_NO       	RACER_NO                                                ");
		    	sbQuery.append("\n        ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE RACER_NO= B.RACER_NO) RACER_NM   ");
		    	sbQuery.append("\n        ,B.MOT_NO         	MOT_NO                                                  ");
		    	sbQuery.append("\n        ,B.BOAT_NO        	BOAT_NO                                                 ");
		    	sbQuery.append("\n        ,A.RUN_TM_0       	RUN_TM_0                                                ");
		    	sbQuery.append("\n        ,A.RUN_TM_1       	RUN_TM_1                                                ");
		    	sbQuery.append("\n        ,NVL(A.INTRO_RUN_TM_1_1,A.INTRO_RUN_TM_2_1) 		   	INTRO_RUN_TM_1_1		");
		    	sbQuery.append("\n        ,DECODE(A.INTRO_RUN_TM_1_1,'','',A.INTRO_RUN_TM_2_1) 	INTRO_RUN_TM_2_1		");
		    	sbQuery.append("\n        ,A.RUN_TM_2         RUN_TM_2                                                  ");
		    	sbQuery.append("\n        ,NVL(A.INTRO_RUN_TM_1_2,A.INTRO_RUN_TM_2_2)			INTRO_RUN_TM_1_2		");
		    	sbQuery.append("\n        ,DECODE(A.INTRO_RUN_TM_1_2,'','',A.INTRO_RUN_TM_2_2)  INTRO_RUN_TM_2_2		");
		    	sbQuery.append("\n        ,A.RUN_TM_3         RUN_TM_3                                                  ");
		    	sbQuery.append("\n        ,A.OST_TM_0         OST_TM_0                                                  ");
		    	sbQuery.append("\n        ,A.OST_TM_1         OST_TM_1                                                  ");
		    	sbQuery.append("\n        ,A.OST_TM_2         OST_TM_2                                                  ");
		    	sbQuery.append("\n        ,A.OST_TM_3         OST_TM_3                                                  ");
		    	sbQuery.append("\n        ,NVL(A.INTRO_RUN_TM_1_3,A.INTRO_RUN_TM_2_3)			INTRO_RUN_TM_1_3		");
		    	sbQuery.append("\n        ,DECODE(A.INTRO_RUN_TM_1_3,'','',A.INTRO_RUN_TM_2_3)	INTRO_RUN_TM_2_3		");
		    	sbQuery.append("\n        ,A.FLYING_CNT		  FLYING_CNT    											");
		    	sbQuery.append("\n        ,A.LATE_CNT		  LATE_CNT      											");
		    	sbQuery.append("\n        ,A.RMK              RMK                                                       ");
		    	sbQuery.append("\n        ,A.TOT_AVG          TOT_AVG                                                   ");
		    	sbQuery.append("\n        ,A.TOT_STDDEV       TOT_STDDEV                                                "); 
		    	sbQuery.append("\n   FROM  (																			");
		}   	
		    	sbQuery.append("\n 			SELECT																													"); 			 
		if  ( "0".equals(ctx.get("DAY_ORD".trim()))  ) {
				sbQuery.append("\n 		   A.STND_YEAR      STND_YEAR                                                                                               ");
				sbQuery.append("\n        ,A.MBR_CD         MBR_CD                                                                                               	");
				sbQuery.append("\n        ,A.TMS            TMS                                                                                                  	");
				sbQuery.append("\n        ,A.DAY_ORD        DAY_ORD                                                                                              	");
				sbQuery.append("\n        ,A.RACE_NO        RACE_NO                                                                                              	");
				sbQuery.append("\n        ,A.RACER_NO       RACER_NO                                                                                             	");
				sbQuery.append("\n        ,A.RACER_NM       RACER_NM                                                                                             	");
				sbQuery.append("\n        ,A.MOT_NO         MOT_NO                                                                                               	");
				sbQuery.append("\n        ,A.BOAT_NO        BOAT_NO                                                                                              	");
			
		} else if  ( "1".equals(ctx.get("DAY_ORD".trim()))  ) { 
				sbQuery.append("\n 		   B.STND_YEAR      STND_YEAR                                                                                               ");
				sbQuery.append("\n        ,B.MBR_CD         MBR_CD                                                                                               	");
				sbQuery.append("\n        ,B.TMS            TMS                                                                                                  	");
				sbQuery.append("\n        ,B.DAY_ORD        DAY_ORD                                                                                              	");
				sbQuery.append("\n        ,B.RACE_NO        RACE_NO                                                                                              	");
				sbQuery.append("\n        ,B.RACER_NO       RACER_NO                                                                                             	");
				sbQuery.append("\n        ,B.RACER_NM       RACER_NM                                                                                             	");
				sbQuery.append("\n        ,B.MOT_NO         MOT_NO                                                                                               	");
				sbQuery.append("\n        ,B.BOAT_NO        BOAT_NO                                                                                              	");
			
		} else if  ( "2".equals(ctx.get("DAY_ORD".trim()))  ) {
				sbQuery.append("\n 		   C.STND_YEAR      STND_YEAR                                                                                               ");
				sbQuery.append("\n        ,C.MBR_CD         MBR_CD                                                                                               	");
				sbQuery.append("\n        ,C.TMS            TMS                                                                                                  	");
				sbQuery.append("\n        ,C.DAY_ORD        DAY_ORD                                                                                              	");
				sbQuery.append("\n        ,C.RACE_NO        RACE_NO                                                                                              	");
				sbQuery.append("\n        ,C.RACER_NO       RACER_NO                                                                                             	");
				sbQuery.append("\n        ,C.RACER_NM       RACER_NM                                                                                             	");
				sbQuery.append("\n        ,C.MOT_NO         MOT_NO                                                                                               	");
				sbQuery.append("\n        ,C.BOAT_NO        BOAT_NO                                                                                              	");
		
		} else if  ( "3".equals(ctx.get("DAY_ORD".trim()))  ) {
				sbQuery.append("\n 		   D.STND_YEAR      STND_YEAR                                                                                               ");
				sbQuery.append("\n        ,D.MBR_CD         MBR_CD                                                                                               	");
				sbQuery.append("\n        ,D.TMS            TMS                                                                                                  	");
				sbQuery.append("\n        ,D.DAY_ORD        DAY_ORD                                                                                              	");
				sbQuery.append("\n        ,D.RACE_NO        RACE_NO                                                                                              	");
				sbQuery.append("\n        ,D.RACER_NO       RACER_NO                                                                                             	");
				sbQuery.append("\n        ,D.RACER_NM       RACER_NM                                                                                             	");
				sbQuery.append("\n        ,D.MOT_NO         MOT_NO                                                                                               	");
				sbQuery.append("\n        ,D.BOAT_NO        BOAT_NO                                                                                              	");
			
		}
		    	sbQuery.append("\n        ,TO_CHAR(A.RUN_TM,'9.99')         RUN_TM_0                                                                                ");
		    	sbQuery.append("\n        ,TO_CHAR(A.OST_TM,'99.99')        OST_TM_0                                                                                ");
		    	sbQuery.append("\n 		  ,TO_CHAR(B.RUN_TM,'9.99')         RUN_TM_1                                                                                ");
		    	sbQuery.append("\n 		  ,TO_CHAR(B.OST_TM,'99.99')        OST_TM_1                                                                                ");
		    	sbQuery.append("\n 		  ,TO_CHAR(B.INTRO_RUN_TM_1,'9.99') INTRO_RUN_TM_1_1                                                                        ");
		    	sbQuery.append("\n 		  ,TO_CHAR(B.INTRO_RUN_TM_2,'9.99') INTRO_RUN_TM_2_1                                                                        ");
		    	sbQuery.append("\n 		  ,TO_CHAR(C.RUN_TM,'9.99')         RUN_TM_2                                                                                ");
		    	sbQuery.append("\n 		  ,TO_CHAR(C.OST_TM,'99.99')        OST_TM_2                                                                                ");
		    	sbQuery.append("\n 		  ,TO_CHAR(C.INTRO_RUN_TM_1,'9.99') INTRO_RUN_TM_1_2                                                                        ");
		    	sbQuery.append("\n 		  ,TO_CHAR(C.INTRO_RUN_TM_2,'9.99') INTRO_RUN_TM_2_2                                                                        ");
		    	sbQuery.append("\n 		  ,TO_CHAR(D.RUN_TM,'9.99')         RUN_TM_3                                                                                ");
		    	sbQuery.append("\n 		  ,TO_CHAR(D.OST_TM,'99.99')        OST_TM_3                                                                                ");
		    	sbQuery.append("\n 		  ,TO_CHAR(D.INTRO_RUN_TM_1,'9.99') INTRO_RUN_TM_1_3                                                                        ");
		    	sbQuery.append("\n 		  ,TO_CHAR(D.INTRO_RUN_TM_2,'9.99') INTRO_RUN_TM_2_3                                                                        ");
		    	
		 	 	sbQuery.append("\n        ,NVL(A.FLYING_CNT,0) + NVL(B.FLYING_CNT,0) + NVL(C.FLYING_CNT,0) + NVL(D.FLYING_CNT,0)  FLYING_CNT						");
			 	sbQuery.append("\n 		  ,NVL(A.LATE_CNT,0)   + NVL(B.LATE_CNT,0)   + NVL(C.LATE_CNT,0)   + NVL(D.LATE_CNT,0) LATE_CNT                             ");
				
			 	
		if  ( "0".equals(ctx.get("DAY_ORD".trim()))  ) {
			 	sbQuery.append("\n 		  ,A.RMK			RMK                                                                                                     ");
		} else if  ( "1".equals(ctx.get("DAY_ORD".trim()))  ) {
				sbQuery.append("\n 		  ,B.RMK			RMK                                                                                                     ");
		} else if ( "2".equals(ctx.get("DAY_ORD".trim()))  ) {
				sbQuery.append("\n 		  ,C.RMK			RMK                                                                                                     ");
		} else if ( "3".equals(ctx.get("DAY_ORD".trim()))  ) {		 
		   		sbQuery.append("\n 		  ,D.RMK			RMK                                                                                                     ");
		} 
		 		sbQuery.append("\n        ,''     			TOT_AVG                                                                                           		");
		 		sbQuery.append("\n 		  ,''       		TOT_STDDEV                                                                                              ");
			
		 
		    	sbQuery.append("\n FROM			                                                                                                                    ");
		    	sbQuery.append("\n 	(	  SELECT   A.STND_YEAR      STND_YEAR                                                                                       ");
		    	sbQuery.append("\n 		          ,A.MBR_CD         MBR_CD                                                                                          ");
		    	sbQuery.append("\n 		          ,A.TMS            TMS                                                                                             ");
		    	sbQuery.append("\n 		          ,A.DAY_ORD        DAY_ORD                                                                                         ");
		    	sbQuery.append("\n 		          ,A.RACE_NO        RACE_NO                                                                                         ");
		    	sbQuery.append("\n 		          ,A.RACER_NO       RACER_NO                                                                                        ");
		    	sbQuery.append("\n 		          ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) RACER_NM                                     ");
		    	sbQuery.append("\n 		          ,A.RACE_REG_NO    RACE_REG_NO                                                                                     ");
		    	sbQuery.append("\n 		          ,A.MOT_NO         MOT_NO                                                                                          ");
		    	sbQuery.append("\n 		          ,A.BOAT_NO        BOAT_NO                                                                                         ");
		    	sbQuery.append("\n 		          ,A.RUN_TM         RUN_TM                                                                                          ");
		    	sbQuery.append("\n 		          ,A.OST_TM         OST_TM                                                                                          ");
		    	sbQuery.append("\n 		          ,A.FLYING_CNT     FLYING_CNT                                                                                      ");
		    	sbQuery.append("\n 		          ,A.LATE_CNT       LATE_CNT                                                                                        ");
		    	sbQuery.append("\n 		          ,A.RMK			RMK                                                                                             ");
		    	sbQuery.append("\n 		     FROM  TBEC_APPO_EXERC_ORGAN   A                                                                                        ");
		    	sbQuery.append("\n 		    WHERE A.STND_YEAR        = ?                                                                                      		");
		    	sbQuery.append("\n 		         AND A.MBR_CD        = ?                                                                                        	");
		    	sbQuery.append("\n 		         AND A.TMS           = ?                                                                                           	");
		    	sbQuery.append("\n 		         AND A.DAY_ORD       = 0  ) A,                                                                                      ");
		    	sbQuery.append("\n 	(	 SELECT                                                                                                                     ");
		    	sbQuery.append("\n 				     A.STND_YEAR      STND_YEAR                                                                                     ");
		    	sbQuery.append("\n 			        ,A.MBR_CD         MBR_CD                                                                                        ");
		    	sbQuery.append("\n 			        ,A.TMS            TMS                                                                                           ");
		    	sbQuery.append("\n 			        ,A.DAY_ORD        DAY_ORD                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RACE_NO        RACE_NO                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RACER_NO       RACER_NO                                                                                      ");
		    	sbQuery.append("\n 			        ,A.RACER_NM       RACER_NM                                                                                      ");
		    	sbQuery.append("\n 			        ,A.MOT_NO         MOT_NO                                                                                        ");
		    	sbQuery.append("\n 			        ,A.BOAT_NO        BOAT_NO                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RUN_TM         RUN_TM                                                                                        ");
		    	sbQuery.append("\n 			        ,A.OST_TM         OST_TM                                                                                        ");
		    	sbQuery.append("\n 			        ,SUBSTR(A.INTRO_RUN_TM,2,INSTR(A.INTRO_RUN_TM,'|',2,1) -2) INTRO_RUN_TM_1                                       ");
		    	sbQuery.append("\n 			        ,REPLACE(SUBSTR(A.INTRO_RUN_TM,INSTR(A.INTRO_RUN_TM,'|',2,1),LENGTH(A.INTRO_RUN_TM)),'|','') INTRO_RUN_TM_2     ");
		    	sbQuery.append("\n 			        ,A.FLYING_CNT     FLYING_CNT                                                                                    ");
		    	sbQuery.append("\n 			        ,A.LATE_CNT       LATE_CNT                                                                                      ");
		    	sbQuery.append("\n 			        ,A.RMK			  RMK                                                                                           ");
		    	sbQuery.append("\n 			 FROM ( SELECT   A.STND_YEAR      STND_YEAR                                                                             ");
		    	sbQuery.append("\n 			                ,A.MBR_CD         MBR_CD                                                                                ");
		    	sbQuery.append("\n 			                ,A.TMS            TMS                                                                                   ");
		    	sbQuery.append("\n 			                ,A.DAY_ORD        DAY_ORD                                                                               ");
		    	sbQuery.append("\n 			                ,A.RACE_NO        RACE_NO                                                                               ");
		    	sbQuery.append("\n 			                ,A.RACER_NO       RACER_NO                                                                              ");
		    	sbQuery.append("\n 			                ,A.RACER_NM       RACER_NM                                                                              ");
		    	sbQuery.append("\n 			                ,A.MOT_NO         MOT_NO                                                                                ");
		    	sbQuery.append("\n 			                ,A.BOAT_NO        BOAT_NO                                                                               ");
		    	sbQuery.append("\n 			                ,A.RUN_TM         RUN_TM                                                                                ");
		    	sbQuery.append("\n 			                ,A.OST_TM         OST_TM                                                                                ");
		    	sbQuery.append("\n 			                ,MAX(SYS_CONNECT_BY_PATH(A.INTRO_RUN_TM, '|')) INTRO_RUN_TM                                             ");
		    	sbQuery.append("\n 			                ,A.FLYING_CNT     FLYING_CNT                                                                            ");
		    	sbQuery.append("\n 			                ,A.LATE_CNT       LATE_CNT                                                                              ");
		    	sbQuery.append("\n 			                ,A.RMK			  RMK                                                                                   ");
		    	sbQuery.append("\n 			        FROM (                                                                                                          ");
		    	sbQuery.append("\n 			                   SELECT  A.STND_YEAR      STND_YEAR                                                                   ");
		    	sbQuery.append("\n 			                          ,A.MBR_CD         MBR_CD                                                                      ");
		    	sbQuery.append("\n 			                          ,A.TMS            TMS                                                                         ");
		    	sbQuery.append("\n 			                          ,A.DAY_ORD        DAY_ORD                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RACE_NO        RACE_NO                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RACER_NO       RACER_NO                                                                    ");
		    	sbQuery.append("\n 			                          ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) RACER_NM                 ");
		    	sbQuery.append("\n 			                          ,A.RACE_REG_NO    RACE_REG_NO                                                                 ");
		    	sbQuery.append("\n 			                          ,A.MOT_NO         MOT_NO                                                                      ");
		    	sbQuery.append("\n 			                          ,A.BOAT_NO        BOAT_NO                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RUN_TM         RUN_TM                                                                      ");
		    	sbQuery.append("\n 			                          ,A.OST_TM         OST_TM                                                                      ");
		    	sbQuery.append("\n 			                          ,B.INTRO_RUN_TM   INTRO_RUN_TM                                                                ");
		    	sbQuery.append("\n 			                          ,A.FLYING_CNT     FLYING_CNT                                                                  ");
		    	sbQuery.append("\n 			                          ,A.LATE_CNT       LATE_CNT                                                                    ");
		    	sbQuery.append("\n 			                          ,A.RMK			RMK                                                                         ");
		    	sbQuery.append("\n 			                          , ROW_NUMBER() OVER (PARTITION BY    B.STND_YEAR                                              ");
		    	sbQuery.append("\n 			                                                              ,B.MBR_CD                                                 ");
		    	sbQuery.append("\n 			                                                              ,B.TMS                                                    ");
		    	sbQuery.append("\n 			                                                              ,B.DAY_ORD                                                ");
		    	sbQuery.append("\n 			                                                              ,B.RACER_NO                                               ");
		    	sbQuery.append("\n 			                                                              ORDER BY B.STND_YEAR                                      ");
		    	sbQuery.append("\n 			                                                                      ,B.MBR_CD                                         ");
		    	sbQuery.append("\n 			                                                                      ,B.TMS                                            ");
		    	sbQuery.append("\n 			                                                                      ,B.DAY_ORD                                        ");
		    	sbQuery.append("\n 			                                                                      ,B.RACE_NO                                        ");
		    	sbQuery.append("\n 			                                                                      ,B.RACER_NO) ROW_ID                               ");
		    	sbQuery.append("\n 			                      FROM                                                                                              ");
		    	sbQuery.append("\n 			                            (  SELECT  A.STND_YEAR      STND_YEAR                                                       ");
		    	sbQuery.append("\n 			                                      ,A.MBR_CD         MBR_CD                                                          ");
		    	sbQuery.append("\n 			                                      ,A.TMS            TMS                                                             ");
		    	sbQuery.append("\n 			                                      ,A.DAY_ORD        DAY_ORD                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACE_NO        RACE_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACER_NO       RACER_NO                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RACE_REG_NO    RACE_REG_NO                                                     ");
		    	sbQuery.append("\n 			                                      ,A.MOT_NO         MOT_NO                                                          ");
		    	sbQuery.append("\n 			                                      ,A.BOAT_NO        BOAT_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RUN_TM         RUN_TM                                                          ");
		    	sbQuery.append("\n 			                                      ,A.OST_TM         OST_TM                                                          ");
		    	sbQuery.append("\n 			                                      ,A.FLYING_CNT     FLYING_CNT                                                      ");
		    	sbQuery.append("\n 			                                      ,A.LATE_CNT       LATE_CNT                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RMK			RMK                                                             ");
		    	sbQuery.append("\n 			                                 FROM  TBEC_APPO_EXERC_ORGAN   A                                                        ");
		    	sbQuery.append("\n 			                                WHERE A.STND_YEAR        = ?                                                       		");
		    	sbQuery.append("\n 			                                     AND A.MBR_CD        = ?                                                        	");
		    	sbQuery.append("\n 			                                     AND A.TMS           = ?                                                           	");
		    	sbQuery.append("\n 			                                     AND A.DAY_ORD       = 1 ) A,                                                       ");
		    	sbQuery.append("\n 			                         (SELECT   A.STND_YEAR          STND_YEAR                                                       ");
		    	sbQuery.append("\n 			                                      ,A.MBR_CD         MBR_CD                                                          ");
		    	sbQuery.append("\n 			                                      ,A.TMS            TMS                                                             ");
		    	sbQuery.append("\n 			                                      ,A.DAY_ORD        DAY_ORD                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACE_NO        RACE_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACER_NO       RACER_NO                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RACE_REG_NO    RACE_REG_NO                                                     ");
		    	sbQuery.append("\n 			                                      ,A.INTRO_RUN_TM   INTRO_RUN_TM                                                    ");
		    	sbQuery.append("\n 			                                  FROM  TBEB_ORGAN   A                                                                  ");
		    	sbQuery.append("\n 			                                WHERE A.STND_YEAR        = ?                                                       		");
		    	sbQuery.append("\n 			                                     AND A.MBR_CD        = ?                                                        	");
		    	sbQuery.append("\n 			                                     AND A.TMS           = ?                                                           	");
		    	sbQuery.append("\n 			                                     AND A.DAY_ORD       = 1 ) B                                                        ");
		    	sbQuery.append("\n 			                        WHERE A.STND_YEAR   = B.STND_YEAR                                                               ");
		    	sbQuery.append("\n 			                          AND A.MBR_CD      = B.MBR_CD                                                                  ");
		    	sbQuery.append("\n 			                          AND A.TMS         = B.TMS                                                                     ");
		    	sbQuery.append("\n 			                          AND A.RACER_NO    = B.RACER_NO                                                                ");
		    	sbQuery.append("\n 			                 ) A                                                                                                    ");
		    	sbQuery.append("\n 			                 START WITH ROW_ID = 1                                                                                  ");
		    	sbQuery.append("\n 			                 CONNECT BY PRIOR ROW_ID    = ROW_ID -1                                                                 ");
		    	sbQuery.append("\n 			                      AND PRIOR A.STND_YEAR = A.STND_YEAR                                                               ");
		    	sbQuery.append("\n 			                      AND PRIOR A.MBR_CD    = A.MBR_CD                                                                  ");
		    	sbQuery.append("\n 			                      AND PRIOR A.TMS       = A.TMS                                                                     ");
		    	sbQuery.append("\n 			                      AND PRIOR A.DAY_ORD   = A.DAY_ORD                                                                 ");
		    	sbQuery.append("\n 			                      AND PRIOR A.RACER_NO  = A.RACER_NO                                                                ");
		    	sbQuery.append("\n 			                 GROUP BY A.STND_YEAR                                                                                   ");
		    	sbQuery.append("\n 			                                  ,A.MBR_CD                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.TMS                                                                                ");
		    	sbQuery.append("\n 			                                  ,A.DAY_ORD                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RACE_NO                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RACER_NO                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.RACER_NM                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.MOT_NO                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.BOAT_NO                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RUN_TM                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.OST_TM                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.FLYING_CNT                                                                         ");
		    	sbQuery.append("\n 			                                  ,A.LATE_CNT                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.RMK     ) A                                                                        ");
		    	sbQuery.append("\n 		) B,	                                                                                                                    ");
		    	sbQuery.append("\n         (    SELECT                                                                                                              ");
		    	sbQuery.append("\n 				     A.STND_YEAR      STND_YEAR                                                                                     ");
		    	sbQuery.append("\n 			        ,A.MBR_CD         MBR_CD                                                                                        ");
		    	sbQuery.append("\n 			        ,A.TMS            TMS                                                                                           ");
		    	sbQuery.append("\n 			        ,A.DAY_ORD        DAY_ORD                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RACE_NO        RACE_NO                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RACER_NO       RACER_NO                                                                                      ");
		    	sbQuery.append("\n 			        ,A.RACER_NM       RACER_NM                                                                                      ");
		    	sbQuery.append("\n 			        ,A.MOT_NO         MOT_NO                                                                                        ");
		    	sbQuery.append("\n 			        ,A.BOAT_NO        BOAT_NO                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RUN_TM         RUN_TM                                                                                        ");
		    	sbQuery.append("\n 			        ,A.OST_TM         OST_TM                                                                                        ");
		    	sbQuery.append("\n 			        ,SUBSTR(A.INTRO_RUN_TM,2,INSTR(A.INTRO_RUN_TM,'|',2,1) -2) INTRO_RUN_TM_1                                       ");
		    	sbQuery.append("\n 			        ,REPLACE(SUBSTR(A.INTRO_RUN_TM,INSTR(A.INTRO_RUN_TM,'|',2,1),LENGTH(A.INTRO_RUN_TM)),'|','') INTRO_RUN_TM_2     ");
		    	sbQuery.append("\n 			        ,A.FLYING_CNT     FLYING_CNT                                                                                    ");
		    	sbQuery.append("\n 			        ,A.LATE_CNT       LATE_CNT                                                                                      ");
		    	sbQuery.append("\n 			        ,A.RMK			  RMK                                                                                           ");
		    	sbQuery.append("\n 			 FROM ( SELECT   A.STND_YEAR      STND_YEAR                                                                             ");
		    	sbQuery.append("\n 			                ,A.MBR_CD         MBR_CD                                                                                ");
		    	sbQuery.append("\n 			                ,A.TMS            TMS                                                                                   ");
		    	sbQuery.append("\n 			                ,A.DAY_ORD        DAY_ORD                                                                               ");
		    	sbQuery.append("\n 			                ,A.RACE_NO        RACE_NO                                                                               ");
		    	sbQuery.append("\n 			                ,A.RACER_NO       RACER_NO                                                                              ");
		    	sbQuery.append("\n 			                ,A.RACER_NM       RACER_NM                                                                              ");
		    	sbQuery.append("\n 			                ,A.MOT_NO         MOT_NO                                                                                ");
		    	sbQuery.append("\n 			                ,A.BOAT_NO        BOAT_NO                                                                               ");
		    	sbQuery.append("\n 			                ,A.RUN_TM         RUN_TM                                                                                ");
		    	sbQuery.append("\n 			                ,A.OST_TM         OST_TM                                                                                ");
		    	sbQuery.append("\n 			                ,MAX(SYS_CONNECT_BY_PATH(A.INTRO_RUN_TM, '|')) INTRO_RUN_TM                                             ");
		    	sbQuery.append("\n 			                ,A.FLYING_CNT     FLYING_CNT                                                                            ");
		    	sbQuery.append("\n 			                ,A.LATE_CNT       LATE_CNT                                                                              ");
		    	sbQuery.append("\n 			                ,A.RMK			  RMK                                                                                   ");
		    	sbQuery.append("\n 			        FROM (                                                                                                          ");
		    	sbQuery.append("\n 			                   SELECT  A.STND_YEAR      STND_YEAR                                                                   ");
		    	sbQuery.append("\n 			                          ,A.MBR_CD         MBR_CD                                                                      ");
		    	sbQuery.append("\n 			                          ,A.TMS            TMS                                                                         ");
		    	sbQuery.append("\n 			                          ,A.DAY_ORD        DAY_ORD                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RACE_NO        RACE_NO                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RACER_NO       RACER_NO                                                                    ");
		    	sbQuery.append("\n 			                          ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) RACER_NM                 ");
		    	sbQuery.append("\n 			                          ,A.RACE_REG_NO    RACE_REG_NO                                                                 ");
		    	sbQuery.append("\n 			                          ,A.MOT_NO         MOT_NO                                                                      ");
		    	sbQuery.append("\n 			                          ,A.BOAT_NO        BOAT_NO                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RUN_TM         RUN_TM                                                                      ");
		    	sbQuery.append("\n 			                          ,A.OST_TM         OST_TM                                                                      ");
		    	sbQuery.append("\n 			                          ,B.INTRO_RUN_TM   INTRO_RUN_TM                                                                ");
		    	sbQuery.append("\n 			                          ,A.FLYING_CNT     FLYING_CNT                                                                  ");
		    	sbQuery.append("\n 			                          ,A.LATE_CNT       LATE_CNT                                                                    ");
		    	sbQuery.append("\n 			                          ,A.RMK			RMK                                                                         ");
		    	sbQuery.append("\n 			                          , ROW_NUMBER() OVER (PARTITION BY    B.STND_YEAR                                              ");
		    	sbQuery.append("\n 			                                                              ,B.MBR_CD                                                 ");
		    	sbQuery.append("\n 			                                                              ,B.TMS                                                    ");
		    	sbQuery.append("\n 			                                                              ,B.DAY_ORD                                                ");
		    	sbQuery.append("\n 			                                                              ,B.RACER_NO                                               ");
		    	sbQuery.append("\n 			                                                              ORDER BY B.STND_YEAR                                      ");
		    	sbQuery.append("\n 			                                                                      ,B.MBR_CD                                         ");
		    	sbQuery.append("\n 			                                                                      ,B.TMS                                            ");
		    	sbQuery.append("\n 			                                                                      ,B.DAY_ORD                                        ");
		    	sbQuery.append("\n 			                                                                      ,B.RACE_NO                                        ");
		    	sbQuery.append("\n 			                                                                      ,B.RACER_NO) ROW_ID                               ");
		    	sbQuery.append("\n 			                      FROM                                                                                              ");
		    	sbQuery.append("\n 			                            (  SELECT  A.STND_YEAR      STND_YEAR                                                       ");
		    	sbQuery.append("\n 			                                      ,A.MBR_CD         MBR_CD                                                          ");
		    	sbQuery.append("\n 			                                      ,A.TMS            TMS                                                             ");
		    	sbQuery.append("\n 			                                      ,A.DAY_ORD        DAY_ORD                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACE_NO        RACE_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACER_NO       RACER_NO                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RACE_REG_NO    RACE_REG_NO                                                     ");
		    	sbQuery.append("\n 			                                      ,A.MOT_NO         MOT_NO                                                          ");
		    	sbQuery.append("\n 			                                      ,A.BOAT_NO        BOAT_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RUN_TM         RUN_TM                                                          ");
		    	sbQuery.append("\n 			                                      ,A.OST_TM         OST_TM                                                          ");
		    	sbQuery.append("\n 			                                      ,A.FLYING_CNT     FLYING_CNT                                                      ");
		    	sbQuery.append("\n 			                                      ,A.LATE_CNT       LATE_CNT                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RMK			RMK                                                             ");
		    	sbQuery.append("\n 			                                 FROM  TBEC_APPO_EXERC_ORGAN   A                                                        ");
		    	sbQuery.append("\n 			                                WHERE A.STND_YEAR        = ?                                                       		");
		    	sbQuery.append("\n 			                                     AND A.MBR_CD        = ?                                                        	");
		    	sbQuery.append("\n 			                                     AND A.TMS           = ?                                                           	");
		    	sbQuery.append("\n 			                                     AND A.DAY_ORD       = 2 ) A,                                                       ");
		    	sbQuery.append("\n 			                         (SELECT   A.STND_YEAR          STND_YEAR                                                       ");
		    	sbQuery.append("\n 			                                      ,A.MBR_CD         MBR_CD                                                          ");
		    	sbQuery.append("\n 			                                      ,A.TMS            TMS                                                             ");
		    	sbQuery.append("\n 			                                      ,A.DAY_ORD        DAY_ORD                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACE_NO        RACE_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACER_NO       RACER_NO                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RACE_REG_NO    RACE_REG_NO                                                     ");
		    	sbQuery.append("\n 			                                      ,A.INTRO_RUN_TM   INTRO_RUN_TM                                                    ");
		    	sbQuery.append("\n 			                                  FROM  TBEB_ORGAN   A                                                                  ");
		    	sbQuery.append("\n 			                                WHERE A.STND_YEAR        = ?                                                       		");
		    	sbQuery.append("\n 			                                     AND A.MBR_CD        = ?                                                        	");
		    	sbQuery.append("\n 			                                     AND A.TMS           = ?                                                           ");
		    	sbQuery.append("\n 			                                     AND A.DAY_ORD       = 2 ) B                                                        ");
		    	sbQuery.append("\n 			                        WHERE A.STND_YEAR   = B.STND_YEAR                                                               ");
		    	sbQuery.append("\n 			                          AND A.MBR_CD      = B.MBR_CD                                                                  ");
		    	sbQuery.append("\n 			                          AND A.TMS         = B.TMS                                                                     ");
		    	sbQuery.append("\n 			                          AND A.RACER_NO    = B.RACER_NO                                                                ");
		    	sbQuery.append("\n 			                 ) A                                                                                                    ");
		    	sbQuery.append("\n 			                 START WITH ROW_ID = 1                                                                                  ");
		    	sbQuery.append("\n 			                 CONNECT BY PRIOR ROW_ID    = ROW_ID -1                                                                 ");
		    	sbQuery.append("\n 			                      AND PRIOR A.STND_YEAR = A.STND_YEAR                                                               ");
		    	sbQuery.append("\n 			                      AND PRIOR A.MBR_CD    = A.MBR_CD                                                                  ");
		    	sbQuery.append("\n 			                      AND PRIOR A.TMS       = A.TMS                                                                     ");
		    	sbQuery.append("\n 			                      AND PRIOR A.DAY_ORD   = A.DAY_ORD                                                                 ");
		    	sbQuery.append("\n 			                      AND PRIOR A.RACER_NO  = A.RACER_NO                                                                ");
		    	sbQuery.append("\n 			                 GROUP BY A.STND_YEAR                                                                                   ");
		    	sbQuery.append("\n 			                                  ,A.MBR_CD                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.TMS                                                                                ");
		    	sbQuery.append("\n 			                                  ,A.DAY_ORD                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RACE_NO                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RACER_NO                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.RACER_NM                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.MOT_NO                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.BOAT_NO                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RUN_TM                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.OST_TM                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.FLYING_CNT                                                                         ");
		    	sbQuery.append("\n 			                                  ,A.LATE_CNT                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.RMK ) A			                                                                ");
		    	sbQuery.append("\n 	) C,                                                                                                                            ");
		    	sbQuery.append("\n     (               SELECT                                                                                                       ");
		    	sbQuery.append("\n 				     A.STND_YEAR      STND_YEAR                                                                                     ");
		    	sbQuery.append("\n 			        ,A.MBR_CD         MBR_CD                                                                                        ");
		    	sbQuery.append("\n 			        ,A.TMS            TMS                                                                                           ");
		    	sbQuery.append("\n 			        ,A.DAY_ORD        DAY_ORD                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RACE_NO        RACE_NO                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RACE_REG_NO    RACE_REG_NO                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RACER_NO       RACER_NO                                                                                      ");
		    	sbQuery.append("\n 			        ,A.RACER_NM       RACER_NM                                                                                      ");
		    	sbQuery.append("\n 			        ,A.MOT_NO         MOT_NO                                                                                        ");
		    	sbQuery.append("\n 			        ,A.BOAT_NO        BOAT_NO                                                                                       ");
		    	sbQuery.append("\n 			        ,A.RUN_TM         RUN_TM                                                                                        ");
		    	sbQuery.append("\n 			        ,A.OST_TM         OST_TM                                                                                        ");
		    	sbQuery.append("\n 			        ,SUBSTR(A.INTRO_RUN_TM,2,INSTR(A.INTRO_RUN_TM,'|',2,1) -2) INTRO_RUN_TM_1                                       ");
		    	sbQuery.append("\n 			        ,REPLACE(SUBSTR(A.INTRO_RUN_TM,INSTR(A.INTRO_RUN_TM,'|',2,1),LENGTH(A.INTRO_RUN_TM)),'|','') INTRO_RUN_TM_2     ");
		    	sbQuery.append("\n 			        ,A.FLYING_CNT     FLYING_CNT                                                                                    ");
		    	sbQuery.append("\n 			        ,A.LATE_CNT       LATE_CNT                                                                                      ");
		    	sbQuery.append("\n 			        ,A.RMK			  RMK                                                                                           ");
		    	sbQuery.append("\n 			 FROM ( SELECT   A.STND_YEAR      STND_YEAR                                                                             ");
		    	sbQuery.append("\n 			                ,A.MBR_CD         MBR_CD                                                                                ");
		    	sbQuery.append("\n 			                ,A.TMS            TMS                                                                                   ");
		    	sbQuery.append("\n 			                ,A.DAY_ORD        DAY_ORD                                                                               ");
		    	sbQuery.append("\n 			                ,A.RACE_NO        RACE_NO                                                                               ");
		    	sbQuery.append("\n 			                ,A.RACE_REG_NO    RACE_REG_NO                                                                               ");
		    	sbQuery.append("\n 			                ,A.RACER_NO       RACER_NO                                                                              ");
		    	sbQuery.append("\n 			                ,A.RACER_NM       RACER_NM                                                                              ");
		    	sbQuery.append("\n 			                ,A.MOT_NO         MOT_NO                                                                                ");
		    	sbQuery.append("\n 			                ,A.BOAT_NO        BOAT_NO                                                                               ");
		    	sbQuery.append("\n 			                ,A.RUN_TM         RUN_TM                                                                                ");
		    	sbQuery.append("\n 			                ,A.OST_TM         OST_TM                                                                                ");
		    	sbQuery.append("\n 			                ,MAX(SYS_CONNECT_BY_PATH(A.INTRO_RUN_TM, '|')) INTRO_RUN_TM                                             ");
		    	sbQuery.append("\n 			                ,A.FLYING_CNT     FLYING_CNT                                                                            ");
		    	sbQuery.append("\n 			                ,A.LATE_CNT       LATE_CNT                                                                              ");
		    	sbQuery.append("\n 			                ,A.RMK			  RMK                                                                                   ");
		    	sbQuery.append("\n 			        FROM (                                                                                                          ");
		    	sbQuery.append("\n 			                   SELECT  A.STND_YEAR      STND_YEAR                                                                   ");
		    	sbQuery.append("\n 			                          ,A.MBR_CD         MBR_CD                                                                      ");
		    	sbQuery.append("\n 			                          ,A.TMS            TMS                                                                         ");
		    	sbQuery.append("\n 			                          ,A.DAY_ORD        DAY_ORD                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RACE_NO        RACE_NO                                                                         ");
		    	sbQuery.append("\n 			                          ,A.RACER_NO       RACER_NO                                                                    ");
		    	sbQuery.append("\n 			                          ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) RACER_NM                 ");
		    	sbQuery.append("\n 			                          ,A.RACE_REG_NO    RACE_REG_NO                                                                 ");
		    	sbQuery.append("\n 			                          ,A.MOT_NO         MOT_NO                                                                      ");
		    	sbQuery.append("\n 			                          ,A.BOAT_NO        BOAT_NO                                                                     ");
		    	sbQuery.append("\n 			                          ,A.RUN_TM         RUN_TM                                                                      ");
		    	sbQuery.append("\n 			                          ,A.OST_TM         OST_TM                                                                      ");
		    	sbQuery.append("\n 			                          ,B.INTRO_RUN_TM   INTRO_RUN_TM                                                                ");
		    	sbQuery.append("\n 			                          ,A.FLYING_CNT     FLYING_CNT                                                                  ");
		    	sbQuery.append("\n 			                          ,A.LATE_CNT       LATE_CNT                                                                    ");
		    	sbQuery.append("\n 			                          ,A.RMK			  RMK                                                                       ");
		    	sbQuery.append("\n 			                          , ROW_NUMBER() OVER (PARTITION BY    B.STND_YEAR                                              ");
		    	sbQuery.append("\n 			                                                              ,B.MBR_CD                                                 ");
		    	sbQuery.append("\n 			                                                              ,B.TMS                                                    ");
		    	sbQuery.append("\n 			                                                              ,B.DAY_ORD                                                ");
		    	sbQuery.append("\n 			                                                              ,B.RACER_NO                                               ");
		    	sbQuery.append("\n 			                                                              ORDER BY B.STND_YEAR                                      ");
		    	sbQuery.append("\n 			                                                                      ,B.MBR_CD                                         ");
		    	sbQuery.append("\n 			                                                                      ,B.TMS                                            ");
		    	sbQuery.append("\n 			                                                                      ,B.DAY_ORD                                        ");
		    	sbQuery.append("\n 			                                                                      ,B.RACE_NO                                        ");
		    	sbQuery.append("\n 			                                                                      ,B.RACER_NO) ROW_ID                               ");
		    	sbQuery.append("\n 			                      FROM                                                                                              ");
		    	sbQuery.append("\n 			                            (  SELECT  A.STND_YEAR      STND_YEAR                                                       ");
		    	sbQuery.append("\n 			                                      ,A.MBR_CD         MBR_CD                                                          ");
		    	sbQuery.append("\n 			                                      ,A.TMS            TMS                                                             ");
		    	sbQuery.append("\n 			                                      ,A.DAY_ORD        DAY_ORD                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACE_NO        RACE_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACER_NO       RACER_NO                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RACE_REG_NO    RACE_REG_NO                                                     ");
		    	sbQuery.append("\n 			                                      ,A.MOT_NO         MOT_NO                                                          ");
		    	sbQuery.append("\n 			                                      ,A.BOAT_NO        BOAT_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RUN_TM         RUN_TM                                                          ");
		    	sbQuery.append("\n 			                                      ,A.OST_TM         OST_TM                                                          ");
		    	sbQuery.append("\n 			                                      ,A.FLYING_CNT     FLYING_CNT                                                      ");
		    	sbQuery.append("\n 			                                      ,A.LATE_CNT       LATE_CNT                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RMK			RMK                                                             ");
		    	sbQuery.append("\n 			                                 FROM  TBEC_APPO_EXERC_ORGAN   A                                                        ");
		    	sbQuery.append("\n 			                                WHERE A.STND_YEAR        = ?                                                       		");
		    	sbQuery.append("\n 			                                     AND A.MBR_CD        = ?                                                       		");
		    	sbQuery.append("\n 			                                     AND A.TMS           = ?                                                           	");
		    	sbQuery.append("\n 			                                     AND A.DAY_ORD       = 3 ) A,                                                       ");
		    	sbQuery.append("\n 			                         (SELECT   A.STND_YEAR          STND_YEAR                                                       ");
		    	sbQuery.append("\n 			                                      ,A.MBR_CD         MBR_CD                                                          ");
		    	sbQuery.append("\n 			                                      ,A.TMS            TMS                                                             ");
		    	sbQuery.append("\n 			                                      ,A.DAY_ORD        DAY_ORD                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACE_NO        RACE_NO                                                         ");
		    	sbQuery.append("\n 			                                      ,A.RACER_NO       RACER_NO                                                        ");
		    	sbQuery.append("\n 			                                      ,A.RACE_REG_NO    RACE_REG_NO                                                     ");
		    	sbQuery.append("\n 			                                      ,A.INTRO_RUN_TM   INTRO_RUN_TM                                                    ");
		    	sbQuery.append("\n 			                                  FROM  TBEB_ORGAN   A                                                                  ");
		    	sbQuery.append("\n 			                                WHERE A.STND_YEAR        = ?                                                       		");
		    	sbQuery.append("\n 			                                     AND A.MBR_CD        = ?                                                        	");
		    	sbQuery.append("\n 			                                     AND A.TMS           = ?                                                           	");
		    	sbQuery.append("\n 			                                     AND A.DAY_ORD       = 3 ) B                                                        ");
		    	sbQuery.append("\n 			                        WHERE A.STND_YEAR   = B.STND_YEAR                                                               ");
		    	sbQuery.append("\n 			                          AND A.MBR_CD      = B.MBR_CD                                                                  ");
		    	sbQuery.append("\n 			                          AND A.TMS         = B.TMS                                                                     ");
		    	sbQuery.append("\n 			                          AND A.RACER_NO    = B.RACER_NO                                                                ");
		    	sbQuery.append("\n 			                 ) A                                                                                                    ");
		    	sbQuery.append("\n 			                 START WITH ROW_ID = 1                                                                                  ");
		    	sbQuery.append("\n 			                 CONNECT BY PRIOR ROW_ID    = ROW_ID -1                                                                 ");
		    	sbQuery.append("\n 			                      AND PRIOR A.STND_YEAR = A.STND_YEAR                                                               ");
		    	sbQuery.append("\n 			                      AND PRIOR A.MBR_CD    = A.MBR_CD                                                                  ");
		    	sbQuery.append("\n 			                      AND PRIOR A.TMS       = A.TMS                                                                     ");
		    	sbQuery.append("\n 			                      AND PRIOR A.DAY_ORD   = A.DAY_ORD                                                                 ");
		    	sbQuery.append("\n 			                      AND PRIOR A.RACER_NO  = A.RACER_NO                                                                ");
		    	sbQuery.append("\n 			                 GROUP BY A.STND_YEAR                                                                                   ");
		    	sbQuery.append("\n 			                                  ,A.MBR_CD                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.TMS                                                                                ");
		    	sbQuery.append("\n 			                                  ,A.DAY_ORD                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RACE_NO                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RACER_NO                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.RACE_REG_NO                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.RACER_NM                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.MOT_NO                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.BOAT_NO                                                                            ");
		    	sbQuery.append("\n 			                                  ,A.RUN_TM                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.OST_TM                                                                             ");
		    	sbQuery.append("\n 			                                  ,A.FLYING_CNT                                                                         ");
		    	sbQuery.append("\n 			                                  ,A.LATE_CNT                                                                           ");
		    	sbQuery.append("\n 			                                  ,A.RMK) A			                                                                    ");
		    	sbQuery.append("\n 		) D	                                                                                                                        ");
		if  ( "0".equals(ctx.get("DAY_ORD".trim()))  ) {
			
				sbQuery.append("\n    WHERE  A.STND_YEAR   =   B.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  A.MBR_CD      =   B.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  A.TMS         =   B.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  A.RACER_NO    =   B.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  A.STND_YEAR   =   C.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  A.MBR_CD      =   C.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  A.TMS         =   C.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  A.RACER_NO    =   C.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  A.STND_YEAR   =   D.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  A.MBR_CD      =   D.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  A.TMS         =   D.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  A.RACER_NO    =   D.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n ORDER BY  A.STND_YEAR                                                                                                            ");
				sbQuery.append("\n          ,A.MBR_CD                                                                                                               ");
				sbQuery.append("\n     	 	,A.TMS                                                                                                                     ");
				sbQuery.append("\n     	 	,A.DAY_ORD                                                                                                                 ");
				sbQuery.append("\n     	 	,A.RACE_NO                                                                                                                 ");
				sbQuery.append("\n          ,A.RACER_NO                                                                                                             ");
				  	   	 
		    		    
		} else if  ( "1".equals(ctx.get("DAY_ORD".trim()))  ) {
				sbQuery.append("\n    WHERE  B.STND_YEAR   =   A.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  B.MBR_CD      =   A.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  B.TMS         =   A.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  B.RACER_NO    =   A.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  B.STND_YEAR   =   C.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  B.MBR_CD      =   C.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  B.TMS         =   C.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  B.RACER_NO    =   C.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  B.STND_YEAR   =   D.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  B.MBR_CD      =   D.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  B.TMS         =   D.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  B.RACER_NO    =   D.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n ORDER BY  B.STND_YEAR                                                                                                            ");
				sbQuery.append("\n          ,B.MBR_CD                                                                                                               ");
				sbQuery.append("\n     	 	,B.TMS                                                                                                                     ");
				sbQuery.append("\n     	 	,B.DAY_ORD                                                                                                                 ");
				sbQuery.append("\n     	 	,B.RACE_NO                                                                                                                 ");
				sbQuery.append("\n          ,B.RACER_NO                                                                                                             ");
			 		 
		} else if ( "2".equals(ctx.get("DAY_ORD".trim()))  ) {
				sbQuery.append("\n    WHERE  C.STND_YEAR   =   A.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  C.MBR_CD      =   A.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  C.TMS         =   A.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  C.RACER_NO    =   A.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  C.STND_YEAR   =   B.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  C.MBR_CD      =   B.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  C.TMS         =   B.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  C.RACER_NO    =   B.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  C.STND_YEAR   =   D.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  C.MBR_CD      =   D.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  C.TMS         =   D.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  C.RACER_NO    =   D.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n ORDER BY  C.STND_YEAR                                                                                                            ");
				sbQuery.append("\n          ,C.MBR_CD                                                                                                               ");
				sbQuery.append("\n     	 	,C.TMS                                                                                                                     ");
				sbQuery.append("\n     	 	,C.DAY_ORD                                                                                                                 ");
				sbQuery.append("\n     	 	,C.RACE_NO                                                                                                                 ");
				sbQuery.append("\n          ,C.RACER_NO                                                                                                             ");
			
		} else if ( "3".equals(ctx.get("DAY_ORD".trim()))  ) {		 
				sbQuery.append("\n    WHERE  D.STND_YEAR   =   A.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  D.MBR_CD      =   A.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  D.TMS         =   A.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  D.RACER_NO    =   A.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  D.STND_YEAR   =   B.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  D.MBR_CD      =   B.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  D.TMS         =   B.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  D.RACER_NO    =   B.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n      AND  D.STND_YEAR   =   C.STND_YEAR(+)                                                                                       ");
				sbQuery.append("\n      AND  D.MBR_CD      =   C.MBR_CD(+)                                                                                          ");
				sbQuery.append("\n      AND  D.TMS         =   C.TMS(+)                                                                                             ");
				sbQuery.append("\n      AND  D.RACER_NO    =   C.RACER_NO(+)                                                                                        ");
				sbQuery.append("\n ORDER BY  D.STND_YEAR                                                                                                            ");
				sbQuery.append("\n          ,D.MBR_CD                                                                                                               ");
				sbQuery.append("\n     	 	,D.TMS                                                                                                                  ");
				sbQuery.append("\n     	 	,D.DAY_ORD                                                                                                              ");
				sbQuery.append("\n     	 	,D.RACE_NO                                                                                                              ");
				sbQuery.append("\n     	 	,D.RACE_REG_NO                                                                                                          ");
				sbQuery.append("\n          ,D.RACER_NO                                                                                                             ");
			
		} 
		if  ( "1".equals(ctx.get("DAY_ORD".trim())) || "2".equals(ctx.get("DAY_ORD".trim())) || "3".equals(ctx.get("DAY_ORD".trim()))) {
				sbQuery.append("\n              ) A,               TBEB_ORGAN B											");
				sbQuery.append("\n          WHERE   B.STND_YEAR   = A.STND_YEAR(+)                                      ");
				sbQuery.append("\n            AND   B.MBR_CD      = A.MBR_CD(+)                                         ");
				sbQuery.append("\n            AND   B.TMS         = A.TMS(+)                                            ");
				sbQuery.append("\n            AND   B.DAY_ORD     = A.DAY_ORD(+)                                        ");
				sbQuery.append("\n            AND   B.RACER_NO    = A.RACER_NO(+)                                       ");
				sbQuery.append("\n            AND   B.STND_YEAR   = ?                                                   ");
				sbQuery.append("\n            AND   B.MBR_CD      = ?                                                   ");
				sbQuery.append("\n            AND   B.TMS         = ?                                                   ");
				sbQuery.append("\n            AND   B.DAY_ORD     = ?                                                   ");
				sbQuery.append("\n         ORDER BY B.STND_YEAR,                                                        ");
				sbQuery.append("\n                  B.MBR_CD,                                                           ");
				sbQuery.append("\n                  B.TMS,                                                              ");
				sbQuery.append("\n                  B.DAY_ORD,                                                          ");
				sbQuery.append("\n                  B.RACE_NO,                                                          ");
				sbQuery.append("\n                  B.RACE_REG_NO                                                       ");
		}
		   	               
		    	PosParameter param = new PosParameter();
		        int i = 0;
		    	
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		if  ( "1".equals(ctx.get("DAY_ORD".trim())) || "2".equals(ctx.get("DAY_ORD".trim())) || "3".equals(ctx.get("DAY_ORD".trim()))) {
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS		          ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD	          ".trim()));
		}
        
        PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutRace";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    }

}


