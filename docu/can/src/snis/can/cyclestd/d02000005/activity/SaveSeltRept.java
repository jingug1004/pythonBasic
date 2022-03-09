/*================================================================================
 * �ý���			: �ĺ��� ���� ����  ����
 * �ҽ����� �̸�	: snis.can.system.d02010050.activity.SeltRept.java
 * ���ϼ���		: �ĺ��� ���� ���� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-01-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000005.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ĺ������������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveSeltRept extends SnisActivity
{    
	
    public SaveSeltRept()
    {
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
    	
    	 PosDataset ds;
         String     sDsName = "";
         sDsName = "dsSeltReptListValue";

         logger.logInfo("codemanager start1 ==============");
         if ( ctx.get(sDsName) != null ) {
 	        ds    = (PosDataset)ctx.get(sDsName);
 	        for ( int i = 0; i <  ds.getRecordCount(); i++ ) 
 	        {
 	        	logger.logInfo("dsSeltReptListValue  =============="+i);
 	            PosRecord record = ds.getRecord(i);
 	            logger.logInfo(record);
 	        }
 	       logger.logInfo("---------------------------------------");
 	      saveState(ctx);
         }
 	      sDsName = "dsUploadFileList";
 	       logger.logInfo("codemanager start2 ==============");
 	       
 	     if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        for ( int i = 0; i <  ds.getRecordCount(); i++ ) 
	        {
	        	logger.logInfo("dsUploadFileList  =============="+i);
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
	        saveStateFile(ctx);
         }
   
 	    logger.logInfo("codemanager end =================");
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
        sDsName = "dsSeltReptListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            logger.logInfo("");
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){ 
	            	nTempCnt = updateSaveSeltRept(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
	              	nTempCnt = insertSaveSeltRept(record);
	            }                  	
	            
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
	               	nDeleteCount = nDeleteCount + deleteSaveSeltRept(record);
	            }
	                nSaveCount = nSaveCount + nTempCnt;
	           		         
	        } // end for     
	     }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
        
    }
    protected void saveStateFile(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
       
        sDsName = "dsUploadFileList";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        PosDataset headDs = (PosDataset) ctx.get("dsSeltReptListValue");
	        PosRecord headRec = headDs.getRecord(0);
	        
	        	logger.logInfo("========== NSIZE " + nSize + "=======================");
	        	logger.logInfo("========== NSIZE " + (String) ctx.get("ISTYPE") + "=======================");
	        	
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		        	nDeleteCount = deleteFile(record);
		        }

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt =insertFile(record,headRec);
		        }
		        
		        if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
		        	nDeleteCount = updateFile(record);
		        }
		        nSaveCount = nSaveCount + nTempCnt;
	        } // end for
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * <p> �ĺ����������� �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveSeltRept(PosRecord record) 
    {
   	    logger.logInfo("==========================  �ĺ����������� �Է�   ============================");
                
        PosParameter param = new PosParameter();       					
        int i = 0;
                
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setValueParamter(i++, (record.getAttribute("RES_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("NM_CHN")));
        param.setValueParamter(i++, record.getAttribute("REPT_NO"));        
        param.setValueParamter(i++, (record.getAttribute("POST_NO")));
        param.setValueParamter(i++, (record.getAttribute("ADDR")));
        param.setValueParamter(i++, (record.getAttribute("DETL_ADDR")));
        param.setValueParamter(i++, (record.getAttribute("PHONE1")));
        param.setValueParamter(i++, (record.getAttribute("PHONE2")));
        param.setValueParamter(i++, (record.getAttribute("PHONE3")));
        param.setValueParamter(i++, (record.getAttribute("CELLPHONE1")));
        param.setValueParamter(i++, (record.getAttribute("CELLPHONE2")));
        param.setValueParamter(i++, (record.getAttribute("CELLPHONE3")));
        param.setValueParamter(i++, (record.getAttribute("GRDU_SCHL_DT")));
        param.setValueParamter(i++, (record.getAttribute("SCHL_NM")));
        param.setValueParamter(i++, (record.getAttribute("SCHL_CARR_STAT")));
        param.setValueParamter(i++, (record.getAttribute("ATHL_CARR")));
        param.setValueParamter(i++, (record.getAttribute("PRZ_CARR")));
        param.setValueParamter(i++, (record.getAttribute("PRNT")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_RELN")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_RES_NO")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_POST_NO")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_ADDR")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_DETL_ADDR")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_TEL1")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_TEL2")));
        param.setValueParamter(i++, (record.getAttribute("PRNT_TEL3")));
        param.setValueParamter(i++, (record.getAttribute("PTO_FILE")));
        param.setValueParamter(i++, (record.getAttribute("RES_DUPL_PTO_FILE")));
        param.setValueParamter(i++, (record.getAttribute("ORI_COPY_FILE")));
        param.setValueParamter(i++, (record.getAttribute("RESUME_FILE")));
        param.setValueParamter(i++, (record.getAttribute("PASS_YN")));
        param.setValueParamter(i++, (record.getAttribute("JOIN_PATH")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, (record.getAttribute("PTO_URL")));
        param.setValueParamter(i++, (record.getAttribute("RACER_TPE")));
        int dmlcount = this.getDao("candao").insert("tbdb_selt_rept_ib001", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> �ĺ����������� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSaveSeltRept(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("NM_KOR"));
        param.setValueParamter(i++, record.getAttribute("NM_CHN"));
        param.setValueParamter(i++, record.getAttribute("REPT_NO"));
        param.setValueParamter(i++, record.getAttribute("POST_NO"));
        param.setValueParamter(i++, record.getAttribute("ADDR"));
        param.setValueParamter(i++, record.getAttribute("DETL_ADDR"));
        param.setValueParamter(i++, record.getAttribute("PHONE1"));
        param.setValueParamter(i++, record.getAttribute("PHONE2"));
        param.setValueParamter(i++, record.getAttribute("PHONE3"));
        param.setValueParamter(i++, record.getAttribute("CELLPHONE1"));
        param.setValueParamter(i++, record.getAttribute("CELLPHONE2"));
        param.setValueParamter(i++, record.getAttribute("CELLPHONE3"));
        param.setValueParamter(i++, record.getAttribute("GRDU_SCHL_DT"));
        param.setValueParamter(i++, record.getAttribute("SCHL_NM"));
        param.setValueParamter(i++, record.getAttribute("SCHL_CARR_STAT"));
        param.setValueParamter(i++, record.getAttribute("ATHL_CARR"));
        param.setValueParamter(i++, record.getAttribute("PRZ_CARR"));
        param.setValueParamter(i++, record.getAttribute("PRNT"));
        param.setValueParamter(i++, record.getAttribute("PRNT_RELN"));
        param.setValueParamter(i++, record.getAttribute("PRNT_RES_NO"));
        
        param.setValueParamter(i++, record.getAttribute("PRNT_POST_NO"));
        param.setValueParamter(i++, record.getAttribute("PRNT_ADDR"));
        param.setValueParamter(i++, record.getAttribute("PRNT_DETL_ADDR"));
        param.setValueParamter(i++, record.getAttribute("PRNT_TEL1"));
        param.setValueParamter(i++, record.getAttribute("PRNT_TEL2"));
        param.setValueParamter(i++, record.getAttribute("PRNT_TEL3"));
        
        param.setValueParamter(i++, record.getAttribute("PTO_FILE"));
        param.setValueParamter(i++, record.getAttribute("RES_DUPL_PTO_FILE"));
        param.setValueParamter(i++, record.getAttribute("ORI_COPY_FILE"));
        param.setValueParamter(i++, record.getAttribute("RESUME_FILE"));
        param.setValueParamter(i++, record.getAttribute("PASS_YN"));     
        
        param.setValueParamter(i++, record.getAttribute("JOIN_PATH"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("PTO_URL"));
        param.setValueParamter(i++, (record.getAttribute("RACER_TPE")));
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("RES_NO" ));

        int dmlcount = this.getDao("candao").update("tbdb_selt_rept_ub001", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p> �ĺ����������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSaveSeltRept(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("RES_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO") );
        param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO") );
        
        int dmlcount  = this.getDao("candao").update("tbdb_selt_rept_db001", param);
        	
        
        return dmlcount;
    }    
    
    
    /**
     * <p> File �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertFile(PosRecord record, PosRecord headRec) 
    {
        PosParameter param = new PosParameter();
        logger.logDebug(" ================== Insert File Start =================== ");
 
        int i = 0;
        param.setValueParamter(i++, headRec.getAttribute("RACER_PERIO_NO") );
        param.setValueParamter(i++, headRec.getAttribute("REPT_NO") );
        param.setValueParamter(i++, headRec.getAttribute("RACER_PERIO_NO") );
        param.setValueParamter(i++, headRec.getAttribute("REPT_NO") );
        
        param.setValueParamter(i++, record.getAttribute("FILE_TITLE") );
        param.setValueParamter(i++, record.getAttribute("FILE_NAME") );
        param.setValueParamter(i++, record.getAttribute("FILE_URL") );
        param.setValueParamter(i++, record.getAttribute("FILE_SIZE") );
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("candao").insert("d02000005_ia001", param);
        
        logger.logDebug(" ================== Insert File End =================== ");
        return dmlcount;
    }
    
    /**
     * <p> File �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateFile(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        logger.logDebug(" ================== Update File Start =================== ");
         
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("FILE_TITLE") );
        param.setValueParamter(i++, record.getAttribute("FILE_NAME") );
        param.setValueParamter(i++, record.getAttribute("FILE_URL") );
        param.setValueParamter(i++, record.getAttribute("FILE_SIZE") );
       // param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO") );
        param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO") );
        param.setWhereClauseParameter(i++, record.getAttribute("FILE_SEQ") );

        int dmlcount = this.getDao("candao").update("d02000005_ua001", param);
        
        logger.logDebug(" ================== Update File End =================== ");
        return dmlcount;
    }
    
    /**
     * <p> File ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteFile(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        logger.logDebug(" ================== Delete File Start =================== ");
         
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO") );
        param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO") );
        param.setWhereClauseParameter(i++, record.getAttribute("FILE_SEQ") );
        
        int dmlcount = this.getDao("candao").delete("d02000005_da001", param);
        
        logger.logDebug(" ================== Delete File End =================== ");
        return dmlcount;
    }
}

