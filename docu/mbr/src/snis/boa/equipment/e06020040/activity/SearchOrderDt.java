/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020040.activity.SearchOrderDt.java
 * ���ϼ���		: �ֹ����ڸ� ��ȸ �Ѵ�.. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020040.activity;

import snis.boa.common.util.SnisActivity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* �ֹ����� ��ȸ 
* @auther J.E. 
* @version 1.0
*/
public class SearchOrderDt extends SnisActivity
{    
    public SearchOrderDt()
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
    	getOrderDt(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> �ֹ����� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getOrderDt(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutOrderDt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD"); 
        rowset = getOrderRowSet(stndYear, mbrCd);   
        
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }   
    
    /**
     * �ֹ����� ��ȸ 
     * @param stndYear ���س⵵
     * @param tms	ȸ�� 
     * @param dayOrd	���� 
     * @return
     */
    public PosRowSet getOrderRowSet(String stndYear, String mbrCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        return  this.getDao("boadao").find("tbef_ent_ff003", param);
    }
    
}

