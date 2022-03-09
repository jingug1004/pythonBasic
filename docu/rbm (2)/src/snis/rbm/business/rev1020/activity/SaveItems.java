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
package snis.rbm.business.rev1020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

public class SaveItems extends SnisActivity {
		public SaveItems(){}
		
		
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
	    	
	    	String sDsNamePrmS   = "";
	    	String sDsNamePrmL   = "";
	    	String sDsNamePrmGrd   = "";
	    	
	    	String sDsNameMnrLItm = "";
	    	String sDsNameMnrMItm = "";
	    	String sDsNameMnrSItm = "";
	    	
	    	String sDsNameSvrLItm = "";
	    	String sDsNameSvrMItm = "";
	    	String sDsNameSvrSItm = "";
	    	
	    	String sDsNameMultLItm   = "";
	    	String sDsNameMultMItm   = "";
	    	String sDsNameMultGrd   = "";
	    	
	    	PosDataset ds1;
	    	PosDataset ds2;
	    	PosDataset ds3;
	    	PosDataset ds4;
	    	PosDataset ds5;
	    	PosDataset ds6;
	    	PosDataset ds7;
	    	PosDataset ds8;
	    	PosDataset ds9;
	    	PosDataset ds10;
	    	PosDataset ds11;
	    	PosDataset ds12;
	    	PosDataset ds13;
	    	PosDataset ds14;
	    	PosDataset ds15;
	    	PosDataset ds16;
	    	
	    	int nSizePrmL       = 0;
	    	int nSizePrmS       = 0;
	    	int nSizePrmGrd     = 0;
	    	
	    	int nSizeMnrLItm    = 0;
	    	int nSizeMnrMItm    = 0;
	    	int nSizeMnrSItm    = 0;
	    	
	    	int nSizeSvrLItm    = 0;
	    	int nSizeSvrMItm    = 0;
	    	int nSizeSvrSItm    = 0;
	    	
	    	int nSizeMultLItm  = 0;
	    	int nSizeMultMItm  = 0;
	    	int nSizeMultGrd   = 0;
	    	
	        int nPrmJobCnt     = 0;
	        int nPrmLrgCnt     = 0;
	        int nPrmGrdCnt     = 0;
	        
	        int nMnrJobCnt     = 0;
	        int nMnrLrgCnt     = 0;
	        
	        int nSvrJobCnt     = 0;
	        int nSvrLrgCnt     = 0;
	        
	        int nMultJobCnt    = 0;
	        int nMultLrgCnt    = 0;
	        int nMultGrdCnt    = 0;
	        
	        sDsNamePrmL   = "dsPrmLItm";
	        sDsNamePrmS   = "dsPrmSItm";
	        sDsNamePrmGrd = "dsItmGrdPrm";
	        
	        sDsNameMnrLItm = "dsMnrLItm";
	        sDsNameMnrMItm = "dsMnrMItm";
	        sDsNameMnrSItm = "dsMnrSItm";
	        
	        sDsNameSvrLItm = "dsSvrLItm";
	        sDsNameSvrMItm = "dsSvrMItm";
	        sDsNameSvrSItm = "dsSvrSItm";
	        
	        sDsNameMultLItm = "dsMultLItm";
	        sDsNameMultMItm = "dsMultMItm";
	        sDsNameMultGrd = "dsItmGrdMult";
	        
	        String sDsNameItm1 = "dsItm1";
	        String sDsNameItm2 = "dsItm2";
	        String sDsNameItm3 = "dsItm3";
	        String sDsNameItm4 = "dsItm4";
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
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
	        
	        if ( ctx.get(sDsNamePrmL) != null ) {
		        ds1   		 = (PosDataset) ctx.get(sDsNamePrmL);
		        nSizePrmL 		 = ds1.getRecordCount();

		        for ( int i = 0; i < nSizePrmL; i++ ) {
		            PosRecord recordPrmL = ds1.getRecord(i);
		            
		            if ( recordPrmL.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordPrmL.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nPrmJobCnt = updateItemsPrmJobItm(recordPrmL)) == 0 ) {
			        		nPrmJobCnt = insertItemsPrmJobItm(recordPrmL);
			        	}
			        	
				        nSaveCount = nSaveCount + nPrmJobCnt + nPrmLrgCnt;
			        	continue;
			        }
		            
