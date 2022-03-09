/*================================================================================
 * 시스템			: 최종평점 및 등급산출
 * 소스파일 이름	: snis.rbm.business.rev4010.activity.UpdateGrade.java
 * 파일설명		: 최종평점 및 등급산출
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-24
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rev4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateGrade extends SnisActivity {
	
	public UpdateGrade(){}

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
    	int nSize      = 0;
    	
    	String sDsName   = "";
    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
        String sEvalNum  = (String)ctx.get("ESTM_NUM");
        
        PosDataset ds;
        
        
        SavePrmRslt SavePrmRslt = new SavePrmRslt();
        
        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("N") ) {
        	try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsg(ctx, "평가마감이 완료되어야 등급산정을 할 수 있습니다.");
        		
        		return;
        	}
        }
        
        //delete
        deleteTotalRslt(sEvalYear, sEvalNum);
        
        //insert
        nSaveCount = insertTotalRslt(sEvalYear, sEvalNum);
    	
        updateGrade(sEvalYear, sEvalNum);	//등급산출 여부/시간 update

        Util.setSaveCount(ctx, nSaveCount);
    }
    
    /**
     * <p> 최종평점 및 등급산출 </p>
     * @param   sEvalYear	평가년도
     * 			sEvalNum	평가회차
     * @return  dmlcount	int		update 개수
     * @throws  none
     */
    protected int updateGrade(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// 사용자 ID
        param.setValueParamter(i++, sEvalYear);         // 평가년도
        param.setValueParamter(i++, sEvalNum);          // 평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 최종평점 및 등급산출 삭제 </p>
     * @param   sEvalYear	평가년도
     * 			sEvalNum	평가회차
     * @return  dmlcount	int		update 개수
     * @throws  none
     */
    protected int deleteTotalRslt(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setWhereClauseParameter(i++, sEvalYear);         // 평가년도
        param.setWhereClauseParameter(i++, sEvalNum);          // 평가회차
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_d01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 최종평점 및 등급산출 insert</p>
     * @param   sEvalYear	평가년도
     * 			sEvalNum	평가회차
     * @return  dmlcount	int		update 개수
     * @throws  none
     */
    protected int insertTotalRslt(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
               
        param.setValueParamter(i++, SESSION_USER_ID);   
        
        param.setValueParamter(i++, sEvalYear);  // 1.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 2.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 3.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 4.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 5.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 6.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 7.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 8.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  // 9.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  //10.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차
        param.setValueParamter(i++, sEvalYear);  //11.평가년도
        param.setValueParamter(i++, sEvalNum);   //   평가회차

        //2016년 상향평가 도입에 따른 근무실적 1차평가자 다수인 경우 처리 
        int dmlcount = this.getDao("rbmdao").update("rev4010_i03", param);
        
        
        /* 2011년,2013년 : 보직분야별로 최종평가등급 산정 
        int dmlcount = this.getDao("rbmdao").update("rev4010_i01", param);
        */ 
        

        /* 2012년도 : 부서그룹별로 최종평가등급 산정(2012.11.24) 
        int dmlcount = this.getDao("rbmdao").update("rev4010_i02", param);
        */
        
        return dmlcount;
    }
}
