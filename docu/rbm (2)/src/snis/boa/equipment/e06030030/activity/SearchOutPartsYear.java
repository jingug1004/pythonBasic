/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06030030.activity.SearchOutPartsYear.java
 * 파일설명		: 부품 출납연보를 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06030030.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 부품 출납연보를 조회한다. 
*/
public class SearchOutPartsYear extends SnisActivity
{    
    public SearchOutPartsYear()
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
     * <p> 부품 출납연보 조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getOutParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutParts";
        String	mbr_cd   = (String) ctx.get("MBR_CD");
        String	partsCd   = (String) ctx.get("PARTS_CD");
        String	partsNm   = (String) ctx.get("PARTS_NM");
        String	entSdt   = (String) ctx.get("ENT_SDT");
        String	entEdt   = (String) ctx.get("ENT_EDT");
        
        rowset = getPartsRowSet( mbr_cd, partsCd, partsNm, entSdt, entEdt);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
    }
    
    public PosRowSet getPartsRowSet(String mbr_cd, String partsCd, String partsNm, String entSdt, String entEdt)
    {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, entSdt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, entSdt);
        param.setWhereClauseParameter(i++, partsCd);
        param.setWhereClauseParameter(i++, partsNm);
        param.setWhereClauseParameter(i++, entSdt);
        param.setWhereClauseParameter(i++, entEdt);
        param.setWhereClauseParameter(i++, mbr_cd);
        param.setWhereClauseParameter(i++, partsCd);
        param.setWhereClauseParameter(i++, partsNm);
        
        return  this.getDao("boadao").find("tbef_outparts_total_ff002", param);
    }
    
}

