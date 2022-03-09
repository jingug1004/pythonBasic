/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04140010.activity.SearchStatTime2.java
 * 파일설명		: 경주 주회타임  분석(월별 선두정 주회타임 비교)
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-03 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04140010.activity;

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

    	
    	sbQuery.append("\n SELECT  '3' AS TURN_CNT, SUBSTR(A.RACE_DAY,1,4) RACE_DAY_YYYY                                             	");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '01',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_01     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '02',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_02     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '03',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_03     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '04',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_04     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '05',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_05     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '06',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_06     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '07',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_07     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '08',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_08     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '09',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_09     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '10',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_10     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '11',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_11     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '12',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_12     ");
    	sbQuery.append("\n FROM  TBEB_ORGAN A, TBEB_RACE_DAY_ORD B, TBEB_RACE C                                                   ");
    	sbQuery.append("\n WHERE A.STND_YEAR = B.STND_YEAR                                                          ");
    	sbQuery.append("\n AND A.MBR_CD    = B.MBR_CD                                                               ");
    	sbQuery.append("\n AND A.TMS       = B.TMS                                                                  ");
    	sbQuery.append("\n AND A.DAY_ORD   = B.DAY_ORD                                                              ");
    	sbQuery.append("\n AND B.RACE_DAY   BETWEEN ? AND ?                                      					");
    	sbQuery.append("\n AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                                          ");
    	sbQuery.append("\n AND A.RANK = 1                                                                           ");
        sbQuery.append("\n AND A.STND_YEAR = C.STND_YEAR                                                            ");                       
        sbQuery.append("\n AND A.MBR_CD = C.MBR_CD                                                                  ");
        sbQuery.append("\n AND A.TMS  = C.TMS                                                                       ");
        sbQuery.append("\n AND A.DAY_ORD = C.DAY_ORD                                                                ");
        sbQuery.append("\n AND A.RACE_NO = C.RACE_NO                                                                ");
        sbQuery.append("\n AND C.TURN_CNT = 3                                                                       ");        
    	sbQuery.append("\n GROUP BY SUBSTR(A.RACE_DAY,1,4)                                                          ");

    	sbQuery.append("\n UNION ALL                                                          ");
    	
    	sbQuery.append("\n SELECT  '2' AS TURN_CNT, SUBSTR(A.RACE_DAY,1,4) RACE_DAY_YYYY                                             	");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '01',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_01     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '02',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_02     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '03',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_03     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '04',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_04     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '05',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_05     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '06',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_06     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '07',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_07     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '08',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_08     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '09',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_09     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '10',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_10     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '11',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_11     ");
    	sbQuery.append("\n      ,TO_CHAR(ROUND(AVG(DECODE( SUBSTR(A.RACE_DAY,5,2) , '12',A.RACE_TM,NULL)),2),'000000.00') RACE_TM_12     ");
    	sbQuery.append("\n FROM  TBEB_ORGAN A, TBEB_RACE_DAY_ORD B, TBEB_RACE C                                                  ");
    	sbQuery.append("\n WHERE A.STND_YEAR = B.STND_YEAR                                                          ");
    	sbQuery.append("\n AND A.MBR_CD    = B.MBR_CD                                                               ");
    	sbQuery.append("\n AND A.TMS       = B.TMS                                                                  ");
    	sbQuery.append("\n AND A.DAY_ORD   = B.DAY_ORD                                                              ");
    	sbQuery.append("\n AND B.RACE_DAY   BETWEEN ? AND ?                                      					");
    	sbQuery.append("\n AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                                          ");
    	sbQuery.append("\n AND A.RANK = 1                                                                           ");
        sbQuery.append("\n AND A.STND_YEAR = C.STND_YEAR                                                            ");                       
        sbQuery.append("\n AND A.MBR_CD = C.MBR_CD                                                                  ");
        sbQuery.append("\n AND A.TMS  = C.TMS                                                                       ");
        sbQuery.append("\n AND A.DAY_ORD = C.DAY_ORD                                                                ");
        sbQuery.append("\n AND A.RACE_NO = C.RACE_NO                                                                ");
        sbQuery.append("\n AND C.TURN_CNT = 2                                                                       ");        
    	sbQuery.append("\n GROUP BY SUBSTR(A.RACE_DAY,1,4)                                                          ");
    	sbQuery.append("\n ORDER BY 2 DESC, 1                                                                       "); 
    	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
       
        PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime2";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
 
    }

}


