/*================================================================================
 * 시스템			: 심판관리   
 * 소스파일 이름	: snis.boa.referere.e04110020.activity.SearchStatTime1.java
 * 파일설명		: 평균 스타트타임 현황
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-03 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.referere.e04110020.activity;

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
    	int maxDayOrd = 3;	// 3일차 까지..
    	
    	PosParameter param = new PosParameter();
        Integer i = 0;

    	for(int d = 1; d <= maxDayOrd; d++){
    		sbQuery.append(getSql(Integer.toString(d)));
    		if(d != (maxDayOrd)){
    			sbQuery.append(" UNION ALL ");       
    		}
    		
    		setParam(ctx, param, i);
    	}
		
    	sbQuery.append(" 		ORDER BY DAY_ORD, RACE_NO      ");        
    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutStatTime1";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
        Util.setSearchCount(ctx, rowset.getAllRow().length);
        
        getMaxRaceRegNo(ctx);
    }
    
    private void setParam(PosContext ctx, PosParameter param, Integer i){
    	param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("FROMDT         		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("TODT          		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
    			
    	param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO      ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("RES_NO          	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("FROMDT         		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("TODT          		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
    }
    
    private StringBuffer getSql(String dayOrd){
    	StringBuffer sbQuery = new StringBuffer();
    	
    	sbQuery.append("\n 	SELECT    A.DAY_ORD                   DAY_ORD                                                                                       ");                    
    	sbQuery.append("\n 	         ,A.RACE_NO|| ' 경주'          RACE_NO                                                                                       ");                    
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_1,2) COURSE1_1                                                                                     ");        
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_2,2) COURSE1_2                                                                                     ");        
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_3,2) COURSE1_3                                                                                     ");        
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_4,2) COURSE1_4                                                                                     ");        
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_5,2) COURSE1_5                                                                                     ");        
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_6,2) COURSE1_6                                                                                     ");        
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_7,2) COURSE1_7                                                                                     ");        
    	sbQuery.append("\n 	         ,ROUND(A.COURSE1_8,2) COURSE1_8                                                                                     ");        
    	sbQuery.append("\n 		     ,ROUND(ROUND((NVL(A.COURSE1_1,0) + NVL(A.COURSE1_2,0) + NVL(A.COURSE1_3,0) +                                             ");
    	sbQuery.append("\n 		                     NVL(A.COURSE1_4,0) + NVL(A.COURSE1_5,0) + NVL(A.COURSE1_6,0) + 											");
    	sbQuery.append("\n 		                     NVL(A.COURSE1_7,0) + NVL(A.COURSE1_8,0)) /                                            						");        
    	sbQuery.append("\n 		                     DECODE((DECODE(A.COURSE1_1,NULL,0,1) + DECODE(A.COURSE1_2,NULL,0,1) +                                      ");        
    	sbQuery.append("\n 		                     DECODE(A.COURSE1_3,NULL,0,1) + DECODE(A.COURSE1_4,NULL,0,1) +                                              ");        
    	sbQuery.append("\n 		                     DECODE(A.COURSE1_5,NULL,0,1) + DECODE(A.COURSE1_6,NULL,0,1) +                                              ");        
    	sbQuery.append("\n 		                     DECODE(A.COURSE1_7,NULL,0,1) + DECODE(A.COURSE1_8,NULL,0,1)), 0, 1,                                        ");        
    	sbQuery.append("\n 		                    (DECODE(A.COURSE1_1,NULL,0,1) + DECODE(A.COURSE1_2,NULL,0,1) +                                              ");        
    	sbQuery.append("\n 		                     DECODE(A.COURSE1_3,NULL,0,1) + DECODE(A.COURSE1_4,NULL,0,1) +                                              ");        
    	sbQuery.append("\n 		                     DECODE(A.COURSE1_5,NULL,0,1) + DECODE(A.COURSE1_6,NULL,0,1) +                                              ");        
    	sbQuery.append("\n 		                     DECODE(A.COURSE1_7,NULL,0,1) + DECODE(A.COURSE1_8,NULL,0,1))) ,2),2) COURSE1_SUM                      ");        
    	sbQuery.append("\n 	FROM (SELECT A.DAY_ORD                             DAY_ORD                                                                          ");          
    	sbQuery.append("\n 	            ,A.RACE_NO                             RACE_NO                                                                          ");          
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_1 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_2 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_3 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_4 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_5 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_6 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_7 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_8 ");
    	sbQuery.append("\n 	      FROM  TBEB_ORGAN  A,( SELECT A.RACER_NO  RACER_NO                                                                             ");
		sbQuery.append("\n 								 FROM  TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                                         ");
		sbQuery.append("\n 								WHERE  A.RACER_NO = B.RACER_NO                                                                          ");
		sbQuery.append("\n 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                                  ");
		sbQuery.append("\n 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                                              ");
		sbQuery.append("\n 							    ) B	                                                                                                    ");
        sbQuery.append("\n         WHERE A.STND_YEAR = ?                                                                                                         ");           
    	sbQuery.append("\n 	       AND A.MBR_CD     = ?                                                                                                         ");          
    	sbQuery.append("\n 	       AND A.RACE_DAY   BETWEEN ? AND ?                                                                                             ");          
    	sbQuery.append("\n 	       AND A.DAY_ORD    = " + dayOrd + "                                                                                                         ");
    	sbQuery.append("\n 		   AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    								");
		sbQuery.append("\n 	       AND A.RACER_NO  	   = B.RACER_NO                                                                                             ");
    	sbQuery.append("\n 	       GROUP BY A.DAY_ORD, A.RACE_NO  ) A                                                                                           ");                
    	sbQuery.append("\n UNION ALL                                                                                                                              ");       
    	sbQuery.append("\n 		SELECT   A.DAY_ORD                                                                                                              ");        
    	sbQuery.append("\n 		        ,'평균' RACE_NO                                                                                                          ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_1),2)  COURSE1_1                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_2),2)  COURSE1_2                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_3),2)  COURSE1_3                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_4),2)  COURSE1_4                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_5),2)  COURSE1_5                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_6),2)  COURSE1_6                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_7),2)  COURSE1_7                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(AVG(A.COURSE1_8),2)  COURSE1_8                                                                            ");        
    	sbQuery.append("\n 		        ,ROUND(ROUND((NVL(AVG(A.COURSE1_1),0) + NVL(AVG(A.COURSE1_2),0) + NVL(AVG(A.COURSE1_3),0) +                           ");        
    	sbQuery.append("\n 		        				NVL(AVG(A.COURSE1_4),0) + NVL(AVG(A.COURSE1_5),0) + NVL(AVG(A.COURSE1_6),0) +                           ");        
    	sbQuery.append("\n 		                        NVL(AVG(A.COURSE1_7),0) + NVL(AVG(A.COURSE1_8),0)) /                          ");        
    	sbQuery.append("\n 		                        DECODE((DECODE(AVG(A.COURSE1_1),NULL,0,1) + DECODE(AVG(A.COURSE1_2),NULL,0,1) +                         ");        
    	sbQuery.append("\n 		                        DECODE(AVG(A.COURSE1_3),NULL,0,1) + DECODE(AVG(A.COURSE1_4),NULL,0,1) +                                 ");        
    	sbQuery.append("\n 		                        DECODE(AVG(A.COURSE1_5),NULL,0,1) + DECODE(AVG(A.COURSE1_6),NULL,0,1) +                                 ");        
    	sbQuery.append("\n 		                        DECODE(AVG(A.COURSE1_7),NULL,0,1) + DECODE(AVG(A.COURSE1_8),NULL,0,1)), 0, 1,                           ");        
    	sbQuery.append("\n 		                        (DECODE(AVG(A.COURSE1_1),NULL,0,1) + DECODE(AVG(A.COURSE1_2),NULL,0,1) +                                ");        
    	sbQuery.append("\n 		                        DECODE(AVG(A.COURSE1_3),NULL,0,1) + DECODE(AVG(A.COURSE1_4),NULL,0,1) +                                 ");        
    	sbQuery.append("\n 		                        DECODE(AVG(A.COURSE1_5),NULL,0,1) + DECODE(AVG(A.COURSE1_6),NULL,0,1) +                                 ");        
    	sbQuery.append("\n 		                        DECODE(AVG(A.COURSE1_7),NULL,0,1) + DECODE(AVG(A.COURSE1_8),NULL,0,1))), 2),2) COURSE1_SUM         ");        
    	sbQuery.append("\n 		FROM ( SELECT                                                                                                                   ");          
    	sbQuery.append("\n 		         A.DAY_ORD                               DAY_ORD                                                                        ");          
    	sbQuery.append("\n 		        ,A.RACE_NO                               RACE_NO                                                                        ");          
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,1,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_1 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,2,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_2 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,3,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_3 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,4,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_4 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,5,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_5 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,6,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_6 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,7,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_7 ");
    	sbQuery.append("\n 	            ,SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, A.STAR_TM)))/FC_ZERO_TO_ONE(SUM(DECODE(A.ENTRY_COURSE,8,DECODE(A.ST_MTHD_CD, '001', 0, 1)))) COURSE1_8 ");            
    	sbQuery.append("\n 		 FROM  TBEB_ORGAN A, (SELECT   A.RACER_NO  RACER_NO                                                                             ");
		sbQuery.append("\n 								FROM   TBEC_RACER_MASTER A, TBEC_RACER_DETAIL B                                                         ");
		sbQuery.append("\n 								WHERE  A.RACER_NO 			 = B.RACER_NO                                                               ");
		sbQuery.append("\n 								AND    A.RACER_PERIO_NO      = NVL(?,A.RACER_PERIO_NO)                                                  ");
		sbQuery.append("\n 								AND    SUBSTR(GET_DEC(B.RES_NO),7,1)  = NVL(?,SUBSTR(GET_DEC(B.RES_NO),7,1))                                              ");
		sbQuery.append("\n 							    ) B	                                                                                                    ");           
    	sbQuery.append("\n 		 WHERE A.STND_YEAR = ?                                                                                                          ");          
    	sbQuery.append("\n 		   AND A.MBR_CD    = ?                                                                                                          ");         
    	sbQuery.append("\n 		   AND A.RACE_DAY  BETWEEN ? AND ?                                                                                              ");         
    	sbQuery.append("\n 		   AND A.DAY_ORD   = " + dayOrd + "                                                                                                          ");
    	sbQuery.append("\n 		   AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                                                    								");
		sbQuery.append("\n 	       AND A.RACER_NO  	   = B.RACER_NO                                                                                             ");
    	sbQuery.append("\n 		   GROUP BY A.DAY_ORD,A.RACE_NO                                                                                                 ");               
    	sbQuery.append("\n 		) A                                                                                                                             ");        
    	sbQuery.append("\n 		GROUP BY A.DAY_ORD                                                                                                              ");
    	
    	return sbQuery;
    }

    private void getMaxRaceRegNo(PosContext ctx){
    	StringBuffer sbQuery = new StringBuffer();
    	sbQuery.append(" 	SELECT  MAX(A.RACE_REG_NO) MAX_RACE_REG_NO_1			                       "); 
    	sbQuery.append(" 	FROM    TBEB_ORGAN A                                                       ");                    
    	sbQuery.append(" 		 WHERE A.STND_YEAR = ?                                                 ");          
    	sbQuery.append(" 		   AND A.MBR_CD    = ?                                                 ");         
    	sbQuery.append(" 		   AND A.RACE_DAY  BETWEEN ? AND ?                                     ");         
    	sbQuery.append(" 		   AND A.RACER_GRD_CD  = NVL(?,A.RACER_GRD_CD)                         ");
    	
    	PosParameter param = new PosParameter();
    	int i = 0;
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD            	".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("FROMDT         		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("TODT          		".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String sResultKey = "MAX_RACE_REG_NO_1";
    	String sDefault = "0";
        ctx.put(sResultKey, Util.nullToStr((String)rowset.getAllRow()[0].getAttribute(sResultKey), sDefault));
        Util.addResultKey(ctx, sResultKey);
    }
}


