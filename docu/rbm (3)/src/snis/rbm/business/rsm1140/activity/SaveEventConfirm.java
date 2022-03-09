/*
 * ================================================================================
 * 시스템 : 관리 
 * 소스파일 이름 : snis.rbm.business.rem4040.activity.SavePeriod.java 
 * 파일설명 : 수동전송주기 관리 
 * 작성자 : 신재선
 * 버전 : 1.0.0 
 * 생성일자 : 2012.12.29
 * 최종수정일자 :
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */

package snis.rbm.business.rsm1140.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SaveEventConfirm extends SnisActivity {

	public SaveEventConfirm(){		
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsListDetl";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);	        
	            nTempCnt = selectGrnCdPswd(record);
	            
	            if (nTempCnt == 0) {
	            	// 기존에 존재하는 자료가 없음(배정등록을 다시 하거나 수정된 경우임)
	    			throw new RuntimeException("입력한 그린카드 암호가 일치하지 않습니다.\n확인후 다시 입력하세요!");
	            }
		        nSaveCount = nSaveCount + updateConfirm(record);
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }
    

    protected int selectGrnCdPswd(PosRecord record)
    {
        PosRowSet    rowset;
        int          nCnt = 0;
        
		int i = 0;
		PosParameter paramId = new PosParameter();
		paramId.setWhereClauseParameter(i++, record.getAttribute("EVENT_DT"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("MIN_SLIP_SEQ"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("CARD_NO"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("CARD_SEQ"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("CARD_PWD"));
		
		rowset = this.getDao("rbmdao").find("rsm1140_s03", paramId);
		if (rowset.hasNext()) {	// 동명이인이 존재하는 경우 사번이 빠른 직원의 사번으로 셋팅
			PosRow rowUserId = rowset.next();
			nCnt = Integer.parseInt(rowUserId.getAttribute("CNT").toString());
		}
           	
    	return nCnt;
    }
    
    /**
     * <p> 당첨확인 처리 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateConfirm(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        
    	int i = 0;
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고(메모)
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("EVENT_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("MIN_SLIP_SEQ"));
        param.setWhereClauseParameter(i++, record.getAttribute("CARD_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("CARD_SEQ"));

        int dmlcount = this.getDao("rbmdao").update("rsm1140_u01", param);
        return dmlcount;
    }    
}
