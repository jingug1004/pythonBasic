/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040015.activity.SaveCnfInsp.java
 * ���ϼ���		: Ȯ���˻��ϵ��
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040015.activity;

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
* �����Ͽ� Ȯ���˻��ϸ� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveCnfInsp extends SnisActivity
{    
    public SaveCnfInsp()
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
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsOutCnfInsp");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
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
        int nTempCnt     = 0;

        // Ȯ���˻�������
        ds    = (PosDataset) ctx.get("dsOutCnfInsp");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateCnfInsp(record, ctx)) == 0 ) {
                	nTempCnt = insertCnfInsp(record, ctx);
                }

            	nSaveCount = nSaveCount + nTempCnt;
            }
        }

        updateRaceTms(ctx);
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> Ȯ���˻��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCnfInsp(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("WIND_SPD    ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("HUMIDITY    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_STR_DT ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_END_DT ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DESC   ".trim()));
        param.setValueParamter(i++, record.getAttribute("SPEC_INFO   ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK         ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_RMK   ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("STND_YEAR   ".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("MBR_CD      ".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("TMS         ".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_cnf_insp_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> Ȯ���˻��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCnfInsp(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("STND_YEAR   ".trim()));
        param.setValueParamter(i++, ctx   .get         ("MBR_CD      ".trim()));
        param.setValueParamter(i++, ctx   .get         ("TMS         ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_NO    ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD    ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("HUMIDITY    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_STR_DT ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_END_DT ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DESC   ".trim()));
        param.setValueParamter(i++, record.getAttribute("SPEC_INFO   ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK         ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_RMK   ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_cnf_insp_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> Ȯ���˻��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx) 
    {
    	if ( ctx.get("CNF_INSP_HOME_REG_YN") == null || !ctx.get("CNF_INSP_HOME_REG_YN").equals("Y")) { 
    		return 0;
    	}

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("CNF_INSP_HOME_REG_YN   ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        
        i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("STND_YEAR   ".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("MBR_CD      ".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("TMS         ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub004", param);
        
        // �۾��α� �ۼ�
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DUTY_CD"  , "002");
        hmProcess.put("WORK_CD"  , "035");
        hmProcess.put("PROG_STAT", "001");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
        
        return dmlcount;
    }
}
