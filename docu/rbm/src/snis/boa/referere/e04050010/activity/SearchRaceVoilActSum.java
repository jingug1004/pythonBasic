/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04050010.activity.SearchRaceVoilActSum.java
 * 파일설명		: 위반내용집계현황조회
 * 작성자			: 정의태
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04050010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRaceVoilActSum extends SnisActivity {
    public SearchRaceVoilActSum()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	SearchVoilActRaceSum(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchVoilActRaceSum(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
    	StringBuffer sbQuery2 = new StringBuffer();
    	
    	

   if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) {   		
 //횟수
	sbQuery.append("	SELECT   ROWNUM             RACE_NUM	--일련번호		");
   	sbQuery.append("\n	        ,A.STND_YEAR  		STND_YEAR	--년도			");
   	sbQuery.append("\n			,A.MBR_CD     		MBR_CD		--경정장코드		");
   	sbQuery.append("\n			,A.TMS		   		TMS			--회차			");
   	sbQuery.append("\n			,A.DAY_ORD			DAY_ORD		--일차			");
   	sbQuery.append("\n			,DECODE(A.ST_MTHD_CD, '001', '(온)'||A.RACE_NO, A.RACE_NO) RACE_NO		--경주번호		");
   	sbQuery.append("\n			,A.RACE_REG_NO      RACE_REG_NO --정번			");
if  ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항별조회(VOIL_JO_NM)
	sbQuery.append("\n			,A.VOIL_JO_NM      VOIL_JO_NM     --조항명		");
} else if ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//기수별조회(RACER_PERIO_NO)
	sbQuery.append("\n			,A.RACER_PERIO_NO  RACER_PERIO_NO  --기수		");
} else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//판정별조회(VOIL_NM)
	sbQuery.append("\n			,A.VOIL_NM 			VOIL_NM  	   --판정		");
} else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//장소조회(ACDNT_LOC_NM)
	sbQuery.append("\n			,A.ACDNT_LOC_NM  	ACDNT_LOC_NM   --장소		");
}
   	sbQuery.append("\n			,A.RACER_NO         RACER_NO    --등록번호		");
   	sbQuery.append("\n			,A.RACER_NM         RACER_NM    --선수명			");
   	sbQuery.append("\n			,A.RACER_GRD_CD     RACER_GRD_CD--등급코드		");
   	sbQuery.append("\n	        ,A.RACER_GRD_NM     RACER_GRD_NM--등급명			");
   	sbQuery.append("\n	        ,A.RACE_DAY     	RACE_DAY	--경주일자		");

   	sbQuery.append("\n	        ,A.CNT995			CNT995		--출주제외(I)		");
   	sbQuery.append("\n	        ,A.CNT997           CNT997      --출주제외(II)	");
   	sbQuery.append("\n	        ,A.CNT110			CNT110		--F				");
   	sbQuery.append("\n	        ,A.CNT120    		CNT120		--L				");
   	sbQuery.append("\n	        ,A.CNT140			CNT140		--실격			");
   	sbQuery.append("\n	        ,A.CNT125     		CNT125		--반칙실격		");
   	sbQuery.append("\n	        ,A.CNT150			CNT150		--반칙경고		");
   	sbQuery.append("\n	        ,A.CNT210			CNT210		--경고			");
   	sbQuery.append("\n	        ,A.CNT220			CNT220		--주의			");
   	sbQuery.append("\n	        ,NVL(A.CNT995,0)+NVL(A.CNT997,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT110,0)+NVL(A.CNT120,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT140,0)+NVL(A.CNT125,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT150,0)+NVL(A.CNT210,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT220,0) 	SUM1		--소계1			");

   	sbQuery.append("\n	        ,A.CNT996   		CNT996		--출주제외면책		");
   	sbQuery.append("\n	        ,A.CNT999			CNT999    	--실격면책 		");
   	sbQuery.append("\n	        ,A.CNT998    		CNT998		--결장면책		");
   	sbQuery.append("\n	        ,A.CNT211    		CNT211		--경고면책		");
   	sbQuery.append("\n	        ,A.CNT221    		CNT221		--주의면책		");
   	sbQuery.append("\n	        ,NVL(A.CNT996,0)+NVL(A.CNT999,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT998,0)+NVL(A.CNT211,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT221,0) 	SUM2		--소계2			");

   	sbQuery.append("\n	        ,A.CNT130   		CNT130		--결장			");
   	sbQuery.append("\n	        ,A.CNT993   		CNT993		--F퇴소결장		");
   	sbQuery.append("\n	        ,A.CNT994   		CNT994		--L퇴소결장		");
   	sbQuery.append("\n	        ,NVL(A.CNT130,0)+NVL(A.CNT993,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT994,0) 	SUM2_1		--소계3			");

   	sbQuery.append("\n	        ,NVL(A.CNT995,0)+NVL(A.CNT997,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT110,0)+NVL(A.CNT120,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT140,0)+NVL(A.CNT125,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT150,0)+NVL(A.CNT210,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT220,0)+NVL(A.CNT996,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT999,0)+NVL(A.CNT998,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT211,0)+NVL(A.CNT221,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT130,0)+NVL(A.CNT993,0)				");
   	sbQuery.append("\n	        +NVL(A.CNT994,0)	SUM3		--총계			");
   	
   	sbQuery.append("\n	        ,A.MOT_NO			MOT_NO		--모터번호		");
   	sbQuery.append("\n	        ,A.BOAT_NO			BOAT_NO		--보트번호		");
   	sbQuery.append("\n	FROM ( SELECT A.STND_YEAR                               STND_YEAR                                               ");
   	sbQuery.append("\n		      ,A.MBR_CD                                     MBR_CD                                                  ");
   	sbQuery.append("\n		      ,A.TMS                                        TMS                                                     ");
   	sbQuery.append("\n		      ,A.DAY_ORD                                    DAY_ORD	   		                                        ");
   	sbQuery.append("\n		      ,A.RACE_NO                                    RACE_NO	   		                                        ");
   	sbQuery.append("\n		      ,A.RACE_REG_NO                                RACE_REG_NO		                                        ");
