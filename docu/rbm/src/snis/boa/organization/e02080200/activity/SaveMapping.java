/*================================================================================
 * 시스템			: 코드 관리
 * 소스파일 이름	: snis.boa.system.e01010030.activity.UserManager.java
 * 파일설명		: 코드 관리
 * 작성자			: 김영철
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02080200.activity;

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
public class SaveMapping extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveMapping()
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
        sDsName = "dsOutPrizeRace";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsOutEventRace";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
        
        sDsName = "dsOutMapRaceInfo";
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
   
        // 대상경주그룹 저장
        sDsName = "dsOutPrizeRace";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateSpecCode(record)) == 0 ) {
		        		nTempCnt = insertSpecCode(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
	        }
	         
	         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteSpecCode(record);	            	
		            }		        
		        }	         
        }
        
        // 이벤트 경주그룹 저장
        sDsName = "dsOutEventRace";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateSpecCode(record)) == 0 ) {
		        		nTempCnt = insertSpecCode(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
	        }
	         
	         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteSpecCode(record);	            	
		            }		        
		        }	         
        }
        
        // 매핑정보 저장
        sDsName = "dsOutMapRaceInfo";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateMapping(record)) == 0 ) {
		        		nTempCnt = insertMapping(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
	        }
	         
	         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteMapping(record);	            	
		            }		        
		        }	         
        }        
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 매핑정보 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertMapping(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("PRIZ_RACE_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
       
        
        int dmlcount = this.getDao("boadao").update("tbeb_priz_race_map_ib001", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 매핑정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateMapping(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("PRIZ_RACE_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR" ));
        param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS" ));
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO" ));

        int dmlcount = this.getDao("boadao").update("tbeb_priz_race_map_ub001", param);
        return dmlcount;
    }

    /**
     * <p> 매핑정보  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteMapping(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR" ));
        param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS" ));
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO" ));
        
        int dmlcount = this.getDao("boadao").update("tbeb_priz_race_map_db001", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 상세코드 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertSpecCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("CD"));
        param.setValueParamter(i++, record.getAttribute("CD_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbeb_priz_race_map_ib002", param);        
        return dmlcount;
    }

    /**
     * <p> 상세코드 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateSpecCode(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("CD_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("boadao").update("tbeb_priz_race_map_ub002", param);
        return dmlcount;
    }    
    
    
    /**
     * <p> 상세코드 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSpecCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("boadao").update("tbeb_priz_race_map_db002", param);
        
        // 해당 그룹코드에 있는 상세 매핑정보도 삭제되어야 함
        PosParameter param_mapdetail = new PosParameter();
        i = 0;
        param_mapdetail.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param_mapdetail.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int mdcount = this.getDao("boadao").update("tbeb_priz_race_map_db003", param_mapdetail);
        
        return dmlcount;
    }
           
}
