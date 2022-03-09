/*
 * ================================================================================
 * �ý��� : �������� �� ���� ����
 * ���� �̸� : snis.rbm.business.rsm6020.activity.SaveStopPay.java 
 * ���ϼ��� :  VW_KDW_TF_REC_LOCK ���� ������ �μ�Ʈ => TBRD_STOPPAY_CNCL_CNTNT
 * �ۼ��� :  ���ֿ�
 * ���� : 1.0.0 �������� : 2017- 11 - 17
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

import java.sql.CallableStatement;
import java.sql.Connection;


public class SaveStoppayInsert extends SnisActivity {
	
	public SaveStoppayInsert(){}

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
    protected int saveState(PosContext ctx) 
    {
    	PosParameter param = new PosParameter();   
        
    	String sRaceYy    = (String) ctx.get("RACE_YY");
    	String sRaceMm    = (String) ctx.get("RACE_MM");
    	String sRaceDay   = (String) ctx.get("RACE_DAY");
    	
        int i = 0;
        int dmlcount=0;
        
        param.setValueParamter(i++, SESSION_USER_ID); // �����
        param.setValueParamter(i++, sRaceDay);
        param.setValueParamter(i++, SESSION_USER_ID); // �����
        
        dmlcount = this.getDao("rbmdao").update("rsm6020_i02", param);
        
        return dmlcount;
    }
    
}
