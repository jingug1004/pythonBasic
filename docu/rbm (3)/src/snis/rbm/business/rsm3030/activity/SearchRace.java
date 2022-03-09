/*================================================================================
 * �ý���			: ���ֺ� �����
 * �ҽ����� �̸�	: snis.rbm.business.rsm3030.activity.SearchRace.java
 * ���ϼ���		: ���ֺ� �����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-12
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm3030.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchRace extends SnisActivity {
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchMonth(ctx); // ������ �˻�
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}

	private void searchMonth(PosContext ctx) throws Exception {

		String MEET_CD = (String) ctx.get("MEET_CD"); // ���:001 ,����:003,
		// â������:002, �λ걳��:004
		String RACE_DAY_ST = (String) ctx.get("RACE_DAY_ST"); // �˻� ���� ��
		String RACE_DAY_END = (String) ctx.get("RACE_DAY_END"); // �˻� ������ ��
		String COMM_NO = Util.getCtxStr(ctx,"COMM_NO"); // ���� �ڵ�

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();

		sbQuery.append(Util.getQuery(ctx, "rsm3030_select01"));
		param.setWhereClauseParameter(nIdx++, MEET_CD); // ���:001
														// ,����:003,â������:002,
														// �λ걳��:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
				
		if ("00".equals(COMM_NO)) // ��ü ������ ���
		{	
			sbQuery.append("\n");
			sbQuery.append(Util.getQuery(ctx, "rsm3030_from02"));
		} 
		else 
		{
			sbQuery.append("\n");
			sbQuery.append(Util.getQuery(ctx, "rsm3030_from01"));
			if ("003".equals(MEET_CD)) // ����
			{
				sbQuery.append("\n");
				if ("01".equals(COMM_NO)) { // �̻縮 ����
					param.setWhereClauseParameter(nIdx++, MEET_CD); 
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where01"));
				} else if ("29".equals(COMM_NO)) { // ���� ����
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where02"));
				} else if ("98".equals(COMM_NO) || "99".equals(COMM_NO)) {	// �̻縮/���� ����Ȱ��
					param.setWhereClauseParameter(nIdx++, MEET_CD);			 // ��� ����
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where04"));	
				} else { // ����
					param.setWhereClauseParameter(nIdx++, COMM_NO); // ���� �ڵ�
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where03"));
				}
			} else {
				sbQuery.append("\n");
				if ("01".equals(COMM_NO)) { // ���, ����
					param.setWhereClauseParameter(nIdx++, MEET_CD); 
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where01"));
				} else if ("29".equals(COMM_NO)) { // ��� ����
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where02"));
				} else if ("98".equals(COMM_NO) || "99".equals(COMM_NO)) {	// �̻縮/���� ����Ȱ��
					param.setWhereClauseParameter(nIdx++, MEET_CD);			 // ��� ����
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where04"));	
				} else { // ����
					param.setWhereClauseParameter(nIdx++, COMM_NO); // ���� �ڵ�
					sbQuery.append(Util.getQuery(ctx, "rsm3030_where03"));
				}
			}
		}

		sbQuery.append("\n");

		sbQuery.append(Util.getQuery(ctx, "rsm3030_group01"));

		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),
				param);
		String sOutDs = "dsRace";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
	}

}
