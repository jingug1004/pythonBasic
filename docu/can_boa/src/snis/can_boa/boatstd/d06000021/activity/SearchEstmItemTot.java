/*================================================================================
 * �ý���		: �л���� 
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000004.activity.SearchWeekEduSchd.java
 * ���ϼ���		: ���������� �ְ����������� ��ȸ�Ѵ�. 
 * �ۼ���		: �ֹ��� 
 * ����			: 1.0.0
 * ��������		: 2008-01-23
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000021.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* �ְ������������� ��ȸ �Ѵ�. 
* @auther �ֹ���  
* @version 1.0
*/
public class SearchEstmItemTot extends SnisActivity
{    
    public SearchEstmItemTot()
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
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	getEstmItemTot(ctx);
						        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ����� �������� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getEstmItemTot(PosContext ctx)
    {
    	String	sResultKey = "dsEstmItemTot";
        String	racerPerioNo    = (String) ctx.get("racerPerioNo");
        String	round    =  (String) ctx.get("round");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, racerPerioNo);
        param.setWhereClauseParameter(paramIndex++, round);
		
        PosRowSet rowSet =   this.getDao("candao").find("tbdn_total_d06000021_fn001", param);
        logger.logInfo("###########################>"+rowSet.count());
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
}

