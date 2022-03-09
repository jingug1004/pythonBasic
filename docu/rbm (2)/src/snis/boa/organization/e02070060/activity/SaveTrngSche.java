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
package snis.boa.organization.e02070060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import java.sql.CallableStatement;import java.sql.Connection;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Ʒñ����ڵ� �� ���� �����ڵ带 ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���缱
* @version 1.0
*/
public class SaveTrngSche extends SnisActivity
{    
    public SaveTrngSche()
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

    	PosDataset dsTrngSche;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sJobType    = (String)ctx.get("JOB_TYPE");
    	if ("BATCH".equals(sJobType)) {
    		batchInsert(ctx);    		
    	} else {
	        dsTrngSche     = (PosDataset) ctx.get("dsTrngSche");
	        nSize = dsTrngSche.getRecordCount();
	
	        // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = dsTrngSche.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	            	nDeleteCount = nDeleteCount + deleteTrngSche(record);
	            } else {
	            	nSaveCount = mergeUpdateTrngSche(record);
	            }
	        }
    	}

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �Ʒñ��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeUpdateTrngSche(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STR_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("STR_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("END_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DD_NUM".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        
        int dmlcount = this.getDao("boadao").update("tbeb_trng_sche_mb001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Ʒñ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTrngSche(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_YEAR".trim()));
        param.setValueParamter(i++, record.getAttribute("STR_DT".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_DD_NUM".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_sche_db001", param);
        
        return dmlcount;
    }


    /**
     * <p> �Ʒñ��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int batchInsert(PosContext ctx)
    {
    	CallableStatement proc = null; 
    	Connection conn = null; 
        String sTrngYear    = (String)ctx.get("TRNG_YEAR");
    	try {
    		conn = this.getDao("boadao").getDBConnection();    	
	        
	        int i = 1;
	    	proc = conn.prepareCall("{call CREATE_TRNG_SCHE(?, ?)}");
	        proc.setString(i++, sTrngYear);
	        proc.setString(i++, SESSION_USER_ID);

	        int nRslt = proc.executeUpdate();
	        
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        return 0;
    }
    
}
