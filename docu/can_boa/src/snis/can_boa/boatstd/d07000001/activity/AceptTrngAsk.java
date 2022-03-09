/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02070030.activity.UpdateTrngAskStcd.java
 * ���ϼ���		: ���������Ʒõ��
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000001.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class AceptTrngAsk extends SnisActivity
{    
    public AceptTrngAsk()
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
    	String sDsName = sDsName = "dsOutTrngAsk";       

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        if (ctx.get("JOB_TYPE").equals("TRNG_FEE_UPDATE")) {
        	sDsName = "dsOutTrngAskRacer";
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
	            {
	            	nTempCnt = updateTrngAskRacer(record);
	            	nSaveCount = nSaveCount + nTempCnt;
	            }	            
	        }
        } else if ( ctx.get(sDsName) != null ) {	// ���������Ʒ� ���� ��û�ڷ� ����
            ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
	            {
	            	nTempCnt = updateTrngAsk(record, ctx);
	            	nSaveCount = nSaveCount + nTempCnt;
	            }
	            
	            String TrngAskStatCd = (String)record.getAttribute("TRNG_ASK_STAT_CD ".trim());
	            if (ctx.get("JOB_TYPE").equals("ACEPT")) {
	            	if (TrngAskStatCd.equals("001")) {
	            		// �Ʒ����� �����ڷ� ����
	            		DeleteRacerTrngMst(record, ctx);
	            	} else if (TrngAskStatCd.equals("002")) {
	            		// �Ʒ����� �����ڷ� ����
	            		InsertRacerTrngMst(record, ctx);
	            	}
	            	
	            	updateTrngAskRacerAcept(record, ctx);		// ������ ���� �ʱⰪ���� ����
	            }
	        
				// �ƷÿϷ�� ��û���µ� "�ƷÿϷ�"�� ����(�������������� �ƷÿϷᰡ "003")
		        if (ctx.get("JOB_TYPE").equals("003004") || ctx.get("JOB_TYPE").equals("004003")) {
					String sStatCd = "";
					String sTrnsAskSeq = "";
					if (ctx.get("JOB_TYPE") == "003004") {
						sStatCd = "006";
					} else if (ctx.get("JOB_TYPE") == "004003") {
						sStatCd = "004";
					}
					sTrnsAskSeq = record.getAttribute("TRNG_ASK_SEQ").toString();
					nSaveCount += updateTrngExptRacer(sStatCd, sTrnsAskSeq);
				}
	        }
        } else {
        	logger.logInfo("Dataset not found...["+sDsName+"]");
        }
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> �����Ʒ� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngAsk(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        if (ctx.get("JOB_TYPE").equals("ACEPT")) {    		
        	param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STAT_CD ".trim()));
        } else {
        	param.setValueParamter(i++, ctx.get("TRNG_ASK_STAT_CD_CNG"));
        }
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub002", param);
        
        return dmlcount;
    }
    

	/**
     * <p> �Ʒû���  ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
	protected int updateTrngExptRacer(String sStatCd, String sTrngAskSeq) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setWhereClauseParameter(i++, sStatCd);
		param.setWhereClauseParameter(i++, sTrngAskSeq);

		int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_uo001", param);
		return dmlcount;
	}

    /**
     * <p> �����Ʒ� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngAskRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("TRNG_FEE_PAY_YN ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_FEE_PAY_MTHD ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_FEE_AMT".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RMK".trim()));
        param.setValueParamter(i++, record.getAttribute("COMMUTE_YN".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_YN".trim()));        
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO    ".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ".trim()));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub003", param);
        
        return dmlcount;
    }
    

    /**
     * <p> �Ʒ����� �ڷ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertRacerTrngMst(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT"));
        
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));        
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT"));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT"));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub004", param);
        
        return dmlcount;
    }


    /**
     * <p> �Ʒ����� �ڷ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int DeleteRacerTrngMst(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_mst_d001", param);
        
        return dmlcount;
    }

    /**
     * <p> �����Ʒ� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngAskRacerAcept(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("candao").update("tbeb_trng_ask_ub005", param);
        
        return dmlcount;
    }
    
}
