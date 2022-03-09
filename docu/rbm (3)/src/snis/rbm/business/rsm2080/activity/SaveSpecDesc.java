/*
 * ================================================================================
 * 시스템 : 분실신고서 접수
 * 파일 이름 : snis.rbm.business.rsm6040.activity.SaveLossReturnsReceipt.java 
 * 파일설명 :  
 * 작성자 : 
 * 버전 : 1.0.0 생성일자 : 2011- 10 - 26
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm2080.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveSpecDesc extends SnisActivity {
	
	public SaveSpecDesc(){}

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
        int nTempCnt     = 0;
        int nSize		 = 0;
        int nSize2		 = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
        
        sDsName = "dsWeather";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
		  		    nTempCnt  = deleteWeather(record);	// 기상정보 삭제
		        } else {
	            	                	
		        	nTempCnt  = updateWeather(record);	// 기상정보 수정
		        }
	  		    nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }

        sDsName = "dsSpecDesc";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	                	
	  		    nTempCnt  = updateSpecDesc(record);	// 상황일지, 특이사항, 주차수량  수정
	  		    
	  		    nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);     
       
    }

    /**
     * <p> 기상정보 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateWeather(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
        param.setValueParamter(i++, record.getAttribute("RACE_DAY")	);		// 경주일   
        param.setValueParamter(i++, record.getAttribute("WEATHER")	);		// 날씨        
        param.setValueParamter(i++, record.getAttribute("TEMP")	    );		// 온도
        param.setValueParamter(i++, record.getAttribute("WIN_DIRT")	);		// 풍향
        param.setValueParamter(i++, record.getAttribute("WIN_SPEED"));		// 풍속        
        param.setValueParamter(i++, record.getAttribute("WATR_TEMP"));		// 수온
        param.setValueParamter(i++, record.getAttribute("INPT_TIME"));		// 입력시간 
        param.setValueParamter(i++, SESSION_USER_ID					);		// 사용자ID(작성자)
		
        int dmlcount = this.getDao("rbmdao").update("rsm2080_u01", param);
        
        return dmlcount;
    }    

    /**
     * <p> 기상정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteWeather(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DAY")	);		// 경주일   
		
        int dmlcount = this.getDao("rbmdao").update("rsm2080_d01", param);
        
        return dmlcount;
    }    
    

    /**
     * <p> 특이사항, 상황일지 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateSpecDesc(PosRecord record) 
    {
    	String sParkingCnt = "";
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
        param.setValueParamter(i++, record.getAttribute("MEET_CD")	  );		// 경륜장코드        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")  );		// 기준년도
        param.setValueParamter(i++, record.getAttribute("TMS")	      );		// 회차
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")    );		// 일차        
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC")  );		// 특이사항
        param.setValueParamter(i++, record.getAttribute("RACE_CNTNT") );		// 경주상황 
        
        sParkingCnt = record.getAttribute("PARKING_CNT").toString();
        if (sParkingCnt != null) {
        	sParkingCnt = sParkingCnt.replaceAll(",", "");
        }
        param.setValueParamter(i++, sParkingCnt);								// 본장 주차대수 
        param.setValueParamter(i++, SESSION_USER_ID					  );		// 사용자ID(작성자)
		
        int dmlcount = this.getDao("rbmdao").update("rsm2080_u02", param);
        
        return dmlcount;
    }   
    
    
}
