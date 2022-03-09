/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.system.d06000002.activity.SaveTotalEduSchd.java
 * 파일설명		: 종합교육일정
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000002.activity;

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

public class SaveTotalEduSchd extends SnisActivity 
{
	public SaveTotalEduSchd() { }
	
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
		
        int    nSize1 = 0;
        int    nSize2 = 0;
        
        String     sDsName1 = "";
        String     sDsName2 = ""; 
        
        sDsName1 = "dsOutEduOutl";
        if ( ctx.get(sDsName1) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName1);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsOutEduOutl------------------->"+record);
	        }
        }
        
        sDsName2 = "dsOutEduAssign";
        if ( ctx.get(sDsName2) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName2);
	        nSize2= ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsOutEduAssign------------------->"+record);
	        }
        }
        
        logger.logInfo("nSize1------------------->"+nSize1);
        logger.logInfo("nSize2------------------->"+nSize2);
    	
        if(nSize1 > 0){
        	saveStateEduOutl(ctx); 
        }
        
        if(nSize2 > 0){
        	saveStateEduAssign(ctx); 
        }        
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
     protected void saveStateEduOutl(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	int nSize        = 0;
        int nTempCnt     = 0;
                      
        // 교육개요 저장
        ds    = (PosDataset)ctx.get("dsOutEduOutl");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateEduOutl(record)) == 0 ) {
                	nTempCnt = insertEduOutl(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
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
     protected void saveStateEduAssign(PosContext ctx) 
     {
	     //   시간배정표 저장
	     int nSaveCount = 0;
	     int nDeleteCount = 0;
	     
	     PosDataset ds    = (PosDataset) ctx.get("dsOutEduAssign");
	     int nSize = ds.getRecordCount();
	     
	     for ( int i = 0; i < nSize; i++ ) 
	     {
	     	PosRecord record = ds.getRecord(i);
	     	logger.logInfo("recordType==================>"+record.getType());
	     	logger.logInfo(String.valueOf(com.posdata.glue.miplatform.vo.PosRecord.UPDATE));
	          switch (record.getType())
	          {
	          	case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	          		updateEduAssign(record);
	          		nSaveCount = nSaveCount + 1;
	          		break;
	          	case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	          		insertEduAssign(record);
	          		nSaveCount = nSaveCount + 1;
	          		break;
	          	case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	          		deleteEduAssign(record);
	          		nDeleteCount = nDeleteCount + 1;
	          		break;
	          	
	          }
	     }
	     
	     Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }  
     
     
     /**
      * <p> 교육개요 입력  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertEduOutl(PosRecord record) 
     {
    	 logger.logInfo("==========================  교육개요 입력   ============================");
              
         PosParameter param = new PosParameter();       					
         int i = 0;
                 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_END_DT")));
         param.setValueParamter(i++, record.getAttribute("EDU_MM"));
         param.setValueParamter(i++, (record.getAttribute("TOT_EDU_TIME")));
         param.setValueParamter(i++, (record.getAttribute("EDU_PERS_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_MAN_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_WOMAN_NO")));
         param.setValueParamter(i++, SESSION_USER_ID);
                 
         int dmlcount = this.getDao("candao").insert("tbdn_cmpt_edu_outl_d06000002_in001", param);
         
         return dmlcount;
     }
     
     
     /**
      * <p> 시간배정표 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertEduAssign(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
        
         logger.logInfo("===================  시간배정표 입력   =====================");
         
         logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("SECTN_CD"));
         logger.logInfo(record.getAttribute("CRS_CD"));
         logger.logInfo(record.getAttribute("EDU_TIME"));
         logger.logInfo(record.getAttribute("LECTR"));
         logger.logInfo(record.getAttribute("BLNG"));
         logger.logInfo(record.getAttribute("RMK"));
         logger.logInfo(SESSION_USER_ID);
                           
         logger.logInfo("===================  시간배정표 입력   ======================");
                      
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
         param.setValueParamter(i++, (record.getAttribute("EDU_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BLNG")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                  
         int dmlcount = this.getDao("candao").insert("tbdn_cmpt_edu_outl_d06000002_in002", param);
         
         return dmlcount;
     }
     
         
     
     /**
      * <p> 교육개요 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateEduOutl(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         logger.logInfo("=================  교육개요 갱신   ===================");
         
         logger.logInfo(record.getAttribute("EDU_STR_DT"));
         logger.logInfo(record.getAttribute("EDU_END_DT"));
         logger.logInfo(record.getAttribute("EDU_MM"));
         logger.logInfo(record.getAttribute("TOT_EDU_TIME"));
         logger.logInfo(record.getAttribute("EDU_PERS_NO"));
         logger.logInfo(SESSION_USER_ID);
         logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
                  
         logger.logInfo("===================  교육개요 갱신   ====================");
                  
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EDU_END_DT")));
         param.setValueParamter(i++, (record.getAttribute("EDU_MM")));
         param.setValueParamter(i++, (record.getAttribute("TOT_EDU_TIME")));
         param.setValueParamter(i++, (record.getAttribute("EDU_PERS_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_MAN_NO")));
         param.setValueParamter(i++, (record.getAttribute("EDU_WOMAN_NO")));
         param.setValueParamter(i++, SESSION_USER_ID);
                  
         i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
                        
         int dmlcount = this.getDao("candao").update("tbdn_cmpt_edu_outl_d06000002_un001", param);
               
         return dmlcount;
     }
     
     
     /**
      * <p> 시간배정표 갱신 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateEduAssign(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         logger.logInfo("==================== 시간배정표 갱신 ============================");
         
         logger.logInfo(record.getAttribute("EDU_TIME"));
         logger.logInfo(record.getAttribute("LECTR"));
         logger.logInfo(record.getAttribute("BLNG"));
         logger.logInfo(record.getAttribute("RMK"));
         logger.logInfo(SESSION_USER_ID);
         logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("SECTN_CD"));
         logger.logInfo(record.getAttribute("CRS_CD"));
                  
         logger.logInfo("==================== 시간배정표 갱신 ============================");
         param.setValueParamter(i++, (record.getAttribute("EDU_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BLNG")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
         i=0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CRS_CD")));
                 
         int dmlcount = this.getDao("candao").update("tbdn_cmpt_edu_outl_d06000002_un002", param);
                
         return dmlcount;
     }
         
     /**
      * <p> 시간배정표  삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteEduAssign(PosRecord record) 
     {
    	 logger.logInfo("====================== 시간배정표  삭제 ===========================");
         
    	 logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("SECTN_CD"));
         logger.logInfo(record.getAttribute("CRS_CD"));
    	 
    	 logger.logInfo("===================== 시간배정표  삭제 ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
                             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CRS_CD")));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_cmpt_edu_outl_d06000002_dn001", param);
                  
         return dmlcount;
     }
}
