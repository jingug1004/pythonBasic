/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03010017.activity.SaveRacerReference.java
 * 파일설명		: 선수 면담정보(인적사항, 경주운영, 선수관계, 면담일지) 저장 및 삭제
 * 작성자		: 정리운
 * 버전			:
 * 생성일자		: 2015-09-12
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05110010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 부상선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 정리운
*/
public class SaveCustomerGuess extends SnisActivity
{    
	
	protected String sStndYear      = "";
	protected String sMbrCd   	    = "";
	protected String sTms           = "";
	protected String sDayOrd   	    = "";
	protected String sSessionUserId = "";
	
    public SaveCustomerGuess()
    {
    }

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
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsRaceRslt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsSpecialDocu");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
        	PosRecord record = ds.getRecord(i);
        	if(logger.isDebugEnabled())	logger.logDebug(record);
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

        sStndYear = (String) ctx.get("STND_YEAR");
        sMbrCd 	  = (String) ctx.get("MBR_CD");
        sTms      = (String) ctx.get("TMS");
        sDayOrd   = (String) ctx.get("DAY_ORD");
        
         ds    = (PosDataset) ctx.get("dsRaceRslt");
         nSize = ds.getRecordCount();

         for ( int i = 0; i < nSize; i++ ) 
         {
             PosRecord record = ds.getRecord(i);
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
             {
             	nSaveCount = nSaveCount + saveCustomerGuess(record);
             }
         }
         
         ds    = (PosDataset) ctx.get("dsSpecialDocu");
         nSize = ds.getRecordCount();
         
         for ( int i = 0; i < nSize; i++ ) 
         {
        	 PosRecord record = ds.getRecord(i);
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
        		 nSaveCount = nSaveCount + deleteRaceSpecialDocu(record);        		 
        	 }
        	 
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
        		 nSaveCount = nSaveCount + updateRaceSpecialDocu(record);        		 
        	 }
        	 
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
        		 nSaveCount = nSaveCount + insertRaceSpecialDocu(record);
        	 }
         }
         
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }  

     
     /**
      * <p>고객 예상순위 저장 </p>
      * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
      * @return  effectedRowCnt int		update 레코드 개수
      * @throws  none
      */
     protected int saveCustomerGuess(PosRecord record)
     {
     	int effectedRowCnt = 0;
     	effectedRowCnt =insertCustomerGuess(record);

         return effectedRowCnt;    	
     }

     
     /**
      * <p> 고객 예상순위 저장 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int insertCustomerGuess(PosRecord record) 
     {
         PosParameter param = new PosParameter();
         int i = 0;

         param.setValueParamter(i++, sStndYear);
         param.setValueParamter(i++, sMbrCd);
         param.setValueParamter(i++, sTms);
         param.setValueParamter(i++, sDayOrd);
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BET_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_INFO".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );
         param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));      
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));        
         param.setValueParamter(i++, Util.trim(record.getAttribute("BET_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_INFO".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );

         int dmlcount = this.getDao("boadao").update("tbee_customer_guess_in001", param);
         
         return dmlcount;  
     }   


     /**
      * <p> 공정관리-경주상황보고 특이사항 update </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int insertRaceSpecialDocu(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
    	 param.setValueParamter(i++, sStndYear);
    	 param.setValueParamter(i++, sMbrCd);
    	 param.setValueParamter(i++, sTms);
    	 param.setValueParamter(i++, sDayOrd);
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("SPECIAL_DOC".trim())));
    	 param.setValueParamter(i++, SESSION_USER_ID );
    	 
    	 int dmlcount = this.getDao("boadao").update("tbee_special_docu_in001", param);
    	 
    	 return dmlcount;  
     }     
     
     
     /**
      * <p> 공정관리-경주상황보고 특이사항 update </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateRaceSpecialDocu(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("SPECIAL_DOC".trim())));
    	 param.setValueParamter(i++, SESSION_USER_ID );
    	 param.setValueParamter(i++, sStndYear);
    	 param.setValueParamter(i++, sMbrCd);
    	 param.setValueParamter(i++, sTms);
    	 param.setValueParamter(i++, sDayOrd);
    	 
    	 int dmlcount = this.getDao("boadao").update("tbee_special_docu_up001", param);
    	 
    	 return dmlcount;  
     }        

     
     /**
      * <p> 공정관리-경주상황보고 특이사항 delete </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int deleteRaceSpecialDocu(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
    	 param.setValueParamter(i++, sStndYear);
    	 param.setValueParamter(i++, sMbrCd);
    	 param.setValueParamter(i++, sTms);
    	 param.setValueParamter(i++, sDayOrd);
    	 
    	 int dmlcount = this.getDao("boadao").update("tbee_special_docu_de001", param);
    	 
    	 return dmlcount;  
     }        
}    