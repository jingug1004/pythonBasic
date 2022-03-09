/*================================================================================
 * 시스템			: 메뉴권한 관리
 * 소스파일 이름	: snis.boa.system.e01010030.activity.MenuManager.java
 * 파일설명		: 메뉴권한 관리
 * 작성자			: 김영철
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-06
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.system.e01010040.activity;

import snis.boa.common.util.DesCipher;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.common.util.UtilDb;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 메뉴권한을 저장(입력/수정)및 삭제하는 클래스이다.
* 변경내역(2014.6.22) : 신재선
* 권한관리내역의 히스토리 관리를 위해 여러 사용자의 권한 설정에서 동시에 1명씩 권한 설정으로 변경
* @auther 신재선
* @version 1.1
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
        
        
        sDsName = "dsAuthListValue";
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
System.out.println("saveStat::::");
 
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	
    	String sDsName   = "";
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // 1) 사용자 추가 및 삭제
        sDsName = "dsUserListValue";
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
            
            nTempCnt = 0;
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		        	deleteUser(record);
		        }
		        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	if ( record.getAttribute("GBN").equals("001") ) continue;
		        	if ( (nTempCnt = updateUser(record)) != 1 ) {
		        		nTempCnt = insertUser(record);
		        	}
		        	nSaveCount = nSaveCount + nTempCnt;
		        }
	        }
        }

        // 2) 사용자 권한 관리        
        String sUserId = (String)ctx.get("USER_ID");
        sDsName = "dsAuthListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	         
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
	                 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	            	updateAuthMenu(record, curDateTime, ctx);
	            	nSaveCount ++;
	            }
	            
	        } // end for
	         // 권한 Insert 시 권한적용부서정보(TBEA_AUTH_APLY_DEPT) 테이블을 현행화 한다.
            syncAuthAplyDept(sUserId);
        }


        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    protected void syncAuthAplyDept(String sUserId){
    	PosParameter param = new PosParameter();       					
        int i = 0;
        param.setValueParamter(i++, sUserId);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbea_auth_aply_dept_ma001", param);
    }


    /**
     * <p> 사용자 정보 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        DesCipher bean  = new DesCipher();
        if ( record.getAttribute("PSWD") != null && !record.getAttribute("PSWD").equals("") ) {
        	record.setAttribute("PSWD", bean.Encode((String) record.getAttribute("PSWD")));
        }

        param.setValueParamter(i++, record.getAttribute("PSWD"   ));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("EMAIL"  ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO" ));
        param.setValueParamter(i++, record.getAttribute("FLOC"   ));
        param.setValueParamter(i++, record.getAttribute("FGRADE" ));
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));

        i =0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));

        int dmlcount = this.getDao("boadao").update("tbea_user_ua001", param);

        return dmlcount;
    }    

    /**
     * <p> 사용자 추가 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID"));
        

        DesCipher bean  = new DesCipher();
        if ( record.getAttribute("PSWD") != null && !record.getAttribute("PSWD").equals("") ) {
        	record.setAttribute("PSWD", bean.Encode((String) record.getAttribute("PSWD")));
        }
        
        param.setValueParamter(i++, record.getAttribute("PSWD"   ));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("EMAIL"  ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO" ));
        param.setValueParamter(i++, record.getAttribute("FLOC"   ));
        param.setValueParamter(i++, record.getAttribute("FGRADE" ));
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));

        int dmlcount = this.getDao("boadao").update("tbea_user_ia001", param);
        return dmlcount;
    }    

    /**
     * <p> 사용자 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));

        int dmlcount = this.getDao("boadao").update("tbea_user_da001", param);
        
        param = new PosParameter();
        i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));
        
        this.getDao("boadao").update("tbea_mn_auth_da001", param);
        return dmlcount;
    }  
    

    /**
    * <p> 메뉴권한 입력 </p>
    * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
    * @return  dmlcount	int		update 레코드 개수
    * @throws  none
    */
   protected int updateAuthMenu(PosRecord record, String curDtm, PosContext ctx) 
   {
		String sSrchYn = "N";
		String sInptYn = "N";
		
		// 1)기존 자료 존재여부
		// 2)자료가 없으면 
		//   2-1) 권한이 있는 경우 신규로 입력(시작일자 : 오늘, 종료일자 : 무한대)
		// 3)자료가 있으면 
		//   3-1) 권한이 있는 경우 기존자료 수정(종료일자:어제), 신규자료 입력(시작일자:오늘, 종료일자:무한대)
		//   3-2) 권한이 없는 경우 수정처리(종료일자:어제) : 시작일자가 오늘보다 작은 경우에만
   	
		// 0) 권한부여 또는 삭제 여부 체크
		boolean bAuthSet   = true;		// 권한이 한개라도 있는 지 여부(없으면 false)
		boolean bAuthExist = false;
		int     dmlcount = 0;
		int     dDelcount = 0;
		int		dPercount = 0;
		   
		if(record.getAttribute("SRCH_YN") != null) {
			if (Double.valueOf(record.getAttribute("SRCH_YN").toString()) == 1) {
				sSrchYn = "Y";
			}
		}
		if(record.getAttribute("INPT_YN") != null) {
			if (Double.valueOf(record.getAttribute("INPT_YN").toString()) == 1) {
				sInptYn = "Y";
			}
		}
		if (sSrchYn.equals("N") && sInptYn.equals("N")) {
		   	bAuthSet = false;
		}
       

		// 1) 기존에 자료가 존재하는 여부 조회
		PosParameter param = new PosParameter();       	
		PosParameter perparam = new PosParameter();
		PosRowSet rowset;
		int i = 0;
		
		String sUserId  = (String)record.getAttribute("USER_ID");
		String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
		String sMnId    = Util.trim(record.getAttribute("MN_ID"));
   	
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sMnId);
		param.setWhereClauseParameter(i++, sStrDtTm);

		rowset = this.getDao("boadao").find("e01010040_s02", param);
		if (rowset.hasNext()) bAuthExist = true;
		
		System.out.println("bAuthSet="+bAuthSet);
		System.out.println("bAuthExist="+bAuthExist);
		
		// 2)자료가 없으며 권한이 있는 경우 신규로 입력(시작일자 : 오늘, 종료일자 : 무한대)
		if (bAuthExist == false && bAuthSet == true) {
			System.out.println("case 1:");
			param = new PosParameter();
			i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sMnId);	        
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInptYn);           	    	     
	        param.setValueParamter(i++, SESSION_USER_ID);

	        dmlcount += this.getDao("boadao").update("e01010040_i01", param);
			
	    // 3)자료가 있으면
		} else if (bAuthExist == true) {
			System.out.println("case 2:");
	    	// 3-1) 권한이 있는 경우 기존권한을 종료(종료일자:어제) : 시작일자가 오늘보다 작은 경우에만
			
			param = new PosParameter();
			i = 0;
			param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, sUserId);
	        param.setWhereClauseParameter(i++, sMnId);	        
	        param.setWhereClauseParameter(i++, sStrDtTm);	        
			
	        dDelcount += this.getDao("boadao").update("e01010040_u01", param);	        
	        
	        // 3-2) 추가설정 권한이 있는 경우 신규자료 입력(시작일자:오늘, 종료일자:무한대)
	        if (dDelcount == 0) {	// 시작일시분이 현재인 경우(분까지 같은 경우)
				System.out.println("case 3:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);           	    	     
		        param.setValueParamter(i++, SESSION_USER_ID);
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);	        

		        dmlcount += this.getDao("boadao").update("e01010040_u02", param);
		        
			} else {
		        if (bAuthSet == true) {		
						System.out.println("case 4:");
						param = new PosParameter();
						i = 0;
				        param.setValueParamter(i++, sUserId);
				        param.setValueParamter(i++, sMnId);	        
				        param.setValueParamter(i++, sSrchYn);
				        param.setValueParamter(i++, sInptYn);           	    	     
				        param.setValueParamter(i++, SESSION_USER_ID);
		
				        dmlcount += this.getDao("boadao").update("e01010040_i01", param);
				}				
			}
	        
	        if (sSrchYn.equals("N")){
	        	System.out.println("case 4-3:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sUserId);
		        param.setValueParamter(i++, SESSION_USER_ID);
		        param.setValueParamter(i++, sUserId);
		        param.setValueParamter(i++, sMnId);	            

		        dmlcount += this.getDao("boadao").update("e01010040_u03", param);
		        
		        System.out.println("case 4-4:");
	 	        perparam = new PosParameter();
	 	        i = 0;
	 	        perparam.setValueParamter(i++, sMnId); 
	 	        perparam.setValueParamter(i++, SESSION_USER_ID);
	 	        perparam.setValueParamter(i++, sUserId);
	 	        
	 	        dPercount += this.getDao("boadao").update("e01010040_i02", perparam);
	        }
		}		
       
       return dmlcount;
   }

}
