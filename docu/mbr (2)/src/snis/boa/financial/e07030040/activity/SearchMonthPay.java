/*================================================================================
 * �ý���			: ��ݰ��� 
 * �ҽ����� �̸�	: snis.boa.financial.e07030040.activity.SearchMonthPay.java
 * ���ϼ���		: ���ο�������� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-27
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07030040.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ���ο�������� ��ȸ �Ѵ�. 
* @auther ������  
* @version 1.0
*/
public class SearchMonthPay extends SnisActivity
{    
    public SearchMonthPay()
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
		        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ���ο������  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPay";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	racerNo   = (String) ctx.get("RACER_NO");
        String	nmKor   = (String) ctx.get("NM_KOR");
        String	suppAmt10   = (String) ctx.get("SUPP_AMT_10");
        String	awardAmt   = (String) ctx.get("AWARD_AMT");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, suppAmt10);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, awardAmt);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, nmKor);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_tax_month_ff004", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
}

