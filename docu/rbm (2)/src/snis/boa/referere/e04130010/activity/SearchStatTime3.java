/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04130010.activity.SearchStatTime3.java
 * 파일설명		: 소개항주타임  분석
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-03 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04130010.activity;

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

    	sbQuery.append("   SELECT A.STND_YEAR				STND_YEAR												");
    	sbQuery.append("\n        ,A.MBR_CD                 MBR_CD                                                  ");
    	sbQuery.append("\n        ,A.TMS                    TMS                                                     ");
    	sbQuery.append("\n        ,A.DAY_ORD                DAY_ORD                                                 ");
    	sbQuery.append("\n        ,A.RACE_NO                RACE_NO                                                 ");
    	sbQuery.append("\n        ,SUBSTR(A.MOT_NO,6,3)     MOT_NO                                                  ");
    	sbQuery.append("\n        ,SUBSTR(A.BOAT_NO,6,3)    BOAT_NO                                                 ");
    	sbQuery.append("\n        ,A.RACER_NO               RACER_NO                                                ");
    	sbQuery.append("\n        ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE   RACER_NO= A.RACER_NO) RACER_NM     ");
    	sbQuery.append("\n        ,TO_CHAR(ROUND(A.INTRO_RUN_TM,2),'0.00') INTRO_RUN_TM                             ");
    	sbQuery.append("\n FROM TBEB_ORGAN A,TBEB_RACE_DAY_ORD B                                                    ");
    	sbQuery.append("\n WHERE A.STND_YEAR = B.STND_YEAR                                                          ");
    	sbQuery.append("\n   AND A.MBR_CD    = B.MBR_CD                                                             ");
    	sbQuery.append("\n   AND A.TMS       = B.TMS                                                                ");
    	sbQuery.append("\n   AND A.DAY_ORD   = B.DAY_ORD                                                            ");
    	sbQuery.append("\n   AND B.RACE_DAY   BETWEEN ? AND ?                                                       ");
    	sbQuery.append("\n   AND( A.INTRO_RUN_TM <= 6.50 OR A.INTRO_RUN_TM >= 7.00)                                 ");
    	sbQuery.append("\n   ORDER BY  A.STND_YEAR																	");
    	sbQuery.append("\n   ,A.MBR_CD 																				");
    	sbQuery.append("\n   ,A.TMS																					");
    	sbQuery.append("\n   ,A.DAY_ORD																				");
    	sbQuery.append("\n   ,A.RACE_NO																				");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
        
        PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime3";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
 
    }

}