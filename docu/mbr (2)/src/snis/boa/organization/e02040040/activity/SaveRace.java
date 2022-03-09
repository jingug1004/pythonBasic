/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02040040.activity.SaveRace.java
 * 파일설명		: 편성등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010061.activity.SaveProcessProg;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 편성를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveRace extends SnisActivity
{    
    public SaveRace()
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
        
        sDsName = "dsOutRace";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
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
        
        String organType = (String)ctx.get("ORGAN_TYPE");
        

        // 편성 저장
        sDsName = "dsOutRace";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

            if("NEW".equals(organType) || "TEMP".equals(organType)){
            	
    	        for ( int i = 0; i < nSize; i++ ) 
    	        {
    	            PosRecord record = ds.getRecord(i);
    	            
                	if ( (nTempCnt = updateTempOrgan(record, ctx)) == 0 ) {
                    	nTempCnt = insertTempOrgan(record, ctx);
                    }
    	        }            	
		            	
            }else if("REAL".equals(organType)){
            	
    	        for ( int i = 0; i < nSize; i++ ) 
    	        {
    	            PosRecord record = ds.getRecord(i);
    	            
                	if ( (nTempCnt = updateOrgan(record, ctx)) == 0 ) {
                    	nTempCnt = insertOrgan(record, ctx);
                    }
    	        }
            }

	        nSaveCount = nSaveCount + nTempCnt;
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 편성 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateOrgan(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, ctx.get("RACE_DAY     ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ABSE_CD      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD    ".trim())));

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_organ_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 편성 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertOrgan(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setValueParamter(i++, ctx.get("MBR_CD       ".trim()));
        param.setValueParamter(i++, ctx.get("TMS          ".trim()));
        param.setValueParamter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, ctx.get("RACE_DAY     ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ABSE_CD      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD    ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_organ_ib001", param);
        return dmlcount;
    }

    /**
     * <p> 회차상태수정 및 회차별 작업 이력 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, null);
        param.setValueParamter(i++, "040");
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        param.setWhereClauseParameter(i++, ctx.get("TMS")      );
        
        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub003", param);

        SaveProcessProg spp = new SaveProcessProg();
        String sTitle = ctx.get("STND_YEAR") + "년 " + ctx.get("TMS") + "회 " + ctx.get("DAY_ORD") + "일차 편성";
        String sDesc  = ctx.get("STND_YEAR") + "년 " + ctx.get("TMS") + "회 " + ctx.get("DAY_ORD") + "일차 편성";
        
        spp.insertProcessProg("002", (String)ctx.get("STND_YEAR"), (String)ctx.get("TMS"), (String)ctx.get("DAY_ORD"), sTitle, sDesc);         
        
        return dmlcount;
    }
    
    /**
     * <p> 가편성 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTempOrgan(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD    ".trim())));

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ      ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_temp_organ_ub001", param);
        
        return dmlcount;
		
    }

    /**
     * <p> 가편성 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertTempOrgan(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setValueParamter(i++, ctx.get("MBR_CD       ".trim()));
        param.setValueParamter(i++, ctx.get("TMS          ".trim()));
        param.setValueParamter(i++, ctx.get("DAY_ORD      ".trim()));
        
        param.setValueParamter(i++, ctx.get("ORGAN_OWNER_ID".trim()));
        param.setValueParamter(i++, ctx.get("ORGAN_SEQ    ".trim()));
        
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD    ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_temp_organ_ib001", param);
        return dmlcount;
    }    
}
