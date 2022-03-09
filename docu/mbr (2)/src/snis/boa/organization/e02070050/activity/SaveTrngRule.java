/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02070020.activity.SaveTrngExptRacer.java
 * ���ϼ���		: ������/��������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02070050.activity;

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
* �����Ͽ� �Ʒñ����ڵ� �� ���� �����ڵ带 ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���缱
* @version 1.0
*/
public class SaveTrngRule extends SnisActivity
{    
    public SaveTrngRule()
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

        ds    = (PosDataset)ctx.get("dsTrngRule");
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

    	PosDataset dsRule;
    	PosDataset dsRuleSanc;
        int nSize        = 0;
        int nTempCnt     = 0;

        dsRule     = (PosDataset) ctx.get("dsTrngRule");
        dsRuleSanc = (PosDataset) ctx.get("dsTrngRuleSanc");
        nSize = dsRule.getRecordCount();

        // ����
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = dsRule.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngRule(record);
            }
        }
        
        // ���� �� ���
        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = dsRule.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = mergeUpdateTrngRule(record);
            }
        }

        // �Ʒñ��ذ� �������� �������� ����
        nSize = dsRuleSanc.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = dsRuleSanc.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngRuleSanc(record);
            }
        }
        
        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = dsRuleSanc.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = mergeUpdateTrngRuleSanc(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �Ʒñ��� �Ϸù�ȣ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected String getTrngExptSeq()
    {
        PosParameter param = new PosParameter();
        PosRowSet rowset = this.getDao("boadao").find("tbeb_trng_expt_racer_fb001", param);

        String sTrngExptSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            sTrngExptSeq = (String) row.getAttribute("TRNG_EXPT_SEQ".trim());
        }
            
        return sTrngExptSeq;
    }


    /**
     * <p> �Ʒñ��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeUpdateTrngRule(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_TYPE".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DESC".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_TERM".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_TERM_UNIT".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM_UNIT".trim()));
        param.setValueParamter(i++, record.getAttribute("HEALTH_TEST_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("STARTTM_TEST_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("ADJST_TEST_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK".trim()));
        param.setValueParamter(i++, record.getAttribute("INJURY_TERM_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_TODT".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        
        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_u01", param);
        
        return dmlcount;
    }

    /**
     * <p> �Ʒñ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTrngRule(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_d01", param);
        
        return dmlcount;
    }

    /**
     * <p> �Ʒñ��غ� �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTrngRuleSanc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_d02", param);
        
        return dmlcount;
    }    

    /**
     * <p> �Ʒñ��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeUpdateTrngRuleSanc(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_RULE_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("RULE_FRDT".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_rule_u02", param);
        
        return dmlcount;
    }
    
}
