/*================================================================================
 * 시스템			: 공정 관리
 * 소스파일 이름	: snis.boa.fairness.e05070030.activity.SearchBrchRtrn.java
 * 파일설명		: 지점고액환급정보조회
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2009-12-02
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05070030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchBrchRtrn extends SnisActivity
{    
    public SearchBrchRtrn()
    {
    }

    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
		String strDt = (String) ctx.get("STR_DT");
		String endDt = (String) ctx.get("END_DT");
		String search1 = (String) ctx.get("SEARCH1");
		String search2 = (String) ctx.get("SEARCH2");
		String obtNm = (String) ctx.get("OBT_NM");
		String rate1 = (String) ctx.get("RATE1");
		String rate2 = (String) ctx.get("RATE2");

		String returnMoney = (String) ctx.get("RETURN_MONEY");
	        
        searchState(ctx, strDt, endDt, search1, search2, obtNm, rate1, rate2, returnMoney);
        
        return PosBizControlConstants.SUCCESS;
    }

	/**
	 * <p> 지점고액환급정보조회 </p>
	 * @param ctx		PosContext 저장소
	 * @param record	PosRecord 코드그룹정보
	 * @return none
	 * @throws none
	 */
    protected void searchState(PosContext ctx, String strDt, String endDt, String search1, String search2, String obtNm, String rate1, String rate2, String returnMoney) {
    	StringBuffer sbSql = new StringBuffer();
    	PosRowSet rowset;

    	sbSql.append("\n		    SELECT   SR_NO                                                                                                 ");
    	sbSql.append("\n				   , STND_YEAR                                                                                             ");
    	sbSql.append("\n				   , MBR_CD                                                                                                ");
    	sbSql.append("\n				   , TMS                                                                                                   ");
    	sbSql.append("\n				   , DAY_ORD                                                                                               ");
    	sbSql.append("\n				   , RACE_NO                                                                                               ");
    	sbSql.append("\n				   , OTB_NM                                                                                                ");
    	sbSql.append("\n				   , CUST_NM                                                                                               ");
    	sbSql.append("\n				   , BUY_PLC                                                                                               ");
    	sbSql.append("\n				   , RETURN_PLC                                                                                            ");
    	sbSql.append("\n				   , WINNER                                                                                                ");
    	sbSql.append("\n				   , NVL(EXA_RATE,0) AS EXA_RATE                                                                           ");
    	sbSql.append("\n				   , NVL(EXA_WON1,0) AS EXA_WON1                                                                           ");
    	sbSql.append("\n				   , NVL(EXA_WON2,0) AS EXA_WON2                                                                           ");
    	sbSql.append("\n				   , NVL(EXA_WON3,0) AS EXA_WON3                                                                           ");
    	sbSql.append("\n				   , NVL(EXA_WON4,0) AS EXA_WON4                                                                           ");
    	sbSql.append("\n				   , NVL(EXA_WON5,0) AS EXA_WON5                                                                           ");
    	sbSql.append("\n				   , NVL(EXA_NO1,0) AS EXA_NO1                                                                             ");
    	sbSql.append("\n				   , NVL(EXA_NO2,0) AS EXA_NO2                                                                             ");
    	sbSql.append("\n				   , NVL(EXA_NO3,0) AS EXA_NO3                                                                             ");
    	sbSql.append("\n				   , NVL(EXA_NO4,0) AS EXA_NO4                                                                             ");
    	sbSql.append("\n				   , NVL(EXA_NO5,0) AS EXA_NO5                                                                             ");
    	sbSql.append("\n				   , NVL(QUI_RATE,0) AS QUI_RATE                                                                           ");
    	sbSql.append("\n				   , NVL(QUI_WON1,0) AS QUI_WON1                                                                           ");
    	sbSql.append("\n				   , NVL(QUI_WON2,0) AS QUI_WON2                                                                           ");
    	sbSql.append("\n				   , NVL(QUI_WON3,0) AS QUI_WON3                                                                           ");
    	sbSql.append("\n				   , NVL(QUI_WON4,0) AS QUI_WON4                                                                           ");
    	sbSql.append("\n				   , NVL(QUI_WON5,0) AS QUI_WON5                                                                           ");
    	sbSql.append("\n				   , NVL(QUI_NO1,0) AS QUI_NO1                                                                             ");
    	sbSql.append("\n				   , NVL(QUI_NO2,0) AS QUI_NO2                                                                             ");
    	sbSql.append("\n				   , NVL(QUI_NO3,0) AS QUI_NO3                                                                             ");
    	sbSql.append("\n				   , NVL(QUI_NO4,0) AS QUI_NO4                                                                             ");
    	sbSql.append("\n				   , NVL(QUI_NO5,0) AS QUI_NO5                                                                             ");
    	sbSql.append("\n				   , NVL(TRI_RATE,0) AS TRI_RATE                                                                           ");
    	sbSql.append("\n				   , NVL(TRI_WON1,0) AS TRI_WON1                                                                           ");
    	sbSql.append("\n				   , NVL(TRI_WON2,0) AS TRI_WON2                                                                           ");
    	sbSql.append("\n				   , NVL(TRI_WON3,0) AS TRI_WON3                                                                           ");
    	sbSql.append("\n				   , NVL(TRI_WON4,0) AS TRI_WON4                                                                           ");
    	sbSql.append("\n				   , NVL(TRI_WON5,0) AS TRI_WON5                                                                           ");
    	sbSql.append("\n				   , NVL(TRI_NO1,0) AS TRI_NO1                                                                             ");
    	sbSql.append("\n				   , NVL(TRI_NO2,0) AS TRI_NO2                                                                             ");
    	sbSql.append("\n				   , NVL(TRI_NO3,0) AS TRI_NO3                                                                             ");
    	sbSql.append("\n				   , NVL(TRI_NO4,0) AS TRI_NO4                                                                             ");
    	sbSql.append("\n				   , NVL(TRI_NO5,0) AS TRI_NO5                                                                             ");
    	sbSql.append("\n				   , NVL(TOTAL_RETURN,0) AS TOTAL_RETURN                                                                   ");
    	sbSql.append("\n				   , VEH_NO                                                                                                ");
    	sbSql.append("\n				   , VEH_COLOR                                                                                             ");
    	sbSql.append("\n				   , CUST_TEL                                                                                              ");
    	sbSql.append("\n				   , CUST_ADDR                                                                                             ");
    	sbSql.append("\n				   , RMK                                                                                                   ");
    	sbSql.append("\n				   , INST_NM                                                                                               ");
    	sbSql.append("\n				   , REG_IP                                                                                                ");
    	sbSql.append("\n				   , PHOTO_NM                                                                                              ");
    	sbSql.append("\n				   , PHOTO_PATH                                                                                            ");
    	sbSql.append("\n				   , PHOTO_TYPE                                                                                            ");
    	sbSql.append("\n				   , FILE_NM                                                                                               ");
    	sbSql.append("\n				   , FILE_PATH                                                                                             ");
    	sbSql.append("\n				   , FILE_TYPE                                                                                             ");
    	sbSql.append("\n				   , FILE_SIZE                                                                                             ");
    	sbSql.append("\n				   , TO_CHAR(INST_DTM,'YYYYMMDD') AS INST_DTM                                                              ");
    	sbSql.append("\n			FROM   TBEE_BRCH_RTRN                                                                                          ");
    	sbSql.append("\n            WHERE  (STND_YEAR, MBR_CD, TMS, DAY_ORD, RACE_NO) IN (SELECT STND_YEAR, MBR_CD, TMS, DAY_ORD, RACE_NO          ");
    	sbSql.append("\n																	FROM TBEB_ORGAN                                        ");
    	sbSql.append("\n																   WHERE RACE_DAY BETWEEN ? AND ?                          ");
    	sbSql.append("\n																	 AND MBR_CD = '001')                                   ");

    	PosParameter param = new PosParameter();
        int i = 0;
        //param 셋팅
		param.setWhereClauseParameter(i++, strDt.trim());
		param.setWhereClauseParameter(i++, endDt.trim());
        
		if (!Util.nullToStr(search1).equals("")) {
			if ("1".equals(search1)) {
		    	sbSql.append("\n			  AND INST_NM LIKE '%'||?||'%'  ");
				param.setWhereClauseParameter(i++, search2.trim());
			} else if ("2".equals(search1)) {
				sbSql.append("\n			  AND ((WINNER like '%'||?||'%') or (CUST_NM  like '%'||?||'%'))  ");
				param.setWhereClauseParameter(i++, search2.trim());
				param.setWhereClauseParameter(i++, search2.trim());
			} else if ("3".equals(search1)) {
				sbSql.append("\n			  AND ((BUY_PLC like '%'||?||'%') or (RETURN_PLC like '%'||?||'%'))  ");
				param.setWhereClauseParameter(i++, search2.trim());
				param.setWhereClauseParameter(i++, search2.trim());
			}
		}
		if (!Util.nullToStr(obtNm).equals("")) {
			sbSql.append("\n			  AND OTB_NM LIKE '%'||?||'%'  ");
			param.setWhereClauseParameter(i++, obtNm.trim());
		}
		if (!Util.nullToStr(rate2).equals("")) {
			if ("1".equals(rate1)) {
				sbSql.append("\n			  AND EXA_RATE >= ?  ");
				param.setWhereClauseParameter(i++, rate2.trim());
			} else if ("2".equals(rate1)) {
				sbSql.append("\n			  AND QUI_RATE >= ?  ");
				param.setWhereClauseParameter(i++, rate2.trim());
			} else if ("3".equals(rate1)) {
				sbSql.append("\n			  AND TRI_RATE >= ?  ");
				param.setWhereClauseParameter(i++, rate2.trim());
			}
		}
		sbSql.append("\n			  AND TOTAL_RETURN >= ?  ");
		param.setWhereClauseParameter(i++, returnMoney.trim());

		sbSql.append("\n			  ORDER BY INST_DTM DESC  ");

        rowset = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);

    	String sResultKey = "dsOutBrchRtrn";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
	}

}
