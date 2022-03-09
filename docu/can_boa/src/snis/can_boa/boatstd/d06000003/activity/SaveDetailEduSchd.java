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
package snis.can_boa.boatstd.d06000003.activity;

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

public class SaveDetailEduSchd extends SnisActivity 
{
	public SaveDetailEduSchd() { }
	
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

    	PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";

        sDsName = "dsDetlEduAssign";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("------------------->"+record);
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

     	PosDataset ds4;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
        
        // 시간배정표 저장       
        ds4   = (PosDataset) ctx.get("dsDetlEduAssign");
        nSize = ds4.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
        logger.logInfo("nTempCnt1------------------->"+nTempCnt);   
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds4.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
            		 nTempCnt = insertDetl_Edu_Assign(record);
            	 }else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            		 nTempCnt = updateDetl_Edu_Assign(record);
                 }        	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteDetl_Edu_Assign(record);
             }
         }
                  
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> 세부교육일정 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertDetl_Edu_Assign(PosRecord record) 
     {
     	logger.logInfo("insertEduAssign ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
 
         String sDt 	= Util.trim(record.getAttribute("DT"));
         String sYear 	= "";
         String sMm		= "";
         sDt = Util.NVL(sDt,"");
         if(!sDt.equals("")){
        	 sYear	= sDt.substring(0,4);
        	 sMm 	= "0" + sDt.substring(4,6); 
 //       	 logger.logInfo("insertDetl_Edu_Assign sYear===="+sYear);
 //       	 logger.logInfo("insertDetl_Edu_Assign sMM===="+sMm);
         }
          
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, sYear);
         param.setValueParamter(i++, sMm);
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DETL_EDU_CRS")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("OBJ")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PLC")));
         param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
         param.setValueParamter(i++, SESSION_USER_ID); 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         
         int dmlcount = this.getDao("candao").insert("tbdn_detl_edu_assign_in001", param);
         
         return dmlcount;
     }
     
     /**
      * <p> 세부교육일정 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateDetl_Edu_Assign(PosRecord record) 
     {
     	logger.logInfo("updateDetl_Edu_Assign ============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
		String sDt 	= Util.trim(record.getAttribute("DT"));
		String sYear 	= "";
		String sMm		= "";
		sDt = Util.NVL(sDt,"");
		if(!sDt.equals("")){
			 sYear	= sDt.substring(0,4);
			 sMm 	=  "0" +  sDt.substring(4,6); 
			 logger.logInfo("insertDetl_Edu_Assign sYear===="+sYear);
			 logger.logInfo("insertDetl_Edu_Assign sMM===="+sMm);
		}
		
		param.setValueParamter(i++, sYear);
		param.setValueParamter(i++, sMm);  
		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));  
		param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));   
		param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("DETL_EDU_CRS"))); 
		param.setValueParamter(i++, Util.trim(record.getAttribute("OBJ")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("PLC")));
		param.setValueParamter(i++, record.getAttribute("EDU_TIME")); 
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

		int dmlcount = this.getDao("candao").update("tbdn_detl_edu_assign_un001", param);

		logger.logInfo("updateDetl_Edu_Assign ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> 시간배정표  삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteDetl_Edu_Assign(PosRecord record) 
     {
    	 logger.logInfo("deleteDetl_Edu_Assign start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_detl_edu_assign_dn001", param);
         
         logger.logInfo("deleteDetl_Edu_Assign end============================");
         return dmlcount;
     }
}
