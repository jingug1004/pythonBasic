/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02050040.activity.SearchIntroRunTm.java
 * ���ϼ���		: ȸ�����Ұ�����Ÿ����ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02050040.activity;

import java.util.ArrayList;
import java.util.HashMap;

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
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchIntroRunTm extends SnisActivity
{    
    public SearchIntroRunTm()
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
    	PosParameter paramMot = new PosParameter();
        PosRowSet rowsetMot = null; 
        int i = 0;

        // ���� ��ȸ
        paramMot.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramMot.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramMot.setWhereClauseParameter(i++, ctx.get("TMS"));
        paramMot.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramMot.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramMot.setWhereClauseParameter(i++, ctx.get("TMS"));
        rowsetMot = this.getDao("boadao").find("tbeb_mot_recd_accu_sum_fb003", paramMot);

        // ȸ���� �Ұ����� ��ȸ
    	PosParameter paramTms = new PosParameter();
        PosRowSet rowsetTms = null; 
        i = 0;

        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("TMS"));
        rowsetTms = this.getDao("boadao").find("tbeb_organ_fb006", paramTms);
        
        String sResultKey = "dsOutMotItrdtRunTm";
        ctx.put(sResultKey, createIntroRunTm(rowsetMot, rowsetTms, ctx));
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> �� ȸ���� �ش��ϴ� �Ұ����� Ÿ�� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected PosRowSet createIntroRunTm(PosRowSet rowsetMot, PosRowSet rowsetTms, PosContext ctx)
    {
        HashMap   hmMot   = new HashMap();
		ArrayList alRows  = new ArrayList();
		PosRowSet rowset  = rowsetMot ;
		int nRow = 0;
		
        PosColumnDef columnDef[] = createColumn(rowsetMot, ctx);
        
        // ���� ����
        PosRow rows[] = rowsetMot.getAllRow();
        for ( int i = 0; i < rows.length; i++ ) {
        	hmMot.put((String) rows[i].getAttribute("MOT_NO"), Integer.toString(i));
        }
        
        // �Ұ�������ȸ
        while ( rowsetTms.hasNext() )
        {
        	PosRow row = rowsetTms.next();
        	String sMotNo = (String) row.getAttribute("MOT_NO");
        	if ( hmMot.get(sMotNo) != null ) {
        		nRow = Integer.parseInt((String) hmMot.get(sMotNo));
            	String sColumnNm = "TMS" + row.getAttribute("TMS");
            	rows[nRow].setAttribute(sColumnNm, row.getAttribute("INTRO_RUN_TM"));
        	}
        }

        for ( int i = 0; i < rows.length; i++ ) {
        	alRows.add(rows[i]);
        }
        
		if ( alRows.size() > 0 ) rowset = new PosRowSetImpl(alRows);

		rowset.setColumnDefs(columnDef);
		
        return rowset;
    }


    /**
     * <p> �� ȸ���� column���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected PosColumnDef[] createColumn(PosRowSet rowsetMot, PosContext ctx)
    {
        PosColumnDef columnDefIntro[] = rowsetMot.getColumnDefs();
        PosColumnDef columnDefTms[]   = new PosColumnDef[Integer.parseInt((String) ctx.get("TMS"))];
        PosColumnDef columnDef[]      = new PosColumnDef[columnDefIntro.length + columnDefTms.length];
        
        int nCnt = 0;

        for ( int i = 0; i < Integer.parseInt((String) ctx.get("TMS")); i++ ) {
        	String sColumnNm = "TMS" + (i + 1);
        	columnDefTms[nCnt++] = new PosColumnDef(sColumnNm, 12, 10);
        }
        
        nCnt = 0;
        for ( int i = 0; i < columnDefIntro.length; i++ ) {
        	columnDef[nCnt++] = columnDefIntro[i];
        }
        
        for ( int i = 0; i < columnDefTms.length; i++ ) {
        	columnDef[nCnt++] = columnDefTms[i];
        }
        
        return columnDef;
    }
}
