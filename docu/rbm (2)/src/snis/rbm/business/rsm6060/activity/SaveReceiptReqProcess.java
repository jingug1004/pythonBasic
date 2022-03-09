package snis.rbm.business.rsm6060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveReceiptReqProcess extends SnisActivity {

	
	public SaveReceiptReqProcess(){}

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
        int nTempCnt     = 0;
        int nSize		 = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
    	PosDataset ds2;
        

        sDsName = "dsTsnRcptResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	                	
	  		    nTempCnt  = updateProcess(record);	//해지요청	    
	  		    
	            nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }
        Util.setSaveCount  (ctx, nSaveCount);     
       
    }
    /**
     * <p> 분실신고서접수 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateProcess(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
    
        param.setValueParamter(i++, record.getAttribute("TIC_STAS")		);		// 티켓상태
       	param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_DEPT") );	// 해지요청자부서 
        param.setValueParamter(i++,  record.getAttribute("CNCL_REQ_ID") );		// 해지요청자      
        param.setValueParamter(i++, SESSION_USER_ID						);		// 사용자ID(작성자)
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")		);		// 발행일자
		param.setValueParamter(i++, record.getAttribute("TSN_NO")		);		// TSN_NO
		
        int dmlcount = this.getDao("rbmdao").update("rsm6060_u01", param);
        
        return dmlcount;
    }    
    
    
    
   
}