if  ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항
   	sbQuery.append("\n		  ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) VOIL_JO_NM					");
} else if  ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//기수
	sbQuery.append("\n		  	  ,D.RACER_PERIO_NO 							RACER_PERIO_NO											");
} else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//판정
	sbQuery.append("\n			,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) VOIL_NM					");
} else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//장소
	sbQuery.append("\n			,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) ACDNT_LOC_NM			");
}

   	sbQuery.append("\n		      ,A.RACER_NO                                   RACER_NO   		                                        ");
   	sbQuery.append("\n		      ,D.NM_KOR                                     RACER_NM			                                    ");
   	sbQuery.append("\n		      ,D.RACER_GRD_CD								RACER_GRD_CD	                                        ");
   	sbQuery.append("\n		      ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD) RACER_GRD_NM        ");
   	sbQuery.append("\n		      ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')    RACE_DAY                                                ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '995', A.RACE_NO))   CNT995                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '997', A.RACE_NO))   CNT997                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '110', A.RACE_NO))   CNT110                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '120', A.RACE_NO))   CNT120                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '140', A.RACE_NO))   CNT140                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '125', A.RACE_NO))   CNT125                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '150', A.RACE_NO))   CNT150                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '210', A.RACE_NO))   CNT210                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '220', A.RACE_NO))   CNT220                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '996', A.RACE_NO))   CNT996                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '999', A.RACE_NO))   CNT999                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '998', A.RACE_NO))   CNT998                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '211', A.RACE_NO))   CNT211                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '221', A.RACE_NO))   CNT221                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '130', A.RACE_NO))   CNT130                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '993', A.RACE_NO))   CNT993                                                  ");
   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '994', A.RACE_NO))   CNT994                                                  ");
   	
   	sbQuery.append("\n		      ,F.MOT_NO										MOT_NO                                                  ");
   	sbQuery.append("\n		      ,F.BOAT_NO									BOAT_NO                                                 ");
   	sbQuery.append("\n		      ,MAX(F.ST_MTHD_CD)							ST_MTHD_CD --(1)                                          ");
   	sbQuery.append("\n		 	      FROM     TBED_RACE_VOIL_ACT     A,                                                                "); 
   	sbQuery.append("\n		                   TBEB_RACE              B,                                                                "); 
   	sbQuery.append("\n		                   TBEB_RACE_DAY_ORD      C,                                                                "); 
   	sbQuery.append("\n		                   TBEC_RACER_MASTER      D,                                                                ");
   	sbQuery.append("\n		                   TBED_RACE_ACDNT_STTS   E,                                                                ");
   	sbQuery.append("\n		                   TBEB_ORGAN             F                                                                 ");
   	sbQuery.append("\n		          WHERE   A.STND_YEAR  = B.STND_YEAR                                                                ");
   	sbQuery.append("\n		          AND     A.MBR_CD         = B.MBR_CD                                                               ");
   	sbQuery.append("\n		          AND     A.TMS            = B.TMS                                                                  ");
   	sbQuery.append("\n		          AND     A.DAY_ORD        = B.DAY_ORD                                                              ");
   	sbQuery.append("\n		          AND     A.RACE_NO        = B.RACE_NO                                                              ");
   	sbQuery.append("\n		          AND     A.STND_YEAR      = E.STND_YEAR(+)                                                         ");
   	sbQuery.append("\n		          AND     A.MBR_CD         = E.MBR_CD(+)                                                            ");
   	sbQuery.append("\n		          AND     A.TMS            = E.TMS(+)                                                               ");
   	sbQuery.append("\n		          AND     A.DAY_ORD        = E.DAY_ORD(+)                                                           ");
   	sbQuery.append("\n		          AND     A.RACE_NO        = E.RACE_NO(+)                                                           ");
   	sbQuery.append("\n		          AND     A.RACE_REG_NO    = E.RACE_REG_NO(+)                                                       ");    
   	sbQuery.append("\n		          AND     A.RACER_NO       = E.RACER_NO(+)                                                          ");
   	sbQuery.append("\n		          AND     A.SEQ       	   = E.SEQ(+)                                                          		");
   	sbQuery.append("\n		          AND     A.STND_YEAR      = F.STND_YEAR(+)                                                         ");
   	sbQuery.append("\n		          AND     A.MBR_CD         = F.MBR_CD(+)                                                            ");
   	sbQuery.append("\n		          AND     A.TMS            = F.TMS(+)                                                               ");
   	sbQuery.append("\n		          AND     A.DAY_ORD        = F.DAY_ORD(+)                                                           ");
   	sbQuery.append("\n		          AND     A.RACE_NO        = F.RACE_NO(+)                                                           ");
   	sbQuery.append("\n		          AND     A.RACE_REG_NO    = F.RACE_REG_NO(+)                                                       ");    
   	sbQuery.append("\n		          AND     A.STND_YEAR      = C.STND_YEAR                                                            ");
   	sbQuery.append("\n		          AND     A.MBR_CD         = C.MBR_CD                                                               ");
   	sbQuery.append("\n		          AND     A.TMS            = C.TMS                                                                  ");
   	sbQuery.append("\n		          AND     A.DAY_ORD        = C.DAY_ORD                                                              ");
   	sbQuery.append("\n		          AND     A.RACER_NO       = D.RACER_NO                                                             ");
   	sbQuery.append("\n		          AND     A.STND_YEAR      = ?                                                                 		");
   	sbQuery.append("\n		          AND     A.MBR_CD         = ?                                                                  	");
   	sbQuery.append("\n		          AND     A.RACE_DAY       BETWEEN ? AND ?                                                         	");
   
 } else if ( "W".equals(ctx.get("SELECT_COND".trim()))  ) {
//조항   

	 sbQuery.append("  SELECT   ROWNUM             RACE_NUM    		--일련번호    			");
	 sbQuery.append("\n         ,A.STND_YEAR        STND_YEAR    	--년도                      	");
	 sbQuery.append("\n         ,A.MBR_CD           MBR_CD        	--경정장코드                	");
	 sbQuery.append("\n         ,A.TMS              TMS            	--회차                      	");
	 sbQuery.append("\n         ,A.DAY_ORD          DAY_ORD        	--일차                      	");
	 sbQuery.append("\n         ,DECODE(A.ST_MTHD_CD, '001', '(온)'||A.RACE_NO, A.RACE_NO)   RACE_NO  --경주번호  ");
	 sbQuery.append("\n         ,A.RACE_REG_NO      RACE_REG_NO 	--정번                      	");
if  ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항
	sbQuery.append("\n			,A.VOIL_JO_NM      VOIL_JO_NM    	--조항명				");
} else if  ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//기수
	sbQuery.append("\n			,A.RACER_PERIO_NO   RACER_PERIO_NO  --기수				");
} else if  ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//판정
	sbQuery.append("\n			,A.VOIL_NM   		VOIL_NM  		--판정				");
}else if  ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//장소
	sbQuery.append("\n			,A.ACDNT_LOC_NM   	ACDNT_LOC_NM  	--장소				");
}

	 sbQuery.append("\n         ,A.RACER_NO         RACER_NO    	--등록번호                  	");
	 sbQuery.append("\n         ,A.RACER_NM         RACER_NM    	--선수명                    	");
	 sbQuery.append("\n         ,A.RACER_GRD_CD     RACER_GRD_CD	--등급코드                  	");
	 sbQuery.append("\n         ,A.RACER_GRD_NM     RACER_GRD_NM	--등급명                    	");
	 sbQuery.append("\n         ,A.RACE_DAY         RACE_DAY    	--경주일자                  	");

	 sbQuery.append("\n         ,A.CNT995           CNT995        	--출주제외(I)         ");
	 sbQuery.append("\n         ,A.CNT997           CNT997      	--출주제외(II)        ");
	 sbQuery.append("\n         ,A.CNT110           CNT110        	--F                 ");
	 sbQuery.append("\n         ,A.CNT120           CNT120        	--L                 ");
	 sbQuery.append("\n         ,A.CNT140           CNT140        	--실격                      	");
	 sbQuery.append("\n         ,A.CNT125           CNT125        	--반칙실격                  	");
	 sbQuery.append("\n         ,A.CNT150           CNT150        	--반칙경고                  	");
	 sbQuery.append("\n         ,A.CNT210           CNT210        	--경고                      	");
	 sbQuery.append("\n         ,A.CNT220           CNT220        	--주의                      	");
	 sbQuery.append("\n         ,DECODE(A.CNT995,NULL,0,1) + DECODE(A.CNT997,NULL,0,1)  ");
	 sbQuery.append("\n         +DECODE(A.CNT110,NULL,0,1) + DECODE(A.CNT120,NULL,0,1)  ");
	 sbQuery.append("\n         +DECODE(A.CNT140,NULL,0,1) + DECODE(A.CNT125,NULL,0,1)  ");
	 sbQuery.append("\n         +DECODE(A.CNT150,NULL,0,1) + DECODE(A.CNT210,NULL,0,1)  ");
	 sbQuery.append("\n         +DECODE(A.CNT220,NULL,0,1) SUM1     --소계1              ");

	 sbQuery.append("\n         ,A.CNT996           CNT996        	--출주제외면책              	");
	 sbQuery.append("\n         ,A.CNT999           CNT999        	--실격면책                  	");
	 sbQuery.append("\n         ,A.CNT998           CNT998        	--결장면책                      	");
	 sbQuery.append("\n         ,A.CNT211           CNT211        	--경고면책                      	");
	 sbQuery.append("\n         ,A.CNT221           CNT221        	--주의면책                      	");
	 sbQuery.append("\n         ,DECODE(A.CNT996,NULL,0,1) + DECODE(A.CNT999,NULL,0,1)  ");
	 sbQuery.append("\n         +DECODE(A.CNT998,NULL,0,1) + DECODE(A.CNT211,NULL,0,1)  ");
	 sbQuery.append("\n         +DECODE(A.CNT221,NULL,0,1) SUM2     --소계2              ");
	 
	 sbQuery.append("\n         ,A.CNT130           CNT130        	--결장              		");
	 sbQuery.append("\n         ,A.CNT993           CNT993        	--F퇴소결장                  	");
	 sbQuery.append("\n         ,A.CNT994           CNT994        	--L퇴소결장                    	");
	 sbQuery.append("\n         ,DECODE(A.CNT130,NULL,0,1) + DECODE(A.CNT993,NULL,0,1)  ");
	 sbQuery.append("\n         +DECODE(A.CNT994,NULL,0,1) SUM2_1   --소계2_1            ");
	
	 sbQuery.append("\n         ,DECODE(A.CNT995,NULL,0,1) + DECODE(A.CNT997,NULL,0,1)           	");
	 sbQuery.append("\n         +DECODE(A.CNT110,NULL,0,1) + DECODE(A.CNT120,NULL,0,1)           	");
	 sbQuery.append("\n         +DECODE(A.CNT140,NULL,0,1) + DECODE(A.CNT125,NULL,0,1)           	");
	 sbQuery.append("\n         +DECODE(A.CNT150,NULL,0,1) + DECODE(A.CNT210,NULL,0,1)           	");
	 sbQuery.append("\n         +DECODE(A.CNT220,NULL,0,1) + DECODE(A.CNT996,NULL,0,1)           	");
	 sbQuery.append("\n         +DECODE(A.CNT999,NULL,0,1) + DECODE(A.CNT998,NULL,0,1) 				");
	 sbQuery.append("\n         +DECODE(A.CNT211,NULL,0,1) + DECODE(A.CNT221,NULL,0,1) 				");
	 sbQuery.append("\n         +DECODE(A.CNT130,NULL,0,1) + DECODE(A.CNT993,NULL,0,1)				");
	 sbQuery.append("\n         +DECODE(A.CNT994,NULL,0,1)   SUM3--총계								");
	 
	 sbQuery.append("\n         ,A.MOT_NO			MOT_NO			--모터번호                  				");
	 sbQuery.append("\n         ,A.BOAT_NO			BOAT_NO			--보트번호                  				");
	 sbQuery.append("\n  FROM ( SELECT A.STND_YEAR                             STND_YEAR         	");
	 sbQuery.append("\n           ,A.MBR_CD                                    MBR_CD            	");
	 sbQuery.append("\n           ,A.TMS                                       TMS               	");                              
	 sbQuery.append("\n           ,A.DAY_ORD                                   DAY_ORD           	");                                             
	 sbQuery.append("\n           ,A.RACE_NO                                   RACE_NO           	");
	 sbQuery.append("\n           ,A.RACE_REG_NO                               RACE_REG_NO       	");
