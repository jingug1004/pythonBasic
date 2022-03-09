/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020060.activity.SearchParts.java
 * ���ϼ���		: �̿� �� ��ǰ�� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020060.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* �̿� �� ��ǰ�� ��ȸ �Ѵ�. 
* @auther ������ 
* @version 1.0
*/
public class SearchParts extends SnisActivity
{    
    public SearchParts()
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
    	getParts(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��ǰ ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	stnd_year  = (String) ctx.get("STND_YEAR");
        String	mbr_cd     = (String) ctx.get("MBR_CD");
        String	parts_tpe  = (String) ctx.get("PARTS_TPE");
        //logger.logInfo("11111111");
        rowset = getPartsRowSet( stnd_year, mbr_cd, parts_tpe);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    
    public PosRowSet getPartsRowSet(String stnd_year, String mbr_cd, String parts_tpe)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, parts_tpe);
        return  this.getDao("boadao").find("tbef_parts_crfw_ff001", param);
    }
    
}

