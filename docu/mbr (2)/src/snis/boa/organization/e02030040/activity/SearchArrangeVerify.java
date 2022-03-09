/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030040.activity.SearchArrangeVerify.java
 * 파일설명		: 주선검증조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030040.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchArrangeVerify extends SnisActivity
{    
    public SearchArrangeVerify()
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
    	PosParameter paramRacer = new PosParameter();
        PosRowSet rowsetRacer = null; 
        int i = 0;

        // 선수 조회
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        rowsetRacer = this.getDao("boadao").find("tbeb_arrange_fb004", paramRacer);

        PosRow rows[] = rowsetRacer.getAllRow();
        
        // 선수리스트로 column생성
        PosColumnDef columnDef[] = new PosColumnDef[rows.length + 1];
        columnDef[0] = new PosColumnDef("NM_KOR", 12, 20);
        for ( int j = 0; j < rows.length; j++ ) {
        	columnDef[j + 1] = new PosColumnDef("RACER_NO_" + j, 3, 10);
        }
        
		ArrayList alrows    = new ArrayList();
       
		// 각각의 선수별 주선검증조회
        for ( int j = 0; j < rows.length; j++ ) {
        	Map mapRow = new HashMap();
        	mapRow.put("NM_KOR", rows[j].getAttribute("NM_KOR"));
        	
            for ( int k = j + 1; k < rows.length; k++ ) {
            	PosParameter param = new PosParameter();
                i = 0;

/*
                param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
                param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
                param.setWhereClauseParameter(i++, ctx.get("TMS"));
                param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
                param.setWhereClauseParameter(i++, rows[j].getAttribute("RACER_NO"));
                param.setWhereClauseParameter(i++, rows[k].getAttribute("RACER_NO"));
                PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_fb005", param);
*/                
                param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
                param.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
                param.setWhereClauseParameter(i++, ctx.get("TMS"));
                param.setWhereClauseParameter(i++, rows[j].getAttribute("RACER_NO"));
                param.setWhereClauseParameter(i++, rows[k].getAttribute("RACER_NO"));
                PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_fb0055", param);
                
            	PosRow row = rowset.next();
            	mapRow.put("RACER_NO_" + k, row.getAttribute("CNT"));
            }
            
        	PosRow row = new PosRowImpl(mapRow);
        	alrows.add(row);
        }
        
        PosRowSet rowset = new PosRowSetImpl(alrows);
        rowset.setColumnDefs(columnDef);
        
        String sResultKey = "dsOutRacer";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);

        sResultKey = "dsOutArrangeVerify";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
