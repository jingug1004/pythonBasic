/*================================================================================
 * 시스템			: 건전레저관리
 * 소스파일 이름	: snis.rbm.business.rbs2020.activity.SaveHealLeisuPrsnNumReg.java
 * 파일설명		: 건전레저 인원등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs2020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHealLeisuPrsnNumReg extends SnisActivity {
		public SaveHealLeisuPrsnNumReg(){}
		
		
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

	        sDsName = "dsList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) 
			        {
			        	String sIsNew = (String)record.getAttribute("IS_NEW");
			        	
			        	if(sIsNew.equals("T"))
			        	{
			        		nTempCnt = insertHealLeisuExec(record);
			        	}
			        	else if(sIsNew.equals("F"))
			        	{
			        		nTempCnt = updateHealLeisuExec(record);
			        	}
			        	
					    nSaveCount = nSaveCount + nTempCnt;
			        	//continue;
			        }	        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );	   
	        
	    }
	    


	    /**
	     * <p> 건전레저인원등록 입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertHealLeisuExec(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_CD"));     // 1.사업내역코드
	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_YEAR"));   // 2.건전레저년도
	        param.setValueParamter(i++, record.getAttribute("EXEC_YEAR"));         // 3.집행년도
	        param.setValueParamter(i++, record.getAttribute("EXEC_MM"));           // 4.집행월
	        param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));          // 5.인원
	      	        
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 6.사용자 ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2020_ins", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 건전레저사업 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateHealLeisuExec(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        
	        param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));                // 1. 인원	        
	        param.setValueParamter(i++, SESSION_USER_ID);                                // 2. 사용자 ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("BUSI_CNTNT_CD"));    // 3. 사업내역코드
	        //param.setWhereClauseParameter(i++, record.getAttribute("HEAL_LEISU_YEAR"));  // 4. 건전레저년도
	        param.setWhereClauseParameter(i++, record.getAttribute("EXEC_YEAR"));        // 5. 집행년도
	        param.setWhereClauseParameter(i++, record.getAttribute("EXEC_MM"));          // 6. 집행월
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs2020_upt", param);
	        return dmlcount;
	    }
	   
}
