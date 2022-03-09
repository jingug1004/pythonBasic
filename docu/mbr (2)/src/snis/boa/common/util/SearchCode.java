/*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.boa.common.util.SearchCode.java
 * 파일설명		: 코드조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchCode extends PosActivity
{    
    public SearchCode()
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
        PosDataset ds = (PosDataset)ctx.get("dsInComCd");
        int size = ds.getRecordCount();
        for (int i = 0; i < size; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);

            getCommonCode(ctx, record);
        }
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 공통코드 조회 </p>
     * @param   ctx		PosContext	저장소
     * @param   record	PosRecord	코드그룹정보
     * @return  none
     * @throws  none
     */
    private void getCommonCode(PosContext ctx, PosRecord record)
    {
        PosParameter param = new PosParameter();
        PosRowSet    rowset;
        
        String       sResultKey = (String) record.getAttribute("DSNAME   ".trim());
        String       sCDGrpID   = (String) record.getAttribute("CD_GRP_ID".trim());
        String       sQueryID   = (String) record.getAttribute("QUERY_ID ".trim());
        
        // 코드그룹값이 존재하면 
        if ( !Util.nullToStr(sCDGrpID).equals("") ) {
        	int i = 0;
        	param.setWhereClauseParameter(i++, record.getAttribute("CD_GRP_ID".trim()));
        		
        	if(sQueryID.equals("sort_common_code")) {
        		sQueryID = "sort_common_code";
        	} else {
        		sQueryID = "common_code";
        	}
            
        }

        rowset = this.getDao("boadao").find(sQueryID, param);
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
    }
}

