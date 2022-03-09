/*================================================================================
 * 시스템			: TQC Simulation
 * 소스파일 이름	: snis.boa.tqc.e08010007.activity.SaveSimulationRate.java
 * 파일설명		: Simulation 관리
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2008-10-11
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.tqc.e08010007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveSimulationRate extends SnisActivity{

	public SaveSimulationRate(){
		
	}
	
	public String runActivity(PosContext ctx)
	    {   	
	    	// 사용자 정보 확인 
		 if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
	    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
	            return PosBizControlConstants.SUCCESS;
	    	}
	     
	        PosDataset ds;
	        int        nSize  = 0;
	        String     sDsName = "";
	        sDsName = "dsSimulList";
	        
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

	        sDsName = "dsSimulList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
  
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
					   || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
					        	nTempCnt = insertRate(record);
						        nSaveCount = nSaveCount + nTempCnt;
					        	continue;
					}	     
		        }        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    
		 protected int insertRate(PosRecord record){ 
			 PosParameter param = new PosParameter();   
		     int i = 0;
		     
		     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// WHERE
		     param.setValueParamter(i++, record.getAttribute("CD"    		  ));
		     param.setValueParamter(i++, record.getAttribute("SEQ"    		  ));
		     param.setValueParamter(i++, record.getAttribute("NEW_RATE"			  ));      
		     param.setValueParamter(i++, record.getAttribute("COMMENTS"			  ));   // UPDATE   
		     param.setValueParamter(i++, SESSION_USER_ID);
		     
		     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// INSERT
		     param.setValueParamter(i++, record.getAttribute("CD"    		  ));
		     param.setValueParamter(i++, record.getAttribute("SEQ"    		  ));
		     param.setValueParamter(i++, record.getAttribute("NEW_RATE"			  ));    
		     param.setValueParamter(i++, record.getAttribute("COMMENTS"			  )); 
		     param.setValueParamter(i++, SESSION_USER_ID);
		     
		     int dmlcount = this.getDao("boadao").update("tbtq_simulation_ia001", param);	        
		     return dmlcount;
		 }

}
