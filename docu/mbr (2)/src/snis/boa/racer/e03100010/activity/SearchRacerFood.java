/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.boa.racer.e03010010.activity.SearchRacerFood.java
 * ���ϼ���		: �����������
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2008-01-13
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03100010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchRacerFood extends SnisActivity {
    public SearchRacerFood()
    {
    }

    public String runActivity(PosContext ctx)
    {
    	SearchFoodInfo(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    protected void SearchFoodInfo(PosContext ctx) 
    {
    	StringBuffer sbQuery = new StringBuffer();

    	sbQuery.append("\n SELECT TRFE.SEQ                                				--�Ϸù�ȣ    	");
    	sbQuery.append("\n              ,TA.STND_YEAR                             		--���س⵵    	");
    	sbQuery.append("\n              ,TA.MBR_CD                             			--�������ڵ�    ");
    	sbQuery.append("\n              ,TA.TMS                                			--ȸ��              ");
    	sbQuery.append("\n              ,NVL(?, TRFE.FD_EXP_DT) FD_EXP_DT      			--�޽���   	");  
    	sbQuery.append("\n              ,TA.RACER_NO                           			--��Ϲ�ȣ       ");
    	sbQuery.append("\n              ,TRM.NM_KOR                            			--������          ");
    	sbQuery.append("\n              ,NVL(TRFE.BRKF_PRC,0) BRKF_PRC         			--���İ���       ");
    	sbQuery.append("\n              ,NVL(TRFE.LUNCH_PRC,0) LUNCH_PRC       			--�߽İ���       ");
    	sbQuery.append("\n              ,NVL(TRFE.DINN_PRC,0) DINN_PRC         			--���İ���       ");
    	sbQuery.append("\n              ,NVL(TRFE.SNCK_PRC1,0) SNCK_PRC1         		--���İ���1       ");
    	sbQuery.append("\n              ,NVL(TRFE.SNCK_PRC2,0) SNCK_PRC2         		--���İ���2       ");
    	sbQuery.append("\n              ,NVL(TRFE.SUM_AMT,0) SUM_AMT              		--�հ�ݾ�       ");
    	sbQuery.append("\n              ,TFEB.BRKF_PRC AS BRKF_BAS             			--���ı���       ");
    	sbQuery.append("\n            ,TFEB.LUNCH_PRC AS LUNCH_BAS           			--�߽ı���       ");
    	sbQuery.append("\n            ,TFEB.DINN_PRC AS DINN_BAS             			--���ı���       ");
    	sbQuery.append("\n            ,TFEB.SNCK_PRC AS SNCK_BAS             			--���ı���       ");
    	sbQuery.append("\n FROM    (SELECT TA.STND_YEAR															       	");
    	sbQuery.append("\n 								, TA.MBR_CD															       		");
    	sbQuery.append("\n 							, TA.TMS															       				");
    	sbQuery.append("\n 							, TA.RACER_NO															       		");
    	sbQuery.append("\n 							, TA.BOAT_NO															       		");
    	sbQuery.append("\n 							, TA.MOT_NO 															       		");
    	sbQuery.append("\n 					FROM TBEB_ARRANGE TA															      ");
    	sbQuery.append("\n 							, TBEB_RACE_TMS TRT															    ");
    	sbQuery.append("\n 					WHERE TRT.ARRANGE_CMPL_YN   ='Y'												");
    	sbQuery.append("\n 					AND TRT.STND_YEAR   = TA.STND_YEAR											");
    	sbQuery.append("\n 					AND TRT.TMS         = TA.TMS) TA                        ");
    	sbQuery.append("\n         ,(SELECT * FROM TBEC_RACER_FD_EXP WHERE                          ");
    	sbQuery.append("\n             STND_YEAR   		 	= ?                                     ");
    	sbQuery.append("\n             AND MBR_CD      		= ?                                     ");
    	sbQuery.append("\n             AND TMS         		= ?                                     ");
    	sbQuery.append("\n             AND FD_EXP_DT   		= ?) TRFE                           	");
    	sbQuery.append("\n       , TBEC_RACER_MASTER TRM                                            ");
    	sbQuery.append("\n       , TBEC_FD_EXP_BAS TFEB                                             ");
    	sbQuery.append("\n WHERE TA.STND_YEAR        = NVL(?,TA.STND_YEAR)                     		");
    	sbQuery.append("\n     AND TA.MBR_CD         = NVL(?,TA.MBR_CD)                         	");
    	sbQuery.append("\n     AND TA.TMS            = ?                                           	");
    	sbQuery.append("\n     AND TA.STND_YEAR      = TRFE.STND_YEAR(+)                            ");
    	sbQuery.append("\n     AND TA.MBR_CD         = TRFE.MBR_CD(+)                               ");
    	sbQuery.append("\n     AND TA.TMS            = TRFE.TMS(+)                                  ");
    	sbQuery.append("\n     AND TA.RACER_NO       = TRFE.RACER_NO(+)                             ");
    	sbQuery.append("\n     AND TA.MBR_CD         = TFEB.MBR_CD                                  ");
    	sbQuery.append("\n     AND TA.STND_YEAR      = TFEB.STND_YEAR                               ");
    	sbQuery.append("\n     AND TA.RACER_NO       = TRM.RACER_NO                                 ");
    	sbQuery.append("\n ORDER BY TRM.RACER_NO                                     	            ");
    	
        PosParameter param = new PosParameter();
        int i = 0;
    	param.setWhereClauseParameter(i++, ctx.get("FD_EXP_DT         ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("TMS         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("FD_EXP_DT         ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("MBR_CD         ".trim()));
    	param.setWhereClauseParameter(i++, ctx.get("TMS         ".trim()));

    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutRacerFood";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);

        Util.setSearchCount(ctx, rowset.getAllRow().length);
    }
}
