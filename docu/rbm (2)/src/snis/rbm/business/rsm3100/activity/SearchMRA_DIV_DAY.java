/*================================================================================
 * �ý���			:  �������� ���ں� 
 * �ҽ����� �̸�	: snis.rbm.business.rsm3100.activity.SearchMRA_DIV_DAY.java
 * ���ϼ���		: �������� ���ں� 
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-14
 * ������������	: 							   
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm3100.activity;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchMRA_DIV_DAY extends SnisActivity {
	public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchMRADivDay(ctx); // ������ �˻�
		} catch (Exception e) {
			Util.setSvcMsg(ctx, e.getMessage());
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchMRADivDay(PosContext ctx) throws Exception {		
		String RACE_DAY_ST  = Util.getCtxStr(ctx, "RACE_DAY_ST");	// �˻� ���� ��
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// �˻� ������ ��
		String OPT          = Util.getCtxStr(ctx, "OPT"); 			// ��������ڵ�
		String VALID_YN     = Util.getCtxStr(ctx, "VALID_YN"); 		// �����ڷ� ����("1":�̰����ڷ�)
		

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		
		
		if("MM".equals(OPT))	// ����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select02"));	
		}
		else	// ���ں�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select01"));
		}
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3100_select03"));
		
		sbQuery.append(" \n");
		if ("1".equals(VALID_YN)) {
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select04Today"));
		} else {
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select04"));
		}
		
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3100_select05"));
		
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// ����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_groupby02"));	
		}
		else	// ���ں�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_groupby01"));
		}
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsMRADIVDay";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
		
		
	}
}
