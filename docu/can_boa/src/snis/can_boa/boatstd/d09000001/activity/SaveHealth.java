/*================================================================================
 * 시스템			: 후보생관리
 * 소스파일 이름	: snis.can_boa.boatstd.d09000001.activity.SaveHealth.java
 * 파일설명		: 건강기록
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d09000001.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/

public class SaveHealth extends SnisActivity 
{
	public SaveHealth() { }
	
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
        PosDataset ds2;
        PosDataset ds3;
        PosDataset ds4;
        
        int nSize1 = 0;
        int nSize2 = 0;
        int nSize3 = 0;
        int nSize4 = 0;
        String sDsName = "";

        //개인상태
        sDsName = "dsHealth";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsHealth------------------->"+record);
	        }
        }
        
        //신체부위(부상내역)1
        sDsName = "dsWound1";
        if ( ctx.get(sDsName) != null ) {
	        ds2 = (PosDataset)ctx.get(sDsName);
	        nSize2 = ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsWound1------------------->"+record);
	        }
        }   
        
        //신체부위(부상내역)2
        sDsName = "dsWound2";
        if ( ctx.get(sDsName) != null ) {
	        ds3 = (PosDataset)ctx.get(sDsName);
	        nSize3 = ds3.getRecordCount();
	        for ( int i = 0; i < nSize3; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	            logger.logInfo("dsWound2------------------->"+record);
	        }
        }  
        
        //외진기록(외래진료)
        sDsName = "dsExtExam";
        if ( ctx.get(sDsName) != null ) {
	        ds4 = (PosDataset)ctx.get(sDsName);
	        nSize4 = ds4.getRecordCount();
	        for ( int i = 0; i < nSize4; i++ ) 
	        {
	            PosRecord record = ds4.getRecord(i);
	            logger.logInfo("dsExtExam------------------->"+record);
	        }
        } 

		logger.logInfo("nSize1------------------->"+nSize1);
		logger.logInfo("nSize2------------------->"+nSize2);
		logger.logInfo("nSize3------------------->"+nSize3);
		logger.logInfo("nSize4------------------->"+nSize4);

		if(nSize1 > 0){
			saveStateHealth(ctx); 
		}else if(nSize2 > 0){
			saveStateWound1(ctx); 
		}else if(nSize3 > 0){
			saveStateWound2(ctx);         	
		}else if(nSize4 > 0){
			saveStateExtExam(ctx); 
		}
        
        return PosBizControlConstants.SUCCESS;
    }
  
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소 : 개인상태
     * @return  none
     * @throws  none
     */
     protected void saveStateHealth(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsHealth");
        nSize = ds.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
  
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updateHealth(record)) == 0 ) {
                   	nTempCnt = insertHealth(record);
                 }    	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteHealth(record);
             }
         }
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     

     /**
      * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
      * @param   ctx		PosContext	저장소 : 신체부위(부상내역)1
      * @return  none
      * @throws  none
      */
      protected void saveStateWound1(PosContext ctx) 
      {
      	int nSaveCount   = 0; 
      	int nDeleteCount = 0; 

      	PosDataset ds;
      	
         int nSize    	= 0;
         int nTempCnt    = 0;
             
         ds   = (PosDataset) ctx.get("dsWound1");
         nSize = ds.getRecordCount();
         logger.logInfo("nSize------------------->"+nSize);
   
         for ( int i = 0; i < nSize; i++ ) 
         {
              PosRecord record = ds.getRecord(i);
              
              if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
              {
             	 if ( (nTempCnt = updateWound1(record)) == 0 ) {
                    	nTempCnt = insertWound1(record);
                  }    	 
             	 nSaveCount = nSaveCount + nTempCnt;
              }
              
              if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
              {
                  // delete
              	nDeleteCount = nDeleteCount + deleteWound(record);
              }
          }
          Util.setSaveCount  (ctx, nSaveCount     );
          Util.setDeleteCount(ctx, nDeleteCount   );
      }
      
      /**
       * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
       * @param   ctx		PosContext	저장소 : 신체부위(부상내역)2
       * @return  none
       * @throws  none
       */
       protected void saveStateWound2(PosContext ctx) 
       {
       	int nSaveCount   = 0; 
       	int nDeleteCount = 0; 

       	PosDataset ds;
       	
          int nSize    	= 0;
          int nTempCnt    = 0;
              
          ds   = (PosDataset) ctx.get("dsWound2");
          nSize = ds.getRecordCount();
          logger.logInfo("nSize------------------->"+nSize);
    
          for ( int i = 0; i < nSize; i++ ) 
          {
               PosRecord record = ds.getRecord(i);
               
               if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
               {
              	 if ( (nTempCnt = updateWound2(record)) == 0 ) {
                     	nTempCnt = insertWound2(record);
                   }    	 
              	 nSaveCount = nSaveCount + nTempCnt;
               }
               
               if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
               {
                   // delete
               	nDeleteCount = nDeleteCount + deleteWound(record);
               }
           }
           Util.setSaveCount  (ctx, nSaveCount     );
           Util.setDeleteCount(ctx, nDeleteCount   );
       }
       
       /**
        * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
        * @param   ctx		PosContext	저장소 : 외진기록(외래진료)
        * @return  none
        * @throws  none
        */
        protected void saveStateExtExam(PosContext ctx) 
        {
        	int nSaveCount   = 0; 
        	int nDeleteCount = 0; 

        	PosDataset ds;
        	
           int nSize    	= 0;
           int nTempCnt    = 0;
               
           ds   = (PosDataset) ctx.get("dsExtExam");
           nSize = ds.getRecordCount();
           logger.logInfo("nSize------------------->"+nSize);
     
           for ( int i = 0; i < nSize; i++ ) 
           {
                PosRecord record = ds.getRecord(i);
                
                if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
                {
               	 if ( (nTempCnt = updateExtExam(record)) == 0 ) {
                      	nTempCnt = insertExtExam(record);
                    }    	 
               	 nSaveCount = nSaveCount + nTempCnt;
                }
                
                if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
                {
                    // delete
                	nDeleteCount = nDeleteCount + deleteExtExam(record);
                }
            }
            Util.setSaveCount  (ctx, nSaveCount     );
            Util.setDeleteCount(ctx, nDeleteCount   );
        }       
     
        /**
         * <p> 개인상태 입력 </p>
         * @param   record	PosRecord 데이타셋에 대한 하나의 레코드
         * @return  dmlcount int insert 레코드 개수
         * @throws  none
         */
        protected int insertHealth(PosRecord record) 
        {
        	logger.logInfo("insertHealth start ============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
            
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("IJRY_DETL")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("CURE_GBN")));            
            param.setValueParamter(i++, Util.trim(record.getAttribute("CUR_CNTNT")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_PATH")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_REAL_NM")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_DISP_NM")));            
            param.setValueParamter(i++, SESSION_USER_ID);
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));            

            int dmlcount = this.getDao("candao").insert("tbdq_health_ib001", param);
            
            logger.logInfo("insertHealth end ============================");
            return dmlcount;
        }
        
        /**
         * <p> 개인상태 갱신  </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		update 레코드 개수
         * @throws  none
         */
        protected int updateHealth(PosRecord record) 
        {
        	logger.logInfo("updateHealth start============================");
        	PosParameter param = new PosParameter();       					
        	int i = 0;
     
            param.setValueParamter(i++, Util.trim(record.getAttribute("IJRY_DETL")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));            
            param.setValueParamter(i++, Util.trim(record.getAttribute("CURE_GBN")));            
            param.setValueParamter(i++, Util.trim(record.getAttribute("CUR_CNTNT")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_PATH")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_REAL_NM")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_DISP_NM")));            
	   		param.setValueParamter(i++, SESSION_USER_ID);
	
	   		i = 0;    
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
            param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
	
	   		int dmlcount = this.getDao("candao").update("tbdq_health_ub001", param);
	
	   		logger.logInfo("updateHealth end ============================");
	   		return dmlcount;
        }
        
    
        /**
         * <p> 개인상태 삭제</p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		delete 레코드 개수
         * @throws  none
         */
        protected int deleteHealth(PosRecord record) 
        {
       	 	logger.logInfo("deleteHealth start============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
                
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
            param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                    
            int dmlcount = this.getDao("candao").delete("tbdq_health_db001", param);
            
            logger.logInfo("deleteHealth end============================");
            return dmlcount;
        } 
        
        /**
         * <p> 신체부위(부상내역) 입력1 </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		insert 레코드 개수
         * @throws  none
         */
        protected int insertWound1(PosRecord record) 
        {
        	logger.logInfo("insertWound1 start ============================");
            PosParameter param = null;       					
            int i = 0;
            int dmlcount = 0;

        	String sDt 			= Util.trim(record.getAttribute("DT"));
        	if(sDt.equals("")){
        		sDt = Util.getCurrDate();
        	}
        	  
        	if(record.getAttribute("BODY_YN1".trim())!= null){
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "1");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN1"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}
            
        	if(record.getAttribute("BODY_YN2".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "2");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN2"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN3".trim()) != null){        	
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "3");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN3"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}
        	
        	if(record.getAttribute("BODY_YN4".trim()) != null){	
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "4");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN4"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN5".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "5");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN5"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN6".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "6");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN6"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}
        	
        	if(record.getAttribute("BODY_YN7".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "7");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN7"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN8".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "8");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN8"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN9".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "9");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN9"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN10".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "10");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN10"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN11".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "11");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN11"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN12".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "12");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN12"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN13".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "13");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN13"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN14".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "14");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN14"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN15".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "15");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN15"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}
        	
        	if(record.getAttribute("BODY_YN16".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "16");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN16"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN17".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "17");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN17"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN18".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "18");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN18"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN19".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "19");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN19"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN20".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "20");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN20"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN21".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "21");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN21"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN22".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "22");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN22"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN23".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "23");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN23"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN24".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "24");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN24"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN25".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "25");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN25"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN26".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "26");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN26"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN27".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "27");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN27"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN28".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "28");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN28"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}

        	if(record.getAttribute("BODY_YN29".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "29");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN29"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}
	
        	if(record.getAttribute("BODY_YN30".trim()) != null){
	            i = 0;
	            param = new PosParameter(); 
	            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setValueParamter(i++, sDt);
	            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setValueParamter(i++, "30");
	            param.setValueParamter(i++, record.getAttribute("BODY_YN30"));
	            param.setValueParamter(i++, SESSION_USER_ID);
	            dmlcount += this.getDao("candao").insert("tbdq_wound_ib001", param);
        	}
           
            logger.logInfo("insertWound1 end ============================");
            return dmlcount;
        }
        
        /**
         * <p> 신체부위(부상내역) 갱신1  </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		update 레코드 개수
         * @throws  none
         */
        protected int updateWound1(PosRecord record) 
        {
        	logger.logInfo("updateWound1 start============================");
        	PosParameter param = null;       					
        	int i = 0;
        	int dmlcount = 0;
        	
        	if (record.getAttribute("BODY_YN1".trim()) != null) {
        		param = new PosParameter();
        		
        	    param.setValueParamter(i++, record.getAttribute("BODY_YN1"));
	            param.setValueParamter(i++, SESSION_USER_ID);
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		   	    param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "1");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
          	
          	if (record.getAttribute("BODY_YN2".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN2"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "2");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}

        	if (record.getAttribute("BODY_YN3".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN3"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "3");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}

        	if (record.getAttribute("BODY_YN4".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN4"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "4");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN5".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN5"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "5");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN6".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN6"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "6");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN7".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN7"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;	   		
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "7");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN8".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN8"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "8");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN9".trim()) != null) {   	
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN9"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "9");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN10".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN10"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "10");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN11".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN11"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "11");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN12".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN12"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "12");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN13".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN13"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "13");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN14".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN14"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "14");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN15".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN15"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "15");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN16".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN16"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "16");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN17".trim()) != null) {   
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN17"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "17");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN18".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN18"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "18");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN19".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN19"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "19");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN20".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN20"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "20");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN21".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN21"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "21");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN22".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN22"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "22");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN23".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN23"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "23");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN24".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN24"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "24");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN25".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN25"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "25");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN26".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN26"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "26");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN27".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN27"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "27");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN28".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN28"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "28");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
        	
        	if (record.getAttribute("BODY_YN29".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN29"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "29");
	            dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}       
			
        	if (record.getAttribute("BODY_YN30".trim()) != null) {
				i = 0;
				param = new PosParameter();
	            param.setValueParamter(i++, record.getAttribute("BODY_YN30"));
		   		param.setValueParamter(i++, SESSION_USER_ID);
		
		   		i = 0;    
		   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	            param.setWhereClauseParameter(i++, "30");
		   		dmlcount += this.getDao("candao").update("tbdq_wound_ub001", param);
        	}
	   		logger.logInfo("updateWound1 end ============================");
	   		return dmlcount;
        }
        
        /**
         * <p> 신체부위(부상내역) 입력2 </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		insert 레코드 개수
         * @throws  none
         */
        protected int insertWound2(PosRecord record) 
        {
        	logger.logInfo("insertWound2 start ============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
        	String sDt = Util.NVL(Util.trim(record.getAttribute("DT")));
        	if(sDt.equals("")){
        		sDt = Util.getCurrDate();
        	}
        	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setValueParamter(i++, sDt);
            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
            param.setValueParamter(i++, record.getAttribute("BODY_PART"));
            param.setValueParamter(i++, Util.trim(record.getAttribute("IJRY_DETL")));
            param.setValueParamter(i++, SESSION_USER_ID);

            int dmlcount = this.getDao("candao").insert("tbdq_wound_ib002", param);
            
            logger.logInfo("insertWound2 end ============================");
            return dmlcount;
        }
        
        /**
         * <p> 신체부위(부상내역) 갱신1  </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		update 레코드 개수
         * @throws  none
         */
        protected int updateWound2(PosRecord record) 
        {
        	logger.logInfo("updateWound2 start============================");
        	PosParameter param = new PosParameter();       					
        	int i = 0;
     
            param.setValueParamter(i++, Util.trim(record.getAttribute("IJRY_DETL")));
	   		param.setValueParamter(i++, SESSION_USER_ID);
	
	   		i = 0;    
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
            param.setWhereClauseParameter(i++, record.getAttribute("BODY_PART"));
	
	   		int dmlcount = this.getDao("candao").update("tbdq_wound_ub002", param);
	
	   		logger.logInfo("updateWound2 end ============================");
	   		return dmlcount;
        }        
        
        /**
         * <p> 신체부위(부상내역) 삭제</p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		delete 레코드 개수
         * @throws  none
         */
        protected int deleteWound(PosRecord record) 
        {
       	 	logger.logInfo("deleteWound start============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
                
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
            param.setWhereClauseParameter(i++, record.getAttribute("BODY_PART"));
                    
            int dmlcount = this.getDao("candao").delete("tbdq_wound_db001", param);
            
            logger.logInfo("deleteWound end============================");
            return dmlcount;
        } 
               
        /**
         * <p> 외진기록(외래진료) 입력 </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		insert 레코드 개수
         * @throws  none
         */
        protected int insertExtExam(PosRecord record) 
        {
        	logger.logInfo("insertExtExam start ============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
            
//            String sDiagFileDispNm = String.valueOf(Util.trim(record.getAttribute("DIAG_FILE_DISP_NM")));
//           String sDiagFileRealNm = "";
//            sDiagFileRealNm = sDiagFileDispNm +"_"+Util.getCurrDate()+Util.getCurrTime();
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_PATH")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_REAL_NM")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_DISP_NM")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_IDEA")));
            param.setValueParamter(i++, SESSION_USER_ID);
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));            

            int dmlcount = this.getDao("candao").insert("tbdq_ext_exam_ib001", param);
            
            logger.logInfo("insertExtExam end ============================");
            return dmlcount;
        }     
        
        /**
         * <p> 외진기록(외래진료) 갱신  </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		update 레코드 개수
         * @throws  none
         */
        protected int updateExtExam(PosRecord record) 
        {
        	logger.logInfo("updateExtExam start============================");
        	PosParameter param = new PosParameter();       					
        	int i = 0;
     
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_PATH")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_REAL_NM")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("DIAG_FILE_DISP_NM")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_IDEA")));
	   		param.setValueParamter(i++, SESSION_USER_ID);
	
	   		i = 0;    
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
	
	   		int dmlcount = this.getDao("candao").update("tbdq_ext_exam_ub001", param);
	
	   		logger.logInfo("updateExtExam end ============================");
	   		return dmlcount;
        }
        
        /**
         * <p> 외진기록(외래진료) 삭제</p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		delete 레코드 개수
         * @throws  none
         */
        protected int deleteExtExam(PosRecord record) 
        {
       	 	logger.logInfo("deleteExtExam start============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
                
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
	        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                    
            int dmlcount = this.getDao("candao").delete("tbdq_ext_exam_db001", param);
            
            logger.logInfo("deleteExtExam end============================");
            return dmlcount;
        }          
}
