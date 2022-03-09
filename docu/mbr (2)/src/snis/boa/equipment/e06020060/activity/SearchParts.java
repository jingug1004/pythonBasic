/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020060.activity.SearchParts.java
 * 파일설명		: 이월 할 부품을 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-11
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020060.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 이월 할 부품을 조회 한다. 
* @auther 유재은 
* @version 1.0
*/
public class SearchParts extends SnisActivity
{    
    public SearchParts()
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
    	getParts(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 부품 조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	stnd_year  = (String) ctx.get("STND_YEAR");
        String	mbr_cd     = (String) ctx.get("MBR_CD");
        String	parts_tpe  = (String) ctx.get("PARTS_TPE");
        //logger.logInfo("11111111");
        rowset = getPartsRowSet( stnd_year, mbr_cd, parts_tpe);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    
    public PosRowSet getPartsRowSet(String stnd_year, String mbr_cd, String parts_tpe)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, parts_tpe);
        return  this.getDao("boadao").find("tbef_parts_crfw_ff001", param);
    }
    
}

