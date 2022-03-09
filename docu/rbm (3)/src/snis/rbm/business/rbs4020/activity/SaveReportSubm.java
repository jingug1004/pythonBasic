/*
 * ================================================================================
 * �ý��� : ���� ���� ���� 
 * �ҽ����� �̸� : snis.rbm.business.rbs4020.activity.SaveReportSubm.java 
 * ���ϼ��� : ���� ����  ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011-10-07 
 * ������������ : 
 * ���������� : ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbs4020.activity;

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

public class SaveReportSubm extends SnisActivity {

	public SaveReportSubm(){
		
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
        sDsName = "dsSubmUploadFile";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize 	 = ds.getRecordCount();
        }
   
	        
        //÷������ ó�� ��Ͻ� ÷������KEY ���� 
        String sMAttFileKey ="";
        
        
        sDsName = "dsReportSubm";
        String sMaxSeq = ""; 
        	
        
        /*   ���������  �������� ����    */
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	
		        	nTempCnt = updateReportSubm(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }	        
	        }	        
        }

        /*   ÷������  ����    */
        sDsName = "dsSubmUploadFile";
        
        String tmpFileKey = "";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = SaveFile.updateFileMana(record,this.getDao("rbmdao"))) == 0 ) {
			        		//÷�������� ������ ��ϵǴ°��
			        		if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){
			        			
			        			
			        			if(tmpFileKey == null || tmpFileKey.equals("")){
			        					tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
			        					
			        					//����� ü��  ÷������ key update
			        					record.setAttribute("ATT_FILE_KEY",tmpFileKey);
			        					
			        					record.setAttribute("DEPT_CD",ctx.get("DEPT_CD"));
			        					record.setAttribute("STND_YEAR",ctx.get("STND_YEAR"));
			        					record.setAttribute("SEQ",ctx.get("SEQ"));
			        					record.setAttribute("MNG_ID",ctx.get("MNG_ID"));
			        					
			        					
			        					if(!sMaxSeq.equals("")){
			        						record.setAttribute("SEQ",sMaxSeq);
			        					}else{
			        						record.setAttribute("SEQ",ctx.get("SEQ"));
			        					}

			        					updateReportSubmAttKey(record);

			        			}
			        	
			        			record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        		}		       
			        			
			        	
		        		}

		        		record.setAttribute("TBL_NM", "TBRE_REPORT_MNG");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
	        		
		        		nTempCnt = SaveFile.insertFileMana(record,this.getDao("rbmdao"));
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
			        
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            
			        	nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record,this.getDao("rbmdao"));	            	
		            }
		            
		        	continue;

	        }

        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    
    /**
     * <p> ����� ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateReportSubm(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//÷������Ű
        param.setValueParamter(i++, record.getAttribute("REPORT_CONT"));		//������
        param.setValueParamter(i++,SESSION_USER_ID);							//������

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));	//�⵵
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));			//����
        param.setWhereClauseParameter(i++, record.getAttribute("MNG_ID"));		//�����ID
        

        int dmlcount = this.getDao("rbmdao").update("rbs4020_u01", param);
        return dmlcount;
    }

   
    /**
     * <p> ���������  ÷������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateReportSubmAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//÷������Ű
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("DEPT_CD" ));			//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));			//�⵵
        param.setValueParamter(i++, record.getAttribute("SEQ" ));				//����
        param.setValueParamter(i++, record.getAttribute("MNG_ID" ));				//����

        
        int dmlcount = this.getDao("rbmdao").update("rbs4020_u02", param);
        return dmlcount;
    }

}
