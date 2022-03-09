/*================================================================================
 * �ý���			: �ý��� ���� 
 * �ҽ����� �̸�	: snis.boa.system.e01010130.activity.SaveFixDivRate.java
 * ���ϼ���		: �½ĺ� Ȯ������ ���
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-04-24
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010130.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �½ĺ� Ȯ������ ��� ���� ���� ���, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveFixDivRate extends SnisActivity
{    
    public SaveFixDivRate()
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
        
        ds    = (PosDataset) ctx.get("dsOutFixDivRate");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteFixDivRate(record);
	            	break;
            }
        }
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertFixDivRate(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateFixDivRate(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �½ĺ� Ȯ������ ��� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveFixDivRate(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateFixDivRate(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertFixDivRate(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �½ĺ� Ȯ������ ��� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateFixDivRate(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        param.setValueParamter(i++, record.getAttribute("PAYOFF"));
        param.setValueParamter(i++, record.getAttribute("STATUS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RS_SEQ"));
        
        return this.getDao("boadao").update("tbes_sdl_rs_ue001", param);
    }

    /**
     * <p>�½ĺ� Ȯ������ ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertFixDivRate(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD")); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RS_SEQ"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        param.setValueParamter(i++, record.getAttribute("PAYOFF"));
        param.setValueParamter(i++, record.getAttribute("STATUS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbes_sdl_rs_ie001", param);
    }

    /**
     * <p>�½ĺ� Ȯ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteFixDivRate(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RS_SEQ"));
        
        return  this.getDao("boadao").update("tbes_sdl_rs_de001", param);
    }
}
