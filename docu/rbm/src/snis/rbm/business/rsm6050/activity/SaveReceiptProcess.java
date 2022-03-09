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
package snis.rbm.business.rsm6050.activity;

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


public class SaveReceiptProcess extends SnisActivity {
	
	public SaveReceiptProcess(){}

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
        int nSize2		 = 0;
        
    	String sDsName   = "";
    	String sDsName2  = "dsUploadFile";
    	PosDataset ds;
    	PosDataset ds2;
        
        //÷������ ó�� ��Ͻ� ÷������KEY ���� 
        String sNewAttFileKey ="";
        String sAttFileKey = "";
    	       
        sDsName = "dsTsnRcptResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	                	
	  		    nTempCnt  = updateProcess(record);	//����,�������,������û,������û���
	  		    
	  		    
	  		    //������ ��� �������� ��� 
	  		    if( record.getAttribute("TIC_STAS").equals("002")){
	  		    	sAttFileKey = (String)record.getAttribute("ATT_FILE_KEY");	
	  		    	if(sAttFileKey == null) sAttFileKey="";
	  		    	
	  		    	
	  		    	//÷������ Ű ����
		        	if(sAttFileKey.equals("")){
		        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		
    		        	
			        	//�нǽŰ� ÷������ key update
			        	updateReqAttKey(record);		        	
		        		
		        	}
		        	
		        	//÷������ ���		            
		            if ( ctx.get(sDsName2) != null ) {
		    	        ds2   		 = (PosDataset) ctx.get(sDsName2);
		    	        nSize2 		 = ds2.getRecordCount();

		    	        
		    	        
		    	         for ( int k = 0; k < nSize2; k++ ) {
		    	            PosRecord record2 = ds2.getRecord(k);		    	            
		    	            

		    		        if ( record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		    		          || record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		    		        	
		    		        	record2.setAttribute("ATT_FILE_KEY", sAttFileKey);
	    		        		record2.setAttribute("TBL_NM", "TBRD_STOPPAY_CNCL_CNTNT");
	    		        		record2.setAttribute("INST_ID", SESSION_USER_ID);
	    		        	
	    		        		
		    		        	if ( SaveFile.updateFileMana(record2,this.getDao("rbmdao")) == 0 ) {
		    		        
		    		        		SaveFile.insertFileMana(record2,this.getDao("rbmdao"));
		    		        	}
		    			
		    		        	continue;
		    		        }
		    		        
		    	            if (record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		    	            {
		    	            
		    	            	SaveFile.deleteFileMana(record2,this.getDao("rbmdao"));	            	
		    	            }
		    	            
		    	        }
		        	
		            }
		            

		        	
	  		    	
	  		    }
	  		    
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
     			
        param.setValueParamter(i++, record.getAttribute("ACDNT_TY_CD")	);		// �������        
        param.setValueParamter(i++, record.getAttribute("REFUND_TY_CD")	);		// ȯ������
        if(  record.getAttribute("TIC_STAS").equals("002")	){
        	 param.setValueParamter(i++, Util.getCurrDate()		);				// ��������
        }else{
        	param.setValueParamter(i++, record.getAttribute("RCPT_DT")		);	// ��������
        }        
        param.setValueParamter(i++, record.getAttribute("RCPT_DEPT_NM")	);		// �����ںμ�
        param.setValueParamter(i++, record.getAttribute("RCPT_ID")		);		// ������ID        
        param.setValueParamter(i++, record.getAttribute("REFUND_AMT")	);		// ȯ�ݱݾ�
        param.setValueParamter(i++, record.getAttribute("RMK")			);		// �������(���) 
        param.setValueParamter(i++, record.getAttribute("CUST_NM")		);		// ����      
        param.setValueParamter(i++, record.getAttribute("CUST_PHONE_NO"));		// ��ȭ��ȣ
        param.setValueParamter(i++, record.getAttribute("CUST_ADDR")	);		// �ּ�
        param.setValueParamter(i++, record.getAttribute("BIRTH_DAY")	);		// ���������        
        param.setValueParamter(i++, record.getAttribute("TIC_STAS")		);		// Ƽ�ϻ���
       	param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_DEPT") );	// ������û�ںμ� 
        param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_ID") );		// ������û��      
        param.setValueParamter(i++, SESSION_USER_ID						);		// �����ID(�ۼ���)
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")		);		// ��������
		param.setValueParamter(i++, record.getAttribute("TSN_NO")		);		// TSN_NO
		
        int dmlcount = this.getDao("rbmdao").update("rsm6050_u01", param);
        
        return dmlcount;
    }    
    
    
    
    /**
     * <p> ������������ ÷������ key���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateReqAttKey(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
    	
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		// ������� 
        
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO"));			// ��������
		param.setValueParamter(i++, record.getAttribute("TSN_NO"));				// TSN_NO
        
		int dmlcount = this.getDao("rbmdao").update("rsm6050_u03", param);
        
        return dmlcount;
    }
}
