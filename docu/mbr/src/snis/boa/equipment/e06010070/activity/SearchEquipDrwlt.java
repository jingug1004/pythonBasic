/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010060.activity.SearchEquipLot.java
 * 파일설명		: 모터/보트 추첨 대상을 조회한다. 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010070.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 기준년도, 회차 정보를 받아 해당 회차에 대한 
* 모터 /보트 등록 확저 여부를 조회 한다. 
* @auther 김성희 
* @version 1.0
*/
public class SearchEquipDrwlt extends SnisActivity
{    
    public SearchEquipDrwlt()
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
    	getEquipDrwlt(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 모터 추첨 대상  조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getEquipDrwlt(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEquipDrwlt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        rowset = getEquipDrwltRwoSet( stndYear, mbrCd, tms);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getEquipDrwltRwoSet(String stndYear, String mbrCd, Integer tms)
    {
        PosParameter param = new PosParameter();


        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        return  this.getDao("boadao").find("tbeb_race_tms_ff001", param);
    }
    
}

