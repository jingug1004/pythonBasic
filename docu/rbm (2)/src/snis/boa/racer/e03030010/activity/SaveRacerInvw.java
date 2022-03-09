/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03030010.activity.SaveRacerInvw.java
 * 파일설명		: 출전선수면담등록
 * 작성자		: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-13
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03030010.activity;

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
public class SaveRacerInvw extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerInvw()
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutRacerInvw");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveRacerInvw(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 출주선수면담 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerInvw(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertRacerInvw(record);
        return effectedRowCnt;
    }

 
    /**
     * <p>출주선수면담  입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertRacerInvw(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        if (record.getAttribute("ENTRY_BODY_WGHT") != null && Float.parseFloat(String.valueOf(record.getAttribute("ENTRY_BODY_WGHT"))) > 0){
            param.setValueParamter(i++, Util.trim(record.getAttribute("ENTRY_BODY_WGHT"))); 
        } else {
            param.setValueParamter(i++, null); 
        }
        param.setValueParamter(i++, Util.trim(record.getAttribute("HEAL_STAT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CONDI_CD")));
        if ("".equals(Util.trim(record.getAttribute("TRNG_STAT")))) {
        	param.setValueParamter(i++, null);            
        } else {
        	param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_STAT")));            
        }
        if ("".equals(Util.trim(record.getAttribute("ETC")))) {
        	param.setValueParamter(i++, null);            
        } else {
        	param.setValueParamter(i++, Util.trim(record.getAttribute("ETC")));            
        }
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIV_TURNNING_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("START_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORM_CNT")));
        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ENTRY_BODY_WGHT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HEAL_STAT_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CONDI_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_STAT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ETC")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PRIV_TURNNING_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("START_CNT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORM_CNT")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbec_racer_invw_ic001", param);

 		//선수상세정보 몸무게 수정
        if (record.getAttribute("ENTRY_BODY_WGHT") != null && Float.parseFloat(String.valueOf(record.getAttribute("ENTRY_BODY_WGHT"))) > 0){
	        i = 0; 
	    	PosParameter param2 = new PosParameter();    	
	    	param2.setValueParamter(i++, record.getAttribute("ENTRY_BODY_WGHT"));
	    	param2.setValueParamter(i++, SESSION_USER_ID );
	        
	        i = 0;
	        param2.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO")));      
	    	dmlcount = this.getDao("boadao").update("tbec_racer_detail_uc002", param2);
        }
        
        return dmlcount;    
    }
}