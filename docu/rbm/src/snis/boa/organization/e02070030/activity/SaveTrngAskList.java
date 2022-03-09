/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02070030.activity.SaveTrngAsk.java
 * ���ϼ���		: ���������Ʒõ��
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02070030.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
* 
* @auther ���缱
* @version 1.0
*/
public class SaveTrngAskList extends SnisActivity
{    
    public SaveTrngAskList()
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
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        String     sDsName = "";
        
        sDsName = "dsOutTrngAsk";
        if ( ctx.get(sDsName) != null ) {
	
	        // 
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	            	nDeleteCount += deleteTrngAsk(record, ctx);
	            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nSaveCount += updateTrngAsk(record);
                }
	                     
	        }
        }
        sDsName = "dsOutTrngAskRacer";
        if ( ctx.get(sDsName) != null ) {
	        
        	ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
		        PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
	            {
	            	nSaveCount += updateTrngAskRacer(record);
	            }
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �����Ʒ� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngAsk(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_PLC_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_DD_NUM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STAT_CD ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_ub001", param);
        
        return dmlcount;
    }


    /**
     * <p> �����Ʒü��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngAskRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_GRP_NO     ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_YN         ".trim()));
        param.setValueParamter(i++, record.getAttribute("COMMUTE_YN       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ     ".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO         ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Ʒÿ�û ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTrngAsk(PosRecord record, PosContext ctx) 
    {
        PosParameter paramTrngAsk = new PosParameter();
        int i = 0;
        paramTrngAsk.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));
    	
        PosRowSet rowset = this.getDao("boadao").find("tbeb_trng_ask_fb003", paramTrngAsk);
         
        if ( rowset.hasNext() ) {
        	PosRow row = rowset.next();
        	
        	if ( !row.getAttribute("TRNG_ASK_STAT_CD").equals("000") ) {
        		Util.setSvcMsg(ctx, record.getAttribute("TRNG_ASK_SEQ") + "�� �������� �Ʒÿ�û�Դϴ�. ���� �Ͻ� �� �����ϴ�.");
        		return -1;
        	}
        }
        
        deleteTrngAskRacerResult(record);
    	deleteTrngAskRacer(record);
        PosParameter param = new PosParameter();
        i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> �Ʒÿ�û���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTrngAskRacer(PosRecord record) 
    {
    	//deleteTrngAskRacerResult(record);
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_NO        ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> �Ʒÿ�û������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTrngAskRacerResult(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACER_NO        ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_db001", param);
        return dmlcount;
    }
}
