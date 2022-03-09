/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02010040.activity.SaveRecdStndTerm.java
 * ���ϼ���		: �����ݿ��б���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02010040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����ȸ���� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRecdStndTerm extends SnisActivity
{    
    public SaveRecdStndTerm()
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
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutRecdStndTerm";
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // �����ݿ��б� ����
        sDsName = "dsOutRecdStndTerm";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
            		nDeleteCount = nDeleteCount + deleteRecdStndTerm(record);
	            }
	        }
	        
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	  	        {
	            	if ( (nTempCnt = updateRecdStndTerm(record)) == 0 ) {
	                	nTempCnt = insertRecdStndTerm(record);
	                }
	  	        }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �����ݿ��б� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRecdStndTerm(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_STR_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_END_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK             ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR     ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("QURT_CD       ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_recd_stnd_term_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �����ݿ��б� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRecdStndTerm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("QURT_CD         ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_STR_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_END_DT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK             ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_recd_stnd_term_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> �����ݿ��б� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRecdStndTerm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR     ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("QURT_CD       ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_recd_stnd_term_db001", param);
        return dmlcount;
    }
}
