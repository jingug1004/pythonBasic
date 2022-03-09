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
package snis.boa.equipment.e06010050.activity;


import java.util.HashMap;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* ���� ��Ʈ Ȯ���˻� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveEquipInsp extends SnisActivity
{    
    public SaveEquipInsp()
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
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        saveState(ctx);
        //�� ��Ȳ ����
        saveDetailState(ctx);
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
        
        ds    = (PosDataset) ctx.get("dsOutEquipInsp");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteEquipInsp(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipInsp(record);
            }
        }

        String inspPrsStatCd = (String)ctx.get("INSP_PRS_STAT_CD");
        String orgInspPrsStatCd = (String)ctx.get("ORG_INSP_PRS_STAT_CD");
        
    	//���μ��� ���� ��Ȳ �α�(���Ȯ���˻�-�Ϸ�/���)
        if(!orgInspPrsStatCd.equals(inspPrsStatCd)) {
        	if(Util.trim(inspPrsStatCd).equals("002")){	// ���Ȯ���˻� Ȯ��
        		HashMap hmProcess = new HashMap();
        		hmProcess.put("STND_YEAR", (String)ctx.get("STND_YEAR"));
        		hmProcess.put("MBR_CD"   , (String)ctx.get("MBR_CD"));
        		hmProcess.put("TMS"      , (String)ctx.get("TMS"));
        		hmProcess.put("DAY_ORD"  , (String)ctx.get("DAY_ORD"));
        		hmProcess.put("DUTY_CD"  , "006");
        		hmProcess.put("WORK_CD"  , "030");
        		hmProcess.put("PROG_STAT", "001");
        		hmProcess.put("USER_ID",   SESSION_USER_ID);
        		
        		SaveProcess sp = new SaveProcess();
        		sp.saveProcess(hmProcess);		
        	} else if(Util.trim(inspPrsStatCd).equals("001")){	// ���Ȯ���˻� Ȯ�� ���
        		HashMap hmProcess = new HashMap();
        		hmProcess.put("STND_YEAR", (String)ctx.get("STND_YEAR"));
        		hmProcess.put("MBR_CD"   , (String)ctx.get("MBR_CD"));
        		hmProcess.put("TMS"      , (String)ctx.get("TMS"));
        		hmProcess.put("DAY_ORD"  , (String)ctx.get("DAY_ORD"));
        		hmProcess.put("DUTY_CD"  , "006");
        		hmProcess.put("WORK_CD"  , "030");
        		hmProcess.put("PROG_STAT", "002");
        		hmProcess.put("USER_ID",   SESSION_USER_ID);
	
        		SaveProcess sp = new SaveProcess();
        		sp.saveProcess(hmProcess);		
        	}
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
     protected void saveDetailState(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	//int nDeleteCount = 0; 

     	PosDataset ds;
         int nSize        = 0;
         
         ds    = (PosDataset) ctx.get("dsOutEquipInspDetail");
         nSize = ds.getRecordCount();

         for ( int i = 0; i < nSize; i++ ){
             PosRecord record = ds.getRecord(i);
             nSaveCount = nSaveCount + mergeEquipInspDetail(record);
         }

         Util.setSaveCount  (ctx, nSaveCount     );
         //Util.setDeleteCount(ctx, nDeleteCount   );
     }

     /**
     * <p> ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipInsp(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateEquipInsp(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertEquipInsp(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p>Ȯ���˻� ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEquipInsp(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_RACER_CNT")); 	
        param.setValueParamter(i++, record.getAttribute("ALTER_MOT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_BOAT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("INSP_RACER_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_MOT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_BOAT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_TOT_RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 

        int dmlCount = this.getDao("boadao").update("tbef_equip_insp_uf001", param);

        /* -- ����ÿ� ��� ��� ���μ����� �����Ǿ� �ּ� ó�� 2008.07.25, ���μ� saveState�� ���� �̵�
        //���μ��� ���� ��Ȳ �α�(���Ȯ���˻�-�Ϸ�/���)
        if(Util.trim(record.getAttribute("INSP_PRS_STAT_CD")).equals("002")){
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
	        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
	        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "006");
	        hmProcess.put("WORK_CD"  , "030");
	        hmProcess.put("PROG_STAT", "001");
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);		
        } else if(Util.trim(record.getAttribute("INSP_PRS_STAT_CD")).equals("001")){
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
	        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
	        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "006");
	        hmProcess.put("WORK_CD"  , "030");
	        hmProcess.put("PROG_STAT", "002");
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);		
        }
        */
        
        return dmlCount;
    }

    /**
     * <p>Ȯ���˻� ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertEquipInsp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_RACER_CNT"));  
        param.setValueParamter(i++, record.getAttribute("ALTER_MOT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_BOAT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("INSP_RACER_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_MOT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_BOAT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_TOT_RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlCount =  this.getDao("boadao").update("tbef_equip_insp_if001", param);
        
        /* -- ����ÿ� ��� ��� ���μ����� �����Ǿ� �ּ� ó�� 2008.07.25, ���μ� saveState�� ���� �̵�
        //���μ��� ���� ��Ȳ �α�(���Ȯ���˻�-�Ϸ�)
        if(Util.trim(record.getAttribute("INSP_PRS_STAT_CD")).equals("002")){
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
	        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
	        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "006");
	        hmProcess.put("WORK_CD"  , "030");
	        hmProcess.put("PROG_STAT", "001");
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);		
        }
        */
        
        return dmlCount;
    }

    /**
     * <p>Ȯ���˻� ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteEquipInsp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        
        int dmlCount =  this.getDao("boadao").update("tbef_equip_insp_df001", param);

        /* -- ����ÿ� ��� ��� ���μ����� �����Ǿ� �ּ� ó�� 2008.07.25, ���μ� saveState�� ���� �̵�
        //���μ��� ���� ��Ȳ �α�(���Ȯ���˻�-���)
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
        hmProcess.put("DUTY_CD"  , "006");
        hmProcess.put("WORK_CD"  , "030");
        hmProcess.put("PROG_STAT", "002");

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);		
        */
        
        return dmlCount;
    }

    /**
     * <p>Ȯ���˻� ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeEquipInspDetail(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 	
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        param.setValueParamter(i++, record.getAttribute("MOT_NO")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_NO")); 
        param.setValueParamter(i++, record.getAttribute("MOT_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("RACER_INSP_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("MOT_INSP_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_INSP_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS")); 	
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 

        int dmlCount = this.getDao("boadao").update("tbef_equip_insp_detail_mf001", param);
        
        return dmlCount;
    }
   
}
