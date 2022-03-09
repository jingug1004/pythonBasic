package snis.rbm.business.rsm2011.activity;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchDivToday extends SnisActivity {
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
		
		String MEET_CD		= Util.getCtxStr(ctx, "MEET_CD"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY		= Util.getCtxStr(ctx, "RACE_DAY"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String SELL_CD  = (String) ctx.get("SELL_CD"); 			// �Ǹ�ó�ڵ�
		String TUE_MBR_YN  = (String) ctx.get("TUE_MBR_YN"); 			// ȭ����� ����
		
		String COMM_NO = Util.getCtxStr(ctx, "COMM_NO"); 			// ���� �ڵ�
		String DIV_NO	= (String) ctx.get("DIV_NO"); 			// �׸� ī�� �� ��
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		StringBuffer sbQuery = new StringBuffer();
		
		
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select12Today"));		
		
		// ������ ��ü�� ��� 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // ���� �ڵ尡 ��ü�� ���
		{
			sbQuery.append("\n");
		}
		else if("03".equals(COMM_NO)) // �̻縮 ����
		{
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where02"));
			
		}		
		else if("01".equals(COMM_NO)) // ���� ����  
		{		
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where01"));
		}
		else if("06".equals(COMM_NO)) // �׸�ī��  
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			if(!"".equals(DIV_NO)) {
				if (!"00".equals(DIV_NO)) {
					SELL_CD = "01";		// �Ǹ�ó�ڵ�
				}
			} else {
				SELL_CD = "%";			// �Ǹ�ó�ڵ�:�׸�ī�� ��ü�� ��� ��ü sell_cd�� �ڷḦ �����;� �� 
			}
			param.setWhereClauseParameter(nIdx++, SELL_CD);		// �Ǹ�ó�ڵ�					
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// �׸�ī��
			sbQuery.append("\n");			
			
			if(!"".equals(DIV_NO))
			{
				param.setWhereClauseParameter(nIdx++, DIV_NO);			// ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where06"));	// �׸�ī��
			}
		} else if("99".equals(COMM_NO)) { // ���� �̻縮 ����
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));
		}
		else   
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "01");			// �Ǹ�ó�ڵ�
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// ������, ����
		}

		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby12Today"));
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// ȭ����� ����
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// ȭ����� ����
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select13Today"));
		
		sbQuery.append("\n");	
		if("00".equals(COMM_NO))	// ��ü�� ���
		{	
			sbQuery.append("\n");
		}		
		else if("03".equals(COMM_NO)) // �̻縮 ����
		{
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where02"));
			
		}		
		else if("01".equals(COMM_NO)) // ���� ����  
		{		
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where01"));
		}
		else if("06".equals(COMM_NO)) // �׸�ī��  
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			if(!"".equals(DIV_NO)) {
				if (!"00".equals(DIV_NO)) {
					SELL_CD = "01";		// �Ǹ�ó�ڵ�
				}
			} else {
				SELL_CD = "%";			// �Ǹ�ó�ڵ�:�׸�ī�� ��ü�� ��� ��ü sell_cd�� �ڷḦ �����;� �� 
			}
			param.setWhereClauseParameter(nIdx++, SELL_CD);		// �Ǹ�ó�ڵ�					
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// �׸�ī��
			sbQuery.append("\n");			
			
			if(!"".equals(DIV_NO))
			{
				param.setWhereClauseParameter(nIdx++, DIV_NO);			// ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where06"));	// �׸�ī��
			}
		} else if("99".equals(COMM_NO)) { // ���� �̻縮 ����
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));
		}
		else   
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "01");			// �Ǹ�ó�ڵ�
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// ������, ����
		}
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);		// ������
		sbQuery.append(Util.getQuery(ctx, "rsm2011_where07"));
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);		// ������
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby13Today"));
            	        
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsDivSales";
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
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 1: ���:001 ,����:003, â������:002, �λ걳��:004
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 2: ���:001 ,����:003, â������:002, �λ걳��:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 3: ������
		
		param.setWhereClauseParameter(nIdx++, DIV_NO);		// 4: ��ǥ��
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select03Today"));
		
		
		// ������ ��ü�� ��� 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // ���� �ڵ尡 ��ü�� ���
		{
			
		}
		else
		{	
			sbQuery.append("\n");
			if("01".equals(COMM_NO)) // ���� �̻縮 ����
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
