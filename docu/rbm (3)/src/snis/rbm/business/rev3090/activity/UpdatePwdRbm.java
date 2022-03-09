/*================================================================================
 * 시스템			: 비밀번호초기화
 * 소스파일 이름	: snis.rbm.business.rev3090.activity.UpdatePwd.java
 * 파일설명		: 다면평가 홈페이지의 비밀번호를 초기화(주민번호 뒤 7자리) 시킨다.
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-12-08
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev3090.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.EncryptSHA256;

public class UpdatePwdRbm extends SnisActivity {
		public UpdatePwdRbm(){}

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

	    protected void saveState(PosContext ctx) 
	    {
	    	int nSaveCount = 0;
	    	int nSize      = 0;	
	    	
	    	String sDsName   = "";
	    	PosDataset ds;
	    	
	    	sDsName = "dsEvEmpRbm";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset) ctx.get(sDsName);
		        nSize = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("RES_NO")));
			        	nSaveCount += updatePwdRbm(record);		        	
			        }	        
		        }	        
	        }
	    	
	        Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> 비밀번호 초기화 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updatePwdRbm(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("PSWD"));			//암호초기화 값
	        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO" ));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3090_u02", param);
	        
	        return dmlcount;
	    }	    
}