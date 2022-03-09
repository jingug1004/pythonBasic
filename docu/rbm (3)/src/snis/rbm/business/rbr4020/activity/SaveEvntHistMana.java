/*================================================================================
 * 시스템			: 사건  관리
 * 소스파일 이름	: snis.rbm.business.rbr4020.activity.SaveEvntHistMana.java
 * 파일설명		: 사건이력 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-09
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr4020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
//import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEvntHistMana extends SnisActivity {
	
	public SaveEvntHistMana(){}

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
    	int nSize        = 0;
        int nTempCnt     = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
        
    	int nUploadFileSize = 0;
    	sDsName = "dsUploadFile";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		    = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize = ds.getRecordCount();
        }

        String sAttFileKey = "";
        
        sDsName = "dsEvntHistMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {	
		        	
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0) {
		        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        	}
		        	
		        	nTempCnt = saveEvntHistMana(record);		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	nDeleteCount = nDeleteCount + deleteEvntHistMana(record);	            	
	            	
	            	//사건이력에 대한 첨부파일이 존재할 시, 첨부파일 삭제
	            	SaveFile.deleteFile(record, this.getDao("rbmdao"));
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
		        		//등록시 첨부파일 키 생성 
		        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
		        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		} else {
		        			//첨부파일이 별도로 등록되는경우
		        			if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){ 			
		        				if(tmpFileKey == null || tmpFileKey.equals("")){
	        						tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
	        					
		        					//사건이력정보 첨부파일 key update
		        					record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        					record.setAttribute("BRNC_CD" ,ctx.get("BRNC_CD"));
		        					record.setAttribute("EVNT_SEQ",ctx.get("EVNT_SEQ"));
		        					record.setAttribute("SEQ"     ,ctx.get("SEQ"));

		        					updateEvntHistAttKey(record);
		        				}
		        				record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        			}
		        		}
		        		
		        		record.setAttribute("TBL_NM", "TBRA_EVNT_HIST_MANA");
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
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 사건이력 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveEvntHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));		//사건순번
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//순번
        param.setValueParamter(i++, record.getAttribute("GEN_DT"));			//발생일자	
        param.setValueParamter(i++, record.getAttribute("MNG"));			//담당자

        param.setValueParamter(i++, record.getAttribute("TITLE"));			//제목
		param.setValueParamter(i++, record.getAttribute("EVNT_CONT"));		//사건내용
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//첨부파일키
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)
        
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(수정자)
      
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));		//사건순번

        int dmlcount = this.getDao("rbmdao").update("rbr4020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 사건이력 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEvntHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수정자)
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));	//사건순번
        param.setValueParamter(i++, record.getAttribute("SEQ"));		//순번
        
        int dmlcount = this.getDao("rbmdao").update("rbr4020_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 사건이력 첨부파일 정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateEvntHistAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));

        int dmlcount = this.getDao("rbmdao").update("rbr4020_u02", param);
        return dmlcount;
    }
}