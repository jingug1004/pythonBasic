/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.MonthPayBacment.e06010010.activity.SaveMonthPaySendMailReq.java
 * ���ϼ���		:  ���� ���� ��û Flag Update
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07010030.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
*  ���� ���� ��û Flag Update�Ѵ� . 
* @auther �輺�� 
* @version 1.0
*/
public class SaveMonthPaySendMailReq extends SnisActivity
{    
    public SaveMonthPaySendMailReq()
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
    	int nDeleteCount = 0; 

        String StndYear = (String)ctx.get("STND_YEAR");
        String payMonth = (String)ctx.get("PAY_MONTH");
        String mailSendReqDt = (String)ctx.get("MAIL_SEND_REQ_DT");
        
        nSaveCount = nSaveCount + saveMonthPaySendMailReq(StndYear, payMonth, mailSendReqDt);

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���� ���� ��û Flag Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveMonthPaySendMailReq(String stndYear, String payMonth, String mailSendReqDt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, mailSendReqDt);  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, payMonth); 
		   
        return this.getDao("boadao").update("tbeg_month_pay_bac_uf031", param);
    }
}
