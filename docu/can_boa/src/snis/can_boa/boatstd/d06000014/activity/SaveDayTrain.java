/*================================================================================
 * 시스템			: 세부교육일정
 * 소스파일 이름	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * 파일설명		: 코드 관리
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000014.activity;

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

public class SaveDayTrain extends SnisActivity 
{
	public SaveDayTrain() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	PosDataset ds1;
		PosDataset ds2;
		PosDataset ds3;
		PosDataset ds4;		
        int    nSize1 = 0;
        int    nSize2 = 0;
        int    nSize3 = 0;
        int    nSize4 = 0;        
        String     sDsName = "";
   
        logger.logInfo("dsDiaryMst start------------------->");
        sDsName = "dsDiaryMst";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        logger.logInfo("nSize1------------------->"+nSize1);
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsDiaryMst------------------->"+record);
	        }
        }
        
        logger.logInfo("dsDiaryAcdnt start------------------->");        
        sDsName = "dsDiaryAcdnt";
        if ( ctx.get(sDsName) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName);
	        nSize2= ds2.getRecordCount();
	        logger.logInfo("nSize2------------------->"+nSize2);	
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsDiaryAcdnt------------------->"+record);
	        }
        }   
                
        logger.logInfo("dsDiaryDetl start------------------->");   
        sDsName = "dsDiaryDetl";
        if ( ctx.get(sDsName) != null ) {
	        ds3   = (PosDataset)ctx.get(sDsName);
	        nSize3= ds3.getRecordCount();
	        logger.logInfo("nSize3------------------->"+nSize3);	
	        for ( int i = 0; i < nSize3; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	            logger.logInfo("dsDiaryDetl------------------->"+record);
	        }
        }   
        
        logger.logInfo("dsDiaryAbs start------------------->");  
        sDsName = "dsDiaryAbs";
        if ( ctx.get(sDsName) != null ) {
	        ds4    = (PosDataset)ctx.get(sDsName);
	        nSize4 = ds4.getRecordCount();
	        logger.logInfo("nSize4------------------>"+nSize4);		        
	        for ( int i = 0; i < nSize4; i++ ) 
	        {
	            PosRecord record = ds4.getRecord(i);
	            logger.logInfo("dsDiaryAbs------------------->"+record);
	        }
        }   
        
         
       logger.logInfo("nSize1------------------->"+nSize1);
       logger.logInfo("nSize2------------------->"+nSize2);
       logger.logInfo("nSize3------------------->"+nSize3);
       logger.logInfo("nSize4------------------->"+nSize4);
        
        if(nSize1 > 0){
        	saveStateTrng_Diary_Mst(ctx); 
        } 
        if(nSize2 > 0){
        	saveStateTrng_Diary_Acdnt(ctx); 
        } 
        if(nSize3 > 0){
        	saveStateTrng_Diary_Detl(ctx);         	
        } 
        if(nSize4 > 0){
        	saveStateTrng_Diary_Abs(ctx); 
        }
        
        logger.logInfo("saveflag start------------------->");
        String saveflag = Util.getCtxStr(ctx, "saveflag");
        if ( "DiaryDetlInit".equals(saveflag) ) {
        	saveStateTrng_Diary_Init(ctx); 
        } 
        
        return PosBizControlConstants.SUCCESS; 
    }
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
     protected void saveStateTrng_Diary_Mst(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 
     	PosDataset ds4;
     	
        int nSize        = 0;
        int nTempCnt     = 0;
        
        // 시간배정표 저장       
        ds4   = (PosDataset) ctx.get("dsDiaryMst");
        nSize = ds4.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds4.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {          	 
            	 if ( (nTempCnt = updateTrng_Diary_Mst(record)) == 0 ) {
                 	nTempCnt = insertTrng_Diary_Mst(record);
                 }            	
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteTrng_Diary_Mst(record);
             }
         }
                  
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
      * @param   ctx		PosContext	저장소
      * @return  none
      * @throws  none
      */
      protected void saveStateTrng_Diary_Acdnt(PosContext ctx) 
      {
      	int nSaveCount   = 0; 
      	int nDeleteCount = 0; 
      	PosDataset ds4;
      	
         int nSize        = 0;
         int nTempCnt     = 0;
         
         // 시간배정표 저장       
         ds4   = (PosDataset) ctx.get("dsDiaryAcdnt");
         nSize = ds4.getRecordCount();

         for ( int i = 0; i < nSize; i++ ) 
         {
              PosRecord record = ds4.getRecord(i);
              
              if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
              {          	 
            	  nTempCnt = updateTrng_Diary_Acdnt(record);
              }
          }
                   
          Util.setSaveCount  (ctx, nSaveCount     );
          Util.setDeleteCount(ctx, nDeleteCount   );
      }     
     
     
     /**
      * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
      * @param   ctx		PosContext	저장소
      * @return  none
      * @throws  none
      */
      protected void saveStateTrng_Diary_Detl(PosContext ctx) 
      {
      	int nSaveCount   = 0; 
      	int nDeleteCount = 0; 
      	PosDataset ds4;
      	
         int nSize        = 0;
         int nTempCnt     = 0;
         
         // 시간배정표 저장       
         ds4   = (PosDataset) ctx.get("dsDiaryDetl");
         nSize = ds4.getRecordCount();

         for ( int i = 0; i < nSize; i++ ) 
         {
              PosRecord record = ds4.getRecord(i);
              
              if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
              {          	 
             	 if ( (nTempCnt = updateTrng_Diary_Detl(record)) == 0 ) {
                  	nTempCnt = insertTrng_Diary_Detl(record);
                  }            	
             	 nSaveCount = nSaveCount + nTempCnt;
              }
              
              if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
              {
                  // delete
              	nDeleteCount = nDeleteCount + deleteTrng_Diary_Detl(record);
              }
          }
                   
          Util.setSaveCount  (ctx, nSaveCount     );
          Util.setDeleteCount(ctx, nDeleteCount   );
      }     
     
      
      /**
       * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
       * @param   ctx		PosContext	저장소
       * @return  none
       * @throws  none
       */
       protected void saveStateTrng_Diary_Abs(PosContext ctx) 
       {
       	int nSaveCount   = 0; 
       	int nDeleteCount = 0; 
       	PosDataset ds4;
        PosRecord record = null;
       	
          int nSize        = 0;
          int nTempCnt     = 0;
          
          // 시간배정표 저장       
          ds4   = (PosDataset) ctx.get("dsDiaryAbs");
          nSize = ds4.getRecordCount();

          for ( int i = 0; i < nSize; i++ ) 
          {
               record = ds4.getRecord(i);
               
               if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
               {          	 
              	 if ( (nTempCnt = updateTrng_Diary_Abs(record)) == 0 ) {
                   	nTempCnt = insertTrng_Diary_Abs(record);
                   }            	
              	 nSaveCount = nSaveCount + nTempCnt;
               }
               
               if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
               {
                   // delete
               	nDeleteCount = nDeleteCount + deleteTrng_Diary_Abs(record);
               }
           }
          
           if(record != null) updateTrng_Diary_Mst_cnt(record);         
           Util.setSaveCount  (ctx, nSaveCount     );
           Util.setDeleteCount(ctx, nDeleteCount   );
      }       
            
       
       /**
        * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
        * @param   ctx		PosContext	저장소
        * @return  none
        * @throws  none
        */
        protected void saveStateTrng_Diary_Init(PosContext ctx) 
        {
        	
        	String racerPerioNo = "";
        	String dt = "";
        	int nSaveCount   = 0; 
        	int nDeleteCount = 0; 
        	
        	racerPerioNo = Util.getCtxStr(ctx, "racerPerioNo");
        	dt = Util.getCtxStr(ctx, "dt");
        	
        	nDeleteCount = deleteTrng_Diary_Init(racerPerioNo, dt);
        	nSaveCount = insertTrng_Diary_Init(racerPerioNo, dt);
        	
        	
        	
            Util.setSaveCount  (ctx, nSaveCount     );
            Util.setDeleteCount(ctx, nDeleteCount   );
       }              
     /**
      * <p> 교육훈련일지마스터 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertTrng_Diary_Mst(PosRecord record) 
     {
     	logger.logInfo("insertTrng_Diary_Mst start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WEATHER1")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WEATHER2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_DIRC1")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_DIRC2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_SPD1")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_SPD2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TEMPT1")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TEMPT2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HUMID1")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HUMID2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WAT_TEMP1")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WAT_TEMP2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WAT_LEV")));
         param.setValueParamter(i++, record.getAttribute("TOT_PERS_NO"));
         param.setValueParamter(i++, record.getAttribute("CURR_PERS_NO"));
         param.setValueParamter(i++, record.getAttribute("ABS_PERS_NO"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("INSTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_PLC")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("IND_DESC")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_DESC")));
         param.setValueParamter(i++, SESSION_USER_ID);
         
         int dmlcount = this.getDao("candao").insert("tbdn_trng_diary_mst_ib001", param);
         
         return dmlcount;
     }
     
     /**
      * <p> 교육훈련일지마스터 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateTrng_Diary_Mst(PosRecord record) 
     {
        PosParameter param = new PosParameter();       					
        int i = 0;
         
        param.setValueParamter(i++, Util.trim(record.getAttribute("WEATHER1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WEATHER2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_DIRC1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_DIRC2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_SPD1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_SPD2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TEMPT1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TEMPT2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HUMID1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HUMID2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WAT_TEMP1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WAT_TEMP2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WAT_LEV")));
        param.setValueParamter(i++, record.getAttribute("TOT_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("CURR_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("ABS_PERS_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INSTR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_PLC")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("IND_DESC")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_DESC")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));

		int dmlcount = this.getDao("candao").update("tbdn_trng_diary_mst_ub001", param);

		return dmlcount;
     }
     
 
     /**
      * <p> 교육훈련일지마스터 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteTrng_Diary_Mst(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;
             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("DT"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_trng_diary_mst_db001", param);
         
         return dmlcount;
     }
     
     /**
      * <p> 교육훈련일지사고 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertTrng_Diary_Acdnt(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;			
			
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("GBN")));         
         param.setValueParamter(i++, record.getAttribute("ROUND"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPAIT_DESC")));
         param.setValueParamter(i++, SESSION_USER_ID);
         
         int dmlcount = this.getDao("candao").insert("tbdn_trng_diary_acdnt_ib001", param);
         
         return dmlcount;
     }
     

     /**
      * <p> 교육훈련일지사고 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateTrng_Diary_Acdnt(PosRecord record) 
     {
        PosParameter param = new PosParameter();       					
        int i = 0;
         			
        param.setValueParamter(i++, Util.trim(record.getAttribute("REPAIT_DESC")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DAY_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ROUND")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TEAM_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_REG_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("VIOL_SEQ")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));

		int dmlcount = this.getDao("candao").update("tbdn_cand_race_viol_ub004", param);

		return dmlcount;
     }
     
 
     /**
      * <p> 교육훈련일지사고 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteTrng_Diary_Acdnt(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;
             
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("GBN")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_trng_diary_acdnt_db001", param);
         
         return dmlcount;
     }
     
     /**
      * <p> 교육훈련일지 마스터 교육인원 수정</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int updateTrng_Diary_Mst_cnt(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;

        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
  		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));

		int dmlcount = this.getDao("candao").delete("tbdn_trng_diary_mst_ub002", param);
         
        return dmlcount;
     }
     
     
     /**
      * <p> 교육훈련일지상세 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertTrng_Diary_Detl(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;
 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, record.getAttribute("SEQ"));         
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
         param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_DETL")));
         param.setValueParamter(i++, SESSION_USER_ID);
         
         int dmlcount = this.getDao("candao").insert("tbdn_trng_diary_detl_ib001", param);
         
         return dmlcount;
     }
     

     /**
      * <p> 교육훈련일지상세 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateTrng_Diary_Detl(PosRecord record) 
     {
        PosParameter param = new PosParameter();       					
        int i = 0;
         
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
        param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_DETL")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("AMPM_CD")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

		int dmlcount = this.getDao("candao").update("tbdn_trng_diary_detl_ub001", param);

		return dmlcount;
     }
     
 
     /**
      * <p> 교육훈련일지상세 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteTrng_Diary_Detl(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;
             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_trng_diary_detl_db001", param);
         this.getDao("candao").update("tbdn_trng_diary_abs_db002", param);	//해당 교육에 작성된 결원 내역도 같이 삭제
         return dmlcount;
     }
     
     
     /**
      * <p> 교육훈련일지결원 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertTrng_Diary_Abs(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;
         int dmlcount = 0;
          
         String sChk = String.valueOf(Util.trim(record.getAttribute("CHK")));

         if(sChk.equals("1")){
	         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	         param.setValueParamter(i++, record.getAttribute("SEQ"));         
	         param.setValueParamter(i++, Util.trim(record.getAttribute("DT"))); 
	         param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));         
	         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
	         param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
	         param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
	         param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
	         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));         
	         param.setValueParamter(i++, Util.trim(record.getAttribute("AMPM_CD")));
	         param.setValueParamter(i++, SESSION_USER_ID);  
	         dmlcount = this.getDao("candao").insert("tbdn_trng_diary_abs_ib001", param);
         } 
         return dmlcount;
     }
     
     /**
      * <p> 교육훈련일지결원 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateTrng_Diary_Abs(PosRecord record) 
     {

        PosParameter param = new PosParameter();       					
        int i = 0;
        int dmlcount = 0;
        
        String sChk = String.valueOf(Util.trim(record.getAttribute("CHK")));

        if(sChk.equals("1")){        
	        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
	        param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));   
	        param.setValueParamter(i++, Util.trim(record.getAttribute("AMPM_CD")));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));		
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
							
			dmlcount = this.getDao("candao").update("tbdn_trng_diary_abs_ub001", param);
        }		
		return dmlcount;
     }
     
 
     /**
      * <p> 교육훈련일지결원 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
    protected int deleteTrng_Diary_Abs(PosRecord record) 
    {
		PosParameter param = new PosParameter();       					
		int i = 0;
		int dmlcount = 0;     
        
		String sChk = String.valueOf(Util.trim(record.getAttribute("CHK")));
        if(sChk.equals("0")){		
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));		
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));    
			dmlcount = this.getDao("candao").delete("tbdn_trng_diary_abs_db001", param);
        }	 

		return dmlcount;
	}         
    
	/**
     * <p> 교육훈련일지상세 초기화(주간교육계획 데이터 입력)  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertTrng_Diary_Init(String racerPerioNo, String dt) 
    {
       PosParameter param = new PosParameter();       					
       int i = 0;
       int dmlcount = 0;
       
       param.setValueParamter(i++, SESSION_USER_ID);
       i = 0; 
       param.setWhereClauseParameter(i++, racerPerioNo);
       param.setWhereClauseParameter(i++, dt);		
       
       dmlcount = this.getDao("candao").update("tbdn_trng_diary_detl_ib002", param);
       
       return dmlcount;
    }
    

    /**
     * <p> 교육훈련일지 삭제</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
   protected int deleteTrng_Diary_Init(String racerPerioNo, String dt)
   {
		PosParameter param = new PosParameter();       					
		int i = 0;
		int dmlcount = 0;     
       
		param.setWhereClauseParameter(i++, racerPerioNo);
		param.setWhereClauseParameter(i++, dt);		
		dmlcount = this.getDao("candao").delete("tbdn_trng_diary_detl_db002", param);
	 

		return dmlcount;
	}         
}
