/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020030.activity.SaveOrderDetail.java
 * ���ϼ���		: �ֹ����� �����Ѵ�.  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020030.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* �ֹ����� ���� �������� ���, ����, ���� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class SaveOrderDetail extends SnisActivity
{    
    public SaveOrderDetail()
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
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	
    	int nSaveCountd   = 0; 
    	int nDeleteCountd = 0; 
    	
    	PosDataset dsd;
        int nSized        = 0;
        
        dsd    = (PosDataset) ctx.get("dsOutOrderPartsList");
        nSized = dsd.getRecordCount();
        for ( int j= 0; j < nSized; j++ ){
            PosRecord recordd = dsd.getRecord(j);
            if (recordd.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCountd = nDeleteCountd + deleteOrderParts(recordd);
            }else{
            	nSaveCountd = nSaveCountd + mergeOrderParts(recordd);
            }
        }
        Util.setSaveCount  (ctx, nSaveCountd     );
        Util.setDeleteCount(ctx, nDeleteCountd   );
    }
   

    /**
     * <p>�ֹ��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergeOrderParts(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));  
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));  
        param.setValueParamter(i++, record.getAttribute("PARTS_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_CNT")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 			 
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        
        return this.getDao("boadao").update("tbef_order_parts_mf001", param);
    }

    /**
     * <p>�ֹ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteOrderParts(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ")); 
        param.setValueParamter(i++, record.getAttribute("PARTS_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR")); 
        
        return  this.getDao("boadao").update("tbef_order_parts_df001", param);
    }
}
