/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04140010.activity.SearchStatTime4.java
 * 파일설명		: 경주 주회타임  분석(년도별 BEST10 주회타임)
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

public class SearchStatTime4 extends SnisActivity {
    public SearchStatTime4()
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

		sbQuery.append("\n    SELECT   A.STND_YEAR              STND_YEAR  																		 ");                                                         
        sbQuery.append("\n               ,A.MBR_CD                 MBR_CD                                                                        ");
        sbQuery.append("\n               ,A.TMS                    TMS                                                                           ");
        sbQuery.append("\n               ,A.DAY_ORD                DAY_ORD                                                                       ");
        sbQuery.append("\n               ,A.RACE_NO                RACE_NO                                                                       ");
        sbQuery.append("\n               ,A.RACE_DAY               RACE_DAY                                                                      ");
        sbQuery.append("\n               ,A.RACER_NO               RACER_NO                                                                      ");
        sbQuery.append("\n               ,C.NM_KOR                 RACER_NM                                                                      ");
        sbQuery.append("\n               ,A.RACE_TM                RACE_TM                                                                       ");
        sbQuery.append("\n               ,A.RANK                   RANK                                                                          ");
        sbQuery.append("\n               ,TO_CHAR(A.TIME_DIFF,'000000') TIME_DIFF                                                                ");
        sbQuery.append("\n         FROM  TBEB_ORGAN A, TBEB_RACE_DAY_ORD B, TBEC_RACER_MASTER C                                                  ");
        sbQuery.append("\n       WHERE A.STND_YEAR = B.STND_YEAR                                                                                 ");
        sbQuery.append("\n         AND A.MBR_CD    = B.MBR_CD                                                                                    ");
        sbQuery.append("\n         AND A.TMS       = B.TMS                                                                                       ");
        sbQuery.append("\n         AND A.DAY_ORD   = B.DAY_ORD                                                                                   ");
        sbQuery.append("\n         AND A.RACER_NO   = C.RACER_NO                                                                                 ");
        sbQuery.append("\n         AND A.TIME_DIFF < '000099'                                                                                    ");
        sbQuery.append("\n         AND B.RACE_DAY   BETWEEN ? AND ?                                                                              ");
        sbQuery.append("\n         AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                                                               ");
        sbQuery.append("\n      UNION ALL                                                                                                        ");
        sbQuery.append("\n        SELECT B.STND_YEAR, B.MBR_CD, B.TMS                                                                            ");
        sbQuery.append("\n              ,B.DAY_ORD, B.RACE_NO, B.RACE_DAY, B.RACER_NO                                                         	 ");
        sbQuery.append("\n              ,(SELECT NM_KOR FROM TBEC_RACER_MASTER WHERE RACER_NO = B.RACER_NO) NM_KOR, B.RACE_TM, B.RANK,  '' TIME_DIFF ");
        sbQuery.append("\n         FROM TBEB_ORGAN B,                                                                                            ");
        sbQuery.append("\n                  ( SELECT  A.STND_YEAR              STND_YEAR                                                         ");   
        sbQuery.append("\n                           ,A.MBR_CD                 MBR_CD                                                            ");   
        sbQuery.append("\n                           ,A.TMS                    TMS                                                               ");   
        sbQuery.append("\n                           ,A.DAY_ORD                DAY_ORD                                                           ");   
        sbQuery.append("\n                           ,A.RACE_NO                RACE_NO                                                           ");   
        sbQuery.append("\n                           ,A.RANK - 1               RANK                                                              ");
        sbQuery.append("\n                     FROM  TBEB_ORGAN A                                                                                ");
        sbQuery.append("\n                   WHERE  A.TIME_DIFF < '000099'                                                                       ");    
        sbQuery.append("\n                     AND A.RACE_DAY   BETWEEN ? AND ?                                                                  ");
        sbQuery.append("\n                     AND (A.RACE_TM IS NOT NULL AND A.RACE_TM <> 0 )                                                   ");
        sbQuery.append("\n                    ) A                                                                                                ");
        sbQuery.append("\n         WHERE A.STND_YEAR 		= B.STND_YEAR                                                                        ");
        sbQuery.append("\n                AND A.MBR_CD	    = B.MBR_CD                                                                           ");
        sbQuery.append("\n                AND A.TMS 		= B.TMS                                                                              ");
        sbQuery.append("\n                AND A.DAY_ORD 	= B.DAY_ORD                                                                          ");
        sbQuery.append("\n                AND A.RACE_NO 	= B.RACE_NO                                                                          ");
        sbQuery.append("\n                AND A.RANK 		= B.RANK                                                                             ");
        sbQuery.append("\n        ORDER BY RACE_DAY, RACE_NO, RANK                                                                               ");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
    	
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT         		".trim()));
        param.setWhereClauseParameter(i++, ctx.get("END_DT            	".trim()));
      
        
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime4";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    	        
 
    }

}


