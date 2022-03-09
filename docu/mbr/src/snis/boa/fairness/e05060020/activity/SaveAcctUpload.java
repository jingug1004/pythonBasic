/*================================================================================
 * 시스템			: 공정 관리 
 * 소스파일 이름	: snis.boa.fairness.e05060020.activity.SaveAcctUpload.java
 * 파일설명		: 계좌 거래내역 업로드 선수등록
 * 작성자			: 한영택 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-23
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05060020.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 계좌 거래내역 등록 정보 대한 등록, 수정, 삭제 한다.
* @auther 한영택 
* @version 1.0
*/
public class SaveAcctUpload extends SnisActivity
{    
    public SaveAcctUpload()
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutAcctUpload");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
           		nSaveCount = nSaveCount + insertAcctUpload(record);
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p>계좌 거래내역 업로드 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertAcctUpload(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT")); 
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT"));
        param.setValueParamter(i++, record.getAttribute("MJR_NM"));
        param.setValueParamter(i++, record.getAttribute("BK_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT"));
        param.setValueParamter(i++, record.getAttribute("BK_ACT"));
        param.setValueParamter(i++, record.getAttribute("NM"));
        param.setValueParamter(i++, record.getAttribute("TR_DT"));
        param.setValueParamter(i++, record.getAttribute("TR_AMT"));
        param.setValueParamter(i++, record.getAttribute("TR_TIME"));
        param.setValueParamter(i++, record.getAttribute("TR_DT"));
        param.setValueParamter(i++, record.getAttribute("IO_GUBUN"));
        param.setValueParamter(i++, record.getAttribute("AGENCY"));
        param.setValueParamter(i++, record.getAttribute("TERMINAL"));
        param.setValueParamter(i++, record.getAttribute("JEOGYO"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        param.setValueParamter(i++, record.getAttribute("MJR_BK_ACT")); 
        
        return this.getDao("boadao").update("tbee_money_account_ie001", param);
    }
}
