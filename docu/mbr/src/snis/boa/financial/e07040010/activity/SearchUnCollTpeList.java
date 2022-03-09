/*================================================================================
 * 시스템			: 상금관리 
 * 소스파일 이름	: snis.boa.financial.e07040010.activity.SearchUnCollTpeList.java
 * 파일설명		: 미수금내역을 조회 한다.. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07040010.activity;

import snis.boa.common.util.SnisActivity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 미수금내역 조회 
* @auther J.E. 
* @version 1.0
*/
public class SearchUnCollTpeList extends SnisActivity
{    
    public SearchUnCollTpeList()
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
    	
    	getRaceIms(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 미수금내역 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getRaceIms(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutUnColList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        //Integer	payMonth =  new Integer((String) ctx.get("PAY_MONTH"));
        String	nmKor =  (String) ctx.get("NM_KOR");
        rowset = getOrderRowSet(stndYear, payMonth, nmKor);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }   
    
    /**
     * 미수금내역 조회 
     * @param stndYear 기준년도
     * @param tms	회차 
     * @param dayOrd	일차 
     * @return
     */
    public PosRowSet getOrderRowSet(String stndYear, String payMonth, String nmKor)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, payMonth);
        param.setWhereClauseParameter(i++, payMonth);
        param.setWhereClauseParameter(i++, nmKor);
        return  this.getDao("boadao").find("tbeg_uncollected_ff002", param);
    }
    
}

