package snis.rbm.system.rsy3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;

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
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
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
		        			if(((String)record.getAttribute("AUTH_GBN")).equals("001")){ //일반사용자
		        				
		             		 	deleteMnAuth(record, curDateTime);
		             		 	
		             		 	nTempCnt = deleteAuthAplyDept(record);
		        			}else if(((String)record.getAttribute("AUTH_GBN")).equals("002")){ //특별관리자
		        				
		        				deleteAdminInfo(record);
		             		 	
		             		 	nTempCnt = deleteAuthAplyDept(record);
		
		             		 	
		        			}
		        	
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
     * <p> 권한적용부서 수정</p>
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
        param.setValueParamter(i++, SESSION_USER_ID);							//작성자
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));		//사용자ID
        param.setWhereClauseParameter(i++, record.getAttribute("AUTH_GBN" ));		//권한구분

        int dmlcount = this.getDao("rbmdao").update("rsy3040_u01", param);
        return dmlcount;
    }

    
   
    /**
     * <p> 메뉴권한관리 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteMnAuth(PosRecord record, String curDtm) 
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
        param.setValueParamter(i++, record.getAttribute("AUTH_GBN") );
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy3040_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> 특별관리자 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAdminInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") );
        
        int dmlcount = this.getDao("rbmdao").update("rsy3040_d03", param);

        return dmlcount;
    }
}
