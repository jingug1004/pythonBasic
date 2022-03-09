/*================================================================================
 * 시스템			: 선수(공정)관리
 * 소스파일 이름	: snis.boa.racer.e03010010.activity.SaveRacerInfoFair.java
 * 파일설명		: 선수공정정보등록
 * 생성일자		: 2009-01-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03010010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @version 1.0
*/
public class SaveRacerInfoFair extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerInfoFair()
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

        sRacerNo = (String) ctx.get("RACER_NO");
        ds    = (PosDataset) ctx.get("dsOutRacerFair");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	// update
            	nSaveCount = nSaveCount + saveRacer(record);
            }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> 선수상세정보 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacer(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =updateRacer(record);

        return effectedRowCnt;    	
    }

    /**
     * <p> 선수상세_공정정보 등록 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRacer(PosRecord record) 
    {
    	int dmlcount   = 0; 
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_INFO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORTUNE")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH3")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH4")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH5")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CLUB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TWO_JOB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_INFO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORTUNE")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH3")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH4")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH5")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CLUB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TWO_JOB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE")));
        param.setValueParamter(i++, SESSION_USER_ID);
        	
     	//선수상세_공정정보 등록
        dmlcount = this.getDao("boadao").update("tbee_racer_detail_fair_ie001", param);
        
        return dmlcount;  
    }
   
}