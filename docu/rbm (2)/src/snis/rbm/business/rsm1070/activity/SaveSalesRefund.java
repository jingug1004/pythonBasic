/*
 * ================================================================================
 * �ý��� : 
 * ���� �ҽ����� �̸� : snis.rbm.system.rsm1070.activity.SaveSalesRefund.java ����
 * ���� : ȯ�Ҿ� ����
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011- 
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm1070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSalesRefund extends SnisActivity {

	public SaveSalesRefund(){
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsRefund";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = saveRefund(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> �����ƿ�Ȱ�����  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRefund(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        
        param.setValueParamter(i++, record.getAttribute("DIV_TOTAL"));		//��������
        param.setValueParamter(i++, record.getAttribute("REFUND"));			//ȯ�Ҿ�

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));		//����ó
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//���س⵵
        param.setValueParamter(i++, record.getAttribute("TMS"));			//ȸ��

        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));		//ȸ�� ����
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));		//���ֹ�ȣ
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));		//�Ǹ�ó
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//comm ��ȣ
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));			//div ��ȣ
        param.setValueParamter(i++, SESSION_USER_ID);						//�ۼ���
        param.setValueParamter(i++, SESSION_USER_ID);						//������ 
        
                
        int dmlcount = this.getDao("rbmdao").update("rsm1070_m01", param);
        
        return dmlcount;
    }
    
    

}
