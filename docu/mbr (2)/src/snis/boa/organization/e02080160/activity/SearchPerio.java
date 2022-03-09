/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02080160.activity.SearchPerio.java
 * 파일설명		: 기수별등급별입상률조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02080160.activity;

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
* 매핑하여 기수별등급별입상률조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchPerio extends SnisActivity
{    
	
    public SearchPerio()
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
    	// 기수조회
        PosRowSet rowsetPerid = this.getDao("boadao").find("tbeb_racer_perio_no_reg_term_fb003");    

    	PosParameter param = new PosParameter();
        
    	// 년도에 따른 기수별 입상률 조회
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        param.setWhereClauseParameter(i++, ctx.get("END_DT"));
        param.setWhereClauseParameter(i++, ctx.get("RANK"   ));
        param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
        param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_organ_fb024", param);    

        // column정의
        PosRow rowPerid[] = rowsetPerid.getAllRow();
        PosColumnDef columnDef[] = new PosColumnDef[1 + (rowPerid.length * 2)];
        
    	columnDef[0] = new PosColumnDef("STND_YEAR", 3, 10);
        for ( int j = 0; j < rowPerid.length; j++ ) {
        	columnDef[1 + (j * 2)] = new PosColumnDef("CNT_"        + rowPerid[j].getAttribute("RACER_PERIO_NO"), 3, 10);
        	columnDef[2 + (j * 2)] = new PosColumnDef("PERCENTAGE_" + rowPerid[j].getAttribute("RACER_PERIO_NO"), 3, 10);
        }

        int nStrYear = Integer.parseInt((String) ctx.get("STR_YEAR"));
        int nEndYear = Integer.parseInt((String) ctx.get("END_YEAR"));
        
        PosRow rowData[] = rowset.getAllRow();
		ArrayList alRows  = new ArrayList();
        
		// 해당년도에 기수별 입상률 매핑
        for ( int j = nEndYear; j >= nStrYear; j-- ) {
        	Map mapRow = new HashMap();
        	mapRow.put("STND_YEAR", Integer.toString(j));
        	
        	for ( int k = 0; k < rowData.length; k++ ) {
        		if ( rowData[k].getAttribute("STND_YEAR").equals(Integer.toString(j))) {
        			String sPerio = (String) rowData[k].getAttribute("RACER_PERIO_NO");
        			mapRow.put("CNT_"        + sPerio, rowData[k].getAttribute("CNT")       );
        			mapRow.put("PERCENTAGE_" + sPerio, rowData[k].getAttribute("PERCENTAGE"));
        		}
        	}

        	PosRow row = new PosRowImpl(mapRow);
        	alRows.add(row);
        }
        
        PosRowSet rowsetPerioList = new PosRowSetImpl(alRows);
        
        rowsetPerioList.setColumnDefs(columnDef);
        
        // 년도별 등급별 입상률 조회
        String sResultKey = "dsOutPerioList";
        ctx.put(sResultKey, rowsetPerioList);
        Util.addResultKey(ctx, sResultKey);

        PosRowSet rowsetGrdList = this.getDao("boadao").find("tbeb_organ_fb025", param);    
        
        sResultKey = "dsOutGrdList";
        ctx.put(sResultKey, rowsetGrdList);
        Util.addResultKey(ctx, sResultKey);
    }
}
