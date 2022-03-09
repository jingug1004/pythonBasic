/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.boa.racer.e03060010.activity.SearchRacerPrz.java
 * ���ϼ���		: ��/���󼱼� ��ȸ
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2008-01-17
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03060010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRacerPrz extends SnisActivity {
    public SearchRacerPrz()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	searchRacerAwd(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void searchRacerAwd(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();

    	sbQuery.append("\n SELECT 																																					");
    	sbQuery.append("\n                   PRZ.STND_YEAR    -- ���س⵵					");
    	sbQuery.append("\n                 , PRZ.RACE_DAY     -- ��������					");
    	sbQuery.append("\n                 , PRZ.RACER_NO     -- ������ȣ					");
    	sbQuery.append("\n                 , TRM.NM_KOR       -- ����					");
    	sbQuery.append("\n                 , PRZ.RANK         -- ����					");
    	sbQuery.append("\n                 , PRZ.AWARD_AMT    -- ��ݾ�					");
    	sbQuery.append("\n                 , PRZ.RMK          -- ���					");
    	sbQuery.append("\n                 , PRZ.GBN          -- ��/���� ���� 				");
    	sbQuery.append("\n FROM                                                         ");
    	sbQuery.append("\n     (                                                        ");
    	sbQuery.append("\n         SELECT /*�Ի�*/										");
    	sbQuery.append("\n                   TRT .STND_YEAR    -- ���س⵵				");
    	sbQuery.append("\n                 , TRDO.RACE_DAY     -- ��������				");
    	sbQuery.append("\n                 , TOR .RACER_NO     -- ������ȣ				");
    	sbQuery.append("\n                 , TOR .RANK         -- ����					");
    	sbQuery.append("\n                 , NULL AS AWARD_AMT	-- ��ݾ�            	");
    	sbQuery.append("\n                 , TRT .TMS_NM AS RMK   -- ȸ����					");
    	//sbQuery.append("\n                 , SUBSTR(TRT .RMK,INSTR(TRT.RMK,',')+1, LENGTH(TRT .RMK))  RMK          -- ���					");
    	sbQuery.append("\n                 , '002' AS GBN      -- ��/���� ����				");
    	sbQuery.append("\n         FROM     TBEB_RACE_TMS     TRT						");
    	sbQuery.append("\n                , TBEB_RACE_DAY_ORD TRDO						");
    	sbQuery.append("\n                , TBEB_RACE         TR						");
    	sbQuery.append("\n                , TBEB_ORGAN        TOR						");
    	sbQuery.append("\n                , TBEB_CFRNT_METH   TCM						");
    	sbQuery.append("\n         WHERE  TRT .STND_YEAR       = TRDO.STND_YEAR			");
    	sbQuery.append("\n         AND    TRT .TMS             = TRDO.TMS				");
    	sbQuery.append("\n         AND    TRDO.STND_YEAR       = TR  .STND_YEAR			");
    	sbQuery.append("\n         AND    TRDO.TMS             = TR  .TMS				");
    	sbQuery.append("\n         AND    TRDO.DAY_ORD         = TR  .DAY_ORD			");
    	sbQuery.append("\n         AND    TR  .STND_YEAR       = TOR .STND_YEAR			");
    	sbQuery.append("\n         AND    TR  .TMS             = TOR .TMS				");
    	sbQuery.append("\n         AND    TR  .DAY_ORD         = TOR .DAY_ORD			");
    	sbQuery.append("\n         AND    TR  .RACE_NO         = TOR .RACE_NO			");
    	sbQuery.append("\n         AND    TOR .RANK      BETWEEN 1						");
    	sbQuery.append("\n                                   AND 3						");
    	sbQuery.append("\n         AND    TCM.RACE_KINDS_CD     != '001'				");
    	sbQuery.append("\n         AND    TRT.CFRNT_CD            = TCM.CFRNT_CD		");
    	sbQuery.append("\n         AND    TR.RACE_DGRE_CD      like '9%'				"); 
    	sbQuery.append("\n         UNION												");
    	sbQuery.append("\n         SELECT  /*����*/										");
    	sbQuery.append("\n                   SUBSTR(AWARD_DT,0,4) AS STND_YEAR	-- ���س⵵		");
    	sbQuery.append("\n                 , AWARD_DT AS RACE_DAY				-- ��������     	");
    	sbQuery.append("\n                 , RACER_NO AS RACER_NO				-- ������ȣ        	");
    	sbQuery.append("\n                 , NULL AS RANK							-- ����            	");
    	sbQuery.append("\n                 , AWARD_AMT AS AWARD_AMT				-- ��ݾ�            	");
    	sbQuery.append("\n                 , AWARD_DESC AS RMK					-- ���             	");
    	sbQuery.append("\n                 , '001' AS GBN						-- ��/���� ����    	");
    	sbQuery.append("\n         FROM TBEG_RACER_AWARD_HIS TRAH						");
    	sbQuery.append("\n         ORDER BY RACE_DAY DESC								");
    	sbQuery.append("\n                , RANK      ASC								");
    	sbQuery.append("\n                , RMK DESC									");
    	sbQuery.append("\n     ) PRZ,													");
    	sbQuery.append("\n     TBEC_RACER_MASTER TRM									");
    	sbQuery.append("\n     WHERE PRZ.RACER_NO = TRM.RACER_NO						");
    	sbQuery.append("\n     AND PRZ.STND_YEAR 	= NVL(?, PRZ.STND_YEAR)				");
    	if (ctx.get("GBN         ".trim()) != null && !ctx.get("GBN         ".trim()).equals("")){
    	sbQuery.append("\n     AND PRZ.GBN 			= NVL(?, PRZ.GBN)					");
    	}
    	if (ctx.get("RMK         ".trim()) != null && !ctx.get("RMK         ".trim()).equals("")){
    	sbQuery.append("\n     AND PRZ.RMK LIKE NVL('%'||?||'%', PRZ.RMK)				");   
    	}
    	if (ctx.get("NM_KOR         ".trim()) != null && !ctx.get("NM_KOR         ".trim()).equals("")){
    	sbQuery.append("\n     AND TRM.NM_KOR LIKE NVL(?, TRM.NM_KOR) || '%'			");
    	}
    	sbQuery.append("\n     ORDER BY PRZ.RACE_DAY DESC, GBN, RANK, AWARD_AMT DESC, RACER_NO	");
    	
    	
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
    	if (ctx.get("GBN         ".trim()) != null && !ctx.get("GBN         ".trim()).equals("")){
        param.setWhereClauseParameter(i++, ctx.get("GBN         ".trim()));
    	}
    	if (ctx.get("RMK         ".trim()) != null && !ctx.get("RMK         ".trim()).equals("")){
    		param.setWhereClauseParameter(i++, ctx.get("RMK         ".trim()));
    	}
    	if (ctx.get("NM_KOR         ".trim()) != null && !ctx.get("NM_KOR         ".trim()).equals("")){
    		param.setWhereClauseParameter(i++, ctx.get("NM_KOR         ".trim()));
    	}    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutRacerPrz";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    }
}
