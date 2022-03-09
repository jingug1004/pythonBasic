/*================================================================================
 * 시스템			: 평가대상생성
 * 소스파일 이름	: snis.rbm.business.rev1070.activity.SaveMultAppr.java
 * 파일설명		: 평가대상생성
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-23
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1080.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1080.activity.*;
import snis.rbm.business.rev2010.activity.SavePrmRslt;

public class SaveMultAppr extends SnisActivity {
		public SaveMultAppr(){}
	
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
	        String sGrant    = (String)ctx.get("GRANT");
	        
	    	//승인요청 시, 저장 불가능
	    	SaveEmp SaveEmp = new SaveEmp();
	    	
	    	if( sGrant.equals("N") ) {	//REV1080(다면평가그룹선정)에서 넘어왔다면 
		        if( SaveEmp.getCfmYn(sEvalYear, sEvalNum).equals("N") ) {
		        	try { 
	        			throw new Exception(); 
	            	} catch(Exception e) {       		
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsg(ctx, "승인요청이 들어갔기 때문에 평가대상생성을 하실 수 없습니다.");
	            		Util.setReturnParam(ctx, "RESULT", "F");
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
            		Util.setSvcMsg(ctx, "평가마감이 완료되어 평가대상생성을 하실 수 없습니다.");
            		Util.setReturnParam(ctx, "RESULT", "F");
            		return;
            	}
	        }
	        
	    	int nSaveCount = 0;
	    	
	    	deleteMultEval(sEvalYear, sEvalNum, sDeptCd);
    		
	    	insertMult  (sEvalYear, sEvalNum, sDeptCd);	//다면평가자 
	    	insertMultDt(sEvalYear, sEvalNum, sDeptCd);	//다면평가자 상세
	    	
	    	Util.setReturnParam(ctx, "RESULT", "S");
	    	Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> 다면평가자 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertMult(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 다면평가자상세 Insert </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertMultDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev1080_i02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 다면평가전체 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public int deleteMultEval(String sEvalYear, String sEvalNum, String sDeptCd) {
	    	int nDelCnt = 0;
	    	
	    	nDelCnt  = deleteMult  (sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteMultDt(sEvalYear, sEvalNum, sDeptCd);
	    	
	    	return nDelCnt;
	    }
	   
		
	    /**
	     * <p> 다면평가자 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMult(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d11", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 다면평가자상세 Delete </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMultDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d12", param);
	        
	        return dmlcount;
	    }
}
