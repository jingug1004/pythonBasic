/*================================================================================
 * 시스템			: 승인관리
 * 소스파일 이름	: snis.rbm.business.rev1040.activity.SaveAprvMana.java
 * 파일설명		: 승인코드 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-26
 * 최종수정일자	: 2014.6.26 
 * 최종수정자		: 신재선
 * 최종수정내용	: 권한이력관리 기능 추가
=================================================================================*/
package snis.rbm.business.rev1050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;
import snis.rbm.business.rev1070.activity.*;
import snis.rbm.business.rev1080.activity.*;
import snis.rbm.business.rev2010.activity.SavePrmRslt;

public class SaveAprvMana extends SnisActivity {
		public SaveAprvMana(){}
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

	    protected void saveState(PosContext ctx) 
	    {
	    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        String sAprvCd   = (String)ctx.get("APRVCD");
	        String sAprvDate = (String)ctx.get("APRVDATE");
	        String sAprvDpet = (String)ctx.get("APRVDEPT");
	        String sMenuId   = (String)ctx.get("MENU_ID");
	        String sMngDept  = getMngDept(sEvalYear, sEvalNum);
	        
	        if(sMenuId.equals("REV1080")) {	//다면평가자그룹선정
	        	if( sAprvCd.equals("01")) {	//승인요청
		        	updateAprv(sEvalYear, sEvalNum, "002", sMngDept, "13", "");
		        } else if( sAprvCd.equals("02") ) {	//회수
		        	updateAprv(sEvalYear, sEvalNum, "001", sMngDept, "13", "");
		        	//다면평가  삭제
		        	SaveMultAppr SaveMultAppr = new SaveMultAppr();
		        	SaveMultAppr.deleteMultEval(sEvalYear, sEvalNum, "");
		        } else if( sAprvCd.equals("03") ) {	//승인	
		        	updateAprv(sEvalYear, sEvalNum, "003", sMngDept, "13", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//반려
		        	updateAprv(sEvalYear, sEvalNum, "001", sMngDept, "13", "");
		        } else if( sAprvCd.equals("05") ) {	//승인취소
		        	//화면개시되었다면 불가능
		        	SaveEmpEstm SaveEmpEstm = new SaveEmpEstm();
			        if( SaveEmpEstm.getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
			        	try { 
		        			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "평가 개시가 시작되었기 때문에 승인취소가 불가능합니다.");
		            		
		            		return;
		            	}
			        }
		        	updateAprv(sEvalYear, sEvalNum, "002", sMngDept, "13", "");
		        }
	        } else if( sMenuId.equals("REV1090")) {	//부서별담당자선정
	        	if( sAprvCd.equals("01")) {	//확정요청
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "4", sAprvDate);
		        } else if( sAprvCd.equals("03") ) {	//회수
		        	//부서별평가자선정에서 '승인'이 들어갔다면 회수 불가능
		        	if( getAprvYn(sEvalYear, sEvalNum, sAprvDpet).equals("N") ) {
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "[ 부서별평가자선정 ]의 평가자가 승인되었기 때문에 [ 확정 취소 ]하실 수 없습니다.");
		            		
		            		return;
		            	}
		        	}
			        
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "4", "");
		        	//업무수행평가, 근무태도평가, 서비스평가 삭제
		        	SaveAppr SaveAppr = new SaveAppr();
		        	SaveAppr.deleteEval(sEvalYear, sEvalNum, sAprvDpet);
		        } 
	        } else if(sMenuId.equals("REV1070")) {	//부서별평가선정
	        	if( sAprvCd.equals("01")) {	//승인요청
	    	        
	        		if( !(getCfmUpdateYn(sEvalYear, sEvalNum, sAprvDpet) > 0) ) {
	        			try { 
	            			throw new Exception(); 
	                	} catch(Exception e) {       		
	                		this.rollbackTransaction("tx_rbm");
	                		Util.setSvcMsg(ctx, "평가대상생성을 한 후에 사용해주세요.");
	                		
	                		return;
	                	}
	        		}
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "1", "");
		        } else if( sAprvCd.equals("02") ) {	//회수
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "1", "");
		        	
		        	//업무수행평가, 근무태도평가, 서비스평가 삭제
		        	SaveAppr SaveAppr = new SaveAppr();
		        	SaveAppr.deleteEval(sEvalYear, sEvalNum, sAprvDpet);	
		        } else if( sAprvCd.equals("03") ) {	//승인
		        	if( !(getAprvReqYn(sEvalYear, sEvalNum, sAprvDpet) > 0) ) {
	        			try { 
	            			throw new Exception(); 
	                	} catch(Exception e) {       		
	                		this.rollbackTransaction("tx_rbm");
	                		Util.setSvcMsg(ctx, "승인요청상태가 아닙니다.");
	                		
	                		return;
	                	}
	        		}
		        	
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "1", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//반려
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "1", "");
		        	//업무수행평가, 근무태도평가, 서비스평가 삭제
		        	SaveAppr SaveAppr = new SaveAppr();
		        	SaveAppr.deleteEval(sEvalYear, sEvalNum, sAprvDpet);
		        } else if( sAprvCd.equals("05") ) {	//승인취소
		        	
		        	//화면개시되었다면 불가능
		        	SaveEmpEstm SaveEmpEstm = new SaveEmpEstm();
			        if( SaveEmpEstm.getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
			        	try { 
		        			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "평가 개시가 시작되었기 때문에 승인취소가 불가능합니다.");
		            		
		            		return;
		            	}
			        }
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "1", "");
		         }
	        } else if(sMenuId.equals("REV2020")) {	//근무태도평가
	        	if( sAprvCd.equals("01")) {	//승인요청
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "2", "");
		        } else if( sAprvCd.equals("02") ) {	//회수
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "2", "");
		        } else if( sAprvCd.equals("03") ) {	//승인	        	
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "2", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//반려
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "2", "");
		        } else if( sAprvCd.equals("05") ) {	//승인취소			        
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
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "2", "");
		        }
	        } else if(sMenuId.equals("REV2030")) {	//서비스평가
	        	if( sAprvCd.equals("01")) {	//승인요청
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "3", "");
		        } else if( sAprvCd.equals("02") ) {	//회수
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "3", "");
		        } else if( sAprvCd.equals("03") ) {	//승인	        	
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "3", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//반려
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "3", "");
		        } else if( sAprvCd.equals("05") ) {	//승인취소
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
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "3", "");
		        }
	        }
	    }
	    
	    /**
	     * <p> 승인상태 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateAprv(String sEvalYear, String sEvalNum, String SAprvStas, String sDeptCd, String sAprvSeq, String SAprvDate)
	    {		
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, SAprvDate);            // 1. 승인날짜
	    	param.setValueParamter(i++, SAprvStas);            // 1. 승인상태
	        param.setValueParamter(i++, SESSION_USER_ID);      // 2. 사용자 ID

	        i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);     // 3. 평가년도
	        param.setWhereClauseParameter(i++, sEvalNum);      // 4. 평가회차
	        param.setWhereClauseParameter(i++, sDeptCd);       // 5. 사원번호
	        param.setWhereClauseParameter(i++, sAprvSeq);      // 6. 승인차수
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1050_u02", param);
	        
	        return dmlcount;
	    }

	    /**
	     * <p> 부서별 평가자 선정 여부 조회 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public int getCfmUpdateYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s07", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
	        
	        return Integer.valueOf(rtnKey).intValue();
	    }

	    /**
	     * <p> 부서별 평가자 승인요청상태 여부 조회 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public int getAprvReqYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s16", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
	        
	        return Integer.valueOf(rtnKey).intValue();
	    }
	    /**
	     * <p> 담당 부서 검색 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public String getMngDept(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1080_s05", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("MNG_DEPT"));
	        
	        return rtnKey;
	    }
	    
	    /**
	     * <p> 부서별 평가자 승인 여부 조회 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public String getAprvYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s13", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("APRV_YN"));
	        
	        return rtnKey;
	    }
	    
	    /* ***********************************************************************************
	              권한부여
	    *********************************************************************************** */ 
	    /**
	     * <p> 부서장 시스템 사용 권한 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplyDeptHead(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0; //setValueParamter
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서장 메뉴 사용 권한 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuDeptHead(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, String sInst_yn, 
	    		                           String sAproYn, String sUserId, String curDtm)
	    {		
	    	/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
			
	        this.getDao("rbmdao").update("rev1080_u03", param);
	        
	        // 2) 신규 권한 입력
	    	param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i04", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서장 메뉴 사용 권한 부여(근무태도평가,서비스평가) </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuDeptHeadMnrSvr(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, String sInst_yn, 
	    		                                 String sAproYn, String sItemCd, String sUserId, String curDtm)
	    {	
	    	/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);	    	
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sItemCd);			
	        this.getDao("rbmdao").update("rev1080_u05", param);	        
	        
	        // 2) 신규 권한 부여
	        param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sItemCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i19", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 담당자 시스템 사용 권한 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplyMng(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 담당자 메뉴 사용 권한 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuMng(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, String sInst_yn, String sAproYn, String sUserId, String curDtm)
	    {			
	    	/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);			
	        this.getDao("rbmdao").update("rev1080_u04", param);
	        
	        // 2) 신규 권한 입력
	    	param = new PosParameter();
	    	
	    	i = 0; 
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 투표소장, 1차 평가자 시스템 사용 권한 부서별 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplyReleaMng(String sEvalYear, String sEvalNum, String sEvDept, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 투표소장, 1차 평가자 시스템 사용 권한 전체 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplyReleaMngAll(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        //param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i13", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 투표소장, 1차 평가자 메뉴 사용 권한 부서별 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuReleaMng(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                           String sInst_yn, String sAproYn, String sEvDept, String sUserId, String curDtm)
	    {		
	    	/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	
	        param.setValueParamter(i++, sEvDept);
	        //Union 이하	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);		
	        this.getDao("rbmdao").update("rev1080_u09", param);
	        
	        // 2) 신규 권한 입력
	    	param = new PosParameter();
	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        //Union 이하
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i08", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 투표소장, 1차 평가자 메뉴 사용 권한 전체 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuReleaMngAll(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                              String sInst_yn, String sAproYn, String sUserId, String curDtm)
	    {
    		/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        //Union 이하	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
			
	        this.getDao("rbmdao").update("rev1080_u06", param);
	        
	        // 2) 신규 권한 입력
	    	param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        //Union 이하	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i14", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가자 시스템 사용 권한 부서별 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplyMnr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i09", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가자 시스템 사용 권한 전체 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplyMnrAll(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i15", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가자 메뉴 사용 권한 부서별 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuMnr(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                      String sInst_yn, String sAproYn, String sEvDept, String sUserId, String curDtm)
	    {	
	    	/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	
	        param.setValueParamter(i++, sEvDept);		
	        this.getDao("rbmdao").update("rev1080_u10", param);
	        
	        // 2) 신규 권한 입력
	    	param = new PosParameter();
	    	
	    	i = 0; 
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i10", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가자 메뉴 사용 권한 전체 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuMnrAll(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                         String sInst_yn, String sAproYn, String sUserId, String curDtm)
	    {	
	    	/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        			
	        this.getDao("rbmdao").update("rev1080_u07", param);
	        
	        // 2) 신규 권한 입력
	    	
	    	param = new PosParameter();
	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i12", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가자 시스템 사용 권한 부서별 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplySvr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i11", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가자 시스템 사용 권한 부서별 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthAplySvrAll(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i17", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가자 메뉴 사용 권한 부서별 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuSvr(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                      String sInst_yn, String sAproYn, String sEvDept, String sUserId, String curDtm)
	    {			
	    	/* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	
	        param.setValueParamter(i++, sEvDept);		
	        this.getDao("rbmdao").update("rev1080_u11", param);
	        
	        // 2) 신규 권한 입력
	    	param = new PosParameter();	    	
	    	i = 0; 
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);	        
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i16", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가자 메뉴 사용 권한 전체 부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertAuthMuSvrAll(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                         String sInst_yn, String sAproYn,   String sUserId, String curDtm)
	    {
	        /* 기존 권한을 종료후 신규 권한 부여 */
	    	
	    	// 1)해당 사용자들의 권한을 모두 종료.. 없으면 말고...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        			
	        this.getDao("rbmdao").update("rev1080_u08", param);
	        
	        // 2) 신규 권한 입력	    	
	    	param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);	    	
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"년도 "+sEvalNum+"회차 평가자");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i18", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 권한 회수 </p>
	     * @param   
	     * @return  
	     * @throws  none
	     */
	    public void deleteAuthMu(String sEvalYear, String sEvalNum, String sEvDept, int nStatus, String sUserId) {

	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	        
	    	if( nStatus == 1) {	//부서장, 담당자
	    		//화면 권한 회수
	    		deleteAuthMuDeptHead(sEvalYear, sEvalNum, sUserId, curDateTime);	//부서장 권한회수
		    	deleteAuthMuMng     (sEvalYear, sEvalNum, sUserId, curDateTime);	//담당자 권한회수
		    	
		    	//시스템 사용권한 회수
		    	deleteAuthAplyDeptHead(sEvalYear, sEvalNum); //부서장 권한회수
		    	deleteAuthAplyMng     (sEvalYear, sEvalNum); //담당자 권한회수
	    	} else if( nStatus == 2) {	//투표소장, 1차평가자, 근무태도평가자, 서비스평가자
	    		//화면 권한 회수
	    		deleteAuthMuReleaMng(sEvalYear, sEvalNum, sEvDept, sUserId, curDateTime);	//투표소장, 1차 평가자 권한회수
		    	deleteAuthMuMnr     (sEvalYear, sEvalNum, sEvDept, sUserId, curDateTime);	//근무태도평가자 권한회수
		    	deleteAuthMuSvr     (sEvalYear, sEvalNum, sEvDept, sUserId, curDateTime);	//서비스평가자 권한회수
		    	
		    	//시스템 사용권한 회수
		    	deleteAuthAplyReleaMng(sEvalYear, sEvalNum, sEvDept); //투표소장, 1차 평가자 권한회수
		    	deleteAuthAplyMnr     (sEvalYear, sEvalNum, sEvDept); //근무태도평가자 권한회수
		    	deleteAuthAplySvr     (sEvalYear, sEvalNum, sEvDept); //서비스평가자 권한회수
	    	} else if( nStatus == 3) {	//전체
	    		//화면 권한 회수
	    		deleteAuthMuDeptHead(sEvalYear, sEvalNum, sUserId, curDateTime);	//부서장 권한회수
		    	deleteAuthMuMng     (sEvalYear, sEvalNum, sUserId, curDateTime);	//담당자 권한회수
		    	deleteAuthMuReleaMng(sEvalYear, sEvalNum, "", sUserId, curDateTime);	//투표소장, 1차 평가자 권한회수
		    	deleteAuthMuMnr     (sEvalYear, sEvalNum, "", sUserId, curDateTime);	//근무태도평가자 권한회수
		    	deleteAuthMuSvr     (sEvalYear, sEvalNum, "", sUserId, curDateTime);	//서비스평가자 권한회수
		    	
		    	//시스템 사용권한 회수
		    	deleteAuthAplyDeptHead(sEvalYear, sEvalNum); //부서장 권한회수
		    	deleteAuthAplyMng     (sEvalYear, sEvalNum); //담당자 권한회수
		    	deleteAuthAplyReleaMng(sEvalYear, sEvalNum, ""); //투표소장, 1차 평가자 권한회수
		    	deleteAuthAplyMnr     (sEvalYear, sEvalNum, ""); //근무태도평가자 권한회수
		    	deleteAuthAplySvr     (sEvalYear, sEvalNum, ""); //서비스평가자 권한회수
	    	} else if( nStatus == 4) {	//투표소장, 1차평가자, 근무태도평가자, 서비스평가자
	    		//화면 권한 회수
	    		deleteAuthMuOne(sEvalYear, sEvalNum, sEvDept, "REV2010", sUserId, curDateTime);	//업무수행평가 권한회수
	    		deleteAuthMuOne(sEvalYear, sEvalNum, sEvDept, "REV2020", sUserId, curDateTime);	//근무태도평가자 권한회수
	    		deleteAuthMuOne(sEvalYear, sEvalNum, sEvDept, "REV2030", sUserId, curDateTime);	//서비스평가자 권한회수
		    	
		    	//시스템 사용권한 회수
		    	//deleteAuthAplyReleaMng(sEvalYear, sEvalNum, sEvDept); //투표소장, 1차 평가자 권한회수
		    	//deleteAuthAplyMnr     (sEvalYear, sEvalNum, sEvDept); //근무태도평가자 권한회수
		    	//deleteAuthAplySvr     (sEvalYear, sEvalNum, sEvDept); //서비스평가자 권한회수
	    	}
	    }
	    
	    /**
	     * <p> 부서장 화면 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthMuDeptHead(String sEvalYear, String sEvalNum, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 담당자 화면 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthMuMng(String sEvalYear, String sEvalNum, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 투표소장, 1차평가자 화면 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthMuReleaMng(String sEvalYear, String sEvalNum, String sEvDept, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가자 화면 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthMuMnr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d04", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가자 화면 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthMuSvr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서장 시스템 사용권한 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthAplyDeptHead(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 담당자 시스템 사용권한 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthAplyMng(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 1차평가자, 투표소장 시스템 사용권한 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthAplyReleaMng(String sEvalYear, String sEvalNum, String sEvDept)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d08", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가자 시스템 사용권한 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthAplyMnr(String sEvalYear, String sEvalNum, String sEvDept)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d09", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가자 시스템 사용권한 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthAplySvr(String sEvalYear, String sEvalNum, String sEvDept)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d10", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 투표소장, 1차평가자, 근태, 서비스 권한 전체 재부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    public int reWorkEval(String sEvalYear, String sEvalNum, String sUserId) {
	    	int nInsertCnt = 0;
	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	        
	    	//권한 부여
        	//투표소장, 1차 평가자 권한부여
	    	insertAuthAplyReleaMngAll(sEvalYear, sEvalNum, sUserId);	//투표소장, 1차 평가자 시스템 사용 권한부여
	    	//nInsertCnt += insertAuthMuReleaMngAll(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//종사원평가
	    	//nInsertCnt += insertAuthMuReleaMngAll(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//평가진행
	    	nInsertCnt += insertAuthMuReleaMngAll(sEvalYear, sEvalNum, "REV2010", "Y", "Y", "Y", sUserId, curDateTime);	//업무수행평가
        	
        	//근무태도평가자 권한부여
	    	nInsertCnt += insertAuthAplyMnrAll(sEvalYear, sEvalNum, sUserId);	//근무태도평가자 시스템 사용 권한부여
	    	//nInsertCnt += insertAuthMuMnrAll(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//종사원평가
	    	//nInsertCnt += insertAuthMuMnrAll(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//평가진행
	    	nInsertCnt += insertAuthMuMnrAll(sEvalYear, sEvalNum, "REV2020", "Y", "Y", "Y", sUserId, curDateTime);	//근무태도평가
        	
        	//서비스태도평가자
        	insertAuthAplySvrAll(sEvalYear, sEvalNum, sUserId);	//서비스평가자 시스템 사용 권한부여
        	//nInsertCnt += insertAuthMuSvrAll(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//종사원평가
        	//nInsertCnt += insertAuthMuSvrAll(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//평가진행
        	nInsertCnt += insertAuthMuSvrAll(sEvalYear, sEvalNum, "REV2030", "Y", "Y", "Y", sUserId, curDateTime);	//서비스평가
        	
	    	return nInsertCnt;
	    }
	    
	    /**
	     * <p> 부서장, 담당자 권한 재부여(REV1060) </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    public  int reEval(String sEvalYear, String sEvalNum, String sUserId, int nCode) {
	    	int nInsertCnt = 0;
	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	        
	    	// nCode = 1일때, 회수
	    	// nCode = 2일때, 평가준비에 관련된 권한 부여
	    	// nCode = 3일때, 평가에 관련된 권한부여
	        
	    	if( nCode == 1 ) {
	    		deleteAuthMu(sEvalYear, sEvalNum, "", 1, sUserId);	//권한회수
	    	}
	    	
	    	if( nCode == 2 ) {
		    	//권한부여
	        	//부서장 권한부여(2차 평가자)
		    	nInsertCnt  = insertAuthAplyDeptHead(sEvalYear, sEvalNum, sUserId);	//부서장 시스템 사용 권한부여
		    	nInsertCnt += insertAuthMuDeptHead(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//종사원평가
		    	nInsertCnt += insertAuthMuDeptHead(sEvalYear, sEvalNum, "REV1000", "Y", "Y", "Y", sUserId, curDateTime);	//평가등록
		    	nInsertCnt += insertAuthMuDeptHead(sEvalYear, sEvalNum, "REV1070", "Y", "N", "N", sUserId, curDateTime);	//부서별평가자선정
		    	
	        	//담당자 권한부여
		    	nInsertCnt += insertAuthAplyMng(sEvalYear, sEvalNum, sUserId);	//담당자 시스템 사용 권한부여
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);		//종사원평가
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1000", "Y", "Y", "Y", sUserId, curDateTime);		//평가등록
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV3000", "Y", "Y", "Y", sUserId, curDateTime);		//평가현황
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1070", "Y", "Y", "Y", sUserId, curDateTime);		//부서별평가자선정
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1090", "Y", "N", "Y", sUserId, curDateTime);		//부서별대상자확정
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV3040", "Y", "N", "N", sUserId, curDateTime);		//부서별진행현황
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1120", "Y", "Y", "Y", sUserId, curDateTime);		//발매실적 제외기간관리
		    }
	    	
	    	if( nCode == 3 ) {
	    		nInsertCnt += insertAuthMuDeptHead   (sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//평가진행
	    		nInsertCnt += insertAuthMuDeptHead   (sEvalYear, sEvalNum, "REV2010", "Y", "Y", "Y", sUserId, curDateTime);	//업무수행평가
		    	nInsertCnt += insertAuthMuDeptHeadMnrSvr(sEvalYear, sEvalNum, "REV2020", "Y", "N", "N", "2", sUserId, curDateTime);	//근무태도평가
		    	nInsertCnt += insertAuthMuDeptHeadMnrSvr(sEvalYear, sEvalNum, "REV2030", "Y", "N", "N", "3", sUserId, curDateTime);	//서비스평가
	    	}
	    	
		    return nInsertCnt;
	    }
	    
	    /**
	     * <p> 투표소장, 1차평가자, 근태, 서비스 권한 부서별 재부여 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    public int reWorkEvalDept(String sEvalYear, String sEvalNum, String sAprvDpet, String sUserId) {
	    	int nInsertCnt = 0;
	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	    	
	    	//deleteAuthMu(sEvalYear, sEvalNum, sAprvDpet, 4);	//권한회수

	    	//권한 부여
	    	//투표소장, 1차 평가자 권한부여
	    	insertAuthAplyReleaMng(sEvalYear, sEvalNum, sAprvDpet, sUserId);	//투표소장, 1차 평가자 시스템 사용 권한부여
	    	insertAuthMuReleaMng(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//종사원평가
	    	insertAuthMuReleaMng(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//평가진행
	    	insertAuthMuReleaMng(sEvalYear, sEvalNum, "REV2010", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//업무수행평가

	    	//근무태도평가자 권한부여
	    	insertAuthAplyMnr(sEvalYear, sEvalNum, sAprvDpet, sUserId);	//근무태도평가자 시스템 사용 권한부여
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//종사원평가
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//평가진행
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "REV2020", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//근무태도평가
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "REV3200", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//발매실적제외기간

	    	//서비스태도평가자
	    	insertAuthAplySvr(sEvalYear, sEvalNum, sAprvDpet, sUserId);	//서비스평가자 시스템 사용 권한부여
	    	insertAuthMuSvr(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//종사원평가
	    	insertAuthMuSvr(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//평가진행
	    	insertAuthMuSvr(sEvalYear, sEvalNum, "REV2030", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//서비스평가

        	
	    	return nInsertCnt;
	    }
	    
	    /**
	     * <p> 부서별 업무수행평가 화면 권한회수 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteAuthMuOne(String sEvalYear, String sEvalNum, String sEvDept, String sMenuId, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	    	param.setWhereClauseParameter(i++, sMenuId);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d13", param);
	        
	        return dmlcount;
	    }
}
