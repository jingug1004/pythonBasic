/*
 * ================================================================================
 * �ý��� : �������� �� ���� ����
 * ���� �̸� : snis.rbm.business.rsm6020.activity.SaveStopPay.java 
 * ���ϼ��� :  
 * �ۼ��� : 
 * ���� : 1.0.0 �������� : 2011- 09 - 16
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm6020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class UpdateStoppay extends SnisActivity {
	
	public UpdateStoppay(){}

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
        int nTempCnt     = 0;
        
        sDsName = "dsTsnResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	                    	
		        nTempCnt = UpdateTsn(record);	//TSN Update
		        		        	
			    nSaveCount = nSaveCount + nTempCnt;
		        continue;		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);       
    }
        
    /**
     * <p> �������� ������ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int UpdateTsn(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        int dmlcount=0;
            
        param.setValueParamter(i++, record.getAttribute("TIC_TYPE_CD"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("PLC_NO"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));        
        param.setValueParamter(i++, record.getAttribute("SALES_AMT"));        
        param.setValueParamter(i++, record.getAttribute("TIC_CONT"));
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC"));
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_ID"));
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_DEPT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO"));
        param.setValueParamter(i++, record.getAttribute("TSN_NO"));	
        
        dmlcount = this.getDao("rbmdao").update("rsm6020_u03", param);
        return dmlcount;

    }  
}
