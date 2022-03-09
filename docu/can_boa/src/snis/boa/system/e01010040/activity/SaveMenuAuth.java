/*================================================================================
 * �ý���			: �޴����� ����
 * �ҽ����� �̸�	: snis.boa.system.e01010030.activity.MenuManager.java
 * ���ϼ���		: �޴����� ����
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.DesCipher;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;


/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �޴������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �迵ö
* @version 1.0
*/
public class SaveMenuAuth extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveMenuAuth()
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
        
        
        sDsName = "dsAuthListValue";
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
           
            nTempCnt = 0;
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		        	deleteUser(record);
		        }
		        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	if ( record.getAttribute("GBN").equals("001") ) continue;
		        	if ( (nTempCnt = updateUser(record)) != 1 ) {
		        		nTempCnt = insertUser(record);
		        	}
		        	nSaveCount = nSaveCount + nTempCnt;
		        }
	        }
        }

        // ���� ����� ���� ����
        sDsName = "dsAuthList";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
        
            PosRecord recordDel = new PosRecord();
            recordDel.setAttribute("USER_ID", ctx.get("USER_ID"));
	        deleteAuthUser(recordDel);

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
        		nTempCnt = insertAuthMenu(record);
	        	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        // ���õ� ����� ���� ����
        sDsName = "dsUserList";
        if ( ctx.get(sDsName) != null ) {
        	PosDataset dsUserList    = (PosDataset) ctx.get(sDsName);
	        int        nSizeUserList = dsUserList.getRecordCount();
        	        
	        sDsName = "dsAuthList";
	        ds   	= (PosDataset) ctx.get(sDsName);
	        nSize 	= ds.getRecordCount();
	        
	        for ( int i = 0; i < nSizeUserList; i++ ) {
	            PosRecord recordUserList = dsUserList.getRecord(i);
	            
		        deleteAuthUser(recordUserList);
		        
		        for ( int j = 0; j < nSize; j++ ) {
		            PosRecord record = ds.getRecord(j);
		            record.setAttribute("USER_ID", recordUserList.getAttribute("USER_ID"));
	        		nTempCnt = insertAuthMenu(record);
		        	nSaveCount = nSaveCount + nTempCnt;
		        }
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> �Խñ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAuthMenu(PosRecord record) 
    {
        PosParameter param = new PosParameter();       					
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID"));
        param.setValueParamter(i++, record.getAttribute("MN_ID"  ));
        param.setValueParamter(i++, record.getAttribute("SRCH_YN"));
        param.setValueParamter(i++, record.getAttribute("INPT_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbea_mn_auth_ia001", param);
        
        return dmlcount;
    }


    /**
     * <p> �Խñ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));

        int dmlcount = this.getDao("boadao").update("tbea_mn_auth_da001", param);
        return dmlcount;
    }

    /**
     * <p> �Խñ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        DesCipher bean  = new DesCipher();
        if ( record.getAttribute("PSWD") != null && !record.getAttribute("PSWD").equals("") ) {
        	record.setAttribute("PSWD", bean.Encode((String) record.getAttribute("PSWD")));
        }

        param.setValueParamter(i++, record.getAttribute("PSWD"   ));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("EMAIL"  ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO" ));
        param.setValueParamter(i++, record.getAttribute("FLOC"   ));
        param.setValueParamter(i++, record.getAttribute("FGRADE" ));
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));

        i =0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));

        int dmlcount = this.getDao("boadao").update("tbea_user_ua001", param);

        return dmlcount;
    }    

    /**
     * <p> �Խñ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID"));
        

        DesCipher bean  = new DesCipher();
        if ( record.getAttribute("PSWD") != null && !record.getAttribute("PSWD").equals("") ) {
        	record.setAttribute("PSWD", bean.Encode((String) record.getAttribute("PSWD")));
        }
        
        param.setValueParamter(i++, record.getAttribute("PSWD"   ));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("EMAIL"  ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO" ));
        param.setValueParamter(i++, record.getAttribute("FLOC"   ));
        param.setValueParamter(i++, record.getAttribute("FGRADE" ));
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));

        int dmlcount = this.getDao("boadao").update("tbea_user_ia001", param);
        return dmlcount;
    }    

    /**
     * <p> �Խñ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));

        int dmlcount = this.getDao("boadao").update("tbea_user_da001", param);
        this.getDao("boadao").update("tbea_mn_auth_da001", param);
        return dmlcount;
    }    
}
