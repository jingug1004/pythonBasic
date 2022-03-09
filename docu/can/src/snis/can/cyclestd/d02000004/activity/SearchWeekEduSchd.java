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
package snis.can.cyclestd.d02000004.activity;

import snis.can.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can.common.util.Util;

/**
* �ְ������������� ��ȸ �Ѵ�. 
* @auther �輺��  
* @version 1.0
*/
public class SearchWeekEduSchd extends SnisActivity
{    
    public SearchWeekEduSchd()
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
    	
    	getWkPart(ctx);
						        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ����� �������� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getWkPart(PosContext ctx)
    {
    	String	sResultKey = "dsWkPart";
        String	perioNo    = (String) ctx.get("perioNo");
        String	yearVal    =  (String) ctx.get("yearVal");
        String	monthVal   =  (String) ctx.get("monthVal");
        String	weekPart   =  (String) ctx.get("weekPart");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, perioNo);
        param.setWhereClauseParameter(paramIndex++, yearVal);
		param.setWhereClauseParameter(paramIndex++, monthVal);
		param.setWhereClauseParameter(paramIndex++, weekPart);
		
        PosRowSet rowSet =   this.getDao("candao").find("tbdb_wk_sche_fb004", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
}

