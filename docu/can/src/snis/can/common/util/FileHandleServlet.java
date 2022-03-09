/*================================================================================
 * �ý���			: ���ø� �ý���
 * �ҽ����� �̸�	: snis.templateg.common.FileHandleServlet.java
 * ���ϼ���		: ���� ���ε�, �ٿ�ε�, ����  ó�� Ŭ������ ȣ���ϱ� ���� Servlet Ŭ����
 * �ۼ���			: �̼�ȣ
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 2007-11-02
 * ����������		: �̼�ȣ
 * ������������	: ���ϰ�� ����
=================================================================================*/

package snis.can.common.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

/**
* �� Ŭ������ ���� ���ε�, �ٿ�ε�, ����  ó�� Ŭ������ ȣ���ϱ� ���� Servlet Ŭ�����̴�.
* @auther �̼�ȣ
* @version 1.0
*/
public class FileHandleServlet extends HttpServlet 
{
	
    /**
     * Servlet service �޼ҵ带 ��ӹ޾� ����ó�� Ÿ��(upload, download, delete)����
     * �ش��ϴ� �޼ҵ带 ȣ���ϱ� ���� �޼� 
     * @param   req		HttpServletRequest
     * @param   res		HttpServletResponse
     * @return	none
     * @throws Exception	ServletException,IOException 
     */	
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PosLog log = PosLogFactory.getLogger(getClass());
        log.logDebug("########## File Up/Down run START ##########");
        log.logDebug(">>>.mode:[" + req.getParameter("mode") + "]");

    	try {
            FileHandle fileHandle = FileHandle.getInstance();
            
            if(req.getParameter("mode").equals("upload"))
                fileHandle.upload(req, res);
            else if(req.getParameter("mode").equals("download"))
                fileHandle.download(req, res);
            else if(req.getParameter("mode").equals("delete"))
                fileHandle.delete(req, res);
            else
                return;
        } catch(Exception e){
            e.printStackTrace();
        }
        
        log.logDebug("########## File Up/Down run End ##########");
    }

}