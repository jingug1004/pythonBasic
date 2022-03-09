/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SearchArrange.java
 * ���ϼ���		: �ּ���ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ּ���ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchArrange extends SnisActivity
{    
    public SearchArrange()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutEscRacer";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        
        rowsetRacer = this.getDao("boadao").find("tbeb_racer_race_alloc_fb001", paramRacer);

        // ȸ�� ��ȸ
    	PosParameter paramTms = new PosParameter();
        PosRowSet rowsetTms = null; 
        i = 0;

        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        rowsetTms = this.getDao("boadao").find("tbeb_race_day_ord_fb000", paramTms);
        
        // ���� ��ȸ
    	PosParameter paramOrgan = new PosParameter();
        PosRowSet rowsetOrgan   = null; 
        i = 0;

        paramOrgan.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramOrgan.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramOrgan.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramOrgan.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        rowsetOrgan = this.getDao("boadao").find("tbeb_organ_fb001", paramOrgan);
        
        // ���ֿ��� ��ȸ
    	PosParameter paramAlloc = new PosParameter();
        PosRowSet rowsetAlloc   = null; 
        i = 0;

        paramAlloc.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramAlloc.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramAlloc.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramAlloc.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetAlloc = this.getDao("boadao").find("tbeb_racer_race_alloc_fb002", paramAlloc);

        // �ּ�����(�����ּ�����)
    	PosParameter paramEsc = new PosParameter();
        PosRowSet rowsetEsc   = null; 
        i = 0;

        paramEsc.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramEsc.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramEsc.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramEsc.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetEsc = this.getDao("boadao").find("tbec_arrange_esc_fb001", paramEsc);

        // �ּ�����(��Ÿ�ּ�����)
    	PosParameter paramEscSanc = new PosParameter();
        PosRowSet rowsetEscSanc   = null; 
        i = 0;

        paramEscSanc.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramEscSanc.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramEscSanc.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramEscSanc.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetEscSanc = this.getDao("boadao").find("tbec_arrange_esc_fb002", paramEscSanc);

        // �ּ�����(�����ּ�����)
    	PosParameter paramEscStnd = new PosParameter();
        PosRowSet rowsetEscStnd   = null; 
        i = 0;

        paramEscStnd.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramEscStnd.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramEscStnd.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramEscStnd.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetEscStnd = this.getDao("boadao").find("tbec_arrange_esc_fb003", paramEscStnd);

        String sResultKey = "dsOutRaceDayOrd";
        ctx.put(sResultKey, rowsetTms);
        Util.addResultKey(ctx, sResultKey);

        sResultKey = "dsOutRacerArrange";
        ctx.put(sResultKey, createArrange(rowsetRacer, rowsetTms, rowsetOrgan, rowsetAlloc, rowsetEsc, rowsetEscSanc, rowsetEscStnd));
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> return �� �ּ� Dataset���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected PosRowSet createArrange(PosRowSet rowsetRacer, PosRowSet rowsetTms, PosRowSet rowsetOrgan, PosRowSet rowsetAlloc, PosRowSet rowsetEsc, PosRowSet rowsetEscSanc, PosRowSet rowsetEscStnd)
    {
        HashMap   hmRacer = new HashMap();
		ArrayList alRows  = new ArrayList();
		PosRowSet rowset  = rowsetRacer ;
		int nRow = 0;
		
		// ȸ���� column����
        PosColumnDef columnDef[] = createColumn(rowsetRacer, rowsetTms);

        // ���� ����Ʈ ����
        PosRow rows[] = rowsetRacer.getAllRow();
        for ( int i = 0; i < rows.length; i++ ) {
        	hmRacer.put((String) rows[i].getAttribute("RACER_NO"), Integer.toString(i));
			logger.logInfo(rows[i]);
        }
        
        String sColumnNmOnline = "";
        String sOnlineCnt = "";
        
        // �ּ�����(�����ּ�����)
        while ( rowsetEsc.hasNext() )
        {
        	PosRow row = rowsetEsc.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, "-");
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, "-");
        	}
        }
        
        // �ּ�����(��Ÿ�ּ�����)
        while ( rowsetEscSanc.hasNext() )
        {
        	PosRow row = rowsetEscSanc.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, "*");
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, "*");
        	}
        }
        
        // �ּ�����(�����ּ�����)
        while ( rowsetEscStnd.hasNext() )
        {
        	PosRow row = rowsetEscStnd.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, "#");
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, "#");
        	}
        }
        
        // ����
        while ( rowsetOrgan.hasNext() )
        {
        	PosRow row = rowsetOrgan.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, row.getAttribute("RACE_ALLOC_CNT"));
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
               	rows[nRow].setAttribute(sColumnNmOnline, row.getAttribute("RACE_ONLINE_CNT"));
        	}
        }

        // ���ֹ���
        while ( rowsetAlloc.hasNext() )
        {
        	PosRow row = rowsetAlloc.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, row.getAttribute("RACE_ALLOC_CNT"));
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, row.getAttribute("RACE_ONLIE_CNT"));
        	}
        }
        // Dataset ����
        for ( int i = 0; i < rows.length; i++ ) {
        	alRows.add(rows[i]);
        }
       
		if ( alRows.size() > 0 ) rowset = new PosRowSetImpl(alRows);
		rowset.setColumnDefs(columnDef);

        return rowset;
    }


    /**
     * <p> ȸ���� column���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected PosColumnDef[] createColumn(PosRowSet rowsetRacer, PosRowSet rowsetTms)
    {
        PosColumnDef columnDefRacer[] = rowsetRacer.getColumnDefs();
        PosColumnDef columnDefTms[]   = new PosColumnDef[rowsetTms.getAllRow().length*2];
        PosColumnDef columnDef[]      = new PosColumnDef[columnDefRacer.length + columnDefTms.length];
        int nCnt = 0;
        
        String sColumnNm = "";
        String sColumnNmOnline = "";
        String sTms = "";
        
        while ( rowsetTms.hasNext() )
        {
        	PosRow row = rowsetTms.next();
        	sTms = row.getAttribute("TMS").toString();
        	sColumnNm = "TMS_" + sTms + "_DAY_" + row.getAttribute("DAY_ORD");
        	columnDefTms[nCnt++] = new PosColumnDef(sColumnNm, 12, 10);
        	sColumnNm = "TMS_" + sTms + "_ONDAY_" + row.getAttribute("DAY_ORD");
        	columnDefTms[nCnt++] = new PosColumnDef(sColumnNm, 12, 10);
        }
        
        nCnt = 0;
        for ( int i = 0; i < columnDefRacer.length; i++ ) {
        	columnDef[nCnt++] = columnDefRacer[i];
        }
        
        for ( int i = 0; i < columnDefTms.length; i++ ) {
        	columnDef[nCnt++] = columnDefTms[i];
        }
        
        return columnDef;
    }
}
