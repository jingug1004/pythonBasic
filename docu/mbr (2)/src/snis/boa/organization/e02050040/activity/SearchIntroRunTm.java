/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02050040.activity.SearchIntroRunTm.java
 * 파일설명		: 회차별소개항주타임조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02050040.activity;

import java.util.ArrayList;
import java.util.HashMap;

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
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchIntroRunTm extends SnisActivity
{    
    public SearchIntroRunTm()
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
    	PosParameter paramMot = new PosParameter();
        PosRowSet rowsetMot = null; 
        int i = 0;

        // 모터 조회
        paramMot.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramMot.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramMot.setWhereClauseParameter(i++, ctx.get("TMS"));
        paramMot.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramMot.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramMot.setWhereClauseParameter(i++, ctx.get("TMS"));
        rowsetMot = this.getDao("boadao").find("tbeb_mot_recd_accu_sum_fb003", paramMot);

        // 회차별 소개항주 조회
    	PosParameter paramTms = new PosParameter();
        PosRowSet rowsetTms = null; 
        i = 0;

        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("TMS"));
        rowsetTms = this.getDao("boadao").find("tbeb_organ_fb006", paramTms);
        
        String sResultKey = "dsOutMotItrdtRunTm";
        ctx.put(sResultKey, createIntroRunTm(rowsetMot, rowsetTms, ctx));
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> 각 회차에 해당하는 소개항주 타임 매핑 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet createIntroRunTm(PosRowSet rowsetMot, PosRowSet rowsetTms, PosContext ctx)
    {
        HashMap   hmMot   = new HashMap();
		ArrayList alRows  = new ArrayList();
		PosRowSet rowset  = rowsetMot ;
		int nRow = 0;
		
        PosColumnDef columnDef[] = createColumn(rowsetMot, ctx);
        
        // 모터 설정
        PosRow rows[] = rowsetMot.getAllRow();
        for ( int i = 0; i < rows.length; i++ ) {
        	hmMot.put((String) rows[i].getAttribute("MOT_NO"), Integer.toString(i));
        }
        
        // 소개항주조회
        while ( rowsetTms.hasNext() )
        {
        	PosRow row = rowsetTms.next();
        	String sMotNo = (String) row.getAttribute("MOT_NO");
        	if ( hmMot.get(sMotNo) != null ) {
        		nRow = Integer.parseInt((String) hmMot.get(sMotNo));
            	String sColumnNm = "TMS" + row.getAttribute("TMS");
            	rows[nRow].setAttribute(sColumnNm, row.getAttribute("INTRO_RUN_TM"));
        	}
        }

        for ( int i = 0; i < rows.length; i++ ) {
        	alRows.add(rows[i]);
        }
        
		if ( alRows.size() > 0 ) rowset = new PosRowSetImpl(alRows);

		rowset.setColumnDefs(columnDef);
		
        return rowset;
    }


    /**
     * <p> 각 회차를 column으로 설정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosColumnDef[] createColumn(PosRowSet rowsetMot, PosContext ctx)
    {
        PosColumnDef columnDefIntro[] = rowsetMot.getColumnDefs();
        PosColumnDef columnDefTms[]   = new PosColumnDef[Integer.parseInt((String) ctx.get("TMS"))];
        PosColumnDef columnDef[]      = new PosColumnDef[columnDefIntro.length + columnDefTms.length];
        
        int nCnt = 0;

        for ( int i = 0; i < Integer.parseInt((String) ctx.get("TMS")); i++ ) {
        	String sColumnNm = "TMS" + (i + 1);
        	columnDefTms[nCnt++] = new PosColumnDef(sColumnNm, 12, 10);
        }
        
        nCnt = 0;
        for ( int i = 0; i < columnDefIntro.length; i++ ) {
        	columnDef[nCnt++] = columnDefIntro[i];
        }
        
        for ( int i = 0; i < columnDefTms.length; i++ ) {
        	columnDef[nCnt++] = columnDefTms[i];
        }
        
        return columnDef;
    }
}
