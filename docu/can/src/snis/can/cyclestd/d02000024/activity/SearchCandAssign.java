/*================================================================================
 * 시스템			: 편성관리관리
 * 소스파일 이름	: snis.can.cyclestd.d02000024.activity.SearchCandAssign.java
 * 파일설명		: 후보생배정
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2007-03-20
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000024.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SearchCandAssign  extends SnisActivity{
    
	public SearchCandAssign()
    {
    }
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	StringBuffer sbSql = new StringBuffer();
    	PosRowSet rowset;
    	if ( ctx.get("SEARCH_CHK").equals("1") ) {//미등록시 선수명단에서 조회
	    	sbSql.append("\n SELECT ROWNUM AS NUM,GRD,CAND_NO,CAND_NM,GBN,DCSN_GBN ");
	    	sbSql.append("\n ,MAT_CD,RACE_CNT,TOT_AVG,TOT_RANK,RACE_YY,ROUND FROM( ");
	    	sbSql.append("\n  SELECT  A.GRD  ");
			sbSql.append("\n  ,A.CAND_NO ");
			sbSql.append("\n  ,A.CAND_NM ");
			sbSql.append("\n  ,'0' AS DCSN_GBN ");
			sbSql.append("\n  ,'1' AS GBN ");
			sbSql.append("\n  ,B.MAT_CD ");
			sbSql.append("\n  ,A.RACE_CNT ");
			sbSql.append("\n  ,DECODE(A.YY_AVG_RACE_SCR,0,A.ESTM_SCR,DECODE(A.YY_AVG_RACE_SCR,NULL,A.ESTM_SCR,A.YY_AVG_RACE_SCR)) AS TOT_AVG ");
			sbSql.append("\n  ,A.TOT_RANK  ");
			sbSql.append("\n  ,A.RACE_YY  ");
			sbSql.append("\n  ,B.ROUND   ");
			sbSql.append("\n   FROM TBDB_REC_TOT A ,TBDB_DETL_MAT B ,TBDB_CAND_IDENT C ");
			sbSql.append("\n   WHERE A.CAND_NO = C.CAND_NO ");
			sbSql.append("\n  AND A.RACE_YY = ? ");
			sbSql.append("\n  AND   B.ROUND = ? ");
			sbSql.append("\n  AND	A.RACE_YY = B.RACE_YY ");
			sbSql.append("\n  AND   A.GRD = B.GRD ");
			sbSql.append("\n  AND  C.GRDU_GBN NOT IN('004','005') ");
	    	if ( ctx.get("RADIO1").equals("0") ) {  //선수등급
	    		if( !ctx.get("GRD").equals("")){
	    			sbSql.append("\n   AND A.GRD = ? ");	
	    		}
	    	}else{//대진표
	    		if( !ctx.get("MAT_CD").equals("")){
	    			sbSql.append("\n   AND B.MAT_CD = ? ");	
	    		}
	    	}
    	}else{//후보생 등록된 경우
	    	sbSql.append("\n SELECT ROWNUM AS NUM,GRD,CAND_NO,CAND_NM,GBN,DCSN_GBN ");
	    	sbSql.append("\n ,MAT_CD,RACE_CNT,TOT_AVG,TOT_RANK,RACE_YY,ROUND FROM( ");
			sbSql.append("\n  SELECT  ");
			sbSql.append("\n    A.GRD ");
			sbSql.append("\n   ,A.CAND_NO ");
			sbSql.append("\n    ,B.CAND_NM ");
			sbSql.append("\n    ,A.DCSN_GBN ");
			sbSql.append("\n    ,TRIM(A.GBN)AS GBN ");
			sbSql.append("\n    ,A.MAT_CD ");
			sbSql.append("\n    ,A.RACE_CNT ");
			sbSql.append("\n    ,A.TOT_AVG ");
			sbSql.append("\n    ,A.TOT_RANK ");
			sbSql.append("\n    ,A.RACE_YY ");
			sbSql.append("\n    ,A.ROUND ");
			sbSql.append("\n    FROM TBDB_CAND_ASSIGN A, TBDB_REC_TOT B ");
			sbSql.append("\n   WHERE A.RACE_YY = ?");
			sbSql.append("\n    AND A.ROUND = ? ");
			sbSql.append("\n   AND A.CAND_NO = B.CAND_NO ");
	    	if ( ctx.get("RADIO1").equals("0") ) {  //선수등급
	    		if( !ctx.get("GRD").equals("")){
	    			sbSql.append("\n   AND A.GRD = ? ");	
	    		}
	    	}else{//대진표
	    		if( !ctx.get("MAT_CD").equals("")){
	    			sbSql.append("\n   AND A.MAT_CD = ? ");	
	    		}
	    	}
    	}
		if ( ctx.get("RADIO2").equals("0") ) {
			sbSql.append("\n  ORDER BY CAND_NO ");
		}else if( ctx.get("RADIO2").equals("1") ) {
			sbSql.append("\n  ORDER BY RACE_CNT ");
		}else if( ctx.get("RADIO2").equals("2") ) {
			sbSql.append("\n  ORDER BY TOT_AVG DESC ");
		}else {
			sbSql.append("\n  ORDER BY CAND_NM ");
		}
		
		sbSql.append("\n ) ");
		sbSql.append("\n  ORDER BY NUM ");
		PosParameter param = new PosParameter();
    	
        int i = 0;
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_YY"));  
        param.setWhereClauseParameter(i++, ctx.get("ROUND"   )); 
    	if ( ctx.get("RADIO1").equals("0") ) {  //선수등급
    		if( !ctx.get("GRD").equals("")){
    			param.setWhereClauseParameter(i++, ctx.get("GRD"   )); 
    		}
    	}else{//대진표
    		if( !ctx.get("MAT_CD").equals("")){
    			param.setWhereClauseParameter(i++, ctx.get("MAT_CD"   )); 
    		}
    	}
    	rowset = this.getDao("candao").findByQueryStatement(sbSql.toString(), param);
    	
    	String sResultKey = "dsCandAssign";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
