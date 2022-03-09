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
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTrngAsk extends SnisActivity
{    
    public SaveTrngAsk()
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
    	
    	/*    	
        PosDataset ds;
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsOutTrngAsk");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }

        ds    = (PosDataset)ctx.get("dsOutTrngAskRacer");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }
        */
    	
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

        // ���������Ʒ� ���� ��û�ڷ� ����
        ds    = (PosDataset) ctx.get("dsOutTrngAsk");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ )
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	int nCount = deleteTrngAsk(record, ctx);
            	if ( nCount < -1 ) return;
            	nDeleteCount = nDeleteCount + nCount;
            }
        }

        String sStrDt = "";
        String sEndDt = "";
        String sAskDd = "";
        
        // ���������Ʒÿ�û ���
        String sTrngAskSeq = "";
        for ( int i = 0; i < nSize; i++ )
        {
            PosRecord record = ds.getRecord(i);
            
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.NORMAL)
            {
            	if ( (nTempCnt = updateTrngAsk(record)) == 0 ) {
            		sTrngAskSeq = getNextTrngAskSeq(getTrngAskSeq());
                	nTempCnt = insertTrngAsk(record, sTrngAskSeq);
                } else {
                	sTrngAskSeq = record.getString("TRNG_ASK_SEQ");
                }
            	nSaveCount = nSaveCount + nTempCnt;
            }
            
            sStrDt = (String) record.getAttribute("TRNG_ASK_STR_DT");
            sEndDt = (String) record.getAttribute("TRNG_ASK_END_DT");
            try {
            	sAskDd = (Double) record.getAttribute("TRNG_ASK_DD_NUM") + "";
            } catch ( Exception e ) {
            	sAskDd = (String) record.getAttribute("TRNG_ASK_DD_NUM");
            }
        }
        
//		Calendar calStr = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
 
		int nYearSta  = Integer.parseInt( sStrDt.substring(0,4) );
		int nMonthSta = Integer.parseInt( sStrDt.substring(4,6) ); 
		int nDateSta  = Integer.parseInt( sStrDt.substring(6,8) );

		int nYearEnd  = Integer.parseInt( sEndDt.substring(0,4) );
		int nMonthEnd = Integer.parseInt( sEndDt.substring(4,6) ); 
		int nDateEnd  = Integer.parseInt( sEndDt.substring(6,8) );
		
//		calStr.set(nYearSta, nMonthSta - 1, nDateSta, 0,0,1);
		calEnd.set(nYearEnd, nMonthEnd - 1, nDateEnd, 0,0,1);

        ds    = (PosDataset) ctx.get("dsOutTrngAskRacer");
        nSize = ds.getRecordCount();
        // ����
        for ( int i = 0; i < nSize; i++ )
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngAskRacer(record);
            }
        }
        
        SimpleDateFormat sdFormat = new SimpleDateFormat();
        sdFormat.applyPattern("yyyyMMdd");

        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateTrngAskRacer(record, sTrngAskSeq)) == 0 ) {
                	nTempCnt = insertTrngAskRacer(record, sTrngAskSeq);
                }
                updateTrngExptRacer(record);
                
        		Calendar calStr = Calendar.getInstance();
        		calStr.set(nYearSta, nMonthSta - 1, nDateSta, 0,0,1);

        		deleteTrngAskRacerResult(record);
                for ( int j = 0; j < Double.parseDouble(sAskDd); j++ ) {
                	
                	String sTempDt = sdFormat.format(calStr.getTime());
                	record.setAttribute("TRNG_DD", sTempDt);
                    if ( (nTempCnt = updateTrngAskRacerResult(record, sTrngAskSeq)) == 0 ) {
                    	nTempCnt = insertTrngAskRacerResult(record, sTrngAskSeq);
                    }

                    calStr.add(Calendar.DATE, 1);
                }
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �����Ʒ� �Ϸù�ȣ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected String getTrngAskSeq()
    {
        PosParameter param = new PosParameter();
        PosRowSet rowset = this.getDao("boadao").find("tbeb_trng_ask_fb001", param);

        String sTrngAskSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            sTrngAskSeq = (String) row.getAttribute("TRNG_ASK_SEQ".trim());
        }
            
        return sTrngAskSeq;
    }

    /**
     * <p> �����Ʒ� �Ϸù�ȣ�� ���� �Ϸù�ȣ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected String getNextTrngAskSeq(String sTrngAskSeq)
    {
        String sNextTrngAskSeq = sTrngAskSeq.substring(0, 3);
        int nTemp = Integer.parseInt(sTrngAskSeq.substring(4)) + 1;
        
        return sNextTrngAskSeq = sNextTrngAskSeq + Util.getFormat("0000", Integer.toString(nTemp));
    }

    /**
     * <p> �Ʒÿ�û�������� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngExptRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, "004");
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_EXPT_SEQ"));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_ub002", param);
        
        return dmlcount;
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
     * <p> �����Ʒ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertTrngAsk(PosRecord record, String sTrngAskSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sTrngAskSeq                                    );
        param.setValueParamter(i++, record.getAttribute("TRNG_PLC_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STR_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_END_DT  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_DD_NUM  ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_ASK_STAT_CD ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_ib001", param);
        return dmlcount;
    }

    /**
     * <p> �����Ʒü��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngAskRacer(PosRecord record, String sTrngAskSeq)
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
        param.setWhereClauseParameter(i++, sTrngAskSeq);
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO         ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �����Ʒü��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertTrngAskRacer(PosRecord record, String sTrngAskSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sTrngAskSeq                                    );
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_GRP_NO     ".trim()));
        param.setValueParamter(i++, record.getAttribute("MENTO_YN         ".trim()));
        param.setValueParamter(i++, record.getAttribute("COMMUTE_YN       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_ib001", param);
        return dmlcount;
    }
    

    /**
     * <p> �����Ʒü������ Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngAskRacerResult(PosRecord record, String sTrngAskSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_STAT_CD     ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sTrngAskSeq);
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_DD          ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �����Ʒü������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertTrngAskRacerResult(PosRecord record, String sTrngAskSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        record.setAttribute("TRNG_STAT_CD", "001");
        param.setValueParamter(i++, sTrngAskSeq                                    );
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DD          ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_STAT_CD     ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_ib001", param);
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
    	deleteTrngAskRacerResult(record);
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
