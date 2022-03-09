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

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;
import snis.rbm.common.util.EncryptSHA256;

public class SaveUser extends SnisActivity {
	
	public SaveUser(){
		
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

    	String strErr = saveState(ctx);
    	if( strErr.equals("F") ){    	
    
    		return PosBizControlConstants.FAILURE;
    	}else{
    		return PosBizControlConstants.SUCCESS;
    	
    	}


    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected String saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	String sJobGbn	 = (String) ctx.get("JOB_GBN");
    	if(sJobGbn ==null) sJobGbn = ""; //01: 입력/수정,  02: 암호수정
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        int nUserCnt	 = 0;
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
	        
        sDsName = "dsTempUser";        
        
        if(sJobGbn.equals("01")){
        	
        	sDsName = "dsTempUser";
        	 
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        //Des des = new Des();	--구 암호화 방식 20141216
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD")));
			        	
			        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
			        		nUserCnt = getUserCount((String)record.getAttribute("USER_ID"));
			        		
			        		
			        		if(nUserCnt>0){
				        		try { 
			            			throw new Exception(); 
				            	} catch(Exception e) {       		
				            		this.rollbackTransaction("tx_rbm");
				            		Util.setSvcMsg(ctx, "입력하신 사용자 ID 가 이미 존재합니다.!");
				            		return "F";
				            		
				            	}
			        			
			        		}
			        		
			        		
			        	}
			        	
			        	
				        nTempCnt = saveUserMana(record);
	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteUserMana(record);
			        	
			        	if(nDeleteCount >0){
			        		
			        		deleteUserAuth(record, curDateTime);
			        		deleteAdminInfo(record);
			        		deleteAuthDept(record);
			        	}
		            }		        
		        }	        
	        }
	        
        	sDsName = "dsEstmUser";
       	 
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        //Des des = new Des();
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD")));
			        	
			        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
			        		nUserCnt = getUserCount((String)record.getAttribute("USER_ID"));
			        		
			        		
			        		if(nUserCnt>0){
				        		try { 
			            			throw new Exception(); 
				            	} catch(Exception e) {       		
				            		this.rollbackTransaction("tx_rbm");
				            		Util.setSvcMsg(ctx, "입력하신 사용자 ID 가 이미 존재합니다.!");
				            		return "F";
				            		
				            	}
			        			
			        		}
			        		
			        		
			        	}
			        	
			        	
				        nTempCnt = saveUserMana(record);
	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteUserMana(record);
			        	
			        	if(nDeleteCount >0){
			        		
			        		deleteUserAuth(record, curDateTime);
			        		deleteAdminInfo(record);
			        		deleteAuthDept(record);
			        	}
		            }		        
		        }	        
	        } 
	        
	        
        }else if(sJobGbn.equals("02")){
        	
        	sDsName = "dsUserPw";
        	
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        //Des des = new Des();
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD")));
			        	
			        	
				        nTempCnt = updateUserPW(record);
	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
      
		        }	        
	        }
        } else if(sJobGbn.equals("03")){    	
			sDsName = "dsUserPw";			
			if ( ctx.get(sDsName) != null ) {
			    ds   		 = (PosDataset) ctx.get(sDsName);
			    nSize 		 = ds.getRecordCount();
			    
			    //Des des = new Des();			    
			    for ( int i = 0; i < nSize; i++ ) {
			        PosRecord record = ds.getRecord(i);			
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	
			        	record.setAttribute("PSWD_OLD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD_OLD")));
			        	record.setAttribute("PSWD_NEW", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD_NEW")));
			        	
				        nTempCnt = changeUserPW(record);
				        
			        	if (nTempCnt == 0) {
				        	try { 
			        			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setReturnParam(ctx, "RESULT", "F");
			            		Util.setSvcMsg(ctx, "기존 암호가 일치하지 않습니다.");
			            		
			            		return "";
			            	}
			        	}
			        }
		        }	        
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
        Util.setReturnParam(ctx, "RESULT", "S");
        
        return "";
    }
 


    
    
    /**
     * <p> 사용자  입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveUserMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("GBN"));	  //구분코드
        param.setValueParamter(i++, record.getAttribute("USER_ID"));	  //사용자ID
        param.setValueParamter(i++, record.getAttribute("PSWD"));	  //비밀번호
        param.setValueParamter(i++, record.getAttribute("USER_NM"));	  //사용자명
        param.setValueParamter(i++, record.getAttribute("EMAIL"));	  //이메일

        param.setValueParamter(i++, record.getAttribute("HP_NO"));	  //이동전화번호
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));	  //전화번호
        param.setValueParamter(i++, record.getAttribute("FLOC"));	  //직위
        param.setValueParamter(i++, record.getAttribute("FGRADE"));	  //직급
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	  //지점코드
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));	  //대분류코드

        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	  //부서코드
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));	  //팀코드
        param.setValueParamter(i++, record.getAttribute("TEAM_DETL_CD"));	  //팀상세코드
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));	  //대분류명
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));	  //부서명
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));	  //팀명
        param.setValueParamter(i++, record.getAttribute("RETIRE_GBN"));	  //퇴사여부
        

        param.setValueParamter(i++, record.getAttribute("INST_ID"));	  //작성자
        param.setValueParamter(i++, record.getAttribute("UPDT_ID"));	  //수정자

        
        
        int dmlcount = this.getDao("rbmdao").update("rsy3050_m01", param);
        
        return dmlcount;
    }

    
    /**
     * <p> 암호  수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateUserPW(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PSWD"));		//암호
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GBN" ));			//권한구분
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));		//사용자ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_u01", param);
        return dmlcount;
    }
    

    
    /**
     * <p> 암호  수정2 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int changeUserPW(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PSWD_NEW"));		//암호
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));		//사용자ID
        param.setWhereClauseParameter(i++, record.getAttribute("PSWD_OLD" ));		//사용자ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_u02", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 사용자  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteUserMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GBN") ); 		//구분
        param.setValueParamter(i++, record.getAttribute("USER_ID") ); 	//사용자 ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 사용자권한 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteUserAuth(PosRecord record, String curDtm) 
    {

    	
    	// 1)해당 사용자의 권한을 모두 종료
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, curDtm);						//작성자
        param.setValueParamter(i++, SESSION_USER_ID);					//작성자
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));	//사용자ID
        param.setWhereClauseParameter(i++, curDtm);
        
        int dmlcount = this.getDao("rbmdao").update("rsy3021_u01", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> 특별관리자 권한 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAdminInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") ); 	//사용자 ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_d03", param);

        return dmlcount;
    }
    /**
     * <p> 권한적용부서 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAuthDept(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") ); 	//사용자 ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_d04", param);

        return dmlcount;
    }
    
    
	/**
     * <p> 기등록 사용자 건수 조회   </p>
     * @param   PosGenericDao dao			DB Connect 정보
     *          String        sUserId		사용자 Id
     * @return  nRtnCnt	int			        사용자 조회  건수 
     * @throws  none
     */
    protected int getUserCount(String sUserId)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sUserId); //사용자 ID
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsy3050_s03", param);        

        int nRtnCnt    = keyRecord.count();
        
        return nRtnCnt;
    }
}
