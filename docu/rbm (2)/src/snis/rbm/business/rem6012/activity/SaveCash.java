/*================================================================================
 * 시스템			: 미사리 현금 입장인원 관리
 * 소스파일 이름	: snis.rbm.business.rem6012.activity.SaveCash.java
 * 파일설명		: 미사리 현금 입장인원 저장
 * 작성자			: 서주원
 * 버전			: 1.0.0
 * 생성일자		: 2017-09-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem6012.activity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveCash extends SnisActivity {
	
	public SaveCash(){}

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
    	
    	PosDataset ds;
    	
        String sDsName  = "dsCash";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	
	        
	        for ( int i = 0; i < nSize; i++ ) {
	           PosRecord record = ds.getRecord(i);
	           
	           nDeleteCount = nDeleteCount + deleteList(record);
	           
        	   if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
  	              record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
        		  saveList(record);
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
     * <p> 미사리 현금 입장인원 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected void saveList(PosRecord record)  
    {
    	Connection conn = null; 
    	CallableStatement proc =  null;
    	
    	int iCnt = 0;
    	String sSdate = "";
    	
        try {
        	System.out.println("#################CNT:" + record.getAttribute("CNT"));
        	iCnt = (int)Double.parseDouble(record.getAttribute("CNT").toString());
        	sSdate = (String) record.getAttribute("SDATE");
        	int i = 1;
        	conn = this.getDao("rbmdao").getDBConnection();
        	proc = conn.prepareCall("{call SP_IMPORT_TBRC_T_TRADE_MSR(?,?)}");
        	proc.setQueryTimeout(120);
        	proc.setInt(i++, iCnt);
			proc.setString(i++, sSdate);
        	proc.execute();
        	//proc.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    
    /**
     * <p> 미사리 현금 입장인원  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteList(PosRecord record) 
    {
    	//TBRC_T_TRADE_MSR
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("SDATE" ));
        
        int dmlcount = this.getDao("rbmdao").update("rem6012_d01", param);
        
		return dmlcount;
        
    }
    
}
