package snis.rbm.business.rsm3010.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchMonth extends SnisActivity {
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {
			
			searchMonth(ctx);	// ������ �˻�
			
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}

	private void searchMonth(PosContext ctx) throws Exception {
		
		String MEET_CD = (String) ctx.get("MEET_CD"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY_ST = (String) ctx.get("RACE_DAY_ST"); 	// �˻� ���� ��
		String RACE_DAY_END = (String) ctx.get("RACE_DAY_END"); // �˻� ������ ��
		String COMM_NO = (String) ctx.get("COMM_NO"); 			// ���� �ڵ�
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// ���:001 ,����:003, â������:002, �λ걳��:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST);	// �˻� ���� ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END);	// �˻� ������ ��
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST);	// �˻� ���� ��
		
		StringBuffer sbQuery = new StringBuffer();
        
		sbQuery.append(Util.getQuery(ctx, "rsm3010_select01"));
		
		// ������ ��ü�� ��� 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // ���� �ڵ尡 ���� ���
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3010_from01"));
		}
		else
		{	
			sbQuery.append(Util.getQuery(ctx, "rsm3010_from02"));
			if("003".equals(MEET_CD)) // ����  
			{	
				sbQuery.append("\n");
				if("01".equals(COMM_NO)) { // �̻縮 ����
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where01"));
				} else if("29".equals(COMM_NO)) { // ���� ����
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where02"));
				} else if ("98".equals(COMM_NO) || "99".equals(COMM_NO)) {	// �̻縮/���� ����Ȱ��
					param.setWhereClauseParameter(nIdx++, MEET_CD);			 // ��� ����
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where06"));	
				} else { // ����
					param.setWhereClauseParameter(nIdx++, Util.getCtxStr(ctx, "COMM_NO"));	// ���� �ڵ�
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where05"));
				}
			}			
			else 
			{	 
				sbQuery.append("\n");
				if("01".equals(COMM_NO)) {	// ���, ����
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where03"));
				} else if("29".equals(COMM_NO)) { // ���  ����
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where04"));
				} else if ("98".equals(COMM_NO) || "99".equals(COMM_NO)) {	// �̻縮/���� ����Ȱ��
					param.setWhereClauseParameter(nIdx++, MEET_CD);			 // ��� ����
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where06"));	
				} else { // ����
					param.setWhereClauseParameter(nIdx++, Util.getCtxStr(ctx, "COMM_NO"));	// ���� �ڵ�
					sbQuery.append(Util.getQuery(ctx, "rsm3010_where05"));
				}
			}
		}

		sbQuery.append("\n");
        
		sbQuery.append(Util.getQuery(ctx, "rsm3010_group01"));
            	        
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsMonth";
    	ctx.put(sOutDs, prs);
    	Util.addResultKey(ctx, sOutDs);    
	}
	
	
}
