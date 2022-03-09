/*================================================================================
 * 시스템			: 선수관리
 * 소스파일 이름	: snis.boa.racer.e03110010.activity.SearchCertIssu.java
 * 파일설명		: 증명서정보 조회
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03110010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchCertIssu extends SnisActivity {
    public SearchCertIssu()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	SearchRacer(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchRacer(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();

    	sbQuery.append("\n SELECT 																																	");
    	sbQuery.append("\n 	TCIH.CERT_KND_CD													--증명종류코드			");
    	sbQuery.append("\n     ,TCIH.SEQ														--일련번호			");
    	sbQuery.append("\n     ,TCIH.ISSU_YEAR													--발급년도			");
    	sbQuery.append("\n     ,TCIH.ISSU_NO													--발급번호			");
    	sbQuery.append("\n     ,TO_CHAR(TCIH.ISSU_DTM,'YYYYMMDD') ISSU_DTM						--발급일자			");
    	sbQuery.append("\n     ,TO_CHAR(TCIH.INST_DTM,'YYYYMMDD') INST_DT						--등록일자			");
    	sbQuery.append("\n     ,TCIH.RACER_NO													--등록번호			");
    	sbQuery.append("\n     ,TRM.NM_KOR 														--선수명				");
    	sbQuery.append("\n     ,GET_DEC(TRD.RES_NO)				  AS RES_NO 					--주민등록번호			");
    	sbQuery.append("\n     ,TCIH.PURPOSES_CD												--용도코드			");
    	sbQuery.append("\n     ,TCIH.ISSU_CNT													--발급부수			");
    	sbQuery.append("\n     ,TCIH.PRS_STAT_CD												--발급상태코드			");
    	sbQuery.append("\n     ,CASE WHEN TCIH.ISSU_YN ='Y' THEN '발급' END AS ISSU_YN			--발급 여부			");
    	sbQuery.append("\n     ,(SELECT USER_NM FROM VWEA_USER WHERE USER_ID(+)    = TCIH.REQ_USER_ID) AS REQ_USER_NM	--신청자명			");
    	sbQuery.append("\n     ,(SELECT USER_NM FROM VWEA_USER WHERE USER_ID(+)    = TCIH.CONFIRM_ID) AS CONFIRM_NM		--확인자명			");
    	sbQuery.append("\n	   ,TCIH.REQ_TYPE													--신청구분			");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n	   ,TCIH.RJT_RS														--반려사유			");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n	   ,TRD.CELPON_NO													--휴대전화			");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n	   ,DECODE(TCIH.PERIOD_CNTN, NULL, DTCIH.PERIOD_CNTN, TCIH.PERIOD_CNTN) AS PERIOD_CNTN		--기간내용			");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n FROM TBEC_CERT_ISSU_HISTORY TCIH																					");
    	sbQuery.append("\n     ,TBEC_RACER_MASTER TRM																");
    	sbQuery.append("\n     ,TBEC_RACER_DETAIL TRD																");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n	   ,(																					"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n			SELECT		ITCIH.PERIOD_CNTN													"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n						,ITCIH.RACER_NO														"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n			FROM		TBEC_CERT_ISSU_HISTORY 		ITCIH									"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n			WHERE		ITCIH.PERIOD_CNTN			IS NOT NULL								"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n			AND			ITCIH.INST_DTM 	= (	SELECT	MAX(INST_DTM)							"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n											FROM	TBEC_CERT_ISSU_HISTORY 					"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n											WHERE	RACEr_NO =  ITCIH.RACER_NO				"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n											AND		PERIOD_CNTN IS NOT NULL					"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n										   )												"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n		)				DTCIH																"); //IWORKS_SFR006 2013.12.21
    	//sbQuery.append("\n WHERE TCIH.CERT_KND_CD 	= NVL(?, TCIH.CERT_KND_CD)										");
    	sbQuery.append("\n WHERE 1=1									");
    	sbQuery.append("\n     AND TCIH.ISSU_YEAR 	= NVL(?, TCIH.ISSU_YEAR)										");
    	sbQuery.append("\n     AND TCIH.PURPOSES_CD = NVL(?, TCIH.PURPOSES_CD) 								");      
    	sbQuery.append("\n     AND TRM.NM_KOR LIKE '%' ||NVL(?, TRM.NM_KOR)|| '%'								");
    	
    	if(ctx.get("ISSU_DTM".trim()) != null && !"".equals(ctx.get("ISSU_DTM".trim()))){
        	sbQuery.append("\n     AND TCIH.ISSU_DTM 																	");
        	sbQuery.append("\n         BETWEEN TO_DATE(?,'YYYYMMDD') 													");
        	sbQuery.append("\n         AND TO_DATE(?,'YYYYMMDD')+0.99999												");
    	}
    	
    	//IWORKS_SFR006 2013.12.21
    	if(ctx.get("REQ_TYPE".trim()) != null && !"0".equals(ctx.get("REQ_TYPE".trim()))){
    		sbQuery.append("\n     AND TCIH.REQ_TYPE 	= NVL(?, TCIH.REQ_TYPE)										");
    	}
    	
    	sbQuery.append("\n     AND TCIH.RACER_NO 	= TRM.RACER_NO													");
    	sbQuery.append("\n     AND TCIH.RACER_NO 	= TRD.RACER_NO													"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n     AND TCIH.RACER_NO 	= DTCIH.RACER_NO(+)												"); //IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n ORDER BY TCIH.ISSU_NO DESC, TCIH.SEQ DESC												");
    	

        PosParameter param = new PosParameter();
        int i = 0;
    	//param.setWhereClauseParameter(i++, ctx.get("CERT_KND_CD         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("ISSU_YEAR         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("PURPOSES_CD         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("NM_KOR         ".trim()));
    	if(ctx.get("ISSU_DTM".trim()) != null && !"".equals(ctx.get("ISSU_DTM".trim()))){
    		param.setWhereClauseParameter(i++, ctx.get("ISSU_DTM        ".trim()));
    		param.setWhereClauseParameter(i++, ctx.get("ISSU_DTM        ".trim()));
    	}

    	//IWORKS_SFR006 2013.12.21
    	if(ctx.get("REQ_TYPE".trim()) != null && !"0".equals(ctx.get("REQ_TYPE".trim()))){
    		param.setWhereClauseParameter(i++, ctx.get("REQ_TYPE        ".trim()));
    	}
    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutCertIssu";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    }

}
