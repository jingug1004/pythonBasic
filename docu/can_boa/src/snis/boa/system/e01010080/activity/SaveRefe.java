/*================================================================================
 * �ý���			: �ڵ� ����
 * �ҽ����� �̸�	: snis.boa.system.e01010030.activity.UserManager.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010080.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �迵ö
* @version 1.0
*/
public class SaveRefe extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveRefe()
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
        sDsName = "dsOutRefe";
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

        sDsName = "dsOutRefe";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateRefe(record)) == 0 ) {
		        		nTempCnt = insertRefe(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteRefe(record);	            	
	            }		        
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteRefe(record);	            	
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
    protected int insertRefe(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MNG"                ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_NAME"      ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_URL"       ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_SIZE"      ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD"          ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_SIZE"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN"          ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_SIZE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbed_refe_id001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> �׷��ڵ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRefe(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MNG"                ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_NAME"      ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_URL"       ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_SIZE"      ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD"          ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_SIZE"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN"          ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_SIZE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("REFERERE_SEQ" ));

        int dmlcount = this.getDao("boadao").update("tbed_refe_ud001", param);
        return dmlcount;
    }

    
    
    /**
     * <p> �׷��ڵ�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRefe(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("REFERERE_SEQ" ));
        
        int dmlcount = this.getDao("boadao").update("tbed_refe_dd001", param);
        return dmlcount;
    }           
}
