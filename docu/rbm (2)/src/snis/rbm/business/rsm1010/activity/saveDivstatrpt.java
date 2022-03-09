/*================================================================================
 * 시스템			: LIS FILE 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1010.activity.saveDivstatrpt.java
 * 파일설명		: SP_IMPORT_LIS_FILE 저장
 * 작성자			: 서주원
 * 버전			: 1.0.0
 * 생성일자		: 2017-11-02
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1010.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

public class saveDivstatrpt extends SnisActivity {
	public saveDivstatrpt(){}
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
	String MEETCD = "";
	String YEAR = "";
    public String runActivity(PosContext ctx)
    {	   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	        
    	saveDivstatrpt(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveDivstatrpt(PosContext ctx) 
    {
    	Connection conn = null;
        CallableStatement proc =  null;
        String sRaceDay = (String) ctx.get("RACE_DAY");
        System.out.println("sRaceDay==="+sRaceDay);
		try { 
			int i = 1;
	
			conn = this.getDao("rbmdao").getDBConnection();
			proc = conn.prepareCall("{call SP_IMPORT_LIS_FILE(?)}");
			proc.setString(i++, sRaceDay);
			proc.execute();
			
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally{
			//try {if(conn!=null) conn.close();} catch(Exception e) {} 
		}    	
    }
    
}
