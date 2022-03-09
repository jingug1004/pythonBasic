/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03020010.activity.SaveRacerIjry.java
 * 파일설명		: 부상선수등록
 * 작성자		: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-09
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03020010.activity;


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
public class SaveRacerIjry extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerIjry()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerIjry");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){            	
            	nDeleteCount = nDeleteCount + deleteRacerIjry(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
            {
            	nSaveCount = nSaveCount + updateRacerIjry(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + insertRacerIjry(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 부상선수 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerIjry(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRacerIjry(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertRacerIjry(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 부상선수  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRacerIjry(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, record.getAttribute("IJRY_DT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_DT"));
        param.setValueParamter(i++, record.getAttribute("IJRY_PART"));
        param.setValueParamter(i++, record.getAttribute("IJRY_STAT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_WK"));
        param.setValueParamter(i++, record.getAttribute("EHOSP_DT"));
        param.setValueParamter(i++, record.getAttribute("RESIGN_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        return this.getDao("boadao").update("tbec_racer_injury_uc001", param);
    }

    /**
     * <p>부상선수정보  입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertRacerIjry(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, String.valueOf(record.getAttribute("IJRY_DT")).substring(0,4));
        param.setValueParamter(i++, String.valueOf(record.getAttribute("IJRY_DT")).substring(0,4));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("IJRY_DT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_DT"));
        param.setValueParamter(i++, record.getAttribute("IJRY_PART"));
        param.setValueParamter(i++, record.getAttribute("IJRY_STAT"));
        param.setValueParamter(i++, record.getAttribute("DIAGNS_WK"));
        param.setValueParamter(i++, record.getAttribute("EHOSP_DT"));
        param.setValueParamter(i++, record.getAttribute("RESIGN_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbec_racer_injury_ic001", param);
    }

    /**
     * <p>부상선수 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteRacerIjry(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ".trim())));
        
        return  this.getDao("boadao").update("tbec_racer_injury_dc001", param);
    }
}