package snis.rbm.business.rsm2011.activity;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchDiv extends SnisActivity {
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {
			
			String MD = Util.getCtxStr(ctx, "MD"); 			// ���� �ڵ�
			if("1".equals(MD))
			{
				searchDiv(ctx);	// ������ �˻�
			}
			else
			{
				searchAmt(ctx);	// ����� �˻�
			}
			
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}

	private void searchDiv(PosContext ctx) throws Exception {
		
		String MEET_CD	 = Util.getCtxStr(ctx, "MEET_CD"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY	 = Util.getCtxStr(ctx, "RACE_DAY"); 		// ���:001 ,����:003, â������:002, �λ걳��:004
		String COMM_NO   = Util.getCtxStr(ctx, "COMM_NO"); 			// ���� �ڵ�
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 0: ������
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 1: ���:001 ,����:003, â������:002, �λ걳��:004
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 2: ���:001 ,����:003, â������:002, �λ걳��:004
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 3: ���:001 ,����:003, â������:002, �λ걳��:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 4: ������
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select02"));
		
		
		// ������ ��ü�� ��� 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // ���� �ڵ尡 ��ü�� ���
		{
			
		}
		else
		{	
			sbQuery.append("\n");
			if("01".equals(COMM_NO)||"03".equals(COMM_NO)) { // ���� �̻縮 ����
				param.setWhereClauseParameter(nIdx++, MEET_CD);		// 0: ���:001 ,����:003, â������:002, �λ걳��:004
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where04"));
			} else if("99".equals(COMM_NO)) { // ���� �̻縮 ����
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));
			} else { // ����
				param.setWhereClauseParameter(nIdx++, COMM_NO);	// ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where05"));
			}
		}

		sbQuery.append("\n");
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_order01"));
            	        
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsDiv";
    	ctx.put(sOutDs, prs);
    	Util.addResultKey(ctx, sOutDs);    
	}
	
	private void searchAmt(PosContext ctx) throws Exception {
		
		
		
		String MEET_CD		= Util.getCtxStr(ctx, "MEET_CD"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY		= Util.getCtxStr(ctx, "RACE_DAY"); 			// ���:001 ,����:003, â������:002, �λ걳��:004		
		String COMM_NO	= Util.getCtxStr(ctx, "COMM_NO"); 			// ���� �ڵ�
		String DIV_NO	= Util.getCtxStr(ctx, "DIV_NO"); 			// ���� �ڵ�
		
		
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 0: ���:001 ,����:003, â������:002, �λ걳��:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 3: ������
		
		param.setWhereClauseParameter(nIdx++, DIV_NO);		// 4: ��ǥ��
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select03"));
		
		
		// ������ ��ü�� ��� 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // ���� �ڵ尡 ��ü�� ���
		{
			
		}
		else
		{	
			sbQuery.append("\n");
			int nCOMM_NO = Integer.parseInt(COMM_NO);
			if("01".equals(COMM_NO)&&nCOMM_NO < 11) // ���� �̻縮 ����
			{
				param.setWhereClauseParameter(nIdx++, MEET_CD);		// 0: ���:001 ,����:003, â������:002, �λ걳��:004
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where04"));
			}
			else // ����
			{	
				param.setWhereClauseParameter(nIdx++, COMM_NO);	// ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where05"));
			}
		}

		sbQuery.append("\n");
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby02"));
            	        
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsAmt";
    	ctx.put(sOutDs, prs);
    	Util.addResultKey(ctx, sOutDs);    
	}
}
