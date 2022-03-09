/*================================================================================
 * 시스템			: 명예심판관리
 * 소스파일 이름	: snis.rbm.business.rbs1120.activity.SaveHeal.java
 * 파일설명		: 명예심판관리 등록
 * 작성자			: 이승배
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-2
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1120.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHnrRefreReq extends SnisActivity {
		public SaveHnrRefreReq(){}
		
		
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

	        sDsName = "dsHnrRefreReq";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
				        nSaveCount += insertHnrRefreTms(record);
			        } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
				        nSaveCount += updateHnrRefreTms(record);
			        } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
			        	nDeleteCount += deleteHnrRefreTms(record);	            	
		            }		        
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    /**
	     * <p> 명예심판 직접 입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertHnrRefreTms(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));    	// 1.개최기관코드
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));   	// 2.기준년도
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));    	// 3.개최기관코드
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));   	// 4.기준년도
	        param.setValueParamter(i++, record.getAttribute("REQ_USER_NM"));    // 5.고객명
	        param.setValueParamter(i++, record.getAttribute("HP_NO"));      	// 6.휴대폰 번호
	        param.setValueParamter(i++, record.getAttribute("AGE"));       		// 7.나이
	        param.setValueParamter(i++, record.getAttribute("SEX"));     		// 8.성별 
	        param.setValueParamter(i++, record.getAttribute("RMK"));     		// 9.비고 
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));     	// 10.지점코드 
	        param.setValueParamter(i++, record.getAttribute("HOME_ADDR1"));     	// 11.주소 
	        param.setValueParamter(i++, SESSION_USER_ID);                       // 12.입력자 ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1120_i01", param);
	        
	        return dmlcount;
	    }
	    	
	    /**
	     * <p> 명예심판 직접입력자료 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateHnrRefreTms(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("REQ_USER_NM"));    // 1.고객명
	        param.setValueParamter(i++, record.getAttribute("HP_NO"));      	// 2.휴대폰 번호
	        param.setValueParamter(i++, record.getAttribute("HOME_ADDR1"));      	// 2.휴대폰 번호
	        param.setValueParamter(i++, record.getAttribute("AGE"));       		// 3.나이
	        param.setValueParamter(i++, record.getAttribute("SEX"));     		// 4.성별 
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));     	// 5.지점코드 
	        param.setValueParamter(i++, record.getAttribute("RMK"));            // 6. 사업명
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 7. 사용자 ID     
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 8. 개최기관코드
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   // 9. 기준년도
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));   	// 10.연번	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1120_u01", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 명예심판 직접입력자료 삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteHnrRefreTms(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 1. 개최기관코드
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   // 2. 기준년도
	        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));   	// 3. 연번	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1120_d01", param);

	        return dmlcount;
	    }
}
