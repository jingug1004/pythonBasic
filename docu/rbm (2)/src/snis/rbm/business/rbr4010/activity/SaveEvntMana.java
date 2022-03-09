/*================================================================================
 * �ý���			: ���  ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * ���ϼ���		: ��� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEvntMana extends SnisActivity {
	
	public SaveEvntMana(){}
 
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
        
        sDsName = "dsEvntMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = saveEvntMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {	
	            	int nDeleteValid = getEvntHistManaKey(record);
	            	
	            	//��ǿ� ���� ����̷��� ������ ��, ���� �Ұ���
	            	if( nDeleteValid > 0 ) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_A007");
		            		
		            		return;
		            	} 
	            	} 
	            	
	            	nDeleteCount = nDeleteCount + deleteEvntMana(record);
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> ��� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEvntMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));	//��Ǽ���
        param.setValueParamter(i++, record.getAttribute("GEN_DT"));		//�߻�����
        param.setValueParamter(i++, record.getAttribute("EVNT_NO"));	//��ǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("EVNT_NM"));	//��Ǹ�
        
        param.setValueParamter(i++, record.getAttribute("MNG"));		//�����
        param.setValueParamter(i++, record.getAttribute("EVNT_CNTNT"));	//��ǿ��
        param.setValueParamter(i++, record.getAttribute("END_YN"));		//���Ῡ��
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rbr4010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEvntMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));   //��Ǽ���

        int dmlcount = this.getDao("rbmdao").update("rbr4010_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ��ǿ� ���� ����̷� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��ǿ� �ɷ��ִ� ����̷� ����
     * @throws  none
     */
    protected int getEvntHistManaKey(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));	//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("EVNT_SEQ" ));	//��Ǽ���
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbr4010_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}