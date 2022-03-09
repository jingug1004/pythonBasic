/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02070020.activity.SaveTrngExptRacer.java
 * ���ϼ���		: �Ʒÿ������� ���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02070020.activity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.StringTokenizer;

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
* �����Ͽ� �Ʒÿ��������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTrngExptRacer extends SnisActivity
{    
    public SaveTrngExptRacer()
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

        ds    = (PosDataset)ctx.get("dsOutTrngExptRacer");
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

        ds    = (PosDataset) ctx.get("dsOutTrngExptRacer");
        nSize = ds.getRecordCount();

        // ����
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngExptRacer(record);
            }
        }
        
        // ���
        String sTrngExptSeq = getTrngExptSeq();
        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateTrngExptRacer(record)) == 0 ) {
                	// ���� �ڷᰡ �����ϴ� ���θ� üũ
                	/* 2013.4.10 �̼��� ���ϴ� ��찡 �����Ͽ� �ٽ� �米���� ��û�� �� ����(��������� ��û����)
	        		if( ExptRaceKeyCount(record) > 0 ) {
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_boa");
		            		
		            		String sDate = (String)record.getAttribute("RACER_NO");
		            		Util.setSvcMsg(ctx, "������ȣ[ " + sDate + " ]��  �̹� ��ϵǾ� �ֽ��ϴ�.");
		            		return;
		            	}
	        		}
	        		*/
                	sTrngExptSeq = getNextTrngExptSeq(sTrngExptSeq);
                	nTempCnt = insertTrngExptRacer(record, sTrngExptSeq);
                	
                	//����Ʒ� & Ŀ�´�Ƽ ��û�� ���  �������·� �����ϰ� SMS�� �߼��Ѵ�. (���� ���������� �ƴѰ�쿡�� �߼�)
                	//if(!"200".equals(record.getAttribute("ACCEPT_STAT")) && "004".equals(record.getAttribute("TRNG_RSN_CD")) && !"".equals(Util.NVL(record.getAttribute("TRNG_RQST_SEQ")))){
            		if(!"200".equals(record.getAttribute("ACCEPT_STAT")) && !"".equals(Util.NVL(record.getAttribute("TRNG_RQST_SEQ")))){
                    	updateTrngRqst(record);
                		
                		String msg = "��û�Ͻ� �Ʒ��� �����Ǿ����ϴ�. - ���� ���";
                		sendSms(Util.trim(record.getAttribute("CELPON_NO")), Util.trim(record.getAttribute("NM_KOR")), msg);
                	}
                }

            	nSaveCount = nSaveCount + nTempCnt;
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �Ʒÿ������� �Ϸù�ȣ ��ȸ </p>
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
     * <p> �Ʒÿ������� �Ϸù�ȣ�� �ش��ϴ� ���� �Ϸù�ȣ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected String getNextTrngExptSeq(String sTrngExptSeq)
    {
        String sNextTrngExptSeq = sTrngExptSeq.substring(0, 3);
        int nTemp = Integer.parseInt(sTrngExptSeq.substring(3)) + 1;
        
        return sNextTrngExptSeq = sNextTrngExptSeq + Util.getFormat("0000", Integer.toString(nTemp));
    }

    /**
     * <p> �Ʒÿ������� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTrngExptRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RSN_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR        ".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_DD_NUM ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_STAT_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("COMP_EDU_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("INJURY_SEQ       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Ʒÿ������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertTrngExptRacer(PosRecord record, String sTrngExptSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sTrngExptSeq                                   );
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RSN_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR        ".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_DD_NUM ".trim()));
        param.setValueParamter(i++, "000");
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("INJURY_SEQ       ".trim()));
        param.setValueParamter(i++, record.getAttribute("COMP_EDU_CD      ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_SEQ"));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> �Ʒÿ������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTrngExptRacer(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> �Ʒÿ��������� ������ ��ϵǾ����� ���θ� üũ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int ExptRaceKeyCount(PosRecord record) 
    {
    	String sTrngRsnCd = String.valueOf(record.getAttribute("TRNG_RSN_CD"));
    	
        PosParameter param = new PosParameter();
        int i = 0;

        if (sTrngRsnCd.equals("001") || sTrngRsnCd.equals("002") ||  
            sTrngRsnCd.equals("003")) {	// ����, �������, �λ��Ʒ�
        	
            param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));  	// ������ȣ
        	param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));  	// ���س⵵
        	param.setWhereClauseParameter(i++, record.getAttribute("SANC_ISSUE_NO"));  	// �Ϸù�ȣ
        	param.setWhereClauseParameter(i++, record.getAttribute("INJURY_SEQ"));  // �λ��Ϸù�ȣ

        	PosRowSet rtnRecord = this.getDao("boadao").find("tbeb_trng_expt_racer_kc001", param);  
            
            PosRow pr[] = rtnRecord.getAllRow();
            
            String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
            
            return Integer.valueOf(rtnQty).intValue();

        	
        } else {
        	return 0;
        }

    }
    
    /**
     * <p> �Ʒý�û���� update </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  none
     * @throws  none
     */
    protected void updateTrngRqst(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_SEQ"));

        this.getDao("boadao").update("tbeb_trng_rqst_ub002", param);
    }
    
    
  //SMS ����
	private boolean sendSms(String recPhoNo, String recNm, String msg){		
		String recPho1 = "";
		String recPho2 = "";
		String recPho3 = "";
		
		String senPho1 = "";
		String senPho2 = "";
		String senPho3 = "";
		
		StringTokenizer recSt = new StringTokenizer(recPhoNo, "),-");
		
		recPho1 = recSt.nextToken().trim();
		recPho2 = recSt.nextToken().trim();
		recPho3 = recSt.nextToken().trim();
		
		StringTokenizer senSt = new StringTokenizer(SESSION_TEL_NO, "),-");
		
		senPho1 = senSt.nextToken().trim();
		senPho2 = senSt.nextToken().trim();
		senPho3 = senSt.nextToken().trim();
		
		boolean isSuccess = false;
		CallableStatement proc = null;
		Connection conn = null;
	
		try{
			conn = this.getDao("boadao").getDBConnection();
			proc = conn.prepareCall("{CALL SMS.SP_SEND_SMS(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1, recPho1);
			proc.setString(2, recPho2);
			proc.setString(3, recPho3);
			proc.setString(4, recNm);
			proc.setString(5, senPho1);
			proc.setString(6, senPho2);
			proc.setString(7, senPho3);
			proc.setString(8, SESSION_USER_NM);
			proc.setString(9, msg);
			proc.setString(10, "00000000");
			proc.setString(11, "00000000");
			proc.setString(12, "MRASYS");
			proc.setString(13, SESSION_USER_ID);
			
			isSuccess = proc.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		
		return isSuccess;
	}
    
}
