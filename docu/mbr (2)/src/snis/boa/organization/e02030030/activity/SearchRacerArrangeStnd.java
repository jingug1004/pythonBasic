/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SearchRacerArrangeStnd.java
 * ���ϼ���		: �ּ���������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ּ��������� ��ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRacerArrangeStnd extends SnisActivity
{    
    public SearchRacerArrangeStnd()
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
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	PosRowSet rowsetRacer = null;
        PosColumnDef columnDef[] = null;
    	
    	PosParameter param = new PosParameter();
        int i = 0;

        // �ּ����� ��ȸ
        param.setWhereClauseParameter(i++, ctx.get("CFRNT_CD"));
        PosRowSet rowset = this.getDao("boadao").find("tbeb_racer_arrange_stnd_fb001", param);
    	
        String sSeltStnd = "";
    	if ( rowset.hasNext() ) {
    		PosRow row = rowset.next();
    		sSeltStnd = (String) row.getAttribute("SELT_STND_CD");
    	}
    	
    	if ( sSeltStnd.equals("002")) {
            // ������ ��������
	    	ctx.put("SEQ"         , "60");
	    	rowsetRacer = GetRacerRecd(ctx);
	        columnDef = rowsetRacer.getColumnDefs();
    	} else {
            // ����ȸ���� ��������
    		// �� ��޺� ����Ƚ���� ���� �������� ������ ���� ����
    		// A1�� 12��
	    	ctx.put("RACER_GRD_CD", "A1");
	    	ctx.put("SEQ"         , "12");
	    	PosRowSet rowsetRacerA1 = GetRacerArrange(ctx, null);
	        columnDef = rowsetRacerA1.getColumnDefs();
	
	        rowsetRacer = addRowSet(rowsetRacer, rowsetRacerA1);
	    	
    		// A2�� 12��
	    	ctx.put("RACER_GRD_CD", "A2");
	    	ctx.put("SEQ"         , "12");
	    	PosRowSet rowsetRacerA2 = GetRacerArrange(ctx, null);
	    	rowsetRacer = addRowSet(rowsetRacer, rowsetRacerA2);
	    	
    		// B1�� 18��
	    	ctx.put("RACER_GRD_CD", "B1");
	    	ctx.put("SEQ"         , "18");
	    	PosRowSet rowsetRacerB1 = GetRacerArrange(ctx, null);
	    	rowsetRacer = addRowSet(rowsetRacer, rowsetRacerB1);
	    	
	    	
    		// B2�� 18��(9ȸ�� �������� 12��)
	    	ctx.put("SEQ"         , "18");
	    	if ( Integer.parseInt((String) ctx.get("TMS")) <= 9 ) {
		    	ctx.put("SEQ"         , "12");
	    	}
	    	ctx.put("RACER_GRD_CD", "B2");
	    	PosRowSet rowsetRacerB2 = GetRacerArrange(ctx, null);
	    	rowsetRacer = addRowSet(rowsetRacer, rowsetRacerB2);
	    	
    		// 9ȸ�� �������� ���μ��� 6�� ����
	    	if ( Integer.parseInt((String) ctx.get("TMS")) <= 9 ) {
		    	ctx.put("SEQ"         , "6");
		    	ctx.put("RACER_GRD_CD", "");
		    	ctx.put("STAT"        , "002");
		    	PosRowSet rowsetRacerEtc = GetRacerArrange(ctx, rowsetRacer);
		    	rowsetRacer = addRowSet(rowsetRacer, rowsetRacerEtc);
	    	}
    	}
    	
    	rowsetRacer.setColumnDefs(columnDef);
        String sResultKey = "dsOutArrangeRacer";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> ��޺� ���� Ƚ���� ���� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected PosRowSet GetRacerArrange(PosContext ctx, PosRowSet rowset)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT                                                                                                                                                 ");
    	sbSql.append("\n          RACER_NO                                                                                                                                      ");
    	sbSql.append("\n        , RACER_GRD_CD                                                                                                                                  ");
    	sbSql.append("\n        , REST_RUN_CNT                                                                                                                                  ");
    	sbSql.append("\n        , RACE_ALLOC_CNT                                                                                                                                ");
    	sbSql.append("\n        , RANK                                                                                                                                          ");
    	sbSql.append("\n        , ROWNUM SEQ                                                                                                                                    ");
    	sbSql.append("\n FROM   (                                                                                                                                               ");
    	sbSql.append("\n             SELECT                                                                                                                                     ");
    	sbSql.append("\n                      RACER_NO                                                                                                                          ");
    	sbSql.append("\n                    , RACER_GRD_CD                                                                                                                      ");
    	sbSql.append("\n                    , REST_RUN_CNT                                                                                                                      ");
    	sbSql.append("\n                    , RACE_ALLOC_CNT                                                                                                                    ");
    	sbSql.append("\n                    , RANK                                                                                                                              ");
    	sbSql.append("\n                    , ROWNUM SEQ                                                                                                                        ");
    	sbSql.append("\n             FROM   (                                                                                                                                   ");
    	sbSql.append("\n                         SELECT                                                                                                                         ");
    	sbSql.append("\n                                  TRM.RACER_NO                                                                                                          ");
    	sbSql.append("\n                                , TRM.RACER_GRD_CD                                                                                                      ");
    	sbSql.append("\n                                , TOR.REST_RUN_CNT                                                                                                      ");
    	sbSql.append("\n                                , TOR.RACE_ALLOC_CNT                                                                                                    ");
    	sbSql.append("\n                                , RANK() OVER (PARTITION BY TRM.RACER_GRD_CD ORDER BY TOR.REST_RUN_CNT DESC ) RANK                                      ");
    	sbSql.append("\n                         FROM     TBEC_RACER_MASTER TRM                                                                                                 ");
    	sbSql.append("\n                                , (                                                                                                                     ");
    	sbSql.append("\n                         			SELECT                                                                                                              ");
    	sbSql.append("\n                         			          TPM  .RACER_NO                                                                                            ");
    	sbSql.append("\n                         			        , NVL(TRBRA.RACE_ALLOC_CNT, NVL(TSRC.STND_RACE_CNT, 0)) RACE_ALLOC_CNT                                      ");
    	sbSql.append("\n                         			        , NVL((NVL(TRBRA.RACE_ALLOC_CNT, NVL(TSRC.STND_RACE_CNT, 0)) - NVL(RUN_CNT, 0)), 0) REST_RUN_CNT            ");
    	sbSql.append("\n                         			        , NVL(TOR  .RUN_CNT  , 0) RUN_CNT                                                                           ");
    	sbSql.append("\n                         			FROM      TBEC_RACER_MASTER        TPM                                                                              ");
    	sbSql.append("\n                         			        , (                                                                                                         ");
    	sbSql.append("\n                         			            SELECT                                                                                                  ");
    	sbSql.append("\n                         			                     TSRC.RACER_GRD_CD                                                                              ");
    	sbSql.append("\n                         			                   , TSRC.STND_RACE_CNT                                                                             ");
    	sbSql.append("\n                         			            FROM   TBEB_STND_RACE_CNT        TSRC                                                                   ");
    	sbSql.append("\n                         			            WHERE  TSRC.STND_YEAR    = ?                                                                            ");
    	sbSql.append("\n                         			            AND    TSRC.QURT_CD      = ?                                                                            ");
    	sbSql.append("\n                         			          ) TSRC                                                                                                    ");
    	sbSql.append("\n                         			        , (                                                                                                         ");
    	sbSql.append("\n                         			            SELECT                                                                                                  ");
    	sbSql.append("\n                         			                     TRBRA.RACER_NO                                                                                 ");
    	sbSql.append("\n                         			                   , TRBRA.RACE_ALLOC_CNT                                                                           ");
    	sbSql.append("\n                         			            FROM   TBEB_RACER_BAC_RACE_ALLOC TRBRA                                                                  ");
    	sbSql.append("\n                         			            WHERE  TRBRA.STND_YEAR   = ?                                                                            ");
    	sbSql.append("\n                         			        	AND    TRBRA.QURT_CD     = ?                                                                            ");
    	sbSql.append("\n                         			          ) TRBRA                                                                                                   ");
    	sbSql.append("\n                         			        , (                                                                                                         ");
    	sbSql.append("\n                                                 SELECT                                                                                                 ");
    	sbSql.append("\n                                                          TOR.RACER_NO                                                                                  ");
    	sbSql.append("\n                                                        , COUNT(*) RUN_CNT                                                                              ");
    	sbSql.append("\n                                                 FROM     TBEB_RACE_TMS   TRT                                                                           ");
    	sbSql.append("\n                                                        , TBEB_ORGAN      TOR                                                                           ");
    	sbSql.append("\n                                                 WHERE  TRT.STND_YEAR   = TOR.STND_YEAR                                                                 ");
    	sbSql.append("\n                                                 AND    TRT.MBR_CD      = TOR.MBR_CD                                                                    ");
    	sbSql.append("\n                                                 AND    TRT.TMS         = TOR.TMS                                                                       ");
    	sbSql.append("\n                                                 AND    TRT.ARRANGE_TPE_CD <> '003'                                                                     ");
    	sbSql.append("\n                                                 AND    TOR.ABSE_CD    <> '003'                                                                         ");
    	sbSql.append("\n                                                 AND    (TRT.STND_YEAR, TRT.MBR_CD, TRT.TMS) IN (                                                       ");
    	sbSql.append("\n                                                                                                     SELECT                                             ");
    	sbSql.append("\n                                                                                                              TRDO.STND_YEAR                            ");
    	sbSql.append("\n                                                                                                            , TRDO.MBR_CD                               ");
    	sbSql.append("\n                                                                                                            , TRDO.TMS                                  ");
    	sbSql.append("\n                                                                                                     FROM     TBEB_RACE_DAY_ORD TRDO                    ");
    	sbSql.append("\n                                                                                                            , (                                         ");
    	sbSql.append("\n                                                                                                                 SELECT                                 ");
    	sbSql.append("\n                                                                                                                          MIN(TRDO.RACE_DAY) STR_DT     ");
    	sbSql.append("\n                                                                                                                        , MAX(TRDO.RACE_DAY) END_DT     ");
    	sbSql.append("\n                                                                                                                 FROM     TBEB_RACE_DAY_ORD TRDO        ");
    	sbSql.append("\n                                                                                                                        , TBEB_RACE_TMS     TRT         ");
    	sbSql.append("\n                                                                                                                 WHERE  TRT.STND_YEAR = TRDO.STND_YEAR  ");
    	sbSql.append("\n                                                                                                                 AND    TRT.MBR_CD    = TRDO.MBR_CD     ");
    	sbSql.append("\n                                                                                                                 AND    TRT.TMS       = TRDO.TMS        ");
    	sbSql.append("\n                                                                                                                 AND    TRT.STND_YEAR = ?               ");
    	sbSql.append("\n                                                                                                                 AND    TRT.MBR_CD    = ?               ");
    	sbSql.append("\n                                                                                                                 AND    TRT.QURT_CD   = ?               ");
    	sbSql.append("\n                                                                                                              ) DT                                      ");
    	sbSql.append("\n                                                                                                     WHERE TRDO.RACE_DAY BETWEEN STR_DT                 ");
    	sbSql.append("\n                                                                                                                             AND END_DT                 ");
    	sbSql.append("\n                                                                                                     GROUP BY                                           ");
    	sbSql.append("\n                                                                                                              TRDO.STND_YEAR                            ");
    	sbSql.append("\n                                                                                                            , TRDO.MBR_CD                               ");
    	sbSql.append("\n                                                                                                            , TRDO.TMS                                  ");
    	sbSql.append("\n                                                                                                )                                                       ");
    	sbSql.append("\n                                                 GROUP BY                                                                                               ");
    	sbSql.append("\n                                                          TOR.RACER_NO                                                                                  ");
    	sbSql.append("\n                         			         ) TOR                                                                                                      ");
    	sbSql.append("\n                         			WHERE  TPM.RACER_NO       = TRBRA.RACER_NO(+)                                                                       ");
    	sbSql.append("\n                         			AND    TPM.RACER_NO       = TOR  .RACER_NO(+)                                                                       ");
    	sbSql.append("\n                         			AND    TPM.RACER_GRD_CD   = TSRC.RACER_GRD_CD(+)                                                                    ");
    	sbSql.append("\n                         			AND    TPM.RACER_STAT_CD IN ('001', '002')                                                                          ");
    	sbSql.append("\n                         			ORDER BY TPM.RACER_GRD_CD                                                                                           ");
    	sbSql.append("\n                         			       , TPM.RACER_NO                                                                                               ");
    	sbSql.append("\n                                  ) TOR                                                                                                                 ");
    	sbSql.append("\n                         WHERE  TRM.RACER_NO = TOR.RACER_NO                                                                                             ");
    	sbSql.append("\n                         AND    TRM.RACER_NO NOT IN (                                                                                                   ");
    	sbSql.append("\n                                                         SELECT                                                                                         ");
    	sbSql.append("\n                                                                RACER_NO                                                                                ");
    	sbSql.append("\n                                                         FROM   TBEB_ORGAN                                                                              ");
    	sbSql.append("\n                                                         WHERE  RACE_DAY IN (                                                                           ");
    	sbSql.append("\n                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                        RACE_DAY                                                        ");
    	sbSql.append("\n                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO                                          ");
    	sbSql.append("\n                                                                                 WHERE  STND_YEAR = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    MBR_CD    = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    TMS       = ? - 1                                               ");
    	sbSql.append("\n                                                                            )                                                                           ");
    	sbSql.append("\n                                                    )                                                                                                   ");

    	// 9ȸ�� �������� ���μ��� ����
    	if ( Integer.parseInt((String) ctx.get("TMS")) <= 9 ) {
        	if ( ctx.get("STAT") != null && ctx.get("STAT").equals("002") ) {
        		sbSql.append("\n                         AND    TRM.RACER_STAT_CD IN ('002')                                                                                        ");
        	} else {
        		sbSql.append("\n                         AND    TRM.RACER_STAT_CD IN ('001')                                                                                        ");
        	}
    	} else {
        	sbSql.append("\n                         AND    TRM.RACER_STAT_CD IN ('001', '002')                                                                                     ");
    	}
    	sbSql.append("\n                         AND    TRM.RACER_NO NOT IN (                                                                                                   ");
    	sbSql.append("\n                                                         SELECT                                                                                         ");
    	sbSql.append("\n                                                                RACER_NO                                                                                ");
    	sbSql.append("\n                                                         FROM   TBEC_ARRANGE_ESC                                                                        ");
    	sbSql.append("\n                                                         WHERE  STR_DT     <= (                                                                         ");
    	sbSql.append("\n                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                        MAX(RACE_DAY)                                                   ");
    	sbSql.append("\n                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO                                          ");
    	sbSql.append("\n                                                                                 WHERE  STND_YEAR = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    MBR_CD    = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    TMS       = ?                                                   ");
    	sbSql.append("\n                                                                              )                                                                         ");
    	sbSql.append("\n                                                         AND    END_DT     >= (                                                                         ");
    	sbSql.append("\n                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                        MIN(RACE_DAY)                                                   ");
    	sbSql.append("\n                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO                                          ");
    	sbSql.append("\n                                                                                 WHERE  STND_YEAR = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    MBR_CD    = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    TMS       = ?                                                   ");
    	sbSql.append("\n                                                                              )                                                                         ");
    	sbSql.append("\n                                                    )                                                                                                   ");
    	sbSql.append("\n                         AND    TRM.RACER_NO NOT IN (                                                                                                   ");
    	sbSql.append("\n                                                         SELECT                                                                                         ");
    	sbSql.append("\n                                                                RACER_NO                                                                                ");
    	sbSql.append("\n                                                         FROM   TBEC_ARRANGE_ESC                                                                        ");
    	sbSql.append("\n                                                         WHERE  STR_DT     <= (                                                                         ");
    	sbSql.append("\n                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                        MAX(RACE_DAY)                                                   ");
    	sbSql.append("\n                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO                                          ");
    	sbSql.append("\n                                                                                 WHERE  STND_YEAR = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    MBR_CD    = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    TMS       = ?                                                   ");
    	sbSql.append("\n                                                                              )                                                                         ");
    	sbSql.append("\n                                                         AND    END_DT     >= (                                                                         ");
    	sbSql.append("\n                                                                                 SELECT                                                                 ");
    	sbSql.append("\n                                                                                        TO_CHAR(TO_DATE(MIN(RACE_DAY), 'YYYYMMDD') - 7, 'YYYYMMDD')     ");
    	sbSql.append("\n                                                                                 FROM   TBEB_RACE_DAY_ORD TRDO                                          ");
    	sbSql.append("\n                                                                                 WHERE  STND_YEAR = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    MBR_CD    = ?                                                   ");
    	sbSql.append("\n                                                                                 AND    TMS       = ?                                                   ");
    	sbSql.append("\n                                                                              )                                                                         ");
    	sbSql.append("\n                                                         AND    RSN_CD    = '001'                                                                       ");
    	sbSql.append("\n                                                    )                                                                                                   ");
    	sbSql.append("\n                         AND    TRM.RACER_GRD_CD = NVL(?, TRM.RACER_GRD_CD)                                                                             ");

        int nCount = 0;
        if ( rowset != null ) {
	        while( rowset.hasNext() ) {
	            PosRow row = rowset.next();
	          
	            if ( nCount == 0 ) {
	                sbSql.append("\n                 AND    TRM.RACER_NO  NOT IN (");
	                sbSql.append("'" + row.getAttribute("RACER_NO").toString() + "'");
	            } else {
	                sbSql.append(",'" + row.getAttribute("RACER_NO").toString() + "'");
	            }
	          
	            nCount++;
	        }
	
	        if ( nCount > 0 ) 
	            sbSql.append("\n                                                 )");
        }
    	
    	sbSql.append("\n                    )                                                                                                                                   ");
    	sbSql.append("\n        )                                                                                                                                               ");
    	sbSql.append("\n WHERE  SEQ <= ?                                                                                                                                        ");
		
    	
    	int i = 0;
    	PosParameter param = new PosParameter();
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD  ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD  ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("QURT_CD  ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACER_GRD_CD".trim()));
        param.setWhereClauseParameter(i++, ctx.get("SEQ         ".trim()));
    	
        PosRowSet rowsetEsc = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowsetEsc;
    }

    /**
     * <p> ������ ���� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected PosRowSet GetRacerRecd(PosContext ctx)
    {
    	PosParameter paramRacer = new PosParameter();
        PosRowSet rowsetRacer = null; 
        int i = 0;

        // ���� ��ȸ
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD   ".trim()));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS      ".trim()));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STR_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("END_DT"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("SEQ"   ));
        
        rowsetRacer = this.getDao("boadao").find("tbec_racer_master_fb012", paramRacer);

        return rowsetRacer;
    }

    /**
     * <p> rowset�� addRowSet�� recode�� �ڿ� ���Ͽ� rowset�� return�Ѵ�. </p>
     */    
	protected PosRowSet addRowSet(PosRowSet rowset, PosRowSet addRowSet) 
    {
		ArrayList rows    = new ArrayList();
		HashMap   hmRacer = new HashMap();
		int       nRow    = 0;
		
		if ( rowset != null ) {
			PosRow row[] = rowset.getAllRow();

			for ( int i = 0; i < row.length; i++ ) {
				hmRacer.put((String) row[i].getAttribute("RACER_NO") , Integer.toString(nRow));
				rows.add(row[i]);
				nRow++; 
			}
		}
		
		if ( addRowSet != null ) {
			PosRow row[] = addRowSet.getAllRow();

			for ( int i = 0; i < row.length; i++ ) {
				if ( hmRacer.get((String) row[i].getAttribute("RACER_NO")) == null ) {
					hmRacer.put((String) row[i].getAttribute("RACER_NO") , Integer.toString(nRow));
					rows.add(row[i]);
					nRow++;
				}
			}
		}

		if ( rows.size() > 0 ) rowset = new PosRowSetImpl(rows);
		
		return rowset;
    }
}
