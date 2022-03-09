/*================================================================================
 * 시스템			: 입장거부자  관리
 * 소스파일 이름	: snis.rbm.business.rem1010.activity.SaveEntRefuMana.java
 * 파일설명		: 입장거부자 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
//import com.posdata.glue.dao.vo.PosRow;
//import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEntRefuMana extends SnisActivity {
	
	public SaveEntRefuMana(){}

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
        int nDeleteValid;
        
    	String sDsName   = "";
    	PosDataset ds;
    	
    	int nUploadFileSize = 0;
    	sDsName = "dsUploadFile";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		    = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize = ds.getRecordCount();
        }

        String sAttFileKey = "";        
        sDsName = "dsEntRefuMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){
		        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);		        		
		        	}
		        	
		        	nTempCnt   = saveEntRefuMana(record);	        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteEntRefuMana(record);
		        	
		        	//입장거부자에 대한 첨부파일(이미지)이 존재할 시, 첨부파일 삭제
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
	        		
	            	if ( SaveFile.updateFileMana(record,this.getDao("rbmdao")) == 0 ) {
		        		//등록시 첨부파일 키 생성 
		        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
		        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		} else {
			        		//첨부파일이 별도로 등록되는경우
			        		if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){
			        			if(tmpFileKey == null || tmpFileKey.equals("")){
		        					tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        					
		        					//입장거부자 이미지 첨부파일 key update
		        					record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        					record.setAttribute("BRNC_CD"     ,ctx.get("BRNC_CD"));
		        					record.setAttribute("SEQ"         ,ctx.get("SEQ"));

		        					updateEntRefuAttKey(record);
			        			}
			        			record.setAttribute("ATT_FILE_KEY", tmpFileKey);
			        		}
		        		}
		        		
		        		record.setAttribute("TBL_NM", "TBRC_ENT_REFU_MANA");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
		        		nTempCnt = SaveFile.insertFileMana(record, this.getDao("rbmdao"));
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
     * <p> 입장거부자 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveEntRefuMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//지점코드
        param.setValueParamter(i++, record.getAttribute("SEQ"));				//순번
        param.setValueParamter(i++, record.getAttribute("TMS"));				//회차
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));			//경주일자	
        param.setValueParamter(i++, record.getAttribute("ENT_REFU_CD"));		//입장거부코드

        param.setValueParamter(i++, record.getAttribute("ENT_REFU_TY_CD"));		//입장거부유형코드
		param.setValueParamter(i++, record.getAttribute("SEX"));				//성별
        param.setValueParamter(i++, record.getAttribute("AGE_CD"));				//연령대코드
        param.setValueParamter(i++, record.getAttribute("ENT_REFU_TERM_FROM"));	//입장거부기간FROM
        param.setValueParamter(i++, record.getAttribute("ENT_REFU_TERM_TO"));	//입장거부기간_to
        
        param.setValueParamter(i++, record.getAttribute("FACE_DESC"));			//인상착의
        param.setValueParamter(i++, record.getAttribute("DTL_CNTNT"));			//주요내용
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));		//첨부파일키
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(수정자)
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//지점코드

        int dmlcount = this.getDao("rbmdao").update("rem1010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 입장거부자 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEntRefuMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//순번
        
        int dmlcount = this.getDao("rbmdao").update("rem1010_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 입장거부자 이미지 파일 정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateEntRefuAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, SESSION_USER_ID);

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));

        int dmlcount = this.getDao("rbmdao").update("rem1010_u02", param);
        return dmlcount;
    }
    
    
}
