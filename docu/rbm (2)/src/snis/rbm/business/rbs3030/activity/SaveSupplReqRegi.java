/*================================================================================
 * 시스템			: 소모품 신청현황
 * 소스파일 이름	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * 파일설명		: 소모품 반출의 승인과 반려를 결정하고 승인 시, 소모품 대장에 추가해줌
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs3030.activity;

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

public class SaveSupplReqRegi extends SnisActivity {
	
	public SaveSupplReqRegi(){}

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
        String sTonerCd  = "002";        
        sDsName = "dsSupplReq";
        
		String sBizGbn = (String) ctx.get("BIZ_GBN".trim());
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		           	nTempCnt   = saveSupplReq(record);
			        nSaveCount = nSaveCount + nTempCnt;
			        
			        if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	sSeq = selectSeq(record);
			        }
			        continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	//승인품목 존재시 삭제 X
	            	if (selectRegiCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "승인된 소모품이 존재하는 신청은  삭제하실 수 없습니다");
		            		
		            		return;
		            	}
	            	}
	            	
	            	//서명되었을 시 삭제 X
	            	if( selectSignCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "서명이 된 소모품은 삭제하실 수 없습니다");
		            		
		            		return;
		            	}
	            	}
	            	
	            	nDeleteCount = nDeleteCount + deleteSupplReq(record);
	            }		        
	        }	        
        }
        
        //소모품대장 저장
        sDsName = "dsSupplRegi";        
        if ( ctx.get(sDsName) != null ) {          	
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        int nRtnKey, nSuppStkQty, nOldQty, nInputQty;
	        Double dInputQty;	//사용자입력값
	        Double dQty;
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);            
	         
	            nSuppStkQty = selectSuppStkCnt(record);	 	   		   //재고
	            dInputQty   = (Double)record.getAttribute("REQ_QTY");
	        	nInputQty   = dInputQty.intValue();	                   //사용자입력값(신청수량)
	        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nRtnKey = selectRegiKey(record);	//기본키 개수 조회
		        	
		        	if( nRtnKey == 0) {
		        		//소모품 대장에 기본키 중복되는 값이 없으면 Insert
		        		if( nSuppStkQty - nInputQty >= 0 || sTonerCd.equals(sBizGbn)) {
		        			dQty = new Double(nSuppStkQty - nInputQty);
		        			record.setAttribute("SUM", dQty);
		        			saveSupplRegi(record, sSeq);	//소모품대장
		        			saveSuppStk(record);	        //소모품재고증가
		        		} else {
			        			//rollback => 재고 부족
			        		try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "재고 부족");
			            		
			            		return;
			            	}
		        		}
		 
		        	} else {
		        		//rollback => 기본키 중복
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "기본키 중복");
		            		
		            		return;
		            	}
		        	}
		        }
		        
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	//재고 + 기존의 값 - 입력된 값
		        	
		        	nOldQty = selectSupplRegiCnt(record); //기존의 값
		        	
		        	if( nSuppStkQty + nOldQty - nInputQty >= 0 || sTonerCd.equals(sBizGbn) ) {
		        		dQty = new Double(nSuppStkQty + nOldQty - nInputQty);
		        		record.setAttribute("SUM", dQty);
		        		
		        		saveSupplRegi(record, sSeq);	//소모품대장
	        			saveSuppStk(record);			//소모품재고증가
		        	} else {
		        		//rollback => 재고가 0보다 작아짐
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "재고 부족");
		            		
		            		return;
		            	}
		        	}
		        }

	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {		            	
	            	//재고 + 기존의 값 
	            	if( nSuppStkQty + nInputQty >= 0 || sTonerCd.equals(sBizGbn) ) {
	            		dQty = new Double(nSuppStkQty + nInputQty);
		        		record.setAttribute("SUM", dQty);
		        		
		        		deleteSupplRegi(record);  //소모품대장삭제
	        			saveSuppStk(record);	  //소모품재고증가
	            	} else {
	            		//rollback => 재고가 0보다 작아짐
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "재고 부족");
		            		
		            		return;
		            	}
	            	}
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
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   //신청자사번
        param.setValueParamter(i++, record.getAttribute("SEQ"));		   //순번
        param.setValueParamter(i++, record.getAttribute("REQ_CNTNT"));	   //신청내용
        param.setValueParamter(i++, record.getAttribute("PROG_STAT_CD"));  //승인여부
        
        param.setValueParamter(i++, record.getAttribute("REPLY"));		   //답변       
        param.setValueParamter(i++, record.getAttribute("MNG_REQ_YN"));    //관리자신청여부
        param.setValueParamter(i++, record.getAttribute("APRV_DT"));       //승인일자
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));       //소모품 분류
        param.setValueParamter(i++, SESSION_USER_ID);					   //사용자ID(작성자)      
        param.setValueParamter(i++, SESSION_USER_ID);					   //사용자ID(수정자)
                		
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //신청일자
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   //신청자사번

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
     * <p> 소모품 대장 기본키 개수 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectRegiKey(PosRecord record) 
    {
    	if( record.getAttribute("SEQ") == null ) {
    		return 0;
    	}
    	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));    //신청일자
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));  //신청자사번
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));       //순번
        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드        

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3030_s03", param);  
    	
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> 소모품대장 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSupplRegi(PosRecord record, String sSeq) 
    {
        PosParameter param = new PosParameter();   
        String sMng = "";
        int i = 0;
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String sToday = formater.format(today);
        
        
        if( record.getAttribute("SEQ") == null ) {
        	record.setAttribute("SEQ", sSeq);
        }
        
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //신청일자
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));      //신청자사번
        param.setValueParamter(i++, record.getAttribute("SEQ"));		   //순번      
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));	   //소모품코드
        param.setValueParamter(i++, record.getAttribute("REQ_QTY"));  	   //수량
        
        sMng = selectMng(record);
        
        if( sMng.equals("Y")) {
        	param.setValueParamter(i++, record.getAttribute("REQ_ID"));  	//실수령인사번
        	//param.setValueParamter(i++, record.getAttribute("REQ_DT"));   //서명일자
        	param.setValueParamter(i++, sToday);    						//서명일자            
        } else {
        	param.setValueParamter(i++, null);  //실수령인사번
            param.setValueParamter(i++, null);  //서명일자
        }
        param.setValueParamter(i++, SESSION_USER_ID);					   //사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);					   //사용자ID(수정자)
						
        int dmlcount = this.getDao("rbmdao").update("rbs3030_m01", param);
        
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
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3030_s04", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        String rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
        
        if( rtnQty == null )	rtnQty = "-1";
        
        return Integer.valueOf(rtnQty).intValue();
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
        String rtnQty = "0";
        if (pr.length > 0) { 
        	rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
        }
        
        if( rtnQty == null )	rtnQty = "-1";
        
        return Integer.valueOf(rtnQty).intValue();
    }
    
    /**
     * <p> 소모품재고 증가 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSuppStk(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드
        param.setValueParamter(i++, record.getAttribute("SUM"));   	   //수량
        param.setValueParamter(i++, SESSION_USER_ID);				   //사용자ID

        int dmlcount = this.getDao("rbmdao").update("rbs3010_m02", param);

        return dmlcount;
    }
    
    /**
     * <p> 소모품대장 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSupplRegi(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	   //신청일자
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));  //신청자사번
        param.setValueParamter(i++, record.getAttribute("SEQ"));       //순번
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드
        
        int dmlcount = this.getDao("rbmdao").update("rbs3030_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 소모품신청 순번 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String selectSeq(PosRecord record) 
    {	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));   //소모품코드
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID")); //신청자사번
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3030_s06", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        String rtnQty = String.valueOf(pr[0].getAttribute("SEQ"));

        return rtnQty;
    }
    
    /**
     * <p> 관리자승인여부 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			
     * @throws  none
     */
    protected String selectMng(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));	    //신청일자
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));	//신청자사번
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));	        //순번
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3030_s07", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("MNG"));

        return rtnKey;
    }
    
    /**
     * <p> 서명 여부 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			
     * @throws  none
     */
    protected int selectSignCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));	    //신청일자
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));	//신청자사번
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));	        //순번
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3030_s08", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.valueOf(rtnKey).intValue();
    }
    
    /**
     * <p> 기본키에 대한 소모품 대장 건수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			
     * @throws  none
     */
    protected int selectRegiCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));	    //신청일자
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));	//신청자사번
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));	        //순번
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3030_s09", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.valueOf(rtnKey).intValue();
    }
}