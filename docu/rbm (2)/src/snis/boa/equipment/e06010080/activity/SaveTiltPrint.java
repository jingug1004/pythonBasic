/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010010.activity.SaveRunInspPrint.java
 * ���ϼ���		: ���ְ˻�ǥ ��� ���� ����
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-01
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010080.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* ���ְ˻�ǥ ��� ���� ����
* @auther �輺�� 
* @version 1.0
*/
public class SaveTiltPrint extends SnisActivity
{    
    public SaveTiltPrint()
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
        
        ds    = (PosDataset) ctx.get("dsOutRaceIms");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            nSaveCount = nSaveCount + saveEquipReprHis(record);
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    /**
     * <p> ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipReprHis(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = mergeRunInspPrint(record);
    	
        return effectedRowCnt;
    }

    /**
     * <p> ���� ��Ʈ ��÷ ���   Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeRunInspPrint(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
    	param.setValueParamter(i++, record.getAttribute("TMS")); 
    	param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
    	param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("TILT_PRINT_YN"));  
        param.setValueParamter(i++, SESSION_USER_ID);
            
        return this.getDao("boadao").update("tbef_run_insp_print_mf001", param);
    }
    
}
