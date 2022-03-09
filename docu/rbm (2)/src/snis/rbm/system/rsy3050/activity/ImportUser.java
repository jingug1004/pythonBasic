/*
 * ================================================================================
 * 시스템 : 사용자 관리 
 * 소스파일 이름 : snis.rbm.system.rsy3050.activity.SaveUser.java 
 * 파일설명 : 사용자 관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011-10-08
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.system.rsy3050.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

public class ImportUser extends SnisActivity {
	
	public ImportUser(){
		
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

		System.out.println("called runActivity");
    	String strErr = ImportUser();
    	
    	if( strErr.equals("F") ){   
    		return PosBizControlConstants.FAILURE;
    	}else{
    		return PosBizControlConstants.SUCCESS;    	
    	}
    }

    /**
    * <p> 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected String ImportUser() 
    {

    	Connection conn = null;
        CallableStatement proc =  null;
        
		try { 	
			System.out.println("called ImportUser");
			conn = this.getDao("rbmdao").getDBConnection();
			proc = conn.prepareCall("{call SP_IMPORT_USER_RBM()}");
			proc.execute();
			
		} catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("error:");
		} finally{
			//try {if(conn!=null) conn.close();} catch(Exception e) {} 
		}    
		
        return "T";
    }
 

}
