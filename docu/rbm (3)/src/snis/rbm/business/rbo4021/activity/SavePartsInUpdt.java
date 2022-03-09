/*
 * ================================================================================
 * 시스템 : 부품 입고 관리 소스파일 
 * 이름 : snis.rbm.business.rbo4021.activity..java 
 * 파일설명 : 입고 이력관리 팝업 
 * 작성자 : 장한너울
 * 버전 : 1.0.0 
 * 생성일자 : 2011-11-10
 * 최종수정일자 : 
 * 최종수정자 : 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo4021.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePartsInUpdt extends SnisActivity {
	public SavePartsInUpdt() {
		
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
		
		if( ctx.get(sDsName) != null )
		{
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++ )
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
				{
					nTempCnt = updatePartsIn(record);
							   UpdatePartsStock(record);
				}else if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
				{					
					nTempCnt = insertPartsIn(record);
							   UpdatePartsStock(record);
				}else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
				{
					nDeleteCount = nDeleteCount + deletePartIn(record);
					  							  DeletePartsStock(record);
				}
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
	
		
	}
	
	
    /**
     * <p> 입고관리 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int insertPartsIn(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. 부품코드
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 2-1). 부품코드(SEQ)
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));	// 2-2). 입고일자(SEQ)															
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));	// 3. 입고일자
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 4. 입고자 (IN_NO)
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	));	// 5. 입고수량
		param.setValueParamter(i++, record.getAttribute("UPDT_RSN"	));	// 6. 수정사유
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 7. 작성자ID

		int dmlcount = this.getDao("rbmdao").update("rbo4021_i01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 입고관리 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int updatePartsIn(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	));		// 1. 입고수량
		param.setValueParamter(i++, record.getAttribute("UPDT_RSN"	));		// 2. 수정사유
		param.setValueParamter(i++, SESSION_USER_ID					 );		// 3. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));		// 4. 부품코드
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));		// 5. 입고일자
		param.setValueParamter(i++, record.getAttribute("SEQ"		));		// 6. 순번
		
		int dmlcount = this.getDao("rbmdao").update("rbo4021_u01", param);
		
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

		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 1. 부품코드
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	 ));	// 2. 입고수량
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 3. 수정자ID
		
		int dmlcount = this.getDao("rbmdao").update("rbo4020_u01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 입고관리 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */	
	protected int deletePartIn(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, SESSION_USER_ID					 );	// 1. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 2. 부품코드
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));	// 3. 입고일자
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 4. 순번
		
		int dmlcount = this.getDao("rbmdao").update("rbo4021_d01", param);
		return dmlcount;
		
	}
	
	
    /**
     * <p> 현재고 수정 (입고 삭제 시) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int DeletePartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	 ));	// 1. 입고수량
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 2. 부품코드
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 3. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 4. 부품코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4020_d02", param);
		
		return dmlcount;
	}
}
