/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010060.activity.SearchEquipInspDetail.java
 * 파일설명		: 장비 확정검사 상세 내역 조회  
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010050.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 장비 
* @auther 김성희 
* @version 1.0
*/
public class SearchEquipInspDetail extends SnisActivity
{    
    public SearchEquipInspDetail()
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
    	getEquipInspDetail(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 장비 확정검사 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getEquipInspDetail(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipInspDetail";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        Integer	dayOrd =  new Integer((String) ctx.get("DAY_ORD"));
        String equipDrwltCmplYn = "X";
        rowset = getEquipInspDetailRowSet(stndYear, mbrCd, tms, dayOrd, equipDrwltCmplYn);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getEquipInspDetailRowSet(String stndYear, String mbrCd, Integer tms, Integer dayOrd, String equipDrwltCmplYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, dayOrd);
        return  this.getDao("boadao").find("tbef_equip_insp_detail_ff001", param);
        
    }
}

