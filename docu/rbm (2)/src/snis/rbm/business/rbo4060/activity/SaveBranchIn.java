/*
 * ================================================================================
 * 시스템 : 부품 출고승인 소스파일 
 * 이름 : snis.rbm.business.rbo4060.activity..java 
 * 파일설명 : 지점입고
 * 작성자 : 장한너울
 * 버전 : 1.0.0 
 * 생성일자 : 2011-11-04
 * 최종수정일자 : 
 * 최종수정자 : 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo4060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBranchIn extends SnisActivity {
	public SaveBranchIn() {
		
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
//		int nDeleteCount = 0;
		String sDsName	 = "";
		String cDsName	 = "";
		
		PosDataset ds;
		int nSize		 = 0;
		int nTempCnt	 = 0;
		String strFlag	= (String)ctx.get("SEND_VALUE"); // 본점(지점)입고(O)/지점입고(BO)/본점(지점)입고취소(C)/지점입고취소(BC)
		sDsName = "dsList";
		cDsName = "dsBrncList";

		
		if( ctx.get(sDsName) != null)
		{
			ds	  = (PosDataset) ctx.get(sDsName);	// dsList
			nSize = ds.getRecordCount();	
			
			if( strFlag.equals("IN") ){
				for ( int i = 0; i < nSize; i++)
				{
					PosRecord record = ds.getRecord(i);
				
					// 지점입고
					if ( record.getAttribute("CHK").equals("1") ){
						
						
						Double dAprvCnt = (Double)record.getAttribute("APRV_CNT");
						if( getStock((String)record.getAttribute("PARTS_CD"),"00") - dAprvCnt.intValue() >=0 ){
							nTempCnt = updatePartsReqIn(record);
							
						    UpdatePartsStock(record);		// 본장 현재고 수정
						    InsertBrncPartsStock(record);	// 지점 현재고 등록
							   		   
							   		  
					   		nSaveCount = nSaveCount + nTempCnt;
					   		
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
					
					
				}
			}
		}else if( ctx.get(cDsName) != null)
		{
			ds	  = (PosDataset) ctx.get(cDsName);	// dsBrncList
			nSize = ds.getRecordCount();	
			
			if( strFlag.equals("CANCEL") ){
				for ( int i = 0; i < nSize; i++)
				{
					PosRecord record = ds.getRecord(i);
				
					// 지점입고취소
					if ( record.getAttribute("CHK").equals("1") ){
						
						//지점출고수량 체크 
						Double dAprvCnt = (Double)record.getAttribute("APRV_CNT");
						if( getStock((String)record.getAttribute("PARTS_CD"),(String)record.getAttribute("BRNC_CD")) - dAprvCnt.intValue() >=0 ){
							nTempCnt = updatePartsReqInCancel(record);
							
							UpdateCnlPartsStock(record);		// 본장 현재고 수정
						    UpdateCnlBrncPartsStock(record);	// 각지점 현재고 수정

						}else{
							try { 
				    			throw new Exception(); 
				        	} catch(Exception e) {       		
				        		this.rollbackTransaction("tx_rbm");
				        		Util.setSvcMsg(ctx, "현재고는 0 이상이어야 합니다.");
				        		
				        		return;
				        	}
						}

					}
					nSaveCount = nSaveCount + nTempCnt;
				}
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);	
	}
	


    /**
     * <p> 지점 현재고 등록 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int InsertBrncPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 1. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	 ));	// 2. 지점코드
		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"));	// 3. 지점입고수량
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 4. 작성자ID
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_i03", param);
		
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

		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"));	// 1. 지점입고수량
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. 수정자ID	
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"));		// 3. 부품코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_u01", param);
		
		return dmlcount;
	}
	

    /**
     * <p> 현재고 수정(지점입고 취소 시) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int UpdateCnlPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"));	// 1. 지점입고수량
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 3. 부품코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_u03", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 지점 현재고 수정(지점입고 취소 시) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int UpdateCnlBrncPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"	));	// 1. 지점입고수량
		param.setValueParamter(i++, SESSION_USER_ID					  	 );	// 2. 수정자ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"		));	// 3. 부품코드
		param.setValueParamter(i++, record.getAttribute("BRNC_CD" 		));	// 4. 지점코드
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_u04", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> 출고신청  입고정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePartsReqIn(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
  

        param.setValueParamter(i++, record.getAttribute("BRNC_IN_ID"));		
        param.setValueParamter(i++, record.getAttribute("BRNC_IN_DT"));		
        param.setValueParamter(i++, SESSION_USER_ID);								
     
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD" ));		
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));	
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));	
        


        int dmlcount = this.getDao("rbmdao").update("rbo4060_u05", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 출고신청  입고취소정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePartsReqInCancel(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
	
        param.setValueParamter(i++, SESSION_USER_ID);								
     
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD" ));		
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));	
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));	
        


        int dmlcount = this.getDao("rbmdao").update("rbo4060_u06", param);
        return dmlcount;
    }
    
    
	/**
     * <p> 현재고 수량 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int getStock(String sPartsCd, String sBrncCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, sPartsCd);
        param.setWhereClauseParameter(i++, sBrncCd);
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo4060_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("STK_CNT"));

        return Integer.parseInt(rtnKey);
    }
}
