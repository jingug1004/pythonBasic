/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030040.activity.SearchArrangeVerify.java
 * ���ϼ���		: �ּ�������ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030040.activity;

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
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchArrangeVerify extends SnisActivity
{    
    public SearchArrangeVerify()
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
    	PosParameter paramRacer = new PosParameter();
        PosRowSet rowsetRacer = null; 
        int i = 0;

        // ���� ��ȸ
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        rowsetRacer = this.getDao("boadao").find("tbeb_arrange_fb004", paramRacer);

        PosRow rows[] = rowsetRacer.getAllRow();
        
        // ��������Ʈ�� column����
        PosColumnDef columnDef[] = new PosColumnDef[rows.length + 1];
        columnDef[0] = new PosColumnDef("NM_KOR", 12, 20);
        for ( int j = 0; j < rows.length; j++ ) {
        	columnDef[j + 1] = new PosColumnDef("RACER_NO_" + j, 3, 10);
        }
        
		ArrayList alrows    = new ArrayList();
       
		// ������ ������ �ּ�������ȸ
        for ( int j = 0; j < rows.length; j++ ) {
        	Map mapRow = new HashMap();
        	mapRow.put("NM_KOR", rows[j].getAttribute("NM_KOR"));
        	
            for ( int k = j + 1; k < rows.length; k++ ) {
            	PosParameter param = new PosParameter();
                i = 0;

/*
                param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
                param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
                param.setWhereClauseParameter(i++, ctx.get("TMS"));
                param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
                param.setWhereClauseParameter(i++, rows[j].getAttribute("RACER_NO"));
                param.setWhereClauseParameter(i++, rows[k].getAttribute("RACER_NO"));
                PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_fb005", param);
*/                
                param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
                param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
                param.setWhereClauseParameter(i++, ctx.get("TMS"));
                param.setWhereClauseParameter(i++, rows[j].getAttribute("RACER_NO"));
                param.setWhereClauseParameter(i++, rows[k].getAttribute("RACER_NO"));
                PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_fb0055", param);
                
            	PosRow row = rowset.next();
            	mapRow.put("RACER_NO_" + k, row.getAttribute("CNT"));
            }
            
        	PosRow row = new PosRowImpl(mapRow);
        	alrows.add(row);
        }
        
        PosRowSet rowset = new PosRowSetImpl(alrows);
        rowset.setColumnDefs(columnDef);
        
        String sResultKey = "dsOutRacer";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);

        sResultKey = "dsOutArrangeVerify";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
