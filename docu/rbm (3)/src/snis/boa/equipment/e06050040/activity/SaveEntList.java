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
package snis.boa.equipment.e06050040.activity;


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
public class SaveEntList extends SnisActivity
{    
    public SaveEntList()
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
        
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	PosDataset ds;
        int nSize        = 0;
        String sIoDt  = (String)ctx.get("IO_DT");
        
        ds    = (PosDataset) ctx.get("dsEntList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            nSaveCount = nSaveCount + mergeOrderEntList(sIoDt, record);
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
    protected int mergeOrderEntList(String sIoDt, PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        Double dAmount;        
        dAmount = Double.valueOf(record.getAttribute("ENT_CNT").toString()) * Double.valueOf(record.getAttribute("DANGA").toString()); 
        
        param.setValueParamter(i++, sIoDt); 	
        param.setValueParamter(i++, "3");  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        param.setValueParamter(i++, record.getAttribute("JUMUN_NO")); 
        param.setValueParamter(i++, record.getAttribute("ENT_CNT")); 
        param.setValueParamter(i++, dAmount); 
    	param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sIoDt); 	
        param.setValueParamter(i++, "3");  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
    	
        return this.getDao("boadao").update("e06050040_m01", param);
    }

}
