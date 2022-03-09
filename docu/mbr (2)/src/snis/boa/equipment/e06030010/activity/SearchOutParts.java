/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06030010.activity.SearchOutParts.java
 * 파일설명		: 부품 출납일보를 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06030010.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 부품 출납일보를 조회한다. 
* @auther 유재은 
* @version 1.0
*/
public class SearchOutParts extends SnisActivity
{    
    public SearchOutParts()
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
    	getOutParts(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 부품 출납일보 조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getOutParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	stnd_year  = (String) ctx.get("STND_YEAR");
        String	mbr_cd     = (String) ctx.get("MBR_CD");
        String	entDt      = (String) ctx.get("ENT_SDT");
        String	parts_gbn  = (String) ctx.get("PARTS_GBN");
        
        rowset = getPartsRowSet( stnd_year, mbr_cd, entDt, parts_gbn);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    public PosRowSet getPartsRowSet(String stnd_year, String mbr_cd, String entDt, String parts_gbn)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, stnd_year);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, entDt);
        param.setWhereClauseParameter(i++, mbr_cd);
        //param.setWhereClauseParameter(i++, parts_gbn);
        
        return  this.getDao("boadao").find("tbef_parts_lack_ff003", param);
    }
    
}

