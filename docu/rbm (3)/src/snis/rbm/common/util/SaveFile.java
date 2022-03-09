/*
 * ================================================================================
 * 시스템 : 첨부파일 관리 
 * 소스파일 이름 : snis.rbm.common.util.SaveFile 
 * 파일설명 : 관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011- 
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.common.util;

import java.io.File;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveFile {
	public SaveFile(){}
    
    /**
     * <p> 첨부파일 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	public static int insertFileMana(PosRecord record, PosGenericDao dao) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//첨부파일키
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//첨부파일키
        param.setValueParamter(i++, record.getAttribute("TBL_NM"));			//테이블명
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));		//파일명        
        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));		//파일경로
        
        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));	//실파일명
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));			//정렬순서
        param.setValueParamter(i++, record.getAttribute("INST_ID"));  
        
        int dmlcount = dao.update("common_file_i01", param);

        return dmlcount;
    }
    
    
    
    /**
     * <p> 첨부파일 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
	public static int updateFileMana(PosRecord record, PosGenericDao dao)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("TBL_NM"));					//테이블명
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));				//파일명
        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));				//파일경로
        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));			//실파일명
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));					//정렬순서
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY" ));	//첨부파일키
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));			//순번
        
        int dmlcount = dao.update("common_file_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 첨부파일  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	public static int deleteFileMana(PosRecord record, PosGenericDao dao) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//첨부파일키
        param.setValueParamter(i++, record.getAttribute("SEQ"         ));	//순번
        
        int dmlcount = dao.update("common_file_d01", param);
        
        
        String delYn =  (String) record.getAttribute("FILE_DEL_YN");
        if(delYn == null) delYn = "";

        if(dmlcount >0 && !delYn.equals("N")){

	    	try {				

	    		deleteFile(record);				
			} catch (Exception e) {				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        return dmlcount;
    }

	/**
     * 첨부파일을 삭제하는 메소드
     * @param   request		HttpServletRequest
     * @param   response		HttpServletResponse
     * @return	none
     * @throws Exception
     */
    public static void deleteFile(PosRecord record) throws Exception {        

    	String physicalFileName = (String) record.getAttribute("FILE_PATH");

        String rtn_val = "FILE_DELETE^SUCC"; 
        
        File file = null;
        
        if(physicalFileName == null || physicalFileName == ""){
            return;
        }
    
        try{
            file = new File(physicalFileName);
            if (file.exists())
            	file.delete();
        }catch(Exception e){
        	rtn_val = "FILE_DELETE^FAIL&Exception Message="+e.getMessage();
        }   
    }

    /**
     * <p> 첨부파일  KEY 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	public static String getFileManaMaxKey(PosGenericDao dao) 
    {
        String rtnKey = "";
        PosRowSet keyRecord = dao.find("common_file_s02");        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("ATT_FILE_KEY"));	//첨부파일키
   
        return rtnKey;
    }
	
	/**
     * <p> 첨부파일 개수 조회  </p>
     * @param   PosGenericDao dao			DB Connect 정보
     *          String        sAttFileKey	FileKey
     * @return  nRtnKey	int			        sAttFileKey데 대한 첨부파일 개수
     * @throws  none
     */
	public static int getFileCount(String sAttFileKey, PosGenericDao dao)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sAttFileKey);	//첨부파일키
        
        PosRowSet keyRecord = dao.find("common_file_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String sRtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        int nRtnKey    = Integer.parseInt(sRtnKey);
        
        return nRtnKey;
    }
	
	/**
     * <p> 첨부파일 레코드 가져오기 </p>
     * @param   record	  PosRecord	 데이타셋에 대한 하나의 레코드
     * @return  rtnRocord PosRowSet  Return Record
     * @throws  none
     */
	public static PosRowSet selectFileRecord(PosRecord record, PosGenericDao dao)
    {
    	PosParameter param = new PosParameter();
        int i = 0;  
        
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY"));
        
        PosRowSet rtnRocord = dao.find("common_file_s01", param);

        return rtnRocord;
    }
	
	/**
     * <p> 첨부파일 레코드 삭제하기 </p>
     * @param   record	  PosRecord	 데이타셋에 대한 하나의 레코드
     * @return  rtnRocord PosRowSet  Return Record
     * @throws  none
     */
	public static int deleteFile(PosRecord record, PosGenericDao dao)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        int nTempDelete = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY"));
        
        Double dAttFileKey = (Double)record.getAttribute("ATT_FILE_KEY");

    	if( dAttFileKey == null ) {
    		return nTempDelete;
    	}
    	
        PosRowSet pFile = dao.find("common_file_s01", param);
       
		for(int nRow = 0; nRow <  pFile.count(); nRow++) {
			PosRow pr[] = pFile.getAllRow();
    		
    		String sFileKey  = String.valueOf(pr[nRow].getAttribute("ATT_FILE_KEY"));
    		String sSeq      = String.valueOf(pr[nRow].getAttribute("SEQ"));
    		String sFilePath = String.valueOf(pr[nRow].getAttribute("FILE_PATH"));
    		
    		record.setAttribute("ATT_FILE_KEY", sFileKey);
    		record.setAttribute("SEQ"         , sSeq);
    		record.setAttribute("FILE_PATH"   , sFilePath);
    		
    		nTempDelete += SaveFile.deleteFileMana(record, dao);
        }
		
		return nTempDelete;
    }
}
