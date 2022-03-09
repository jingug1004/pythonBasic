/*
 * ================================================================================
 * �ý��� : ���� 
 * �ҽ����� �̸� : snis.rbm.business.rem4050.activity.SaveSendVeri.java 
 * ���ϼ��� : ���������ֱ� ���� 
 * �ۼ��� : ���缱
 * ���� : 1.0.0 
 * �������� : 2012.12.29
 * ������������ :
 * ���������� : 
 * ������������ :
 * =================================================================================
 */

package snis.rbm.business.rem4050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SaveSendVeri extends SnisActivity {

	public SaveSendVeri(){		
		
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
    /**
     * @param ctx
     */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;

        // ��������   ����
        sDsName = "dsSendVeri";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);	            	
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {		        	
			        nSaveCount += saveSendVeri(record);			        
		        }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �������� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSendVeri(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("VERI"));	   		//�����ڵ�
        param.setValueParamter(i++, SESSION_USER_ID);					   		//�����ID(������)
        param.setValueParamter(i++, record.getAttribute("TRAN_DT"));	//��ȸ�з� ����
                		
        int dmlcount = this.getDao("rbmdao").update("rem4050_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePayAdjScrRule(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	    	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));	//��ȸ�з��ڵ�
        param.setValueParamter(i++, record.getAttribute("RANK"));	    		//����
        param.setValueParamter(i++, record.getAttribute("MRTN_RACE_TYPE"));	    //�ڽ�����
        
        int dmlcount = this.getDao("rbmdao").update("rbs5050_d01", param);

        return dmlcount;
    }
}
