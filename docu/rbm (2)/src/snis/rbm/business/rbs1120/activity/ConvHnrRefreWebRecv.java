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

public class ConvHnrRefreWebRecv extends SnisActivity {
		public ConvHnrRefreWebRecv(){}
		
		
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

	        nSaveCount   += updateHnrRefreReq(ctx);
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    /**
	     * <p> 명예심판 신청내역 가져오기 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateHnrRefreReq(PosContext ctx) 
	    {			           
	        PosParameter param = new PosParameter(); 	        
	        int i = 0;
	    	String sMeetCd   = "";
	    	String sStndYear = "";
	    	
	        sMeetCd	  = (String) ctx.get("MEET_CD");
	        sStndYear = (String) ctx.get("STND_YEAR");
	        
	        param.setValueParamter(i++, sMeetCd);  				// 경륜경정 구분	        
	        param.setValueParamter(i++, sStndYear);  			// 경륜경정 구분	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1120_u02", param);	        
	        return dmlcount;
	    }
	    	
	    
}
