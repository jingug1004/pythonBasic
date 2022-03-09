package snis.rbm.business.rbs3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplReturn extends SnisActivity {
	public SaveSupplReturn(){
		
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
         String sSeq      = "";
         
         
         //소모품대장 저장
         sDsName = "dsSupplRegi";        
         if ( ctx.get(sDsName) != null ) {          	
 	        ds    = (PosDataset) ctx.get(sDsName);
 	        nSize = ds.getRecordCount();

 	        int nRtnKey, nSuppStkQty, nOldQty, nInputQty;
 	        Double dInputQty;	//사용자입력값
 	        
 	        for ( int i = 0; i < nSize; i++ ) {
 	            PosRecord record = ds.getRecord(i);            
 	         
 	            nSuppStkQty = selectSuppStkCnt(record);	 	   		   //재고
 	            dInputQty   = (Double)record.getAttribute("RETN_QTY");
 	        	nInputQty   = dInputQty.intValue();	                   //사용자입력값(신청수량)
 	        	
 	        	nOldQty = selectSupplRegiCnt(record); //기존의 값
 	        	
 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
 		        
 		        	//재고 + 기존의 값 - 입력된 값
 		        	Double dQty = new Double(nSuppStkQty - nOldQty + nInputQty);
        		
	        		record.setAttribute("SUM", dQty);
	        		
	        		saveSupplRegi(record, sSeq);	//소모품대장
        			saveSuppStk(record);			//소모품재고증가
 		        }

 	           
 	        }	        
         }
         Util.setSaveCount  (ctx, nSaveCount  );
         Util.setDeleteCount(ctx, nDeleteCount);
     }
     
     
     /**
      * <p> 소모품재고 수량 조회 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int selectSuppStkCnt(PosRecord record) 
     {	
         PosParameter param = new PosParameter();
         int i = 0;

         param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드
         
         PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s03", param);  
         PosRow pr[] = rtnRecord.getAllRow();        
         String rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
         
         if( rtnQty == null )	rtnQty = "-1";
         
         return Integer.valueOf(rtnQty).intValue();
     }

     
     
     /**
      * <p> 소모품대장 반납 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int saveSupplRegi(PosRecord record, String sSeq) 
     {

         PosParameter param = new PosParameter();   
         
         int i = 0;
         
         param.setValueParamter(i++, 	record.getAttribute("RETN_QTY"));		//반납수량
         param.setValueParamter(i++,	record.getAttribute("RETN_ID"));		//반납자 사번
         param.setValueParamter(i++, 	SESSION_USER_ID);		//수정자ID
         
         
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));			//신청일자
         param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));			//신청자사번
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));			//순번
         param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));		//소모품코드
         		
         int dmlcount = this.getDao("rbmdao").update("rbs3040_u02", param);
         
         return dmlcount;
         
  
     }
     
     
     
     /**
      * <p> 소모품재고 반납 증가 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int saveSuppStk(PosRecord record) 
     {
         PosParameter param = new PosParameter();   
         
         int i = 0;
         
         param.setValueParamter(i++, 	record.getAttribute("SUM"));		//반납수량
         param.setValueParamter(i++,	SESSION_USER_ID);		//반납자 사번

         
         
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));		//소모품코드
         		
         int dmlcount = this.getDao("rbmdao").update("rbs3040_u03", param);
         
         return dmlcount;
     }
     
     
     
     /**
      * <p> 소모품대장 수량 조회 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int selectSupplRegiCnt(PosRecord record) 
     {	
         PosParameter param = new PosParameter();
         int i = 0;
         
         param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));    //신청일자
         param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));  //신청자사번
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));  	  //순번
         param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드
         
         PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3040_s05", param);  
         PosRow pr[] = rtnRecord.getAllRow();        
         String rtnQty = String.valueOf(pr[0].getAttribute("RETN_QTY"));
         
         if( rtnQty == null )	rtnQty = "-1";
         
         return Integer.valueOf(rtnQty).intValue();
     }
}




