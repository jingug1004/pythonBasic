/*================================================================================
 * �ý���			: LIS FILE ���ε�
 * �ҽ����� �̸�	: snis.rbm.business.rsm1010.activity.SaveLISFileUpload.java
 * ���ϼ���		: LIS FILE ����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm1010.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

public class VerifyLISFile extends SnisActivity {
	public VerifyLISFile(){}
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
	String MEETCD = "";
	String YEAR = "";
    public String runActivity(PosContext ctx)
    {	   	
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	        
        verifyLisFile(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void verifyLisFile(PosContext ctx) 
    {
    	Connection conn = null;
        CallableStatement proc =  null;
        String sRaceDay = (String) ctx.get("RACE_DAY");
        
		try { 
			int i = 1;
	
			conn = this.getDao("rbmdao").getDBConnection();
			proc = conn.prepareCall("{call SP_LIS_DAS_VERI(?)}");
			proc.setString(i++, sRaceDay);
			proc.execute();
			
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally{
			//try {if(conn!=null) conn.close();} catch(Exception e) {} 
		}    	
    }
    
}
