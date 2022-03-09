/*================================================================================
 * 시스템			: 평가마감
 * 소스파일 이름	: snis.rbm.business.rev3020.activity.SaveEstmEnd.java
 * 파일설명		: 평가마감
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
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1050.activity.*;

public class SaveEstmEnd extends SnisActivity {
		public SaveEstmEnd(){}
		
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
	    	String sDsName   = "";
	    	
	    	PosDataset ds;
	        int nSize        = 0;
	        int nTempCnt     = 0;
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        	
	        sDsName = "dsEstmEnd";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            nTempCnt = dsEstmEnd(record);	//평가마감 처리
			        nSaveCount = nSaveCount + nTempCnt;
			    }	        
	        }
	        
	        /*
	        //다면평가에서 제외여부가 체크되어 제외된 사람 (전체 평균값 적용)
	        sDsName = "dsExcept";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            nTempCnt   = updateExcept(record);
			        nSaveCount = nSaveCount + nTempCnt;
			    }	        
	        }
	        
	        updateEvWkMult(sEvalYear, sEvalNum);	//다면평가 그룹이 0인 사람(그룹이 3명이 넘지 않는 사람)
	        
	        */
	        
	        updateRec(sEvalYear, sEvalNum);
	        
	        updateAvgExcept(sEvalYear, sEvalNum);	//평균점수 적용
	        
	        //평가를 하지 않은 사람(최하점 적용)
	        sDsName = "dsNoEval";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            nTempCnt   = updateExcept(record);
			        nSaveCount = nSaveCount + nTempCnt;
			    }	        
	        }
	        
	        //권한회수
	        SaveAprvMana SaveAprvMana = new SaveAprvMana();
	        SaveAprvMana.deleteAuthMu(sEvalYear, sEvalNum, "", 3, SESSION_USER_ID);
	        
	        //임시사용자 삭제
	        deleteUser();
	        
	        Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> 평가마감 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int dsEstmEnd(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 5. 평가회차

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u01", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 제외자 점수 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateExcept(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));   
	        param.setValueParamter(i++, SESSION_USER_ID);    

	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3020_u03", param);
	        return dmlcount;
	    }

	    /**
	     * <p> 다면평가자 3명이 않되는 그룹에 평균값 넣기 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEvWkMult(String sEvalYear, String sEvalNum)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    	param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	  
	       // i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u02", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 임시사용자 삭제 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteUser()
	    {			
	        int dmlcount = this.getDao("rbmdao").update("rev3020_d01");
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 평균값 적용(제외가 체크된 사람 / 나를 평가한 사람이 80%가 넘지 않는 사람 / 다면평가그룹이 3명 이하인 사람(그룹 코드가 0인 사람) </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateAvgExcept(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    	param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, SESSION_USER_ID); 
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 평균값 적용(제외가 체크된 사람 / 나를 평가한 사람이 80%가 넘지 않는 사람 / 다면평가그룹이 3명 이하인 사람(그룹 코드가 0인 사람) </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateRec(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    	param.setValueParamter(i++, SESSION_USER_ID); 
	    	param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u06", param);
	        
	        return dmlcount;
	    }
}
