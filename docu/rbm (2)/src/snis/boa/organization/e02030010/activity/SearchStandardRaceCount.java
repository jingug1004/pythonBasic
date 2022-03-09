/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030010.activity.SearchStandardRaceCount.java
 * ���ϼ���		: ��������Ƚ����ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ��޺���������Ƚ���� ��ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchStandardRaceCount extends SnisActivity
{    
	
    public SearchStandardRaceCount()
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
        
        // ��޺���������Ƚ�� ��ȸ
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        rowset = this.getDao("boadao").find("tbeb_stnd_race_cnt_fb001", param);

        if ( !rowset.hasNext() )
        {
            rowset = this.getDao("boadao").find("tbeb_stnd_race_cnt_fb002", param);
        }
        
        String sResultKey = "dsOutStndRaceCnt";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);

    
        // �⵵,�б⺰ ���ּ� ��ȸ
        PosParameter paramRace = new PosParameter();
        i = 0;
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("QURT_CD"  ));
        rowset = this.getDao("boadao").find("tbeb_stnd_race_cnt_fb003", paramRace);

        sResultKey = "dsOutRaceInfo";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
