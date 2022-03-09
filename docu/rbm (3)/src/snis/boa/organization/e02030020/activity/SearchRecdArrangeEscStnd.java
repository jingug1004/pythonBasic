/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030020.activity.SearchRecdArrangeEscStnd.java
 * ���ϼ���		: ������ּ�����������ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������ּ�����������ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRecdArrangeEscStnd extends SnisActivity
{    
	
    public SearchRecdArrangeEscStnd()
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
    	
		logger.logInfo(ctx.get("STND_YEAR"));
		logger.logInfo(ctx.get("QURT_CD"  ));
	        
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	PosParameter param = new PosParameter();
        PosRowSet rowset = null; 
        int i = 0;

        // ��޺�����Ƚ�� ��ȸ
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        rowset = this.getDao("boadao").find("tbec_racer_master_fb010", param);

        String sResultKey = "dsOutRacerGrd";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);

        // ��޺� ��� ��ȸ
    	PosParameter paramPerio = new PosParameter();
        PosRowSet rowsetPerio = null; 
        i = 0;

/*
        paramPerio.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));
        rowsetPerio = this.getDao("boadao").find("tbec_racer_master_fb101", paramPerio);
*/
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));//��޽ɻ� ������ ���� ��ϵ� ������ ���. ��������. 2013.1.16
        paramPerio.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramPerio.setWhereClauseParameter(i++, ctx.get("END_DT"));

        rowsetPerio = this.getDao("boadao").find("tbec_arrange_esc_fc003", paramPerio);    

        sResultKey = "dsOutPerio";
        ctx.put(sResultKey, rowsetPerio);
        Util.addResultKey(ctx, sResultKey);

        PosParameter paramRacer = new PosParameter();
        PosRowSet rowsetRacer = null; 
        i = 0;

        // ��������Ʈ(����) ��ȸ
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        
        paramRacer.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("OPEN_DAY"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("RACER_GRD_END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        
        rowsetRacer = this.getDao("boadao").find("tbec_racer_master_fb009", paramRacer);

        sResultKey = "dsOutRacer";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
    }
}
