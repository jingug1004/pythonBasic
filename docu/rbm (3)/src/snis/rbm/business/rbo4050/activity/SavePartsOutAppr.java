/*
 * ================================================================================
 * 시스템 : 부품 출고승인 소스파일 
 * 이름 : snis.rbm.business.rbo4050.activity..java 
 * 파일설명 : 출고승인
 * 작성자 : 장한너울
 * 버전 : 1.0.0 
 * 생성일자 : 2011-11-03
 * 최종수정일자 : 
 * 최종수정자 : 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo4050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rbo4040.activity.*;

public class SavePartsOutAppr extends SnisActivity {
	public SavePartsOutAppr() {
		
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
		
		sDsName = "dsList";
		String sAprvStasChg = (String)ctx.get("APRV_STAS_CHG");
		Double dAprvCnt;
		
		if( ctx.get(sDsName) != null)
		{
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			SavePartsOutReq req = new SavePartsOutReq();
			
			for ( int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				
				if( sAprvStasChg.equals("002_001") ) {	//승인대기-> 반려
					if( !req.getAprvStas(record).equals("002") ) {
						try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "승인요청 상태가 아닌  데이터가 존재하므로 반려하실 수 없습니다.");
		            		return;
		            	}
					}
				}
				
				if( sAprvStasChg.equals("002_003") ) {	//승인대기 -> 승인
					if( !req.getAprvStas(record).equals("002") ) {
						try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "승인요청 상태가 아닌  데이터가 존재하므로 승인하실 수 없습니다.");
		            		return;
		            	}
					}
				}
				
				if( sAprvStasChg.equals("003_002") ) {	//승인완료 -> 승인대기
					if( !req.getAprvStas(record).equals("003") ) {
						try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "승인완료 상태가 아닌  데이터가 존재하므로 승인취소하실 수 없습니다.");
		            		return;
		            	}
					}
				}
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
					nTempCnt = updatePartsOutAppr(record);
				}

				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);		
	}
	
    /**
     * <p> 출고승인 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int updatePartsOutAppr(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		param.setValueParamter(i++, record.getAttribute("APRV_STAS"	  	));	// 1. 승인상태
		param.setValueParamter(i++, record.getAttribute("APRV_CNT"	  	));	// 2. 승인수량
		param.setValueParamter(i++, record.getAttribute("APRV_DT"	  	));	// 3. 승인일자
		param.setValueParamter(i++, record.getAttribute("APRV_OFIR_NO"	));	// 4. 승인자
		param.setValueParamter(i++, SESSION_USER_ID				  	   	 );	// 5. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"		));	// 6. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"		));	// 7. 지점코드
		param.setValueParamter(i++, record.getAttribute("SEQ"			));	// 8. 순번
		
		int dmlcount = this.getDao("rbmdao").update("rbo4050_u01", param);
		
		return dmlcount;
	}
	

}
