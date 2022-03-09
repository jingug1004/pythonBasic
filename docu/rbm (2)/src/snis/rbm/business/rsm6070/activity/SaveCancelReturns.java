/*
 * ================================================================================
 * �ý��� : �нǽŰ� ����
 * ���� �̸� : snis.rbm.business.rsm6040.activity.SaveLossReturnsReceipt.java 
 * ���ϼ��� :  
 * �ۼ��� : 
 * ���� : 1.0.0 �������� : 2011- 10 - 26
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm6070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveCancelReturns extends SnisActivity {
	
	public SaveCancelReturns(){}

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
    	int nSize        = 0;
        int nTempCnt     = 0;
        int nTempFileCnt     = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
        
    	
        sDsName = "dsTsnRcptResult";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            nTempCnt =  updatetCancelTicStas(record);	          
	        	nSaveCount = nTempCnt;
	        	continue;	  		  	            
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }
    /**
     * <p> Ƽ�ϻ���(ȯ��->���)ó�� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatetCancelTicStas(PosRecord record) 
    {	   
    	 int i = 0;   
         PosParameter param = new PosParameter();
         
         param.setValueParamter(i++, "004"							);	// Ƽ�ϻ���(ȯ�����->����)
         param.setValueParamter(i++, ""								);	// ȯ���ںμ���
         param.setValueParamter(i++, ""								);	// ȯ����ID
         param.setValueParamter(i++, ""								);	// ȯ��������
         param.setValueParamter(i++, SESSION_USER_ID				);	// �����ID(�ۼ���)         
         param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")	);	// ��������
         param.setValueParamter(i++, record.getAttribute("TSN_NO")	);  // �нǽŰ���ȣ
         
         int dmlcount = this.getDao("rbmdao").update("rsm6070_u01", param);
         
         return dmlcount;
    }
    /**
     * <p> ȯ��ó������(���)   ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatetCencelRefund(PosRecord record) 
    {	   
		 
        int i = 0;   
        PosParameter param = new PosParameter();   
        
        param.setValueParamter(i++, SESSION_USER_ID);							// ������ID        
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO"));		// �нǽŰ���ȣ
        
        int dmlcount = this.getDao("rbmdao").update("rsm6070_u04", param);
        
        return dmlcount;
    }
}
