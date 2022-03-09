/*================================================================================
 * �ý���			: ���� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr1010.activity.SaveLastYear.java
 * ���ϼ���		: ���⵵��  ���� ������ ���� �⵵�� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs9010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSalesPlan extends SnisActivity {
	
	public SaveSalesPlan(){}

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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	int nSize      = 0;
    	
    	String sFlag = "N";	//�޼����� ����ڿ��� ����� ������ ���ϴ� Flag
    	String sMsg  = "";
    	PosDataset ds;
    	
    	String sPlanType = "";
        String sDsName = "dsNetPlanSave";
        
        sPlanType = (String)ctx.get("PLAN_TYPE");
        if ("N".equals(sPlanType)) sDsName = "dsTotPlanSave"; 
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 	 ) {	
	            	
			        nSaveCount += saveSalesPlan(record);			        
		        }
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> ���� ������/�Ѹ��� ��ȹ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSalesPlan(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	   	// �������ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		// ���س⵵
        param.setValueParamter(i++, record.getAttribute("STND_MONTH"));		// ���ؿ�
        param.setValueParamter(i++, record.getAttribute("PLAN_TYPE"));		// ������/�Ѹ��� ����("Y":������)
        param.setValueParamter(i++, record.getAttribute("NET_SALES_AMT"));	// ������/�Ѹ��� ��ǥ
        param.setValueParamter(i++, SESSION_USER_ID);					   	// �����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs9010_m01", param);
        
        return dmlcount;
    }
    
}
