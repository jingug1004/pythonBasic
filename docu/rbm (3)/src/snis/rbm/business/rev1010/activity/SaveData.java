/*================================================================================
 * 시스템			: 기초자료 생성
 * 소스파일 이름	: snis.rbm.business.rev1010.activity.SaveData.java
 * 파일설명		: 기초자료 생성
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveData extends SnisActivity {
	
	public SaveData(){}

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
        		Util.setSvcMsg(ctx, "부서별대상자를 확정한 부서가 존재하므로 기초자료를 생성하실 수 없습니다.");
        		
        		return;
        	}
        }
        
        deleteWk      (sEvalYear, sEvalNum);	//보직삭제
        deleteAtt     (sEvalYear, sEvalNum);	//근태삭제
        deleteDate    (sEvalYear, sEvalNum);	//발령일자삭제
        deleteDeptHead(sEvalYear, sEvalNum);	//부서장삭제
        deleteDept    (sEvalYear, sEvalNum);	//부서삭제
        deleteEmp     (sEvalYear, sEvalNum);	//사원삭제
        deleteComm    (sEvalYear, sEvalNum);	//투표소삭제
        deleteAprv    (sEvalYear, sEvalNum);	//승인 사업지원팀 삭제
        deleteTelmp   (sEvalYear, sEvalNum);    //발매실적 삭제
        deletePcDiv   (sEvalYear, sEvalNum);    //다스 투표소 매핑 삭제
        deleteAppr    (sEvalYear, sEvalNum);	//승인테이블 삭제
        
        //보직 Insert
        sDsName = "dsVenusWk";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);	        
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);
	            
//		        nSaveCount = nSaveCount + insertWk(record); 
	        }	        
	        nSaveCount += InsertAllWk(conn, ctx, ds);
	         
        }
        
        //근태 Insert
        sDsName = "dsVenusAtt";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertAtt(record);
	        }	        
	        nSaveCount += InsertAllAtt(conn, ctx, ds);
        }
        
        //발령일자 Insert
        sDsName = "dsVenusDate";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertDate(record);
	        }	       
	        nSaveCount += InsertAllDate(conn, ctx, ds); 
        }
        
      //부서장 Insert
        sDsName = "dsVenusDept";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertDeptHead(record);
	        }	
	        nSaveCount += InsertAllDeptHead(conn, ctx, ds);         
        }
        
      //부서 Insert
        sDsName = "dsDept";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertDept(record);
	        }	
	        nSaveCount += InsertAllDept(conn, ctx, ds);                
        }
        
        //사원 Insert
        sDsName = "dsRegEmp";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertEmp(record);
	        }	
	        nSaveCount += InsertAllEmp(conn, ctx, ds);                    
        }
        
      //투표소 Insert
        sDsName = "dsDwComm";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertComm(record);
	        }	   
	        nSaveCount += InsertAllComm(conn, ctx, ds);           
        }
        
        //발매실적 Insert
	    sDsName = "dsDwTelmp";
	    if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);
	
		        //nSaveCount = nSaveCount + insertTelmp(record); 
	        }	     
	        nSaveCount += InsertAllTelmp(conn, ctx, ds);        
	    }
        
	  // 다스 투표소 매핑  Insert
	    sDsName = "dsPcDiv";
	    if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);
	
		        //nSaveCount = nSaveCount + insertPcDiv(record); 
	        }	     
	        nSaveCount += InsertAllPcDiv(conn, ctx, ds);              
	    }
	    
	    updateByPass(sEvalYear, sEvalNum); 	//년간 발매실적 제외자는  TOTAL_CNT = 99999, WK_DAY_CNT = 99999로 설정
	    updateTeamAvg(sEvalYear, sEvalNum); //년간 발매실적 제외자의 평균 발매건수는 팀평균 적용
	    insertApprAll(sEvalYear, sEvalNum);
        insertAprv(sEvalYear, sEvalNum);
        
        Util.setReturnParam(ctx, "RESULT", "0");
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    /**
     * <p> 보직 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertWk(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//평가회차
        param.setValueParamter(i++, record.getAttribute("WK_JJOB"));	//보직코드
        param.setValueParamter(i++, record.getAttribute("CD_NM"));   	//보직명
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_i02", param);
        
        return dmlcount;
    }

    /**
     * <p> 보직 일괄 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllWk(Connection conn, PosContext ctx, PosDataset ds)
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i02");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//평가년도
				stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//평가회차
				stmt.setString(iparam++,  record.getAttribute("WK_JJOB").toString());	//보직코드
				stmt.setString(iparam++,  record.getAttribute("CD_NM").toString());   	//보직명
				stmt.setString(iparam++,  SESSION_USER_ID);					//사용자ID(작성자)
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
     * <p> 보직 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteWk(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> 근무실태 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAtt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//평가회차
        param.setValueParamter(i++, record.getAttribute("ATT_CD"));		//근태코드
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   	//사번
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   	//성명
        
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));   	//부서코드
        param.setValueParamter(i++, record.getAttribute("CNT"));   		//건수
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_i03", param);
        
        return dmlcount;
    }

    /**
     * <p> 근무실태 일괄 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllAtt(Connection conn, PosContext ctx, PosDataset ds)  
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i03");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//평가년도
				stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//평가회차
				stmt.setString(iparam++,  record.getAttribute("ATT_CD").toString());		//근태코드
				stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   	//사번
				stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   	//성명
		        
				stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());   	//부서코드
				stmt.setString(iparam++,  record.getAttribute("CNT").toString());   		//건수
				stmt.setString(iparam++,  SESSION_USER_ID);					//사용자ID(작성자)
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
     * <p> 근태 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAtt(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d03", param);

        return dmlcount;
    }

    /**
     * <p> 발령일자 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertDate(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//평가회차
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));		//사번
        param.setValueParamter(i++, record.getAttribute("APT_DT"));   	//발령일자
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));   	//부서코드
        
        param.setValueParamter(i++, record.getAttribute("SEQ"));   	
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));   	//부서명
        param.setValueParamter(i++, record.getAttribute("APT_RSN"));   	//발령사유
        param.setValueParamter(i++, record.getAttribute("WK_JOB_CD"));  //보직
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   	//성명

        int dmlcount = this.getDao("rbmdao").update("rev1010_i04", param);

        return dmlcount;
    }

    /**
     * <p> 발령일자 일괄입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllDate(Connection conn, PosContext ctx, PosDataset ds)  
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i04");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
			    stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//평가년도
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//평가회차
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());		//사번
		        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());   	//발령일자
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());   	//부서코드
		        
		        stmt.setString(iparam++,  record.getAttribute("SEQ").toString());   	
		        stmt.setString(iparam++,  record.getAttribute("DEPT_NM").toString());   	//부서명
		        stmt.setString(iparam++,  record.getAttribute("APT_RSN").toString());   	//발령사유
		        stmt.setString(iparam++,  record.getAttribute("WK_JOB_CD").toString());  //보직
		        stmt.setString(iparam++,  SESSION_USER_ID);					//사용자ID(작성자)
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   	//성명
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
     * <p> 근태 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteDate(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d04", param);

        return dmlcount;
    }

    /**
     * <p> 부서장 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertDeptHead(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//평가회차
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setValueParamter(i++, record.getAttribute("UPPER_DEPT_CD"));  //상위부서코드
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));   		//부서명
        
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   		//사번
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   		//성명
        param.setValueParamter(i++, record.getAttribute("HP_NO"));   		//이동전화번호
        param.setValueParamter(i++, record.getAttribute("JOB_RANK_NM"));   	//직급명
        param.setValueParamter(i++, record.getAttribute("LVL"));   			//레벨

        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i05", param);

		
        return dmlcount;
    }

    /**
     * <p> 부서장 일괄 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllDeptHead(Connection conn, PosContext ctx, PosDataset ds)
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i05");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());		//평가년도
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//평가회차
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("UPPER_DEPT_CD").toString());  //상위부서코드
		        stmt.setString(iparam++,  record.getAttribute("DEPT_NM").toString());   		//부서명
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   		//사번
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   		//성명
		        stmt.setString(iparam++,  record.getAttribute("HP_NO").toString());   		//이동전화번호
		        stmt.setString(iparam++,  record.getAttribute("JOB_RANK_NM").toString());   	//직급명
		        stmt.setString(iparam++,  record.getAttribute("LVL").toString());   			//레벨
		        stmt.setString(iparam++,  SESSION_USER_ID);					//사용자ID(작성자)

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
     * <p> 부서장 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteDeptHead(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d05", param);

        return dmlcount;
    }
    
    /**
     * <p> 부서 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertDept(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//평가회차
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));   		//부서명
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i06", param);

		
        return dmlcount;
    }

    /**
     * <p> 부서 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllDept(Connection conn, PosContext ctx, PosDataset ds) 
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i06");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//평가년도
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//평가회차
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("DEPT_NM").toString());   	//부서명
		        stmt.setString(iparam++,  SESSION_USER_ID);								//사용자ID(작성자)

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
     * <p> 부서 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteDept(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d06", param);

        return dmlcount;
    }

    /**
     * <p> 사원 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertEmp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//평가회차
        param.setValueParamter(i++, record.getAttribute("PERM_TEMP_GBN"));  //정규/비정규구분
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//부서코드
        param.setValueParamter(i++, record.getAttribute("WRK_GBN"));		//근무자구분
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   		//사번
        
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   		//성명
        param.setValueParamter(i++, record.getAttribute("RES_NO"));			//주민번호
        param.setValueParamter(i++, record.getAttribute("PERM_TEMP_GBN"));  //정규/비정규구분
        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));		//평가부서
        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));		//평가부서
        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));		//평가부서
        param.setValueParamter(i++, record.getAttribute("PWD_NO"));			//비밀번호
        param.setValueParamter(i++, record.getAttribute("WK_JOB_CD"));   	//보직코드
        
        param.setValueParamter(i++, record.getAttribute("APT_DT"));   		//발령일자
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_CD"));		//직급코드
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_NM"));		//직급명
        param.setValueParamter(i++, record.getAttribute("HP_NO"));			//이동전화번호
        param.setValueParamter(i++, record.getAttribute("ENTR_DT"));		//입사일자
        
        param.setValueParamter(i++, record.getAttribute("ESTM_ESC_GBN"));   	//평가대상제외자구분
        param.setValueParamter(i++, record.getAttribute("APT_DT"));   		//1차2차평가자구분
        param.setValueParamter(i++, record.getAttribute("MNG_GBN"));		//담당자구분
        param.setValueParamter(i++, record.getAttribute("MULT_ESTM_GRP"));	//다면평가자그룹
        param.setValueParamter(i++, record.getAttribute("CNL_GBN"));		//평가취소구분
        
        param.setValueParamter(i++, record.getAttribute("ESTM_WK_JOB"));   	//평가보직코드    
        param.setValueParamter(i++, record.getAttribute("ESTM_EMP"));  		//평가자사번
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//지점세부코드
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));			//투표소코드
        param.setValueParamter(i++, record.getAttribute("MNR_FST_SND"));	//근무태도1차2차평가자
        
        param.setValueParamter(i++, record.getAttribute("SVR_FST_SND"));   	//서비스1차2차평가자
        param.setValueParamter(i++, record.getAttribute("PERM_TEMP_GBN"));  //정규/비정규구분
        param.setValueParamter(i++, record.getAttribute("CO_WRK_GBN"));     //공동근무자 여부
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i07", param);
		
        return dmlcount;
    }

    /**
     * <p> 사원 일괄 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllEmp(Connection conn, PosContext ctx, PosDataset ds)  
    {    
    	int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i07");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());		//평가년도
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//평가회차
		        stmt.setString(iparam++,  record.getAttribute("PERM_TEMP_GBN").toString());  //정규/비정규구분
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("WRK_GBN").toString());		//근무자구분
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   		//사번
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   		//성명
		        stmt.setString(iparam++,  record.getAttribute("RES_NO").toString());			//주민번호
		        stmt.setString(iparam++,  record.getAttribute("PERM_TEMP_GBN").toString());  //정규/비정규구분
		        stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());		//평가부서
		        stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());		//평가부서
		        stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());		//평가부서
		        stmt.setString(iparam++,  record.getAttribute("PWD_NO").toString());			//비밀번호
		        stmt.setString(iparam++,  record.getAttribute("WK_JOB_CD").toString());   	//보직코드
		        
		        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());   		//발령일자
		        stmt.setString(iparam++,  record.getAttribute("JOB_TIT_CD").toString());		//직급코드
		        stmt.setString(iparam++,  record.getAttribute("JOB_TIT_NM").toString());		//직급명
		        stmt.setString(iparam++,  record.getAttribute("HP_NO").toString());			//이동전화번호
		        stmt.setString(iparam++,  (String)record.getAttribute("ENTR_DT"));		//입사일자
		        
		        stmt.setString(iparam++,  record.getAttribute("ESTM_ESC_GBN").toString());   	//평가대상제외자구분
		        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());   		//1차2차평가자구분
		        stmt.setString(iparam++,  record.getAttribute("MNG_GBN").toString());		//담당자구분
		        stmt.setString(iparam++,  record.getAttribute("MULT_ESTM_GRP").toString());	//다면평가자그룹
		        stmt.setString(iparam++,  record.getAttribute("CNL_GBN").toString());		//평가취소구분
		        
		        stmt.setString(iparam++,  record.getAttribute("ESTM_WK_JOB").toString());   	//평가보직코드    
		        stmt.setString(iparam++,  record.getAttribute("ESTM_EMP").toString());  		//평가자사번
		        stmt.setString(iparam++,  record.getAttribute("COMM_NO").toString());		//지점세부코드
		        stmt.setString(iparam++,  record.getAttribute("DIV_NO").toString());			//투표소코드
		        stmt.setString(iparam++,  record.getAttribute("MNR_FST_SND").toString());	//근무태도1차2차평가자
		        
		        stmt.setString(iparam++,  record.getAttribute("SVR_FST_SND").toString());   	//서비스1차2차평가자
		        stmt.setString(iparam++,  record.getAttribute("PERM_TEMP_GBN").toString());  //정규/비정규구분
		        stmt.setString(iparam++,  (String)record.getAttribute("CO_WRK_GBN"));     //공동근무자 여부
		        stmt.setString(iparam++,  SESSION_USER_ID);						//사용자ID(작성자)
		        
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
     * <p> 사원 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEmp(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d07", param);

        return dmlcount;
    }
    
    /**
     * <p> 투표소 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertComm(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//평가회차
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));   		//투표소코드
        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));   	//발매원번호
        
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));   		//부서코드
        param.setValueParamter(i++, record.getAttribute("YEAR_DATE"));   	//최근일자
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i08", param);

		
        return dmlcount;
    }

    /**
     * <p> 투표소 일괄 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllComm(Connection conn, PosContext ctx, PosDataset ds)  
    {    	
    	int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i08");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
		        stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());		//평가년도
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//평가회차
		        stmt.setString(iparam++,  record.getAttribute("COMM_NO").toString());		//지점코드
		        stmt.setString(iparam++,  record.getAttribute("DIV_NO").toString());   		//투표소코드
		        stmt.setString(iparam++,  record.getAttribute("TELLER_ID").toString());   	//발매원번호
		        
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());   		//부서코드
		        stmt.setString(iparam++,  record.getAttribute("YEAR_DATE").toString());   	//최근일자
		        stmt.setString(iparam++,  SESSION_USER_ID);						//사용자ID(작성자)
				
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
     * <p> 투표소 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteComm(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d08", param);

        return dmlcount;
    }
    
    /**
     * <p> 승인 사업지원팀  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertAprv(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//2.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//3.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_i10", param);

		
        return dmlcount;
    }
    
    /**
     * <p> 승인 사업지원팀  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAprv(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sEvalYear);	//평가년도
        param.setValueParamter(i++, sEvalNum);  //평가회차
        param.setValueParamter(i++, sEvalYear);	//평가년도
        param.setValueParamter(i++, sEvalNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d10", param);

        return dmlcount;
    }

    /**
     * <p> 발매실적 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    public int insertTelmp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//평가회차
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//부서
        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));	//발매원번호
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   	//사번
        
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   	//성명
        param.setValueParamter(i++, record.getAttribute("SOLD_AVG"));   //발매실적건수
        param.setValueParamter(i++, record.getAttribute("TOTAL_CNT"));   
        param.setValueParamter(i++, record.getAttribute("WK_DAY_CNT"));   
        
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i09", param);
        
        return dmlcount;
    }

    /**
     * <p> 발매실적 일괄 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    public int InsertAllTelmp(Connection conn, PosContext ctx, PosDataset ds)  
    {    	
    	int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i09");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
		        stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//평가년도
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//평가회차
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());	//부서
		        stmt.setString(iparam++,  record.getAttribute("TELLER_ID").toString());	//발매원번호
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   	//사번
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   	//성명
		        stmt.setString(iparam++,  record.getAttribute("SOLD_AVG").toString());   //발매실적건수
		        stmt.setString(iparam++,  record.getAttribute("TOTAL_CNT").toString());   
		        stmt.setString(iparam++,  record.getAttribute("WK_DAY_CNT").toString());   
		        
		        stmt.setString(iparam++,  SESSION_USER_ID);					//사용자ID(작성자)
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
     * <p> 발매실적 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    public int deleteTelmp(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d09", param);

        return dmlcount;
    }

    /**
     * <p> 다스 투표소 매핑 추가 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertPcDiv(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//평가회차
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));   	//
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));   	//
        
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));     //
        param.setValueParamter(i++, record.getAttribute("COMM_NAME"));  //
        param.setValueParamter(i++, record.getAttribute("DIV_NAME"));   //
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i11", param);
        
        return dmlcount;
    }

    /**
     * <p> 다스 투표소 매핑 일괄 추가 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int InsertAllPcDiv(Connection conn, PosContext ctx, PosDataset ds)  
    {    	
		int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i11");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//평가년도
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//평가회차
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());	//
		        stmt.setString(iparam++,  record.getAttribute("SELL_CD").toString());   	//
		        stmt.setString(iparam++,  record.getAttribute("COMM_NO").toString());   	//
		        
		        stmt.setString(iparam++,  record.getAttribute("DIV_NO").toString());     //
		        stmt.setString(iparam++,  record.getAttribute("COMM_NAME").toString());  //
		        stmt.setString(iparam++,  record.getAttribute("DIV_NAME").toString());   //
		        stmt.setString(iparam++,  SESSION_USER_ID);					//사용자ID(작성자)

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
     * <p> 다스 투표소 매핑 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deletePcDiv(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//평가년도
        param.setValueParamter(i++, sNum);  //평가회차

        int dmlcount = this.getDao("rbmdao").update("rev1010_d11", param);

        return dmlcount;
    }
    
    /**
     * <p> 승인테이블 추가 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertApprAll(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//2.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//3.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//4.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//5.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//6.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//7.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        param.setValueParamter(i++, sEvalYear);		//8.평가년도
        param.setValueParamter(i++, sEvalNum);		//  평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev1060_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 승인테이블 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAppr(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setWhereClauseParameter(i++, sEvalYear);		//1.평가년도
        param.setWhereClauseParameter(i++, sEvalNum);		//2.평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev1060_d01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 년간 발매실적 제외자는  TOTAL_CNT = 99999, WK_DAY_CNT = 99999로 설정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    public int updateByPass(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.평가년도
        param.setValueParamter(i++, sEvalNum);		//2.평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_u03", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 년간 발매실적 제외자의 평균 발매건수는 팀평균 적용 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    public int updateTeamAvg(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.평가년도
        param.setValueParamter(i++, sEvalNum);		//2.평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_u04", param);
        
        return dmlcount;
    }
}