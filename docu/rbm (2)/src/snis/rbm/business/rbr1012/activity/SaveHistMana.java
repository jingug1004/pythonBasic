/*================================================================================
 * �ý���			: ����  ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr1012.activity.SaveHistMana.java
 * ���ϼ���		: ���� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-17
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr1012.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHistMana extends SnisActivity {
	
	public SaveHistMana(){}

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
    	int nSaveCount     = 0; 
    	int nDeleteCount   = 0; 
    	String sDsName     = "";
    	//String sAttFileKey = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsHistMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	/*
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
		        		sAttFileKey = getFileManaMaxKey();
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);		        		
		        	}*/		        	
		        	
		        	nTempCnt   = saveHistMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	//÷������ �߰���
	            	/*
	            	int deleteValid = getAttFileKey(record);
	            	
	            	if( deleteValid > 0 ) {
	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		//Util.setSvcMsgCode(ctx, "SNIS_RBM_B001");
		            		Util.setSvcMsg(ctx, "üũ�Ͻ� �׸� �� [ " + (i+1) + " ]��° ���� [ ÷������ ]�� �����ϹǷ� �����Ͻ� �� �����ϴ�.");
		            		
		            		return;
		            	} 

	            	}*/
		        	nDeleteCount = nDeleteCount + deleteHistMana(record);	            	
	            }		        
	        }	        
        }
        
        // ÷������ �߰���
        /*
        sDsName = "dsUploadFile";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
	        			        		
	        		//��Ͻ� ÷������ Ű ���� 
	        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
	        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
	        		}
	        		
		        	nTempCnt = insertFileMana(record);
		        	nSaveCount = nSaveCount + nTempCnt;
			        continue;
	            }
  
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
				{
					nDeleteCount = nDeleteCount + deleteFileMana(record);	            	
				}
	         } //for
        } //if*/
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> ���� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("YM"));				//���
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//����
        param.setValueParamter(i++, record.getAttribute("CNTNT"));			//����	
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//÷������Ű
		
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�����)
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(������)

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("YM"));				//���

        int dmlcount = this.getDao("rbmdao").update("rbr1012_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("YM"));				//���
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//����
        
        int dmlcount = this.getDao("rbmdao").update("rbr1012_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ÷������  KEY ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getFileManaMaxKey() 
    {
        String rtnKey = "";
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbr1012_s02");        
        	
        PosRow pr[] = keyRecord.getAllRow();
           
        rtnKey = String.valueOf(pr[0].getAttribute("ATT_FILE_KEY"));
   
        return rtnKey;
    }
    
    /**
     * <p> ÷������  �Է�(���X) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertFileMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, record.getAttribute("TBL_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));

        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);  
                
        int dmlcount = this.getDao("rbmdao").update("rbr4020_i01", param);

        return dmlcount;
    }
    
    /**
     * <p> ÷������  ����(���X) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteFileMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));

        
        int dmlcount = this.getDao("rbmdao").update("rbr4020_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> ������ ���� ÷������ ��ȸ(���X)  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��ǿ� �ɷ��ִ� ����̷� ����
     * @throws  none
     */
    protected int getAttFileKey(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY" ));	//÷������Ű
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbr4020_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}