package snis.boa.system.e01010230.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveDeptMove extends SnisActivity {
	public SaveDeptMove(){
		
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsDeptMove";
        
        String sAuthChk = "";
        String sDelAuthChk = "";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            
	            sAuthChk = (String)record.getAttribute("AUTH_CHK");
	            if(sAuthChk == null) sAuthChk ="";

	            sDelAuthChk = (String)record.getAttribute("DELAUTH_CHK");
	            if(sDelAuthChk == null) sDelAuthChk ="";
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
				      || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if(sAuthChk.equals("1")){ //권한유지
		        			
		        		 	nTempCnt = updateAuthAplyDept(record);
		
		        		}else if(sDelAuthChk.equals("1")){	//권한삭제 
	             		 	deleteMnAuth(record);
	             		 	nTempCnt = deleteAuthAplyDept(record);
		        		}
		        }
	        	nSaveCount = nSaveCount + nTempCnt;
	        	continue;
	        }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 권한적용부서 수정 !!!</p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAuthAplyDept(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("CUR_DEPT_CD"));	//이동후부서
        param.setValueParamter(i++, record.getAttribute("CUR_TEAM_CD"));	//이동후팀
        param.setValueParamter(i++, SESSION_USER_ID);						//작성자
        param.setValueParamter(i++, record.getAttribute("USER_ID" ));		//적용 ID
        int dmlcount = this.getDao("boadao").update("tbea_auth_aply_dept_ua001", param);
        return dmlcount;
    }

    
   
    /**
     * <p> 메뉴권한관리 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteMnAuth(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID );	
        param.setValueParamter(i++, record.getAttribute("USER_ID") );	
        int dmlcount = this.getDao("boadao").update("tbea_auth_aply_dept_da001", param);
        return dmlcount;
    }
    
    
    /**
     * <p>  권한적용부서 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAuthAplyDept(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") );	
        int dmlcount = this.getDao("boadao").update("tbea_auth_aply_dept_da002", param);
        return dmlcount;
    }
    
}
