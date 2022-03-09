/*================================================================================
 * 시스템			: 게시판 관리
 * 소스파일 이름	: snis.cra.system.jsy0005.activity.SaveBoard.java
 * 파일설명		: 게시판 관리
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2009-02-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.system.rsy5020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 이영상
* @version 1.0
*/
public class SaveBoard extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveBoard()
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
/*
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsInsertBoard";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	        }
        }
*/
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

        
        String sJobGbn   = (String) ctx.get("JOB_GBN");
        String sSeqNo    = "";
        
        if(sJobGbn == null) sJobGbn = "01";
        
       
        
        if(sJobGbn.equals("02")){
        	
        	sSeqNo = (String) ctx.get("SEQ_NO");
            sDsName = "dsInsertBoard";
            
            updateSrcCnt(sSeqNo);

        }else{
        
        
	        sDsName = "dsInsertBoard";
	        boolean isUpdate = false;
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
	           
		         if( (String) ctx.get("ISTYPE") != null && ((String) ctx.get("ISTYPE")).equals("D") ) {
		        	 nDeleteCount = deleteBoard(ctx);
		         }
	
		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	nTempCnt = updateBoard(record);
			        	if(nTempCnt > 0) isUpdate = true;
			        	else	isUpdate = false;
			        }
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if( ((String) record.getAttribute("ISTYPE")).equals("I") ) nTempCnt = insertBoard(record);
			        	if( ((String) record.getAttribute("ISTYPE")).equals("R") ) nTempCnt = replyBoard(record, ctx);
			        }
	
			        nSaveCount = nSaveCount + nTempCnt;
		        } // end for
	        }
	        
	        
	        sDsName = "dsUploadFile";
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
	           
		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
			        	nTempCnt = deleteFile(record,ctx);
			        }
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if(isUpdate == true) nTempCnt = insertFileWithSeq(record, ctx);
			        	else nTempCnt = insertFile(record, ctx);
			        }
	
			        nSaveCount = nSaveCount + nTempCnt;
		        } // end for
	        }        
	

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
    protected int updateBoard(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TITLE"      ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"      ));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));

        int dmlcount = this.getDao("rbmdao").update("rsy5010_u02", param);
        
        return dmlcount;
    }

    /**
     * <p> 게시글 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertBoard(PosRecord record) 
    {
        PosParameter param = new PosParameter();

        String clsCd		= (String) record.getAttribute("CLS_CD");
       
        int i = 0;
        param.setValueParamter(i++, clsCd);
        param.setValueParamter(i++, record.getAttribute("MAKE_MAN"      	  ));
        param.setValueParamter(i++, record.getAttribute("TITLE"    		  ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"       ));
        param.setValueParamter(i++, "0");
        param.setValueParamter(i++, "001");
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        int dmlcount = this.getDao("rbmdao").insert("rsy5010_i02", param);
        
        return dmlcount;
    }


    /**
     * <p> 게시글 답변 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int replyBoard(PosRecord record, PosContext ctx) 
    {
    	String grpNo 		= "";
    	String grpRankNo  	= "";
    	String grpLowNo		= ""; 
    	
    	grpNo 		= (String)ctx.get("GRP_NO");
    	grpRankNo   = (String)ctx.get("GRP_RANK_NO");
    	grpLowNo 	= (String)ctx.get("GRP_LOW_NO");
    	
    	System.out.println("grpNo >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + grpNo);
    	System.out.println("grpRankNo >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + grpRankNo);
    	System.out.println("grpLowNo >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + grpLowNo);
    	
        PosParameter param = new PosParameter();

    	int grpRankNoi 	= Integer.parseInt(grpRankNo);
    	int grpLowNoi 	= Integer.parseInt(grpLowNo);
    	
    	grpRankNoi 		= grpRankNoi 	+ 1;
    	grpLowNoi  		= grpLowNoi 	+ 1;
    	
        // 자신보다 낮은 grp_low_no update
    	updateGrpLowNo(grpNo, grpLowNoi);
    	
        int i = 0;
        param.setValueParamter(i++, (String) record.getAttribute("CLS_CD"));
        param.setValueParamter(i++, grpNo);
        param.setValueParamter(i++, Integer.toString(grpRankNoi) );
        param.setValueParamter(i++, Integer.toString(grpLowNoi)  );
        param.setValueParamter(i++, record.getAttribute("MAKE_MAN"      ));
        param.setValueParamter(i++, "RE:"+record.getAttribute("TITLE"      ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"      ));
        param.setValueParamter(i++, "0");
        param.setValueParamter(i++, "002");
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        int dmlcount = this.getDao("rbmdao").insert("rsy5010_i01", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p> 게시글 답변 선처리작업 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected void updateGrpLowNo(String grpNo, int grpLowNo) 
    {
    	
    	System.out.println("grpNo >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + grpNo);
    	System.out.println("grpLowNo >>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + grpLowNo);
    	
        PosParameter param = new PosParameter();

        int i = 0;
        param.setWhereClauseParameter(i++, grpNo);
        param.setWhereClauseParameter(i++, Integer.toString(grpLowNo));

        this.getDao("rbmdao").update("rsy5010_u01", param);
    }
    
    
    
    /**
     * <p> 게시글 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteBoard(PosContext ctx)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, "Y");
        
        i = 0; 
        param.setWhereClauseParameter(i++,ctx.get("SEQ_NO"));

        int dmlcount = this.getDao("rbmdao").delete("rsy5010_d01", param);
        return dmlcount;
    }

    
    
    
    /**
     * <p> File 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertFile(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        
        PosDataset headDs = (PosDataset) ctx.get("dsInsertBoard");
        PosRecord headRecord = headDs.getRecord(0);
         
        int i = 0;
        param.setValueParamter(i++, headRecord.getAttribute("CLS_CD") );
        param.setValueParamter(i++, record.getAttribute("FILE_NAME") );
        param.setValueParamter(i++, record.getAttribute("FILE_URL") );
        param.setValueParamter(i++, record.getAttribute("FILE_SIZE") );
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("rbmdao").insert("rsy5010_i04", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p> File 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertFileWithSeq(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        PosDataset headDs = (PosDataset) ctx.get("dsInsertBoard");
        PosRecord headRecord = headDs.getRecord(0); 
        int i = 0;
        param.setValueParamter(i++, headRecord.getAttribute("CLS_CD") );
        param.setValueParamter(i++, ctx.get("SEQ_NO"));
        param.setValueParamter(i++, record.getAttribute("FILE_NAME") );
        param.setValueParamter(i++, record.getAttribute("FILE_URL") );
        param.setValueParamter(i++, record.getAttribute("FILE_SIZE") );
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("rbmdao").insert("rsy5010_i05", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> File 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteFile(PosRecord record,  PosContext ctx) 
    {
        PosParameter param = new PosParameter();
         
        int i = 0;
        param.setWhereClauseParameter(i++, (String) record.getAttribute("CLS_CD") );
        param.setWhereClauseParameter(i++, (String) ctx.get("SEQ_NO")   );
        param.setWhereClauseParameter(i++, record.getAttribute("FILE_SEQ") );
        
        int dmlcount = this.getDao("rbmdao").delete("rsy5010_d02", param);
        
        return dmlcount;
    }

    
    /**
     * <p> 조회수 증가  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateSrcCnt(String sSeqNo) 
    {

        PosParameter param = new PosParameter();
        int i = 0;

        i = 0;
        param.setWhereClauseParameter(i++, sSeqNo);

        int dmlcount = this.getDao("rbmdao").update("rsy5010_u03", param);

        return dmlcount;
    }
 }
