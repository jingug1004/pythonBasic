/*================================================================================
 * �ý���			: ������ ����
 * �ҽ����� �̸�	: snis.rbm.business.rbb5210.activity.SavePayAdj.java
 * ���ϼ���		: �޿������λ�� ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2012-11-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs5210.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SavePayAdj extends SnisActivity {
	
	public SavePayAdj(){}

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

        // ����� ���ޱ���  ����
        sDsName = "dsPayAdj";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            // ���� ���峻�� ��ü ����
	            nDeleteCount = nDeleteCount + deletePayAdj(record);

	            nSaveCount += savePayAdj(record);
			    
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �޿������λ�� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int savePayAdj(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));			//������ȣ
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));			//���⵵
        param.setValueParamter(i++, record.getAttribute("CNTR_AMT"));			//����
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_CD"));			//���������ڵ�
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_SCR"));  		//��������
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_RATE"));  		//�����λ��   
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_AMT"));  		//�����λ�ݾ� 
        param.setValueParamter(i++, SESSION_USER_ID);					   		//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5210_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �޿������λ�� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePayAdj(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACER_ID"));			//������ȣ
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));			//���⵵
        
        int dmlcount = this.getDao("rbmdao").update("rbs5210_d01", param);

        return dmlcount;
    }


}