/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020050.activity.SearchDelvParts.java
 * ���ϼ���		: ��� �� ���� ��ȸ  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020050.activity;

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
public class SearchDelvParts extends SnisActivity
{    
    public SearchDelvParts()
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
    	getDelvParts(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��� �� ����  ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getDelvParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutOrderPartsList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	delv_seq   = (String) ctx.get("DELV_SEQ");
        
        //logger.logDebug("stndYear =" +stndYear );
        
        rowset = getDelvPartsRowSet(stndYear, mbrCd, delv_seq);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getDelvPartsRowSet(String stndYear, String mbrCd, String delv_seq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, delv_seq);
        return  this.getDao("boadao").find("tbef_delv_parts_ff001", param);
    }
}

