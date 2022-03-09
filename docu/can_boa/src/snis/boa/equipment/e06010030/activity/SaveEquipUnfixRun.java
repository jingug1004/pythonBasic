/*================================================================================
 * �ý���			: ������ ����  ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010010.activity.SaveEquipUnfixRun.java
 * ���ϼ���		: ��� ���� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010030.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ������ ���ֿ� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveEquipUnfixRun extends SnisActivity
{    
    public SaveEquipUnfixRun()
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
        
        ds    = (PosDataset) ctx.get("dsOutEquipUnfixRun");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteEquipUnfixRun(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipUnfixRun(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ������ ���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipUnfixRun(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateEquipUnfixRun(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertEquipUnfixRun(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ������ ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEquipUnfixRun(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RUN_DT")); 
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("MOT_REG_NO")); 
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_REG_NO")); 
        
        param.setValueParamter(i++, record.getAttribute("RUN_CNT")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        return this.getDao("boadao").update("tbef_equip_unfix_run_uf001", param);
    }

    /**
     * <p>������ ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertEquipUnfixRun(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RUN_DT")); 
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("MOT_REG_NO")); 
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_REG_NO")); 
        
        param.setValueParamter(i++, record.getAttribute("RUN_CNT")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbef_equip_unfix_run_if001", param);
    }

    /**
     * <p>������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteEquipUnfixRun(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        
        return  this.getDao("boadao").update("tbef_equip_unfix_run_df001", param);
    }
   
}
