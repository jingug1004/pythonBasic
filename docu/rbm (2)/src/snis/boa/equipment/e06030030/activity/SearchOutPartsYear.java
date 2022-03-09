/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06030030.activity.SearchOutPartsYear.java
 * ���ϼ���		: ��ǰ �ⳳ������ ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06030030.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ��ǰ �ⳳ������ ��ȸ�Ѵ�. 
*/
public class SearchOutPartsYear extends SnisActivity
{    
    public SearchOutPartsYear()
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
     * <p> ��ǰ �ⳳ���� ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getOutParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	mbr_cd   = (String) ctx.get("MBR_CD");
        String	partsCd   = (String) ctx.get("PARTS_CD");
        String	partsNm   = (String) ctx.get("PARTS_NM");
        String	entSdt   = (String) ctx.get("ENT_SDT");
        String	entEdt   = (String) ctx.get("ENT_EDT");
        
        rowset = getPartsRowSet( mbr_cd, partsCd, partsNm, entSdt, entEdt);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    public PosRowSet getPartsRowSet(String mbr_cd, String partsCd, String partsNm, String entSdt, String entEdt)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, entSdt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entSdt);
        param.setWhereClauseParameter(i++, partsCd);
        param.setWhereClauseParameter(i++, partsNm);
        param.setWhereClauseParameter(i++, entSdt);
        param.setWhereClauseParameter(i++, entEdt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, partsCd);
        param.setWhereClauseParameter(i++, partsNm);
        
        return  this.getDao("boadao").find("tbef_outparts_total_ff002", param);
    }
    
}

