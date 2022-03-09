/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.boa.racer.e03010010.activity.DeleteRacerInfo.java
 * ���ϼ���		: ������������
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-12-05
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03010010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class DeleteRacerInfo extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public DeleteRacerInfo()
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

        sSessionUserId = (String) ctx.getSessionUserData("USER_ID");

        ds    = (PosDataset)ctx.get("dsOutRacer");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsOutRacerFam");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }

        ds    = (PosDataset)ctx.get("dsOutRacerAcad");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }        

        ds    = (PosDataset)ctx.get("dsOutRacerRela");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
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

        sRacerNo = (String) ctx.get("RACER_NO");
        ds    = (PosDataset) ctx.get("dsOutRacer");
        nSize = ds.getRecordCount();

        // ���������� ����
    	int effectedRowCnt = 0;
    	effectedRowCnt = deleteRacer(sRacerNo);
    }

 
    /**
     * <p> ���������� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacer(String sRacerNo) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sRacerNo                           );
        
        int dmlcount = this.getDao("boadao").update("tbec_racer_master_dc001", param);
        
        if (dmlcount > 0)	{ //������������ ���������� ������Ʈ �Ǹ�..
        	//���������� ����
        	PosParameter param2 = new PosParameter();        	
            i = 0;
            param2.setWhereClauseParameter(i++, sRacerNo                           );
        	dmlcount = this.getDao("boadao").update("tbec_racer_detail_dc001", param2);

        	//������������ ����
        	PosParameter param3 = new PosParameter();  
            i = 0;
            param3.setWhereClauseParameter(i++, sRacerNo                           );
            
            dmlcount = this.getDao("boadao").update("tbec_racer_family_dc002", param3);
            
        	//�����з����� ����
        	PosParameter param4 = new PosParameter();
            i = 0;
            param4.setWhereClauseParameter(i++, sRacerNo                           );

        	dmlcount = this.getDao("boadao").update("tbec_racer_academic_dc002", param4);
        	
        	//������������ ����
        	PosParameter param5 = new PosParameter();  
            i = 0;
            param5.setWhereClauseParameter(i++, sRacerNo                           );

        	dmlcount = this.getDao("boadao").update("tbec_racer_relation_dc002", param5);

        }
        
        return dmlcount;
    }
 

}