/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06030050.activity.SearchPartsSight.java
 * ���ϼ���		: ������纸���� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06030050.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ������纸���� ��ȸ�Ѵ�. 
* @auther ������ 
* @version 1.0
*/
public class SearchPartsSight extends SnisActivity
{    
    public SearchPartsSight()
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
    	getOutParts(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��ǰ ������纸�� ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getOutParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	stnd_year   = (String) ctx.get("SRCH_DT");
        String	mbr_cd   = (String) ctx.get("MBR_CD");
        String	parts_gbn   = (String) ctx.get("PARTS_GBN");
        
        rowset = getPartsRowSet( stnd_year, mbr_cd, parts_gbn);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    public PosRowSet getPartsRowSet(String stnd_year, String mbr_cd, String parts_gbn)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        //param.setWhereClauseParameter(i++, parts_gbn);
        
        return  this.getDao("boadao").find("tbef_parts_sight_ff001", param);
    }
    
}

