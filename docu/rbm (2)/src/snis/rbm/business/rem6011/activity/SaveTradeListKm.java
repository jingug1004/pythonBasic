/*================================================================================
 * 시스템			: 광명 현금 입장인원 관리
 * 소스파일 이름	: snis.rbm.business.rem6011.activity.SaveTradeList.java
 * 파일설명		: 광명 현금 입장인원 저장
 * 작성자			: 서주원
 * 버전			: 1.0.0
 * 생성일자		: 2017-07-23
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem6011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveTradeListKm extends SnisActivity {
	
	public SaveTradeListKm(){}

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
    	
        String sDsName  = "dsTradeKm";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 	 ) {	
	            	
	            	nDeleteCount = nDeleteCount + deleteList(record);
	            	nSaveCount += saveList(record);
		        }
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteList(record);
		        	nSaveCount += saveRaceList(record);
	            }
	            	
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 광명 현금 입장인원 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveList(PosRecord record) 
    {
        //TBRC_T_TRADE_KM_SUM
    	PosParameter param = new PosParameter();  
    	PosParameter perparam = new PosParameter();
    	PosParameter raceparam = new PosParameter();
    	
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("TRADE_DATE"));		// 입장일자
        param.setValueParamter(i++, record.getAttribute("CNT"));			// 방문자수
        param.setValueParamter(i++, record.getAttribute("USER_ID"));		// 사용자ID(수정자)
        
        int dmlcount = this.getDao("rbmdao").update("rem6011_i04", param);
        
        //TBRC_T_TRADE_KM
        int j = 0;
        perparam.setValueParamter(j++, record.getAttribute("USER_ID"));		// 사용자ID(수정자)
        perparam.setValueParamter(j++, record.getAttribute("TRADE_DATE"));	// 입장일자
        
        int dPercount = this.getDao("rbmdao").update("rem6011_i05", perparam);
        
        //TBRC_T_TRADE_RACE_SUM
        int z = 0;
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// 입장일자
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// 입장일자
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// 입장일자
        
        int dracecount = this.getDao("rbmdao").update("rem6011_i08", raceparam);
        
        return dmlcount;
    }
        
    /**
     * <p> TBRC_T_TRADE_RACE_SUM 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveRaceList(PosRecord record) 
    {
    	PosParameter raceparam = new PosParameter();

        //TBRC_T_TRADE_RACE_SUM
        int z = 0;
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// 입장일자
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// 입장일자
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// 입장일자
        
        int dracecount = this.getDao("rbmdao").update("rem6011_i08", raceparam);
        
        return dracecount;
    }
    
    /**
     * <p> 광명 현금 입장인원  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteList(PosRecord record) 
    {
    	//TBRC_T_TRADE_KM_SUM
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("TRADE_DATE" ));
        int dmlcount = this.getDao("rbmdao").update("rem6011_d04", param);
        
        //TBRC_T_TRADE_KM
        PosParameter perparam = new PosParameter();
        int j = 0;
        
        perparam.setWhereClauseParameter(j++, record.getAttribute("TRADE_DATE" ));
        int dPercount = this.getDao("rbmdao").update("rem6011_d05", perparam);
        
        //TBRC_T_TRADE_RACE_SUM
        PosParameter raceparam = new PosParameter();
        int z = 0;
        
        raceparam.setWhereClauseParameter(z++, record.getAttribute("TRADE_DATE" ));
        int dracecount = this.getDao("rbmdao").update("rem6011_d08", perparam);
        
		return dmlcount;
    }
    
}
