/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030020.activity.SearchEscRacer.java
 * ���ϼ���		: ������ �ּ����� ���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03060015.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRacerArrangeEscPrint extends SnisActivity
{    
	
    public SearchRacerArrangeEscPrint()
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	PosParameter paramRacerSummery = new PosParameter();
        
        int i = 0;
        paramRacerSummery.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerSummery.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerSummery.setWhereClauseParameter(i++, ctx.get("END_DT"));

        PosRowSet rowsetRacerSummery = this.getDao("boadao").find("tbec_arrange_esc_fc003", paramRacerSummery);    

        String sResultKey = "dsOutRacerSummery";
        ctx.put(sResultKey, rowsetRacerSummery);
        Util.addResultKey(ctx, sResultKey);

    	PosParameter paramRacerCancel = new PosParameter();
        
        i = 0;
        paramRacerCancel.setWhereClauseParameter(i++, ctx.get("STR_DT"));

        PosRowSet rowsetRacerCancel = this.getDao("boadao").find("tbec_arrange_esc_fc004", paramRacerCancel);    

        sResultKey = "dsOutRacerCancel";
        ctx.put(sResultKey, rowsetRacerCancel);
        Util.addResultKey(ctx, sResultKey);

    	PosParameter paramRacerEsc = new PosParameter();
        
        i = 0;
        paramRacerEsc.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerEsc.setWhereClauseParameter(i++, ctx.get("END_DT"));

        PosRowSet rowsetRacerEsc = this.getDao("boadao").find("tbec_arrange_esc_fc005", paramRacerEsc);    

        sResultKey = "dsOutRacerEsc";
        ctx.put(sResultKey, rowsetRacerEsc);
        Util.addResultKey(ctx, sResultKey);

    	PosParameter paramRacerEtc = new PosParameter();
        
        i = 0;
        paramRacerEtc.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacerEtc.setWhereClauseParameter(i++, ctx.get("END_DT"));

        PosRowSet rowsetRacerEtc = this.getDao("boadao").find("tbec_arrange_esc_fc006", paramRacerEtc);    

        sResultKey = "dsOutRacerEtc";
        ctx.put(sResultKey, rowsetRacerEtc);
        Util.addResultKey(ctx, sResultKey);
    }
}
