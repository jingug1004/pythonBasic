/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04120010.activity.SearchStatTime2.java
 * 파일설명		: 랩타임 분석
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-03 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04120010.activity;

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

    	sbQuery.append("\n SELECT      SUBSTR(B.RACE_DAY,1,4) RACE_DAY_YYYY																	 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '01',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_01 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '01',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_01 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '01',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_01 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '02',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_02 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '02',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_02 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '02',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_02 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '03',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_03 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '03',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_03 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '03',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_03 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '04',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_04 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '04',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_04 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '04',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_04 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '05',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_05 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '05',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_05 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '05',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_05 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '06',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_06 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '06',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_06 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '06',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_06 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '07',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_07 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '07',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_07 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '07',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_07 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '08',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_08 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '08',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_08 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '08',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_08 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '09',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_09 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '09',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_09 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '09',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_09 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '10',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_10 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '10',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_10 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '10',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_10 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '11',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_11 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '11',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_11 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '11',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_11 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '12',A.LAPTIME_1,NULL)),0),'000000')  LAPTIME_1_12 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '12',A.LAPTIME_2,NULL)),0),'000000')  LAPTIME_2_12 ");
    	sbQuery.append("\n            ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(B.RACE_DAY,5,2) , '12',A.LAPTIME_3,NULL)),0),'000000')  LAPTIME_3_12 ");
    	sbQuery.append("\n  FROM TBEB_RACE A, TBEB_RACE_DAY_ORD B                                                                               ");
    	sbQuery.append("\n     WHERE A.STND_YEAR = B.STND_YEAR                                                                                  ");
    	sbQuery.append("\n       AND A.MBR_CD    = B.MBR_CD                                                                                     ");
    	sbQuery.append("\n       AND A.TMS       = B.TMS                                                                                        ");
    	sbQuery.append("\n       AND A.DAY_ORD   = B.DAY_ORD                                                                                    ");
    	sbQuery.append("\n       AND B.RACE_DAY   BETWEEN ? AND ?                                                                 				");
    	sbQuery.append("\n       GROUP BY   SUBSTR(B.RACE_DAY,1,4)                                                                              ");
    	sbQuery.append("\n       ORDER BY   SUBSTR(B.RACE_DAY,1,4)                                                                              ");
    	
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


