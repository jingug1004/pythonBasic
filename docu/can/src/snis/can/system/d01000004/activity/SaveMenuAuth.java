/*================================================================================
 * 시스템			: 메뉴권한 관리
 * 소스파일 이름	: snis.can.system.d01010030.activity.MenuManager.java
 * 파일설명		: 메뉴권한 관리
 * 작성자			: 박연자
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-06
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.system.d01000004.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.*;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
import snis.can.common.util.UtilDb;


/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 메뉴권한을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 박연자
* @version 1.0
*/
public class SaveMenuAuth extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveMenuAuth()
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
        
        sDsName = "dsUpAuthListValue";
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
    	String UserID	 = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsUpAuthListValue";
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        logger.logInfo("========== NSIZE " + nSize + "=======================");
	        	
					// 기존 자료 백업 20141004 jdlee
        	backupAuthUser();
					
					// 삭제
            deleteAuthUser(ctx);

             nTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            UserID = (String)record.getAttribute("USER_ID");
	            
	            if(UserID == null) UserID="";
	            
	        	if(	   record.getAttribute("INPT_YN")   != null 
	        		&& record.getAttribute("SRCH_YN") != null){
	        		if((((String)record.getAttribute("INPT_YN")).equals("1")) 
	    	        		|| (((String)record.getAttribute("SRCH_YN")).equals("1")) ){
	    	        	nTempCnt = insertAuthMenu(record, ctx);	        			
	        		}
	        	}  

		        nSaveCount = nSaveCount + nTempCnt;
		        
		        //insertAuthAplyDept(record);				// 사용자별 : 현재 사용자의 부서정보 업데이트
    			//deleteAuthAplyDept(UserID, curDateTime);	// 사용자별 : 메뉴에 대한 권한이 하나도 없는 경우 권한이 없는 경우 권한설정부서정보를 삭제
    			
		        insertAuthAplyDept(record, ctx);		// 사용자별 : 현재 사용자의 부서정보 업데이트
    			deleteAuthAplyDept(record, ctx);		// 사용자별 : 메뉴에 대한 권한이 하나도 없는 경우 권한이 없는 경우 권한설정부서정보를 삭제
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> 메뉴권한 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAuthMenu(PosRecord record, PosContext ctx) 
    {
    	logger.logInfo("menuAuthInsert============================ ");
    	logger.logInfo("USER_ID==================================>"+(String)ctx.get("USER_ID"));
    	logger.logInfo("MN_ID==================================>"+record.getAttribute("MN_ID"));
    	logger.logInfo("SRCH_YN==================================>"+record.getAttribute("SRCH_YN"));
    	logger.logInfo("INPT_YN==================================>"+record.getAttribute("INPT_YN"));
    	    	
    	PosParameter param = new PosParameter();       					
        int i = 0;
    	
        param.setValueParamter(i++, (String)ctx.get("USER_ID"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MN_ID")));
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("SRCH_YN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INPT_YN")));
           	    	     
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("candao").update("tbda_mn_auth_ia001", param);
        
        return dmlcount;
    }


   /**
     * <p> 메뉴권한 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAuthUser(PosContext ctx)
    {
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,(String)ctx.get("USER_ID"));

        int dmlcount = this.getDao("candao").update("tbda_mn_auth_da001", param);
        return dmlcount;
    }    
    
        /**
     * <p> 백업 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int backupAuthUser()
    {
			PosParameter param = new PosParameter();
			int dmlcount = 0;
			
			PosRowSet prs = this.getDao("candao").find("tbda_mn_auth_backup_cnt", param);
		
			// 당일 저장된 백업이 없다면 insert
			if (prs.count() > 0 && Integer.parseInt(prs.getAllRow()[0].getAttribute("cnt").toString()) == 0) {
				param = null;
				dmlcount = this.getDao("candao").update("tbda_auth_backup", param);
			}
      return dmlcount;
	}
    
    /**
     * <p> 권한적용부서 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    //protected int insertAuthAplyDept(PosRecord record)
    protected int insertAuthAplyDept(PosRecord record, PosContext ctx)
    {
    	PosParameter param = new PosParameter();    
    	PosRowSet    rowset;
    	int dmlcount = 0;
        int i = 0;

    	param = new PosParameter();
    	i = 0;
    	              	    	     
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, (String)ctx.get("USER_ID"));
        //param.setValueParamter(i++, record.getAttribute("USER_ID"));	//사용자ID
     
        dmlcount = this.getDao("candao").update("d01000011_m01", param);
       
        return dmlcount;
    }
    
    /**
     * <p> 사용자 권한적용부서 삭제  </p>
     * @param   userID	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    //protected int deleteAuthAplyDept(String userID, String curDtm)
    protected int deleteAuthAplyDept(PosRecord record, PosContext ctx)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, (String)ctx.get("USER_ID"));
        //param.setWhereClauseParameter(i++, UserID);
        //param.setWhereClauseParameter(i++, curDtm); 

        int dmlcount = this.getDao("candao").update("d01000011_d01", param);
        return dmlcount;
    }
}
