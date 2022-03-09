/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.financial.e06010010.activity.CalcMonthPay.java
 * 파일설명		: 수당 지급기준 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07020055.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.*;

/**
* 회차별 성적 상금 계산 한다.
* @auther 김성희 
* @version 1.0
*/
public class CalcMonthPay extends SnisActivity
{    
    public CalcMonthPay()
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
    	calcMonthPay(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void calcMonthPay(PosContext ctx) 
    {
    	String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));	//월이 아닌 회차로 변경(2014.6.27)
    	String sPayDt = (String)ctx.get("PAY_DT");

    	clearMonthPay(stndYear, payMonth);
    	calcMonthPay(stndYear, payMonth, sPayDt) ;
    	new SaveMonthPay().updateTax(stndYear, payMonth, SESSION_USER_ID);
    	
    	//처리중으로 Update
    	new SaveMonthPayBac().saveMonthPrizFinish(stndYear, payMonth, "N", sPayDt);
    }

    

    /**
     * <p> 월별 성적상금 계산   Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int calcMonthPay(String stndYear,  Integer month, String sPayDt) 
    {
        PosParameter param = new PosParameter();
        int i = 0;     
        param.setValueParamter(i++, sPayDt);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  

        
        
        return this.getDao("boadao").update("tbeg_month_pay_mf001", param);
    }
    
    

    /**
     * <p>회차별 성적 상금 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int clearMonthPay(String stndYear, Integer month) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, month);  
        return this.getDao("boadao").update("tbeg_month_pay_uf010", param);
    }
}
