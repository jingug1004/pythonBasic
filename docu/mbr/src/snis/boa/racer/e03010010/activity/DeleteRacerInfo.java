/*================================================================================
 * 시스템			: 선수관리
 * 소스파일 이름	: snis.boa.racer.e03010010.activity.DeleteRacerInfo.java
 * 파일설명		: 선수정보삭제
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-05
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03010010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 선수정보를를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class DeleteRacerInfo extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public DeleteRacerInfo()
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

        sSessionUserId = (String) ctx.getSessionUserData("USER_ID");

        ds    = (PosDataset)ctx.get("dsOutRacer");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsOutRacerFam");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }

        ds    = (PosDataset)ctx.get("dsOutRacerAcad");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }        

        ds    = (PosDataset)ctx.get("dsOutRacerRela");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; i++ ) 
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

        sRacerNo = (String) ctx.get("RACER_NO");
        ds    = (PosDataset) ctx.get("dsOutRacer");
        nSize = ds.getRecordCount();

        // 선수상세정보 저장
    	int effectedRowCnt = 0;
    	effectedRowCnt = deleteRacer(sRacerNo);
    }

 
    /**
     * <p> 선수상세정보 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRacer(String sRacerNo) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sRacerNo                           );
        
        int dmlcount = this.getDao("boadao").update("tbec_racer_master_dc001", param);
        
        if (dmlcount > 0)	{ //마스터정보가 정상적으로 업데이트 되면..
        	//선수상세정보 삭제
        	PosParameter param2 = new PosParameter();        	
            i = 0;
            param2.setWhereClauseParameter(i++, sRacerNo                           );
        	dmlcount = this.getDao("boadao").update("tbec_racer_detail_dc001", param2);

        	//선수가족정보 삭제
        	PosParameter param3 = new PosParameter();  
            i = 0;
            param3.setWhereClauseParameter(i++, sRacerNo                           );
            
            dmlcount = this.getDao("boadao").update("tbec_racer_family_dc002", param3);
            
        	//선수학력정보 삭제
        	PosParameter param4 = new PosParameter();
            i = 0;
            param4.setWhereClauseParameter(i++, sRacerNo                           );

        	dmlcount = this.getDao("boadao").update("tbec_racer_academic_dc002", param4);
        	
        	//선수관계정보 삭제
        	PosParameter param5 = new PosParameter();  
            i = 0;
            param5.setWhereClauseParameter(i++, sRacerNo                           );

        	dmlcount = this.getDao("boadao").update("tbec_racer_relation_dc002", param5);

        }
        
        return dmlcount;
    }
 

}