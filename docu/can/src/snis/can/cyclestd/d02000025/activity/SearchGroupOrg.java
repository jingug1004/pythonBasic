/*================================================================================
 * �ý���			: ����������
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000025.activity.SearchGroupOrg.java
 * ���ϼ���		: �ĺ�������
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2007-03-20
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

package snis.can.cyclestd.d02000025.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SearchGroupOrg extends SnisActivity{

	public SearchGroupOrg()
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
			StringBuffer sbSql = new StringBuffer();
			PosRowSet rowset;
			sbSql.append("\n SELECT 														 ");	
			sbSql.append("\n '' NUM ,'' RACE_NO,'' BACK_NO														 ");
			sbSql.append("\n    ,RACE_YY		,ROUND		,CAND_NO	,NM_KOR		,RACER_PERIO_NO ");
			sbSql.append("\n    ,MAT_CD		,RACE_GRD_CD,GEAR_RATE	,TOT_AVG,GRD,STRATEGY 	 ");
			sbSql.append("\n    ,LEG_TPE		,WIN_RATE	,HIGH_RANK_RATE					 ");
			sbSql.append("\n    ,RANK1_CNT	,RANK2_CNT	,RANK3_CNT	,RANK_OUT 				 ");
			sbSql.append("\n    ,ELIM1_CNT	,ELIM2_CNT	,ELIM3_CNT	,TOT_RANK	,ESTM_SCR 	 ");
			sbSql.append("\n   ,BF_ROUND	,BF_RACE										 ");
			sbSql.append("\n FROM(  														 ");
			sbSql.append("\n SELECT 														 ");
			sbSql.append("\n     A.RACE_YY				-- �⵵								 ");
			sbSql.append("\n    ,A.ROUND				-- ȸ��   						 		 ");
			sbSql.append("\n    ,A.CAND_NO				-- �ĺ��� ��ȣ							 ");			
			sbSql.append("\n    ,B.NM_KOR				-- �ĺ��� �̸�  						 ");
			sbSql.append("\n   ,B.RACER_PERIO_NO    	-- ��� 								 ");
			sbSql.append("\n    ,A.MAT_CD 													 ");
			sbSql.append("\n    ,D.RACE_GRD_CD	 											 ");
			sbSql.append("\n    ,C.GEAR_RATE			-- ����� 							 ");
			sbSql.append("\n    ,C.YY_AVG_RACE_SCR AS TOT_AVG  -- ����� 						 ");
			sbSql.append("\n    ,C.GRD				-- ���									 ");
			sbSql.append("\n   ,C.STRATEGY			-- ����									 ");
			sbSql.append("\n   ,C.LEG_TPE           -- ���� 									 ");
			sbSql.append("\n   ,C.WIN_RATE			-- �·� 									 ");
			sbSql.append("\n   ,C.HIGH_RANK_RATE    -- ������    						 		");
			sbSql.append("\n    ,C.RANK1_CNT		-- 1��									 ");
			sbSql.append("\n   ,C.RANK2_CNT			-- 2�� 									");
			sbSql.append("\n   ,C.RANK3_CNT			-- 3�� 									");
			sbSql.append("\n   ,(C.RANK4_CNT + C.RANK5_CNT + C.RANK6_CNT + C.RANK7_CNT)AS RANK_OUT -- ���� ");
			sbSql.append("\n   ,C.ELIM1_CNT	   	    -- �ǰ�				 ");
			sbSql.append("\n    ,C.ELIM2_CNT			-- ���			 ");
			sbSql.append("\n    ,C.ELIM3_CNT			-- ����			 ");
			sbSql.append("\n    ,C.TOT_RANK			-- �Ѽ���				 ");
			sbSql.append("\n    ,C.ESTM_SCR			-- ���� 	 		");
			sbSql.append("\n    ,C.BF_ROUND			-- ��ȸ��				 ");
			sbSql.append("\n    ,T1.BF_RACE 							");
			sbSql.append("\n    ,T1.RANK 								");
			sbSql.append("\n FROM										 ");
			sbSql.append("\n    TBDB_CAND_ASSIGN A						 ");
			sbSql.append("\n    ,TBDB_CAND_IDENT B 						");
			sbSql.append("\n    ,TBDB_REC_TOT C							 ");
			sbSql.append("\n    ,TBDB_ENFOR_RACE_GRD D					 ");
			sbSql.append("\n   ,( SELECT 								 ");
			sbSql.append("\n 			 RACE_YY 						");
			sbSql.append("\n 			 ,ROUND 						");
			sbSql.append("\n 			 ,CAND_NO 						");
			sbSql.append("\n 			 ,RANK							");
			sbSql.append("\n 			 ,SUBSTR((SELECT CD_NM FROM TBDA_SPEC_CD WHERE T2.RACE_GRD_CD = CD),0,2)||T2.RACE_NO ||'-'||T2.RANK AS BF_RACE ");	
			sbSql.append("\n  FROM 								");
			sbSql.append("\n  TBDB_RACE_REC_DETL T2				 ");
			sbSql.append("\n WHERE RACE_YY = '2007' 				");
			sbSql.append("\n AND ROUND = '1' -- ��ȸ��				 ");
			sbSql.append("\n )T1			   						 ");
			sbSql.append("\n WHERE					");
			sbSql.append("\n    A.CAND_NO = B.CAND_NO 			");
			sbSql.append("\n    AND A.CAND_NO = C.CAND_NO		");
			sbSql.append("\n    AND A.MAT_CD = D.MAT_CD 		");
			sbSql.append("\n    AND A.CAND_NO = T1.CAND_NO		 ");
			sbSql.append("\n   AND A.RACE_YY = ? 					");
			sbSql.append("\n    AND A.ROUND =  ? 					 ");
			sbSql.append("\n    AND A.DCSN_GBN = '0' 				");
			sbSql.append("\n    AND A.GBN = '1'						 ");
			sbSql.append("\n 	   ORDER BY A.MAT_CD,D.RACE_GRD_CD, T1.RANK--A.MAT_CD --,C.TOT_RANK	DESC		");	   
			sbSql.append("\n  )	");

			
			PosParameter param = new PosParameter();
	    	
	        int i = 0;
	        
	        param.setWhereClauseParameter(i++, ctx.get("RACE_YY"));  
	        param.setWhereClauseParameter(i++, ctx.get("ROUND"   )); 
	    	rowset = this.getDao("candao").findByQueryStatement(sbSql.toString(), param);
	    	
	    	String sResultKey = "dsGroupOrg";
	        ctx.put(sResultKey, rowset);
	        Util.addResultKey(ctx, sResultKey);
	    }
}
