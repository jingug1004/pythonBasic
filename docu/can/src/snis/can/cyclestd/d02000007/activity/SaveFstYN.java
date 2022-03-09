/*================================================================================
 * 시스템			: 1차 선발시험  합격
 * 소스파일 이름	: snis.can.system.d02000007.activity.SaveFstYN.java
 * 파일설명		: 1차 선발시험 관리
 * 작성자			: 
 * 버전			: 1.0.0
 * 생성일자		: 2008-05-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SaveFstYN extends SnisActivity{

	 public SaveFstYN(){}
	
	 public String runActivity(PosContext ctx)
	{
	
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	    PosDataset ds;
	    int        nSize        = 0;
	    String     sDsName      = "";
	    
	    sDsName = "dsPerioExam3";
	    if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
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
	        sDsName = "dsPerioExam3";
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);         
		                    
		                      
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) 
		            {
		            	nTempCnt = updateSaveFstYN(record);
		    	    }
		        
		            nSaveCount += nTempCnt;
		           		         
		        } // end for
	        }     // end if

	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	 
	  protected int updateSaveFstYN(PosRecord record) 
	    {
	   	    logger.logInfo("==========================  1차 선발시험 합격여부   ============================");
	                 
	   	    PosParameter param = null;
	        int i = 0;
	        int dmlcount = 0;

        	param = new PosParameter();
        	param.setValueParamter(i++, record.getAttribute("SUCCESS_YN"));
	        param.setValueParamter(i++, record.getAttribute("CAND_NO")); 
	        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO")); 
			dmlcount = this.getDao("candao").update("tbdb_perio_exam_fst_ub002", param);

	        return dmlcount;
	    }
}
