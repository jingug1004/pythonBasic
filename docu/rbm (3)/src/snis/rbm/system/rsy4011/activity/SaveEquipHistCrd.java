/*================================================================================
 * 시스템			: 장비이력   관리
 * 소스파일 이름	: snis.rbm.business.rsy4011.activity.SaveEquipHistCrd.java
 * 파일설명		: 장비이력  관리
 * 작성자			: 김은정
 * 버전			: 1.0.0
 * 생성일자		: 2011-08-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/

package snis.rbm.system.rsy4011.activity;

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

public class SaveEquipHistCrd extends SnisActivity {

	public SaveEquipHistCrd(){
		
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
        String sDAttFileKey ="";
        
        sDsName = "dsEquipHistCrd";
        
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

		        	
		        	if ( (nTempCnt = updateEquipHistCrd(record)) == 0 ) {
		        		nTempCnt = insertEquipHistCrd(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteEquipHistCrd(record);	            	
	            }		        
	        }	        
        }

        
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
			        					
			        					//장비이력정보 첨부파일 key update
			        					record.setAttribute("ATT_FILE_KEY",tmpFileKey);
			        					record.setAttribute("EQUIP_DIST_NO",ctx.get("EQUIP_DIST_NO"));
			        					record.setAttribute("EQUIP_HIST_SEQ",ctx.get("EQUIP_HIST_SEQ"));
			        					record.setAttribute("CHECK_EQUIP_GBN",ctx.get("CHECK_EQUIP_GBN"));
			        			        
			        			        
			        					updateEquipHistCrdAttKey(record);

			        			}
			        	
			        			record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        		}		       
			        			
			        	
		        		}
		        		
		        		
		        		record.setAttribute("TBL_NM", "TBRK_EQUIP_HIST_CRD");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
		        		nTempCnt = SaveFile.insertFileMana(record,this.getDao("rbmdao"));
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record,this.getDao("rbmdao"));	            	
	            }	
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 장비이력카드  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertEquipHistCrd(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO"));	//장비고유번호
        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO"));	//장비고유번호
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN"));//장비이력순번
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN"));//점검장비구분
        param.setValueParamter(i++, record.getAttribute("CHECK_DT"));		//점검일자

        param.setValueParamter(i++, record.getAttribute("CHECK_STAT_CD"));	//점검상태코드
        param.setValueParamter(i++, record.getAttribute("CHECK_TY_CD"));	//점검유형코드
        param.setValueParamter(i++, record.getAttribute("CHECK_CNTNT"));	//점검내용
        param.setValueParamter(i++, record.getAttribute("WORKER"));			//작업자
        param.setValueParamter(i++, record.getAttribute("ETC_CHECK_CNTNT"));//기타점검내용

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//첨부파일키
        param.setValueParamter(i++, SESSION_USER_ID);        
  
        
        int dmlcount = this.getDao("rbmdao").update("rsy4011_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 장비이력카드 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateEquipHistCrd(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("CHECK_DT"));		//점검일자
        param.setValueParamter(i++, record.getAttribute("CHECK_STAT_CD"));	//점검상태코드
        param.setValueParamter(i++, record.getAttribute("CHECK_TY_CD"));	//점검유형코드
        param.setValueParamter(i++, record.getAttribute("CHECK_CNTNT"));	//점검내용
        param.setValueParamter(i++, record.getAttribute("WORKER"));			//작업자

        param.setValueParamter(i++, record.getAttribute("ETC_CHECK_CNTNT"));//기타점검내용
        param.setValueParamter(i++,SESSION_USER_ID);						//수정자

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_DIST_NO" ));	//장비고유번호
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_HIST_SEQ" ));	//장비이력순번
        param.setWhereClauseParameter(i++, record.getAttribute("CHECK_EQUIP_GBN" ));//점검장비구분
        


        int dmlcount = this.getDao("rbmdao").update("rsy4011_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 장비이력카드  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEquipHistCrd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO"      ) );	//장비고유번호
        param.setValueParamter(i++, record.getAttribute("EQUIP_HIST_SEQ"      ) );	//장비이력순번
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN"      ) );	//점검장비구분
        
        int dmlcount = this.getDao("rbmdao").update("rsy4011_d01", param);

        return dmlcount;
    }
    
    
    
    /**
     * <p> 장비이력카드 첨부파일 정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateEquipHistCrdAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//첨부파일키
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("EQUIP_DIST_NO" ));		//장비고유번호
        param.setValueParamter(i++, record.getAttribute("EQUIP_HIST_SEQ" ));	//장비이력순번
        param.setValueParamter(i++, record.getAttribute("CHECK_EQUIP_GBN" ));	//점검장비구분
        


        int dmlcount = this.getDao("rbmdao").update("rsy4011_u03", param);
        return dmlcount;
    }

    

  
}
