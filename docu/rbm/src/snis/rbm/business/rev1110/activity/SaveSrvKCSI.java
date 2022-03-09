/*================================================================================
 * 시스템			: 다면평가그룹생성
 * 소스파일 이름	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * 파일설명		: 다면평가그룹 저장
 * 작성자			: 배태일
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev1110.activity;

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

public class SaveSrvKCSI extends SnisActivity {
		public SaveSrvKCSI(){}
		
		
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
	    	
	    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        //평가마감이 되었다면 저장 불가능
    		SavePrmRslt SavePrmRslt = new SavePrmRslt();	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setReturnParam(ctx, "RESULT", "F");
            		Util.setSvcMsg(ctx, "평가마감이 완료되어 저장하실 수 없습니다.");
            		
            		return;
            	}
	        }
               	
	        nSaveCount = updateSrvKCSI(sEvalYear, sEvalNum);

	        Util.setReturnParam(ctx, "RESULT", "S");
	        Util.setSaveCount  (ctx, nSaveCount);
	    }

	    /**
	     * <p> KCSI평가점수를 서비스평가에 반영 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateSrvKCSI(String sEvalYear, String sEvalNum)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);            // 1. 평가년도
	        param.setValueParamter(i++, sEvalNum);             // 2. 평가회차      
	  
	        i = 0;
	  //      param.setWhereClauseParameter(i++, sEvalYear);     // 3. 평가년도
	  //      param.setWhereClauseParameter(i++, sEvalNum);      // 4. 평가회차

	        int dmlcount = this.getDao("rbmdao").update("rev1110_u02", param);
	        return dmlcount;
	    }

}
