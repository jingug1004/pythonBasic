/*================================================================================
 * �ý���			:  �ݾױ����� �߸�
 * �ҽ����� �̸�	: snis.rbm.business.rsm3190.activity.SearchSellstDetail.java
 * ���ϼ���		: �ݾױ����� �߸�
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-14
 * ������������	: 							   
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm3190.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchSellstDetail extends SnisActivity {
public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			SearchSellstDetail(ctx); // ������ �˻�
		} catch (Exception e) {
			Util.setSvcMsg(ctx, e.getMessage());
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void SearchSellstDetail(PosContext ctx) throws Exception {
		String WIN_TYPE		= Util.getCtxStr(ctx, "WIN_TYPE");		// �߸ű� ����
		String MEET_CD		= Util.getCtxStr(ctx, "MEET_CD");		// ��� ����
		String GROUP_OPT	= Util.getCtxStr(ctx, "GROUP_OPT");		// �ɼ�
		String RACE_DAY_ST = Util.getCtxStr(ctx, "RACE_DAY_ST");	// �˻� ���� ��
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// �˻� ������ ��
		String COMM_NO		= Util.getCtxStr(ctx, "COMM_NO"); 		// ���� �ڵ�
				

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, WIN_TYPE); 	// �׸�ī�� ����(0:��ü,1:����,2:�׸�ī��)
		param.setWhereClauseParameter(nIdx++, MEET_CD); 	// ��� ����
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
		
		// SELECT
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3190_select01"));
		
		sbQuery.append(" \n");
		if("TMS".equals(GROUP_OPT))	// ȸ����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select02"));	
		}
		else if("RACE_NO".equals(GROUP_OPT))	// ���ֺ�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select03"));	
		}
		else if("RACE_DAY".equals(GROUP_OPT))	// ���ں�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select04"));	
		}
		else if("BRNC".equals(GROUP_OPT))	// ������
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select04_1"));	
		}
		
		// SELECT 
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3190_select05"));
		
		//  FROM
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3190_from01"));
		
		
		// WHERE

		if(!"00".equals(COMM_NO))
		{
			sbQuery.append(" \n");
			if("01".equals(COMM_NO))	// ����
			{
				param.setWhereClauseParameter(nIdx++, MEET_CD); // ��� ����
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where01"));
			}
			else if ("29".equals(COMM_NO))	// ����
			{
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where02"));	
			}
			else if ("98".equals(COMM_NO) || "99".equals(COMM_NO))	// �̻縮/���� ����Ȱ��
			{
				param.setWhereClauseParameter(nIdx++, MEET_CD); // ��� ����
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where05"));	
			}
			else		// ������
			{
				param.setWhereClauseParameter(nIdx++, COMM_NO); // ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where03"));
			}
		
		}
		
		
		// GROUP BY 
		sbQuery.append(" \n");
		if("TMS".equals(GROUP_OPT))	// ȸ����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby01"));	
		}
		else if("RACE_NO".equals(GROUP_OPT))	// ���ֺ�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby02"));	
		}
		else if("RACE_DAY".equals(GROUP_OPT))	// ���ں�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby03"));	
		}
		else if("BRNC".equals(GROUP_OPT))	// ������
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby03_1"));	
		}
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsSellst";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
		
		
	}
}
