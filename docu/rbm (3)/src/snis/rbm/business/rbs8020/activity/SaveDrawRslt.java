/*================================================================================
 * 시스템			: 하계휴양소  추첨결과 저장
 * 소스파일 이름	: snis.rbm.business.rbs8020.activity.SaveEvntMana.java
 * 파일설명		: 하계휴양소의 추첨결과를 저장한다.
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2013-06-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs8020.activity;

import java.text.SimpleDateFormat; 
import java.util.Date;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveDrawRslt extends SnisActivity {
	
	public SaveDrawRslt(){}

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
        int nTempCnt     = 0;

        String sRsvYear = (String)ctx.get("RSV_YEAR");
        String sRsvTms  = (String)ctx.get("RSV_TMS");
  
        // 하계휴양소 신청정보 저장
        sDsName = "dsApplyList";        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {		        	
			        nSaveCount += saveDrawRslt(record);			        
		        }
	        }	        
        }        

        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 하계휴양소신청정보 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveDrawRslt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("WIN_YN"));		//하계휴양소 당첨여부
        param.setValueParamter(i++, record.getAttribute("WEIGHT_VAL"));							//하계휴양소 가중치
        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	//하계휴양소 신청년도
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	//하계휴양소 신청회차
        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//신청자 사번
                		
        int dmlcount = this.getDao("rbmdao").update("rbs8020_u01", param);
        
        return dmlcount;
    }
    

}