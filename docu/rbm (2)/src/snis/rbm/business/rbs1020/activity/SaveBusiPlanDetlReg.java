/*================================================================================
 * 시스템			: 사업품질관리
 * 소스파일 이름	: snis.rbm.business.rbs1020.activity.SaveBusiPlanDetlReg.java
 * 파일설명		: 사업계획상세등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBusiPlanDetlReg extends SnisActivity {
		public SaveBusiPlanDetlReg(){}
		
		
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
	        PosDataset dsFile;
	        int nFileSize        = 0;
	        
	        String sFileDsName = "dsUploadFile";
	        if ( ctx.get(sFileDsName) != null ) {
	        	dsFile   		 = (PosDataset) ctx.get(sFileDsName);
	        	
		        nUploadFileSize 	 = dsFile.getRecordCount();
	        }
		        
		        
	        //첨부파일 처음 등록시 첨부파일KEY 생성 
	        String sAttFileKey ="";
	        
	        sDsName = "dsList";
	        
	        
	        
	        if ( ctx.get(sDsName) != null ) {
	        	
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        for ( int i = 0; i < nSize; i++ ) {
		        	
		            PosRecord record = ds.getRecord(i);

		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		            	nTempCnt = deleteBusiPlanDetl(record);
		            } else {	
				      
		            	// 기존의 ATT_FILE_KEY 가  없으면, 새로운 ATT_FILE_KEY 를 구한다. 
		                Double dTempFileKey = (Double)record.getAttribute("ATT_FILE_KEY"); 
		                
		                System.out.println("dTempFileKey=" + dTempFileKey);
		                
		                if(dTempFileKey == null)
		                {
		                	sAttFileKey = getFileManaMaxKey();
			        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		                }    	
			        		System.out.println("sAttFileKey=" + sAttFileKey);
		                
			            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			            	nTempCnt = insertBusiPlanDetl(record);
			            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			            	nTempCnt = updateBusiPlanDetl(record);
			            }	
				        nSaveCount = nSaveCount + nTempCnt;
			        	
			        }    
		        }	        
	        }

	        
	        
	        sFileDsName = "dsUploadFile";
	        if ( ctx.get(sFileDsName) != null ) {
	        	dsFile   		 = (PosDataset) ctx.get(sFileDsName);
	        	nFileSize 		 = dsFile.getRecordCount();

		         for ( int i = 0; i < nFileSize; i++ ) {
		            PosRecord record = dsFile.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateFileMana(record)) == 0 ) {
			        		
			        		
			        		//신규등록시 첨부파일 키 생성 
			        		if(sAttFileKey != null && !sAttFileKey.equals("")){
			        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
			        		}
			        		
			        		nTempCnt = insertFileMana(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteFileMana(record);	            	
		            }	
		        }	         
	        }
	        
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    
	    /**
	     * <p> 사업계획상세등록 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateBusiPlanDetl(PosRecord record)
	    {		
					
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));           // 1. 부서코드 
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));            // 2. 담당자ID
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));        // 3. 회계구분코드	
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));           // 4. 사업명
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_PRPS"));   // 5. 사업내용목적
	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_SMRY"));   // 6. 사업내용개요
	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_STR"));       // 7. 사업일자 시작
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_END"));       // 8. 사업일자 종료
	        
	        param.setValueParamter(i++, record.getAttribute("QUAR_1"));            // 9.  1분기
	        param.setValueParamter(i++, record.getAttribute("QUAR_2"));            // 10. 2분기
	        param.setValueParamter(i++, record.getAttribute("QUAR_3"));            // 11. 3분기
	        param.setValueParamter(i++, record.getAttribute("QUAR_4"));            // 12. 4분기
	        
	        param.setValueParamter(i++, record.getAttribute("TRANS_AMT"));         // 13. 이월금액
	        param.setValueParamter(i++, record.getAttribute("ASS_PRO_AMT"));       // 14. 자산취득비
	        param.setValueParamter(i++, record.getAttribute("FAC_MANA_AMT"));      // 15. 시설관리 유지비
	        
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));      // 16. 첨부파일키
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 17. 사용자ID  
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 18. 시개금코드
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1020_u01", param);
	        return dmlcount;
	    }


	    /**
	     * <p> 사업계획상세계획 등록 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertBusiPlanDetl(PosRecord record)
	    {		
					
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));     // 1.년도
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_TMS"));   // 2.승인차수
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));    // 3.회계구분코드
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));       // 4.사업명
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_AMT"));   // 5.승인금액
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));       // 6.부서코드 
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));        // 7.담당자ID	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_PRPS"));   // 8. 사업내용목적	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_SMRY"));   // 9. 사업내용개요	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_STR"));       // 10. 사업일자 시작
	        param.setValueParamter(i++, record.getAttribute("BUSI_DT_END"));       // 11. 사업일자 종료	        
	        param.setValueParamter(i++, record.getAttribute("QUAR_1"));            // 12.  1분기
	        param.setValueParamter(i++, record.getAttribute("QUAR_2"));            // 13. 2분기
	        param.setValueParamter(i++, record.getAttribute("QUAR_3"));            // 14. 3분기
	        param.setValueParamter(i++, record.getAttribute("QUAR_4"));            // 15. 4분기	        
	        param.setValueParamter(i++, record.getAttribute("TRANS_AMT"));         // 16. 이월금액
	        
	        param.setValueParamter(i++, record.getAttribute("ASS_PRO_AMT"));       // 17. 자산취득비
	        param.setValueParamter(i++, record.getAttribute("FAC_MANA_AMT"));      // 18. 시설관리 유지비
	        
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));      // 19. 첨부파일키	        
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 20. 사용자ID
	       
	        int dmlcount = this.getDao("rbmdao").update("rbs1020_i01", param);
	        return dmlcount;
	    }


	    /**
	     * <p> 사업계획상세계획 삭제 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteBusiPlanDetl(PosRecord record)
	    {		
					
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 18. 시개금코드	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1020_d01", param);
	        return dmlcount;
	    }

	    
	    /**
	     * <p> 첨부파일  KEY 조회  </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected String getFileManaMaxKey() 
	    {
	        String rtnKey = "";
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs1020_s_filekey");        
	        	
	        PosRow pr[] = keyRecord.getAllRow();	     
	       
	        rtnKey = String.valueOf(pr[0].getAttribute("ATT_FILE_KEY"));
	   
	        return rtnKey;
	    }
	    
	    
	    /**
	     * <p> 첨부파일  입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertFileMana(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
	        param.setValueParamter(i++, "TBRE_BUSI_PLAN");
	        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
	        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));

	        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));
	        param.setValueParamter(i++, SESSION_USER_ID);  
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1020_i_file", param);
	        
	        
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 첨부파일 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateFileMana(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;          
	        
	        param.setValueParamter(i++, "TBRE_BUSI_PLAN");
	        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
	        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));
	        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));
	        
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY" ));
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));
	        


	        int dmlcount = this.getDao("rbmdao").update("rbs1020_u_file", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> 첨부파일  삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteFileMana(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"      ) );
	        param.setValueParamter(i++, record.getAttribute("SEQ"      ) );

	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1020_d_file", param);

	        return dmlcount;
	    }
	    
}
