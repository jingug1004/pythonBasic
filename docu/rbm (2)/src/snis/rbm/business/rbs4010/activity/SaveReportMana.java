/*
 * ================================================================================
 * 시스템 : 보고서 관리 
 * 소스파일 이름 : snis.rbm.business.rbs4010.activity.SaveReportMana.java 
 * 파일설명 : 보고서 관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011 - 10 -06
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbs4010.activity;

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

public class SaveReportMana extends SnisActivity {

	public SaveReportMana(){
		
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
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize 	 = ds.getRecordCount();
        }
   
	        
        //첨부파일 처음 등록시 첨부파일KEY 생성 
        String sMAttFileKey ="";
        
        
        sDsName = "dsReportMana";
        String sMaxSeq = ""; 
        	
        
        /*   보고서저장    */
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){
		        		sMAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sMAttFileKey);		
		        	}

		        	
		        	if ( (nTempCnt = updateReportMana(record)) == 0 ) {
		        		
		        		sMaxSeq = getReportManaMaxKey(record);
		        		
		        		record.setAttribute("SEQ", sMaxSeq);
		        		
		        		nTempCnt = insertReportMana(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteReportMana(record);	            	
	            }		        
	        }	        
        }

        /*   첨부파일  저장    */
        sDsName = "dsUploadFile";
        
        String tmpFileKey = "";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = SaveFile.updateFileMana(record,this.getDao("rbmdao"))) == 0 ) {
		        		
		        		//처음 첨부파일 등록시  첨부파일 키 생성
		        		//신규등록시 첨부파일 같이 등록 된 경우 
		        		if(sMAttFileKey != null  && !sMAttFileKey.equals("")){
		        			
		        			record.setAttribute("ATT_FILE_KEY", sMAttFileKey);
		        		}else{
		        		
	
			        		//첨부파일이 별도로 등록되는경우
			        		if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){
			        			
			        			
			        			if(tmpFileKey == null || tmpFileKey.equals("")){
			        					tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
			        					
			        					
			        					record.setAttribute("ATT_FILE_KEY",tmpFileKey);
			        					
			        					record.setAttribute("DEPT_CD",ctx.get("DEPT_CD"));
			        					record.setAttribute("STND_YEAR",ctx.get("STND_YEAR"));
			        					
			        					
			        					if(!sMaxSeq.equals("")){
			        						record.setAttribute("SEQ",sMaxSeq);
			        					}else{
			        						record.setAttribute("SEQ",ctx.get("SEQ"));
			        					}
			        					
		
			        					updateReportManaAttKey(record);

			        			}
			        	
			        			record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        		}		       
			        			
			        	
		        		}

		        		record.setAttribute("TBL_NM", "TBRE_REPORT_MANA");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
		        		nTempCnt = SaveFile.insertFileMana(record,this.getDao("rbmdao"));
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	record.setAttribute("FILE_DEL_YN","N");
	            	
		        	nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record,this.getDao("rbmdao"));	            	
	            }
	            
	        }
	         
	         
         /*   보고서담당자 저장    */
         sDsName = "dsReportMng";
         
         if ( ctx.get(sDsName) != null ) {
 	        ds   		 = (PosDataset) ctx.get(sDsName);
 	        nSize 		 = ds.getRecordCount();

 	        for ( int i = 0; i < nSize; i++ ) {
 	            PosRecord record = ds.getRecord(i);

 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 
 		        	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
 		        	
	        		if(!sMaxSeq.equals("")){
	        			record.setAttribute("SEQ", sMaxSeq);
	        		}
	        		
 		        	nTempCnt = insertReportMng(record);
 		        	
 			        nSaveCount = nSaveCount + nTempCnt;
 		        	continue;
 		        }
 		        
 	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
 	            {
 		        	nDeleteCount = nDeleteCount + deleteReportMng(record);	            	
 	            }		        
 	        }	        
         }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * <p> 보고서  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertReportMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//기준년도
        
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//SEQ

        param.setValueParamter(i++, record.getAttribute("REQ_DT"));			//요청일자
        param.setValueParamter(i++, record.getAttribute("SUBM_LIMIT_DT"));	//제출기한일자

        param.setValueParamter(i++, record.getAttribute("REPORT_NM"));		//보고서명
        param.setValueParamter(i++, record.getAttribute("INST_GUIDE"));		//작성지침
        param.setValueParamter(i++, record.getAttribute("SAMP_YN"));		//양식여부
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//첨부파일키
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고

        param.setValueParamter(i++, SESSION_USER_ID);        				//작성자
  
        
        int dmlcount = this.getDao("rbmdao").update("rbs4010_i01", param);
        
        return dmlcount;
    }

    /**
     * <p> 보고서 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateReportMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));				//요청일자
        param.setValueParamter(i++, record.getAttribute("SUBM_LIMIT_DT"));		//제출기한일자
        param.setValueParamter(i++, record.getAttribute("REPORT_NM"));			//보고서명칭
        param.setValueParamter(i++, record.getAttribute("INST_GUIDE"));			//작성지침
        param.setValueParamter(i++, record.getAttribute("SAMP_YN"));			//양식여부

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//첨부파일키
        param.setValueParamter(i++, record.getAttribute("RMK"));				//비고
        param.setValueParamter(i++,SESSION_USER_ID);							//수정자

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));	//년도
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));			//순번
        

        int dmlcount = this.getDao("rbmdao").update("rbs4010_u01", param);
        return dmlcount;
    }

    /**
     * <p> 보고서  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteReportMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DEPT_CD") );		//부서코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR") );		//년도
        param.setValueParamter(i++, record.getAttribute("SEQ") );			//순번
        
        int dmlcount = this.getDao("rbmdao").update("rbs4010_d01", param);

        return dmlcount;
    }

    /**
     * <p> 보고서 첨부파일 정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateReportManaAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//첨부파일키
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("DEPT_CD" ));			//부서코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));			//년도
        param.setValueParamter(i++, record.getAttribute("SEQ" ));				//순번
        
        int dmlcount = this.getDao("rbmdao").update("rbs4010_u03", param);
        return dmlcount;
    }

    /**
     * <p> 보고서담당자  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertReportMng(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//부서코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//년도
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//순번
        param.setValueParamter(i++, record.getAttribute("MNG_ID"));		//담당자ID
        param.setValueParamter(i++, record.getAttribute("MNG_DEPT_CD"));//담당자부서코드

        int dmlcount = this.getDao("rbmdao").update("rbs4010_m01", param);
        
        return dmlcount;
    }

    /**
     * <p> 보고서담당자  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteReportMng(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//부서코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//년도
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//순번
        param.setValueParamter(i++, record.getAttribute("MNG_ID"));		//담당자ID
        
                		
        int dmlcount = this.getDao("rbmdao").update("rbs4010_d02", param);

        return dmlcount;
    }  
    
    
    
    /**
     * <p> 보고서 MAX KEY 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getReportManaMaxKey(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));	//년도

                		
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs4010_s03",param);        
       
        
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("SEQ"));	//첨부파일키
   
        return rtnKey;
    }
	
}
