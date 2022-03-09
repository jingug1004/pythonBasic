/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.cyclestd.d02000024.activity.SaveGrdAssign.java
 * 파일설명		: 경주등급별배정
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2007-03-10
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/

package snis.can.cyclestd.d02000021.activity;

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
* @auther 전홍조
* @version 1.0
*/



public class SaveGrdAssign extends SnisActivity{

	public SaveGrdAssign(){ }
	
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
       	        
         sDsName = "dsAssignListValue";
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
        ds    = (PosDataset)ctx.get("dsAssignListValue");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) 
            {
            	//nDeleteCount = nDeleteCount + deleteGrdAssign(record);
                nTempCnt = updateGrdAssign(record);
               	nSaveCount = nSaveCount + nTempCnt;
        		//nTempCnt = insertGrdAssign(record);
        		//nSaveCount = nSaveCount + nTempCnt;
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	try 
            	{
            		nTempCnt = insertGrdAssign(record);
            		nSaveCount = nSaveCount + nTempCnt;
            
            	}
            	catch(Exception e)
            	{
            		Util.setSvcMsg(ctx, "이미 등록된 자료가 존재합니다1");
            	}
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	 nDeleteCount = nDeleteCount + deleteGrdAssign(record);
            }
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     /**
      * <p> 경주등급별배정 입력  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertGrdAssign(PosRecord record) 
     {  
    	 logger.logInfo("==========================  경주등급별 배정 입력   ============================");
    	 
    	 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("ADV_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("STR_RANK"));
		 logger.logInfo(record.getAttribute("END_RANK"));
		 logger.logInfo(record.getAttribute("RACE_NO"));
		 logger.logInfo(record.getAttribute("EQU_RANK"));
		 logger.logInfo(record.getAttribute("EQU_RANK_NO"));
         logger.logInfo(SESSION_USER_ID);
    	 
    	 logger.logInfo("==========================  경주등급별 배정 입력   ============================");
    	 
    	 PosParameter param = new PosParameter();   
    	 int i = 0;
         param.setValueParamter(i++, Util.trim(record.getAttribute("MAT_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO2")));
         param.setValueParamter(i++, SESSION_USER_ID);
         
    	 int dmlcount = this.getDao("candao").insert("tbdb_grd_assign_ib001", param);
         
         return dmlcount;
     }
     /**
      * <p> 경주등급별배정 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateGrdAssign(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         
    	 logger.logInfo("==========================  경주등급별 배정 수정   ============================");
    	 
    	 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("ADV_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("STR_RANK"));
		 logger.logInfo(record.getAttribute("END_RANK"));
		 logger.logInfo(record.getAttribute("RACE_NO"));
		 logger.logInfo(record.getAttribute("EQU_RANK"));
		 logger.logInfo(record.getAttribute("EQU_RANK_NO"));
         logger.logInfo(SESSION_USER_ID);
         logger.logInfo(record.getAttribute("W_MAT_CD"));
		 logger.logInfo(record.getAttribute("W_STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("W_ADV_RACE_GRD_CD"));
         
    	 logger.logInfo("==========================  경주등급별 배정 수정   ============================");
        
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("STR_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_RANK")));
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK2")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("EQU_RANK_NO2")));
         param.setValueParamter(i++, SESSION_USER_ID);
         
         i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MAT_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
         
    	 int dmlcount = this.getDao("candao").update("tbdb_grd_assign_ub001", param);
         
         return dmlcount;
     }
     /**
      * <p> 경주등급별배정  삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteGrdAssign(PosRecord record) 
     {
    	 logger.logInfo("====================== 시행경주등급  삭제 ===========================");
         
    	 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("STLT_RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("ADV_RACE_GRD_CD"));
    	 
    	 logger.logInfo("===================== 시행경주등급  삭제 ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MAT_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STLT_RACE_GRD_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ADV_RACE_GRD_CD")));
         
    	 int dmlcount = this.getDao("candao").delete("tbdb_grd_assign_db001", param);
         
         return dmlcount;
     }
}
