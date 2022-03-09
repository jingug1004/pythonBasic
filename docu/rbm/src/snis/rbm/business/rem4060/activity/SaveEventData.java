/*
 * ================================================================================
 * �ý��� : ���� 
 * �ҽ����� �̸� : snis.rbm.business.rem4060.activity.SaveEventData.java 
 * ���ϼ��� : ���������ֱ� ���� 
 * �ۼ��� : ���缱
 * ���� : 1.0.0 
 * �������� : 2012.12.29
 * ������������ :
 * ���������� : 
 * ������������ :
 * =================================================================================
 */

package snis.rbm.business.rem4060.activity;

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

public class SaveEventData extends SnisActivity {

	public SaveEventData(){		
		
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

        String EventDt = (String) ctx.get("EVENT_DT");
    		
        nDeleteCount = deleteTradeEvent(EventDt);        
        nSaveCount   = InsertTradeEvent(EventDt);        
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> �̺�Ʈ ��÷�� ����Ÿ ���� </p>
     * @param   EventDt	 String		�̺�Ʈ ��¥
     * @return  dmlcount int		���� ���ڵ� ����
     * @throws  none
     */
    protected int deleteTradeEvent(String EventDt)
    {
    	PosParameter param = new PosParameter();
    	int i=0;
        param.setValueParamter(i++, EventDt);
        
        int dmlcount = this.getDao("rbmdao").update("rem4060_d01", param);
        return dmlcount;
    }

    
    /**
     * <p> �̺�Ʈ ��÷�� ����Ÿ ���� </p>
     * @param   EventDt	 String		�̺�Ʈ ��¥
     * @return  dmlcount int		���� ���ڵ� ����
     * @throws  none
     */
    protected int InsertTradeEvent(String EventDt)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, EventDt);
        param.setValueParamter(i++, EventDt);
        param.setValueParamter(i++, EventDt);

        int dmlcount = this.getDao("rbmdao").update("rem4060_i01", param);
        return dmlcount;
    }

    
}
