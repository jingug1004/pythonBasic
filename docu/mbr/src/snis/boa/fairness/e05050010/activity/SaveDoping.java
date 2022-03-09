/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.fairness.e05050010.activity.SaveDoping.java
 * ���ϼ���		: �๰�˻� �������
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-01-17
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05050010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �๰�˻� ���� ��� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveDoping extends SnisActivity
{    
    public SaveDoping()
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
        
        ds    = (PosDataset) ctx.get("dsOutDoping");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
            {
            	if (record.getAttribute("CHK").toString().equals("0"))
            	{
            		nDeleteCount = nDeleteCount + deleteDoping(record);
            	}
            	else
            	{
//            		if(logger.isDebugEnabled())logger.logDebug("==========================>UPDATE");
            		nSaveCount = nSaveCount + insertDoping(record);
            	}
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �๰�˻� ���� ��� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveDoping(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateDoping(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertDoping(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �๰�˻� ���� ��� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateDoping(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DOPING_DESC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));

        return this.getDao("boadao").update("tbee_doping_test_ue001", param);
    }

    /**
     * <p>�๰�˻� ���� ��� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertDoping(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("DOPING_DESC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("DOPING_DESC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbee_doping_test_ie001", param);
    }

    /**
     * <p>�๰�˻� ���� ��� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteDoping(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        
        return  this.getDao("boadao").update("tbee_doping_test_de001", param);
    }
}
