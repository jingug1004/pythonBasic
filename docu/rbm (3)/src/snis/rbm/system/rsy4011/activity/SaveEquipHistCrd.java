/*================================================================================
 * �ý���			: ����̷�   ����
 * �ҽ����� �̸�	: snis.rbm.business.rsy4011.activity.SaveEquipHistCrd.java
 * ���ϼ���		: ����̷�  ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-08-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

package snis.rbm.system.rsy4011.activity;

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

public class SaveEquipHistCrd extends SnisActivity {

	public SaveEquipHistCrd(){
		
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
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize 	 = ds.getRecordCount();
        }
	        
	        
        //÷������ ó�� ��Ͻ� ÷������KEY ���� 
        String sMAttFileKey ="";
        String sDAttFileKey ="";
        
        sDsName = "dsEquipHistCrd";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {		        	
		        	
		        	
		        	
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){
		        		sMAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sMAttFileKey);		
		        	}

		        	
		        	if ( (nTempCnt = updateEquipHistCrd(record)) == 0 ) {
		        		nTempCnt = insertEquipHistCrd(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteEquipHistCrd(record);	            	
	            }		        
	        }	        
        }

        
        sDsName = "dsUploadFile";
        
        String tmpFileKey = "";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = SaveFile.updateFileMana(record,this.getDao("rbmdao"))) == 0 ) {
		        		
		        		//ó�� ÷������ ��Ͻ�  ÷������ Ű ����
		        		//�űԵ�Ͻ� ÷������ ���� ��� �� ��� 
		        		if(sMAttFileKey != null  && !sMAttFileKey.equals("")){
		        			
		        			record.setAttribute("ATT_FILE_KEY", sMAttFileKey);
		        		}else{
		        		
	
			        		//÷�������� ������ ��ϵǴ°��
			        		if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){
			        			
			        			
			        			if(tmpFileKey == null || tmpFileKey.equals("")){
			        					tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
			        					
			        					//����̷����� ÷������ key update
			        					record.setAttribute("ATT_FILE_KEY",tmpFileKey);
			        					record.setAttribute("EQUIP_DIST_NO",ctx.get("EQUIP_DIST_NO"));
			        					record.setAttribute("EQUIP_HIST_SEQ",ctx.get("EQUIP_HIST_SEQ"));
			        					record.setAttribute("CHECK_EQUIP_GBN",ctx.get("CHECK_EQUIP_GBN"));
			        			        
			        			        
			        					updateEquipHistCrdAttKey(record);

			        			}
			        	
			        			record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        		}		       
			        			
			        	
		        		}
		        		
		        		
		        		record.setAttribute("TBL_NM", "TBRK_EQUIP_HIST_CRD");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
		        		nTempCnt = SaveFile.insertFileMana(record,this.getDao("rbmdao"));
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record,this.getDao("rbmdao"));	            	
	            }	
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> ����̷�ī��  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertEquipHistCrd(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO"));	//��������ȣ
        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO"));	//��������ȣ
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN"));//����̷¼���
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN"));//������񱸺�
        param.setValueParamter(i++, record.getAttribute("CHECK_DT"));		//��������

        param.setValueParamter(i++, record.getAttribute("CHECK_STAT_CD"));	//���˻����ڵ�
        param.setValueParamter(i++, record.getAttribute("CHECK_TY_CD"));	//���������ڵ�
        param.setValueParamter(i++, record.getAttribute("CHECK_CNTNT"));	//���˳���
        param.setValueParamter(i++, record.getAttribute("WORKER"));			//�۾���
        param.setValueParamter(i++, record.getAttribute("ETC_CHECK_CNTNT"));//��Ÿ���˳���

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//÷������Ű
        param.setValueParamter(i++, SESSION_USER_ID);        
  
        
        int dmlcount = this.getDao("rbmdao").update("rsy4011_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> ����̷�ī�� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEquipHistCrd(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("CHECK_DT"));		//��������
        param.setValueParamter(i++, record.getAttribute("CHECK_STAT_CD"));	//���˻����ڵ�
        param.setValueParamter(i++, record.getAttribute("CHECK_TY_CD"));	//���������ڵ�
        param.setValueParamter(i++, record.getAttribute("CHECK_CNTNT"));	//���˳���
        param.setValueParamter(i++, record.getAttribute("WORKER"));			//�۾���

        param.setValueParamter(i++, record.getAttribute("ETC_CHECK_CNTNT"));//��Ÿ���˳���
        param.setValueParamter(i++,SESSION_USER_ID);						//������

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_DIST_NO" ));	//��������ȣ
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_HIST_SEQ" ));	//����̷¼���
        param.setWhereClauseParameter(i++, record.getAttribute("CHECK_EQUIP_GBN" ));//������񱸺�
        


        int dmlcount = this.getDao("rbmdao").update("rsy4011_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ����̷�ī��  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEquipHistCrd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO"      ) );	//��������ȣ
        param.setValueParamter(i++, record.getAttribute("EQUIP_HIST_SEQ"      ) );	//����̷¼���
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN"      ) );	//������񱸺�
        
        int dmlcount = this.getDao("rbmdao").update("rsy4011_d01", param);

        return dmlcount;
    }
    
    
    
    /**
     * <p> ����̷�ī�� ÷������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEquipHistCrdAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//÷������Ű
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO" ));		//��������ȣ
        param.setValueParamter(i++, record.getAttribute("EQUIP_HIST_SEQ" ));	//����̷¼���
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN" ));	//������񱸺�
        


        int dmlcount = this.getDao("rbmdao").update("rsy4011_u03", param);
        return dmlcount;
    }

    

  
}
