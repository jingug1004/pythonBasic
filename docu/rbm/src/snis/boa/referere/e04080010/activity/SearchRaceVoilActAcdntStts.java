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
package snis.boa.referere.e04080010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRaceVoilActAcdntStts extends SnisActivity {
   public SearchRaceVoilActAcdntStts()
   {
   }

   public String runActivity(PosContext ctx)
   {
   	SearchVoilActAcdntSttsRace(ctx);
       
       return PosBizControlConstants.SUCCESS;
   }
   
   protected void SearchVoilActAcdntSttsRace(PosContext ctx) 
   {
   	StringBuffer sbQuery = new StringBuffer();

if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
//위반내역조회
		 	sbQuery.append("       SELECT   A.STND_YEAR																								");
			sbQuery.append("\n             ,A.MBR_CD                                                                                            ");
			sbQuery.append("\n             ,A.TMS                                                                                               ");
			sbQuery.append("\n             ,A.DAY_ORD                                                                                           ");
			sbQuery.append("\n             ,DECODE(FC_GET_STMTHDCD(A.STND_YEAR, A.MBR_CD, A.TMS, A.DAY_ORD, A.RACE_NO), '001', '(온)','')||A.RACE_NO RACE_NO ");
			sbQuery.append("\n             ,A.RACE_REG_NO                                                                                       ");
			sbQuery.append("\n             ,A.RACER_NO                                                                                          ");
			sbQuery.append("\n             ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) RACER_NM                        ");
			sbQuery.append("\n             ,A.SEQ                                                                                               ");
			sbQuery.append("\n             ,A.CIRCUIT_CNT_CD                                                                                    ");
			sbQuery.append("\n             ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00045' AND CD = A.CIRCUIT_CNT_CD) CIRCUIT_CNT_NM   ");
			sbQuery.append("\n             ,A.ACDNT_LOC_CD                                                                                      ");
			sbQuery.append("\n             ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) ACDNT_LOC_NM       ");
			sbQuery.append("\n             ,A.VOIL_JO                                                                                           ");
			sbQuery.append("\n             ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) VOIL_JO_NM              ");
			sbQuery.append("\n             ,A.VOIL_HANG                                                                                         ");
			sbQuery.append("\n             ,A.VOIL_HO                                                                                           ");
			sbQuery.append("\n             ,A.VOIL_CD                                                                                           ");
			sbQuery.append("\n             ,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD = A.VOIL_CD) VOIL_NM                 ");
			sbQuery.append("\n             ,A.VOIL_DESC                                                                                         ");
			sbQuery.append("\n             ,A.RMK                                                                                               ");
			sbQuery.append("\n             ,A.INST_ID                                                                                           ");
			sbQuery.append("\n             ,A.INST_DTM                                                                                          ");
			sbQuery.append("\n             ,A.UPDT_ID                                                                                           ");
			sbQuery.append("\n             ,A.UPDT_DTM                                                                                          ");
			sbQuery.append("\n   FROM   TBED_RACE_VOIL_ACT A                                                                                    ");
			sbQuery.append("\n    WHERE  A.STND_YEAR    = ?                                                                                  	");
			sbQuery.append("\n 	  AND A.MBR_CD          = ?                                                                                     ");
if ( "0".equals(ctx.get("CHK".trim()))) {
			sbQuery.append("\n 	  AND A.TMS             = ?                                                                                     ");
} else {
			sbQuery.append("\n 	  AND A.TMS             IN (SELECT  TMS																			");
			sbQuery.append("\n                              FROM  ( SELECT  DISTINCT TMS														");
			sbQuery.append("\n                                      FROM    TBED_RACE_VOIL_ACT TRVA												"); 
			sbQuery.append("\n 											   ,TBEC_RACER_MASTER TRM												");
			sbQuery.append("\n                                      WHERE   TRVA.RACER_NO = TRM.RACER_NO										");
			sbQuery.append("\n                                      AND     TRVA.STND_YEAR    =  ?												");
			sbQuery.append("\n                                      AND     TRVA.MBR_CD       =  ?												");
			sbQuery.append("\n                                      AND     TRVA.TMS          <= ?												");
			sbQuery.append("\n                                      AND     TRM .NM_KOR       =  ?												");
			sbQuery.append("\n                                      ORDER BY TMS DESC)															");  
			sbQuery.append("\n                              WHERE   ROWNUM < 7)																	");                                                                    
}
//선수별조회(RACER_NM)
if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
			sbQuery.append("\n AND (SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) LIKE NVL(?, (SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO)) || '%'   ");
}			
			sbQuery.append("\n      ORDER BY A.STND_YEAR                                                                                        ");
			sbQuery.append("\n             ,A.MBR_CD                                                                                            ");
			sbQuery.append("\n             ,A.TMS                                                                                               ");
			sbQuery.append("\n             ,A.DAY_ORD                                                                                           ");
			sbQuery.append("\n             ,A.RACE_NO                                                                                           ");
			sbQuery.append("\n             ,A.RACE_REG_NO                                                                                       ");
			sbQuery.append("\n             ,A.SEQ 																								");
			  
} else if ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//사고내역조회
			sbQuery.append("\n    SELECT     																								 ");  
			sbQuery.append("\n 		 A.STND_YEAR                                                                                             ");
			sbQuery.append("\n 		,A.MBR_CD                                                                                                ");
			sbQuery.append("\n 		,A.TMS                                                                                                   ");
			sbQuery.append("\n 		,A.DAY_ORD                                                                                               ");
			sbQuery.append("\n 		,DECODE(FC_GET_STMTHDCD(A.STND_YEAR, A.MBR_CD, A.TMS, A.DAY_ORD, A.RACE_NO), '001', '(온)','')||A.RACE_NO RACE_NO ");
			sbQuery.append("\n 		,A.RACE_REG_NO                                                                                           ");
			sbQuery.append("\n 		,A.RACER_NO                                                                                              ");
			sbQuery.append("\n 		,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) RACER_NM                            ");
			sbQuery.append("\n 		,A.SEQ                                                                                                   ");
			sbQuery.append("\n 		,A.CIRCUIT_CNT_CD                                                                                        ");
			sbQuery.append("\n 		,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00045' AND CD = A.CIRCUIT_CNT_CD) CIRCUIT_CNT_NM       ");
			sbQuery.append("\n 		,A.ACDNT_LOC_CD                                                                                          ");
			sbQuery.append("\n 		,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00034' AND CD = A.ACDNT_LOC_CD) ACDNT_LOC_NM           ");
			sbQuery.append("\n 		,A.ACDNT_ITEM                                                                                            ");
			sbQuery.append("\n 		,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.ACDNT_ITEM) ACDNT_ITEM_NM            ");
			sbQuery.append("\n 		,A.ACDNT_TPE_CD                                                                                          ");
			sbQuery.append("\n 		,(SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00062' AND CD = A.ACDNT_TPE_CD) ACDNT_TPE_NM           ");
			sbQuery.append("\n 		,A.ACDNT_DESC                                                                                            ");
			sbQuery.append("\n 		,A.RMK                                                                                                   ");
			sbQuery.append("\n 		,A.INST_ID                                                                                               ");
			sbQuery.append("\n 		,A.INST_DTM                                                                                              ");
			sbQuery.append("\n 		,A.UPDT_ID                                                                                               ");
			sbQuery.append("\n 		,A.UPDT_DTM                                                                                              ");
			sbQuery.append("\n   FROM   TBED_RACE_ACDNT_STTS A                                                                               ");
			sbQuery.append("\n   WHERE  A.STND_YEAR     = ?                                                                           		");
			sbQuery.append("\n 	  AND A.MBR_CD          = ?                                                                              	");
