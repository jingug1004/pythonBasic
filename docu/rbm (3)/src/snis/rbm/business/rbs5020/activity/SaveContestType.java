/*================================================================================
 * 시스템			: 소모품 신청현황
 * 소스파일 이름	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * 파일설명		: 소모품 반출의 승인과 반려를 결정하고 승인 시, 소모품 대장에 추가해줌
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs5020.activity;

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

public class SaveContestType extends SnisActivity {
	
	public SaveContestType(){}

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

        // 대회분류  저장
        sDsName = "dsContestType";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 대회분류  삭제
	            	nDeleteCount = nDeleteCount + deleteContestType(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveContestType(record);
			        
		        }
		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 선수정보 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveContestType(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));	//대회분류연번
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	   		//종목코드
        param.setValueParamter(i++, record.getAttribute("INTL_TYPE"));		   	//국제대회여부
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_NM"));	//종목코드
        param.setValueParamter(i++, record.getAttribute("RMK"));  				// 선수구분      
        param.setValueParamter(i++, SESSION_USER_ID);					   		//사용자ID(수정자)
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	   		//종목코드
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 선수정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteContestType(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	    	//종목코드
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));	//대회분류연번
        
        int dmlcount = this.getDao("rbmdao").update("rbs5020_d01", param);

        return dmlcount;
    }


}