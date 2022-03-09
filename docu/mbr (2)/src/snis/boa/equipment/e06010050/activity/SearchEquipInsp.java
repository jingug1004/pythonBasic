/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010050.activity.SearchEquipInsp.java
 * ���ϼ���		: Ȯ�� �˻縦 ��ȸ �Ѵ�.. 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010050.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;
import com.posdata.glue.dao.vo.*;

/**
* ��� Ȯ���˻� ��ȸ 
* @auther �輺�� 
* @version 1.0
*/
public class SearchEquipInsp extends SnisActivity
{    
	String EQUIP_DRWLT_CMPL_YN = "X";
    public SearchEquipInsp()
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
    	getEquipInsp(ctx);
    	getEquipInspDetail(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��� Ȯ���˻� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getEquipInsp(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipInsp";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        Integer	dayOrd =  new Integer((String) ctx.get("DAY_ORD"));
        rowset = getEquipInspRowSet(stndYear, mbrCd, tms, dayOrd);
        
        
       if(rowset!= null && rowset.hasNext()){
    		PosRow[] pr = rowset.getAllRow();
    		if(logger.isDebugEnabled())logger.logDebug("pr = " + pr);
    		if(pr!= null && pr.length>0){
    			if(logger.isDebugEnabled())logger.logDebug("pr[0] = " + pr[0]);
    			EQUIP_DRWLT_CMPL_YN = (String)(pr[0].getAttribute("INSP_PRS_STAT_CD"));
    			if(logger.isDebugEnabled())logger.logDebug("INSP_PRS_STAT_CD = " + EQUIP_DRWLT_CMPL_YN);
    		}
    	}
    	
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * Ȯ���˻� �� ���� ��ȸ 
     * @param ctx
     */
    private void getEquipInspDetail(PosContext ctx){
    	PosRowSet    rowset;
        String	sResultKey = "dsOutEquipInspDetail";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        Integer	dayOrd =  new Integer((String) ctx.get("DAY_ORD"));
        rowset = (new SearchEquipInspDetail()).getEquipInspDetailRowSet(stndYear, mbrCd, tms, dayOrd, EQUIP_DRWLT_CMPL_YN);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * Ȯ���˻� ��� ��ȸ 
     * @param stndYear ���س⵵
     * @param tms	ȸ�� 
     * @param dayOrd	���� 
     * @return
     */
    public PosRowSet getEquipInspRowSet(String stndYear, String mbrCd, Integer tms, Integer dayOrd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, dayOrd);
        return  this.getDao("boadao").find("tbef_equip_insp_ff001", param);
    }
    
}

