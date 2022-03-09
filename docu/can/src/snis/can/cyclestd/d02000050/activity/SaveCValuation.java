/*================================================================================
 * �ý���			: �� ���� 
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000050.activity.SaveCValuation.java
 * ���ϼ���		: ����򰡱���
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-03-17
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000050.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* ����򰡱��� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveCValuation extends SnisActivity
{    
    public SaveCValuation()
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
        
        ds    = (PosDataset) ctx.get("dsOutCValuation");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCValuation(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertCValuation(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateCValuation(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ����򰡱��� ���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCValuation(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCValuation(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCValuation(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ������޺� �������� ���� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCValuation(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACELEN")); 
        param.setValueParamter(i++, record.getAttribute("FROM_TIME"));
        param.setValueParamter(i++, record.getAttribute("TO_TIME"));
        return this.getDao("candao").update("tbdb_indv_rec_basis_ub001", param);
    }

    /**
     * <p>����򰡱��� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCValuation(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACELEN")); 
        param.setValueParamter(i++, record.getAttribute("FROM_TIME"));
        param.setValueParamter(i++, record.getAttribute("TO_TIME"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
       	return this.getDao("candao").update("tbdb_indv_rec_basis_ib001", param);
    }

    /**
     * <p>����򰡱��� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCValuation(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACELEN")); 
        param.setValueParamter(i++, record.getAttribute("FROM_TIME"));
        param.setValueParamter(i++, record.getAttribute("TO_TIME"));
        return  this.getDao("candao").update("tbdb_indv_rec_basis_db001", param);
    }
}
