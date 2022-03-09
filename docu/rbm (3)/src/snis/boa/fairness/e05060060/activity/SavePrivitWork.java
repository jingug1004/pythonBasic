/*================================================================================
 * 시스템			: 공정 관리 
 * 소스파일 이름	: snis.boa.fairness.e05060060.activity.SavePrivitWork.java
 * 파일설명		: 사설경정활동 등록
 * 작성자			: 한영택 
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-04
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05060060.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 사설경정활동 정보 대한 등록, 수정, 삭제 한다.
* @auther 한영택 
* @version 1.0
*/
public class SavePrivitWork extends SnisActivity
{    
    public SavePrivitWork()
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutPrivitWork");
        if ( ds != null ) 
        {
	        nSize = ds.getRecordCount();
	
	        for ( int i = 0; i < nSize; i++ ){
	            PosRecord record = ds.getRecord(i);
	            switch (record.getType())
	            {
		            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
		            	nDeleteCount = nDeleteCount + deletePrivitWork(record);
		            	break;
		            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
		            	nSaveCount = nSaveCount + insertPrivitWork(record);
		            	break;	
		            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
		            	nSaveCount = nSaveCount + updatePrivitWork(record);
		            	break;	
	            }
	        }
        }
        PosDataset dsUploadFile;
        int nRowCount        = 0;
        
        dsUploadFile	= (PosDataset) ctx.get("dsOutUploadFile");
        if ( dsUploadFile != null ) 
        {
	        nRowCount 		= dsUploadFile.getRecordCount();
	
	        for ( int i = 0; i < nRowCount; i++ ){
	            PosRecord record = dsUploadFile.getRecord(i);
	            switch (record.getType())
	            {
		            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
		            	nDeleteCount = nDeleteCount + deleteUploadFile(record);
		            	break;
		            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
		            	nSaveCount = nSaveCount + insertUploadFile(record);
		            	break;	
	            }
	        }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * <p> 사설경정활동 정보  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePrivitWork(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SBJT"));
        param.setValueParamter(i++, record.getAttribute("ACT_DESC")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        return this.getDao("boadao").update("tbee_investigate_work_ue001", param);
    }
   
    /**
     * <p>사설경정활동 데이터 등록</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertPrivitWork(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("SBJT"));
        param.setValueParamter(i++, record.getAttribute("ACT_DESC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbee_investigate_work_ie001", param);
    }

    /**
     * <p>사설경정활동 데이터 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deletePrivitWork(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        
        return  this.getDao("boadao").update("tbee_investigate_work_de001", param);
    }
    
    /**
     * <p>첨부파일 데이터 등록</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertUploadFile(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_URL"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        
        return this.getDao("boadao").update("tbee_investigate_work_ie002", param);
    }

    /**
     * <p>첨부파일 데이터 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteUploadFile(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("ACT_NM"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        return  this.getDao("boadao").update("tbee_investigate_work_de002", param);
    }
}
