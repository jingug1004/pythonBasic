/*================================================================================
 * �ý���			: �� ���� 
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000037.activity.SaveCConfirmRecord.java
 * ���ϼ���		: �����ȸ
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-03-27
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000037.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �����ȸ ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveCConfirmRecord extends SnisActivity
{    
    public SaveCConfirmRecord()
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
        
        ds    = (PosDataset) ctx.get("dsOutCConfirmRecord");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCConfirmRecord(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertCConfirmRecord(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateCConfirmRecord(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �����ȸ ���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCConfirmRecord(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCConfirmRecord(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCConfirmRecord(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �����ȸ ���� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCConfirmRecord(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("STRATEGY"));
        param.setValueParamter(i++, record.getAttribute("LEG_TPE"));
        param.setValueParamter(i++, record.getAttribute("WIN_RATE"));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATE"));
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        
        return this.getDao("candao").update("tbec_cert_issu_history_ub001", param);
    }

    /**
     * <p>�����ȸ ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCConfirmRecord(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("CAND_NM")); 
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("STRATEGY"));
        param.setValueParamter(i++, record.getAttribute("LEG_TPE"));
        param.setValueParamter(i++, record.getAttribute("WIN_RATE"));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATE"));
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
       	return this.getDao("candao").update("tbec_cert_issu_history_ib001", param);
    }

    /**
     * <p>�����ȸ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCConfirmRecord(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        
        return  this.getDao("candao").update("tbec_cert_issu_history_db001", param);
    }
}
