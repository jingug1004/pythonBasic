/*
 * ================================================================================
 * �ý��� : ü���ο��������� 
 * �ҽ����� �̸� : snis.rbm.system.rem3011.activity.SaveStayEndMana.java 
 * ���ϼ��� : ü���ο������������� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011-10- 01
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rem3011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveStayEndMana extends SnisActivity {
	
	public SaveStayEndMana(){
		
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        
        int nUploadFileSize = 0;
        sDsName = "dsUploadFile";
        String sMAttFileKey = "";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize 	 = ds.getRecordCount();
        }
        

        sDsName = "dsStayEndMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            record.setAttribute("STAY_END_STAT_CD", "001");
	            
		        if ( record.getType() == PosRecord.INSERT ||  record.getType() == PosRecord.UPDATE) {

		        	if(nUploadFileSize > 0){
		        		sMAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sMAttFileKey);		

		        	}
		        	
		        	nTempCnt = insertStayEndMana(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }    
	        }	        
        }

        
       sDsName = "dsUploadFile";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	record.setAttribute("ATT_FILE_KEY", sMAttFileKey);

	        		
	        		record.setAttribute("TBL_NM", "TBRC_STAY_END_MANA");
	        		record.setAttribute("INST_ID", SESSION_USER_ID);
	        		
	        		SaveFile.insertFileMana(record,this.getDao("rbmdao"));
	         }	         
          }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    
    /**
     * <p> ü���ο�������� ��û �Է� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertStayEndMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));				//��������
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));				//�����ڵ�
        
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));					//��������
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));				//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));				//������ڵ�   
        
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));				//������ڵ�
        param.setValueParamter(i++, record.getAttribute("REQ_RSN"));				//��û����   
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"      ));		//÷������Ű
        param.setValueParamter(i++, record.getAttribute("RMK"      ));				//���        
        param.setValueParamter(i++, record.getAttribute("STAY_END_STAT_CD"      ));				//���
        
        param.setValueParamter(i++, SESSION_USER_ID);								//�ۼ���
   

        
        int dmlcount = this.getDao("rbmdao").update("rem3011_i01", param);
        return dmlcount;
    }

	
	
	
	
}
