/*================================================================================
 * 시스템			: 지점 관리
 * 소스파일 이름	: snis.rbm.business.rbr1010.activity.SaveLastYear.java
 * 파일설명		: 전년도의  지점 사항을 현재 년도로 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr1060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRptPrepSituBonjang extends SnisActivity {
	
	public SaveRptPrepSituBonjang(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        
        sDsName = "dsRptPrepSitu";        
        if ( ctx.get(sDsName) != null ) {
        	
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	        	
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nSaveCount = saveReport(record);
	            }
	        }
        }
        

        sDsName = "dsRptPrepSituNum";        
        if ( ctx.get(sDsName) != null ) {
        	
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	        	
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nSaveCount += saveReportNum(record);
	            }
	        }
        }
  
        Util.setSaveCount  (ctx, nSaveCount );
    }

    /**
     * <p> 경주개최 준비상황보고 저장 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert or update 개수
     * @throws  none
     */
    protected int saveReport(PosRecord record)
    {		
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACE_DAY"));           // 1. 경주일 
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));            // 2. 지점코드
        param.setValueParamter(i++, record.getAttribute("WRKG_NUM"));        	// 3. 근무인원	
        param.setValueParamter(i++, record.getAttribute("DUTY_OFF_NUM"));       // 4. 휴가인원
        param.setValueParamter(i++, record.getAttribute("NUM_CHK_RSLT"));   	// 5. 인원 이상유무
        param.setValueParamter(i++, record.getAttribute("CHK_POINT"));   		// 6. 점검항목
        param.setValueParamter(i++, record.getAttribute("CHK_POINT_RSLT"));     // 7. 점검결과
        param.setValueParamter(i++, record.getAttribute("SIGNFT_POINT"));       // 8. 특이사항
        param.setValueParamter(i++, SESSION_USER_ID);                          	//10. 등록/수정자 사번  
  
        int dmlcount = this.getDao("rbmdao").update("rbr1050_m01", param);
        return dmlcount;
    }

    /**
     * <p> 경주개최 준비인원 저장 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert or update 개수
     * @throws  none
     */
    protected int saveReportNum(PosRecord record)
    {		
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACE_DAY"));           // 1. 경주일 
        param.setValueParamter(i++, record.getAttribute("DEPT_SEQ"));           // 2. 부서코드
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));            // 3. 부서명
        param.setValueParamter(i++, record.getAttribute("WRKG_NUM"));        	// 4. 근무인원	
        param.setValueParamter(i++, record.getAttribute("DUTY_OFF_NUM"));       // 5. 휴가인원
        param.setValueParamter(i++, SESSION_USER_ID);                          	// 6. 등록/수정자 사번  
  
        int dmlcount = this.getDao("rbmdao").update("rbr1060_m01", param);
        return dmlcount;
    }

}
