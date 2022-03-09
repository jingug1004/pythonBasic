/*================================================================================
 * 시스템			: 심판관리
 * 소스파일 이름	: snis.boa.referere.e04090010.activity.SearchVoilAcdntReport.java
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

public class SearchVoilAcdntReport extends SnisActivity {
    public SearchVoilAcdntReport()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	SearchReportVoilAcdnt(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchReportVoilAcdnt(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();
    	PosParameter param = new PosParameter();
    	Integer i = 0;
    	
    	//전체 조회시..
    	if("".equals(ctx.get("DAY_ORD".trim()))){
    		for(int k = 1; k <= Integer.parseInt((String)ctx.get("DAY_ORD_CNT".trim())); k++){
    			sbQuery.append(getSql(ctx, k));
    			
    			if(k != Integer.parseInt((String)ctx.get("DAY_ORD_CNT".trim()))){
    				sbQuery.append("\n UNION ALL ");
    			}
    			
    			setParam(ctx, param, i );
    		}
    	//해당 일차 조회..
    	}else{
    		sbQuery.append(getSql(ctx, Integer.parseInt((String)ctx.get("DAY_ORD".trim()))));
    		setParam(ctx, param, i );
    	}
		    	    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutVoilAcdnt";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    }

    private StringBuffer getSql(PosContext ctx, int dayOrd){
    	
    	StringBuffer sbQuery = new StringBuffer();
    	
    	sbQuery.append("\n SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00035' AND CD =  VOIL_CD)                	   ACDNT_TPE_NM,  "); 
		sbQuery.append("\n              COUNT(*)                                                                                   SUMRACE,       ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '01', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '01', 1 , 0 ))) RACENO01,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '02', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '02', 1 , 0 ))) RACENO02,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '03', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '03', 1 , 0 ))) RACENO03,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '04', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '04', 1 , 0 ))) RACENO04,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '05', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '05', 1 , 0 ))) RACENO05,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '06', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '06', 1 , 0 ))) RACENO06,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '07', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '07', 1 , 0 ))) RACENO07,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '08', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '08', 1 , 0 ))) RACENO08,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '09', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '09', 1 , 0 ))) RACENO09,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '10', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '10', 1 , 0 ))) RACENO10,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '11', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '11', 1 , 0 ))) RACENO11,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '12', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '12', 1 , 0 ))) RACENO12,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '13', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '13', 1 , 0 ))) RACENO13,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '14', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '14', 1 , 0 ))) RACENO14,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '15', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '15', 1 , 0 ))) RACENO15,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '16', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '16', 1 , 0 ))) RACENO16,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '17', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '17', 1 , 0 ))) RACENO17,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '18', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '18', 1 , 0 ))) RACENO18       ");
		sbQuery.append("\n          FROM   TBED_RACE_VOIL_ACT                                                                                     ");
		sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                                      "); 
		sbQuery.append("\n          AND   MBR_CD         = ?                                                                                      ");
		sbQuery.append("\n          AND   TMS            = ?                                                                                      ");
		sbQuery.append("\n          AND   DAY_ORD        = " + dayOrd + "                                                                         ");
		if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		sbQuery.append("\n 		AND   VOIL_CD      NOT IN ('996','998','999','211','221')                                                  					  ");
		}
		sbQuery.append("\n         GROUP BY VOIL_CD                                                                                               ");
		sbQuery.append("\n          UNION ALL                                                                                                     ");
		sbQuery.append("\n          SELECT '위반행위계'                                                                            ACDNT_TPE_NM,  "); 
		sbQuery.append("\n              COUNT(*)                                                                                   SUMRACE,       ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '01', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '01', 1 , 0 ))) RACENO01,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '02', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '02', 1 , 0 ))) RACENO02,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '03', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '03', 1 , 0 ))) RACENO03,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '04', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '04', 1 , 0 ))) RACENO04,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '05', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '05', 1 , 0 ))) RACENO05,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '06', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '06', 1 , 0 ))) RACENO06,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '07', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '07', 1 , 0 ))) RACENO07,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '08', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '08', 1 , 0 ))) RACENO08,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '09', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '09', 1 , 0 ))) RACENO09,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '10', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '10', 1 , 0 ))) RACENO10,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '11', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '11', 1 , 0 ))) RACENO11,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '12', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '12', 1 , 0 ))) RACENO12,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '13', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '13', 1 , 0 ))) RACENO13,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '14', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '14', 1 , 0 ))) RACENO14,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '15', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '15', 1 , 0 ))) RACENO15,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '16', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '16', 1 , 0 ))) RACENO16,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '17', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '17', 1 , 0 ))) RACENO17,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '18', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '18', 1 , 0 ))) RACENO18       ");
		sbQuery.append("\n           FROM   TBED_RACE_VOIL_ACT                                                                                    ");
		sbQuery.append("\n         WHERE STND_YEAR      = ?                                                                                       ");
		sbQuery.append("\n          AND   MBR_CD        = ?                                                                                       ");
		sbQuery.append("\n          AND   TMS           = ?                                                                                       ");
		sbQuery.append("\n          AND   DAY_ORD       = " + dayOrd + "                                                                                       ");
		if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		sbQuery.append("\n 		AND   VOIL_CD      NOT IN ('996','998','999','211','221')                                                  					  ");
		}
		sbQuery.append("\n        UNION ALL                                                                                                       ");
		sbQuery.append("\n          SELECT (SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD  ='E00062' AND CD =  ACDNT_TPE_CD)        ACDNT_TPE_NM,   ");
		sbQuery.append("\n              COUNT(*)                                                                                  SUMRACE,        ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '01', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '01', 1 , 0 ))) RACENO01,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '02', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '02', 1 , 0 ))) RACENO02,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '03', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '03', 1 , 0 ))) RACENO03,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '04', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '04', 1 , 0 ))) RACENO04,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '05', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '05', 1 , 0 ))) RACENO05,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '06', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '06', 1 , 0 ))) RACENO06,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '07', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '07', 1 , 0 ))) RACENO07,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '08', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '08', 1 , 0 ))) RACENO08,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '09', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '09', 1 , 0 ))) RACENO09,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '10', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '10', 1 , 0 ))) RACENO10,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '11', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '11', 1 , 0 ))) RACENO11,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '12', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '12', 1 , 0 ))) RACENO12,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '13', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '13', 1 , 0 ))) RACENO13,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '14', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '14', 1 , 0 ))) RACENO14,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '15', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '15', 1 , 0 ))) RACENO15,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '16', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '16', 1 , 0 ))) RACENO16,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '17', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '17', 1 , 0 ))) RACENO17,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '18', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '18', 1 , 0 ))) RACENO18       ");
		sbQuery.append("\n          FROM   TBED_RACE_ACDNT_STTS                                                                                   ");
		sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                                      "); 
		sbQuery.append("\n          AND   MBR_CD         = ?                                                                                      ");
		sbQuery.append("\n          AND   TMS            = ?                                                                                      "); 
		sbQuery.append("\n          AND   DAY_ORD        = " + dayOrd + "                                                                                      ");
		sbQuery.append("\n          GROUP BY ACDNT_TPE_CD                                                                                         ");
		sbQuery.append("\n          UNION ALL                                                                                                     ");
		sbQuery.append("\n          SELECT '사고행위계'                                                                            ACDNT_TPE_NM,  ");
		sbQuery.append("\n              COUNT(*)                                                                                   SUMRACE,       ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '01', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '01', 1 , 0 ))) RACENO01,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '02', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '02', 1 , 0 ))) RACENO02,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '03', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '03', 1 , 0 ))) RACENO03,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '04', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '04', 1 , 0 ))) RACENO04,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '05', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '05', 1 , 0 ))) RACENO05,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '06', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '06', 1 , 0 ))) RACENO06,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '07', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '07', 1 , 0 ))) RACENO07,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '08', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '08', 1 , 0 ))) RACENO08,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '09', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '09', 1 , 0 ))) RACENO09,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '10', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '10', 1 , 0 ))) RACENO10,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '11', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '11', 1 , 0 ))) RACENO11,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '12', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '12', 1 , 0 ))) RACENO12,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '13', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '13', 1 , 0 ))) RACENO13,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '14', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '14', 1 , 0 ))) RACENO14,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '15', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '15', 1 , 0 ))) RACENO15,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '16', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '16', 1 , 0 ))) RACENO16,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '17', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '17', 1 , 0 ))) RACENO17,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '18', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '18', 1 , 0 ))) RACENO18       ");
		sbQuery.append("\n           FROM TBED_RACE_ACDNT_STTS                                                                                    ");
		sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                                      "); 
		sbQuery.append("\n          AND   MBR_CD         = ?                                                                                      ");
		sbQuery.append("\n          AND   TMS            = ?                                                                                      ");
		sbQuery.append("\n          AND   DAY_ORD        = " + dayOrd + "                                                                                      ");
		sbQuery.append("\n UNION ALL                                                                                                              ");
		sbQuery.append("\n  SELECT  A.ACDNT_TPE_NM                   	  			ACDNT_TPE_NM,                                                 ");
		sbQuery.append("\n         SUM(A.SUMRACE     +  B.SUMRACE )        		SUMRACE,                                                          ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO01    +  B.RACENO01))      RACENO01,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO02    +  B.RACENO02))      RACENO02,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO03    +  B.RACENO03))      RACENO03,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO04    +  B.RACENO04))      RACENO04,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO05    +  B.RACENO05))      RACENO05,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO06    +  B.RACENO06))      RACENO06,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO07    +  B.RACENO07))      RACENO07,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO08    +  B.RACENO08))      RACENO08,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO09    +  B.RACENO09))      RACENO09,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO10    +  B.RACENO10))      RACENO10,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO11    +  B.RACENO11))      RACENO11,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO12    +  B.RACENO12))      RACENO12,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO13    +  B.RACENO13))      RACENO13,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO14    +  B.RACENO14))      RACENO14,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO15    +  B.RACENO15))      RACENO15,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO16    +  B.RACENO16))      RACENO16,                                                       ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO17    +  B.RACENO17))      RACENO17,                                                      ");
		sbQuery.append("\n         TO_CHAR(SUM(A.RACENO18    +  B.RACENO18))      RACENO18                                                        ");
		sbQuery.append("\n   FROM  ( SELECT '" + dayOrd + "일차계'                                                                              ACDNT_TPE_NM,  "); 
		sbQuery.append("\n              COUNT(*)                                                                                   SUMRACE,       ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '01', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '01', 1 , 0 ))) RACENO01,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '02', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '02', 1 , 0 ))) RACENO02,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '03', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '03', 1 , 0 ))) RACENO03,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '04', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '04', 1 , 0 ))) RACENO04,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '05', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '05', 1 , 0 ))) RACENO05,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '06', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '06', 1 , 0 ))) RACENO06,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '07', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '07', 1 , 0 ))) RACENO07,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '08', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '08', 1 , 0 ))) RACENO08,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '09', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '09', 1 , 0 ))) RACENO09,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '10', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '10', 1 , 0 ))) RACENO10,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '11', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '11', 1 , 0 ))) RACENO11,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '12', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '12', 1 , 0 ))) RACENO12,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '13', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '13', 1 , 0 ))) RACENO13,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '14', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '14', 1 , 0 ))) RACENO14,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '15', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '15', 1 , 0 ))) RACENO15,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '16', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '16', 1 , 0 ))) RACENO16,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '17', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '17', 1 , 0 ))) RACENO17,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '18', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '18', 1 , 0 ))) RACENO18       ");
		sbQuery.append("\n           FROM   TBED_RACE_VOIL_ACT                                                                                    ");
		sbQuery.append("\n         WHERE STND_YEAR      = ?                                                                                       ");
		sbQuery.append("\n          AND   MBR_CD        = ?                                                                                       ");
		sbQuery.append("\n          AND   TMS           = ?                                                                                       ");
		sbQuery.append("\n          AND   DAY_ORD       = " + dayOrd + "                                                                                       ");
		if(ctx.get("VOIL_CD".trim()) != null && !"".equals(ctx.get("VOIL_CD".trim()))){
		sbQuery.append("\n 		AND   VOIL_CD      NOT IN ('996','998','999','211','221')                                                  					  ");
		}
		sbQuery.append("\n      ) A,                                                                                                              ");
		sbQuery.append("\n      (SELECT '" + dayOrd + "일차계'                                                                                  ACDNT_TPE_NM,  ");
		sbQuery.append("\n              COUNT(*)                                                                                   SUMRACE,       ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '01', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '01', 1 , 0 ))) RACENO01,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '02', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '02', 1 , 0 ))) RACENO02,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '03', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '03', 1 , 0 ))) RACENO03,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '04', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '04', 1 , 0 ))) RACENO04,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '05', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '05', 1 , 0 ))) RACENO05,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '06', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '06', 1 , 0 ))) RACENO06,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '07', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '07', 1 , 0 ))) RACENO07,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '08', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '08', 1 , 0 ))) RACENO08,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '09', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '09', 1 , 0 ))) RACENO09,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '10', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '10', 1 , 0 ))) RACENO10,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '11', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '11', 1 , 0 ))) RACENO11,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '12', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '12', 1 , 0 ))) RACENO12,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '13', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '13', 1 , 0 ))) RACENO13,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '14', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '14', 1 , 0 ))) RACENO14,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '15', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '15', 1 , 0 ))) RACENO15,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '16', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '16', 1 , 0 ))) RACENO16,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '17', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '17', 1 , 0 ))) RACENO17,      ");
		sbQuery.append("\n              DECODE(SUM(DECODE( RACE_NO , '18', 1 , 0 )), 0, '0', SUM(DECODE( RACE_NO , '18', 1 , 0 ))) RACENO18       ");
		sbQuery.append("\n           FROM TBED_RACE_ACDNT_STTS                                                                                    ");
		sbQuery.append("\n          WHERE STND_YEAR      = ?                                                                                      "); 
		sbQuery.append("\n          AND   MBR_CD         = ?                                                                                      ");
		sbQuery.append("\n          AND   TMS            = ?                                                                                      "); 
		sbQuery.append("\n          AND   DAY_ORD        = " + dayOrd + "                                                                                      ");
		sbQuery.append("\n         ) B                                                                                                            ");
		sbQuery.append("\n         GROUP BY A.ACDNT_TPE_NM																						  ");
		
		return sbQuery;
    }
    
    private void setParam(PosContext ctx, PosParameter param, Integer i ){
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("TMS          	  ".trim()));
		
		param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("TMS          	  ".trim()));
		   	       	
		param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("TMS          	 ".trim()));
		
		param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("TMS          	 ".trim()));
		 
		param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("TMS          	 ".trim()));
		
		param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("MBR_CD            ".trim()));
		param.setWhereClauseParameter(i++, ctx.get("TMS          	 ".trim()));
    }
}
