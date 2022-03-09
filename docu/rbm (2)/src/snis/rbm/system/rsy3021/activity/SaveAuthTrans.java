package snis.rbm.system.rsy3021.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;

public class SaveAuthTrans extends SnisActivity {

		public SaveAuthTrans(){
			
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

	        sDsName = "dsUser";
	        
	        String sAuthGbn = (String) ctx.get("AUTH_GBN");
	        if(sAuthGbn == null) sAuthGbn="";
	        
	        String sAuthUserId = (String) ctx.get("AUTH_USER_ID");
	        if(sAuthUserId == null) sAuthUserId="";
	        
	        String  curDateTime = "";
	        if(sAuthGbn.equals("001")){ 	//일반사용자
		        UtilDb udb = new UtilDb();
		        curDateTime = udb.getCurTime();
	        }
	        
	        String sChk ="";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if(record.getAttribute("CHK").equals("1")){
		            	record.setAttribute("AUTH_USER_ID", sAuthUserId);
		            	
		            	if(sAuthGbn.equals("001")){ 	//일반사용자
		            		
		            		record.setAttribute("AUTH_GBN", "001");
		            		nTempCnt = saveAuthTrans(record, curDateTime);
		            		
		            	} else if(sAuthGbn.equals("002")) { //특별관리자:권한부여자
		            		
		            		record.setAttribute("AUTH_GBN", "002");
		            		nTempCnt = saveAdminAuthTrans(record);
		            		
		            	}
		            	
		            	updateAuthAplyDept(record);
		           
			        	continue;

		            }

		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> 권한이양 입력/수정(일반사용자) </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int saveAuthTrans(PosRecord record, String curDtm) 
	    {
	    	/* 처리방법
	    	 * 1) 받는 사람의 권한을 모두 종료 처리
	    	 * 2) 부여자권한을 조회하여 받을 사람들에게 신규로 입력 : insert .. select
	    	 */

	    	
	    	// 1)해당 사용자의 권한을 모두 종료
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, curDtm);						//작성자
	        param.setValueParamter(i++, SESSION_USER_ID);					//작성자
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));	//사용자ID
	        param.setWhereClauseParameter(i++, curDtm);
	        
	        int dmlcount = this.getDao("rbmdao").update("rsy3021_u01", param);
	        
	        // 2)부여자 권한을 조회하여 받을 사람으로 신규 입력
	        param = new PosParameter();   
	        
	        i = 0;
	        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//사용자ID
	        param.setValueParamter(i++, curDtm);						//시작일시
	        param.setValueParamter(i++, SESSION_USER_ID);					//입력자 사번
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("AUTH_USER_ID"));	//권한보유자 사용자ID
	        
	        dmlcount += this.getDao("rbmdao").update("rsy3021_i01", param);
	        
	        return dmlcount;
	    }

	    
	    /**
	     * <p> 권한이양 입력/수정(일반사용자) </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int saveAdminAuthTrans(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//사용자ID
	        param.setValueParamter(i++, SESSION_USER_ID);					//작성자
	        param.setValueParamter(i++, SESSION_USER_ID);					//작성자
	        param.setValueParamter(i++, record.getAttribute("AUTH_USER_ID"));	//사용자ID
	        
	     
	        
	        int dmlcount = this.getDao("rbmdao").update("rsy3021_m02", param);
	        
	        return dmlcount;
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
	        
	        param.setValueParamter(i++, record.getAttribute("AUTH_GBN"));	//권한구분
	        param.setValueParamter(i++, SESSION_USER_ID);					//작성자
	        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//사용자ID

	        int dmlcount = this.getDao("rbmdao").update("rsy3021_m03", param);
	        return dmlcount;
	    }

}
