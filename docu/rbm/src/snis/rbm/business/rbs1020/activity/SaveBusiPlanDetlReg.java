/*================================================================================
 * �ý���			: ���ǰ������
 * �ҽ����� �̸�	: snis.rbm.business.rbs1020.activity.SaveBusiPlanDetlReg.java
 * ���ϼ���		: �����ȹ�󼼵��
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBusiPlanDetlReg extends SnisActivity {
		public SaveBusiPlanDetlReg(){}
		
		
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
	        PosDataset dsFile;
	        int nFileSize        = 0;
	        
	        String sFileDsName = "dsUploadFile";
	        if ( ctx.get(sFileDsName) != null ) {
	        	dsFile   		 = (PosDataset) ctx.get(sFileDsName);
	        	
		        nUploadFileSize 	 = dsFile.getRecordCount();
	        }
		        
		        
	        //÷������ ó�� ��Ͻ� ÷������KEY ���� 
	        String sAttFileKey ="";
	        
	        sDsName = "dsList";
	        
	        
	        
	        if ( ctx.get(sDsName) != null ) {
	        	
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        for ( int i = 0; i < nSize; i++ ) {
		        	
		            PosRecord record = ds.getRecord(i);

		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		            	nTempCnt = deleteBusiPlanDetl(record);
		            } else {	
				      
		            	// ������ ATT_FILE_KEY ��  ������, ���ο� ATT_FILE_KEY �� ���Ѵ�. 
		                Double dTempFileKey = (Double)record.getAttribute("ATT_FILE_KEY"); 
		                
		                System.out.println("dTempFileKey=" + dTempFileKey);
		                
		                if(dTempFileKey == null)
		                {
		                	sAttFileKey = getFileManaMaxKey();
			        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		                }    	
			        		System.out.println("sAttFileKey=" + sAttFileKey);
		                
			            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			            	nTempCnt = insertBusiPlanDetl(record);
			            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			            	nTempCnt = updateBusiPlanDetl(record);
			            }	
				        nSaveCount = nSaveCount + nTempCnt;
			        	
			        }    
		        }	        
	        }

	        
	        
	        sFileDsName = "dsUploadFile";
	        if ( ctx.get(sFileDsName) != null ) {
	        	dsFile   		 = (PosDataset) ctx.get(sFileDsName);
	        	nFileSize 		 = dsFile.getRecordCount();

		         for ( int i = 0; i < nFileSize; i++ ) {
		            PosRecord record = dsFile.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateFileMana(record)) == 0 ) {
			        		
			        		
			        		//�űԵ�Ͻ� ÷������ Ű ���� 
			        		if(sAttFileKey != null && !sAttFileKey.equals("")){
			        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
			        		}
			        		
			        		nTempCnt = insertFileMana(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteFileMana(record);	            	
		            }	
		        }	         
	        }
	        
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    
	    /**
	     * <p> �����ȹ�󼼵�� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateBusiPlanDetl(PosRecord record)
	    {		
					
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));           // 1. �μ��ڵ� 
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));            // 2. �����ID
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));        // 3. ȸ�豸���ڵ�	
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));           // 4. �����
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_PRPS"));   // 5. ����������
	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_SMRY"));   // 6. ������밳��
	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_STR"));       // 7. ������� ����
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_END"));       // 8. ������� ����
	        
	        param.setValueParamter(i++, record.getAttribute("QUAR_1"));            // 9.  1�б�
	        param.setValueParamter(i++, record.getAttribute("QUAR_2"));            // 10. 2�б�
	        param.setValueParamter(i++, record.getAttribute("QUAR_3"));            // 11. 3�б�
	        param.setValueParamter(i++, record.getAttribute("QUAR_4"));            // 12. 4�б�
	        
	        param.setValueParamter(i++, record.getAttribute("TRANS_AMT"));         // 13. �̿��ݾ�
	        param.setValueParamter(i++, record.getAttribute("ASS_PRO_AMT"));       // 14. �ڻ�����
	        param.setValueParamter(i++, record.getAttribute("FAC_MANA_AMT"));      // 15. �ü����� ������
	        
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));      // 16. ÷������Ű
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 17. �����ID  
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 18. �ð����ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1020_u01", param);
	        return dmlcount;
	    }


	    /**
	     * <p> �����ȹ�󼼰�ȹ ��� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertBusiPlanDetl(PosRecord record)
	    {		
					
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));     // 1.�⵵
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_TMS"));   // 2.��������
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));    // 3.ȸ�豸���ڵ�
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));       // 4.�����
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_AMT"));   // 5.���αݾ�
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));       // 6.�μ��ڵ� 
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));        // 7.�����ID	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_PRPS"));   // 8. ����������	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_SMRY"));   // 9. ������밳��	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_STR"));       // 10. ������� ����
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_END"));       // 11. ������� ����	        
	        param.setValueParamter(i++, record.getAttribute("QUAR_1"));            // 12.  1�б�
	        param.setValueParamter(i++, record.getAttribute("QUAR_2"));            // 13. 2�б�
	        param.setValueParamter(i++, record.getAttribute("QUAR_3"));            // 14. 3�б�
	        param.setValueParamter(i++, record.getAttribute("QUAR_4"));            // 15. 4�б�	        
	        param.setValueParamter(i++, record.getAttribute("TRANS_AMT"));         // 16. �̿��ݾ�
	        
	        param.setValueParamter(i++, record.getAttribute("ASS_PRO_AMT"));       // 17. �ڻ�����
	        param.setValueParamter(i++, record.getAttribute("FAC_MANA_AMT"));      // 18. �ü����� ������
	        
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));      // 19. ÷������Ű	        
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 20. �����ID
	       
	        int dmlcount = this.getDao("rbmdao").update("rbs1020_i01", param);
	        return dmlcount;
	    }


	    /**
	     * <p> �����ȹ�󼼰�ȹ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteBusiPlanDetl(PosRecord record)
	    {		
					
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 18. �ð����ڵ�	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1020_d01", param);
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
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs1020_s_filekey");        
	        	
	        PosRow pr[] = keyRecord.getAllRow();	     
	       
	        rtnKey = String.valueOf(pr[0].getAttribute("ATT_FILE_KEY"));
	   
	        return rtnKey;
	    }
	    
	    
	    /**
	     * <p> ÷������  �Է� </p>
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
	        param.setValueParamter(i++, "TBRE_BUSI_PLAN");
	        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
	        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));

	        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));
	        param.setValueParamter(i++, SESSION_USER_ID);  
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1020_i_file", param);
	        
	        
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> ÷������ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateFileMana(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;          
	        
	        param.setValueParamter(i++, "TBRE_BUSI_PLAN");
	        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
	        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));
	        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));
	        
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY" ));
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));
	        


	        int dmlcount = this.getDao("rbmdao").update("rbs1020_u_file", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> ÷������  ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteFileMana(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"      ) );
	        param.setValueParamter(i++, record.getAttribute("SEQ"      ) );

	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1020_d_file", param);

	        return dmlcount;
	    }
	    
}
