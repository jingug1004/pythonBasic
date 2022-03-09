/*================================================================================
 * 시스템			: 사업품질관리
 * 소스파일 이름	: snis.rbm.business.rbs1090.activity.SaveYearAccuAmt.java
 * 파일설명		: 년도별 적립금액 등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-23
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1090.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveYearAccuAmt extends SnisActivity {
		public SaveYearAccuAmt(){}
		
		
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
			        		nTempCnt = insertSav(record);
			        	}
			        	else if(sIsNew.equals("F"))
			        	{
			        		nTempCnt = updateSav(record);
			        	}
			        	
					    nSaveCount = nSaveCount + nTempCnt;
			        	//continue;
			        }	        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );	   
	        
	    }
	    


	    /**
	     * <p> 적립금액 등록 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertSav(PosRecord record) 
	    {	   
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));   // 1.사업년도	        
	        param.setValueParamter(i++, record.getAttribute("ACC_CD"));      // 2.회계구분코드
	        param.setValueParamter(i++, record.getAttribute("SAV_AMT"));     // 3.적립금액
	        param.setValueParamter(i++, record.getAttribute("AMU_AMT"));     // 4.누적잔액
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                    // 5.사용자 ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1090_i_sav", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 적립금액 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateSav(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("SAV_AMT"));    // 1.적립금액
	        param.setValueParamter(i++, record.getAttribute("AMU_AMT"));    // 2.누적잔액
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                   // 3. 사용자ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("SAV_CNTNT_CD"));    // 3. 적립내역코드
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1090_u_sav", param);
	        return dmlcount;
	    }
	    
}
