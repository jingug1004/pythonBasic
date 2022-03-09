/*================================================================================
 * 시스템		: 선수훈련일지
 * 소스파일 이름	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * 파일설명		: 코드 관리
 * 작성자		: 백인주
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000004.activity;

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

public class SavePlayerTrain extends SnisActivity 
{
	public SavePlayerTrain() { }
	
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
        int    nSize = 0;
        String     sDsName = "";
   
        sDsName = "dsDiaryMst";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        sDsName = "dsDiaryAcdnt";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        sDsName = "dsDiaryDetl";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        sDsName = "dsDiaryAbs";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        saveState(ctx); 
        
        return PosBizControlConstants.SUCCESS; 
    }
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소 경정선수훈련일지마스타 
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
        String     sDsName = "";
        
        sDsName = "dsDiaryMst";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( (nTempCnt = updateDiaryMst(record, ctx)) == 0 ) {
	            	nTempCnt = insertDiaryMst(record, ctx);
	            }
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        sDsName = "dsDiaryAcdnt";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteDiaryAcdnt(record, ctx);
	 			}
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
	 			  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            if ( (nTempCnt = updateDiaryAcdnt(record, ctx)) == 0 ) {
		            	nTempCnt = insertDiaryAcdnt(record, ctx);
		            }
	 			}
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
	        
        }

        sDsName = "dsDiaryDetl";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteDiaryDetl(record, ctx);
	 			}
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
	 			  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            if ( (nTempCnt = updateDiaryDetl(record, ctx)) == 0 ) {
		            	nTempCnt = insertDiaryDetl(record, ctx);
		            }
	 			}
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
	        
        }

        sDsName = "dsDiaryAbs";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteDiaryAbs(record, ctx);
	 			}
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
	 			  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            if ( (nTempCnt = updateDiaryAbs(record, ctx)) == 0 ) {
		            	nTempCnt = insertDiaryAbs(record, ctx);
		            }
	 			}
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
	        
        }
                  
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
     
     /**
      * <p> 선수훈련일지마스터 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
   	protected int insertDiaryMst(PosRecord record, PosContext ctx) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, ctx   .get         ("DT"));
		param.setValueParamter(i++, record.getAttribute("WEATHER1"));
		param.setValueParamter(i++, record.getAttribute("WEATHER2"));
		param.setValueParamter(i++, record.getAttribute("WIND_DIRC1"));
		param.setValueParamter(i++, record.getAttribute("WIND_DIRC2"));
		param.setValueParamter(i++, record.getAttribute("WIND_SPD1"));
		param.setValueParamter(i++, record.getAttribute("WIND_SPD2"));
		param.setValueParamter(i++, record.getAttribute("TEMPT1"));
		param.setValueParamter(i++, record.getAttribute("TEMPT2"));
		param.setValueParamter(i++, record.getAttribute("HUMID1"));
		param.setValueParamter(i++, record.getAttribute("HUMID2"));
		param.setValueParamter(i++, record.getAttribute("WAT_TEMP1"));
		param.setValueParamter(i++, record.getAttribute("WAT_TEMP2"));
		param.setValueParamter(i++, record.getAttribute("WAT_LEV"));
		param.setValueParamter(i++, record.getAttribute("TOT_PERS_NO"));
		param.setValueParamter(i++, record.getAttribute("CURR_PERS_NO"));
		param.setValueParamter(i++, record.getAttribute("ABS_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("EXC_PERS_NO"));		
		param.setValueParamter(i++, record.getAttribute("INSTR"));
		param.setValueParamter(i++, record.getAttribute("EDU_PLC"));
		param.setValueParamter(i++, record.getAttribute("IND_DESC"));
		param.setValueParamter(i++, record.getAttribute("ETC_DESC"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_mst_ib001", param);

		return dmlcount;
	}
     
     /**
      * <p> 선수훈련일지마스터 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
    protected int updateDiaryMst(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();       					
        int i = 0;
         
        param.setValueParamter(i++, record.getAttribute("WEATHER1"));
        param.setValueParamter(i++, record.getAttribute("WEATHER2"));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC1"));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC2"));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD1"));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD2"));
        param.setValueParamter(i++, record.getAttribute("TEMPT1"));
        param.setValueParamter(i++, record.getAttribute("TEMPT2"));
        param.setValueParamter(i++, record.getAttribute("HUMID1"));
        param.setValueParamter(i++, record.getAttribute("HUMID2"));
        param.setValueParamter(i++, record.getAttribute("WAT_TEMP1"));
        param.setValueParamter(i++, record.getAttribute("WAT_TEMP2"));
        param.setValueParamter(i++, record.getAttribute("WAT_LEV"));
        param.setValueParamter(i++, record.getAttribute("TOT_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("CURR_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("ABS_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("EXC_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("INSTR"));
        param.setValueParamter(i++, record.getAttribute("EDU_PLC"));
        param.setValueParamter(i++, record.getAttribute("IND_DESC"));
        param.setValueParamter(i++, record.getAttribute("ETC_DESC"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, ctx   .get         ("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, ctx   .get         ("DT"));
		
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_mst_ub001", param);

		return dmlcount;
     }
     
     /**
      * <p> 선수훈련일지사고 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
    protected int insertDiaryAcdnt(PosRecord record, PosContext ctx) 
    {
         PosParameter param = new PosParameter();       					
         int i = 0;			
		          
         //param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
         param.setValueParamter(i++, record.getAttribute("DT"));
         param.setValueParamter(i++, record.getAttribute("SEQ"));
         param.setValueParamter(i++, record.getAttribute("GBN"));         
         param.setValueParamter(i++, record.getAttribute("RACER_NO"));
         param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT"));
         param.setValueParamter(i++, record.getAttribute("RACE_CNTNT"));
         param.setValueParamter(i++, record.getAttribute("REPAIT_DESC"));
         param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, SESSION_USER_ID);
         
         int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_acdnt_ib001", param);
         
         return dmlcount;
    }
     

     /**
      * <p> 선수훈련일지사고 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
    protected int updateDiaryAcdnt(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();       					
        int i = 0;
         			
		param.setValueParamter(i++, record.getAttribute("GBN"));
		param.setValueParamter(i++, record.getAttribute("RACER_NO"));
		param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT"));
		param.setValueParamter(i++, record.getAttribute("RACE_CNTNT"));
		param.setValueParamter(i++, record.getAttribute("REPAIT_DESC"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_acdnt_ub001", param);

		return dmlcount;
    }
     
 
     /**
      * <p> 선수훈련일지사고 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
    protected int deleteDiaryAcdnt(PosRecord record, PosContext ctx) 
    {
  		PosParameter param = new PosParameter();       					
		int i = 0;
        
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_acdnt_db001", param);
		return dmlcount;
    }
     
     
     /**
      * <p> 선수훈련일지상세 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
 	protected int insertDiaryDetl(PosRecord record, PosContext ctx) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setValueParamter(i++, record.getAttribute("DT"));
		param.setValueParamter(i++, record.getAttribute("SEQ"));         
		param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
		param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
		param.setValueParamter(i++, record.getAttribute("EDU_DETL"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_detl_ib001", param);

		return dmlcount;
	}
     

     /**
      * <p> 선수훈련일지상세 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateDiaryDetl(PosRecord record, PosContext ctx) 
     {
        PosParameter param = new PosParameter();       					
        int i = 0;
         
        param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
        param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
        param.setValueParamter(i++, record.getAttribute("EDU_DETL"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    		
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_detl_ub001", param);

		return dmlcount;
     }
     
 
     /**
      * <p> 선수훈련일지상세 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
    protected int deleteDiaryDetl(PosRecord record, PosContext ctx) 
    {
 		PosParameter param = new PosParameter();       					
		int i = 0;
        
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_abs_db001", param);
		return dmlcount;
    }
     
     
     /**
      * <p> 선수훈련일지결원 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
    protected int insertDiaryAbs(PosRecord record, PosContext ctx) 
    {
         PosParameter param = new PosParameter();       					
         int i = 0;
          
         param.setValueParamter(i++, record.getAttribute("DT")); 
         param.setValueParamter(i++, record.getAttribute("SEQ"));         
         param.setValueParamter(i++, record.getAttribute("RACER_NO"));         
         param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
         param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
         param.setValueParamter(i++, record.getAttribute("ABS_EXC_GBN"));
         param.setValueParamter(i++, record.getAttribute("RMK"));         
         param.setValueParamter(i++, SESSION_USER_ID);  
         param.setValueParamter(i++, SESSION_USER_ID);  

         int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_abs_ib001", param);
         return dmlcount;
    }
     
     /**
      * <p> 선수훈련일지결원 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
    protected int updateDiaryAbs(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();       					
        int i = 0;
        
		param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
        param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
        param.setValueParamter(i++, record.getAttribute("ABS_EXC_GBN"));
        param.setValueParamter(i++, record.getAttribute("RMK"));   
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
							
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_abs_ub001", param);
		return dmlcount;
    }
     
 
     /**
      * <p> 선수훈련일지결원 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
    protected int deleteDiaryAbs(PosRecord record, PosContext ctx) 
    {
		PosParameter param = new PosParameter();       					
		int i = 0;
        
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_abs_db001", param);
		return dmlcount;
	}         
}
