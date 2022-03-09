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
package snis.can_boa.system.d05000004.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

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
    	
    	PosDataset ds;
        int nSize        = 0;

        sDsName = "dsAuthListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        logger.logInfo("========== NSIZE " + nSize + "=======================");
	         
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            //System.out.println(record.getAttribute("MN_ID"));
	            //System.out.println(record.getAttribute("INPT_YN"));
	            //System.out.println(record.getAttribute("SRCH_YN"));
	            //System.out.println(record.getAttribute("APRV_YN"));
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
	                 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	            	updateAuthMenu(record);
	            	nSaveCount ++;
	            }
	            
	            // 상위메뉴에 대한 권한설정은 하지 않는다. 조회시에 상위메뉴을 조회하도록 수정
	        } // end for	         
        }

        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
     /**
     * <p> 메뉴권한 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateAuthMenu(PosRecord record) 
    {
    	logger.logInfo("updateAuthMenu============================ ");
    	logger.logInfo("USER_ID==================================>"+record.getAttribute("USER_ID"));
    	logger.logInfo("MN_ID==================================>"+record.getAttribute("MN_ID"));
    	logger.logInfo("SRCH_YN==================================>"+record.getAttribute("SRCH_YN"));
    	logger.logInfo("INPT_YN==================================>"+record.getAttribute("INPT_YN"));
    	String sSrchYn = "N";
    	String sInptYn = "N";
    	String sAprvYn = "N";

    	String sUserId  = (String)record.getAttribute("USER_ID");
    	String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
    	String sMnId    = Util.trim(record.getAttribute("MN_ID"));
    	
        // 권한 Insert 시 권한적용부서정보(TBEA_AUTH_APLY_DEPT) 테이블을 현행화 한다.
        syncAuthAplyDept(sUserId);	            

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
        if(record.getAttribute("APRV_YN") != null) {
    		if (Double.valueOf(record.getAttribute("APRV_YN").toString()) == 1) {
    			sAprvYn = "Y";
    		}
    	}
        if (sSrchYn.equals("N") && sInptYn.equals("N") && sAprvYn.equals("N")) {
        	bAuthSet = false;
        }
        

		// 1) 기존에 자료가 존재하는 여부 조회
    	PosParameter param = new PosParameter();       					
        PosRowSet rowset;
        
        int i = 0;		
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sMnId);
		param.setWhereClauseParameter(i++, sStrDtTm);

		rowset = this.getDao("candao").find("d05000004_s02", param);
		if (rowset.hasNext()) bAuthExist = true;
		
		//System.out.println("bAuthSet="+bAuthSet);
		//System.out.println("bAuthExist="+bAuthExist);
		
		
    	// 2)자료가 없으며 권한이 있는 경우 신규로 입력(시작일자 : 오늘, 종료일자 : 무한대)
		if (bAuthExist == false && bAuthSet == true) {
			//System.out.println("case 1:");
			param = new PosParameter();
			i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sMnId);	        
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInptYn);
	        param.setValueParamter(i++, sAprvYn);	           	    	     
	        param.setValueParamter(i++, SESSION_USER_ID);

	        dmlcount += this.getDao("candao").update("d05000004_i01", param);
			
    	// 3)자료가 있으면
		} else if (bAuthExist == true) {
			//System.out.println("case 2:");
	    	// 3-1) 권한이 있는 경우 기존권한을 종료(종료일자:어제) : 시작일자가 오늘보다 작은 경우에만
			param = new PosParameter();
			i = 0;
			param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, sUserId);
	        param.setWhereClauseParameter(i++, sMnId);	        
	        param.setWhereClauseParameter(i++, sStrDtTm);	        
			
	        dDelcount += this.getDao("candao").update("d05000004_u01", param);	        
	        
	        // 3-2) 추가설정 권한이 있는 경우 신규자료 입력(시작일자:오늘, 종료일자:무한대)
	        if (dDelcount == 0) {	// 시작일시분이 현재인 경우(분까지 같은 경우)
				//System.out.println("case 3:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);
		        param.setValueParamter(i++, sAprvYn);	           	    	     
		        param.setValueParamter(i++, SESSION_USER_ID);
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);	        

		        dmlcount += this.getDao("candao").update("d05000004_u02", param);
		        
			} else {
		        if (bAuthSet == true) {		
						//System.out.println("case 4:");
						param = new PosParameter();
						i = 0;
				        param.setValueParamter(i++, sUserId);
				        param.setValueParamter(i++, sMnId);	        
				        param.setValueParamter(i++, sSrchYn);
				        param.setValueParamter(i++, sInptYn);
				        param.setValueParamter(i++, sAprvYn);	           	    	     
				        param.setValueParamter(i++, SESSION_USER_ID);
		
				        dmlcount += this.getDao("candao").update("d05000004_i01", param);
				}				
			}
		}		
		
        return dmlcount;
    }

    protected void syncAuthAplyDept(String sUserId){
    	PosParameter param = new PosParameter();       					
        int i = 0;
        param.setValueParamter(i++, sUserId);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("candao").update("tbdm_auth_aply_dept_ma001", param);
    }
}
