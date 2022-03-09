/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.fairness.e05060060.activity.SavePrivitWork.java
 * ���ϼ���		: �缳����Ȱ�� ���
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-03-04
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05060060.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �缳����Ȱ�� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SavePrivitWork extends SnisActivity
{    
    public SavePrivitWork()
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutPrivitWork");
        if ( ds != null ) 
        {
	        nSize = ds.getRecordCount();
	
	        for ( int i = 0; i < nSize; i++ ){
	            PosRecord record = ds.getRecord(i);
	            switch (record.getType())
	            {
		            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
		            	nDeleteCount = nDeleteCount + deletePrivitWork(record);
		            	break;
		            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
		            	nSaveCount = nSaveCount + insertPrivitWork(record);
		            	break;	
		            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
		            	nSaveCount = nSaveCount + updatePrivitWork(record);
		            	break;	
	            }
	        }
        }
        PosDataset dsUploadFile;
        int nRowCount        = 0;
        
        dsUploadFile	= (PosDataset) ctx.get("dsOutUploadFile");
        if ( dsUploadFile != null ) 
        {
	        nRowCount 		= dsUploadFile.getRecordCount();
	
	        for ( int i = 0; i < nRowCount; i++ ){
	            PosRecord record = dsUploadFile.getRecord(i);
	            switch (record.getType())
	            {
		            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
		            	nDeleteCount = nDeleteCount + deleteUploadFile(record);
		            	break;
		            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
		            	nSaveCount = nSaveCount + insertUploadFile(record);
		            	break;	
	            }
	        }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * <p> �缳����Ȱ�� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePrivitWork(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SBJT"));
        param.setValueParamter(i++, record.getAttribute("ACT_DESC")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        return this.getDao("boadao").update("tbee_investigate_work_ue001", param);
    }
   
    /**
     * <p>�缳����Ȱ�� ������ ���</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertPrivitWork(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("SBJT"));
        param.setValueParamter(i++, record.getAttribute("ACT_DESC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbee_investigate_work_ie001", param);
    }

    /**
     * <p>�缳����Ȱ�� ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deletePrivitWork(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        
        return  this.getDao("boadao").update("tbee_investigate_work_de001", param);
    }
    
    /**
     * <p>÷������ ������ ���</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertUploadFile(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_URL"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        
        return this.getDao("boadao").update("tbee_investigate_work_ie002", param);
    }

    /**
     * <p>÷������ ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteUploadFile(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        return  this.getDao("boadao").update("tbee_investigate_work_de002", param);
    }
}
