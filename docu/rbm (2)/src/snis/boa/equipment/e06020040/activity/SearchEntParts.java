/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020040.activity.SearchEntParts.java
 * 파일설명		: 입고 상세 내역 조회  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020040.activity;

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
public class SearchEntParts extends SnisActivity
{    
    public SearchEntParts()
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
    	getEntParts(ctx);
    	getEntSum(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 입고 상세 내역  조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getEntParts(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEnt";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	order_seq   = (String) ctx.get("ORDER_SEQ");
        
        //logger.logDebug("stndYear =" +stndYear );
        
        rowset = getOrderPartsRowSet(stndYear, mbrCd, order_seq);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    public PosRowSet getOrderPartsRowSet(String stndYear, String mbrCd, String order_seq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, order_seq);
        return  this.getDao("boadao").find("tbef_ent_ff002", param);
    }
    
    /**
     * <p> 입고 상세 내역 조회  </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    public void getEntSum(PosContext ctx)
    {
        PosRowSet    rowset;
        String	sResultKey = "dsOutEntSum";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	order_seq   = (String) ctx.get("ORDER_SEQ");
        String	ent_seq   = (String) ctx.get("ENT_SEQ");
        
        //logger.logDebug("stndYear =" +stndYear );
        
        rowset = getEntSumRowSet(stndYear, mbrCd, order_seq, ent_seq);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    public PosRowSet getEntSumRowSet(String stndYear, String mbrCd, String order_seq, String ent_seq)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, order_seq);
        return  this.getDao("boadao").find("tbef_ent_ff004", param);
    }
}

