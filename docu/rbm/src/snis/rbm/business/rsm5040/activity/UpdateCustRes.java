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
package snis.rbm.business.rsm5040.activity;

import java.util.ArrayList;
import java.util.HashMap;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;

public class UpdateCustRes extends SnisActivity {
	
	public UpdateCustRes(){}

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

		int nSaveCount	 = 0;
		int nDeleteCount = 0;
		String sDsName	 = "";
		
		PosDataset ds;
		int nSize		 = 0;
		int nTempCnt	 = 0;
		
    	String sPayYear  = (String)ctx.get("PAY_YEAR");		//지급년도
		sDsName = "dsPcTax";
		
		if( ctx.get(sDsName) != null )
		{
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++ )
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
				{
					nTempCnt = updateCustRes(record);
				} 
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);	
		
    }

    /**
     * <p> PC파일에서 저장된 테이블의 주민번호 정보 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int updateCustRes(PosRecord record)
	{
		if (!"1".equals(record.getAttribute("CHK").toString())) return 0;
		
		PosParameter param = new PosParameter();
		
		int i = 0;
				
		param.setValueParamter(i++, SESSION_USER_ID					 	);		// 1. 수정자ID
		param.setValueParamter(i++, record.getAttribute("TAX_MEET_CD"	));		// 2. 경륜장코드
		param.setValueParamter(i++, record.getAttribute("TAX_SELL_CD"	));		// 3. 운영처코드
		param.setValueParamter(i++, record.getAttribute("TAX_TSN"		));		// 4. 경주권번호
		param.setValueParamter(i++, record.getAttribute("PAY_YEAR"		));		// 5. 지급년도
		
		int dmlcount = this.getDao("rbmdao").update("rsm5040_u01", param);
		
		return dmlcount;
	}
	
}
