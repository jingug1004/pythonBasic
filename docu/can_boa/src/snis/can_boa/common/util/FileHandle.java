/*================================================================================
 * 시스템			: 템플릿 시스템
 * 소스파일 이름	: snis.templateg.common.FileHandle.java
 * 파일설명		: 파일 업로드, 다운로드, 삭제 처리를 하는  클래스
 * 작성자			: 이성호
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 2007-11-02
 * 최종수정자		: 이성호
 * 최종수정내용	: 파일경로 수정
=================================================================================*/

package snis.can_boa.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

/**
* 이 클래스는 파일 업로드, 다운로드, 삭제 처리를 하는  클래스이다.
* @auther 이성호
* @version 1.0
*/
public class FileHandle {
    private static String FILE_SEPERATOR = File.separator;
    private static String COMMON_FILE_PATH
    			= System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? 
    						"C:" + FILE_SEPERATOR + "upload_file" + FILE_SEPERATOR
    						: "/WAS/webApp/can_boa/upload_file/";
    
    private static String MNG_IMG_FILE_PATH
	= System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? 
				"C:" + FILE_SEPERATOR + "upload_file" + FILE_SEPERATOR
				: "/WAS/webApp/can_boa/mngimages/";
    
    private static String RACER_FILE_PATH      
    			= System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? 
    						"C:" + FILE_SEPERATOR + "upload_file" + FILE_SEPERATOR + "image"  + FILE_SEPERATOR
    						: "/WAS/webApp/can_boa/upload_image/";
    
//    private static String[] IMAGE_TYPE
//    			= {".jpg", ".gif", ".bmp"};
    
    //private static boolean IS_FILE_NAME_ENCODING = true;
    //private static String CHARACTER_SET = "euc-kr";
    
    private static int BUFFER_SIZE      = 1024;
    
    private static FileHandle instance  = null;
    
    public static FileHandle getInstance() {
        if(instance == null){
            return new FileHandle();
        }
        return instance;
    }
    PosLog log = PosLogFactory.getLogger(getClass());
    
    /**
     * 첨부파일 업로드
     * @param   request		HttpServletRequest
     * @param   response		HttpServletResponse
     * @return	none
     * @throws Exception
     */
    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {        
    	String sWork = "";
    	if ( request.getParameter("work") != null ) {
    		sWork = (String) request.getParameter("work");
    	}
    	
    	// Directory 생성
    	String sWorkDirectory = getWorkDirectory(sWork);
    	createDir(COMMON_FILE_PATH);
    	createDir(MNG_IMG_FILE_PATH);
    	createDir(RACER_FILE_PATH);
    	createDir(sWorkDirectory);
    	
    	String sFileName = getCookie(request);
        String sPhysicalFileName = getPhysicalFileName(sFileName);

        log.logDebug("sFileName :[" + sFileName + "]");
        log.logDebug("sWorkDirectory :[" + sWorkDirectory + "]");
        log.logDebug("sPhysicalFileName :[" + sPhysicalFileName + "]");
        
        String sReturn = "FILE_UPLOAD^SUCC&";
        InputStream  inStream  = null;
        OutputStream outStream = null;
 
        try{
        	long nPhysicalFileSize = 0;			// 물리적 파일 크기
        	
            inStream = new BufferedInputStream(request.getInputStream());
            outStream = new FileOutputStream(sWorkDirectory + sPhysicalFileName);
            
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int n = inStream.read(buffer);            
                if (n <= 0) {
                    break;
                }
                nPhysicalFileSize = nPhysicalFileSize + n;
                outStream.write(buffer, 0, n);
            }
            outStream.flush();
            
            sReturn = sReturn + "physicalFileName^" + sWorkDirectory + sPhysicalFileName + "&" 
            				  + "physicalFileSize^" + nPhysicalFileSize;

            log.logDebug("request.getCharacterEncoding :[" + request.getCharacterEncoding() + "]");
            log.logDebug("response.getCharacterEncoding :[" + response.getCharacterEncoding() + "]");
            log.logDebug("*************************************************");
            log.logDebug("FILE UPLOAD END");

        } catch (Exception e) {       
        	sReturn = "FILE_UPLOAD^FAIL&Exception Message="+e.getMessage();
        } finally{
            if(inStream  != null) inStream.close();
            if(outStream != null) outStream.close();
        }
        
