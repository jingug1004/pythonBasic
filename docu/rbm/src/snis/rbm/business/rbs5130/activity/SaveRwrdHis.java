/*================================================================================
 * 시스템			: 선수단 관리
 * 소스파일 이름	: snis.rbm.business.rbb5130.activity.SaveRwrdHis.java
 * 파일설명		: 입상내역 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-10
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs5130.activity;

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

public class SaveRwrdHis extends SnisActivity {
	
	public SaveRwrdHis(){}

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

        // 포상금 지급기준  저장
        sDsName = "dsRwrdHis";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 포상금 지급기준  삭제
	            	nDeleteCount = nDeleteCount + deleteRwrdHis(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveRwrdHis(record);
			        
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
    protected int saveRwrdHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));		//선수ID
        param.setValueParamter(i++, record.getAttribute("RWRD_SEQ"));		//포상내역 연번
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));		//종목코드
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));  	//대회분류연번
        param.setValueParamter(i++, record.getAttribute("RWRD_RULE_SEQ"));  	//포상지급기준연번   
        param.setValueParamter(i++, record.getAttribute("WIN_HIS_SEQ"));  	//입상내역연번   
        param.setValueParamter(i++, record.getAttribute("PAY_DY"));  		//지급일자
        param.setValueParamter(i++, record.getAttribute("PAY_AMT"));  		//포상금액   
        param.setValueParamter(i++, record.getAttribute("RMK"));  			//비고
        param.setValueParamter(i++, record.getAttribute("PRV_GRP_TYPE"));   //개인/단체 구분
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5130_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 선수정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRwrdHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RWRD_SEQ"));	//입상내역연번
        
        int dmlcount = this.getDao("rbmdao").update("rbs5130_d01", param);

        return dmlcount;
    }


}