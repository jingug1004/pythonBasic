/*================================================================================
 * 시스템			: 건전레저사업
 * 소스파일 이름	: snis.rbm.business.rbs2010.activity.SaveHealLeisuBusiReg.java
 * 파일설명		: 건전레저사업 등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-2
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHealLeisuBusiReg extends SnisActivity {
		public SaveHealLeisuBusiReg(){}
		
		
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

	        sDsName = "dsHeal";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateHealLeisu(record)) == 0 ) {
			        		nTempCnt = insertHealLeisu(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteHealLeisu(record);	            	
		            }		        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> 건전레저사업 입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertHealLeisu(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR").toString().substring(0, 4) );  // 1.년도
	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_CD"));    // 2.건전레저코드
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));          // 3.사업명
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));          // 4.부서코드
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));           // 5.담당자ID
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR"));      // 6.사업시작년월 
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_END"));      // 7.사업종료년월
	        if(  record.getAttribute("BUSI_YM_END") == null ||  record.getAttribute("BUSI_YM_END").equals("") ){
	        	param.setValueParamter(i++, "001");      // 8.사업종료년월 - 진행
	        }else{
	        	param.setValueParamter(i++, "002");      // 8.사업종료년월 - 종료
	        }
	        
	        param.setValueParamter(i++, record.getAttribute("ORD"));      // 9.정렬순서
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                         // 10.사용자 ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2010_ins", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 건전레저사업 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateHealLeisu(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_CD"));          // 1. 건전레저코드 
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));                // 2. 사업명
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));                // 3. 부서코드	
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));                 // 4. 담당자ID	
	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR"));            // 5. 사업시작년월
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_END"));            // 6. 사업종료년월
	        if(  record.getAttribute("BUSI_YM_END") == null ||  record.getAttribute("BUSI_YM_END").equals("") ){
	        	param.setValueParamter(i++, "001");      // 7.사업종료년월 - 진행
	        }else{
	        	param.setValueParamter(i++, "002");      // 7.사업종료년월 - 종료
	        }
	        
	        param.setValueParamter(i++, record.getAttribute("ORD"));                    // 8. 정렬순서
	        param.setValueParamter(i++, SESSION_USER_ID);                               // 9. 사용자 ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("BUSI_CNTNT_CD"));   // 10. 사업내역코드
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs2010_upt", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> 건전레저사업 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteHealLeisu(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_CD"));   // 1. 사업내역코드
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2010_del", param);

	        return dmlcount;
	    }
}
