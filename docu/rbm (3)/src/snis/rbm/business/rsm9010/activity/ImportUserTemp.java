/*
 * ================================================================================
 * 시스템 : 발매원 승인처리
 * 파일 이름 : snis.rbm.business.rsm9010.activity.SaveTeller.java 
 * 파일설명 :  
 * 작성자 : 
 * 버전 : 1.0.0 생성일자 : 2011- 12 - 17
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm9010.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;


public class ImportUserTemp extends SnisActivity {
	
	public ImportUserTemp(){}

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
    	CallableStatement proc = null; 
    	Connection conn = null; 
    	try {  
    		conn = this.getDao("rbmdao").getDBConnection(); 
    		proc = conn.prepareCall("{call SP_IMPORT_USER_TEMP }"); 
    		proc.setQueryTimeout(60);   // 오래 걸리는 경우 타임아웃설정(기본:30초)

    		proc.execute(); 
    	} catch (Exception e) { 
    		e.printStackTrace();  
    	} finally{ 
    		//try {if(conn!=null) conn.close();} catch(Exception e) {}  
    	} 
    	
    	Util.setSaveCount  (ctx, nSaveCount);
    }
    
}
