/*================================================================================
 * �ý���			: ��ݰ��� 
 * �ҽ����� �̸�	: snis.boa.financial.e07020060.activity.SearchMonthPrizStat.java
 * ���ϼ���		: ������� ��� ��Ȳ�� ��ȸ�Ѵ�. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07020060.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ������� ��� ��Ȳ�� ��ȸ �Ѵ�. 
* @auther �輺��  
* @version 1.0
*/
public class SearchMonthPrizStat extends SnisActivity
{    
    public SearchMonthPrizStat()
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
    	
		getMonthPrizPerio(ctx);
		getMonthPrizGrd(ctx);
		getMonthPrizAmt(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ����� ��� �ݾ� ��  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getMonthPrizPerio(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPrizPerio";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	includeAllRegRacer   = (String) ctx.get("INCLUDE_ALL_REG_RACER");
        String	includeAllStatRacer   = (String) ctx.get("INCLUDE_ALL_STAT_RACER");
        
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, stndYear);
		param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, includeAllRegRacer);
		param.setWhereClauseParameter(paramIndex++, includeAllStatRacer);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_month_pay_ff101", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> ��޺� ��� �ݾ� ��  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getMonthPrizGrd(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPrizGrd";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	includeAllRegRacer   = (String) ctx.get("INCLUDE_ALL_REG_RACER");
        String	includeAllStatRacer   = (String) ctx.get("INCLUDE_ALL_STAT_RACER");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, stndYear);
		param.setWhereClauseParameter(paramIndex++, payMonth);
		param.setWhereClauseParameter(paramIndex++, includeAllRegRacer);
		param.setWhereClauseParameter(paramIndex++, includeAllStatRacer);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_month_pay_ff102", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * <p> ����� ��� �ݾ� ��  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void getMonthPrizAmt(PosContext ctx)
    {
        String	sResultKey = "dsOutMonthPrizAmt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	includeAllRegRacer   = (String) ctx.get("INCLUDE_ALL_REG_RACER");
        String	includeAllStatRacer   = (String) ctx.get("INCLUDE_ALL_STAT_RACER");
		
        int paramIndex=0;
        PosParameter param = new PosParameter();
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, payMonth);
        
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        
        param.setWhereClauseParameter(paramIndex++, payMonth);
        param.setWhereClauseParameter(paramIndex++, stndYear);
        
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, stndYear);
		param.setWhereClauseParameter(paramIndex++, payMonth);
		
		param.setWhereClauseParameter(paramIndex++, includeAllRegRacer);
		param.setWhereClauseParameter(paramIndex++, includeAllStatRacer);
		
        PosRowSet rowSet =   this.getDao("boadao").find("tbeg_month_pay_ff103", param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    
}

