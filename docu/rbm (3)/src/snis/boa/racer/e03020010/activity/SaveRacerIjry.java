/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03020010.activity.SaveRacerIjry.java
 * ���ϼ���		: �λ󼱼����
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-12-09
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03020010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �λ󼱼������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveRacerIjry extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerIjry()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerIjry");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){            	
            	nDeleteCount = nDeleteCount + deleteRacerIjry(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
            {
            	nSaveCount = nSaveCount + updateRacerIjry(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + insertRacerIjry(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �λ󼱼� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerIjry(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRacerIjry(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertRacerIjry(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �λ󼱼�  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerIjry(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, record.getAttribute("IJRY_DT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_DT"));
        param.setValueParamter(i++, record.getAttribute("IJRY_PART"));
        param.setValueParamter(i++, record.getAttribute("IJRY_STAT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_WK"));
        param.setValueParamter(i++, record.getAttribute("EHOSP_DT"));
        param.setValueParamter(i++, record.getAttribute("RESIGN_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        return this.getDao("boadao").update("tbec_racer_injury_uc001", param);
    }

    /**
     * <p>�λ󼱼�����  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerIjry(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, String.valueOf(record.getAttribute("IJRY_DT")).substring(0,4));
        param.setValueParamter(i++, String.valueOf(record.getAttribute("IJRY_DT")).substring(0,4));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("IJRY_DT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_DT"));
        param.setValueParamter(i++, record.getAttribute("IJRY_PART"));
        param.setValueParamter(i++, record.getAttribute("IJRY_STAT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_WK"));
        param.setValueParamter(i++, record.getAttribute("EHOSP_DT"));
        param.setValueParamter(i++, record.getAttribute("RESIGN_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbec_racer_injury_ic001", param);
    }

    /**
     * <p>�λ󼱼� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerIjry(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        return  this.getDao("boadao").update("tbec_racer_injury_dc001", param);
    }
}