/*================================================================================
 * 시스템			: 예상지 적중률 관리
 * 소스파일 이름	: snis.rbm.business.rbs1210.activity.SaveYesCom.java
 * 파일설명		: 예상결과 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2014-09-06
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1230.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveYesSangRslt extends SnisActivity {
		public SaveYesSangRslt(){}
		
		
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

	        sDsName = "dsYesSangRslt";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

				    nSaveCount += updateYesSangRslt(record);
		        	continue;
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    
	    /**
	     * <p> 예상결과 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateYesSangRslt(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("YESANG"));     	// 1.예상
	        param.setValueParamter(i++, record.getAttribute("AB"));   			// 2.A-B
	        param.setValueParamter(i++, record.getAttribute("AC"));    			// 3.A-C
	        param.setValueParamter(i++, record.getAttribute("BA"));       		// 4.B-A
	        param.setValueParamter(i++, record.getAttribute("CA"));   			// 5.C-A
	        param.setValueParamter(i++, record.getAttribute("ABC"));    		// 6.삼복승 
	        param.setValueParamter(i++, record.getAttribute("EX_AB"));        	// 7.AB가중치                
	        param.setValueParamter(i++, record.getAttribute("EX_ABC"));        	// 8.ABC가중치                
	        param.setValueParamter(i++, record.getAttribute("EX_AC"));        	// 9.AC가중치                
	        param.setValueParamter(i++, record.getAttribute("EX_A"));        	// 10.축 가중치                
	        param.setValueParamter(i++, record.getAttribute("AUTO"));        	// 11.자동생성여부                
	        param.setValueParamter(i++, record.getAttribute("RATE"));        	// 12.배당률(쌍승)   
	        param.setValueParamter(i++, record.getAttribute("RATE_QU"));      	// 13.배당률(복승)   
	        param.setValueParamter(i++, record.getAttribute("ETC"));        	// 14.가산점수별업체수                
	        param.setValueParamter(i++, record.getAttribute("EXCLUSION"));      // 15.결장제외               
	        param.setValueParamter(i++, record.getAttribute("EX_BA"));        	// 16.BA가중치                
	        param.setValueParamter(i++, record.getAttribute("RT_AB"));        	// 17.AB배당률가중치      
	        param.setValueParamter(i++, record.getAttribute("RT_BA"));        	// 18.BA배당률가중치                
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 19.수정자 사번		        
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("YES_COM_SEQ")); // 20. 업체번호
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 21. 경륜/경정 구분
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR")); 	// 22. 년도
	        param.setWhereClauseParameter(i++, record.getAttribute("TMS")); 		// 23. 회차
	        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD")); 	// 24. 일차
	        param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO")); 	// 25. 경주번호
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1230_u01", param);
	        return dmlcount;
	    }

}
