/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02010010.activity.SaveScore.java
 * ���ϼ���		: ������/��������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02010010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
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
public class SaveScore extends SnisActivity
{    
	protected String sStndYear      = "";
	
    public SaveScore()
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

        ds    = (PosDataset)ctx.get("dsOutRankScr");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }
        
        ds    = (PosDataset)ctx.get("dsOutGppScr");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }
        
        ds    = (PosDataset)ctx.get("dsOutAcdntScr");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
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

        sStndYear = (String) ctx.get("STND_YEAR");

        // ������ ����
        ds    = (PosDataset) ctx.get("dsOutRankScr");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteRankScr(record);
            }
        }

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                // update
            	nSaveCount = nSaveCount + saveRankScr(record);
            }
        }

        // GPP ����
        ds    = (PosDataset) ctx.get("dsOutGppScr");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteGppScr(record);
            }
        }

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                // update
            	nSaveCount = nSaveCount + saveGppScr(record);
            }
        }

        // ����� ����
        ds    = (PosDataset)ctx.get("dsOutAcdntScr");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteAcdntScr(record);
            }
        }
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateAcdntScr(record)) == 0 ) {
                	nTempCnt = insertAcdntScr(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRankScr(PosRecord record)
    {
    	/*
        IWORK_SFR-008 ���� ������ �޴� ���� 2013-12-07 8������ �߰� ����.
        */
    	int maxRank = 8;
    	for ( int i = 0; i <= maxRank; i++ ) {
            if ( updateRankScr(record, i) == 0 ) {
            	insertRankScr(record, i);
            }
    	}
        
        return 1;
    }

    /**
     * <p> ������ Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRankScr(PosRecord record, int nRank)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setWhereClauseParameter(i++, Integer.toString(nRank));

        int dmlcount = this.getDao("boadao").update("tbeb_rank_scr_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRankScr(PosRecord record, int nRank) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear                           );
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setValueParamter(i++, Integer.toString(nRank));
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_rank_scr_ib001", param);
        return dmlcount;
    }

    /**
     * <p> ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRankScr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_rank_scr_db001", param);
        return dmlcount;
    }

    /**
     * <p> GPP Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveGppScr(PosRecord record)
    {
    	int maxRank = 8;
    	for ( int i = 0; i <= maxRank; i++ ) {
            if ( updateGppScr(record, i) == 0 ) {
            	insertGppScr(record, i);
            }
    	}
        
        return 1;
    }

    /**
     * <p> Gpp Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateGppScr(PosRecord record, int nRank)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setWhereClauseParameter(i++, Integer.toString(nRank));

        int dmlcount = this.getDao("boadao").update("tbeb_gpp_scr_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> GPP �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertGppScr(PosRecord record, int nRank) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear                           );
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));
        param.setValueParamter(i++, Integer.toString(nRank));
        param.setValueParamter(i++, record.getAttribute("RANK_" + nRank));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_gpp_scr_ib001", param);
        return dmlcount;
    }

    /**
     * <p> GPP ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteGppScr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear                           );
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_DGRE_CD".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_gpp_scr_db001", param);
        return dmlcount;
    }
    
    
    /**
     * <p> ����� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAcdntScr(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("VOIL_DESC".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_SCR".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear                    );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("VOIL_CD".trim())));
        
        int dmlcount = this.getDao("boadao").update("tbeb_acdnt_scr_ub001", param);
        return dmlcount;
    }

    /**
     * <p> ����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAcdntScr(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear                    );
        param.setValueParamter(i++, Util.trim(record.getAttribute("VOIL_CD  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("VOIL_DESC".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_SCR".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbeb_acdnt_scr_ib001", param);
        return dmlcount;
    }

    /**
     * <p> ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAcdntScr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear                    );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("VOIL_CD".trim())));
        
        int dmlcount = this.getDao("boadao").delete("tbeb_acdnt_scr_db001", param);
        return dmlcount;
    }
}
