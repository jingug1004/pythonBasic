/*================================================================================
 * �ý���			: �Ҹ�ǰ ��û
 * �ҽ����� �̸�	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * ���ϼ���		: �Ҹ�ǰ ��û������ ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs3020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplReq extends SnisActivity {
	
	public SaveSupplReq(){}

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
        
        sDsName = "dsSupplReq";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveSupplReq(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {		       
	            	int nDeleteValid = selectProgStatCd(record);
	            	
	            	//�Ҹ�ǰ��û ���� �׸� �� ���ο��ΰ�  '����'��  ������ ��, ���� �Ұ���
	            	if( nDeleteValid > 0 ) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_E012");
		            		
		            		return;
		            	} 
	            	}
	            	nDeleteCount = nDeleteCount + deleteSupplReq(record);
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �Ҹ�ǰ��û �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSupplReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   		//��û�ڻ��
        param.setValueParamter(i++, record.getAttribute("SEQ"));		   //����
        param.setValueParamter(i++, record.getAttribute("REQ_CNTNT"));	   //��û����
        param.setValueParamter(i++, record.getAttribute("PROG_STAT_CD"));  //���ο���
        
        param.setValueParamter(i++, record.getAttribute("REPLY"));		   //�亯
        param.setValueParamter(i++, record.getAttribute("MNG_REQ_YN"));	   //�����ڽ�û����
        param.setValueParamter(i++, record.getAttribute("APRV_DT"));	   //��������
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));	   //���,���� ����
        param.setValueParamter(i++, SESSION_USER_ID);					   //�����ID(�ۼ���)       
        param.setValueParamter(i++, SESSION_USER_ID);					   //�����ID(������)
                		
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   		//��û�ڻ��
        int dmlcount = this.getDao("rbmdao").update("rbs3020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ��û ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSupplReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	    //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));   //��û�ڻ��
        param.setValueParamter(i++, record.getAttribute("SEQ"));        //����
        
        int dmlcount = this.getDao("rbmdao").update("rbs3020_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ���ο��� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��ǿ� �ɷ��ִ� ����̷� ����
     * @throws  none
     */
    protected int selectProgStatCd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT" ));	    //��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID" ));	//��û�ڻ��
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));	    //����
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3020_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}