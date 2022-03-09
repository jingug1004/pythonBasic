/*================================================================================
 * 시스템			: 소모품이력 관리
 * 소스파일 이름	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * 파일설명		: 소모품의 입고내용이 등록, 수정, 삭제될 때의 처리 
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-24
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs3010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplStk extends SnisActivity {
	
	public SaveSupplStk(){}

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
        

        // 부품 품목 저장
        sDsName = "dsSupplStk";        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        for ( int i = 0; i < ds.getRecordCount(); i++ ) {
        	
	        	PosRecord record = ds.getRecord(i);
    			
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {		        	
	        		nSaveCount += insertSupplCode(record);
	        	} else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {		        	
	        		nSaveCount += updateSupplCode(record);
	        	} else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {		        	
	        		nDeleteCount += deleteSupplCode(record);
	        	}	        	
	        }        	
        }
        
        
        sDsName = "dsSupplHist";        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        int nSuppStkQty, nInputQty, nOldQty;
	        Double dInputQty;	//사용자입력값
	        
	        //재고 저장
	        for ( int i = 0; i < nSize; i++ ) {
        	
	        	PosRecord record = ds.getRecord(i);
	        	
	        	dInputQty   = (Double)record.getAttribute("QTY");
	        	nInputQty   = dInputQty.intValue();					//사용자입력값	        	
    			
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	        		
	        		//소모품이력 기본키 중복체크
	        		if( selectSupplKeyCnt(record) > 0 ) {
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		String sDate = (String)record.getAttribute("PRHS_DT");
		            		Util.setSvcMsg(ctx, "[ " + sDate.substring(0, 4) + "-" + sDate.substring(4, 6) + "-" +
		            				            sDate.substring(6, 8) + " ]의 [ " + selectSupplName(record) + 
		            				            " ]는 중복된 값이라 추가하실 수 없습니다.");
		            		return;
		            	}
	        		}
	        		
	        		if( selectSupplStkChk(record) == 0 ) {
	        			//재고 테이블에 값이 없으면 insert       			
	        			record.setAttribute("SUM", dInputQty);
	        			saveSuppStk(record);
	        		} else {
	        			//재고 테이블에 값이 있다면 update(현재고 + 입력된 값 )
	        			nSuppStkQty = selectSuppStkCnt(record);		//재고
	        			Double dQty = new Double(nSuppStkQty + nInputQty);
	        			record.setAttribute("SUM", dQty);
	        			
	        			saveSuppStk(record);
	        		}
	        	}
	        	
	        	nSuppStkQty = selectSuppStkCnt(record);				//재고
	        	
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	        		//재고 + 입력된 값 - 기존의 값
	        		nOldQty = selectSuppHistCnt(record);			//기존의 입력되어 있던 값;
	        		
	        		if( nSuppStkQty + nInputQty - nOldQty >= 0) {
		        		Double dQty = new Double(nSuppStkQty + nInputQty - nOldQty);      		
		        		record.setAttribute("SUM", dQty);
	
	        			saveSuppStk(record);
	        		} else {
	        			//Exception 발생
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		String sDate = (String)record.getAttribute("PRHS_DT");
		            		Util.setSvcMsg(ctx, "[ " + sDate.substring(0, 4) + "-" + sDate.substring(4, 6) + "-" +
		            				            sDate.substring(6, 8) + " ]의 [ " + selectSupplName(record) + 
		            				            " ]의 변경 값 때문에 재고가 0보다 작아지므로 변경하실 수 없습니다.");
		            		return;
		            	}
	        		}
	        	}
	        	
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	        		//재고 - 기존의 값
	        		
	        		if( nSuppStkQty - nInputQty >= 0) {
	        			Double nQty = new Double(nSuppStkQty - nInputQty);      		
		        		record.setAttribute("SUM", nQty);
	
	        			saveSuppStk(record);
	        		} else {
	        			//Exception 발생
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		String sDate = (String)record.getAttribute("PRHS_DT");
		            		Util.setSvcMsg(ctx, "[ " + sDate.substring(0, 4) + "-" + sDate.substring(4, 6) + "-" +
		            				            sDate.substring(6, 8) + " ]의 [ " + selectSupplName(record) + 
		            				            " ]를 삭제하면 재고가 0보다 작아지므로 삭제하실 수 없습니다.");
		            		return;
		            	}
	        		}
	        	}

	        }   
	        
	        //Dataset 저장
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = saveSupplHist(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {          		
	            	nDeleteCount = nDeleteCount + deleteSupplHist(record);
	            }
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 소모품이력 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveSupplHist(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));	//소모품코드
        param.setValueParamter(i++, record.getAttribute("PRHS_DT"));	//구매일자
        param.setValueParamter(i++, record.getAttribute("QTY"));		//수량
        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수정자)
        
        int dmlcount = this.getDao("rbmdao").update("rbs3010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 소모품이력 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSupplHist(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드
        param.setValueParamter(i++, record.getAttribute("PRHS_DT"));   //구매일자

        int dmlcount = this.getDao("rbmdao").update("rbs3010_d01", param);

        return dmlcount;
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
     * <p> 소모품이력 수량 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectSuppHistCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드
        param.setWhereClauseParameter(i++, record.getAttribute("PRHS_DT"));   //구매일자

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s04", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
        
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
     * <p> 소모품재고 테이블에 재고 존재 여부 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectSupplStkChk(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s05", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnQty).intValue();
    }
    
    /**
     * <p> 소모품이름 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String selectSupplName(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s06", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();

        return String.valueOf(pr[0].getAttribute("CD_NM"));
    }
    
    /**
     * <p> 소모품이력 기본키 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectSupplKeyCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //소모품코드
        param.setWhereClauseParameter(i++, record.getAttribute("PRHS_DT"));   //구매일자

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s07", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnQty).intValue();
    }

    /**
     * <p> 소모품이력 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertSupplCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, "031");									//소모품그룹코드
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD_NM"));	//소모품 코드명
        param.setValueParamter(i++, record.getAttribute("MANUF_NM"));		// 제조사명
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));		//(일반소모품, 프린터소모품 구분)
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)
        
        int dmlcount = this.getDao("rbmdao").update("rsy1010_i03", param);
        
        return dmlcount;
    }

    /**
     * <p> 소모품이력 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateSupplCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SUPPL_CD_NM"));	//소모품 코드명
        param.setValueParamter(i++, record.getAttribute("MANUF_NM"));									//추가코드(일반소모품, 프린터소모품 구분)
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));		//경륜경정 구분
        param.setValueParamter(i++, null);									//추가코드(일반소모품, 프린터소모품 구분)
        param.setValueParamter(i++, null);									//추가코드(일반소모품, 프린터소모품 구분)
        param.setValueParamter(i++, null);									//추가코드(일반소모품, 프린터소모품 구분)
        param.setValueParamter(i++, null);									//추가코드(일반소모품, 프린터소모품 구분)
        param.setValueParamter(i++, null);									//추가코드(일반소모품, 프린터소모품 구분)
        param.setValueParamter(i++, "N");						//사용자ID(작성자)        
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(수정자)
        param.setValueParamter(i++, null);									//추가코드(일반소모품, 프린터소모품 구분)
        
        param.setValueParamter(i++, "031");									//소모품그룹코드
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));		//소모품 코드
        int dmlcount = this.getDao("rbmdao").update("rsy1010_u02", param);
        
        return dmlcount;
    }
        
    
    /**
     * <p> 소모품이력 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSupplCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, "031");									//소모품그룹코드
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));		//소모품 코드

        int dmlcount = this.getDao("rbmdao").update("rsy1010_d02", param);

        return dmlcount;
    }
}