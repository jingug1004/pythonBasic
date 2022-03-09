/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.racer.e02070030.activity.SearchTremTrngAskRacer.java
 * ���ϼ���		: �����Ʒÿ�û������ȸ
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02070030.activity;

import java.util.ArrayList;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �����Ʒÿ�û������ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchTremTrngAskRacer extends SnisActivity
{    
	
    public SearchTremTrngAskRacer()
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
    	PosParameter paramTerm = new PosParameter();
        
        int i = 0;
        paramTerm.setWhereClauseParameter(i++, ctx.get("TRNG_PLC_CD"));
        paramTerm.setWhereClauseParameter(i++, ctx.get("STR_DT"     ));
        paramTerm.setWhereClauseParameter(i++, ctx.get("END_DT"     ));

        // �Ʒÿ�û �Ⱓ��ȸ
        PosRowSet rowsetTerm      = this.getDao("boadao").find("tbeb_trng_ask_racer_rslt_fb001", paramTerm);
        
        // �Ʒÿ�û ������ȸ
        PosRowSet rowsetExptRacer = this.getDao("boadao").find("tbeb_trng_ask_racer_rslt_fb003", paramTerm);    

        PosRow rowTerm[] = rowsetTerm.getAllRow();
        
        // column����
        PosColumnDef columnDef[]    = new PosColumnDef[rowTerm.length];
        PosRowSet    rowsetAskRacer = null;
        
        for ( int j = 0; j < rowTerm.length; j++ ) {
        	columnDef[j] = new PosColumnDef("TRNG_DD_" + rowTerm[j].getAttribute("TRNG_DD"), 12, 6);
        	
        	PosParameter param = new PosParameter();
        	
        	i = 0;
        	param.setWhereClauseParameter(i++, ctx.get("TRNG_PLC_CD"));
        	param.setWhereClauseParameter(i++, rowTerm[j].getAttribute("TRNG_DD"));
            PosRowSet rowsetAskRacerDate  = this.getDao("boadao").find("tbeb_trng_ask_racer_rslt_fb002", param);
            rowsetAskRacer = addPosRowSet(rowsetAskRacer, rowsetAskRacerDate, "TRNG_DD_" + rowTerm[j].getAttribute("TRNG_DD"));
        }

        if ( rowsetAskRacer != null )
        	rowsetAskRacer.setColumnDefs(columnDef);

        // �Ʒÿ�û�Ⱓ
        String sResultKey = "dsOutTrngAskTerm";
        ctx.put(sResultKey, rowsetTerm);
        Util.addResultKey(ctx, sResultKey);

        // �Ʒÿ�û����
        sResultKey = "dsOutTrngAskRacer";
        ctx.put(sResultKey, rowsetAskRacer);
        Util.addResultKey(ctx, sResultKey);

        // �Ʒÿ�������
        sResultKey = "dsOutTrngExptRacer";
        ctx.put(sResultKey, rowsetExptRacer);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> rowset�� addRowSet�� record�� ���Ͽ� return�Ѵ�. </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
	protected PosRowSet addPosRowSet(PosRowSet rowset, PosRowSet addRowSet, String sColumn) 
    {
		ArrayList rows    = new ArrayList();
		PosRow row[]      = null;
		
		if ( rowset != null ) {
			row = rowset.getAllRow();
		}
		
		if ( addRowSet != null ) {
			PosRow addrow[] = addRowSet.getAllRow();
			if ( row == null ) {
				for ( int i = 0; i < addrow.length; i++ ) {
					addrow[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
					rows.add(addrow[i]);
				}
			} else {
				if ( row.length >= addrow.length ) {
					for ( int i = 0; i < row.length; i++ ) {
						if ( i < addrow.length ) {
							row[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
					    }
						rows.add(row[i]);
					}
				} else {
					for ( int i = 0; i < addrow.length; i++ ) {
						if ( i >= row.length ) {
							addrow[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
							rows.add(addrow[i]);
						} else {
							row[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
							rows.add(row[i]);
						}
					}
				}
			}
		}

		if ( rows.size() > 0 ) rowset = new PosRowSetImpl(rows);
		
		return rowset;
    }
}
