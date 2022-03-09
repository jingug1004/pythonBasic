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


public class SaveStoppay extends SnisActivity {
	
	public SaveStoppay(){}

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
    	
    	String strErr = saveState(ctx);
    	if( strErr.equals("F") ){    	
    
    		return PosBizControlConstants.FAILURE;
    	}else{
    		return PosBizControlConstants.SUCCESS;
    	
    	}
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected String saveState(PosContext ctx) 
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
	            
		        	// ������ �ش� �������ڿ� tns no�������ϴ��� üũ
		        	if (selectTnsCnt(record) > 0 ) {
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "�Է��Ͻ� TSN�� �ش� �������ڿ� �����մϴ�!");
		            		return "F";
		            	}
		        	}
		        	
			        
		        	if(record.getAttribute("MISS_REPO_CD").equals("001") && record.getAttribute("RCPT_STAT_CD").equals("002")){
		        		//�¶��� ���� �� �ڷ����� ó�� 
		        		UpdateStoppayOnline(record);
		        	}
		        	
		        	nTempCnt = InsertTsn(record);	//TSN Insert
		        		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
        return "";
    }
    /**
     * <p> �⺻Ű�� ���� �Ҹ�ǰ ���� �Ǽ� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			
     * @throws  none
     */
    protected int selectTnsCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("MISS_REPO_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("TSN_NO"));        
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm6020_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        return Integer.valueOf(rtnKey).intValue();
    }
    
    /**
     * <p> �нǽŰ��ȣ��ȸ(������) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			
     * @throws  none
     */
    protected String getMissRepoNo() 
    {         
        String rtnKey = "";
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm6020_s04");
        
        PosRow pr[] = keyRecord.getAllRow();        
        rtnKey = String.valueOf(pr[0].getAttribute("MISS_REPO_NO"));
        return rtnKey;
    }
    
    
    /**
     * <p> �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertTsn(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        int dmlcount=0;

	
        param.setValueParamter(i++, record.getAttribute("PUBL_DT"));
        param.setValueParamter(i++, record.getAttribute("TSN_NO"));	
        param.setValueParamter(i++, record.getAttribute("TIC_TYPE_CD"));
        param.setValueParamter(i++, record.getAttribute("PLC_NO"));	        
        param.setValueParamter(i++, record.getAttribute("SALES_AMT"));        
        param.setValueParamter(i++, record.getAttribute("STOPPAY_REQ_DEPT"));
        param.setValueParamter(i++, record.getAttribute("STOPPAY_REQ_NM"));    
        param.setValueParamter(i++, SESSION_USER_ID); // ������        
        param.setValueParamter(i++, Util.getCurrDate());        
        
        param.setValueParamter(i++, record.getAttribute("TIC_STAS"));        
        param.setValueParamter(i++, record.getAttribute("TIC_CONT"));
        
        param.setValueParamter(i++, record.getAttribute("VERI_RSLT_CD"));
        param.setValueParamter(i++, record.getAttribute("VERI_RSLT_DT"));  
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_CD"));        
        if( record.getAttribute("MISS_REPO_CD").equals("001")){ 
        	 	// �¶���
        	 param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO") );        	  		
             	
        }else{ 	// ��������
        	 param.setValueParamter(i++, getMissRepoNo() );        	 
        }
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        
        //�¶��� ������
        param.setValueParamter(i++, record.getAttribute("CUST_NM"));		//����
        param.setValueParamter(i++, record.getAttribute("CUST_PHONE_NO"));	//����ȣ
        
        
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC"));		//Ư�̻���
        
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_ID"));		//Ư�̻���
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_DEPT"));		//Ư�̻���
        
        
        param.setValueParamter(i++, SESSION_USER_ID); // �����
        dmlcount = this.getDao("rbmdao").update("rsm6020_i01", param);
        return dmlcount;

    }  
    
    
    /**
     * <p> �¶��� ���� �� �ڷ����� ó�� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int UpdateStoppayOnline(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

    	param.setValueParamter(i++, "005"		);

        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")	);
        
        int dmlcount = this.getDao("rbmdao").update("rsm6020_u04", param);
	
        return dmlcount;
    }
}