if  ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항
	 sbQuery.append("\n	  ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) VOIL_JO_NM						");
} else if  ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//기수 
   	sbQuery.append("\n	      ,D.RACER_PERIO_NO							  RACER_PERIO_NO												");
} else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//판정
	sbQuery.append("\n			,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) VOIL_NM					");
} else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//장소
	sbQuery.append("\n			,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) ACDNT_LOC_NM			");
}
	 sbQuery.append("\n           ,A.RACER_NO                                  RACER_NO          	");                                             
	 sbQuery.append("\n           ,D.NM_KOR                                    RACER_NM          	");
	 sbQuery.append("\n           ,D.RACER_GRD_CD                              RACER_GRD_CD      	");
	 sbQuery.append("\n           ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD) RACER_GRD_NM 	");
	 sbQuery.append("\n           ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')   RACE_DAY												");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '995', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT995 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '997', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT997 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '110', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT110 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '120', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT120 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '140', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT140 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '125', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT125 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '150', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT150 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '210', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT210 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '220', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT220 ");

	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '996', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT996 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '999', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT999 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '998', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT998 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '211', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT211 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '221', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT221 ");
	 
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '130', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT130 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '993', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT993 ");
	 sbQuery.append("\n           ,DECODE(A.VOIL_CD, '994', DECODE(A.VOIL_JO, '69', '―', TRIM(A.VOIL_JO) || '조') || DECODE(A.VOIL_HANG, NULL, '', TRIM(A.VOIL_HANG) || '항') || DECODE(A.VOIL_HO, NULL, '', TRIM(A.VOIL_HO) || '호')) CNT994 ");
	
	 
	 sbQuery.append("\n           ,F.MOT_NO										MOT_NO  ");
	 sbQuery.append("\n           ,F.BOAT_NO								BOAT_NO ");
	 sbQuery.append("\n           ,F.ST_MTHD_CD								ST_MTHD_CD  --(2) ");
	 sbQuery.append("\n         FROM     TBED_RACE_VOIL_ACT     A,                       ");                                                          
	 sbQuery.append("\n                  TBEB_RACE              B,                       ");                                                          
	 sbQuery.append("\n                  TBEB_RACE_DAY_ORD      C,                       ");                                                          
	 sbQuery.append("\n                  TBEC_RACER_MASTER      D,                       ");
	 sbQuery.append("\n                  TBED_RACE_ACDNT_STTS   E,                       ");
	 sbQuery.append("\n                  TBEB_ORGAN             F                        ");                                  
	 sbQuery.append("\n           WHERE   A.STND_YEAR  = B.STND_YEAR                     ");                                                    
	 sbQuery.append("\n           AND     A.MBR_CD         = B.MBR_CD                    ");                                                        
	 sbQuery.append("\n           AND     A.TMS            = B.TMS                       ");                                                        
	 sbQuery.append("\n           AND     A.DAY_ORD        = B.DAY_ORD                   ");                                                        
	 sbQuery.append("\n           AND     A.RACE_NO        = B.RACE_NO                   ");
	 sbQuery.append("\n           AND     A.STND_YEAR      = E.STND_YEAR(+)              ");                                                          
	 sbQuery.append("\n           AND     A.MBR_CD         = E.MBR_CD(+)                 ");                                                           
	 sbQuery.append("\n           AND     A.TMS            = E.TMS(+)                    ");                                                           
	 sbQuery.append("\n           AND     A.DAY_ORD        = E.DAY_ORD(+)                ");                                                           
	 sbQuery.append("\n           AND     A.RACE_NO        = E.RACE_NO(+)                ");
	 sbQuery.append("\n           AND     A.RACE_REG_NO    = E.RACE_REG_NO(+)            ");                                                               
	 sbQuery.append("\n           AND     A.RACER_NO       = E.RACER_NO(+)               ");
	 sbQuery.append("\n           AND     A.SEQ       	   = E.SEQ(+)              		 ");
	 sbQuery.append("\n           AND     A.STND_YEAR      = F.STND_YEAR(+)              ");                                                          
	 sbQuery.append("\n           AND     A.MBR_CD         = F.MBR_CD(+)                 ");                                                           
	 sbQuery.append("\n           AND     A.TMS            = F.TMS(+)                    ");                                                           
	 sbQuery.append("\n           AND     A.DAY_ORD        = F.DAY_ORD(+)                ");                                                           
	 sbQuery.append("\n           AND     A.RACE_NO        = F.RACE_NO(+)                ");
	 sbQuery.append("\n           AND     A.RACE_REG_NO    = F.RACE_REG_NO(+)            ");                                                               
	 sbQuery.append("\n           AND     A.STND_YEAR      = C.STND_YEAR                ");                                                         
	 sbQuery.append("\n           AND     A.MBR_CD         = C.MBR_CD                    ");                                                        
	 sbQuery.append("\n           AND     A.TMS            = C.TMS                       ");                                                        
	 sbQuery.append("\n           AND     A.DAY_ORD        = C.DAY_ORD                   ");                                                        
	 sbQuery.append("\n           AND     A.RACER_NO       = D.RACER_NO                  ");                                                        
	 sbQuery.append("\n           AND     A.STND_YEAR      = ?                      	 ");                                                        
	 sbQuery.append("\n           AND     A.MBR_CD         = ?                       	 ");                                                     
	 sbQuery.append("\n		      AND     A.RACE_DAY       BETWEEN ? AND ?		         ");
	 } else if ( "X".equals(ctx.get("SELECT_COND".trim()))  ) {
//누계
		      
		    sbQuery.append("	SELECT   ROWNUM             RACE_NUM    --일련번호		");
if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
//선수별조회(VOIL_JO_NM)
	 		sbQuery.append("\n	        ,A.RACER_NO         RACER_NO    --등록번호		");
	 		sbQuery.append("\n			,A.RACER_NM         RACER_NM    --선수명			");
	 		sbQuery.append("\n			,A.RACER_GRD_CD     RACER_GRD_CD--등급코드		");
	 		sbQuery.append("\n	        ,A.RACER_GRD_NM     RACER_GRD_NM--등급명			");	
} else if ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항별조회
			sbQuery.append("\n			,A.VOIL_JO_NM      VOIL_JO_NM    --조항명			");

} else if ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//기수별조회
			sbQuery.append("\n			,A.RACER_PERIO_NO   RACER_PERIO_NO --기수		");

} else if ( "4".equals(ctx.get("SEARCH_COND".trim()))  ) {
//회차별조회
			sbQuery.append("\n	        ,A.TMS         		TMS    		--회차			");
			sbQuery.append("\n			,A.RACE_DAY         RACE_DAY    --경주일자		");
			sbQuery.append("\n			,A.DAY_ORD     		DAY_ORD		--일차			");
			sbQuery.append("\n			,,DECODE(A.ST_MTHD_CD, '001', '(온)'||A.RACE_NO, A.RACE_NO)     		RACE_NO		--경주번호		");
} else if ( "5".equals(ctx.get("SEARCH_COND".trim()))  ) {
//경주별조회
			sbQuery.append("\n			,A.RACE_NO     		RACE_NO		--경주번호		");
} else if ( "6".equals(ctx.get("SEARCH_COND".trim()))  ) {
//일차별조회
	sbQuery.append("\n					,A.DAY_ORD     		DAY_ORD		--일차			");
} else if ( "7".equals(ctx.get("SEARCH_COND".trim()))  ) {
//등급별조회
	sbQuery.append("\n					,A.RACER_GRD_NM     RACER_GRD_NM --등급    		");
} else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//판정별조회
	sbQuery.append("\n					,A.VOIL_NM     		VOIL_NM 	 --판정    		");
} else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//장소별조회
	sbQuery.append("\n					,A.ACDNT_LOC_NM     ACDNT_LOC_NM --장소    		");
}
		   	sbQuery.append("\n	        ,A.CNT995			CNT995		--출주제외(I)		");
		   	sbQuery.append("\n	        ,A.CNT997           CNT997      --출주제외(II)	");
		   	sbQuery.append("\n	        ,A.CNT110			CNT110		--F				");
		   	sbQuery.append("\n	        ,A.CNT120    		CNT120		--L				");
		   	sbQuery.append("\n	        ,A.CNT140			CNT140		--실격			");
		   	sbQuery.append("\n	        ,A.CNT125     		CNT125		--반칙실격		");
		   	sbQuery.append("\n	        ,A.CNT150			CNT150		--반칙경고		");
		   	sbQuery.append("\n	        ,A.CNT210			CNT210		--경고			");
		   	sbQuery.append("\n	        ,A.CNT220			CNT220		--주의			");
		   	sbQuery.append("\n	        ,NVL(A.CNT995,0)+NVL(A.CNT997,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT110,0)+NVL(A.CNT120,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT140,0)+NVL(A.CNT125,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT150,0)+NVL(A.CNT210,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT220,0) 	SUM1		--소계1			");

		   	sbQuery.append("\n	        ,A.CNT996   		CNT996		--출주제외면책		");
		   	sbQuery.append("\n	        ,A.CNT999			CNT999    	--실격면책 		");
		   	sbQuery.append("\n	        ,A.CNT998    		CNT998		--결장면책		");
		   	sbQuery.append("\n	        ,A.CNT211			CNT211		--경고면책		");
		   	sbQuery.append("\n	        ,A.CNT221			CNT221		--주의면책		");
		   	sbQuery.append("\n	        ,NVL(A.CNT996,0)+NVL(A.CNT999,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT998,0)+NVL(A.CNT211,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT221,0) 	SUM2		--소계2			");
		   	
			sbQuery.append("\n	        ,A.CNT130   		CNT130		--결장			");
		   	sbQuery.append("\n	        ,A.CNT993			CNT993    	--F퇴소결장 		");
		   	sbQuery.append("\n	        ,A.CNT994    		CNT994		--L퇴소결장		");
		   	sbQuery.append("\n	        ,NVL(A.CNT130,0) + NVL(A.CNT993,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT994,0) 	SUM2_1		--소계2_1		");
		   
		   	sbQuery.append("\n	        ,NVL(A.CNT995,0)+NVL(A.CNT997,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT110,0)+NVL(A.CNT120,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT140,0)+NVL(A.CNT125,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT150,0)+NVL(A.CNT210,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT220,0)+NVL(A.CNT996,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT999,0)+NVL(A.CNT998,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT211,0)+NVL(A.CNT221,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT130,0)+NVL(A.CNT993,0)				");
		   	sbQuery.append("\n	        +NVL(A.CNT994,0)	SUM3--총계					");
		   	
		   	if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
