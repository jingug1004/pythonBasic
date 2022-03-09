/*
 * ================================================================================
 * 시스템 : 체류인원마감관리 
 * 소스파일 이름 : snis.rbm.system.rem3011.activity.SaveStayEndMana.java 
 * 파일설명 : 체류인원마감관리관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011-10- 01
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rem3011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveStayEndMana extends SnisActivity {
	
	public SaveStayEndMana(){
		
		
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

        
        int nUploadFileSize = 0;
        sDsName = "dsUploadFile";
        String sMAttFileKey = "";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize 	 = ds.getRecordCount();
        }
        

        sDsName = "dsStayEndMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            record.setAttribute("STAY_END_STAT_CD", "001");
	            
		        if ( record.getType() == PosRecord.INSERT ||  record.getType() == PosRecord.UPDATE) {

		        	if(nUploadFileSize > 0){
		        		sMAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sMAttFileKey);		

		        	}
		        	
		        	nTempCnt = insertStayEndMana(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }    
	        }	        
        }

        
       sDsName = "dsUploadFile";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	record.setAttribute("ATT_FILE_KEY", sMAttFileKey);

	        		
	        		record.setAttribute("TBL_NM", "TBRC_STAY_END_MANA");
	        		record.setAttribute("INST_ID", SESSION_USER_ID);
	        		
	        		SaveFile.insertFileMana(record,this.getDao("rbmdao"));
	         }	         
          }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    
    /**
     * <p> 체류인원마감취소 요청 입력 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int insertStayEndMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));				//경주일자
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));				//지점코드
        
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));					//경주일자
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));				//지점코드
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));				//경륜장코드   
        
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));				//경륜장코드
        param.setValueParamter(i++, record.getAttribute("REQ_RSN"));				//요청사유   
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"      ));		//첨부파일키
        param.setValueParamter(i++, record.getAttribute("RMK"      ));				//비고        
        param.setValueParamter(i++, record.getAttribute("STAY_END_STAT_CD"      ));				//비고
        
        param.setValueParamter(i++, SESSION_USER_ID);								//작성자
   

        
        int dmlcount = this.getDao("rbmdao").update("rem3011_i01", param);
        return dmlcount;
    }

	
	
	
	
}
