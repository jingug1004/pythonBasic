/*================================================================================
 * 시스템			:  금액구간별 발매
 * 소스파일 이름	: snis.rbm.business.rsm3190.activity.SearchSellstDetail.java
 * 파일설명		: 금액구간별 발매
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-14
 * 최종수정일자	: 							   
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm3190.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchSellstDetail extends SnisActivity {
public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			SearchSellstDetail(ctx); // 데이터 검색
		} catch (Exception e) {
			Util.setSvcMsg(ctx, e.getMessage());
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void SearchSellstDetail(PosContext ctx) throws Exception {
		String WIN_TYPE		= Util.getCtxStr(ctx, "WIN_TYPE");		// 발매기 종류
		String MEET_CD		= Util.getCtxStr(ctx, "MEET_CD");		// 경륜 경정
		String GROUP_OPT	= Util.getCtxStr(ctx, "GROUP_OPT");		// 옵션
		String RACE_DAY_ST = Util.getCtxStr(ctx, "RACE_DAY_ST");	// 검색 시작 일
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// 검색 마지막 일
		String COMM_NO		= Util.getCtxStr(ctx, "COMM_NO"); 		// 지점 코드
				

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, WIN_TYPE); 	// 그린카드 여부(0:전체,1:현금,2:그린카드)
		param.setWhereClauseParameter(nIdx++, MEET_CD); 	// 경륜 경정
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일
		
		// SELECT
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3190_select01"));
		
		sbQuery.append(" \n");
		if("TMS".equals(GROUP_OPT))	// 회차별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select02"));	
		}
		else if("RACE_NO".equals(GROUP_OPT))	// 경주별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select03"));	
		}
		else if("RACE_DAY".equals(GROUP_OPT))	// 일자별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select04"));	
		}
		else if("BRNC".equals(GROUP_OPT))	// 지점별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_select04_1"));	
		}
		
		// SELECT 
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3190_select05"));
		
		//  FROM
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3190_from01"));
		
		
		// WHERE

		if(!"00".equals(COMM_NO))
		{
			sbQuery.append(" \n");
			if("01".equals(COMM_NO))	// 본장
			{
				param.setWhereClauseParameter(nIdx++, MEET_CD); // 경륜 경정
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where01"));
			}
			else if ("29".equals(COMM_NO))	// 교차
			{
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where02"));	
			}
			else if ("98".equals(COMM_NO) || "99".equals(COMM_NO))	// 미사리/광명 공동활용
			{
				param.setWhereClauseParameter(nIdx++, MEET_CD); // 경륜 경정
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where05"));	
			}
			else		// 지점별
			{
				param.setWhereClauseParameter(nIdx++, COMM_NO); // 지점 코드
				sbQuery.append(Util.getQuery(ctx, "rsm3190_where03"));
			}
		
		}
		
		
		// GROUP BY 
		sbQuery.append(" \n");
		if("TMS".equals(GROUP_OPT))	// 회차별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby01"));	
		}
		else if("RACE_NO".equals(GROUP_OPT))	// 경주별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby02"));	
		}
		else if("RACE_DAY".equals(GROUP_OPT))	// 일자별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby03"));	
		}
		else if("BRNC".equals(GROUP_OPT))	// 지점별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3190_groupby03_1"));	
		}
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsSellst";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
		
		
	}
}
