/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.racer.e02020040.activity.SearchRacerGrdStat.java
 * ���ϼ���		: ���������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-12-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02020040.activity;

import java.util.ArrayList;
import java.util.Hashtable;

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
* �����Ͽ� ������ ����� ��ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRacerGrdStat extends SnisActivity
{    
	
    public SearchRacerGrdStat()
    {
    }

    /**
     * <p> searchState Activity�� �����Ű�� ���� �޼ҵ� </p>
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
        PosRowSet rowset = null;
        
        rowset = searchRacerAll(ctx);
        
        int nColumnCnt = 0;
        Hashtable hsRow = new Hashtable();
        Hashtable hsCol = new Hashtable();
        
        // ��������Ʈ ����
        PosRow rows[] = rowset.getAllRow();
        for ( int i = 0; i < rows.length; i++ )
        	hsRow.put((String) rows[i].getAttribute("RACER_NO"), Integer.toString(i));
        
        // �Ⱓ ����
        int nRange     = (Integer.parseInt((String) ctx.get("ESTND_YEAR")) - 
        				  Integer.parseInt((String) ctx.get("SSTND_YEAR")) + 1) * 2;
        
        int nStartYear = Integer.parseInt((String) ctx.get("SSTND_YEAR"));
        int nEndYear   = Integer.parseInt((String) ctx.get("ESTND_YEAR"));

        // return Dataset�� column����
        PosColumnDef columnDef[] = new PosColumnDef[(2 + nRange)];
        columnDef[nColumnCnt++] = new PosColumnDef("RACER_NO", 		12, 6 );
        columnDef[nColumnCnt++] = new PosColumnDef("NM_KOR", 			12, 20);
        
        int nCnt = 0;
        String sColumnNm = "";
        for ( int i = nStartYear; i <= nEndYear; i++ ) {
        	sColumnNm = nStartYear + "001";
            hsCol.put(sColumnNm, Integer.toString(nCnt++));
            columnDef[nColumnCnt++] = new PosColumnDef("N_" + sColumnNm, 		12, 2 );
            
        	sColumnNm = nStartYear + "002";
            hsCol.put(sColumnNm, Integer.toString(nCnt++));
            columnDef[nColumnCnt++] = new PosColumnDef("N_" + sColumnNm, 		12, 2 );
            
	    	nStartYear++;
        } 
        
        // ������ ��� ��ȸ
        rowset = searchRacerGrd(ctx);
        
        // �� �Ⱓ�� �´� ���� ��� ����
        PosRow datarows[] = rowset.getAllRow();
	    for ( int i = 0; i < datarows.length; i++ ) {
	    	int    nRow  = Integer.parseInt((String) hsRow.get(datarows[i].getAttribute("RACER_NO")));
	    	String sQurt = "N_" + (String) datarows[i].getAttribute("STND_YEAR") + (String)datarows[i].getAttribute("QURT_CD");
	    	rows[nRow].setAttribute(sQurt, (String) datarows[i].getAttribute("RACER_GRD_CD"));
	    }
	    
	    // ���ε� �ڷḦ return�� Dataset���� ��ȯ
        ArrayList alRows = new ArrayList();
        for( int i = 0; i < rows.length; i++ ) {
        	alRows.add(rows[i]);
        }
        
        PosRowSet returnRowset = new PosRowSetImpl(alRows);
        returnRowset.setColumnDefs(columnDef);

        String sResultKey = "dsOutRacerGrdStat";
        ctx.put(sResultKey, returnRowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    // ������ ��� ��ȸ
    protected PosRowSet searchRacerGrd(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("ESTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("NM_KOR"    ));
                
        return this.getDao("boadao").find("tbeb_racer_grd_fb001", param);
    }
    
    // ��������Ʈ ��ȸ
	protected PosRowSet searchRacerAll(PosContext ctx) 
	{
        PosParameter param = new PosParameter();
        int i = 0;
    	
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("ESTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("NM_KOR"    ));
        
        return this.getDao("boadao").find("tbeb_racer_grd_fb002", param);
	}
}
