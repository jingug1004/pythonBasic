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
package snis.boa.financial.e07030051.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.SaveMonthPayBac;
import snis.boa.system.e01010061.activity.SaveProcessProg;

/**
* ���� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveIncomIssuPrintCheck extends SnisActivity
{    
    public SaveIncomIssuPrintCheck()
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutIncomIssu");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveIncomIssuPrintCheck(record);
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
  
    
    
    /**
     * <p> ���� ���Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveIncomIssuPrintCheck(PosRecord record)
    {
    	int effectedRowCnt = updateIncomIssuPrintCheck(record);
        return effectedRowCnt;
    }

    /**
     * <p> ���� ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateIncomIssuPrintCheck(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("PRS_STAT_CD"));  
        param.setValueParamter(i++, SESSION_USER_ID); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("INCOM_ISSU_SEQ"));  
			    		
        return this.getDao("boadao").update("tbeg_incom_issu_uf002", param);
    }
    
    
    
    
    
}
