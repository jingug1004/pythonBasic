/*
 * ================================================================================
 * 시스템 : 부품 출고승인 소스파일 
 * 이름 : snis.rbm.business.rbo4090.activity..java 
 * 파일설명 : 지점부품사용등록
 * 작성자 : 장한너울
 * 버전 : 1.0.0 
 * 생성일자 : 2011-11-04
 * 최종수정일자 : 
 * 최종수정자 : 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo4090.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBranchUse extends SnisActivity {
	public SaveBranchUse() {
		
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
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
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
		
		sDsName = "dsListDetail";
		
		if( ctx.get(sDsName) != null)
		{
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++)
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + deleteBranchUse(record);
					UpdateDelPartsStock(record);
				}

				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
					//현재고가 음수가 되면 사용 불가능
					Double dUseCnt = (Double)record.getAttribute("USE_CNT");
					
					if( getStock(record) - dUseCnt.intValue() >=0 ){
						nTempCnt += insertBranchUse(record);
						UpdatePartsStock(record);
					} else {
						try { 
			    			throw new Exception(); 
			        	} catch(Exception e) {       		
			        		this.rollbackTransaction("tx_rbm");
			        		Util.setSvcMsg(ctx, "현재고는 0 이상이어야 합니다.");
			        		
			        		return;
			        	}
					}
				}
				
				if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
					nTempCnt += updateBranchUse(record);
				}
				
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
	}
	
    /**
     * <p> 지점입고 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int insertBranchUse(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. 지점코드
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 3-1. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 3-2. 지점코드
		param.setValueParamter(i++, record.getAttribute("USE_DT"	));	// 4. 사용일자
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 5. 사용자
		param.setValueParamter(i++, record.getAttribute("USE_CNT"	));	// 6. 사용수량
		param.setValueParamter(i++, record.getAttribute("USE_RSN"	));	// 7. 사용내역
		param.setValueParamter(i++, SESSION_USER_ID				  	 );	// 8. 작성자ID
																		// 9. 작성일시
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_i01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 지점입고 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int updateBranchUse(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("USE_DT"	));	// 1. 사용일자
		param.setValueParamter(i++, record.getAttribute("USE_RSN"	));	// 2. 사용내역
		param.setValueParamter(i++, SESSION_USER_ID				  	 );	// 3. 수정자ID
		
		i = 0;
		param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD")); // 4. 부품코드
		param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	 // 5. 지점코드
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"	));	 // 6. 순번
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_u04", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 현재고 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int UpdatePartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("USE_CNT"));	// 1. 지점입고수량
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. 수정자ID	
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"));		// 3. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		// 3. 부품코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_u02", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 현재고 수정(delete) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int UpdateDelPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("USE_CNT"));	// 1. 지점입고수량
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. 수정자ID	
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"));		// 3. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		// 3. 부품코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_u03", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 지점입고 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int deleteBranchUse(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. 지점코드
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 3. 순번
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_d01", param);
		return dmlcount;
	}
	
	/**
     * <p> 현재고 수량 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int getStock(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo4090_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("STK_CNT"));

        return Integer.parseInt(rtnKey);
    }
}
