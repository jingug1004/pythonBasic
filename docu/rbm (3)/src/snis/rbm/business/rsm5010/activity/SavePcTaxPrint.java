/*================================================================================
 * 시스템			: 지급조서내역
 * 소스파일 이름	: snis.rbm.business.rsm5010.activity.SavePayCntnt
 * 파일설명		: 지급조서관리_상세내역 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm5010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePcTaxPrint extends SnisActivity {
	
	public SavePcTaxPrint(){}

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
        
        sDsName = "dsPcTax";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {		        	
		        	nTempCnt = savePcTaxPrint(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> 지급조서관리_상세내역 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int savePcTaxPrint(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("PRINT_DT"));	//경륜장코드
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("SELL_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("TSN"));
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_u04", param);
	
        return dmlcount;
    }
}
