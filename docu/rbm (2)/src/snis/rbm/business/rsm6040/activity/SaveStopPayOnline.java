/*
 * ================================================================================
 * 시스템 : 분실신고서 온라인접수
 * 파일 이름 : snis.rbm.business.rsm6040.activity.SaveStopPayOnline.java 
 * 파일설명 :  
 * 작성자 : 
 * 버전 : 1.0.0 생성일자 : 2011- 10 - 23
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm6040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveStopPayOnline extends SnisActivity {
	
	public SaveStopPayOnline(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsTsnRcptResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            //접수취소로 바뀔 때
	            if( !record.getAttribute("RCPT_STAT_CD").equals("002") ) {
	            	if( getCancelYn(record).equals("N") ) {
	            		try { 
		        			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setReturnParam(ctx, "RESULT", "F");
		            		Util.setSvcMsg(ctx, "해당 자료는 이미 처리중입니다.");
		            		
		            		return;
		            	}
	            	}
	            }
	            
	            nTempCnt = UpdateStoppayRcptOnline(record);	//접수상태 및 티켓내용 업데이트	        	
			    nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }
        
        Util.setReturnParam(ctx, "RESULT", "S");
        Util.setSaveCount  (ctx, nSaveCount);
       
    }
    
   
    /**
     * <p> //접수상태 및 티켓내용 업데이트 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int UpdateStoppayRcptOnline(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
		param.setValueParamter(i++, record.getAttribute("ACDNT_TY_CD")	);
    	param.setValueParamter(i++, record.getAttribute("REFUND_TY_CD")	);
    	param.setValueParamter(i++, record.getAttribute("PUBL_DT")		);
    	param.setValueParamter(i++, record.getAttribute("BRNC_CD")		);
    	param.setValueParamter(i++, record.getAttribute("PLC_NO")		);
    	param.setValueParamter(i++, record.getAttribute("RACE_NO")		);
    	param.setValueParamter(i++, record.getAttribute("BUY_CNTNT")	);
    	param.setValueParamter(i++, record.getAttribute("REFR_DESC")	);
    	param.setValueParamter(i++, record.getAttribute("RCPT_STAT_CD")	);
    	param.setValueParamter(i++, record.getAttribute("TIC_CNTNT")	);
    	// 고객등록으로 환원할때는 접수일자를 지워준다.
    	if( record.getAttribute("RCPT_STAT_CD").equals("001")){
    		param.setValueParamter(i++, ""	);
    	}else if( record.getAttribute("RCPT_STAT_CD").equals("002")){
    		param.setValueParamter(i++, Util.getCurrDate()	);
    	}else{
    		param.setValueParamter(i++, record.getAttribute("RCPT_DT")	);
    	}
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")	);
        
        int dmlcount = this.getDao("rbmdao").update("rsm6040_u01", param);
	
        return dmlcount;
    }
    
    /**
     * <p> 화면 개시 여부 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getCancelYn(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("MISS_REPO_NO"));
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm6040_s02", param);        
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CANCEL_YN"));

        return rtnKey;
    }
}
