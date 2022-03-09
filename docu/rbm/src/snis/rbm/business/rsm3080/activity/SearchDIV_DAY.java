/*================================================================================
 * 시스템			:  일자별 매출액
 * 소스파일 이름	: snis.rbm.business.rsm3080.activity.SearchDIV_DAY.java
 * 파일설명		: 일자별 매출액
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-14
 * 최종수정일자	: 							   
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm3080.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchDIV_DAY extends SnisActivity {
public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchDivDay(ctx); // 데이터 검색
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchDivDay(PosContext ctx) throws Exception {

		String MEET_CD      = Util.getCtxStr(ctx, "MEET_CD"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String RACE_DAY_ST  = Util.getCtxStr(ctx, "RACE_DAY_ST");	// 검색 시작 일
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// 검색 마지막 일
		String OPT = Util.getCtxStr(ctx, "OPT"); 	// 검색 마지막 일
		
		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		//param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일
		//param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 년도용
		//param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 년도용
		
		if("MM".equals(OPT))	// 월별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_select02"));	
		}
		else	// 일자별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_select01"));
		}
		sbQuery.append(" \n");
		
//			sbQuery.append(Util.getQuery(ctx, "rsm3080_select03"));
		sbQuery.append(Util.getQuery(ctx, "rsm3080_select03_grncd_1"));
		
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// 월별
		{
//			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02"));	
			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02_grncd_1"));	
		}
		else	// 일자별
		{
//				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01"));
				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01_grncd_1"));
		}
		
		
		///////////////
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일
		//param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 년도 용
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3080_select03_grncd_2"));
		
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// 월별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02_grncd_1"));	
		}
		else	// 일자별
		{
				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01_grncd_1"));
		}
		
		///////////////
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// 월별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby02_grncd_2"));	
		}
		else	// 일자별
		{
				sbQuery.append(Util.getQuery(ctx, "rsm3080_groupby01_grncd_2"));
		}
		
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsDIVDay";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
}
