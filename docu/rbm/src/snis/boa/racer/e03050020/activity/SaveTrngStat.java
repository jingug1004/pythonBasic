/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03050020.activity.SaveExerciseOrganization.java
 * ���ϼ���		: �������� �Ʒû��� ���� ��
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2008-04-22
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03050020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ּ����ܼ��������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveTrngStat extends SnisActivity
{    
    public SaveTrngStat()
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
        
        ds    = (PosDataset) ctx.get("dsOutTrngStat");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
        	nSaveCount = nSaveCount + saveAppoExercOrgan(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );   
    }

    /**
     * <p> �������� �Ʒû���  ����  Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveAppoExercOrgan(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertExercise(record);
        return effectedRowCnt;
    }

    /**
     * <p>�������� �Ʒû��� ����  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertExercise(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        String sRacerNo	= "";
        if (record.getAttribute("RACER_NO") == null || record.getAttribute("RACER_NO").equals("")){
        	sRacerNo	= " ";
        }else{
        	sRacerNo	= (String)record.getAttribute("RACER_NO");
        }
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_LINE_ACCE_CD"));
        param.setValueParamter(i++, record.getAttribute("MOT_TURN_CD"));
        param.setValueParamter(i++, record.getAttribute("START_SENSE_CD"));
        param.setValueParamter(i++, record.getAttribute("PROP_REPR_CD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
		param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_LINE_ACCE_CD"));
        param.setValueParamter(i++, record.getAttribute("MOT_TURN_CD"));
        param.setValueParamter(i++, record.getAttribute("START_SENSE_CD"));
        param.setValueParamter(i++, record.getAttribute("PROP_REPR_CD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbec_appo_exerc_trng_stat_uc001", param); 
    }
}