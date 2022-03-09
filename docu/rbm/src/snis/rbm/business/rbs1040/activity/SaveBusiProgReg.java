/*================================================================================
 * 시스템			: 사업품질관리
 * 소스파일 이름	: snis.rbm.business.rbs1040.activity.SaveBusiProgReg.java
 * 파일설명		: 사업진행 등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-17
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBusiProgReg extends SnisActivity {
		public SaveBusiProgReg(){}
		
		
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

	        	
	        // 사업계획 - 지연사유, 비고  UPDATE 
	        sDsName = "dsList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        
		        // LOOP 
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        nTempCnt = updateBusiPlan(record);
			        
				    nSaveCount = nSaveCount + nTempCnt;
			        
			        		        
		        }
		        // LOOP END
	        }
	        
	        
	        
	        // 집행내역 - INSERT, UPDATE, DELETE 
	        sDsName = "dsExec";
	        
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateBusiExec(record)) == 0 ) {
			        		nTempCnt = insertBusiExec(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteBusiExec(record);	            	
		            }		        
		        }	        
	        }
	        
	        
	        //  진행상태 UPDATE
	        
	        
	        

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    
	    /**
	     * <p> 사업계획 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateBusiPlan(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("QUAR_1_DELAY_RSN"));     // 1. 1분기 지연사유
	        param.setValueParamter(i++, record.getAttribute("QUAR_2_DELAY_RSN"));     // 2. 2분기 지연사유
	        param.setValueParamter(i++, record.getAttribute("QUAR_3_DELAY_RSN"));     // 3. 3분기 지연사유
	        param.setValueParamter(i++, record.getAttribute("QUAR_4_DELAY_RSN"));     // 4. 4분기 지연사유
	        
	        param.setValueParamter(i++, record.getAttribute("QUAR_1_RMK"));           // 5. 1분기 비고
	        param.setValueParamter(i++, record.getAttribute("QUAR_2_RMK"));           // 6. 2분기 비고
	        param.setValueParamter(i++, record.getAttribute("QUAR_3_RMK"));           // 7. 3분기 비고
	        param.setValueParamter(i++, record.getAttribute("QUAR_4_RMK"));           // 8. 4분기 비고
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 9. 사용자ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 10. 시개금코드
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1040_u01", param);
	        return dmlcount;
	    }
	    
	    

	    /**
	     * <p> 집행내역 등록 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertBusiExec(PosRecord record) 
	    {	   
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // 1.시개금코드
	        param.setValueParamter(i++, record.getAttribute("REPORT_NO"));     // 2.결의서번호
	        param.setValueParamter(i++, record.getAttribute("EXEC_DT"));       // 3.집행일자
	        param.setValueParamter(i++, record.getAttribute("EXEC_CNTNT"));    // 4.집행내역
	        param.setValueParamter(i++, record.getAttribute("EXEC_AMT"));      // 5.집행금액
	    	                
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 6.사용자 ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1040_i01", param);
	        
	        return dmlcount;
	      
	    }
	    
	    
	    
	    /**
	     * <p> 집행내역 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateBusiExec(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
			
	        
	        param.setValueParamter(i++, record.getAttribute("REPORT_NO"));     // 1. 결의서번호 
	        param.setValueParamter(i++, record.getAttribute("EXEC_DT"));       // 2. 집행일자
	        param.setValueParamter(i++, record.getAttribute("EXEC_CNTNT"));    // 3. 집행내역	
	        param.setValueParamter(i++, record.getAttribute("EXEC_AMT"));      // 4. 집행금액
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 5. 사용자ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("EXEC_CNTNT_CD"));   // 6. 집행내역코드
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1040_u02", param);
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
	        param.setValueParamter(i++, record.getAttribute("EXEC_CNTNT_CD"));   // 1. 집행내역코드
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1040_d01", param);

	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 진행상태 수정 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateBusiProg(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // 1. 시개금코드
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("", param);

	        return dmlcount;
	    }
}
