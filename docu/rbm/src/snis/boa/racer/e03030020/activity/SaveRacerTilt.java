/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03030020.activity.SaveRacerTilt.java
 * ���ϼ���		: ���ּ��� ƿƮ�� ���
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-12-21
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03030020.activity;

import java.math.BigDecimal;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ���ּ��� ƿƮ�������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveRacerTilt extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerTilt()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerTilt");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveRacerTilt(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���ּ��� ƿƮ������  Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerTilt(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertRacerTilt(record);
        return effectedRowCnt;
    }

    /**
     * <p>���ּ��� ƿƮ������  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerTilt(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO"))); 
        param.setValueParamter(i++, Util.trim(record.getAttribute("TILT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_ADD_WGHT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("JACKET_ADD_WGHT_CD")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));   
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TILT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_ADD_WGHT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("JACKET_ADD_WGHT_CD")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbec_racer_tilt_ic001", param);
    }         
}