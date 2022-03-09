/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020010.activity.SearchParts.java
 * 파일설명		: 부품을 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-06
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020010.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 부품을 조회 한다. 
* @auther 유재은 
* @version 1.0
*/
public class SearchParts extends SnisActivity
{        
    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
	public String runActivity(PosContext ctx)
	  {
	    getParts(ctx);
	    return "success";
	  }

    /**
     * <p> 부품 조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
	private void getParts(PosContext ctx)
	  {
	    String sResultKey = "dsOutParts";
	    String stnd_year = (String)ctx.get("STND_YEAR");
	    String parts_cd = (String)ctx.get("PARTS_CD");
	    String mbr_cd = (String)ctx.get("MBR_CD");
	    String parts_item_cd_nm = (String)ctx.get("PARTS_ITEM_CD_NM");
	    String module_code = (String)ctx.get("MODULE_CODE");
	    String partsComCd = (String)ctx.get("PARTS_COM_CD");

	    PosRowSet rowset = getPartsRowSet(stnd_year, parts_cd, parts_item_cd_nm, mbr_cd, module_code, partsComCd);
	    ctx.put(sResultKey, rowset);
	    Util.addResultKey(ctx, sResultKey);
	  }
    
	public PosRowSet getPartsRowSet(String stnd_year, String parts_cd, String parts_item_cd_nm, String mbr_cd, String module_code, String partsComCd)
	  {
	    PosParameter param = new PosParameter();

	    int i = 0;
	    param.setWhereClauseParameter(i++, stnd_year);
	    param.setWhereClauseParameter(i++, parts_cd);
	    param.setWhereClauseParameter(i++, parts_item_cd_nm);
	    param.setWhereClauseParameter(i++, mbr_cd);
	    param.setWhereClauseParameter(i++, module_code);
	    param.setWhereClauseParameter(i++, partsComCd);
	    return getDao("boadao").find("tbef_parts_ff001", param);
	  }
}

