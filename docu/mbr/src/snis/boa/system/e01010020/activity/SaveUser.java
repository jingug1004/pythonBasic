/*================================================================================
 * �ý���			: User ����
 * �ҽ����� �̸�	: snis.boa.system.e01010030.activity.UserManager.java
 * ���ϼ���		: User ����
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-05
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010020.activity;

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
public class SaveUser extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveUser()
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
        
        sDsName = "dsUserListValue";
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

        sDsName = "dsUserListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	 nTempCnt = updateUser(record);
		        }
		        
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = insertUser(record);
		        }

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		        	nTempCnt = deleteUser(record);
		        }
		        
		        nSaveCount 		= 	nSaveCount 		+ nTempCnt;
		        nDeleteCount 	= 	nDeleteCount 	+ nTempCnt;
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> �Խñ� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateUser(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_NM"      ));
        param.setValueParamter(i++, record.getAttribute("PSWD"      ));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"      ));
        param.setValueParamter(i++, record.getAttribute("DUTY_GRP_CD"      ));
        param.setValueParamter(i++, record.getAttribute("FLOC_CD"      ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO"      ));
        param.setValueParamter(i++, record.getAttribute("HP_NO"      ));
        param.setValueParamter(i++, record.getAttribute("STR_DT"      ));
        param.setValueParamter(i++, record.getAttribute("END_DT"      ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));

        int dmlcount = this.getDao("boadao").update("tbea_user_ua001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Խñ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertUser(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID"));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("PSWD"));
        param.setValueParamter(i++, record.getAttribute("DUTY_GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("FLOC_CD"));
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));
        param.setValueParamter(i++, record.getAttribute("HP_NO"));
        param.setValueParamter(i++, record.getAttribute("STR_DT"));
        param.setValueParamter(i++, record.getAttribute("END_DT"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbea_user_ia001", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteUser(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));
        
        int dmlcount  = this.getDao("boadao").update("tbea_user_da001", param);
        
        param = new PosParameter();
        i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));
        
        dmlcount += this.getDao("boadao").update("tbea_mn_auth_da001", param);
        
        return dmlcount;
    }    
}
