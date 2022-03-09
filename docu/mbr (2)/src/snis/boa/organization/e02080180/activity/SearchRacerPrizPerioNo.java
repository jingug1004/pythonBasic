/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030020.activity.SearchRacerPrizPerioNo.java
 * ���ϼ���		: ����� ���� ��� ��ȸ 
 * �ۼ���			: �輺��
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02080180.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����� ���� ��� ��ȸ�ϴ� Ŭ�����̴�.
* @auther �輺��
* @version 1.0
*/
public class SearchRacerPrizPerioNo extends SnisActivity
{    
	
    public SearchRacerPrizPerioNo()
    {
    }

    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchRacerPriz(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ����� ���� ��� ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public void searchRacerPriz(PosContext ctx)
    {
        String	sResultKey = "dsOutRacerPrizPerioNo";
        String	strDt   = (String) ctx.get("STR_DT");
        String	endDt   = (String) ctx.get("END_DT");
        int  MAX_PERIO_NO=  new Integer((String) ctx.get("MAX_RACER_PERIO_NO")).intValue();
        
        PosParameter param = new PosParameter();
        int paramIndex = 0;
        StringBuffer sb = new StringBuffer();
        sb.append(" -- ����� ���� ���  (���� query�� java�ܿ��� ������) \n");
        sb.append(" SELECT   \n");
        sb.append("     TRP.STND_YEAR,   \n");
        for(int i=1; i<=MAX_PERIO_NO; i++){
        	sb.append("     FLOOR(SUM(CASE WHEN TRM.RACER_PERIO_NO = "+i+" THEN RANK_AMT + RUN_AMT + EVENT_AMT + WAIT_AMT + SAFY_AMT + STR_AMT+ WIN_AMT + ETC_AMT ELSE 0 END)/CASE WHEN COUNT(DISTINCT CASE WHEN TRM.RACER_PERIO_NO="+i+" THEN TRP.RACER_NO ELSE NULL END)=0 THEN 1 ELSE COUNT(DISTINCT CASE WHEN TRM.RACER_PERIO_NO="+i+" THEN TRP.RACER_NO ELSE NULL END) END) AS PRIZ_AMT_"+i+", \n");
        }
        sb.append("     FLOOR(SUM(RANK_AMT + RUN_AMT + EVENT_AMT + WAIT_AMT + SAFY_AMT + STR_AMT+ WIN_AMT + ETC_AMT)/count(distinct TRP.RACER_NO)) AS PRIZ_AMT_AVG \n");
        sb.append(" FROM  TBEB_RACE_TMS TRT,  -- ���� ȸ�� TABLE \n");
        sb.append("     TBEG_RACE_PRIZ TRP,   -- ���ֺ� ��� TABLE \n");
        sb.append("     TBEC_RACER_MASTER TRM   -- ���� MASTER \n");
        sb.append(" WHERE TRP.STND_YEAR = TRT.STND_YEAR \n");
        sb.append(" AND TRP.TMS = TRT.TMS \n");
        sb.append(" AND TRP.RACER_NO = TRM.RACER_NO \n");
        sb.append(" AND TRP.MBR_CD = TRT.MBR_CD \n");
        sb.append(" AND TRT.TMS_PRIZ_FINISH_YN = 'Y' \n");
        sb.append(" AND (TRT.STND_YEAR,TRP.MBR_CD, TRP.TMS) IN (SELECT STND_YEAR, MBR_CD, TMS FROM TBEB_RACE_DAY_ORD WHERE  RACE_DAY BETWEEN ? AND ?)     \n"); 
        sb.append(" GROUP BY TRP.STND_YEAR \n");
        sb.append(" ORDER BY TRP.STND_YEAR DESC\n");

        param.setWhereClauseParameter(paramIndex++ , strDt);
        param.setWhereClauseParameter(paramIndex++ , endDt);
        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sb.toString(), param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
}
