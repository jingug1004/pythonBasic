/*================================================================================
 * �ý���			:  ���ں� �����
 * �ҽ����� �̸�	: snis.rbm.business.rsm3080.activity.SearchDIV_DAY.java
 * ���ϼ���		: ���ں� �����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-14
 * ������������	: 							   
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm3080.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchDIV_DAY extends SnisActivity {
public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchDivDay(ctx); // ������ �˻�
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchDivDay(PosContext ctx) throws Exception {

		String MEET_CD      = Util.getCtxStr(ctx, "MEET_CD"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY_ST  = Util.getCtxStr(ctx, "RACE_DAY_ST");	// �˻� ���� ��
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// �˻� ������ ��
		String OPT = Util.getCtxStr(ctx, "OPT"); 	// �˻� ������ ��
		
		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		//param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
		//param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� �⵵��
		//param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� �⵵��
		
		if("MM".equals(OPT))	// ����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_select02"));	
		}
		else	// ���ں�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_select01"));
		}
		sbQuery.append(" \n");
		
//			sbQuery.append(Util.getQuery(ctx, "rsm3080_select03"));
		sbQuery.append(Util.getQuery(ctx, "rsm3080_select03_grncd_1"));
		
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// ����
		{
//			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02"));	
			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02_grncd_1"));	
		}
		else	// ���ں�
		{
//				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01"));
				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01_grncd_1"));
		}
		
		
		///////////////
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
		//param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� �⵵ ��
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3080_select03_grncd_2"));
		
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// ����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02_grncd_1"));	
		}
		else	// ���ں�
		{
				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01_grncd_1"));
		}
		
		///////////////
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// ����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02_grncd_2"));	
		}
		else	// ���ں�
		{
				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01_grncd_2"));
		}
		
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsDIVDay";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
}
