/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020040.activity.SearchOrderDt.java
 * 파일설명		: 주문일자를 조회 한다.. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-11
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020040.activity;

import snis.boa.common.util.SnisActivity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 주문일자 조회 
* @auther J.E. 
* @version 1.0
*/
public class SearchOrderDt extends SnisActivity
{    
    public SearchOrderDt()
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
    	getOrderDt(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 주문일자 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getOrderDt(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutOrderDt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD"); 
        rowset = getOrderRowSet(stndYear, mbrCd);   
        
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }   
    
    /**
     * 주문일자 조회 
     * @param stndYear 기준년도
     * @param tms	회차 
     * @param dayOrd	일차 
     * @return
     */
    public PosRowSet getOrderRowSet(String stndYear, String mbrCd)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        return  this.getDao("boadao").find("tbef_ent_ff003", param);
    }
    
}

