/*================================================================================
 * 시스템			: 평가대상조정
 * 소스파일 이름	: snis.rbm.business.rev1100.activity.SaveEvEmp.java
 * 파일설명		: 부서별평가자 수정
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-11-27
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1100.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEvEmp extends SnisActivity {
		public SaveEvEmp(){}
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
	        int nTempCnt     = 0;
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");

	        //다면평가승인시, 수정불가능
	        if( getAprvYn(sEvalYear, sEvalNum).equals("N")) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "[ 다면평가자그룹선정 ]에서 승인요청이 되었기 때문에 저장하실 수 없습니다.");
            		
            		return;
            	}
	        }
	     
	        sDsName = "dsEvEmp";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	nTempCnt += updateEvEmp(record);	//비대상으로 변경
			        	
			        	//삭제
			        	deletePrm(record);
			        	deletePrmDt(record);
			        	deleteMnr(record);
			        	deleteMnrDt(record);
			        	deleteSrv(record);
			        	deleteSrvDt(record);
			        }     
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount  );
	    }
	    
	    /**
	     * <p> 대상자를 비대상으로 변경 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEvEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_ESC_RSN"));
	        param.setValueParamter(i++, SESSION_USER_ID);		  
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_u01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deletePrm(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 업무수행평가 상세삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deletePrmDt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMnr(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 근무태도평가상세 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteMnrDt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d04", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteSrv(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 서비스평가상세 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteSrvDt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 다면평가 승인 상태 조회 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected String getAprvYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1100_s02", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("APRV_YN"));
	        
	        return rtnKey;
	    }
}
