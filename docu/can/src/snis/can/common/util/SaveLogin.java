package snis.can.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/*================================================================================
 * �ý���			: �α��� ���
 * �ҽ����� �̸�	: 
 * ���ϼ���		: �α��� ���
 * �ۼ���			: jdlee
 * ����			: 1.0.0
 * ��������		: 2014-05-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �̿���
* @version 1.0
*/
public class SaveLogin extends SnisActivity
{    		
	public SaveLogin()
    {
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

    	insertLogin(ctx);
    	
    	insertLoginDupChk(ctx);
    	
      return PosBizControlConstants.SUCCESS;
    }


    private int insertLogin( PosContext ctx ){

        String sUserId = Util.getCtxStr(ctx, "USER_ID");
        String sIpAddr = Util.getCtxStr(ctx, "IP_ADDR");
        
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sUserId);									//17.CUST_SSN(�ֹι�ȣ)
        param.setValueParamter(i++, sUserId);									//18.ETC(���޽ð�)
        param.setValueParamter(i++, sIpAddr);															//19.�ۼ���ID


        int dmlcount = this.getDao("candao").update("tbda_user_login_save", param);
        return dmlcount;
    } 
    
    /**
	 * �ߺ��α��� ������ ���� �Է�
	 * 
	 * @return
	 */
	private int insertLoginDupChk(PosContext ctx){
    	String sUserId = Util.getCtxStr(ctx, "USER_ID");
        String sSessionID = Util.getCtxStr(ctx, "JSESSIONID");
        
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sUserId);									
        param.setValueParamter(i++, sSessionID);								
        param.setValueParamter(i++, sUserId);									
        param.setValueParamter(i++, sSessionID);

        int dmlcount = this.getDao("candao").update("user_login_chk_save", param);
        return dmlcount;
    }
}
