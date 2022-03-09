/*================================================================================
 * �ý���			: �򰡹��ǥ   ��
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * ���ϼ���		: �򰡹��ǥ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev2020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev2010.activity.*;

public class SaveMnrDt extends SnisActivity {
		public SaveMnrDt(){}

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
	    	String sDsNameMnr   = "";
	    	String sDsNameMnrDt   = "";
	    	
	    	PosDataset ds;
	    	PosDataset ds2;
	    	int nSize        = 0;
	    	int nSizeDt        = 0;
	        int nTempCnt     = 0;
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        SavePrmRslt SavePrmRslt = new SavePrmRslt();
	        
	        if( !SavePrmRslt.getStartYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "�򰡰��ð� ���� �ʾ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	        
	        sDsNameMnr = "dsMnr";
	        sDsNameMnrDt = "dsMnrDt";
	        
	        if ( ctx.get(sDsNameMnr) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsNameMnr);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);	
		        	nTempCnt = updateMnr(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }	        
	        }
	        
	        if ( ctx.get(sDsNameMnrDt) != null ) {
		        ds2   		 = (PosDataset) ctx.get(sDsNameMnrDt);
		        nSizeDt 		 = ds2.getRecordCount();

		        for ( int i = 0; i < nSizeDt; i++ ) {
		            PosRecord record2 = ds2.getRecord(i);

			        if ( record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
			        	nTempCnt = updateMnrDt(record2);
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        } 
		        }	        
	        }
	        
	        int nUploadFileSize = 0;
	    	String sDsName = "dsUploadFile";
	    	String sAttFileKey = "";
	    	String tmpFileKey  = "";
	    	
	        if ( ctx.get(sDsName) != null ) {
		        ds   		    = (PosDataset) ctx.get(sDsName);
		        nUploadFileSize = ds.getRecordCount();
	        }
	        
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
			        			System.out.println("sAttFileKey"+sAttFileKey);
			        		} else {
			        			//÷�������� ������ ��ϵǴ°��
			        			if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){ 			
			        				if(tmpFileKey == null || tmpFileKey.equals("")){
		        						tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        					
			        					//����̷����� ÷������ key update
			        					record.setAttribute("ATT_FILE_KEY" ,tmpFileKey);
			        					record.setAttribute("ESTM_YEAR"    ,ctx.get("ESTM_YEAR"));
			        					record.setAttribute("ESTM_NUM"     ,ctx.get("ESTM_NUM"));
			        					record.setAttribute("ESTM_DEPT"    ,ctx.get("ESTM_DEPT"));
			        					record.setAttribute("ESTM_LITEM_CD",ctx.get("ESTM_LITEM_CD"));
			        					record.setAttribute("ESTM_EMP"     ,ctx.get("ESTM_EMP"));
			        					record.setAttribute("EMP_NO"       ,ctx.get("EMP_NO"));

			        					updateMnrDtFile(record);
			        					
			        					System.out.println("tmpFileKey in if"+tmpFileKey);
			        				}
			        				record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        				System.out.println("tmpFileKey"+tmpFileKey);
			        			}
			        		}
			        		
			        		
			        		
			        		record.setAttribute("TBL_NM", "TBRF_EV_WK_MNR_DT");
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
	    }
	    
	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateMnrDt(PosRecord record2)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record2.getAttribute("CNT"));            // 1. �򰡺μ� 
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. ����� ID      
	        
	        i = 0;

	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_NUM"));      // 5. ��ȸ��
	        param.setWhereClauseParameter(i++, record2.getAttribute("EMP_NO"));      // 6. �����ȣ
	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_DEPT"));
	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_LITEM_CD"));

	        int dmlcount = this.getDao("rbmdao").update("rev2020_u01", param);
	        return i;
	    }
	    
	    protected int updateMnr(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));            // 1. �򰡺μ� 
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. ����� ID      
	        
	        i = 0;

	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 5. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));      // 6. �����ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_DEPT"));

	        int dmlcount = this.getDao("rbmdao").update("rev2020_u02", param);
	        return i;
	    }
	    
	    /**
	     * <p> ÷������ ���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateMnrDtFile(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;          
	        
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
	        param.setValueParamter(i++, SESSION_USER_ID);

	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_LITEM_CD"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_EMP"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
			
	        int dmlcount = this.getDao("rbmdao").update("rev2020_u03", param);
	        return dmlcount;
	    }
	    
}
