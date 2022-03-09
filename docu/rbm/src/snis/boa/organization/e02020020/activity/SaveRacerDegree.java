/*================================================================================
 * �ý���			: ��޺��� �Է�
 * �ҽ����� �̸�	: snis.boa.system.e02020020.activity.SaveRacerDegree.java
 * ���ϼ���		: ��޺��� �Է�
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-12-05
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02020020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ��������� ����(�Է�/����)�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRacerDegree extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveRacerDegree()
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
        int        nSize        = 0;
        String     sDsName = "";
        sDsName = "dsOutRacerDegree";
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
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // ��������̷� �Է�
        sDsName = "dsOutRacerDegree";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	         nTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	        	if ( updateDegree(record, ctx) < 1 ) {
	        		nTempCnt = insertDegree(record, ctx);
	        	}
		        	
	        	updateRacerMaster(record, ctx);
			    
		        nSaveCount 		= nSaveCount 	+ 	nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    /**
     * <p> ��������̷� �Է� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertDegree(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"      ));
        param.setValueParamter(i++, Util.getCurrentDate("yyyyMMdd"));
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_GRD" ));
        param.setValueParamter(i++, ctx   .get         ("RMK"           ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_racer_dgre_ib001", param);

        return dmlcount;
    }
    
    /**
     * <p> ��������̷� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateDegree(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_GRD" ));
        param.setValueParamter(i++, ctx   .get         ("RMK"           ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"      ));
        param.setWhereClauseParameter(i++, Util.getCurrentDate("yyyyMMdd"));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_dgre_ub001", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ������� ����(����������) </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerMaster(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_GRD" ));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"      ));

        int dmlcount = this.getDao("boadao").update("tbec_racer_master_ub001", param);
        
        return dmlcount;
    }
}
