/*================================================================================
 * 시스템			: TQC 코드 관리
 * 소스파일 이름	: snis.boa.system.e08010001.activity.SaveTqcCode.java
 * 파일설명		: 코드 관리
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2008-09-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.tqc.e08010001.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveTqcCode extends SnisActivity{

	public SaveTqcCode(){
		
	}
	 public String runActivity(PosContext ctx)
	    {   	
	    	// 사용자 정보 확인
	    	
		 if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
	    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
	            return PosBizControlConstants.SUCCESS;
	    	}
	     
	        PosDataset ds;
	        int        nSize        = 0;
	        String     sDsName = "";
	        sDsName = "dsPartCodeListValue";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo(record);
		        }
	        }
		        
	        sDsName = "dsSpecCodeListValue";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo(record);
		        }
	        }
		        
	        saveState(ctx);

	        return PosBizControlConstants.SUCCESS;
	    }
	    protected void saveState(PosContext ctx) 
	    {
	    	int nSaveCount   = 0; 
	    	int nDeleteCount = 0; 
	    	String sDsName   = "";
	    	
	    	PosDataset ds;
	        int nSize        = 0;
	        int nTempCnt     = 0;

	        sDsName = "dsPartCodeListValue";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updatePartCode(record)) == 0 ) {
			        		nTempCnt = insertPartCode(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deletePartCode(record);	            	
		            }		        
		        }
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deletePartCode(record);	            	
		            }		        
		        }	        
	        }

	        sDsName = "dsSpecCodeListValue";
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateSpecCode(record)) == 0 ) {
			        		nTempCnt = insertSpecCode(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
		        }
		         
		         for ( int i = 0; i < nSize; i++ ) {
			            PosRecord record = ds.getRecord(i);

			            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			            {
				        	nDeleteCount = nDeleteCount + deleteSpecCode(record);	            	
			            }		        
			        }	         
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    protected int insertPartCode(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("PART_CD"));
	        param.setValueParamter(i++, record.getAttribute("PART_NM"));
	        param.setValueParamter(i++, "N");
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, record.getAttribute("RMK"));
	        
	        int dmlcount = this.getDao("boadao").update("tbtq_part_cd_ia001", param);	        
	        return dmlcount;
	    }
	    protected int updatePartCode(PosRecord record) 
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("PART_NM"      ));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, record.getAttribute("RMK"));
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PART_CD" ));

	        int dmlcount = this.getDao("boadao").update("tbtq_part_cd_ua001", param);
	        return dmlcount;
	    }
	    protected int deletePartCode(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PART_CD" ));
	        
	        int dmlcount = this.getDao("boadao").update("tbtq_part_cd_da001", param);
	        return dmlcount;
	    }
	    protected int insertSpecCode(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("CD"));
	        param.setValueParamter(i++, record.getAttribute("CD_NM"));
	        param.setValueParamter(i++, record.getAttribute("USE_YN"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, record.getAttribute("RMK"));
	        param.setValueParamter(i++, record.getAttribute("PART_CD"));
	        param.setValueParamter(i++, record.getAttribute("BRANCH"));
	        param.setValueParamter(i++, record.getAttribute("ORD_NO"));
	        param.setValueParamter(i++, record.getAttribute("CONTENTS"));
	        int dmlcount = this.getDao("boadao").update("tbtq_spec_cd_ia001", param);
	        
	        return dmlcount;
	    }
	    protected int updateSpecCode(PosRecord record) 
	    {
	    	
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("CD_NM"      ));
	        param.setValueParamter(i++, record.getAttribute("USE_YN"     ));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, record.getAttribute("RMK"));
	        param.setValueParamter(i++, record.getAttribute("PART_CD"));
	        param.setValueParamter(i++, record.getAttribute("BRANCH"));
	        param.setValueParamter(i++, record.getAttribute("ORD_NO"));
	        param.setValueParamter(i++, record.getAttribute("CONTENTS"));
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

	        int dmlcount = this.getDao("boadao").update("tbtq_spec_cd_ua001", param);
	        
	        return dmlcount;
	    }
	    protected int deleteSpecCode(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

	        int dmlcount = this.getDao("boadao").update("tbtq_spec_cd_da001", param);
	        
	        return dmlcount;
	    }
}
