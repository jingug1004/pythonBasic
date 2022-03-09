/*================================================================================
 * 시스템			: 부서별담당자선정
 * 소스파일 이름	: snis.rbm.business.rev1070.activity.SaveEmpEstm.java
 * 파일설명		: 부서별평가자 수정
 * 작성자			: 배태일
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev1050.activity.SaveAprvMana;
import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.EncryptSHA256;

public class SaveEmpEstm extends SnisActivity {
		public SaveEmpEstm(){}
		
		
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
	    	String sDsNameExp   = "";
	    	String sDsNameMnr   = "";
	    	String sDsNameSvr   = "";
	    	String sDsNameEstm1 = "";
	    	
	    	PosDataset ds;
	        int nSize          = 0;
	        int nEmpEstmCnt    = 0;
	        int nEmpEstmExpCnt = 0;
	        int nEmpEstmMnrCnt = 0;
	        int nEmpEstmSvrCnt = 0;
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        String sDeptCd   = (String)ctx.get("DEPT_CD");
	        String sReGrant  = (String)ctx.get("REGRANT");
	        
	    	//승인이 되었다면 불가능

    		if( getAprvYn(sEvalYear, sEvalNum, sDeptCd).equals("N") ) {
    			try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "승인요청이 되었기 때문에 저장하실 수 없습니다.");
            		
            		return;
            	}
    		}
	        
	        
	        if( sReGrant.equals("N")) {
		        //평가개시되었다면 저장 불가능
		        if( getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
		        	try { 
	        			throw new Exception(); 
	            	} catch(Exception e) {       		
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsg(ctx, "평가 개시가 시작되었기 때문에 저장 불가능합니다.");
	            		
	            		return;
	            	}
		        }
	        }
	        
	        //평가마감이 되었다면 저장 불가능
    		SavePrmRslt SavePrmRslt = new SavePrmRslt();
	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "평가마감이 완료되어 저장하실 수 없습니다.");
            		
            		return;
            	}
	        }
	        
	        sDsName      = "dsEmpEstm";
	        sDsNameEstm1 = "dsEmpEstm1";
	        sDsNameExp   = "dsEmpEstmExp";
	        sDsNameMnr   = "dsEmpEstmMnr";
	        sDsNameSvr   = "dsEmpEstmSvr";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		        	PosRecord record = ds.getRecord(i);
		        	
		        	if(i==0)	refreshEmpEstm(record);	//초기화
		        	
		        	if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {        	
		        		nEmpEstmCnt = updateEmpEstm(record);	        	
				        nSaveCount  = nSaveCount + nEmpEstmCnt;
			        	continue;
		            }
		        }	        
	        }
	        
	        if ( ctx.get(sDsNameMnr) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsNameMnr);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordMnr = ds.getRecord(i);
	            	refreshEmpEstmMnr(recordMnr);
		        	
		        }
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordMnr = ds.getRecord(i);

		            if ( recordMnr.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
		            	recordMnr.setAttribute("APRV_SEQ", "2");
		            	updateEvAprv(recordMnr);
		        		nEmpEstmMnrCnt = updateEmpEstmMnr(recordMnr);
				        nSaveCount = nSaveCount + nEmpEstmMnrCnt;
			        	continue;
		            }
		        }	        
	        }
	        if ( ctx.get(sDsNameSvr) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsNameSvr);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordSvr = ds.getRecord(i);
	            	refreshEmpEstmSvr(recordSvr);
		        }
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordSvr = ds.getRecord(i);

		            if ( recordSvr.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
		            	recordSvr.setAttribute("APRV_SEQ", "3");
		            	updateEvAprv(recordSvr);
		        		nEmpEstmExpCnt = updateEmpEstmSvr(recordSvr);
		        	
				        nSaveCount = nSaveCount + nEmpEstmSvrCnt;
			        	continue;
		            }
		        }	        
	        }
	        
	        if ( ctx.get(sDsNameEstm1) != null ) {
		        ds    = (PosDataset) ctx.get(sDsNameEstm1);
		        nSize = ds.getRecordCount();
		        		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            if(i==0) {
		            	refreshEmpEstm1(record);	//초기화
		            	//deleteUser(record);		//임시사용자로 추가
		            }
		            
		            String sPassword = (String)record.getAttribute("RES_NO");
		            sPassword = EncryptSHA256.getEncryptSHA256(sPassword);
		            
		            record.setAttribute("PSWD", sPassword);
		            
		            String sEmpNo = (String)record.getAttribute("EMP_NO");
		            if( sEmpNo.trim().length() > 0 ) {
		            	insertUser(record);
		            }
		            
		        	updateEmpEstm1(record);
		        }	        
	        }
	        
	        //발매원이 경주사업관리시스템에 로그인할 수 있도록 추가
	        if ( ctx.get(sDsNameExp) != null ) {
		        ds    = (PosDataset) ctx.get(sDsNameExp);
		        nSize = ds.getRecordCount();
		        		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            String sEstmWkJob = (String)record.getAttribute("ESTM_WK_JOB");
		            if("1003".equals(sEstmWkJob)) {
			            String sPassword = (String)record.getAttribute("RES_NO");
			            sPassword = EncryptSHA256.getEncryptSHA256(sPassword);
			            record.setAttribute("PSWD", sPassword);
			            
			            String sEmpNo = (String)record.getAttribute("EMP_NO");
			            if( sEmpNo.trim().length() > 0 ) {
			            	insertUser(record);
			            }
		        	}	
		        }	        
	        }
	        
	        if ( ctx.get(sDsNameExp) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsNameExp);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordExp = ds.getRecord(i);

		            if ( recordExp.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	
		        		nEmpEstmExpCnt = updateEmpEstmExp(recordExp);
				        nSaveCount = nSaveCount + nEmpEstmExpCnt;
			        	continue;
		            }
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount);
	    }

	    
	    /**
	     * <p> 부서별 대상자 확정 여부 조회 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected String getAprvYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s14", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("APRV_YN"));

	        return rtnKey;
	    }	    
	    
	    /**
	     * <p> 업무수행평가현황 평가자 내용 초기화 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int refreshEmpEstm(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));       // 3. 부서코드
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u01", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가현황 평가자 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmpEstm(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EV_GBN"));          // 1. "1차2차 평가자 구분" 에 평가자구분값을 넣음
	    	param.setValueParamter(i++, record.getAttribute("SNACK_ESTM_YN"));   // 1. "매점 평가자 여부
	    	param.setValueParamter(i++, SESSION_USER_ID);                        // 2. 사용자 ID
	    	
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. 부서코드
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));        // 6. 사원번호

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u02", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가현황 계약직평가자 내용 초기화 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int refreshEmpEstm1(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));       // 3. 부서코드
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u09", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가현황 계약직평가자 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmpEstm1(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;	
	    	param.setValueParamter(i++, record.getAttribute("EV_GBN"));          // 1. "1차2차 평가자 구분" 에 평가자구분값을 넣음
	    	param.setValueParamter(i++, record.getAttribute("ESTM_WK_JOB"));       // 4. 보직코드
	    	param.setValueParamter(i++, SESSION_USER_ID);                        // 5. 사용자 ID
	    	
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 6. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 7. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 8. 부서코드
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));        // 9. 사원번호

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u10", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가현황 평가자 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmpEstmExp(PosRecord recordExp)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordExp.getAttribute("ESTM_EMP"));        // 1. 평가자(B)
	    	param.setValueParamter(i++, recordExp.getAttribute("ESTM_WK_JOB"));     // 2. 평가보직
	    	param.setValueParamter(i++, recordExp.getAttribute("DIV_NO"));         	// 3. 투표소번호
	    	param.setValueParamter(i++, recordExp.getAttribute("COMM_NO")); 		// 4. 지점코드
	    	param.setValueParamter(i++, recordExp.getAttribute("RELEA_ESC_YN")); 	// 5. 발매실적평가 제외자 여부
	    	
	        param.setValueParamter(i++, SESSION_USER_ID);                          	// 6. 사용자 ID

	        i = 0;
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("ESTM_YEAR"));     // 7. 평가년도
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("ESTM_NUM"));      // 8. 평가회차
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("DEPT_CD"));       // 9. 부서코드
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("EMP_NO"));        // 10. 사원번호	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_u03", param);
	        return dmlcount;
	    }

	    /**
	     * <p> 근무태도 평가항목 내용 초기화 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int refreshEmpEstmMnr(PosRecord recordMnr)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordMnr.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setValueParamter(i++, recordMnr.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setValueParamter(i++, recordMnr.getAttribute("DEPT_CD"));      // 6. 부서코드
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u04", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도 평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmpEstmMnr(PosRecord recordMnr)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordMnr.getAttribute("EV_GBN"));         // 1. 평가자(B)
	        param.setValueParamter(i++, SESSION_USER_ID);                         // 3. 사용자 ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("DEPT_CD"));      // 6. 부서코드
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("EMP_NO"));      // 7. 사원번호

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u05", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스 평가항목 내용 초기화 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int refreshEmpEstmSvr(PosRecord recordSvr)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordSvr.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setValueParamter(i++, recordSvr.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setValueParamter(i++, recordSvr.getAttribute("DEPT_CD"));      // 6. 부서코드
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u06", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스 평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmpEstmSvr(PosRecord recordSvr)
	    {			
	    	PosParameter param = new PosParameter();
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordSvr.getAttribute("EV_GBN"));         // 1. 평가자(B)
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 3. 사용자 ID

	        i = 0;
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("DEPT_CD"));       // 6. 부서코드
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("EMP_NO"));        // 7. 사원번호

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u07", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서별 승인자 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEvAprv(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));        		  // 1. 승인요청자 사번
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. 사용자 ID

	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. 사원번호
	        param.setWhereClauseParameter(i++, record.getAttribute("APRV_SEQ"));      // 6. 승인차수

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서별 사용자 삭제 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		delete 레코드 개수
	     * @throws  none
	     */
	    protected int deleteUser(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	        param.setWhereClauseParameter(i++, record.getAttribute("TEAM_CD"));     //팀코드

	        int dmlcount = this.getDao("rbmdao").update("rev1070_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 사용자 추가 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		insert 레코드 개수
	     * @throws  none
	     */
	    protected int insertUser(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("PSWD")); 
	    	param.setValueParamter(i++, record.getAttribute("ESTM_WK_JOB"));
	    	param.setValueParamter(i++, SESSION_USER_ID); 
	    	param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	    	param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	    	param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	    	   
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i09", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 화면 개시 여부 조회 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public String getUpdateYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s11", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("ESTM_OPN_YN"));

	        return rtnKey;
	    }
}
