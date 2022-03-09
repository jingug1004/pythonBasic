/*================================================================================
 * �ý���			: �ڵ� ��
 * �ҽ����� �̸�	: snis.can.system.d01010030.activity.UserManager.java
 * ���ϼ���		: �ڵ� ��
 * �ۼ���			: �ڿ���
 * ����			: 1.0.0
 * ������		: 2007-12-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.system.d05000001.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� ������ �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ڿ���
* @version 1.0
*/
public class SaveCode extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveCode()
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
        sDsName = "dsGrpCodeListValue";
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
        
	        sDsName = "dsSpecCodeListValue";
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

        sDsName = "dsGrpCodeListValue";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateGrpCode(record)) == 0 ) {
		        		nTempCnt = insertGrpCode(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteGrpCode(record);	            	
	            }		        
	        }	        
        }

        sDsName = "dsSpecCodeListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateSpecCode(record)) == 0 ) {
		        		nTempCnt = insertSpecCode(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteSpecCode(record);	            	
	            }	
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> �׷��ڵ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertGrpCode(PosRecord record) 
    {
    	logger.logInfo("insertGrpCode start ==============");
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("GRP_NM"));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        int dmlcount = this.getDao("candao").update("tbdm_grp_cd_im001", param);
        
        logger.logInfo("insertGrpCode end ==============");
        return dmlcount;
    }
    
    
    
    /**
     * <p> �׷��ڵ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateGrpCode(PosRecord record)
    {
    	logger.logInfo("updateGrpCode start ==============");
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_NM"      ));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));

        int dmlcount = this.getDao("candao").update("tbdm_grp_cd_um001", param);
        
        logger.logInfo("updateGrpCode end ==============");
        return dmlcount;
    }
    
    
    /**
     * <p> �׷��ڵ�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteGrpCode(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, "Y" );
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        
        int dmlcount = this.getDao("candao").update("tbdm_grp_cd_dm001", param);
        
        PosParameter param1 = new PosParameter();
        int j = 0;
        param1.setWhereClauseParameter(j++, record.getAttribute("GRP_CD" ));
        param1.setWhereClauseParameter(j++, "");
        
        dmlcount = dmlcount + this.getDao("candao").update("tbdm_spec_cd_dm001", param1);
       
        return dmlcount;
    }
    
    
    /**
     * <p> ���ڵ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertSpecCode(PosRecord record) 
    {
    	PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("CD"));
        param.setValueParamter(i++, record.getAttribute("CD_NM"));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, record.getAttribute("ORDR"));
                
        int dmlcount = this.getDao("candao").update("tbdm_spec_cd_im001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> ���ڵ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSpecCode(PosRecord record)
    {
    	logger.logInfo("DEL_YN ==================>>>"+record.getAttribute("DEL_YN"));
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("CD_NM"      ));
        param.setValueParamter(i++, record.getAttribute("DEL_YN"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, record.getAttribute("ORDR"));
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("candao").update("tbdm_spec_cd_um001", param);
        
        return dmlcount;
    }    
    
    
    /**
     * <p> ���ڵ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSpecCode(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("candao").update("tbdm_spec_cd_dm001", param);
        
        return dmlcount;
    }        
           
}
