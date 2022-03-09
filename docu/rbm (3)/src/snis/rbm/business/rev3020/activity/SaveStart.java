/*================================================================================
 * 시스템			: 평가 개시
 * 소스파일 이름	: snis.rbm.business.rev3020.activity.SaveStart.java
 * 파일설명		: 평가 개시 : TBRF_MASTER의 플래그 값을 바꾸고, 평가자들에게 권한을 준다.
 * 작성자			: 배태일
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev3020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.EncryptSHA256;

import snis.rbm.business.rev1050.activity.*;
import snis.rbm.business.rev1060.activity.*;

public class SaveStart extends SnisActivity {
		public SaveStart(){}

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
	    	int nSaveCount = 0;
	    	
	    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        nSaveCount = updateMasterStart(sEvalYear, sEvalNum); //TBRF_EV_MASTER의 시작 플래그값 변경
	        
	        //권한부여
	        SaveAprvMana SaveAprvMana = new SaveAprvMana();
	        SaveAprvMana.reWorkEval(sEvalYear, sEvalNum, SESSION_USER_ID);
	        
	        //부서장 평가화면 부여(REV2010, REV2020, REV2030)
	        SaveAprvMana.reEval(sEvalYear, sEvalNum, SESSION_USER_ID, 3);
	        
	        //평가 사용자
	        updateEvalUser(sEvalYear, sEvalNum);
	        
	        Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> TBRF_EV_MASTER의 시작 플래그값 변경</p>
	     * @param   
	     * @return  nUptCnt int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateMasterStart(String sEvalYear, String sEvalNum)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;          
	        
	        param.setValueParamter(i++, SESSION_USER_ID);   //사용자ID
	        
	        i = 0; 
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);

	        int nUptCnt = this.getDao("rbmdao").update("rev3020_u04", param);
	        
	        return nUptCnt;
	    }
	    
	    /**
	     * <p> 평가사용자 추가하기 </p>
	     */
	    protected int updateEvalUser(String sEvalYear, String sEvalNum)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        int nInsertCnt = 0;
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet pEvalUser = this.getDao("rbmdao").find("rev3020_s18", param);
	        
	        for(int nRow = 0; nRow <  pEvalUser.count(); nRow++) {
				PosRow pr[] = pEvalUser.getAllRow();
	    		
	    		String sPswd = String.valueOf(pr[nRow].getAttribute("PSWD"));
	    		sPswd = EncryptSHA256.getEncryptSHA256(sPswd);
	    		pr[nRow].setAttribute("PSWD", sPswd);
	    		
	    		nInsertCnt += insertEvalUser(pr[nRow]);
	        }
			return nInsertCnt;
	    }
	    
	    /**
	     * <p> 평가사용자 Insert </p>
	     */
	    protected int insertEvalUser(PosRow prUser)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, prUser.getAttribute("EMP_NO"));
	        param.setValueParamter(i++, prUser.getAttribute("PSWD"));
	        param.setValueParamter(i++, prUser.getAttribute("EMP_NM"));
	        param.setValueParamter(i++, prUser.getAttribute("HP_NO"));
	        param.setValueParamter(i++, prUser.getAttribute("TEAM_DETL_CD"));
	        
	        param.setValueParamter(i++, prUser.getAttribute("TEAM_CD"));
	        param.setValueParamter(i++, prUser.getAttribute("ASSO_CD"));
	        param.setValueParamter(i++, prUser.getAttribute("WK_JOB_NM"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        int nInsertCnt = this.getDao("rbmdao").update("rev3020_i01", param);
	        
			return nInsertCnt;
	    }
}
