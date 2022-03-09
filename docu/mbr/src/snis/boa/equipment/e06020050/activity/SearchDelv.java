/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020050.activity.SearchDelv.java
 * ���ϼ���		: ������� ��ȸ �Ѵ�.. 
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
* ����� ��ȸ 
* @auther J.E. 
* @version 1.0
*/
public class SearchDelv extends SnisActivity
{    
    public SearchDelv()
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
    	getDelv(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ����� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getDelv(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutOrderList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	delv_sdt =  (String) ctx.get("DELV_SDT");
        String	delv_edt =  (String) ctx.get("DELV_EDT");
        rowset = getDelvRowSet(stndYear, mbrCd, delv_sdt, delv_edt);       
        
        ctx.put(sResultKey, rowset);
        logger.logDebug("rowset---> =" +rowset );
        Util.addResultKey(ctx, sResultKey);
    }   
    
    /**
     * ����� ��ȸ 
     * @param stndYear ���س⵵
     * @param tms	ȸ�� 
     * @param dayOrd	���� 
     * @return
     */
    public PosRowSet getDelvRowSet(String stndYear, String mbrCd, String delv_sdt, String delv_edt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, delv_sdt);
        param.setWhereClauseParameter(i++, delv_edt);       
        return  this.getDao("boadao").find("tbef_delv_ff001", param);
    }
    
}

