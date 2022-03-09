/*================================================================================
 * 시스템			: 하계휴양소  관리
 * 소스파일 이름	: snis.rbm.business.rbs8010.activity.SaveEvntMana.java
 * 파일설명		: 하계휴양소의 신청을 위한 기본정보를 관리한다.
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2013-06-16
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs8010.activity;

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

public class SaveRstMaster extends SnisActivity {
	
	public SaveRstMaster(){}

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
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		    = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize = ds.getRecordCount();
        }

        String sAttFileKey = "";
        String attFileKey = "";
        String sRsvYear = (String)ctx.get("RSV_YEAR");
        String sRsvTms  = (String)ctx.get("RSV_TMS");
        
        // 하계휴양소 신청정보 저장
        sDsName = "dsAskInfo";        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	            	nDeleteCount = nDeleteCount + deleteAskInfo(record); 	// 하계휴양소 신청정보 삭제
	            	
	            	//첨부파일이 존재할 시, 첨부파일 삭제
	            	SaveFile.deleteFile(record, this.getDao("rbmdao"));
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {	
	            	
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){	// 신규등록시 첨부파일이 있는 경우 
	            		attFileKey = record.getAttribute("ATT_FILE_KEY").toString();
	            		if (Util.isNull(attFileKey)) {	            		
			            	sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
			        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
	            		}
		        	}
	            	
			        nSaveCount += saveAskInfo(record);			        
		        }
	        }	        
        }   
        

        sDsName = "dsUploadFile";
        String tmpFileKey = "";

        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
		             record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	        		
	            	if ( (nTempCnt = SaveFile.updateFileMana(record,this.getDao("rbmdao"))) == 0 ) {
		        		 
		        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
		        			//등록시 첨부파일 키 생성된 경우
		        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		} else {
		        			//첨부파일이 별도로 등록되는경우
		        			if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){ 			
		        				if(tmpFileKey == null || tmpFileKey.equals("")){
	        						tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
	        						
	        						//record.setAttribute("ATT_FILE_KEY", tmpFileKey);
	        						//record.setAttribute("RSV_YEAR",ctx.get("RSV_YEAR"));
		        					//record.setAttribute("RSV_TMS" ,ctx.get("RSV_TMS"));
		        					
		        					updateAskInfoAttKey(tmpFileKey, sRsvYear, sRsvTms);
		        				}
		        				record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        			}
		        		}
		        		
		        		record.setAttribute("TBL_NM", "TBRF_EV_MASTER");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
			        	nTempCnt   = SaveFile.insertFileMana(record, this.getDao("rbmdao"));
	            	}
	            	nSaveCount = nSaveCount + nTempCnt;
			        continue;
            	}
  
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record, this.getDao("rbmdao"));
				}
	         } //for
        } //if
        
        
        // 하계휴양소 신청정보 저장
        sDsName = "dsRstMaster";        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	            	nDeleteCount = nDeleteCount + deleteRstMaster(record); 	// 하계휴양소 신청정보 삭제
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {		        	
			        nSaveCount += saveRstMaster(record);			        
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
    protected int saveAskInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //하계휴양소 신청년도
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	   	//하계휴양소 신청회차
        param.setValueParamter(i++, record.getAttribute("USE_STR_DT"));		//하계휴양소 이용시작일
        param.setValueParamter(i++, record.getAttribute("USE_END_DT"));		//하계휴양소 이용종료일
        param.setValueParamter(i++, record.getAttribute("ASK_STR_DT"));		//하계휴양소 신청시작일
        param.setValueParamter(i++, record.getAttribute("ASK_END_DT"));		//하계휴양소 신청종료일
        param.setValueParamter(i++, record.getAttribute("RMK"));			//하계휴양소 신청정보 비고
        param.setValueParamter(i++, record.getAttribute("DAY_NUM"));		//하계휴양소 숙박일수
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs8010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 하계휴양소신청정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAskInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //하계휴양소 신청년도
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	    //하계휴양소 신청회차
        
        int dmlcount = this.getDao("rbmdao").update("rbs8010_d01", param);

        return dmlcount;
    }

    /**
     * <p> 하계휴양소정보 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveRstMaster(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //신청년도
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	   	//신청회차
        param.setValueParamter(i++, record.getAttribute("RST_ID"));		    //휴양소 아이디
        param.setValueParamter(i++, record.getAttribute("RST_SEQ"));		//휴양소 연번
        param.setValueParamter(i++, record.getAttribute("RST_NAME"));		//휴양소명
        param.setValueParamter(i++, record.getAttribute("RSV_MAX_NUM"));	//객실수
        param.setValueParamter(i++, record.getAttribute("USE_STR_DT"));		//이용시작일
        param.setValueParamter(i++, record.getAttribute("USE_END_DT"));		//이용종료일
        param.setValueParamter(i++, record.getAttribute("DAY_NUM"));		//숙박일수
        param.setValueParamter(i++, SESSION_USER_ID);					   	//사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs8010_m02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 하계휴양소정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRstMaster(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RSV_YEAR"));	    //신청년도
        param.setValueParamter(i++, record.getAttribute("RSV_TMS"));	    //신청회차
        param.setValueParamter(i++, record.getAttribute("RST_ID"));	    	//휴양소 아이디
        param.setValueParamter(i++, record.getAttribute("RST_SEQ"));	    //휴양소 일련번호
        
        int dmlcount = this.getDao("rbmdao").update("rbs8010_d02", param);

        return dmlcount;
    }

    /**
     * <p> 첨부파일 정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAskInfoAttKey(String attFileKey, String rsvYear, String rsvTms)
    { 
    	System.out.println("attFileKey="+attFileKey);
    	System.out.println("rsvYear="+rsvYear);
    	System.out.println("rsvTms="+rsvTms);

    	PosParameter param = new PosParameter();
        int i = 0;          	        
        param.setValueParamter(i++, attFileKey);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, rsvYear);     // 신청년도
        param.setValueParamter(i++, rsvTms);      // 신청차수

        int dmlcount = this.getDao("rbmdao").update("rbs8010_u01", param);
        return dmlcount;
    }
    
}