/*================================================================================
 * 시스템			: 평가대상생성
 * 소스파일 이름	: snis.rbm.business.rev1070.activity.SaveAppr.java
 * 파일설명		: 평가대상생성
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-21
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

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1050.activity.SaveAprvMana;
import snis.rbm.business.rev2010.activity.SavePrmRslt;

public class SaveAppr extends SnisActivity {
		public SaveAppr(){}
	
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
	    	String sDeptCd   = (String)ctx.get("DEPT_CD");
	        String sReGrant  = (String)ctx.get("REGRANT");
	    	
	    	int nSaveCount = 0;
	    	
	    	//대상자 확정이 되지 않았다면 불가능
	    	if( !getDeptUpdateYn(sEvalYear, sEvalNum, sDeptCd).equals("Y") ) {
	    		try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "부서별대상자확정에서 대상자를 확인한 후, 확정하신 후에 사용해주세요.");
            		
            		return;
            	}
	    	}
	    	
	    	//승인이 되었다면 불가능
	    	if( sReGrant.equals("N")) {
	    		if( getAprvYn(sEvalYear, sEvalNum, sDeptCd).equals("N") ) {
	    			try { 
	        			throw new Exception(); 
	            	} catch(Exception e) {       		
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsg(ctx, "승인요청이 되었기 때문에 저장하실 수 없습니다.");
	            		
	            		return;
	            	}
	    		}
	    	}
	    	
	    	//평가마감이 되었다면 불가능
	    	if( sReGrant.equals("Y")) {
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
	    	}
	    	
	    	deleteEval(sEvalYear, sEvalNum, sDeptCd);	//업무수행평가, 근무태도평가, 서비스평가 삭제
	    	
    		nSaveCount  = insertPrm(sEvalYear, sEvalNum, sDeptCd);	//업무수행평가
    		nSaveCount += insertMnr(sEvalYear, sEvalNum, sDeptCd);	//근무태도평가
    		nSaveCount += insertSrv(sEvalYear, sEvalNum, sDeptCd);	//서비스평가
    		
    		nSaveCount += insertPrmDtHead1 (sEvalYear, sEvalNum, sDeptCd);	//업무수행평가(투표소장이 발매원평가)
    		nSaveCount += insertPrmDtHead2 (sEvalYear, sEvalNum, sDeptCd);	//업무수행평가(발매원이 투표소장평가)
    		nSaveCount += insertPrmDtWork1(sEvalYear, sEvalNum, sDeptCd);	//비발매원 1차평가
    		nSaveCount += insertPrmDtWork2(sEvalYear, sEvalNum, sDeptCd);	//발매/비발매 2차평가
    		nSaveCount += insertMnrDt     (sEvalYear, sEvalNum, sDeptCd);	//근무태도평가상세
    		nSaveCount += insertSrvDt     (sEvalYear, sEvalNum, sDeptCd);	//서비스평가상세
    		
    		//관리자 전용
    		if( sReGrant.equals("Y")) {
    			updateAprv(sEvalYear, sEvalNum, sDeptCd); //평가대상 재생성 시, 근무태도평가,서비스평가 승인상태 초기화(관리자 전용)
    			//권한 재부여
    			SaveAprvMana SaveAprvMana = new SaveAprvMana();
    			SaveAprvMana.reWorkEvalDept(sEvalYear, sEvalNum, sDeptCd, SESSION_USER_ID);
    		}
    		
	    	Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> 업무수행평가 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertPrm(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
       
	        //UNION 이하
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertMnr(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertSrv(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가(투표소장) 발매원 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public int deleteEval(String sEvalYear, String sEvalNum, String sDeptCd) {
	    	
	    	int nDelCnt = 0;
	    	nDelCnt  = deletePrm(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteMnr(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteSrv(sEvalYear, sEvalNum, sDeptCd);
	    	
	    	nDelCnt += deletePrmDt(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteMnrDt(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteSrvDt(sEvalYear, sEvalNum, sDeptCd);
	    	
	    	return nDelCnt;
	    }
	    
	    /**
	     * <p> 업무수행평가(투표소장이 발매원 평가) Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertPrmDtHead1(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i04_1", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가(발매원이 투표소장 평가) Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertPrmDtHead2(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i04_2", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 비발매원 1차평가 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertPrmDtWork1(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 발매/비발매 2차평가 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertPrmDtWork2(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가상세 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertMnrDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가상세 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertSrvDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i08", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가 입력여부 조회  </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  rtnKey	int			사건에 걸려있는 사건이력 개수
	     * @throws  none
	     */
	    protected int getPrmCnt(String sEvalYear, String sEvalNum, String sDeptCd)  
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

	        return Integer.parseInt(rtnKey);
	    }
	    
	    /**
	     * <p> 업무태도평가 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deletePrm(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무태도평가상세 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deletePrmDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMnr(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d04", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가상세 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMnrDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteSrv(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteSrvDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서별 대상자 확정 여부 조회 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public String getDeptUpdateYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s12", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("UPDATE_YN"));

	        return rtnKey;
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
	     * <p> 근무태도평가, 서비스평가 승인상태 초기화 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateAprv(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_u08", param);
	        
	        return dmlcount;
	    }
}
