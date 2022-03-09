/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04120010.activity.SearchStatTime1.java
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

public class SearchStatTime1 extends SnisActivity {
    public SearchStatTime1()
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

    	sbQuery.append("\n	SELECT  A.STND_YEAR          STND_YEAR																																																																																		");						
    	sbQuery.append("\n           ,A.MBR_CD           MBR_CD                                                                                                                                                                                                                                                                                                                                     	");
    	sbQuery.append("\n           ,A.TMS              TMS                                                                                                                                                                                                                                                                                                                                        	");
    	sbQuery.append("\n           ,A.LAPTIME_1        LAPTIME_1                                                                                                                                                                                                                                                                                                                                  	");
    	sbQuery.append("\n           ,A.LAPTIME_2        LAPTIME_2                                                                                                                                                                                                                                                                                                                                  	");
    	sbQuery.append("\n           ,TRIM(TO_CHAR(FLOOR(( (DECODE(LENGTH(A.LAPTIME_2),6,SUBSTR(A.LAPTIME_2, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_2, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_2, 4, 3),'000000')) -  (DECODE(LENGTH(LAPTIME_1),6,SUBSTR(LAPTIME_1, 1, 1)  * 60000 +  SUBSTR(LAPTIME_1, 2, 2)  * 1000 +  SUBSTR(LAPTIME_1, 4, 3),'000000')) ) /60000),'0'))  ||                               	");
    	sbQuery.append("\n			  TRIM(TO_CHAR(FLOOR((MOD(( (DECODE(LENGTH(A.LAPTIME_2),6,SUBSTR(A.LAPTIME_2, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_2, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_2, 4, 3),'000000')) -  (DECODE(LENGTH(LAPTIME_1),6,SUBSTR(LAPTIME_1, 1, 1)  * 60000 +  SUBSTR(LAPTIME_1, 2, 2)  * 1000 +  SUBSTR(LAPTIME_1, 4, 3),'000000')) ),60000)) /1000),'00')) ||                     	");
    	sbQuery.append("\n			  TRIM(TO_CHAR(FLOOR(MOD((MOD(( (DECODE(LENGTH(A.LAPTIME_2),6,SUBSTR(A.LAPTIME_2, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_2, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_2, 4, 3),'000000')) - (DECODE(LENGTH(LAPTIME_1),6,SUBSTR(LAPTIME_1, 1, 1)  * 60000 +  SUBSTR(LAPTIME_1, 2, 2)  * 1000 +  SUBSTR(LAPTIME_1, 4, 3),'000000')) ),60000)),1000)),'000')) LAPTIME_2_1        	");
    	sbQuery.append("\n           ,A.LAPTIME_3        LAPTIME_3                                                                                                                                                                                                                                                                                                                                  	");
    	sbQuery.append("\n           ,TRIM(TO_CHAR(FLOOR(( (DECODE(LENGTH(A.LAPTIME_3),6,SUBSTR(A.LAPTIME_3, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_3, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_3, 4, 3),'000000')) -  (DECODE(LENGTH(A.LAPTIME_2),6,SUBSTR(A.LAPTIME_2, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_2, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_2, 4, 3),'000000')) ) /60000),'0'))  ||                       	");
    	sbQuery.append("\n			  TRIM(TO_CHAR(FLOOR((MOD(( (DECODE(LENGTH(A.LAPTIME_3),6,SUBSTR(A.LAPTIME_3, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_3, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_3, 4, 3),'000000')) -  (DECODE(LENGTH(A.LAPTIME_2),6,SUBSTR(A.LAPTIME_2, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_2, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_2, 4, 3),'000000')) ),60000)) /1000),'00')) ||             	");
    	sbQuery.append("\n			  TRIM(TO_CHAR(FLOOR(MOD((MOD(( (DECODE(LENGTH(A.LAPTIME_3),6,SUBSTR(A.LAPTIME_3, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_3, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_3, 4, 3),'000000')) - (DECODE(LENGTH(A.LAPTIME_2),6,SUBSTR(A.LAPTIME_2, 1, 1)  * 60000 +  SUBSTR(A.LAPTIME_2, 2, 2)  * 1000 +  SUBSTR(A.LAPTIME_2, 4, 3),'000000')) ),60000)),1000)),'000')) LAPTIME_3_1	");
    	sbQuery.append("\n     FROM  (                                                                                                                                                                                                                                                                                                                                                              	");
    	sbQuery.append("\n               SELECT    A.STND_YEAR                                                    STND_YEAR                                                                                                                                                                                                                                                                         	");
    	sbQuery.append("\n                        ,A.MBR_CD                                                       MBR_CD                                                                                                                                                                                                                                                                            	");
    	sbQuery.append("\n                        ,A.TMS                                                          TMS                                                                                                                                                                                                                                                                               	");
    	sbQuery.append("\n                        ,TRIM(TO_CHAR(ROUND(AVG(NVL(A.LAPTIME_1,0)),2),'000000'))       LAPTIME_1                                                                                                                                                                                                                                                                         	");
    	sbQuery.append("\n                        ,TRIM(TO_CHAR(ROUND(AVG(NVL(A.LAPTIME_2,0)),2),'000000'))       LAPTIME_2                                                                                                                                                                                                                                                                         	");
    	sbQuery.append("\n                        ,TRIM(TO_CHAR(ROUND(AVG(NVL(A.LAPTIME_3,0)),2),'000000'))       LAPTIME_3                                                                                                                                                                                                                                                                         	");
    	sbQuery.append("\n                 FROM TBEB_RACE A, TBEB_RACE_DAY_ORD B                                                                                                                                                                                                                                                                                                                    	");
    	sbQuery.append("\n                 WHERE A.STND_YEAR = B.STND_YEAR                                                                                                                                                                                                                                                                                                                          	");
    	sbQuery.append("\n                   AND A.MBR_CD    = B.MBR_CD                                                                                                                                                                                                                                                                                                                             	");
    	sbQuery.append("\n                   AND A.TMS       = B.TMS                                                                                                                                                                                                                                                                                                                                	");
    	sbQuery.append("\n                   AND A.DAY_ORD   = B.DAY_ORD                                                                                                                                                                                                                                                                                                                            	");
    	sbQuery.append("\n               AND B.RACE_DAY   BETWEEN ? AND ?                                                                                                                                                                                                                                                                                                        						");
    	sbQuery.append("\n               GROUP BY  A.STND_YEAR                                                                                                                                                                                                                                                                                                                                      	");
    	sbQuery.append("\n                    	,A.MBR_CD                                                                                                                                                                                                                                                                                                                                           	");
    	sbQuery.append("\n                    	,A.TMS                                                                                                                                                                                                                                                                                                                                              	");
    	sbQuery.append("\n            ) A                                                                                                                                                                                                                                                                                                                                                           	");
    	sbQuery.append("\n        ORDER BY   A.STND_YEAR                                                                                                                                                                                                                                                                                                                                            	");
    	sbQuery.append("\n                  ,A.MBR_CD                                                                                                                                                                                                                                                                                                                                               	");
    	sbQuery.append("\n                  ,A.TMS																																																																																						");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
            
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime1";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
 
    }

}


