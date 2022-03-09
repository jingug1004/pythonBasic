/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03050010.activity.SaveExerciseOrganization.java
 * ���ϼ���		: �������������� ��
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2008-01-05
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03050010.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ּ����ܼ��������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveExerciseOrganization extends SnisActivity
{    
    public SaveExerciseOrganization()
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
        
        ds    = (PosDataset) ctx.get("dsOutAppoExercOrgan");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
        	nSaveCount = nSaveCount + saveAppoExercOrgan(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );

    
        // �������� ��� ���� ����
        ds    = (PosDataset)ctx.get("dsOutAppoExercRslt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveAppoExercOrganRslt(record);
            }
        }        
        
        if ( ctx.get("WORK_CD") != null && ctx.get("WORK_CD").equals("025") ) {
	        // �۾��α� �ۼ�
	        //==============================================
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
	        hmProcess.put("TMS"      , ctx.get("TMS"      ));
	        hmProcess.put("DAY_ORD"  , ctx.get("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "003");
	        hmProcess.put("WORK_CD"  , "025");
	        hmProcess.put("PROG_STAT", "001");
            hmProcess.put("USER_ID",   SESSION_USER_ID);
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);
	        //==============================================
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );   
    }

    /**
     * <p> ���������� ����  Save </p>
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
     * <p> ����������� ���� ����  Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveAppoExercOrganRslt(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertExerciseRslt(record);
        return effectedRowCnt;
    }
        
    /**
     * <p>���������� ����  �Է�</p>
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
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
        
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO"));
        
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		
		param.setValueParamter(i++, record.getAttribute("RMK_0")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
        
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3"));
		
		param.setValueParamter(i++, record.getAttribute("START_RMK"));
		param.setValueParamter(i++, record.getAttribute("START_RMK_2R"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NO"));
        
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO"));
        
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		param.setValueParamter(i++, record.getAttribute("START_TM_1"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		param.setValueParamter(i++, record.getAttribute("START_TM_2"));
		
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		param.setValueParamter(i++, record.getAttribute("START_TM_3"));
		
		param.setValueParamter(i++, record.getAttribute("RMK_0"));
		param.setValueParamter(i++, record.getAttribute("RMK"));
		
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2_0"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3_0"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3_0"));
		
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_1"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_2"));
		param.setValueParamter(i++, record.getAttribute("START_FLYING_CNT_3"));
		param.setValueParamter(i++, record.getAttribute("START_LATE_CNT_3"));
		
		param.setValueParamter(i++, record.getAttribute("START_RMK"));
		param.setValueParamter(i++, record.getAttribute("START_RMK_2R"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbec_appo_exerc_organ_ic001", param); 
    }
    
    /**
     * <p>���������� ����  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertExerciseRslt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("NOTICE")));
        param.setValueParamter(i++, SESSION_USER_ID );        	
    	param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("TMS")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT")));	
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CNTNT1")));	
        param.setValueParamter(i++, Util.trim(record.getAttribute("NOTICE")));
    	param.setValueParamter(i++, SESSION_USER_ID );
    	param.setValueParamter(i++, SESSION_USER_ID );
    	
 		//�������� ��� ���� �Է�
    	return this.getDao("boadao").update("tbec_appo_exerc_organ_ic002", param);
    }
}