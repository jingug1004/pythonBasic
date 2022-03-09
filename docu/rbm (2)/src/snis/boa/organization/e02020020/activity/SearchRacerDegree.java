/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.racer.e02020020.activity.SearchRacerDegree.java
 * ���ϼ���		: ��������� ��ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-12-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02020020.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������� �����ϱ� ���Ͽ� ���������� ��ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRacerDegree extends SnisActivity {
    
	public SearchRacerDegree()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        sDsName = "dsOutCond";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
        
    	searchRacer(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> ��ȸ���� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    protected void searchRacer(PosContext ctx) 
    {
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        PosRowSet rowsetEsc   = null;
        PosRowSet rowsetRacer = null;
        
        
        // ��ȸ ��޺��� ���� ���� ��ȸ
        // ��ȸ ������ �ϳ��� ����̶� ��ü ��ȸ �� ������ �����Ͽ� ������ �ش��ϴ� ����ο��� ��ȸ�Ѵ�.
        sDsName = "dsOutCond";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
            
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            PosRowSet rowset = null;
	            if ( record.getAttribute("RACER_GRD_CD").equals("B2")) {
	            	rowset = getEtcRacer(record, ctx, rowsetEsc);

	            	// Ư�� �����ȸ�� �ƴϸ� ��ȸ�� ���� ����Ʈ�� ���Ѵ�.
		            // Ư�� ����� ��� �ش� ����� ��ȸ�� ������ return�Ѵ�.
		            if ( ctx.get("RACER_GRD_CD").equals("")) {
		            	rowsetRacer = addRowSet(rowsetRacer, rowset);
		            } if ( record.getAttribute("RACER_GRD_CD").equals(ctx.get("RACER_GRD_CD")) ) {
		            	rowsetRacer = rowset;
		            }
	            } else {
	            	rowset = getCondRacer(record, ctx, rowsetEsc);
		            rowsetEsc = addRowSet(rowsetEsc, rowset);
		            
		            // Ư�� �����ȸ�� �ƴϸ� ��ȸ�� ���� ����Ʈ�� ���Ѵ�.
		            // Ư�� ����� ��� �ش� ����� ��ȸ�� ������ return�Ѵ�.
		            if ( ctx.get("RACER_GRD_CD").equals("")) {
		            	rowsetRacer = addRowSet(rowsetRacer, rowset);
		            } if ( record.getAttribute("RACER_GRD_CD").equals(ctx.get("RACER_GRD_CD")) ) {
		            	rowsetRacer = rowset;
		            }
	            }
	        }
        }

        // return Dataset�� �÷��� �����Ѵ�.
        PosColumnDef columnDef[] = new PosColumnDef[24];

        int i = 0;
        columnDef[i++] = new PosColumnDef("RACER_NO"                  , 12, 6);
        columnDef[i++] = new PosColumnDef("NM_KOR"                    , 12, 20);
        columnDef[i++] = new PosColumnDef("RACER_GRD_CD"              , 12, 3);
        columnDef[i++] = new PosColumnDef("NEW_RACER_GRD"             , 12, 3);
//        columnDef[i++] = new PosColumnDef("RACER_STAT_CD"             , 12, 3);
//        columnDef[i++] = new PosColumnDef("RACER_PERIO_NO"            , 12, 3);
        columnDef[i++] = new PosColumnDef("RACE_CNT"                  ,  3, 10);
        columnDef[i++] = new PosColumnDef("AVG_RANK_SCR"              ,  3, 10);
        columnDef[i++] = new PosColumnDef("WIN_RATIO"                 ,  3, 10);
        columnDef[i++] = new PosColumnDef("HIGH_RANK_RATIO"           ,  3, 10);
        columnDef[i++] = new PosColumnDef("HIGH_3_RANK_RATIO"         ,  3, 10);
        columnDef[i++] = new PosColumnDef("AVG_ACDNT_SCR"             ,  3, 10);
        columnDef[i++] = new PosColumnDef("AVG_SCR"                   ,  3, 10);
        columnDef[i++] = new PosColumnDef("AVG_ST"                    ,  3, 10);
        columnDef[i++] = new PosColumnDef("RANK"                      ,  3, 10);

        columnDef[i++] = new PosColumnDef("F_CNT"                     ,  3, 10);
        columnDef[i++] = new PosColumnDef("L_CNT"                     ,  3, 10);
        columnDef[i++] = new PosColumnDef("ABSE_CNT"                  ,  3, 10);
        columnDef[i++] = new PosColumnDef("RACE_ESC_1_CNT"            ,  3, 10);
        columnDef[i++] = new PosColumnDef("RACE_ESC_2_CNT"            ,  3, 10);
        columnDef[i++] = new PosColumnDef("FOUL_ELIM_CNT"             ,  3, 10);
        columnDef[i++] = new PosColumnDef("ELIM_CNT"                  ,  3, 10);
        columnDef[i++] = new PosColumnDef("FOUL_WARN_CNT"             ,  3, 10);
        columnDef[i++] = new PosColumnDef("WARN_CNT"                  ,  3, 10);
        columnDef[i++] = new PosColumnDef("ATTEN_CNT"                 ,  3, 10);
        columnDef[i++] = new PosColumnDef("RMK"                       , 12, 4000);
        
        rowsetRacer.setColumnDefs(columnDef);

        String sResultKey = "dsOutRacerDegree";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> �� ���(A1, A2, B1)�� ���ǿ� �´� ������ ��ȸ�Ѵ�. rowsetEsc�� ������ ��ȸ�� ���� ��Ų��. </p>
     */    
    protected PosRowSet getCondRacer(PosRecord record, PosContext ctx, PosRowSet rowsetEsc) 
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT  /*SearchRacerDegree.getCondRacer()*/                                                                                                            ");
    	sbSql.append("\n           RACER_NO                    --��Ϲ�ȣ                                                                                                                                         ");
    	sbSql.append("\n         , NM_KOR                      --������                                                                                                                                            ");
    	sbSql.append("\n         , RACER_GRD_CD                --�����                                                                                                                                            ");
    	sbSql.append("\n         , ? NEW_RACER_GRD             --������                                                                                                                                         ");
    	sbSql.append("\n         , RACE_CNT                    --����Ƚ��                                                                                                                                         ");
    	sbSql.append("\n         , AVG_RANK_SCR                --���������                                                                                                                                     ");
    	sbSql.append("\n         , AVG_ACDNT_SCR               --��ջ����                                                                                                                                     ");
    	sbSql.append("\n         , AVG_SCR                     --��յ���                                                                                                                                        ");
    	sbSql.append("\n         , WIN_RATIO                   --�·�                                                                                                                                              ");
    	sbSql.append("\n         , HIGH_RANK_RATIO             --������                                                                                                                                           ");
    	sbSql.append("\n         , HIGH_3_RANK_RATIO           --�￬����                                                                                                                                        ");
    	sbSql.append("\n         , AVG_ST                      --���ST                                                                     ");
    	sbSql.append("\n         , F_CNT                       --F                                                                         ");
    	sbSql.append("\n         , L_CNT                       --L                                                                         ");
    	sbSql.append("\n         , ABSE_CNT                    --����                                                                                                                                              ");
    	sbSql.append("\n         , RACE_ESC_1_CNT              --��������1                                                                   ");
    	sbSql.append("\n         , RACE_ESC_2_CNT              --��������2                                                                   ");
    	sbSql.append("\n         , FOUL_ELIM_CNT               --��Ģ�ǰ�                                                                                                                                        ");
    	sbSql.append("\n         , ELIM_CNT                    --�ǰ�                                                                                                                                               ");
    	sbSql.append("\n         , FOUL_WARN_CNT               --��Ģ���                                                                                                                                         ");
    	sbSql.append("\n         , WARN_CNT                    --���                                                                                                                                               ");
    	sbSql.append("\n         , ATTEN_CNT                   --����                                                                                                                                               ");
    	sbSql.append("\n         , RANK                        --����                                                                                                                                               ");
    	sbSql.append("\n         , '' RMK                      --���                                                                                                                                               ");
    	sbSql.append("\n FROM    (                                                                                                          ");
    	sbSql.append("\n             SELECT                                                                                                 ");
    	sbSql.append("\n                       TOR.RACER_NO                                                                                 ");
    	sbSql.append("\n                     , TOR.RACE_CNT                                                                                 ");
    	sbSql.append("\n                     , TRM.NM_KOR                                                                                   ");
    	sbSql.append("\n                     , TRM.RACER_GRD_CD                                                                             ");
    	sbSql.append("\n                     , ROUND(TOR.AMU_RANK_SCR  / TOR.RACE_CNT, 2)                                AVG_RANK_SCR       ");
    	sbSql.append("\n                     , ROUND(TOR.AMU_ACDNT_SCR / TOR.RACE_CNT, 2)                                AVG_ACDNT_SCR      ");
    	sbSql.append("\n                     , ROUND((TOR.AMU_RANK_SCR - TOR.AMU_ACDNT_SCR) / TOR.RACE_CNT, 2)           AVG_SCR            ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1) / TOR.RACE_CNT * 100, 1)                               WIN_RATIO          ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1 + TOR.RANK_2) / TOR.RACE_CNT * 100, 1)                  HIGH_RANK_RATIO    ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1 + TOR.RANK_2 + TOR.RANK_3) / TOR.RACE_CNT * 100, 1)     HIGH_3_RANK_RATIO  ");
    	sbSql.append("\n                     , ROUND(TOR.AVG_ST,2)                                                       AVG_ST             ");
    	sbSql.append("\n                     , TOR.F_CNT                                                                                    ");
    	sbSql.append("\n                     , TOR.L_CNT                                                                                    ");
    	sbSql.append("\n                     , TOR.ABSE_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.RACE_ESC_1_CNT                                                                           ");
    	sbSql.append("\n                     , TOR.RACE_ESC_2_CNT                                                                           ");
    	sbSql.append("\n                     , TOR.FOUL_ELIM_CNT                                                                            ");
    	sbSql.append("\n                     , TOR.ELIM_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.FOUL_WARN_CNT                                                                            ");
    	sbSql.append("\n                     , TOR.WARN_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.ATTEN_CNT                                                                                ");
    	sbSql.append("\n                     , RANK() OVER (ORDER BY ROUND(TOR.AMU_RANK_SCR  / TOR.RACE_CNT, 2) DESC) RANK                  ");
    	sbSql.append("\n             FROM      TBEC_RACER_MASTER TRM                                                                        ");
    	sbSql.append("\n                     , (                                                                                            ");
    	sbSql.append("\n                          SELECT                                                                                    ");
    	sbSql.append("\n                                   TOR.RACER_NO                                                                     ");
    	sbSql.append("\n                                 , COUNT(*)                                                       RACE_CNT          ");
    	sbSql.append("\n                                 , SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, TOR.STAR_TM))/NVL(SUM(DECODE(TOR.ST_MTHD_CD, '001', 0, 1)),1)  AVG_ST ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0))                        , 0) RANK_1            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0))                        , 0) RANK_2            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0))                        , 0) RANK_3            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 4, 1, 0))                        , 0) RANK_4            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 5, 1, 0))                        , 0) RANK_5            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 6, 1, 0))                        , 0) RANK_6            ");
    	sbSql.append("\n                                 , NVL(SUM(TRS .RACE_SCR      )                              , 0) AMU_RANK_SCR      ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ACDNT_SCR     )                              , 0) AMU_ACDNT_SCR     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.F_CNT         )                              , 0) F_CNT             ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.L_CNT         )                              , 0) L_CNT             ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ABSE_CNT      )                              , 0) ABSE_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.RACE_ESC_1_CNT)                              , 0) RACE_ESC_1_CNT    ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.RACE_ESC_2_CNT)                              , 0) RACE_ESC_2_CNT    ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.FOUL_ELIM_CNT )                              , 0) FOUL_ELIM_CNT     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ELIM_CNT      )                              , 0) ELIM_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.FOUL_WARN_CNT )                              , 0) FOUL_WARN_CNT     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.WARN_CNT      )                              , 0) WARN_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ATTEN_CNT     )                              , 0) ATTEN_CNT         ");
    	sbSql.append("\n                          FROM     TBEB_ORGAN         TOR                                                           ");
    	sbSql.append("\n                                 , TBEB_RACE          TR                                                            ");
    	sbSql.append("\n                                 , TBEB_RANK_SCR      TRS                                                           ");
    	sbSql.append("\n                                 , (                                                                                ");
    	sbSql.append("\n                                      SELECT                                                                        ");
    	sbSql.append("\n                                               TRVA.STND_YEAR                                                       ");
    	sbSql.append("\n                                             , TRVA.MBR_CD                                                          ");
    	sbSql.append("\n                                             , TRVA.TMS                                                             ");
    	sbSql.append("\n                                             , TRVA.DAY_ORD                                                         ");
    	sbSql.append("\n                                             , TRVA.RACE_NO                                                         ");
    	sbSql.append("\n                                             , TRVA.RACE_REG_NO                                                     ");
    	sbSql.append("\n                                             , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                        ");
    	sbSql.append("\n                                             , TRVA.F_CNT                                                           ");
    	sbSql.append("\n                                             , TRVA.L_CNT                                                           ");
    	sbSql.append("\n                                             , TRVA.ABSE_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.RACE_ESC_1_CNT                                                  ");
    	sbSql.append("\n                                             , TRVA.RACE_ESC_2_CNT                                                  ");
    	sbSql.append("\n                                             , TRVA.FOUL_ELIM_CNT                                                   ");
    	sbSql.append("\n                                             , TRVA.ELIM_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.FOUL_WARN_CNT                                                   ");
    	sbSql.append("\n                                             , TRVA.WARN_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.ATTEN_CNT                                                       ");
    	sbSql.append("\n                                      FROM     (                                                                    ");
    	sbSql.append("\n                                                 SELECT                                                             ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                                        , SUM(ACDNT_SCR) ACDNT_SCR                                  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '110', 1, NULL)) F_CNT           ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '120', 1, NULL)) L_CNT           ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '130', 1, NULL)) ABSE_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '995', 1, NULL)) RACE_ESC_1_CNT  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '997', 1, NULL)) RACE_ESC_2_CNT  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '125', 1, NULL)) FOUL_ELIM_CNT   ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '140', 1, NULL)) ELIM_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '150', 1, NULL)) FOUL_WARN_CNT   ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '210', 1, NULL)) WARN_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '220', 1, NULL)) ATTEN_CNT       ");
    	sbSql.append("\n                                                 FROM     TBED_RACE_VOIL_ACT TRVA                                   ");
    	sbSql.append("\n                                                        , TBEB_ACDNT_SCR     TAS                                    ");
    	sbSql.append("\n                                                        , TBEB_ORGAN         TOR                                    ");
    	sbSql.append("\n                                                 WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD      = TAS.VOIL_CD                             ");
    	sbSql.append("\n                                                 AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
    	sbSql.append("\n                                                 AND    TRVA.TMS          = TOR.TMS                                 ");
    	sbSql.append("\n                                                 AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
    	sbSql.append("\n                                                 AND    TOR .ABSE_CD     <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TOR .IMMT_CLS_CD <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_DAY      BETWEEN ?                                ");
    	sbSql.append("\n                                                                               AND ?                                ");
    	sbSql.append("\n                                                 GROUP BY                                                           ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                              ) TRVA                                                                ");
    	sbSql.append("\n                                            , (                                                                     ");
    	sbSql.append("\n                                                 SELECT                                                             ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                                        , TAS .ACDNT_SCR                                            ");
    	sbSql.append("\n                                                 FROM     TBED_RACE_VOIL_ACT TRVA                                   ");
    	sbSql.append("\n                                                        , TBEB_ACDNT_SCR     TAS                                    ");
    	sbSql.append("\n                                                        , TBEB_ORGAN         TOR                                    ");
    	sbSql.append("\n                                                 WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD      = TAS.VOIL_CD                             ");
    	sbSql.append("\n                                                 AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
    	sbSql.append("\n                                                 AND    TRVA.TMS          = TOR.TMS                                 ");
    	sbSql.append("\n                                                 AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
    	sbSql.append("\n                                                 AND    TOR .ABSE_CD     <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TOR .IMMT_CLS_CD <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_DAY      BETWEEN ?                                ");
    	sbSql.append("\n                                                                               AND ?                                ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD     IN ('110', '120')                          ");
    	sbSql.append("\n                                               ) TRVB                                                               ");
    	sbSql.append("\n                                     WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.TMS         = TRVB.TMS        (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                  ");
    	sbSql.append("\n                                   ) TRVA                                                                           ");
    	sbSql.append("\n                          WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                                                 ");
    	sbSql.append("\n                          AND    TOR .MBR_CD       = TR  .MBR_CD                                                    ");
    	sbSql.append("\n                          AND    TOR .TMS          = TR  .TMS                                                       ");
    	sbSql.append("\n                          AND    TOR .DAY_ORD      = TR  .DAY_ORD                                                   ");
    	sbSql.append("\n                          AND    TOR .RACE_NO      = TR  .RACE_NO                                                   ");
    	sbSql.append("\n                          AND    TOR .STND_YEAR    = TRS .STND_YEAR                                                 ");
    	sbSql.append("\n                          AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                                              ");
    	sbSql.append("\n                          AND    TOR .RANK         = TRS .RANK                                                      ");
    	sbSql.append("\n                          AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)                                            ");
    	sbSql.append("\n                          AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)                                            ");
    	sbSql.append("\n                          AND    TOR .TMS          = TRVA.TMS        (+)                                            ");
    	sbSql.append("\n                          AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)                                            ");
    	sbSql.append("\n                          AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)                                            ");
    	sbSql.append("\n                          AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                            ");
    	sbSql.append("\n                          AND    TOR .ABSE_CD     <> '003'                                                          ");
    	sbSql.append("\n                          AND    TOR .IMMT_CLS_CD <> '003'                                                          ");
    	sbSql.append("\n                          AND    TOR .RACE_DAY      BETWEEN ?                                                       ");
    	sbSql.append("\n                                                        AND ?                                                       ");
    	sbSql.append("\n                          AND    TOR.RACER_NO NOT IN ( SELECT TAE.RACER_NO                                          ");
    	sbSql.append("\n                                                       FROM   TBEC_ARRANGE_ESC TAE                                  ");
    	sbSql.append("\n                                                       WHERE  TAE.STR_DT > ?                                        ");
    	sbSql.append("\n                                                       AND    TAE.RSN_CD LIKE '2%' )                                ");
        sbSql.append("\n                          GROUP BY TOR .RACER_NO                                                                    ");
    	sbSql.append("\n                      ) TOR                                                                                         ");
    	sbSql.append("\n                    , (                                                                                             ");
    	sbSql.append("\n                        SELECT                                                                                      ");
    	sbSql.append("\n                                 RACER_GRD_CD                                                                       ");
    	sbSql.append("\n                               , COUNT(*) GRD_CNT                                                                   ");
    	sbSql.append("\n                               , SUM(CNT.RACE_CNT) RACE_CNT                                                         ");
    	sbSql.append("\n                               , ROUND(SUM(CNT.RACE_CNT)/COUNT(*)) STND_CNT                                         ");
    	sbSql.append("\n                               , ROUND((SUM(CNT.RACE_CNT)/COUNT(*))/2) STND_CNT_50                                  ");
    	sbSql.append("\n                        FROM     (                                                                                  ");
    	sbSql.append("\n                                    SELECT                                                                          ");
    	sbSql.append("\n                                             TRG.RACER_NO                                                           ");
    	sbSql.append("\n                                           , TRG.CHG_DT                                                             ");
    	sbSql.append("\n                                           , TRG.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                           , RANK() OVER (PARTITION BY TRG.RACER_NO ORDER BY TRG.CHG_DT DESC ) SEQ  ");
    	sbSql.append("\n                                    FROM     TBEC_RACER_MASTER TRM                                                  ");
    	sbSql.append("\n                                           , TBEB_RACER_GRD    TRG                                                  ");
    	sbSql.append("\n                                    WHERE  TRM.RACER_NO = TRG.RACER_NO                                              ");
    	sbSql.append("\n                                    AND    TRG.RACER_NO IN (                                                        ");
    	sbSql.append("\n                                                                SELECT                                              ");
    	sbSql.append("\n                                                                       TOR.RACER_NO                                 ");
    	sbSql.append("\n                                                                FROM   TBEB_ORGAN TOR                               ");
    	sbSql.append("\n                                                                WHERE  TOR.RACE_DAY BETWEEN ?                       ");
    	sbSql.append("\n                                                                                        AND ?                       ");
    	sbSql.append("\n                                                                GROUP BY TOR.RACER_NO                               ");
    	sbSql.append("\n                                                           )                                                        ");
    	sbSql.append("\n                                    AND    TRM.RACER_PERIO_NO BETWEEN TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                                                  AND TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                 ) GRD                                                                              ");
    	sbSql.append("\n                               , (                                                                                  ");
    	sbSql.append("\n                                    SELECT                                                                          ");
    	sbSql.append("\n                                             TOR.RACER_NO                                                           ");
    	sbSql.append("\n                                           , COUNT(*) RACE_CNT                                                      ");
    	sbSql.append("\n                                    FROM     TBEC_RACER_MASTER TRM                                                  ");
    	sbSql.append("\n                                           , TBEB_ORGAN        TOR                                                  ");
    	sbSql.append("\n                                    WHERE  TRM.RACER_NO = TOR.RACER_NO                                              ");
    	sbSql.append("\n                                    AND    TOR.RACE_DAY BETWEEN ?                                                   ");
    	sbSql.append("\n                                                            AND ?                                                   ");
    	sbSql.append("\n                                    AND    TRM.RACER_PERIO_NO BETWEEN TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                                                  AND TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                    AND    TOR.ABSE_CD     <> '003'                                                 ");
    	sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD <> '003'                                                 ");
    	sbSql.append("\n                                    -- ���ֺҼ����� ��� ABSE_CD='001', IMMT_CLS_CD='001' �̸鼭                                                  ");
    	sbSql.append("\n                                    -- RANK�� NULL�� ���Ƿ� ����Ƚ������ ����                                                                                     ");                              
    	sbSql.append("\n                                    AND    TOR.RANK IS NOT NULL                                                     ");
    	sbSql.append("\n                                    GROUP BY TOR.RACER_NO                                                           ");
    	sbSql.append("\n                                 ) CNT                                                                              ");
    	sbSql.append("\n                         WHERE SEQ = 1                                                                              ");
    	sbSql.append("\n                         AND   GRD.RACER_NO = CNT.RACER_NO                                                          ");
    	sbSql.append("\n                         GROUP BY RACER_GRD_CD                                                                      ");
    	sbSql.append("\n                         ORDER BY RACER_GRD_CD                                                                      ");
/*
    	sbSql.append("\n                        SELECT                                                                                      ");
    	sbSql.append("\n                                 TRM.RACER_GRD_CD                                                                   ");
    	sbSql.append("\n                               , TRM.GRD_CNT                                                                        ");
    	sbSql.append("\n                               , TOR.RACE_CNT                                                                       ");
    	sbSql.append("\n                               , ROUND(TOR.RACE_CNT/TRM.GRD_CNT) STND_CNT                                           ");
    	sbSql.append("\n                               , ROUND(TOR.RACE_CNT/TRM.GRD_CNT/2) STND_CNT_50                                      ");
    	sbSql.append("\n                        FROM     (                                                                                  ");
    	sbSql.append("\n                                    SELECT                                                                          ");
    	sbSql.append("\n                                             TRM.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                           , COUNT(*) GRD_CNT                                                       ");
    	sbSql.append("\n                                    FROM   TBEC_RACER_MASTER TRM                                                    ");
    	sbSql.append("\n                                    WHERE  TRM.RACER_NO IN (                                                        ");
    	sbSql.append("\n                                                                SELECT                                              ");
    	sbSql.append("\n                                                                       TOR.RACER_NO                                 ");
    	sbSql.append("\n                                                                FROM   TBEB_ORGAN TOR                               ");
    	sbSql.append("\n                                                                WHERE  TOR.RACE_DAY BETWEEN ?                       ");
    	sbSql.append("\n                                                                                        AND ?                       ");
    	sbSql.append("\n                                                                GROUP BY TOR.RACER_NO                               ");
    	sbSql.append("\n                                                           )                                                        ");
    	sbSql.append("\n                                    AND    TRM.RACER_PERIO_NO BETWEEN TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                                                  AND TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                    GROUP BY TRM.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                 ) TRM                                                                              ");
    	sbSql.append("\n                               , (                                                                                  ");
    	sbSql.append("\n                                    SELECT                                                                          ");
    	sbSql.append("\n                                             TOR.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                           , COUNT(*) RACE_CNT                                                      ");
    	sbSql.append("\n                                    FROM     TBEC_RACER_MASTER TRM                                                  ");
    	sbSql.append("\n                                           , TBEB_ORGAN        TOR                                                  ");
    	sbSql.append("\n                                    WHERE  TRM.RACER_NO = TOR.RACER_NO                                              ");
    	sbSql.append("\n                                    AND    TOR.RACE_DAY BETWEEN ?                                                   ");
    	sbSql.append("\n                                                            AND ?                                                   ");
    	sbSql.append("\n                                    AND    TRM.RACER_PERIO_NO BETWEEN TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                                                  AND TO_NUMBER(?)                                  ");
    	sbSql.append("\n                                    AND    TOR.ABSE_CD     <> '003'                                                 ");
    	sbSql.append("\n                                    AND    TOR.IMMT_CLS_CD <> '003'                                                 ");
    	sbSql.append("\n                                    -- ���ֺҼ����� ��� ABSE_CD='001', IMMT_CLS_CD='001' �̸鼭                                                  ");
    	sbSql.append("\n                                    -- RANK�� NULL�� ���Ƿ� ����Ƚ������ ����                                                                                     ");                              
    	sbSql.append("\n                                    AND    TOR.RANK IS NOT NULL                                                     ");
    	sbSql.append("\n                                    GROUP BY TOR.RACER_GRD_CD                                                       ");
    	sbSql.append("\n                                 ) TOR                                                                              ");
    	sbSql.append("\n                         WHERE TRM.RACER_GRD_CD = TOR.RACER_GRD_CD                                                  ");
    	sbSql.append("\n                         ORDER BY TRM.RACER_GRD_CD                                                                  ");
*/
    	sbSql.append("\n                      ) CNT                                                                                         ");
    	sbSql.append("\n             WHERE  TRM.RACER_NO                                              = TOR .RACER_NO                       ");
    	sbSql.append("\n             AND    TRM.RACER_GRD_CD                                          = CNT .RACER_GRD_CD                   ");
    	sbSql.append("\n             AND    TRM.RACER_STAT_CD <> '003'                                                              ");
    	sbSql.append("\n             AND    TRM.RACER_PERIO_NO BETWEEN TO_NUMBER(?)                                                         ");
    	sbSql.append("\n                                           AND TO_NUMBER(?)                                                         ");
    	sbSql.append("\n             AND    ROUND((TOR.RANK_1 + TOR.RANK_2) / TOR.RACE_CNT * 100, 1) >= ?                                   ");
    	sbSql.append("\n             AND    ROUND( TOR.AMU_ACDNT_SCR        / TOR.RACE_CNT, 2)       <= ?                                   ");
    	sbSql.append("\n             AND    TOR.RACE_CNT                                             >= CNT.STND_CNT_50                     ");
        
    	if ( rowsetEsc != null ) {
	    	int nCount = 0;
	        while( rowsetEsc.hasNext() ) {
	            PosRow row = rowsetEsc.next();
	          
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
	        
    	sbSql.append("\n        )                                                                                                           ");
    	sbSql.append("\n WHERE  RANK <= ?                                                                                                   ");    	
    	
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_GRD_CD"    ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_PERIO"       ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_PERIO"       ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_PERIO"       ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_PERIO"       ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_PERIO"       ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_PERIO"       ));
        param.setWhereClauseParameter(i++, record.getAttribute("COND2"           ));
        param.setWhereClauseParameter(i++, record.getAttribute("COND3"           ));
        param.setWhereClauseParameter(i++, record.getAttribute("COND1"           ));

    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowset;
    }

    /**
     * <p> rowsetEsc�� ������ ������ ������ ������ ��ȸ�Ѵ�.(B2��޿� �ش��ϴ� ����) </p>
     */    
    protected PosRowSet getEtcRacer(PosRecord record, PosContext ctx, PosRowSet rowsetEsc) 
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT                                                                                                             ");
    	sbSql.append("\n           RACER_NO                                                                                                 ");
    	sbSql.append("\n         , NM_KOR                                                                                                   ");
    	sbSql.append("\n         , RACER_GRD_CD                                                                                             ");
    	sbSql.append("\n         , ? NEW_RACER_GRD                                                                                          ");
    	sbSql.append("\n         , RACE_CNT                                                                                                 ");
    	sbSql.append("\n         , AVG_RANK_SCR                                                                                             ");
    	sbSql.append("\n         , AVG_ACDNT_SCR                                                                                            ");
    	sbSql.append("\n         , AVG_SCR                                                                                                  ");
    	sbSql.append("\n         , WIN_RATIO                                                                                                ");
    	sbSql.append("\n         , HIGH_RANK_RATIO                                                                                          ");
    	sbSql.append("\n         , HIGH_3_RANK_RATIO                                                                                        ");
    	sbSql.append("\n         , AVG_ST                                                                                                   ");
    	sbSql.append("\n         , F_CNT                                                                                                    ");
    	sbSql.append("\n         , L_CNT                                                                                                    ");
    	sbSql.append("\n         , ABSE_CNT                                                                                                 ");
    	sbSql.append("\n         , RACE_ESC_1_CNT                                                                                           ");
    	sbSql.append("\n         , RACE_ESC_2_CNT                                                                                           ");
    	sbSql.append("\n         , FOUL_ELIM_CNT                                                                                            ");
    	sbSql.append("\n         , ELIM_CNT                                                                                                 ");
    	sbSql.append("\n         , FOUL_WARN_CNT                                                                                            ");
    	sbSql.append("\n         , WARN_CNT                                                                                                 ");
    	sbSql.append("\n         , ATTEN_CNT                                                                                                ");
    	sbSql.append("\n         , RANK                                                                                                     ");
    	sbSql.append("\n         , RMK                                                                                                   ");
    	sbSql.append("\n FROM    (                                                                                                          ");
    	sbSql.append("\n             SELECT                                                                                                 ");
    	sbSql.append("\n                       TOR.RACER_NO                                                                                 ");
    	sbSql.append("\n                     , TOR.RACE_CNT                                                                                 ");
    	sbSql.append("\n                     , TRM.NM_KOR                                                                                   ");
    	sbSql.append("\n                     , TRM.RACER_GRD_CD                                                                             ");
    	sbSql.append("\n                     , ROUND(TOR.AMU_RANK_SCR  / TOR.RACE_CNT, 2)                                AVG_RANK_SCR       ");
    	sbSql.append("\n                     , ROUND(TOR.AMU_ACDNT_SCR / TOR.RACE_CNT, 2)                                AVG_ACDNT_SCR      ");
    	sbSql.append("\n                     , ROUND((TOR.AMU_RANK_SCR - TOR.AMU_ACDNT_SCR) / TOR.RACE_CNT, 2)           AVG_SCR            ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1) / TOR.RACE_CNT * 100, 1)                               WIN_RATIO          ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1 + TOR.RANK_2) / TOR.RACE_CNT * 100, 1)                  HIGH_RANK_RATIO    ");
    	sbSql.append("\n                     , ROUND((TOR.RANK_1 + TOR.RANK_2 + TOR.RANK_3) / TOR.RACE_CNT * 100, 1)     HIGH_3_RANK_RATIO  ");
    	sbSql.append("\n                     , ROUND(TOR.AVG_ST,2)                                                       AVG_ST             ");
    	sbSql.append("\n                     , TOR.F_CNT                                                                                    ");
    	sbSql.append("\n                     , TOR.L_CNT                                                                                    ");
    	sbSql.append("\n                     , TOR.ABSE_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.RACE_ESC_1_CNT                                                                           ");
    	sbSql.append("\n                     , TOR.RACE_ESC_2_CNT                                                                           ");
    	sbSql.append("\n                     , TOR.FOUL_ELIM_CNT                                                                            ");
    	sbSql.append("\n                     , TOR.ELIM_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.FOUL_WARN_CNT                                                                            ");
    	sbSql.append("\n                     , TOR.WARN_CNT                                                                                 ");
    	sbSql.append("\n                     , TOR.ATTEN_CNT                                                                                ");
    	sbSql.append("\n                     , RANK() OVER (ORDER BY ROUND(TOR.AMU_RANK_SCR  / TOR.RACE_CNT, 2) DESC) RANK                  ");
    	sbSql.append("\n                     , NVL(TAE.RMK,'') RMK                                                                          ");
    	sbSql.append("\n             FROM      TBEC_RACER_MASTER TRM                                                                        ");
    	sbSql.append("\n                     , (                                                                                            ");
    	sbSql.append("\n                          SELECT                                                                                    ");
    	sbSql.append("\n                                   TOR.RACER_NO                                                                     ");
    	sbSql.append("\n                                 , COUNT(*)                                                       RACE_CNT          ");
    	sbSql.append("\n                                 , AVG(TOR.STAR_TM)                                               AVG_ST            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 1, 1, 0))                        , 0) RANK_1            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 2, 1, 0))                        , 0) RANK_2            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 3, 1, 0))                        , 0) RANK_3            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 4, 1, 0))                        , 0) RANK_4            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 5, 1, 0))                        , 0) RANK_5            ");
    	sbSql.append("\n                                 , NVL(SUM(DECODE(TOR.RANK, 6, 1, 0))                        , 0) RANK_6            ");
    	sbSql.append("\n                                 , NVL(SUM(TRS .RACE_SCR      )                              , 0) AMU_RANK_SCR      ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ACDNT_SCR     )                              , 0) AMU_ACDNT_SCR     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.F_CNT         )                              , 0) F_CNT             ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.L_CNT         )                              , 0) L_CNT             ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ABSE_CNT      )                              , 0) ABSE_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.RACE_ESC_1_CNT)                              , 0) RACE_ESC_1_CNT    ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.RACE_ESC_2_CNT)                              , 0) RACE_ESC_2_CNT    ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.FOUL_ELIM_CNT )                              , 0) FOUL_ELIM_CNT     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ELIM_CNT      )                              , 0) ELIM_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.FOUL_WARN_CNT )                              , 0) FOUL_WARN_CNT     ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.WARN_CNT      )                              , 0) WARN_CNT          ");
    	sbSql.append("\n                                 , NVL(SUM(TRVA.ATTEN_CNT     )                              , 0) ATTEN_CNT         ");
    	sbSql.append("\n                          FROM     TBEB_ORGAN         TOR                                                           ");
    	sbSql.append("\n                                 , TBEB_RACE          TR                                                            ");
    	sbSql.append("\n                                 , TBEB_RANK_SCR      TRS                                                           ");
    	sbSql.append("\n                                 , (                                                                                ");
    	sbSql.append("\n                                      SELECT                                                                        ");
    	sbSql.append("\n                                               TRVA.STND_YEAR                                                       ");
    	sbSql.append("\n                                             , TRVA.MBR_CD                                                          ");
    	sbSql.append("\n                                             , TRVA.TMS                                                             ");
    	sbSql.append("\n                                             , TRVA.DAY_ORD                                                         ");
    	sbSql.append("\n                                             , TRVA.RACE_NO                                                         ");
    	sbSql.append("\n                                             , TRVA.RACE_REG_NO                                                     ");
    	sbSql.append("\n                                             , NVL(TRVB.ACDNT_SCR, TRVA.ACDNT_SCR) ACDNT_SCR                        ");
    	sbSql.append("\n                                             , TRVA.F_CNT                                                           ");
    	sbSql.append("\n                                             , TRVA.L_CNT                                                           ");
    	sbSql.append("\n                                             , TRVA.ABSE_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.RACE_ESC_1_CNT                                                  ");
    	sbSql.append("\n                                             , TRVA.RACE_ESC_2_CNT                                                  ");
    	sbSql.append("\n                                             , TRVA.FOUL_ELIM_CNT                                                   ");
    	sbSql.append("\n                                             , TRVA.ELIM_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.FOUL_WARN_CNT                                                   ");
    	sbSql.append("\n                                             , TRVA.WARN_CNT                                                        ");
    	sbSql.append("\n                                             , TRVA.ATTEN_CNT                                                       ");
    	sbSql.append("\n                                      FROM     (                                                                    ");
    	sbSql.append("\n                                                 SELECT                                                             ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                                        , SUM(ACDNT_SCR) ACDNT_SCR                                  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '110', 1, NULL)) F_CNT           ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '120', 1, NULL)) L_CNT           ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '130', 1, NULL)) ABSE_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '995', 1, NULL)) RACE_ESC_1_CNT  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '997', 1, NULL)) RACE_ESC_2_CNT  ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '125', 1, NULL)) FOUL_ELIM_CNT   ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '140', 1, NULL)) ELIM_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '150', 1, NULL)) FOUL_WARN_CNT   ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '210', 1, NULL)) WARN_CNT        ");
    	sbSql.append("\n                                                        , SUM(DECODE(TRVA.VOIL_CD, '220', 1, NULL)) ATTEN_CNT       ");
    	sbSql.append("\n                                                 FROM     TBED_RACE_VOIL_ACT TRVA                                   ");
    	sbSql.append("\n                                                        , TBEB_ACDNT_SCR     TAS                                    ");
    	sbSql.append("\n                                                        , TBEB_ORGAN         TOR                                    ");
    	sbSql.append("\n                                                 WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD      = TAS.VOIL_CD                             ");
    	sbSql.append("\n                                                 AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
    	sbSql.append("\n                                                 AND    TRVA.TMS          = TOR.TMS                                 ");
    	sbSql.append("\n                                                 AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
    	sbSql.append("\n                                                 AND    TOR .ABSE_CD     <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TOR .IMMT_CLS_CD <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_DAY      BETWEEN ?                                ");
    	sbSql.append("\n                                                                               AND ?                                ");
    	sbSql.append("\n                                                 GROUP BY                                                           ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                              ) TRVA                                                                ");
    	sbSql.append("\n                                            , (                                                                     ");
    	sbSql.append("\n                                                 SELECT                                                             ");
    	sbSql.append("\n                                                          TRVA.STND_YEAR                                            ");
    	sbSql.append("\n                                                        , TRVA.MBR_CD                                               ");
    	sbSql.append("\n                                                        , TRVA.TMS                                                  ");
    	sbSql.append("\n                                                        , TRVA.DAY_ORD                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_NO                                              ");
    	sbSql.append("\n                                                        , TRVA.RACE_REG_NO                                          ");
    	sbSql.append("\n                                                        , TAS .ACDNT_SCR                                            ");
    	sbSql.append("\n                                                 FROM     TBED_RACE_VOIL_ACT TRVA                                   ");
    	sbSql.append("\n                                                        , TBEB_ACDNT_SCR     TAS                                    ");
    	sbSql.append("\n                                                        , TBEB_ORGAN         TOR                                    ");
    	sbSql.append("\n                                                 WHERE  TRVA.STND_YEAR    = TAS.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD      = TAS.VOIL_CD                             ");
    	sbSql.append("\n                                                 AND    TRVA.STND_YEAR    = TOR.STND_YEAR                           ");
    	sbSql.append("\n                                                 AND    TRVA.MBR_CD       = TOR.MBR_CD                              ");
    	sbSql.append("\n                                                 AND    TRVA.TMS          = TOR.TMS                                 ");
    	sbSql.append("\n                                                 AND    TRVA.DAY_ORD      = TOR.DAY_ORD                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_NO      = TOR.RACE_NO                             ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_REG_NO  = TOR.RACE_REG_NO                         ");
    	sbSql.append("\n                                                 AND    TOR .ABSE_CD     <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TOR .IMMT_CLS_CD <> '003'                                   ");
    	sbSql.append("\n                                                 AND    TRVA.RACE_DAY      BETWEEN ?                                ");
    	sbSql.append("\n                                                                               AND ?                                ");
    	sbSql.append("\n                                                 AND    TRVA.VOIL_CD     IN ('110', '120')                          ");
    	sbSql.append("\n                                               ) TRVB                                                               ");
    	sbSql.append("\n                                     WHERE  TRVA.STND_YEAR   = TRVB.STND_YEAR  (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.MBR_CD      = TRVB.MBR_CD     (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.TMS         = TRVB.TMS        (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.DAY_ORD     = TRVB.DAY_ORD    (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.RACE_NO     = TRVB.RACE_NO    (+)                                  ");
    	sbSql.append("\n                                     AND    TRVA.RACE_REG_NO = TRVB.RACE_REG_NO(+)                                  ");
    	sbSql.append("\n                                   ) TRVA                                                                           ");
    	sbSql.append("\n                          WHERE  TOR .STND_YEAR    = TR  .STND_YEAR                                                 ");
    	sbSql.append("\n                          AND    TOR .MBR_CD       = TR  .MBR_CD                                                    ");
    	sbSql.append("\n                          AND    TOR .TMS          = TR  .TMS                                                       ");
    	sbSql.append("\n                          AND    TOR .DAY_ORD      = TR  .DAY_ORD                                                   ");
    	sbSql.append("\n                          AND    TOR .RACE_NO      = TR  .RACE_NO                                                   ");
    	sbSql.append("\n                          AND    TOR .STND_YEAR    = TRS .STND_YEAR                                                 ");
    	sbSql.append("\n                          AND    TR  .RACE_DGRE_CD = TRS .RACE_DGRE_CD                                              ");
    	sbSql.append("\n                          AND    TOR .RANK         = TRS .RANK                                                      ");
    	sbSql.append("\n                          AND    TOR .STND_YEAR    = TRVA.STND_YEAR  (+)                                            ");
    	sbSql.append("\n                          AND    TOR .MBR_CD       = TRVA.MBR_CD     (+)                                            ");
    	sbSql.append("\n                          AND    TOR .TMS          = TRVA.TMS        (+)                                            ");
    	sbSql.append("\n                          AND    TOR .DAY_ORD      = TRVA.DAY_ORD    (+)                                            ");
    	sbSql.append("\n                          AND    TOR .RACE_NO      = TRVA.RACE_NO    (+)                                            ");
    	sbSql.append("\n                          AND    TOR .RACE_REG_NO  = TRVA.RACE_REG_NO(+)                                            ");
    	sbSql.append("\n                          AND    TOR .ABSE_CD     <> '003'                                                          ");
    	sbSql.append("\n                          AND    TOR .IMMT_CLS_CD <> '003'                                                          ");
    	sbSql.append("\n                          AND    TOR .RACE_DAY      BETWEEN ?                                                       ");
    	sbSql.append("\n                                                        AND ?                                                       ");
    	sbSql.append("\n                          GROUP BY TOR .RACER_NO                                                                    ");
    	sbSql.append("\n                      ) TOR                                                                                         ");
    	sbSql.append("\n                     , (                                                                                            ");
    	sbSql.append("\n                          SELECT                                                                                    ");
    	sbSql.append("\n                                   TAE.RACER_NO                                                                     ");
    	sbSql.append("\n                                 , TAE.RMK                                                                          ");
    	sbSql.append("\n                          FROM     TBEC_ARRANGE_ESC TAE                                                             ");
    	sbSql.append("\n                          WHERE  TAE.STR_DT > ?                                                                     ");
    	sbSql.append("\n                          AND    TAE.RSN_CD LIKE '2%'                                                               ");
    	sbSql.append("\n                      ) TAE                                                                                         ");
    	sbSql.append("\n             WHERE  TRM.RACER_NO                                              = TOR .RACER_NO                       ");
    	sbSql.append("\n             AND    TRM.RACER_NO                                              = TAE .RACER_NO(+)                    ");
// ����� ������ �ٽ� ����  (2011.07.21)	
    	sbSql.append("\n             AND    TRM.RACER_STAT_CD                                        IN ('001', '002')                      ");
        
    	if ( rowsetEsc != null ) {
	    	int nCount = 0;
	        while( rowsetEsc.hasNext() ) {
	            PosRow row = rowsetEsc.next();
	          
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
    	
    	sbSql.append("\n        )                                                                                                              ");

    	sbSql.append("\n UNION  -- �ּ�����(��Ÿ, ������)                                                                                         ");
    	sbSql.append("\n SELECT                                                                                                                ");
    	sbSql.append("\n           TRM.RACER_NO                                                                                                ");
    	sbSql.append("\n         , TRM.NM_KOR                                                                                                  ");
    	sbSql.append("\n         , TRM.RACER_GRD_CD                                                                                            ");
    	sbSql.append("\n         , ? NEW_RACER_GRD                                                                                             ");
    	sbSql.append("\n         , 0 RACE_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 AVG_RANK_SCR                                                                                              ");
    	sbSql.append("\n         , 0 AVG_ACDNT_SCR                                                                                             ");
    	sbSql.append("\n         , 0 AVG_SCR                                                                                                   ");
    	sbSql.append("\n         , 0 WIN_RATIO                                                                                                 ");
    	sbSql.append("\n         , 0 HIGH_RANK_RATIO                                                                                           ");
    	sbSql.append("\n         , 0 HIGH_3_RANK_RATIO                                                                                         ");
    	sbSql.append("\n         , 0 AVG_ST                                                                                                   ");
    	sbSql.append("\n         , 0 F_CNT                                                                                                     ");
    	sbSql.append("\n         , 0 L_CNT                                                                                                     ");
    	sbSql.append("\n         , 0 ABSE_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 RACE_ESC_1_CNT                                                                                            ");
    	sbSql.append("\n         , 0 RACE_ESC_2_CNT                                                                                            ");
    	sbSql.append("\n         , 0 FOUL_ELIM_CNT                                                                                             ");
    	sbSql.append("\n         , 0 ELIM_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 FOUL_WARN_CNT                                                                                             ");
    	sbSql.append("\n         , 0 WARN_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 ATTEN_CNT                                                                                                 ");
    	sbSql.append("\n         , 0 RANK                                                                                                      ");
    	sbSql.append("\n         , TAE.RMK                                                                                                     ");
    	sbSql.append("\n FROM      TBEC_RACER_MASTER TRM                                                                                       ");
    	sbSql.append("\n         , TBEC_ARRANGE_ESC TAE                                                                                        ");
    	sbSql.append("\n WHERE   TRM.RACER_NO = TAE.RACER_NO                                                                                   ");
//    	 ����� ������ �ٽ� ����  (2011.07.26)
    	sbSql.append("\n AND     TRM.RACER_STAT_CD != '003'                                                                                    ");
    	sbSql.append("\n AND     TAE.RSN_CD IN ('004', '101')                                                                                  ");
    	sbSql.append("\n AND     TAE.STR_DT >= (SELECT STR_DT FROM TBEB_RECD_STND_TERM WHERE STND_YEAR = SUBSTR(?,1,4) AND QURT_CD = '001')     ");
    	sbSql.append("\n AND     TAE.END_DT <= (SELECT END_DT FROM TBEB_RECD_STND_TERM WHERE STND_YEAR = SUBSTR(?,1,4) AND QURT_CD = '002')     ");
    	sbSql.append("\n UNION   -- �ּ�����(��������)                                                                                            ");
    	sbSql.append("\n SELECT                                                                                                                ");
    	sbSql.append("\n           TRM.RACER_NO                                                                                                ");
    	sbSql.append("\n         , TRM.NM_KOR                                                                                                  ");
    	sbSql.append("\n         , TRM.RACER_GRD_CD                                                                                            ");
    	sbSql.append("\n         , ? NEW_RACER_GRD                                                                                             ");
    	sbSql.append("\n         , 0 RACE_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 AVG_RANK_SCR                                                                                              ");
    	sbSql.append("\n         , 0 AVG_ACDNT_SCR                                                                                             ");
    	sbSql.append("\n         , 0 AVG_SCR                                                                                                   ");
    	sbSql.append("\n         , 0 WIN_RATIO                                                                                                 ");
    	sbSql.append("\n         , 0 HIGH_RANK_RATIO                                                                                           ");
    	sbSql.append("\n         , 0 HIGH_3_RANK_RATIO                                                                                         ");
    	sbSql.append("\n         , 0 AVG_ST                                                                                                   ");
    	sbSql.append("\n         , 0 F_CNT                                                                                                     ");
    	sbSql.append("\n         , 0 L_CNT                                                                                                     ");
    	sbSql.append("\n         , 0 ABSE_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 RACE_ESC_1_CNT                                                                                            ");
    	sbSql.append("\n         , 0 RACE_ESC_2_CNT                                                                                            ");
    	sbSql.append("\n         , 0 FOUL_ELIM_CNT                                                                                             ");
    	sbSql.append("\n         , 0 ELIM_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 FOUL_WARN_CNT                                                                                             ");
    	sbSql.append("\n         , 0 WARN_CNT                                                                                                  ");
    	sbSql.append("\n         , 0 ATTEN_CNT                                                                                                 ");
    	sbSql.append("\n         , 0 RANK                                                                                                      ");
    	sbSql.append("\n         , TAE.RMK                                                                                                     ");
    	sbSql.append("\n FROM      TBEC_RACER_MASTER TRM                                                                                       ");
    	sbSql.append("\n         , TBEC_ARRANGE_ESC TAE                                                                                        ");
    	sbSql.append("\n WHERE   TRM.RACER_NO = TAE.RACER_NO                                                                                   ");
//    	 ����� ������ �ٽ� ����   (2011.07.26)
    	sbSql.append("\n AND     TRM.RACER_STAT_CD != '003'                                                                                    ");
    	sbSql.append("\n AND     TAE.RSN_CD  LIKE '2%'                                                                                         ");
    	sbSql.append("\n AND     TAE.STR_DT >= ?                                                                                               ");
    	sbSql.append("\n AND     TAE.END_DT <= ?                                                                                               ");
    	sbSql.append("\n ORDER BY AVG_RANK_SCR DESC                                                                                            ");
    	
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_GRD_CD"    ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("STR_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("END_DT"          ));
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_GRD_CD"    ));
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_GRD_END_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_GRD_CD"    ));
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_GRD_STR_DT"));
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_GRD_END_DT"));

    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);
        return rowset;
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
