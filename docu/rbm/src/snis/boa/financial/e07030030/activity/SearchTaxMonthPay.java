/*================================================================================
 * �ý���			: ��ݰ��� 
 * �ҽ����� �̸�	: snis.boa.financial.e07030030.activity.SearchTaxMonthPay.java
 * ���ϼ���		: ��õ¡���������� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07030030.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ��õ¡���������� ��ȸ �Ѵ�. 
* @auther ������  
* @version 1.0
*/
public class SearchTaxMonthPay extends SnisActivity
{    
    public SearchTaxMonthPay()
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
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
		getComInfo(ctx);
		getRacerComInfo(ctx);
		getMonthPay(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��õ¡��������  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutComInfo";
		
        PosParameter param = new PosParameter();
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_tax_month_ff001", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> ��õ¡��������  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getRacerComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutRacerComInfo";
        String	racerNo   = (String) ctx.get("RACER_NO");
        String	nmKor   = (String) ctx.get("NM_KOR");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, racerNo);
        param.setWhereClauseParameter(paramIndex++, nmKor);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_tax_month_ff002", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * <p> ��õ¡��������  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getMonthPay(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPay";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	racerNo   = (String) ctx.get("RACER_NO");
        String	nmKor   = (String) ctx.get("NM_KOR");
        String	pmtDt   = (String) ctx.get("PMT_DT");

        
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, racerNo);
        param.setWhereClauseParameter(paramIndex++, nmKor);
        param.setWhereClauseParameter(paramIndex++, pmtDt);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, racerNo);
        param.setWhereClauseParameter(paramIndex++, nmKor);
        param.setWhereClauseParameter(paramIndex++, pmtDt);
        param.setWhereClauseParameter(paramIndex++, pmtDt);
        param.setWhereClauseParameter(paramIndex++, pmtDt);
        
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_tax_month_ff003", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
}

