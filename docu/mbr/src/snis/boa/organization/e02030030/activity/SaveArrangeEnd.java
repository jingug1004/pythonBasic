/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030030.activity.SaveArrangeEnd.java
 * 파일설명		: 주선상태등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 주선상태를 저장(입력/수정)하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveArrangeEnd extends SnisActivity
{    

    public SaveArrangeEnd()
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
        updateRaceTms(ctx);
    }

    /**
     * <p> 주선상태 및 회차별 작업 이력 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("ARRANGE_CMPL_YN" ));
        param.setValueParamter(i++, ctx.get("RACE_TMS_STAT_CD"));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub002", param);
        
        if ( ctx.get("ARRANGE_CMPL_YN") != null ) {
            // 작업로그 작성
            //==============================================
            HashMap hmProcess = new HashMap();
            hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
            hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
            hmProcess.put("TMS"      , ctx.get("TMS"      ));
            hmProcess.put("DUTY_CD"  , "002");
            hmProcess.put("WORK_CD"  , "010");
            if ( ctx.get("ARRANGE_CMPL_YN").equals("Y") ) {
                hmProcess.put("PROG_STAT", "001");
	        } else if ( ctx.get("ARRANGE_CMPL_YN").equals("N") ) {
                hmProcess.put("PROG_STAT", "002");
	        }
            hmProcess.put("USER_ID",   SESSION_USER_ID);

            SaveProcess sp = new SaveProcess();
            sp.saveProcess(hmProcess);
            //==============================================
        }
        
        return dmlcount;
    }
}
