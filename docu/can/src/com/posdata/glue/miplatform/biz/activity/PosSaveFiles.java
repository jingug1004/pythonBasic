/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : ������
*@LastVersion    : 1.0
*    2007-10-01    ������
*        1.0       ���� ����
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
 * MiPlatform Dataset�� ����Ǿ� �ִ� ���������� ����Ͽ� ���� ���̵忡 ������ �����ϴ� Activity. <br>
 * ������ MiPlatform Client ��ũ��Ʈ �ҽ��̴�.
 * (Dataset�� ���ϸ�, ���ϰ��, ����������(���̳ʸ�)�� �����ϰ� ������ ������Ʈ�� ������ ����)
 * <xmp>
 * 
    // ���ڵ带 �߰��Ѵ�.
    var row = dsUploadFile.AddRow();

	File0.FileName = 
        FileDialog0.FilePath + "\\" + FileDialog0.FileName;

	edit0.Text = File0.FileName;
	edit1.Text = FileDialog0.FileName;

	File0.Open("rb");
	var blobbuffer = File0.ReadBinary();
	File0.Close();

	dsUploadFile.SetColumn(row, "filesize", File0.GetLength());

	// ���� ���� ������ dataset�� �����Ѵ�.
	dsUploadFile.SetColumn(row, "filecontent", blobbuffer);
	
	// �������� file path, file name ����
	dsUploadFile.SetColumn(row, "filepath", "testfile");
	dsUploadFile.SetColumn(row, "filename", FileDialog0.FileName);
    
    
 * </xmp>
 * ���� ���̵忡�� PosSaveFiles Activity�� ������ ���� ����ϸ� 
 * Ŭ���̾�Ʈ���� �߻��� ������ ������ �� �ִ�.
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
    
    << Property ���� >>
    file-upload-path: File�� ������ Directory Path.
                      �� �׸��� �������� ������ Default�� Context Root�� ����
    
    is-real-path: �������̸� true, ����� �̸� false
                  �������� ������ Default�� ����� ����
    
    dataset-id: Ŭ���̾�Ʈ���� �߻� �� ������ byte array�� ������ Dataset ID
    
    resultkey: ������ File ���� Context�� ����
               �������� ������ File���� Context�� �������� ����.
 * 
 * </xmp>
 * 
 * @author ������
 */
public class PosSaveFiles extends PosActivity 
{
    protected static PosLog log = PosLogFactory.getLogger(PosSaveFiles.class);
    
    /**
     * Dataset�� 'filepath' ���� �����Ǿ� ������ �� ��ġ�� ������ �����Ѵ�.
     * �����Ǿ� ���� ������ SaveFiles Activity Property�� ������ ��ġ�� ������ �����Ѵ�.
     * 
     * <xmp>
     * << ������Ƽ ��� >> 
     *  1. file-upload-path 
     *      File�� ������ Directory Path. 
     *      ���׸��� �������� ������ Default�� Context Root�� ����
     *  2. is-real-path 
     *      �������̸� true, ����� �̸� false. �� �׸��� �������� ������ Default�� ����� ����
     *  3. resultkey 
     *      ������ File ���� Context�� ������. ���׸��� �������� ������ File���� Context�� �������� ����.
     *  3. dataset-id
     *      Ŭ���̾�Ʈ���� �߻� �� ������ byte array�� ������ Dataset ID
     * </xmp>
     * @return success or failure
     * @param ctx ���ؽ�Ʈ
     */
    public String runActivity(PosContext ctx) 
    {
        String dsid = getProperty(PosMiPlatformConstants.DATESET);
        PosDataset ds = (PosDataset) ctx.get(dsid);
        if (ds == null) 
            throw new PosException("Dataset is null. ID [" + dsid + "]. Check ur dataset id");
        
        int fileSize = 0;
        // DML ����� ���ڵ� ó����? Ȯ�� �ʿ�
        // ����� normal record ó���� ����
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
     * byte array�� ����� ������ �����Ѵ�.
     * 
     * @param contents ���Ͽ��� ���� �� byte array
     * @param name ���ϸ�
     * @param path �����н�
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
     * OutputStream�� �ݴ� Utility Method
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
     * InputStream�� �ݴ� Utility Method
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