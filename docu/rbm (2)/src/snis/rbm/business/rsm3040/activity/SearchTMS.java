/*================================================================================
 * 시스템			: 회차별 매출액
 * 소스파일 이름	: snis.rbm.business.rsm3030.activity.SearchTMS.java
 * 파일설명		: 회차별 매출액
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-13
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm3040.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchTMS extends SnisActivity {
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchTms(ctx); // 데이터 검색
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}

	private void searchTms(PosContext ctx) throws Exception {

		String MEET_CD = Util.getCtxStr(ctx, "MEET_CD"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String RACE_DAY_ST = Util.getCtxStr(ctx, "RACE_DAY_ST");	// 검색 시작 일
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// 검색 마지막 일
		String COMM_NO = Util.getCtxStr(ctx,"COMM_NO"); // 지점 코드
		String GREEN_YN = Util.getCtxStr(ctx,"GREEN_YN"); // 지점 코드

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();

		sbQuery.append(Util.getQuery(ctx, "rsm3040_select01"));
		param.setWhereClauseParameter(nIdx++, MEET_CD); // 경륜:001
														// ,경정:003,창원교차:002,
														// 부산교차:004
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
				
		if ("00".equals(COMM_NO)) // 전체 선택일 경우
		{	
			sbQuery.append("\n");
			sbQuery.append(Util.getQuery(ctx, "rsm3040_from02"));
		} 
		else 
		{
			sbQuery.append("\n");
			if ("Y".equals(GREEN_YN)) {
				sbQuery.append(Util.getQuery(ctx, "rsm3040_from03"));
			} else {
				sbQuery.append(Util.getQuery(ctx, "rsm3040_from01"));
			}
			
			
			if ("01".equals(COMM_NO)) { // 광명/미사리 본장
				param.setWhereClauseParameter(nIdx++, MEET_CD); 
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where01"));
			} else if ("29".equals(COMM_NO)) { //  교차
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where02"));
			} else if ("98".equals(COMM_NO) || "99".equals(COMM_NO)) {	// 미사리/광명 공동활용
				param.setWhereClauseParameter(nIdx++, MEET_CD);			 // 경륜 경정
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where04"));	
			} else { // 지점
				param.setWhereClauseParameter(nIdx++, COMM_NO); // 지점 코드
				sbQuery.append(Util.getQuery(ctx, "rsm3040_where03"));
			}
		}

		sbQuery.append("\n");

		sbQuery.append(Util.getQuery(ctx, "rsm3040_groupby01"));

		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),
				param);
		String sOutDs = "dsTMS";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
	}
}
