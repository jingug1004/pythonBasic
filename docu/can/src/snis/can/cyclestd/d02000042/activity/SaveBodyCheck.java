/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.cyclestd.d02000008.activity.SaveOneTest.java
 * 파일설명		: 신체조성측정
 * 작성자		: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000042.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/


public class SaveBodyCheck extends SnisActivity 
{
	public SaveBodyCheck() { }
	
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
    	
    	PosDataset ds1;
		int    nSize1 = 0;
        String     sDsName = "";

        sDsName = "dsPerioExam1";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsPerioExam1------------------->"+record);
	        }
        } 
        	saveState(ctx); 
        return PosBizControlConstants.SUCCESS;
    }
   
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx	PosContext 종목구분 저장소
     * @return  none
     * @throws  none
     */
     protected void saveState(PosContext ctx) 
     {
		int nSaveCount   = 0; 
		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //종목구분 저장       
        ds1   = (PosDataset) ctx.get("dsPerioExam1");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
                 nTempCnt = insertPerio_Exam1(record);            	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }
		Util.setSaveCount  (ctx, nSaveCount     );
     }
     
     
     /**
      * <p> 신체조성측정 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertPerio_Exam1(PosRecord record) 
     {
    	 logger.logInfo("insertPerio_Exam1 start============================");
         PosParameter param = null;
         int i = 0;
         int dmlcount = 0;
         int recCount = 0;
 		
         String sHght       = Util.trim(String.valueOf(record.getAttribute("HGHT")));
         String sWght       = Util.trim(String.valueOf(record.getAttribute("WGHT")));
         String sBdyMss     = Util.trim(String.valueOf(record.getAttribute("BDYMSS_IDX")));
         String sFatRate    = Util.trim(String.valueOf(record.getAttribute("FAT_RATE")));
         String sMusRate    = Util.trim(String.valueOf(record.getAttribute("MUS_RATE")));
         String sBellyFat   = Util.trim(String.valueOf(record.getAttribute("BELLY_FAT")));
                  
                          
         if(!sHght.equals("")){
        	i = 0; 
        	recCount ++;
         	param = new PosParameter();
         	
         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "501");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sHght);	
    		param.setValueParamter(i++, record.getAttribute("HGHT_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
    		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "501");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sHght);	
    		param.setValueParamter(i++, record.getAttribute("HGHT_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
 			 	
 			dmlcount += this.getDao("candao").insert("tbdb_body_make_mesur_ib001", param);
         }
         
         
         if(!sWght.equals("")){
 			i = 0;  
 			recCount ++;
         	param = new PosParameter();
         	
         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "502");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sWght);	
    		param.setValueParamter(i++, record.getAttribute("WGHT_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
    		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "502");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sWght);	
    		param.setValueParamter(i++, record.getAttribute("WGHT_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
 		
 			dmlcount += this.getDao("candao").insert("tbdb_body_make_mesur_ib001", param);
         }
        
         
         if(!sBdyMss.equals("")){
 			i = 0; 
 			recCount ++;
         	param = new PosParameter();

         	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "503");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sBdyMss);	
    		param.setValueParamter(i++, record.getAttribute("BDYMSS_IDX_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
    		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "503");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sBdyMss);	
    		param.setValueParamter(i++, record.getAttribute("BDYMSS_IDX_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
    		
 			dmlcount += this.getDao("candao").insert("tbdb_body_make_mesur_ib001", param);
         }
        
         
         if(!sFatRate.equals("")){
        	i = 0; 
        	recCount ++;
          	param = new PosParameter();	
  			
          	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "504");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sFatRate);	
    		param.setValueParamter(i++, record.getAttribute("FAT_RATE_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
    		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "504");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sFatRate);	
    		param.setValueParamter(i++, record.getAttribute("FAT_RATE_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
    		
  			 dmlcount += this.getDao("candao").insert("tbdb_body_make_mesur_ib001", param);
          }
          
         
          if(!sMusRate.equals("")){
    	  	i = 0;   
    	  	recCount ++;
    	  	param = new PosParameter();	

        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "505");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sMusRate);	
    		param.setValueParamter(i++, record.getAttribute("MUS_RATE_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);
    		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
    		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
    		param.setValueParamter(i++, "505");
    		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
    		param.setValueParamter(i++, sMusRate);	
    		param.setValueParamter(i++, record.getAttribute("MUS_RATE_SCR"));
    		param.setValueParamter(i++, SESSION_USER_ID);  
        		
  			dmlcount += this.getDao("candao").insert("tbdb_body_make_mesur_ib001", param);
          }
          
         
          if(!sBellyFat.equals("")){
        	  
    	  	i = 0;    
    	  	recCount ++;
    	  
			param = new PosParameter();	
			
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "506");
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
			param.setValueParamter(i++, sBellyFat);	
			param.setValueParamter(i++, record.getAttribute("BELLY_FAT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "506");
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
			param.setValueParamter(i++, sBellyFat);	
			param.setValueParamter(i++, record.getAttribute("BELLY_FAT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);  
	    		
        	  dmlcount += this.getDao("candao").insert("tbdb_body_make_mesur_ib001", param);
          }
                               
 		  logger.logInfo("insertPerio_Exam1 end============================");
 		  return dmlcount;
     }
}