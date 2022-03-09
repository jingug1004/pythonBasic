package snis.rbm.business.rsm6060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveReceiptReqProcess extends SnisActivity {

	
	public SaveReceiptReqProcess(){}

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
        int nTempCnt     = 0;
        int nSize		 = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
    	PosDataset ds2;
        

        sDsName = "dsTsnRcptResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	                	
	  		    nTempCnt  = updateProcess(record);	//������û	    
	  		    
	            nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }
        Util.setSaveCount  (ctx, nSaveCount);     
       
    }
    /**
     * <p> �нǽŰ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateProcess(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
    
        param.setValueParamter(i++, record.getAttribute("TIC_STAS")		);		// Ƽ�ϻ���
       	param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_DEPT") );	// ������û�ںμ� 
        param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_ID") );		// ������û��      
        param.setValueParamter(i++, SESSION_USER_ID						);		// �����ID(�ۼ���)
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")		);		// ��������
		param.setValueParamter(i++, record.getAttribute("TSN_NO")		);		// TSN_NO
		
        int dmlcount = this.getDao("rbmdao").update("rsm6060_u01", param);
        
        return dmlcount;
    }    
    
    
    
   
}
