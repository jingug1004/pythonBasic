/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.fairness.e05060020.activity.SaveAcctUpload.java
 * ���ϼ���		: ���� �ŷ����� ���ε� �������
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-01-23
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05060020.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���� �ŷ����� ��� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveAcctUpload extends SnisActivity
{    
    public SaveAcctUpload()
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutAcctUpload");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
           		nSaveCount = nSaveCount + insertAcctUpload(record);
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p>���� �ŷ����� ���ε� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertAcctUpload(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT")); 
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT"));
        param.setValueParamter(i++, record.getAttribute("MJR_NM"));
        param.setValueParamter(i++, record.getAttribute("BK_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT"));
        param.setValueParamter(i++, record.getAttribute("BK_ACT"));
        param.setValueParamter(i++, record.getAttribute("NM"));
        param.setValueParamter(i++, record.getAttribute("TR_DT"));
        param.setValueParamter(i++, record.getAttribute("TR_AMT"));
        param.setValueParamter(i++, record.getAttribute("TR_TIME"));
        param.setValueParamter(i++, record.getAttribute("TR_DT"));
        param.setValueParamter(i++, record.getAttribute("IO_GUBUN"));
        param.setValueParamter(i++, record.getAttribute("AGENCY"));
        param.setValueParamter(i++, record.getAttribute("TERMINAL"));
        param.setValueParamter(i++, record.getAttribute("JEOGYO"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT")); 
        
        return this.getDao("boadao").update("tbee_money_account_ie001", param);
    }
}
