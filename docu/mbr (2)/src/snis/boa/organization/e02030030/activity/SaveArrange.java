/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SaveArrange.java
 * ���ϼ���		: �ּ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

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
* �����Ͽ� �����ּ��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveArrange extends SnisActivity
{
    public SaveArrange()
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
	        
        sDsName = "dsOutRacer";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutRacerArrange";
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

        // �ּ����� ���
        sDsName = "dsOutRacer";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        deleteArrange(ds, ctx);
	        updateOrgan(ds, ctx);
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	if ( (nTempCnt = updateArrange(record, ctx)) == 0 ) {
                	nTempCnt = insertArrange(record, ctx);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        // �ּ������� ���ּ� ����
        sDsName = "dsOutRacerArrange";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        deleteRaceAlloc(ds, ctx);
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            
	            // �ּ����� ������ �����Ѵ�.
	            if ( record.getAttribute("RACE_ALLOC_CNT") == null ) continue; 
	            if ( record.getAttribute("RACE_ALLOC_CNT").equals("-")) continue;
	            if ( record.getAttribute("RACE_ALLOC_CNT").equals("*")) continue;
	            if ( record.getAttribute("RACE_ALLOC_CNT").equals("#")) continue;
	            
            	if ( (nTempCnt = updateRaceAlloc(record, ctx)) == 0 ) {
                	nTempCnt = insertRaceAlloc(record, ctx);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        updateRaceTms(ctx);
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �ּ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteArrange(PosDataset ds, PosContext ctx) 
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n DELETE               ");
    	sbSql.append("\n FROM   TBEB_ARRANGE  ");
    	sbSql.append("\n WHERE  STND_YEAR = ? ");
    	sbSql.append("\n AND    MBR_CD    = ? ");
    	sbSql.append("\n AND    TMS       = ? ");
    	
        int nCount = 0;
        if ( ds != null ) {
	        int nSize = ds.getRecordCount();
	
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( nCount == 0 ) {
	                sbSql.append("\n                 AND    RACER_NO  NOT IN (");
	                sbSql.append("'" + record.getAttribute("RACER_NO").toString() + "'");
	            } else {
	                sbSql.append(",'" + record.getAttribute("RACER_NO").toString() + "'");
	            }
	          
	            nCount++;
	        }
	        
	        if ( nCount > 0 ) 
	            sbSql.append("\n                                                 )");
        }

        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        param.setWhereClauseParameter(i++, ctx.get("TMS")      );
        int dmlcount = this.getDao("boadao").updateByQueryStatement(sbSql.toString(), param);
        
        return dmlcount;
    }

    /**
     * <p> �ּ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateArrange(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String sOnlineYn = record.getAttribute("ONLINE_YN".trim()).toString();
        String sStMthdCd = "002";
        if("Y".equals(sOnlineYn)) sStMthdCd = "001";
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("EXCL_YN  ".trim())));
        param.setValueParamter(i++, Util.trim(sStMthdCd));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO  ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_arrange_ub001", param);
        return dmlcount;
    }
    
    /**
     * <p> �ּ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertArrange(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String sOnlineYn = record.getAttribute("ONLINE_YN".trim()).toString();
        String sStMthdCd = "002";
        if("Y".equals(sOnlineYn)) sStMthdCd = "001";
        
        param.setValueParamter(i++, ctx.get("STND_YEAR".trim()));
        param.setValueParamter(i++, ctx.get("MBR_CD   ".trim()));
        param.setValueParamter(i++, ctx.get("TMS      ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO  ".trim())));
        param.setValueParamter(i++, ctx.get("EXCL_YN  ".trim()));
        param.setValueParamter(i++, Util.trim(sStMthdCd));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_arrange_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> �ּ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRaceAlloc(PosDataset ds, PosContext ctx) 
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n DELETE                        ");
    	sbSql.append("\n FROM   TBEB_RACER_RACE_ALLOC  ");
    	sbSql.append("\n WHERE  STND_YEAR = ?          ");
    	sbSql.append("\n AND    MBR_CD    = ?          ");
    	sbSql.append("\n AND    TMS       = ?          ");
    	
        int nCount = 0;
        if ( ds != null ) {
	        int nSize = ds.getRecordCount();
	
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( nCount == 0 ) {
	                sbSql.append("\n                 AND    RACER_NO  NOT IN (");
	                sbSql.append("'" + record.getAttribute("RACER_NO").toString() + "'");
	            } else {
	                sbSql.append(",'" + record.getAttribute("RACER_NO").toString() + "'");
	            }
	          
	            nCount++;
	        }
	        
	        if ( nCount > 0 ) 
	            sbSql.append("\n                                                 )");
        }

        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        param.setWhereClauseParameter(i++, ctx.get("TMS")      );
        int dmlcount = this.getDao("boadao").updateByQueryStatement(sbSql.toString(), param);
        
        return dmlcount;
    }

    /**
     * <p> �ּ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceAlloc(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_ONLINE_CNT".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD       ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_race_alloc_ub004", param);
        return dmlcount;
    }

    /**
     * <p> �ּ����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRaceAlloc(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("STND_YEAR".trim()));
        param.setValueParamter(i++, ctx.get("MBR_CD   ".trim()));
        param.setValueParamter(i++, ctx.get("TMS      ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_ONLINE_CNT".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_racer_race_alloc_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> �̹� ���Ǿ� �ִٸ� �� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateOrgan(PosDataset ds, PosContext ctx)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n UPDATE TBEB_ORGAN SET  ");
    	sbSql.append("\n          RACER_NO = '' ");
    	sbSql.append("\n        , MOT_NO   = '' ");
    	sbSql.append("\n        , BOAT_NO  = '' ");
    	sbSql.append("\n WHERE  STND_YEAR  = ?  ");
    	sbSql.append("\n AND    MBR_CD     = ?  ");
    	sbSql.append("\n AND    TMS        = ?  ");
    	
        int nCount = 0;
        if ( ds != null ) {
	        int nSize = ds.getRecordCount();
	
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( nCount == 0 ) {
	                sbSql.append("\n                 AND    RACER_NO  NOT IN (");
	                sbSql.append("'" + record.getAttribute("RACER_NO").toString() + "'");
	            } else {
	                sbSql.append(",'" + record.getAttribute("RACER_NO").toString() + "'");
	            }
	          
	            nCount++;
	        }
	        
	        if ( nCount > 0 ) 
	            sbSql.append("\n                                                 )");
        }

        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        param.setWhereClauseParameter(i++, ctx.get("TMS")      );
        int dmlcount = this.getDao("boadao").updateByQueryStatement(sbSql.toString(), param);
        
        return dmlcount;
    }

    /**
     * <p> ȸ���� �۾� �̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("ARRANGE_CMPL_YN"));
        param.setValueParamter(i++, ctx.get("RACE_TMS_STAT_CD"));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub002", param);
        
        if ( ctx.get("ARRANGE_CMPL_YN") != null ) {
	        if ( ctx.get("ARRANGE_CMPL_YN").equals("Y") ) {
                // �۾��α� �ۼ�
                //==============================================
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
                hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
                hmProcess.put("TMS"      , ctx.get("TMS"      ));
                hmProcess.put("DUTY_CD"  , "002");
                hmProcess.put("WORK_CD"  , "010");
                hmProcess.put("PROG_STAT", "001");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
                //==============================================
	        }
        }
        
        return dmlcount;
    }
}