if ( "0".equals(ctx.get("CHK".trim()))) {
			sbQuery.append("\n 	  AND A.TMS             = ?                                                                                     ");
} else {
			sbQuery.append("\n 	  AND A.TMS             IN (SELECT  TMS																			");
			sbQuery.append("\n                              FROM  ( SELECT  DISTINCT TMS														");
			sbQuery.append("\n                                      FROM    TBED_RACE_ACDNT_STTS TRAS												"); 
			sbQuery.append("\n 											   ,TBEC_RACER_MASTER TRM												");
			sbQuery.append("\n                                      WHERE   TRAS.RACER_NO = TRM.RACER_NO										");
			sbQuery.append("\n                                      AND     TRAS.STND_YEAR    =  ?												");
			sbQuery.append("\n                                      AND     TRAS.MBR_CD       =  ?												");
			sbQuery.append("\n                                      AND     TRAS.TMS          <= ?												");
			sbQuery.append("\n                                      AND     TRM .NM_KOR       =  ?												");
			sbQuery.append("\n                                      ORDER BY TMS DESC)															");  
			sbQuery.append("\n                              WHERE   ROWNUM < 7)																	");                                                                    
}
//선수별조회(RACER_NM)
if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
		        sbQuery.append("\n AND (SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) LIKE NVL(?, (SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO)) || '%'   ");
}
			
			sbQuery.append("\n    ORDER BY A.STND_YEAR                                                                                       ");
			sbQuery.append("\n             ,A.MBR_CD                                                                                         ");
			sbQuery.append("\n             ,A.TMS                                                                                            ");
			sbQuery.append("\n             ,A.DAY_ORD                                                                                        ");
			sbQuery.append("\n             ,A.RACE_NO                                                                                        ");
			sbQuery.append("\n             ,A.RACE_REG_NO                                                                                    ");
			sbQuery.append("\n             ,A.SEQ                                                                                            ");
	
}
		  	
   	   PosParameter param = new PosParameter();
       int i = 0;
   	
       param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
       param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
if ( "0".equals(ctx.get("CHK".trim()))) {
       param.setWhereClauseParameter(i++, ctx.get("TMS		         ".trim()));
} else {
       param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
       param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
       param.setWhereClauseParameter(i++, ctx.get("TMS		         ".trim()));
	   param.setWhereClauseParameter(i++, ctx.get("SEARCH_VALUE      ".trim()));
}
//검색조건에서 LIKE 검색
if(ctx.get("SEARCH_VALUE".trim()) != null && !"".equals(ctx.get("SEARCH_VALUE".trim()))){
	   param.setWhereClauseParameter(i++, ctx.get("SEARCH_VALUE      ".trim()));
}
  
      PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
      String       sResultKey ="";
if  ( "1".equals(ctx.get("SEARCH_COND".trim()))  ) {
//위반내역조회
	  sResultKey = "dsOutVoilAct";
} else if ( "2".equals(ctx.get("SEARCH_COND".trim()))  ) {
//사고내역조회
	  sResultKey = "dsOutAcdntStts";
}
      
      ctx.put(sResultKey, rowset);
       
      Util.addResultKey(ctx, sResultKey);

      Util.setSearchCount(ctx, rowset.getAllRow().length);
   }

}