/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030030.activity.SearchArrange.java
 * 파일설명		: 주선조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 주선조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchArrange extends SnisActivity
{    
    public SearchArrange()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutEscRacer";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("QURT_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        
        rowsetRacer = this.getDao("boadao").find("tbeb_racer_race_alloc_fb001", paramRacer);

        // 회차 조회
    	PosParameter paramTms = new PosParameter();
        PosRowSet rowsetTms = null; 
        i = 0;

        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramTms.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramTms.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        rowsetTms = this.getDao("boadao").find("tbeb_race_day_ord_fb000", paramTms);
        
        // 출주 조회
    	PosParameter paramOrgan = new PosParameter();
        PosRowSet rowsetOrgan   = null; 
        i = 0;

        paramOrgan.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramOrgan.setWhereClauseParameter(i++, ctx.get("MBR_CD"));
        paramOrgan.setWhereClauseParameter(i++, ctx.get("STR_TMS"));
        paramOrgan.setWhereClauseParameter(i++, ctx.get("END_TMS"));
        rowsetOrgan = this.getDao("boadao").find("tbeb_organ_fb001", paramOrgan);
        
        // 출주예정 조회
    	PosParameter paramAlloc = new PosParameter();
        PosRowSet rowsetAlloc   = null; 
        i = 0;

        paramAlloc.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramAlloc.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramAlloc.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramAlloc.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetAlloc = this.getDao("boadao").find("tbeb_racer_race_alloc_fb002", paramAlloc);

        // 주선제외(제재주선제외)
    	PosParameter paramEsc = new PosParameter();
        PosRowSet rowsetEsc   = null; 
        i = 0;

        paramEsc.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramEsc.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramEsc.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramEsc.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetEsc = this.getDao("boadao").find("tbec_arrange_esc_fb001", paramEsc);

        // 주선제외(기타주선제외)
    	PosParameter paramEscSanc = new PosParameter();
        PosRowSet rowsetEscSanc   = null; 
        i = 0;

        paramEscSanc.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramEscSanc.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramEscSanc.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramEscSanc.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetEscSanc = this.getDao("boadao").find("tbec_arrange_esc_fb002", paramEscSanc);

        // 주선제외(성적주선제외)
    	PosParameter paramEscStnd = new PosParameter();
        PosRowSet rowsetEscStnd   = null; 
        i = 0;

        paramEscStnd.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramEscStnd.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramEscStnd.setWhereClauseParameter(i++, ctx.get("STR_TMS"  ));
        paramEscStnd.setWhereClauseParameter(i++, ctx.get("END_TMS"  ));
        rowsetEscStnd = this.getDao("boadao").find("tbec_arrange_esc_fb003", paramEscStnd);

        String sResultKey = "dsOutRaceDayOrd";
        ctx.put(sResultKey, rowsetTms);
        Util.addResultKey(ctx, sResultKey);

        sResultKey = "dsOutRacerArrange";
        ctx.put(sResultKey, createArrange(rowsetRacer, rowsetTms, rowsetOrgan, rowsetAlloc, rowsetEsc, rowsetEscSanc, rowsetEscStnd));
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> return 할 주선 Dataset생성 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosRowSet createArrange(PosRowSet rowsetRacer, PosRowSet rowsetTms, PosRowSet rowsetOrgan, PosRowSet rowsetAlloc, PosRowSet rowsetEsc, PosRowSet rowsetEscSanc, PosRowSet rowsetEscStnd)
    {
        HashMap   hmRacer = new HashMap();
		ArrayList alRows  = new ArrayList();
		PosRowSet rowset  = rowsetRacer ;
		int nRow = 0;
		
		// 회차별 column설정
        PosColumnDef columnDef[] = createColumn(rowsetRacer, rowsetTms);

        // 선수 리스트 생성
        PosRow rows[] = rowsetRacer.getAllRow();
        for ( int i = 0; i < rows.length; i++ ) {
        	hmRacer.put((String) rows[i].getAttribute("RACER_NO"), Integer.toString(i));
			logger.logInfo(rows[i]);
        }
        
        String sColumnNmOnline = "";
        String sOnlineCnt = "";
        
        // 주선제외(제재주선제외)
        while ( rowsetEsc.hasNext() )
        {
        	PosRow row = rowsetEsc.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, "-");
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, "-");
        	}
        }
        
        // 주선제외(기타주선제외)
        while ( rowsetEscSanc.hasNext() )
        {
        	PosRow row = rowsetEscSanc.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, "*");
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, "*");
        	}
        }
        
        // 주선제외(성적주선제외)
        while ( rowsetEscStnd.hasNext() )
        {
        	PosRow row = rowsetEscStnd.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, "#");
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, "#");
        	}
        }
        
        // 출주
        while ( rowsetOrgan.hasNext() )
        {
        	PosRow row = rowsetOrgan.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, row.getAttribute("RACE_ALLOC_CNT"));
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
               	rows[nRow].setAttribute(sColumnNmOnline, row.getAttribute("RACE_ONLINE_CNT"));
        	}
        }

        // 출주배정
        while ( rowsetAlloc.hasNext() )
        {
        	PosRow row = rowsetAlloc.next();
        	String sRacerNo = (String) row.getAttribute("RACER_NO");
        	if ( hmRacer.get(sRacerNo) != null ) {
        		nRow = Integer.parseInt((String) hmRacer.get(sRacerNo));
            	String sColumnNm = "TMS_" + row.getAttribute("TMS") + "_DAY_" + row.getAttribute("DAY_ORD");
            	rows[nRow].setAttribute(sColumnNm, row.getAttribute("RACE_ALLOC_CNT"));
           		sColumnNmOnline = "TMS_" + row.getAttribute("TMS") + "_ONDAY_" + row.getAttribute("DAY_ORD");
           		rows[nRow].setAttribute(sColumnNmOnline, row.getAttribute("RACE_ONLIE_CNT"));
        	}
        }
        // Dataset 생성
        for ( int i = 0; i < rows.length; i++ ) {
        	alRows.add(rows[i]);
        }
       
		if ( alRows.size() > 0 ) rowset = new PosRowSetImpl(alRows);
		rowset.setColumnDefs(columnDef);

        return rowset;
    }


    /**
     * <p> 회차별 column설정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected PosColumnDef[] createColumn(PosRowSet rowsetRacer, PosRowSet rowsetTms)
    {
        PosColumnDef columnDefRacer[] = rowsetRacer.getColumnDefs();
        PosColumnDef columnDefTms[]   = new PosColumnDef[rowsetTms.getAllRow().length*2];
        PosColumnDef columnDef[]      = new PosColumnDef[columnDefRacer.length + columnDefTms.length];
        int nCnt = 0;
        
        String sColumnNm = "";
        String sColumnNmOnline = "";
        String sTms = "";
        
        while ( rowsetTms.hasNext() )
        {
        	PosRow row = rowsetTms.next();
        	sTms = row.getAttribute("TMS").toString();
        	sColumnNm = "TMS_" + sTms + "_DAY_" + row.getAttribute("DAY_ORD");
        	columnDefTms[nCnt++] = new PosColumnDef(sColumnNm, 12, 10);
        	sColumnNm = "TMS_" + sTms + "_ONDAY_" + row.getAttribute("DAY_ORD");
        	columnDefTms[nCnt++] = new PosColumnDef(sColumnNm, 12, 10);
        }
        
        nCnt = 0;
        for ( int i = 0; i < columnDefRacer.length; i++ ) {
        	columnDef[nCnt++] = columnDefRacer[i];
        }
        
        for ( int i = 0; i < columnDefTms.length; i++ ) {
        	columnDef[nCnt++] = columnDefTms[i];
        }
        
        return columnDef;
    }
}
