/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06030060.activity.SearchPartsLackNum.java
 * ���ϼ���		: ��ǰ ��� �̴�ǰ ��Ȳ�� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-12
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06030060.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ��ǰ ��� �̴�ǰ ��Ȳ�� ��ȸ�Ѵ�. 
* @auther ������ 
* @version 1.0
*/
public class SearchPartsLackNum extends SnisActivity
{    
    public SearchPartsLackNum()
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
     * <p> ��ǰ ��� �̴�ǰ ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	stnd_year   = (String) ctx.get("STND_YEAR");
        String	mbr_cd   = (String) ctx.get("MBR_CD");
        String	parts_cd   = (String) ctx.get("PARTS_CD");
        String	parts_item_cd_nm   = (String) ctx.get("PARTS_ITEM_CD_NM");
        //logger.logInfo("11111111");
        rowset = getPartsRowSet( stnd_year, mbr_cd, parts_cd, parts_item_cd_nm);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    
    public PosRowSet getPartsRowSet(String stnd_year, String mbr_cd, String parts_cd, String parts_item_cd_nm)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, parts_cd);
        param.setWhereClauseParameter(i++, parts_item_cd_nm);
        return  this.getDao("boadao").find("tbef_parts_lack_ff002", param);
    }
    
}

