/*
 * ================================================================================
 * �ý��� : ����� ���� 
 * �ҽ����� �̸� : snis.rbm.system.rsy3050.activity.SaveUser.java 
 * ���ϼ��� : ����� ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011-10-08
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.system.rsy3050.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

public class ImportUser extends SnisActivity {
	
	public ImportUser(){
		
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

		System.out.println("called runActivity");
    	String strErr = ImportUser();
    	
    	if( strErr.equals("F") ){   
    		return PosBizControlConstants.FAILURE;
    	}else{
    		return PosBizControlConstants.SUCCESS;    	
    	}
    }

    /**
    * <p> �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected String ImportUser() 
    {

    	Connection conn = null;
        CallableStatement proc =  null;
        
		try { 	
			System.out.println("called ImportUser");
			conn = this.getDao("rbmdao").getDBConnection();
			proc = conn.prepareCall("{call SP_IMPORT_USER_RBM()}");
			proc.execute();
			
		} catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("error:");
		} finally{
			//try {if(conn!=null) conn.close();} catch(Exception e) {} 
		}    
		
        return "T";
    }
 

}
