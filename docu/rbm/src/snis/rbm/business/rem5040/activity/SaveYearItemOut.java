/*================================================================================
 * �ý���			: �����¼��� ����
 * �ҽ����� �̸�	: snis.rbm.business.rem5040.activity.saveGoodUnStor.java
 * ���ϼ���		: �����¼��� ��ǰ ��� ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2013-08-16
 * ������������	: 
 * ����������		:  
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rem5040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveYearItemOut extends SnisActivity {
	
	public SaveYearItemOut(){		 
		
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
    	String sUnStorDt = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsYearItemOut";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        sUnStorDt    = (String)ctx.get("UNSTOR_DT");

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
		        	
		        	// �ܰ�  ��Ȯ��(��ȸ�� ���ÿ� 2������ �Է��ϴ� ��� ����)
	            	if(!CheckBalance(record)) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_C010");
		            		
		            		return;
		            	} 
	            	}
	            	
	            	nSaveCount += saveYearItemOut(record, sUnStorDt);
		        }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }


    /**
     * <p> �ܰ�����ǰ�� ���� ����� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveYearItemOut(PosRecord record, String UnStorDt) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO"));			//����
        param.setValueParamter(i++, UnStorDt);								//�������
        param.setValueParamter(i++, record.getAttribute("UNSTOR_CNT"));		//������
        param.setValueParamter(i++, record.getAttribute("DISUSE_CNT"));		//������
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���
        param.setValueParamter(i++, "001");									//����
        param.setValueParamter(i++, SESSION_USER_ID);						//�԰��� ���
        param.setValueParamter(i++, record.getAttribute("APRV_ID"));		//������ ���
        param.setValueParamter(i++, null);									//�����Ͻ�
        param.setValueParamter(i++, SESSION_USER_ID);						//�Է���/������ ���
        
        int dmlcount = this.getDao("rbmdao").update("rem5040_m02", param);
        
        
        return dmlcount;
    }
    

    /**
     * <p> ��� ���翩�� ���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��ǿ� �ɷ��ִ� ����̷� ����
     * @throws  none
     */
    protected boolean CheckBalance(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));			//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO" ));	//�ܰ�����ǰ����
        param.setWhereClauseParameter(i++, record.getAttribute("UNSTOR_DT" ));	    	//�������
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));			//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO" ));	//�ܰ�����ǰ����
        param.setWhereClauseParameter(i++, record.getAttribute("UNSTOR_DT" ));	    	//�������
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rem5040_s04", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        
        //double nBalanceCnt = 0;
        double nUnstorCntCum = 0;
        double nUnstorCntThis = 0;
        
        //nBalanceCnt = Double.parseDouble(record.getAttribute("BALANCE_CNT").toString());
        nUnstorCntThis = Double.parseDouble(record.getAttribute("UNSTOR_CNT").toString()) +
				         Double.parseDouble(record.getAttribute("DISUSE_CNT").toString());
        nUnstorCntCum = Double.parseDouble(rtnKey);
        //System.out.println("nBalanceCnt="+nBalanceCnt);
        //System.out.println("CheckBalance::::nUnstorCntCum="+nUnstorCntCum);
        //System.out.println("CheckBalance::::nUnstorCntThis="+nUnstorCntThis);
        if (nUnstorCntCum < nUnstorCntThis) {	// ���� �ִ� ���� ���� ���/��� ������ ���� ���
        	return false;
        }
        return true;
    }
}
