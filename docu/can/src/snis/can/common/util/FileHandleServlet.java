/*================================================================================
 * 시스템			: 템플릿 시스템
 * 소스파일 이름	: snis.templateg.common.FileHandleServlet.java
 * 파일설명		: 파일 업로드, 다운로드, 삭제  처리 클래스를 호출하기 위한 Servlet 클래스
 * 작성자			: 이성호
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 2007-11-02
 * 최종수정자		: 이성호
 * 최종수정내용	: 파일경로 수정
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
* 이 클래스는 파일 업로드, 다운로드, 삭제  처리 클래스를 호출하기 위한 Servlet 클래스이다.
* @auther 이성호
* @version 1.0
*/
public class FileHandleServlet extends HttpServlet 
{
	
    /**
     * Servlet service 메소드를 상속받아 파일처리 타입(upload, download, delete)별로
     * 해당하는 메소드를 호출하기 위한 메소 
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