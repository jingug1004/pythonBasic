/*
 * ================================================================================
 * 시스템 : 관리 
 * 소스파일 이름 : snis.rbm.business.rem4040.activity.SavePeriod.java 
 * 파일설명 : 수동전송주기 관리 
 * 작성자 : 신재선
 * 버전 : 1.0.0 
 * 생성일자 : 2012.12.29
 * 최종수정일자 :
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */

package snis.rbm.business.rem4060.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;

public class SaveWinNo extends SnisActivity {

	public SaveWinNo(){		
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        String EventDt    = (String) ctx.get("EVENT_DT");
        String EventWinNO = (String) ctx.get("EVENT_WIN_NO");
        
        nSaveCount = saveExceptWinNo(EventDt, EventWinNO);
        nSaveCount += saveFinalWinNo(EventDt, EventWinNO);
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 당첨번호가 아닌 경우 전부 비당첨으로 처리 </p>
     * @param   EventDt	 String		이벤트 날짜
     * @return  dmlcount int		삭제 레코드 개수
     * @throws  none
     */
    protected int saveExceptWinNo(String EventDt, String EventWinNO)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, EventDt);
        param.setValueParamter(i++, EventWinNO);

        int dmlcount = this.getDao("rbmdao").update("rem4060_u03", param);
        return dmlcount;
    }

    /**
     * <p> 최종 당첨번호 저장 </p>
     * @param   EventDt	 String		이벤트 날짜
     * @return  dmlcount int		삭제 레코드 개수
     * @throws  none
     */
    protected int saveFinalWinNo(String EventDt, String EventWinNO)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, EventDt);
        param.setValueParamter(i++, EventWinNO);

        int dmlcount = this.getDao("rbmdao").update("rem4060_u04", param);
        return dmlcount;
    }
    
}
