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
			
			String MD = Util.getCtxStr(ctx, "MD"); 			// 지점 코드
			if("1".equals(MD))
			{
				searchDiv(ctx);	// 데이터 검색
			}
			else
			{
				searchAmt(ctx);	// 매출액 검색
			}
			
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}

	private void searchDiv(PosContext ctx) throws Exception {
		
		String MEET_CD	 = Util.getCtxStr(ctx, "MEET_CD"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String RACE_DAY	 = Util.getCtxStr(ctx, "RACE_DAY"); 		// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String COMM_NO   = Util.getCtxStr(ctx, "COMM_NO"); 			// 지점 코드
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 0: 경주일
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 1: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 2: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 3: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 4: 경주일
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select02"));
		
		
		// 지점이 전체일 경우 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // 지점 코드가 전체일 경우
		{
			
		}
		else
		{	
			sbQuery.append("\n");
			if("01".equals(COMM_NO)||"03".equals(COMM_NO)) { // 광명 미사리 본장
				param.setWhereClauseParameter(nIdx++, MEET_CD);		// 0: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where04"));
			} else if("99".equals(COMM_NO)) { // 광명 미사리 본장
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));
			} else { // 지점
				param.setWhereClauseParameter(nIdx++, COMM_NO);	// 지점 코드
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
		
		
		
		String MEET_CD		= Util.getCtxStr(ctx, "MEET_CD"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String RACE_DAY		= Util.getCtxStr(ctx, "RACE_DAY"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004		
		String COMM_NO	= Util.getCtxStr(ctx, "COMM_NO"); 			// 지점 코드
		String DIV_NO	= Util.getCtxStr(ctx, "DIV_NO"); 			// 지점 코드
		
		
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 0: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 3: 경주일
		
		param.setWhereClauseParameter(nIdx++, DIV_NO);		// 4: 투표소
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select03"));
		
		
		// 지점이 전체일 경우 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // 지점 코드가 전체일 경우
		{
			
		}
		else
		{	
			sbQuery.append("\n");
			int nCOMM_NO = Integer.parseInt(COMM_NO);
			if("01".equals(COMM_NO)&&nCOMM_NO < 11) // 광명 미사리 본장
			{
				param.setWhereClauseParameter(nIdx++, MEET_CD);		// 0: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where04"));
			}
			else // 지점
			{	
				param.setWhereClauseParameter(nIdx++, COMM_NO);	// 지점 코드
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
