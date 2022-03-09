/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04130010.activity.SearchStatTime2.java
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

public class SearchStatTime2 extends SnisActivity {
    public SearchStatTime2()
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

    	sbQuery.append("    SELECT  SUBSTR(B.RACE_DAY,1,4)                                                   RACE_DAY_YYYY		");
    	sbQuery.append("\n         ,SUBSTR(A.MOT_NO,6,3)                                                     MOT_NO             "); 
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '01',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_01    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '02',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_02    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '03',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_03    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '04',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_04    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '05',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_05    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '06',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_06    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '07',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_07    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '08',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_08    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '09',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_09    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '10',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_10    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '11',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_11    ");
    	sbQuery.append("\n         ,ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '12',A.INTRO_RUN_TM,NULL)),2) INTRO_RUN_TM_12    ");
    	sbQuery.append("\n FROM TBEB_ORGAN A, TBEB_RACE_DAY_ORD B                                                               ");
    	sbQuery.append("\n  WHERE A.STND_YEAR = B.STND_YEAR                                                                     ");
    	sbQuery.append("\n    AND A.MBR_CD    = B.MBR_CD                                                                        ");
    	sbQuery.append("\n    AND A.TMS       = B.TMS                                                                           ");
    	sbQuery.append("\n    AND A.DAY_ORD   = B.DAY_ORD                                                                       ");
    	sbQuery.append("\n    AND B.RACE_DAY   BETWEEN ? AND ?                                                              	");
    	sbQuery.append("\n    GROUP BY   SUBSTR(B.RACE_DAY,1,4) ,SUBSTR(A.MOT_NO,6,3)                                           ");
    	sbQuery.append("\n    ORDER BY   SUBSTR(B.RACE_DAY,1,4)                                                                 ");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
        
        
        PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime2";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
 
    }

}


