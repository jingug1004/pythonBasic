/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02040060.activity.RegistHomePage.java
 * 파일설명		: 출주표홈페이지등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040060.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 출주표홈페이지를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class RegistHomePage extends SnisActivity
{    
    public RegistHomePage()
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 

    	// 출주표삭제
    	nDeleteCount = deleteRaceDoc(ctx);
    	nSaveCount   = insertRaceDoc(ctx);

    	if ( nSaveCount == 0 ) {
    		Util.setSvcMsg(ctx, "편성작업이 아직 이루어 지지 않았습니다.");
    	}
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 출주표 홈페이지 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRaceDoc(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_STAT_CD".trim()));

        int dmlcount = this.getDao("boadao").delete("tbeb_race_doc_db001", param);
        
        return dmlcount;
    }

    /**
     * <p> 출주표 홈페이지 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRaceDoc(PosContext ctx) 
    {
    	String sDayOrd = (String)ctx.get("DAY_ORD");
    	String sQueryId = "tbeb_race_doc_ib001";
    	
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
        param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
        if ("3".equals(sDayOrd)) {
            param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
            param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
        }
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        //param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        //param.setValueParamter(i++, ctx.get("STND_YEAR"));
        //param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        //param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        //param.setValueParamter(i++, ctx.get("STND_YEAR"));
        //param.setValueParamter(i++, ctx.get("TMS"      ));

        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
        if ("3".equals(sDayOrd)) {
            param.setValueParamter(i++, ctx.get("STND_YEAR"));
            param.setValueParamter(i++, ctx.get("MBR_CD"   ));
            param.setValueParamter(i++, ctx.get("TMS"      ));
            param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
            param.setValueParamter(i++, ctx.get("STND_YEAR"));
            param.setValueParamter(i++, ctx.get("MBR_CD"   ));
            param.setValueParamter(i++, ctx.get("TMS"      ));
            param.setValueParamter(i++, ctx.get("DAY_ORD"  ));            
        }
        
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
        if ("3".equals(sDayOrd)) {
        	sQueryId = "tbeb_race_doc_ib002";
        }
        int dmlcount = this.getDao("boadao").insert(sQueryId, param);
        
        
        // 작업로그 작성
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DAY_ORD"  , ctx.get("DAY_ORD"  ));
        hmProcess.put("DUTY_CD"  , "002");
        
        // 확정, 미확정등록여부
        if ( ctx.get("ORGAN_STAT_CD").equals("001") ) {
            hmProcess.put("WORK_CD"  , "042");
        } else {
            hmProcess.put("WORK_CD"  , "045");
        }

        hmProcess.put("PROG_STAT", "001");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
        
        return dmlcount;
    }
}
