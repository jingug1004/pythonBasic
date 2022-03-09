/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04090010.activity.SearchVoilReport.java
 * 파일설명		: 경주결과보고
 * 작성자			: 정의태
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04090010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchVoilReport extends SnisActivity {
    public SearchVoilReport()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	SearchReportVoil(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchReportVoil(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
    	sbQuery.append("   SELECT   ACDNT_TPE_NM,																								");
    	sbQuery.append("\n          MAX(TODAY_SUMRACE) TODAY_SUMRACE,                                                                           ");
    	sbQuery.append("\n          MAX(PREV_SUMRACE)  PREV_SUMRACE,                                                                            ");
    	sbQuery.append("\n          ROUND(  DECODE(MAX(PREV_SUMRACE),0,0, -(100 - ((MAX(TODAY_SUMRACE)/MAX(PREV_SUMRACE)) * 100))),2) RISE_RATE ");
    	sbQuery.append("\n FROM(                                                                                                                ");
    	sbQuery.append("\n         SELECT   '  소계'                    ACDNT_TPE_NM,                                                           	");
    	sbQuery.append("\n                 COUNT(*)                    TODAY_SUMRACE,                                                           ");
    	sbQuery.append("\n                 0                           PREV_SUMRACE                                                             ");
    	sbQuery.append("\n           FROM  TBED_RACE_VOIL_ACT                                                                                   ");
    	sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                             		");
    	sbQuery.append("\n          AND   MBR_CD         = ?                                                                                	");
    	sbQuery.append("\n          AND   TMS            = ?                                                                                   	");
    	sbQuery.append("\n          AND   DAY_ORD        LIKE ? || '%'                                                                          ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
    	sbQuery.append("\n AND   VOIL_CD      NOT IN ('996','998','999','211','221')                                                  						");
}
    	sbQuery.append("\n         UNION ALL                                                                                                    ");
    	sbQuery.append("\n         SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD =  VOIL_CD)   ACDNT_TPE_NM,            ");
    	sbQuery.append("\n                 COUNT(*)                    TODAY_SUMRACE,                                                           ");
    	sbQuery.append("\n                 0                           PREV_SUMRACE                                                             ");
    	sbQuery.append("\n          FROM   TBED_RACE_VOIL_ACT                                                                                   ");
    	sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                               		");
    	sbQuery.append("\n          AND   MBR_CD         = ?                                                                                	");
    	sbQuery.append("\n          AND   TMS            = ?                                                                                   	");
    	sbQuery.append("\n          AND   DAY_ORD        LIKE ? || '%'                                                                          ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
        sbQuery.append("\n AND   VOIL_CD      NOT IN ('996','998','999','211','221')                                                  						");
}
    	sbQuery.append("\n          GROUP BY VOIL_CD                                                                                            ");
    	sbQuery.append("\n        UNION ALL                                                                                                     ");
    	sbQuery.append("\n       SELECT '  소계'                      ACDNT_TPE_NM,                                                             	");
    	sbQuery.append("\n               0                            TODAY_SUMRACE,                                                            ");
    	sbQuery.append("\n               COUNT(*)                     PREV_SUMRACE                                                              ");
    	sbQuery.append("\n             FROM   TBED_RACE_VOIL_ACT                                                                                ");
    	sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                            		");
    	sbQuery.append("\n          AND   MBR_CD         = ?                                                                                	");
    	sbQuery.append("\n          AND   TMS            = TO_NUMBER(?) -1                                                                     	");
    	sbQuery.append("\n          AND   DAY_ORD        LIKE ? || '%'                                                                          ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
        sbQuery.append("\n AND   VOIL_CD      NOT IN ('996','998','999','211','221')                                                  						");
}
    	sbQuery.append("\n         UNION ALL                                                                                                    ");
    	sbQuery.append("\n         SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD =  VOIL_CD)       ACDNT_TPE_NM,        ");
    	sbQuery.append("\n                  0                         TODAY_SUMRACE,                                                            ");
    	sbQuery.append("\n                 COUNT(*)                   PREV_SUMRACE                                                              ");
    	sbQuery.append("\n          FROM   TBED_RACE_VOIL_ACT                                                                                   ");
    	sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                               		");
    	sbQuery.append("\n          AND   MBR_CD         = ?                                                                                	");
    	sbQuery.append("\n          AND   TMS            = TO_NUMBER(?) -1                                                                     	");
    	sbQuery.append("\n          AND   DAY_ORD        LIKE ? || '%'                                                                          ");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
        sbQuery.append("\n AND   VOIL_CD      NOT IN ('996','998','999','211','221')                                                  						");
}
    	sbQuery.append("\n          GROUP BY VOIL_CD                                                                                            ");
    	sbQuery.append("\n   )       							 																				");
    	sbQuery.append("\n   GROUP BY ACDNT_TPE_NM  																							");
    	sbQuery.append("\n   ORDER BY ACDNT_TPE_NM  																							");
    	
    	    	PosParameter param = new PosParameter();
    	        int i = 0;
    	    	
    	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("TMS          	  ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD           ".trim()));
    	    
    	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
  	            param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
  	            param.setWhereClauseParameter(i++, ctx.get("TMS          	  ".trim()));
  	            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD           ".trim()));
  	           	       	
  	           param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
  	           param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
  	           param.setWhereClauseParameter(i++, ctx.get("TMS          	 ".trim()));
  	           param.setWhereClauseParameter(i++, ctx.get("DAY_ORD           ".trim()));
  	       
	           param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
	           param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
	           param.setWhereClauseParameter(i++, ctx.get("TMS          	 ".trim()));
	           param.setWhereClauseParameter(i++, ctx.get("DAY_ORD           ".trim()));
	        
    	        
    	    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	    	String       sResultKey = "dsOutVoil";
    	        ctx.put(sResultKey, rowset);
    	        
    	        Util.addResultKey(ctx, sResultKey);

    	        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
 
    }

}