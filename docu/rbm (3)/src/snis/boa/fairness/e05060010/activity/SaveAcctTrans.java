/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.fairness.e05060010.activity.SaveAcctTrans.java
 * ���ϼ���		: ���� �ŷ����� ����
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-01-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05060010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���� �ŷ����� ���� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveAcctTrans extends SnisActivity
{    
    public SaveAcctTrans()
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
        
        ds    = (PosDataset) ctx.get("dsOutAcctTrans");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
            	nDeleteCount = nDeleteCount + deleteAcctTrans(record);
            	break;
            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
            	nSaveCount = nSaveCount + insertAcctTrans(record);
            	break;	
            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
            	nSaveCount = nSaveCount + updateAcctTrans(record);
            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���� �ŷ����� ���� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveAcctTrans(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateAcctTrans(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertAcctTrans(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p>���� �ŷ����� ���� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteAcctTrans(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT")); 
        
        return  this.getDao("boadao").update("tbee_main_account_de001", param);
    }

    /**
     * <p> ���� �ŷ����� ���� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAcctTrans(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MJR_NM"));
        param.setValueParamter(i++, record.getAttribute("BK_NM"));
        param.setValueParamter(i++, record.getAttribute("MEMO"));
        param.setValueParamter(i++, record.getAttribute("RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT"));

        return this.getDao("boadao").update("tbee_main_account_ue001", param);
    }

    /**
     * <p>���� �ŷ����� ���� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertAcctTrans(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT")); 
        param.setValueParamter(i++, record.getAttribute("MJR_NM"));
        param.setValueParamter(i++, record.getAttribute("BK_NM"));
        param.setValueParamter(i++, record.getAttribute("MEMO"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);

        return this.getDao("boadao").update("tbee_main_account_ie001", param);
    }
}
