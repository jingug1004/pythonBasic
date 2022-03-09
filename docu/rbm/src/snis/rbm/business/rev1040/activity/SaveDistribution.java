/*================================================================================
 * 시스템			: 평가배분표   생
 * 소스파일 이름	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * 파일설명		: 평가배분표 저장
 * 작성자			: 배태일
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

public class SaveDistribution extends SnisActivity {
		public SaveDistribution(){}
		
		
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
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//평가년도
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//평가회차
	        
	        SaveEVMistr SaveEVMistr = new SaveEVMistr();

	        if( !SaveEVMistr.getUpdateYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
	    			throw new Exception(); 
	        	} catch(Exception e) {       		
	        		this.rollbackTransaction("tx_rbm");
	        		Util.setSvcMsg(ctx, "부서별대상자를 확정한 부서가 존재하므로 기초자료를 생성하실 수 없습니다.");
	        		
	        		return;
	        	}
	        }
	        
	        sDsName = "dsDistribution";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateDistribution(record)) == 0 ) {
			        		nTempCnt = insertDistribution(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        } 
		        	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
			        	nDeleteCount = nDeleteCount + deleteDistribution(record);	            	
		            } 
		            
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> 평가배분표 입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertDistribution(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));       // 1.현재평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));        // 2.현재평가회차
	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));    // 3.평가대상인원수
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 4.예전평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 5.예전평가회차

	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));    // 6.평가대상인원수 
	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));    // 7.평가대상인원수
	        param.setValueParamter(i++, record.getAttribute("S_GRD_NUM"));       // 8. S인원수 	
	        param.setValueParamter(i++, record.getAttribute("A_GRD_NUM"));       // 9. A인원수
	        param.setValueParamter(i++, record.getAttribute("B_GRD_NUM"));       // 10.B인원수
	        param.setValueParamter(i++, record.getAttribute("C_GRD_NUM"));       // 11.C인원수
	        param.setValueParamter(i++, record.getAttribute("D_GRD_NUM"));       // 12.D인원수		        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 13.사용자 ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1040_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 평가배분표 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateDistribution(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("S_GRD_NUM"));            // 1. S인원수 
	        param.setValueParamter(i++, record.getAttribute("A_GRD_NUM"));            // 2. A인원수
	        param.setValueParamter(i++, record.getAttribute("B_GRD_NUM"));            // 3. B인원수	
	        param.setValueParamter(i++, record.getAttribute("C_GRD_NUM"));            // 4. C인원수	
	        param.setValueParamter(i++, record.getAttribute("D_GRD_NUM"));            // 5. D인원수	

	        param.setValueParamter(i++, SESSION_USER_ID);                             // 6. 사용자 ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 7. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 8. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DISTR_CD"));      // 9. 배분표코드
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_OBJ_NUM"));  // 10.평가대상인원수 

	        int dmlcount = this.getDao("rbmdao").update("rev1040_u01", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> 평가배분표 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteDistribution(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, record.getAttribute("DISTR_CD"));      // 3. 배분표코드
	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));  // 4. 평가대상인원수 
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1040_d01", param);

	        return dmlcount;
	    }

}
