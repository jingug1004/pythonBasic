package snis.rbm.business.rsm2031.activity;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchRaceResultToday extends SnisActivity {
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {
			
			searchRaceResult(ctx);	// ������ �˻�
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}
	
	/**
     * ���ֺ� ��Ȳ
     * 
     */
	private void searchRaceResult(PosContext ctx) throws Exception {
		
		
		
		String MEET_CD = (String) ctx.get("MEET_CD"); 		// ���:001 ,����:003
		String STND_YEAR = (String) ctx.get("STND_YEAR"); 	// ���س⵵
		String TMS = (String) ctx.get("TMS"); 				// ȸ��
		String DAY_ORD = (String) ctx.get("DAY_ORD"); 		// ����
		String RACE_YOIL = (String) ctx.get("RACE_YOIL"); 	// ����
		
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		
		StringBuffer sbQuery = new StringBuffer();
		
		sbQuery.append(Util.getQuery(ctx, "rsm2031_select03"));		
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// ���:001 ,����:003
		param.setWhereClauseParameter(nIdx++, STND_YEAR);	// ���س⵵
		param.setWhereClauseParameter(nIdx++, TMS);			// ȸ��
		param.setWhereClauseParameter(nIdx++, DAY_ORD);		// ����
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// ���:001 ,����:003
		param.setWhereClauseParameter(nIdx++, STND_YEAR);	// ���س⵵
		param.setWhereClauseParameter(nIdx++, TMS);			// ȸ��
		//param.setWhereClauseParameter(nIdx++, DAY_ORD);		// ����
		param.setWhereClauseParameter(nIdx++, RACE_YOIL);		// ����
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// ���:001 ,����:003
		param.setWhereClauseParameter(nIdx++, STND_YEAR);	// ���س⵵
		param.setWhereClauseParameter(nIdx++, TMS);			// ȸ��
		//param.setWhereClauseParameter(nIdx++, DAY_ORD);		// ����
		param.setWhereClauseParameter(nIdx++, RACE_YOIL);		// ����
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// ���:001 ,����:003
		param.setWhereClauseParameter(nIdx++, STND_YEAR);	// ���س⵵
		param.setWhereClauseParameter(nIdx++, TMS);			// ȸ��
		param.setWhereClauseParameter(nIdx++, DAY_ORD);		// ����
		
		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm2031_from01Today"));
		
		if("003".equals(MEET_CD)) // ������ ���
		{
			sbQuery.append("\n");
			
			param.setWhereClauseParameter(nIdx++, STND_YEAR);	// ���س⵵
			param.setWhereClauseParameter(nIdx++, TMS);			// ȸ��
			param.setWhereClauseParameter(nIdx++, DAY_ORD);		// ����
			sbQuery.append(Util.getQuery(ctx, "rsm2031_from02"));
		}
		else	// ����� ���
		{
			sbQuery.append("\n");
			param.setWhereClauseParameter(nIdx++, STND_YEAR);	// ���س⵵
			param.setWhereClauseParameter(nIdx++, MEET_CD);		// ���:001 ,����:003
			param.setWhereClauseParameter(nIdx++, TMS);			// ȸ��			
			param.setWhereClauseParameter(nIdx++, DAY_ORD);		// ����
			sbQuery.append(Util.getQuery(ctx, "rsm2031_from03"));			
		}
		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm2031_where01")); 
		
		
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsRaceResult";
    	ctx.put(sOutDs, prs);
    	Util.addResultKey(ctx, sOutDs);    
	}
	
	
}
