/*
 * ================================================================================
 * �ý��� : �߸ſ� ����ó��
 * ���� �̸� : snis.rbm.business.rsm9010.activity.SaveTeller.java 
 * ���ϼ��� :  
 * �ۼ��� : 
 * ���� : 1.0.0 �������� : 2011- 12 - 17
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm9010.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;


public class ImportUserTemp extends SnisActivity {
	
	public ImportUserTemp(){}

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
    	CallableStatement proc = null; 
    	Connection conn = null; 
    	try {  
    		conn = this.getDao("rbmdao").getDBConnection(); 
    		proc = conn.prepareCall("{call SP_IMPORT_USER_TEMP }"); 
    		proc.setQueryTimeout(60);   // ���� �ɸ��� ��� Ÿ�Ӿƿ�����(�⺻:30��)

    		proc.execute(); 
    	} catch (Exception e) { 
    		e.printStackTrace();  
    	} finally{ 
    		//try {if(conn!=null) conn.close();} catch(Exception e) {}  
    	} 
    	
    	Util.setSaveCount  (ctx, nSaveCount);
    }
    
}
