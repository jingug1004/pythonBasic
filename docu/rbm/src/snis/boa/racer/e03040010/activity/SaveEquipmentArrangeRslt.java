/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03040010.activity.SaveEquipmentArrange.java
 * 파일설명		: 모터/보트배정 등록
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-23
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 부상선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SaveEquipmentArrangeRslt extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveEquipmentArrangeRslt()
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
System.out.println("saveState:::::::");

    	int nSaveCount   	= 0; 

    	PosDataset ds;
        int nSize        	= 0;
        PosRecord record  	= null;

        ds    = (PosDataset) ctx.get("dsLotRslt");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
    		nSaveCount = nSaveCount + saveRacerArrange(ctx, record);
        }

        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 모터/보트배정  Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerArrange(PosContext ctx, PosRecord record)
    {
System.out.println("saveRacerArrange:::::::");
    	PosParameter param = new PosParameter();
        int i = 0;
       
        param.setWhereClauseParameter(i++, (String)ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, (String)ctx.get("MBR_CD"));
        param.setWhereClauseParameter(i++, (String)ctx.get("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("PRT_SEQ_001")));
        param.setWhereClauseParameter(i++, (String)ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, (String)ctx.get("MBR_CD"));
        param.setWhereClauseParameter(i++, (String)ctx.get("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("PRT_SEQ_002")));
        param.setWhereClauseParameter(i++, (String)ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, (String)ctx.get("MBR_CD"));
        param.setWhereClauseParameter(i++, (String)ctx.get("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("PRT_SEQ_003")));
        param.setWhereClauseParameter(i++, SESSION_USER_ID);
        
System.out.println("saveRacerArrange:::::2222::");
        return this.getDao("boadao").update("tbeb_arrange_uc002", param);  
    }

}


