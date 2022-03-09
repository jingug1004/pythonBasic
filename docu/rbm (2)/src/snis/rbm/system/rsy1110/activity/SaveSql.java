package snis.rbm.system.rsy1110.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther 
* @version 1.0
*/
public class SaveSql extends SnisActivity {    
	
	
    public SaveSql(){
    }

    /**
     * <p> SaveSql Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx) {
    	
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	/*
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ){
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("-------------------------------------- dsDetailList ----------------------");
	            logger.logInfo(record);
	            logger.logInfo("-------------------------------------- dsDetailList ----------------------");
	        }
        }
		*/
    	
        saveState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsSqlList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
		        	nDeleteCount = nDeleteCount + delete(record, ctx);	            	
	            }	
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 
		          //|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.NORMAL
		          ) {
		        	if ( (nTempCnt = update(record)) == 0 ) {
		        		nTempCnt = insert(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        }

	        }	 
	        	        
        }
        
        Util.setSaveCount(ctx, nSaveCount); 
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    
    /**
     * <p> ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insert(PosRecord record) {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("TITLE"));
        param.setValueParamter(i++, record.getAttribute("MAIN_STR"));
        param.setValueParamter(i++, record.getAttribute("EDIT_STR"));
        param.setValueParamter(i++, record.getAttribute("ORDER_STR"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("rbmdao").insert("rsy1110_TBRS_SQL_LIST_insert1", param);
    }
   
    /**
     * <p> ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int update(PosRecord record){
        PosParameter param = new PosParameter();

        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("TITLE"));
        param.setValueParamter(i++, record.getAttribute("MAIN_STR"));       
        param.setValueParamter(i++, record.getAttribute("EDIT_STR"));
        param.setValueParamter(i++, record.getAttribute("ORDER_STR"));        
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));
        
        return this.getDao("rbmdao").update("rsy1110_TBRS_SQL_LIST_update1", param);
    }
    
    
    /**
     * <p> ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int delete(PosRecord record, PosContext ctx) {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));  
        
        return this.getDao("rbmdao").delete("rsy1110_TBRS_SQL_LIST_delete1", param);
    }      
    
 }
