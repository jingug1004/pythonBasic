/*
 * ================================================================================
 * 시스템 : 부품 관리 소스파일 
 * 이름 : snis.rbm.business.rbo4010.activity..java 
 * 파일설명 : 모델관리 
 * 작성자 : 장한너울
 * 버전 : 1.0.0 
 * 생성일자 : 2011-10-21
 * 최종수정일자 : 
 * 최종수정자 : 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo4010.activity;

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

public class SavePartsModel extends SnisActivity {
	public SavePartsModel(){
		
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
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
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
		int nSaveCount 	 = 0;
		int nDeleteCount = 0;
		String sDsName 	 = "";
		
		PosDataset ds;
		int nSize 		 = 0;
		int nTempCnt	 = 0;
		int nDelCnt		 = 0;
		
		int nUploadFileSize = 0;
		sDsName = "dsUploadFile";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		    = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize = ds.getRecordCount();
        }

        String sAttFileKey = "";
        
        
		sDsName = "dsList";
		
		if( ctx.get(sDsName) != null )
		{
			ds		= (PosDataset) ctx.get(sDsName);
			nSize 	= ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++ )
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
				  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
				{
					if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){
		        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);		        		
		        	}
					
					if ( (nTempCnt = updatePartsModel(record)) == 0 )
					{
						nTempCnt = insertPartsModel(record);
						nSaveCount = nSaveCount + nTempCnt;
					}
					continue;
				}
				else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
				{
					//첨부파일이 존재할 시, 첨부파일 삭제
	            	SaveFile.deleteFile(record, this.getDao("rbmdao"));
					nDeleteCount = nDeleteCount + deletePartsModel(record);
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
	        						
	        						// 첨부파일 Key update
	        						record.setAttribute("ATT_FILE_KEY", tmpFileKey);
	        						record.setAttribute("PARTS_CD", ctx.get("PARTS_CD"));
		        					
	        						updatePartsStrAttKey(record);
		        				}
		        				record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        			}
		        		}
		        		
		        		record.setAttribute("TBL_NM", "TBRB_PARTS_MODEL");
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
        
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
		
	}
    
    
    /**
     * <p> 모델관리 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int insertPartsModel(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_LCD"		));	// 1. 발매기코드
		param.setValueParamter(i++, record.getAttribute("PARTS_MCD"		));	// 2. 부품그룹코드
		param.setValueParamter(i++, record.getAttribute("PARTS_NM"		));	// 3. 부품명		
		param.setValueParamter(i++, record.getAttribute("MADE_GBN"		));	// 4. 생산지여부
		param.setValueParamter(i++, record.getAttribute("USE_YN"		));	// 5. 사용여부	

		param.setValueParamter(i++, SESSION_USER_ID					 	 );	// 7. 작성자ID

		int dmlcount = this.getDao("rbmdao").update("rbo4010_i01", param);
		
		return dmlcount;
	}
	
	
	/**
     * <p> 첨부파일 정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePartsStrAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          	        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY")	);
        param.setValueParamter(i++, SESSION_USER_ID					   	);
        param.setValueParamter(i++, record.getAttribute("PARTS_CD")		);

        int dmlcount = this.getDao("rbmdao").update("rbo4010_u02", param);
        return dmlcount;
    }
    
	
    /**
     * <p> 모델관리 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int updatePartsModel(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_LCD"	));	// 1. 발매기코드
		param.setValueParamter(i++, record.getAttribute("PARTS_MCD"	));	// 2. 부품그룹코드
		param.setValueParamter(i++, record.getAttribute("PARTS_NM"	));	// 3. 부품명		
		param.setValueParamter(i++, record.getAttribute("MADE_GBN"	));	// 4. 생산지여부
		param.setValueParamter(i++, record.getAttribute("USE_YN"	));	// 5. 사용여부		
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 6. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 7. 부품코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4010_u01", param);
		return dmlcount;
	}
	
	
    /**
     * <p> 모델관리 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int deletePartsModel(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. 부품코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4010_d01", param);
		return dmlcount;
	}
}
