/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02050030.activity.SaveRacerRecdInit.java
 * ���ϼ���		: �⵵�����������ʱ�ȭ���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02050030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �⵵�����������ʱ�ȭ����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRacerRecdInit extends SnisActivity
{    

    public SaveRacerRecdInit()
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
        // �⵵�����������ʱ�ȭ����
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));

        int nDeleteCount = this.getDao("boadao").update("tbeb_racer_recd_accu_sum_db001", param);

        // �⵵�����������ʱ�ȭ���
        // �� �⵵�� 1��1���� �������� �ϴ� ������ ����
        param = new PosParameter();
        
        i = 0;
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        
        int nSaveCount = this.getDao("boadao").update("tbeb_racer_recd_accu_sum_ib001", param);

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
}