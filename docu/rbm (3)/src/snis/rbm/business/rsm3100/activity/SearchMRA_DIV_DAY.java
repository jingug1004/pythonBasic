/*================================================================================
 * 시스템			:  경정교차 일자별 
 * 소스파일 이름	: snis.rbm.business.rsm3100.activity.SearchMRA_DIV_DAY.java
 * 파일설명		: 경정교차 일자별 
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-14
 * 최종수정일자	: 							   
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm3100.activity;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchMRA_DIV_DAY extends SnisActivity {
	public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchMRADivDay(ctx); // 데이터 검색
		} catch (Exception e) {
			Util.setSvcMsg(ctx, e.getMessage());
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchMRADivDay(PosContext ctx) throws Exception {		
		String RACE_DAY_ST  = Util.getCtxStr(ctx, "RACE_DAY_ST");	// 검색 시작 일
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// 검색 마지막 일
		String OPT          = Util.getCtxStr(ctx, "OPT"); 			// 경륜구분코드
		String VALID_YN     = Util.getCtxStr(ctx, "VALID_YN"); 		// 검증자료 유무("1":미검증자료)
		

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		
		
		if("MM".equals(OPT))	// 월별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select02"));	
		}
		else	// 일자별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select01"));
		}
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3100_select03"));
		
		sbQuery.append(" \n");
		if ("1".equals(VALID_YN)) {
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select04Today"));
		} else {
			sbQuery.append(Util.getQuery(ctx, "rsm3100_select04"));
		}
		
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3100_select05"));
		
		sbQuery.append(" \n");
		if("MM".equals(OPT))	// 월별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_groupby02"));	
		}
		else	// 일자별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3100_groupby01"));
		}
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsMRADIVDay";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
		
		
	}
}
