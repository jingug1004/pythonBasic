/*================================================================================
 * 시스템			: 선수관리
 * 소스파일 이름	: snis.boa.racer.e03010010.activity.SearchRacerFood.java
 * 파일설명		: 선수정보등록
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-13
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
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

    	sbQuery.append("\n SELECT TRFE.SEQ                                				--일련번호    	");
    	sbQuery.append("\n              ,TA.STND_YEAR                             		--기준년도    	");
    	sbQuery.append("\n              ,TA.MBR_CD                             			--경정장코드    ");
    	sbQuery.append("\n              ,TA.TMS                                			--회차              ");
    	sbQuery.append("\n              ,NVL(?, TRFE.FD_EXP_DT) FD_EXP_DT      			--급식일   	");  
    	sbQuery.append("\n              ,TA.RACER_NO                           			--등록번호       ");
    	sbQuery.append("\n              ,TRM.NM_KOR                            			--선수명          ");
    	sbQuery.append("\n              ,NVL(TRFE.BRKF_PRC,0) BRKF_PRC         			--조식가격       ");
    	sbQuery.append("\n              ,NVL(TRFE.LUNCH_PRC,0) LUNCH_PRC       			--중식가격       ");
    	sbQuery.append("\n              ,NVL(TRFE.DINN_PRC,0) DINN_PRC         			--석식가격       ");
    	sbQuery.append("\n              ,NVL(TRFE.SNCK_PRC1,0) SNCK_PRC1         		--간식가격1       ");
    	sbQuery.append("\n              ,NVL(TRFE.SNCK_PRC2,0) SNCK_PRC2         		--간식가격2       ");
    	sbQuery.append("\n              ,NVL(TRFE.SUM_AMT,0) SUM_AMT              		--합계금액       ");
    	sbQuery.append("\n              ,TFEB.BRKF_PRC AS BRKF_BAS             			--조식기준       ");
    	sbQuery.append("\n            ,TFEB.LUNCH_PRC AS LUNCH_BAS           			--중식기준       ");
    	sbQuery.append("\n            ,TFEB.DINN_PRC AS DINN_BAS             			--석식기준       ");
    	sbQuery.append("\n            ,TFEB.SNCK_PRC AS SNCK_BAS             			--간식기준       ");
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
