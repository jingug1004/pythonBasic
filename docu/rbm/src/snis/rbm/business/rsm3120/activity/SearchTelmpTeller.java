/*================================================================================
 * �ý���			:  ���κ� �߸�
 * �ҽ����� �̸�	: snis.rbm.business.rsm3120.activity.SearchTelmpTeller.java
 * ���ϼ���		: ���κ� �߸�
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-11-14
 * ������������	: 							   
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm3120.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchTelmpTeller extends SnisActivity {
	public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			String MODE	= Util.getCtxStr(ctx, "MODE");		// ��� ����
			if("VIEW".equals(MODE) )
			{
				// ���κ� �߸�  View
				searchTelmpTellerView(ctx); // ���� �˻� Ŭ���� ������ �˻� 
			}
			else
			{
				searchTelmpTeller(ctx); // ������ �˻�	
			}
		} catch (Exception e) {
			Util.setSvcMsg(ctx, e.getMessage());
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchTelmpTeller(PosContext ctx) throws Exception {
		String MEET_CD_001	= Util.getCtxStr(ctx, "MEET_CD_001");		// ������
		String MEET_CD_002	= Util.getCtxStr(ctx, "MEET_CD_002");		// â�����
		String MEET_CD_003	= Util.getCtxStr(ctx, "MEET_CD_003");		// ����(�̻縮)
		String MEET_CD_004	= Util.getCtxStr(ctx, "MEET_CD_004");		// �λ���
		//String OPTION	= Util.getCtxStr(ctx, "OPTION");		// �ɼ� (��������)
		String TABLE_NM = Util.getCtxStr(ctx, "TABLE_NM");		// ���ֱ�, ���ű�
		String TYPE		= Util.getCtxStr(ctx, "TYPE");			// �߸ſ���, â����
		
		String RACE_DAY_ST	= Util.getCtxStr(ctx, "RACE_DAY_ST");	// �˻� ���� ��
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// �˻� ������ ��		
		String COMM_NO		= Util.getCtxStr(ctx, "COMM_NO"); 		// ���� �ڵ�
				


		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
				
		// SELECT
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select01"));
		
		sbQuery.append(" \n");
		if("TELLER_ID".equals(TYPE))
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select02"));
		}
		else
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select03"));
		}
		
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select04"));
		
		
		// FROM
		sbQuery.append(" \n");
		if("TBJI_PC_TELMP".equals(TABLE_NM))	// ���ֱ�
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from01"));	
		}
		else	// â����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from02"));
		}
		
		
		// WHERE		
		param.setWhereClauseParameter(nIdx++, MEET_CD_001); // ������
		param.setWhereClauseParameter(nIdx++, MEET_CD_002); // ������
		param.setWhereClauseParameter(nIdx++, MEET_CD_003); // ������
		param.setWhereClauseParameter(nIdx++, MEET_CD_004); // ������
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_where01"));
		
		if(!"00".equals(COMM_NO))// ��ü������ �ƴ� ���
		{
			sbQuery.append(" \n");
			if("01".equals(COMM_NO)) {
				param.setWhereClauseParameter(nIdx++, MEET_CD_001); // ������
				param.setWhereClauseParameter(nIdx++, MEET_CD_002); // â�����
				param.setWhereClauseParameter(nIdx++, MEET_CD_003); // ����(�̻縮)
				param.setWhereClauseParameter(nIdx++, MEET_CD_004); // �λ���
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where02"));
			} else if("29".equals(COMM_NO)) { // ���� 
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where03"));
			} else if("98".equals(COMM_NO)) { // �̻縮 
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where05"));
			} else if("99".equals(COMM_NO)) { // ���� 
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where06"));
			} else {
				param.setWhereClauseParameter(nIdx++, COMM_NO); // �����ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where04"));
			}
		}
		
		
		if("TELLER_ID".equals(TYPE))	// �߸ſ���
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_groupby01"));	
		}
		else	// â����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_groupby02"));
		}
		
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsTelmpTeller";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
	
	
	// ���κ� �߸�  View 
	private void searchTelmpTellerView(PosContext ctx) throws Exception {
		
		String MEET_CD_001	= Util.getCtxStr(ctx, "MEET_CD_001");		// ������
		String MEET_CD_002	= Util.getCtxStr(ctx, "MEET_CD_002");		// â�����
		String MEET_CD_003	= Util.getCtxStr(ctx, "MEET_CD_003");		// ����(�̻縮)
		String MEET_CD_004	= Util.getCtxStr(ctx, "MEET_CD_004");		// �λ���
		String TABLE_NM = Util.getCtxStr(ctx, "TABLE_NM");		// ���ֱ�, ���ű�
		String TYPE		= Util.getCtxStr(ctx, "TYPE");			// �߸ſ���, â����		
		String RACE_DAY_ST	= Util.getCtxStr(ctx, "RACE_DAY_ST");	// �˻� ���� ��
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// �˻� ������ ��
		String TELLER_ID	= Util.getCtxStr(ctx, "TELLER_ID"); 		// �߸ſ� ��ȣ
		String COMM_NO		= Util.getCtxStr(ctx, "COMM_NO"); 		// ���� �ڵ�
		
		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
				
		// SELECT
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select01"));
		
		sbQuery.append(" \n");
		if("TELLER_ID".equals(TYPE))
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select25"));
		}
		else
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select26"));
		}
		
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select27"));
		
		
		// FROM
		sbQuery.append(" \n");
		System.out.println("TABLE_NM="+TABLE_NM);		
		System.out.println("TYPE="+TYPE);		
		if("TBJI_PC_TELMP".equals(TABLE_NM))	// �߸ſ���
		//if("TELLER_ID".equals(TYPE))	// �߸ſ���
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from01"));	
		}
		else	// â����
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from02"));
		}
		
		
		// WHERE
		
		param.setWhereClauseParameter(nIdx++, MEET_CD_001); // ������
		param.setWhereClauseParameter(nIdx++, MEET_CD_002); // ������
		param.setWhereClauseParameter(nIdx++, MEET_CD_003); // ������
		param.setWhereClauseParameter(nIdx++, MEET_CD_004); // ������
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // �˻� ������ ��
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_where01"));

		// ���γ��� ��ü��ȸ�� ��쿡�� VIEW���� �����ڵ尡 �Ѿ��.
		if (COMM_NO.trim().length() > 1) {
			sbQuery.append(" \n");		
			if(!"00".equals(COMM_NO))// ��ü������ �ƴ� ���
			{
				sbQuery.append(" \n");
				if("01".equals(COMM_NO))
				{
					param.setWhereClauseParameter(nIdx++, MEET_CD_001); // ������
					param.setWhereClauseParameter(nIdx++, MEET_CD_003); // ����(�̻縮)
					sbQuery.append(Util.getQuery(ctx, "rsm3120_where02"));
				}
				else if("29".equals(COMM_NO)) // ���� 
				{				
					sbQuery.append(Util.getQuery(ctx, "rsm3120_where03"));
				}
				else 
				{
					param.setWhereClauseParameter(nIdx++, COMM_NO); // �����ڵ�
					sbQuery.append(Util.getQuery(ctx, "rsm3120_where04"));
				}
			}
		}
		
		sbQuery.append(" \n");
		if("TELLER_ID".equals(TYPE))	// �߸ſ���
		{
			param.setWhereClauseParameter(nIdx++, TELLER_ID); 	// �߸ſ�
			sbQuery.append(Util.getQuery(ctx, "rsm3120_where25"));	
		}
		else	// â����
		{
			param.setWhereClauseParameter(nIdx++, TELLER_ID); 	// â��
			sbQuery.append(Util.getQuery(ctx, "rsm3120_where26"));
		}
		
		
		
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsTelmpTeller";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
	
}
