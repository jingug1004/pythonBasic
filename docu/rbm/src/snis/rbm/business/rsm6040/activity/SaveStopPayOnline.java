/*
 * ================================================================================
 * �ý��� : �нǽŰ� �¶�������
 * ���� �̸� : snis.rbm.business.rsm6040.activity.SaveStopPayOnline.java 
 * ���ϼ��� :  
 * �ۼ��� : 
 * ���� : 1.0.0 �������� : 2011- 10 - 23
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm6040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveStopPayOnline extends SnisActivity {
	
	public SaveStopPayOnline(){}

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
        
        sDsName = "dsTsnRcptResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            //������ҷ� �ٲ� ��
	            if( !record.getAttribute("RCPT_STAT_CD").equals("002") ) {
	            	if( getCancelYn(record).equals("N") ) {
	            		try { 
		        			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setReturnParam(ctx, "RESULT", "F");
		            		Util.setSvcMsg(ctx, "�ش� �ڷ�� �̹� ó�����Դϴ�.");
		            		
		            		return;
		            	}
	            	}
	            }
	            
	            nTempCnt = UpdateStoppayRcptOnline(record);	//�������� �� Ƽ�ϳ��� ������Ʈ	        	
			    nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }
        
        Util.setReturnParam(ctx, "RESULT", "S");
        Util.setSaveCount  (ctx, nSaveCount);
       
    }
    
   
    /**
     * <p> //�������� �� Ƽ�ϳ��� ������Ʈ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int UpdateStoppayRcptOnline(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
		param.setValueParamter(i++, record.getAttribute("ACDNT_TY_CD")	);
    	param.setValueParamter(i++, record.getAttribute("REFUND_TY_CD")	);
    	param.setValueParamter(i++, record.getAttribute("PUBL_DT")		);
    	param.setValueParamter(i++, record.getAttribute("BRNC_CD")		);
    	param.setValueParamter(i++, record.getAttribute("PLC_NO")		);
    	param.setValueParamter(i++, record.getAttribute("RACE_NO")		);
    	param.setValueParamter(i++, record.getAttribute("BUY_CNTNT")	);
    	param.setValueParamter(i++, record.getAttribute("REFR_DESC")	);
    	param.setValueParamter(i++, record.getAttribute("RCPT_STAT_CD")	);
    	param.setValueParamter(i++, record.getAttribute("TIC_CNTNT")	);
    	// ��������� ȯ���Ҷ��� �������ڸ� �����ش�.
    	if( record.getAttribute("RCPT_STAT_CD").equals("001")){
    		param.setValueParamter(i++, ""	);
    	}else if( record.getAttribute("RCPT_STAT_CD").equals("002")){
    		param.setValueParamter(i++, Util.getCurrDate()	);
    	}else{
    		param.setValueParamter(i++, record.getAttribute("RCPT_DT")	);
    	}
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")	);
        
        int dmlcount = this.getDao("rbmdao").update("rsm6040_u01", param);
	
        return dmlcount;
    }
    
    /**
     * <p> ȭ�� ���� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getCancelYn(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("MISS_REPO_NO"));
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm6040_s02", param);        
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CANCEL_YN"));

        return rtnKey;
    }
}
