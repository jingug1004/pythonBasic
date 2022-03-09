/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.MonthPayBacment.e06010010.activity.SaveMonthPaySendMailReq.java
 * 파일설명		:  메일 전송 요청 Flag Update
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07010030.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
*  메일 전송 요청 Flag Update한다 . 
* @auther 김성희 
* @version 1.0
*/
public class SaveMonthPaySendMailReq extends SnisActivity
{    
    public SaveMonthPaySendMailReq()
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
    	int nDeleteCount = 0; 

        String StndYear = (String)ctx.get("STND_YEAR");
        String payMonth = (String)ctx.get("PAY_MONTH");
        String mailSendReqDt = (String)ctx.get("MAIL_SEND_REQ_DT");
        
        nSaveCount = nSaveCount + saveMonthPaySendMailReq(StndYear, payMonth, mailSendReqDt);

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 메일 전송 요청 Flag Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveMonthPaySendMailReq(String stndYear, String payMonth, String mailSendReqDt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, mailSendReqDt);  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, payMonth); 
		   
        return this.getDao("boadao").update("tbeg_month_pay_bac_uf031", param);
    }
}
