/*================================================================================
 * 시스템			:  개인별 발매
 * 소스파일 이름	: snis.rbm.business.rsm3120.activity.SearchTelmpTeller.java
 * 파일설명		: 개인별 발매
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-14
 * 최종수정일자	: 							   
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm3120.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchTelmpTeller extends SnisActivity {
	public String runActivity(PosContext ctx) {
		
		String sRet = PosBizControlConstants.SUCCESS;
		try {			
			String MODE	= Util.getCtxStr(ctx, "MODE");		// 경륜 경정
			if("VIEW".equals(MODE) )
			{
				// 개인별 발매  View
				searchTelmpTellerView(ctx); // 구분 검색 클릭시 데이터 검색 
			}
			else
			{
				searchTelmpTeller(ctx); // 데이터 검색	
			}
		} catch (Exception e) {
			Util.setSvcMsg(ctx, e.getMessage());
			logger.logError(e.getMessage());
		}
		
		return sRet;
	}

	private void searchTelmpTeller(PosContext ctx) throws Exception {
		String MEET_CD_001	= Util.getCtxStr(ctx, "MEET_CD_001");		// 광명경륜
		String MEET_CD_002	= Util.getCtxStr(ctx, "MEET_CD_002");		// 창원경륜
		String MEET_CD_003	= Util.getCtxStr(ctx, "MEET_CD_003");		// 경정(미사리)
		String MEET_CD_004	= Util.getCtxStr(ctx, "MEET_CD_004");		// 부산경륜
		//String OPTION	= Util.getCtxStr(ctx, "OPTION");		// 옵션 (교차포함)
		String TABLE_NM = Util.getCtxStr(ctx, "TABLE_NM");		// 경주권, 구매권
		String TYPE		= Util.getCtxStr(ctx, "TYPE");			// 발매원별, 창구별
		
		String RACE_DAY_ST	= Util.getCtxStr(ctx, "RACE_DAY_ST");	// 검색 시작 일
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// 검색 마지막 일		
		String COMM_NO		= Util.getCtxStr(ctx, "COMM_NO"); 		// 지점 코드
				


		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
				
		// SELECT
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select01"));
		
		sbQuery.append(" \n");
		if("TELLER_ID".equals(TYPE))
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select02"));
		}
		else
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select03"));
		}
		
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select04"));
		
		
		// FROM
		sbQuery.append(" \n");
		if("TBJI_PC_TELMP".equals(TABLE_NM))	// 경주권
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from01"));	
		}
		else	// 창구별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from02"));
		}
		
		
		// WHERE		
		param.setWhereClauseParameter(nIdx++, MEET_CD_001); // 광명경륜
		param.setWhereClauseParameter(nIdx++, MEET_CD_002); // 광명경륜
		param.setWhereClauseParameter(nIdx++, MEET_CD_003); // 광명경륜
		param.setWhereClauseParameter(nIdx++, MEET_CD_004); // 광명경륜
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_where01"));
		
		if(!"00".equals(COMM_NO))// 전체선택이 아닐 경우
		{
			sbQuery.append(" \n");
			if("01".equals(COMM_NO)) {
				param.setWhereClauseParameter(nIdx++, MEET_CD_001); // 광명경륜
				param.setWhereClauseParameter(nIdx++, MEET_CD_002); // 창원경륜
				param.setWhereClauseParameter(nIdx++, MEET_CD_003); // 경정(미사리)
				param.setWhereClauseParameter(nIdx++, MEET_CD_004); // 부산경륜
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where02"));
			} else if("29".equals(COMM_NO)) { // 교차 
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where03"));
			} else if("98".equals(COMM_NO)) { // 미사리 
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where05"));
			} else if("99".equals(COMM_NO)) { // 광명 
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where06"));
			} else {
				param.setWhereClauseParameter(nIdx++, COMM_NO); // 지점코드
				sbQuery.append(Util.getQuery(ctx, "rsm3120_where04"));
			}
		}
		
		
		if("TELLER_ID".equals(TYPE))	// 발매원별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_groupby01"));	
		}
		else	// 창구별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_groupby02"));
		}
		
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsTelmpTeller";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
	
	
	// 개인별 발매  View 
	private void searchTelmpTellerView(PosContext ctx) throws Exception {
		
		String MEET_CD_001	= Util.getCtxStr(ctx, "MEET_CD_001");		// 광명경륜
		String MEET_CD_002	= Util.getCtxStr(ctx, "MEET_CD_002");		// 창원경륜
		String MEET_CD_003	= Util.getCtxStr(ctx, "MEET_CD_003");		// 경정(미사리)
		String MEET_CD_004	= Util.getCtxStr(ctx, "MEET_CD_004");		// 부산경륜
		String TABLE_NM = Util.getCtxStr(ctx, "TABLE_NM");		// 경주권, 구매권
		String TYPE		= Util.getCtxStr(ctx, "TYPE");			// 발매원별, 창구별		
		String RACE_DAY_ST	= Util.getCtxStr(ctx, "RACE_DAY_ST");	// 검색 시작 일
		String RACE_DAY_END = Util.getCtxStr(ctx, "RACE_DAY_END"); 	// 검색 마지막 일
		String TELLER_ID	= Util.getCtxStr(ctx, "TELLER_ID"); 		// 발매원 번호
		String COMM_NO		= Util.getCtxStr(ctx, "COMM_NO"); 		// 지점 코드
		
		PosRowSet prs = null;

		PosParameter param = new PosParameter();
		int nIdx = 0;

		StringBuffer sbQuery = new StringBuffer();
				
		// SELECT
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select01"));
		
		sbQuery.append(" \n");
		if("TELLER_ID".equals(TYPE))
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select25"));
		}
		else
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_select26"));
		}
		
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_select27"));
		
		
		// FROM
		sbQuery.append(" \n");
		System.out.println("TABLE_NM="+TABLE_NM);		
		System.out.println("TYPE="+TYPE);		
		if("TBJI_PC_TELMP".equals(TABLE_NM))	// 발매원별
		//if("TELLER_ID".equals(TYPE))	// 발매원별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from01"));	
		}
		else	// 창구별
		{
			sbQuery.append(Util.getQuery(ctx, "rsm3120_from02"));
		}
		
		
		// WHERE
		
		param.setWhereClauseParameter(nIdx++, MEET_CD_001); // 광명경륜
		param.setWhereClauseParameter(nIdx++, MEET_CD_002); // 광명경륜
		param.setWhereClauseParameter(nIdx++, MEET_CD_003); // 광명경륜
		param.setWhereClauseParameter(nIdx++, MEET_CD_004); // 광명경륜
		param.setWhereClauseParameter(nIdx++, RACE_DAY_ST); // 검색 시작 일
		param.setWhereClauseParameter(nIdx++, RACE_DAY_END); // 검색 마지막 일
		sbQuery.append(" \n");
		sbQuery.append(Util.getQuery(ctx, "rsm3120_where01"));

		// 세부내역 전체조회인 경우에만 VIEW에서 지점코드가 넘어옴.
		if (COMM_NO.trim().length() > 1) {
			sbQuery.append(" \n");		
			if(!"00".equals(COMM_NO))// 전체선택이 아닐 경우
			{
				sbQuery.append(" \n");
				if("01".equals(COMM_NO))
				{
					param.setWhereClauseParameter(nIdx++, MEET_CD_001); // 광명경륜
					param.setWhereClauseParameter(nIdx++, MEET_CD_003); // 경정(미사리)
					sbQuery.append(Util.getQuery(ctx, "rsm3120_where02"));
				}
				else if("29".equals(COMM_NO)) // 교차 
				{				
					sbQuery.append(Util.getQuery(ctx, "rsm3120_where03"));
				}
				else 
				{
					param.setWhereClauseParameter(nIdx++, COMM_NO); // 지점코드
					sbQuery.append(Util.getQuery(ctx, "rsm3120_where04"));
				}
			}
		}
		
		sbQuery.append(" \n");
		if("TELLER_ID".equals(TYPE))	// 발매원별
		{
			param.setWhereClauseParameter(nIdx++, TELLER_ID); 	// 발매원
			sbQuery.append(Util.getQuery(ctx, "rsm3120_where25"));	
		}
		else	// 창구별
		{
			param.setWhereClauseParameter(nIdx++, TELLER_ID); 	// 창구
			sbQuery.append(Util.getQuery(ctx, "rsm3120_where26"));
		}
		
		
		
				
		prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(),param);
		String sOutDs = "dsTelmpTeller";
		ctx.put(sOutDs, prs);
		Util.addResultKey(ctx, sOutDs);
		
	}
	
}
