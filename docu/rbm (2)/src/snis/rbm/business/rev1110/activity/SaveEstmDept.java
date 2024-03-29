/*================================================================================
 * 시스템			: 다면평가그룹생성
 * 소스파일 이름	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * 파일설명		: 다면평가그룹 저장
 * 작성자			: 배태일
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1110.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEstmDept extends SnisActivity {
		public SaveEstmDept(){}
		
		
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
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        
	        //평가마감이 되었다면 저장 불가능
    		SavePrmRslt SavePrmRslt = new SavePrmRslt();	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setReturnParam(ctx, "RESULT", "F");
            		Util.setSvcMsg(ctx, "평가마감이 완료되어 저장하실 수 없습니다.");
            		
            		return;
            	}
	        }
	                	
	        sDsName = "dsEstmDept";

	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
		        		nTempCnt = updateEstmDept(record);
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        } 
		        }	        
	        }

	        Util.setReturnParam(ctx, "RESULT", "S");
	        Util.setSaveCount  (ctx, nSaveCount);
	    }

	    /**
	     * <p> 평가배분표 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEstmDept(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("KCSI_ESTM_SCR"));   // 1. 다면평가그룹
	        param.setValueParamter(i++, record.getAttribute("SALES_ESTM_SCR"));  // 2. 발매실적 제외부서 여부
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));      

	        int dmlcount = this.getDao("rbmdao").update("rev1110_u01", param);
	        return dmlcount;
	    }


}
