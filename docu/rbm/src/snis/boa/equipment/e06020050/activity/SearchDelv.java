/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020050.activity.SearchDelv.java
 * 파일설명		: 출고목록을 조회 한다.. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020050.activity;

import snis.boa.common.util.SnisActivity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 출고목록 조회 
* @auther J.E. 
* @version 1.0
*/
public class SearchDelv extends SnisActivity
{    
    public SearchDelv()
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
    	getDelv(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 출고목록 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getDelv(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutOrderList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	delv_sdt =  (String) ctx.get("DELV_SDT");
        String	delv_edt =  (String) ctx.get("DELV_EDT");
        rowset = getDelvRowSet(stndYear, mbrCd, delv_sdt, delv_edt);       
        
        ctx.put(sResultKey, rowset);
        logger.logDebug("rowset---> =" +rowset );
        Util.addResultKey(ctx, sResultKey);
    }   
    
    /**
     * 출고목록 조회 
     * @param stndYear 기준년도
     * @param tms	회차 
     * @param dayOrd	일차 
     * @return
     */
    public PosRowSet getDelvRowSet(String stndYear, String mbrCd, String delv_sdt, String delv_edt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, delv_sdt);
        param.setWhereClauseParameter(i++, delv_edt);       
        return  this.getDao("boadao").find("tbef_delv_ff001", param);
    }
    
}

