package com.hanaph.gw.co.common.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * Class Name : FileUtil.java
 * 설명 : com.shinhan.common.FileUtil.java File Upload/Download 관련 Class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17. JaeKap Kim		생성
 * </pre>
 * 
 * @version 1.0
 * @author slawin(@irush.co.kr)
 * @since 2014. 10. 17.
 */
public class FileUtil {
	
	static String fileUploadPath = "D:\\hana_AtoJ\\upload\\"; // 파일 업로드 경로
	
	
	/**
     * 
     * 파일의 경로를 담은 문자열에서 파일 경로 구분자를 /로 통일시키면서 /의 중복을 하나로 없애주는 메소드 
     * 
     * @param orgPath
     *            파일의 경로를 담은 문자열
     * @return String 파일 구분자를 /로 변경한 파일의 경로를 담은 문자열
     */
    public static String replaceAllFileSepToSlash(String orgPath) {

        String path = orgPath.replaceAll("\\\\\\\\", "/");
        path = path.replaceAll("\\\\", "/");
        path = path.replaceAll("//", "/");
        path = path.replaceAll("//", "/");

        return path;

    }

	/**
     * 
     * 디렉토리 생성 메소드 
     * 
     * @param orgPath
     *            생성할 디렉토리의 절대 경로
     * @return String 생성된 디렉토의 절대 경로
     */
    public static String makeDirectory(String orgPath) {
        String fullpath = "";

        String path = replaceAllFileSepToSlash(orgPath);

        File fileDirectory = new File(path); // parasoft-suppress BD.SECURITY.TDFNAMES "Java System Property를 사용함"

        if (!fileDirectory.exists()) {
            String[] pathUnit = path.split("/");
            String upDirPath = "";
            StringBuffer currDirPath = new StringBuffer();
            File upDir = null;
            File currDir = null;
            String dirName = "";
            for (int i = 0; i < pathUnit.length; i++) {
                dirName = pathUnit[i];
                if ("".equals(dirName)) {
                    dirName = "/";
                }
                upDirPath = currDirPath.toString();
                /*currDirPath = "".equals(currDirPath) ? dirName : (currDirPath + "/" + dirName);
                PMD 적용에 따른 StringBuffer화*/
                if ("".equals(currDirPath.toString())) {
                    currDirPath.append(dirName);
                }
                else {
                    currDirPath.append("/").append(dirName);
                }
                upDir = new File(upDirPath);
                currDir = new File(currDirPath.toString()); // parasoft-suppress BD.SECURITY.TDFNAMES "Java System Property를 사용함"

                if (upDir.exists() && upDir.isDirectory()) {
                    if (!currDir.exists()) {
                        currDir.mkdir();
                    }
                }
            }
            fullpath = currDirPath.toString();
        }

        return fullpath;
    }
    
    /**
     * 
     * 원본 파일을 대상 디렉토리에 동일한 이름으로 이동시키는 메소드
     * 
     * @param orgnFileName
     *            원본 파일 이름
     * @param orgnFilePath
     * 			  원본 파일 디렉토리
     * @param targetPath
     *            대상 디렉토리
     * @return boolean 성공 여부
     */
    public static boolean moveFile(String orgnFileName, String orgnFilePath, String targetPath) {

        try {
            makeDirectory(targetPath);
            String dest = targetPath + orgnFileName;
            String orgnDir = orgnFilePath + orgnFileName;
            
            File orgnFile = new File(orgnDir);
            File destFile = new File(dest);
            
            if (destFile.exists() && destFile.isFile()) {
                destFile.delete();
            }
            orgnFile.renameTo(new File(dest));

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * 원본 파일을 대상 디렉토리에 특정 이름으로 이동시키는 메소드 
     * @param orgnFileName
     *            원본 파일 이름
     * @param orgnFilePath
     * 			  원본 파일 디렉토리
     * @param targetFileName
     *            대상 파일 이름
     * @param targetPath
     *            대상 디렉토리
     * @return boolean 성공 여부
     */
    public static boolean moveFile(String orgnFileName, String orgnFilePath, String targetFileName, String targetPath) {

        try {
            makeDirectory(targetPath);
            String dest = targetPath + targetFileName;
            String orgnDir = orgnFilePath + orgnFileName;
            
            File orgnFile = new File(orgnDir);
            File destFile = new File(dest);
            
            if (destFile.exists() && destFile.isFile()) {
                destFile.delete();
            }
            orgnFile.renameTo(new File(dest));

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * 단건 파일을 삭제하는 메소드. 
     * 
     * @param filePath
     *            삭제 대상 절대 경로
     */
    public static void deleteFile(String filePath) {
        File delFile = null;
        if (!"".equals(filePath)) {
            delFile = new File(filePath);
            if (delFile.exists() && delFile.isFile()) {
                delFile.delete();
            }
        }
    }
    
    /**
     * 
     * 파일명 변경
     * 현재 시간 + 4자리 난수 + 확장자
     * 
     * @param fileNam
	 *				수정하려는 업로드 파일 원본이름
     * @return	바뀐 업로드 파일명
     */
	static public String renameFile(String fileName){
		
		String returnFileName = "";
		
		// 현재 시간
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss", Locale.KOREA);
		Date currentTime = new Date ();
		String time = formatter.format(currentTime);
		
		// 난수 생성
	    String randomNumber = getRandomNumber();
	    
	    // 파일 확장자
	    String fileExt = getFileExt(fileName);
	    
	    // 파일명 조합
	    returnFileName = time + randomNumber + "." + fileExt;
		
		return returnFileName;
	}
	
	/**
	 * 
	 * 난수 생성
	 * 
	 * @return	난수
	 */	
	static public String getRandomNumber(){
		
		Random random = new Random();
		String randomNumber = "";
		
		// 4자리 난수 생성
		for (int i = 0; i < 4; i++) {
			randomNumber += random.nextInt(10);
		}
		
		return randomNumber;
	}
	
	/**
	 * 
	 * 파일명 확장자 구하기
	 * 
	 * @param fileName
	 * 				업로드 파일명
	 * @return	파일의 확장자명
	 */
	static public String getFileExt(String fileName){
		
		int fileExtIndex = fileName.lastIndexOf(".");
		
		return fileName.substring(fileExtIndex + 1);
		
	}

	/**
	 * 업로드된 MultipartFile 객체를 실체 파일 객체로 생성시키는 메소드
	 * @param mFile
	 * @param parentDirPath
	 * @param newFileName
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static File createFileFromMultipartFile(MultipartFile mFile, String parentDirPath, String newFileName) throws IllegalStateException, IOException{
		
		File uploadDir = new File(parentDirPath);
		if(!uploadDir.exists()){
			FileUtils.forceMkdir(uploadDir);
		}
		
		
		File f = new File(uploadDir.getAbsolutePath()+"/"+newFileName);
		
		mFile.transferTo(f);
		
		return f;
	}
	
}
