/*================================================================================
 * 시스템		    : 학사관리
 * 소스파일 이름	: snis.can.cyclestd.d02000008.activity.SearchPerioExam.java
 * 파일설명		: 선수자격시험
 * 작성자		    : 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000045.activity;

import java.util.ArrayList;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/


public class SearchPerioExam extends SnisActivity 
{
	public SearchPerioExam() { }
	
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
     * @param   ctx	PosContext 종목구분 저장소
     * @return  none
     * @throws  none
     */
     protected void searchState(PosContext ctx) 
     {
 		ArrayList alItemCd = new ArrayList();

 		// 자전거실기 조회
		alItemCd.clear();
		alItemCd.add("301");
		alItemCd.add("302");
		alItemCd.add("303");
		alItemCd.add("304");
		alItemCd.add("305");
		alItemCd.add("306");
		 
		alItemCd.add(ctx.get("RACER_PERIO_NO"));
		alItemCd.add("004");
		alItemCd.add("003");
		alItemCd.add(ctx.get("RACER_PERIO_NO"));

     	String sResultKey = "dsPerioExam1";
        ctx.put(sResultKey, searchPerioExam(ctx, alItemCd));
        Util.addResultKey(ctx, sResultKey);
     }
     
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx	 PosContext	환산기준표 저장소
     * @return  none
     * @throws  none
     */
	protected PosRowSet searchPerioExam(PosContext ctx, ArrayList alItemCd)
	{
		// 자전거실수 조회
		PosParameter param = new PosParameter();

		int i = 0;
		int j = 0;
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));

		return this.getDao("candao").find("tbdb_in_exam_fb001", param);
	}
}
