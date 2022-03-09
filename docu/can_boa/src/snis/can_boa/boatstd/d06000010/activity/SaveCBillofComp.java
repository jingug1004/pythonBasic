/*================================================================================
 * 시스템			: 재원증명서
 * 소스파일 이름	: snis.can.system.d02000012.activity.SaveItemCode.java
 * 파일설명		: 상벌조항/항목 코드 관리
 * 작성자			: 왕영신
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유재은
* @version 1.0
*/
public class SaveCBillofComp extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveCBillofComp()
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
        sDsName = "dsBlngCertList";
        logger.logInfo("codemanager start ==============");
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
        else
        {
        	String RACER_PERIO_NO = ctx.get("RACER_PERIO_NO").toString();
        	String ISSUE_NO = ctx.get("ISSUE_NO").toString();
        	
        	updateBlng_Cert_Print(RACER_PERIO_NO, ISSUE_NO);
        }  
        saveState(ctx);
        logger.logInfo("codemanager end =================");
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

        sDsName = "dsBlngCertList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         logger.logInfo("nSize ============= " + nSize);
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(" record.getType() ==============" + record.getType());
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	nTempCnt = updateBlng_Cert(record);
		        }
		        
		        else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = insertBlng_Cert(record);
		        }
		        
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteBlng_Cert(record);	            	
	            }		        

		        nSaveCount = nSaveCount + nTempCnt;
	        } // end for
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 재원증명서 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertBlng_Cert(PosRecord record) 
    {
    	logger.logInfo("insertBlng_Cert start ==============");
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
        //param.setValueParamter(i++, record.getAttribute("ISSUE_NO"));
        param.setValueParamter(i++, record.getAttribute("ISSUE_DT"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("USE_PRPS"));
        param.setValueParamter(i++, record.getAttribute("ETC"));
        param.setValueParamter(i++, SESSION_USER_ID);       
        
        int dmlcount = this.getDao("candao").update("tbdn_blng_cert_ib001", param);
        
        logger.logInfo("insertBlng_Cert end ==============");
        return dmlcount;
    }
    
    
    
    /**
     * <p> 재원증명서 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateBlng_Cert(PosRecord record)
    {
    	logger.logInfo("updateBlng_Cert start ==============");
    	PosParameter param = new PosParameter();
        int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("ISSUE_DT"));
		param.setValueParamter(i++, record.getAttribute("CAND_NO"));
		param.setValueParamter(i++, record.getAttribute("USE_PRPS"));
		param.setValueParamter(i++, record.getAttribute("ETC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("ISSUE_NO"));

        int dmlcount = this.getDao("candao").update("tbdn_blng_cert_ub001", param);
        
        logger.logInfo("updateBlng_Cert end ==============");
        return dmlcount;
    }

    /**
     * <p> 재원증명서 인쇄 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateBlng_Cert_Print(String RACER_PERIO_NO, String ISSUE_NO)
    {
    	logger.logInfo("updateBlng_Cert_Print start ==============");
    	PosParameter param = new PosParameter();
        int i = 0;
	 
        param.setWhereClauseParameter(i++, RACER_PERIO_NO);
        param.setWhereClauseParameter(i++, ISSUE_NO);

        int dmlcount = this.getDao("candao").update("tbdn_blng_cert_ub002", param);
        
        logger.logInfo("updateBlng_Cert_Print end ==============");
        return dmlcount;
    }
    
    /**
     * <p> 그룹코드  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteBlng_Cert(PosRecord record) 
    {
    	logger.logInfo("deleteBlng_Cert start ==============");
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("ISSUE_NO"));
        
        int dmlcount = this.getDao("candao").update("tbdn_blng_cert_db001", param);
        
        logger.logInfo("deleteBlng_Cert end ==============");
        return dmlcount;
    }          
}
