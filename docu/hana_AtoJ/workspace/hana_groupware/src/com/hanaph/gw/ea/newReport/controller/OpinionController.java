/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.FileUtil;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.service.NewReportService;
import com.hanaph.gw.ea.newReport.service.OpinionService;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.OpinionVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : OpinionController.java
 * 설명 : 의견 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
@Controller
public class OpinionController {

	private Environment env = new Environment();
	
	@Autowired
	private OpinionService opinionService;
	
	@Autowired
	private NewReportService newReportService;
	
	private static final Logger logger = Logger.getLogger(NewReportController.class);

	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 의견 리스트 가져온다.
	 * 2. 처리내용 : 의견 리스트 가져온다.
	 * </pre>
	 * @Method Name : getOpinionList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/opinionList.do")
	public ModelAndView getOpinionList(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/opinionList");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String opinion_seq = StringUtil.nvl((String)request.getParameter("opinion_seq"));  //CHOE 20151207 추가	
		
		//결재 마스터 정보 가져온다.
		ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
				
		//의견정보 리스트 가져온다.
		paramMap.put("approval_seq", approval_seq);
		List<OpinionVO> opinionList = opinionService.getOpinionList(paramMap);
				
		/*첨부파일*/				
		List<FileAttachVO> attachList = opinionService.getFileOpinionList(paramMap);
					
		mav.addObject("approvalMasterVO", approvalMasterVO);
		mav.addObject("opinionList", opinionList);
		mav.addObject("approval_seq", approval_seq);
		mav.addObject("opinion_seq", opinion_seq);		
		mav.addObject("attachList", attachList);//첨부파일		
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 의견 저장한다.
	 * 2. 처리내용 : 의견 저장한다.
	 * </pre>
	 * @Method Name : insertOpinion
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/ea/newReport/insertOpinion.do")
	public void insertOpinion(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String create_no = memberSessionVO.getEmp_no();
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));		
		String contents = StringUtil.nvl((String)request.getParameter("contents"));		
		
		OpinionVO opinionVO = new OpinionVO();
		opinionVO.setContents(contents);
		opinionVO.setCreate_no(create_no);
		opinionVO.setApproval_seq(approval_seq);			

		/*CHOE 의견 등록의 첨부파일은 의견 등록이 이뤄진 후 첨부 파일을 등록한다.
		 * 공지사항 게시판 쪽지 , 전자결재와는 다름 : 
		 * 		1. 파일 전송, 파일 번호 TABLE INSERT
		 * 		2. 문서 등록 -문서번호 나옴
		 * 		3. 문서번호 update 구조임 
		 * */
		int iResult = opinionService.insertOpinion(opinionVO);
		
		paramMap.put("approval_seq", approval_seq);
		//System.out.println("---------- opinionController insertOpinion getOpinion_seq : "+ opinionVO.getOpinion_seq());
		//System.out.println("---------- opinionController insertOpinion iResult : "+ iResult);
		
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/opinionList.do");
		
