/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02040060.activity.SearchRaceSchedule.java
 * 파일설명		: 출주표조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 출주표조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRaceSchedule extends SnisActivity
{    
	
    public SearchRaceSchedule()
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 조회시작 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	PosParameter param = new PosParameter();
    	int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_fb002", param);

        // 경주 수 만큼 경주내용을 조회한다.
        while ( rowset.hasNext() )
        {
        	PosRow rowTms = rowset.next();
        	ctx.put("RACE_NO", rowTms.getAttribute("RACE_NO"));
        	if ( ctx.get("DB_SERVER") != null && ctx.get("DB_SERVER").equals("1") ) {
        		searchRaceHomepage(ctx);
        	} else {
        		searchRace(ctx);
        	}
        }
	}
    
    /**
     * <p> 출주표 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected void searchRace(PosContext ctx) 
    {
        PosParameter paramRace = new PosParameter();
        PosRowSet rowsetRace = null; 
        int i = 0;
        
        String sQueryId = "tbeb_organ_fb007";
        String sDayOrd = (String)ctx.get("DAY_ORD");
        if ("3".equals(sDayOrd)) sQueryId = "tbeb_organ_fb007_3";
        
        // 출주표 조회
        // 출주표 조회
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        //paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        //paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        //paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        //paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        //paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        //paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        if ("3".equals(sDayOrd)) {
        	paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        }
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"  ));
        
        rowsetRace = this.getDao("boadao").find(sQueryId, paramRace);

        String sResultKey = "dsOutRace" + Integer.parseInt((String) ctx.get("RACE_NO"  ));
        ctx.put(sResultKey, rowsetRace);
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> 홈페이지에 등록된 출주표 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected void searchRaceHomepage(PosContext ctx) 
    {
        PosParameter paramRace = new PosParameter();
        PosRowSet rowsetRace = null; 
        int i = 0;
        
        // 출주횟수 조회
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"    ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"       ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"          ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_STAT_CD"));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"      ));
        
        rowsetRace = this.getDao("boadao").find("tbeb_race_doc_fb001", paramRace);

        String sResultKey = "dsOutRace" + Integer.parseInt((String) ctx.get("RACE_NO"  ));
        ctx.put(sResultKey, rowsetRace);
        Util.addResultKey(ctx, sResultKey);    
    }
}
