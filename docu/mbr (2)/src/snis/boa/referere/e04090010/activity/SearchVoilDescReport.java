/*================================================================================
 * �ý���			: ���ǰ���
 * �ҽ����� �̸�	: snis.boa.referere.e04090010.activity.SearchVoilDescReport.java
 * ���ϼ���		: ���ְ������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.referere.e04090010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchVoilDescReport extends SnisActivity {
    public SearchVoilDescReport()
    {
    }

    public String runActivity(PosContext ctx)
    {
    
    	SearchReportVoilDesc(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchReportVoilDesc(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
    	
    	
    	sbQuery.append(" SELECT TRIM(A.VOIL_CD)        VOIL_CD																	");
    	sbQuery.append("\n 	,(SELECT CD_NM VOIL_CD FROM TBEA_SPEC_CD WHERE GRP_CD = 'E00035' AND CD = A.VOIL_CD) VOIL_NM		");
    	sbQuery.append("\n 	,'  - ' || A.RACE_NO ||'����' || ' ' || A.RACE_REG_NO || 'ȣ��' || ' ' || '(' || 						");
    	sbQuery.append("\n 	(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO=  A.RACER_NO) ||								");
    	sbQuery.append("\n 	')'|| ' ' || A.VOIL_DESC || '  ' 																	");
    	sbQuery.append("\n 	|| (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00048' AND CD = A.VOIL_JO) 						");
    	sbQuery.append("\n 	|| '�� �ǰ� ' 																							");
    	sbQuery.append("\n	|| (SELECT CD_NM VOIL_CD FROM TBEA_SPEC_CD WHERE GRP_CD = 'E00035' AND CD = A.VOIL_CD) VOIL_DESC	");
    	sbQuery.append("\n	FROM TBED_RACE_VOIL_ACT A 																			");
    	sbQuery.append("\n	WHERE A.STND_YEAR = ?																				");
    	sbQuery.append("\n	AND A.MBR_CD      = ?																				");
    	sbQuery.append("\n	AND A.TMS         = ?																				");
    	sbQuery.append("\n	AND A.DAY_ORD     LIKE ? || '%'																		");
    	sbQuery.append("\n	AND A.VOIL_CD     NOT IN ('210','220','211','221')																");
if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
        sbQuery.append("\n	AND A.VOIL_CD      NOT IN ('996','998','999','211','221')                                                  		");
}
    	sbQuery.append("\n	ORDER BY     A.VOIL_CD																				");
    
    	
    	    	PosParameter param = new PosParameter();
    	        int i = 0;
    	    	
    	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("TMS          	  ".trim()));
    	        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD           ".trim()));
    	    
    	    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	    	String       sResultKey = "dsOutVoilDesc";
    	        ctx.put(sResultKey, rowset);
    	        
    	        Util.addResultKey(ctx, sResultKey);
    	        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
    }

}
