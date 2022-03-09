/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010050.activity.SearchEquipInsp.java
 * 파일설명		: 확정 검사를 조회 한다.. 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010090.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;
import com.posdata.glue.dao.vo.*;

/**
* 장비 전일확정타임 조회 
* @auther 정민화 
* @version 1.0
*/
public class SearchEquipFormer extends SnisActivity
{    
    public SearchEquipFormer()
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
    	getEquipFormer(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 장비 전일확정타임 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getEquipFormer(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipFormer";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        rowset = getEquipFormerRowSet(stndYear, mbrCd, tms);
        
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * 전일확정타임 조회 
     * @param stndYear 기준년도
     * @param tms	회차 
     * @return
     */
    public PosRowSet getEquipFormerRowSet(String stndYear, String mbrCd, Integer tms)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        return  this.getDao("boadao").find("tbef_equip_former_ff001", param);
    }
    
}

