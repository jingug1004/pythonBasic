/*================================================================================
 * 시스템			: 발매실적 초기화
 * 소스파일 이름	: snis.rbm.business.rev3200.activity.SaveTelmp.java
 * 파일설명		: 발매실적 초기화
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2012-01-19
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

import snis.rbm.business.rev1010.activity.SaveData;
import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveTelmp extends SnisActivity {
	
	public SaveTelmp(){}

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
        
        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//평가년도
        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//평가회차
        String sCommCd   = (String)ctx.get("DEPT_CD");		//지점
        String sPrmStrDt = (String)ctx.get("PRM_STR_DT");	//평가기간시작일
        String sPrmEndDt = (String)ctx.get("PRM_END_DT");	//평가기간종료일
        
        String sDeptCd   = getDeptCd(sCommCd); //부서
        
        //평가마감 되었다면 저장 불가능
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
        
        //발매실적삭제
        deleteTelmp(sEvalYear, sEvalNum, sDeptCd);
        
        //발매실적 Insert
        nSaveCount = insertTelmp(sEvalYear, sEvalNum, sDeptCd, sPrmStrDt, sPrmEndDt);
        
        //업무수행평가 발매실적 초기화
        updateReleaInit(sEvalYear, sEvalNum, sDeptCd);
        
        SaveData sd = new SaveData();
        sd.updateByPass(sEvalYear, sEvalNum);  //년간 발매실적 제외자는  TOTAL_CNT = 99999, WK_DAY_CNT = 99999로 설정
        sd.updateTeamAvg(sEvalYear, sEvalNum); //년간 발매실적 제외자의 평균 발매건수는 팀평균 적용
        
        Util.setReturnParam(ctx, "RESULT", "0");
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }  
    
    /**
     * <p> 발매실적 부서별 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTelmp(String sEvalYear, String sEvalNum, String sDeptCd)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.평가년도
        param.setValueParamter(i++, sEvalNum);		//2.평가회차
        param.setValueParamter(i++, sDeptCd);		//3.지점코드
        
        int dmlcount = this.getDao("rbmdao").update("rev3200_d02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 발매실적 부서별 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertTelmp(String sEvalYear, String sEvalNum, String sDeptCd, String sPrmStrDt, String sPrmEndDt)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		   //1.평가년도
        param.setValueParamter(i++, sEvalNum);		   //2.평가회차
        param.setValueParamter(i++, SESSION_USER_ID);  //3.사용자ID(등록자)
        param.setValueParamter(i++, SESSION_USER_ID);  //4.사용자ID(수정자)
        param.setValueParamter(i++, sPrmStrDt);		   //5.평가회차
        param.setValueParamter(i++, sPrmEndDt);		   //6.평가회차
        param.setValueParamter(i++, sEvalYear);		   //7.평가년도
        param.setValueParamter(i++, sEvalNum);		   //8.평가회차
        param.setValueParamter(i++, sDeptCd);		   //9.부서(지점)
        param.setValueParamter(i++, sEvalYear);		   //10.평가년도
        param.setValueParamter(i++, sEvalNum);		   //11.평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev3200_i02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 업무수행평가 발매실적 초기화</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateReleaInit(String sEvalYear, String sEvalNum, String sDeptCd)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);  //1.사용자ID(수정자)

        i = 0;
        param.setWhereClauseParameter(i++, sEvalYear); //2.평가년도
        param.setWhereClauseParameter(i++, sEvalNum);  //3.평가회차
        param.setWhereClauseParameter(i++, sDeptCd);   //4.부서(지점)
        
        int dmlcount = this.getDao("rbmdao").update("rev3200_u03", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 지점코드를 부서코드로 변환 </p>
     */
    protected String getDeptCd(String sCommCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sCommCd);
        PosRowSet keyRecord = this.getDao("rbmdao").find("rev3200_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("DEPT_CD"));
        
        return rtnKey;
    }
}