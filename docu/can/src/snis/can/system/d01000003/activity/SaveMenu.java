/*================================================================================
 * �ý���			: �޴� ����
 * �ҽ����� �̸�	: snis.can.system.d01000003.activity.MenuManager.java
 * ���ϼ���		: �޴� ����
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-05
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.system.d01000003.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �迵ö
* @version 1.0
*/
public class SaveMenu extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveMenu()
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
        int        nSize        = 0;
        String     sDsName = "";
        sDsName = "dsMenuGrpValue";
        logger.logInfo("codemanager start ==============");
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
    	
        sDsName = "dsMenuListValue";
        logger.logInfo("codemanager start ==============");
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
        int nDTempCnt     = 0;

        sDsName = "dsMenuGrpValue";
        
        String sPersonalDataYn; //�������� 
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        
	         logger.logInfo("========== NSIZE " + nSize + "=======================");

	         nTempCnt = 0;
	         nDTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            sPersonalDataYn = (String)record.getAttribute("PERSONAL_DATA_MN");
	        	if(sPersonalDataYn == null) sPersonalDataYn=""; //��������
	        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	 nTempCnt = updateMenu(record);
		        }else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = updateMenu(record);
		        	if(nTempCnt == 0) nTempCnt = insertMenu(record);
		        }else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
			    	nDTempCnt = deleteMenu(record);
			    	nDeleteCount += nDTempCnt;
			    	if(nDTempCnt > 0) deleteAuthMenu(record);
		        }
		        nSaveCount 		= nSaveCount 	+ 	nTempCnt;
		       // nDeleteCount 	= nDeleteCount 	+ 	nDTempCnt;
	        } // end for
        }
         
        sDsName = "dsMenuListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	         logger.logInfo("========== NSIZE " + nSize + "=======================");

	         nTempCnt = 0;
	         nDTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            sPersonalDataYn = (String)record.getAttribute("PERSONAL_DATA_MN");
	        	if(sPersonalDataYn == null) sPersonalDataYn=""; //��������
	        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	 nTempCnt = updateMenu(record);
		        }else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = updateMenu(record);
		        	if(nTempCnt == 0) nTempCnt = insertMenu(record);
		        }else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
			    	nDTempCnt = deleteMenu(record);
			    	nDeleteCount += nDTempCnt;
			    	if(nTempCnt > 0) deleteAuthMenu(record);
		        }
		        nSaveCount = nSaveCount + nTempCnt;
		       // nDeleteCount 	= nDeleteCount 	+ nDTempCnt;
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> �޴� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMenu(PosRecord record)
    {
    	logger.logInfo("updateMenu ============================");
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_NM"      ));
        param.setValueParamter(i++, record.getAttribute("UP_MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("URL"      ));
        param.setValueParamter(i++, record.getAttribute("RMK"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));

        int dmlcount = this.getDao("candao").update("tbda_mn_ua001", param);
        
        logger.logInfo("updateMenu ============================");
        return dmlcount;
    }

    /**
     * <p> �޴� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertMenu(PosRecord record) 
    {
    	logger.logInfo("insertMenu ============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"));
        param.setValueParamter(i++, record.getAttribute("MN_NM"));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));
        param.setValueParamter(i++, record.getAttribute("UP_MN_ID"));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"));
        param.setValueParamter(i++, record.getAttribute("URL"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));
        int dmlcount = this.getDao("candao").update("tbda_mn_ia001", param);
        
        logger.logInfo("insertMenu ============================");
        return dmlcount;
    }



    
    /**
     * <p> �޴� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteMenu(PosRecord record)
    {
    	logger.logInfo("========== deleteMenu =======================");
    	logger.logInfo("========== deleteMenu =======================");
    	logger.logInfo("========== deleteMenu =======================");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("MN_ID"));
        param.setWhereClauseParameter(i++,record.getAttribute("MN_ID"));
        
        int dmlcount = this.getDao("candao").update("tbda_mn_da001", param);
        return dmlcount;
    }

    
    /**
     * <p> �޴����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthMenu(PosRecord record)
    {
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("MN_ID"));
        
        int dmlcount = this.getDao("candao").update("tbda_mn_da002", param);
        return dmlcount;
    }    
}
