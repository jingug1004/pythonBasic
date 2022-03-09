/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010130.activity.SaveExpendableChg.java
 * ���ϼ���		: ��� ���� 
 * ����			: 1.0.0
 * ��������		: 2011-06-30
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010130.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �ش�ȸ�� ���ָ��Ϳ� ���� �Ҹ𼺺�ǰ ��ü������ ���, ����, ���� �Ѵ�.
* @version 1.0
*/
public class SaveExpendableChg extends SnisActivity
{    
    public SaveExpendableChg()
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
        
        ds    = (PosDataset) ctx.get("dsOutExpendableChg");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteExpendableChg(record);
            }else{
            	nSaveCount = nSaveCount + saveExpendableChg(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveExpendableChg(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = mergeExpendableChg(record);
        return effectedRowCnt;
    }

    

    /**
     * <p>��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergeExpendableChg(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE1_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE2_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE3_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE4_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE5_YN"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbef_expendable_chg_mf001", param);
    }

    /**
     * <p>��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteExpendableChg(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        
        return  this.getDao("boadao").update("tbef_expendable_chg_df001", param);
    }
   
}
