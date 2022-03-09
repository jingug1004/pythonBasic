/*================================================================================
 * 시스템			: 게시판 관리
 * 소스파일 이름	: snis.boa.system.e01010060.activity.BoardManager.java
 * 파일설명		: 게시판 관리
 * 작성자			: 김영철
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.system.e01010061.activity;

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
public class SaveProcessProg extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveProcessProg()
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsInsertBoard";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        	logger.logInfo("========== NSIZE " + nSize + "=======================");
	        	
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			         if( (String) ctx.get("ISTYPE") != null && ((String) ctx.get("ISTYPE")).equals("D") ) {
			        	 nDeleteCount = deleteBoard(ctx);
			         } else {
			        	 nTempCnt = updateProcessProg(record);
			         }
		        }
		        
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if( ((String) record.getAttribute("ISTYPE")).equals("I") ) nTempCnt = insertProcessProg(record);
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 게시글 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateProcessProg(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SBJT"      ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"      ));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));
        param.setValueParamter(i++, record.getAttribute("TMS"      ));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"      ));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));

        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ua001", param);
        
        return dmlcount;
    }

    /**
     * <p> 게시글 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertProcessProg(PosRecord record) 
    {
        PosParameter param = new PosParameter();

        String clsCd		= (String) record.getAttribute("CLS_CD");
       
        logger.logInfo("========== clscd " + clsCd + "=======================");
        
        int i = 0;
        param.setValueParamter(i++, clsCd);
        param.setValueParamter(i++, record.getAttribute("SBJT"    		  ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"       ));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"       ));
        param.setValueParamter(i++, record.getAttribute("TMS"       	  ));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"         ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ia001", param);
        
        return dmlcount;
    }
    
    
    /*
     * 프로세스 진행현황 insert Method
     */
    public int insertProcessProg(String clsCd, String stndYear, String tms, String day_ord, String sbjt, String rmk) 
    {
    	/*
    	 * 001 : 시스템관리
    	 * 002 : 편성관리
    	 * 003 : 선수관리
    	 * 004 : 심판관리
    	 * 005 : 공정관리
    	 * 006 : 장비관리
    	 * 007 : 상금관리
    	 */
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, clsCd);
        param.setValueParamter(i++, sbjt);
        param.setValueParamter(i++, rmk);
        param.setValueParamter(i++, stndYear);
        param.setValueParamter(i++, tms);
        param.setValueParamter(i++, day_ord);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ia001", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p> 게시글 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteBoard(PosContext ctx)
    {
    	logger.logInfo("========== DELETEBOARD =======================");
    	logger.logInfo("========== DELETEBOARD =======================");
    	logger.logInfo("========== DELETEBOARD =======================");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, "Y");
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0; 
        param.setWhereClauseParameter(i++,ctx.get("SEQ_NO"));

        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ua002", param);
        return dmlcount;
    }
    
 }
