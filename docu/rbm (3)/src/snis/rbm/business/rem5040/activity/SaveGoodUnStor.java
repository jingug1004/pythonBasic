/*================================================================================
 * 시스템			: 지정좌석실 관리
 * 소스파일 이름	: snis.rbm.business.rem5040.activity.saveGoodUnStor.java
 * 파일설명		: 지정좌석실 상품 출고 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2013-08-16
 * 최종수정일자	: 
 * 최종수정자		:  
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem5040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveGoodUnStor extends SnisActivity {
	
	public SaveGoodUnStor(){		 
		
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
    	String sUnStorDt = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsGoodUnStor";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        sUnStorDt    = (String)ctx.get("UNSTOR_DT");

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
		        	
		        	// 잔고  재확인(조회후 동시에 2곳에서 입력하는 경우 있음)
	            	if(!CheckBalance(record)) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_C010");
		            		
		            		return;
		            	} 
	            	}
	            	
			        nSaveCount += saveGoodUnStor(record, sUnStorDt);
			        updateGoodStor(record);
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
    protected int saveGoodUnStor(PosRecord record, String UnStorDt) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("STOR_DT"));		//입고일자
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));			//연번
        param.setValueParamter(i++, UnStorDt);								//출고일자
        param.setValueParamter(i++, record.getAttribute("UNSTOR_CNT"));		//출고수량
        param.setValueParamter(i++, record.getAttribute("DISUSE_CNT"));		//폐기수량
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, "001");									//상태
        param.setValueParamter(i++, SESSION_USER_ID);						//입고자 사번
        param.setValueParamter(i++, record.getAttribute("APRV_ID"));		//승인자 사번
        param.setValueParamter(i++, null);		//승인일시
        param.setValueParamter(i++, SESSION_USER_ID);						//입력자/수정자 사번
        
        int dmlcount = this.getDao("rbmdao").update("rem5040_m01", param);
        
        
        return dmlcount;
    }
    


    /**
     * <p> IP 입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateGoodStor(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);						//입력자/수정자 사번
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("STOR_DT"));		//입고일자
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));			//연번
        
        int dmlcount = this.getDao("rbmdao").update("rem5040_u01_1", param);
        
        return dmlcount;
    }
    

    /**
     * <p> 재고 존재여부 여부 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			사건에 걸려있는 사건이력 개수
     * @throws  none
     */
    protected boolean CheckBalance(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));	//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("STOR_DT" ));	//입고일자
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));	    //입고연번
        param.setWhereClauseParameter(i++, record.getAttribute("UNSTOR_DT" ));	    //출고일자
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rem5040_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        
        double nStorCnt = 0;
        double nUnstorCntCum = 0;
        double nUnstorCntThis = 0;
        
        nStorCnt = Double.parseDouble(record.getAttribute("STOR_CNT").toString());
        nUnstorCntThis = Double.parseDouble(record.getAttribute("UNSTOR_CNT").toString()) +
        Double.parseDouble(record.getAttribute("DISUSE_CNT").toString());
        nUnstorCntCum = Double.parseDouble(rtnKey);
        if (nStorCnt - nUnstorCntCum - nUnstorCntThis < 0) {
        	return false;
        }
        return true;
    }
}
