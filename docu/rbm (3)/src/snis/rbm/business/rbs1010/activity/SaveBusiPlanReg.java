/*================================================================================
 * 시스템			: 사업품질관리
 * 소스파일 이름	: snis.rbm.business.rbs1010.activity.SaveBusiPlanReg.java
 * 파일설명		: 사업계획등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-09
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBusiPlanReg extends SnisActivity {
		public SaveBusiPlanReg(){}
		
		
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

	        sDsName = "dsList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateBusiPlan(record)) == 0 ) {
			        		nTempCnt = insertBusiPlan(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	deleteFileMana(record);		// 첨부파일 삭제
		            	deleteBusiExec(record);		// 집행내역 삭제
			        	nDeleteCount = nDeleteCount + deleteBusiPlan(record);	// 사업계획 등록 삭제
		            }		        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> 사업계획등록 입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertBusiPlan(PosRecord record) 
	    {	   
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));     // 1.년도
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_TMS"));   // 2.승인차수
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));    // 3.회계구분코드
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));       // 4.사업명
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_AMT"));   // 5.승인금액

	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));       // 6.부서코드 
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));        // 7.담당자ID
	    	                
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 8.사용자 ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 사업계획등록 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateBusiPlan(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));     // 1. 년도 
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_TMS"));   // 2. 승인차수
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));    // 3. 회계구분코드	
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));       // 4. 사업명
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_AMT"));   // 5. 승인금액
	        
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));       // 6. 부서코드
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));        // 7. 담당자ID
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 8. 사용자ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 9. 시개금코드
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1010_u01", param);
	        return dmlcount;
	    }

	    
	    /** 사업계획등록 정보 삭제시 Relation 되어 있는 자식 테이블부터 삭제 후 마스터 정보를 삭제 한다. (무결성 제약조건 위배 이유) **/
	    /**
	     * <p> 첨부파일  삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteFileMana(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"      ) );	// 첨부파일
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_d_file", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> 집행내역 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteBusiExec(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // 시개금코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_d_exec", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> 사업계획등록 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteBusiPlan(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // 시개금코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_d01", param);

	        return dmlcount;
	    }
}
