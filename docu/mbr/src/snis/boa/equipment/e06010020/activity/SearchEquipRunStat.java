/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010060.activity.SearchEquipRunStat.java
 * ���ϼ���		: ��� ���� ��Ȳ   
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010020.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* ��� ���� ��Ȳ  
* @auther �輺�� 
* @version 1.0
*/
public class SearchEquipRunStat extends SnisActivity
{   
	private int paramIndex = 0;
    public SearchEquipRunStat()
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
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
		getEquipDayRunStat(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

   
    
    
    /**
     * <p> ��� ���� ��Ȳ ��ȸ ��,ȭ,��,��,��,��,�Ϻ�  </p>
     * @param ctx
     */
    public void getEquipDayRunStat(PosContext ctx)
    {
        String	sResultKey = "dsOutEquipRunStat";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	equipTpeCd   = (String) ctx.get("EQUIP_TPE_CD");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        Integer	minTms =  new Integer((String) ctx.get("MIN_TMS"));
        Integer	maxTms =  new Integer((String) ctx.get("MAX_TMS"));
        String	useYn =  (String) ctx.get("USE_YN");
        PosParameter param = new PosParameter();
        paramIndex = 0;

        StringBuffer sb = new StringBuffer("-- ���Ϻ� ��� ���� ��Ȳ  \n");
        sb.append(" SELECT T2.STND_YEAR, T2.MBR_CD, SUBSTR(T2.EQUIP_NO,1,1)||SUBSTR(T2.EQUIP_NO,6,3) AS EQUIP_NO,   \n");
        //ȸ���� ���� 
        genQueryDayEtcParts(sb);
        //ȸ�� ���� 
        genQueryDayParts(sb, param, minTms.intValue(),  maxTms.intValue());
        sb.append("     NVL(SUM(RUN_CNT),0) AS RUN_TOTAL_CNT \n");
        sb.append(" FROM (  \n");
        sb.append("     SELECT   STND_YEAR, MBR_CD, TMS, DAY, DECODE(?, 'M', MOT_NO, BOAT_NO) AS EQUIP_NO, RUN_CNT FROM (  \n");
        param.setWhereClauseParameter(paramIndex++, equipTpeCd);
        sb.append("         -- 1,2���� ��, ��������   \n");
        sb.append("         SELECT TOR.STND_YEAR, TOR.MBR_CD, TOR.TMS, TOR.DAY_ORD, TRDO.RACE_DAY,TO_CHAR(TO_DATE(TRDO.RACE_DAY, 'YYYYMMDD'), 'DY') AS DAY,  \n"); 
        sb.append("             TOR.MOT_NO, TOR.BOAT_NO, 1 AS RUN_CNT  \n");
        sb.append("         FROM (  \n");
        sb.append("             -- ��������  \n");
        sb.append("             SELECT STND_YEAR, MBR_CD, TMS, DAY_ORD, MOT_NO, BOAT_NO  FROM TBEC_APPO_EXERC_ORGAN  \n");
        sb.append("             WHERE STND_YEAR=?  \n");
        sb.append("             AND MBR_CD LIKE ?||'%'  \n");
        sb.append("             AND DAY_ORD >0  \n");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        sb.append("             UNION ALL  \n");
        sb.append("             -- ��   \n");
        sb.append("             SELECT STND_YEAR, MBR_CD, TMS, DAY_ORD, MOT_NO, BOAT_NO FROM TBEB_ORGAN  \n");
        sb.append("             WHERE STND_YEAR=?  \n");
        sb.append("             AND MBR_CD LIKE ?||'%'  \n");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        sb.append("          )TOR, TBEB_RACE_DAY_ORD TRDO  \n");
        sb.append("          WHERE TOR.STND_YEAR = TRDO.STND_YEAR  \n");
        sb.append("          AND TOR.MBR_CD = TRDO.MBR_CD  \n");
        sb.append("          AND TOR.TMS = TRDO.TMS  \n");
        sb.append("          AND TOR.DAY_ORD = TRDO.DAY_ORD  \n");
        sb.append("          -- 0���� ��������  \n");
        sb.append("         UNION ALL  \n");
        sb.append("         SELECT TAEO.STND_YEAR, TAEO.MBR_CD, TAEO.TMS, TAEO.DAY_ORD,  \n"); 
        sb.append("             TO_CHAR(TO_DATE(TRDO.RACE_DAY, 'YYYYMMDD')-1, 'YYYYMMDD') AS RACE_DAY, TO_CHAR(TO_DATE(TRDO.RACE_DAY, 'YYYYMMDD')-1, 'DY') AS DAY,  \n");
        sb.append("             TAEO.MOT_NO, TAEO.BOAT_NO  , 1 AS RUN_CNT  \n");
        sb.append("         FROM TBEC_APPO_EXERC_ORGAN TAEO, TBEB_RACE_DAY_ORD TRDO  \n");
        sb.append("         WHERE TAEO.STND_YEAR = TRDO.STND_YEAR  \n");
        sb.append("         AND TAEO.MBR_CD = TRDO.MBR_CD  \n");
        sb.append("        AND TAEO.TMS = TRDO.TMS  \n");
        sb.append("         AND TAEO.DAY_ORD = TRDO.DAY_ORD-1  \n");
        sb.append("         AND TAEO.STND_YEAR=?  \n");
        sb.append("         AND TAEO.MBR_CD LIKE ?||'%'  \n");
        sb.append("         AND TAEO.DAY_ORD =0  \n");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        sb.append("        -- ������ ���� ����   \n");
        sb.append("         UNION ALL  \n");
        sb.append("         SELECT STND_YEAR, MBR_CD, TMS, DAY_ORD, RUN_DT AS RACE_DAY, TO_CHAR(TO_DATE(RUN_DT, 'YYYYMMDD'), 'DY') AS DAY, MOT_NO, BOAT_NO, RUN_CNT  \n"); 
        sb.append("         FROM TBEF_EQUIP_UNFIX_RUN  \n");
        sb.append("         WHERE STND_YEAR=?  \n");
        sb.append("         AND MBR_CD LIKE ?||'%'  \n");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        sb.append("     )   \n");
        sb.append(" )T1, (SELECT STND_YEAR, MBR_CD,EQUIP_NO , USE_YN  FROM TBEF_EQUIP WHERE STND_YEAR=? AND MBR_CD LIKE ?||'%' AND EQUIP_TPE_CD=?  AND USE_YN = DECODE(?,NULL,USE_YN, ?)  )T2  \n");
        param.setWhereClauseParameter(paramIndex++, stndYear);
        param.setWhereClauseParameter(paramIndex++, mbrCd);
        param.setWhereClauseParameter(paramIndex++, equipTpeCd);
        param.setWhereClauseParameter(paramIndex++, useYn);
        param.setWhereClauseParameter(paramIndex++, useYn);
        sb.append(" WHERE T2.STND_YEAR = T1.STND_YEAR(+)  \n");
        sb.append(" AND T2.MBR_CD = T1.MBR_CD(+)  \n");
        sb.append(" AND T2.EQUIP_NO = T1.EQUIP_NO(+)  \n");
        sb.append(" GROUP BY T2.STND_YEAR, T2.MBR_CD, T2.EQUIP_NO  \n");
        sb.append(" ORDER BY T2.STND_YEAR, T2.MBR_CD, T2.EQUIP_NO  \n");

        PosRowSet rowSet =   this.getDao("boadao").findByQueryStatement(sb.toString(), param);
        ctx.put(sResultKey, rowSet);
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * ������ �κ� QUERY
     * @param sb StringBuffer
     * @param param	PosParameter
     * @param minTms	���� ȸ��
     * @param maxTms	���� ȸ�� 
     * @param basicDayOrd	��������
     */
    public void genQueryParts(StringBuffer sb, PosParameter param, int minTms, int maxTms, int basicDayOrd){
    	for(int i=minTms; i<=maxTms; i++){
    		for(int j=0; j<=basicDayOrd; j++){
    			sb.append("     SUM(CASE WHEN M1.TMS=? AND M1.DAY_ORD=? THEN M2.RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,j)).append(", \n");
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    			param.setWhereClauseParameter(paramIndex++, new Integer(j));
    		}
    	}
        
    }
    
    /**
     * ���Ϻ� �κ� QUERY
     * @param sb
     * @param param
     * @param minTms
     * @param maxTms
     * @param basicDayOrd
     */
    public void genQueryDayParts(StringBuffer sb, PosParameter param, int minTms, int maxTms){
    	for(int i=minTms; i<=maxTms; i++){
    			sb.append("     SUM(CASE WHEN TMS=? AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,1)).append(", \n");
    			sb.append("     SUM(CASE WHEN TMS=? AND DAY='ȭ' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,2)).append(", \n");
    			sb.append("     SUM(CASE WHEN TMS=? AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,3)).append(", \n");
    			sb.append("     SUM(CASE WHEN TMS=? AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,4)).append(", \n");
    			sb.append("     SUM(CASE WHEN TMS=? AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,5)).append(", \n");
    			sb.append("     SUM(CASE WHEN TMS=? AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,6)).append(", \n");
    			sb.append("     SUM(CASE WHEN TMS=? AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,7)).append(", \n");
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    			param.setWhereClauseParameter(paramIndex++, new Integer(i));
    	}
        
    }
    
    
    public void genQueryDayEtcParts(StringBuffer sb){
    	int i=0;
		sb.append("     SUM(CASE WHEN TMS IS NULL AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,1)).append(", \n");
		sb.append("     SUM(CASE WHEN TMS IS NULL AND DAY='ȭ' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,2)).append(", \n");
		sb.append("     SUM(CASE WHEN TMS IS NULL AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,3)).append(", \n");
		sb.append("     SUM(CASE WHEN TMS IS NULL AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,4)).append(", \n");
		sb.append("     SUM(CASE WHEN TMS IS NULL AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,5)).append(", \n");
		sb.append("     SUM(CASE WHEN TMS IS NULL AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,6)).append(", \n");
		sb.append("     SUM(CASE WHEN TMS IS NULL AND DAY='��' THEN RUN_CNT ELSE 0 END) AS ").append(genColumnAlias(i,7)).append(", \n");
        
    }
    
    /**
     * �÷� Alais ����
     * @param tms
     * @param dayOrd
     * @return
     */
    public StringBuffer genColumnAlias(int tms, int dayOrd){
    	StringBuffer sb = new StringBuffer("");
    	sb.append("TMS_").append(tms).append("_DAY_").append(dayOrd);
    	return sb;
    }
    
    
}

