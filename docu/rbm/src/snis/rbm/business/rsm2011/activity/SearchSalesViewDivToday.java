package snis.rbm.business.rsm2011.activity;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchSalesViewDivToday extends SnisActivity{
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {

			searchCompare(ctx);	// 데이터 검색
			
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}
	
	/**
     * 지점별 매출액 비교 조회
     * 
     */
	private void searchCompare(PosContext ctx) throws Exception {
				
		String MEET_CD  = (String) ctx.get("MEET_CD"); 			// 경륜:001 ,경정:003, 창원교차:002, 부산교차:004
		String RACE_DAY = (String) ctx.get("RACE_DAY"); 		// 경주일
		String COMM_NO  = (String) ctx.get("COMM_NO"); 			// 지점 코드
		String DIV_NO	= (String) ctx.get("DIV_NO"); 			// 그린 카드 일 때
		String BRNCH_NM = (String) ctx.get("BRNCH_NM"); 		// 지점 이름
		String SELL_CD  = (String) ctx.get("SELL_CD"); 			// 판매처코드
		String TUE_MBR_YN  = (String) ctx.get("TUE_MBR_YN"); 			// 화요경정 유무
				
		PosRowSet rowset;
		String TMS = "";
		String DAY_ORD = "";
		
		rowset = getRaceInfo(RACE_DAY);
		
		if (rowset.hasNext()) {
			
			PosRow row = rowset.next();

			TMS     = String.valueOf(Integer.parseInt(row.getAttribute("TMS").toString()));
			DAY_ORD = String.valueOf(Integer.parseInt(row.getAttribute("DAY_ORD").toString()));
			
		} else {			
			// 해당일자의 경주일 정보가 없는 경우				
			throw new RuntimeException("해당일자에 대한 경주정보가 없습니다.일자 변경후 작업하세요");
		}
				
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		
		StringBuffer sbQuery = new StringBuffer();
		
		String sBrnchNm = "";
		if("00".equals(COMM_NO))
		{
			sBrnchNm = "전체";
		}		
		if("01".equals(COMM_NO)) // 광명 본장  
		{	
			sBrnchNm = "광명본장";
		}
		else if("03".equals(COMM_NO)) // 미사리
		{
			sBrnchNm = "미사리";
		}
		else
		{
			sBrnchNm = BRNCH_NM;
		}
		
		param.setWhereClauseParameter(nIdx++, TMS);		// 회차
		param.setWhereClauseParameter(nIdx++, DAY_ORD);	// 일차
		param.setWhereClauseParameter(nIdx++, RACE_DAY);// 경주일
		
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select07Today"));
		
		
		if("06".equals(COMM_NO)) // 그린카드  
		{		
			sbQuery.append(Util.getQuery(ctx, "rsm2011_from07_02Today"));		// 당일자료인 경우 그린카드DB에서 직접 조회함
		} else {
			sbQuery.append(Util.getQuery(ctx, "rsm2011_from07_01Today"));
		}
		
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
		}
		else if("99".equals(COMM_NO)) // 본장공동활용  
		{		
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));	// 
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
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby07"));
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// 화요경정 유무
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// 화요경정 유무
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// 경주일
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select08Today"));
		
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
		}
		else if("99".equals(COMM_NO)) // 본장공동활용  
		{		
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));	// 
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
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby08"));
		        
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsSalesViewDiv";
    	ctx.put(sOutDs, prs);
    	Util.addResultKey(ctx, sOutDs);    
	}
	

	/**
	 * <p>
	 * 경주일정보 조회
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	protected PosRowSet getRaceInfo(String race_day) {
		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, race_day);

		return this.getDao("rbmdao").find("rsm2010_s01", param);
	}
	
	
}
