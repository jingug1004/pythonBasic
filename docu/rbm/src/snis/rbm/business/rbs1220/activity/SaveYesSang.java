/*================================================================================
 * 시스템			: 예상지 적중률 관리
 * 소스파일 이름	: snis.rbm.business.rbs1210.activity.SaveYesCom.java
 * 파일설명		: 예상지 업체 등록
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2014-08-31
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs1220.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveYesSang extends SnisActivity {
		public SaveYesSang(){}
		
		
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

	        sDsName = "dsYesSang";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

				    nSaveCount += updateYesSang(record);
		        	continue;
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    
	    /**
	     * <p> 예상내역 수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateYesSang(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));     	// 1.기준년도
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));   		// 2.경륜경정 구분
	        param.setValueParamter(i++, record.getAttribute("TMS"));    		// 3.회차
	        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));       	// 4.일차
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));   		// 5.경주번호
	        param.setValueParamter(i++, record.getAttribute("YES_COM_SEQ"));    // 6.예상지업체 고유번호 
	        param.setValueParamter(i++, record.getAttribute("YESANG"));        	// 7.예상내역                
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 8.수정자 사번		        
	       
	        int dmlcount = this.getDao("rbmdao").update("rbs1220_u01", param);
	        return dmlcount;
	    }

}
