/*================================================================================
 * 시스템			: 기타소득세 내역 확정
 * 소스파일 이름	: snis.rbm.business.rsm5010.activity.SaveCfmCntnt
 * 파일설명		: 기타소득세 확정내역 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-15
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm5060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class DeleteGitaCfmCntnt extends SnisActivity {
	
	public DeleteGitaCfmCntnt(){}

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
    	saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sPayYear = (String)ctx.get("PAY_YEAR");	//지급년도
        String sPayMm   = (String)ctx.get("PAY_MM");	//지급월
        
	    nTempCnt = deleteGitaCfmCntnt(sPayYear,sPayMm);
		
	    
		Util.setDeleteCount(ctx, nTempCnt);
    }

    /**
     * <p> 기타소득세 확정여부 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteGitaCfmCntnt(String strYear, String strMon) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, strYear );	//년
        param.setValueParamter(i++, strMon	);	//월
                
        int dmlcount = this.getDao("rbmdao").update("rsm5060_d02", param);

        return dmlcount;
    }    
}
