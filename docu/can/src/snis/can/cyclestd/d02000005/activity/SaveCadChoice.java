/*================================================================================
 * �ý���			: �ĺ��� ���� ����  ����
 * �ҽ����� �̸�	: snis.can.system.d020000005.activity.SeltRept.java
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
public class SaveCadChoice extends SnisActivity
{    
	
    public SaveCadChoice()
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
       
//    	 ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        PosDataset ds;
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsSeltReptDesc");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsFamList");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }

        ds    = (PosDataset)ctx.get("dsEduList");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }        

        ds    = (PosDataset)ctx.get("dsActList");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }        
        
        ds    = (PosDataset)ctx.get("dsCarList");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }   
        
        ds    = (PosDataset)ctx.get("dsLicList");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }    
        
        ds    = (PosDataset)ctx.get("dsUploadFileList");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
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
    	int nTempCnt = 0;
    	int nDeleteCount = 0; 
         
    	PosDataset ds;
        int nSize        = 0;
      
        ds    = (PosDataset) ctx.get("dsSeltReptDesc");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
            	nSaveCount += insertCadChoice(record);
            	
            }
            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){ 
            	nSaveCount += updateCadChoice(record);
            }
            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
               	nDeleteCount += deleteCadChoice(record);
            }
        }

        ds    = (PosDataset) ctx.get("dsFamList");
        nSize = ds.getRecordCount();
        nTempCnt = nTempCnt + saveFamList((PosDataset)ctx.get("dsSeltReptDesc"),(PosDataset)ctx.get("dsFamList"));
              
        ds    = (PosDataset) ctx.get("dsEduList");
        nSize = ds.getRecordCount();
        nTempCnt = nTempCnt + saveEduList((PosDataset)ctx.get("dsSeltReptDesc"),(PosDataset)ctx.get("dsEduList"));
               
        ds    = (PosDataset) ctx.get("dsActList");
        nSize = ds.getRecordCount();
        nTempCnt = nTempCnt + saveActList((PosDataset)ctx.get("dsSeltReptDesc"),(PosDataset)ctx.get("dsActList"));
               
        ds    = (PosDataset) ctx.get("dsCarList");
        nSize = ds.getRecordCount();
        nTempCnt = nTempCnt + saveCarList((PosDataset)ctx.get("dsSeltReptDesc"),(PosDataset)ctx.get("dsCarList"));
        
        ds    = (PosDataset) ctx.get("dsLicList");
        nSize = ds.getRecordCount();
        nTempCnt = nTempCnt + saveLicList((PosDataset)ctx.get("dsSeltReptDesc"),(PosDataset)ctx.get("dsLicList"));
        
        ds    = (PosDataset) ctx.get("dsUploadFileList");
        nSize = ds.getRecordCount();
        nTempCnt = nTempCnt + saveFileList((PosDataset)ctx.get("dsSeltReptDesc"),(PosDataset)ctx.get("dsUploadFileList"));
        
        nSaveCount = nSaveCount + nTempCnt;
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   ); 
    }
    
    
    /**
     * <p> �ĺ����������� �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCadChoice(PosRecord record) 
    {
   	    logger.logInfo("==========================  �ĺ����������� �Է�   ============================");

        PosParameter param = new PosParameter();       					
        int i = 0;
                
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, record.getAttribute("RES_NO"));
        param.setValueParamter(i++, record.getAttribute("REPT_NO"));
        
        
        param.setValueParamter(i++, record.getAttribute("NM_KOR"));
        param.setValueParamter(i++, record.getAttribute("NM_CHN"));        
        param.setValueParamter(i++, record.getAttribute("POST_NO1"));
        param.setValueParamter(i++, record.getAttribute("POST_NO2"));
        
        param.setValueParamter(i++, record.getAttribute("ADDR"));
        param.setValueParamter(i++, record.getAttribute("DETL_ADDR")); 
        param.setValueParamter(i++, record.getAttribute("PERMNT_POST_NO1"));
        param.setValueParamter(i++, record.getAttribute("PERMNT_POST_NO2"));
        param.setValueParamter(i++, record.getAttribute("PERMNT"));
        
        param.setValueParamter(i++, record.getAttribute("DETL_PERMNT"));    
        param.setValueParamter(i++, record.getAttribute("PHONE1"));
        param.setValueParamter(i++, record.getAttribute("PHONE2"));
        param.setValueParamter(i++, record.getAttribute("PHONE3"));
        param.setValueParamter(i++, record.getAttribute("CELLPHONE1"));
        
        param.setValueParamter(i++, record.getAttribute("CELLPHONE2"));
        param.setValueParamter(i++, record.getAttribute("CELLPHONE3"));
        param.setValueParamter(i++, record.getAttribute("SPA_PHONE1"));
        param.setValueParamter(i++, record.getAttribute("SPA_PHONE2"));
        param.setValueParamter(i++, record.getAttribute("SPA_PHONE3"));
        
        param.setValueParamter(i++, record.getAttribute("EMAIL"));
        param.setValueParamter(i++, record.getAttribute("MTARY_GBN"));
        param.setValueParamter(i++, record.getAttribute("ARMY_TPE"));
        param.setValueParamter(i++, record.getAttribute("BRNCH"));
        param.setValueParamter(i++, record.getAttribute("MLT_CLS"));
        
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM1"));
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM2"));
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM_MM"));
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM_BLNG"));
        param.setValueParamter(i++, record.getAttribute("ARMY_PATRIOTS"));
        
        param.setValueParamter(i++, record.getAttribute("HGHT"));
        param.setValueParamter(i++, record.getAttribute("WGHT"));
        param.setValueParamter(i++, record.getAttribute("SIGHT_LEFT"));
        param.setValueParamter(i++, record.getAttribute("SIGHT_RIGHT"));
        param.setValueParamter(i++, record.getAttribute("HEAL_SPEC_DESC"));
		
        param.setValueParamter(i++, record.getAttribute("MRRG_YN"));
        param.setValueParamter(i++, record.getAttribute("RELGN"));
        param.setValueParamter(i++, record.getAttribute("SPA_ABTY"));
        param.setValueParamter(i++, record.getAttribute("HOBY"));
        param.setValueParamter(i++, record.getAttribute("PTO_FILE"));
        
        param.setValueParamter(i++, record.getAttribute("PTO_URL"));
        param.setValueParamter(i++, record.getAttribute("JOIN_PATH"));
        
        param.setValueParamter(i++, record.getAttribute("RACER_TPE"));
        param.setValueParamter(i++, record.getAttribute("LAST_SCHL"));
        param.setValueParamter(i++, record.getAttribute("SCHL_CARR_STAT"));
        
        
        param.setValueParamter(i++, record.getAttribute("BIRTH_DT"));
         
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);        
        int dmlcount = this.getDao("candao").insert("tbdb_selt_rept_ib001", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> �ĺ����������� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCadChoice(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("NM_KOR"));
        param.setValueParamter(i++, record.getAttribute("NM_CHN"));
        param.setValueParamter(i++, record.getAttribute("POST_NO1"));
        param.setValueParamter(i++, record.getAttribute("POST_NO2"));
        
        param.setValueParamter(i++, record.getAttribute("ADDR"));
        param.setValueParamter(i++, record.getAttribute("DETL_ADDR")); 
        param.setValueParamter(i++, record.getAttribute("PERMNT_POST_NO1"));
        param.setValueParamter(i++, record.getAttribute("PERMNT_POST_NO2"));
        param.setValueParamter(i++, record.getAttribute("PERMNT"));
        
        param.setValueParamter(i++, record.getAttribute("DETL_PERMNT"));    
        param.setValueParamter(i++, record.getAttribute("PHONE1"));
        param.setValueParamter(i++, record.getAttribute("PHONE2"));
        param.setValueParamter(i++, record.getAttribute("PHONE3"));
        param.setValueParamter(i++, record.getAttribute("CELLPHONE1"));
        
        param.setValueParamter(i++, record.getAttribute("CELLPHONE2"));
        param.setValueParamter(i++, record.getAttribute("CELLPHONE3"));
        param.setValueParamter(i++, record.getAttribute("SPA_PHONE1"));
        param.setValueParamter(i++, record.getAttribute("SPA_PHONE2"));
        param.setValueParamter(i++, record.getAttribute("SPA_PHONE3"));
        
        param.setValueParamter(i++, record.getAttribute("EMAIL"));
        param.setValueParamter(i++, record.getAttribute("MTARY_GBN"));
        param.setValueParamter(i++, record.getAttribute("ARMY_TPE"));
        param.setValueParamter(i++, record.getAttribute("BRNCH"));
        param.setValueParamter(i++, record.getAttribute("MLT_CLS"));
        
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM1"));
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM2"));
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM_MM"));
        param.setValueParamter(i++, record.getAttribute("SVCE_TERM_BLNG"));
        param.setValueParamter(i++, record.getAttribute("ARMY_PATRIOTS"));
        
        param.setValueParamter(i++, record.getAttribute("HGHT"));
        param.setValueParamter(i++, record.getAttribute("WGHT"));
        param.setValueParamter(i++, record.getAttribute("SIGHT_LEFT"));
        param.setValueParamter(i++, record.getAttribute("SIGHT_RIGHT"));
        param.setValueParamter(i++, record.getAttribute("HEAL_SPEC_DESC"));
		
        param.setValueParamter(i++, record.getAttribute("MRRG_YN"));
        param.setValueParamter(i++, record.getAttribute("RELGN"));
        param.setValueParamter(i++, record.getAttribute("SPA_ABTY"));
        param.setValueParamter(i++, record.getAttribute("HOBY"));
        param.setValueParamter(i++, record.getAttribute("PTO_FILE"));
        
        param.setValueParamter(i++, record.getAttribute("PTO_URL"));
        param.setValueParamter(i++, record.getAttribute("JOIN_PATH"));
        param.setValueParamter(i++, record.getAttribute("DEL_YN")); 
        
        param.setValueParamter(i++, record.getAttribute("RACER_TPE"));
        param.setValueParamter(i++, record.getAttribute("LAST_SCHL"));
        param.setValueParamter(i++, record.getAttribute("SCHL_CARR_STAT"));
        
        
        param.setValueParamter(i++, record.getAttribute("BIRTH_DT"));
        
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO" ));
        //param.setWhereClauseParameter(i++, record.getAttribute("RES_NO" ));

        int dmlcount = this.getDao("candao").update("tbdb_selt_rept_ub001", param);
        
        return dmlcount;
    }

    
    /**
     * <p> �ĺ����������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCadChoice(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO" ));
        //param.setWhereClauseParameter(i++, record.getAttribute("RES_NO"));
        
        int dmlcount  = this.getDao("candao").update("tbdb_selt_rept_db001", param);
        	
        
        return dmlcount;
    }
    
    
    /**
     * <p> �ĺ����������� �������� ���� </p>
     * @param   dsParm	PosDataset	�����Ϸ��� �����ͼ�
     */
    protected int saveFamList(PosDataset dsHead,PosDataset dsBody){
    	int dmlcount = 0;
    	PosRecord recHead = (PosRecord)dsHead.getRecord(0); 
    	for(int j=0;j<dsBody.getRecordCount();j++){    		
        	PosRecord recBody = dsBody.getRecord(j);
        	
        	switch(recBody.getType()){
        	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
        		dmlcount = insertFamList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
        		dmlcount = updateFamList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
        		dmlcount = deleteFamList(recHead,recBody);
        		break;
        	}
        }
    	return dmlcount;
    }
    
    /**
     * <p> �������� �Է� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int insertFamList(PosRecord recHead,PosRecord recBody){
    	PosParameter param = new PosParameter();        
        int i = 0;
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recBody.getAttribute("FMLY_RELN"));
        param.setValueParamter(i++, recBody.getAttribute("NM_KOR"));
        param.setValueParamter(i++, recBody.getAttribute("LUNSOL"));
        param.setValueParamter(i++, recBody.getAttribute("BIRTH_DT"));
        param.setValueParamter(i++, recBody.getAttribute("NATY_SCHL"));
        param.setValueParamter(i++, recBody.getAttribute("OFFC_NM"));
        param.setValueParamter(i++, recBody.getAttribute("FLOC"));
        param.setValueParamter(i++, recBody.getAttribute("LODGE_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("candao").insert("tbdb_selt_fmly_ib001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �������� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int updateFamList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;
                
        param.setValueParamter(i++, recBody.getAttribute("FMLY_RELN"));
        param.setValueParamter(i++, recBody.getAttribute("NM_KOR"));
        param.setValueParamter(i++, recBody.getAttribute("LUNSOL"));
        param.setValueParamter(i++, recBody.getAttribute("BIRTH_DT"));
        param.setValueParamter(i++, recBody.getAttribute("NATY_SCHL"));
        param.setValueParamter(i++, recBody.getAttribute("OFFC_NM"));
        param.setValueParamter(i++, recBody.getAttribute("FLOC"));
        param.setValueParamter(i++, recBody.getAttribute("LODGE_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").update("tbdb_selt_fmly_ub001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �������� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int deleteFamList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter(); 
    	int i = 0;
        
        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").delete("tbdb_selt_fmly_db001", param);
    	return dmlcount;
    }
    
    
    
    /**
     * <p> �ĺ����������� �������� ���� </p>
     * @param   dsParm	PosDataset	�����Ϸ��� �����ͼ�
     */
    protected int saveEduList(PosDataset dsHead,PosDataset dsBody){
    	int dmlcount = 0;
    	PosRecord recHead = (PosRecord) dsHead.getRecord(0);
    	for(int j=0;j<dsBody.getRecordCount();j++){
    		PosRecord recBody = dsBody.getRecord(j);
        	switch(recBody.getType()){
        	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
        		dmlcount = insertEduList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
        		dmlcount = updateEduList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
        		dmlcount = deleteEduList(recHead,recBody);
        		break;
        	}
        }
    	return dmlcount;
    }
    
    /**
     * <p> �з»��� �Է� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int insertEduList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recBody.getAttribute("LAST_SCHL_NM"));
        param.setValueParamter(i++, recBody.getAttribute("MTRIC_SCHL_DT"));
        param.setValueParamter(i++, recBody.getAttribute("GRDU_SCHL_DT"));
        param.setValueParamter(i++, recBody.getAttribute("MJR"));
        param.setValueParamter(i++, recBody.getAttribute("GRDU_GBN"));
        param.setValueParamter(i++, recBody.getAttribute("THIS_SCHL_GBN"));
        param.setValueParamter(i++, recBody.getAttribute("DAY_GBN"));
        param.setValueParamter(i++, recBody.getAttribute("LAST_SCHL_AREA"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("candao").insert("tbdb_selt_schl_carr_ib001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �з»��� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int updateEduList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setValueParamter(i++, recBody.getAttribute("LAST_SCHL_NM"));
        param.setValueParamter(i++, recBody.getAttribute("MTRIC_SCHL_DT"));
        param.setValueParamter(i++, recBody.getAttribute("GRDU_SCHL_DT"));
        param.setValueParamter(i++, recBody.getAttribute("MJR"));
        param.setValueParamter(i++, recBody.getAttribute("GRDU_GBN"));
        param.setValueParamter(i++, recBody.getAttribute("THIS_SCHL_GBN"));
        param.setValueParamter(i++, recBody.getAttribute("DAY_GBN"));
        param.setValueParamter(i++, recBody.getAttribute("LAST_SCHL_AREA"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").update("tbdb_selt_schl_carr_ub001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �з»��� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int deleteEduList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").delete("tbdb_selt_schl_carr_db001", param);
    	return dmlcount;
    }
    
    
    /**
     * <p> �ĺ����������� �ֿ�Ȱ�� ���� </p>
      * @param   dsParm	PosDataset	�����Ϸ��� �����ͼ�
     */
    protected int saveActList(PosDataset dsHead,PosDataset dsBody){
    	int dmlcount = 0;
    	PosRecord recHead = (PosRecord) dsHead.getRecord(0);
    	for(int j=0;j<dsBody.getRecordCount();j++){
        	PosRecord recBody = dsBody.getRecord(j);
        	switch(recBody.getType()){
        	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
        		dmlcount = insertActList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
        		dmlcount = updateActList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
        		dmlcount = deleteActList(recHead,recBody);
        		break;
        	}
        }
    	return dmlcount;
    }
    
    /**
     * <p> �ֿ�Ȱ�� �Է� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int insertActList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;
        
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recBody.getAttribute("STR_DT"));
        param.setValueParamter(i++, recBody.getAttribute("END_DT"));
        param.setValueParamter(i++, recBody.getAttribute("GROUP_NM"));
        param.setValueParamter(i++, recBody.getAttribute("FRES"));
        param.setValueParamter(i++, recBody.getAttribute("DETL_CNTNT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("candao").insert("tbdb_selt_detl_carr_ib001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �ֿ�Ȱ�� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int updateActList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setValueParamter(i++, recBody.getAttribute("STR_DT"));
        param.setValueParamter(i++, recBody.getAttribute("END_DT"));
        param.setValueParamter(i++, recBody.getAttribute("GROUP_NM"));
        param.setValueParamter(i++, recBody.getAttribute("FRES"));
        param.setValueParamter(i++, recBody.getAttribute("DETL_CNTNT"));
        param.setValueParamter(i++, SESSION_USER_ID);
                
        i = 0;
        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").update("tbdb_selt_detl_carr_ub001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �ֿ�Ȱ�� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int deleteActList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").delete("tbdb_selt_detl_carr_db001", param);
    	return dmlcount;
    }
    
    
    /**
     * <p> �ĺ����������� ��»��� ���� </p>
     * @param   dsParm	PosDataset	�����Ϸ��� �����ͼ�
     */
    protected int saveCarList(PosDataset dsHead,PosDataset dsBody){
    	int dmlcount = 0;
    	PosRecord recHead = (PosRecord)dsHead.getRecord(0);
    	for(int j=0;j<dsBody.getRecordCount();j++){
        	PosRecord recBody = dsBody.getRecord(j);
        	switch(recBody.getType()){
        	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
        		dmlcount = insertCarList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
        		dmlcount = updateCarList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
        		dmlcount = deleteCarList(recHead,recBody);
        		break;
        	}
        }
    	return dmlcount;
    }
    
    /**
     * <p> ��»��� �Է� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int insertCarList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;
        
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recBody.getAttribute("STR_DT"));
        param.setValueParamter(i++, recBody.getAttribute("END_DT"));
        param.setValueParamter(i++, recBody.getAttribute("CARR_TERM"));
        param.setValueParamter(i++, recBody.getAttribute("OFFC_NM"));
        param.setValueParamter(i++, recBody.getAttribute("FLOC"));
        param.setValueParamter(i++, recBody.getAttribute("CHA_FLD"));
        param.setValueParamter(i++, recBody.getAttribute("LEAVE_RSN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("candao").insert("tbdb_selt_carr_ib001", param);
    	return dmlcount;
    }
    
    /**
     * <p> ��»��� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int updateCarList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setValueParamter(i++, recBody.getAttribute("STR_DT"));
        param.setValueParamter(i++, recBody.getAttribute("END_DT"));
        param.setValueParamter(i++, recBody.getAttribute("CARR_TERM"));
        param.setValueParamter(i++, recBody.getAttribute("OFFC_NM"));
        param.setValueParamter(i++, recBody.getAttribute("FLOC"));
        param.setValueParamter(i++, recBody.getAttribute("CHA_FLD"));
        param.setValueParamter(i++, recBody.getAttribute("LEAVE_RSN"));
        param.setValueParamter(i++, SESSION_USER_ID);
                
        i = 0;
        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").update("tbdb_selt_carr_ub001", param);
    	return dmlcount;
    }
    
    /**
     * <p> ��»��� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int deleteCarList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").delete("tbdb_selt_carr_db001", param);
    	return dmlcount;
    }
    
    
    
    
    
    /**
     * <p> �ĺ����������� �ڰݸ��� ���� </p>
     * @param   dsParm	PosDataset	�����Ϸ��� �����ͼ�
     */
    protected int saveLicList(PosDataset dsHead,PosDataset dsBody){
    	int dmlcount = 0;
    	PosRecord recHead = (PosRecord)dsHead.getRecord(0);
    	for(int j=0;j<dsBody.getRecordCount();j++){
        	PosRecord recBody = dsBody.getRecord(j);
        	switch(recBody.getType()){
        	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
        		dmlcount = insertLicList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
        		dmlcount = updateLicList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
        		dmlcount = deleteLicList(recHead,recBody);
        		break;
        	}
        }
    	return dmlcount;
    }
    
    /**
     * <p> �ڰݸ��� �Է� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int insertLicList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;
                
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO"));
        param.setValueParamter(i++, recBody.getAttribute("CERT_NM"));
        param.setValueParamter(i++, recBody.getAttribute("CERT_NO"));
        param.setValueParamter(i++, recBody.getAttribute("REG_DT"));
        param.setValueParamter(i++, recBody.getAttribute("CERT_GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("candao").insert("tbdb_selt_cert_ib001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �ڰݸ��� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int updateLicList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;
                
        param.setValueParamter(i++, recBody.getAttribute("CERT_NM"));
        param.setValueParamter(i++, recBody.getAttribute("CERT_NO"));
        param.setValueParamter(i++, recBody.getAttribute("REG_DT"));
        param.setValueParamter(i++, recBody.getAttribute("CERT_GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));
        
        int dmlcount = this.getDao("candao").update("tbdb_selt_cert_ub001", param);
    	return dmlcount;
    }
    
    /**
     * <p> �ڰݸ��� ���� </p>
     * @param   record		PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int			ó���� ���ڵ� ����
     * @throws  none
     */
    protected int deleteLicList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter();       					
        int i = 0;

        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO"));
        param.setWhereClauseParameter(i++, recBody.getAttribute("SEQ"));

        int dmlcount = this.getDao("candao").delete("tbdb_selt_cert_db001", param);
    	return dmlcount;
    }
    
    
    /**
     * <p> �ĺ����������� ��»��� ���� </p>
     * @param   dsParm	PosDataset	�����Ϸ��� �����ͼ�
     */
    protected int saveFileList(PosDataset dsHead,PosDataset dsBody){
    	int dmlcount = 0;
    	PosRecord recHead = (PosRecord)dsHead.getRecord(0);
    	for(int j=0;j<dsBody.getRecordCount();j++){
        	PosRecord recBody = dsBody.getRecord(j);
        	switch(recBody.getType()){
        	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
        		dmlcount = insertFileList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
        		dmlcount = updateFileList(recHead,recBody);
        		break;
        	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
        		dmlcount = deleteFileList(recHead,recBody);
        		break;
        	}
        }
    	return dmlcount;
    }
    
    protected int insertFileList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter(); 
    	int i = 0;
    	logger.logInfo("===RACER_PERIO_NO====="+recHead.getAttribute("RACER_PERIO_NO"));
    	logger.logInfo("===REPT_NO====="+recHead.getAttribute("REPT_NO"));
    	logger.logInfo("===FILE_TITLE====="+recBody.getAttribute("FILE_TITLE"));
    	logger.logInfo("===FILE_NAME====="+recBody.getAttribute("FILE_NAME"));
    	logger.logInfo("===FILE_URL====="+recBody.getAttribute("FILE_URL"));
    	logger.logInfo("===FILE_SIZE====="+recBody.getAttribute("FILE_SIZE"));
    	
      	param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO") );
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO") );
        param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO") );
        param.setValueParamter(i++, recHead.getAttribute("REPT_NO") );
        param.setValueParamter(i++, recBody.getAttribute("FILE_TITLE") );
        param.setValueParamter(i++, recBody.getAttribute("FILE_NAME") );
        param.setValueParamter(i++, recBody.getAttribute("FILE_URL") );
        param.setValueParamter(i++, recBody.getAttribute("FILE_SIZE") );
        param.setValueParamter(i++, SESSION_USER_ID);
        
    	int dmlcount = this.getDao("candao").insert("d02000005_ia001", param);
    	return dmlcount;
    }
    
    protected int updateFileList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter(); 
    	int i = 0;
        
        param.setValueParamter(i++, recBody.getAttribute("FILE_TITLE") );
        param.setValueParamter(i++, recBody.getAttribute("FILE_NAME") );
        param.setValueParamter(i++, recBody.getAttribute("FILE_URL") );
        param.setValueParamter(i++, recBody.getAttribute("FILE_SIZE") );
       // param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO") );
        param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO") );
        param.setWhereClauseParameter(i++, recBody.getAttribute("FILE_SEQ") );
    	int dmlcount = this.getDao("candao").update("d02000005_ua001", param);
    	return dmlcount;
    }
    
    protected int deleteFileList(PosRecord recHead, PosRecord recBody){
    	PosParameter param = new PosParameter(); 
    	  int i = 0;
	    param.setWhereClauseParameter(i++, recBody.getAttribute("RACER_PERIO_NO") );
	    param.setWhereClauseParameter(i++, recBody.getAttribute("REPT_NO") );
	    param.setWhereClauseParameter(i++, recBody.getAttribute("FILE_SEQ") );
	      
    	int dmlcount = this.getDao("candao").delete("d02000005_da001", param);
    	return dmlcount;
    }
    
}