		            if (recordPrmL.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	deletePrmJobItm2(recordPrmL);
		            	nDeleteCount = nDeleteCount + deletePrmJobItm(recordPrmL);	
		            }
		        }	 
	        }
	        
	        if ( ctx.get(sDsNamePrmS) != null ) {
		        ds2   		 = (PosDataset) ctx.get(sDsNamePrmS);
		        nSizePrmS 		 = ds2.getRecordCount();
		        
		        for ( int j = 0; j < nSizePrmS; j++ ) {
		            PosRecord recordPrmS = ds2.getRecord(j);
		            
		            if ( recordPrmS.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordPrmS.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nPrmJobCnt = updateItemsPrmLrgItm(recordPrmS)) == 0 ) {
			        		nPrmJobCnt = insertItemsPrmLrgItm(recordPrmS);
			        	}
			        	
				        nSaveCount = nSaveCount + nPrmJobCnt + nPrmLrgCnt;
			        	continue;
			        }
		            
		            
		            if (recordPrmS.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	nDeleteCount = nDeleteCount + deletePrmLrgItm(recordPrmS);	
		            }
		        }	 
	        }
	        
	        if ( ctx.get(sDsNamePrmGrd) != null ) {
		        ds3   		 = (PosDataset) ctx.get(sDsNamePrmGrd);
		        nSizePrmGrd 		 = ds3.getRecordCount();
		       
		        for ( int j = 0; j < nSizePrmGrd; j++ ) {
		            PosRecord recordPrmGrd = ds3.getRecord(j);
		           
		            if ( recordPrmGrd.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordPrmGrd.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nPrmGrdCnt = updateItemsPrmGrd(recordPrmGrd)) == 0 ) {
			        		 insertItemsPrmGrd(recordPrmGrd);
			        	}
			        	continue;
			        }
		            
		        }	 
	        }
	        
	        if ( ctx.get(sDsNameMnrLItm) != null ) {
		        ds4   		 = (PosDataset) ctx.get(sDsNameMnrLItm);
		        nSizeMnrLItm 		 = ds4.getRecordCount();

		        for ( int i = 0; i < nSizeMnrLItm; i++ ) {
		            PosRecord recordMnrLItm = ds4.getRecord(i);
		            
		            if ( recordMnrLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordMnrLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nMnrJobCnt = updateItemsMnrJobItm(recordMnrLItm)) == 0 ) {
			        		nMnrJobCnt = insertItemsMnrJobItm(recordMnrLItm);
			        	}
			        	
				        nSaveCount = nSaveCount + nMnrJobCnt;
			        	continue;
			        }
		            
		            if (recordMnrLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	deleteMnrJobItm2(recordMnrLItm);
		            	nDeleteCount = nDeleteCount + deleteMnrJobItm(recordMnrLItm);	
		            }
		        }	 
	        }
	        if ( ctx.get(sDsNameMnrSItm) != null ) {
	        	ds5   		 = (PosDataset) ctx.get(sDsNameMnrSItm);
	        	nSizeMnrSItm 		 = ds5.getRecordCount();
		        
		        for ( int j = 0; j < nSizeMnrSItm; j++ ) {
		            PosRecord recordMnrSItm = ds5.getRecord(j);
		            
		            if ( recordMnrSItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordMnrSItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nMnrLrgCnt = updateItemsMnrLrgItm(recordMnrSItm)) == 0 ) {
			        		nMnrLrgCnt = insertItemsMnrLrgItm(recordMnrSItm);
			        	}
			        	
				        nSaveCount = nSaveCount + nMnrLrgCnt;
			        	continue;
			        }
		            
		            
		            if (recordMnrSItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	nDeleteCount = nDeleteCount + deleteMnrLrgItm(recordMnrSItm);	
		            }
		        }
	        }
	        
	        if ( ctx.get(sDsNameMnrMItm) != null ) {
	        	ds6   		 = (PosDataset) ctx.get(sDsNameMnrMItm);
	        	nSizeMnrMItm 		 = ds6.getRecordCount();
		        
		        for ( int k = 0; k < nSizeMnrMItm; k++ ) {
		        	PosRecord recordMnrMItm = ds6.getRecord(k);
		        	
		        		updateMnrBestScr(recordMnrMItm);
		        	
		        }
	        }
	        
	        if ( ctx.get(sDsNameSvrLItm) != null ) {
		        ds7   		 = (PosDataset) ctx.get(sDsNameSvrLItm);
		        nSizeSvrLItm 		 = ds7.getRecordCount();

		        for ( int i = 0; i < nSizeSvrLItm; i++ ) {
		            PosRecord recordSvrLItm = ds7.getRecord(i);
		            
		            if ( recordSvrLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordSvrLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nSvrJobCnt = updateItemsSvrJobItm(recordSvrLItm)) == 0 ) {
			        		nSvrJobCnt = insertItemsSvrJobItm(recordSvrLItm);
			        	}
			        	
				        nSaveCount = nSaveCount + nSvrJobCnt;
			        	continue;
			        }
		            
		            if (recordSvrLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	deleteSvrJobItm2(recordSvrLItm);
		            	nDeleteCount = nDeleteCount + deleteSvrJobItm(recordSvrLItm);	
		            }
		        }	 
	        }
	        if ( ctx.get(sDsNameSvrSItm) != null ) {
	        	ds8   		 = (PosDataset) ctx.get(sDsNameSvrSItm);
	        	nSizeSvrSItm 		 = ds8.getRecordCount();
		        
		        for ( int j = 0; j < nSizeSvrSItm; j++ ) {
		            PosRecord recordSvrSItm = ds8.getRecord(j);
		            
		            if ( recordSvrSItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordSvrSItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nSvrLrgCnt = updateItemsSvrLrgItm(recordSvrSItm)) == 0 ) {
			        		nSvrLrgCnt = insertItemsSvrLrgItm(recordSvrSItm);
			        	}
			        	
				        nSaveCount = nSaveCount + nSvrLrgCnt;
			        	continue;
			        }
		            
		            
		            if (recordSvrSItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	nDeleteCount = nDeleteCount + deleteSvrLrgItm(recordSvrSItm);	
		            }
		        }
	        }
	        
	        if ( ctx.get(sDsNameSvrMItm) != null ) {
	        	ds9   		 = (PosDataset) ctx.get(sDsNameSvrMItm);
	        	nSizeSvrMItm 		 = ds9.getRecordCount();
		        
		        for ( int k = 0; k < nSizeSvrMItm; k++ ) {
		        	PosRecord recordSvrMItm = ds9.getRecord(k);
		        	//if ( recordSvrMItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
					//  || recordSvrMItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		updateSvrBestScr(recordSvrMItm);
		        	//}
		        }
	        }
	        if ( ctx.get(sDsNameMultLItm) != null ) {
		        ds10   		 = (PosDataset) ctx.get(sDsNameMultLItm);
		        nSizeMultLItm 		 = ds10.getRecordCount();

		        for ( int i = 0; i < nSizeMultLItm; i++ ) {
		            PosRecord recordMultLItm = ds10.getRecord(i);
		            
		            if ( recordMultLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordMultLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nMultJobCnt = updateItemsMultJobItm(recordMultLItm)) == 0 ) {
			        		nMultJobCnt = insertItemsMultJobItm(recordMultLItm);
			        	}
			        	
				        nSaveCount = nSaveCount + nMultJobCnt + nMultLrgCnt;
			        	continue;
			        }
		            
		            if (recordMultLItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	deleteMultJobItm2(recordMultLItm);
		            	nDeleteCount = nDeleteCount + deleteMultJobItm(recordMultLItm);	
		            }
		        }	 
	        }
	        
	        if ( ctx.get(sDsNameMultMItm) != null ) {
		        ds11   		 = (PosDataset) ctx.get(sDsNameMultMItm);
		        nSizeMultMItm 		 = ds11.getRecordCount();
		        
		        for ( int j = 0; j < nSizeMultMItm; j++ ) {
		            PosRecord recordMultMItm = ds11.getRecord(j);
		            
		            if ( recordMultMItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		            	nMultJobCnt = updateItemsMultLrgItm(recordMultMItm);
		            }
			        if ( recordMultMItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	nMultLrgCnt = insertItemsMultLrgItm(recordMultMItm);
			        }
			        nSaveCount = nSaveCount + nMultJobCnt + nMultLrgCnt;
		        	
		            if (recordMultMItm.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	nDeleteCount = nDeleteCount + deleteMultLrgItm(recordMultMItm);	
		            }
		        }	 
	        }
	        
	        if ( ctx.get(sDsNameMultGrd) != null ) {
	        	ds12   		 = (PosDataset) ctx.get(sDsNameMultGrd);
		        nSizeMultGrd 		 = ds12.getRecordCount();
		       
		        for ( int j = 0; j < nSizeMultGrd; j++ ) {
		            PosRecord recordMultGrd = ds12.getRecord(j);
		           
		            if ( recordMultGrd.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || recordMultGrd.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nMultGrdCnt = updateItemsMultGrd(recordMultGrd)) == 0 ) {
			        		 insertItemsMultGrd(recordMultGrd);
			        	}
			        	continue;
			        }
		            
		        }	 
	        }
	        
	        if ( ctx.get(sDsNameItm1) != null ) {
	        	ds13   	  = (PosDataset) ctx.get(sDsNameItm1);
		        int nSize = ds13.getRecordCount();
		        
		        deleteItmDtl((String)ctx.get("ESTM_YEAR"), (String)ctx.get("ESTM_NUM"), "1");
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds13.getRecord(i);
		            insertItmDtl(record); 
		        }	 
	        }
	        
	        if ( ctx.get(sDsNameItm2) != null ) {
	        	ds14   	  = (PosDataset) ctx.get(sDsNameItm2);
		        int nSize = ds14.getRecordCount();
		        
		        deleteItmDtl((String)ctx.get("ESTM_YEAR"), (String)ctx.get("ESTM_NUM"), "2");
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds14.getRecord(i);
		            insertItmDtl(record);
		            
		        }	 
	        }
	        
	        if ( ctx.get(sDsNameItm3) != null ) {
	        	ds14   	  = (PosDataset) ctx.get(sDsNameItm3);
		        int nSize = ds14.getRecordCount();
		        
		        deleteItmDtl((String)ctx.get("ESTM_YEAR"), (String)ctx.get("ESTM_NUM"), "3");
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds14.getRecord(i);
		            insertItmDtl(record);
		            
		        }	 
	        }
	        
	        if ( ctx.get(sDsNameItm4) != null ) {
	        	ds14   	  = (PosDataset) ctx.get(sDsNameItm4);
		        int nSize = ds14.getRecordCount();
		        
		        deleteItmDtl((String)ctx.get("ESTM_YEAR"), (String)ctx.get("ESTM_NUM"), "4");
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds14.getRecord(i);
		            insertItmDtl(record);   
		        }	 
	        }
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    
	    /**
	     * <p> 업무수행평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsPrmJobItm(PosRecord recordPrmL)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        
	        param.setValueParamter(i++, recordPrmL.getAttribute("CNTR_ITM_IN"));            // 1. 보직IN 
	        param.setValueParamter(i++, recordPrmL.getAttribute("CNTR_ITM_NM"));            // 2. 보직IN명
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmL.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, recordPrmL.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setWhereClauseParameter(i++, recordPrmL.getAttribute("CNTR_ITM_CD"));      // 6. 보직항목코드

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u011", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 업무수행평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsPrmJobItm(PosRecord recordPrmL)
	    {			
    		PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, recordPrmL.getAttribute("ESTM_YEAR"));        // 1. 평가년도
	        param.setValueParamter(i++, recordPrmL.getAttribute("ESTM_NUM"));         // 2. 평가회차
	        param.setValueParamter(i++, recordPrmL.getAttribute("CNTR_ITM_CD"));      // 3. 보직항목코드
	        param.setValueParamter(i++, recordPrmL.getAttribute("CNTR_ITM_IN"));      // 4. 보직IN
	        param.setValueParamter(i++, recordPrmL.getAttribute("CNTR_ITM_NM"));      // 5. 보직IN명

	        param.setValueParamter(i++, SESSION_USER_ID);                            // 6. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i011", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 업무수행평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsPrmLrgItm(PosRecord recordPrmS)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_LITEM_NM"));         // 1. 평가대항목명
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_RATE"));             // 2. 평가대항목명
	        param.setValueParamter(i++, recordPrmS.getAttribute("LITEM_DEF"));             // 3. 역량정의
	        param.setValueParamter(i++, recordPrmS.getAttribute("LITEM_CNDR"));            // 4. 평가시 고려요소
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_LITEM_TYPE"));       // 5. 평가대항목명
	        param.setValueParamter(i++, SESSION_USER_ID);                                  // 6. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmS.getAttribute("ESTM_YEAR"));      // 7. 평가년도
	        param.setWhereClauseParameter(i++, recordPrmS.getAttribute("ESTM_NUM"));       // 8. 평가회차
	        param.setWhereClauseParameter(i++, recordPrmS.getAttribute("CNTR_ITM_CD"));    // 9. 보직항목코드
	        param.setWhereClauseParameter(i++, recordPrmS.getAttribute("ESTM_LITEM_CD"));  //10. 평가대항목코드
	        param.setWhereClauseParameter(i++, recordPrmS.getAttribute("FST_SND_GBN"));            // 1. 1차/2차 구분

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u012", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 업무수행평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsPrmLrgItm(PosRecord recordPrmS)
	    {			
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_YEAR"));       // 1. 평가년도
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_NUM"));        // 2. 평가회차
	        param.setValueParamter(i++, recordPrmS.getAttribute("CNTR_ITM_CD"));     // 3. 보직항목코드
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_LITEM_CD"));   // 4. 평가대항목코드
	        param.setValueParamter(i++, recordPrmS.getAttribute("FST_SND_GBN"));     // 5. 1차/2차 구분
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_LITEM_NM"));   // 6. 평가대항목명
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_RATE"));       // 7. 평가비율
	        param.setValueParamter(i++, recordPrmS.getAttribute("LITEM_DEF"));       // 8. 평가항목 정의
	        param.setValueParamter(i++, recordPrmS.getAttribute("LITEM_CNDR"));      // 9. 평가시 고려요소
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_LITEM_TYPE")); //10. 평가항목 구분
	        param.setValueParamter(i++, SESSION_USER_ID);                            //11. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i012", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deletePrmJobItm(PosRecord recordPrmL) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmL.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordPrmL.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordPrmL.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d011", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 업무수행평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deletePrmJobItm2(PosRecord recordPrmS) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordPrmS.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d012", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 업무수행평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deletePrmLrgItm(PosRecord recordPrmS) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordPrmS.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        param.setValueParamter(i++, recordPrmS.getAttribute("ESTM_LITEM_CD"));    // 4.평가항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d013", param);

	        return dmlcount;
	    }
	    
	    protected int insertItemsPrmGrd(PosRecord recordPrmGrd) 
	    {
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("ESTM_YEAR"));       // 1.평가년도
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("ESTM_NUM"));        // 2.평가회차
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("S_RATE"));       // 3.S등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("A_RATE"));        // 4.A등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("B_RATE"));       // 5.B등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("C_RATE"));        // 6.C등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("D_RATE"));        // 6.D등급배분율
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 9.사용자 ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i014", param);
	        
	        return dmlcount;
	    }
	    
	    protected int updateItemsPrmGrd(PosRecord recordPrmGrd) 
	    {
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("S_RATE"));       // 1.S등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("A_RATE"));        // 2.A등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("B_RATE"));       // 3.B등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("C_RATE"));        // 4.C등급배분율
	        param.setValueParamter(i++, recordPrmGrd.getAttribute("D_RATE"));        // 5.D등급배분율
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 6. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmGrd.getAttribute("ESTM_YEAR"));     // 7. 평가년도
	        param.setWhereClauseParameter(i++, recordPrmGrd.getAttribute("ESTM_NUM"));      // 8. 평가회차

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u014", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsMnrJobItm(PosRecord recordMnrLItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("CNTR_ITM_IN"));            // 1. 보직IN 
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("CNTR_ITM_NM"));            // 2. 보직IN명
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordMnrLItm.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, recordMnrLItm.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setWhereClauseParameter(i++, recordMnrLItm.getAttribute("CNTR_ITM_CD"));      // 6. 보직항목코드

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u021", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 근무태도평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsMnrJobItm(PosRecord recordMnrLItm)
	    {			
    		PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, recordMnrLItm.getAttribute("ESTM_YEAR"));        // 1. 평가년도
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("ESTM_NUM"));         // 2. 평가회차
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("CNTR_ITM_CD"));      // 3. 보직항목코드
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("CNTR_ITM_IN"));      // 4. 보직IN
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("CNTR_ITM_NM"));      // 5. 보직IN명

	        param.setValueParamter(i++, SESSION_USER_ID);                            // 6. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i021", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 근무태도평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMnrJobItm(PosRecord recordMnrLItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d021", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 근무태도평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMnrJobItm2(PosRecord recordMnrLItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordMnrLItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d022", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsMnrLrgItm(PosRecord recordMnrSItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        
	    	param.setValueParamter(i++, recordMnrSItm.getAttribute("FST_SND_GBN"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_LITEM_NM"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_RATE"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordMnrSItm.getAttribute("STND_SCR"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_SCR"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordMnrSItm.getAttribute("HI_CNT"));          // 2. 평가대항목명
	        param.setValueParamter(i++, SESSION_USER_ID);                                  // 4. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordMnrSItm.getAttribute("ESTM_YEAR"));       // 5. 평가년도
	        param.setWhereClauseParameter(i++, recordMnrSItm.getAttribute("ESTM_NUM"));        // 6. 평가회차
	        param.setWhereClauseParameter(i++, recordMnrSItm.getAttribute("CNTR_ITM_CD"));     // 7. 보직항목코드
	        param.setWhereClauseParameter(i++, recordMnrSItm.getAttribute("ESTM_LITEM_CD"));   // 8. 평가대항목코드

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u022", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 근무태도평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsMnrLrgItm(PosRecord recordMnrSItm)
	    {			
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_YEAR"));  // 1. 평가년도
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_NUM"));   // 2. 평가회차
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("CNTR_ITM_CD"));  // 3. 보직항목코드
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_LITEM_CD"));  // 4. 평가대항목코드
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("FST_SND_GBN"));    // 5. 1차/2차 구분
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_LITEM_NM"));  // 6. 평가대항목명
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_RATE"));      // 7. 평가비율
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("STND_SCR"));
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_SCR"));
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("HI_CNT"));
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 8. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i022", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 근무태도평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMnrLrgItm(PosRecord recordMnrSItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        param.setValueParamter(i++, recordMnrSItm.getAttribute("ESTM_LITEM_CD"));    // 4.평가항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d023", param);

	        return dmlcount;
	    }
	    protected int updateMnrBestScr(PosRecord recordMnrMItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    		    	
	    	param.setValueParamter(i++, recordMnrMItm.getAttribute("BEST_SCR"));          // 2. 평가대항목명
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordMnrMItm.getAttribute("ESTM_YEAR"));       // 5. 평가년도
	        param.setWhereClauseParameter(i++, recordMnrMItm.getAttribute("ESTM_NUM"));        // 6. 평가회차
	        param.setWhereClauseParameter(i++, recordMnrMItm.getAttribute("CNTR_ITM_CD"));     // 7. 보직항목코드
	        param.setWhereClauseParameter(i++, recordMnrMItm.getAttribute("FST_SND_GBN"));
	        int dmlcount = this.getDao("rbmdao").update("rev1020_u023", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsSvrJobItm(PosRecord recordSvrLItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("CNTR_ITM_IN"));            // 1. 보직IN 
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("CNTR_ITM_NM"));            // 2. 보직IN명
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordSvrLItm.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, recordSvrLItm.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setWhereClauseParameter(i++, recordSvrLItm.getAttribute("CNTR_ITM_CD"));      // 6. 보직항목코드

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u031", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 서비스평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsSvrJobItm(PosRecord recordSvrLItm)
	    {			
    		PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, recordSvrLItm.getAttribute("ESTM_YEAR"));        // 1. 평가년도
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("ESTM_NUM"));         // 2. 평가회차
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("CNTR_ITM_CD"));      // 3. 보직항목코드
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("CNTR_ITM_IN"));      // 4. 보직IN
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("CNTR_ITM_NM"));      // 5. 보직IN명

	        param.setValueParamter(i++, SESSION_USER_ID);                            // 6. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i031", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 근무태도평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteSvrJobItm(PosRecord recordSvrLItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d031", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 서비스평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteSvrJobItm2(PosRecord recordSvrLItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordSvrLItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d032", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsSvrLrgItm(PosRecord recordSvrSItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        
	    	param.setValueParamter(i++, recordSvrSItm.getAttribute("FST_SND_GBN"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_LITEM_NM"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_RATE"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordSvrSItm.getAttribute("STND_SCR"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_SCR"));          // 2. 평가대항목명
	    	param.setValueParamter(i++, recordSvrSItm.getAttribute("HI_CNT"));          // 2. 평가대항목명
	        param.setValueParamter(i++, SESSION_USER_ID);                                  // 4. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordSvrSItm.getAttribute("ESTM_YEAR"));       // 5. 평가년도
	        param.setWhereClauseParameter(i++, recordSvrSItm.getAttribute("ESTM_NUM"));        // 6. 평가회차
	        param.setWhereClauseParameter(i++, recordSvrSItm.getAttribute("CNTR_ITM_CD"));     // 7. 보직항목코드
	        param.setWhereClauseParameter(i++, recordSvrSItm.getAttribute("ESTM_LITEM_CD"));   // 8. 평가대항목코드

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u032", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 서비스평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsSvrLrgItm(PosRecord recordSvrSItm)
	    {			
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_YEAR"));  // 1. 평가년도
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_NUM"));   // 2. 평가회차
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("CNTR_ITM_CD"));  // 3. 보직항목코드
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_LITEM_CD"));  // 4. 평가대항목코드
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("FST_SND_GBN"));    // 5. 1차/2차 구분
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_LITEM_NM"));  // 6. 평가대항목명
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_RATE"));      // 7. 평가비율
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("STND_SCR"));
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_SCR"));
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("HI_CNT"));
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 8. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i032", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 서비스평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteSvrLrgItm(PosRecord recordSvrSItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        param.setValueParamter(i++, recordSvrSItm.getAttribute("ESTM_LITEM_CD"));    // 4.평가항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d033", param);

	        return dmlcount;
	    }
	    protected int updateSvrBestScr(PosRecord recordSvrMItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordSvrMItm.getAttribute("BEST_SCR"));          // 2. 평가대항목명
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordSvrMItm.getAttribute("ESTM_YEAR"));       // 5. 평가년도
	        param.setWhereClauseParameter(i++, recordSvrMItm.getAttribute("ESTM_NUM"));        // 6. 평가회차
	        param.setWhereClauseParameter(i++, recordSvrMItm.getAttribute("CNTR_ITM_CD"));     // 7. 보직항목코드
	        param.setWhereClauseParameter(i++, recordSvrMItm.getAttribute("FST_SND_GBN"));
	        int dmlcount = this.getDao("rbmdao").update("rev1020_u033", param);
	        return dmlcount;
	    }
	    
    	/**
	     * <p> 다면평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsMultJobItm(PosRecord recordMultLItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_ITM_IN"));            // 1. 보직IN 
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_ITM_NM"));            // 2. 보직IN명
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_JOB_NM"));
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordMultLItm.getAttribute("ESTM_YEAR"));     // 4. 평가년도
	        param.setWhereClauseParameter(i++, recordMultLItm.getAttribute("ESTM_NUM"));      // 5. 평가회차
	        param.setWhereClauseParameter(i++, recordMultLItm.getAttribute("CNTR_ITM_CD"));      // 6. 보직항목코드

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u041", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 다면평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsMultJobItm(PosRecord recordMultLItm)
	    {			
    		PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, recordMultLItm.getAttribute("ESTM_YEAR"));        // 1. 평가년도
	        param.setValueParamter(i++, recordMultLItm.getAttribute("ESTM_NUM"));         // 2. 평가회차
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_ITM_CD"));      // 3. 보직항목코드
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_ITM_IN"));      // 4. 보직IN
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_ITM_NM"));      // 5. 보직IN명
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_JOB_NM"));      // 5. 보직IN명

	        param.setValueParamter(i++, SESSION_USER_ID);                            // 6. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i041", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 다면평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateItemsMultLrgItm(PosRecord recordMultMItm)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_LITEM_NM"));          // 2. 평가대항목명
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_RATE"));              // 3. 평가대항목명
	        param.setValueParamter(i++, SESSION_USER_ID);                                  // 4. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordMultMItm.getAttribute("ESTM_YEAR"));       // 5. 평가년도
	        param.setWhereClauseParameter(i++, recordMultMItm.getAttribute("ESTM_NUM"));        // 6. 평가회차
	        param.setWhereClauseParameter(i++, recordMultMItm.getAttribute("CNTR_ITM_CD"));     // 7. 보직항목코드
	        param.setWhereClauseParameter(i++, recordMultMItm.getAttribute("ESTM_LITEM_CD"));   // 8. 평가대항목코드

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u042", param);
	        return dmlcount;
	    }
	    /**
	     * <p> 다면평가항목 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertItemsMultLrgItm(PosRecord recordMultMItm)
	    {			
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_YEAR"));  // 1. 평가년도
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_NUM"));   // 2. 평가회차
	        param.setValueParamter(i++, recordMultMItm.getAttribute("CNTR_ITM_CD"));  // 3. 보직항목코드
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_LITEM_CD"));  // 4. 평가대항목코드
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_LITEM_NM"));  // 6. 평가대항목명
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_RATE"));      // 7. 평가비율
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 8. 사용자 ID
	                
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i042", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> 다면평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMultJobItm(PosRecord recordMultLItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMultLItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordMultLItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordMultLItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d041", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 다면평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMultJobItm2(PosRecord recordMultMItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordMultMItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d042", param);

	        return dmlcount;
	    }
	    /**
	     * <p> 다면평가항목 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMultLrgItm(PosRecord recordMultMItm) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setValueParamter(i++, recordMultMItm.getAttribute("CNTR_ITM_CD"));    // 3.보직항목코드
	        param.setValueParamter(i++, recordMultMItm.getAttribute("ESTM_LITEM_CD"));    // 4.평가항목코드
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d043", param);

	        return dmlcount;
	    }
	    
	    protected int insertItemsMultGrd(PosRecord recordMultGrd) 
	    {
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, recordMultGrd.getAttribute("ESTM_YEAR"));       // 1.평가년도
	        param.setValueParamter(i++, recordMultGrd.getAttribute("ESTM_NUM"));        // 2.평가회차
	        param.setValueParamter(i++, recordMultGrd.getAttribute("S_RATE"));       // 3.S등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("A_RATE"));        // 4.A등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("B_RATE"));       // 5.B등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("C_RATE"));        // 6.C등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("D_RATE"));        // 6.D등급배분율
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 9.사용자 ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i044", param);
	        
	        return dmlcount;
	    }
	    
	    protected int updateItemsMultGrd(PosRecord recordMultGrd) 
	    {
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	        param.setValueParamter(i++, recordMultGrd.getAttribute("S_RATE"));       // 1.S등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("A_RATE"));        // 2.A등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("B_RATE"));       // 3.B등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("C_RATE"));        // 4.C등급배분율
	        param.setValueParamter(i++, recordMultGrd.getAttribute("D_RATE"));        // 5.D등급배분율
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 6. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordMultGrd.getAttribute("ESTM_YEAR"));     // 7. 평가년도
	        param.setWhereClauseParameter(i++, recordMultGrd.getAttribute("ESTM_NUM"));      // 8. 평가회차

	        int dmlcount = this.getDao("rbmdao").update("rev1020_u044", param);
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
