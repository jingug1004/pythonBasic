/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010060.activity.SearchEquipInspDetail.java
 * ���ϼ���		: ��� Ȯ���˻� �� ���� ��ȸ  
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010050.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ��� 
* @auther �輺�� 
* @version 1.0
*/
public class SearchEquipInspDetail extends SnisActivity
{    
    public SearchEquipInspDetail()
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
    	getEquipInspDetail(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��� Ȯ���˻� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getEquipInspDetail(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipInspDetail";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        Integer	dayOrd =  new Integer((String) ctx.get("DAY_ORD"));
        String equipDrwltCmplYn = "X";
        rowset = getEquipInspDetailRowSet(stndYear, mbrCd, tms, dayOrd, equipDrwltCmplYn);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getEquipInspDetailRowSet(String stndYear, String mbrCd, Integer tms, Integer dayOrd, String equipDrwltCmplYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        return  this.getDao("boadao").find("tbef_equip_insp_detail_ff001", param);
        
    }
}

