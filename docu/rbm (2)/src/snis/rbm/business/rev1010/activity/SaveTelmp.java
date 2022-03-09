/*================================================================================
 * 시스템			: 발매실적
 * 소스파일 이름	: snis.rbm.business.rev1010.activity.SaveTelmp.java
 * 파일설명		: 발매실적
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1010.activity;

import java.sql.Connection;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1070.activity.*;
import snis.rbm.business.rev1010.activity.SaveData;

public class SaveTelmp extends SnisActivity {
	
	public SaveTelmp(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        Connection conn = null;        
        
        conn = getDao("rbmdao").getDBConnection();
        
        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//평가년도
        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//평가회차

        SaveEmpEstm SaveEmpEstm = new SaveEmpEstm();
        
        //평가개시되었다면 저장 불가능
        if( SaveEmpEstm.getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
        	try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsg(ctx, "평가 개시가 시작되었기 때문에 저장 불가능합니다.");
        		
        		return;
        	}
        }
        
        SaveData sd = new SaveData();
        sd.deleteTelmp(sEvalYear, sEvalNum);	//발매실적삭제
        sd.deleteComm (sEvalYear, sEvalNum);	//투표소삭제
        
        //발매실적 Insert
        sDsName = "dsDwTelmp";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + sd.insertTelmp(record); 
	        }	

	        nSaveCount += sd.InsertAllTelmp(conn, ctx, ds);
        }
        
        //투표소 Insert
        sDsName = "dsDwComm";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + sd.insertComm(record); 
	        }	  
	        nSaveCount = nSaveCount + sd.InsertAllComm(conn, ctx, ds);
        }
        
        sd.updateByPass(sEvalYear, sEvalNum); //년간 발매실적 제외자는  TOTAL_CNT = 99999, WK_DAY_CNT = 99999로 설정
        sd.updateTeamAvg(sEvalYear, sEvalNum); //년간 발매실적 제외자의 평균 발매건수는 팀평균 적용
        
        Util.setReturnParam(ctx, "RESULT", "0");
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }  
}