/*================================================================================
 * 시스템			: 지점관리
 * 소스파일 이름	: snis.boa.system.e01010011.activity.SaveBrnch.java
 * 파일설명		: 지점등록/관리
 * 작성자			: 홍성돈
 * 버전			: 1.0.0
 * 생성일자		: 2008-08-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.system.e01010011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김영철
* @version 1.0
*/
public class SaveBrnch extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveBrnch()
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
   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        sDsName = "dsBrnchGrp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsBrnchList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsBrnchGrp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateBrnchGrp(record)) == 0 ) {
		        		nTempCnt = insertBrnchGrp(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteBrnchGrp(record);	            	
	            }		        
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteBrnchGrp(record);	            	
	            }		        
	        }	        
        }

        sDsName = "dsBrnchList";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateBrnchList(record)) == 0 ) {
		        		nTempCnt = insertBrnchList(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
	        }
	         
	         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteBrnchList(record);	            	
		            }		        
		        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 시행체 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertBrnchGrp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ASSC_NO"));
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSC_NM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        int dmlcount = this.getDao("boadao").update("tbea_brnch_ia001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 시행체 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateBrnchGrp(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ASSC_NM"));
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ASSC_NO" ));

        int dmlcount = this.getDao("boadao").update("tbea_brnch_ua001", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 시행체 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteBrnchGrp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ASSC_NO" ));
        
        int dmlcount = this.getDao("boadao").update("tbea_brnch_da001", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 지점 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertBrnchList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNCH_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSC_NO"));
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("BRNCH_NM"));
        param.setValueParamter(i++, record.getAttribute("CRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("MRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        int dmlcount = this.getDao("boadao").update("tbea_brnch_ia002", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 지점 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateBrnchList(PosRecord record)
    {
    	/*
    	String delYn = "";
    	if(record.getAttribute("DEL_YN").equals("사용")) delYn = "N";
    	else if(record.getAttribute("DEL_YN").equals("미사용")) delYn = "Y";
    	*/
        PosParameter param = new PosParameter();
        int i = 0;
        //param.setValueParamter(i++, delYn);
        param.setValueParamter(i++, record.getAttribute("ASSC_NO"));
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("BRNCH_NM"));
        param.setValueParamter(i++, record.getAttribute("CRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("MRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNCH_CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_brnch_ua002", param);
        
        return dmlcount;
    }    
    
    
    /**
     * <p> 지점 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteBrnchList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNCH_CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_brnch_da002", param);
        
        return dmlcount;
    }
           
}
