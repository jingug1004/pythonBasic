/*================================================================================
 * �ý���			: ���  ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr4020.activity.SaveEvntHistMana.java
 * ���ϼ���		: ����̷� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-09
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr4020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
//import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEvntHistMana extends SnisActivity {
	
	public SaveEvntHistMana(){}

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
        
    	String sDsName   = "";
    	PosDataset ds;
        
    	int nUploadFileSize = 0;
    	sDsName = "dsUploadFile";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		    = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize = ds.getRecordCount();
        }

        String sAttFileKey = "";
        
        sDsName = "dsEvntHistMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {	
		        	
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0) {
		        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        	}
		        	
		        	nTempCnt = saveEvntHistMana(record);		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteEvntHistMana(record);	            	
	            	
	            	//����̷¿� ���� ÷�������� ������ ��, ÷������ ����
	            	SaveFile.deleteFile(record, this.getDao("rbmdao"));
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
		        		//��Ͻ� ÷������ Ű ���� 
		        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
		        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		} else {
		        			//÷�������� ������ ��ϵǴ°��
		        			if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){ 			
		        				if(tmpFileKey == null || tmpFileKey.equals("")){
	        						tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
	        					
		        					//����̷����� ÷������ key update
		        					record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        					record.setAttribute("BRNC_CD" ,ctx.get("BRNC_CD"));
		        					record.setAttribute("EVNT_SEQ",ctx.get("EVNT_SEQ"));
		        					record.setAttribute("SEQ"     ,ctx.get("SEQ"));

		        					updateEvntHistAttKey(record);
		        				}
		        				record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        			}
		        		}
		        		
		        		record.setAttribute("TBL_NM", "TBRA_EVNT_HIST_MANA");
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
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> ����̷� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEvntHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));		//��Ǽ���
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//����
        param.setValueParamter(i++, record.getAttribute("GEN_DT"));			//�߻�����	
        param.setValueParamter(i++, record.getAttribute("MNG"));			//�����

        param.setValueParamter(i++, record.getAttribute("TITLE"));			//����
		param.setValueParamter(i++, record.getAttribute("EVNT_CONT"));		//��ǳ���
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//÷������Ű
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
        
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(������)
      
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));		//��Ǽ���

        int dmlcount = this.getDao("rbmdao").update("rbr4020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ����̷� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEvntHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));	//��Ǽ���
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//����
        
        int dmlcount = this.getDao("rbmdao").update("rbr4020_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ����̷� ÷������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEvntHistAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));

        int dmlcount = this.getDao("rbmdao").update("rbr4020_u02", param);
        return dmlcount;
    }
}