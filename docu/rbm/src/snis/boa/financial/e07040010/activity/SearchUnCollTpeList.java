/*================================================================================
 * �ý���			: ��ݰ��� 
 * �ҽ����� �̸�	: snis.boa.financial.e07040010.activity.SearchUnCollTpeList.java
 * ���ϼ���		: �̼��ݳ����� ��ȸ �Ѵ�.. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07040010.activity;

import snis.boa.common.util.SnisActivity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* �̼��ݳ��� ��ȸ 
* @auther J.E. 
* @version 1.0
*/
public class SearchUnCollTpeList extends SnisActivity
{    
    public SearchUnCollTpeList()
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
    	
    	getRaceIms(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> �̼��ݳ��� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getRaceIms(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutUnColList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        //Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	nmKor =  (String) ctx.get("NM_KOR");
        rowset = getOrderRowSet(stndYear, payMonth, nmKor);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }   
    
    /**
     * �̼��ݳ��� ��ȸ 
     * @param stndYear ���س⵵
     * @param tms	ȸ�� 
     * @param dayOrd	���� 
     * @return
     */
    public PosRowSet getOrderRowSet(String stndYear, String payMonth, String nmKor)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, payMonth);
        param.setWhereClauseParameter(i++, payMonth);
        param.setWhereClauseParameter(i++, nmKor);
        return  this.getDao("boadao").find("tbeg_uncollected_ff002", param);
    }
    
}

