/*================================================================================
 * 시스템			: User 관리
 * 소스파일 이름	: snis.boa.system.e01010030.activity.UserManager.java
 * 파일설명		: User 관리
 * 작성자			: 김영철
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-05
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.system.e01010020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김영철
* @version 1.0
*/
public class SaveUser extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveUser()
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

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        
        sDsName = "dsUserListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
        int nTempCnt     = 0;

        sDsName = "dsUserListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	 nTempCnt = updateUser(record);
		        }
		        
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = insertUser(record);
		        }

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		        	nTempCnt = deleteUser(record);
		        }
		        
		        nSaveCount 		= 	nSaveCount 		+ nTempCnt;
		        nDeleteCount 	= 	nDeleteCount 	+ nTempCnt;
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 게시글 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateUser(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_NM"      ));
        param.setValueParamter(i++, record.getAttribute("PSWD"      ));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"      ));
        param.setValueParamter(i++, record.getAttribute("DUTY_GRP_CD"      ));
        param.setValueParamter(i++, record.getAttribute("FLOC_CD"      ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO"      ));
        param.setValueParamter(i++, record.getAttribute("HP_NO"      ));
        param.setValueParamter(i++, record.getAttribute("STR_DT"      ));
        param.setValueParamter(i++, record.getAttribute("END_DT"      ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));

        int dmlcount = this.getDao("boadao").update("tbea_user_ua001", param);
        
        return dmlcount;
    }

    /**
     * <p> 게시글 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertUser(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID"));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("PSWD"));
        param.setValueParamter(i++, record.getAttribute("DUTY_GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("FLOC_CD"));
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));
        param.setValueParamter(i++, record.getAttribute("HP_NO"));
        param.setValueParamter(i++, record.getAttribute("STR_DT"));
        param.setValueParamter(i++, record.getAttribute("END_DT"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbea_user_ia001", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> 게시글 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteUser(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));
        
        int dmlcount  = this.getDao("boadao").update("tbea_user_da001", param);
        	dmlcount += this.getDao("boadao").update("tbea_mn_auth_da001", param);
        
        return dmlcount;
    }    
}
