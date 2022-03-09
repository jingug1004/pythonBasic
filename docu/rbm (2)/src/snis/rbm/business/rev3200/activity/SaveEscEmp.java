/*================================================================================
 * 시스템			: 발매원제외기간
 * 소스파일 이름	: snis.rbm.business.rev3200.activity.SaveDistribution.java
 * 파일설명		: 발매원제외기간 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2012-01-15
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev3200.activity;

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

public class SaveEscEmp extends SnisActivity {
		public SaveEscEmp(){}
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
	    	int nSize = 0;
	    	
	    	String sEscStrDt = "";
	    	String sEscEndDt = "";
	    	String sOrgEscStrDt = "";
	    	String sOrgEscEndDt = "";

	    	String sDsName = "";
	    	
	    	PosDataset ds;

	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        //평가 마감시 저장 X
	        SavePrmRslt SavePrmRslt = new SavePrmRslt();
	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "평가마감이 완료되어 저장하실 수 없습니다.");
            		
            		return;
            	}
	        }
	        
	        sDsName = "dsEscEmp";
	        String sMsg = "제외기간을 제외한 이후에 발매실적이 한 건도 없을 경우에는 제외구분을 [년간]으로 변경해주세요.";
	        	
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            	nSaveCount += insertEscEmp(record);
		            	
		            	if( getReleaCnt(record) == 0 ) {
		            		try { 
				    			throw new Exception(); 
				        	} catch(Exception e) {       		
				        		this.rollbackTransaction("tx_rbm");
				        		Util.setSvcMsg(ctx, sMsg);
				        		
				        		return;
				        	}
		            	}
			        	continue;
		            }
		            		
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {

				    	sEscStrDt    = (String)record.getAttribute("ESC_STR_DT");
				    	sEscEndDt    = (String)record.getAttribute("ESC_END_DT");
				    	sOrgEscStrDt = (String)record.getAttribute("ORG_ESC_STR_DT");
				    	sOrgEscEndDt = (String)record.getAttribute("ORG_ESC_END_DT");
				    	
				    	
				    	if( sEscStrDt.equals(sOrgEscStrDt) && sEscEndDt.equals(sOrgEscEndDt) ) {
				    		//시작일자와 종료일자가 변경이 없을 경우
				    		nSaveCount += updateEscEmp(record);
				    	} else {
				    		//시작일자와 종료일자가 변했을 경우
				    		deleteEscEmp(record);
				    		
				    		if( getEscDate(record) > 0 ) {
				    			try { 
					    			throw new Exception(); 
					        	} catch(Exception e) {       		
					        		this.rollbackTransaction("tx_rbm");
					        		Util.setSvcMsg(ctx, "변경하신 기간 내에 포함되는 기간이 이미 존재합니다.");
					        		
					        		return;
					        	}
				    		} else {
				    			nSaveCount += insertEscEmp(record);
				    		}
				    	}
				    	
				    	if( getReleaCnt(record) == 0 ) {
		            		try { 
				    			throw new Exception(); 
				        	} catch(Exception e) {       		
				        		this.rollbackTransaction("tx_rbm");
				        		Util.setSvcMsg(ctx, sMsg);
				        		
				        		return;
				        	}
		            	}
			        }
			        
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {	
		            	nDeleteCount += deleteEscEmp(record);
		            	continue;
		            }		        
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    
	    /**
	     * <p> 발매제외기간 입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertEscEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//평가차수
	        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));  //발매원번호
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));	    //사번
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
	        
	        param.setValueParamter(i++, record.getAttribute("ESC_STR_DT"));	//제외시작일자
	        param.setValueParamter(i++, record.getAttribute("ESC_END_DT"));	//제외종료일자
	        param.setValueParamter(i++, record.getAttribute("ESC_CD"));		//제외구분
	        param.setValueParamter(i++, record.getAttribute("ESC_RSN"));	//제외사유
	        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3200_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 발매제외기간 수정 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateEscEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESC_STR_DT"));	//제외시작일자
	        param.setValueParamter(i++, record.getAttribute("ESC_END_DT"));	//제외종료일자
	        param.setValueParamter(i++, record.getAttribute("ESC_CD"));		//제외구분
	        param.setValueParamter(i++, record.getAttribute("ESC_RSN"));	//제외사유
	        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수정자)
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	    //평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	    //평가차수
	        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));       //발매원번호
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));	        //사번
	        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));      	//지점코드
	        param.setWhereClauseParameter(i++, record.getAttribute("ORG_ESC_STR_DT"));	//제외시작일자

	        int dmlcount = this.getDao("rbmdao").update("rev3200_u01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 발매제외기간 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteEscEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	    //평가년도
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	    //평가차수
	        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));      //발매원번호
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));	        //사번
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	    //지점코드
	        param.setValueParamter(i++, record.getAttribute("ORG_ESC_STR_DT"));	//제외시작일자
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3200_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 입력한 기간 내에 겹치는 기간이 존재하는지 조회  </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public int getEscDate(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//평가차수
	        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));   //발매원번호
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));	    //사번
	        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));     //지점코드
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_STR_DT"));	//제외시작일자
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_END_DT"));	//제외종료일자
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_STR_DT")); 	//제외시작일자
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_END_DT"));	//제외종료일자
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev3200_s03", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

	        return Integer.parseInt(rtnKey);
	    }
	    
	    /**
	     * <p> 제외기간을 제외한 이후에 발매실적 건수(일자) 조회  </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    public int getReleaCnt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//평가차수
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//평가차수
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//평가차수
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//평가년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//평가차수
	        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));   //발매원번호
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev3200_s04", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

	        return Integer.parseInt(rtnKey);
	    }
}
