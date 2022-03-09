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
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchCandRaceDetl extends SnisActivity
{    
	protected String sStndYear      = "";
	
    public SearchCandRaceDetl()
    {
    }

    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	// ��������Ʈ
        PosParameter param = new PosParameter();
        int i = 0;
        //param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DT            ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        //param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        //param.setWhereClauseParameter(i++, ctx.get("DT            ".trim()));

        PosRowSet rowsetCand = this.getDao("candao").find("tbdn_cand_ident_fn101", param);

    	// ���ָ���Ʈ
        PosParameter paramStnd = new PosParameter();
        i = 0;
        paramStnd.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        paramStnd.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));

        PosRowSet rowsetStnd = this.getDao("candao").find("tbdn_cand_race_race_fn001", paramStnd);
        
        // Ȯ������
        PosParameter paramCfm = new PosParameter();
        i = 0;
        paramCfm.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        paramCfm.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
        paramCfm.setWhereClauseParameter(i++, ctx.get("DT            ".trim()));

        PosRowSet rowsetCfm = this.getDao("candao").find("tbdn_cand_race_cfm_fn001", paramCfm);
        

        // ��������Ʈ
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
