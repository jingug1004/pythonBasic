/*================================================================================
 * �ý���			: �л���� 
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d06009028.activity.searchRaceTime.java
 * ���ϼ���		: ���ǰ��� ��ŸƮ�ð��� ���ֳ����� ��ȸ �Ѵ�.. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-04-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06009028.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* ���ǰ��� ��ŸƮ�ð��� ���ֳ��� ��ȸ 
* @auther ������ 
* @version 1.0
*/
public class SearchRaceTime extends SnisActivity
{   
	private int paramIndex = 0;
	public SearchRaceTime()
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
//    	����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}    	
    	getTempAbs(ctx);
    	logger.logDebug("------------------>sadddddddddddddddd:");
    	return PosBizControlConstants.SUCCESS;
    }    
    
    /**
     * ���ǰ��� ��ŸƮ�ð��� ���ֳ��� ��ȸ 
     * @param pRacerPerioNo ���
     * * @param pGdu ��������
     * @return
     */
    public PosRowSet getEduListRowSet(String pRacerPerioNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        return  this.getDao("candao").find("tbdn_cand_race_detlpart_fb002", param);
    }
    
    /**
     * ���ǰ��� ��ŸƮ�ð��� ���ֳ��� ��ȸ 
     * @param ctx
     */
    private void getTempAbs(PosContext ctx){
    	
    	PosRowSet    rowset;
        String	sResultKey = "dsCandList";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");        
        
        PosRowSet prs = getEduListRowSet(pRacerPerioNo);
        PosParameter param = new PosParameter();
        paramIndex = 0;

        StringBuffer sb = new StringBuffer("-- ���ǰ��� ��ŸƮ�ð��� ���ֳ��� ��Ȳ  \n");
        
        sb.append(" SELECT    \n");
        sb.append(" 	  B.CAND_NO,    \n");
        sb.append(" 	  B.NM_KOR,    \n");
        sb.append(" 	  NVL(TOTAL_DT,0) AS TOTAL_DT,    \n");
        sb.append(" 	  ROUND(NVL(RACE_TIME,0),2) AS RACE_TIME,    \n");
        sb.append(" 	  NVL(VIOL_5,0) AS VIOL_5,    \n");
        sb.append(" 	  NVL(VIOL_6,0) AS VIOL_6,    \n");        
        sb.append(" 	  ROUND(DECODE(NVL(TOTAL_DT,0),0,0,(NVL(WIN_RATE,0) /TOTAL_DT)*100)) AS WIN_RATE,    \n");
        sb.append(" 	  ROUND(DECODE(NVL(TOTAL_DT,0),0,0,(NVL(TWO_RATE,0) /TOTAL_DT)*100)) AS TWO_RATE     \n");
        //��������
        genDay(sb, param, prs);
        //sb.append(" 	  ,NVL(RACETIME_1,0) AS RACETIME_1    \n");
        //sb.append(" 	  ,NVL(ARRIVORD_1,0) AS ARRIVORD_1    \n");
        
        sb.append("FROM(    \n");
        sb.append("     SELECT     									\n");
        sb.append("           RACER_PERIO_NO, 	    				\n");
        sb.append("           CAND_NO, 	    						\n");
        sb.append("           COUNT(TEAM_NO) AS TOTAL_DT, 	    	\n");
        sb.append("           NVL(AVG(RACE_TIME),0) AS RACE_TIME,	\n");
        sb.append("           NVL(SUM(VIOL_5),0) AS VIOL_5,	    	\n");
        sb.append("           NVL(SUM(VIOL_6),0) AS VIOL_6, 	   	\n");
        sb.append("           NVL(SUM(WIN_RATE),0) AS WIN_RATE,	   	\n");
        sb.append("           NVL(SUM(TWO_RATE),0) AS TWO_RATE    	\n");
        //��������
        genDaySub(sb, param, prs);
        //sb.append(" 	      ,SUM(RACETIME_1) AS RACETIME_1    \n");
//      sb.append(" 	      ,SUM(ARRIVORD_1) AS ARRIVORD_1    \n");
        
        sb.append("     FROM(    \n");
        sb.append("     	 SELECT     \n");  
        sb.append("           		RACER_PERIO_NO, 	    \n");
        sb.append("           		CAND_NO, 	    \n");
        sb.append("           		ROUND, 	    \n");
        sb.append("           		TEAM_NO, 	    \n");
        sb.append("           		NVL(SUM(RACE_TIME1),0) AS RACE_TIME, 	    \n");
        sb.append("           		CASE WHEN VIOL_CNTNT = '005' THEN COUNT(VIOL_CNTNT) 	    \n");
        sb.append("           		ELSE 0 	    		\n");
        sb.append("           		END AS VIOL_5,	    \n");
        sb.append("           		CASE WHEN VIOL_CNTNT = '006' THEN COUNT(VIOL_CNTNT) 	    \n");
        sb.append("           		ELSE 0 	    		\n");
        sb.append("           		END AS VIOL_6,	    \n");
        sb.append("           		CASE WHEN ARRIV_ORD = 1 THEN COUNT(ARRIV_ORD) ELSE 0 END AS WIN_RATE,	    \n");
        sb.append("           		CASE WHEN ARRIV_ORD = 1 OR ARRIV_ORD = 2 THEN COUNT(ARRIV_ORD) ELSE 0 END AS TWO_RATE	    \n");
        //��������
        genDaySubT(sb, param, prs);
        //sb.append(" 	      	    ,CASE WHEN ROUND = 1 AND TEAM_NO = 1 THEN NVL(SUM(RACE_TIME1),0) ELSE 0 END AS RACETIME_1     \n");
        //sb.append("           	,CASE WHEN ROUND = 1 AND TEAM_NO = 1 THEN NVL(ARRIV_ORD,0) ELSE 0 END AS ARRIVORD_1		    \n");
        
        sb.append("          FROM  TBDN_CAND_RACE_DETL    \n");
        sb.append("          WHERE RACER_PERIO_NO =?   \n");
        param.setWhereClauseParameter(paramIndex++, pRacerPerioNo);
        sb.append("          AND   GBN = '006'    \n");
        sb.append("          GROUP BY RACER_PERIO_NO,CAND_NO, ROUND, TEAM_NO, VIOL_CNTNT, ARRIV_ORD      \n");
        sb.append("         )    \n");
        sb.append("      GROUP BY RACER_PERIO_NO, CAND_NO    \n");
        sb.append("  ) A, TBDN_CAND_IDENT B    \n");
        sb.append(" WHERE  A.RACER_PERIO_NO(+) = B.RACER_PERIO_NO    \n");
        sb.append(" AND    A.CAND_NO(+) = B.CAND_NO    \n");
        sb.append(" AND    B.RACER_PERIO_NO = ?    \n");
        param.setWhereClauseParameter(paramIndex++, pRacerPerioNo);
        sb.append(" ORDER BY B.CAND_NO    \n");

        PosRowSet rowSet =   this.getDao("candao").findByQueryStatement(sb.toString(), param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    /**
     * �κк������ð� ���Ϻ� �ð� QUERY
     * @param sb
     * @param param
     * @param basicDayOrd
     */
    public void genDay(StringBuffer sb, PosParameter param, PosRowSet prs){
    	
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    		sb.append("     ,NVL(RACETIME_").append(i).append(",0) AS RACETIME_").append(i).append(" \n");
	    		sb.append("     ,NVL(ARRIVORD_").append(i).append(",0) AS ARRIVORD_").append(i).append(" \n");
	    	}
    	}
    }
    
    public void genDaySub(StringBuffer sb, PosParameter param, PosRowSet prs){
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    		sb.append("     ,SUM(RACETIME_").append(i).append(") AS RACETIME_").append(i).append(" \n");
	    		sb.append("     ,SUM(ARRIVORD_").append(i).append(") AS ARRIVORD_").append(i).append(" \n");
	    	}
    	}
    }
    
    public void genDaySubT(StringBuffer sb, PosParameter param, PosRowSet prs){
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    		sb.append("     ,CASE WHEN ROUND = ? AND TEAM_NO = ? THEN NVL(SUM(RACE_TIME1),0) ELSE 0 END AS RACETIME_").append(i).append(" \n");
	    		sb.append("     ,CASE WHEN ROUND = ? AND TEAM_NO = ? THEN NVL(ARRIV_ORD,0) ELSE 0 END AS ARRIVORD_").append(i).append(" \n");
	    		param.setWhereClauseParameter(paramIndex++, pr[i-1].getAttribute("ROUND"));
	    		param.setWhereClauseParameter(paramIndex++, pr[i-1].getAttribute("TEAM_NO"));
	    		param.setWhereClauseParameter(paramIndex++, pr[i-1].getAttribute("ROUND"));
	    		param.setWhereClauseParameter(paramIndex++, pr[i-1].getAttribute("TEAM_NO"));
	    	}
    	}
    }
}

