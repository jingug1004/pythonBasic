/*
 * ================================================================================
 * 시스템 : 유성지점 일일매표 관리 
 * 소스파일 이름 : snis.rbm.business.rbo2010.activity.SaveAppoSeatMana.java 
 * 파일설명 : 유성지점  일일매표관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011- 09 - 21
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */

package snis.rbm.business.rbo2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveAppoSeatMana extends SnisActivity {

	public SaveAppoSeatMana(){
		
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
        
        String sJobGbn   = (String) ctx.get("jobGbn");
        if(sJobGbn == null) sJobGbn = "";	// 01:입력/수정, 02:삭제 , 03: 환불처리 , 04: 좌석코드변경

        String sSalesNo = "";

        
    	sDsName = "dsSalesMana";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if(sJobGbn.equals("01")){	// 01 신규,수정
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateAppoSeatMana(record)) == 0 ) {
			        		
			        		if(sSalesNo == null || sSalesNo.equals("")){
			        			sSalesNo = getMaxSalesNo(record);
			        		}
			        		
			        		//판매번호 설정 
			        		record.setAttribute("SALES_NO", sSalesNo);
			        		
			        		nTempCnt = insertAppoSeatMana(record);
			        	}
				       


			        	ctx.put("SALES_DT", record.getAttribute("SALES_DT"));
			        	ctx.put("BRNC_CD", record.getAttribute("BRNC_CD"));
			        	ctx.put("SALES_NO", record.getAttribute("SALES_NO"));
			        	
			        	nSaveCount = nSaveCount + nTempCnt;
			        	
			        	continue;
			        }
	            }else if(sJobGbn.equals("02")){	// 02 삭제 
	            	 
	            	nTempCnt = deleteAppoSeatMana(record); 
 	            	
 	            	nDeleteCount = nDeleteCount + nTempCnt;
	 	            
	            	
	            }else if(sJobGbn.equals("03")){	// 03 환불 
	            	 record.setAttribute("REFUND_AMT", record.getAttribute("SAVE_AMT"));
	            	
	            	 nTempCnt = updateAppoSeatRetn(record);
	            	 
	            	 nSaveCount = nSaveCount + nTempCnt;
	            }

	            
		        
	        }	        
        }

        
        if(sJobGbn.equals("01")){	// 01 신규,수정
        	sDsName = "dsAppoSeatMana";

        	  if ( ctx.get(sDsName) != null ) {
      	        ds   		 = (PosDataset) ctx.get(sDsName);
      	        nSize 		 = ds.getRecordCount();

	      	        for ( int i = 0; i < nSize; i++ ) {
	      	            PosRecord record = ds.getRecord(i);
	      			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	      			        	
	      			        	nTempCnt = updateAppoSeat(record);
	      			        	nSaveCount = nSaveCount + nTempCnt;
	      			        	
	      			        	continue;
	      			        }
	      	        }
              }
        
        }
        
        if(sJobGbn.equals("04")){	// 04: 좌석정보변경 
        	sDsName = "dsSeatGbnCd";

        	  if ( ctx.get(sDsName) != null ) {
      	        ds   		 = (PosDataset) ctx.get(sDsName);
      	        nSize 		 = ds.getRecordCount();

	      	        for ( int i = 0; i < nSize; i++ ) {
	      	            PosRecord record = ds.getRecord(i);
	      			        if ( record.getType() == PosRecord.UPDATE
	      			        			|| record.getType() == PosRecord.INSERT ) {
	      			        	
	      			        	nTempCnt = updateSeatCd(record);
	      			        	nSaveCount = nSaveCount + nTempCnt;
	      			        	
	      			        	continue;
	      			        }
	      	        }
              }
        
        }


        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 일일매표 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAppoSeatMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SALES_DT"));	//판매일자
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("SALES_NO"));	//판매번호
        param.setValueParamter(i++, record.getAttribute("AREA"));		//구역
        param.setValueParamter(i++, record.getAttribute("SEAT_NO"));	//좌석번호


        param.setValueParamter(i++, record.getAttribute("UNIT_PRICE"));	//단가
        param.setValueParamter(i++, record.getAttribute("CUST_KD_CD"));	//고객구분
        param.setValueParamter(i++, record.getAttribute("SAVE_AMT"));	//입금액
        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);					//작성

        
        int dmlcount = this.getDao("rbmdao").update("rbo2010_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 일일매표 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAppoSeatMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("UNIT_PRICE"));	//단가
        param.setValueParamter(i++, record.getAttribute("CUST_KD_CD"));	//고객구분
        param.setValueParamter(i++, record.getAttribute("SAVE_AMT"));	//입금액
        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);      				//수정자
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_DT"));	//판매일자
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_NO"));	//판매번호	
        param.setWhereClauseParameter(i++, record.getAttribute("AREA"));		//구역
        param.setWhereClauseParameter(i++, record.getAttribute("SEAT_NO"));		//좌석번호

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u01", param);
        return dmlcount;
    }
  
    
    /**
     * <p> 일일매표 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAppoSeat(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);      				//수정자
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_DT"));	//판매일자
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_NO"));	//판매번호	
        param.setWhereClauseParameter(i++, record.getAttribute("AREA"));		//구역
        param.setWhereClauseParameter(i++, record.getAttribute("SEAT_NO"));		//좌석번호

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u03", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 일일매표 환불저장(수정) </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAppoSeatRetn(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("REFUND_AMT"));		//환불액
        param.setValueParamter(i++, SESSION_USER_ID);      					//수정자
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_DT"));	//판매일자
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_NO"));	//판매번호
        param.setWhereClauseParameter(i++, record.getAttribute("AREA"));		//구역
        param.setWhereClauseParameter(i++, record.getAttribute("SEAT_NO"));		//좌석번

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u02", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 일일매표  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAppoSeatMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SALES_DT"));	//판매일자
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("SALES_NO"));	//판매번호
        param.setValueParamter(i++, record.getAttribute("AREA"));		//구역
        param.setValueParamter(i++, record.getAttribute("SEAT_NO"));	//좌석번

        
        
        int dmlcount = this.getDao("rbmdao").update("rbo2010_d01", param);

        return dmlcount;
    }
    
    
    /**
     * <p> 판매번호 생성 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getMaxSalesNo(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        

        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo2010_s04",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
       
        rtnKey = String.valueOf(pr[0].getAttribute("SALES_NO"));

        return rtnKey;
    }
    
    
    /**
     * <p> 좌석정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateSeatCd(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("CD_TERM2"));		//좌석구분
        param.setValueParamter(i++, SESSION_USER_ID);      					//수정자
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD"));	//그룹번호
        param.setWhereClauseParameter(i++, record.getAttribute("CD"));		//코드

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u04", param);
        return dmlcount;
    }
    

}
