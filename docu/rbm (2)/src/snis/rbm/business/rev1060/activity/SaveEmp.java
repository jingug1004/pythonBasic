/*================================================================================
 * 시스템			: 부서별담당자선정
 * 소스파일 이름	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * 파일설명		: 부서별담당자 수정
 * 작성자			: 배태일
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1060.activity;

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
import snis.rbm.business.rev1050.activity.SaveAprvMana;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

public class SaveEmp extends SnisActivity {
		public SaveEmp(){}
		
		
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
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");

	        SaveEVMistr SaveEVMistr = new SaveEVMistr();

	        if( !SaveEVMistr.getUpdateYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
	    			throw new Exception(); 
	        	} catch(Exception e) {       		
	        		this.rollbackTransaction("tx_rbm");
	        		Util.setSvcMsg(ctx, "부서별대상자를 확정한 부서가 존재하므로 변경하실 수 없습니다.");
	        		
	        		return;
	        	}
	        }
	        
	        //권한 회수(담당자)
	        SaveAprvMana SaveAprvMana = new SaveAprvMana();
	        SaveAprvMana.reEval(sEvalYear, sEvalNum, SESSION_USER_ID, 1);
	        
	        sDsName = "dsEmpDept";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
			        	updateAprv    (record);	//승인
			        	updateAprvCfm (record);	//승인
			        	updateEmpEmpty(record);	

			        	nTempCnt = updateEmp(record);
			        	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        } 
		        }	        
	        }
	        
	        //권한 부여(담당자) :: 담당자 변경시 기존 잘못 부여된 담당자의 메뉴 권한 삭제하지 않음(오류!!)
	        System.out.println("****************************");
	        SaveAprvMana.reEval(sEvalYear, sEvalNum, SESSION_USER_ID, 2);
	        
	        Util.setSaveCount  (ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> 부서별담당자 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmp(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);                             // 1. 사용자 ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 2. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 3. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));      // 4. 사원번호
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));      // 5. 사원번호

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u02", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서별 승인자 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateAprv(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));        		  // 1. 승인요청자 사번
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. 사용자 ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. 사원번호
	        param.setWhereClauseParameter(i++, "1");       							  // 6. 승인차수

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 대상 확정자 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateAprvCfm(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));        		  // 1. 승인요청자 사번
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. 사용자 ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. 사원번호
	        param.setWhereClauseParameter(i++, "4");       							  // 6. 승인차수

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 부서별담당자 부서 전체 해제 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmpEmpty(PosRecord record)
	    {
    		PosParameter param = new PosParameter();
	    	
	    	int i = 0;

	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 1. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 2. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));      // 3. 사원번호

	        int nEptyCnt = this.getDao("rbmdao").update("rev1060_u01", param);
	        return nEptyCnt;
	    }
	    
	    /**
	     * <p> 승인 개수 조회  </p>
	     * @param   
	     *          String        sEvalYear  	
	     *          String		  sEvalNum
	     * @return  nRtnKey	int			        sAttFileKey데 대한 첨부파일 개수
	     * @throws  none
	     */
		public int getAprvCnt(String sEvalYear, String sEvalNum)
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;

	        param.setWhereClauseParameter(i++, sEvalYear);	//년도
	        param.setWhereClauseParameter(i++, sEvalNum);	//회차
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1060_s03", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        String sRtnKey = String.valueOf(pr[0].getAttribute("CNT"));
	        int nRtnKey    = Integer.parseInt(sRtnKey);
	        
	        return nRtnKey;
	    }

}
