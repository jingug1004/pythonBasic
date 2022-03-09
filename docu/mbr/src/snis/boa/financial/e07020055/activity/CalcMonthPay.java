/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.financial.e06010010.activity.CalcMonthPay.java
 * ���ϼ���		: ���� ���ޱ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07020055.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.*;

/**
* ȸ���� ���� ��� ��� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class CalcMonthPay extends SnisActivity
{    
    public CalcMonthPay()
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
    	calcMonthPay(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void calcMonthPay(PosContext ctx) 
    {
    	String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));	//���� �ƴ� ȸ���� ����(2014.6.27)
    	String sPayDt = (String)ctx.get("PAY_DT");

    	clearMonthPay(stndYear, payMonth);
    	calcMonthPay(stndYear, payMonth, sPayDt) ;
    	new SaveMonthPay().updateTax(stndYear, payMonth, SESSION_USER_ID);
    	
    	//ó�������� Update
    	new SaveMonthPayBac().saveMonthPrizFinish(stndYear, payMonth, "N", sPayDt);
    }

    

    /**
     * <p> ���� ������� ���   Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int calcMonthPay(String stndYear,  Integer month, String sPayDt) 
    {
        PosParameter param = new PosParameter();
        int i = 0;     
        param.setValueParamter(i++, sPayDt);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  

        
        
        return this.getDao("boadao").update("tbeg_month_pay_mf001", param);
    }
    
    

    /**
     * <p>ȸ���� ���� ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int clearMonthPay(String stndYear, Integer month) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        return this.getDao("boadao").update("tbeg_month_pay_uf010", param);
    }
}
