/*================================================================================
 * 시스템			: 명예심판관리
 * 소스파일 이름	: snis.rbm.business.rbs11030.activity.SaveHeal.java
 * 파일설명		: 명예심판관리 등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-2
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1130.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHnrRefreWnr extends SnisActivity {
		public SaveHnrRefreWnr(){}
		
		
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

	        sDsName = "dsHnrRefreWnr";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
				        nSaveCount += insertHnrRefreTms(record);
			        } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
				        nSaveCount += updateHnrRefreTms(record);
			        } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
			        	nDeleteCount += deleteHnrRefreTms(record);	            	
		            }		        
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    /**
	     * <p> 명예심판 직접 입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertHnrRefreTms(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));    	// 1. 개최기관코드
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));   	// 2. 기준년도
	        param.setValueParamter(i++, record.getAttribute("TMS"));    		// 3. 차수
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));    	// 4. 개최기관코드
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));   	// 5. 기준년도
	        param.setValueParamter(i++, record.getAttribute("TMS"));    		// 6. 차수
	        param.setValueParamter(i++, record.getAttribute("REQ_STND_YEAR"));  // 7. 요청년도
	        param.setValueParamter(i++, record.getAttribute("REQ_SEQ_NO"));     // 8. 요청연번
	        param.setValueParamter(i++, record.getAttribute("RMK"));     		// 9. 비고 
	        param.setValueParamter(i++, record.getAttribute("INST_MTH"));     	// 10. 신청구분 
	        param.setValueParamter(i++, SESSION_USER_ID);                       // 11.입력자 ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1130_i01", param);
	        
	        return dmlcount;
	    }
	    	
	    /**
	     * <p> 명예심판 직접입력자료 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateHnrRefreTms(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("REQ_STND_YEAR"));  // 1. 요청년도
	        param.setValueParamter(i++, record.getAttribute("REQ_SEQ_NO"));     // 2. 요청연번
	        param.setValueParamter(i++, record.getAttribute("RMK"));     		// 3. 비고 
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 4. 사용자 ID     
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 5. 개최기관코드
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   // 6. 기준년도
	        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));   		// 7. 차수
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));   	// 8. 연번	        
	        param.setWhereClauseParameter(i++, record.getAttribute("INST_MTH"));   	// 8. 연번	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1130_u01", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 명예심판 직접입력자료 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteHnrRefreTms(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 1. 개최기관코드
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   // 2. 기준년도
	        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));   		// 3. 차수
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));   	// 4. 연번	        
	        param.setWhereClauseParameter(i++, record.getAttribute("INST_MTH"));   	// 5. 신청구분	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1130_d01", param);

	        return dmlcount;
	    }
}
