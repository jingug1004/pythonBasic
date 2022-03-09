/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.racer.e02070030.activity.SearchTremTrngAskRacer.java
 * 파일설명		: 수시훈련요청선수조회
 * 작성자			: 김영철
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02070030.activity;

import java.util.ArrayList;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 수시훈련요청선수조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchTremTrngAskRacer extends SnisActivity
{    
	
    public SearchTremTrngAskRacer()
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
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 조회시작 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {   	
    	PosParameter paramTerm = new PosParameter();
        
        int i = 0;
        paramTerm.setWhereClauseParameter(i++, ctx.get("TRNG_PLC_CD"));
        paramTerm.setWhereClauseParameter(i++, ctx.get("STR_DT"     ));
        paramTerm.setWhereClauseParameter(i++, ctx.get("END_DT"     ));

        // 훈련요청 기간조회
        PosRowSet rowsetTerm      = this.getDao("boadao").find("tbeb_trng_ask_racer_rslt_fb001", paramTerm);
        
        // 훈련요청 선수조회
        PosRowSet rowsetExptRacer = this.getDao("boadao").find("tbeb_trng_ask_racer_rslt_fb003", paramTerm);    

        PosRow rowTerm[] = rowsetTerm.getAllRow();
        
        // column정의
        PosColumnDef columnDef[]    = new PosColumnDef[rowTerm.length];
        PosRowSet    rowsetAskRacer = null;
        
        for ( int j = 0; j < rowTerm.length; j++ ) {
        	columnDef[j] = new PosColumnDef("TRNG_DD_" + rowTerm[j].getAttribute("TRNG_DD"), 12, 6);
        	
        	PosParameter param = new PosParameter();
        	
        	i = 0;
        	param.setWhereClauseParameter(i++, ctx.get("TRNG_PLC_CD"));
        	param.setWhereClauseParameter(i++, rowTerm[j].getAttribute("TRNG_DD"));
            PosRowSet rowsetAskRacerDate  = this.getDao("boadao").find("tbeb_trng_ask_racer_rslt_fb002", param);
            rowsetAskRacer = addPosRowSet(rowsetAskRacer, rowsetAskRacerDate, "TRNG_DD_" + rowTerm[j].getAttribute("TRNG_DD"));
        }

        if ( rowsetAskRacer != null )
        	rowsetAskRacer.setColumnDefs(columnDef);

        // 훈련요청기간
        String sResultKey = "dsOutTrngAskTerm";
        ctx.put(sResultKey, rowsetTerm);
        Util.addResultKey(ctx, sResultKey);

        // 훈련요청선수
        sResultKey = "dsOutTrngAskRacer";
        ctx.put(sResultKey, rowsetAskRacer);
        Util.addResultKey(ctx, sResultKey);

        // 훈련예정선수
        sResultKey = "dsOutTrngExptRacer";
        ctx.put(sResultKey, rowsetExptRacer);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * <p> rowset에 addRowSet의 record를 더하여 return한다. </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
	protected PosRowSet addPosRowSet(PosRowSet rowset, PosRowSet addRowSet, String sColumn) 
    {
		ArrayList rows    = new ArrayList();
		PosRow row[]      = null;
		
		if ( rowset != null ) {
			row = rowset.getAllRow();
		}
		
		if ( addRowSet != null ) {
			PosRow addrow[] = addRowSet.getAllRow();
			if ( row == null ) {
				for ( int i = 0; i < addrow.length; i++ ) {
					addrow[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
					rows.add(addrow[i]);
				}
			} else {
				if ( row.length >= addrow.length ) {
					for ( int i = 0; i < row.length; i++ ) {
						if ( i < addrow.length ) {
							row[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
					    }
						rows.add(row[i]);
					}
				} else {
					for ( int i = 0; i < addrow.length; i++ ) {
						if ( i >= row.length ) {
							addrow[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
							rows.add(addrow[i]);
						} else {
							row[i].setAttribute(sColumn, addrow[i].getAttribute("NM_KOR"));
							rows.add(row[i]);
						}
					}
				}
			}
		}

		if ( rows.size() > 0 ) rowset = new PosRowSetImpl(rows);
		
		return rowset;
    }
}
