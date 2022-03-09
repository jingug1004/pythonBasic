/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02010030.activity.SaveRaceTms.java
 * ���ϼ���		: ���ֵ��
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02010030.activity;

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
* �����Ͽ� �����ڵ带 ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRaceTms extends SnisActivity
{    
	
    public SaveRaceTms()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutRaceTms";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // ����ȸ�� ����
        sDsName = "dsOutRaceTms";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
            		nDeleteCount = nDeleteCount + deleteRaceTms(record);
	            }
	        }
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	            	if ( (nTempCnt = updateRaceTms(record)) == 0 ) {
	                	nTempCnt = insertRaceTms(record);
	                }
                	insertRace(record);
                	insertRaceDayOrd(record);

                	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }

        // ����ȸ�� ����
        sDsName = "dsOutRaceDayOrd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	            	nTempCnt = updateRaceDayOrd(record, ctx);

                	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ����ȸ�� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceTms(PosRecord record)
    {
    	deleteRace2(record);
    	deleteRaceDayOrd2(record);

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS_NM              ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("QURT_CD             ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MONTH               ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT              ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT              ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD            ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ARRANGE_TPE_CD      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK                 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_TMS_STAT_CD    ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIZ_RACE_GRP_CD		".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIZ_DAY_ORD			".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIZ_RACE_NO		".trim())));

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD   ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS      ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> ����ȸ�� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRaceTms(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS           ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS_NM        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("QURT_CD       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MONTH         ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ARRANGE_TPE_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK           ".trim())));
        param.setValueParamter(i++, "001");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIZ_RACE_GRP_CD	".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIZ_DAY_ORD		".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIZ_RACE_NO	".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ib001", param);
        
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
        hmProcess.put("DUTY_CD"  , "002");
        hmProcess.put("WORK_CD"  , "001");
        hmProcess.put("PROG_STAT", "001");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        
        return dmlcount;
    }

    /**
     * <p> ����ȸ�� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRaceTms(PosRecord record) 
    {
    	deleteRace(record);
    	deleteRaceDayOrd(record);
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_db001", param);

        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
        hmProcess.put("DUTY_CD"  , "002");
        hmProcess.put("WORK_CD"  , "001");
        hmProcess.put("PROG_STAT", "002");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);

        
        return dmlcount;
    }
    
    /**
     * <p> ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceDayOrd(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DAY              ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(ctx   .get         ("SAVE_TMS            ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD             ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_race_day_ord_ub002", param);
        return dmlcount;
    }

    /**
     * <p> ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRaceDayOrd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT              ".trim())));
        param.setValueParamter(i++, "001");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD           ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_race_day_ord_ib001", param);
        return dmlcount;
    }

    /**
     * <p> ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRaceDayOrd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD             ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_race_day_ord_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> ȸ�� ������ ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRaceDayOrd2(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_race_day_ord_db002", param);
        return dmlcount;
    }
    
    /**
     * <p> ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRace(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD           ".trim())));
        
        int dmlcount = this.getDao("boadao").insert("tbeb_race_ib001", param);
        return dmlcount;
    }

    /**
     * <p> ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRace(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD             ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO             ".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbeb_race_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> ȸ�� ������ ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRace2(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD              ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TMS                 ".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbeb_race_db002", param);
        return dmlcount;
    }
}
