/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06030010.activity.SearchOutParts.java
 * ���ϼ���		: ��ǰ �ⳳ�Ϻ��� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06030010.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ��ǰ �ⳳ�Ϻ��� ��ȸ�Ѵ�. 
* @auther ������ 
* @version 1.0
*/
public class SearchOutParts extends SnisActivity
{    
    public SearchOutParts()
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
     * <p> ��ǰ �ⳳ�Ϻ� ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getOutParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	stnd_year  = (String) ctx.get("STND_YEAR");
        String	mbr_cd     = (String) ctx.get("MBR_CD");
        String	entDt      = (String) ctx.get("ENT_SDT");
        String	parts_gbn  = (String) ctx.get("PARTS_GBN");
        
        rowset = getPartsRowSet( stnd_year, mbr_cd, entDt, parts_gbn);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    public PosRowSet getPartsRowSet(String stnd_year, String mbr_cd, String entDt, String parts_gbn)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, mbr_cd);
        //param.setWhereClauseParameter(i++, parts_gbn);
        
        return  this.getDao("boadao").find("tbef_parts_lack_ff003", param);
    }
    
}

