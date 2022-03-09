/*================================================================================
 * �ý���			: �л���� 
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d06009027.activity.SearchResultList.java
 * ���ϼ���		: ��ŸƮ���� ��ŸƮ�ð��� ��ȸ �Ѵ�.. 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-04-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06009027.activity;

import snis.can_boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.can_boa.common.util.Util;

/**
* ��ŸƮ���� ��ŸƮ�ð� ��ȸ 
* @auther ������ 
* @version 1.0
*/
public class SearchResultList extends SnisActivity
{   
	private int paramIndex = 0;
	public SearchResultList()
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
    	
    	searchState(ctx);

    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ��ŸƮ���� ��ŸƮ�ð� ��ȸ  </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void searchState(PosContext ctx)
    {
        //PosRowSet    rowset;
        //String	sResultKey = "dsCandListResult";
        //String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        //String	pGdu   = (String) ctx.get("pGdu");
       // rowset = getEduListRowSet(pRacerPerioNo,pGdu);
            	
        //ctx.put(sResultKey, rowset);
        //Util.addResultKey(ctx, sResultKey);
        
        getTempAbs(ctx);
    }
    
    /**
     * ��ŸƮ���� ��ŸƮ�ð� ��ȸ 
     * @param pRacerPerioNo ���
     * * @param pGdu ��������
     * @return
     */
    public PosRowSet getEduListRowSet(String pRacerPerioNo, String pGdu)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, pRacerPerioNo);
        param.setWhereClauseParameter(i++, pGdu);
        return  this.getDao("candao").find("tbdn_cand_race_detlpart_fb001", param);
    }
    
    /**
     * ��ŸƮ���� ��ŸƮ�ð� ��ȸ 
     * @param ctx
     */
    private void getTempAbs(PosContext ctx){
    	
    	PosRowSet    rowset;
        String	sResultKey = "dsCandList";
        String	pRacerPerioNo   = (String) ctx.get("pRacerPerioNo");
        String	pGdu   = (String) ctx.get("pGdu");
        
        PosRowSet prs = getEduListRowSet(pRacerPerioNo,pGdu);
        PosParameter param = new PosParameter();
        paramIndex = 0;

        StringBuffer sb = new StringBuffer("-- ��ŸƮ���� ��ŸƮ�ð� ��Ȳ  \n");
        
        
        sb.append(" SELECT    \n");
        sb.append(" 	  B.CAND_NO,    \n");
        sb.append(" 	  B.NM_KOR,    \n");
        sb.append(" 	  NVL(TOTAL_DT,0) AS TOTAL_DT,    \n");
        sb.append(" 	  NVL(RACE_TIME,0) AS RACE_TIME,    \n");
        sb.append(" 	  NVL(VIOL_5,0) AS VIOL_5,    \n");
        sb.append(" 	  NVL(VIOL_6,0) AS VIOL_6    \n");
        //��������
        genDay(sb, param, prs);
        //sb.append(" 	  NVL(DAY_1,0) AS DAY_1,    \n");
        
        sb.append("FROM(    \n");
        sb.append("     SELECT     									\n");
        sb.append("           RACER_PERIO_NO, 	    				\n");
        sb.append("           CAND_NO, 	    						\n");
        sb.append("           COUNT(TOTAL_DT) AS TOTAL_DT,	    	\n");
        sb.append("           NVL(AVG(RACE_TIME),0) AS RACE_TIME,	\n");
        sb.append("           NVL(SUM(VIOL_5),0) AS VIOL_5,	    	\n");
        sb.append("           NVL(SUM(VIOL_6),0) AS VIOL_6 	    	\n");
        //��������
        genDaySub(sb, param, prs);
        //sb.append(" 	      SUM(DAY_1) AS DAY_1,    \n");
        
        sb.append("     FROM(    \n");
        sb.append("     	 SELECT     \n");  
        sb.append("           		RACER_PERIO_NO, 	    \n");
        sb.append("           		CAND_NO, 	    \n");
        sb.append("           		DT AS TOTAL_DT, 	    \n");
        //sb.append("           		NVL(SUM(to_number(SUBSTR(boat_time,0,2))* 60 +to_number(SUBSTR(boat_time,3,2))),0) AS RACE_TIME, 	    \n");
        sb.append("           		NVL(SUM(to_number(boat_time)),0) AS RACE_TIME, 	    \n");
        sb.append("           		CASE WHEN VIOL_CNTNT = '005' THEN COUNT(VIOL_CNTNT) 	    \n");
        sb.append("           		ELSE 0 	    		\n");
        sb.append("           		END AS VIOL_5,	    \n");
        sb.append("           		CASE WHEN VIOL_CNTNT = '006' THEN COUNT(VIOL_CNTNT) 	    \n");
        sb.append("           		ELSE 0 	    		\n");
        sb.append("           		END AS VIOL_6	    \n");
        //��������
        genDaySubT(sb, param, prs);
        //sb.append(" 	      	    CASE WHEN DT = '20080103' THEN NVL(SUM(to_number(SUBSTR(boat_time,0,2))* 60 +to_number(SUBSTR(boat_time,3,2))),0)    \n");
        //sb.append("           		ELSE 0 END AS DAY_1	    \n");
        
        sb.append("          FROM  TBDN_CAND_RACE_DETL    \n");
        sb.append("          WHERE RACER_PERIO_NO =?   \n");
        param.setWhereClauseParameter(paramIndex++, pRacerPerioNo);
        sb.append("          AND   GBN = ?    \n");
        param.setWhereClauseParameter(paramIndex++, pGdu);
        sb.append("          GROUP BY RACER_PERIO_NO,CAND_NO, DT, VIOL_CNTNT    \n");
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
	    		//sb.append("     ,NVL(DAY_1,0) AS DAY_").append(i).append(" \n");
	    		sb.append("     ,NVL(DAY_").append(i).append(",0) AS DAY_").append(i).append(" \n");
	    	}
    	}
    }
    
    public void genDaySub(StringBuffer sb, PosParameter param, PosRowSet prs){
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    			sb.append("     ,SUM(DAY_").append(i).append(") AS DAY_").append(i).append(" \n");
	    	}
    	}
    }
    
    public void genDaySubT(StringBuffer sb, PosParameter param, PosRowSet prs){
    	PosRow[] pr = prs.getAllRow();
    	if(pr.length>0){
	    	for(int i=1; i<=pr.length; i++){
	    			//sb.append("     ,CASE WHEN DT = ? THEN NVL(SUM(to_number(SUBSTR(boat_time,0,2))* 60 +to_number(SUBSTR(boat_time,3,2))),0) ELSE 0 END AS DAY_").append(i).append(" \n");
	    			sb.append("     ,CASE WHEN DT = ? THEN NVL(SUM(to_number(boat_time)),0) ELSE 0 END AS DAY_").append(i).append(" \n");
	    			param.setWhereClauseParameter(paramIndex++, (String)pr[i-1].getAttribute("DT"));
	    	}
    	}
    }
}

