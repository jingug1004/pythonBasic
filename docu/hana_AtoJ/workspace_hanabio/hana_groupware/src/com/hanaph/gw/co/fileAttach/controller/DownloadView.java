/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.fileAttach.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.hanaph.gw.co.fileAttach.service.FileAttachService;
import com.hanaph.gw.ea.newReport.service.NewReportService;
import com.hanaph.gw.ea.newReport.service.OpinionService;

/**
 * <pre>
 * Class Name : DownloadView.java
 * 설명 : 파일 다운로드 Class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 22.      우정아          
 * </pre>
 * 
 * @version : 
 * @author  : babyj(@irush.co.kr)
 * @since   : 2014. 10. 22.
 */
public class DownloadView  extends AbstractView{

	public DownloadView() {
        setContentType("applicaiton/download;charset=utf-8");
    }
	@Autowired
	FileAttachService fileAttachService;
	
	@Autowired
	NewReportService newReportService;
	
	@Autowired
	private OpinionService opinionService;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.debug("renderMergedOutputModel>>>>>>>>>>>>>>>>>>>>>>>>>>>>>start");
		
		File file = (File) map.get("downloadFile");
		String file_seq =  (String) map.get("file_seq");
		String type =  (String) map.get("type");
		
		String origin_file_nm = "";
		
		/*전자결재 파일첨부*/
		if("appr".equals(type)){
			origin_file_nm = newReportService.getOriginFileNm(file_seq);
		}else if("opinion".equals(type)){			
			origin_file_nm = opinionService.getOriginFileOpinionNm(file_seq);
		}else{
			origin_file_nm = fileAttachService.getOriginFileNm(file_seq);
		}
		
		logger.debug("DownloadView --> file.getPath() : " + file.getPath());
		logger.debug("DownloadView --> file.getName() : " + file.getName());

		response.setContentType(getContentType());
		response.setContentLength((int) file.length());

		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;

		String fileName = null;

		if (ie) {
			fileName = URLEncoder.encode(origin_file_nm, "utf-8");
		} else {
			fileName = new String((origin_file_nm).getBytes("KSC5601"), "8859_1");//new String(origin_file_nm.getBytes("utf-8"));
		}

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;

		try {

			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}

		out.flush();
	}
	
	
}
