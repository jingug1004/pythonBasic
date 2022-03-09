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


public class SaveStoppay extends SnisActivity {
	
	public SaveStoppay(){}

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
    	
    	String strErr = saveState(ctx);
    	if( strErr.equals("F") ){    	
    
    		return PosBizControlConstants.FAILURE;
    	}else{
    		return PosBizControlConstants.SUCCESS;
    	
    	}
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected String saveState(PosContext ctx) 
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
	            
		        	// 기존에 해당 발행일자와 tns no가존재하는지 체크
		        	if (selectTnsCnt(record) > 0 ) {
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "입력하신 TSN는 해당 발행일자에 존재합니다!");
		            		return "F";
		            	}
		        	}
		        	
			        
		        	if(record.getAttribute("MISS_REPO_CD").equals("001") && record.getAttribute("RCPT_STAT_CD").equals("002")){
		        		//온라인 접수 건 자료존재 처리 
		        		UpdateStoppayOnline(record);
		        	}
		        	
		        	nTempCnt = InsertTsn(record);	//TSN Insert
		        		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
        return "";
    }
    /**
     * <p> 기본키에 대한 소모품 대장 건수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			
     * @throws  none
     */
    protected int selectTnsCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("MISS_REPO_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("TSN_NO"));        
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm6020_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        return Integer.valueOf(rtnKey).intValue();
    }
    
    /**
     * <p> 분실신고번호조회(시퀀스) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			
     * @throws  none
     */
    protected String getMissRepoNo() 
    {         
        String rtnKey = "";
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm6020_s04");
        
        PosRow pr[] = keyRecord.getAllRow();        
        rtnKey = String.valueOf(pr[0].getAttribute("MISS_REPO_NO"));
        return rtnKey;
    }
    
    
    /**
     * <p> 지불정지 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertTsn(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        int dmlcount=0;

	
        param.setValueParamter(i++, record.getAttribute("PUBL_DT"));
        param.setValueParamter(i++, record.getAttribute("TSN_NO"));	
        param.setValueParamter(i++, record.getAttribute("TIC_TYPE_CD"));
        param.setValueParamter(i++, record.getAttribute("PLC_NO"));	        
        param.setValueParamter(i++, record.getAttribute("SALES_AMT"));        
        param.setValueParamter(i++, record.getAttribute("STOPPAY_REQ_DEPT"));
        param.setValueParamter(i++, record.getAttribute("STOPPAY_REQ_NM"));    
        param.setValueParamter(i++, SESSION_USER_ID); // 정지자        
        param.setValueParamter(i++, Util.getCurrDate());        
        
        param.setValueParamter(i++, record.getAttribute("TIC_STAS"));        
        param.setValueParamter(i++, record.getAttribute("TIC_CONT"));
        
        param.setValueParamter(i++, record.getAttribute("VERI_RSLT_CD"));
        param.setValueParamter(i++, record.getAttribute("VERI_RSLT_DT"));  
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_CD"));        
        if( record.getAttribute("MISS_REPO_CD").equals("001")){ 
        	 	// 온라인
        	 param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO") );        	  		
             	
        }else{ 	// 오프라인
        	 param.setValueParamter(i++, getMissRepoNo() );        	 
        }
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        
        //온라인 고객정보
        param.setValueParamter(i++, record.getAttribute("CUST_NM"));		//고객명
        param.setValueParamter(i++, record.getAttribute("CUST_PHONE_NO"));	//고객번호
        
        
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC"));		//특이사항
        
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_ID"));		//특이사항
        param.setValueParamter(i++, record.getAttribute("CNCL_REQ_DEPT"));		//특이사항
        
        
        param.setValueParamter(i++, SESSION_USER_ID); // 등록자
        dmlcount = this.getDao("rbmdao").update("rsm6020_i01", param);
        return dmlcount;

    }  
    
    
    /**
     * <p> 온라인 접수 건 자료존재 처리 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int UpdateStoppayOnline(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

    	param.setValueParamter(i++, "005"		);

        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MISS_REPO_NO")	);
        
        int dmlcount = this.getDao("rbmdao").update("rsm6020_u04", param);
	
        return dmlcount;
    }
}
