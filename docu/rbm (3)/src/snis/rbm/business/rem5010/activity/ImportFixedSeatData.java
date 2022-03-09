/*================================================================================
 * 시스템			: 지정좌석실 관리
 * 소스파일 이름	: snis.rbm.business.rem5010.activity.ImportFixedSeatData.java
 * 파일설명		: 연계DB로부터 입장인원 가져오기
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2014-03-15
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem5010.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;

public class ImportFixedSeatData extends SnisActivity {
	
	public ImportFixedSeatData(){		
		
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
    	int nDeleteCount = 0; 
    	
        Connection conn = null;
        CallableStatement proc = null;

        try {

        	conn = this.getDao("rbmdao").getDBConnection();
        	proc = conn.prepareCall("{call SP_IMPORT_FIXEDSEAT_TRADE()}");
        	proc.setQueryTimeout(60); // 오래 걸리는 경우 타임아웃설정(기본:30초)         

        	proc.execute();

        } catch (Exception e) {
        	e.printStackTrace();
        } finally{
        	//try {if(conn!=null) conn.close();} catch(Exception e) {}
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
}
