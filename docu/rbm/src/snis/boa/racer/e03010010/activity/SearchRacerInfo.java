/*================================================================================
 * 시스템			: 선수관리
 * 소스파일 이름	: snis.boa.racer.e03010010.activity.SaveRacerInfo.java
 * 파일설명		: 선수정보등록
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03010010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRacerInfo extends SnisActivity {
    public SearchRacerInfo()
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
    	
    	sbQuery.append("\n SELECT 																	");
    	sbQuery.append("\n    	       TRM.RACER_NO									--선수번호		");
    	sbQuery.append("\n    	       ,TRM.NM_KOR									--이름-한글		");
    	sbQuery.append("\n    	       ,TRM.RACER_GRD_CD							--선수등급코드		");
    	sbQuery.append("\n    	       ,TRD.NATV_PLAC_CD							--출신지코드		");
    	sbQuery.append("\n    	       ,TRD.HOME_PHONE_NO							--전화번호		");
    	sbQuery.append("\n    	       ,TRD.CELPON_NO								--핸드폰번호		");
    	sbQuery.append("\n    	       ,TRD.POST_NO									--우편번호		");
    	sbQuery.append("\n    	       ,TRD.ADD1_HOME||' '||TRD.ADD2_HOME ADD_HOME	--주소			");
    	sbQuery.append("\n    	       ,GET_DEC(TRD.RES_NO) RES_NO					--주민등록번호		");
    	sbQuery.append("\n    	       ,CASE WHEN SUBSTR(GET_DEC(TRD.RES_NO),7,1) IN ('1','2','7','8')");
    	sbQuery.append("\n    	       THEN TO_NUMBER(TO_CHAR(SYSDATE,'YYYY')) - TO_NUMBER('19'||SUBSTR(GET_DEC(TRD.RES_NO),1,2))	");
    	sbQuery.append("\n    	       ELSE TO_NUMBER(TO_CHAR(SYSDATE,'YYYY')) - TO_NUMBER('20'||SUBSTR(GET_DEC(TRD.RES_NO),1,2))	");
    	sbQuery.append("\n    	       END AS AGE 									-- 나이             	");
    	sbQuery.append("\n    	       ,TRD.WGHT   									-- 체중 			");
    	sbQuery.append("\n    	       ,TRD.HGHT   									-- 신장 			");
    	sbQuery.append("\n    	       ,GET_CD_NM('E00023',TRI.BK_CD) BANK			-- 은행 			");
    	sbQuery.append("\n    	       ,GET_DEC(TRI.BK_ACCUNT) BK_ACCUNT			-- 계좌번호 		");
    	sbQuery.append("\n    	       ,TRD.NATY_UNIV   							-- 대학교 		");
    	sbQuery.append("\n    	       ,DECODE(SUBSTR(GET_DEC(TRD.RES_NO),7,1),1,'남',3,'남',2,'여',4,'여') RACER_SEX	--성별			");
    	sbQuery.append("\n 			   ,DECODE((SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD ='E00082' AND CD = TRD.MRRG_CD),'미혼','N','Y') MRRG_YN		");
    	sbQuery.append("\n 			   ,DECODE(CAR_NO,NULL,'N','Y') CAR_YN			--차량소유여부		");
    	sbQuery.append("\n 			   ,DECODE(NATY_RACER_YN,'Y','Y','N') NATY_RACER_YN	--선수출신여부	");
    	sbQuery.append("\n 			   ,RACER_STAT_CD								--선수상태		");
    	sbQuery.append("\n 			   ,CNL_DT										--취소일자		");
    	sbQuery.append("\n 			   ,TRD.PTO_NM									--선수사진명		");
    	sbQuery.append("\n 			   ,TRD.PTO_PATH								--선수사진 위		");
    	sbQuery.append("\n 			   ,TRM.RACER_PERIO_NO || '기' as RACER_PERIO_NO	--선수기수		");
    	sbQuery.append("\n FROM 	TBEC_RACER_MASTER TRM											");
    	sbQuery.append("\n ,TBEC_RACER_DETAIL TRD													");
    	sbQuery.append("\n ,TBEG_RACER_COM_INFO TRI													");
    	sbQuery.append("\n WHERE TRM.RACER_NO =  TRD.RACER_NO(+)                                    ");
    	sbQuery.append("\n AND TRM.RACER_NO =  TRI.RACER_NO(+)                                    ");
    	sbQuery.append("\n AND TRM.NM_KOR LIKE NVL(?, TRM.NM_KOR) || '%'                            ");
    	if(ctx.get("RACER_GRD_CD".trim()) != null && !"".equals(ctx.get("RACER_GRD_CD".trim()))){
    		sbQuery.append("\n AND TRM.RACER_GRD_CD = ?                           					");
    	}
    	if(ctx.get("RACER_STAT_CD".trim()) != null && !"".equals(ctx.get("RACER_STAT_CD".trim()))){
    		if(ctx.get("RACER_STAT_CD".trim()).equals("003")) { //취소선수
    			sbQuery.append("\n AND TRM.RACER_STAT_CD = '003'                           		");
    		}else{ //신인을 포함한 모든선수
    			sbQuery.append("\n AND TRM.RACER_STAT_CD != '003'                           		");
    		}
    	}
    	//sbQuery.append("\n ORDER BY TRM.RACER_PERIO_NO,TRM.RACER_NO									");
    	sbQuery.append("\n ORDER BY TRM.RACER_NO													");

        PosParameter param = new PosParameter();
        int i = 0;
    	param.setWhereClauseParameter(i++, ctx.get("NM_KOR         ".trim()));
    	if(ctx.get("RACER_GRD_CD".trim()) != null && !"".equals(ctx.get("RACER_GRD_CD".trim()))){
    		param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD        ".trim()));
    	}

    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutSRacer";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    }

}
