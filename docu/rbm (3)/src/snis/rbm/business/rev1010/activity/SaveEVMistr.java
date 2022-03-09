/*================================================================================
 * �ý���			: �򰡹��ǥ  ����
 * �ҽ����� �̸�	: snis.rbm.business.rev1030.activity.SaveEVDistr.java
 * ���ϼ���		: �򰡹��ǥ ����
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-08-31
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEVMistr extends SnisActivity {
		public SaveEVMistr(){}
		
		
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
	        
	        sDsName = "dsEVCreate";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){
			        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
			        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);		        		
			        	}
			        	
			        	if( getUpdateYn(record).equals("Y")) {
			        		if ( (nTempCnt = updateEstmTms(record)) == 0 ) {
				        		nTempCnt = insertEstmTms(record);
				        		nSaveCount = nSaveCount + nTempCnt;
				        	}
			        	} else {
			        		try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "�μ�������ڸ� Ȯ���� �μ��� �����ϹǷ� �����Ͻ� �� �����ϴ�.");
			            		
			            		return;
			            	}
			        	}
			        	continue;
			        }
			        
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
			        	
			        	if( getDeleteValid(record).equals("Y") ) {
			        		try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsgCode(ctx, "SNIS_RBM_F021");
			            		
			            		return;
			            	}
			        	}
			        	
			        	nDeleteCount = nDeleteCount + deleteEstmTms(record);
			        	
			        	//÷�������� ������ ��, ÷������ ����
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
		        						
		        						record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        					record.setAttribute("ESTM_YEAR",ctx.get("ESTM_YEAR"));
			        					record.setAttribute("ESTM_NUM" ,ctx.get("ESTM_NUM"));
			        					
			        					updateEVMistrAttKey(record);
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

	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }

	    /**
	     * <p> �� �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertEstmTms(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));       // 1.�򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));        // 2.��ȸ��
	        param.setValueParamter(i++, record.getAttribute("ESTM_STR_DT"));    // 3.�򰡽�������
	        param.setValueParamter(i++, record.getAttribute("ESTM_END_DT"));    // 4.����������
	        param.setValueParamter(i++, record.getAttribute("PRM_STR_DT"));
    		param.setValueParamter(i++, record.getAttribute("PRM_END_DT"));
    		param.setValueParamter(i++, record.getAttribute("ESC_STR_DT"));
    		param.setValueParamter(i++, record.getAttribute("ESC_END_DT"));
    		param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
	        param.setValueParamter(i++, record.getAttribute("RMK"));    // 5.��� 
	        param.setValueParamter(i++, record.getAttribute("MNG_NO"));
    		param.setValueParamter(i++, record.getAttribute("RELEA_RATE"));
    		param.setValueParamter(i++, record.getAttribute("CRA_RATE"));
    		param.setValueParamter(i++, record.getAttribute("MRA_RATE")); 
    		param.setValueParamter(i++, record.getAttribute("MNG_DEPT")); 
    		param.setValueParamter(i++, record.getAttribute("RELEA_ESC_CNT"));
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 6.����� ID	

	        int dmlcount = this.getDao("rbmdao").update("rev1010_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEstmTms(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	       
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_STR_DT"));    // 1.�򰡽�������
	        param.setValueParamter(i++, record.getAttribute("ESTM_END_DT"));    // 2.����������
	        param.setValueParamter(i++, record.getAttribute("RMK"));            // 3. ���	
	        param.setValueParamter(i++, record.getAttribute("PRM_STR_DT"));
    		param.setValueParamter(i++, record.getAttribute("PRM_END_DT"));
    		param.setValueParamter(i++, record.getAttribute("ESC_STR_DT"));
			param.setValueParamter(i++, record.getAttribute("ESC_END_DT"));
			param.setValueParamter(i++, record.getAttribute("MNG_NO"));
			param.setValueParamter(i++, record.getAttribute("RELEA_RATE"));
			param.setValueParamter(i++, record.getAttribute("CRA_RATE"));
			param.setValueParamter(i++, record.getAttribute("MRA_RATE"));	
			param.setValueParamter(i++, record.getAttribute("MNG_DEPT"));	
			param.setValueParamter(i++, record.getAttribute("RELEA_ESC_CNT"));	
	        param.setValueParamter(i++, SESSION_USER_ID);                       // 4. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 5. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 6. ��ȸ��

	        int dmlcount = this.getDao("rbmdao").update("rev1010_u01", param);
	        return dmlcount;
	    }

	    protected int deleteEstmTms(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. �򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. ��ȸ��
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1010_d01", param);

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
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rbr4020_s03");        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	           
	        rtnKey = String.valueOf(pr[0].getAttribute("ATT_FILE_KEY"));
	   
	        return rtnKey;
	    }
	    
	    /**
	     * <p> �����ڷ� ���� ���� ��ȸ  </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected String getDeleteValid(PosRecord record) 
	    {
	    	PosParameter param = new PosParameter();
	    	int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 1. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 2. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��

	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1010_s15", param);   

	        PosRow pr[] = keyRecord.getAllRow();
	           
	        rtnKey = String.valueOf(pr[0].getAttribute("DEL_YN"));
	        return rtnKey;
	    }
	    
	    /**
	     * <p> ÷������ ���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEVMistrAttKey(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;          	        
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��

	        int dmlcount = this.getDao("rbmdao").update("rev1010_u02", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����� Ȯ�� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getUpdateYn(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR" ));
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM" ));
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1020_s05", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("UPDATE_YN"));

	        return rtnKey;
	    }
	    
	    /**
	     * <p> ����� Ȯ�� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getUpdateYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1020_s05", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("UPDATE_YN"));

	        return rtnKey;
	    }

	    /**
	     * <p> ����� Ȯ�� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getUpdateDeptYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1020_s051", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("UPDATE_YN"));

	        return rtnKey;
	    }
	    
	    /**
	     * <p> ���� Ȯ�� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getWorkUpdateYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1020_s06", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("UPDATE_YN"));

	        return rtnKey;
	    }
}
