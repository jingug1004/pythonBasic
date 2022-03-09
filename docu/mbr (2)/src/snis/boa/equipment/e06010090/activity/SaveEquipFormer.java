/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010010.activity.SaveEquipInsp.java
 * ���ϼ���		: ���ͺ�Ʈ Ȯ���˻� ����� �����Ѵ�. 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010090.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* ���� ���� Ȯ��Ÿ���� ���, ����, ���� �Ѵ�.
* @auther ����ȭ 
* @version 1.0
*/
public class SaveEquipFormer extends SnisActivity
{    
    public SaveEquipFormer()
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
    	//����� ���� Ȯ��
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutEquipFormer");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            nSaveCount = nSaveCount + saveEquipFormer(record);
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    /**
     * <p> ���� �Ұ�����Ÿ�� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipFormer(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = mergeEquipFormer(record);
    	
        return effectedRowCnt;
    }

    /**
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeEquipFormer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
    	param.setValueParamter(i++, record.getAttribute("TMS")); 
    	param.setValueParamter(i++, record.getAttribute("RACE_DAY")); 
    	param.setValueParamter(i++, record.getAttribute("MOT_NO")); 
    	param.setValueParamter(i++, record.getAttribute("INTRO_RUN_TM"));  
    	param.setValueParamter(i++, SESSION_USER_ID);
         
        return this.getDao("boadao").update("tbef_equip_former_mf001", param);
    }

    
}
