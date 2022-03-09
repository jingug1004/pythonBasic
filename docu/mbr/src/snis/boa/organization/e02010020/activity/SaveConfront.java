/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02010020.activity.SaveConfront.java
 * ���ϼ���		: ������ĵ��
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02010020.activity;

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
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveConfront extends SnisActivity
{    
	
    public SaveConfront()
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
        int        nSize        = 0;
        String     sDsName = "";
        
        sDsName = "dsOutCfrntMeth";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutRacerArrangeStnd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for (int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }

        sDsName = "dsOutCfrntDay";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for (int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutCfrntRace";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for (int i = 0; i < nSize; i++ ) 
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
    	String sMessage     = "";
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // ������� ����
        sDsName = "dsOutCfrntMethDelete";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
        	// ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	if ( getRaceTmsCnt(record) == 0 ) {
	            		nDeleteCount = nDeleteCount + deleteCfrntMeth(record);
	            	} else {
	            		if ( sMessage.equals("") ) {
		            		sMessage = Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		} else {
		            		sMessage = sMessage + ", " + Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		}
	            	}
	            }
	        }

        	if ( sMessage.length() > 0 ) {
            	Util.setSvcMsg(ctx, sMessage + "�� ������� �����ڵ��Դϴ�. \n ���� �� ������ �Ұ��մϴ�.");
            	return;
        	}
        }
    	// ����
        sDsName = "dsOutCfrntMeth";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.NORMAL)
	            {
	            	if ( getRaceTmsCnt(record) == 0 ) {
		            	if ( (nTempCnt = updateCfrntMeth(record)) == 0 ) {
		                	nTempCnt = insertCfrntMeth(record);
		                }
		            	nSaveCount = nSaveCount + nTempCnt;
	            	} else {
	            		if ( sMessage.equals("") ) {
		            		sMessage = Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		} else {
		            		sMessage = sMessage + ", " + Util.trim(record.getAttribute("CFRNT_CD     ".trim()));
	            		}
	            	}
	            }
	        }

        	if ( sMessage.length() > 0 ) {
            	Util.setSvcMsg(ctx, sMessage + "�� ������� �����ڵ��Դϴ�. \n ���� �� ������ �Ұ��մϴ�.");
            	return;
        	}
        }
        
        // �����ּ����� ����
        sDsName = "dsOutRacerArrangeStnd";
        if ( ctx.get(sDsName) != null ) {
        	
        	String sCfrntCd = (String) ctx.get("CFRNT_CD");
        	int    nSeq     = getRacerArrangeStndSeq(sCfrntCd);

	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
            
	        // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteRacerArrangeStnd(record);
	            }
	        }

            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	                if ( (nTempCnt = updateRacerArrangeStnd(record)) == 0 ) {
	                	nTempCnt = insertRacerArrangeStnd(record, nSeq++);
	                }
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }

	        
        // ������ ����
        sDsName = "dsOutCfrntDay";
        if ( ctx.get(sDsName) != null ) {
        	ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
            
	        // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteCfrntDay(record);
	            }
	        }
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	                if ( (nTempCnt = updateCfrntDay(record)) == 0 ) {
	                	nTempCnt = insertCfrntDay(record);
	                }
	
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }

	        
        // �������� ����
        sDsName = "dsOutCfrntDay";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get("dsOutCfrntRace");
	        nSize = ds.getRecordCount();
            
	        // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteCfrntRace(record);
	            }
	        }
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	                if ( (nTempCnt = updateCfrntRace(record)) == 0 ) {
	                	nTempCnt = insertCfrntRace(record);
	                }
	
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ��������� ���Ǿ��� ��ȸ(��������� ���Ǿ��ٸ� ����/�����Ұ�)) </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int getRaceTmsCnt(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_tms_fb002", param);

        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("CNT".trim());
            logger.logInfo("tbeb_race_tms_fb002.CNT : " + nCnt);
        }
            
        return nCnt.intValue();
    }

    /**
     * <p> ������� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCfrntMeth(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR    ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_KINDS_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_meth_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> ������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCfrntMeth(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR    ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_KINDS_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_meth_ib001", param);
        return dmlcount;
    }

    /**
     * <p> ������� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteCfrntMeth(PosRecord record) 
    {
    	deleteCfrntRace(record);
    	deleteCfrntDay(record);
    	deleteRacerArrangeStnd(record);
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD    ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_meth_db001", param);
        return dmlcount;
    }
    /**
     * <p> �����ּ����� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerArrangeStnd(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_CNT   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SELT_STND_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD     ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ          ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_arrange_stnd_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �����ּ����� Seq ���ϱ� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int getRacerArrangeStndSeq(String sCfrntCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sCfrntCd);
        PosRowSet rowset = this.getDao("boadao").find("tbeb_racer_arrange_stnd_fb000", param);

        BigDecimal nSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nSeq = (BigDecimal) row.getAttribute("SEQ".trim());
            logger.logInfo("tbeb_racer_arrange_stnd_fb000.SEQ : " + nSeq);
        }
            
        return nSeq.intValue();
    }

    /**
     * <p> �����ּ����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerArrangeStnd(PosRecord record, int nSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD    ".trim())));
        param.setValueParamter(i++, Integer.toString(nSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_CNT   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SELT_STND_CD".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_racer_arrange_stnd_ib001", param);
        return dmlcount;
    }

    /**
     * <p> �����ּ����� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerArrangeStnd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEQ     ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_arrange_stnd_db001", param);
        return dmlcount;
    }
    /**
     * <p> ������ Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCfrntDay(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_CNT".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_day_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCfrntDay(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INST_ID ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_day_ib001", param);
        return dmlcount;
    }

    /**
     * <p> ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteCfrntDay(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_day_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> �������� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCfrntRace(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STRT_TIME      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DIST      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TURN_CNT       ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO ".trim())));
        
        int dmlcount = this.getDao("boadao").update("tbeb_cfrnt_race_ub001", param);
        return dmlcount;
    }

    /**
     * <p> �������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCfrntRace(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("CFRNT_CD       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO        ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STRT_TIME      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO_CNT".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DIST      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TURN_CNT       ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbeb_cfrnt_race_ib001", param);
        return dmlcount;
    }

    /**
     * <p> �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteCfrntRace(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CFRNT_CD".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_ORD ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO ".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbeb_cfrnt_race_db001", param);
        return dmlcount;
    }
}
