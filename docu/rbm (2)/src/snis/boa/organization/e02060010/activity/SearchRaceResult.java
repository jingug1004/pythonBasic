/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.racer.e02060010.activity.SearchRaceResult.java
 * ���ϼ���		: ���ְ����ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-12-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02060010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ���ְ����ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRaceResult extends SnisActivity
{    
	
    public SearchRaceResult()
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
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {   	
        PosRowSet rowset = null;
        
        // ���ְ����ȸ
        PosParameter param = new PosParameter();
        int i = 0;
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("TMS"));
    	
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
    	
    	rowset = this.getDao("boadao").find("tbeb_race_result_fb001", param);

        String sResultKey = "dsOutRaceResult";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
