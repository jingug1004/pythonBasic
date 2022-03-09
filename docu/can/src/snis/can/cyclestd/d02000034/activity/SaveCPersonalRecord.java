/*================================================================================
 * �ý���			: �� ���� 
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000034.activity.SaveCPersonalRecord.java
 * ���ϼ���		: ���κ������
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-03-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000034.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* ���κ������ ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveCPersonalRecord extends SnisActivity
{    
    public SaveCPersonalRecord()
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
        
        ds    = (PosDataset) ctx.get("dsOutCPersonalRecord");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCPersonalRecord(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertCPersonalRecord(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateCPersonalRecord(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���κ������ ���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCPersonalRecord(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCPersonalRecord(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCPersonalRecord(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ���κ������ ���� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCPersonalRecord(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TIME_200M"));
        param.setValueParamter(i++, record.getAttribute("TIME_400M"));
        param.setValueParamter(i++, record.getAttribute("TIME_1000M"));
        param.setValueParamter(i++, record.getAttribute("ESTM_GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("ESTM_DT"));
        param.setValueParamter(i++, record.getAttribute("CNT")); 
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        
        return this.getDao("candao").update("tbdb_indv_rec_estm_ub001", param);
    }

    /**
     * <p>���κ������ ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCPersonalRecord(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ESTM_DT"));
        param.setValueParamter(i++, record.getAttribute("CNT")); 
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("TIME_200M"));
        param.setValueParamter(i++, record.getAttribute("TIME_400M"));
        param.setValueParamter(i++, record.getAttribute("TIME_1000M"));
        param.setValueParamter(i++, record.getAttribute("ESTM_GRD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
       	return this.getDao("candao").update("tbdb_indv_rec_estm_ib001", param);
    }

    /**
     * <p>���κ������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCPersonalRecord(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ESTM_DT"));
        param.setValueParamter(i++, record.getAttribute("CNT")); 
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        
        return  this.getDao("candao").update("tbdb_indv_rec_estm_db001", param);
    }
}
