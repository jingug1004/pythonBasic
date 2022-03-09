/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.fairness.e05010010.activity.SaveSancBasis.java
 * ���ϼ���		: �������ص��
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-01-04
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05010010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���������ڵ� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveSancBasis extends SnisActivity
{    
    public SaveSancBasis()
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
        
        ds    = (PosDataset) ctx.get("dsOutSancBasis");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteSancBasis(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertSancBasis(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateSancBasis(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���������ڵ� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSancBasis(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateSancBasis(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertSancBasis(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ���������ڵ� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSancBasis(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("TERM")); 
        param.setValueParamter(i++, record.getAttribute("UNIT")); 
        param.setValueParamter(i++, record.getAttribute("STR_DT")); 
        param.setValueParamter(i++, record.getAttribute("END_DT"));
        param.setValueParamter(i++, record.getAttribute("SANC_ITEM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD"));
        return this.getDao("boadao").update("tbee_sanc_basis_ue001", param);
    }

    /**
     * <p>���������ڵ� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSancBasis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD")); 
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("TERM")); 
        param.setValueParamter(i++, record.getAttribute("UNIT")); 
        param.setValueParamter(i++, record.getAttribute("STR_DT")); 
        param.setValueParamter(i++, record.getAttribute("END_DT"));
        param.setValueParamter(i++, record.getAttribute("SANC_ITEM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
       	return this.getDao("boadao").update("tbee_sanc_basis_ie001", param);
    }

    /**
     * <p>���������ڵ� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSancBasis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD")); 
        return  this.getDao("boadao").update("tbee_sanc_basis_de001", param);
    }
}
