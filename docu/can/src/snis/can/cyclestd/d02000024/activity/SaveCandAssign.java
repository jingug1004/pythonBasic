/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.can.cyclestd.d02000024.activity.SaveCandAssign.java
 * 파일설명		: 후보생 배정 (저장)
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2007-03-20
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/

package snis.can.cyclestd.d02000024.activity;

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

public class SaveCandAssign extends SnisActivity {
	
	public SaveCandAssign(){ }

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
         sDsName = "dsCandAssign";

         logger.logInfo("codemanager start ==============");
         if ( ctx.get(sDsName) != null ) {
 	        ds    = (PosDataset)ctx.get(sDsName);
 	        nSize = ds.getRecordCount();
 	        for ( int i = 0; i < nSize; i++ ) 
 	        {
 	            PosRecord record = ds.getRecord(i);
 	            logger.logInfo(record);
 	        }
 	       logger.logInfo("---------------------------------------");
         }   
 	     saveState(ctx);
 	    logger.logInfo("codemanager end =================");
         return PosBizControlConstants.SUCCESS;
     }
   
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt     = 0;
               
        // 교육개요 저장
        ds    = (PosDataset)ctx.get("dsCandAssign");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i); 
        	//try 
        	//{
        		nTempCnt = insertCandAssign(record);
        		nSaveCount = nSaveCount + nTempCnt;      
        	//}
        //	catch(Exception e)
        //	{
        //		Util.setSvcMsg(ctx, "이미 등록된 자료가 존재합니다1");
        //	}
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
    /**
     * <p> 후보생배정   </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertCandAssign(PosRecord record) 
    {  
   	 logger.logInfo("==========================  후보생배정 입력   ============================");

   	 	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("CAND_NO"));
		 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("GBN"));
		 logger.logInfo(record.getAttribute("DCSN_GBN"));
		 logger.logInfo(record.getAttribute("RACE_CNT"));
		 logger.logInfo(record.getAttribute("TOT_AVG"));
		 logger.logInfo(record.getAttribute("TOT_RANK"));
         logger.logInfo(SESSION_USER_ID);
   	 
   	 logger.logInfo("==========================  후보생배정 입력   ============================");
   	 
   	 PosParameter param = new PosParameter();   
   	 int i = 0;
	 
   	    param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND")); 
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));//WHERE
        param.setValueParamter(i++, record.getAttribute("MAT_CD"));
        param.setValueParamter(i++, record.getAttribute("GBN"));
        param.setValueParamter(i++, record.getAttribute("DCSN_GBN"));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
        param.setValueParamter(i++, record.getAttribute("TOT_AVG"));
        param.setValueParamter(i++, record.getAttribute("TOT_RANK"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));  //UPDATE
   	    param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("MAT_CD"));
        param.setValueParamter(i++, record.getAttribute("GBN"));
        param.setValueParamter(i++, record.getAttribute("DCSN_GBN"));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
        param.setValueParamter(i++, record.getAttribute("TOT_AVG"));
        param.setValueParamter(i++, record.getAttribute("TOT_RANK"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));  //INSERT
        
   	 int dmlcount = this.getDao("candao").insert("tbdb_cand_assign_ib001", param);
        
        return dmlcount;
    }
}
