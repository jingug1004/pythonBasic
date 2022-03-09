/*================================================================================
 * �ý���			: �����¼��� ����
 * �ҽ����� �̸�	: snis.rbm.business.rem5010.activity.ImportFixedSeatData.java
 * ���ϼ���		: ����DB�κ��� �����ο� ��������
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2014-03-15
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rem5010.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

public class ImportFixedSeatData extends SnisActivity {
	
	public ImportFixedSeatData(){		
		
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
    	
        Connection conn = null;
        CallableStatement proc = null;

        try {

        	conn = this.getDao("rbmdao").getDBConnection();
        	proc = conn.prepareCall("{call SP_IMPORT_FIXEDSEAT_TRADE()}");
        	proc.setQueryTimeout(60); // ���� �ɸ��� ��� Ÿ�Ӿƿ�����(�⺻:30��)         

        	proc.execute();

        } catch (Exception e) {
        	e.printStackTrace();
        } finally{
        	//try {if(conn!=null) conn.close();} catch(Exception e) {}
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
}
