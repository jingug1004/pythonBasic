/*================================================================================
 * 시스템			: 선수단 관리
 * 소스파일 이름	: snis.rbm.business.rbs5010.activity.SaveEvntMana.java
 * 파일설명		: 선수를 등록하고 수정하는 기능을 수행한다.
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-11-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs5010.activity;

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

public class SaveRacerInfo extends SnisActivity {
	
	public SaveRacerInfo(){}

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

        // 선수정보 저장
        sDsName = "dsRacerList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 선수 정보 삭제
	            	nDeleteCount = nDeleteCount + deleteRacerInfo(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveRacerInfo(record);
			        
		        }
		        
	        }	        
        }
        
        // 선수계약정보 저장
        sDsName = "dsRacerCntr";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 선수 정보 삭제
	            	nDeleteCount = nDeleteCount + deleteRacerCntr(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveRacerCntr(record);
			        
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
    protected int saveRacerInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //선수번호
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));	   		//선수사번
        param.setValueParamter(i++, record.getAttribute("RACER_NM"));		//선수명
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	   	//종목코드
        param.setValueParamter(i++, record.getAttribute("RACER_TYPE"));  	//선수구분      
        param.setValueParamter(i++, record.getAttribute("RETIRE_GBN"));  	//퇴직여부      
        param.setValueParamter(i++, record.getAttribute("EMAIL"));  		//E-MAIL      
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));  		//부서명      
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));  		//팀명      
        param.setValueParamter(i++, record.getAttribute("FLOC"));  			//직위      
        param.setValueParamter(i++, record.getAttribute("FGRADE"));  		//직급      
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));  		//전화번호      
        param.setValueParamter(i++, record.getAttribute("HP_NO"));  		//핸드폰번호          
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
        param.setValueParamter(i++, record.getAttribute("WORK_FRDT"));  	//입사일자          
        param.setValueParamter(i++, record.getAttribute("WORK_TODT"));  	//퇴사일자          
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 선수정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRacerInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //선수번호
        
        int dmlcount = this.getDao("rbmdao").update("rbs5010_d01", param);

        return dmlcount;
    }

    /**
     * <p> 선수계약정보 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerCntr(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //선수번호
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));	   	//계약년도
        param.setValueParamter(i++, record.getAttribute("CNTR_AMT"));		//계약금액
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_CD"));	   	//조정코드
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_RATE"));  	//조정비율      
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_AMT"));  	//조정금액      
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5010_m02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 선수계약정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRacerCntr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //선수번호
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));	    //계약년도
        
        int dmlcount = this.getDao("rbmdao").update("rbs5010_d02", param);

        return dmlcount;
    }
    

}