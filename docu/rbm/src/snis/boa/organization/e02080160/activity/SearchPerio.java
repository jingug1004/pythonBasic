/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02080160.activity.SearchPerio.java
 * ���ϼ���		: �������޺��Ի����ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02080160.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �������޺��Ի����ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchPerio extends SnisActivity
{    
	
    public SearchPerio()
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
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	// �����ȸ
        PosRowSet rowsetPerid = this.getDao("boadao").find("tbeb_racer_perio_no_reg_term_fb003");    

    	PosParameter param = new PosParameter();
        
    	// �⵵�� ���� ����� �Ի�� ��ȸ
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("RANK"   ));
        param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
        param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_organ_fb024", param);    

        // column����
        PosRow rowPerid[] = rowsetPerid.getAllRow();
        PosColumnDef columnDef[] = new PosColumnDef[1 + (rowPerid.length * 2)];
        
    	columnDef[0] = new PosColumnDef("STND_YEAR", 3, 10);
        for ( int j = 0; j < rowPerid.length; j++ ) {
        	columnDef[1 + (j * 2)] = new PosColumnDef("CNT_"        + rowPerid[j].getAttribute("RACER_PERIO_NO"), 3, 10);
        	columnDef[2 + (j * 2)] = new PosColumnDef("PERCENTAGE_" + rowPerid[j].getAttribute("RACER_PERIO_NO"), 3, 10);
        }

        int nStrYear = Integer.parseInt((String) ctx.get("STR_YEAR"));
        int nEndYear = Integer.parseInt((String) ctx.get("END_YEAR"));
        
        PosRow rowData[] = rowset.getAllRow();
		ArrayList alRows  = new ArrayList();
        
		// �ش�⵵�� ����� �Ի�� ����
        for ( int j = nEndYear; j >= nStrYear; j-- ) {
        	Map mapRow = new HashMap();
        	mapRow.put("STND_YEAR", Integer.toString(j));
        	
        	for ( int k = 0; k < rowData.length; k++ ) {
        		if ( rowData[k].getAttribute("STND_YEAR").equals(Integer.toString(j))) {
        			String sPerio = (String) rowData[k].getAttribute("RACER_PERIO_NO");
        			mapRow.put("CNT_"        + sPerio, rowData[k].getAttribute("CNT")       );
        			mapRow.put("PERCENTAGE_" + sPerio, rowData[k].getAttribute("PERCENTAGE"));
        		}
        	}

        	PosRow row = new PosRowImpl(mapRow);
        	alRows.add(row);
        }
        
        PosRowSet rowsetPerioList = new PosRowSetImpl(alRows);
        
        rowsetPerioList.setColumnDefs(columnDef);
        
        // �⵵�� ��޺� �Ի�� ��ȸ
        String sResultKey = "dsOutPerioList";
        ctx.put(sResultKey, rowsetPerioList);
        Util.addResultKey(ctx, sResultKey);

        PosRowSet rowsetGrdList = this.getDao("boadao").find("tbeb_organ_fb025", param);    
        
        sResultKey = "dsOutGrdList";
        ctx.put(sResultKey, rowsetGrdList);
        Util.addResultKey(ctx, sResultKey);
    }
}
