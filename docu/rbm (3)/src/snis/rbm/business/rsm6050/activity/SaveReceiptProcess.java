/*
 * ================================================================================
 * 시스템 : 분실신고서 접수
 * 파일 이름 : snis.rbm.business.rsm6040.activity.SaveLossReturnsReceipt.java 
 * 파일설명 :  
 * 작성자 : 
 * 버전 : 1.0.0 생성일자 : 2011- 10 - 26
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm6050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveReceiptProcess extends SnisActivity {
	
	public SaveReceiptProcess(){}

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
        int nTempCnt     = 0;
        int nSize		 = 0;
        int nSize2		 = 0;
        
    	String sDsName   = "";
    	String sDsName2  = "dsUploadFile";
    	PosDataset ds;
    	PosDataset ds2;
        
        //첨부파일 처음 등록시 첨부파일KEY 생성 
        String sNewAttFileKey ="";
        String sAttFileKey = "";
    	       
        sDsName = "dsTsnRcptResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	                	
	  		    nTempCnt  = updateProcess(record);	//접수,접수취소,해지요청,해지요청취소
	  		    
	  		    
	  		    //접수일 경우 증빙서류 등록 
	  		    if( record.getAttribute("TIC_STAS").equals("002")){
	  		    	sAttFileKey = (String)record.getAttribute("ATT_FILE_KEY");	
	  		    	if(sAttFileKey == null) sAttFileKey="";
	  		    	
	  		    	
	  		    	//첨부파일 키 생성
		        	if(sAttFileKey.equals("")){
		        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		
    		        	
			        	//분실신고서 첨부파일 key update
			        	updateReqAttKey(record);		        	
		        		
		        	}
		        	
		        	//첨부파일 등록		            
		            if ( ctx.get(sDsName2) != null ) {
		    	        ds2   		 = (PosDataset) ctx.get(sDsName2);
		    	        nSize2 		 = ds2.getRecordCount();

		    	        
		    	        
		    	         for ( int k = 0; k < nSize2; k++ ) {
		    	            PosRecord record2 = ds2.getRecord(k);		    	            
		    	            

		    		        if ( record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		    		          || record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		    		        	
		    		        	record2.setAttribute("ATT_FILE_KEY", sAttFileKey);
	    		        		record2.setAttribute("TBL_NM", "TBRD_STOPPAY_CNCL_CNTNT");
	    		        		record2.setAttribute("INST_ID", SESSION_USER_ID);
	    		        	
	    		        		
		    		        	if ( SaveFile.updateFileMana(record2,this.getDao("rbmdao")) == 0 ) {
		    		        
		    		        		SaveFile.insertFileMana(record2,this.getDao("rbmdao"));
		    		        	}
		    			
		    		        	continue;
		    		        }
		    		        
		    	            if (record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		    	            {
		    	            
		    	            	SaveFile.deleteFileMana(record2,this.getDao("rbmdao"));	            	
		    	            }
		    	            
		    	        }
		        	
		            }
		            

		        	
	  		    	
	  		    }
	  		    
	            nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }
        Util.setSaveCount  (ctx, nSaveCount);     
       
    }
    /**
     * <p> 분실신고서접수 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateProcess(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
        param.setValueParamter(i++, record.getAttribute("ACDNT_TY_CD")	);		// 사고유형        
        param.setValueParamter(i++, record.getAttribute("REFUND_TY_CD")	);		// 환급유형
        if(  record.getAttribute("TIC_STAS").equals("002")	){
        	 param.setValueParamter(i++, Util.getCurrDate()		);				// 접수일자
        }else{
        	param.setValueParamter(i++, record.getAttribute("RCPT_DT")		);	// 접수일자
        }        
        param.setValueParamter(i++, record.getAttribute("RCPT_DEPT_NM")	);		// 접수자부서
        param.setValueParamter(i++, record.getAttribute("RCPT_ID")		);		// 접수자ID        
        param.setValueParamter(i++, record.getAttribute("REFUND_AMT")	);		// 환금금액
        param.setValueParamter(i++, record.getAttribute("RMK")			);		// 참고사항(비고) 
        param.setValueParamter(i++, record.getAttribute("CUST_NM")		);		// 성명      
        param.setValueParamter(i++, record.getAttribute("CUST_PHONE_NO"));		// 전화번호
        param.setValueParamter(i++, record.getAttribute("CUST_ADDR")	);		// 주소
        param.setValueParamter(i++, record.getAttribute("BIRTH_DAY")	);		// 고객생년월일        
        param.setValueParamter(i++, record.getAttribute("TIC_STAS")		);		// 티켓상태
       	param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_DEPT") );	// 해지요청자부서 
        param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_ID") );		// 해지요청자      
        param.setValueParamter(i++, SESSION_USER_ID						);		// 사용자ID(작성자)
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")		);		// 발행일자
		param.setValueParamter(i++, record.getAttribute("TSN_NO")		);		// TSN_NO
		
        int dmlcount = this.getDao("rbmdao").update("rsm6050_u01", param);
        
        return dmlcount;
    }    
    
    
    
    /**
     * <p> 지불정지에서 첨부파일 key저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateReqAttKey(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
    	
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		// 사고유형 
        
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO"));			// 발행일자
		param.setValueParamter(i++, record.getAttribute("TSN_NO"));				// TSN_NO
        
		int dmlcount = this.getDao("rbmdao").update("rsm6050_u03", param);
        
        return dmlcount;
    }
}
