/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040060.activity.RegistHomePage.java
 * ���ϼ���		: ����ǥȨ���������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040060.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����ǥ�� ���� �߼ۿ��θ� �����ϴ� Ŭ�����̴�.
* @auther ���缱
* @version 1.0
*/
public class SaveMailHist extends SnisActivity
{    
    public SaveMailHist()
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 

    	// ����ǥ �߼ۿ��� ����
    	nSaveCount   = saveMail(ctx);

    	if ( nSaveCount == 0 ) {
    		Util.setSvcMsg(ctx, "���۾��� ���� �̷�� ���� �ʾҽ��ϴ�.");
    	}
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ����ǥ Ȩ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveMail(PosContext ctx) 
    {

		int nInsertCount = 0;
		
		PosParameter param = new PosParameter();
		int i = 0;

		nInsertCount = insertMail(ctx);
		
		Util.setSaveCount(ctx, nInsertCount);

		return nInsertCount;
    	
    }
    

	/**
	 * <p>
	 * ���
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected int insertMail(PosContext ctx) {
		PosParameter param = new PosParameter();
		int i = 0;
		
		param.setValueParamter(i++, ctx.get("STND_YEAR"));
		param.setValueParamter(i++, ctx.get("MBR_CD"));
		param.setValueParamter(i++, ctx.get("TMS"));
		param.setValueParamter(i++, ctx.get("DAY_ORD"));
		param.setValueParamter(i++, ctx.get("CFM_YN"));
		
		param.setValueParamter(i++, ctx.get("STND_YEAR"));
		param.setValueParamter(i++, ctx.get("MBR_CD"));
		param.setValueParamter(i++, ctx.get("TMS"));
		param.setValueParamter(i++, ctx.get("DAY_ORD"));
		param.setValueParamter(i++, ctx.get("CFM_YN"));
		
		param.setValueParamter(i++, "1");
		param.setValueParamter(i++, "");
		param.setValueParamter(i++, SESSION_USER_ID);
		
		return this.getDao("boadao").insert("e02040060_i01", param);
	}
	
}
