/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04090010.activity.SearchVoilDescDetailReport.java
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

public class SearchVoilDescDetailReport extends SnisActivity {
    public SearchVoilDescDetailReport()
    {
    }

    public String runActivity(PosContext ctx)
    {
    
    	SearchReportVoilDescDetail(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchReportVoilDescDetail(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
    	
    	sbQuery.append("SELECT  A.VOIL_CD             VOIL_CD 			");
    	sbQuery.append("\n	   ,'○ ' || (SELECT CD_NM VOIL_CD FROM TBEA_SPEC_CD WHERE GRP_CD = 'E00035' AND CD = A.VOIL_CD) || ' : ' || COUNT(A.VOIL_CD) || '건'  VOIL_COUNT "); 
    	sbQuery.append("\n	FROM TBED_RACE_VOIL_ACT A         			");                                                                         
    	sbQuery.append("\n WHERE A.STND_YEAR = ?         				");                                                                           
    	sbQuery.append("\n   AND A.MBR_CD    = ?        				");                                                                           
    	sbQuery.append("\n	 AND A.TMS       = ?            			");                                                                     
    	sbQuery.append("\n	 AND A.DAY_ORD   LIKE ? || '%' 				");                                                                       
  
    if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
        sbQuery.append("\n	 AND A.VOIL_CD NOT IN ('996','998','999','211','221')	");
    }
      	sbQuery.append("\n	 AND A.VOIL_CD     NOT IN ('210','220','211','221') 	");
    	sbQuery.append("\n	 GROUP BY A.VOIL_CD                     	");                                          
    	sbQuery.append("\n	 ORDER BY A.VOIL_CD							");
    	
    
    			PosParameter param = new PosParameter();
    	        int i = 0;
    	    	
    	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("TMS          	  ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD           ".trim()));
    	    
    	    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	    	String       sResultKey = "dsOutVoilDescDetail";
    	        ctx.put(sResultKey, rowset);
    	        
    	        Util.addResultKey(ctx, sResultKey);
    	        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
    }

}
