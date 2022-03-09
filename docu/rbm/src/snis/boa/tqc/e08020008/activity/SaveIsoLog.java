/*================================================================================
 * 시스템			: TQC isolig 관리
 * 소스파일 이름	: snis.boa.system.e08020008.activity.SaveIsoLog.java
 * 파일설명		: 건수 등록
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2008-09-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.tqc.e08020008.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveIsoLog extends SnisActivity{
	
	public SaveIsoLog(){	
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
        sDsName = "dsLog";
        
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
        
        sDsName = "dsLog";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
	        		
		        	nTempCnt = insertIsoLog(record);

			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }	        
	        }	        
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
 protected int insertIsoLog(PosRecord record){
	 
	 PosParameter param = new PosParameter();   
     int i = 0;
     
     param.setValueParamter(i++, SESSION_USER_ID);
     param.setValueParamter(i++, record.getAttribute("MENU_ID"      ));	// WHERE
     param.setValueParamter(i++, SESSION_USER_NM);
     param.setValueParamter(i++, record.getAttribute("TEAM_CD"      ));

     param.setValueParamter(i++, SESSION_USER_ID);
     param.setValueParamter(i++, record.getAttribute("MENU_ID"      ));	// WHERE
     param.setValueParamter(i++, SESSION_USER_NM);
     param.setValueParamter(i++, record.getAttribute("TEAM_CD"      ));
     
     int dmlcount = this.getDao("boadao").update("tbtq_isolog_ia001", param);	        
     return dmlcount;
 }
}
