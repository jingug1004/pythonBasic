/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.MonthPayment.e06010010.activity.SaveMonthPay.java
 * ���ϼ���		: ���� ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07030050.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class DeleteIncomIssu extends SnisActivity
{    
    public DeleteIncomIssu()
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
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 

        String stndYearMonth = (String)ctx.get("STND_YEAR_MONTH");
    	Integer incomIssuSeq = new Integer((String)ctx.get("INCOM_ISSU_SEQ"));
    	
    	nSaveCount = nSaveCount + deleteIncomIssue(stndYearMonth, incomIssuSeq);

    }
    
    /**
     * <p> ���� ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteIncomIssue(String stndYearMonth, Integer incomIssuSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, Util.trim(stndYearMonth));
        param.setWhereClauseParameter(i++, incomIssuSeq);
			    		
        return this.getDao("boadao").update("tbeg_incom_issu_df001", param);
    }
    
}
