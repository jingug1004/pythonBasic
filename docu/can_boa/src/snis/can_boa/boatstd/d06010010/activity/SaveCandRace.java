package snis.can_boa.boatstd.d06010010.activity;

import java.math.BigDecimal;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ĺ��� ���� ���� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveCandRace extends SnisActivity
{    
    public SaveCandRace()
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

        ds    = (PosDataset)ctx.get("dsOutCandRace");
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

        ds    = (PosDataset) ctx.get("dsOutCandRace");
        nSize = ds.getRecordCount();

        // ����
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteCandRaceMst(record, ctx);
            	
            }
        }

        int nDayNo = getCandRaceMstSeq(ctx);
        
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                // update
            	if ( (nTempCnt = updateCandRaceMst(record, ctx)) == 0 ) {
            		ctx.put("DAY_NO", Integer.toString(nDayNo));
            		nTempCnt = insertCandRaceMst(record, ctx);
            		nDayNo++;
            	}

            	nSaveCount = nSaveCount + nTempCnt;
            }
        }

		ctx.put("DAY_NO", "");
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int getCandRaceMstSeq(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));

        PosRowSet rowset = this.getDao("candao").find("tbdn_cand_race_fn000", param);
        
        int nDayNo = 0;
        if ( rowset.hasNext() ) {
        	PosRow row = (PosRow) rowset.next();
        	
        	nDayNo = ((BigDecimal) row.getAttribute("DAY_NO")).intValue();
        }
        return nDayNo;
    }

    /**
     * <p> Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCandRaceMst(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DT            ".trim()));
        param.setValueParamter(i++, record.getAttribute("PLC_CD        ".trim()));
        param.setValueParamter(i++, record.getAttribute("ROUND_CNT     ".trim()));
        param.setValueParamter(i++, record.getAttribute("WEATHER       ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC     ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TEMPT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("HUMID         ".trim()));
        param.setValueParamter(i++, record.getAttribute("WAT_TEMP      ".trim()));
        param.setValueParamter(i++, record.getAttribute("WAT_LEV       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_NO        ".trim()));

        int dmlcount = this.getDao("candao").update("tbdn_cand_race_un001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCandRaceMst(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setValueParamter(i++, record.getAttribute("DT            ".trim()));
        param.setValueParamter(i++, record.getAttribute("PLC_CD        ".trim()));
        param.setValueParamter(i++, record.getAttribute("ROUND_CNT     ".trim()));
        param.setValueParamter(i++, record.getAttribute("WEATHER       ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC     ".trim()));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TEMPT         ".trim()));
        param.setValueParamter(i++, record.getAttribute("HUMID         ".trim()));
        param.setValueParamter(i++, record.getAttribute("WAT_TEMP      ".trim()));
        param.setValueParamter(i++, record.getAttribute("WAT_LEV       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("candao").update("tbdn_cand_race_in001", param);
        return dmlcount;
    }

    /**
     * <p> ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteCandRaceMst(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_NO        ".trim()));

        int dmlcount = this.getDao("candao").update("tbdn_cand_race_dn001", param);
        
        this.getDao("candao").update("tbdn_cand_race_plan_dn001", param);
        
        param.setWhereClauseParameter(i++, null);
        this.getDao("candao").update("tbdn_cand_race_race_dn001", param);

        param.setWhereClauseParameter(i++, null);
        param.setWhereClauseParameter(i++, null);
        this.getDao("candao").update("tbdn_cand_race_organ_dn001", param);

        param.setWhereClauseParameter(i++, null);
        this.getDao("candao").update("tbdn_cand_race_recd_dn001", param);
        
        param.setWhereClauseParameter(i++, null);
        this.getDao("candao").update("tbdn_cand_race_viol_del001", param);
        
        return dmlcount;
    }
    
}
