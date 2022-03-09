/*================================================================================
 * 시스템			: 무료입장인원 관리
 * 소스파일 이름	: snis.rbm.business.rem6011.activity.SaveTradeList.java
 * 파일설명		: 무료입장인원 저장
 * 작성자			: 서주원
 * 버전			: 1.0.0
 * 생성일자		: 2017-07-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem6011.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveTradeList extends SnisActivity {
	
	public SaveTradeList(){}

	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	int nSize      = 0;
    	int nDeleteCount = 0;
    	
    	String sFlag = "N";	//메세지를 사용자에게 띄울지 말지를 정하는 Flag
    	String sMsg  = "";
    	PosDataset ds;
    	
        String sDsName  = "dsTradeList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {	
	            	
	            	nDeleteCount = nDeleteCount + deleteList(record);
		        	nSaveCount += saveList(record);
		        }
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteList(record);
	            }
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }
    
    /**
     * <p> 무료입장인원 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveList(PosRecord record) 
    {
        //TBRC_T_TRADE_SUM
    	PosParameter param = new PosParameter();   
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("TRADE_DATE"));		// 입장일자
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		// 지점
        param.setValueParamter(i++, record.getAttribute("CNT"));			// 방문자수
        param.setValueParamter(i++, record.getAttribute("USER_ID"));		// 사용자ID(수정자)
        
        int dmlcount = this.getDao("rbmdao").update("rem6011_i01", param);
        
        //TBRC_T_TRADE_RACE_SUM
        PosParameter raceparam = new PosParameter();   
        int j = 0;

        raceparam.setValueParamter(j++, record.getAttribute("TRADE_DATE"));		// 입장일자
        raceparam.setValueParamter(j++, record.getAttribute("TRADE_DATE"));		// 입장일자
        raceparam.setValueParamter(j++, record.getAttribute("MEET_CD"));		// 경주구분
        raceparam.setValueParamter(j++, record.getAttribute("COMM_NO"));		// 지점
        raceparam.setValueParamter(j++, record.getAttribute("MEET_CD"));		// 경주구분
        raceparam.setValueParamter(j++, record.getAttribute("CNT"));			// 방문자수
        
        int racecount = this.getDao("rbmdao").update("rem6011_i02", raceparam);
        
        //TBRC_T_TRADE
    	
        PosParameter tradeparam = new PosParameter();   
        int z = 0;

        tradeparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// 입장일자
        tradeparam.setValueParamter(z++, record.getAttribute("COMM_NO"));		// 지점
        tradeparam.setValueParamter(z++, record.getAttribute("USER_ID"));		// 사용자ID(수정자)
        
        int tradecount = this.getDao("rbmdao").update("rem6011_i03", tradeparam);
        
        return dmlcount;
    }
        
    /**
     * <p> 무료입장인원  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteList(PosRecord record) 
    {
    	//TBRC_T_TRADE_SUM
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("TRADE_DATE" ));
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));
        
        int dmlcount = this.getDao("rbmdao").update("rem6011_d01", param);
        
        //TBRC_T_TRADE_RACE_SUM
        PosParameter raceparam = new PosParameter();
        int j = 0;
        
        raceparam.setWhereClauseParameter(j++, record.getAttribute("TRADE_DATE" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("COMM_NO" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("MEET_CD" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("TRADE_DATE" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("MEET_CD" ));
        
        int racecount = this.getDao("rbmdao").update("rem6011_d02", raceparam);
        
        //TBRC_T_TRADE
        PosParameter tradeparam = new PosParameter();
        int z = 0;
        
        tradeparam.setWhereClauseParameter(z++, record.getAttribute("TRADE_DATE" ));
        tradeparam.setWhereClauseParameter(z++, record.getAttribute("COMM_NO" ));
        
        int tradecount = this.getDao("rbmdao").update("rem6011_d03", tradeparam);
        
		return dmlcount;
        
    }
    
}
