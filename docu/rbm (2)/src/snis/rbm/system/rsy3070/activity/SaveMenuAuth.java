package snis.rbm.system.rsy3070.activity;

import java.util.ArrayList;
import java.util.List;

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

public class SaveMenuAuth extends SnisActivity {
	public SaveMenuAuth(){
		
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
    	String sDsName   = "";
    	String sDsName2  = "";
    	String sMenuId	 = "";
    	String sUserId	 = "";
    	
    	PosDataset ds;
    	PosDataset ds2;
        int nSize        = 0;
        int nSize2       = 0;
        int nTempCnt     = 0;
                
        String sPerYn 	 = "";
        String sSrchYn 	 = "";
        String sLvl	  	 = "";
        String sPerMnIp  = "";
        
        List userlist = new ArrayList();
        
        sDsName2 	= "dsMenuGrpValue";		//메뉴권한리스트
        sDsName 	= "dsUserListValue";	//사용자리스트

        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        if ( ctx.get(sDsName2) != null) {
        	//System.out.println("---1---");        	
        	ds2			= (PosDataset) ctx.get(sDsName2);
        	nSize2 		= ds2.getRecordCount();
        	
        	for ( int j = 0; j < nSize2; j++ ) {		//메뉴 리스트만큼 반복
        		//System.out.println("---2---");        	
        		PosRecord mrecord = ds2.getRecord(j);
        		
        		sMenuId = (String)mrecord.getAttribute("MN_ID");
    			
    			if(sMenuId == null) sMenuId="";
    			
    			if ( ctx.get(sDsName2) != null  && !sMenuId.equals("")) {
	    	        ds    = (PosDataset) ctx.get(sDsName);
	    	        nSize = ds.getRecordCount();
	    	        
	    	        for ( int i = 0; i < nSize; i++ ) {
	    	           PosRecord record = ds.getRecord(i); 
	    	           nSaveCount += updateAuthMenu(sMenuId, curDateTime, record);
	    	           
	    	           sUserId = (String)record.getAttribute("USER_ID");
	    	           if (userlist.indexOf(sUserId) < 0) userlist.add(sUserId);
	    	        } 
	            }	  	
        	}       	
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }

     /**
     * <p> 메뉴권한 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateAuthMenu(String MenuID, String curDtm, PosRecord record) 
    {
 		// 1)기존 자료 존재여부
 		// 2)자료가 없으면 
 		//   2-1) 권한이 있는 경우 신규로 입력(시작일자 : 오늘, 종료일자 : 무한대)
 		// 3)자료가 있으면 
 		//   3-1) 권한이 있는 경우 기존자료 수정(종료일자:어제), 신규자료 입력(시작일자:오늘, 종료일자:무한대)
 		//   3-2) 권한이 없는 경우 수정처리(종료일자:어제) : 시작일자가 오늘보다 작은 경우에만
    	
 		String sPerYn = "N";
 		String sPerAuthChk = "N";
 		
 		String sPerMnIp = (String)record.getAttribute("PERSONAL_MN_IP");
 		String sSrchYn 	 = (String)record.getAttribute("SRCH_YN");
 		
 		// 0) 권한부여 또는 삭제 여부 체크
 		boolean bAuthSet   = true;		// 권한이 한개라도 있는 지 여부(없으면 false)
 		boolean bAuthExist = false;
 		int     dmlcount = 0;
 		int     dDelcount = 0;
 		int		dPercount = 0;
 		   
 		if(record.getAttribute("PERSONAL_YN") != null) {
 			if (Double.valueOf(record.getAttribute("PERSONAL_YN").toString()) == 1) {
 				sPerYn = "Y";
 				sPerAuthChk = "Y";
 			}
 		}
 		if (sPerYn.equals("N")) {
 			sPerAuthChk = "N";
 		   	bAuthSet = false;
 		}
        
 		// 1) 기존에 자료가 존재하는 여부 조회 		
 		PosParameter param = new PosParameter();
 		PosParameter perparam = new PosParameter();  
 		PosRowSet rowset;
 		int i = 0;
 		
 		String sUserId  = Util.trim(record.getAttribute("USER_ID"));
 		String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
 		String sMnId    = MenuID;
 		 		
 		param.setWhereClauseParameter(i++, sUserId);
 		param.setWhereClauseParameter(i++, sMnId);
 		param.setWhereClauseParameter(i++, curDtm);		// 현재 시점의 권한 여부를 체크
 		
 		rowset = this.getDao("rbmdao").find("rsy3020_s04", param);
 		if (rowset.hasNext()) bAuthExist = true;
 		
 		//System.out.println("bAuthSet="+bAuthSet);
 		//System.out.println("bAuthExist="+bAuthExist);
 		
 		// 2)자료가 없으며 권한이 있는 경우 신규로 입력(시작일자 : 오늘, 종료일자 : 무한대)
 		if (bAuthExist == false && bAuthSet == true) {
 			System.out.println("case 1:");
 			param = new PosParameter();
 			i = 0;
 	        param.setValueParamter(i++, sUserId);
 	        param.setValueParamter(i++, sMnId);  
 	        param.setValueParamter(i++, curDtm);        	    	     
 	        param.setValueParamter(i++, sPerYn);
	        param.setValueParamter(i++, SESSION_USER_ID);  
	        param.setValueParamter(i++, sPerMnIp); 
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sPerAuthChk); 
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);

 	        dmlcount += this.getDao("rbmdao").update("rsy3070_i02", param);
 	        
 	        System.out.println("case 2:");
 	        perparam = new PosParameter();
 	        i = 0;
 	        perparam.setValueParamter(i++, sMnId); 
 	        perparam.setValueParamter(i++, SESSION_USER_ID);
 	        perparam.setValueParamter(i++, sPerAuthChk); 
 	        perparam.setValueParamter(i++, curDtm);
 	        perparam.setValueParamter(i++, sUserId);
 	       perparam.setValueParamter(i++, sPerMnIp); 
 	        
 	        dPercount += this.getDao("rbmdao").update("rsy3070_i03", perparam);
 	    // 3)자료가 있으면
 		} else if (bAuthExist == true) {
 			System.out.println("case 3:");
 	    	// 3-1) 권한이 있는 경우 기존권한을 종료(종료일자:어제) : 시작일자가 오늘보다 작은 경우에만
 			param = new PosParameter();
			i = 0;
	        param.setValueParamter(i++, sPerYn);           	    	     
	        param.setValueParamter(i++, sPerMnIp);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sPerAuthChk);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        i = 0;
	        param.setWhereClauseParameter(i++, sUserId);
	        param.setWhereClauseParameter(i++, sMnId);	        
	        param.setWhereClauseParameter(i++, sStrDtTm);	

	        dmlcount += this.getDao("rbmdao").update("rsy3070_u02", param);
	        
	        System.out.println("case 4:");
	        perparam = new PosParameter();
	        i = 0;
	        perparam.setValueParamter(i++, sMnId); 
	        perparam.setValueParamter(i++, SESSION_USER_ID);
	        perparam.setValueParamter(i++, sPerAuthChk); 
	        perparam.setValueParamter(i++, sUserId);
	        perparam.setValueParamter(i++, sPerMnIp); 
 	        
 	        dPercount += this.getDao("rbmdao").update("rsy3070_i03", perparam);
 		}		
        
        return dmlcount;
    }       
}
