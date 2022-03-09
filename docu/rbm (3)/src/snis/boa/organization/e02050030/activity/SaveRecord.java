/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02050030.activity.SaveRecord.java
 * ���ϼ���		: ȸ�����������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02050030.activity;

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
* �����Ͽ� ȸ������������ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRecord extends SnisActivity
{    
	private String sStndYear = "";
	private String sMbrCd    = "";
	private String sTms      = "";
	
    public SaveRecord()
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
        
        sDsName = "dsOutRacerRecd";
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

    	PosDataset ds = null;
        int nSize     = 0;
        int nTempCnt  = 0;

        // �������� ����
        sDsName = "dsOutRacerRecd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getAttribute("CHK") == null ) continue;
	            if ( record.getAttribute("CHK").equals("") ) continue;
	            
            	if ( (nTempCnt = updateRacerRecdAccuSum(record, ctx, "000")) == 0 ) {
                	nTempCnt = insertRacerRecdAccuSum(record, ctx, "000");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        // �������� ����(�ö���)
        sDsName = "dsOutRacerRecdFly";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getAttribute("CHK") == null ) continue;
	            if ( record.getAttribute("CHK").equals("") ) continue;
	            
            	if ( (nTempCnt = updateRacerRecdAccuSum(record, ctx, "002")) == 0 ) {
                	nTempCnt = insertRacerRecdAccuSum(record, ctx, "002");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        // �������� ����(�¶���)
        sDsName = "dsOutRacerRecdOnl";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getAttribute("CHK") == null ) continue;
	            if ( record.getAttribute("CHK").equals("") ) continue;
	            
            	if ( (nTempCnt = updateRacerRecdAccuSum(record, ctx, "001")) == 0 ) {
                	nTempCnt = insertRacerRecdAccuSum(record, ctx, "001");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        // ���ͼ��� ����
        sDsName = "dsOutMotRecd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
            	if ( (nTempCnt = updateMotRecdAccuSum(record, ctx, "000")) == 0 ) {
                	nTempCnt = insertMotRecdAccuSum(record, ctx, "000");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        // ���ͼ��� ����(�ö���)
        sDsName = "dsOutMotRecdFly";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
            	if ( (nTempCnt = updateMotRecdAccuSum(record, ctx, "002")) == 0 ) {
                	nTempCnt = insertMotRecdAccuSum(record, ctx, "002");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        // ���ͼ��� ����(�¶���)
        sDsName = "dsOutMotRecdOnl";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
            	if ( (nTempCnt = updateMotRecdAccuSum(record, ctx, "001")) == 0 ) {
                	nTempCnt = insertMotRecdAccuSum(record, ctx, "001");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        // ��Ʈ���� ����
        sDsName = "dsOutBoatRecd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
            	if ( (nTempCnt = updateBoatRecdAccuSum(record, ctx, "000")) == 0 ) {
                	nTempCnt = insertBoatRecdAccuSum(record, ctx, "000");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        // ��Ʈ���� ����(�ö���)
        sDsName = "dsOutBoatRecdFly";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
            	if ( (nTempCnt = updateBoatRecdAccuSum(record, ctx, "002")) == 0 ) {
                	nTempCnt = insertBoatRecdAccuSum(record, ctx, "002");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        // ��Ʈ���� ����(�¶���)
        sDsName = "dsOutBoatRecdOnl";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
            	if ( (nTempCnt = updateBoatRecdAccuSum(record, ctx, "001")) == 0 ) {
                	nTempCnt = insertBoatRecdAccuSum(record, ctx, "001");
                }
	            
            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

/*
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
        
        sDsName = "dsOutRacerRecd";
        ds    = (PosDataset) ctx.get(sDsName);

        deleteRacerRecdAccuSum(ds);
        deleteMotRecdAccuSum  (ds);
        deleteBoatRecdAccuSum (ds);
        
        // ����
    	nTempCnt = insertRacerRecdAccuSum(ds);
    	nTempCnt = insertMotRecdAccuSum  (ds);
    	nTempCnt = insertBoatRecdAccuSum (ds);
*/
        updateRacerRaceAllocEnd(ctx);
    	updateRaceTms(ctx);
    	nSaveCount = nSaveCount + nTempCnt;

    	Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �������ռ������ �Է� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerRecdAccuSum(PosRecord record, PosContext ctx, String sStMthdCd)
    {
        PosParameter param = new PosParameter();

        int i = 0;

        if("".equals(record.getAttribute("STND_YEAR")) ||  record.getAttribute("STND_YEAR") == null) {
        	param.setValueParamter(i++, ctx.get("STND_YEAR"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        }
        if("".equals(record.getAttribute("MBR_CD")) || record.getAttribute("MBR_CD") == null) {
        	param.setValueParamter(i++, "000");
        } else {
        	param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        }
        if("".equals(record.getAttribute("TMS")) ||  record.getAttribute("TMS") == null) {
        	param.setValueParamter(i++, "0");
        } else {
        	param.setValueParamter(i++, record.getAttribute("TMS"));
        }
        if("".equals(record.getAttribute("APLY_DT")) || record.getAttribute("APLY_DT") == null) {
        	param.setValueParamter(i++, ctx.get("APLY_DT"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("APLY_DT"));
        }
        
        param.setValueParamter(i++, record.getAttribute("RACER_NO               ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_RANK             ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AMU_RANK_SCR     ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_RANK_SCR     ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AMU_ACDNT_SCR    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_ACDNT_SCR    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_SCR          ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_WIN_RATIO        ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_HIGH_RANK_RATIO  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_HIGH_3_RANK_RATIO".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_STRT_TM      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_8_RANK_ORD         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RACE_CNT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RANK_1           ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RANK_2           ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RANK_3           ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_RANK_SCR           ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_RANK_SCR           ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_ACDNT_SCR          ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_ACDNT_SCR          ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_AMU_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_AVG_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_AMU_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_AVG_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_SCR                ".trim()));
        param.setValueParamter(i++, record.getAttribute("GPP_SCR                ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIN_RATIO              ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATIO        ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_3_RANK_RATIO      ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_STRT_TM            ".trim()));
        param.setValueParamter(i++, record.getAttribute("RUN_CNT                ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("TOT_RACE_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_RACE_CNT            ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_RACE_CNT            ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_1_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_2_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_3_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_4_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_5_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_6_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_ETC_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("DGRE_JUDG_F_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("DGRE_JUDG_L_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_F_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_L_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_F_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_L_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("ABSE_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_ESC_1_CNT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_ESC_2_CNT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("FOUL_ELIM_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("ELIM_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("FOUL_WARN_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("WARN_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("ATTEN_CNT              ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_TMS                 ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_TMS              ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_BF_TMS           ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_MBR_CD              ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_MBR_CD           ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_BF_MBR_CD        ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sStMthdCd );
    	
        int dmlcount = this.getDao("boadao").update("tbeb_racer_recd_accu_sum_ib002", param);
    	return dmlcount;
    }

    /**
     * <p> �������ռ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerRecdAccuSum(PosRecord record, PosContext ctx, String sStMthdCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_RANK             ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AMU_RANK_SCR     ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_RANK_SCR     ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AMU_ACDNT_SCR    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_ACDNT_SCR    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_SCR          ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_WIN_RATIO        ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_HIGH_RANK_RATIO  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_HIGH_3_RANK_RATIO".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_AVG_STRT_TM      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_8_RANK_ORD         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RACE_CNT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RANK_1           ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RANK_2           ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_6_RANK_3           ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_RANK_SCR           ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_RANK_SCR           ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_ACDNT_SCR          ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_ACDNT_SCR          ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_AMU_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_AVG_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_AMU_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_AVG_ACDNT_SCR       ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_SCR                ".trim()));
        param.setValueParamter(i++, record.getAttribute("GPP_SCR                ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIN_RATIO              ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATIO        ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_3_RANK_RATIO      ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_STRT_TM            ".trim()));
        param.setValueParamter(i++, record.getAttribute("RUN_CNT                ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("TOT_RACE_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_RACE_CNT            ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_RACE_CNT            ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_1_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_2_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_3_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_4_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_5_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_6_CNT             ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_ETC_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("DGRE_JUDG_F_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("DGRE_JUDG_L_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_F_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_L_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_F_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("AF_L_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("ABSE_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_ESC_1_CNT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_ESC_2_CNT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("FOUL_ELIM_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("ELIM_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("FOUL_WARN_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("WARN_CNT               ".trim()));
        param.setValueParamter(i++, record.getAttribute("ATTEN_CNT              ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_TMS                 ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_TMS              ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_BF_TMS           ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_MBR_CD              ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_MBR_CD           ".trim()));
        param.setValueParamter(i++, record.getAttribute("BF_BF_BF_MBR_CD        ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sStMthdCd );
    	
        i = 0;
        if("".equals(record.getAttribute("STND_YEAR")) || record.getAttribute("STND_YEAR") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        }
        if("".equals(record.getAttribute("MBR_CD")) || record.getAttribute("MBR_CD") == null) {
        	param.setWhereClauseParameter(i++, "000");
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
        }
        if("".equals(record.getAttribute("TMS")) || record.getAttribute("TMS") == null) {
        	param.setWhereClauseParameter(i++, "0");
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        }
        if("".equals(record.getAttribute("APLY_DT")) || record.getAttribute("APLY_DT") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("APLY_DT"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("APLY_DT"));
        }
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO               ".trim()));
        param.setWhereClauseParameter(i++, sStMthdCd );
        
        int dmlcount = this.getDao("boadao").update("tbeb_racer_recd_accu_sum_ub002", param);
    	return dmlcount;
    }

    /**
     * <p> �������ռ������ �Է� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertMotRecdAccuSum(PosRecord record, PosContext ctx, String sStMthdCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        if("".equals(record.getAttribute("STND_YEAR")) ||  record.getAttribute("STND_YEAR") == null) {
        	param.setValueParamter(i++, ctx.get("STND_YEAR"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        }
        if("".equals(record.getAttribute("MBR_CD")) || record.getAttribute("MBR_CD") == null) {
        	param.setValueParamter(i++, ctx.get("MBR_CD"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        }
        if("".equals(record.getAttribute("TMS")) || record.getAttribute("TMS")==null) {
        	param.setValueParamter(i++, ctx.get("TMS"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("TMS"));
        }
        param.setValueParamter(i++, record.getAttribute("MOT_NO            ".trim()));
        param.setValueParamter(i++, record.getAttribute("RUN_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY_CNT      ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_1_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_2_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_3_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_4_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_5_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_6_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIN_RATIO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATIO   ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_3_RANK_RATIO ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_3_ITRDT_RUN_TM".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MAX_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MIN_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("ITRDT_RUN_TM_DVTN ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sStMthdCd );
        param.setValueParamter(i++, record.getAttribute("AVG_STRT_TM ".trim()));
    	
        int dmlcount = this.getDao("boadao").update("tbeb_mot_recd_accu_sum_ib001", param);
    	return dmlcount;
    }

    /**
     * <p> �������ռ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMotRecdAccuSum(PosRecord record, PosContext ctx, String sStMthdCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RUN_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY_CNT      ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_1_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_2_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_3_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_4_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_5_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_6_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIN_RATIO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATIO   ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_3_RANK_RATIO ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_3_ITRDT_RUN_TM".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MAX_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MIN_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("ITRDT_RUN_TM_DVTN ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sStMthdCd );
        param.setValueParamter(i++, record.getAttribute("AVG_STRT_TM ".trim()));
    	
        i = 0;
        if("".equals(record.getAttribute("STND_YEAR")) || record.getAttribute("STND_YEAR") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        }
        if("".equals(record.getAttribute("MBR_CD")) || record.getAttribute("MBR_CD") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
        }
        if("".equals(record.getAttribute("TMS")) || record.getAttribute("TMS") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("TMS"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        }
        param.setWhereClauseParameter(i++, record.getAttribute("MOT_NO            ".trim()));
        param.setWhereClauseParameter(i++, sStMthdCd );

        int dmlcount = this.getDao("boadao").update("tbeb_mot_recd_accu_sum_ub001", param);
    	return dmlcount;
    }


    /**
     * <p> ��Ʈ���ռ������ �Է� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertBoatRecdAccuSum(PosRecord record, PosContext ctx, String sStMthdCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        if("".equals(record.getAttribute("STND_YEAR")) || record.getAttribute("STND_YEAR") == null) {
        	param.setValueParamter(i++, ctx.get("STND_YEAR"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        }
        if("".equals(record.getAttribute("MBR_CD")) || record.getAttribute("MBR_CD") == null) {
        	param.setValueParamter(i++, ctx.get("MBR_CD"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        }
        if("".equals(record.getAttribute("TMS")) || record.getAttribute("TMS") == null) {
        	param.setValueParamter(i++, ctx.get("TMS"));
        } else {
        	param.setValueParamter(i++, record.getAttribute("TMS"));
        }
        param.setValueParamter(i++, record.getAttribute("BOAT_NO           ".trim()));
        param.setValueParamter(i++, record.getAttribute("RUN_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY_CNT      ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_1_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_2_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_3_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_4_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_5_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_6_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIN_RATIO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATIO   ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_3_RANK_RATIO ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_3_ITRDT_RUN_TM".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MAX_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MIN_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("ITRDT_RUN_TM_DVTN ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sStMthdCd );
    	
        int dmlcount = this.getDao("boadao").update("tbeb_boat_recd_accu_sum_ib001", param);
    	return dmlcount;
    }

    /**
     * <p> ��Ʈ���ռ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateBoatRecdAccuSum(PosRecord record, PosContext ctx, String sStMthdCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RUN_CNT           ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT          ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY_CNT      ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_1_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_2_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_3_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_4_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_5_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RANK_6_CNT        ".trim()));
        param.setValueParamter(i++, record.getAttribute("AMU_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_RANK_SCR      ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIN_RATIO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATIO   ".trim()));
        param.setValueParamter(i++, record.getAttribute("HIGH_3_RANK_RATIO ".trim()));
        param.setValueParamter(i++, record.getAttribute("TMS_3_ITRDT_RUN_TM".trim()));
        param.setValueParamter(i++, record.getAttribute("AVG_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MAX_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("MIN_ITRDT_RUN_TM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("ITRDT_RUN_TM_DVTN ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sStMthdCd );
    	
        i = 0;
        if("".equals(record.getAttribute("STND_YEAR")) ||  record.getAttribute("STND_YEAR") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        }
        if("".equals(record.getAttribute("MBR_CD")) || record.getAttribute("MBR_CD") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
        }
        if("".equals(record.getAttribute("TMS")) || record.getAttribute("TMS") == null) {
        	param.setWhereClauseParameter(i++, ctx.get("TMS"));
        } else {
        	param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        }
        param.setWhereClauseParameter(i++, record.getAttribute("BOAT_NO           ".trim()));
        param.setWhereClauseParameter(i++, sStMthdCd );

        int dmlcount = this.getDao("boadao").update("tbeb_boat_recd_accu_sum_ub001", param);
    	return dmlcount;
    }

    /**
     * <p> �������ռ������(MBR_CD='000', TMS='0', APLY_DT�� �ش�ȸ�������Ϸ� �����Ѵ�.) </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n INSERT INTO TBEB_RACER_RECD_ACCU_SUM                                                                                                                                                   ");
    	sbSql.append("\n (                                                                                                                                                                                      ");
    	sbSql.append("\n           STND_YEAR                       -- ���س⵵                                                                                                                                  ");
    	sbSql.append("\n         , MBR_CD                          -- �������ڵ�                                                                                                                                ");
    	sbSql.append("\n         , TMS                             -- ȸ��                                                                                                                                      ");
    	sbSql.append("\n         , RACER_NO                        -- ������Ϲ�ȣ                                                                                                                              ");
    	sbSql.append("\n         , APLY_DT                         -- ��������                                                                                                                                  ");
    	sbSql.append("\n         , TMS_6_AMU_RANK_SCR              -- 6ȸ����������                                                                                                                             ");
    	sbSql.append("\n         , TMS_6_AVG_RANK_SCR              -- 6ȸ���������                                                                                                                             ");
    	sbSql.append("\n         , TMS_6_AMU_ACDNT_SCR             -- 6ȸ���������                                                                                                                             ");
    	sbSql.append("\n         , TMS_6_AVG_ACDNT_SCR             -- 6ȸ���������                                                                                                                             ");
    	sbSql.append("\n         , TMS_6_AVG_SCR                   -- 6ȸ��յ���                                                                                                                               ");
    	sbSql.append("\n         , TMS_6_WIN_RATIO                 -- 6ȸ�·�                                                                                                                                   ");
    	sbSql.append("\n         , TMS_6_HIGH_RANK_RATIO           -- 6ȸ������                                                                                                                                 ");
    	sbSql.append("\n         , TMS_6_HIGH_3_RANK_RATIO         -- 6ȸ3������                                                                                                                                ");
    	sbSql.append("\n         , TMS_6_AVG_STRT_TM               -- 6ȸ���ST                                                                                                                                 ");
    	sbSql.append("\n         , TMS_8_RANK_ORD                  -- 8���� ����                                                                                                                                ");
    	sbSql.append("\n         , TMS_6_RACE_CNT                  -- 6ȸ����Ƚ��                                                                                                                               ");
    	sbSql.append("\n         , TMS_6_RANK_1                    -- 6ȸ1��Ƚ��                                                                                                                                ");
    	sbSql.append("\n         , TMS_6_RANK_2                    -- 6ȸ2��Ƚ��                                                                                                                                ");
    	sbSql.append("\n         , TMS_6_RANK_3                    -- 6ȸ3��Ƚ��                                                                                                                                ");
    	sbSql.append("\n         , AMU_RANK_SCR                    -- ����������                                                                                                                                ");
    	sbSql.append("\n         , AVG_RANK_SCR                    -- ���������                                                                                                                                ");
    	sbSql.append("\n         , AMU_ACDNT_SCR                   -- ���������                                                                                                                                ");
    	sbSql.append("\n         , AVG_ACDNT_SCR                   -- ��ջ����                                                                                                                                ");
        sbSql.append("\n         , BF_AMU_ACDNT_SCR                -- ���ݱ⴩�������                                                                                                                          ");
        sbSql.append("\n         , BF_AVG_ACDNT_SCR                -- ���ݱ���ջ����                                                                                                                          ");
        sbSql.append("\n         , AF_AMU_ACDNT_SCR                -- �Ĺݱ⴩�������                                                                                                                          ");
        sbSql.append("\n         , AF_AVG_ACDNT_SCR                -- �Ĺݱ���ջ����                                                                                                                          ");
    	sbSql.append("\n         , AVG_SCR                         -- ��������                                                                                                                                  ");
    	sbSql.append("\n         , WIN_RATIO                       -- �·�                                                                                                                                      ");
    	sbSql.append("\n         , HIGH_RANK_RATIO                 -- ������                                                                                                                                    ");
    	sbSql.append("\n         , HIGH_3_RANK_RATIO               -- 3������                                                                                                                                   ");
    	sbSql.append("\n         , AVG_STRT_TM                     -- ���ST                                                                                                                                    ");
    	sbSql.append("\n         , RUN_CNT                         -- ����Ƚ��                                                                                                                                  ");
    	sbSql.append("\n         , RACE_DAY_CNT                    -- �����ϼ�                                                                                                                                  ");
    	sbSql.append("\n         , RACE_CNT                        -- ����Ƚ��                                                                                                                                  ");
    	sbSql.append("\n         , TOT_RACE_CNT                    -- ������Ƚ��                                                                                                                                ");
    	sbSql.append("\n         , BF_RACE_CNT                     -- ���ݱ�����Ƚ��                                                                                                                            ");
    	sbSql.append("\n         , AF_RACE_CNT                     -- �Ĺݱ�����Ƚ��                                                                                                                            ");
    	sbSql.append("\n         , RANK_1_CNT                      -- 1��Ƚ��                                                                                                                                   ");
    	sbSql.append("\n         , RANK_2_CNT                      -- 2��Ƚ��                                                                                                                                   ");
    	sbSql.append("\n         , RANK_3_CNT                      -- 3��Ƚ��                                                                                                                                   ");
    	sbSql.append("\n         , RANK_4_CNT                      -- 4��Ƚ��                                                                                                                                   ");
    	sbSql.append("\n         , RANK_5_CNT                      -- 5��Ƚ��                                                                                                                                   ");
    	sbSql.append("\n         , RANK_6_CNT                      -- 6��Ƚ��                                                                                                                                   ");
    	sbSql.append("\n         , RANK_ETC_CNT                    -- ����Ƚ��                                                                                                                                  ");
    	sbSql.append("\n         , DGRE_JUDG_F_CNT                 -- ��޽ɻ� FȽ��                                                                                                                            ");
    	sbSql.append("\n         , DGRE_JUDG_L_CNT                 -- ����ɻ� LȽ��                                                                                                                            ");
    	sbSql.append("\n         , BF_F_CNT                        -- ���ݱ�FȽ��                                                                                                                               ");
    	sbSql.append("\n         , AF_F_CNT                        -- �Ĺݱ�FȽ��                                                                                                                               ");
    	sbSql.append("\n         , BF_L_CNT                        -- ���ݱ�LȽ��                                                                                                                               ");
    	sbSql.append("\n         , AF_L_CNT                        -- �Ĺݱ�LȽ��                                                                                                                               ");
    	sbSql.append("\n         , ABSE_CNT                        -- ����Ƚ��                                                                                                                                  ");
    	sbSql.append("\n         , RACE_ESC_1_CNT                  -- ��������1Ƚ��                                                                                                                             ");
    	sbSql.append("\n         , RACE_ESC_2_CNT                  -- ��������2Ƚ��                                                                                                                             ");
    	sbSql.append("\n         , FOUL_ELIM_CNT                   -- ��Ģ�ǰ�Ƚ��                                                                                                                              ");
    	sbSql.append("\n         , ELIM_CNT                        -- �ǰ�Ƚ��                                                                                                                                  ");
    	sbSql.append("\n         , FOUL_WARN_CNT                   -- ��Ģ���Ƚ��                                                                                                                              ");
    	sbSql.append("\n         , WARN_CNT                        -- ���Ƚ��                                                                                                                                  ");
    	sbSql.append("\n         , ATTEN_CNT                       -- ����Ƚ��                                                                                                                                  ");
    	sbSql.append("\n         , BF_TMS                          -- ��ȸ��                                                                                                                                    ");
    	sbSql.append("\n         , BF_BF_TMS                       -- ����ȸ��                                                                                                                                  ");
    	sbSql.append("\n         , BF_BF_BF_TMS                    -- ������ȸ��                                                                                                                                ");
    	sbSql.append("\n         , BF_MBR_CD                       -- ��������                                                                                                                                  ");
    	sbSql.append("\n         , BF_BF_MBR_CD                    -- ����������                                                                                                                                ");
    	sbSql.append("\n         , BF_BF_BF_MBR_CD                 -- ������������                                                                                                                              ");
    	sbSql.append("\n         , INST_ID                         -- �ۼ���ID                                                                                                                                  ");
    	sbSql.append("\n         , INST_DTM                        -- �ۼ��Ͻ�                                                                                                                                  ");
    	sbSql.append("\n         , UPDT_ID                         -- ������ID                                                                                                                                  ");
    	sbSql.append("\n         , UPDT_DTM                        -- �����Ͻ�                                                                                                                                  ");
    	sbSql.append("\n )                                                                                                                                                                                      ");
    	sbSql.append("\n SELECT                                                                                                                                                                                 ");
    	sbSql.append("\n           ?                                                                                                                                        -- ���س⵵                         ");
    	sbSql.append("\n         , ?                                                                                                                                        -- �������ڵ�                       ");
    	sbSql.append("\n         , ?                                                                                                                                        -- ȸ��                             ");
    	sbSql.append("\n         , TRM.RACER_NO                                                                                                                             -- ������Ϲ�ȣ                     ");
    	sbSql.append("\n         , (SELECT MAX(TRDO.RACE_DAY) RACE_DAY FROM TBEB_RACE_DAY_ORD TRDO WHERE TRDO.STND_YEAR = ? AND TRDO.MBR_CD = ? AND TRDO.TMS = ?)           -- ��������                         ");
    	sbSql.append("\n         , TMS_6_AMU_RANK_SCR                                                                                                                       -- 6ȸ����������                    ");
    	sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_RANK_SCR / TMS_6_RACE_CNT, 90.99))                                                                                -- 6ȸ���������                    ");
    	sbSql.append("\n         , TMS_6_AMU_ACDNT_SCR                                                                                                                      -- 6ȸ���������                    ");
    	sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_ACDNT_SCR / TMS_6_RACE_CNT, 90.99))                                                                               -- 6ȸ���������                    ");
    	sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_AMU_RANK_SCR - NVL(TMS_6_AMU_ACDNT_SCR, 0)) / TMS_6_RACE_CNT, 90.99))                                                -- 6ȸ��յ���                      ");
    	sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1) / TMS_6_RACE_CNT * 100, 990.9))                                                                              -- 6ȸ�·�                          ");
    	sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2) / TMS_6_RACE_CNT * 100, 990.9))                                                               -- 6ȸ������                        ");
    	sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2 + TMS_6_RANK_3) / TMS_6_RACE_CNT * 100, 990.9))                                                -- 6ȸ3������                       ");
    	sbSql.append("\n         , TMS_6_AVG_STAR_TM                                                                                                                        -- 6ȸ���ST                        ");
    	sbSql.append("\n         , TMS_8_RANK_1 || TMS_8_RANK_2 || TMS_8_RANK_3 || TMS_8_RANK_4 || TMS_8_RANK_5 || TMS_8_RANK_6 || TMS_8_RANK_7 || TMS_8_RANK_8             -- 8���� ����                       ");
    	sbSql.append("\n         , TMS_6_RACE_CNT                                                                                                                           -- 6ȸ����Ƚ��                      ");
    	sbSql.append("\n         , TMS_6_RANK_1                                                                                                                             -- 6ȸ1��Ƚ��                       ");
    	sbSql.append("\n         , TMS_6_RANK_2                                                                                                                             -- 6ȸ2��Ƚ��                       ");
    	sbSql.append("\n         , TMS_6_RANK_3                                                                                                                             -- 6ȸ3��Ƚ��                       ");
    	sbSql.append("\n         , AMU_RANK_SCR                                                                                                                             -- ����������                       ");
    	sbSql.append("\n         , TRIM(TO_CHAR(AMU_RANK_SCR / RACE_CNT, 90.99))                                                                                            -- ���������                       ");
    	sbSql.append("\n         , AMU_ACDNT_SCR                                                                                                                            -- ���������                       ");
    	sbSql.append("\n         , TRIM(TO_CHAR(AMU_ACDNT_SCR / RACE_CNT, 90.99))                                                                                           -- ��ջ����                       ");
        sbSql.append("\n         , BF_ACDNT_SCR                                                                                                                             -- ���ݱ�����                     ");
        sbSql.append("\n         , TRIM(TO_CHAR(BF_ACDNT_SCR / BF_RACE_CNT, 90.99))                                                                                           -- ��ջ����                       ");
        sbSql.append("\n         , AF_ACDNT_SCR                                                                                                                             -- �Ĺݱ�����                     ");
        sbSql.append("\n         , TRIM(TO_CHAR(AF_ACDNT_SCR / AF_RACE_CNT, 90.99))                                                                                           -- ��ջ����                       ");
    	sbSql.append("\n         , TRIM(TO_CHAR((AMU_RANK_SCR - AMU_ACDNT_SCR) / RACE_CNT, 90.99))                                                                          -- ��������                         ");
    	sbSql.append("\n         , TRIM(TO_CHAR((RANK_1) / RACE_CNT * 100, 990.9))                                                                                          -- �·�                             ");
    	sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2) / RACE_CNT * 100, 990.9))                                                                                 -- ������                           ");
    	sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2 + RANK_3) / RACE_CNT * 100, 990.9))                                                                        -- 3������                          ");
    	sbSql.append("\n         , AVG_STAR_TM                                                                                                                              -- ���ST                           ");
    	sbSql.append("\n         , RUN_CNT                                                                                                                                  -- ����Ƚ��                         ");
    	sbSql.append("\n         , RACE_DAY_CNT                                                                                                                             -- �����ϼ�                         ");
    	sbSql.append("\n         , RACE_CNT                                                                                                                                 -- ����Ƚ��                         ");
    	sbSql.append("\n         , TOT_RACE_CNT                                                                                                                             -- ������Ƚ��                       ");
    	sbSql.append("\n         , BF_RACE_CNT                                                                                                                              -- ���ݱ�����Ƚ��                   ");
    	sbSql.append("\n         , AF_RACE_CNT                                                                                                                              -- �Ĺݱ�����Ƚ��                   ");
    	sbSql.append("\n         , RANK_1                                                                                                                                   -- 1��Ƚ��                          ");
    	sbSql.append("\n         , RANK_2                                                                                                                                   -- 2��Ƚ��                          ");
    	sbSql.append("\n         , RANK_3                                                                                                                                   -- 3��Ƚ��                          ");
    	sbSql.append("\n         , RANK_4                                                                                                                                   -- 4��Ƚ��                          ");
    	sbSql.append("\n         , RANK_5                                                                                                                                   -- 5��Ƚ��                          ");
    	sbSql.append("\n         , RANK_6                                                                                                                                   -- 6��Ƚ��                          ");
    	sbSql.append("\n         , RACE_CNT - (RANK_1 + RANK_2 + RANK_3 + RANK_4 + RANK_5 + RANK_6)                                                                         -- ����Ƚ��                         ");
    	sbSql.append("\n         , 0                                                                                                                                        -- ��޽ɻ� FȽ��                   ");
    	sbSql.append("\n         , 0                                                                                                                                        -- ����ɻ� LȽ��                   ");
    	sbSql.append("\n         , BF_F_CNT                                                                                                                                 -- ���ݱ�FȽ��                      ");
    	sbSql.append("\n         , AF_F_CNT                                                                                                                                 -- �Ĺݱ�FȽ��                      ");
    	sbSql.append("\n         , BF_L_CNT                                                                                                                                 -- ���ݱ�LȽ��                      ");
    	sbSql.append("\n         , AF_L_CNT                                                                                                                                 -- �Ĺݱ�LȽ��                      ");
    	sbSql.append("\n         , ABSE_CNT                                                                                                                                 -- ����Ƚ��                         ");
    	sbSql.append("\n         , RACE_ESC_1_CNT                                                                                                                           -- ��������1Ƚ��                    ");
    	sbSql.append("\n         , RACE_ESC_2_CNT                                                                                                                           -- ��������2Ƚ��                    ");
    	sbSql.append("\n         , FOUL_ELIM_CNT                                                                                                                            -- ��Ģ�ǰ�Ƚ��                     ");
    	sbSql.append("\n         , ELIM_CNT                                                                                                                                 -- �ǰ�Ƚ��                         ");
    	sbSql.append("\n         , FOUL_WARN_CNT                                                                                                                            -- ��Ģ���Ƚ��                     ");
    	sbSql.append("\n         , WARN_CNT                                                                                                                                 -- ���Ƚ��                         ");
    	sbSql.append("\n         , ATTEN_CNT                                                                                                                                -- ����Ƚ��                         ");
    	sbSql.append("\n         , BF_TMS                                                                                                                                   -- ��ȸ��                           ");
    	sbSql.append("\n         , BF_BF_TMS                                                                                                                                -- ����ȸ��                         ");
    	sbSql.append("\n         , BF_BF_BF_TMS                                                                                                                             -- ������ȸ��                       ");
    	sbSql.append("\n         , BF_MBR_CD                                                                                                                                -- ��������                         ");
    	sbSql.append("\n         , BF_BF_MBR_CD                                                                                                                             -- ����������                       ");
    	sbSql.append("\n         , BF_BF_BF_MBR_CD                                                                                                                          -- ������������                     ");
    	sbSql.append("\n         , ?                                                                                                                                        -- �ۼ���ID                         ");
    	sbSql.append("\n         , SYSDATE                                                                                                                                  -- �ۼ��Ͻ�                         ");
    	sbSql.append("\n         , ?                                                                                                                                        -- ������ID                         ");
    	sbSql.append("\n         , SYSDATE                                                                                                                                  -- �����Ͻ�                         ");
    	sbSql.append("\n FROM      TBEC_RACER_MASTER TRM                                                                                                                                                        ");
    	sbSql.append("\n         , (                                                                                                                                                                            ");
    	sbSql.append("\n             -- 6ȸ�� ����                                                                                                                                                              ");
    	sbSql.append("\n             SELECT                                                                                                                                                                     ");
    	sbSql.append("\n                      TOR.RACER_NO                                                                                                                                                      ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0)), 0)   TMS_6_RANK_1                                                                                                             ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0)), 0)   TMS_6_RANK_2                                                                                                             ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0)), 0)   TMS_6_RANK_3                                                                                                             ");
    	sbSql.append("\n                    , STTM.TMS_6_AVG_STAR_TM                                                                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)             , 0)   TMS_6_AMU_RANK_SCR                                                                                                       ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)           , 0)   TMS_6_AMU_ACDNT_SCR                                                                                                      ");
    	sbSql.append("\n                    , COUNT(*)                                 TMS_6_RACE_CNT                                                                                                           ");
    	sbSql.append("\n             FROM     (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                TOR.*                                                                                                                                                   ");
    	sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
    	sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (                                                                                                 ");
    	sbSql.append("\n                                                                                         SELECT                                                                                         ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                                , TOR.RACER_NO                                                                          ");
    	sbSql.append("\n                                                                                         FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                                     SELECT                                                                             ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                            , TOR.RACER_NO                                                              ");
    	sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO                                    ");
    	sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6                       ");
    	sbSql.append("\n                                                                                                     FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
    	sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                                 SELECT                                 ");
    	sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                            )                                           ");
    	sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD     <> '003'   -- ��å������ �ƴ� ���              ");
    	sbSql.append("\n                                                                                                                 AND    TOR.IMMT_CLS_CD <> '003'   -- ��å�� �ƴ� ���                  ");
    	sbSql.append("\n                                                                                                                 AND    TOR.STND_YEAR   >= ? - 1                                        ");
    	sbSql.append("\n                                                                                                                 GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
    	sbSql.append("\n                                                                                                            ) TOR                                                                       ");
    	sbSql.append("\n                                                                                                ) TOR                                                                                   ");
    	sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6                                                                              ");
    	sbSql.append("\n                                                                                      )                                                                                                 ");
    	sbSql.append("\n                      ) TOR                                                                                                                                                             ");
    	sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                  TOR.RACER_NO                                                                                                                                          ");
    	sbSql.append("\n                                , NVL(ROUND(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, TOR.STAR_TM))/NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, 1)),1),2), 0) TMS_6_AVG_STAR_TM                                 ");
    	sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
    	sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (                                                                                                 ");
    	sbSql.append("\n                                                                                         SELECT                                                                                         ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                                , TOR.RACER_NO                                                                          ");
    	sbSql.append("\n                                                                                         FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                                     SELECT                                                                             ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                            , TOR.RACER_NO                                                              ");
    	sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO                                    ");
    	sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6                       ");
    	sbSql.append("\n                                                                                                     FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
    	sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                                 SELECT                                 ");
    	sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                            )                                           ");
    	sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD      = '001'   -- ������ �ƴ� ���                  ");
    	sbSql.append("\n                                                                                                                 AND    TOR.STND_YEAR   >= ? - 1                                        ");
    	sbSql.append("\n                                                                                                                 GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
    	sbSql.append("\n                                                                                                            ) TOR                                                                       ");
    	sbSql.append("\n                                                                                                ) TOR                                                                                   ");
    	sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6                                                                              ");
    	sbSql.append("\n                                                                                      )                                                                                                 ");
        sbSql.append("\n                        GROUP BY TOR.RACER_NO                                                                                                                                           ");
    	sbSql.append("\n                      ) STTM                                                                                                                                                            ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                             ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                            ");
    	sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                                                                                                                    ");
    	sbSql.append("\n 			                      TRVA.STND_YEAR                                                                                                                                                   ");
    	sbSql.append("\n 			                    , TRVA.MBR_CD                                                                                                                                                      ");
    	sbSql.append("\n 			                    , TRVA.TMS                                                                                                                                                         ");
    	sbSql.append("\n 			                    , TRVA.DAY_ORD                                                                                                                                                     ");
    	sbSql.append("\n 			                    , TRVA.RACE_NO                                                                                                                                                     ");
    	sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                                                                                                                                 ");
    	sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                                                                                                                    ");
    	sbSql.append("\n                         FROM     (                                                                                                                                                                ");
    	sbSql.append("\n                                    SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                                                                                                        ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                                                                                                           ");
    	sbSql.append("\n                                           , TRVA.TMS                                                                                                                                              ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                                                                                                                      ");
    	sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR                                                                                                                              ");
    	sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA                                                                                                                               ");
    	sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS                                                                                                                                ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                                                                                                       ");
    	sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                                                                                                       ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                                                                                                          ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                                                                                                             ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                                                                                                                     ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                                                                                                               ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                                                                                                               ");
    	sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
    	sbSql.append("\n                                                                                                        SELECT                                                                                     ");
    	sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
    	sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
    	sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
    	sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
    	sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
    	sbSql.append("\n                                                                                                                    SELECT                                                                         ");
    	sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
    	sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
    	sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
    	sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
    	sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
    	sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
    	sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
    	sbSql.append("\n                                                                                                                                SELECT                                                             ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
    	sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
    	sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
    	sbSql.append("\n                                                                                                                                                                SELECT                             ");
    	sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
    	sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
    	sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
    	sbSql.append("\n                                                                                                                                                           )                                       ");
    	sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- ���� ���                     ");
    	sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- ��å�� �ƴ� ���             ");
    	sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
    	sbSql.append("\n                                                                                                                                GROUP BY                                                           ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                           ) TOR                                                                   ");
    	sbSql.append("\n                                                                                                               ) TOR                                                                               ");
    	sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                                                                          ");
    	sbSql.append("\n                                                                                                     )                                                                                             ");
    	sbSql.append("\n                                    GROUP BY                                                                                                                                                       ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                                                                                                        ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                                                                                                           ");
    	sbSql.append("\n                                           , TRVA.TMS                                                                                                                                              ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                                                                                                                      ");
    	sbSql.append("\n 			                     ) TRVA                                                                                                                                                            ");
    	sbSql.append("\n 			                   , (                                                                                                                                                                 ");
    	sbSql.append("\n                                    SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                                                                                                        ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                                                                                                           ");
    	sbSql.append("\n                                           , TRVA.TMS                                                                                                                                              ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                                                                                                                      ");
    	sbSql.append("\n                                           , TAS .ACDNT_SCR                                                                                                                                        ");
    	sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA                                                                                                                               ");
    	sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS                                                                                                                                ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                                                                                                       ");
    	sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                                                                                                       ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                                                                                                          ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                                                                                                             ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                                                                                                                     ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                                                                                                               ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                                                                                                               ");
    	sbSql.append("\n                                    AND    TRVA.VOIL_CD     IN ('110', '120')                                                                                                                      ");
    	sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
    	sbSql.append("\n                                                                                                        SELECT                                                                                     ");
    	sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
    	sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
    	sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
    	sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
    	sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
    	sbSql.append("\n                                                                                                                    SELECT                                                                         ");
    	sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
    	sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
    	sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
    	sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
    	sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
    	sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
    	sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
    	sbSql.append("\n                                                                                                                                SELECT                                                             ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
    	sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
    	sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
    	sbSql.append("\n                                                                                                                                                                SELECT                             ");
    	sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
    	sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
    	sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
    	sbSql.append("\n                                                                                                                                                           )                                       ");
    	sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- ���� ���                     ");
    	sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- ��å�� �ƴ� ���             ");
    	sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
    	sbSql.append("\n                                                                                                                                GROUP BY                                                           ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                           ) TOR                                                                   ");
    	sbSql.append("\n                                                                                                               ) TOR                                                                               ");
    	sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                                                                          ");
    	sbSql.append("\n                                                                                                     )                                                                                             ");
    	sbSql.append("\n                                  ) TRVB                                                                                                                                                           ");
    	sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                                                                                                              ");
    	sbSql.append("\n                      ) TRVA                                                                                                                                                            ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                                                                                                                                  ");
    	sbSql.append("\n             AND    TOR .MBR_CD       = TR  .MBR_CD                                                                                                                                     ");
    	sbSql.append("\n             AND    TOR .TMS          = TR  .TMS                                                                                                                                        ");
    	sbSql.append("\n             AND    TOR .DAY_ORD      = TR  .DAY_ORD                                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .RACE_NO      = TR  .RACE_NO                                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = TRS .STND_YEAR                                                                                                                                  ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                                                                                                                               ");
    	sbSql.append("\n             AND    TOR .RANK         = TRS .RANK                                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .TMS          = TRVA.TMS        (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .RACER_NO     = STTM.RACER_NO   (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR.ABSE_CD      <> '003'   -- ��å������ �ƴ� ���                                                                                                                 ");
    	sbSql.append("\n             AND    TOR.IMMT_CLS_CD  <> '003'   -- ��å�� �ƴ� ���                                                                                                                     ");
    	sbSql.append("\n             GROUP BY TOR .RACER_NO                                                                                                                                                     ");
    	sbSql.append("\n                    , STTM.TMS_6_AVG_STAR_TM                                                                                                                                            ");
    	sbSql.append("\n           ) TMS_6                                                                                                                                                                      ");
    	sbSql.append("\n         , (                                                                                                                                                                            ");
    	sbSql.append("\n             -- 8ȸ�� ����                                                                                                                                                              ");
    	sbSql.append("\n             SELECT                                                                                                                                                                     ");
    	sbSql.append("\n                      RACER_NO                                                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 1, RANK)) TMS_8_RANK_1                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 2, RANK)) TMS_8_RANK_2                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 3, RANK)) TMS_8_RANK_3                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 4, RANK)) TMS_8_RANK_4                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 5, RANK)) TMS_8_RANK_5                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 6, RANK)) TMS_8_RANK_6                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 7, RANK)) TMS_8_RANK_7                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_8, 8, RANK)) TMS_8_RANK_8                                                                                                                          ");
    	sbSql.append("\n             FROM   (                                                                                                                                                                   ");
    	sbSql.append("\n                         SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                  TOR.*                                                                                                                                                 ");
    	sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                                                                                                                ");
    	sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC                                                                                                           ");
    	sbSql.append("\n		                                                  , TOR.RACE_NO  DESC ) TMS_8                                                                                                    ");
    	sbSql.append("\n                         FROM   TBEB_ORGAN        TOR, TBEB_RACE TR                                                                                                                                   ");
    	sbSql.append("\n                         WHERE  TOR.ABSE_CD     <> '003'    -- ��å������ �ƴ� ���                                                                                                     ");
    	sbSql.append("\n                         AND    TOR.IMMT_CLS_CD <> '003'    -- ��å�� �ƴ� ���                                                                                                         ");
    	sbSql.append("\n                         AND    TR.RACE_STTS_CD = '001'     -- ���ּ����� ������                                                                                                        ");
    	sbSql.append("\n                         AND    TOR.STND_YEAR = TR.STND_YEAR                                                                                                                            ");
    	sbSql.append("\n                         AND    TOR.MBR_CD = TR.MBR_CD                                                                                                                                  ");
    	sbSql.append("\n                         AND    TOR.TMS = TR.TMS                                                                                                                                        ");
    	sbSql.append("\n                         AND    TOR.DAY_ORD = TR.DAY_ORD                                                                                                                                ");
    	sbSql.append("\n                         AND    TOR.RACE_NO = TR.RACE_NO                                                                                                                                ");
    	sbSql.append("\n                         AND    TOR.RACE_DAY    <= (                                                                                                                                     ");
    	sbSql.append("\n                                                     SELECT                                                                                                                             ");
    	sbSql.append("\n                                                            MAX(TRDO.RACE_DAY) RACE_DAY                                                                                                 ");
    	sbSql.append("\n                                                     FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                      ");
    	sbSql.append("\n                                                     WHERE  TRDO.STND_YEAR = ?                                                                                                          ");
    	sbSql.append("\n                                                     AND    TRDO.MBR_CD    = ?                                                                                                          ");
    	sbSql.append("\n                                                     AND    TRDO.TMS       = ?                                                                                                          ");
    	sbSql.append("\n                                                   )                                                                                                                                     ");
    	sbSql.append("\n                   )                                                                                                                                                                    ");
    	sbSql.append("\n             WHERE   TMS_8 <= 8                                                                                                                                                         ");
    	sbSql.append("\n             GROUP BY RACER_NO                                                                                                                                                          ");
    	sbSql.append("\n           ) TMS_8                                                                                                                                                                      ");
    	sbSql.append("\n         , (                                                                                                                                                                            ");
    	sbSql.append("\n             -- �⵵�� ����                                                                                                                                                             ");
    	sbSql.append("\n             SELECT                                                                                                                                                                     ");
    	sbSql.append("\n                      TOR.RACER_NO                                                                                                                                                      ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS))                RUN_CNT                                                                                                     ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT                                                                                                ");
    	sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                                                                                                    ");
    	sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '001', 1, NULL))              BF_RACE_CNT                                                                                                 ");
    	sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '002', 1, NULL))              AF_RACE_CNT                                                                                                 ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.ACDNT_SCR, NULL)), 0) BF_ACDNT_SCR                                                                                        ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.ACDNT_SCR, NULL)), 0) AF_ACDNT_SCR                                                                                        ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0))                       , 0) RANK_1                                                                                              ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0))                       , 0) RANK_2                                                                                              ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0))                       , 0) RANK_3                                                                                              ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 4, 1, 0))                       , 0) RANK_4                                                                                              ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 5, 1, 0))                       , 0) RANK_5                                                                                              ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 6, 1, 0))                       , 0) RANK_6                                                                                              ");
    	sbSql.append("\n                    , NVL(STTM.AVG_STAR_TM                                     , 0) AVG_STAR_TM                                                                                         ");
    	sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)                                    , 0) AMU_RANK_SCR                                                                                        ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)                                  , 0) AMU_ACDNT_SCR                                                                                       ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.F_CNT, NULL))    , 0) BF_F_CNT                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.F_CNT, NULL))    , 0) AF_F_CNT                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.L_CNT, NULL))    , 0) BF_L_CNT                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.L_CNT, NULL))    , 0) AF_L_CNT                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.ABSE_CNT      )                             , 0) ABSE_CNT                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_1_CNT)                             , 0) RACE_ESC_1_CNT                                                                                      ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_2_CNT)                             , 0) RACE_ESC_2_CNT                                                                                      ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_ELIM_CNT )                             , 0) FOUL_ELIM_CNT                                                                                       ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.ELIM_CNT      )                             , 0) ELIM_CNT                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_WARN_CNT )                             , 0) FOUL_WARN_CNT                                                                                       ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.WARN_CNT      )                             , 0) WARN_CNT                                                                                            ");
    	sbSql.append("\n                    , NVL(SUM(TRVA.ATTEN_CNT     )                             , 0) ATTEN_CNT                                                                                           ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                            ");
    	sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                                                                                            ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                             ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                            ");
    	sbSql.append("\n                    , TBEB_RECD_STND_TERM TRST                                                                                                                                          ");
    	sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                             ");
    	sbSql.append("\n 			                      TRVA.STND_YEAR                                                            ");
    	sbSql.append("\n 			                    , TRVA.MBR_CD                                                               ");
    	sbSql.append("\n 			                    , TRVA.TMS                                                                  ");
    	sbSql.append("\n 			                    , TRVA.DAY_ORD                                                              ");
    	sbSql.append("\n 			                    , TRVA.RACE_NO                                                              ");
    	sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                                          ");
    	sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                             ");
    	sbSql.append("\n                                , TRVA.F_CNT                                                                ");
    	sbSql.append("\n                                , TRVA.L_CNT                                                                ");
    	sbSql.append("\n                                , TRVA.ABSE_CNT                                                             ");
    	sbSql.append("\n                                , TRVA.RACE_ESC_1_CNT                                                       ");
    	sbSql.append("\n                                , TRVA.RACE_ESC_2_CNT                                                       ");
    	sbSql.append("\n                                , TRVA.FOUL_ELIM_CNT                                                        ");
    	sbSql.append("\n                                , TRVA.ELIM_CNT                                                             ");
    	sbSql.append("\n                                , TRVA.FOUL_WARN_CNT                                                        ");
    	sbSql.append("\n                                , TRVA.WARN_CNT                                                             ");
    	sbSql.append("\n                                , TRVA.ATTEN_CNT                                                            ");
    	sbSql.append("\n                         FROM     (                                                                         ");
    	sbSql.append("\n             			            SELECT                                                                  ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                 ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                    ");
    	sbSql.append("\n                                           , TRVA.TMS                                                       ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                   ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                   ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                               ");
    	sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR                                       ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '110', 1, NULL)) F_CNT                ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '120', 1, NULL)) L_CNT                ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '130', 1, NULL)) ABSE_CNT             ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '995', 1, NULL)) RACE_ESC_1_CNT       ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '997', 1, NULL)) RACE_ESC_2_CNT       ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '125', 1, NULL)) FOUL_ELIM_CNT        ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '140', 1, NULL)) ELIM_CNT             ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '150', 1, NULL)) FOUL_WARN_CNT        ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '210', 1, NULL)) WARN_CNT             ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '220', 1, NULL)) ATTEN_CNT            ");
    	sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                        ");
    	sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                         ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                ");
    	sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                  ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                                                                                                       ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                                                                                                          ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                                                                                                             ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                                                                                                                     ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                                                                                                               ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                                                                                                               ");
    	sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                            ");
    	sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                            ");
    	sbSql.append("\n             			                                            SELECT                                  ");
    	sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY      ");
    	sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO           ");
    	sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.TMS       = ?               ");
    	sbSql.append("\n             			                                       )                                            ");
    	sbSql.append("\n             			            GROUP BY                                                                ");
    	sbSql.append("\n             			                     TRVA.STND_YEAR                                                 ");
    	sbSql.append("\n             			                   , TRVA.MBR_CD                                                    ");
    	sbSql.append("\n             			                   , TRVA.TMS                                                       ");
    	sbSql.append("\n             			                   , TRVA.DAY_ORD                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_NO                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                               ");
    	sbSql.append("\n 			                     ) TRVA                                                                     ");
    	sbSql.append("\n 			                   , (                                                                          ");
    	sbSql.append("\n             			            SELECT                                                                  ");
    	sbSql.append("\n             			                     TRVA.STND_YEAR                                                 ");
    	sbSql.append("\n             			                   , TRVA.MBR_CD                                                    ");
    	sbSql.append("\n             			                   , TRVA.TMS                                                       ");
    	sbSql.append("\n             			                   , TRVA.DAY_ORD                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_NO                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                               ");
    	sbSql.append("\n             			                   , TAS .ACDNT_SCR                                                 ");
    	sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                        ");
    	sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                         ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                ");
    	sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                  ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                                                                                                       ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                                                                                                          ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                                                                                                             ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                                                                                                                     ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                                                                                                               ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                                                                                                               ");
    	sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                            ");
    	sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                            ");
    	sbSql.append("\n             			                                            SELECT                                  ");
    	sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY      ");
    	sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO           ");
    	sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.TMS       = ?               ");
    	sbSql.append("\n             			                                       )                                            ");
    	sbSql.append("\n             			            AND    TRVA.VOIL_CD     IN ('110', '120')                               ");
    	sbSql.append("\n                                  ) TRVB                                                                    ");
    	sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                       ");
    	sbSql.append("\n                      ) TRVA                                                                                                                                                            ");
    	sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                  TOR.RACER_NO                                                                                                                                          ");
    	sbSql.append("\n                                , NVL(ROUND(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, TOR.STAR_TM))/NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, 1)),1),2), 0) AVG_STAR_TM                              ");
    	sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
    	sbSql.append("\n                         WHERE  TOR .STND_YEAR    = ?                                                                                                                                   ");
    	sbSql.append("\n                         AND    TOR .RACE_DAY    <= (                                                                                                                                   ");
    	sbSql.append("\n                                                         SELECT                                                                                                                         ");
    	sbSql.append("\n                                                                MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                                         FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                                         WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                                         AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                                         AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                                    )                                                                                                                                   ");
        sbSql.append("\n                         GROUP BY TOR.RACER_NO                                                                                                                                          ");
    	sbSql.append("\n                      ) STTM                                                                                                                                                            ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                                                                                                                                  ");
    	sbSql.append("\n             AND    TOR .MBR_CD       = TR  .MBR_CD                                                                                                                                     ");
    	sbSql.append("\n             AND    TOR .TMS          = TR  .TMS                                                                                                                                        ");
    	sbSql.append("\n             AND    TOR .DAY_ORD      = TR  .DAY_ORD                                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .RACE_NO      = TR  .RACE_NO                                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = TRT .STND_YEAR                                                                                                                                  ");
    	sbSql.append("\n             AND    TOR .MBR_CD       = TRT .MBR_CD                                                                                                                                     ");
    	sbSql.append("\n             AND    TOR .TMS          = TRT .TMS                                                                                                                                        ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = TRS .STND_YEAR                                                                                                                                  ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                                                                                                                               ");
    	sbSql.append("\n             AND    TOR .RANK         = TRS .RANK                                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .TMS          = TRVA.TMS        (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .RACER_NO     = STTM.RACER_NO   (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = TRST.STND_YEAR                                                                                                                                  ");
    	sbSql.append("\n             AND    TRT .STR_DT BETWEEN TRST.STR_DT                                                                                                                                     ");
    	sbSql.append("\n                                    AND TRST.END_DT                                                                                                                                     ");
    	sbSql.append("\n             AND    TOR .ABSE_CD     <> '003'   -- ��å������ �ƴ� ���                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .IMMT_CLS_CD <> '003'   -- ��å�� �ƴ� ���                                                                                                                     ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = ?                                                                                                                                               ");
    	sbSql.append("\n             AND    TOR .RACE_DAY    <= (                                                                                                                                               ");
    	sbSql.append("\n                                             SELECT                                                                                                                                     ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                                         ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                              ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                                  ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                                  ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                                  ");
    	sbSql.append("\n                                        )                                                                                                                                               ");
    	sbSql.append("\n             GROUP BY TOR .RACER_NO                                                                                                                                                     ");
    	sbSql.append("\n                    , STTM.AVG_STAR_TM                                                                                                                                                  ");
    	sbSql.append("\n           ) YEAR                                                                                                                                                                       ");
    	sbSql.append("\n         , (                                                                                                                                                                            ");
    	sbSql.append("\n             -- ��ȸ�� ������                                                                                                                                                           ");
    	sbSql.append("\n             SELECT                                                                                                                                                                     ");
    	sbSql.append("\n                      RACER_NO                                                                                                                                                          ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, TMS   )) BF_TMS                                                                                                                              ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, TMS   )) BF_BF_TMS                                                                                                                           ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, TMS   )) BF_BF_BF_TMS                                                                                                                        ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, MBR_CD)) BF_MBR_CD                                                                                                                           ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, MBR_CD)) BF_BF_MBR_CD                                                                                                                        ");
    	sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, MBR_CD)) BF_BF_BF_MBR_CD                                                                                                                     ");
    	sbSql.append("\n             FROM   (                                                                                                                                                                   ");
    	sbSql.append("\n                         SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                  TOR.STND_YEAR                                                                                                                                         ");
    	sbSql.append("\n                                , TOR.MBR_CD                                                                                                                                            ");
    	sbSql.append("\n                                , TOR.TMS                                                                                                                                               ");
    	sbSql.append("\n                                , TOR.RACER_NO                                                                                                                                          ");
    	sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                                                                                                                ");
    	sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                                                                                                   ");
    	sbSql.append("\n                         FROM   (                                                                                                                                                       ");
    	sbSql.append("\n                                     SELECT                                                                                                                                             ");
    	sbSql.append("\n                                              TOR.STND_YEAR                                                                                                                             ");
    	sbSql.append("\n                                            , TOR.MBR_CD                                                                                                                                ");
    	sbSql.append("\n                                            , TOR.TMS                                                                                                                                   ");
    	sbSql.append("\n                                            , TOR.RACER_NO                                                                                                                              ");
    	sbSql.append("\n                                            , MAX(TOR.RACE_DAY) RACE_DAY                                                                                                                ");
    	sbSql.append("\n                                     FROM   TBEB_ORGAN        TOR                                                                                                                       ");
    	sbSql.append("\n                                     WHERE  TOR.RACE_DAY     <= (                                                                                                                       ");
    	sbSql.append("\n                                                                     SELECT                                                                                                             ");
    	sbSql.append("\n                                                                            MAX(TRDO.RACE_DAY) RACE_DAY                                                                                 ");
    	sbSql.append("\n                                                                     FROM   TBEB_RACE_DAY_ORD TRDO                                                                                      ");
    	sbSql.append("\n                                                                     WHERE  TRDO.STND_YEAR = ?                                                                                          ");
    	sbSql.append("\n                                                                     AND    TRDO.MBR_CD    = ?                                                                                          ");
    	sbSql.append("\n                                                                     AND    TRDO.TMS       = ?                                                                                          ");
    	sbSql.append("\n                                                                )                                                                                                                       ");
    	sbSql.append("\n                                     AND    TOR .ABSE_CD     <> '003'   -- ��å������ �ƴ� ���                                                                                         ");
    	sbSql.append("\n                                     AND    TOR .IMMT_CLS_CD <> '003'   -- ��å�� �ƴ� ���                                                                                             ");
    	sbSql.append("\n                                     GROUP BY                                                                                                                                           ");
    	sbSql.append("\n                                              TOR.STND_YEAR                                                                                                                             ");
    	sbSql.append("\n                                            , TOR.MBR_CD                                                                                                                                ");
    	sbSql.append("\n                                            , TOR.TMS                                                                                                                                   ");
    	sbSql.append("\n                                            , TOR.RACER_NO                                                                                                                              ");
    	sbSql.append("\n                                ) TOR                                                                                                                                                   ");
    	sbSql.append("\n                    )                                                                                                                                                                   ");
    	sbSql.append("\n             GROUP BY RACER_NO                                                                                                                                                          ");
    	sbSql.append("\n           ) TMS_3                                                                                                                                                                      ");
    	sbSql.append("\n         , (                                                                                                                                                                            ");
    	sbSql.append("\n             --  ��ü ����                                                                                                                                                              ");
    	sbSql.append("\n             SELECT                                                                                                                                                                     ");
    	sbSql.append("\n                      RACER_NO                                                                                                                                                          ");
    	sbSql.append("\n                    , COUNT(*) TOT_RACE_CNT                                                                                                                                             ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                            ");
    	sbSql.append("\n             WHERE  TOR.RACE_DAY     <= (                                                                                                                                               ");
    	sbSql.append("\n                                             SELECT                                                                                                                                     ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                                         ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                              ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                                  ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                                  ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                                  ");
    	sbSql.append("\n                                        )                                                                                                                                               ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = ?                                                                                                                                               ");
    	sbSql.append("\n             AND    TOR .ABSE_CD     <> '003'   -- ��å�� �ƴ� ���                                                                                                                     ");
    	sbSql.append("\n             GROUP BY RACER_NO                                                                                                                                                          ");
    	sbSql.append("\n           ) TOT                                                                                                                                                                        ");
    	sbSql.append("\n WHERE   TRM.RACER_NO = TMS_6.RACER_NO(+)                                                                                                                                               ");
    	sbSql.append("\n AND     TRM.RACER_NO = TMS_8.RACER_NO(+)                                                                                                                                               ");
    	sbSql.append("\n AND     TRM.RACER_NO = YEAR .RACER_NO(+)                                                                                                                                               ");
    	sbSql.append("\n AND     TRM.RACER_NO = TMS_3.RACER_NO(+)                                                                                                                                               ");
    	sbSql.append("\n AND     TRM.RACER_NO = TOT  .RACER_NO(+)                                                                                                                                               ");
        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 )
        	sbSql.append("\n AND     TRM.RACER_NO IN (");
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 )
        	sbSql.append(")");
        
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, "000"    );  
        param.setValueParamter(i++, "0"      );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        
        // 6ȸ��
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear); 
        
        // 8����
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     ); 
        
        // �⵵�� ����
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  

        // ��������
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        
        // ��ü 
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);  

        int dmlcount = this.getDao("boadao").insertByQueryStatement(sbSql.toString(), param);
        
        /************************************************************************************
        *  �ش� �����忡 �ش��ϴ� ���� ��������
        ************************************************************************************/
        sbSql.delete(0, sbSql.length());
        sbSql.append("\n INSERT INTO TBEB_RACER_RECD_ACCU_SUM                                                                                                                                                   ");
        sbSql.append("\n (                                                                                                                                                                                      ");
        sbSql.append("\n           STND_YEAR                       -- ���س⵵                                                                                                                                  ");
        sbSql.append("\n         , MBR_CD                          -- �������ڵ�                                                                                                                                ");
        sbSql.append("\n         , TMS                             -- ȸ��                                                                                                                                      ");
        sbSql.append("\n         , RACER_NO                        -- ������Ϲ�ȣ                                                                                                                              ");
        sbSql.append("\n         , APLY_DT                         -- ��������                                                                                                                                  ");
        sbSql.append("\n         , TMS_6_AMU_RANK_SCR              -- 6ȸ����������                                                                                                                             ");
        sbSql.append("\n         , TMS_6_AVG_RANK_SCR              -- 6ȸ���������                                                                                                                             ");
        sbSql.append("\n         , TMS_6_AMU_ACDNT_SCR             -- 6ȸ���������                                                                                                                             ");
        sbSql.append("\n         , TMS_6_AVG_ACDNT_SCR             -- 6ȸ���������                                                                                                                             ");
        sbSql.append("\n         , TMS_6_AVG_SCR                   -- 6ȸ��յ���                                                                                                                               ");
        sbSql.append("\n         , TMS_6_WIN_RATIO                 -- 6ȸ�·�                                                                                                                                   ");
        sbSql.append("\n         , TMS_6_HIGH_RANK_RATIO           -- 6ȸ������                                                                                                                                 ");
        sbSql.append("\n         , TMS_6_HIGH_3_RANK_RATIO         -- 6ȸ3������                                                                                                                                ");
        sbSql.append("\n         , TMS_6_AVG_STRT_TM               -- 6ȸ���ST                                                                                                                                 ");
        sbSql.append("\n         , TMS_8_RANK_ORD                  -- 8���� ����                                                                                                                                ");
        sbSql.append("\n         , TMS_6_RACE_CNT                  -- 6ȸ����Ƚ��                                                                                                                               ");
        sbSql.append("\n         , TMS_6_RANK_1                    -- 6ȸ1��Ƚ��                                                                                                                                ");
        sbSql.append("\n         , TMS_6_RANK_2                    -- 6ȸ2��Ƚ��                                                                                                                                ");
        sbSql.append("\n         , TMS_6_RANK_3                    -- 6ȸ3��Ƚ��                                                                                                                                ");
        sbSql.append("\n         , AMU_RANK_SCR                    -- ����������                                                                                                                                ");
        sbSql.append("\n         , AVG_RANK_SCR                    -- ���������                                                                                                                                ");
        sbSql.append("\n         , AMU_ACDNT_SCR                   -- ���������                                                                                                                                ");
        sbSql.append("\n         , AVG_ACDNT_SCR                   -- ��ջ����                                                                                                                                ");
        sbSql.append("\n         , BF_AMU_ACDNT_SCR                -- ���ݱ⴩�������                                                                                                                          ");
        sbSql.append("\n         , BF_AVG_ACDNT_SCR                -- ���ݱ���ջ����                                                                                                                          ");
        sbSql.append("\n         , AF_AMU_ACDNT_SCR                -- �Ĺݱ⴩�������                                                                                                                          ");
        sbSql.append("\n         , AF_AVG_ACDNT_SCR                -- �Ĺݱ���ջ����                                                                                                                          ");
        sbSql.append("\n         , AVG_SCR                         -- ��������                                                                                                                                  ");
        sbSql.append("\n         , WIN_RATIO                       -- �·�                                                                                                                                      ");
        sbSql.append("\n         , HIGH_RANK_RATIO                 -- ������                                                                                                                                    ");
        sbSql.append("\n         , HIGH_3_RANK_RATIO               -- 3������                                                                                                                                   ");
        sbSql.append("\n         , AVG_STRT_TM                     -- ���ST                                                                                                                                    ");
        sbSql.append("\n         , RUN_CNT                         -- ����Ƚ��                                                                                                                                  ");
        sbSql.append("\n         , RACE_DAY_CNT                    -- �����ϼ�                                                                                                                                  ");
        sbSql.append("\n         , RACE_CNT                        -- ����Ƚ��                                                                                                                                  ");
        sbSql.append("\n         , TOT_RACE_CNT                    -- ������Ƚ��                                                                                                                                ");
        sbSql.append("\n         , BF_RACE_CNT                     -- ���ݱ�����Ƚ��                                                                                                                            ");
        sbSql.append("\n         , AF_RACE_CNT                     -- �Ĺݱ�����Ƚ��                                                                                                                            ");
        sbSql.append("\n         , RANK_1_CNT                      -- 1��Ƚ��                                                                                                                                   ");
        sbSql.append("\n         , RANK_2_CNT                      -- 2��Ƚ��                                                                                                                                   ");
        sbSql.append("\n         , RANK_3_CNT                      -- 3��Ƚ��                                                                                                                                   ");
        sbSql.append("\n         , RANK_4_CNT                      -- 4��Ƚ��                                                                                                                                   ");
        sbSql.append("\n         , RANK_5_CNT                      -- 5��Ƚ��                                                                                                                                   ");
        sbSql.append("\n         , RANK_6_CNT                      -- 6��Ƚ��                                                                                                                                   ");
        sbSql.append("\n         , RANK_ETC_CNT                    -- ����Ƚ��                                                                                                                                  ");
        sbSql.append("\n         , DGRE_JUDG_F_CNT                 -- ��޽ɻ� FȽ��                                                                                                                            ");
        sbSql.append("\n         , DGRE_JUDG_L_CNT                 -- ����ɻ� LȽ��                                                                                                                            ");
        sbSql.append("\n         , BF_F_CNT                        -- ���ݱ�FȽ��                                                                                                                               ");
        sbSql.append("\n         , AF_F_CNT                        -- �Ĺݱ�FȽ��                                                                                                                               ");
        sbSql.append("\n         , BF_L_CNT                        -- ���ݱ�LȽ��                                                                                                                               ");
        sbSql.append("\n         , AF_L_CNT                        -- �Ĺݱ�LȽ��                                                                                                                               ");
        sbSql.append("\n         , ABSE_CNT                        -- ����Ƚ��                                                                                                                                  ");
        sbSql.append("\n         , RACE_ESC_1_CNT                  -- ��������1Ƚ��                                                                                                                             ");
        sbSql.append("\n         , RACE_ESC_2_CNT                  -- ��������2Ƚ��                                                                                                                             ");
        sbSql.append("\n         , FOUL_ELIM_CNT                   -- ��Ģ�ǰ�Ƚ��                                                                                                                              ");
        sbSql.append("\n         , ELIM_CNT                        -- �ǰ�Ƚ��                                                                                                                                  ");
        sbSql.append("\n         , FOUL_WARN_CNT                   -- ��Ģ���Ƚ��                                                                                                                              ");
        sbSql.append("\n         , WARN_CNT                        -- ���Ƚ��                                                                                                                                  ");
        sbSql.append("\n         , ATTEN_CNT                       -- ����Ƚ��                                                                                                                                  ");
        sbSql.append("\n         , BF_TMS                          -- ��ȸ��                                                                                                                                    ");
        sbSql.append("\n         , BF_BF_TMS                       -- ����ȸ��                                                                                                                                  ");
        sbSql.append("\n         , BF_BF_BF_TMS                    -- ������ȸ��                                                                                                                                ");
        sbSql.append("\n         , BF_MBR_CD                       -- ��������                                                                                                                                  ");
        sbSql.append("\n         , BF_BF_MBR_CD                    -- ����������                                                                                                                                ");
        sbSql.append("\n         , BF_BF_BF_MBR_CD                 -- ������������                                                                                                                              ");
        sbSql.append("\n         , INST_ID                         -- �ۼ���ID                                                                                                                                  ");
        sbSql.append("\n         , INST_DTM                        -- �ۼ��Ͻ�                                                                                                                                  ");
        sbSql.append("\n         , UPDT_ID                         -- ������ID                                                                                                                                  ");
        sbSql.append("\n         , UPDT_DTM                        -- �����Ͻ�                                                                                                                                  ");
        sbSql.append("\n )                                                                                                                                                                                      ");
        sbSql.append("\n SELECT                                                                                                                                                                                 ");
        sbSql.append("\n           ?                                                                                                                                        -- ���س⵵                         ");
        sbSql.append("\n         , ?                                                                                                                                        -- �������ڵ�                       ");
        sbSql.append("\n         , ?                                                                                                                                        -- ȸ��                             ");
        sbSql.append("\n         , TRM.RACER_NO                                                                                                                             -- ������Ϲ�ȣ                     ");
        sbSql.append("\n         , (SELECT MAX(TRDO.RACE_DAY) RACE_DAY FROM TBEB_RACE_DAY_ORD TRDO WHERE TRDO.STND_YEAR = ? AND TRDO.MBR_CD = ? AND TRDO.TMS = ?)           -- ��������                         ");
        sbSql.append("\n         , TMS_6_AMU_RANK_SCR                                                                                                                       -- 6ȸ����������                    ");
        sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_RANK_SCR / TMS_6_RACE_CNT, 90.99))                                                                                -- 6ȸ���������                    ");
        sbSql.append("\n         , TMS_6_AMU_ACDNT_SCR                                                                                                                      -- 6ȸ���������                    ");
        sbSql.append("\n         , TRIM(TO_CHAR(TMS_6_AMU_ACDNT_SCR / TMS_6_RACE_CNT, 90.99))                                                                               -- 6ȸ���������                    ");
        sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_AMU_RANK_SCR - NVL(TMS_6_AMU_ACDNT_SCR, 0)) / TMS_6_RACE_CNT, 90.99))                                                -- 6ȸ��յ���                      ");
        sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1) / TMS_6_RACE_CNT * 100, 990.9))                                                                              -- 6ȸ�·�                          ");
        sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2) / TMS_6_RACE_CNT * 100, 990.9))                                                               -- 6ȸ������                        ");
        sbSql.append("\n         , TRIM(TO_CHAR((TMS_6_RANK_1 + TMS_6_RANK_2 + TMS_6_RANK_3) / TMS_6_RACE_CNT * 100, 990.9))                                                -- 6ȸ3������                       ");
        sbSql.append("\n         , TMS_6_AVG_STAR_TM                                                                                                                        -- 6ȸ���ST                        ");
        sbSql.append("\n         , TMS_8_RANK_1 || TMS_8_RANK_2 || TMS_8_RANK_3 || TMS_8_RANK_4 || TMS_8_RANK_5 || TMS_8_RANK_6 || TMS_8_RANK_7 || TMS_8_RANK_8             -- 8���� ����                       ");
        sbSql.append("\n         , TMS_6_RACE_CNT                                                                                                                           -- 6ȸ����Ƚ��                      ");
        sbSql.append("\n         , TMS_6_RANK_1                                                                                                                             -- 6ȸ1��Ƚ��                       ");
        sbSql.append("\n         , TMS_6_RANK_2                                                                                                                             -- 6ȸ2��Ƚ��                       ");
        sbSql.append("\n         , TMS_6_RANK_3                                                                                                                             -- 6ȸ3��Ƚ��                       ");
        sbSql.append("\n         , AMU_RANK_SCR                                                                                                                             -- ����������                       ");
        sbSql.append("\n         , TRIM(TO_CHAR(AMU_RANK_SCR / RACE_CNT, 90.99))                                                                                            -- ���������                       ");
        sbSql.append("\n         , AMU_ACDNT_SCR                                                                                                                            -- ���������                       ");
        sbSql.append("\n         , TRIM(TO_CHAR(AMU_ACDNT_SCR / RACE_CNT, 90.99))                                                                                           -- ��ջ����                       ");
        sbSql.append("\n         , BF_ACDNT_SCR                                                                                                                             -- ���ݱ�����                     ");
        sbSql.append("\n         , TRIM(TO_CHAR(BF_ACDNT_SCR / BF_RACE_CNT, 90.99))                                                                                           -- ��ջ����                       ");
        sbSql.append("\n         , AF_ACDNT_SCR                                                                                                                             -- �Ĺݱ�����                     ");
        sbSql.append("\n         , TRIM(TO_CHAR(AF_ACDNT_SCR / AF_RACE_CNT, 90.99))                                                                                           -- ��ջ����                       ");
        sbSql.append("\n         , TRIM(TO_CHAR((AMU_RANK_SCR - AMU_ACDNT_SCR) / RACE_CNT, 90.99))                                                                          -- ��������                         ");
        sbSql.append("\n         , TRIM(TO_CHAR((RANK_1) / RACE_CNT * 100, 990.9))                                                                                          -- �·�                             ");
        sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2) / RACE_CNT * 100, 990.9))                                                                                 -- ������                           ");
        sbSql.append("\n         , TRIM(TO_CHAR((RANK_1 + RANK_2 + RANK_3) / RACE_CNT * 100, 990.9))                                                                        -- 3������                          ");
        sbSql.append("\n         , AVG_STAR_TM                                                                                                                              -- ���ST                           ");
        sbSql.append("\n         , RUN_CNT                                                                                                                                  -- ����Ƚ��                         ");
        sbSql.append("\n         , RACE_DAY_CNT                                                                                                                             -- �����ϼ�                         ");
        sbSql.append("\n         , RACE_CNT                                                                                                                                 -- ����Ƚ��                         ");
        sbSql.append("\n         , TOT_RACE_CNT                                                                                                                             -- ������Ƚ��                       ");
        sbSql.append("\n         , BF_RACE_CNT                                                                                                                              -- ���ݱ�����Ƚ��                   ");
        sbSql.append("\n         , AF_RACE_CNT                                                                                                                              -- �Ĺݱ�����Ƚ��                   ");
        sbSql.append("\n         , RANK_1                                                                                                                                   -- 1��Ƚ��                          ");
        sbSql.append("\n         , RANK_2                                                                                                                                   -- 2��Ƚ��                          ");
        sbSql.append("\n         , RANK_3                                                                                                                                   -- 3��Ƚ��                          ");
        sbSql.append("\n         , RANK_4                                                                                                                                   -- 4��Ƚ��                          ");
        sbSql.append("\n         , RANK_5                                                                                                                                   -- 5��Ƚ��                          ");
        sbSql.append("\n         , RANK_6                                                                                                                                   -- 6��Ƚ��                          ");
        sbSql.append("\n         , RACE_CNT - (RANK_1 + RANK_2 + RANK_3 + RANK_4 + RANK_5 + RANK_6)                                                                         -- ����Ƚ��                         ");
        sbSql.append("\n         , 0                                                                                                                                        -- ��޽ɻ� FȽ��                   ");
        sbSql.append("\n         , 0                                                                                                                                        -- ����ɻ� LȽ��                   ");
        sbSql.append("\n         , BF_F_CNT                                                                                                                                 -- ���ݱ�FȽ��                      ");
        sbSql.append("\n         , AF_F_CNT                                                                                                                                 -- �Ĺݱ�FȽ��                      ");
        sbSql.append("\n         , BF_L_CNT                                                                                                                                 -- ���ݱ�LȽ��                      ");
        sbSql.append("\n         , AF_L_CNT                                                                                                                                 -- �Ĺݱ�LȽ��                      ");
        sbSql.append("\n         , ABSE_CNT                                                                                                                                 -- ����Ƚ��                         ");
        sbSql.append("\n         , RACE_ESC_1_CNT                                                                                                                           -- ��������1Ƚ��                    ");
        sbSql.append("\n         , RACE_ESC_2_CNT                                                                                                                           -- ��������2Ƚ��                    ");
        sbSql.append("\n         , FOUL_ELIM_CNT                                                                                                                            -- ��Ģ�ǰ�Ƚ��                     ");
        sbSql.append("\n         , ELIM_CNT                                                                                                                                 -- �ǰ�Ƚ��                         ");
        sbSql.append("\n         , FOUL_WARN_CNT                                                                                                                            -- ��Ģ���Ƚ��                     ");
        sbSql.append("\n         , WARN_CNT                                                                                                                                 -- ���Ƚ��                         ");
        sbSql.append("\n         , ATTEN_CNT                                                                                                                                -- ����Ƚ��                         ");
        sbSql.append("\n         , BF_TMS                                                                                                                                   -- ��ȸ��                           ");
        sbSql.append("\n         , BF_BF_TMS                                                                                                                                -- ����ȸ��                         ");
        sbSql.append("\n         , BF_BF_BF_TMS                                                                                                                             -- ������ȸ��                       ");
        sbSql.append("\n         , BF_MBR_CD                                                                                                                                -- ��������                         ");
        sbSql.append("\n         , BF_BF_MBR_CD                                                                                                                             -- ����������                       ");
        sbSql.append("\n         , BF_BF_BF_MBR_CD                                                                                                                          -- ������������                     ");
        sbSql.append("\n         , ?                                                                                                                                        -- �ۼ���ID                         ");
        sbSql.append("\n         , SYSDATE                                                                                                                                  -- �ۼ��Ͻ�                         ");
        sbSql.append("\n         , ?                                                                                                                                        -- ������ID                         ");
        sbSql.append("\n         , SYSDATE                                                                                                                                  -- �����Ͻ�                         ");
        sbSql.append("\n FROM      TBEC_RACER_MASTER TRM                                                                                                                                                        ");
        sbSql.append("\n         , (                                                                                                                                                                            ");
        sbSql.append("\n             -- 6ȸ�� ����                                                                                                                                                              ");
        sbSql.append("\n             SELECT                                                                                                                                                                     ");
        sbSql.append("\n                      TOR.RACER_NO                                                                                                                                                          ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0)), 0)   TMS_6_RANK_1                                                                                                             ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0)), 0)   TMS_6_RANK_2                                                                                                             ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0)), 0)   TMS_6_RANK_3                                                                                                             ");
    	sbSql.append("\n                    , STTM.TMS_6_AVG_STAR_TM                                                                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)             , 0)   TMS_6_AMU_RANK_SCR                                                                                                       ");
        sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)           , 0)   TMS_6_AMU_ACDNT_SCR                                                                                                      ");
        sbSql.append("\n                    , COUNT(*)                                 TMS_6_RACE_CNT                                                                                                           ");
        sbSql.append("\n             FROM     (                                                                                                                                                                 ");
        sbSql.append("\n                         SELECT                                                                                                                                                         ");
        sbSql.append("\n                                TOR.*                                                                                                                                                   ");
        sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
        sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (                                                                                                 ");
        sbSql.append("\n                                                                                         SELECT                                                                                         ");
        sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                                         ");
        sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                            ");
        sbSql.append("\n                                                                                                , TOR.TMS                                                                               ");
        sbSql.append("\n                                                                                                , TOR.RACER_NO                                                                          ");
        sbSql.append("\n                                                                                         FROM   (                                                                                       ");
        sbSql.append("\n                                                                                                     SELECT                                                                             ");
        sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                             ");
        sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                                ");
        sbSql.append("\n                                                                                                            , TOR.TMS                                                                   ");
        sbSql.append("\n                                                                                                            , TOR.RACER_NO                                                              ");
        sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO                                    ");
        sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6                       ");
        sbSql.append("\n                                                                                                     FROM   (                                                                           ");
        sbSql.append("\n                                                                                                                 SELECT                                                                 ");
        sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
        sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
        sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
        sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
        sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
        sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                                           ");
        sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                                           ");
        sbSql.append("\n                                                                                                                                                 SELECT                                 ");
        sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY     ");
        sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO          ");
        sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?              ");
        sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?              ");
        sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?              ");
        sbSql.append("\n                                                                                                                                            )                                           ");
        sbSql.append("\n                                                                                                                 AND    TOR.MBR_CD       = ?                                            ");
        sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD     <> '003'   -- ��å������ �ƴ� ���              ");
        sbSql.append("\n                                                                                                                 AND    TOR.IMMT_CLS_CD <> '003'   -- ��å�� �ƴ� ���                  ");
    	sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
        sbSql.append("\n                                                                                                                 GROUP BY                                                               ");
        sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
        sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
        sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
        sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
        sbSql.append("\n                                                                                                            ) TOR                                                                       ");
        sbSql.append("\n                                                                                                ) TOR                                                                                   ");
        sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6                                                                              ");
        sbSql.append("\n                                                                                      )                                                                                                 ");
        sbSql.append("\n                      ) TOR                                                                                                                                                             ");
    	sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                  TOR.RACER_NO                                                                                                                                          ");
    	sbSql.append("\n                                , NVL(AVG(TOR.STAR_TM)              , 0)   TMS_6_AVG_STAR_TM                                                                                            ");
    	sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
    	sbSql.append("\n                         WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.RACER_NO) IN (                                                                                                 ");
    	sbSql.append("\n                                                                                         SELECT                                                                                         ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                                , TOR.RACER_NO                                                                          ");
    	sbSql.append("\n                                                                                         FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                                     SELECT                                                                             ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                            , TOR.RACER_NO                                                              ");
    	sbSql.append("\n                                                                                                            , RANK() OVER (PARTITION BY TOR.RACER_NO                                    ");
    	sbSql.append("\n                                                                                                                               ORDER BY TOR.RACE_DAY DESC ) TMS_6                       ");
    	sbSql.append("\n                                                                                                     FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
    	sbSql.append("\n                                                                                                                        , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                                 FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                                 WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                                 SELECT                                 ");
    	sbSql.append("\n                                                                                                                                                        MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                                 WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                                 AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                                 AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                            )                                           ");
        sbSql.append("\n                                                                                                                 AND    TOR.MBR_CD       = ?                                            ");
    	sbSql.append("\n                                                                                                                 AND    TOR.ABSE_CD      = '001'   -- ������ �ƴ� ���                  ");
    	sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
    	sbSql.append("\n                                                                                                                 GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                                          TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                                        , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                                        , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                                        , TOR.RACER_NO                                                  ");
    	sbSql.append("\n                                                                                                            ) TOR                                                                       ");
    	sbSql.append("\n                                                                                                ) TOR                                                                                   ");
    	sbSql.append("\n                                                                                         WHERE  TMS_6 <= 6                                                                              ");
    	sbSql.append("\n                                                                                      )                                                                                                 ");
        sbSql.append("\n                        GROUP BY TOR.RACER_NO                                                                                                                                           ");
    	sbSql.append("\n                      ) STTM                                                                                                                                                            ");
        sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                             ");
        sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                            ");
        sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                                                                                                                    ");
    	sbSql.append("\n 			                      TRVA.STND_YEAR                                                                                                                                                   ");
    	sbSql.append("\n 			                    , TRVA.MBR_CD                                                                                                                                                      ");
    	sbSql.append("\n 			                    , TRVA.TMS                                                                                                                                                         ");
    	sbSql.append("\n 			                    , TRVA.DAY_ORD                                                                                                                                                     ");
    	sbSql.append("\n 			                    , TRVA.RACE_NO                                                                                                                                                     ");
    	sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                                                                                                                                 ");
    	sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                                                                                                                    ");
    	sbSql.append("\n                         FROM     (                                                                                                                                                                ");
    	sbSql.append("\n                                    SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                                                                                                        ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                                                                                                           ");
    	sbSql.append("\n                                           , TRVA.TMS                                                                                                                                              ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                                                                                                                      ");
    	sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR                                                                                                                              ");
    	sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA                                                                                                                               ");
    	sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS                                                                                                                                ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                                                                                                       ");
    	sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                                                                                                       ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                                                                                                          ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                                                                                                             ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                                                                                                                     ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                                                                                                               ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                                                                                                               ");
    	sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
    	sbSql.append("\n                                                                                                        SELECT                                                                                     ");
    	sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
    	sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
    	sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
    	sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
    	sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
    	sbSql.append("\n                                                                                                                    SELECT                                                                         ");
    	sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
    	sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
    	sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
    	sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
    	sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
    	sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
    	sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
    	sbSql.append("\n                                                                                                                                SELECT                                                             ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
    	sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
    	sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
    	sbSql.append("\n                                                                                                                                                                SELECT                             ");
    	sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
    	sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
    	sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
    	sbSql.append("\n                                                                                                                                                           )                                       ");
        sbSql.append("\n                                                                                                                     			AND    TOR.MBR_CD       = ?                                        ");    
    	sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- ���� ���                     ");
    	sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- ��å�� �ƴ� ���             ");
    	sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
    	sbSql.append("\n                                                                                                                                GROUP BY                                                           ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                           ) TOR                                                                   ");
    	sbSql.append("\n                                                                                                               ) TOR                                                                               ");
    	sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                                                                          ");
    	sbSql.append("\n                                                                                                     )                                                                                             ");
    	sbSql.append("\n                                    GROUP BY                                                                                                                                                       ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                                                                                                        ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                                                                                                           ");
    	sbSql.append("\n                                           , TRVA.TMS                                                                                                                                              ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                                                                                                                      ");
    	sbSql.append("\n 			                     ) TRVA                                                                                                                                                            ");
    	sbSql.append("\n 			                   , (                                                                                                                                                                 ");
    	sbSql.append("\n                                    SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                                                                                                        ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                                                                                                           ");
    	sbSql.append("\n                                           , TRVA.TMS                                                                                                                                              ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                                                                                                          ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                                                                                                                      ");
    	sbSql.append("\n                                           , TAS .ACDNT_SCR                                                                                                                                        ");
    	sbSql.append("\n                                    FROM     TBED_RACE_VOIL_ACT TRVA                                                                                                                               ");
    	sbSql.append("\n                                           , TBEB_ACDNT_SCR     TAS                                                                                                                                ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                                    WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                                                                                                       ");
    	sbSql.append("\n                                    AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                                                                                                       ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                                                                                                          ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                                                                                                             ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                                                                                                         ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                                                                                                                     ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                                                                                                               ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                                                                                                               ");
    	sbSql.append("\n                                    AND    TRVA.VOIL_CD     IN ('110', '120')                                                                                                                      ");
    	sbSql.append("\n                                    AND    (TRVA.STND_YEAR, TRVA.MBR_CD, TRVA.TMS, TRVA.RACER_NO) IN (                                                                                             ");
    	sbSql.append("\n                                                                                                        SELECT                                                                                     ");
    	sbSql.append("\n                                                                                                                 TOR.STND_YEAR                                                                     ");
    	sbSql.append("\n                                                                                                               , TOR.MBR_CD                                                                        ");
    	sbSql.append("\n                                                                                                               , TOR.TMS                                                                           ");
    	sbSql.append("\n                                                                                                               , TOR.RACER_NO                                                                      ");
    	sbSql.append("\n                                                                                                        FROM   (                                                                                   ");
    	sbSql.append("\n                                                                                                                    SELECT                                                                         ");
    	sbSql.append("\n                                                                                                                             TOR.STND_YEAR                                                         ");
    	sbSql.append("\n                                                                                                                           , TOR.MBR_CD                                                            ");
    	sbSql.append("\n                                                                                                                           , TOR.TMS                                                               ");
    	sbSql.append("\n                                                                                                                           , TOR.RACER_NO                                                          ");
    	sbSql.append("\n                                                                                                                           , RANK() OVER (PARTITION BY TOR.RACER_NO                                ");
    	sbSql.append("\n                                                                                                                                              ORDER BY TOR.RACE_DAY DESC ) TMS_6                   ");
    	sbSql.append("\n                                                                                                                    FROM   (                                                                       ");
    	sbSql.append("\n                                                                                                                                SELECT                                                             ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                                       , MAX(TOR.RACE_DAY) RACE_DAY                                ");
    	sbSql.append("\n                                                                                                                                FROM   TBEB_ORGAN        TOR                                       ");
    	sbSql.append("\n                                                                                                                                WHERE  TOR .RACE_DAY    <= (                                       ");
    	sbSql.append("\n                                                                                                                                                                SELECT                             ");
    	sbSql.append("\n                                                                                                                                                                       MAX(TRDO.RACE_DAY) RACE_DAY ");
    	sbSql.append("\n                                                                                                                                                                FROM   TBEB_RACE_DAY_ORD TRDO      ");
    	sbSql.append("\n                                                                                                                                                                WHERE  TRDO.STND_YEAR = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.MBR_CD    = ?          ");
    	sbSql.append("\n                                                                                                                                                                AND    TRDO.TMS       = ?          ");
    	sbSql.append("\n                                                                                                                                                           )                                       ");
        sbSql.append("\n                                                                                                                     			AND    TOR.MBR_CD       = ?                                        ");    
    	sbSql.append("\n                                                                                                                                AND    TOR.ABSE_CD     <> '003'   -- ���� ���                     ");
    	sbSql.append("\n                                                                                                                                AND    TOR.IMMT_CLS_CD <> '003'    -- ��å�� �ƴ� ���             ");
    	sbSql.append("\n                                                                                                                                AND    TOR.STND_YEAR   >= ? - 1                                    ");
    	sbSql.append("\n                                                                                                                                GROUP BY                                                           ");
    	sbSql.append("\n                                                                                                                                         TOR.STND_YEAR                                             ");
    	sbSql.append("\n                                                                                                                                       , TOR.MBR_CD                                                ");
    	sbSql.append("\n                                                                                                                                       , TOR.TMS                                                   ");
    	sbSql.append("\n                                                                                                                                       , TOR.RACER_NO                                              ");
    	sbSql.append("\n                                                                                                                           ) TOR                                                                   ");
    	sbSql.append("\n                                                                                                               ) TOR                                                                               ");
    	sbSql.append("\n                                                                                                        WHERE  TMS_6 <= 6                                                                          ");
    	sbSql.append("\n                                                                                                     )                                                                                             ");
    	sbSql.append("\n                                  ) TRVB                                                                                                                                                           ");
    	sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                                                                                                              ");
    	sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                                                                                                              ");
        sbSql.append("\n                      ) TRVA                                                                                                                                                            ");
        sbSql.append("\n             WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                                                                                                                                  ");
        sbSql.append("\n             AND    TOR .MBR_CD       = TR  .MBR_CD                                                                                                                                     ");
        sbSql.append("\n             AND    TOR .TMS          = TR  .TMS                                                                                                                                        ");
        sbSql.append("\n             AND    TOR .DAY_ORD      = TR  .DAY_ORD                                                                                                                                    ");
        sbSql.append("\n             AND    TOR .RACE_NO      = TR  .RACE_NO                                                                                                                                    ");
        sbSql.append("\n             AND    TOR .STND_YEAR    = TRS .STND_YEAR                                                                                                                                  ");
        sbSql.append("\n             AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                                                                                                                               ");
        sbSql.append("\n             AND    TOR .RANK         = TRS .RANK                                                                                                                                       ");
        sbSql.append("\n             AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .TMS          = TRVA.TMS        (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .RACER_NO     = STTM.RACER_NO   (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR.ABSE_CD      <> '003'   -- ��å������ �ƴ� ���                                                                                                                 ");
        sbSql.append("\n             AND    TOR.IMMT_CLS_CD  <> '003'   -- ��å�� �ƴ� ���                                                                                                                     ");
        sbSql.append("\n             GROUP BY TOR.RACER_NO                                                                                                                                                          ");
    	sbSql.append("\n                    , STTM.TMS_6_AVG_STAR_TM                                                                                                                                            ");
        sbSql.append("\n           ) TMS_6                                                                                                                                                                      ");
        sbSql.append("\n         , (                                                                                                                                                                            ");
        sbSql.append("\n             -- 8ȸ�� ����                                                                                                                                                              ");
        sbSql.append("\n             SELECT                                                                                                                                                                     ");
        sbSql.append("\n                      RACER_NO                                                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 1, RANK)) TMS_8_RANK_1                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 2, RANK)) TMS_8_RANK_2                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 3, RANK)) TMS_8_RANK_3                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 4, RANK)) TMS_8_RANK_4                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 5, RANK)) TMS_8_RANK_5                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 6, RANK)) TMS_8_RANK_6                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 7, RANK)) TMS_8_RANK_7                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_8, 8, RANK)) TMS_8_RANK_8                                                                                                                          ");
        sbSql.append("\n             FROM   (                                                                                                                                                                   ");
        sbSql.append("\n                         SELECT                                                                                                                                                         ");
        sbSql.append("\n                                  TOR.*                                                                                                                                                 ");
        sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                                                                                                                ");
    	sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC                                                                                                           ");
    	sbSql.append("\n		                                                  , TOR.RACE_NO  DESC ) TMS_8                                                                                                    ");
        sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
        sbSql.append("\n                         WHERE  TOR.ABSE_CD     <> '003'    -- ��å������ �ƴ� ���                                                                                                     ");
        sbSql.append("\n                         AND    TOR.IMMT_CLS_CD <> '003'    -- ��å�� �ƴ� ���                                                                                                         ");
        sbSql.append("\n                         AND    TOR.MBR_CD       = ?                                                                                                                                    ");
        sbSql.append("\n                         AND    TOR.RACE_DAY    <= (                                                                                                                                    "); 
        sbSql.append("\n                                                     SELECT                                                                                                                             ");
        sbSql.append("\n                                                            MAX(TRDO.RACE_DAY) RACE_DAY                                                                                                 ");
        sbSql.append("\n                                                     FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                      ");
        sbSql.append("\n                                                     WHERE  TRDO.STND_YEAR = ?                                                                                                          ");
        sbSql.append("\n                                                     AND    TRDO.MBR_CD    = ?                                                                                                          ");
        sbSql.append("\n                                                     AND    TRDO.TMS       = ?                                                                                                          ");
        sbSql.append("\n                                                   )                                                                                                                                    "); 
        sbSql.append("\n                   )                                                                                                                                                                    ");
        sbSql.append("\n             WHERE   TMS_8 <= 8                                                                                                                                                         ");
        sbSql.append("\n             GROUP BY RACER_NO                                                                                                                                                          ");
        sbSql.append("\n           ) TMS_8                                                                                                                                                                      ");
        sbSql.append("\n         , (                                                                                                                                                                            ");
        sbSql.append("\n             -- �⵵�� ����                                                                                                                                                             ");
        sbSql.append("\n             SELECT                                                                                                                                                                     ");
        sbSql.append("\n                      TOR.RACER_NO                                                                                                                                                      ");
        sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS))                RUN_CNT                                                                                                     ");
        sbSql.append("\n                    , COUNT(DISTINCT(TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT                                                                                                ");
        sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                                                                                                    ");
        sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '001', 1, NULL))              BF_RACE_CNT                                                                                                 ");
        sbSql.append("\n                    , SUM(DECODE(TRST.QURT_CD, '002', 1, NULL))              AF_RACE_CNT                                                                                                 ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.ACDNT_SCR, NULL)), 0) BF_ACDNT_SCR                                                                                        ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.ACDNT_SCR, NULL)), 0) AF_ACDNT_SCR                                                                                        ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0))                       , 0) RANK_1                                                                                              ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0))                       , 0) RANK_2                                                                                              ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0))                       , 0) RANK_3                                                                                              ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 4, 1, 0))                       , 0) RANK_4                                                                                              ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 5, 1, 0))                       , 0) RANK_5                                                                                              ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TOR.RANK, 6, 1, 0))                       , 0) RANK_6                                                                                              ");
        sbSql.append("\n                    , NVL(STTM.AVG_STAR_TM                                     , 0) AVG_STAR_TM                                                                                         ");
        sbSql.append("\n                    , NVL(SUM(TRS.RACE_SCR)                                    , 0) AMU_RANK_SCR                                                                                        ");
        sbSql.append("\n                    , NVL(SUM(TRVA.ACDNT_SCR)                                  , 0) AMU_ACDNT_SCR                                                                                       ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.F_CNT, NULL))    , 0) BF_F_CNT                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.F_CNT, NULL))    , 0) AF_F_CNT                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '001', TRVA.L_CNT, NULL))    , 0) BF_L_CNT                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(DECODE(TRST.QURT_CD, '002', TRVA.L_CNT, NULL))    , 0) AF_L_CNT                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(TRVA.ABSE_CNT      )                             , 0) ABSE_CNT                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_1_CNT)                             , 0) RACE_ESC_1_CNT                                                                                      ");
        sbSql.append("\n                    , NVL(SUM(TRVA.RACE_ESC_2_CNT)                             , 0) RACE_ESC_2_CNT                                                                                      ");
        sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_ELIM_CNT )                             , 0) FOUL_ELIM_CNT                                                                                       ");
        sbSql.append("\n                    , NVL(SUM(TRVA.ELIM_CNT      )                             , 0) ELIM_CNT                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(TRVA.FOUL_WARN_CNT )                             , 0) FOUL_WARN_CNT                                                                                       ");
        sbSql.append("\n                    , NVL(SUM(TRVA.WARN_CNT      )                             , 0) WARN_CNT                                                                                            ");
        sbSql.append("\n                    , NVL(SUM(TRVA.ATTEN_CNT     )                             , 0) ATTEN_CNT                                                                                           ");
        sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                            ");
        sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                                                                                            ");
        sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                             ");
        sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                            ");
    	sbSql.append("\n                    , TBEB_RECD_STND_TERM TRST                                                                                                                                          ");
        sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                             ");
    	sbSql.append("\n 			                      TRVA.STND_YEAR                                                            ");
    	sbSql.append("\n 			                    , TRVA.MBR_CD                                                               ");
    	sbSql.append("\n 			                    , TRVA.TMS                                                                  ");
    	sbSql.append("\n 			                    , TRVA.DAY_ORD                                                              ");
    	sbSql.append("\n 			                    , TRVA.RACE_NO                                                              ");
    	sbSql.append("\n 			                    , TRVA.RACE_REG_NO                                                          ");
    	sbSql.append("\n 			                    , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                             ");
    	sbSql.append("\n                                , TRVA.F_CNT                                                                ");
    	sbSql.append("\n                                , TRVA.L_CNT                                                                ");
    	sbSql.append("\n                                , TRVA.ABSE_CNT                                                             ");
    	sbSql.append("\n                                , TRVA.RACE_ESC_1_CNT                                                       ");
    	sbSql.append("\n                                , TRVA.RACE_ESC_2_CNT                                                       ");
    	sbSql.append("\n                                , TRVA.FOUL_ELIM_CNT                                                        ");
    	sbSql.append("\n                                , TRVA.ELIM_CNT                                                             ");
    	sbSql.append("\n                                , TRVA.FOUL_WARN_CNT                                                        ");
    	sbSql.append("\n                                , TRVA.WARN_CNT                                                             ");
    	sbSql.append("\n                                , TRVA.ATTEN_CNT                                                            ");
    	sbSql.append("\n                         FROM     (                                                                         ");
    	sbSql.append("\n             			            SELECT                                                                  ");
    	sbSql.append("\n                                             TRVA.STND_YEAR                                                 ");
    	sbSql.append("\n                                           , TRVA.MBR_CD                                                    ");
    	sbSql.append("\n                                           , TRVA.TMS                                                       ");
    	sbSql.append("\n                                           , TRVA.DAY_ORD                                                   ");
    	sbSql.append("\n                                           , TRVA.RACE_NO                                                   ");
    	sbSql.append("\n                                           , TRVA.RACE_REG_NO                                               ");
    	sbSql.append("\n                                           , SUM(ACDNT_SCR) ACDNT_SCR                                       ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '110', 1, NULL)) F_CNT                ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '120', 1, NULL)) L_CNT                ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '130', 1, NULL)) ABSE_CNT             ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '995', 1, NULL)) RACE_ESC_1_CNT       ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '997', 1, NULL)) RACE_ESC_2_CNT       ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '125', 1, NULL)) FOUL_ELIM_CNT        ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '140', 1, NULL)) ELIM_CNT             ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '150', 1, NULL)) FOUL_WARN_CNT        ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '210', 1, NULL)) WARN_CNT             ");
    	sbSql.append("\n                                           , SUM(DECODE(TRVA.VOIL_CD, '220', 1, NULL)) ATTEN_CNT            ");
    	sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                        ");
    	sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                         ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                         ");
    	sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                ");
    	sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                  ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                   ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                      ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                  ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                  ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                              ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                        ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                        ");
    	sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                            ");
        sbSql.append("\n                         			AND    TRVA.MBR_CD       = ?                                            ");
    	sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                            ");
    	sbSql.append("\n             			                                            SELECT                                  ");
    	sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY      ");
    	sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO           ");
    	sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.TMS       = ?               ");
    	sbSql.append("\n             			                                       )                                            ");
    	sbSql.append("\n             			            GROUP BY                                                                ");
    	sbSql.append("\n             			                     TRVA.STND_YEAR                                                 ");
    	sbSql.append("\n             			                   , TRVA.MBR_CD                                                    ");
    	sbSql.append("\n             			                   , TRVA.TMS                                                       ");
    	sbSql.append("\n             			                   , TRVA.DAY_ORD                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_NO                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                               ");
    	sbSql.append("\n 			                     ) TRVA                                                                     ");
    	sbSql.append("\n 			                   , (                                                                          ");
    	sbSql.append("\n             			            SELECT                                                                  ");
    	sbSql.append("\n             			                     TRVA.STND_YEAR                                                 ");
    	sbSql.append("\n             			                   , TRVA.MBR_CD                                                    ");
    	sbSql.append("\n             			                   , TRVA.TMS                                                       ");
    	sbSql.append("\n             			                   , TRVA.DAY_ORD                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_NO                                                   ");
    	sbSql.append("\n             			                   , TRVA.RACE_REG_NO                                               ");
    	sbSql.append("\n             			                   , TAS .ACDNT_SCR                                                 ");
    	sbSql.append("\n             			            FROM     TBED_RACE_VOIL_ACT TRVA                                        ");
    	sbSql.append("\n             			                   , TBEB_ACDNT_SCR     TAS                                         ");
    	sbSql.append("\n                                           , TBEB_ORGAN         TOR                                         ");
    	sbSql.append("\n             			            WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                                ");
    	sbSql.append("\n             			            AND    TRVA.VOIL_CD      = TAS.VOIL_CD                                  ");
        sbSql.append("\n                                    AND    TRVA.STND_YEAR    = TOR.STND_YEAR                                ");
        sbSql.append("\n                                    AND    TRVA.MBR_CD       = TOR.MBR_CD                                   ");
        sbSql.append("\n                                    AND    TRVA.TMS          = TOR.TMS                                      ");
        sbSql.append("\n                                    AND    TRVA.DAY_ORD      = TOR.DAY_ORD                                  ");
        sbSql.append("\n                                    AND    TRVA.RACE_NO      = TOR.RACE_NO                                  ");
        sbSql.append("\n                                    AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                              ");
        sbSql.append("\n                                    AND    TOR.ABSE_CD      <> '003'                                        ");
        sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD  <> '003'                                        ");
    	sbSql.append("\n                                    AND    TRVA.VOIL_CD     IN ('110', '120')                               ");
    	sbSql.append("\n             			            AND    TRVA.STND_YEAR    = ?                                            ");
        sbSql.append("\n                         			AND    TRVA.MBR_CD       = ?                                            ");
    	sbSql.append("\n             			            AND    TRVA.RACE_DAY    <= (                                            ");
    	sbSql.append("\n             			                                            SELECT                                  ");
    	sbSql.append("\n             			                                                   MAX(TRDO.RACE_DAY) RACE_DAY      ");
    	sbSql.append("\n             			                                            FROM   TBEB_RACE_DAY_ORD TRDO           ");
    	sbSql.append("\n             			                                            WHERE  TRDO.STND_YEAR = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.MBR_CD    = ?               ");
    	sbSql.append("\n             			                                            AND    TRDO.TMS       = ?               ");
    	sbSql.append("\n             			                                       )                                            ");
    	sbSql.append("\n                                  ) TRVB                                                                    ");
    	sbSql.append("\n                        WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.TMS         = TRVB.TMS        (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                       ");
    	sbSql.append("\n 			            AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                       ");
        sbSql.append("\n                      ) TRVA                                                                                                                                                            ");
    	sbSql.append("\n                    , (                                                                                                                                                                 ");
    	sbSql.append("\n                         SELECT                                                                                                                                                         ");
    	sbSql.append("\n                                  TOR.RACER_NO                                                                                                                                          ");
    	sbSql.append("\n                                , NVL(ROUND(AVG(TOR.STAR_TM), 2), 0) AVG_STAR_TM                                                                                                                  ");
    	sbSql.append("\n                         FROM   TBEB_ORGAN        TOR                                                                                                                                   ");
    	sbSql.append("\n                         WHERE  TOR .STND_YEAR    = ?                                                                                                                                   ");
        sbSql.append("\n                         AND    TOR .MBR_CD       = ?                                                                                                                                   ");
    	sbSql.append("\n                         AND    TOR .RACE_DAY    <= (                                                                                                                                   ");
    	sbSql.append("\n                                                         SELECT                                                                                                                         ");
    	sbSql.append("\n                                                                MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                                         FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                                         WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                                         AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                                         AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                                    )                                                                                                                                   ");
        sbSql.append("\n                         GROUP BY TOR.RACER_NO                                                                                                                                          ");
    	sbSql.append("\n                      ) STTM                                                                                                                                                            ");
        sbSql.append("\n             WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                                                                                                                                  ");
        sbSql.append("\n             AND    TOR .MBR_CD       = TR  .MBR_CD                                                                                                                                     ");
        sbSql.append("\n             AND    TOR .TMS          = TR  .TMS                                                                                                                                        ");
        sbSql.append("\n             AND    TOR .DAY_ORD      = TR  .DAY_ORD                                                                                                                                    ");
        sbSql.append("\n             AND    TOR .RACE_NO      = TR  .RACE_NO                                                                                                                                    ");
        sbSql.append("\n             AND    TOR .STND_YEAR    = TRT .STND_YEAR                                                                                                                                  ");
        sbSql.append("\n             AND    TOR .MBR_CD       = TRT .MBR_CD                                                                                                                                     ");
        sbSql.append("\n             AND    TOR .TMS          = TRT .TMS                                                                                                                                        ");
        sbSql.append("\n             AND    TOR .STND_YEAR    = TRS .STND_YEAR                                                                                                                                  ");
        sbSql.append("\n             AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                                                                                                                               ");
        sbSql.append("\n             AND    TOR .RANK         = TRS .RANK                                                                                                                                       ");
        sbSql.append("\n             AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .TMS          = TRVA.TMS        (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                                                                                                             ");
        sbSql.append("\n             AND    TOR .RACER_NO     = STTM.RACER_NO   (+)                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .STND_YEAR    = TRST.STND_YEAR                                                                                                                                  ");
    	sbSql.append("\n             AND    TRT .STR_DT BETWEEN TRST.STR_DT                                                                                                                                     ");
    	sbSql.append("\n                                    AND TRST.END_DT                                                                                                                                     ");
        sbSql.append("\n             AND    TOR .ABSE_CD     <> '003'   -- ��å������ �ƴ� ���                                                                                                                 ");
        sbSql.append("\n             AND    TOR .IMMT_CLS_CD <> '003'   -- ��å�� �ƴ� ���                                                                                                                     ");
        sbSql.append("\n             AND    TOR .STND_YEAR    = ?                                                                                                                                               ");
        sbSql.append("\n             AND    TOR .MBR_CD       = ?                                                                                                                                               ");
        sbSql.append("\n             AND    TOR .RACE_DAY    <= (                                                                                                                                               ");
        sbSql.append("\n                                             SELECT                                                                                                                                     ");
        sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                                         ");
        sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                              ");
        sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                                  ");
        sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                                  ");
        sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                                  ");
        sbSql.append("\n                                        )                                                                                                                                               ");
        sbSql.append("\n             GROUP BY TOR .RACER_NO                                                                                                                                                     ");
        sbSql.append("\n                    , STTM.AVG_STAR_TM                                                                                                                                                     ");
        sbSql.append("\n           ) YEAR                                                                                                                                                                       ");
        sbSql.append("\n         , (                                                                                                                                                                            ");
        sbSql.append("\n             -- ��ȸ�� ������                                                                                                                                                           ");
        sbSql.append("\n             SELECT                                                                                                                                                                     ");
        sbSql.append("\n                      RACER_NO                                                                                                                                                          ");
        sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, TMS   )) BF_TMS                                                                                                                              ");
        sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, TMS   )) BF_BF_TMS                                                                                                                           ");
        sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, TMS   )) BF_BF_BF_TMS                                                                                                                        ");
        sbSql.append("\n                    , MIN(DECODE(TMS_3, 1, MBR_CD)) BF_MBR_CD                                                                                                                           ");
        sbSql.append("\n                    , MIN(DECODE(TMS_3, 2, MBR_CD)) BF_BF_MBR_CD                                                                                                                        ");
        sbSql.append("\n                    , MIN(DECODE(TMS_3, 3, MBR_CD)) BF_BF_BF_MBR_CD                                                                                                                     ");
        sbSql.append("\n             FROM   (                                                                                                                                                                   ");
        sbSql.append("\n                         SELECT                                                                                                                                                         ");
        sbSql.append("\n                                  TOR.STND_YEAR                                                                                                                                         ");
        sbSql.append("\n                                , TOR.MBR_CD                                                                                                                                            ");
        sbSql.append("\n                                , TOR.TMS                                                                                                                                               ");
        sbSql.append("\n                                , TOR.RACER_NO                                                                                                                                          ");
        sbSql.append("\n                                , RANK() OVER (PARTITION BY TOR.RACER_NO                                                                                                                ");
        sbSql.append("\n                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                                                                                                   ");
        sbSql.append("\n                         FROM   (                                                                                                                                                       ");
        sbSql.append("\n                                     SELECT                                                                                                                                             ");
        sbSql.append("\n                                              TOR.STND_YEAR                                                                                                                             ");
        sbSql.append("\n                                            , TOR.MBR_CD                                                                                                                                ");
        sbSql.append("\n                                            , TOR.TMS                                                                                                                                   ");
        sbSql.append("\n                                            , TOR.RACER_NO                                                                                                                              ");
        sbSql.append("\n                                            , MAX(TOR.RACE_DAY) RACE_DAY                                                                                                                ");
        sbSql.append("\n                                     FROM   TBEB_ORGAN        TOR                                                                                                                       ");
        sbSql.append("\n                                     WHERE  TOR.RACE_DAY     <= (                                                                                                                       ");
        sbSql.append("\n                                                                     SELECT                                                                                                             ");
        sbSql.append("\n                                                                            MAX(TRDO.RACE_DAY) RACE_DAY                                                                                 ");
        sbSql.append("\n                                                                     FROM   TBEB_RACE_DAY_ORD TRDO                                                                                      ");
        sbSql.append("\n                                                                     WHERE  TRDO.STND_YEAR = ?                                                                                          ");
        sbSql.append("\n                                                                     AND    TRDO.MBR_CD    = ?                                                                                          ");
        sbSql.append("\n                                                                     AND    TRDO.TMS       = ?                                                                                          ");
        sbSql.append("\n                                                                )                                                                                                                       ");
        sbSql.append("\n                                     AND    TOR .MBR_CD       = ?                                                                                                                       ");
        sbSql.append("\n                                     AND    TOR .ABSE_CD     <> '003'   -- ��å������ �ƴ� ���                                                                                         ");
        sbSql.append("\n                                     AND    TOR .IMMT_CLS_CD <> '003'   -- ��å�� �ƴ� ���                                                                                             ");
        sbSql.append("\n                                     GROUP BY                                                                                                                                           ");
        sbSql.append("\n                                              TOR.STND_YEAR                                                                                                                             ");
        sbSql.append("\n                                            , TOR.MBR_CD                                                                                                                                ");
        sbSql.append("\n                                            , TOR.TMS                                                                                                                                   ");
        sbSql.append("\n                                            , TOR.RACER_NO                                                                                                                              ");
        sbSql.append("\n                                ) TOR                                                                                                                                                   ");
        sbSql.append("\n                    )                                                                                                                                                                   ");
        sbSql.append("\n             GROUP BY RACER_NO                                                                                                                                                          ");
        sbSql.append("\n           ) TMS_3                                                                                                                                                                      ");
        sbSql.append("\n         , (                                                                                                                                                                            ");
        sbSql.append("\n             --  ��ü ����                                                                                                                                                              ");
        sbSql.append("\n             SELECT                                                                                                                                                                     ");
        sbSql.append("\n                      RACER_NO                                                                                                                                                          ");
        sbSql.append("\n                    , COUNT(*) TOT_RACE_CNT                                                                                                                                             ");
        sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                            ");
        sbSql.append("\n             WHERE  TOR.RACE_DAY     <= (                                                                                                                                               ");
        sbSql.append("\n                                             SELECT                                                                                                                                     ");
        sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                                         ");
        sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                              ");
        sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                                  ");
        sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                                  ");
        sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                                  ");
        sbSql.append("\n                                        )                                                                                                                                               ");
        sbSql.append("\n             AND    TOR .MBR_CD       = ?                                                                                                                                               ");
        sbSql.append("\n             AND    TOR .STND_YEAR    = ?                                                                                                                                               ");
        sbSql.append("\n             AND    TOR .ABSE_CD     <> '003'   -- ��å�� �ƴ� ���                                                                                                                     ");
        sbSql.append("\n             GROUP BY RACER_NO                                                                                                                                                          ");
        sbSql.append("\n           ) TOT                                                                                                                                                                        ");
        sbSql.append("\n WHERE   TRM.RACER_NO = TMS_6.RACER_NO(+)                                                                                                                                               ");
        sbSql.append("\n AND     TRM.RACER_NO = TMS_8.RACER_NO(+)                                                                                                                                               ");
        sbSql.append("\n AND     TRM.RACER_NO = YEAR .RACER_NO(+)                                                                                                                                               ");
        sbSql.append("\n AND     TRM.RACER_NO = TMS_3.RACER_NO(+)                                                                                                                                               ");
        sbSql.append("\n AND     TRM.RACER_NO = TOT  .RACER_NO(+)                                                                                                                                               ");

        if ( nSize > 0 )
        	sbSql.append("\n AND     TRM.RACER_NO IN (");
        for ( i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 )
        	sbSql.append(")");
        
    	PosParameter paramMbr = new PosParameter();
        i = 0;
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, SESSION_USER_ID );
        paramMbr.setValueParamter(i++, SESSION_USER_ID );
        
        // 6ȸ��
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);
        
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);
        
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);
        
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, sMbrCd   ); 
        paramMbr.setValueParamter(i++, sStndYear);  

        // 8���� ����
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        
        // �⵵�� ����
        paramMbr.setValueParamter(i++, sStndYear);
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );
        
        paramMbr.setValueParamter(i++, sStndYear);
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );
        
        paramMbr.setValueParamter(i++, sStndYear);
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );
        
        paramMbr.setValueParamter(i++, sStndYear);
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );

        // ��ȸ��
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        
        // �⵵�� ������
        paramMbr.setValueParamter(i++, sStndYear);  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sTms     );  
        paramMbr.setValueParamter(i++, sMbrCd   );  
        paramMbr.setValueParamter(i++, sStndYear);  

        dmlcount = this.getDao("boadao").insertByQueryStatement(sbSql.toString(), paramMbr);

        return dmlcount;
    }

    /**
     * <p> ȸ���� ���� ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();

    	sbSql.append("\n DELETE                                                      ");
    	sbSql.append("\n FROM   TBEB_RACER_RECD_ACCU_SUM                             ");
    	sbSql.append("\n WHERE  STND_YEAR = ?                        -- ���س⵵     ");
    	sbSql.append("\n AND    MBR_CD   IN ('000', ?)               -- �������ڵ�   ");
    	sbSql.append("\n AND    TMS      IN ('0'  , ?)               -- ȸ��         ");
    	sbSql.append("\n AND    APLY_DT   = (                                        ");
    	sbSql.append("\n                       SELECT MAX(TRDO.RACE_DAY) RACE_DAY    ");
    	sbSql.append("\n                       FROM   TBEB_RACE_DAY_ORD TRDO         ");
    	sbSql.append("\n                       WHERE  TRDO.STND_YEAR = ?             ");
    	sbSql.append("\n                       AND    TRDO.MBR_CD    = ?             ");
    	sbSql.append("\n                       AND    TRDO.TMS       = ?             ");
    	sbSql.append("\n                    )                                        ");
    	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 )
        	sbSql.append("\n AND    RACER_NO IN (");
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 )
        	sbSql.append(")");

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );

        int dmlcount = this.getDao("boadao").updateByQueryStatement(sbSql.toString(), param);
        return dmlcount;
    }


    /**
     * <p> ȸ���� ���� ���� ��� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertMotRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n INSERT INTO TBEB_MOT_RECD_ACCU_SUM                                                                                                                                         ");
    	sbSql.append("\n (                                                                                                                                                                          ");
    	sbSql.append("\n          STND_YEAR              -- ���س⵵                                                                                                                                ");
    	sbSql.append("\n        , MBR_CD                 -- �������ڵ�                                                                                                                              ");
    	sbSql.append("\n        , TMS                    -- ȸ��                                                                                                                                    ");
    	sbSql.append("\n        , MOT_NO                 -- ���͹�ȣ                                                                                                                                ");
    	sbSql.append("\n        , RUN_CNT                -- ����ȸ��                                                                                                                                ");
    	sbSql.append("\n        , RACE_DAY_CNT           -- �����ϼ�                                                                                                                                ");
    	sbSql.append("\n        , RACE_CNT               -- ����Ƚ��                                                                                                                                ");
    	sbSql.append("\n        , RANK_1_CNT             -- 1��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_2_CNT             -- 2��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_3_CNT             -- 3��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_4_CNT             -- 4��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_5_CNT             -- 5��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_6_CNT             -- 6��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , AMU_RANK_SCR           -- ����������                                                                                                                              ");
    	sbSql.append("\n        , AVG_RANK_SCR           -- ���������                                                                                                                              ");
    	sbSql.append("\n        , WIN_RATIO              -- �����·�                                                                                                                                ");
    	sbSql.append("\n        , HIGH_RANK_RATIO        -- ������                                                                                                                                  ");
    	sbSql.append("\n        , HIGH_3_RANK_RATIO      -- 3������                                                                                                                                 ");
    	sbSql.append("\n        , TMS_3_ITRDT_RUN_TM     -- 3ȸ �Ұ� Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , AVG_ITRDT_RUN_TM       -- ��� �Ұ�Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , MAX_ITRDT_RUN_TM       -- �ְ� �Ұ�Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , MIN_ITRDT_RUN_TM       -- �ּ� �Ұ�Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , ITRDT_RUN_TM_DVTN      -- �Ұ�Ÿ������                                                                                                                            ");
    	sbSql.append("\n        , INST_ID                -- �ۼ���ID                                                                                                                                ");
    	sbSql.append("\n        , INST_DTM               -- �ۼ��Ͻ�                                                                                                                                ");
    	sbSql.append("\n        , UPDT_ID                -- ������ID                                                                                                                                ");
    	sbSql.append("\n        , UPDT_DTM               -- �����Ͻ�                                                                                                                                ");
    	sbSql.append("\n )                                                                                                                                                                          ");
    	sbSql.append("\n SELECT                                                                                                                                                                     ");
    	sbSql.append("\n          DISTINCT(?)                                                                                                                                                       ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , TE   .EQUIP_NO                                                                                                                                                    ");
    	sbSql.append("\n        , YEAR .RUN_CNT                                                                                                                                                     ");
    	sbSql.append("\n        , YEAR .RACE_DAY_CNT                                                                                                                                                ");
    	sbSql.append("\n        , YEAR .RACE_CNT                                                                                                                                                    ");
    	sbSql.append("\n        , YEAR .RANK_1                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_2                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_3                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_4                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_5                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_6                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .AMU_RANK_SCR                                                                                                                                                ");
    	sbSql.append("\n        , YEAR .AVG_RANK_SCR                                                                                                                                                ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1) / YEAR .RACE_CNT * 100, 990.9))                                                                                                       ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2) / YEAR .RACE_CNT * 100, 990.9))                                                                                        ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2 + YEAR .RANK_3) / YEAR .RACE_CNT * 100, 990.9))                                                                         ");
    	sbSql.append("\n        , TMS_3.TMS_3_ITRDT_RUN_TM                                                                                                                                          ");
    	sbSql.append("\n        , MOT  .AVG_ITRDT_RUN_TM                                                                                                                                            ");
    	sbSql.append("\n        , MOT  .MAX_ITRDT_RUN_TM                                                                                                                                            ");
    	sbSql.append("\n        , MOT  .MIN_ITRDT_RUN_TM                                                                                                                                            ");
    	sbSql.append("\n        , MOT  .ITRDT_RUN_TM_DVTN                                                                                                                                           ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                                                                                           ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                                                                                           ");
    	sbSql.append("\n FROM     TBEF_EQUIP   TE                                                                                                                                                   ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- �⵵�� ����                                                                                                                                                 ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                                                                                ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS))                RUN_CNT                                                                                         ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT                                                                                    ");
    	sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                                                                                        ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 1, 1, 0))                        RANK_1                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 2, 1, 0))                        RANK_2                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 3, 1, 0))                        RANK_3                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 4, 1, 0))                        RANK_4                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 5, 1, 0))                        RANK_5                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 6, 1, 0))                        RANK_6                                                                                          ");
    	sbSql.append("\n                    , SUM(TRS.RACE_SCR)                                     AMU_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TRS.RACE_SCR), 90.99))               AVG_RANK_SCR                                                                                    ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                 ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR      = TR  .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TR  .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TR  .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .DAY_ORD        = TR  .DAY_ORD                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_NO        = TR  .RACE_NO                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRT .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TRT .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TRT .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRS .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD   = TRS .RACE_DGRE_CD                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RANK           = TRS .RANK                                                                                                                         ");
    	sbSql.append("\n             AND    TOR .ABSE_CD       <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .IMMT_CLS_CD   <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) YEAR                                                                                                                                                            ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 3ȸ�� ����                                                                                                                                                  ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99)) TMS_3_ITRDT_RUN_TM                                                                                       ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.MOT_NO) IN (                                                                                                   ");
    	sbSql.append("\n                                                                             SELECT                                                                                         ");
    	sbSql.append("\n                                                                                      TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                    , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                    , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                    , TOR.MOT_NO                                                                            ");
    	sbSql.append("\n                                                                             FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                         SELECT                                                                             ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                , TOR.MOT_NO                                                                ");
    	sbSql.append("\n                                                                                                , RANK() OVER (PARTITION BY TOR.MOT_NO                                      ");
    	sbSql.append("\n                                                                                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                       ");
    	sbSql.append("\n                                                                                         FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                     SELECT                                                                 ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.MOT_NO                                                    ");
    	sbSql.append("\n                                                                                                            , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                     FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                     WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                     SELECT                                 ");
    	sbSql.append("\n                                                                                                                                            MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                     FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                     WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                )                                           ");
    	sbSql.append("\n                                                                                                     AND    TOR.MBR_CD       = ?                                            ");
    	sbSql.append("\n                                                                                                     AND    TOR.ABSE_CD     <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.IMMT_CLS_CD <> '003'                                        ");
    	sbSql.append("\n                                                                                                     GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.MOT_NO                                                    ");
    	sbSql.append("\n                                                                                                ) TOR                                                                       ");
    	sbSql.append("\n                                                                                    ) TOR                                                                                   ");
    	sbSql.append("\n                                                                             WHERE TMS_3 <= 3                                                                               ");
    	sbSql.append("\n                                                                        )                                                                                                   ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) TMS_3                                                                                                                                                           ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- ��ռҰ�Ÿ��                                                                                                                                                ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      MOT_NO                                                                                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY MOT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) MOT                                                                                                                                                             ");
    	sbSql.append("\n WHERE  TE   .EQUIP_TPE_CD = 'M'                                                                                                                                            ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = MOT  .MOT_NO(+)                                                                                                                                ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = YEAR .MOT_NO(+)                                                                                                                                ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = TMS_3.MOT_NO(+)                                                                                                                                ");

        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    MOT.MOT_NO IN (                                        ");
        	sbSql.append("\n                         SELECT                                ");
        	sbSql.append("\n                                 TOR.MOT_NO                    ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?          ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?          ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?          ");
        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'      ");
        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'      ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (             ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                      )                                        ");
        }
        
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        if ( nSize > 0 ) {
	        param.setValueParamter(i++, sStndYear);
	        param.setValueParamter(i++, sMbrCd   );
	        param.setValueParamter(i++, sTms     );
        }

        int dmlcount = this.getDao("boadao").insertByQueryStatement(sbSql.toString(), param);
        
        
        
        return dmlcount;
    }

    /**
     * <p> ȸ���� ���� ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteMotRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();

        sbSql.append("\n DELETE FROM TBEB_MOT_RECD_ACCU_SUM                  ");
        sbSql.append("\n WHERE  STND_YEAR = ?                                ");
        sbSql.append("\n AND    MBR_CD    = ?                                ");
        sbSql.append("\n AND    TMS       = ?                                ");

        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();
        
        if ( nSize > 0 ) {
        	sbSql.append("\n AND    MOT_NO IN (                                        ");
        	sbSql.append("\n                         SELECT                                ");
        	sbSql.append("\n                                 TOR.MOT_NO                 ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?          ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?          ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?          ");
        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'      ");
        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'      ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (             ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                  )                                        ");
        }

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        if ( nSize > 0 ) {
	        param.setWhereClauseParameter(i++, sStndYear);
	        param.setWhereClauseParameter(i++, sMbrCd   );
	        param.setWhereClauseParameter(i++, sTms     );
        }

        int dmlcount = this.getDao("boadao").updateByQueryStatement(sbSql.toString(), param);
        return dmlcount;
    }



    /**
     * <p> ȸ���� ��Ʈ ���� ��� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertBoatRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n INSERT INTO TBEB_BOAT_RECD_ACCU_SUM                                                                                                                                        ");
    	sbSql.append("\n (                                                                                                                                                                          ");
    	sbSql.append("\n          STND_YEAR              -- ���س⵵                                                                                                                                ");
    	sbSql.append("\n        , MBR_CD                 -- �������ڵ�                                                                                                                              ");
    	sbSql.append("\n        , TMS                    -- ȸ��                                                                                                                                    ");
    	sbSql.append("\n        , BOAT_NO                -- ��Ʈ��ȣ                                                                                                                                ");
    	sbSql.append("\n        , RUN_CNT                -- ����ȸ��                                                                                                                                ");
    	sbSql.append("\n        , RACE_DAY_CNT           -- �����ϼ�                                                                                                                                ");
    	sbSql.append("\n        , RACE_CNT               -- ����Ƚ��                                                                                                                                ");
    	sbSql.append("\n        , RANK_1_CNT             -- 1��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_2_CNT             -- 2��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_3_CNT             -- 3��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_4_CNT             -- 4��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_5_CNT             -- 5��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , RANK_6_CNT             -- 6��ȸ��                                                                                                                                 ");
    	sbSql.append("\n        , AMU_RANK_SCR           -- ����������                                                                                                                              ");
    	sbSql.append("\n        , AVG_RANK_SCR           -- ���������                                                                                                                              ");
    	sbSql.append("\n        , WIN_RATIO              -- �����·�                                                                                                                                ");
    	sbSql.append("\n        , HIGH_RANK_RATIO        -- ������                                                                                                                                  ");
    	sbSql.append("\n        , HIGH_3_RANK_RATIO      -- 3������                                                                                                                                 ");
    	sbSql.append("\n        , TMS_3_ITRDT_RUN_TM     -- 3ȸ �Ұ� Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , AVG_ITRDT_RUN_TM       -- ��� �Ұ�Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , MAX_ITRDT_RUN_TM       -- �ְ� �Ұ�Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , MIN_ITRDT_RUN_TM       -- �ּ� �Ұ�Ÿ��                                                                                                                           ");
    	sbSql.append("\n        , ITRDT_RUN_TM_DVTN      -- �Ұ�Ÿ������                                                                                                                            ");
    	sbSql.append("\n        , INST_ID                -- �ۼ���ID                                                                                                                                ");
    	sbSql.append("\n        , INST_DTM               -- �ۼ��Ͻ�                                                                                                                                ");
    	sbSql.append("\n        , UPDT_ID                -- ������ID                                                                                                                                ");
    	sbSql.append("\n        , UPDT_DTM               -- �����Ͻ�                                                                                                                                ");
    	sbSql.append("\n )                                                                                                                                                                          ");
    	sbSql.append("\n SELECT                                                                                                                                                                     ");
    	sbSql.append("\n          DISTINCT(?)                                                                                                                                                       ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , TE   .EQUIP_NO                                                                                                                                                     ");
    	sbSql.append("\n        , YEAR .RUN_CNT                                                                                                                                                     ");
    	sbSql.append("\n        , YEAR .RACE_DAY_CNT                                                                                                                                                ");
    	sbSql.append("\n        , YEAR .RACE_CNT                                                                                                                                                    ");
    	sbSql.append("\n        , YEAR .RANK_1                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_2                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_3                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_4                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_5                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .RANK_6                                                                                                                                                      ");
    	sbSql.append("\n        , YEAR .AMU_RANK_SCR                                                                                                                                                ");
    	sbSql.append("\n        , YEAR .AVG_RANK_SCR                                                                                                                                                ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1) / YEAR .RACE_CNT * 100, 990.9))                                                                                                       ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2) / YEAR .RACE_CNT * 100, 990.9))                                                                                        ");
    	sbSql.append("\n        , TRIM(TO_CHAR((YEAR .RANK_1 + YEAR .RANK_2 + YEAR .RANK_3) / YEAR .RACE_CNT * 100, 990.9))                                                                         ");
    	sbSql.append("\n        , TMS_3.TMS_3_ITRDT_RUN_TM                                                                                                                                          ");
    	sbSql.append("\n        , YEAR .AVG_ITRDT_RUN_TM                                                                                                                                            ");
    	sbSql.append("\n        , YEAR .MAX_ITRDT_RUN_TM                                                                                                                                            ");
    	sbSql.append("\n        , YEAR .MIN_ITRDT_RUN_TM                                                                                                                                            ");
    	sbSql.append("\n        , YEAR .ITRDT_RUN_TM_DVTN                                                                                                                                           ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                                                                                           ");
    	sbSql.append("\n        , ?                                                                                                                                                                 ");
    	sbSql.append("\n        , SYSDATE                                                                                                                                                           ");
    	sbSql.append("\n FROM     TBEF_EQUIP   TE                                                                                                                                                   ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- �⵵�� ����                                                                                                                                                 ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      TOR.BOAT_NO                                                                                                                                           ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS))                RUN_CNT                                                                                         ");
    	sbSql.append("\n                    , COUNT(DISTINCT(TOR.STND_YEAR || TOR.MBR_CD || TOR.TMS || TOR.DAY_ORD)) RACE_DAY_CNT                                                                                    ");
    	sbSql.append("\n                    , COUNT(*)                                              RACE_CNT                                                                                        ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 1, 1, 0))                        RANK_1                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 2, 1, 0))                        RANK_2                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 3, 1, 0))                        RANK_3                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 4, 1, 0))                        RANK_4                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 5, 1, 0))                        RANK_5                                                                                          ");
    	sbSql.append("\n                    , SUM(DECODE(TOR.RANK, 6, 1, 0))                        RANK_6                                                                                          ");
    	sbSql.append("\n                    , SUM(TRS.RACE_SCR)                                     AMU_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TRS.RACE_SCR), 90.99))               AVG_RANK_SCR                                                                                    ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN         TOR                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE_TMS      TRT                                                                                                                                ");
    	sbSql.append("\n                    , TBEB_RACE          TR                                                                                                                                 ");
    	sbSql.append("\n                    , TBEB_RANK_SCR      TRS                                                                                                                                ");
    	sbSql.append("\n             WHERE  TOR .STND_YEAR      = TR  .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TR  .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TR  .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .DAY_ORD        = TR  .DAY_ORD                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .RACE_NO        = TR  .RACE_NO                                                                                                                      ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRT .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = TRT .MBR_CD                                                                                                                       ");
    	sbSql.append("\n             AND    TOR .TMS            = TRT .TMS                                                                                                                          ");
    	sbSql.append("\n             AND    TOR .STND_YEAR      = TRS .STND_YEAR                                                                                                                    ");
    	sbSql.append("\n             AND    TR  .RACE_DGRE_CD   = TRS .RACE_DGRE_CD                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RANK           = TRS .RANK                                                                                                                         ");
    	sbSql.append("\n             AND    TOR .ABSE_CD       <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .IMMT_CLS_CD   <> '003'                                                                                                                             ");
    	sbSql.append("\n             AND    TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n          ) YEAR                                                                                                                                                            ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- 3ȸ�� ����                                                                                                                                                  ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99)) TMS_3_ITRDT_RUN_TM                                                                                       ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  (TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.BOAT_NO) IN (                                                                                                  ");
    	sbSql.append("\n                                                                             SELECT                                                                                         ");
    	sbSql.append("\n                                                                                      TOR.STND_YEAR                                                                         ");
    	sbSql.append("\n                                                                                    , TOR.MBR_CD                                                                            ");
    	sbSql.append("\n                                                                                    , TOR.TMS                                                                               ");
    	sbSql.append("\n                                                                                    , TOR.BOAT_NO                                                                           ");
    	sbSql.append("\n                                                                             FROM   (                                                                                       ");
    	sbSql.append("\n                                                                                         SELECT                                                                             ");
    	sbSql.append("\n                                                                                                  TOR.STND_YEAR                                                             ");
    	sbSql.append("\n                                                                                                , TOR.MBR_CD                                                                ");
    	sbSql.append("\n                                                                                                , TOR.TMS                                                                   ");
    	sbSql.append("\n                                                                                                , TOR.BOAT_NO                                                               ");
    	sbSql.append("\n                                                                                                , RANK() OVER (PARTITION BY TOR.BOAT_NO                                      ");
    	sbSql.append("\n                                                                                                                   ORDER BY TOR.RACE_DAY DESC ) TMS_3                       ");
    	sbSql.append("\n                                                                                         FROM   (                                                                           ");
    	sbSql.append("\n                                                                                                     SELECT                                                                 ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.BOAT_NO                                                   ");
    	sbSql.append("\n                                                                                                            , MAX(TOR.RACE_DAY) RACE_DAY                                    ");
    	sbSql.append("\n                                                                                                     FROM   TBEB_ORGAN        TOR                                           ");
    	sbSql.append("\n                                                                                                     WHERE  TOR .RACE_DAY    <= (                                           ");
    	sbSql.append("\n                                                                                                                                     SELECT                                 ");
    	sbSql.append("\n                                                                                                                                            MAX(TRDO.RACE_DAY) RACE_DAY     ");
    	sbSql.append("\n                                                                                                                                     FROM   TBEB_RACE_DAY_ORD TRDO          ");
    	sbSql.append("\n                                                                                                                                     WHERE  TRDO.STND_YEAR = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.MBR_CD    = ?              ");
    	sbSql.append("\n                                                                                                                                     AND    TRDO.TMS       = ?              ");
    	sbSql.append("\n                                                                                                                                )                                           ");
    	sbSql.append("\n                                                                                                     AND    TOR.MBR_CD       = ?                                            ");
    	sbSql.append("\n                                                                                                     AND    TOR.ABSE_CD     <> '003'                                        ");
    	sbSql.append("\n                                                                                                     AND    TOR.IMMT_CLS_CD <> '003'                                        ");
    	sbSql.append("\n                                                                                                     GROUP BY                                                               ");
    	sbSql.append("\n                                                                                                              TOR.STND_YEAR                                                 ");
    	sbSql.append("\n                                                                                                            , TOR.MBR_CD                                                    ");
    	sbSql.append("\n                                                                                                            , TOR.TMS                                                       ");
    	sbSql.append("\n                                                                                                            , TOR.BOAT_NO                                                   ");
    	sbSql.append("\n                                                                                                ) TOR                                                                       ");
    	sbSql.append("\n                                                                                    ) TOR                                                                                   ");
    	sbSql.append("\n                                                                             WHERE TMS_3 <= 3                                                                               ");
    	sbSql.append("\n                                                                        )                                                                                                   ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                                ");
    	sbSql.append("\n          ) TMS_3                                                                                                                                                           ");
    	sbSql.append("\n        , (                                                                                                                                                                 ");
    	sbSql.append("\n             -- ��ռҰ�Ÿ��                                                                                                                                                ");
    	sbSql.append("\n             SELECT                                                                                                                                                         ");
    	sbSql.append("\n                      BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(AVG(TOR.INTRO_RUN_TM) , 90.99))          AVG_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MAX(TOR.RACE_TM)                                      MAX_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , MIN(TOR.RACE_TM)                                      MIN_ITRDT_RUN_TM                                                                                ");
    	sbSql.append("\n                    , TRIM(TO_CHAR(STDDEV(TOR.INTRO_RUN_TM), 90.99))        ITRDT_RUN_TM_DVTN                                                                               ");
    	sbSql.append("\n             FROM     TBEB_ORGAN        TOR                                                                                                                                 ");
    	sbSql.append("\n             WHERE  TOR .MBR_CD         = ?                                                                                                                                 ");
    	sbSql.append("\n             AND    TOR .RACE_DAY      <= (                                                                                                                                 ");
    	sbSql.append("\n                                             SELECT                                                                                                                         ");
    	sbSql.append("\n                                                    MAX(TRDO.RACE_DAY) RACE_DAY                                                                                             ");
    	sbSql.append("\n                                             FROM   TBEB_RACE_DAY_ORD TRDO                                                                                                  ");
    	sbSql.append("\n                                             WHERE  TRDO.STND_YEAR = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.MBR_CD    = ?                                                                                                      ");
    	sbSql.append("\n                                             AND    TRDO.TMS       = ?                                                                                                      ");
    	sbSql.append("\n                                          )                                                                                                                                 ");
    	sbSql.append("\n             GROUP BY BOAT_NO                                                                                                                                               ");
    	sbSql.append("\n          ) BOAT                                                                                                                                                            ");
    	sbSql.append("\n WHERE  TE   .EQUIP_TPE_CD = 'B'                                                                                                                                            ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = BOAT .BOAT_NO(+)                                                                                                                               ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = YEAR .BOAT_NO(+)                                                                                                                               ");
    	sbSql.append("\n AND    TE   .EQUIP_NO     = TMS_3.BOAT_NO(+)                                                                                                                               ");
        	
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    BOAT.BOAT_NO IN (                                        ");
        	sbSql.append("\n                         SELECT                                ");
        	sbSql.append("\n                                 TOR.BOAT_NO                 ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?          ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?          ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?          ");
        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'      ");
        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'      ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (             ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                        )                                        ");
        }
        
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sStndYear);  
        param.setValueParamter(i++, sMbrCd   );  
        param.setValueParamter(i++, sTms     );  
        if ( nSize > 0 ) {
	        param.setValueParamter(i++, sStndYear);
	        param.setValueParamter(i++, sMbrCd   );
	        param.setValueParamter(i++, sTms     );
        }

        int dmlcount = this.getDao("boadao").insertByQueryStatement(sbSql.toString(), param);
        
        return dmlcount;
    }

    /**
     * <p> ȸ���� ��Ʈ ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteBoatRecdAccuSum(PosDataset ds)
    {
    	StringBuffer sbSql = new StringBuffer();

        sbSql.append("\n DELETE FROM TBEB_BOAT_RECD_ACCU_SUM                 ");
        sbSql.append("\n WHERE  STND_YEAR = ?                                ");
        sbSql.append("\n AND    MBR_CD    = ?                                ");
        sbSql.append("\n AND    TMS       = ?                                ");
        
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();
        
        if ( nSize > 0 ) {
        	sbSql.append("\n AND    BOAT_NO IN (                                           ");
        	sbSql.append("\n                         SELECT                                ");
        	sbSql.append("\n                                 TOR.BOAT_NO                   ");
        	sbSql.append("\n                         FROM    TBEB_ORGAN TOR                ");
        	sbSql.append("\n                         WHERE   TOR.STND_YEAR    = ?          ");
        	sbSql.append("\n                         AND     TOR.MBR_CD       = ?          ");
        	sbSql.append("\n                         AND     TOR.TMS          = ?          ");
        	sbSql.append("\n                         AND     TOR.ABSE_CD     <> '003'      ");
        	sbSql.append("\n                         AND     TOR.IMMT_CLS_CD <> '003'      ");
        	sbSql.append("\n                         AND     TOR.RACER_NO IN (             ");
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);

            if ( i == 0 ) {
            	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
            } else { 
            	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
            }
        }
        
        if ( nSize > 0 ) {
        	sbSql.append("\n                                                 )             ");
        	sbSql.append("\n                  )                                        ");
        }

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        if ( nSize > 0 ) {
	        param.setWhereClauseParameter(i++, sStndYear);
	        param.setWhereClauseParameter(i++, sMbrCd   );
	        param.setWhereClauseParameter(i++, sTms     );
        }

        int dmlcount = this.getDao("boadao").updateByQueryStatement(sbSql.toString(), param);
        return dmlcount;
    }

    /**
     * <p> ȸ������(�������) �� ȸ���� �۾� �̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx) 
    {
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));

        // ������� üũ
        /***
        String sStatCd = "";
        SaveProcess sp1 = new SaveProcess();        
        sStatCd = sp1.getRaceTmsStatCd(sStndYear, sMbrCd, sTms);

		if(!"020".equals(sStatCd)){
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_boa");
        		Util.setSvcMsg(ctx, "�����÷ �Ϸ���°� �ƴմϴ�!!!");
        		return 0;        		
        	}			
		}  ***/
		
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, null);
        param.setValueParamter(i++, "055");
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd);
        param.setWhereClauseParameter(i++, sTms);
        
        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub002", param);

        // �۾��α� �ۼ�
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DUTY_CD"  , "002");
        hmProcess.put("WORK_CD"  , "055");
        hmProcess.put("PROG_STAT", "001");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
        
        return dmlcount;
    }
    
    /**
     * <p> �ּ���������Ƚ�� Update (ȸ������ ��) </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerRaceAllocEnd(PosContext ctx)
    {
    	sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
        
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sMbrCd);
        param.setValueParamter(i++, sTms);
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_racer_race_alloc_ub003_end", param);

        return dmlcount;
    }
}

