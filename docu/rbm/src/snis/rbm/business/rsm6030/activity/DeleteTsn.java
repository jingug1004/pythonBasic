/*
 * ================================================================================
 * �ý��� : �нǽŰ� �������� ��� ����
 * ���� �̸� : snis.rbm.business.rsm6030.activity.deleteTsn.java 
 * ���ϼ��� :  
 * �ۼ��� : 
 * ���� : 1.0.0 �������� : 2011- 11- 09
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm6030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class DeleteTsn extends SnisActivity {
	
	public DeleteTsn(){}

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
    	int nDeleteCount = 0;
    	int nSaveCount	 = 0;
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsTsnResult";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);	            
	            if( record.getAttribute("CHK").equals("1") ) {
		        	nTempCnt = deleteStoppayList(record);	        	
		        	nDeleteCount = nDeleteCount + nTempCnt;
		        	continue;		        
		        }
	        }	        
        }
        nSaveCount = nDeleteCount;
        Util.setSaveCount  (ctx, nSaveCount);
       
    }
    
   
    /**
     * <p> //�������� �� Ƽ�ϳ��� ������Ʈ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteStoppayList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("PUBL_DT")	);
        param.setValueParamter(i++, record.getAttribute("TSN_NO")	);                 
       
        int dmlcount = this.getDao("rbmdao").update("rsm6030_d01", param);
	
        return dmlcount;
    }
}
