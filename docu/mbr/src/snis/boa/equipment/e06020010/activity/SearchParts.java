/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020010.activity.SearchParts.java
 * ���ϼ���		: ��ǰ�� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020010.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ��ǰ�� ��ȸ �Ѵ�. 
* @auther ������ 
* @version 1.0
*/
public class SearchParts extends SnisActivity
{        
    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
	public String runActivity(PosContext ctx)
	  {
	    getParts(ctx);
	    return "success";
	  }

    /**
     * <p> ��ǰ ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
	private void getParts(PosContext ctx)
	  {
	    String sResultKey = "dsOutParts";
	    String stnd_year = (String)ctx.get("STND_YEAR");
	    String parts_cd = (String)ctx.get("PARTS_CD");
	    String mbr_cd = (String)ctx.get("MBR_CD");
	    String parts_item_cd_nm = (String)ctx.get("PARTS_ITEM_CD_NM");
	    String module_code = (String)ctx.get("MODULE_CODE");
	    String partsComCd = (String)ctx.get("PARTS_COM_CD");

	    PosRowSet rowset = getPartsRowSet(stnd_year, parts_cd, parts_item_cd_nm, mbr_cd, module_code, partsComCd);
	    ctx.put(sResultKey, rowset);
	    Util.addResultKey(ctx, sResultKey);
	  }
    
	public PosRowSet getPartsRowSet(String stnd_year, String parts_cd, String parts_item_cd_nm, String mbr_cd, String module_code, String partsComCd)
	  {
	    PosParameter param = new PosParameter();

	    int i = 0;
	    param.setWhereClauseParameter(i++, stnd_year);
	    param.setWhereClauseParameter(i++, parts_cd);
	    param.setWhereClauseParameter(i++, parts_item_cd_nm);
	    param.setWhereClauseParameter(i++, mbr_cd);
	    param.setWhereClauseParameter(i++, module_code);
	    param.setWhereClauseParameter(i++, partsComCd);
	    return getDao("boadao").find("tbef_parts_ff001", param);
	  }
}

