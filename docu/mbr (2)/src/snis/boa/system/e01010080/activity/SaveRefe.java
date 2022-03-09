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
package snis.boa.system.e01010080.activity;

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
public class SaveRefe extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveRefe()
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
        sDsName = "dsOutRefe";
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

        sDsName = "dsOutRefe";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateRefe(record)) == 0 ) {
		        		nTempCnt = insertRefe(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteRefe(record);	            	
	            }		        
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteRefe(record);	            	
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
    protected int insertRefe(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MNG"                ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_NAME"      ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_URL"       ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_SIZE"      ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD"          ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_SIZE"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN"          ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_SIZE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("tbed_refe_id001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 그룹코드 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRefe(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MNG"                ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_NAME"      ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_URL"       ));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_SIZE"      ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD"          ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_SIZE"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN"          ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_NAME"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_URL" ));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_SIZE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("REFERERE_SEQ" ));

        int dmlcount = this.getDao("boadao").update("tbed_refe_ud001", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 그룹코드  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRefe(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("REFERERE_SEQ" ));
        
        int dmlcount = this.getDao("boadao").update("tbed_refe_dd001", param);
        return dmlcount;
    }           
}
