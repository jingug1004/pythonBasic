/*================================================================================
 * �ý���			: ������������
 * �ҽ����� �̸�	: snis.rbm.business.rsm5020.activity.UpdateCfmCntnt
 * ���ϼ���		: ������������_Ȯ������ ����(Ȯ���� ������ ��Ȯ������ ������ �� �ִ�)
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-09
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateCfmCntnt extends SnisActivity {
	
	public UpdateCfmCntnt(){}

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
        
        sDsName = "dsCfmCntnt";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if( i == 0 && getValidCnt(record) > 0 ) {
	            	try { 
            			throw new Exception(); 
	            	} catch(Exception e) {
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsgCode(ctx, "SNIS_RBM_D006");
	            		
	            		return;
	            	}
		        }
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	            	nTempCnt = updateCfmCntnt(record);
			        nSaveCount = nSaveCount + nTempCnt; 
	            }
	        }	        
        }       
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> ������������_Ȯ������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCfmCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("FST_CFM_CD"));	//1��Ȯ���ڵ�
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD"));	 //������ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("SELL_CD"));	 //�ó�ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR")); //���޳⵵
        // ������ �������� ������ �ϳ��� ���⶧���� ���Խ����� ������ ��...
        String strCommNo =  record.getAttribute("BRNC_CD" ).toString();        
        if( strCommNo.equals("00") || strCommNo.equals("01") ||  strCommNo.equals("02") ||  strCommNo.equals("03") ||  strCommNo.equals("04") ||  strCommNo.equals("08") ){
        	strCommNo = "^(0[123468])$";    	
        }   
        
        param.setWhereClauseParameter(i++, strCommNo);	 //�߸�������ȣ
        param.setWhereClauseParameter(i++, strCommNo);	 //�߸�������ȣ
        param.setWhereClauseParameter(i++, strCommNo);	 //�߸�������ȣ

        int dmlcount = this.getDao("rbmdao").update("rsm5020_u01", param);

        return dmlcount;
    }
    
    /**
     * <p> ������������ �������� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			0 : Ȯ������ 0�ʰ� : Ȯ���Ұ��� 
     * @throws  none
     */
    protected int getValidCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵      
        // ������ �������� ������ �ϳ��� ���⶧���� ���Խ����� ������ �Ǵ�.
        String strCommNo =  record.getAttribute("BRNC_CD" ).toString();        
        if( strCommNo.equals("00") || strCommNo.equals("01") ||  strCommNo.equals("02") ||  strCommNo.equals("03") ||  strCommNo.equals("04") ||  strCommNo.equals("08") ){
        	strCommNo = "^(0[12348])$";    	
        }     
        param.setWhereClauseParameter(i++, strCommNo);  	//�����ڵ�
        param.setWhereClauseParameter(i++, strCommNo);  	//�����ڵ�
        param.setWhereClauseParameter(i++, strCommNo);  	//�����ڵ�
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5020_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.parseInt(rtnKey);
    }
}