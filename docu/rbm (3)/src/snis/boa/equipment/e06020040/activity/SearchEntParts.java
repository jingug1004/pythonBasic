/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020040.activity.SearchEntParts.java
 * ���ϼ���		: �԰� �� ���� ��ȸ  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-19
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
* ��ǰ 
* @auther J.E. 
* @version 1.0
*/
public class SearchEntParts extends SnisActivity
{    
    public SearchEntParts()
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
    	getEntParts(ctx);
    	getEntSum(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> �԰� �� ����  ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getEntParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEnt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	order_seq   = (String) ctx.get("ORDER_SEQ");
        
        //logger.logDebug("stndYear =" +stndYear );
        
        rowset = getOrderPartsRowSet(stndYear, mbrCd, order_seq);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getOrderPartsRowSet(String stndYear, String mbrCd, String order_seq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, order_seq);
        return  this.getDao("boadao").find("tbef_ent_ff002", param);
    }
    
    /**
     * <p> �԰� �� ���� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getEntSum(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEntSum";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	order_seq   = (String) ctx.get("ORDER_SEQ");
        String	ent_seq   = (String) ctx.get("ENT_SEQ");
        
        //logger.logDebug("stndYear =" +stndYear );
        
        rowset = getEntSumRowSet(stndYear, mbrCd, order_seq, ent_seq);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    public PosRowSet getEntSumRowSet(String stndYear, String mbrCd, String order_seq, String ent_seq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, order_seq);
        return  this.getDao("boadao").find("tbef_ent_ff004", param);
    }
}

