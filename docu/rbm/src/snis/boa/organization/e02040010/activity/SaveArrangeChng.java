/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030030.activity.SaveArrangeChng.java
 * 파일설명		: 선수/장비변경등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040010.activity;

import java.math.BigDecimal;

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
* 매핑하여 선수/장비변경를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveArrangeChng extends SnisActivity
{    
	private String sStndYear = "";
	private String sMbrCd    = "";
	private String sTms      = "";
	private String sDayOrd   = "";
	private int    nSeq      = 0;
	
    public SaveArrangeChng()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutArrange";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
        sDayOrd   = Util.nullToStr((String) ctx.get("DAY_ORD  ".trim()));
        sDayOrd   = "0";
        
        // 선수/장비변경 저장
        sDsName = "dsOutArrange";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
	        nSeq = getArrangeChngSeq();
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	nTempCnt = updateArrange(record, ctx);
            	if (nTempCnt == 0) {
            		// 기존에 존재하는 자료가 없음(배정등록을 다시 하거나 수정된 경우임)
	    			throw new RuntimeException("모터/보트 정보가 수정되었습니다.\n다시 조회후 작업하세요!");
            	}
            	nTempCnt = updateRacerRaceAlloc(record);
            	nTempCnt = insertArrangeChg(record);
            	nTempCnt = updateEquipLot(record);

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 변경이력 일련번호 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int getArrangeChngSeq()
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, sDayOrd  );
        PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_chg_fb001", param);

        BigDecimal nSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nSeq = (BigDecimal) row.getAttribute("SEQ".trim());
        }
            
        return nSeq.intValue();
    }

    /**
     * <p> 주선선수 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateArrange(PosRecord record, PosContext ctx)
    {
    	//
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO ".trim())));
        
        if ( record.getAttribute("SPEC").equals("1")) {
        	param.setValueParamter(i++, ctx.get("SPEC_CD"));
        } else {
        	param.setValueParamter(i++, null);
        }
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ONLINE_YN ".trim())));

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_MOT_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_BOAT_NO".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_arrange_ub002", param);

        return dmlcount;
    }

    /**
     * <p> 주선선수출주횟수 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRacerRaceAlloc(PosRecord record)
    {
    	int nUpdate = 0;
    	for ( int j = 1; j <= 7; j++ ) {
    		String sOnlineYn = (String)Util.trim(record.getAttribute("ONLINE_YN"));
    		String sRaceCnt  = (Util.trim(record.getAttribute("RACE_CNT_" + j))).toString();
    		String sOnlineCnt  = "0";
    		if("1".equals(sOnlineYn))	//온라인이 체크되었을 경우 
    			sOnlineCnt = sRaceCnt;

    		PosParameter paramAlloc = new PosParameter();
            int i = 0;
            paramAlloc.setValueParamter(i++, sRaceCnt);
            paramAlloc.setValueParamter(i++, sStndYear);
            paramAlloc.setValueParamter(i++, sMbrCd   );
            paramAlloc.setValueParamter(i++, sTms     );
            paramAlloc.setValueParamter(i++, Integer.toString(j));
            paramAlloc.setValueParamter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));
            
            paramAlloc.setValueParamter(i++, sOnlineCnt);
            paramAlloc.setValueParamter(i++, sStndYear);
            paramAlloc.setValueParamter(i++, sMbrCd   );
            paramAlloc.setValueParamter(i++, sTms     );
            paramAlloc.setValueParamter(i++, Integer.toString(j));
            paramAlloc.setValueParamter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));

            paramAlloc.setValueParamter(i++, SESSION_USER_ID );
            i = 0;
            paramAlloc.setWhereClauseParameter(i++, sStndYear);
            paramAlloc.setWhereClauseParameter(i++, sMbrCd   );
            paramAlloc.setWhereClauseParameter(i++, sTms     );
            paramAlloc.setWhereClauseParameter(i++, Integer.toString(j));
            paramAlloc.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));

            nUpdate += this.getDao("boadao").update("tbeb_racer_race_alloc_ub003", paramAlloc);
    	}
    	
    	return nUpdate;
/*
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, null     );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_race_alloc_ub001", param);
        
        return dmlcount;
 */
    }

    /**
     * <p> 변경이력 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertArrangeChg(PosRecord record) 
    {
    	int dmlcount = 0;
    	if ( record.getAttribute("ORG_RACER_NO") != null && record.getAttribute("RACER_NO") != null ) {
	    	if ( !record.getAttribute("ORG_RACER_NO").equals(record.getAttribute("RACER_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, sStndYear);
		        param.setValueParamter(i++, sMbrCd   );
		        param.setValueParamter(i++, sTms     );
		        param.setValueParamter(i++, sDayOrd  );
		        param.setValueParamter(i++, Integer.toString(nSeq++));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO   ".trim())));
		        param.setValueParamter(i++, "R"                                                    );
		        param.setValueParamter(i++, Util.trim(record.getAttribute("ORG_RACER_NO  ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_RSN     ".trim())));
		        param.setValueParamter(i++, SESSION_USER_ID );
		        param.setValueParamter(i++, SESSION_USER_ID );
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_chg_ib001", param);
		        nSeq++;
	    	}
    	}
    	
    	if ( record.getAttribute("ORG_MOT_NO") != null && record.getAttribute("MOT_NO") != null ) {
        	if ( !record.getAttribute("ORG_MOT_NO").equals(record.getAttribute("MOT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, sStndYear);
		        param.setValueParamter(i++, sMbrCd   );
		        param.setValueParamter(i++, sTms     );
		        param.setValueParamter(i++, sDayOrd  );
		        param.setValueParamter(i++, Integer.toString(nSeq++));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO   ".trim())));
		        param.setValueParamter(i++, "M"                                                    );
		        param.setValueParamter(i++, Util.trim(record.getAttribute("ORG_MOT_NO    ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO        ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_RSN       ".trim())));
		        param.setValueParamter(i++, SESSION_USER_ID );
		        param.setValueParamter(i++, SESSION_USER_ID );
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_chg_ib001", param);
		        nSeq++;
	    	}
    	}

    	if ( record.getAttribute("ORG_BOAT_NO") != null && record.getAttribute("BOAT_NO") != null ) {
        	if ( !record.getAttribute("ORG_BOAT_NO").equals(record.getAttribute("BOAT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, sStndYear);
		        param.setValueParamter(i++, sMbrCd   );
		        param.setValueParamter(i++, sTms     );
		        param.setValueParamter(i++, sDayOrd  );
		        param.setValueParamter(i++, Integer.toString(nSeq++));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO   ".trim())));
		        param.setValueParamter(i++, "B"                                                    );
		        param.setValueParamter(i++, Util.trim(record.getAttribute("ORG_BOAT_NO   ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_RSN      ".trim())));
		        param.setValueParamter(i++, SESSION_USER_ID );
		        param.setValueParamter(i++, SESSION_USER_ID );
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_chg_ib001", param);
		        nSeq++;
	    	}
    	}
		
    	return dmlcount;
    }

    /**
     * <p> 장비변경 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateEquipLot(PosRecord record) 
    {
    	int dmlcount = 0;
    	// 모터 변경 저장
    	if ( record.getAttribute("ORG_MOT_NO") != null && record.getAttribute("MOT_NO") != null ) {
	    	if ( !record.getAttribute("ORG_MOT_NO").equals(record.getAttribute("MOT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        param.setWhereClauseParameter(i++, sStndYear);
		        param.setWhereClauseParameter(i++, sMbrCd   );
		        param.setWhereClauseParameter(i++, sTms     );
		        param.setWhereClauseParameter(i++, "M"      );
		        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MOT_NO      ".trim())));
	
		        dmlcount = this.getDao("boadao").update("tbef_equip_lot_ub001", param);
	    	}
    	}

    	// 보트 변경 저장
    	if ( record.getAttribute("ORG_BOAT_NO") != null && record.getAttribute("BOAT_NO") != null ) {
	    	if ( !record.getAttribute("ORG_BOAT_NO").equals(record.getAttribute("BOAT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        param.setWhereClauseParameter(i++, sStndYear);
		        param.setWhereClauseParameter(i++, sMbrCd   );
		        param.setWhereClauseParameter(i++, sTms     );
		        param.setWhereClauseParameter(i++, "B"      );
		        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("BOAT_NO     ".trim())));
	
		        dmlcount = this.getDao("boadao").update("tbef_equip_lot_ub001", param);
	    	}
    	}

    	return dmlcount;
    }
}
