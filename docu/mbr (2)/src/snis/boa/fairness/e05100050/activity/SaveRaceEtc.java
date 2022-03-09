/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.boa.fairness.e05100020.activity.SaveRaceAnal.java
 * ���ϼ���		: ���ֺм�������
 * �ۼ���			: ����ȭ
 * ����			: 1.0.0
 * ��������		: 2011-12-3
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05100050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
*/
public class SaveRaceEtc extends SnisActivity
{   	
    public SaveRaceEtc()
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

        ds    = (PosDataset) ctx.get("dsOutRaceAnal");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	// MERGE
            	nSaveCount = nSaveCount + saveRaceEtc(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> ���� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    protected int saveRaceEtc(PosRecord record)
    {
    	int dmlcount   = 0; 
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MEMO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ETC")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        	
        dmlcount = this.getDao("boadao").update("tbee_risk_mng_ie501", param);
        
        return dmlcount;  
    }
}
