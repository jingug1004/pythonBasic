/*
 * ================================================================================
 * 시스템 : 발매원 승인처리
 * 파일 이름 : snis.rbm.business.rsm9010.activity.SaveTeller.java 
 * 파일설명 :  
 * 작성자 : 
 * 버전 : 1.0.0 생성일자 : 2011- 12 - 17
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm9010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveTeller extends SnisActivity {
	
	public SaveTeller(){}

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
    	int nSize        = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
        

    	String sLevel    = (String)ctx.get("LEVEL");
    	String sAprvStat = (String)ctx.get("APRV_STAT");
    	
    	sDsName = "dsPcTeller";
    	
    	if ( ctx.get(sDsName) != null ) {
    		ds    = (PosDataset) ctx.get(sDsName);
        	nSize = ds.getRecordCount();
    	} else {
    		return;
    	}
    	
    	// LEVEL = 1은 승인자
    	if( sLevel.equals("1") ) {
    		if( sAprvStat.equals("ARPV")) {	//승인(변경과 삭제) 
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE  ) {
		            	//deletePcTellerAprv(record);
		            	deletePcTellerAprvFlag(record);
		            }
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
		            	//Check 된 경우에만 처리
		            	if( record.getAttribute("CHK").equals("1") ) {
		            		if( getTellerCnt(record) == 0) {
		            			updatePcTellerAprv(record);
		            		} else {
		            			//중복키 발생
		            			try { 
		        	    			throw new Exception(); 
		        	        	} catch(Exception e) {       		
		        	        		this.rollbackTransaction("tx_rbm");
		        	        		Util.setSvcMsg(ctx, "동일한 지점,TELLER_ID, 사번을 쓰는 데이터가 존재합니다.");
		        	        		
		        	        		Util.setReturnParam(ctx, "RESULT", "F");
		        	        		return;
		        	        	}
		            		}
		            	}	//CHK if 문 종
			        }	//UPDATE if문 종료 
	            }	//for문 종료
    		} else if(sAprvStat.equals("CANCEL")){ //반려
    			for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
		            	//Check 된 경우에만 처리
		            	if( record.getAttribute("CHK").equals("1") ) {
		            		if( getTellerCnt(record) == 0) {
		            			updatePcTellerCancel(record);
		            		} 
		            	}
			        }
	            }
    		}
    	}

    	// LEVEL = 2는 요청자
    	if( sLevel.equals("2")) {	//승인요청
    		if ( ctx.get(sDsName) != null ) {
            	ds    = (PosDataset) ctx.get(sDsName);
            	nSize = ds.getRecordCount();
    	        
    	        for ( int i = 0; i < nSize; i++ ) {
    	            PosRecord record = ds.getRecord(i);
    	            
    	    		if( sAprvStat.equals("RECORM")) {	//확인
    	    			updatePcTellerReConfirm(record);
    	    		} else {
	    	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT  ) {
	    	            	insertPcTellerReq(record);
	    	            }
	    	            
	    	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
	    	            	updatePcTellerReq(record);
	    		        }
    	    		}
    	        }	 
            }
    	}
    	
    	Util.setReturnParam(ctx, "RESULT", "S");
        Util.setSaveCount  (ctx, nSaveCount);
    }
    
    /**
     * <p> 발매원 추가 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertPcTellerReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//1.발매원소속
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));			//2.사번
        param.setValueParamter(i++, record.getAttribute("TELLER_NM"));		//3.성명
        param.setValueParamter(i++, record.getAttribute("SELL_TYPE_CD"));	//4.매표구분
        param.setValueParamter(i++, SESSION_USER_ID);						//5.승인요청자
        param.setValueParamter(i++, record.getAttribute("PROC_GBN"));		//6.처리구분
        param.setValueParamter(i++, record.getAttribute("TELLER_GBN"));		//7.발매원구분
        param.setValueParamter(i++, record.getAttribute("ETC"));			//8.비고
        param.setValueParamter(i++, SESSION_USER_ID);						//9.작성자
					
        int dmlcount = this.getDao("rbmdao").update("rsm9010_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 발매원 수정(승인요청) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updatePcTellerReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("PROC_GBN"));	//처리여부
        param.setValueParamter(i++, record.getAttribute("ETC"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);					//승인요청자
        param.setValueParamter(i++, SESSION_USER_ID);					//수정자
        i = 0;	
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 요청자 확인 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updatePcTellerReConfirm(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//수정자
        i = 0;	
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u05", param);
        
        return dmlcount;
    }    
    /**
     * <p> 발매원 수정(승인) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updatePcTellerAprv(PosRecord record) 
    {
        PosParameter param = new PosParameter();   

        if ("004".equals(record.getAttribute("PROC_GBN"))) {		// 처리여부가 삭제인 경우
        	int delcount = deletePcTellerAprvFlag(record);
        	return delcount;
        }
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));		//발매원ID
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));			//사번
        param.setValueParamter(i++, record.getAttribute("TELLER_NM"));		//성명
        param.setValueParamter(i++, record.getAttribute("SELL_TYPE_CD"));	//매표구분
        param.setValueParamter(i++, record.getAttribute("TELLER_GBN"));		//발매원구분
        param.setValueParamter(i++, record.getAttribute("ETC"));			//비고
        param.setValueParamter(i++, record.getAttribute("PASSWD"));			//비고
        param.setValueParamter(i++, SESSION_USER_ID);						//승인자
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자ID
        
        i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 발매원 수정(반려) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updatePcTellerCancel(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ETC"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);	
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u03", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 발매원 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deletePcTellerAprv(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_d01", param);
        return dmlcount;
    }
    
    /**
     * <p> 발매원 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deletePcTellerAprvFlag(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ETC"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);	
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u04", param);
        return dmlcount;
    }
    
    /**
     * <p> 기본키 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int getTellerCnt(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        String sCommNo      = (String)record.getAttribute("COMM_NO");
        String sOrgCommon   = (String)record.getAttribute("ORG_COMM_NO");
        String sTellerId    = (String)record.getAttribute("TELLER_ID");
        String sOrgTellerId = (String)record.getAttribute("ORG_TELLER_ID");
        String sEmpNo       = (String)record.getAttribute("EMP_NO");
        String sOrgEmpNo    = (String)record.getAttribute("ORG_EMP_NO");

        //기본키에 변화가 없다면
        if( sCommNo  .equals(sOrgCommon)   &&
        	sTellerId.equals(sOrgTellerId) &&	
        	sEmpNo   .equals(sOrgEmpNo)    ) {
        	return 0;
        }
        
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));
        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));

        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm9010_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.parseInt(rtnKey);
    }
}
