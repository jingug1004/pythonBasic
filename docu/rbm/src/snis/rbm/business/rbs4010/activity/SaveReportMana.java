/*
 * ================================================================================
 * �ý��� : ���� ���� 
 * �ҽ����� �̸� : snis.rbm.business.rbs4010.activity.SaveReportMana.java 
 * ���ϼ��� : ���� ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011 - 10 -06
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbs4010.activity;

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

public class SaveReportMana extends SnisActivity {

	public SaveReportMana(){
		
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
        
        
        sDsName = "dsReportMana";
        String sMaxSeq = ""; 
        	
        
        /*   ��������    */
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

		        	
		        	if ( (nTempCnt = updateReportMana(record)) == 0 ) {
		        		
		        		sMaxSeq = getReportManaMaxKey(record);
		        		
		        		record.setAttribute("SEQ", sMaxSeq);
		        		
		        		nTempCnt = insertReportMana(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteReportMana(record);	            	
	            }		        
	        }	        
        }

        /*   ÷������  ����    */
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
			        					
			        					
			        					record.setAttribute("ATT_FILE_KEY",tmpFileKey);
			        					
			        					record.setAttribute("DEPT_CD",ctx.get("DEPT_CD"));
			        					record.setAttribute("STND_YEAR",ctx.get("STND_YEAR"));
			        					
			        					
			        					if(!sMaxSeq.equals("")){
			        						record.setAttribute("SEQ",sMaxSeq);
			        					}else{
			        						record.setAttribute("SEQ",ctx.get("SEQ"));
			        					}
			        					
		
			        					updateReportManaAttKey(record);

			        			}
			        	
			        			record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        		}		       
			        			
			        	
		        		}

		        		record.setAttribute("TBL_NM", "TBRE_REPORT_MANA");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
		        		nTempCnt = SaveFile.insertFileMana(record,this.getDao("rbmdao"));
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	record.setAttribute("FILE_DEL_YN","N");
	            	
		        	nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record,this.getDao("rbmdao"));	            	
	            }
	            
	        }
	         
	         
         /*   ��������� ����    */
         sDsName = "dsReportMng";
         
         if ( ctx.get(sDsName) != null ) {
 	        ds   		 = (PosDataset) ctx.get(sDsName);
 	        nSize 		 = ds.getRecordCount();

 	        for ( int i = 0; i < nSize; i++ ) {
 	            PosRecord record = ds.getRecord(i);

 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 
 		        	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
 		        	
	        		if(!sMaxSeq.equals("")){
	        			record.setAttribute("SEQ", sMaxSeq);
	        		}
	        		
 		        	nTempCnt = insertReportMng(record);
 		        	
 			        nSaveCount = nSaveCount + nTempCnt;
 		        	continue;
 		        }
 		        
 	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
 	            {
 		        	nDeleteCount = nDeleteCount + deleteReportMng(record);	            	
 	            }		        
 	        }	        
         }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * <p> ����  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertReportMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//���س⵵
        
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//SEQ

        param.setValueParamter(i++, record.getAttribute("REQ_DT"));			//��û����
        param.setValueParamter(i++, record.getAttribute("SUBM_LIMIT_DT"));	//�����������

        param.setValueParamter(i++, record.getAttribute("REPORT_NM"));		//������
        param.setValueParamter(i++, record.getAttribute("INST_GUIDE"));		//�ۼ���ħ
        param.setValueParamter(i++, record.getAttribute("SAMP_YN"));		//��Ŀ���
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//÷������Ű
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���

        param.setValueParamter(i++, SESSION_USER_ID);        				//�ۼ���
  
        
        int dmlcount = this.getDao("rbmdao").update("rbs4010_i01", param);
        
        return dmlcount;
    }

    /**
     * <p> ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateReportMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));				//��û����
        param.setValueParamter(i++, record.getAttribute("SUBM_LIMIT_DT"));		//�����������
        param.setValueParamter(i++, record.getAttribute("REPORT_NM"));			//������Ī
        param.setValueParamter(i++, record.getAttribute("INST_GUIDE"));			//�ۼ���ħ
        param.setValueParamter(i++, record.getAttribute("SAMP_YN"));			//��Ŀ���

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//÷������Ű
        param.setValueParamter(i++, record.getAttribute("RMK"));				//���
        param.setValueParamter(i++,SESSION_USER_ID);							//������

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));	//�⵵
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));			//����
        

        int dmlcount = this.getDao("rbmdao").update("rbs4010_u01", param);
        return dmlcount;
    }

    /**
     * <p> ����  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteReportMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DEPT_CD") );		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR") );		//�⵵
        param.setValueParamter(i++, record.getAttribute("SEQ") );			//����
        
        int dmlcount = this.getDao("rbmdao").update("rbs4010_d01", param);

        return dmlcount;
    }

    /**
     * <p> ���� ÷������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateReportManaAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//÷������Ű
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("DEPT_CD" ));			//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));			//�⵵
        param.setValueParamter(i++, record.getAttribute("SEQ" ));				//����
        
        int dmlcount = this.getDao("rbmdao").update("rbs4010_u03", param);
        return dmlcount;
    }

    /**
     * <p> ���������  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertReportMng(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//�⵵
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//����
        param.setValueParamter(i++, record.getAttribute("MNG_ID"));		//�����ID
        param.setValueParamter(i++, record.getAttribute("MNG_DEPT_CD"));//����ںμ��ڵ�

        int dmlcount = this.getDao("rbmdao").update("rbs4010_m01", param);
        
        return dmlcount;
    }

    /**
     * <p> ���������  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteReportMng(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//�⵵
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//����
        param.setValueParamter(i++, record.getAttribute("MNG_ID"));		//�����ID
        
                		
        int dmlcount = this.getDao("rbmdao").update("rbs4010_d02", param);

        return dmlcount;
    }  
    
    
    
    /**
     * <p> ���� MAX KEY ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getReportManaMaxKey(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));	//�⵵

                		
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs4010_s03",param);        
       
        
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("SEQ"));	//÷������Ű
   
        return rtnKey;
    }
	
}
