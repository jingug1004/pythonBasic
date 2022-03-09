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
		
		String MEET_CD		= Util.getCtxStr(ctx, "MEET_CD"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String RACE_DAY		= Util.getCtxStr(ctx, "RACE_DAY"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String SELL_CD  = (String) ctx.get("SELL_CD"); 			// 판매처코드
		String TUE_MBR_YN  = (String) ctx.get("TUE_MBR_YN"); 			// 화요경정 유무
		
		String COMM_NO = Util.getCtxStr(ctx, "COMM_NO"); 			// 지점 코드
		String DIV_NO	= (String) ctx.get("DIV_NO"); 			// 그린 카드 일 때
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		StringBuffer sbQuery = new StringBuffer();
		
		
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select12Today"));		
		
		// 지점이 전체일 경우 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // 지점 코드가 전체일 경우
		{
			sbQuery.append("\n");
		}
		else if("03".equals(COMM_NO)) // 미사리 본장
		{
			param.setWhereClauseParameter(nIdx++, "003");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "003");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "003");			// 경륜경정 구분 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where02"));
			
		}		
		else if("01".equals(COMM_NO)) // 광명 본장  
		{		
			param.setWhereClauseParameter(nIdx++, "001");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "001");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "001");			// 경륜경정 구분 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where01"));
		}
		else if("06".equals(COMM_NO)) // 그린카드  
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			if(!"".equals(DIV_NO)) {
				if (!"00".equals(DIV_NO)) {
					SELL_CD = "01";		// 판매처코드
				}
			} else {
				SELL_CD = "%";			// 판매처코드:그린카드 전체인 경우 전체 sell_cd의 자료를 가져와야 함 
			}
			param.setWhereClauseParameter(nIdx++, SELL_CD);		// 판매처코드					
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// 지점 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// 그린카드
			sbQuery.append("\n");			
			
			if(!"".equals(DIV_NO))
			{
				param.setWhereClauseParameter(nIdx++, DIV_NO);			// 지점 코드
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where06"));	// 그린카드
			}
		} else if("99".equals(COMM_NO)) { // 광명 미사리 본장
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));
		}
		else   
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "01");			// 판매처코드
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// 지점 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// 마이켓, 지점
		}

		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby12Today"));
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// 화요경정 유무
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// 화요경정 유무
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select13Today"));
		
		sbQuery.append("\n");	
		if("00".equals(COMM_NO))	// 전체일 경우
		{	
			sbQuery.append("\n");
		}		
		else if("03".equals(COMM_NO)) // 미사리 본장
		{
			param.setWhereClauseParameter(nIdx++, "003");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "003");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "003");			// 경륜경정 구분 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where02"));
			
		}		
		else if("01".equals(COMM_NO)) // 광명 본장  
		{		
			param.setWhereClauseParameter(nIdx++, "001");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "001");			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "001");			// 경륜경정 구분 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where01"));
		}
		else if("06".equals(COMM_NO)) // 그린카드  
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			if(!"".equals(DIV_NO)) {
				if (!"00".equals(DIV_NO)) {
					SELL_CD = "01";		// 판매처코드
				}
			} else {
				SELL_CD = "%";			// 판매처코드:그린카드 전체인 경우 전체 sell_cd의 자료를 가져와야 함 
			}
			param.setWhereClauseParameter(nIdx++, SELL_CD);		// 판매처코드					
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// 지점 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// 그린카드
			sbQuery.append("\n");			
			
			if(!"".equals(DIV_NO))
			{
				param.setWhereClauseParameter(nIdx++, DIV_NO);			// 지점 코드
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where06"));	// 그린카드
			}
		} else if("99".equals(COMM_NO)) { // 광명 미사리 본장
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));
		}
		else   
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// 경륜경정 구분 코드
			param.setWhereClauseParameter(nIdx++, "01");			// 판매처코드
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// 지점 코드
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// 마이켓, 지점
		}
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);		// 경주일
		sbQuery.append(Util.getQuery(ctx, "rsm2011_where07"));
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);		// 경주일
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby13Today"));
            	        
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsDivSales";
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
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 1: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		param.setWhereClauseParameter(nIdx++, MEET_CD);		// 2: 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 3: 경주일
		
		param.setWhereClauseParameter(nIdx++, DIV_NO);		// 4: 투표소
        
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select03Today"));
		
		
		// 지점이 전체일 경우 
		sbQuery.append("\n");
		if("00".equals(COMM_NO)) // 지점 코드가 전체일 경우
		{
			
		}
		else
		{	
			sbQuery.append("\n");
			if("01".equals(COMM_NO)) // 광명 미사리 본장
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
