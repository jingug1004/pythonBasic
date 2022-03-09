/*================================================================================
 * �ý���			: ȸ���� �����
 * �ҽ����� �̸�	: snis.rbm.business.rsm3030.activity.SearchTMS.java
 * ���ϼ���		: ȸ���� �����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-13
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm3040.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchTMS extends SnisActivity {
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchTms(ctx); // ������ �˻�
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}

	private void searchTms(PosContext ctx) throws Exception {

		String MEET_CD = Util.getCtxStr(ctx, "MEET_CD"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY_ST = Util.getCtxStr(ctx, "RACE_DAY_ST");	// �˻� ���� ��
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// �˻� ������ ��
		String COMM_NO = Util.getCtxStr(ctx,"COMM_NO"); // ���� �ڵ�
		String GREEN_YN = Util.getCtxStr(ctx,"GREEN_YN"); // ���� �ڵ�

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();

		sbQuery.append(Util.getQuery(ctx, "rsm3040_select01"));
		param.setWhereClauseParameter(nIdx++, MEET_CD); // ���:001
														// ,����:003,â������:002,
														// �λ걳��:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
				
		if ("00".equals(COMM_NO)) // ��ü ������ ���
		{	
			sbQuery.append("\n");
			sbQuery.append(Util.getQuery(ctx, "rsm3040_from02"));
		} 
		else 
		{
			sbQuery.append("\n");
			if ("Y".equals(GREEN_YN)) {
				sbQuery.append(Util.getQuery(ctx, "rsm3040_from03"));
			} else {
				sbQuery.append(Util.getQuery(ctx, "rsm3040_from01"));
			}
			
			
			if ("01".equals(COMM_NO)) { // ����/�̻縮 ����
				param.setWhereClauseParameter(nIdx++, MEET_CD); 
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where01"));
			} else if ("29".equals(COMM_NO)) { //  ����
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where02"));
			} else if ("98".equals(COMM_NO) || "99".equals(COMM_NO)) {	// �̻縮/���� ����Ȱ��
				param.setWhereClauseParameter(nIdx++, MEET_CD);			 // ��� ����
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where04"));	
			} else { // ����
				param.setWhereClauseParameter(nIdx++, COMM_NO); // ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where03"));
			}
		}

		sbQuery.append("\n");

		sbQuery.append(Util.getQuery(ctx, "rsm3040_groupby01"));

		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),
				param);
		String sOutDs = "dsTMS";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
	}
}
