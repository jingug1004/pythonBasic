/*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.boa.common.util.SearchCode.java
 * 파일설명		: 코드조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0 
*/
public class SnisActivity extends PosActivity
{
    public String SESSION_USER_ID     = "";
    public String SESSION_USER_NM     = "";
    public String SESSION_DEPT_CD     = "";
    public String SESSION_PSWD        = "";
    public String SESSION_DUTY_GRP_CD = "";
    public String SESSION_FLOC_CD     = "";
    public String SESSION_STAT        = "";
    public String SESSION_TEL_NO      = "";
    public String SESSION_HP_NO       = "";
    public String SESSION_STR_DT      = "";
    public String SESSION_END_DT      = "";
	
	public SnisActivity()
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
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String setUserInfo(PosContext ctx)
    {
    	SESSION_USER_ID     = Util.trim(ctx.getSessionUserData("USER_ID    ".trim()));
    	SESSION_USER_NM     = Util.trim(ctx.getSessionUserData("USER_NM    ".trim()));
    	SESSION_DEPT_CD     = Util.trim(ctx.getSessionUserData("DEPT_CD    ".trim()));
    	SESSION_PSWD        = Util.trim(ctx.getSessionUserData("PSWD       ".trim()));
    	SESSION_DUTY_GRP_CD = Util.trim(ctx.getSessionUserData("DUTY_GRP_CD".trim()));
    	SESSION_FLOC_CD     = Util.trim(ctx.getSessionUserData("FLOC_CD    ".trim()));
    	SESSION_STAT        = Util.trim(ctx.getSessionUserData("STAT       ".trim()));
    	SESSION_TEL_NO      = Util.trim(ctx.getSessionUserData("TEL_NO     ".trim()));
    	SESSION_HP_NO       = Util.trim(ctx.getSessionUserData("HP_NO      ".trim()));
    	SESSION_STR_DT      = Util.trim(ctx.getSessionUserData("STR_DT     ".trim()));
    	SESSION_END_DT      = Util.trim(ctx.getSessionUserData("END_DT     ".trim()));
    	
        if ( SESSION_USER_ID.equals("") ) {
    		if ( ctx.get("gdsUser") != null ) {
    	    	PosDataset dsUser = (PosDataset) ctx.get("gdsUser");
    	    	
    	    	PosRecord record = dsUser.getRecord(0);
    	        logger.logInfo(record);

    	        SESSION_USER_ID     = Util.trim(record.getAttribute("USER_ID    ".trim()));
    	        SESSION_USER_NM     = Util.trim(record.getAttribute("USER_NM    ".trim()));
    	        SESSION_DEPT_CD     = Util.trim(record.getAttribute("DEPT_CD    ".trim()));
    	        SESSION_PSWD        = Util.trim(record.getAttribute("PSWD       ".trim()));
    	        SESSION_DUTY_GRP_CD = Util.trim(record.getAttribute("DUTY_GRP_CD".trim()));
    	        SESSION_FLOC_CD     = Util.trim(record.getAttribute("FLOC_CD    ".trim()));
    	        SESSION_STAT        = Util.trim(record.getAttribute("STAT       ".trim()));
    	        SESSION_TEL_NO      = Util.trim(record.getAttribute("TEL_NO     ".trim()));
    	        SESSION_HP_NO       = Util.trim(record.getAttribute("HP_NO      ".trim()));
    	        SESSION_STR_DT      = Util.trim(record.getAttribute("STR_DT     ".trim()));
    	        SESSION_END_DT      = Util.trim(record.getAttribute("END_DT     ".trim()));
    		}
    	}
        
        logger.logInfo("SESSION_USER_ID     : " + SESSION_USER_ID    );
        logger.logInfo("SESSION_USER_NM     : " + SESSION_USER_NM    );
        logger.logInfo("SESSION_DEPT_CD     : " + SESSION_DEPT_CD    );
        logger.logInfo("SESSION_PSWD        : " + SESSION_PSWD       );
        logger.logInfo("SESSION_DUTY_GRP_CD : " + SESSION_DUTY_GRP_CD);
        logger.logInfo("SESSION_FLOC_CD     : " + SESSION_FLOC_CD    );
        logger.logInfo("SESSION_STAT        : " + SESSION_STAT       );
        logger.logInfo("SESSION_TEL_NO      : " + SESSION_TEL_NO     );
        logger.logInfo("SESSION_HP_NO       : " + SESSION_HP_NO      );
        logger.logInfo("SESSION_STR_DT      : " + SESSION_STR_DT     );
        logger.logInfo("SESSION_END_DT      : " + SESSION_END_DT     );

    	if ( SESSION_USER_ID.equals("") ) {
            return PosBizControlConstants.FAILURE;
    	}

        return PosBizControlConstants.SUCCESS;
    }
}

