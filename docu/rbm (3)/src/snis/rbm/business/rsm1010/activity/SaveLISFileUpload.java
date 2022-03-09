/*================================================================================
 * 시스템			: LIS FILE 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1010.activity.SaveLISFileUpload.java
 * 파일설명		: LIS FILE 저장
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1010.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import snis.rbm.common.util.LISFileReader;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveLISFileUpload extends SnisActivity {
	public SaveLISFileUpload(){}
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
	String MEETCD = "";
	String YEAR = "";
    public String runActivity(PosContext ctx)
    {	   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	        
        saveState(ctx);
    	
        /* 
         *  일괄로 파일 올릴때 사용 meetcd, 파일 경로만 변경 
		String dir = "E:\\LIS\\2011\\CRA\\lis";
		MEETCD = "001";
		YEAR   = "2011";
    	readFolder(dir,"");
    	*/
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
    	String sDsName   = "";
    	String sFileCd = "002";
    	String sResult = "F";
    	PosDataset ds;
    	PosRow[] pr = null;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsLISFile";
        HashMap hMap = null;
        
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            String FILE_LOCAL_PATH = (String)record.getAttribute("FILE_LOCAL_PATH");
	            String FILE_PATH = (String)record.getAttribute("FILE_PATH");
	            String FILE_NM = (String)record.getAttribute("FILE_NM");
	            
	            LISFileReader lisfReader = new LISFileReader();
	            LISFile lisf = new LISFile(ctx,record, this.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);
	            
	            // 입력하려는  년도, 일자에 해당하는 DAS 데이터 검색
	            //pr = lisf.searchDasLIS(this.getDao("rbmdao"),record);	// commented by TOMMY(2013.8.30)
	    		
	            // 입력하려는  년도, 일자에 해당하는 LIS FILE 데이터 먼저 삭제
	            lisf.deleteLISFile(this.getDao("rbmdao"),record);
	            
	            ArrayList aList = null;
	            int dmlLISFile =0;
	            if(!"".equals(FILE_LOCAL_PATH)&&FILE_LOCAL_PATH!=null )
	            {    
		            aList = lisfReader.readFile(FILE_PATH);
//		            dmlLISFile = lisf.saveLISFile(aList,pr); // LIS 테이블에 데이터 저장
		            dmlLISFile = lisf.saveLISFile(aList); // LIS 테이블에 데이터 저장
	            }
	            
	            nSaveCount = nSaveCount + dmlLISFile;	            
	            aList = null;
	            
	            sResult = "T";
	            
	            sFileCd = FILE_NM!=null&&!"".equals(FILE_NM)?"001":"002";
		    }	        
        }

        
        Util.setReturnParam(ctx, "FILE_CD", sFileCd); // 업로드 파일 결과
        Util.setReturnParam(ctx, "RESULT", sResult); // 결과
    }

    
    protected ArrayList readFolder(String dir,String date) {  
		File   file          =  null; 
		HashMap param = new HashMap(); 
		ArrayList folderlist = new ArrayList();
		
		try { 
			HashMap hMap = null;
			String[] ar= null;
			File[] files = new File(dir).listFiles(); 
			for(int i=0;i<files.length;i++){   
				file = files[i]; 
				if(file.isDirectory()){
					ar = file.getName().split("_");
					System.out.println("dir:"+file.getName()+" ar1:"+ar[1]);
					readFolder(file.getAbsolutePath(),ar[1]);
				}else{
					if(file.getName().equals("divstatrpt.lis")){
						hMap = new HashMap();
				        hMap.put("FILE_LOCAL_PATH",file.getAbsolutePath());
				        hMap.put("FILE_PATH",file.getAbsolutePath());
				        hMap.put("FILE_NM",file.getName());
				        hMap.put("MEET_CD",MEETCD);
				        hMap.put("STND_YEAR",YEAR);
				        hMap.put("DT",date);
				        saveAllState(hMap);
						System.out.println("file:"+file.getAbsolutePath()+ "  "+hMap);
					}
				}
			} 
        	
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally{ 
		}
		return folderlist;
	}
    
    
	
    protected void saveAllState(HashMap hMap) 
    {
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	String sFileCd = "002";
    	String sResult = "F";
    	PosDataset ds;
    	PosRow[] pr =null;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsLISFile"; 
          
        
        String FILE_LOCAL_PATH = (String)hMap.get("FILE_LOCAL_PATH");
        String FILE_PATH       = (String)hMap.get("FILE_PATH");
        String FILE_NM         = (String)hMap.get("FILE_NM");
        
        LISFileReader lisfReader = new LISFileReader();
        LISFile lisf = new LISFile( this.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);
        
        ArrayList aList = null; 
        aList = (ArrayList)lisfReader.readFile(FILE_PATH);
        lisf.saveAllLISFile(aList,hMap); // LIS 테이블에 데이터 저장        
         
    }
    
}
