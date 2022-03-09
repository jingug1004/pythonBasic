/*================================================================================
 * 시스템			: 시스템관리
 * 소스파일 이름	: snis.boa.system.e01010220.activity.SaveProcess.java
 * 파일설명		: 진행상황등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.system.e01010220.activity;

import java.math.BigDecimal;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveProcess extends SnisActivity
{    
    public SaveProcess()
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

        // 업무진행현황비고 저장
        ds    = (PosDataset)ctx.get("dsOutProcess");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
            {
                // update
            	nSaveCount = nSaveCount + updateProcess(record);
            }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    public int updateProcess(PosRecord record)
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PROG_DESC"));

        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"   ));
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"   ));

    	int dmlcount = this.getDao("boadao").insert("tbea_duty_prs_hst_ua001", param);
        return dmlcount;
    }

    /**
     * <p> Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    public int saveProcess(String sStndYear
				        	, String sMbrCd   
				        	, String sMonth     
				        	, String sTms     
				        	, String sDayOrd  
				        	, String sRaceNo  
				        	, String sDutyCd  
				        	, String sWorkCd  
				        	, String sProgStat
				        	, String sProgDesc
				        	, String sUserId)
    {
        PosParameter paramSeq = new PosParameter();
    	
        int i = 0;
        paramSeq.setWhereClauseParameter(i++, Util.trim(sStndYear));
        paramSeq.setWhereClauseParameter(i++, Util.trim(sMbrCd   ));
    	
        PosRowSet rowsetSeq = this.getDao("boadao").find("tbea_duty_prs_hst_fa000", paramSeq);
        PosRow row = (PosRow) rowsetSeq.next();
        BigDecimal nSeqNo = (BigDecimal) row.getAttribute("SEQ_NO");
        
        PosParameter param = new PosParameter();
        
        i = 0;
        param.setValueParamter(i++, Util.trim(sStndYear));
        param.setValueParamter(i++, Util.trim(sMbrCd   ));
        param.setValueParamter(i++, nSeqNo              );
        param.setValueParamter(i++, Util.trim(sMonth   ));
        param.setValueParamter(i++, Util.trim(sTms     ));
        param.setValueParamter(i++, Util.trim(sDayOrd  ));
        param.setValueParamter(i++, Util.trim(sRaceNo  ));
        param.setValueParamter(i++, Util.trim(sDutyCd  ));
        param.setValueParamter(i++, Util.trim(sWorkCd  ));
        param.setValueParamter(i++, Util.trim(sProgStat));
        param.setValueParamter(i++, Util.trim(sProgDesc));
        param.setValueParamter(i++, Util.NVL(sUserId, SESSION_USER_ID) );

        int dmlcount = this.getDao("boadao").insert("tbea_duty_prs_hst_ia001", param);
        return dmlcount;
    }


    /**
     * <p> Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    public int saveProcess(HashMap hmProcess)
    {
    	return saveProcess(   Util.trim(hmProcess.get("STND_YEAR".trim()))
			                , Util.trim(hmProcess.get("MBR_CD   ".trim()))
			                , Util.trim(hmProcess.get("MONTH    ".trim()))
			                , Util.trim(hmProcess.get("TMS      ".trim()))
			                , Util.trim(hmProcess.get("DAY_ORD  ".trim()))
			                , Util.trim(hmProcess.get("RACE_NO  ".trim()))
			                , Util.trim(hmProcess.get("DUTY_CD  ".trim()))
			                , Util.trim(hmProcess.get("WORK_CD  ".trim()))
			                , Util.trim(hmProcess.get("PROG_STAT".trim()))
			                , Util.trim(hmProcess.get("PROG_DESC".trim()))
			                , Util.trim(hmProcess.get("USER_ID  ".trim())));
    }

	/**
     * <p> 경주회차 상태 조회   </p>
     * @param   PosGenericDao dao			DB Connect 정보
     *          String        sStndYear		경주년도
     *          String        sMbrCd		경정장코드
     *          String        sTms			회차
     * @return  sRaceTmsSTatCd	String		회차상태 
     * @throws  none
     */
    public String getRaceTmsStatCd(String sStndYear, String sMbrCd, String sTms)
    {
        String sRaceTmsSTatCd = "";
        PosParameter param = new PosParameter();

        int i = 0;
		param.setWhereClauseParameter(i++, sStndYear); 	// 경주년도
        param.setWhereClauseParameter(i++, sMbrCd); 	// 경정자코드
        param.setWhereClauseParameter(i++, sTms); 		// 회차
        
        PosRowSet keyRecord = this.getDao("boadao").find("tbeb_race_tms_fb003", param);        

        PosRow pr[] = keyRecord.getAllRow();        
        sRaceTmsSTatCd = String.valueOf(pr[0].getAttribute("RACE_TMS_STAT_CD"));
        
        return sRaceTmsSTatCd;
    }

	/**
     * <p> 경주회차 모터추첨대상 상태 조회   </p>
     * @param   PosGenericDao dao			DB Connect 정보
     *          String        sStndYear		경주년도
     *          String        sMbrCd		경정장코드
     *          String        sTms			회차
     * @return  sRaceTmsSTatCd	String		회차상태 
     * @throws  none
     */
    public String getEquipDrwltCmplYn(String sStndYear, String sMbrCd, String sTms)
    {
        String sRaceTmsSTatCd = "";
        PosParameter param = new PosParameter();

        int i = 0;
		param.setWhereClauseParameter(i++, sStndYear); 	// 경주년도
        param.setWhereClauseParameter(i++, sMbrCd); 	// 경정자코드
        param.setWhereClauseParameter(i++, sTms); 		// 회차
        
        PosRowSet keyRecord = this.getDao("boadao").find("tbeb_race_tms_fb004", param);        

        PosRow pr[] = keyRecord.getAllRow();        
        sRaceTmsSTatCd = String.valueOf(pr[0].getAttribute("EQUIP_DRWLT_CMPL_YN"));
        
        return sRaceTmsSTatCd;
    }
}
