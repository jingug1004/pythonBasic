/*================================================================================
 * 시스템			: 평가배분표  관리
 * 소스파일 이름	: snis.rbm.business.rev1030.activity.SaveEVDistr.java
 * 파일설명		: 평가배분표 저장
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-08-31
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

public class SaveEstmStnd extends SnisActivity {
		public SaveEstmStnd(){}
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
	    	int nSaveCount2   = 0;
	    	int nDeleteCount = 0; 
	    	String sDsName   = "";
	    	String sDsName2   = "";
	    	
	    	PosDataset ds;
	    	PosDataset ds2;
	    	int nSize        = 0;
	        int nTempCnt     = 0;
	        int nSize2        = 0;
	        int nTempCnt2     = 0;
	        
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
	        
	        sDsName = "dsTotWkFld";
	        sDsName2 = "dsItemGrd";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateEstmStnd(record)) == 0 ) {
			        		nTempCnt = insertEstmStnd(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteEstmStnd(record);	            	
		            }
		        }
		        
	        }
	        if ( ctx.get(sDsName2) != null ) {
		        ds2   		 = (PosDataset) ctx.get(sDsName2);
		        nSize2 		 = ds2.getRecordCount();

		        for ( int i = 0; i < nSize2; i++ ) {
		            PosRecord record2 = ds2.getRecord(i);

			        if ( record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt2 = updateItemGrd(record2)) == 0 ) {
			        		nTempCnt2 = insertItemGrd(record2);
			        	}
				        nSaveCount2 = nSaveCount2 + nTempCnt2;
			        	continue;
			        }
			        
		        }	        
	        }
	        
	        sDsName2 = "dsItm";
	        if ( ctx.get(sDsName2) != null ) {
		        ds2   		 = (PosDataset) ctx.get(sDsName2);
		        nSize2 		 = ds2.getRecordCount();

            	deleteItmDtl((String)ctx.get("ESTM_YEAR"), (String)ctx.get("ESTM_NUM"), (String)ctx.get("ITEM_CD"));
            	
		        for ( int i = 0; i < nSize2; i++ ) {
		            PosRecord record = ds2.getRecord(i);
		            insertItmDtl(record);
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
	    protected int insertEstmStnd(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));       // 1.평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));        // 2.평가회차
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_CD"));       // 3.평가년도
	        
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_IN"));    	// 5.평가보직분야in코드
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_NM"));       // 6.평가보직분야 
	        param.setValueParamter(i++, record.getAttribute("CNTR_JOB_NM"));       // 7.평가보직분야
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 8.사용자 ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 평가배분표 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEstmStnd(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_IN"));    // 1.평가보직분야in코드
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_NM"));       // 2.평가보직분야 
	        param.setValueParamter(i++, record.getAttribute("CNTR_JOB_NM"));
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 5. 평가회차

	        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_ITM_CD"));    // 6.평가보직분야코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_u01", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> 평가배분표 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteEstmStnd(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_CD"));    // 3.평가보직분야코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_d01", param);

	        return dmlcount;
	    }
	    
	    protected int insertItemGrd(PosRecord record2) 
	    {
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record2.getAttribute("ESTM_YEAR"));       // 1.평가년도
	        param.setValueParamter(i++, record2.getAttribute("ESTM_NUM"));        // 2.평가회차
	        param.setValueParamter(i++, record2.getAttribute("S_RATE"));       // 3.S등급배분율
	        param.setValueParamter(i++, record2.getAttribute("A_RATE"));        // 4.A등급배분율
	        param.setValueParamter(i++, record2.getAttribute("B_RATE"));       // 5.B등급배분율
	        param.setValueParamter(i++, record2.getAttribute("C_RATE"));        // 6.C등급배분율
	        param.setValueParamter(i++, record2.getAttribute("D_RATE"));        // 6.D등급배분율
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 9.사용자 ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_i02", param);
	        
	        return dmlcount;
	    }
	    protected int updateItemGrd(PosRecord record2) 
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	   
	        param.setValueParamter(i++, record2.getAttribute("S_RATE"));       // 1.S등급배분율
	        param.setValueParamter(i++, record2.getAttribute("A_RATE"));        // 2.A등급배분율
	        param.setValueParamter(i++, record2.getAttribute("B_RATE"));       // 3.B등급배분율
	        param.setValueParamter(i++, record2.getAttribute("C_RATE"));        // 4.C등급배분율
	        param.setValueParamter(i++, record2.getAttribute("D_RATE"));        // 5.D등급배분율
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 6. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_YEAR"));     // 7. 평가년도
	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_NUM"));      // 8. 평가회차

	        int dmlcount = this.getDao("rbmdao").update("rev1030_u02", param);
	        return dmlcount;
	    }
	    
	    protected int deleteItemGrd(PosRecord record) 
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 7. 평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 8. 평가회차

	        int dmlcount = this.getDao("rbmdao").update("rev1030_d02", param);
	        return dmlcount;
	    }
	   
	    /**
	     * <p> 평가보직항목 세부 추가 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItmDtl(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		
	        param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_CD"));			
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_CD"));			
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));			
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_CD"));
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_DTL"));						
	        param.setValueParamter(i++, SESSION_USER_ID);						
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 평가보직항목 세부 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteItmDtl(String sEvalYear, String sEvalNum, String sEstmItemCd) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);     // 1. 평가년도
	        param.setValueParamter(i++, sEvalNum);      // 2. 평가회차
	        param.setValueParamter(i++, sEstmItemCd);   // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d01", param);

	        return dmlcount;
	    }
}
