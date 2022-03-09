/*================================================================================
 * 시스템		: 선수훈련결과등록
 * 소스파일 이름	: snis.can.system.d02000002.activity.SavePlayerResult.java
 * 파일설명		: 코드 관리
 * 작성자		: 백인주
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000003.activity;

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

public class SavePlayerResult extends SnisActivity 
{
	public SavePlayerResult() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//  사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
    	        return PosBizControlConstants.SUCCESS;
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
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
        
        // 훈련상태 변경       
        ds   = (PosDataset) ctx.get("dsRacerTrngRslt");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 if ( (nTempCnt = updatePlayerResult(record)) == 0 ) {
                     nTempCnt = insertPlayerResult(record);
                 }              	 
            	 updateRacerResult(record);
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }        
                  
        // 스타트 측정 결과 저장       
        ds   = (PosDataset) ctx.get("dsRacerTrngRsltStart");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 nTempCnt = mergeUpdateResultStart(ctx, record);
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }
        
        // 정비기술 평가 결과 저장       
        ds   = (PosDataset) ctx.get("dsRacerTrngRsltMngTech");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 nTempCnt = mergeUpdateResultMngTech(ctx, record);
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }
        
        // 모터보트 조종실기 평가 결과 저장       
        ds   = (PosDataset) ctx.get("dsRacerTrngRsltManiEx");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 nTempCnt = mergeUpdateResultManiEx(ctx, record);
            	 nSaveCount = nSaveCount + nTempCnt;
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
  	protected int insertPlayerResult(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setValueParamter(i++, record.getAttribute("ESTI_DT")); 
		param.setValueParamter(i++, record.getAttribute("RACER_NO"));
		
		param.setValueParamter(i++, record.getAttribute("PER_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_3"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_1"));
		param.setValueParamter(i++, record.getAttribute("ST"));
		param.setValueParamter(i++, record.getAttribute("IMIT_RACE"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME_GRD"));
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT"));
		param.setValueParamter(i++, record.getAttribute("BODY_TRNG"));
		param.setValueParamter(i++, record.getAttribute("VIDEO"));
		param.setValueParamter(i++, record.getAttribute("PRIZ_PENAL")); 
		param.setValueParamter(i++, record.getAttribute("CMPL_YN")); 
		param.setValueParamter(i++, record.getAttribute("DETL_EDU_CNTN")); 
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT_PELLER")); 
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_rslt_io001", param);
		return dmlcount;
	}
     
     /**
      * <p> 세부교육일정 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updatePlayerResult(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
		param.setValueParamter(i++, record.getAttribute("PER_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_3"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_1"));
		param.setValueParamter(i++, record.getAttribute("ST"));
		param.setValueParamter(i++, record.getAttribute("IMIT_RACE"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME_GRD"));
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT"));
		param.setValueParamter(i++, record.getAttribute("BODY_TRNG"));
		param.setValueParamter(i++, record.getAttribute("VIDEO"));
		param.setValueParamter(i++, record.getAttribute("PRIZ_PENAL")); 
		param.setValueParamter(i++, record.getAttribute("CMPL_YN")); 
		param.setValueParamter(i++, record.getAttribute("DETL_EDU_CNTN")); 
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT_PELLER")); 
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));
			
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_rslt_uo001", param);
		return dmlcount;
     }
     
     
     /**
      * <p> 평가결과  삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
 	protected int deletePlayerResult(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));

		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_rslt_do001", param);
		return dmlcount;
	}
 	 
    /**
     * <p> 평가결과  수정</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
	protected int updateRacerResult(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;
		if ( record.getAttribute("CMPL_YN").equals("Y") ) {
			param.setValueParamter(i++, "002");
		} else if ( record.getAttribute("CMPL_YN").equals("N") ) {
			param.setValueParamter(i++, "003");
		} else {
			param.setValueParamter(i++, "001");
		}

		i = 0;
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));

		int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_uo001", param);
		return dmlcount;
	}
	 
	

    /**
     * <p> 스타트 평가결과  수정</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
	protected int mergeUpdateResultStart(PosContext ctx, PosRecord record) 
	{
		String sTrngSeq = ctx.get("TRNG_ASK_SEQ").toString();
		
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++,  sTrngSeq);
		param.setValueParamter(i++,  record.getAttribute("RACER_NO"));
		param.setValueParamter(i++,  record.getAttribute("ESTI_DT"));
		param.setValueParamter(i++,  record.getAttribute("RSLT1"));
		param.setValueParamter(i++,  record.getAttribute("RSLT2"));
		param.setValueParamter(i++,  record.getAttribute("RSLT3"));
		param.setValueParamter(i++,  record.getAttribute("RSLT4"));
		param.setValueParamter(i++,  record.getAttribute("RSLT5"));
		param.setValueParamter(i++,  record.getAttribute("RSLT6"));
		param.setValueParamter(i++,  record.getAttribute("RSLT"));
		param.setValueParamter(i++,  record.getAttribute("ESTIER"));
		
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("d07000003_u11", param);
		return dmlcount;
	}

    /**
     * <p> 정비기술 평가결과  수정</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
	protected int mergeUpdateResultMngTech(PosContext ctx, PosRecord record) 
	{
		String sTrngSeq = ctx.get("TRNG_ASK_SEQ").toString();
		
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++,  sTrngSeq);
		param.setValueParamter(i++,  record.getAttribute("RACER_NO"));
		param.setValueParamter(i++,  record.getAttribute("ESTI_DT"));
		param.setValueParamter(i++,  record.getAttribute("ITEM1"));
		param.setValueParamter(i++,  record.getAttribute("ITEM2"));
		param.setValueParamter(i++,  record.getAttribute("ITEM3"));
		param.setValueParamter(i++,  record.getAttribute("ITEM4"));
		param.setValueParamter(i++,  record.getAttribute("ITEM5"));
		param.setValueParamter(i++,  record.getAttribute("LEAD_TIME"));
		param.setValueParamter(i++,  record.getAttribute("DISQUAL"));
		param.setValueParamter(i++,  record.getAttribute("RSLT"));
		param.setValueParamter(i++,  record.getAttribute("ESTIER"));
		
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("d07000003_u12", param);
		return dmlcount;
	}

    /**
     * <p> 스타트 평가결과  수정</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
	protected int mergeUpdateResultManiEx(PosContext ctx, PosRecord record) 
	{
		String sTrngSeq = ctx.get("TRNG_ASK_SEQ").toString();
		
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++,  sTrngSeq);
		param.setValueParamter(i++,  record.getAttribute("RACER_NO"));
		param.setValueParamter(i++,  record.getAttribute("ESTI_DT"));
		param.setValueParamter(i++,  record.getAttribute("ITEM1"));
		param.setValueParamter(i++,  record.getAttribute("ITEM2"));
		param.setValueParamter(i++,  record.getAttribute("ITEM3"));
		param.setValueParamter(i++,  record.getAttribute("ITEM4"));
		param.setValueParamter(i++,  record.getAttribute("ITEM5"));
		param.setValueParamter(i++,  record.getAttribute("ITEM6"));
		param.setValueParamter(i++,  record.getAttribute("ITEM7"));
		param.setValueParamter(i++,  record.getAttribute("DISQUAL"));
		param.setValueParamter(i++,  record.getAttribute("RSLT"));
		param.setValueParamter(i++,  record.getAttribute("ESTIER"));
		
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("d07000003_u13", param);
		return dmlcount;
	}
	
}
