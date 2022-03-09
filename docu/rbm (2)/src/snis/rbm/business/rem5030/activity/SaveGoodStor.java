/*================================================================================
 * 시스템			: 지정좌석실 관리
 * 소스파일 이름	: snis.rbm.business.rem5030.activity.SaveGoodStor.java
 * 파일설명		: 지정좌석실 상품 출고 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2013-08-16
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem5030.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


public class SaveGoodStor extends SnisActivity {
	
	public SaveGoodStor(){
		
		
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

        sDsName = "dsGoodStor";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveGoodStor(record);
		        } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
		        	// 출고내역이 존재하는 지 여부 체크
		        	int nDeleteValid = CountUnstor(record);
	            	
	            	//출고내역이 존재하는 경우 삭제 불가능
	            	if( nDeleteValid > 0) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_C009");
		            		
		            		return;
		            	} 
	            	}
		        	nDeleteCount += deleteGoodStor(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> IP 입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveGoodStor(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("STOR_DT"));	//입고일자
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));		//연번
        param.setValueParamter(i++, record.getAttribute("GOOD_NM"));	//상품명
        param.setValueParamter(i++, record.getAttribute("GOOD_TYPE_CD"));	//상품분류코드
        param.setValueParamter(i++, record.getAttribute("PRICE"));			//단가
        param.setValueParamter(i++, record.getAttribute("STOR_CNT"));		//입고수량
        param.setValueParamter(i++, record.getAttribute("STOR_USER_ID"));	//입고자 사번
        param.setValueParamter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO"));			//단가계약물품 연번
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, record.getAttribute("BALANC_CNT"));		//현재수량
        param.setValueParamter(i++, SESSION_USER_ID);						//입력자/수정자 사번
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("STOR_DT"));	//입고일자
        
        int dmlcount = this.getDao("rbmdao").update("rem5030_m01", param);
        
        String sCommNo = (String)record.getAttribute("COMM_NO");
        
        if ("04".equals(sCommNo) || "05".equals(sCommNo)) {
        	// 입고내역을 광명창고에서 출고로 처리
            param = new PosParameter();               
            i = 0;
            param.setValueParamter(i++, "00");		//지점코드
            param.setValueParamter(i++, SESSION_USER_ID);						//입고자 사번
            param.setValueParamter(i++, SESSION_USER_ID);						//입고자 사번
            param.setValueParamter(i++, record.getAttribute("STOR_DT"));		//입고일자
            param.setValueParamter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO"));			//단가계약물품 연번
            
            dmlcount += this.getDao("rbmdao").update("rem5040_m03", param);
        }
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> IP  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteGoodStor(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	
        param.setValueParamter(i++, record.getAttribute("STOR_DT"));
        param.setValueParamter(i++, record.getAttribute("SEQ_NO" ));
        
        
        int dmlcount = this.getDao("rbmdao").update("rem5030_d01", param);
        
        String sCommNo = (String)record.getAttribute("COMM_NO");
        if ("04".equals(sCommNo) || "05".equals(sCommNo)) {
        	// 삭제내역을 광명창고의 재고에서 수량 윈위치
            param = new PosParameter();               
            i = 0;
            param.setValueParamter(i++, "00");		//지점코드
            param.setValueParamter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO"));			//단가계약물품 연번
            param.setValueParamter(i++, record.getAttribute("STOR_DT"));		//입고일자
            param.setValueParamter(i++, record.getAttribute("STOR_CNT"));		//입고수량
            param.setValueParamter(i++, SESSION_USER_ID);						//입고자 사번
            param.setValueParamter(i++, SESSION_USER_ID);						//입고자 사번
            
            dmlcount += this.getDao("rbmdao").update("rem5040_m04", param);
         }
        return dmlcount;
    }
    

    /**
     * <p> 출고내역 존재 여부 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			사건에 걸려있는 사건이력 개수
     * @throws  none
     */
    protected int CountUnstor(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        PosRowSet keyRecord = null;
        String bYearItemYN = "N";

        Double dYearItemSeqNo = Double.valueOf(0.0);
        dYearItemSeqNo = (Double)record.getAttribute("YEAR_ITEM_SEQ_NO");
        System.out.println("sYearItemSeqNo="+dYearItemSeqNo);
        
        if (dYearItemSeqNo != null) 
        	if (dYearItemSeqNo.doubleValue() > 0.0) bYearItemYN = "Y";
        
        if ("Y".equals(bYearItemYN)) {		//단가계약품목인 경우
	        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));	//지점코드
	        param.setWhereClauseParameter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO" ));	    //입고연번
	        param.setWhereClauseParameter(i++, record.getAttribute("STOR_DT" ));	//입고일자
	        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));	//지점코드
	        param.setWhereClauseParameter(i++, record.getAttribute("YEAR_ITEM_SEQ_NO" ));	    //입고연번
	        
	        keyRecord = this.getDao("rbmdao").find("rem5030_s03", param);
        } else {
	        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));	//지점코드
	        param.setWhereClauseParameter(i++, record.getAttribute("STOR_DT" ));	//입고일자
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));	    //입고연번
	        
	        keyRecord = this.getDao("rbmdao").find("rem5030_s02", param);
        }
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}
