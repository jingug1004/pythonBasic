/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.can.cyclestd.d02000025.activity.SaveGroupOrg.java
 * 파일설명		: 조편성 (저장)
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2007-03-27
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000025.activity;
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
public class SaveGroupOrg extends SnisActivity  {
	
	public SaveGroupOrg(){ }
	
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
         sDsName = "dsGroupOrg";

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
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt     = 0;
               
        // 교육개요 저장
        ds    = (PosDataset)ctx.get("dsGroupOrg");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i); 
            if(i == 0){
            	nDeleteCount = nDeleteCount + deleteGroupOrg(record);
            }
    		nTempCnt = insertGroupOrg(record);
    		nSaveCount = nSaveCount + nTempCnt;      
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
       // Util.setDeleteCount(ctx, nDeleteCount   );
     }
    /**
     * <p> 조편성   </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertGroupOrg(PosRecord record) 
    {  
   	 logger.logInfo("==========================  조편성 입력   ============================");

   	 	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("CAND_NO"));
		 logger.logInfo(record.getAttribute("MAT_CD"));		 
		 logger.logInfo(record.getAttribute("RACE_GRD_CD"));
		 logger.logInfo(record.getAttribute("RACE_NO"));
		 logger.logInfo(record.getAttribute("BACK_NO"));
		 logger.logInfo(record.getAttribute("GEAR_RATE"));
		 logger.logInfo(record.getAttribute("TOT_AVG"));
		 logger.logInfo(record.getAttribute("GRD"));
         logger.logInfo(SESSION_USER_ID);
   	 
   	 logger.logInfo("==========================  조편성 입력   ============================");
   	 
   	 PosParameter param = new PosParameter();   
   	 int i = 0;
	 
   	    param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND")); 
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));//WHERE
        param.setValueParamter(i++, record.getAttribute("MAT_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_GRD_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, record.getAttribute("GEAR_RATE"));
        param.setValueParamter(i++, record.getAttribute("TOT_AVG"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("DCSN_GBN"));
        param.setValueParamter(i++, record.getAttribute("TOT_RANK"));//TOT_RANK
        param.setValueParamter(i++, record.getAttribute("STRATEGY"));
        param.setValueParamter(i++, record.getAttribute("LEG_TPE"));
        param.setValueParamter(i++, record.getAttribute("WIN_RATE"));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATE"));
        param.setValueParamter(i++, record.getAttribute("RANK1_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK2_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK3_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK4_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK5_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK6_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK7_CNT"));
        param.setValueParamter(i++, record.getAttribute("ELIM1_CNT"));
        param.setValueParamter(i++, record.getAttribute("ELIM2_CNT"));
        param.setValueParamter(i++, record.getAttribute("ELIM3_CNT"));	
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, record.getAttribute("BF_ROUND"));
        param.setValueParamter(i++, record.getAttribute("BF_RACE"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));  //UPDATE
   	    
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND")); 
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("MAT_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_GRD_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, record.getAttribute("GEAR_RATE"));
        param.setValueParamter(i++, record.getAttribute("TOT_AVG"));
        param.setValueParamter(i++, record.getAttribute("GRD"));	
        param.setValueParamter(i++, record.getAttribute("DCSN_GBN"));
        param.setValueParamter(i++, record.getAttribute("TOT_RANK")); //TOT_RANK
        param.setValueParamter(i++, record.getAttribute("STRATEGY"));
        param.setValueParamter(i++, record.getAttribute("LEG_TPE"));
        param.setValueParamter(i++, record.getAttribute("WIN_RATE"));
        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATE"));
        param.setValueParamter(i++, record.getAttribute("RANK1_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK2_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK3_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK4_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK5_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK6_CNT"));
        param.setValueParamter(i++, record.getAttribute("RANK7_CNT"));
        param.setValueParamter(i++, record.getAttribute("ELIM1_CNT"));
        param.setValueParamter(i++, record.getAttribute("ELIM2_CNT"));
        param.setValueParamter(i++, record.getAttribute("ELIM3_CNT"));	
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, record.getAttribute("BF_ROUND"));
        param.setValueParamter(i++, record.getAttribute("BF_RACE"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));//INSERT
        
   	 int dmlcount = this.getDao("candao").insert("tbdb_group_org_ib001", param);
        
        return dmlcount;
    }
    
    protected int deleteGroupOrg(PosRecord record) 
    {  
   	 	 logger.logInfo("====================== 조편성  삭제 ===========================");
         
    	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
    	 
    	 logger.logInfo("===================== 조편성  삭제 ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("RACE_YY"));
         param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
         
    	 int dmlcount = this.getDao("candao").delete("tbdb_group_org_db001", param);
         
         return dmlcount;
    }
    protected int deleteCand(PosRecord record) 
    {  
   	 	 logger.logInfo("====================== 선수  삭제 ===========================");
         
    	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("CAND_NO"));
    	 
    	 logger.logInfo("===================== 선수  삭제 ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("RACE_YY"));
         param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
         param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"));
    	 int dmlcount = this.getDao("candao").delete("tbdb_group_org_db002", param);
         
         return dmlcount;
    }

}
