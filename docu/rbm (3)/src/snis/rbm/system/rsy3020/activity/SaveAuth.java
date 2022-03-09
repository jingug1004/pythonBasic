/*================================================================================
 * 시스템			: 메뉴권한 관리
 * 소스파일 이름	: snis.rbm.system.rsy0040.activity.SaveAuth.java
 * 파일설명		: 메뉴권한 관리
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2011-07-31
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.system.rsy3020.activity;

import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
* 사용자별 메뉴권한을 관리하는 클래스이다.
* @auther 이영상
* @version 1.0
*/
public class SaveAuth extends SnisActivity
{    
    public SaveAuth()
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

    	/*
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        	        
        sDsName = "dsAuthListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	        }
        }
        */
	        
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
    	String sDsName   = "";
    	String sDsName2  = "";
    	String UserID	 = "";
    	
    	PosDataset ds;
    	PosDataset ds2;
        int nSize        = 0;
        int nSize2       = 0;
                
        Connection conn = null; 
        conn = getDao("rbmdao").getDBConnection();
        
        sDsName 	= "dsUserListValue";	//사용자리스트
        sDsName2 	= "dsAuthListValue";	//메뉴권한리스트
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        if ( ctx.get(sDsName) != null) {    	
        	ds2			= (PosDataset) ctx.get(sDsName);
        	nSize2 		= ds2.getRecordCount();
        	
        	for ( int j = 0; j < nSize2; j++ ) {		//사용자리스트만큼 반복
        		PosRecord urecord = ds2.getRecord(j);
        		
        		if (((String)urecord.getAttribute("CHK")).equals("1")) {	// 사용자리스트에서  체크된 사용자인지 체크
        			UserID = (String)urecord.getAttribute("USER_ID");
        			
        			if(UserID == null) UserID="";
        			
        			if ( ctx.get(sDsName2) != null  && !UserID.equals("")) {
    	    	        ds   		 = (PosDataset) ctx.get(sDsName2);
    	    	        nSize 		 = ds.getRecordCount();
    	    	        
    	    	        for ( int i = 0; i < nSize; i++ ) {
    	    	           PosRecord record = ds.getRecord(i);    	    	           	    	    	            
    	    	           
    	    	           nSaveCount += updateAuthMenu(UserID, curDateTime, record);
    	    	           
    	    	       		
    	    	        } // end for
    	            }	  // end if
        			
        			insertAuthAplyDept(urecord);		// 사용자별 : 현재 사용자의 부서정보 업데이트
        			deleteAuthAplyDept(UserID, curDateTime);			// 사용자별 : 메뉴에 대한 권한이 하나도 없는 경우 권한이 없는 경우 권한설정부서정보를 삭제
        		}
	        	
        	}
        	
        	//프로그램 권한이 하나도 없는 사용자에 대해서 메뉴권한 일괄 정리
        	PosParameter param = new PosParameter();	        	
			int i = 0;
			param.setValueParamter(i++, curDateTime);
			param.setValueParamter(i++, curDateTime);
	        i = 0;
	        param.setWhereClauseParameter(i++, curDateTime);
        	this.getDao("rbmdao").update("rsy3020_d02", param);
	        	
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
    * <p> 메뉴권한 입력 </p>
    * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
    * @return  dmlcount	int		update 레코드 개수
    * @throws  none
    */
   protected int updateAuthMenu(String UserId, String curDtm, PosRecord record) 
   {
		// 1)기존 자료 존재여부
		// 2)자료가 없으면 
		//   2-1) 권한이 있는 경우 신규로 입력(시작일자 : 오늘, 종료일자 : 무한대)
		// 3)자료가 있으면 
		//   3-1) 권한이 있는 경우 기존자료 수정(종료일자:어제), 신규자료 입력(시작일자:오늘, 종료일자:무한대)
		//   3-2) 권한이 없는 경우 수정처리(종료일자:어제) : 시작일자가 오늘보다 작은 경우에만
   	
		String sSrchYn = "N";
		String sInptYn = "N";
		String sAprvYn = "N";
		
		// 0) 권한부여 또는 삭제 여부 체크
		boolean bAuthSet   = true;		// 권한이 한개라도 있는 지 여부(없으면 false)
		boolean bAuthExist = false;
		int     dmlcount = 0;
		int     dDelcount = 0;
		   
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
		if(record.getAttribute("APRO_YN") != null) {
			if (Double.valueOf(record.getAttribute("APRO_YN").toString()) == 1) {
				sAprvYn = "Y";
			}
		}
		if (sSrchYn.equals("N") && sInptYn.equals("N") && sAprvYn.equals("N")) {
		   	bAuthSet = false;
		}
		String sRmk = (String)record.getAttribute("RMK");
       
		// 1) 기존에 자료가 존재하는 여부 조회
		PosParameter param = new PosParameter();       					
		PosRowSet rowset;
		int i = 0;
		
		String sUserId  = UserId;
		String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
		String sMnId    = Util.trim(record.getAttribute("MN_ID"));
		String sPersYn   = (String)record.getAttribute("PERSONAL_YN");
 		String sPerMnlIp = (String)record.getAttribute("PERSONAL_MN_IP");
   	
		System.out.println("UserId="+UserId);
		System.out.println("sMnId ="+sMnId);
		System.out.println("curDtm="+curDtm);
		
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sMnId);
		param.setWhereClauseParameter(i++, curDtm);		// 현재 시점의 권한 여부를 체크

		rowset = this.getDao("rbmdao").find("rsy3020_s04", param);
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
	        param.setValueParamter(i++, curDtm);  
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInptYn);  
	        param.setValueParamter(i++, sAprvYn);           	    	     
	        param.setValueParamter(i++, sRmk);           	    	     
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sPersYn);        
	        param.setValueParamter(i++, sPerMnlIp);

	        dmlcount += this.getDao("rbmdao").update("rsy3020_i02", param);
			
	    // 3)자료가 있으면
		} else if (bAuthExist == true) {
			System.out.println("case 2:");
	    	// 3-1) 권한이 있는 경우 기존권한을 종료(종료일자:어제) : 시작일자가 오늘보다 작은 경우에만
			if (sStrDtTm.equals(curDtm)) {	// 동일시간에 입력한 자료인 경우 수정
        		
				System.out.println("case 4-1:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);
		        param.setValueParamter(i++, sAprvYn);           	    	     
		        param.setValueParamter(i++, sRmk);           	    	     
		        param.setValueParamter(i++, SESSION_USER_ID);
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);	

		        dmlcount += this.getDao("rbmdao").update("rsy3020_u02", param);
		        
		        return dmlcount;
			} else {
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, curDtm);  
				param.setValueParamter(i++, SESSION_USER_ID);
		        
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);       
				
		        dDelcount += this.getDao("rbmdao").update("rsy3020_u01", param);	
			}
	        
	        // 3-2) 추가설정 권한이 있는 경우 신규자료 입력(시작일자:오늘, 종료일자:무한대)

	        if (bAuthSet == true) {		
	        	
	        	System.out.println("case 4:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sUserId);
		        param.setValueParamter(i++, sMnId);	    
		        param.setValueParamter(i++, curDtm);      
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);  
		        param.setValueParamter(i++, sAprvYn);
		        param.setValueParamter(i++, sRmk); 
		        param.setValueParamter(i++, SESSION_USER_ID);
		        param.setValueParamter(i++, sPersYn);        
		        param.setValueParamter(i++, sPerMnlIp);

		        dmlcount += this.getDao("rbmdao").update("rsy3020_i02", param);	        	
	        }
		}		
       
       return dmlcount;
   }


    /**
     * <p> 권한적용부서 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAuthAplyDept(PosRecord record)
    {
    	PosParameter param = new PosParameter();    
    	PosRowSet    rowset;
    	int dmlcount = 0;
        int i = 0;

    	param = new PosParameter();
    	i = 0;
    	
  
        param.setValueParamter(i++, "001"); 							//AUTH_GBN : 001: 일반, 002: 특별관리자               	    	     
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//사용자ID

     
        dmlcount = this.getDao("rbmdao").update("rsy3020_m02", param);
        
        
        return dmlcount;
    }
    
    /**
     * <p> 사용자 권한적용부서 삭제  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAuthAplyDept(String UserID, String curDtm)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, UserID);
        param.setWhereClauseParameter(i++, "001"); 
        param.setWhereClauseParameter(i++, curDtm); 

        int dmlcount = this.getDao("rbmdao").update("rsy3020_d03", param);
        return dmlcount;
    }
      
}
