/*================================================================================
 * 시스템			: 평가대생선정
 * 소스파일 이름	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * 파일설명		: 평가배분표 저장
 * 작성자			: 배태일
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1050.activity;

import java.sql.Connection;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	        Connection conn = null;        
	        
	        conn = getDao("rbmdao").getDBConnection();
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//평가년도
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//평가회차
	        
	        SaveEVMistr SaveEVMistr = new SaveEVMistr();

	        if( !SaveEVMistr.getUpdateYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
	    			throw new Exception(); 
	        	} catch(Exception e) {       		
	        		this.rollbackTransaction("tx_rbm");
	        		Util.setSvcMsg(ctx, "부서별대상자를 확정한 부서가 존재하므로 저장하실 수 없습니다.");
	        		
	        		return;
	        	}
	        }
	        
	        sDsName = "dsEmp";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        //nTempCnt = updateEmp(record);
				    //nSaveCount = nSaveCount + nTempCnt;
		        }
		        nSaveCount += UpdateAllEmp(conn, ctx, ds);
	        }
	        
	        
	        updateAprvInit(sEvalYear, sEvalNum, "2");	//근무태도평가 승인코드 초기화
	        updateAprvCompleted(sEvalYear, sEvalNum, "2", "2");	//근무태도평가 받지 않는 부서의 승인코드를 승인완료(003)으로 변경
	        
	        updateAprvInit(sEvalYear, sEvalNum, "3");	//서비스평가 승인코드 초기화
	        updateAprvCompleted(sEvalYear, sEvalNum, "3", "3"); //근무태도평가 받지 않는 부서의 승인코드를 승인완료(003)으로 변경
	        
	        Util.setSaveCount(ctx, nSaveCount);
	    }

	    /**
	     * <p> 평가대상 저장 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEmp(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));     // 1. 평가부서 
	        param.setValueParamter(i++, record.getAttribute("ESTM_ESC_GBN"));  // 2. 평가대상제외자구분
	        param.setValueParamter(i++, record.getAttribute("APT_DT"));      // 3. 발령일자
	        param.setValueParamter(i++, record.getAttribute("AVG_IN")); 	 // 4. 평균점적용
	        param.setValueParamter(i++, SESSION_USER_ID);                    // 5. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));  // 6. 평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));   // 7. 평가회차
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));     // 8. 사원번호
	        param.setWhereClauseParameter(i++, record.getAttribute("WRK_GBN"));    // 9. 대상자구분(001:일반직 002:파견직:

	        int dmlcount = this.getDao("rbmdao").update("rev1050_u01", param);
	        return dmlcount;
	    }

	    /**
	     * <p> 평가대상 일괄 저장 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int UpdateAllEmp(Connection conn, PosContext ctx, PosDataset ds)  
	    {    	
    		int dmlcount = 0;
	        PreparedStatement stmt = null;
	        String sqlStr = Util.getQuery(ctx, "rev1050_u01");
	        PosParameter param = new PosParameter();   
	
	        try {
	        	int iparam = 1;
				stmt = conn.prepareStatement(sqlStr);
				for ( int i = 0; i < ds.getRecordCount(); i++ ) {
				    PosRecord record = ds.getRecord(i);
					stmt.clearParameters();	
					iparam = 1;				
					stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());     // 1. 평가부서 
			        stmt.setString(iparam++,  record.getAttribute("ESTM_ESC_GBN").toString());  // 2. 평가대상제외자구분
			        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());      // 3. 발령일자
			        stmt.setString(iparam++,  record.getAttribute("AVG_IN").toString()); 	 // 4. 평균점적용
			        stmt.setString(iparam++,  SESSION_USER_ID);                    // 5. 사용자 ID      
			  
			        stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());  // 6. 평가년도
			        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());   // 7. 평가회차
			        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());     // 8. 사원번호
			        stmt.setString(iparam++,  record.getAttribute("WRK_GBN").toString());    // 9. 대상자구분(001:일반직 002:파견직:
			        
		    		stmt.addBatch();
				}
				stmt.executeBatch();
				dmlcount = stmt.getUpdateCount();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	        return dmlcount;
	    }

	    /**
	     * <p> 평가승인코드 초기화 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateAprvInit(String sEvalYear, String sEvalNum, String sAprvSeq)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);   // 1. 사용자 ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);  // 2. 평가년도
	        param.setWhereClauseParameter(i++, sEvalNum);   // 3. 평가회차
	        param.setWhereClauseParameter(i++, sAprvSeq);   // 4. 승인번호

	        int dmlcount = this.getDao("rbmdao").update("rev1050_u03", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가/서비스평가 받지 않는 부서의 승인코드를 승인완료(003)으로 변경 </p>
	     * @ sItemCd     2:근무태도평가   3:서비스평가
	     */
	    protected int updateAprvCompleted(String sEvalYear, String sEvalNum, String sItemCd, String sAprvSeq)
	    {	
	    	PosParameter param  = new PosParameter();

	    	int i = 0;
	    	int j = 0;
	    	int nUpdateCnt = 0;
	        
	        param.setWhereClauseParameter(i++, sEvalYear);  // 1. 평가년도
	        param.setWhereClauseParameter(i++, sEvalNum);   // 2. 평가회차
	        param.setWhereClauseParameter(i++, sEvalYear);  // 3. 평가년도
	        param.setWhereClauseParameter(i++, sEvalNum);   // 4. 평가회차
	        param.setWhereClauseParameter(i++, sItemCd);    // 5. 평가구분(1:업무수행평가 2:근무태도평가 3:서비스평가)
	        
	    	PosRowSet pAprvDept = this.getDao("rbmdao").find("rev1050_s06", param);
	    	
	    	if( pAprvDept.count() == 0) return 0;
	    	
	    	PosRow pr[] = pAprvDept.getAllRow();
	    	
	    	for(int nRow = 0; nRow <  pAprvDept.count(); nRow++) {
	    		PosParameter param2 = new PosParameter();
	    		j = 0;
	    		param2.setValueParamter(j++, SESSION_USER_ID);
	    		
	    		j = 0;
	    		param2.setWhereClauseParameter(j++, sEvalYear);
	    		param2.setWhereClauseParameter(j++, sEvalNum);
	    		param2.setWhereClauseParameter(j++, String.valueOf(pr[nRow].getAttribute("DEPT_CD")));
	    		param2.setWhereClauseParameter(j++, sAprvSeq);
	    		
	    		nUpdateCnt += this.getDao("rbmdao").update("rev1050_u04", param2);
	        }
	    	
	    	return nUpdateCnt;
	    }
}