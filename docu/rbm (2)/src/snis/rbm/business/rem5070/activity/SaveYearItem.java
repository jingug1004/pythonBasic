/*================================================================================
 * �ý���			: �����¼��� ����
 * �ҽ����� �̸�	: snis.rbm.business.rem5070.activity.SaveYearItem.java
 * ���ϼ���		: �����¼��� ��ǰ ��� ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2013-08-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rem5070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveYearItem extends SnisActivity {
	
	public SaveYearItem(){
		
		
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

        sDsName = "dsYearItem";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveYearItem(record);
		        }else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	// �԰����� �����ϴ� �� ���� üũ
		        	int nDeleteValid = CountYearItemStor(record);
	            	
	            	//�Ҹ�ǰ��û ���� �׸� �� ���ο��ΰ�  '����'��  ������ ��, ���� �Ұ���
	            	if( nDeleteValid > 0 ) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_C011");
		            		
		            		return;
		            	} 
	            	}
		        	
		        	nDeleteCount += deleteYearItem(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> IP �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveYearItem(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));			//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO"));	//������ �ܰ����ǰ���Ϸù�ȣ
        param.setValueParamter(i++, record.getAttribute("STR_DT"));				//�ܰ���� ��������
        param.setValueParamter(i++, record.getAttribute("END_DT"));				//�ܰ���� ��������
        param.setValueParamter(i++, record.getAttribute("GOOD_TYPE_CD"));		//��ǰ�з��ڵ�
        param.setValueParamter(i++, record.getAttribute("GOOD_NM"));			//��ǰ��
        param.setValueParamter(i++, record.getAttribute("UNIT"));				//����
        param.setValueParamter(i++, record.getAttribute("PRICE"));				//�ܰ�
        param.setValueParamter(i++, record.getAttribute("RMK"));				//���
        param.setValueParamter(i++, SESSION_USER_ID);							//�Է���/������ ���
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));			//�����ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rem5070_m01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> IP  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteYearItem(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	
        param.setValueParamter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO"));
        
        
        int dmlcount = this.getDao("rbmdao").update("rem5070_d01", param);

        return dmlcount;
    }

    /**
     * <p> ����� ���� ���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��ǿ� �ɷ��ִ� ����̷� ����
     * @throws  none
     */
    protected int CountYearItemStor(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        PosRowSet keyRecord = null;

        Double dYearItemSeqNo;
        dYearItemSeqNo = Double.valueOf(0.0);
        dYearItemSeqNo = (Double)record.getAttribute("YEAR_ITEM_SEQ_NO");
        System.out.println("sYearItemSeqNo="+dYearItemSeqNo);
        
        if (dYearItemSeqNo != null) 
        
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));	//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO" ));	    //�԰���
        
        keyRecord = this.getDao("rbmdao").find("rem5070_s03", param);
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }

}

