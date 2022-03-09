/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.MonthPayment.e06010010.activity.SaveMonthPay.java
 * 파일설명		: 월별 상금 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07030050.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 월별 상금 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class DeleteIncomIssu extends SnisActivity
{    
    public DeleteIncomIssu()
    {
    }

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

        String stndYearMonth = (String)ctx.get("STND_YEAR_MONTH");
    	Integer incomIssuSeq = new Integer((String)ctx.get("INCOM_ISSU_SEQ"));
    	
    	nSaveCount = nSaveCount + deleteIncomIssue(stndYearMonth, incomIssuSeq);

    }
    
    /**
     * <p> 월별 상금  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteIncomIssue(String stndYearMonth, Integer incomIssuSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, Util.trim(stndYearMonth));
        param.setWhereClauseParameter(i++, incomIssuSeq);
			    		
        return this.getDao("boadao").update("tbeg_incom_issu_df001", param);
    }
    
}
