/*
 * ================================================================================
 * 시스템 : 알림 관리 
 * 소스파일 이름 : snis.rbm.system.rsy7020.activity.SaveAlarm.java 
 * 파일설명 : 알림 관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011-10-21 
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.system.rsy7020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveAlarm extends SnisActivity {
	public SaveAlarm(){
		
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
    	String sAlarmCd  = (String)ctx.get("alarmCd");
    	if(sAlarmCd == null) sAlarmCd="";
    	
    	String sAlarmGbn  = (String)ctx.get("alarmGbn");
    	if(sAlarmGbn == null) sAlarmGbn="";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsAlarm";
        
        
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {

		        	record.setAttribute("ALARM_CD", sAlarmCd);
		        	record.setAttribute("ALARM_GBN", sAlarmGbn);
		        	
		        	
		        	if(record.getAttribute("CHK").equals("1")){
		        		if(record.getAttribute("ALARM_YN").equals("N")){
		        			
			        		nTempCnt = insertAlarm(record);
		        		}
	
		        	}else{
		        		
		        		nDeleteCount = nDeleteCount + deleteAlarm(record);	
		        	}

			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }

	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 알림담당자  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAlarm(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ALARM_CD"));			//알림코드
        param.setValueParamter(i++, record.getAttribute("ALARM_GBN"));			//알림구분코드
        param.setValueParamter(i++, record.getAttribute("USER_ID"));			//받는사람ID
        
        

        param.setValueParamter(i++, SESSION_USER_ID);							//작성자
        param.setValueParamter(i++, SESSION_USER_ID);							//작성자

        
        int dmlcount = this.getDao("rbmdao").update("rsy7020_i01", param);
        
        return dmlcount;
    }
    
    
  
    
    /**
     * <p> 알림담당자  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAlarm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ALARM_CD") );	//알림코드
        param.setValueParamter(i++, record.getAttribute("USER_ID") );	//받는사람ID
        param.setValueParamter(i++, record.getAttribute("ALARM_GBN"));	//알림구분코드
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy7020_d01", param);

        return dmlcount;
    }
}
