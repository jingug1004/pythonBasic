/*
 * ================================================================================
 * 시스템 : 지불정지 및 해지 저장
 * 파일 이름 : snis.rbm.business.rsm6020.activity.SaveStopPay.java 
 * 파일설명 :  VW_KDW_TF_REC_LOCK 당일 데이터 인서트 => TBRD_STOPPAY_CNCL_CNTNT
 * 작성자 :  서주원
 * 버전 : 1.0.0 생성일자 : 2017- 11 - 17
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm6020.activity;

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


public class SaveStoppayInsert extends SnisActivity {
	
	public SaveStoppayInsert(){}

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
    protected int saveState(PosContext ctx) 
    {
    	PosParameter param = new PosParameter();   
        
    	String sRaceYy    = (String) ctx.get("RACE_YY");
    	String sRaceMm    = (String) ctx.get("RACE_MM");
    	String sRaceDay   = (String) ctx.get("RACE_DAY");
    	
        int i = 0;
        int dmlcount=0;
        
        param.setValueParamter(i++, SESSION_USER_ID); // 등록자
        param.setValueParamter(i++, sRaceDay);
        param.setValueParamter(i++, SESSION_USER_ID); // 등록자
        
        dmlcount = this.getDao("rbmdao").update("rsm6020_i02", param);
        
        return dmlcount;
    }
    
}
