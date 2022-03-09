/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010060.activity.SearchEquipLot.java
 * ���ϼ���		: ����/��Ʈ ��÷ ����� ��ȸ�Ѵ�. 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010070.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���س⵵, ȸ�� ������ �޾� �ش� ȸ���� ���� 
* ���� /��Ʈ ��� Ȯ�� ���θ� ��ȸ �Ѵ�. 
* @auther �輺�� 
* @version 1.0
*/
public class SearchEquipDrwlt extends SnisActivity
{    
    public SearchEquipDrwlt()
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
    	getEquipDrwlt(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ���� ��÷ ���  ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getEquipDrwlt(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipDrwlt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        rowset = getEquipDrwltRwoSet( stndYear, mbrCd, tms);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getEquipDrwltRwoSet(String stndYear, String mbrCd, Integer tms)
    {
        PosParameter param = new PosParameter();


        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        return  this.getDao("boadao").find("tbeb_race_tms_ff001", param);
    }
    
}

