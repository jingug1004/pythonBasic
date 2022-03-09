/*================================================================================
 * �ý���			: �л���� 
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d06009008.activity.SearchGduList.java
 * ���ϼ���		: �����ڿ��� ��ȸ �Ѵ�.. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-03-20
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06009008.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* �����ڿ� ��ȸ 
* @auther ������ 
* @version 1.0
*/
public class SearchGduList extends SnisActivity
{    
	public SearchGduList()
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
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	getEduList(ctx);
    	getTempAbs(ctx);
    	logger.logDebug("------------------>");
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ������ �� �̼��� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getEduList(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsEduList";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        //String	pGdu   = (String) ctx.get("pGdu");
        //rowset = getEduListRowSet(pRacerPerioNo, pGdu);
        rowset = getEduListRowSet(pRacerPerioNo);
            	
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * Ȯ���˻� �� ���� ��ȸ 
     * @param ctx
     */
    private void getTempAbs(PosContext ctx){
    	PosRowSet    rowset;
        String	sResultKey = "dsTempAbs";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        //String	pGdu   = (String) ctx.get("pGdu");
        //rowset = getTempAbsRowSet(pRacerPerioNo, pGdu);
        rowset = getTempAbsRowSet(pRacerPerioNo);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * ������ �� �̼��� ��ȸ 
     * @param pRacerPerioNo ���
     * @param pGdu	�������� 
     * @return
     */
    public PosRowSet getEduListRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        //param.setWhereClauseParameter(i++, pGdu);
        
        return  this.getDao("candao").find("tbdn_cmpt_edu_outlr_fb001", param);
    }
    
    /**
     * ����� ��ȸ 
     * @param pRacerPerioNo ���
     * @param pGdu	�������� 
     * @return
     */
    public PosRowSet getTempAbsRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        //param.setWhereClauseParameter(i++, pGdu);
        
        return  this.getDao("candao").find("tbdn_cmpt_edu_outlr_fb002", param);
    }
    
}

