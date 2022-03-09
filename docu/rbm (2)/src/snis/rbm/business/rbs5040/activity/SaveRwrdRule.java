/*================================================================================
 * 시스템			: 선수단 관리
 * 소스파일 이름	: snis.rbm.business.rbb5040.activity.SaveRwrdRule.java
 * 파일설명		: 포상금지급기준을 추가 및 삭제 처리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-10
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs5040.activity;

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

public class SaveRwrdRule extends SnisActivity {
	
	public SaveRwrdRule(){}

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
        sDsName = "dsRwrdRule";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 포상금 지급기준  삭제
	            	nDeleteCount = nDeleteCount + deleteRwrdRule(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveRwrdRule(record);
			        
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
    protected int saveRwrdRule(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	   		//종목코드
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));	//대회분류 연번
        param.setValueParamter(i++, record.getAttribute("RWRD_RULE_SEQ"));		//포상금지급기준연번
        param.setValueParamter(i++, record.getAttribute("RANK"));		   		//순위
        param.setValueParamter(i++, record.getAttribute("PRV_GRP_TYPE"));		//개인/단체 구분
        param.setValueParamter(i++, record.getAttribute("MRTN_RACE_TYPE"));		//마라톤 경주 분류
        param.setValueParamter(i++, record.getAttribute("CYCL_PLC_TYPE"));		//사이클 장소 구분
        param.setValueParamter(i++, record.getAttribute("CYCL_TERM_TYPE"));		//사이클 기간 구분
        param.setValueParamter(i++, record.getAttribute("PRTC_MEMR_TYPE"));  	//참가인원 구분      
        param.setValueParamter(i++, record.getAttribute("FB_LEG_TYPE"));  		//축구리그 구분      
        param.setValueParamter(i++, record.getAttribute("RWRD_AMT_1"));  		//포상금액(갑)      
        param.setValueParamter(i++, record.getAttribute("RWRD_AMT_2"));  		//포상금액(을)      
        param.setValueParamter(i++, record.getAttribute("RWRD_AMT_3"));  		//포상금액(병)      
        param.setValueParamter(i++, record.getAttribute("RWRD_AMT_4"));  		//포상금액(정) 
        param.setValueParamter(i++, record.getAttribute("RMK"));  				//비고   
        param.setValueParamter(i++, record.getAttribute("WIN_RWRD"));  			//승리포상
        param.setValueParamter(i++, record.getAttribute("AUTO_CALC_YN"));  		//자동계산여부
        param.setValueParamter(i++, SESSION_USER_ID);					   		//사용자ID(수정자)
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	   		//종목코드
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));	//대회분류 연번
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5040_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 선수정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRwrdRule(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	    	//종목코드
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));	//대회분류코드
        param.setValueParamter(i++, record.getAttribute("RWRD_RULE_SEQ"));	    //포상금지급기준 연번
        
        int dmlcount = this.getDao("rbmdao").update("rbs5040_d01", param);

        return dmlcount;
    }


}