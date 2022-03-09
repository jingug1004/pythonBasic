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
package snis.boa.system.e01010010.activity;

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
public class SaveCode extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveCode()
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
        sDsName = "dsGrpCodeListValue";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsSpecCodeListValue";
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

        sDsName = "dsGrpCodeListValue";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateGrpCode(record)) == 0 ) {
		        		nTempCnt = insertGrpCode(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteGrpCode(record);	            	
	            }		        
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteGrpCode(record);	            	
	            }		        
	        }	        
        }

        sDsName = "dsSpecCodeListValue";
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
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 그룹코드 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertGrpCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("GRP_NM"));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        int dmlcount = this.getDao("boadao").update("tbea_grp_cd_ia001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 그룹코드 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateGrpCode(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_NM"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_grp_cd_ua001", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 그룹코드  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteGrpCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, "Y" );
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        
        int dmlcount = this.getDao("boadao").update("tbea_grp_cd_da001", param);
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
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, record.getAttribute("SORT_ODR"));
        param.setValueParamter(i++, record.getAttribute("RMK2"));
        
        int dmlcount = this.getDao("boadao").update("tbea_spec_cd_ia001", param);
        
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
    	String delYn = "";
    	if(record.getAttribute("DEL_YN").equals("사용")) delYn = "N";
    	else if(record.getAttribute("DEL_YN").equals("미사용")) delYn = "Y";
    	
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("CD_NM"      ));
        param.setValueParamter(i++, delYn);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, record.getAttribute("SORT_ODR"));
        param.setValueParamter(i++, record.getAttribute("RMK2"));
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_spec_cd_ua001", param);
        
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

        int dmlcount = this.getDao("boadao").update("tbea_spec_cd_da001", param);
        
        return dmlcount;
    }
           
}
