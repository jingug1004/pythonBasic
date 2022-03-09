/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.boa.racer.e03110010.activity.SearchCertIssu.java
 * ���ϼ���		: �������� ��ȸ
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2008-03-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
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
    	sbQuery.append("\n 	TCIH.CERT_KND_CD													--���������ڵ�			");
    	sbQuery.append("\n     ,TCIH.SEQ														--�Ϸù�ȣ			");
    	sbQuery.append("\n     ,TCIH.ISSU_YEAR													--�߱޳⵵			");
    	sbQuery.append("\n     ,TCIH.ISSU_NO													--�߱޹�ȣ			");
    	sbQuery.append("\n     ,TO_CHAR(TCIH.ISSU_DTM,'YYYYMMDD') ISSU_DTM						--�߱�����			");
    	sbQuery.append("\n     ,TO_CHAR(TCIH.INST_DTM,'YYYYMMDD') INST_DT						--�������			");
    	sbQuery.append("\n     ,TCIH.RACER_NO													--��Ϲ�ȣ			");
    	sbQuery.append("\n     ,TRM.NM_KOR 														--������				");
    	sbQuery.append("\n     ,GET_DEC(TRD.RES_NO)				  AS RES_NO 					--�ֹε�Ϲ�ȣ			");
    	sbQuery.append("\n     ,TCIH.PURPOSES_CD												--�뵵�ڵ�			");
    	sbQuery.append("\n     ,TCIH.ISSU_CNT													--�߱޺μ�			");
    	sbQuery.append("\n     ,TCIH.PRS_STAT_CD												--�߱޻����ڵ�			");
    	sbQuery.append("\n     ,CASE WHEN TCIH.ISSU_YN ='Y' THEN '�߱�' END AS ISSU_YN			--�߱� ����			");
    	sbQuery.append("\n     ,(SELECT USER_NM FROM VWEA_USER WHERE USER_ID(+)    = TCIH.REQ_USER_ID) AS REQ_USER_NM	--��û�ڸ�			");
    	sbQuery.append("\n     ,(SELECT USER_NM FROM VWEA_USER WHERE USER_ID(+)    = TCIH.CONFIRM_ID) AS CONFIRM_NM		--Ȯ���ڸ�			");
    	sbQuery.append("\n	   ,TCIH.REQ_TYPE													--��û����			");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n	   ,TCIH.RJT_RS														--�ݷ�����			");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n	   ,TRD.CELPON_NO													--�޴���ȭ			");	//IWORKS_SFR006 2013.12.21
    	sbQuery.append("\n	   ,DECODE(TCIH.PERIOD_CNTN, NULL, DTCIH.PERIOD_CNTN, TCIH.PERIOD_CNTN) AS PERIOD_CNTN		--�Ⱓ����			");	//IWORKS_SFR006 2013.12.21
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