//선수별조회
			sbQuery.append("\n	FROM ( SELECT A.RACER_NO                                RACER_NO   		                                        ");
		   	sbQuery.append("\n		      ,D.NM_KOR                                     RACER_NM			                                    ");
		   	sbQuery.append("\n		      ,D.RACER_GRD_CD								RACER_GRD_CD	                                        ");
		   	sbQuery.append("\n		      ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD) RACER_GRD_NM        ");
  
} else if ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항별조회
			sbQuery.append("\n	FROM ( SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) VOIL_JO_NM    		");
} else if ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//기수별조회
	sbQuery.append("\n	FROM ( SELECT 	  D.RACER_PERIO_NO                              RACER_PERIO_NO											");
}else if ( "4".equals(ctx.get("SEARCH_COND".trim()))  ) {
//회차별조회
			sbQuery.append("\n	FROM ( SELECT A.TMS                                		TMS   		                                       		");
		   	sbQuery.append("\n		      ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')    RACE_DAY			                                    ");
		   	sbQuery.append("\n		      ,A.DAY_ORD									DAY_ORD	                                       			");
		   	sbQuery.append("\n		      ,A.RACE_NO									RACE_NO													");
}else if ( "5".equals(ctx.get("SEARCH_COND".trim()))  ) {
//경주별조회
			sbQuery.append("\n	FROM ( SELECT A.RACE_NO								RACE_NO	  		                                       		");
}else if ( "6".equals(ctx.get("SEARCH_COND".trim()))  ) {
//일차별조회
			sbQuery.append("\n	FROM ( SELECT A.DAY_ORD								DAY_ORD	  		                                       		");
}else if ( "7".equals(ctx.get("SEARCH_COND".trim()))  ) {
//등급별조회
	sbQuery.append("\n	FROM ( SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD) RACER_GRD_NM  			");
}else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//판정조회
	sbQuery.append("\n	FROM ( SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) 		VOIL_NM 				");
}else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//장소별조회
	sbQuery.append("\n	FROM ( SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) 	ACDNT_LOC_NM  			");
}		   	
			sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '995', A.RACE_NO))   CNT995                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '997', A.RACE_NO))   CNT997                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '110', A.RACE_NO))   CNT110                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '120', A.RACE_NO))   CNT120                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '140', A.RACE_NO))   CNT140                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '125', A.RACE_NO))   CNT125                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '150', A.RACE_NO))   CNT150                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '210', A.RACE_NO))   CNT210                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '220', A.RACE_NO))   CNT220                                                  ");

		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '996', A.RACE_NO))   CNT996                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '999', A.RACE_NO))   CNT999                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '998', A.RACE_NO))   CNT998                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '211', A.RACE_NO))   CNT211                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '221', A.RACE_NO))   CNT221                                                  ");
		   	
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '130', A.RACE_NO))   CNT130                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '993', A.RACE_NO))   CNT993                                                  ");
		   	sbQuery.append("\n		      ,COUNT(DECODE(A.VOIL_CD, '994', A.RACE_NO))   CNT994                                                  ");
		   	sbQuery.append("\n            ,MAX(F.ST_MTHD_CD)							ST_MTHD_CD  --(3) ");
		   	sbQuery.append("\n		 	      FROM     TBED_RACE_VOIL_ACT     A,                                                                "); 
		   	sbQuery.append("\n		                   TBEB_RACE              B,                                                                "); 
		   	sbQuery.append("\n		                   TBEB_RACE_DAY_ORD      C,                                                                "); 
		   	sbQuery.append("\n		                   TBEC_RACER_MASTER      D,                                                                ");
		   	sbQuery.append("\n		                   TBED_RACE_ACDNT_STTS   E,                                                                ");
		   	sbQuery.append("\n		                   TBEB_ORGAN             F                                                                 ");
		   	sbQuery.append("\n		          WHERE   A.STND_YEAR  = B.STND_YEAR                                                                ");
		   	sbQuery.append("\n		          AND     A.MBR_CD         = B.MBR_CD                                                               ");
		   	sbQuery.append("\n		          AND     A.TMS            = B.TMS                                                                  ");
		   	sbQuery.append("\n		          AND     A.DAY_ORD        = B.DAY_ORD                                                              ");
		   	sbQuery.append("\n		          AND     A.RACE_NO        = B.RACE_NO                                                              ");
		   	sbQuery.append("\n		          AND     A.STND_YEAR      = E.STND_YEAR(+)                                                         ");
		   	sbQuery.append("\n		          AND     A.MBR_CD         = E.MBR_CD(+)                                                            ");
		   	sbQuery.append("\n		          AND     A.TMS            = E.TMS(+)                                                               ");
		   	sbQuery.append("\n		          AND     A.DAY_ORD        = E.DAY_ORD(+)                                                           ");
		   	sbQuery.append("\n		          AND     A.RACE_NO        = E.RACE_NO(+)                                                           ");
		   	sbQuery.append("\n		          AND     A.RACE_REG_NO    = E.RACE_REG_NO(+)                                                       ");    
		   	sbQuery.append("\n		          AND     A.RACER_NO       = E.RACER_NO(+)                                                          ");
		   	sbQuery.append("\n           	  AND     A.SEQ       	   = E.SEQ(+)              		 											");
		   	sbQuery.append("\n		          AND     A.STND_YEAR      = F.STND_YEAR(+)                                                         ");
		   	sbQuery.append("\n		          AND     A.MBR_CD         = F.MBR_CD(+)                                                            ");
		   	sbQuery.append("\n		          AND     A.TMS            = F.TMS(+)                                                               ");
		   	sbQuery.append("\n		          AND     A.DAY_ORD        = F.DAY_ORD(+)                                                           ");
		   	sbQuery.append("\n		          AND     A.RACE_NO        = F.RACE_NO(+)                                                           ");
		   	sbQuery.append("\n		          AND     A.RACE_REG_NO    = F.RACE_REG_NO(+)                                                       ");    
		   	sbQuery.append("\n		          AND     A.STND_YEAR      = C.STND_YEAR                                                           ");
		   	sbQuery.append("\n		          AND     A.MBR_CD         = C.MBR_CD                                                               ");
		   	sbQuery.append("\n		          AND     A.TMS            = C.TMS                                                                  ");
		   	sbQuery.append("\n		          AND     A.DAY_ORD        = C.DAY_ORD                                                              ");
		   	sbQuery.append("\n		          AND     A.RACER_NO       = D.RACER_NO                                                             ");
		   	sbQuery.append("\n		          AND     A.STND_YEAR      = ?                                                                 		");
		   	sbQuery.append("\n		          AND     A.MBR_CD         = ?                                                                  	");
		   	sbQuery.append("\n		          AND     A.RACE_DAY       BETWEEN ? AND ?                                                          ");
     }
	   
	
   //검색조건
	if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
	       sbQuery.append("\n  			  AND     A.VOIL_CD      NOT IN ('996','998','999','211','221')													");
	}
	   	
	   if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	선수별조회(RACER_NM)
		  	if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND D.NM_KOR LIKE NVL(?, D.NM_KOR) || '%'   																");
		    }
		  	//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		         ORDER BY  A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                              	");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		         ) A                                                                                                ");
 		  	}
		
	 	} else if ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	조항별(VOIL_JO_NM)
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO)) || '%'   ");
		    }
			//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("						  ,A.VOIL_JO																				");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  A.VOIL_JO                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  A.VOIL_JO                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY A.VOIL_JO                                                                                ");
				sbQuery.append("		         ORDER BY  A.VOIL_JO                                                                                ");
				sbQuery.append("		         ) A                                                                                                ");
 		  	}
			
		} else if ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	기수별
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND D.RACER_PERIO_NO LIKE NVL(?, D.RACER_PERIO_NO) || '%'   ");
		    }
			//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("						  ,D.RACER_PERIO_NO																			");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  D.RACER_PERIO_NO                                                                         ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  D.RACER_PERIO_NO                                                                         ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY D.RACER_PERIO_NO                                                                         ");
				sbQuery.append("		         ORDER BY  D.RACER_PERIO_NO                                                                         ");
				sbQuery.append("		         ) A                                                                                                ");
 		  	}
		} else if ( "4".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	회차별
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND A.TMS LIKE NVL(?, A.TMS) || '%'   																			");
		    }
		  	//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  A.TMS                                                                               		");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  A.TMS                                                                               		");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY A.TMS                                                                               		");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                             	");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                           		");
			   	sbQuery.append("						  ,A.RACE_NO																				");
			   	sbQuery.append("		         ORDER BY  A.TMS                                                                               		");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                           		");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                           		");
				sbQuery.append("		         ) A                                                                                                ");
 		  	}
		} else if ( "5".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	경주별
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND A.RACE_NO LIKE NVL(?, A.RACE_NO) || '%'   																	");
		    }
		  	//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.RACE_NO                                                                              	");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  A.RACE_NO                                                                               	");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                               		");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  A.RACE_NO                                                                               	");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                               		");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY A.RACE_NO                                                                               	");
			   	sbQuery.append("		         ORDER BY  A.RACE_NO                                                                               	");
			  	sbQuery.append("		         ) A                                                                                                ");
 		  	}
		} else if ( "6".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	일차별
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND A.DAY_ORD LIKE NVL(?, A.DAY_ORD) || '%'   																	");
		    }
		  	//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.DAY_ORD                                                                             	");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                               		");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  A.DAY_ORD                                                                              	");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                               		");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY A.DAY_ORD                                                                               	");
			   	sbQuery.append("		         ORDER BY  A.DAY_ORD                                                                               	");
			  	sbQuery.append("		         ) A                                                                                                ");
 		  	}
		} else if ( "7".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	등급별
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD)) || '%'   																	");
		    }
		  	//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                           		");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                               		");
			   	sbQuery.append("						  ,A.DAY_ORD																				");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                               		");
			   	sbQuery.append("						  ,A.DAY_ORD																				");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		         ORDER BY  D.RACER_GRD_CD                                                                           ");
			  	sbQuery.append("		         ) A                                                                                                ");
 		  	}
		} else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	판정별 
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD)) || '%'   ");
		    }
			//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("						  ,A.VOIL_CD																				");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  A.VOIL_CD                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  A.VOIL_CD                                                                                ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY A.VOIL_CD                                                                                ");
				sbQuery.append("		         ORDER BY  A.VOIL_CD                                                                                ");
				sbQuery.append("		         ) A                                                                                                ");
 		  	}	
		} else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//	장소별
			if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD)) || '%'   ");
		    }
			//횟수별
		  	if      ( "M".equals(ctx.get("SELECT_COND".trim()))  ) { 
			  	sbQuery.append("		          GROUP BY A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("						  ,A.ACDNT_LOC_CD																			");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')                                                ");
			   	sbQuery.append("		                  ,D.RACER_GRD_CD                                                                           ");
			   	sbQuery.append("		                  ,D.NM_KOR                                                                                 ");
			   	sbQuery.append("		                  ,F.MOT_NO                                                                                 ");
			   	sbQuery.append("	                      ,F.BOAT_NO                                                                                ");
			   	sbQuery.append("		         ORDER BY  A.ACDNT_LOC_CD                                                                           ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //조항별
		  	} else if  ( "W".equals(ctx.get("SELECT_COND".trim()))  ) { 
		  		sbQuery.append("		         ORDER BY  A.ACDNT_LOC_CD                                                                           ");
			   	sbQuery.append("		                  ,A.RACER_NO                                                                               ");
			   	sbQuery.append("		                  ,A.STND_YEAR                                                                              ");
			   	sbQuery.append("		                  ,A.MBR_CD                                                                                 ");
			   	sbQuery.append("		                  ,A.TMS                                                                                    ");
			   	sbQuery.append("		                  ,A.DAY_ORD                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_NO                                                                                ");
			   	sbQuery.append("		                  ,A.RACE_REG_NO                                                                            ");
			   	sbQuery.append("		         ) A                                                                                                ");
            //누계별
		  	} else if  ( "X".equals(ctx.get("SELECT_COND".trim()))  ) { 	
		  		sbQuery.append("		          GROUP BY A.ACDNT_LOC_CD                                                                           ");
				sbQuery.append("		         ORDER BY  A.ACDNT_LOC_CD                                                                           ");
				sbQuery.append("		         ) A                                                                                                ");
 		  	}
		 }
	   
