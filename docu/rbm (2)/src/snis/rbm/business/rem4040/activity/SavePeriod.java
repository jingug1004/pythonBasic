/*
 * ================================================================================
 * 시스템 : 관리 
 * 소스파일 이름 : snis.rbm.business.rem4040.activity.SavePeriod.java 
 * 파일설명 : 수동전송주기 관리 
 * 작성자 : 신재선
 * 버전 : 1.0.0 
 * 생성일자 : 2012.12.29
 * 최종수정일자 :
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */

package snis.rbm.business.rem4040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SavePeriod extends SnisActivity {

	public SavePeriod(){		
		
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
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	String ImportYn = "N";
    	ImportYn = (String) ctx.get("IMPORT_YN");
    	if ("Y".equals(ImportYn)) {
    		ImportPeriod(ctx);
    	} else {   	
    		saveState(ctx);
    	}

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
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsPeriod";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// 수동전송주기  삭제
	            	nDeleteCount = nDeleteCount + deletePeriod(record);
	            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {

		        	nTempCnt = updatePeriod(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }    
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> 수동전송주기  수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePeriod(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRAN_DT"));		//전송일자
        param.setValueParamter(i++, record.getAttribute("TERM"));			//전송주기
        param.setValueParamter(i++, record.getAttribute("START_TM"));		//시작시간
        param.setValueParamter(i++, record.getAttribute("END_TM"));			//종료시간
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자

        int dmlcount = this.getDao("tmoneydao").update("rem4040_m01", param);
        return dmlcount;
    }


    
    /**
     * <p> 수동전송주기  삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int deletePeriod(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRAN_DT" ));		//경주일자

        int dmlcount = this.getDao("tmoneydao").update("rem4040_d01", param);
        return dmlcount;
    }


    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
     * @throws SQLException 
    * @throws  none
    */
    protected void ImportPeriod(PosContext ctx) 
    {
    	Connection conn = null; 
    	CallableStatement proc =  null;

        try {
        	conn = this.getDao("rbmdao").getDBConnection();
        	proc = conn.prepareCall("{call SP_IMPORT_TMONEY_TRADE}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	//proc.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
