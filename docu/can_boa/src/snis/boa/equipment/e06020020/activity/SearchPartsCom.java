/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020020.activity.SearchPartsCom.java
 * 파일설명		: 업체를 조회한다. 
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-04
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020020.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* 업체를 조회 한다. 
* @auther 유재은 
* @version 1.0
*/
public class SearchPartsCom extends SnisActivity
{    
    public SearchPartsCom()
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
    	getPartsCom(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 업체 조회 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    private void getPartsCom(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutPartsCom";
        String	parts_com_cd   = (String) ctx.get("PARTS_COM_CD");
        String	com_nm   = (String) ctx.get("COM_NM");
        rowset = getPartsComRowSet( parts_com_cd, com_nm);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getPartsComRowSet(String parts_com_cd, String com_nm)
    {
        PosParameter param = new PosParameter();


        int i = 0;
        param.setWhereClauseParameter(i++, parts_com_cd);
        param.setWhereClauseParameter(i++, com_nm);
        return  this.getDao("boadao").find("tbef_parts_com_ff001", param);
    }
    
}

