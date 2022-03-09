/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.boa.racer.e03010010.activity.SaveRacerInfo.java
 * ���ϼ���		: �����������
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
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
    	sbQuery.append("\n    	       TRM.RACER_NO									--������ȣ		");
    	sbQuery.append("\n    	       ,TRM.NM_KOR									--�̸�-�ѱ�		");
    	sbQuery.append("\n    	       ,TRM.RACER_GRD_CD							--��������ڵ�		");
    	sbQuery.append("\n    	       ,TRD.NATV_PLAC_CD							--������ڵ�		");
    	sbQuery.append("\n    	       ,TRD.HOME_PHONE_NO							--��ȭ��ȣ		");
    	sbQuery.append("\n    	       ,TRD.CELPON_NO								--�ڵ�����ȣ		");
    	sbQuery.append("\n    	       ,TRD.POST_NO									--�����ȣ		");
    	sbQuery.append("\n    	       ,TRD.ADD1_HOME||' '||TRD.ADD2_HOME ADD_HOME	--�ּ�			");
    	sbQuery.append("\n    	       ,GET_DEC(TRD.RES_NO) RES_NO					--�ֹε�Ϲ�ȣ		");
    	sbQuery.append("\n    	       ,CASE WHEN SUBSTR(GET_DEC(TRD.RES_NO),7,1) IN ('1','2','7','8')");
    	sbQuery.append("\n    	       THEN TO_NUMBER(TO_CHAR(SYSDATE,'YYYY')) - TO_NUMBER('19'||SUBSTR(GET_DEC(TRD.RES_NO),1,2))	");
    	sbQuery.append("\n    	       ELSE TO_NUMBER(TO_CHAR(SYSDATE,'YYYY')) - TO_NUMBER('20'||SUBSTR(GET_DEC(TRD.RES_NO),1,2))	");
    	sbQuery.append("\n    	       END AS AGE 									-- ����             	");
    	sbQuery.append("\n    	       ,TRD.WGHT   									-- ü�� 			");
    	sbQuery.append("\n    	       ,TRD.HGHT   									-- ���� 			");
    	sbQuery.append("\n    	       ,GET_CD_NM('E00023',TRI.BK_CD) BANK			-- ���� 			");
    	sbQuery.append("\n    	       ,GET_DEC(TRI.BK_ACCUNT) BK_ACCUNT			-- ���¹�ȣ 		");
    	sbQuery.append("\n    	       ,TRD.NATY_UNIV   							-- ���б� 		");
    	sbQuery.append("\n    	       ,DECODE(SUBSTR(GET_DEC(TRD.RES_NO),7,1),1,'��',3,'��',2,'��',4,'��') RACER_SEX	--����			");
    	sbQuery.append("\n 			   ,DECODE((SELECT CD_NM FROM TBEA_SPEC_CD WHERE GRP_CD ='E00082' AND CD = TRD.MRRG_CD),'��ȥ','N','Y') MRRG_YN		");
    	sbQuery.append("\n 			   ,DECODE(CAR_NO,NULL,'N','Y') CAR_YN			--������������		");
    	sbQuery.append("\n 			   ,DECODE(NATY_RACER_YN,'Y','Y','N') NATY_RACER_YN	--������ſ���	");
    	sbQuery.append("\n 			   ,RACER_STAT_CD								--��������		");
    	sbQuery.append("\n 			   ,CNL_DT										--�������		");
    	sbQuery.append("\n 			   ,TRD.PTO_NM									--����������		");
    	sbQuery.append("\n 			   ,TRD.PTO_PATH								--�������� ��		");
    	sbQuery.append("\n 			   ,TRM.RACER_PERIO_NO || '��' as RACER_PERIO_NO	--�������		");
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
    		if(ctx.get("RACER_STAT_CD".trim()).equals("003")) { //��Ҽ���
    			sbQuery.append("\n AND TRM.RACER_STAT_CD = '003'                           		");
    		}else{ //������ ������ ��缱��
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
