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
package snis.rbm.business.rbr1060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRptPrepSituBonjang extends SnisActivity {
	
	public SaveRptPrepSituBonjang(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        
        sDsName = "dsRptPrepSitu";        
        if ( ctx.get(sDsName) != null ) {
        	
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	        	
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nSaveCount = saveReport(record);
	            }
	        }
        }
        

        sDsName = "dsRptPrepSituNum";        
        if ( ctx.get(sDsName) != null ) {
        	
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	        	
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nSaveCount += saveReportNum(record);
	            }
	        }
        }
  
        Util.setSaveCount  (ctx, nSaveCount );
    }

    /**
     * <p> ���ְ��� �غ��Ȳ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert or update ����
     * @throws  none
     */
    protected int saveReport(PosRecord record)
    {		
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACE_DAY"));           // 1. ������ 
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));            // 2. �����ڵ�
        param.setValueParamter(i++, record.getAttribute("WRKG_NUM"));        	// 3. �ٹ��ο�	
        param.setValueParamter(i++, record.getAttribute("DUTY_OFF_NUM"));       // 4. �ް��ο�
        param.setValueParamter(i++, record.getAttribute("NUM_CHK_RSLT"));   	// 5. �ο� �̻�����
        param.setValueParamter(i++, record.getAttribute("CHK_POINT"));   		// 6. �����׸�
        param.setValueParamter(i++, record.getAttribute("CHK_POINT_RSLT"));     // 7. ���˰��
        param.setValueParamter(i++, record.getAttribute("SIGNFT_POINT"));       // 8. Ư�̻���
        param.setValueParamter(i++, SESSION_USER_ID);                          	//10. ���/������ ���  
  
        int dmlcount = this.getDao("rbmdao").update("rbr1050_m01", param);
        return dmlcount;
    }

    /**
     * <p> ���ְ��� �غ��ο� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert or update ����
     * @throws  none
     */
    protected int saveReportNum(PosRecord record)
    {		
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACE_DAY"));           // 1. ������ 
        param.setValueParamter(i++, record.getAttribute("DEPT_SEQ"));           // 2. �μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));            // 3. �μ���
        param.setValueParamter(i++, record.getAttribute("WRKG_NUM"));        	// 4. �ٹ��ο�	
        param.setValueParamter(i++, record.getAttribute("DUTY_OFF_NUM"));       // 5. �ް��ο�
        param.setValueParamter(i++, SESSION_USER_ID);                          	// 6. ���/������ ���  
  
        int dmlcount = this.getDao("rbmdao").update("rbr1060_m01", param);
        return dmlcount;
    }

}
