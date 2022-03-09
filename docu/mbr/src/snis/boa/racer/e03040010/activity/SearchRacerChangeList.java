/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03040010.activity.SaveEquipmentUnConfirm.java
 * 파일설명		: 모터/보트배정 확정취소
 * 작성자			: 강민수
 * 버전			: 1.0.0
 * 생성일자		: 2008-05-14
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 부상선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SearchRacerChangeList extends SnisActivity
{    
    public SearchRacerChangeList()
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
     * <p> 모터보트 배정상태 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected void searchState(PosContext ctx)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT  /* searchRacerChangeList */ ");
    	sbSql.append("\n          TA .RACER_NO               ");
    	sbSql.append("\n        , TRM.NM_KOR                 ");
    	sbSql.append("\n        , TRM.RACER_GRD_CD           ");
    	sbSql.append("\n        , TRM.RACER_PERIO_NO         ");
    	sbSql.append("\n FROM     TBEB_ARRANGE      TA       ");
    	sbSql.append("\n        , TBEC_RACER_MASTER TRM      ");
    	sbSql.append("\n WHERE  TA.RACER_NO  = TRM.RACER_NO  ");
    	sbSql.append("\n AND    TA.STND_YEAR = ?             ");
    	sbSql.append("\n AND    TA.MBR_CD    = ?             ");
    	sbSql.append("\n AND    TA.TMS       = ?             ");
    	sbSql.append("\n AND    NVL(TA.EXCL_YN,'N') = ?      ");
    	
        String sDsName = "dsOutRacerArrange";
        PosDataset ds  = (PosDataset) ctx.get(sDsName);
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    TA.RACER_NO NOT IN ( ");
            for ( int i = 0; i < nSize; i++ ) 
            {
                PosRecord record = ds.getRecord(i);

                if ( i == 0 ) {
                	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
                } else { 
                	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
                }
            }
        	sbSql.append("\n                                                 )             ");
        }
        
    	int i = 0;
    	PosParameter param = new PosParameter();
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        param.setWhereClauseParameter(i++, ctx.get("TMS")      );
        param.setWhereClauseParameter(i++, ctx.get("EXCL_YN")  );
	    
        PosRowSet rowsetRacer = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);

        String sResultKey = "dsOutRacerList";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
    }
}


