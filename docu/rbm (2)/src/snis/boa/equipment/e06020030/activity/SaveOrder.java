/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020030.activity.SaveOrder.java
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
* �ֹ����� ���� ������ ���, ����, ���� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class SaveOrder extends SnisActivity
{    
    public SaveOrder()
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
    	//�ֹ��� ������ ����
        saveState(ctx);
        
        //�ֹ��� �� ���� ���� 
        new SaveOrderDetail().runActivity(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	PosDataset ds;
        int nSize        = 0;
        ds    = (PosDataset) ctx.get("dsOutOrderList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteOrder(record);  
            	deleteAllOrderParts(record);
            }else{
            	nSaveCount = nSaveCount + mergeOrder(record);            	
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    

    /**
     * <p>�ֹ��� ������ �Է�/ ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergeOrder(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 	
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));  
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));  
        param.setValueParamter(i++, record.getAttribute("PARTS_COM_CD")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_DT")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_CON")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 	
        param.setValueParamter(i++, record.getAttribute("ENT_STATE")); 	
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 	
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        
        return this.getDao("boadao").update("tbef_order_mf001", param);
    }

    /**
     * <p>�ֹ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteOrder(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ")); 
        
        return  this.getDao("boadao").update("tbef_order_df001", param);
    }
       
    /**
     * <p>�ֹ��� ������ ������ ������ ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteAllOrderParts(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));  
        
        return  this.getDao("boadao").update("tbef_order_parts_df002", param);
    }
}
