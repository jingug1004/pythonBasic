/*================================================================================
 * 시스템			: 지점별 매출 건수
 * 소스파일 이름	: snis.rbm.business.rsm3060.activity.SearchDIVTMSCNT.java
 * 파일설명		: 회차별 매출액
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-14
 * 최종수정일자	: 							   
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm3060.activity;
import java.util.Calendar;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class SearchDivTmsManCnt extends SnisActivity {
	public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			searchDivTmsCnt(ctx); // 데이터 검색
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchDivTmsCnt(PosContext ctx) throws Exception {

		String MEET_CD      = Util.getCtxStr(ctx, "MEET_CD"); 		// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String RACE_DAY_ST  = Util.getCtxStr(ctx, "RACE_DAY_ST");	// 검색 시작 일
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// 검색 마지막 일
		

		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
		
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, MEET_CD);
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일

		sbQuery.append(Util.getQuery(ctx, "rsm3060_select02"));

		// 기준년도 값 이런식으로 값 안넣으면 멈춤 
		sbQuery.append(" \n");
		sbQuery.append(" AND V.STND_YEAR = SUBSTR( '"+RACE_DAY_ST+"' ,1,4)	-- 4:RACE_DAY_ST 기준년도  \n");
		sbQuery.append(" AND T.STND_YEAR = SUBSTR( '"+RACE_DAY_ST+"' ,1,4)	-- 5:RACE_DAY_ST 검색 기간 시작 일 \n");
		
		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm3060_groupby02"));
		
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		
		String sOutDs = "dsDivTmsManCnt";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
}
