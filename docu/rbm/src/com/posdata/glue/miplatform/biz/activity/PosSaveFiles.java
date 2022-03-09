/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : 조성민
*@LastVersion    : 1.0
*    2007-10-01    조성민
*        1.0       최초 생성
==============================================================================*/

package com.posdata.glue.miplatform.biz.activity;

import com.posdata.glue.PosException;
import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.biz.activity.PosMiPlatformConstants;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * MiPlatform Dataset에 저장되어 있는 파일정보를 기반하여 서버 사이드에 파일을 저장하는 Activity. <br>
 * 다음은 MiPlatform Client 스크립트 소스이다.
 * (Dataset에 파일명, 파일경로, 파일컨텐츠(바이너리)를 저장하고 서버로 리퀘스트를 보내는 로직)
 * <xmp>
 * 
    // 레코드를 추가한다.
    var row = dsUploadFile.AddRow();

	File0.FileName = 
        FileDialog0.FilePath + "\\" + FileDialog0.FileName;

	edit0.Text = File0.FileName;
	edit1.Text = FileDialog0.FileName;

	File0.Open("rb");
	var blobbuffer = File0.ReadBinary();
	File0.Close();

	dsUploadFile.SetColumn(row, "filesize", File0.GetLength());

	// 읽은 파일 내용을 dataset에 저장한다.
	dsUploadFile.SetColumn(row, "filecontent", blobbuffer);
	
	// 서버상의 file path, file name 지정
	dsUploadFile.SetColumn(row, "filepath", "testfile");
	dsUploadFile.SetColumn(row, "filename", FileDialog0.FileName);
    
    
 * </xmp>
 * 서버 사이드에서 PosSaveFiles Activity를 다음과 같이 사용하면 
 * 클라이언트에서 발생한 파일을 저장할 수 있다.
 * 
 * <xmp>
 *    
    <activity name="SaveFiles" class="com.posdata.glue.miplatform.biz.activity.PosSaveFiles">
      <transition name="success" value="CustomFileLoader" />
      <property name="file-upload-path" value="d:/testfiles/glue" />
      <property name="is-real-path" value="false" />
      <property name="dataset-id" value="input" />
      <property name="resultkey" value="SavedFilesCount" />
    </activity>
    
    << Property 설명 >>
    file-upload-path: File을 저장할 Directory Path.
                      이 항목을 정의하지 않으면 Default로 Context Root에 저장
    
    is-real-path: 절대경로이면 true, 상대경로 이면 false
                  정의하지 않으면 Default로 상대경로 적용
    
    dataset-id: 클라이언트에서 발생 한 파일의 byte array를 포함한 Dataset ID
    
    resultkey: 저장한 File 수를 Context에 저장
               정의하지 않으면 File수를 Context에 저장하지 않음.
 * 
 * </xmp>
 * 
 * @author 조성민
 */
public class PosSaveFiles extends PosActivity 
{
    protected static PosLog log = PosLogFactory.getLogger(PosSaveFiles.class);
    
    /**
     * Dataset에 'filepath' 값이 지정되어 있으면 그 위치에 파일을 저장한다.
     * 지정되어 있지 않으면 SaveFiles Activity Property에 지정된 위치에 파일을 저장한다.
     * 
     * <xmp>
     * << 프로퍼티 대상 >> 
     *  1. file-upload-path 
     *      File을 저장할 Directory Path. 
     *      이항목을 정의하지 않으면 Default로 Context Root에 저장
     *  2. is-real-path 
     *      절대경로이면 true, 상대경로 이면 false. 이 항목은 정의하지 않으면 Default로 상대경로 적용
     *  3. resultkey 
     *      저장한 File 수를 Context에 저장함. 이항목을 정의하지 않으면 File수를 Context에 저장하지 않음.
     *  3. dataset-id
     *      클라이언트에서 발생 한 파일의 byte array를 포함한 Dataset ID
     * </xmp>
     * @return success or failure
     * @param ctx 컨텍스트
     */
    public String runActivity(PosContext ctx) 
    {
        String dsid = getProperty(PosMiPlatformConstants.DATESET);
        PosDataset ds = (PosDataset) ctx.get(dsid);
        if (ds == null) 
            throw new PosException("Dataset is null. ID [" + dsid + "]. Check ur dataset id");
        
        int fileSize = 0;
        // DML 적용된 레코드 처리는? 확인 필요
        // 현재는 normal record 처리만 가능
        PosRecord[] records = ds.getRecordForNormal();
        for (int i=0, size=records.length; i<size; i++) 
        {
            Object content = records[i].getAttribute(PosMiPlatformConstants.FILE_CONTENT);
            if (!(content instanceof byte[]))
                throw new PosException("Check your 'filecontent'");
            
            byte[] contents = (byte[])content;
            String filepath = records[i].getString(PosMiPlatformConstants.FILE_PATH);
            if (filepath == null)
                filepath = getProperty(PosMiPlatformConstants.FILE_UPLOAD_PATH);
                
            String filename = records[i].getString(PosMiPlatformConstants.FILE_NAME);
            String fullpath = filepath + "/" + filename;
            handleFile(filepath, filename, contents);
            logger.logInfo("File is Saved... [" + fullpath + "]");
            fileSize++;
        }
        String resultkey = getProperty(PosMiPlatformConstants.RESULT_KEY);
        if (resultkey != null)
            ctx.put(resultkey, new Integer(fileSize));
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * byte array로 추출된 파일을 저장한다.
     * 
     * @param contents 파일에서 추출 된 byte array
     * @param name 파일명
     * @param path 파일패스
     */
    public static void handleFile(String path, String name, byte[] contents) 
    {
        ByteArrayInputStream bais = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try 
        {
            bais = new ByteArrayInputStream(contents);
            File dir = new File(path);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            File file = new File(path + "/" + name);
            file.createNewFile();
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = 0;
            while ( (read = bais.read()) != -1)
            {
                bos.write(read);
            }
        } 
        catch (Exception ex) 
        {
            throw new PosException(
                "Fail to save file [" + path + "/" + name + "] - " + ex.getMessage(), ex);
        }
        finally 
        {
            closeOutputStream(bos);
            closeOutputStream(fos);
            closeInputStream(bais);
        }
    }    
    
    /**
     * OutputStream을 닫는 Utility Method
     * 
     * @param os OutputStream
     */
    public static void closeOutputStream(OutputStream os) 
    {
        if (os != null) 
        {
            try 
            {
                os.close();
            } 
            catch (Exception ex) 
            {
                log.logWarn("Fail to close output stream object: " + ex.getMessage());
            }
        }
    }
    
    /**
     * InputStream을 닫는 Utility Method
     * 
     * @param in InputStream
     */
    public static void closeInputStream(InputStream in) 
    {
        if (in != null) 
        {
            try 
            {
                in.close();
            } 
            catch (Exception ex) 
            {
                log.logWarn("Fail to close input stream object: " + ex.getMessage());
            }
        }
    }
}