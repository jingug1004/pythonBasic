/*================================================================================
 * 시스템		: 학사관리
 * 소스파일 이름	: snis.can.system.d02000002.activity.UserManager.java
 * 파일설명		: 주단위 기준등록
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-09
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000001.activity;

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

public class SaveWkStandard extends SnisActivity
{
	
	public SaveWkStandard() { }
	
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
       	        
         sDsName = "dsOutWkStandard";
         
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
        ds    = (PosDataset)ctx.get("dsOutWkStandard");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) 
            {
                nTempCnt = updateWkStandard(record);
               	nSaveCount = nSaveCount + nTempCnt;
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nTempCnt = insertWkStandard(record);
            	nSaveCount = nSaveCount + nTempCnt;
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nTempCnt = deleteWkStandard(record);
            	nDeleteCount = nDeleteCount + nTempCnt; 
            }
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> 주단위기준 입력  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertWkStandard(PosRecord record) 
     {
    	          
         PosParameter param = new PosParameter();       					
         int i = 0;
                 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("YY"))); 
         param.setValueParamter(i++, Util.trim(record.getAttribute("MM"))); 
         param.setValueParamter(i++, record.getAttribute("WK"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                 
         int inscount = this.getDao("candao").insert("tbdb_cmpt_wk_std_ib001", param);
         
         return inscount;
     }
     
     
     /**
      * <p> 주단위기준 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateWkStandard(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;
                           
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("YY"))); 
         param.setValueParamter(i++, Util.trim(record.getAttribute("MM")));          
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                  
         i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("WK"));
               
         int updcount = this.getDao("candao").update("tbdb_cmpt_wk_std_ub001", param);
               
         return updcount;
     }
     
     /**
      * <p> 주단위기준  삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteWkStandard(PosRecord record) 
     {
    	  	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
                             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("YY")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MM")));
         param.setWhereClauseParameter(i++, record.getAttribute("WK"));
         
         int delcount = this.getDao("candao").delete("tbdb_cmpt_wk_std_db001", param);
         
         return delcount;
     }
}