if  ( "10".equals(ctx.get("SEARCH_COND".trim()))  ) {
//사고별조회
	sbQuery2.append("   			SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00062' AND CD =  ACDNT_TPE_CD)            ACDNT_TPE_NM,	"); 
	sbQuery2.append("\n	                    COUNT(*)                                                                                  SUMRACE,	   	"); 
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '01', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '01', 1 , 0 ))) RACENO01,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '02', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '02', 1 , 0 ))) RACENO02,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '03', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '03', 1 , 0 ))) RACENO03,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '04', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '04', 1 , 0 ))) RACENO04,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '05', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '05', 1 , 0 ))) RACENO05,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '06', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '06', 1 , 0 ))) RACENO06,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '07', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '07', 1 , 0 ))) RACENO07,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '08', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '08', 1 , 0 ))) RACENO08,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '09', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '09', 1 , 0 ))) RACENO09,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '10', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '10', 1 , 0 ))) RACENO10,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '11', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '11', 1 , 0 ))) RACENO11,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '12', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '12', 1 , 0 ))) RACENO12,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '13', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '13', 1 , 0 ))) RACENO13,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '14', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '14', 1 , 0 ))) RACENO14,    	");
	sbQuery2.append("\n	                    DECODE(SUM(DECODE( RACE_NO , '15', 1 , 0 )), 0, '', SUM(DECODE( RACE_NO , '15', 1 , 0 ))) RACENO15     	");
	sbQuery2.append("\n	               FROM   TBED_RACE_ACDNT_STTS																					");
	sbQuery2.append("\n	              WHERE   STND_YEAR = ?    																						");
	sbQuery2.append("\n	                AND   MBR_CD    = ?    																						");
	sbQuery2.append("\n	                AND   RACE_DAY  BETWEEN ? AND ?  																			");
	sbQuery2.append("\n	             GROUP BY ACDNT_TPE_CD          																				");	 

}
	   
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS_FROM          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS_TO            ".trim()));
       
		if  ( "10".equals(ctx.get("SEARCH_COND".trim()))  ) {
		//사고별조회
			PosRowSet rowset2 = this.getDao("boadao").findByQueryStatement(sbQuery2.toString(), param);
			String    sResultKey = "dsOutRace";
		    ctx.put(sResultKey, rowset2);
		    
		    Util.addResultKey(ctx, sResultKey);
		    Util.setSearchCount(ctx, rowset2.getAllRow().length);
		} else { 
			   //검색조건에서 LIKE 검색
			    if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        	  		param.setWhereClauseParameter(i++, ctx.get("SEARCH_VALUE      ".trim()));
		        }
			    PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
			    String    sResultKey = "dsOutRace";
		        ctx.put(sResultKey, rowset);
		        
		        Util.addResultKey(ctx, sResultKey);
		        Util.setSearchCount(ctx, rowset.getAllRow().length);
		}  
        
    }

}
