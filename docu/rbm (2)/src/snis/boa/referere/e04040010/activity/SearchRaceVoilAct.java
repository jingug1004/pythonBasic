/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04040010.activity.SearchRaceVoilAct.java
 * 파일설명		: 위반내용상세현황조회
 * 작성자			: 정의태
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04040010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRaceVoilAct extends SnisActivity {
    public SearchRaceVoilAct()
    {
    }
    public String runActivity(PosContext ctx)
    {
    	SearchVoilActRace(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchVoilActRace(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
    	
    	sbQuery.append(" SELECT  ROWNUM               RACE_NUM																				");
    	sbQuery.append("\n		,A.STND_YEAR          STND_YEAR																				");
    	sbQuery.append("\n		,A.MBR_CD             MBR_CD																				");
    	sbQuery.append("\n		,A.TMS                TMS    																				");
    	sbQuery.append("\n		,A.DAY_ORD            DAY_ORD																				");
    	sbQuery.append("\n		,DECODE(A.ST_MTHD_CD, '001', '(온)'||A.RACE_NO, A.RACE_NO)  RACE_NO											");
        sbQuery.append("\n		,A.RACE_REG_NO        RACE_REG_NO																			");	
        sbQuery.append("\n		,A.RACER_NO           RACER_NO																				");	
        sbQuery.append("\n		,A.RACE_DAY           RACE_DAY																				");	
        sbQuery.append("\n		,A.RACER_GRD_CD       RACER_GRD_CD																			");	
        sbQuery.append("\n		,A.RACER_GRD_NM       RACER_GRD_NM																			");	
        sbQuery.append("\n		,A.RACER_PERIO_NO     RACER_PERIO_NO																		");
        sbQuery.append("\n		,A.RACER_NM           RACER_NM																				");
        sbQuery.append("\n		,A.RACE_DGRE_NM       RACE_DGRE_NM																			");		
        sbQuery.append("\n		,A.ENTRY_COURSE       ENTRY_COURSE																			");		
        sbQuery.append("\n		,A.SEQ                SEQ																					");	
        sbQuery.append("\n		,A.CIRCUIT_CNT_CD     CIRCUIT_CNT_CD																		");		
        sbQuery.append("\n		,A.CIRCUIT_CNT_NM     CIRCUIT_CNT_NM																		");
        sbQuery.append("\n		,A.ACDNT_LOC_CD       ACDNT_LOC_CD																			");	
        sbQuery.append("\n		,A.ACDNT_LOC_NM       ACDNT_LOC_NM																			");	
        sbQuery.append("\n		,A.VOIL_JO            VOIL_JO																				");
        sbQuery.append("\n		,A.VOIL_JO_NM         VOIL_JO_NM																			");
        sbQuery.append("\n		,A.VOIL_HANG          VOIL_HANG																				");	
        sbQuery.append("\n		,A.VOIL_HO            VOIL_HO																				");
        sbQuery.append("\n		,A.VOIL_CD            VOIL_CD																				");		
        sbQuery.append("\n		,A.VOIL_NM            VOIL_NM																				");	
        sbQuery.append("\n		,A.VOIL_DESC          VOIL_DESC																				");	
        sbQuery.append("\n		,A.RMK                RMK																					");
        sbQuery.append("\n		,A.ACDNT_TPE_CD       ACDNT_TPE_CD																			");		
        sbQuery.append("\n		,A.ACDNT_TPE_NM       ACDNT_TPE_NM																			");		
        sbQuery.append("\n		FROM																										");
    	sbQuery.append("\n ( SELECT  ROWNUM										RACE_NUM													");
    	sbQuery.append("\n          ,A.STND_YEAR								STND_YEAR													");
    	sbQuery.append("\n          ,A.MBR_CD									MBR_CD                                                      ");
    	sbQuery.append("\n          ,A.TMS										TMS                                                         ");
    	sbQuery.append("\n          ,A.DAY_ORD									DAY_ORD                                                     ");
    	sbQuery.append("\n          ,A.RACE_NO									RACE_NO                                                     ");
    	sbQuery.append("\n          ,A.RACE_REG_NO								RACE_REG_NO                                                 ");
    	sbQuery.append("\n          ,A.RACER_NO									RACER_NO                                                    ");
    	sbQuery.append("\n          ,TO_CHAR(TO_DATE(C.RACE_DAY),'YYYY-MM-DD')  RACE_DAY                                                    ");
    	sbQuery.append("\n          ,NVL(G.RACER_GRD_CD,'B2')					RACER_GRD_CD                                                ");
    	sbQuery.append("\n          ,NVL((SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = G.RACER_GRD_CD),'B2') RACER_GRD_NM          ");
    	sbQuery.append("\n          ,D.RACER_PERIO_NO   						RACER_PERIO_NO                                              ");
    	sbQuery.append("\n          ,D.NM_KOR   								RACER_NM                                                    ");
    	sbQuery.append("\n          ,F.ENTRY_COURSE   							ENTRY_COURSE                                                ");
    	sbQuery.append("\n          ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00005' AND CD = B.RACE_DGRE_CD) RACE_DGRE_NM          ");
    	sbQuery.append("\n          ,A.SEQ										SEQ                                                         ");
    	sbQuery.append("\n          ,A.CIRCUIT_CNT_CD							CIRCUIT_CNT_CD                                              ");
    	sbQuery.append("\n          ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00045' AND CD = A.CIRCUIT_CNT_CD) CIRCUIT_CNT_NM      ");
    	sbQuery.append("\n          ,A.ACDNT_LOC_CD								ACDNT_LOC_CD                                                ");
    	sbQuery.append("\n          ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) ACDNT_LOC_NM          ");
    	sbQuery.append("\n          ,A.VOIL_JO									VOIL_JO                                                     ");
    	sbQuery.append("\n          ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) VOIL_JO_NM                 ");
    	sbQuery.append("\n          ,A.VOIL_HANG								VOIL_HANG                                                   ");
    	sbQuery.append("\n          ,A.VOIL_HO									VOIL_HO                                                     ");
    	sbQuery.append("\n          ,A.VOIL_CD									VOIL_CD                                                     ");
    	sbQuery.append("\n           ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) VOIL_NM                   ");
    	sbQuery.append("\n          ,A.VOIL_DESC								VOIL_DESC                                                   ");
    	sbQuery.append("\n          ,A.RMK										RMK                                                         ");
    	sbQuery.append("\n          ,E.ACDNT_TPE_CD								ACDNT_TPE_CD                                                ");
    	sbQuery.append("\n          ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00062' AND CD = E.ACDNT_TPE_CD) ACDNT_TPE_NM          ");
    	sbQuery.append("\n          ,F.ST_MTHD_CD								ST_MTHD_CD                                                  ");
    	sbQuery.append("\n  FROM     TBED_RACE_VOIL_ACT   A,                                                                                ");
    	sbQuery.append("\n           TBEB_RACE            B,                                                                                ");
    	sbQuery.append("\n           TBEB_RACE_DAY_ORD    C,                                                                                ");
    	sbQuery.append("\n           TBEC_RACER_MASTER    D,                                                                                ");
    	sbQuery.append("\n			 TBED_RACE_ACDNT_STTS E, 																				");
    	sbQuery.append("\n			 TBEB_ORGAN           F,																				");
    	sbQuery.append("\n			 VWEB_RACER_GRD_HIS   G     																			");
    	sbQuery.append("\n  WHERE  	A.STND_YEAR      = B.STND_YEAR                                                                          ");
    	sbQuery.append("\n  AND 	A.MBR_CD         = B.MBR_CD                                                                             ");
    	sbQuery.append("\n  AND 	A.TMS            = B.TMS                                                                                ");
    	sbQuery.append("\n  AND 	A.DAY_ORD        = B.DAY_ORD                                                                            ");
    	sbQuery.append("\n  AND 	A.RACE_NO        = B.RACE_NO                                                                            ");
		sbQuery.append("\n  AND    	A.STND_YEAR  	 = C.STND_YEAR                                                                          ");
    	sbQuery.append("\n  AND    	A.MBR_CD         = C.MBR_CD                                                                             ");
    	sbQuery.append("\n  AND    	A.TMS            = C.TMS                                                                                ");
    	sbQuery.append("\n  AND    	A.DAY_ORD        = C.DAY_ORD                                                                            ");
    	sbQuery.append("\n  AND    	A.RACER_NO       = D.RACER_NO                                                                           ");
    	sbQuery.append("\n  AND  	A.STND_YEAR      = F.STND_YEAR                                                                          ");
    	sbQuery.append("\n  AND 	A.MBR_CD         = F.MBR_CD                                                                             ");
    	sbQuery.append("\n  AND 	A.TMS            = F.TMS                                                                                ");
    	sbQuery.append("\n  AND 	A.DAY_ORD        = F.DAY_ORD                                                                            ");
    	sbQuery.append("\n  AND 	A.RACE_NO        = F.RACE_NO                                                                            ");
    	sbQuery.append("\n  AND 	A.RACE_REG_NO    = F.RACE_REG_NO                                                                        ");
    	sbQuery.append("\n  AND 	A.RACER_NO       = G.RACER_NO(+)                                                                        ");
    	sbQuery.append("\n  AND 	A.RACE_DAY       BETWEEN G.STR_DT(+) AND G.END_DT(+)                                                    ");
    	sbQuery.append("\n  AND     A.STND_YEAR      = E.STND_YEAR(+)                                                                       ");  
    	sbQuery.append("\n  AND     A.MBR_CD         = E.MBR_CD(+)                                                                          ");   
    	sbQuery.append("\n  AND     A.TMS            = E.TMS(+)                                                                             ");   
    	sbQuery.append("\n  AND     A.DAY_ORD        = E.DAY_ORD(+)                                                                         ");   
    	sbQuery.append("\n  AND     A.RACE_NO        = E.RACE_NO(+)																			");
    	sbQuery.append("\n  AND     A.RACE_REG_NO    = E.RACE_REG_NO(+)																		");
    	sbQuery.append("\n  AND     A.RACER_NO       = E.RACER_NO(+)																		");
//사고별
if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {    	
		sbQuery.append("\n  AND     A.SEQ       = E.SEQ(+)																					");
}
if ("0".equals(ctx.get("SEARCH_TERM".trim()))) {
    	sbQuery.append("\n  AND 	A.STND_YEAR      = ?                                                                               		");
}
    	sbQuery.append("\n  AND 	A.MBR_CD         = ?                                                                               		");
    	sbQuery.append("\n  AND 	A.RACE_DAY  BETWEEN ?  AND ?                                                                         	");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
    //996=>출주제외(면책),998==>결장면책,999==>실격(면책)	
	sbQuery.append("\n  AND      A.VOIL_CD      NOT IN ('996','998','999','211','221')                                                  	");
}

if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
//회차별(TMS)
  	if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
        sbQuery.append("\n AND A.TMS LIKE NVL(?, A.TMS) || '%'   																			");
    }
        sbQuery.append("\n  ORDER BY A.TMS                                                                                            		");
       	sbQuery.append("\n          ,A.STND_YEAR                                                                                            ");
       	sbQuery.append("\n          ,A.MBR_CD                                                                                               ");
       	sbQuery.append("\n          ,A.DAY_ORD                                                                                              ");
       	sbQuery.append("\n          ,A.RACE_NO                                                                                              ");
       	sbQuery.append("\n          ,A.RACE_REG_NO    ) A                                                                                   ");
       	
 	} else if ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {             
//선수별조회(RACER_NM)
 		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND D.NM_KOR LIKE NVL(?, D.NM_KOR) || '%'   																	");
 	    }
 	        sbQuery.append("\n  ORDER BY A.RACER_NO                                                                                         ");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	        sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");
  	
 	} else if ( "3".equals(ctx.get("SEARCH_COND".trim()))  ) {
//조항별(VOIL_JO_NM)
 		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO)) || '%'   ");
 	    }
 	        sbQuery.append("\n  ORDER BY A.VOIL_JO                    																		");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	       	sbQuery.append("\n          ,A.RACE_NO                                                                                      	");
 	    	sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");
	
 		
	} else if ( "4".equals(ctx.get("SEARCH_COND".trim()))  ) {
//경주일자별(RACE_DAY)
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND A.RACE_DAY LIKE NVL(?, A.RACE_DAY) || '%'   																");
 	    }
 	        sbQuery.append("\n  ORDER BY A.RACE_DAY                                                                                        	");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	       	sbQuery.append("\n          ,A.RACE_NO                                                                                      	");
 	    	sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");

	} else if ( "5".equals(ctx.get("SEARCH_COND".trim()))  ) {
//주회수별CIRCUIT_CNT_NM
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00045' AND CD = A.CIRCUIT_CNT_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00045' AND CD = A.CIRCUIT_CNT_CD)) || '%'   ");
 	    }
 	        sbQuery.append("\n  ORDER BY A.CIRCUIT_CNT_CD                 																	");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	       	sbQuery.append("\n          ,A.RACE_NO                                                                                      	");
 	    	sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");

	} else if ( "6".equals(ctx.get("SEARCH_COND".trim()))  ) {
//장소별ACDNT_LOC_NM
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD)) || '%'   ");
 	    }
 	        sbQuery.append("\n  ORDER BY A.ACDNT_LOC_CD                   																	");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	       	sbQuery.append("\n          ,A.RACE_NO                                                                                      	");
 	    	sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");
	
	} else if ( "7".equals(ctx.get("SEARCH_COND".trim()))  ) {
//등급별RACER_GRD_NM
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00046' AND CD = D.RACER_GRD_CD)) || '%'   ");
 	    }
 	        sbQuery.append("\n  ORDER BY D.RACER_GRD_CD                   																	");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	       	sbQuery.append("\n          ,A.RACE_NO                                                                                      	");
 	    	sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");

	} else if ( "8".equals(ctx.get("SEARCH_COND".trim()))  ) {
//일차별DAY_ORD
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
	        sbQuery.append("\n AND A.DAY_ORD LIKE NVL(?, A.DAY_ORD) || '%'   																		");
	    }
	        sbQuery.append("\n  ORDER BY A.DAY_ORD                                                                                          ");
	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
	       	sbQuery.append("\n          ,A.TMS                                                                                              ");
	       	sbQuery.append("\n          ,A.RACE_NO                                                                                          ");
	       	sbQuery.append("\n          ,A.RACE_REG_NO    ) A                                                                               ");

	} else if ( "9".equals(ctx.get("SEARCH_COND".trim()))  ) {
//사고별
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00062' AND CD = E.ACDNT_TPE_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00062' AND CD = E.ACDNT_TPE_CD)) || '%'   ");
 	    }
		    sbQuery.append("\n  AND     E.ACDNT_TPE_CD IS NOT NULL                  														");
		    sbQuery.append("\n  ORDER BY E.ACDNT_TPE_CD                   																	");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	       	sbQuery.append("\n          ,A.RACE_NO                                                                                      	");
 	    	sbQuery.append("\n          ,A.RACE_REG_NO  ) A                                                                                ");
	
	} else if ( "10".equals(ctx.get("SEARCH_COND".trim()))  ) {
//판정별VOIL_NM
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
 	        sbQuery.append("\n AND (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) LIKE NVL(?, (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD)) || '%'   ");
 	    }
 	        sbQuery.append("\n  ORDER BY A.VOIL_CD                   																		");
 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
 	       	sbQuery.append("\n          ,A.RACE_NO                                                                                      	");
 	    	sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");
	
	} else if ( "11".equals(ctx.get("SEARCH_COND".trim())) ) {
//경주별RACE_NO
		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
	        sbQuery.append("\n AND A.RACE_NO LIKE NVL(?, A.RACE_NO) || '%'   																");
	    }
	        sbQuery.append("\n  ORDER BY A.RACE_NO                                                                                          ");
	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
	       	sbQuery.append("\n          ,A.TMS                                                                                              ");
	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
	       	sbQuery.append("\n          ,A.RACE_REG_NO    ) A                                                                               ");

	} else if ( "12".equals(ctx.get("SEARCH_COND".trim()))  ) {             
//기수별조회(RACER_PERIO_NO)
		 		if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		 	        sbQuery.append("\n AND D.RACER_PERIO_NO LIKE NVL(?, D.RACER_PERIO_NO) || '%'   													");
		 	    }
		 	        sbQuery.append("\n  ORDER BY D.RACER_PERIO_NO                                                                                   ");
		 	       	sbQuery.append("\n          ,A.STND_YEAR                                                                                        ");
		 	       	sbQuery.append("\n          ,A.MBR_CD                                                                                           ");
		 	       	sbQuery.append("\n          ,A.TMS                                                                                         		");
		 	       	sbQuery.append("\n          ,A.DAY_ORD                                                                                          ");
		 	        sbQuery.append("\n          ,A.RACE_REG_NO   ) A                                                                                ");
		  	
	}			
    
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        if ("0".equals(ctx.get("SEARCH_TERM".trim()))) {
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
        }
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

