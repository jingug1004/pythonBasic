package snis.rbm.system.rsy4080.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveDutyList extends SnisActivity {
	public SaveDutyList(){	
		
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

        sDsName = "dsDutyList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteDutyList(record);	            	
	            }
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveDutyList(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> 업무일지 입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveDutyList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("USER_ID"));		// 등록자
        param.setValueParamter(i++, record.getAttribute("DUTY_DT"));		// 등록일자
        param.setValueParamter(i++, record.getAttribute("SEQ"));			// 연번
        param.setValueParamter(i++, record.getAttribute("JOB_TYPE"));		// 업무구분   
        param.setValueParamter(i++, record.getAttribute("JOB_TYPE_DTL"));	// 업무세부구분	
        param.setValueParamter(i++, SESSION_USER_ID);						// 작업자 사번
        param.setValueParamter(i++, record.getAttribute("IMPTC"));			// 중요도	
        param.setValueParamter(i++, record.getAttribute("STATUS"));			// 상태
        param.setValueParamter(i++, record.getAttribute("DUTY_TITLE"));		// 작업 제목
        param.setValueParamter(i++, record.getAttribute("DUTY_PLAN"));		// 작업 계획
        param.setValueParamter(i++, record.getAttribute("DUTY_DONE"));		// 처리내역
        param.setValueParamter(i++, record.getAttribute("DUE_DT"));			// 처리기한
        param.setValueParamter(i++, record.getAttribute("END_DT"));			// 완료일자
        param.setValueParamter(i++, record.getAttribute("DUTY_DT"));		// 등록일자
        
        int dmlcount = this.getDao("rbmdao").update("rsy4080_u01", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> 업무일지 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteDutyList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") );        
        param.setValueParamter(i++, record.getAttribute("DUTY_DT") );        
        param.setValueParamter(i++, record.getAttribute("SEQ") );        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4080_d01", param);

        return dmlcount;
    }
}
