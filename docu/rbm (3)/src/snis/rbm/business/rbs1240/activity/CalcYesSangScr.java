/*================================================================================
 * 시스템			: 예상지 적중률 관리
 * 소스파일 이름	: snis.rbm.business.rbs1210.activity.SaveYesCom.java
 * 파일설명		: 예상결과 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 원작자			: 윤기돈 (인트라넷의 RaceByDAO.java)
 * 생성일자		: 2014-09-06
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1240.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class CalcYesSangScr extends SnisActivity {
		static final int _AB  = 6;
		static final int _BA  = 3;
		static final int _AC  = 3;
		static final int _ABC = 3;
		static final int _EX_A1 = 6;
		static final int _EX_A2 = 3;
		static final int _EX_A3 = 2;
		static final int _EX_AB1 = 6;
		static final int _EX_AB2 = 3;
		static final int _EX_AB3 = 2;
		static final int _EX_AC1 = 3;
		static final int _EX_AC2 = 2;
		static final int _EX_AC3 = 1;
		static final int _EX_BA1 = 3;
		static final int _EX_BA2 = 2;
		static final int _EX_BA3 = 1;
		static final int _RT_AB10 = 10;
		static final int _RT_AB20 = 15;
		static final int _RT_AB30 = 20;
		static final int _RT_BA10 = 5;
		static final int _RT_BA20 = 10;
		static final int _RT_BA30 = 15;
		
		public CalcYesSangScr(){}
		
		/**
	     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
	     * @param   ctx		PosContext	저장소
	     * @return  SUCCESS	String		sucess 문자열
	     * @throws  none
	     */    
	    public String runActivity(PosContext ctx)
	    {	   	
	    	// 사용자 정보 확인
	    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
	    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
	            return PosBizControlConstants.SUCCESS;
	    	}
		        
	        saveState(ctx);

	        return PosBizControlConstants.SUCCESS;
	    }

	    /**
	    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	    * @param   ctx		PosContext	저장소
	    * @return  none
	    * @throws  none
	    */
	    protected void saveState(PosContext ctx) 
	    {
	    	int nSaveCount   = 0; 
	    	int nDeleteCount = 0;
	    	String sRet = "";
	    	    	

			ArrayList <PosRow> arrRaceRslt, arrYesSang;
	        
	        /* 처리절차
	         1. 조회 조건에 해당하는 경주결과를 조회한다.(순위, 복/쌍승 배당률)
	         2. 조회 조건에 해당하는 참여업체에 예상내역을 조회한다.
	         3. 경주별로 LOOP : 경주별로 점수를 산정한다.
	           3-1) 업체별로 적중점수를 계산
	           3-2) 승식별 적중업체를 카운트 한다.
	           3-3) 승식별 적중업체수에 따른 가산점수를 계산
	           3-4) 배당율에 따른 가산점수 계산
	         4.계산된 점수를 DB에 저장한다.
	        */   
			arrRaceRslt = selectRaceInfo(ctx);		// 경주결과 조회
			arrYesSang  = selectYesSang(ctx);		// 예상지 예상내역 조회
			
			sRet = calcScrYesSAng(ctx, arrRaceRslt, arrYesSang);	// 적중점수 계산
			
			if (sRet.equals(PosBizControlConstants.SUCCESS)) {
				nSaveCount = SaveCalcResult(ctx, arrYesSang);		// 계산 결과 저장
			}
			
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

		/**
		* <p> 경주결과 조회 </p>
		* @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
		* @return  dmlcount int		update 레코드 개수
		* @throws  none
		*/
		protected ArrayList <PosRow> selectRaceInfo(PosContext ctx)
		{			

	    	String sMeetCd   = (String)ctx.get("MEET_CD");
	    	String sStndYear = (String)ctx.get("STND_YEAR");
	    	String sTmsFrom  = (String)ctx.get("TMS_FROM");
	    	String sTmsTo    = (String)ctx.get("TMS_TO");	
	    	
	    	PosRowSet rowset;
	    	PosParameter param = new PosParameter();
	        int i = 0;

			i = 0;
			param.setWhereClauseParameter(i++, sMeetCd);
			param.setWhereClauseParameter(i++, sStndYear);
			param.setWhereClauseParameter(i++, sTmsFrom);
			param.setWhereClauseParameter(i++, sTmsTo);

			rowset = this.getDao("rbmdao").find("rbs1240_s02", param);
			
			ArrayList <PosRow> arrRslt = new ArrayList<PosRow>();				
			while(rowset.hasNext())
			{
				PosRow row = rowset.next();
				arrRslt.add(row);
			}
	        return arrRslt;
	    }


	    /**
	     * <p> 예상내역 조회 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected  ArrayList <PosRow> selectYesSang(PosContext ctx)
	    {			

	    	String sMeetCd   = (String)ctx.get("MEET_CD");
	    	String sStndYear = (String)ctx.get("STND_YEAR");
	    	String sTmsFrom  = (String)ctx.get("TMS_FROM");
	    	String sTmsTo    = (String)ctx.get("TMS_TO");	
	    	
	    	PosRowSet rowset;
	    	PosParameter param = new PosParameter();
	        int i = 0;

			i = 0;
			param.setWhereClauseParameter(i++, sMeetCd);
			param.setWhereClauseParameter(i++, sStndYear);
			param.setWhereClauseParameter(i++, sTmsFrom);
			param.setWhereClauseParameter(i++, sTmsTo);

			rowset = this.getDao("rbmdao").find("rbs1240_s03", param);

			ArrayList <PosRow> arrYeSang = new ArrayList<PosRow>();				
			while(rowset.hasNext())
			{
				PosRow row = rowset.next();
				arrYeSang.add(row);
			}
	        return arrYeSang;
	    }

	    /**
	     * <p> 경주별 점수 계산 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected String calcScrYesSAng(PosContext ctx, ArrayList <PosRow> arrRslt, ArrayList <PosRow> arrYeSang)
	    {			
	    	/*
	         3. 경주별로 LOOP : 경주별로 점수를 산정한다.
	           3-0) 결과 존재 여부 체크
	           3-1) 업체별로 적중점수를 계산 및 적중업체 카운트
	           3-2) 승식별 적중업체수 및 배당율에 따른 가산점수 계산
	        */
		       
	    	String arrRank[] = new String[3];	// 순위 정보
	    	int    arrHitCnt[] = new int[4];	// 적중업체수를 저장
	    	
	    	float  nExRate, nQuRate;	// 쌍승 및 복승의 배당률
	    	PosRow rowRslt, rowYeSang;
	    	String sFuncResult;		// 함수의 결과값
	    	String sHitCnt = "";	//업체의 적중건수를 문자열로 저장
	    	String sExclusion = "N";
	    	
	    	for (int iCnt=0;iCnt<arrRslt.size();iCnt++) {		// 경주별로 LOOP 돌면서 점수를 계산함  ********************************************
	    	
	    		clearStr(arrRank);		// 문자열 배열변수 초기화
	    		clearint(arrHitCnt);	// 숫자    배열변수 초기화
	    		
	    		rowRslt = arrRslt.get(iCnt);
	    		
	    		// 3-0) 결과 존재 여부 체크
	    		sFuncResult = checkRaceResult(ctx, rowRslt); 	// 동착 체크(하위 순위에 자료 복사)
	    		if (PosBizControlConstants.FAILURE.equals(sFuncResult)) return PosBizControlConstants.FAILURE;	// 결과 없으면 종료
	    		
	    		arrRank[0] = rowRslt.getAttribute("RANK_1").toString();						// 1등 정번
	    		arrRank[1] = rowRslt.getAttribute("RANK_2").toString();						// 2등 정번
	    		arrRank[2] = rowRslt.getAttribute("RANK_3").toString();						// 3등 정번
	    		nExRate = Float.parseFloat(rowRslt.getAttribute("EX_RATE").toString());		// 쌍승 배당률
	    		nQuRate = Float.parseFloat(rowRslt.getAttribute("QU_RATE").toString());		// 복승 배당률
	    		
	    		// 경주취소여부 ..결장 여부 체크
	    		if (nExRate == 1.0 && arrRank[0] == null) sExclusion = "Y";
	    		else                                      sExclusion = "N";
	    		
	    		// 3-1) 업체별 적중점수 계산
	    		for (int jCnt=0;jCnt<arrYeSang.size();jCnt++) {
	    			rowYeSang = arrYeSang.get(jCnt);
	    			if (rowRslt.getAttribute("MEET_CD").toString().equals(rowYeSang.getAttribute("MEET_CD").toString()) &&
    					rowRslt.getAttribute("STND_YEAR").toString().equals(rowYeSang.getAttribute("STND_YEAR").toString()) &&
    					rowRslt.getAttribute("TMS").toString().equals(rowYeSang.getAttribute("TMS").toString()) &&
    					rowRslt.getAttribute("DAY_ORD").toString().equals(rowYeSang.getAttribute("DAY_ORD").toString()) &&
    					rowRslt.getAttribute("RACE_NO").toString().equals(rowYeSang.getAttribute("RACE_NO").toString())     	) {
	    				
	    	    		initYesSangScore(rowYeSang);						// 모든 점수를 0으로 초기화
	    	    		
	    				// 적중점수 계산 및 적중업체 Counting
	    				setScore(ctx, arrRank, arrHitCnt, rowYeSang);
	    			}
	    		}
	    		
	    		sHitCnt = "";
	    		for (int i=0;i<arrHitCnt.length;i++) {			// 적중업체수를 "2,2,1,2" 형식의 문자열로 변환
	    			if (i > 0) sHitCnt = sHitCnt.concat(","); 
	    			sHitCnt = sHitCnt.concat(String.valueOf(arrHitCnt[i]));
	    		}
	    		
	    		//3-2) 승식별 적중업체수 및 배당율에 따른 가산점수 계산
	    		for (int jCnt=0;jCnt<arrYeSang.size();jCnt++) {	
	    			rowYeSang = arrYeSang.get(jCnt);
	    			if (rowRslt.getAttribute("MEET_CD").toString().equals(rowYeSang.getAttribute("MEET_CD").toString()) &&
    					rowRslt.getAttribute("STND_YEAR").toString().equals(rowYeSang.getAttribute("STND_YEAR").toString()) &&
    					rowRslt.getAttribute("TMS").toString().equals(rowYeSang.getAttribute("TMS").toString()) &&
    					rowRslt.getAttribute("DAY_ORD").toString().equals(rowYeSang.getAttribute("DAY_ORD").toString()) &&
    					rowRslt.getAttribute("RACE_NO").toString().equals(rowYeSang.getAttribute("RACE_NO").toString())     	) {
	    				
	    				//3-2-1) 축가산점을 맞춘 경우
	    				System.out.println("dfkdjl EX_A:"+Integer.parseInt(rowYeSang.getAttribute("EX_A").toString()));	    				
	    				System.out.println("dfkdjl arrHitCnt[0]:"+arrHitCnt[0]);	    				
	    				if (Integer.parseInt(rowYeSang.getAttribute("EX_A").toString()) == 1.0) {
	    					if      (arrHitCnt[0] == 1) rowYeSang.setAttribute("EX_A", _EX_A1); 
	    					else if (arrHitCnt[0] == 2) rowYeSang.setAttribute("EX_A", _EX_A2);
	    					else if (arrHitCnt[0] == 3) rowYeSang.setAttribute("EX_A", _EX_A3);
	    				}
	    				//3-2-2) A→B 적중한 경우
	    				if (Integer.parseInt(rowYeSang.getAttribute("AB").toString()) > 0) {
	    					// 업체적중수에 따른 가산점
	    					if      (arrHitCnt[1] == 1) rowYeSang.setAttribute("EX_AB", _EX_AB1); 
	    					else if (arrHitCnt[1] == 2) rowYeSang.setAttribute("EX_AB", _EX_AB2);
	    					else if (arrHitCnt[1] == 3) rowYeSang.setAttribute("EX_AB", _EX_AB3);
	    					
	    					// 배당률에 따른 가산점
	    					if      (nExRate >= 10.0 && nExRate < 20.0) rowYeSang.setAttribute("RT_AB", _RT_AB10); 
	    					else if (nExRate >= 20.0 && nExRate < 30.0) rowYeSang.setAttribute("RT_AB", _RT_AB20); 
	    					else if (nExRate >= 30.0                  ) rowYeSang.setAttribute("RT_AB", _RT_AB30); 
	    					
	    				}
	    				//3-2-3) A→C 적중한 경우
	    				if (Integer.parseInt(rowYeSang.getAttribute("AC").toString()) > 0) {
	    					if      (arrHitCnt[2] == 1) rowYeSang.setAttribute("EX_AC", _EX_AC1); 
	    					else if (arrHitCnt[2] == 2) rowYeSang.setAttribute("EX_AC", _EX_AC2);
	    					else if (arrHitCnt[2] == 3) rowYeSang.setAttribute("EX_AC", _EX_AC3); 
	    				}
	    				//3-2-4) B→A 적중한 경우
	    				if (Integer.parseInt(rowYeSang.getAttribute("BA").toString()) > 0) {
	    					// 업체적중수에 따른 가산점
	    					if      (arrHitCnt[3] == 1) rowYeSang.setAttribute("EX_BA", _EX_BA1); 
	    					else if (arrHitCnt[3] == 2) rowYeSang.setAttribute("EX_BA", _EX_BA2);
	    					else if (arrHitCnt[3] == 3) rowYeSang.setAttribute("EX_BA", _EX_BA3); 
	    					
	    					// 배당률에 따른 가산점
	    					if      (nQuRate >= 10.0 && nExRate < 20.0) rowYeSang.setAttribute("RT_AB", _RT_BA10); 
	    					else if (nQuRate >= 20.0 && nExRate < 30.0) rowYeSang.setAttribute("RT_AB", _RT_BA20); 
	    					else if (nQuRate >= 30.0                  ) rowYeSang.setAttribute("RT_AB", _RT_BA30); 
	    				}
	    				
	    				// 
	    				rowYeSang.setAttribute("ETC",       sHitCnt);			//적중업체수 저장				
	    				rowYeSang.setAttribute("RATE",      nExRate);			//쌍승 배당률
	    				rowYeSang.setAttribute("RATE_QU",   nQuRate);			//복승 배당률
	    				rowYeSang.setAttribute("EXCLUSION", sExclusion);		//결장 여부
	    				
	    			}
	    		}
	    	}				// 경주별로 LOOP End  ********************************************
	        return PosBizControlConstants.SUCCESS;
	    }

	    /**
	     * <p> 적중점수 및 가산점 초기화 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected String initYesSangScore(PosRow row)
	    {	
	    	row.setAttribute("AB",    0);		//A→B
	    	row.setAttribute("AC",    0);		//A→C
	    	row.setAttribute("BA",    0);		//B→A
	    	row.setAttribute("ABC",   0);		//삼복승
	    	row.setAttribute("EX_A",  0);		//A축 가산점
	    	row.setAttribute("EX_AB", 0);		//A→B 가산점
	    	row.setAttribute("EX_AC", 0);		//A→C 가산점
	    	row.setAttribute("EX_BA", 0);		//B→A 가산점
	    	row.setAttribute("RT_AB", 0);		//A→B 배당률 가산점
	    	row.setAttribute("RT_BA", 0);		//B→A 배당률 가산점
	    	
	    	row.setAttribute("RATE",  0);		//쌍승 배당률
	    	row.setAttribute("RATE_QU", 0);		//복승 배당률
	    	row.setAttribute("ETC",  "");		//적중업체수
	    	row.setAttribute("EXCLUSION", "N");		//결장 여부
	    	row.setAttribute("AUTO", "Y");		//결장 여부
	    	
    		return PosBizControlConstants.SUCCESS;
	    }


	    /**
	     * <p> 경주결과 및 동착 체크 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected String checkRaceResult(PosContext ctx, PosRow row)
	    {		

	    	String sRank1, sRank2, sRank3;
	    	//float  nExRate, nQuRate;
	    	
    		sRank1 = row.getAttribute("RANK_1").toString();						// 1등 정번
    		sRank2 = row.getAttribute("RANK_2").toString();						// 2등 정번
    		sRank3 = row.getAttribute("RANK_3").toString();						// 3등 정번
    		//nExRate = Float.parseFloat(row.getAttribute("EX_RATE").toString());	// 쌍승 배당률
    		//nQuRate = Float.parseFloat(row.getAttribute("QU_RATE").toString());	// 복승 배당률
    		
    		if ("-".equals(sRank1)) {	// 결과가 입력되지 않았음
    			Util.setSvcMsgCode(ctx, "SNIS_RBM_E029");	// 경주결과 미존재
                return PosBizControlConstants.FAILURE;
    		}
    		if ("-".equals(sRank2)) {	//2위 동착
    			row.setAttribute("RANK_2", sRank1);
    			sRank2 = sRank1;
    		}
    		if ("-".equals(sRank3)) {	//3위 동착
    			row.setAttribute("RANK_3", sRank2);
    		}
    		
    		return PosBizControlConstants.SUCCESS;
	    }


	    /**
	     * <p> 업체별 적중점수 계산 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected String setScore(PosContext ctx, String[] aRank, int[] aHitCnt, PosRow row)
	    {		
	    	String sYesang = "";	    	
	    	sYesang = row.getAttribute("YESANG").toString();
	    	
	    	if (sYesang == null || sYesang.length() < 4) {
	    		Util.setSvcMsgCode(ctx, "SNIS_RBM_E030");	// 경주결과 미존재
                return PosBizControlConstants.FAILURE;
	    	}
	    	String a = sYesang.substring(0, 1);
	    	String b = sYesang.substring(1, 2);
	    	String c = sYesang.substring(2, 3);
	    	
    		// A→B
	    	if (aRank[0].contains(a) && aRank[1].contains(b)) {
    			row.setAttribute("AB", _AB);
    			aHitCnt[1] ++;
    		} else if (aRank[0].contains(b) && aRank[1].contains(a)) {
    			row.setAttribute("BA", _BA);
    			aHitCnt[3] ++;
    		} else if (aRank[0].contains(a) && aRank[1].contains(c)) {
    			row.setAttribute("AC", _AC);
    			aHitCnt[2] ++;
    		}
	    	
	    	if ((aRank[0]+aRank[1]+aRank[2]).contains(a) && 
	    		(aRank[0]+aRank[1]+aRank[2]).contains(b) && 
	    		(aRank[0]+aRank[1]+aRank[2]).contains(c))   {
	    		row.setAttribute("ABC", _ABC);
	    	}

	    	if (aRank[0].contains(a)) {	// 축 적중 여부
	    		row.setAttribute("EX_A", 1);
    			aHitCnt[0] ++;
	    	} 
    		
	    	return PosBizControlConstants.SUCCESS;
	    }

	    /**
	     * <p> 점수계산결과 저장 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int SaveCalcResult(PosContext ctx, ArrayList <PosRow> arrYesSang)
	    {		
	    	Connection conn = null;
	    	conn = getDao("rbmdao").getDBConnection();
	    	int i = 0;
	    	int dmlCount = 0;
	    	
	    	PreparedStatement stmt = null;
	    	String sqlStr = Util.getQuery(ctx, "rbs1240_u01");
	    	
	    	try {
	    		stmt = conn.prepareStatement(sqlStr);
	    		for (int rCnt=0;rCnt<arrYesSang.size();rCnt++) {
	    			PosRow row = arrYesSang.get(rCnt);
	    			stmt.clearParameters();
	    			i= 1;
	    			stmt.setObject(i++, row.getAttribute("AB").toString());
	    			stmt.setObject(i++, row.getAttribute("AC").toString());
	    			stmt.setObject(i++, row.getAttribute("BA").toString());
	    			stmt.setObject(i++, row.getAttribute("CA").toString());
	    			stmt.setObject(i++, row.getAttribute("ABC").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_AB").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_ABC").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_AC").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_A").toString());
	    			stmt.setObject(i++, row.getAttribute("AUTO").toString());
	    			stmt.setObject(i++, row.getAttribute("RATE").toString());
	    			stmt.setObject(i++, row.getAttribute("RATE_QU").toString());
	    			stmt.setObject(i++, row.getAttribute("ETC").toString());
	    			stmt.setObject(i++, row.getAttribute("EXCLUSION").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_BA").toString());
	    			stmt.setObject(i++, row.getAttribute("RT_AB").toString());
	    			stmt.setObject(i++, row.getAttribute("RT_BA").toString());
	    			stmt.setObject(i++, SESSION_USER_ID);
	    			stmt.setObject(i++, row.getAttribute("YES_COM_SEQ").toString());
	    			stmt.setObject(i++, row.getAttribute("MEET_CD").toString());
	    			stmt.setObject(i++, row.getAttribute("STND_YEAR").toString());
	    			stmt.setObject(i++, row.getAttribute("TMS").toString());
	    			stmt.setObject(i++, row.getAttribute("DAY_ORD").toString());
	    			stmt.setObject(i++, row.getAttribute("RACE_NO").toString());
	    			
	    			stmt.addBatch();
	    		}
		    	stmt.executeBatch();
		    	dmlCount = stmt.getUpdateCount();
		    	stmt.close();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    	return dmlCount;
	    }
	    
	    // 문자열 배열변수 초기화
	    private String[] clearStr(String a[]) {
	    	for (int i=0;i<a.length;i++) {
	    		a[i] = "";
	    	}
	    	return a;
	    }
	    // 숫자 배열변수 초기화
	    private int[] clearint(int a[]) {
	    	for (int i=0;i<a.length;i++) {
	    		a[i] = 0;
	    	}
	    	return a;
	    }
}