		if(iResult > 0){
			String script = "<script> location.href='"+env.getValue("root_dir.url")+"/ea/newReport/opinionList.do?approval_seq="+approval_seq+"';</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}		
		
		/*CHOE 20151208 파일첨부삭제시퀀스 : 의견은 전자결재와 다른게 새로운 건이 등록 될수 있다
		String delFileSeq = StringUtil.nvl(request.getParameter("delFileSeq"));	
		
		if(!"".equals(delFileSeq)){
			opinionService.deleteFileOpinion(delFileSeq);
		}
		*/
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인의견 삭제한다.
	 * 2. 처리내용 : 개인의견 삭제한다.
	 * </pre>
	 * @Method Name : deleteOpinion
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/ea/newReport/deleteOpinion.do")
	public void deleteOpinion(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String opinion_seq = StringUtil.nvl((String)request.getParameter("opinion_seq"));
		
		paramMap.put("opinion_seq", opinion_seq);

		int iResult = opinionService.deleteOpinion(paramMap);
		
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/opinionList.do");
		
		if(iResult > 0){
			String script = "<script> location.href='"+env.getValue("root_dir.url")+"/ea/newReport/opinionList.do?approval_seq="+approval_seq+"';</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 의견의 첨부파일을 삭제한다.
	 * 2. 처리내용 : 의견의 첨부파일을 삭제한다.
	 * </pre>
	 * @Method Name : deleteFileOpinion
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/ea/newReport/deleteFileOpinion.do")
	public void deleteFileOpinion(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
				
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String delFileSeq = StringUtil.nvl(request.getParameter("delFileSeq"));	//파일첨부삭제시퀀스
		//System.out.println("---------- opinionController deleteFileOpinion delFileSeq : "+delFileSeq);
		String alertMessage = "수정 되었습니다.";
		
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("file_seq", delFileSeq); //삭제 파일 번호
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/opinionList.do");
		
		if(!"".equals(delFileSeq)){
			
			opinionService.deleteFileOpinion(delFileSeq);
		}
		String script = "<script> location.href='"+env.getValue("root_dir.url")+"/ea/newReport/opinionList.do?approval_seq="+approval_seq+"';</script>";
		MarshallerUtil.marshalling("txt", response, script);
	}
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 의견 첨부파일을 업로드한다. 
	 * 2. 처리내용 : 업로드한 파일은 비동기식으로 서버에 파일은 만든다.
	 * </pre>
	 * 
	 * @Method Name : opinionMultiFileUploadAction
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/opinionMultiFileUploadAction.do", method = RequestMethod.POST)
	public void opinionMultiFileUploadAction(MultipartHttpServletRequest req,HttpServletResponse response)
			throws Exception {

		String gubun = StringUtil.nvl(req.getParameter("filePathKind")); //전자결재문서 경로
		
		String fileParamName  = StringUtil.nvl(req.getParameter("fileParamName"), "image");	// file객체를 담은 파라메터명
		String callbackFuncName  = StringUtil.nvl(req.getParameter("callbackFuncName"));	//upload 후에 호출할 callback함수명. 없으면 json형태로		
		
		HttpSession memberSession = req.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());
		String create_no = emp_no; // 등록자 id
		
		String descr = StringUtil.nvl(req.getParameter("descr")); // 첨부파일 설명

		/*첨부파일 vo 객체 생성*/
		FileAttachVO uploadVO = new FileAttachVO();

		/*CHOE 의견 등록의 첨부파일은 의견 등록이 이뤄진 후 첨부 파일을 등록한다.
		 * 공지사항 게시판 쪽지 , 전자결재와는 다름 : 
		 * 		1. 파일 전송, 파일 번호 TABLE INSERT
		 * 		2. 문서 등록 -문서번호 나옴
		 * 		3. 문서번호 update 구조임 
		 * */
		String approval_seq = StringUtil.nvl(req.getParameter("approval_seq"));
		String opinion_seq = StringUtil.nvl(req.getParameter("opinion_seq"));
		//int opinion_seq = Integer.parseInt(req.getParameter("opinion_seq"));		
		//int opinion_seq = ((Integer)req.getAttribute("opinion_seq")).intValue();
		//System.out.println("---------- opinionController opinionMultiFileUploadAction opinion_seq : "+opinion_seq);
		
		uploadVO.setApproval_seq(approval_seq);
		uploadVO.setOpinion_seq(Integer.parseInt(opinion_seq));
		//uploadVO.setOpinion_seq(0);
		uploadVO.setCreate_no(create_no);
		uploadVO.setDelete_yn("N");

		List files = req.getFiles(fileParamName);
		
		logger.debug("files.size() : "+files.size());

		int fileSeq = 0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();

		String today = dateFormat.format(currentDate.getTime()); 
		
		String path = env.getValue("file_dir.url")+"/"+gubun+"/"+today;
		String relativePath = "/"+gubun+"/"+today;
		FileUtil.makeDirectory(path);

		logger.debug("------------------imgMulti---------------------");
		logger.debug("path : "+path);
		logger.debug("create_no : "+create_no);
		logger.debug("uploadVO.getCreate_no() : "+uploadVO.getCreate_no());
		logger.debug("files.size()=" + files.size());
		logger.debug("------------------imgMulti---------------------");

		if(files.size() > 0){
			for (int i = 0; i < files.size(); i++) {
	
				MultipartFile file = (MultipartFile) files.get(i);
				
				/*업로드한 파일 정보 변수 setting*/
				String fileOriginNm = file.getOriginalFilename();
				String fileServerNm = FileUtil.renameFile(fileOriginNm);
				String fileExt = FileUtil.getFileExt(fileOriginNm);
				String pathName = path + File.separator + fileServerNm;
				
				/*서버에 파일 저장*/
				file.transferTo(new File(pathName));
	
				/*DB 저장*/			
				uploadVO.setOrigin_file_nm(fileOriginNm);
				uploadVO.setFile_nm(fileServerNm);
				uploadVO.setFile_size(file.getSize());
				uploadVO.setFile_ext(fileExt);
				uploadVO.setDescr(descr);
				uploadVO.setFile_path(relativePath);
	
				System.out.println("---------- getFile_seq : "+uploadVO.getFile_seq());
				System.out.println("---------- getApproval_seq : "+uploadVO.getApproval_seq());
				System.out.println("---------- getOpinion_seq : "+uploadVO.getOpinion_seq());
				System.out.println("---------- fileOriginNm : "+fileOriginNm);
				fileSeq = opinionService.insertFileOpinion(uploadVO);
	
			}
		}
		
		uploadVO.setFile_seq(fileSeq);
		
		/*callback함수가 있을 경우 fileSeq를 인수로 해서 호출한다.*/
		if("".equals(callbackFuncName)){
			MarshallerUtil.marshalling("json", response, uploadVO);
		} else {
			/*현재 document는 response에 write되는 script문자열이 전부이므로 부모창의 함수를 호출하는 것이므로.*/
			String script = "<script>try{parent."+callbackFuncName+"('"+fileSeq+"');}catch(e){opener."+callbackFuncName+"('"+fileSeq+"');}</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}
		
	}
}
