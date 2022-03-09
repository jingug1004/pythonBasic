/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020020.activity.SearchPartsCom.java
 * ���ϼ���		: ��ü�� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-04
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020020.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ��ü�� ��ȸ �Ѵ�. 
* @auther ������ 
* @version 1.0
*/
public class SearchPartsCom extends SnisActivity
{    
    public SearchPartsCom()
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
    	getPartsCom(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��ü ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getPartsCom(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutPartsCom";
        String	parts_com_cd   = (String) ctx.get("PARTS_COM_CD");
        String	com_nm   = (String) ctx.get("COM_NM");
        rowset = getPartsComRowSet( parts_com_cd, com_nm);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getPartsComRowSet(String parts_com_cd, String com_nm)
    {
        PosParameter param = new PosParameter();


        int i = 0;
        param.setWhereClauseParameter(i++, parts_com_cd);
        param.setWhereClauseParameter(i++, com_nm);
        return  this.getDao("boadao").find("tbef_parts_com_ff001", param);
    }
    
}

