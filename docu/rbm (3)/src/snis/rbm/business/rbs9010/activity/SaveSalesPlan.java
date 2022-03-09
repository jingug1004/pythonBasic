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
package snis.rbm.business.rbs9010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSalesPlan extends SnisActivity {
	
	public SaveSalesPlan(){}

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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	int nSize      = 0;
    	
    	String sFlag = "N";	//메세지를 사용자에게 띄울지 말지를 정하는 Flag
    	String sMsg  = "";
    	PosDataset ds;
    	
    	String sPlanType = "";
        String sDsName = "dsNetPlanSave";
        
        sPlanType = (String)ctx.get("PLAN_TYPE");
        if ("N".equals(sPlanType)) sDsName = "dsTotPlanSave"; 
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 	 ) {	
	            	
			        nSaveCount += saveSalesPlan(record);			        
		        }
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 연간 순매출/총매출 계획 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSalesPlan(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	   	// 개최지코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		// 기준년도
        param.setValueParamter(i++, record.getAttribute("STND_MONTH"));		// 기준월
        param.setValueParamter(i++, record.getAttribute("PLAN_TYPE"));		// 순매출/총매출 여부("Y":순매출)
        param.setValueParamter(i++, record.getAttribute("NET_SALES_AMT"));	// 순매출/총매출 목표
        param.setValueParamter(i++, SESSION_USER_ID);					   	// 사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs9010_m01", param);
        
        return dmlcount;
    }
    
}
