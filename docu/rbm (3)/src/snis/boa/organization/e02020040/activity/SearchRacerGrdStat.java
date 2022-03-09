/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.racer.e02020040.activity.SearchRacerGrdStat.java
 * 파일설명		: 선수등급평가
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02020040.activity;

import java.util.ArrayList;
import java.util.Hashtable;

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
* 매핑하여 선수의 등급을 조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRacerGrdStat extends SnisActivity
{    
	
    public SearchRacerGrdStat()
    {
    }

    /**
     * <p> searchState Activity를 실행시키기 위한 메소드 </p>
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
        PosRowSet rowset = null;
        
        rowset = searchRacerAll(ctx);
        
        int nColumnCnt = 0;
        Hashtable hsRow = new Hashtable();
        Hashtable hsCol = new Hashtable();
        
        // 선수리스트 생성
        PosRow rows[] = rowset.getAllRow();
        for ( int i = 0; i < rows.length; i++ )
        	hsRow.put((String) rows[i].getAttribute("RACER_NO"), Integer.toString(i));
        
        // 기간 설정
        int nRange     = (Integer.parseInt((String) ctx.get("ESTND_YEAR")) - 
        				  Integer.parseInt((String) ctx.get("SSTND_YEAR")) + 1) * 2;
        
        int nStartYear = Integer.parseInt((String) ctx.get("SSTND_YEAR"));
        int nEndYear   = Integer.parseInt((String) ctx.get("ESTND_YEAR"));

        // return Dataset의 column정의
        PosColumnDef columnDef[] = new PosColumnDef[(2 + nRange)];
        columnDef[nColumnCnt++] = new PosColumnDef("RACER_NO", 		12, 6 );
        columnDef[nColumnCnt++] = new PosColumnDef("NM_KOR", 			12, 20);
        
        int nCnt = 0;
        String sColumnNm = "";
        for ( int i = nStartYear; i <= nEndYear; i++ ) {
        	sColumnNm = nStartYear + "001";
            hsCol.put(sColumnNm, Integer.toString(nCnt++));
            columnDef[nColumnCnt++] = new PosColumnDef("N_" + sColumnNm, 		12, 2 );
            
        	sColumnNm = nStartYear + "002";
            hsCol.put(sColumnNm, Integer.toString(nCnt++));
            columnDef[nColumnCnt++] = new PosColumnDef("N_" + sColumnNm, 		12, 2 );
            
	    	nStartYear++;
        } 
        
        // 선수별 등급 조회
        rowset = searchRacerGrd(ctx);
        
        // 각 기간에 맞는 선수 등급 매핑
        PosRow datarows[] = rowset.getAllRow();
	    for ( int i = 0; i < datarows.length; i++ ) {
	    	int    nRow  = Integer.parseInt((String) hsRow.get(datarows[i].getAttribute("RACER_NO")));
	    	String sQurt = "N_" + (String) datarows[i].getAttribute("STND_YEAR") + (String)datarows[i].getAttribute("QURT_CD");
	    	rows[nRow].setAttribute(sQurt, (String) datarows[i].getAttribute("RACER_GRD_CD"));
	    }
	    
	    // 매핑된 자료를 return할 Dataset으로 변환
        ArrayList alRows = new ArrayList();
        for( int i = 0; i < rows.length; i++ ) {
        	alRows.add(rows[i]);
        }
        
        PosRowSet returnRowset = new PosRowSetImpl(alRows);
        returnRowset.setColumnDefs(columnDef);

        String sResultKey = "dsOutRacerGrdStat";
        ctx.put(sResultKey, returnRowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    // 선수별 등급 조회
    protected PosRowSet searchRacerGrd(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("ESTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("NM_KOR"    ));
                
        return this.getDao("boadao").find("tbeb_racer_grd_fb001", param);
    }
    
    // 선수리스트 조회
	protected PosRowSet searchRacerAll(PosContext ctx) 
	{
        PosParameter param = new PosParameter();
        int i = 0;
    	
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("ESTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("SSTND_YEAR"));
    	param.setWhereClauseParameter(i++, ctx.get("NM_KOR"    ));
        
        return this.getDao("boadao").find("tbeb_racer_grd_fb002", param);
	}
}
