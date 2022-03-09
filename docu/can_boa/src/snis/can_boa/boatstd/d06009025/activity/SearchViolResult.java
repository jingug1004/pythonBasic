/*================================================================================
 * �ý���			: �л���� 
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d06009025.activity.SearchViolResult.java
 * ���ϼ���		: ���ս����ð��� ��ȸ �Ѵ�.. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-03-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06009025.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* ���ս����ð� ��ȸ 
* @auther ������ 
* @version 1.0
*/
public class SearchViolResult extends SnisActivity
{    
	public SearchViolResult()
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
    	getEduList(ctx);
    	getTempAbs(ctx);
    	logger.logDebug("------------------>");
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ���ս����ð� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getEduList(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsCandList";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        rowset = getEduListRowSet(pRacerPerioNo);
            	
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * ���/�ִ�/�ּ� ��ȸ 
     * @param ctx
     */
    private void getTempAbs(PosContext ctx){
    	PosRowSet    rowset;
        String	sResultKey = "dsCandListResult";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        rowset = getTempAbsRowSet(pRacerPerioNo);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * ���ս����ð� ��ȸ 
     * @param pRacerPerioNo ���
     * @return
     */
    public PosRowSet getEduListRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        return  this.getDao("candao").find("tbdn_cand_race_detlr_fb002", param);
    }
    
    /**
     * ���/�ִ�/�ּ� ��ȸ 
     * @param pRacerPerioNo ���
     * @return
     */
    public PosRowSet getTempAbsRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        
        return  this.getDao("candao").find("tbdn_cand_race_detlr_fb003", param);
    }
    
}

