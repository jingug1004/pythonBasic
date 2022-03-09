/*
 * ================================================================================
 * 시스템 : 부품 출고신청 소스파일 
 * 이름 : snis.rbm.business.rbo4020.activity..java 
 * 파일설명 : 출고신청 
 * 작성자 : 장한너울
 * 버전 : 1.0.0 
 * 생성일자 : 2011-11-03
 * 최종수정일자 : 
 * 최종수정자 : 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo4040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePartsOutReq extends SnisActivity {
	public SavePartsOutReq() {
		
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
		
		sDsName = "dsAprvList";
		
		if( ctx.get(sDsName) != null)
		{
			nTempCnt = 0;
			
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			Double dSeq, dReqCnt;
			
			for ( int i = 0; i < nSize; i++)
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == PosRecord.UPDATE
							||  record.getType() == PosRecord.INSERT) {
					dSeq = (Double)record.getAttribute("SEQ");
					
					if( ( record.getType() == PosRecord.UPDATE && dSeq.intValue() != 0 ) ) {
						if( !getAprvStas(record).equals("001") && !getAprvStas(record).equals("002")) {
							try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "승인된 데이터가 존재하므로 저장을 하실 수 없습니다.");
			            		
			            		return;
			            	}
						}
					}
					
					dReqCnt = (Double)record.getAttribute("REQ_CNT");
					
					//신청수량이 0이면 삭제
					if( dReqCnt.intValue() == 0 ) {
						deletePartsOutReq(record);
					} else {
						if ( (nTempCnt = updatePartsOutReq(record)) == 0 ) {
							nTempCnt += insertPartsOutReq(record);
						}
					}	
				}
				
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deletePartsOutReq(record);	            	
	            }	
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
		
		
	}
	
	
    /**
     * <p> 출고신청 정보 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int insertPartsOutReq(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		System.out.println("111111111111111");
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. 지점코드
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 3-1. 순번(부품코드)
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 3-2. 순번(지점코드)
		param.setValueParamter(i++, SESSION_USER_ID);	// 4. 신청자
		param.setValueParamter(i++, record.getAttribute("STK_CNT"));	// 5. 신청가능수량
		param.setValueParamter(i++, record.getAttribute("REQ_CNT"	));	// 6. 신청수량
		param.setValueParamter(i++, record.getAttribute("APRV_STAS"	));	// 7. 승인상태
		param.setValueParamter(i++, record.getAttribute("REQ_RSN"	));	// 8. 신청사유
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 9. 작성자ID
		
		int dmlcount = this.getDao("rbmdao").update("rbo4040_i01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 출고신청 정보 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int updatePartsOutReq(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("REQ_CNT"	));	// 1. 신청수량
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 2. 신청자
		param.setValueParamter(i++, record.getAttribute("REQ_RSN"	));	// 3. 신청사유
		param.setValueParamter(i++, record.getAttribute("APRV_STAS"	));	// 4. 승인상태																		
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 5. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 6. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 7. 지점코드
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 8. 순번
		
		int dmlcount = this.getDao("rbmdao").update("rbo4040_u01", param);
		return dmlcount;
	}
	
	
    /**
     * <p> 출고신청 정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int deletePartsOutReq(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. 지점코드
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 3. 순번
		
		int dmlcount = this.getDao("rbmdao").update("rbo4040_d01", param);
		return dmlcount;
	}
	
	/**
     * <p> 승인상태 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    public String getAprvStas(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo4040_s04", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("APRV_STAS"));

        return rtnKey;
    }
}
