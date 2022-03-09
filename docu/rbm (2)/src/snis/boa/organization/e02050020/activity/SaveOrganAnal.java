/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030030.activity.SaveArrange.java
 * 파일설명		: 회차별 편성결과 분석등록
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2010-01-24
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02050020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveOrganAnal extends SnisActivity
{   	
    public SaveOrganAnal()
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
        
        sDsName = "dsOutOrganAnal";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // 편성결과 분석 저장
        for ( int j = 1; j <= 15; j++ ) {
	        sDsName = "dsOutOrganAnal";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	 nTempCnt = updateOrganAnal(record, ctx);
			        }
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    
    /**
     * <p> 편성결과 분석 저장 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
    protected int updateOrganAnal(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"));
        param.setValueParamter(i++, ctx.get("TMS"));
        param.setValueParamter(i++, record.getAttribute("ORGAN_WAY"));
        param.setValueParamter(i++, record.getAttribute("RESULT_ANAL"));
        param.setValueParamter(i++, record.getAttribute("GENERAL_ANAL"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY1_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY1_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY1_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY2_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY2_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY2_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY3_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY3_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY3_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY4_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY4_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY4_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY5_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY5_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY5_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY6_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY6_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY6_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY7_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY7_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY7_T"));
        param.setValueParamter(i++, record.getAttribute("ANAL_INST_NM"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_organ_analysis_ub001", param);
        
        return dmlcount;
    }
}
