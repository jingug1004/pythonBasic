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
package snis.boa.equipment.e06050050.activity;


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
public class SaveOutList extends SnisActivity
{    
    public SaveOutList()
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
    	//����� ����
        saveState(ctx);        
        
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	PosDataset ds;
        int nSize        = 0;
        String sIoDt  = (String)ctx.get("OUT_DT");
        
        ds    = (PosDataset) ctx.get("dsOutSuppList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteOutSuppList(record);  
            } else {
            	nSaveCount = nSaveCount + mergeOutSuppList(sIoDt, record);
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  

    /**
     * <p>����� �Է�/ ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergeOutSuppList(String sIoDt, PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        Double dAmount;        
        dAmount = Double.valueOf(record.getAttribute("OUT_QTY").toString()) * Double.valueOf(record.getAttribute("DANGA").toString()); 
        
        param.setValueParamter(i++, sIoDt); 	
        param.setValueParamter(i++, "4");  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        param.setValueParamter(i++, record.getAttribute("OUT_QTY")); 
        param.setValueParamter(i++, dAmount); 
        param.setValueParamter(i++, record.getAttribute("ACCEPT_NM")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sIoDt); 	
        param.setValueParamter(i++, "4");  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
    	
        return this.getDao("boadao").update("e06050050_m01", param);
    }

    /**
     * <p>����� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteOutSuppList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("OUT_DT")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_CD")); 
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        return  this.getDao("boadao").update("e06050050_d01", param);
    }
}
