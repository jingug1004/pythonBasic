/*
 * ================================================================================
 * 시스템 : 지불정지 및 해지 저장
 * 파일 이름 : snis.rbm.business.rsm6020.activity.SaveStopPay.java 
 * 파일설명 :  
 * 작성자 : 
 * 버전 : 1.0.0 생성일자 : 2011- 09 - 16
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm6020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class UpdateStoppay extends SnisActivity {
	
	public UpdateStoppay(){}

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
        
        sDsName = "dsTsnResult_S";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	                    	
		        nTempCnt = UpdateTsn(record);	//TSN Update
		        		        	
			    nSaveCount = nSaveCount + nTempCnt;
		        continue;		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);       
    }
        
    /**
     * <p> 지불정지 수저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int UpdateTsn(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        int dmlcount=0;
            
        param.setValueParamter(i++, record.getAttribute("TIC_TYPE_CD"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("PLC_NO"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));        
        param.setValueParamter(i++, record.getAttribute("SALES_AMT"));        
        param.setValueParamter(i++, record.getAttribute("TIC_CONT"));
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC"));
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_ID"));
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_DEPT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO"));
        param.setValueParamter(i++, record.getAttribute("TSN_NO"));	
        
        dmlcount = this.getDao("rbmdao").update("rsm6020_u03", param);
        return dmlcount;

    }  
}
