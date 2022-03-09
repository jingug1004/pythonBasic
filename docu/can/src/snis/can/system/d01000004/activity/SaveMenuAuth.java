/*================================================================================
 * �ý���			: �޴����� ����
 * �ҽ����� �̸�	: snis.can.system.d01010030.activity.MenuManager.java
 * ���ϼ���		: �޴����� ����
 * �ۼ���			: �ڿ���
 * ����			: 1.0.0
 * ��������		: 2007-12-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.system.d01000004.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.*;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
import snis.can.common.util.UtilDb;


/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �޴������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ڿ���
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
        
        sDsName = "dsUpAuthListValue";
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
    	String UserID	 = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsUpAuthListValue";
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        logger.logInfo("========== NSIZE " + nSize + "=======================");
	        	
					// ���� �ڷ� ��� 20141004 jdlee
        	backupAuthUser();
					
					// ����
            deleteAuthUser(ctx);

             nTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            UserID = (String)record.getAttribute("USER_ID");
	            
	            if(UserID == null) UserID="";
	            
	        	if(	   record.getAttribute("INPT_YN")   != null 
	        		&& record.getAttribute("SRCH_YN") != null){
	        		if((((String)record.getAttribute("INPT_YN")).equals("1")) 
	    	        		|| (((String)record.getAttribute("SRCH_YN")).equals("1")) ){
	    	        	nTempCnt = insertAuthMenu(record, ctx);	        			
	        		}
	        	}  

		        nSaveCount = nSaveCount + nTempCnt;
		        
		        //insertAuthAplyDept(record);				// ����ں� : ���� ������� �μ����� ������Ʈ
    			//deleteAuthAplyDept(UserID, curDateTime);	// ����ں� : �޴��� ���� ������ �ϳ��� ���� ��� ������ ���� ��� ���Ѽ����μ������� ����
    			
		        insertAuthAplyDept(record, ctx);		// ����ں� : ���� ������� �μ����� ������Ʈ
    			deleteAuthAplyDept(record, ctx);		// ����ں� : �޴��� ���� ������ �ϳ��� ���� ��� ������ ���� ��� ���Ѽ����μ������� ����
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> �޴����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAuthMenu(PosRecord record, PosContext ctx) 
    {
    	logger.logInfo("menuAuthInsert============================ ");
    	logger.logInfo("USER_ID==================================>"+(String)ctx.get("USER_ID"));
    	logger.logInfo("MN_ID==================================>"+record.getAttribute("MN_ID"));
    	logger.logInfo("SRCH_YN==================================>"+record.getAttribute("SRCH_YN"));
    	logger.logInfo("INPT_YN==================================>"+record.getAttribute("INPT_YN"));
    	    	
    	PosParameter param = new PosParameter();       					
        int i = 0;
    	
        param.setValueParamter(i++, (String)ctx.get("USER_ID"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MN_ID")));
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("SRCH_YN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INPT_YN")));
           	    	     
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("candao").update("tbda_mn_auth_ia001", param);
        
        return dmlcount;
    }


   /**
     * <p> �޴����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthUser(PosContext ctx)
    {
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,(String)ctx.get("USER_ID"));

        int dmlcount = this.getDao("candao").update("tbda_mn_auth_da001", param);
        return dmlcount;
    }    
    
        /**
     * <p> ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int backupAuthUser()
    {
			PosParameter param = new PosParameter();
			int dmlcount = 0;
			
			PosRowSet prs = this.getDao("candao").find("tbda_mn_auth_backup_cnt", param);
		
			// ���� ����� ����� ���ٸ� insert
			if (prs.count() > 0 && Integer.parseInt(prs.getAllRow()[0].getAttribute("cnt").toString()) == 0) {
				param = null;
				dmlcount = this.getDao("candao").update("tbda_auth_backup", param);
			}
      return dmlcount;
	}
    
    /**
     * <p> ��������μ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    //protected int insertAuthAplyDept(PosRecord record)
    protected int insertAuthAplyDept(PosRecord record, PosContext ctx)
    {
    	PosParameter param = new PosParameter();    
    	PosRowSet    rowset;
    	int dmlcount = 0;
        int i = 0;

    	param = new PosParameter();
    	i = 0;
    	              	    	     
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, (String)ctx.get("USER_ID"));
        //param.setValueParamter(i++, record.getAttribute("USER_ID"));	//�����ID
     
        dmlcount = this.getDao("candao").update("d01000011_m01", param);
       
        return dmlcount;
    }
    
    /**
     * <p> ����� ��������μ� ����  </p>
     * @param   userID	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    //protected int deleteAuthAplyDept(String userID, String curDtm)
    protected int deleteAuthAplyDept(PosRecord record, PosContext ctx)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, (String)ctx.get("USER_ID"));
        //param.setWhereClauseParameter(i++, UserID);
        //param.setWhereClauseParameter(i++, curDtm); 

        int dmlcount = this.getDao("candao").update("d01000011_d01", param);
        return dmlcount;
    }
}
