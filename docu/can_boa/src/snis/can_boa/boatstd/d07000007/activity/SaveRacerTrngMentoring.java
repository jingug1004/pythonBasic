/*================================================================================
 * 시스템		    : 
 * 소스파일 이름	: snis.can.system.d07000007.activity.SaveRacerMentoring.java
 * 파일설명		: 선수멘토링훈련내용 입력/수정/삭제
 * 작성자		    : 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2012-12-10
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/

public class SaveRacerTrngMentoring extends SnisActivity 
{
	public SaveRacerTrngMentoring() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String	sucess 문자열
     * @throws  none
     */    
	    
    public String runActivity(PosContext ctx)
    {
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	/*
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsRacerMentoring";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
        */
        saveState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
     
      
  	/**
  	* <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
  	* @param   ctx		PosContext	저장소 : 일자별 학과평가
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveState(PosContext ctx)
  	{
  		int nSaveCount   = 0;
  		int nDeleteCount = 0;

  		PosDataset ds;
  		
        String sDsName      = "";
  		int nSize 	= 0;

        sDsName = "dsRacerMentoring";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        logger.logDebug("================================================================" + nSize);
	        
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteRacerMentoring(record);
	 			}

	             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
	             {      
	            	 nSaveCount += mergeUpdateRacerMentoring(record);
	             }
	        }
        }
        
        sDsName = "dsMentee";	// 멘티 관리
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nSaveCount += deleteMentee(record);
	 			}

	             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
	             {      
	            	 nSaveCount += updateMentee(record);
	             }
	        }
        }
        
  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    

     /**
      * <p> 선수멘토링훈련내용 삭제 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int deleteRacerMentoring(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
     	param.setWhereClauseParameter(i++, record.getAttribute("MENTO_GRP_NO"));
     	param.setWhereClauseParameter(i++, record.getAttribute("TRNG_DT"));
        
     	int dmlcount = this.getDao("candao").delete("d07000007_d01", param);
        
     	return dmlcount;
     }
     
     

     /**
      * <p> 선수멘토링훈련내용 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int mergeUpdateRacerMentoring(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
     	param.setValueParamter(i++, record.getAttribute("MENTO_GRP_NO"));
     	param.setValueParamter(i++, record.getAttribute("TRNG_DT"));
     	param.setValueParamter(i++, record.getAttribute("TRNG_CONT"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
     	int dmlcount = this.getDao("candao").update("d07000007_m01", param);
        
     	return dmlcount;
     }


     /**
      * <p> 선수멘티 삭제 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int deleteMentee(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
     	param.setWhereClauseParameter(i++, record.getAttribute("MENTO_GRP_NO"));
     	param.setWhereClauseParameter(i++, record.getAttribute("MENTEE_RACER_NO"));
        
     	int dmlcount = this.getDao("candao").delete("d07000007_d02", param);
        
     	return dmlcount;
     }


     /**
      * <p> 멘티선수 삭제 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int updateMentee(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setValueParamter(i++, record.getAttribute("MENTO_GRP_NO"));
		param.setValueParamter(i++, record.getAttribute("MENTEE_RACER_NO"));
		param.setValueParamter(i++, record.getAttribute("MENTO_RACER_NO"));
		param.setValueParamter(i++, record.getAttribute("MENTEE_TRNG_ASK_SEQ"));
		param.setValueParamter(i++, record.getAttribute("RMK"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
     	int dmlcount = this.getDao("candao").update("d07000007_m02", param);
        
     	return dmlcount;
     }
     
}
