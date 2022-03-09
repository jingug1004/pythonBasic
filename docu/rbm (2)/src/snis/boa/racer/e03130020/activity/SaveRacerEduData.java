/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03130020.activity.SaveRacerEduData.java
 * ���ϼ���		: ���������ڷ�
 * �ۼ���		: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-06-04
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03130020.activity;


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
public class SaveRacerEduData extends SnisActivity
{    
    public SaveRacerEduData()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerEduData");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){            	
            	nDeleteCount = nDeleteCount + deleteRacerEduData(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
            {
            	nSaveCount = nSaveCount + updateRacerEduData(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + insertRacerEduData(record);
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
    protected int saveRacerEduData(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRacerEduData(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertRacerEduData(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �λ󼱼�  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerEduData(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, record.getAttribute("CNTNT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
        
        return this.getDao("boadao").update("tbec_racer_edu_data_uc001", param);
    }

    /**
     * <p>�λ󼱼�����  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerEduData(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
        param.setValueParamter(i++, record.getAttribute("CNTNT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbec_racer_edu_data_ic001", param);
    }

    /**
     * <p>�λ󼱼� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerEduData(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
        
        return  this.getDao("boadao").update("tbec_racer_edu_data_dc001", param);
    }
}