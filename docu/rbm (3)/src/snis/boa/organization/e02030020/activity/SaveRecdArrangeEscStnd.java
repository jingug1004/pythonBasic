/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030020.activity.SaveRecdArrangeEscStnd.java
 * ���ϼ���		: ������ּ��������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030020.activity;

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
* �����Ͽ� ������ּ������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRecdArrangeEscStnd extends SnisActivity
{    
    public SaveRecdArrangeEscStnd()
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
        
        sDsName = "dsOutRecdArrangeEscStnd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutEscRacer";
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
/*
        // �ּ����ܱ��� ����
        sDsName = "dsOutRecdArrangeEscStnd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	if ( (nTempCnt = updateRecdArrangeEscStnd(record)) == 0 ) {
                	nTempCnt = insertRecdArrangeEscStnd(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }
*/
        // �ּ������� ����
        sDsName = "dsOutEscRacer";
        //deleteEscRacer(ctx);
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        int nSeq = getEscRacerSeq(ctx);
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	nTempCnt = insertEscRacer(record, ctx, nSeq++);

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �ּ����ܱ��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRecdArrangeEscStnd(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_COND          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR         ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("QURT_CD           ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RECD_ESC_CLS_CD   ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_recd_arrange_esc_stnd_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �ּ����ܱ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRecdArrangeEscStnd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR         ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("QURT_CD           ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RECD_ESC_CLS_CD   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_COND          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_COND          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_recd_arrange_esc_stnd_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> �ּ������� �Ϸù�ȣ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int getEscRacerSeq(PosContext ctx)
    {
        PosParameter param = new PosParameter();

        PosRowSet rowset = this.getDao("boadao").find("tbec_arrange_esc_fb000", param);
        
        int nSeq = 0;
        if ( rowset.hasNext() ) {
        	PosRow row = rowset.next(); 
        	nSeq = ((BigDecimal) row.getAttribute("SEQ")).intValue();
        }
        return nSeq;
    }

    
    /**
     * <p> �ּ������� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertEscRacer(PosRecord record, PosContext ctx, int nSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO ".trim())));
        param.setValueParamter(i++, Integer.toString(nSeq));
        param.setValueParamter(i++, Util.trim(ctx.get            ("STR_DT   ".trim())));
        param.setValueParamter(i++, Util.trim(ctx.get            ("END_DT   ".trim())));
        
        String sRsnCd = (String) record.getAttribute("RSN_CD   ".trim());
        if ( sRsnCd.equals("201")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("AVG_RANK_SCR".trim())) + ")");
        } else if ( sRsnCd.equals("202")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("AVG_ACDNT_SCR".trim())) + ")");
        } else if ( sRsnCd.equals("203")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("CONT_CNT".trim())) + ")");
        } else if ( sRsnCd.equals("204")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("FL_CNT".trim())) + ")");
        } else if ( sRsnCd.equals("205")) {
            param.setValueParamter(i++, Util.trim(record.getAttribute("DESC".trim())) + "("+ Util.trim(record.getAttribute("AVG_SCR".trim())) + ")");
        }
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSN_CD   ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbec_arrange_esc_ib001", param);
        
        return dmlcount;
    }

    /**
     * <p> �ּ������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEscRacer(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(ctx.get("STND_YEAR         ".trim())));
        param.setValueParamter(i++, Util.trim(ctx.get("QURT_CD           ".trim())));

        int dmlcount = this.getDao("boadao").update("tbec_arrange_esc_db001", param);
        return dmlcount;
    }
}
