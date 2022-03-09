/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020040.activity.SaveEnt.java
 * 파일설명		: 입고등록을 저장한다.  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-11
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020040.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 입고등록에 대한 정보를 등록, 수정, 삭제 한다.
* @auther 유재은 
* @version 1.0
*/
public class SaveEnt extends SnisActivity
{    
    public SaveEnt()
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
    	//사용자 정보 확인
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	//입고수량 변경 
        saveState(ctx);
        
        //입고상태, 부품테이블 현재고량 변경
        saveOrderState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    
    protected void saveOrderState(PosContext ctx) 
    {
    	 //입고상태, 부품테이블 현재고량 변경
    	(new SaveEntState()).runActivity(ctx);
    }
   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	
    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutEnt");
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	entSeq   = (String) ctx.get("ENT_SEQ");
        String  nextEntSeq = (String) ctx.get("NEXT_ENT_SEQ");
        String	entDt   = (String) ctx.get("ENT_DT");
        nSize = ds.getRecordCount();
        
        if(nSize > 0){
        	saveOrderM(stndYear,mbrCd,entSeq,entDt,nextEntSeq);    
        }
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveOrder(record,stndYear,mbrCd,entSeq,entDt,nextEntSeq);            	
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    /**
     * <p> 착순점 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveOrder(PosRecord record, String stndYear, String mbrCd, String entSeq, String entDt, String nextEntSeq)
    {
    	int effectedRowCnt = 0;
    	
    	if(entSeq.equals("") || entSeq == null){
    		effectedRowCnt =mergeEntParts(record, nextEntSeq);    	
    	}
    	else
    	{
    		effectedRowCnt =mergeEntParts(record, entSeq);
    	}
    	
    	return effectedRowCnt;
    }
    /**
     * <p> 착순점 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveOrderM(String stndYear, String mbrCd, String entSeq, String entDt, String nextEntSeq)
    {
    	int effectedRowCnt = 0;
    	if(entSeq.equals("") || entSeq == null){
    		effectedRowCnt =insertEnt(stndYear,mbrCd,entDt,nextEntSeq);   	
    	}
    	
        return effectedRowCnt;
    }
    /**
     * <p>입고등록 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertEnt(String stndYear, String mbrCd, String entDt,String nextEntSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        
        param.setValueParamter(i++, stndYear);
	    param.setValueParamter(i++, mbrCd); 
	    param.setValueParamter(i++, nextEntSeq);
	    param.setValueParamter(i++, entDt);
    	param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbef_ent_if001", param);
    }    
    /**
     * <p>입고등록 디테일 입력/수</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeEntParts(PosRecord record, String nextEntSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0; 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        //param.setValueParamter(i++, record.getAttribute("ENT_SEQ")); 
        param.setValueParamter(i++, nextEntSeq);
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));
        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("ENT_CNT"));		  
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, nextEntSeq);
        return this.getDao("boadao").update("tbef_ent_mf001", param);
    }    
}
