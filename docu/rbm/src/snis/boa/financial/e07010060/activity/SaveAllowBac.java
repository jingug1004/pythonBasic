/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.AllowBacment.e06010010.activity.SaveAllowBac.java
 * ���ϼ���		: ���� ���ޱ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07010060.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���� ���ޱ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveAllowBac extends SnisActivity
{    
    public SaveAllowBac()
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
        
        ds    = (PosDataset) ctx.get("dsOutAllowBac");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveAllowBac(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���� ���ޱ���Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveAllowBac(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateAllowBac(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertAllowBac(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ���� ���ޱ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAllowBac(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RUN_AMT")); 
        param.setValueParamter(i++, record.getAttribute("EVENT_AMT"));
        param.setValueParamter(i++, record.getAttribute("SAFY_AMT")); 
        param.setValueParamter(i++, record.getAttribute("WAIT_AMT_RATE")); 
        param.setValueParamter(i++, record.getAttribute("STR_AMT")); 
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_AMT_RATE")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        return this.getDao("boadao").update("tbeg_allow_bac_uf001", param);
    }
    
    

    /**
     * <p>���� ���ޱ��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertAllowBac(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
        param.setValueParamter(i++, record.getAttribute("RUN_AMT"));  
        param.setValueParamter(i++, record.getAttribute("EVENT_AMT"));
        param.setValueParamter(i++, record.getAttribute("SAFY_AMT"));  
        param.setValueParamter(i++, record.getAttribute("WAIT_AMT_RATE")); 
        param.setValueParamter(i++, record.getAttribute("STR_AMT"));  
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_AMT_RATE")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_allow_bac_if001", param);
    }
}
