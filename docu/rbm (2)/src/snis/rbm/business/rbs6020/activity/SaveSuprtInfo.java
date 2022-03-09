/*================================================================================
 * 시스템			: 지원신청내역 관리
 * 소스파일 이름	: snis.rbm.business.rbs6020.activity.SaveEvntMana.java
 * 파일설명		: 기동요원를 등록하고 수정하는 기능을 수행한다.
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs6020.activity;

import java.text.SimpleDateFormat; 
import java.util.Date;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveSuprtInfo extends SnisActivity {
	
	public SaveSuprtInfo(){}

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
    	saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0; 

        // 지원신청 저장
        sDsName = "dsSuprtList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 지원신청 내역  삭제
	            	nDeleteCount = nDeleteCount + deleteSuptr_Info(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
	            	// 1일 3회 이상 신청내역 존재 여부 체크
	            	if (CheckSchedule(ctx, record)) {
	            		return ;	            		
	            	}
	            	
	            	// 지점에서 신청기간내 기 신청내역이 존재하는 경우 체크
	            	if (CheckDupBrnc(ctx, record)) {
	            		return ;
	            	}
	            		            	
	            	// 자료 저장
			        nSaveCount += saveSuptr_Info(record);			        
		        }		        
	        }	        
        }        
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
        
        return ;
    }

    /**
     * <p> 지원신청내역 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSuptr_Info(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));			//지원신청 연번
        param.setValueParamter(i++, record.getAttribute("STR_DT"));			//지원시작일
        param.setValueParamter(i++, record.getAttribute("END_DT"));			//지원종료일
        param.setValueParamter(i++, record.getAttribute("REQ_AREA"));		//지원분야
        param.setValueParamter(i++, record.getAttribute("REQ_RSN"));		//지원사유
        param.setValueParamter(i++, record.getAttribute("SUPTR_ID"));		//기동요원사번
        param.setValueParamter(i++, record.getAttribute("STAT_CD"));		//상태
        param.setValueParamter(i++, record.getAttribute("REQ_TEAM_CD"));	//요청부서
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));			//요청자 사번
        param.setValueParamter(i++, record.getAttribute("BRNC_APRV_ID"));	//지점승인자 사번(지점장)
        param.setValueParamter(i++, record.getAttribute("BRNC_ARPV_DT"));	//지점승인일시
        param.setValueParamter(i++, record.getAttribute("MNGR_ARPV_ID"));	//관리부서 결재자사번
        param.setValueParamter(i++, record.getAttribute("MNGR_APRV_DT"));	//관리부서 최종승인일시
        param.setValueParamter(i++, record.getAttribute("CNCL_RSN"));	    //취소사유
        param.setValueParamter(i++, record.getAttribute("CONDUCT"));	   	//근태사항
        param.setValueParamter(i++, record.getAttribute("SUPRT_CONT"));	   	//지원내용
        param.setValueParamter(i++, record.getAttribute("BRNC_OPINN"));	   	//부서의견
        param.setValueParamter(i++, record.getAttribute("RSLT_REPTR_ID"));	//결과보고서 작성자 사번
        param.setValueParamter(i++, record.getAttribute("RSLT_APRV_ID"));	//보고서 최종결재자 사번
        param.setValueParamter(i++, record.getAttribute("RSLT_REPTR_DT"));	//보고서 작성일자
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 지원신청내역 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSuptr_Info(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));	    // 지원신청내역 연번
        
        int dmlcount = this.getDao("rbmdao").update("rbs6020_d01", param);

        return dmlcount;
    }

    private boolean CheckSchedule(PosContext ctx, PosRecord record) 
    {
    	PosParameter param = new PosParameter();
    	PosRowSet rowset;
		int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//신청 시작일
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));			//신청 종료일
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//신청 시작일
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));
        
		rowset = this.getDao("rbmdao").find("rbs6020_s02", param);
		if (rowset.hasNext()) {		// 신청기간내 날짜별로 3명이상 존재하는 경우 신청불가
			String sResultKey = "dsDupList";
			ctx.put(sResultKey, rowset);
			Util.addResultKey(ctx, sResultKey);	
			Util.setSvcMsgCode(ctx, "SNIS_RBM_E026");
			return true;
		}
		return false;
    }
    

    private boolean CheckDupBrnc(PosContext ctx, PosRecord record) 
    {
    	PosParameter param = new PosParameter();
    	PosRowSet rowset;
		int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//신청 시작일
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));			//신청 종료일
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//신청 시작일
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_TEAM_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));
        
		rowset = this.getDao("rbmdao").find("rbs6020_s05", param);
		if (rowset.hasNext()) {		// 신청기간내 동일 부서에서 신청한 내역이 존재하는 지 여부
			String sResultKey = "dsDupList";
			ctx.put(sResultKey, rowset);
			Util.addResultKey(ctx, sResultKey);	
			Util.setSvcMsgCode(ctx, "SNIS_RBM_E028");
			return true;
		}
		return false;
    }
}