/*================================================================================
 * 시스템			: 1차 선발시험  필기   관리
 * 소스파일 이름	: snis.can.system.d02000007.activity.SaveFstSeltExam.java
 * 파일설명		: 1차 선발시험 관리
 * 작성자			: 
 * 버전			: 1.0.0
 * 생성일자		: 2008-05-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;


public class SaveFstSeltExam extends SnisActivity {
   
	public SaveFstSeltExam()
    {
    }
	
	 public String runActivity(PosContext ctx)
	    {
	    	
	        PosDataset ds;
	        int        nSize        = 0;
	        String     sDsName      = "";
	        
			if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
		    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
		            return PosBizControlConstants.SUCCESS;
		     }
	        sDsName = "dsFstSeltExam2";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo("dsFstSeltExam2============>"+record);
		        }
	        }
		        
	        saveState(ctx);
	        return PosBizControlConstants.SUCCESS;
	    }
	 
	 protected void saveState(PosContext ctx) 
	    {
	    	int nSaveCount   = 0;  
	    	String sDsName   = "";
	    	
	    	PosDataset ds;
	        int nSize        = 0;
	        int nTempCnt     = 0;
	        sDsName = "dsFstSeltExam2";
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);         
		                    
		                      
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) 
		            {
		            	
		  				nTempCnt = insertSaveFstSeltExam2(record); 
		  				nSaveCount = nSaveCount + nTempCnt;
		    	    }
		        } // end for
	        }     // end if
	        Util.setSaveCount  (ctx, nSaveCount     );
	    }
	    /**
	     * <p> 2차선발시험 필기 입력  </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		insert 레코드 개수
	     * @throws  none
	     */
	    protected int insertSaveFstSeltExam2(PosRecord record) 
	    {
	   	    logger.logInfo("==========================  1차 선발시험 필기   입력   ============================");
	                
	        PosParameter param = new PosParameter();  
	        int i = 0;
	        int dmlcount = 0;
	      /*  
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "101");
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TOT_SCR"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "101");
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("TOT_SCR"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			*/
	        logger.logInfo("RACER_PERIO_NO:"+ record.getAttribute("RACER_PERIO_NO"));
	        logger.logInfo("EXAM_CD:"+ record.getAttribute("EXAM_CD"));
	        logger.logInfo("ITEM_GBN_CD:"+ record.getAttribute("ITEM_GBN_CD"));
	        logger.logInfo("CAND_NO:"+ record.getAttribute("CAND_NO"));
	        logger.logInfo("TOT_SCR:"+ record.getAttribute("TOT_SCR"));
	        logger.logInfo("SESSION_USER_ID:"+ SESSION_USER_ID);
	        
			param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
			param.setValueParamter(i++, record.getAttribute("EXAM_CD"));
			param.setValueParamter(i++, record.getAttribute("ITEM_GBN_CD"));
			param.setValueParamter(i++, "101");
			param.setValueParamter(i++, record.getAttribute("CAND_NO"));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
	        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
			param.setValueParamter(i++, record.getAttribute("EXAM_CD"));
			param.setValueParamter(i++, record.getAttribute("ITEM_GBN_CD"));
			param.setValueParamter(i++, "101");
			param.setValueParamter(i++, record.getAttribute("CAND_NO"));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_fst_ib003", param);
	        return dmlcount;
	    } 

}
