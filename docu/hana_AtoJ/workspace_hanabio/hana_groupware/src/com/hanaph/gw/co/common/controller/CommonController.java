package com.hanaph.gw.co.common.controller;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.FileUtil;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.fileAttach.service.FileAttachService;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : CommonController.java
 * 설명 : 공통사용 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 7.      Beomjin          
 * </pre>
 * 
 * @version : 
 * @author  : Beomjin(@irush.co.kr)
 * @since   : 2014. 11. 7.
 */
@Controller
public class CommonController {
	
	@Autowired
	private FileAttachService fileAttachService;
	
	// log4j
	private static final Logger logger = Logger
			.getLogger(CommonController.class);

	// 프로퍼티 객체 생성
	Environment ev = new Environment();
	/**
	 * <pre>
	 * 1. 개요     : 공통 인쇄
	 * 2. 처리내용 : 공통 인쇄(인쇄 버튼 유)
	 * </pre>
	 * @Method Name : commonPrint
	 * @param request
	 * @return
	 */		
	@RequestMapping("/co/common/commonPrint.do")
	public ModelAndView commonPrint(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("co/common/commonPrint");
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 공통 인쇄
	 * 2. 처리내용 : 공통 인쇄(인쇄 버튼 무)
	 * </pre>
	 * @Method Name : commonPrintDirect
	 * @param request
	 * @return
	 */		
	@RequestMapping("/co/common/commonPrintDirect.do")
	public ModelAndView commonPrintDirect(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("co/common/commonPrintDirect");
		
		return mav;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요     : 공통 인쇄
	 * 2. 처리내용 : 공통 인쇄(플러그인 사용)
	 * </pre>
	 * @Method Name : commonPrintPlugin
	 * @param request
	 * @return
	 */		
	@RequestMapping("/co/common/commonPrintPlugin.do")
	public ModelAndView commonPrintPlugin(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("co/common/commonPrintPlugin");
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개발중인 메뉴들 alert 으로 막음
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : alert
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/co/common/alert.do")
	public void alert(HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
	    PrintWriter out = response.getWriter();
	    out.println("<script>alert('준비중입니다');");
	    out.println("history.go(-1)");
	    out.println("</script>");
	    
	    out.close();
		
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 첨부파일을 업로드한다. 
	 * 2. 처리내용 : 업로드한 파일은 비동기식으로 서버에 파일은 만든다.
	 * </pre>
	 * 
	 * @Method Name : imgUpload1
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgMultiFileUploadAction.do", method = RequestMethod.POST)
	public void imgUpload1(MultipartHttpServletRequest req,HttpServletResponse response)
			throws Exception {

		//String seq = StringUtil.nvl(req.getParameter("seq"), ""); // 게시판 글 seq
		String cd = StringUtil.nvl(req.getParameter("cd")); // 페이지 code
		String gubun = StringUtil.nvl(req.getParameter("filePathKind")); // 페이지 code
		
		String fileParamName  = StringUtil.nvl(req.getParameter("fileParamName"), "image");	// file객체를 담은 파라메터명
		String callbackFuncName  = StringUtil.nvl(req.getParameter("callbackFuncName"));	//upload 후에 호출할 callback함수명. 없으면 json형태로
		
		HttpSession memberSession = req.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());
		String create_no = emp_no; // 등록자 id
		
		String descr = StringUtil.nvl(req.getParameter("descr")); // 첨부파일 설명

		// 첨부파일 vo 객체 생성
		FileAttachVO uploadvo = new FileAttachVO();

		uploadvo.setSeq("0");
		uploadvo.setCd(cd);
		uploadvo.setCreate_no(create_no);
		uploadvo.setDelete_yn("N");

		List files = req.getFiles(fileParamName);
		
		logger.debug("files.size() : "+files.size());

		String sNum = req.getParameter("num");
		int num = 0;
		if (null != sNum && !"".equals(sNum)) {
			num = Integer.parseInt(sNum);
		}
		int fileSeq = 0;
		String today = getToday();
		String path = ev.getValue("file_dir.url")+"/"+gubun+"/"+today;
		String relativePath = "/"+gubun+"/"+today;
		FileUtil.makeDirectory(path);

		logger.debug("------------------imgMulti---------------------");
		logger.debug("path : "+path);
		logger.debug("create_no : "+create_no);
		logger.debug("uploadvo.getCreate_no() : "+uploadvo.getCreate_no());
		logger.debug("files.size()=" + files.size());
		logger.debug("------------------imgMulti---------------------");

		if(files.size() > 0){
			for (int i = 0; i < files.size(); i++) {
	
				MultipartFile file = (MultipartFile) files.get(i);
	
				// 업로드한 파일 정보 변수 setting
				String fileOriginNm = file.getOriginalFilename();
				String fileServerNm = FileUtil.renameFile(fileOriginNm);
				String fileExt = FileUtil.getFileExt(fileOriginNm);
				String pathName = path + File.separator + fileServerNm;
	
				// 서버에 파일 저장
				file.transferTo(new File(pathName));
				BufferedImage img = ImageIO.read(new File(pathName));
	
				// DB 저장
	
				uploadvo.setOrigin_file_nm(fileOriginNm);
				uploadvo.setFile_nm(fileServerNm);
				uploadvo.setFile_size(file.getSize());
				uploadvo.setFile_ext(fileExt);
				uploadvo.setDescr(descr);
				uploadvo.setFile_path(relativePath);
	
				fileSeq = fileAttachService.insertFileAttach(uploadvo);
	
			}
		}
		uploadvo.setFile_seq(fileSeq);
		
		// callback함수가 있을 경우 fileSeq를 인수로 해서 호출한다.
		if("".equals(callbackFuncName)){
			MarshallerUtil.marshalling("json", response, uploadvo);
		} else {
			// 현재 document는 response에 write되는 script문자열이 전부이므로 부모창의 함수를 호출하는 것이므로.
			String script = "<script>try{parent."+callbackFuncName+"('"+fileSeq+"');}catch(e){opener."+callbackFuncName+"('"+fileSeq+"');}</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}
		
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 첨부파일을 업로드한다. 
	 * 2. 처리내용 : 업로드한 파일은 비동기식으로 서버에 파일은 만든다.
	 * </pre>
	 * 
	 * @Method Name : imgUpload
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgFileUploadAction.do", method = RequestMethod.POST)
	public ModelAndView imgUpload(MultipartHttpServletRequest req)
			throws Exception {
		
		String gubun = StringUtil.nvl(req.getParameter("filePathKind")); // 페이지 code
		List files = req.getFiles("image");

		String today = getToday();
		String path = ev.getValue("file_dir.url")+"/"+gubun+"/"+today;
		//String relativePath = "/"+gubun+"/"+today;

		MultipartFile file = (MultipartFile) files.get(0);
		String pathName = path + File.separator + file.getOriginalFilename();
		file.transferTo(new File(pathName));
		BufferedImage img = ImageIO.read(new File(pathName));

		Map<String, String> image = new HashMap<String, String>();

		image.put("width", String.valueOf(img.getWidth()));

		image.put("original", path + file.getOriginalFilename());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");
		modelAndView.addObject("image", image);

		Robot robot = new Robot();
		robot.delay(4000);

		return modelAndView;
	}

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 게시글 상세보기에서 첨부파일은 선택하여 다운로드한다.
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : fileDownload
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/fileDownload.do")
	public ModelAndView fileDownload(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		
		// 다운로드할 파일의 경로와 파일명
		String fileName = req.getParameter("filename");
		String filePath = req.getParameter("filePath");
		String file_seq = req.getParameter("file_seq");
		String type = req.getParameter("type");
		String path = ev.getValue("file_dir.url")+filePath;
		String fullPath = path + "/" + fileName;

		//System.out.println("---------- CommonController fileDownload fileName : "+ fileName);
		//System.out.println("---------- CommonController fileDownload filePath : "+ filePath);
		//System.out.println("---------- CommonController fileDownload file_seq : "+ file_seq);
		//System.out.println("---------- CommonController fileDownload type : "+ type);
		//System.out.println("---------- CommonController fileDownload path : "+ path);
		
		File file = new File(fullPath);
		
		ModelAndView modelAndView = new ModelAndView("download", "downloadFile", file);
		modelAndView.addObject("file_seq", file_seq);
		modelAndView.addObject("type", type);
		
		return modelAndView;

	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 에디터 이미지 등록
	 * 2. 처리내용 : 에디터 이미지 등록
	 * </pre>
	 * @Method Name : imgEditorFileUpload
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/imgEditorFileUpload.do")
	public void imgEditorFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StringBuffer sb = new StringBuffer();
		
		if (!(request instanceof MultipartHttpServletRequest)) {
		} else {

			//String uploadPath = commonConfigVO.getUploadRoot() + "/test";
			String uploadPath = ev.getValue("file_dir_edit.url");

			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;

			MultipartFile mFile = mhsr.getFile("image");
			
			if (mFile != null && mFile.getSize() > 0) {

				String newName = System.currentTimeMillis()
						+ "."
						+ FilenameUtils.getExtension(mFile
								.getOriginalFilename());
				File file = FileUtil.createFileFromMultipartFile(mFile,
						uploadPath, newName);
				
				sb.append("{\"original\":\"" + ev.getValue("edit_root_dir.url") + "/edit/" + file.getName() + "\", \"width\":\"" + "300" + "\"}");

			} else {
				logger.error("파일 업로드 실패");
			}
		}
		
		MarshallerUtil.marshalling("txt", response, sb.toString());
	}

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 오늘 날짜
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getToday
	 * @return today
	 */
	public static String getToday(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();

		String today= dateFormat.format(currentDate.getTime()); 

		return today;
	}
}
