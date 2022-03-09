/*================================================================================
 * �ý���			: ��ݰ��� 
 * �ҽ����� �̸�	: snis.boa.financial.e07020010.activity.SearchRankAmtList.java
 * ���ϼ���		: ������������ ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-01
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07020010.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ������������ ��ȸ �Ѵ�. 
* @auther ������  
* @version 1.0
*/
public class SearchRankAmtList extends SnisActivity
{    
    public SearchRankAmtList()
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
    	
		getComInfo(ctx);
		getRacerComInfo(ctx);
		getRacerEventComInfo(ctx);
		getMonthPay(ctx);
		getSafyList(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ����������  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutAmtlList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
        
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff001", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> ���������� (��������)  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getRacerComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutRunList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff002", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> ���������� (�̺�Ʈ���ּ���)  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getRacerEventComInfo(PosContext ctx)
    {
        String	sResultKey = "dsOutEventList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_event_rankamt_ff002", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * <p> ���������� (��������) </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getMonthPay(PosContext ctx)
    {
    	String	sResultKey = "dsOutStrList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff003", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> ���������� (��������)  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getSafyList(PosContext ctx)
    {
    	String	sResultKey = "dsOutSafyList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        if(payMonth.length()==1){
        	payMonth = "0"+payMonth;
        }
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_race_priz_rankamt_ff004", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
}

