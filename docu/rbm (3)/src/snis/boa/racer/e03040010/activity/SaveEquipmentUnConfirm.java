/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03040010.activity.SaveEquipmentUnConfirm.java
 * 파일설명		: 모터/보트배정 확정취소
 * 작성자			: 강민수
 * 버전			: 1.0.0
 * 생성일자		: 2008-05-14
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 부상선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SaveEquipmentUnConfirm extends SnisActivity
{    
	private String sStndYear = "";
	private String sMbrCd    = "";
	private String sTms      = "";
	
    public SaveEquipmentUnConfirm()
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

    	updateRaceTms(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> 모터보트 배정상태 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx) 
    {
        // 모터보트 배정 확정  저장
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
    	
        // 현재상태 체크
        String sStatCd = "";
        SaveProcess sp = new SaveProcess();        
        sStatCd = sp.getRaceTmsStatCd(sStndYear, sMbrCd, sTms);

		if(!"020".equals(sStatCd)){
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_boa");
        		Util.setSvcMsg(ctx, "장비추첨 완료상태가 아닙니다!!!");
        		return 0;        		
        	}			
		}
		        

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, null);
        param.setValueParamter(i++, "010");
        param.setValueParamter(i++, SESSION_USER_ID );
        
        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
	        
        int effectedRow = 0;
        effectedRow = this.getDao("boadao").update("tbeb_race_tms_ub002", param);
        
        // 작업로그 작성
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DUTY_CD"  , "003");
        hmProcess.put("WORK_CD"  , "020");
        hmProcess.put("PROG_STAT", "002");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        //SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
        
        return effectedRow;
    }
}


