package snis.can_boa.boatstd.d06010011.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchCandRaceDetl extends SnisActivity
{    
	protected String sStndYear      = "";
	
    public SearchCandRaceDetl()
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
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	// 선수리스트
        PosParameter param = new PosParameter();
        int i = 0;
        //param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DT            ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        //param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        //param.setWhereClauseParameter(i++, ctx.get("DT            ".trim()));

        PosRowSet rowsetCand = this.getDao("candao").find("tbdn_cand_ident_fn101", param);

    	// 경주리스트
        PosParameter paramStnd = new PosParameter();
        i = 0;
        paramStnd.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        paramStnd.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));

        PosRowSet rowsetStnd = this.getDao("candao").find("tbdn_cand_race_race_fn001", paramStnd);
        
        // 확정여부
        PosParameter paramCfm = new PosParameter();
        i = 0;
        paramCfm.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        paramCfm.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
        paramCfm.setWhereClauseParameter(i++, ctx.get("DT            ".trim()));

        PosRowSet rowsetCfm = this.getDao("candao").find("tbdn_cand_race_cfm_fn001", paramCfm);
        

        // 배정리스트
        for ( int j = 0; j < Integer.parseInt((String) ctx.get("ROUND_CNT")); j++ ) {
        	for ( int k = 0; k < 4; k++ ) {
                PosParameter paramDetl = new PosParameter();
                i = 0;
                paramDetl.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
                paramDetl.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
                paramDetl.setWhereClauseParameter(i++, Integer.toString(j + 1));
                paramDetl.setWhereClauseParameter(i++, Integer.toString(k + 1));
                paramDetl.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
                paramDetl.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
                paramDetl.setWhereClauseParameter(i++, Integer.toString(j + 1));
                paramDetl.setWhereClauseParameter(i++, Integer.toString(k + 1));
                paramDetl.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
                paramDetl.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
                paramDetl.setWhereClauseParameter(i++, Integer.toString(j + 1));
                paramDetl.setWhereClauseParameter(i++, Integer.toString(k + 1));

                PosRowSet rowsetDetl = this.getDao("candao").find("tbdn_cand_race_organ_fn001", paramDetl);
                
                PosRow temprows[] = rowsetDetl.getAllRow();
                if ( temprows.length != 6 ) {
                	PosColumnDef[] columnDef = rowsetDetl.getColumnDefs();
                	
    	        	List rowList = new java.util.ArrayList();

    	        	int nRaceRegNo = 1;
                    int nRow       = 0;
    	        	
    	        	while(nRaceRegNo <= 6) {
            			if ( temprows.length <= nRow ) {
        	        		addRaceRegNo(rowList, (j + 1), (k + 1), nRaceRegNo);
            			} else if ( nRaceRegNo == ((BigDecimal) temprows[nRow].getAttribute("RACE_REG_NO")).intValue() ) {
            				rowList.add(temprows[nRow]);
            				nRow++;
            			} else {
        	        		addRaceRegNo(rowList, (j + 1), (k + 1), nRaceRegNo);
            			}
            			nRaceRegNo++;
            		}
            		
    	        	rowsetDetl = new PosRowSetImpl(rowList);
    	        	rowsetDetl.setColumnDefs(columnDef);
                }
                
                String sResultKey = "dsOutCandRaceDetl_" + (j + 1) + "R_" + (k + 1) + "T";
                ctx.put(sResultKey, rowsetDetl);
                Util.addResultKey(ctx, sResultKey);
        	}
        }
        
        String sResultKey = "dsOutCand";
        ctx.put(sResultKey, rowsetCand);
        Util.addResultKey(ctx, sResultKey);

        sResultKey = "dsOutStnd";
        ctx.put(sResultKey, rowsetStnd);
        Util.addResultKey(ctx, sResultKey);
        
        sResultKey = "dsCfm";
        ctx.put(sResultKey, rowsetCfm);
        Util.addResultKey(ctx, sResultKey);
        
        
    }

    protected void addRaceRegNo(List raceList, int nRound, int nTeamNo, int nRaceRegNo) 
    {
    	Map rowMap = new HashMap();
		rowMap.put("ROUND"      , Integer.toString(nRound    ));
		rowMap.put("TEAM_NO"    , Integer.toString(nTeamNo   ));
		rowMap.put("RACE_REG_NO", Integer.toString(nRaceRegNo));
    	PosRow row = new PosRowImpl(rowMap);
    	raceList.add(row);
    }
}
