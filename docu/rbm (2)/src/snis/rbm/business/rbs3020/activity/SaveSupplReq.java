/*================================================================================
 * 시스템			: 소모품 신청
 * 소스파일 이름	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * 파일설명		: 소모품 신청내역을 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs3020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplReq extends SnisActivity {
	
	public SaveSupplReq(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsSupplReq";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveSupplReq(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {		       
	            	int nDeleteValid = selectProgStatCd(record);
	            	
	            	//소모품신청 삭제 항목 중 승인여부가  '승인'이  존재할 시, 삭제 불가능
	            	if( nDeleteValid > 0 ) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_E012");
		            		
		            		return;
		            	} 
	            	}
	            	nDeleteCount = nDeleteCount + deleteSupplReq(record);
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 소모품신청 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSupplReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //신청일자
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   		//신청자사번
        param.setValueParamter(i++, record.getAttribute("SEQ"));		   //순번
        param.setValueParamter(i++, record.getAttribute("REQ_CNTNT"));	   //신청내용
        param.setValueParamter(i++, record.getAttribute("PROG_STAT_CD"));  //승인여부
        
        param.setValueParamter(i++, record.getAttribute("REPLY"));		   //답변
        param.setValueParamter(i++, record.getAttribute("MNG_REQ_YN"));	   //관리자신청여부
        param.setValueParamter(i++, record.getAttribute("APRV_DT"));	   //승인일자
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));	   //경륜,경정 구분
        param.setValueParamter(i++, SESSION_USER_ID);					   //사용자ID(작성자)       
        param.setValueParamter(i++, SESSION_USER_ID);					   //사용자ID(수정자)
                		
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //신청일자
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   		//신청자사번
        int dmlcount = this.getDao("rbmdao").update("rbs3020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 소모품신청 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSupplReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	    //신청일자
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));   //신청자사번
        param.setValueParamter(i++, record.getAttribute("SEQ"));        //순번
        
        int dmlcount = this.getDao("rbmdao").update("rbs3020_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 승인여부 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			사건에 걸려있는 사건이력 개수
     * @throws  none
     */
    protected int selectProgStatCd(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT" ));	    //신청일자
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID" ));	//신청자사번
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));	    //순번
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3020_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}