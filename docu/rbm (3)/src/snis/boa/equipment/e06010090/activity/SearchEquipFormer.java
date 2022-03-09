/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010050.activity.SearchEquipInsp.java
 * ���ϼ���		: Ȯ�� �˻縦 ��ȸ �Ѵ�.. 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010090.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;
import com.posdata.glue.dao.vo.*;

/**
* ��� ����Ȯ��Ÿ�� ��ȸ 
* @auther ����ȭ 
* @version 1.0
*/
public class SearchEquipFormer extends SnisActivity
{    
    public SearchEquipFormer()
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
    	getEquipFormer(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��� ����Ȯ��Ÿ�� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getEquipFormer(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipFormer";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        rowset = getEquipFormerRowSet(stndYear, mbrCd, tms);
        
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * ����Ȯ��Ÿ�� ��ȸ 
     * @param stndYear ���س⵵
     * @param tms	ȸ�� 
     * @return
     */
    public PosRowSet getEquipFormerRowSet(String stndYear, String mbrCd, Integer tms)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        return  this.getDao("boadao").find("tbef_equip_former_ff001", param);
    }
    
}