        Cookie theCookie = new Cookie ("FileParam", sReturn);
        log.logDebug("rtn_val Second :[" + sReturn + "]");        
        response.setContentType("text/html");
        response.addCookie(theCookie);
    }
    
    /**
     * 첨부파일을 다운로드하는 메소드
     * @param   request		HttpServletRequest
     * @param   response		HttpServletResponse
     * @return	none
     * @throws Exception
     */
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        String sReturn = "FILE_DOWNLOAD^SUCC";
    	String sFileName = getCookie(request);

        log.logDebug("FileName : " + sFileName);        
        
        File file = new File(sFileName);
        if ( !file.isFile() ){
        	sReturn = "File Not Found!!!";
            log.logError(sReturn);
            throw new Exception("File Not Found!!!");
        }
        
        BufferedInputStream fin = null;
        ServletOutputStream out = null;
        
        try {
            fin = new BufferedInputStream(new FileInputStream(file));
            out = response.getOutputStream();

            byte buffer[] = new byte[BUFFER_SIZE];
            int num = 0;
            
            while ( (num = fin.read(buffer)) != -1 ) {
                out.write(buffer, 0, num);
            }

            out.flush();
        } catch(Exception e) {
            log.logError(e);
        	sReturn = "FILE_DOWNLOAD^FAIL&Exception Message="+e.getMessage();
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
        }
        
        Cookie theCookie = new Cookie ("FileParam", sReturn);
    	response.setContentType("text/html");
    	response.addCookie(theCookie);
    }
    
    /**
     * 첨부파일을 삭제하는 메소드
     * @param   request		HttpServletRequest
     * @param   response		HttpServletResponse
     * @return	none
     * @throws Exception
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {        
               
    	String physicalFileName = request.getParameter("filename");
    	
        String rtn_val = "FILE_DELETE^SUCC";

        log.logDebug("FileName : " + physicalFileName);        
        
        File file = null;
        
        if(physicalFileName == null || physicalFileName == ""){
            return;
        }
    
        log.logDebug("[delete]:[" + physicalFileName + "]"); 

        Cookie theCookie = null;
        
        try{
            file = new File(physicalFileName);
            if (file.exists())
            	file.delete();
        }catch(Exception e){
        	rtn_val = "FILE_DELETE^FAIL&Exception Message="+e.getMessage();
        }
        
        theCookie = new Cookie ("FileParam", rtn_val);
        response.setContentType("text/html");
        response.addCookie(theCookie);                
    }

    public String getCookie(HttpServletRequest request) {
        String cookie   = null;
        Cookie theCookie = null;
        Cookie cookies[] = request.getCookies();
  
        if (cookies != null) {
            for(int i=0, n=cookies.length; i < n; i++) {
                theCookie = cookies[i];
                if (theCookie.getName().equals("FileParam")) {
                    try {
                        cookie = ascToKsc(theCookie.getValue().toString());
                    } catch (NumberFormatException ignored) {
                        cookie = null;
                    }
                        break;
                }
            }
        }    	
    	return cookie;
    }
    
    /**
     * 서버의 물리적 경로를 얻어오는 메소드
     * @param   cookieValue		String	처리하고자하는 파일명
     * @param   fileUpDownType	String	파일 업로드 또는 다운로드/삭제 구분
     * @return	none
     * @throws Exception
     */
    public String getPhysicalFileName(String sFileName) {
    	String sFilePath = ""; 
    	String sExpName  = sFileName.substring(sFileName.lastIndexOf("."));
    	
    	sFilePath = System.currentTimeMillis() + sExpName;
    	return sFilePath;
    }
    
    /**
     * ksc 인코딩을 asc 인코딩으로 바꾸어 return 해주는 메소드
     * @param	str String	ksc 인코딩 문자열
     * @return	str	String	asc 인코딩 문자열
     */
    private String kscToAsc(String str) {
        try {
            if(str != null)
       	
                str = new String(str.getBytes("euc-kr"), "8859_1");
       
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
    
    /**
     * asc 인코딩을 ksc 인코딩으로 바꾸어 return 해주는 메소드
     * @param	str String	asc 인코딩 문자열
     * @return	str	String	ksc 인코딩 문자열
     */
    private String ascToKsc(String str) {
        try {
            if(str != null)
                str = new String(str.getBytes("8859_1"), "euc-kr");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
    
    /**
     * 인코딩을 테스트하기 위한 메소드
     * @param	temp	String	테스트대상	문자열
     * @return	none
     */
    private void getEncodingTest(String temp){
        
        try{
            System.out.println("**************************************************");
            String [] charSet = {"euc-kr","KSC5601","ISO-8859-1","UTF-8","8859_1"};
    
            System.out.println("Parameter Word :["+ temp + "]");
            System.out.println("**************************************************");
    
            for(int i=0 ; i<charSet.length ; i++){
                for(int j=0 ; j<charSet.length ; j++){
                    System.out.println(""+charSet[i]+" -> "+charSet[j]+" \t=>[" + new String(temp.getBytes(charSet[i]), charSet[j]) + "]");
                }
            }
            System.out.println("**************************************************");
        }catch(UnsupportedEncodingException e){}
    }	
	
    private String getWorkDirectory(String sWork) {
    	String sDirectory = "";
    	
    	if ( sWork.equals("RACER") ) {
    		sDirectory = RACER_FILE_PATH;
    	} else if ( sWork.equals("REFEREE") ) {
    		sDirectory = MNG_IMG_FILE_PATH;
    	} else {
    		sDirectory = COMMON_FILE_PATH;
    	}
    	
    	return sDirectory;
    }

    public void createDir(String sDir) {
    	File createDir = new File(sDir);
    
        if (!createDir.exists()){
        	createDir.mkdirs();
        }
    }
    
}
