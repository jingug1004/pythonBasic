/*================================================================================
 * �ý���			: �ϰ��޾��  ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs8010.activity.SaveEvntMana.java
 * ���ϼ���		: �ϰ��޾���� ��û�� ���� �⺻������ �����Ѵ�.
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2013-06-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs8010.activity;

import java.text.SimpleDateFormat; 
import java.util.Date;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveRstMaster extends SnisActivity {
	
	public SaveRstMaster(){}

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
	        ds   		    = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize = ds.getRecordCount();
        }

        String sAttFileKey = "";
        String attFileKey = "";
        String sRsvYear = (String)ctx.get("RSV_YEAR");
        String sRsvTms  = (String)ctx.get("RSV_TMS");
        
        // �ϰ��޾�� ��û���� ����
        sDsName = "dsAskInfo";        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	            	nDeleteCount = nDeleteCount + deleteAskInfo(record); 	// �ϰ��޾�� ��û���� ����
	            	
	            	//÷�������� ������ ��, ÷������ ����
	            	SaveFile.deleteFile(record, this.getDao("rbmdao"));
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {	
	            	
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){	// �űԵ�Ͻ� ÷�������� �ִ� ��� 
	            		attFileKey = record.getAttribute("ATT_FILE_KEY").toString();
	            		if (Util.isNull(attFileKey)) {	            		
			            	sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
			        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
	            		}
		        	}
	            	
			        nSaveCount += saveAskInfo(record);			        
		        }
	        }	        
        }   
        

        sDsName = "dsUploadFile";
        String tmpFileKey = "";

        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
		             record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	        		
	            	if ( (nTempCnt = SaveFile.updateFileMana(record,this.getDao("rbmdao"))) == 0 ) {
		        		 
		        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
		        			//��Ͻ� ÷������ Ű ������ ���
		        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		} else {
		        			//÷�������� ������ ��ϵǴ°��
		        			if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){ 			
		        				if(tmpFileKey == null || tmpFileKey.equals("")){
	        						tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
	        						
	        						//record.setAttribute("ATT_FILE_KEY", tmpFileKey);
	        						//record.setAttribute("RSV_YEAR",ctx.get("RSV_YEAR"));
		        					//record.setAttribute("RSV_TMS" ,ctx.get("RSV_TMS"));
		        					
		        					updateAskInfoAttKey(tmpFileKey, sRsvYear, sRsvTms);
		        				}
		        				record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        			}
		        		}
		        		
		        		record.setAttribute("TBL_NM", "TBRF_EV_MASTER");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
			        	nTempCnt   = SaveFile.insertFileMana(record, this.getDao("rbmdao"));
	            	}
	            	nSaveCount = nSaveCount + nTempCnt;
			        continue;
            	}
  
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record, this.getDao("rbmdao"));
				}
	         } //for
        } //if
        
        
        // �ϰ��޾�� ��û���� ����
        sDsName = "dsRstMaster";        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	            	nDeleteCount = nDeleteCount + deleteRstMaster(record); 	// �ϰ��޾�� ��û���� ����
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {		        	
			        nSaveCount += saveRstMaster(record);			        
		        }
	        }	        
        }        

        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �ϰ��޾�ҽ�û���� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveAskInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //�ϰ��޾�� ��û�⵵
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	   	//�ϰ��޾�� ��ûȸ��
        param.setValueParamter(i++, record.getAttribute("USE_STR_DT"));		//�ϰ��޾�� �̿������
        param.setValueParamter(i++, record.getAttribute("USE_END_DT"));		//�ϰ��޾�� �̿�������
        param.setValueParamter(i++, record.getAttribute("ASK_STR_DT"));		//�ϰ��޾�� ��û������
        param.setValueParamter(i++, record.getAttribute("ASK_END_DT"));		//�ϰ��޾�� ��û������
        param.setValueParamter(i++, record.getAttribute("RMK"));			//�ϰ��޾�� ��û���� ���
        param.setValueParamter(i++, record.getAttribute("DAY_NUM"));		//�ϰ��޾�� �����ϼ�
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs8010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �ϰ��޾�ҽ�û���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAskInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //�ϰ��޾�� ��û�⵵
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	    //�ϰ��޾�� ��ûȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rbs8010_d01", param);

        return dmlcount;
    }

    /**
     * <p> �ϰ��޾������ �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRstMaster(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //��û�⵵
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	   	//��ûȸ��
        param.setValueParamter(i++, record.getAttribute("RST_ID"));		    //�޾�� ���̵�
        param.setValueParamter(i++, record.getAttribute("RST_SEQ"));		//�޾�� ����
        param.setValueParamter(i++, record.getAttribute("RST_NAME"));		//�޾�Ҹ�
        param.setValueParamter(i++, record.getAttribute("RSV_MAX_NUM"));	//���Ǽ�
        param.setValueParamter(i++, record.getAttribute("USE_STR_DT"));		//�̿������
        param.setValueParamter(i++, record.getAttribute("USE_END_DT"));		//�̿�������
        param.setValueParamter(i++, record.getAttribute("DAY_NUM"));		//�����ϼ�
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs8010_m02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �ϰ��޾������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRstMaster(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //��û�⵵
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	    //��ûȸ��
        param.setValueParamter(i++, record.getAttribute("RST_ID"));	    	//�޾�� ���̵�
        param.setValueParamter(i++, record.getAttribute("RST_SEQ"));	    //�޾�� �Ϸù�ȣ
        
        int dmlcount = this.getDao("rbmdao").update("rbs8010_d02", param);

        return dmlcount;
    }

    /**
     * <p> ÷������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAskInfoAttKey(String attFileKey, String rsvYear, String rsvTms)
    { 
    	System.out.println("attFileKey="+attFileKey);
    	System.out.println("rsvYear="+rsvYear);
    	System.out.println("rsvTms="+rsvTms);

    	PosParameter param = new PosParameter();
        int i = 0;          	        
        param.setValueParamter(i++, attFileKey);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, rsvYear);     // ��û�⵵
        param.setValueParamter(i++, rsvTms);      // ��û����

        int dmlcount = this.getDao("rbmdao").update("rbs8010_u01", param);
        return dmlcount;
    }
    
}