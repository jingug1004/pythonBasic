/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.system.d02000002.activity.UserManager.java
 * 파일설명		: 지도교사
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-12
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000013.activity;

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

public class SaveTeacher extends SnisActivity
{
	
	public SaveTeacher() { }
	
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
    	
    	 PosDataset ds;
         int        nSize        = 0;
         String     sDsName = "";
       	        
         sDsName = "dsLecMan";
         
         if ( ctx.get(sDsName) != null ) {
 	        ds    = (PosDataset)ctx.get(sDsName);
 	        nSize = ds.getRecordCount();
 	        for ( int i = 0; i < nSize; i++ ) 
 	        {
 	            PosRecord record = ds.getRecord(i);
 	            logger.logInfo(record);
 	        }
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

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt     = 0;
               
        // 교육개요 저장
        ds    = (PosDataset)ctx.get("dsLecMan");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateLecMan(record)) == 0 ) {
                	nTempCnt = insertLecMan(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
            }
            
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteLecMan(record);
            }
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> 지도교사 입력  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertLecMan(PosRecord record) 
     {
    	 logger.logInfo("==========================  지도교사 입력   ============================");
    	 
    	 logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("GUD_LECTR_ID"));
         logger.logInfo(record.getAttribute("NM_KOR"));
         logger.logInfo(record.getAttribute("CHA_FLD"));
         logger.logInfo(record.getAttribute("BLNG"));
         logger.logInfo(record.getAttribute("USE_YN"));
         logger.logInfo(record.getAttribute("RMK"));
         logger.logInfo(SESSION_USER_ID);
    	 
    	 logger.logInfo("==========================  지도교사 입력   ============================");
                 
         PosParameter param = new PosParameter();       					
         int i = 0;
                 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHA_FLD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BLNG")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("USE_YN")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                 
         int dmlcount = this.getDao("candao").insert("tbdb_gud_lectr_ib001", param);
         
         return dmlcount;
     }
     
     
     /**
      * <p> 지도교사 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateLecMan(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         logger.logInfo("=================  지도교사 갱신   ===================");
         
         logger.logInfo(record.getAttribute("GUD_LECTR_ID"));
         logger.logInfo(record.getAttribute("NM_KOR"));
         logger.logInfo(record.getAttribute("CHA_FLD"));
         logger.logInfo(record.getAttribute("BLNG"));
         logger.logInfo(record.getAttribute("USE_YN"));
         logger.logInfo(record.getAttribute("RMK"));
         logger.logInfo(SESSION_USER_ID);
         
         logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("GUD_LECTR_ID"));
                  
         logger.logInfo("===================  지도교사 갱신   ====================");
                  
         param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CHA_FLD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BLNG")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("USE_YN")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                  
         i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));
                        
         int dmlcount = this.getDao("candao").update("tbdb_gud_lectr_ub001", param);
               
         return dmlcount;
     }
     
     /**
      * <p> 지도교사  삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteLecMan(PosRecord record) 
     {
    	 logger.logInfo("====================== 지도교사  삭제 ===========================");
         
    	 logger.logInfo(record.getAttribute("RACER_PERIO_NO"));
         logger.logInfo(record.getAttribute("GUD_LECTR_ID"));
             	 
    	 logger.logInfo("===================== 지도교사  삭제 ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
                             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));
                       
         int dmlcount = this.getDao("candao").delete("tbdb_gud_lectr_db001", param);
         
         
         return dmlcount;
     }
}

