/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020050.activity.SearchDelvParts.java
 * 파일설명		: 출고 상세 내역 조회  
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
* 부품 
* @auther J.E. 
* @version 1.0
*/
public class SearchDelvParts extends SnisActivity
{    
    public SearchDelvParts()
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
    	getDelvParts(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 출고 상세 내역  조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getDelvParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutOrderPartsList";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	delv_seq   = (String) ctx.get("DELV_SEQ");
        
        //logger.logDebug("stndYear =" +stndYear );
        
        rowset = getDelvPartsRowSet(stndYear, mbrCd, delv_seq);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getDelvPartsRowSet(String stndYear, String mbrCd, String delv_seq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, delv_seq);
        return  this.getDao("boadao").find("tbef_delv_parts_ff001", param);
    }
}

