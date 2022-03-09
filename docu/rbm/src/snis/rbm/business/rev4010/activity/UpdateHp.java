/*================================================================================
 * 시스템			: 휴대폰 번호 다시 가져오기
 * 소스파일 이름	: snis.rbm.business.rev4010.activity.UpdateHp.java
 * 파일설명		: 휴대폰 번호 다시 가져오기
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2012-01-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateHp extends SnisActivity {
	
	public UpdateHp(){}

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
    	int nSaveCount = 0;

    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
        String sEvalNum  = (String)ctx.get("ESTM_NUM");
        
        nSaveCount = updateHp(sEvalYear, sEvalNum);	
        
        if( nSaveCount > 0 ) {
        	Util.setSvcMsg(ctx, "[ " + nSaveCount + " ]의 평가자 휴대폰 번호가 변경되었습니다.");
        } else {
        	Util.setSvcMsg(ctx, "휴대폰 번호가 바뀐 평가자가 없습니다.");
        }

        Util.setSaveCount(ctx, nSaveCount  );
    }
    
    /**
     * <p> 휴대폰 번호 다시 가져오기 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateHp(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int nUpdateCnt = 0;
        String sEmpNo, sHp;
        
        param.setWhereClauseParameter(i++, sEvalYear);
        param.setWhereClauseParameter(i++, sEvalNum);
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rev4010_s03", param);        
        
        PosRow pr[] = keyRecord.getAllRow();

        for( int k=0; k<pr.length; k++) {
        	sEmpNo = String.valueOf(pr[k].getAttribute("EMP_NO"));
        	sHp    = String.valueOf(pr[k].getAttribute("HP_NO"));
        	
        	nUpdateCnt += updateHphone(sEvalYear, sEvalNum, sEmpNo, sHp);
        }
        
        return nUpdateCnt;
    }
    
    /**
     * <p> 휴대폰 번호 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateHphone(String sEvalYear, String sEvalNum, String sEmpNo, String sHpNo)
    {			
    	PosParameter param = new PosParameter();
        
    	int i = 0;
        
        param.setValueParamter(i++, sHpNo);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, sEvalYear);
        param.setWhereClauseParameter(i++, sEvalNum);
        param.setWhereClauseParameter(i++, sEmpNo);
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_u04", param);
        	
        return dmlcount;
    }
}
