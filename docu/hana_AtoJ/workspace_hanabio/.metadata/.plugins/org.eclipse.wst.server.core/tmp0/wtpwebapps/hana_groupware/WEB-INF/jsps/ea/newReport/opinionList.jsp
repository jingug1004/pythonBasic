<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : opinionList.jsp
 * @메뉴명 : 의견 팝업    
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.OpinionVO" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	
	/* 의견리스트 */
	List<OpinionVO> opinionList = (List<OpinionVO>)request.getAttribute("opinionList");
	
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));
	
	//CHOE 20151204 의견 - 첨부파일
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList");
	
	//세션 회원정보
	MemberVO memberSessionVO = new MemberVO();
	memberSessionVO = (MemberVO) session.getAttribute("gwUser");	
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/styles.css" />
	<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/fileDrag.js"></script>
	<script type="text/javascript">	
	
	/* 파일업로드 */
	$(function(){
		// 파일업로드 drag & drop 초기화 
		if (window.File && window.FileList && window.FileReader) {
			fileDragInit();
		}
	});
	
	/**
	 * 삭제한 첨부파일 정보//삭제한 첨부파일 정보
	 * @param seq
	 */
	function deleteFileAttach(seq){
		//alert("deleteFileAttach file_seq 1: "+ seq);
		
		var delFileSeq = $('#delFileSeq').val();
	
		if(delFileSeq==''){
			 $('#delFileSeq').val(seq);
		}else{
			$('#delFileSeq').val(delFileSeq+','+seq);
		}
		$('#file_'+seq).remove();	
		//alert("deleteFileAttach file_seq 2: "+ seq);
		parent.opinionReload();
		document.frm.action='<%=GROUP_ROOT%>/ea/newReport/deleteFileOpinion.do';
		document.frm.submit();			
	}
	
	/**
	 * ex8파일업로드 iframe
	 * @param formId
	 * @param url
	 * @param fileParamName
	 * @param callbackFuncName
	 */
	function fileUploadByIframe(formId, url, fileParamName, callbackFuncName){
		
		var $iframe = $('<iframe></iframe>');
		$iframe.attr('style', 'display:none');
		$iframe.attr('id', 'fileUpload_Iframe');
		$iframe.attr('name', 'fileUpload_Iframe');
		$iframe.attr('frameBorder', '0');
		$iframe.appendTo('body');
		
		var $frm = $('#'+formId);
		$frm.attr('action',url);
		$frm.attr('target', 'fileUpload_Iframe');
		$frm.attr('enctype', 'multipart/form-data');
		$frm.append($('<input type="hidden" value="'+$('#cd').val()+'" name="cd">'));
		$frm.append($('<input type="hidden" value="'+$('#filePathKind').val()+'" name="filePathKind">'));
		$frm.append($('<input type="hidden" value="'+fileParamName+'" name="fileParamName">'));
		$frm.append($('<input type="hidden" value="'+callbackFuncName+'" name="callbackFuncName">'));
		$frm[0].submit();
	}
	
	/**
	 * ex8파입업로드 콜백 함수
	 * @param fileNum
	 */
	function fileUploadByIframeCallback(fileNum){
		$('#fileNum').val($('#fileNum').val()+fileNum+",");
		var $frm = $('#frm');
		$frm.attr('action','<%=GROUP_ROOT%>/ea/newReport/insertDocument.do');
		$frm.removeAttr('target');
		$frm.removeAttr('enctype');
		$frm[0].submit();
	}
	
	
	$(document).ready(function () {
		document.getElementById("contents").focus();
    });
	
    /**
     * 의견저장한다.
     */
    function goSave(){
    	
    	if(formCheck.isNull(document.getElementById("contents"), "의견을 입력해주세요.")){
			return;
		}	
    	
		if (formCheck.getByteLength(document.getElementById("contents").value) > 2000){
			alert("1000자 이하로 입력해 주세요.");
			return;
		}
		if(confirm("등록하시겠습니까?") == true){
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/insertOpinion.do";
			document.getElementById("frm").submit();
			parent.opinionReload();		
		}
	}	
    
    
    /**
     * 의견삭제한다.
     */
    function deleteOpinion(opinion_seq){
    	if(confirm("삭제하시겠습니까?") == true){
    		parent.opinionReload();
    		document.getElementById("opinion_seq").value = opinion_seq;
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/deleteOpinion.do";
			document.getElementById("frm").submit();
		}
    }
    
    /**
     * 의견 radio check.
     */
    function goComment(status){
    	if("N" == status){
    		document.getElementById("contents").value = "의견 없습니다.";
    	}else{
    		document.getElementById("contents").value = "";
    		document.getElementById("contents").focus()
    	}
    }
    
    /*CHOE 의견 등록의 첨부파일은 의견 등록이 이뤄진 후 첨부 파일을 등록한다.
	 * 공지사항 게시판 쪽지 , 전자결재와는 다름 : 
	 * 		1. 파일 전송, 파일 번호 TABLE INSERT
	 * 		2. 문서 등록 -문서번호 나옴
	 * 		3. 문서번호 update 구조임 
	 * */    
    function uploadFileOpinion(opinion_seq){
		//alert("uploadFileOpinion opinion_seq : "+ opinion_seq);
		/*CHOE 20151207 등록을 누르는 순간 업로드 할 파일을 저장한다.*/			
		if (window.File && window.FileList && window.FileReader) {
			var fileCount = fileUploadCnt();				
			if(fileCount > 0) {
				loadingBar();
				uploadNext(opinion_seq);
			}
			$('#pro').bPopup().close();
			<%--
			document.frm.action='<%=GROUP_ROOT%>/ea/newReport/insertOpinion.do';
			document.frm.submit();
			--%>				
		}else{
			if($("#fileselect").val() != ""){
				fileUploadByIframe('frm', '/hanagw/opinionMultiFileUploadAction.do', 'fileselect[]', 'fileUploadByIframeCallback');
			} else {	
				<%--
				var $frm = $('#frm');
				$frm.attr('action','<%=GROUP_ROOT%>/ea/newReport/insertOpinion.do');
				$frm.removeAttr('target');
				$frm.removeAttr('enctype');
				$frm[0].submit();
				--%>
			}
		}
    }
    
    /**
	 * 로딩바
	 */
	function loadingBar(){
		$('#pro').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			//iframeAttr:'scrolling="no" frameborder="0" width="600px" height="800px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: false,
            opacity: 0.6,
            positionStyle: 'fixed',
            modalColor : 'white'
		});
	}
    /**
	 * 파일업로드
	 */
	function uploadNext(opinion_seq) {
		//alert("uploadNext opinion_seq : "+ opinion_seq);
		for (var int = 0; int < file_list.length; int++) {
			var nextFile = file_list[int];
			multiUploadFile(nextFile, opinion_seq);	//ajax 파일업로드
		}
	}
	
	/**
	 * 파일업로드
	 * @param file
	 */
	function multiUploadFile(file, opinion_seq) {
		//alert("multiUploadFile opinion_seq : "+ opinion_seq);
		var fd = new FormData(); // https://hacks.mozilla.org/2011/01/how-to-develop-a-html5-image-uploader/
		fd.append("image", file);
		fd.append("approval_seq", $('#approval_seq').val());
		fd.append("opinion_seq", opinion_seq);
		fd.append("filePathKind", $('#filePathKind').val());
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/hanagw/opinionMultiFileUploadAction.do", false);
		xhr.onload = function() {
			try {
				var res = JSON.parse(xhr.responseText);			// 업로드한 파일과 게시글 매핑시 사용함.
				var fileNum = res.file_seq;
			} catch (e) {
				console.log(e);
				return this.onError();
			}
			$('#fileNum').val($('#fileNum').val()+fileNum+",");
		};
		xhr.onerror = function() {};
		xhr.upload.onprogress = function(e) {
			//this.setProgress(e.loaded / e.total);
		};
		xhr.send(fd);
	}
	
	</script>	
</head>
<body>
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3><%=approvalMasterVO.getReq_dt() %><span> <%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></span></h3>
		</div>
		<%-- CHOE 20151204 해당 값이 있으면 오히려 사이즈가 안 맞음 
		<div class="inner"> --%>
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit">의견</span>
				</div>
				<form name="frm" id="frm" method="post" >				
				<!-- 2015-03-12 추가 시작 -->
				<div class="opinion">
					<input type="radio" name="comment" id="opinion_yes" value="Y" checked onclick="javascript:goComment(this.value);"><label for="opinion_yes">의견 있습니다.</label>
					<input type="radio" name="comment" id="opinion_no" value="N" onclick="javascript:goComment(this.value);"><label for="opinion_no">의견 없습니다.</label>
				</div>
				<!-- 2015-03-12 추가 끝 -->
				<input type="hidden" id="approval_seq" name="approval_seq" value="<%=approval_seq %>" />
				<input type="hidden" id="opinion_seq" name="opinion_seq" value=""/>
				<input type="hidden" id="filePathKind" name="filePathKind" value="Opinion"/>
				<input type="hidden" id="delFileSeq" name="delFileSeq" />
				<div class="box_comment opinion">
					<input type="text" name="contents" id="contents" class="js-sms-content"/>
					<input type="text" name="" id="" style="display: none;"/>
					<button type="button" onclick="javascript:goSave(); return false;">등록</button>
				</div>	
				</form>	
				<div class="wrap_tbl_comment">
					<table class="tbl_record">
						<colgroup>							
							<col style="" class="team"/>
							<col style="" class="grade"/>
							<col style="" class="person"/>							
							<col style="" class="comment"/>
							<col style="">
						</colgroup>
						<thead>							
							<th>부서</th>
							<th>직급</th>
							<th>작성자</th>							
							<th>의견</th>
							<th>작성시간</th>
						</thead>
						<tbody>
							<tr>
								<td colspan="5" class="box_scroll">
									<div>
										<table class="tbl_comment">
											<colgroup>												
												<col style="" class="team"/>
												<col style="" class="grade"/>
												<col style="" class="person"/>												
												<col style="" class="comment2"/>
												<col style="">
											</colgroup>
											<tbody>
											<%
												if(opinionList.size()!=0){
													for(int i=0; opinionList.size()>i;i++){
														OpinionVO opinionVO = new OpinionVO();
														opinionVO = opinionList.get(i);
											%>
												<tr>																									
													<td><%=StringUtil.nvl(opinionVO.getDept_ko_nm()) %></td>
													<td><%=StringUtil.nvl(opinionVO.getGrad_ko_nm()) %></td>
													<td><%=StringUtil.nvl(opinionVO.getEmp_ko_nm()) %></td>													
													<td class="comment"><%=RequestFilterUtil.toHtmlTagReplace("", opinionVO.getContents()) %> <%if(memberSessionVO.getEmp_no().equals(opinionVO.getCreate_no())){ %><a href="javascript:deleteOpinion('<%=opinionVO.getOpinion_seq()%>');"><img src="<%=GROUP_ROOT%>/asset/img/popup_btn_close01.gif" alt="삭제" /></a><%} %></td>
													<td class="bdrn"><%=StringUtil.nvl(opinionVO.getCreate_dt()) %></td>
												</tr>
												<tr>
													<th class="append_tit">
														첨부파일								
													</th>
													<td colspan="3" class="append_file br_none">
														<% if(i == 0) {
															if(memberSessionVO.getEmp_no().equals(opinionVO.getCreate_no())){ %>
														<div class="append_search" align=left>
															<input type="file" id="fileselect" name="fileselect[]" multiple="multiple" />
															<input type="hidden" name="fileNum" id="fileNum" />
															<div id="filedrag">마우스로 파일을 끌어 놓으세요.</div>
														</div>
														<%	} 
														} %>								
														<div id="messages" class="attachList" align=left>
														<%
															if(attachList!=null){
																for(int j =0; j<attachList.size(); j++){
																	FileAttachVO fileAttachvo = new FileAttachVO();
																	fileAttachvo = attachList.get(j);
																	/*CHOE 20151208 모든 값을 가져 오지만 의견 번호가 맞는 것만 아래로 표시 하도록 한다.																	
																	*/
																	if ( opinionVO.getOpinion_seq() == fileAttachvo.getOpinion_seq()){
																%>
																<p>																																
																<a href="<%=GROUP_ROOT%>/fileDownload.do?file_seq=<%=fileAttachvo.getFile_seq()%>&filename=<%=fileAttachvo.getFile_nm()%>&filePath=<%=fileAttachvo.getFile_path()%>&type=opinion">
																	<%=fileAttachvo.getOrigin_file_nm()%>
																</a>
																	<%if(memberSessionVO.getEmp_no().equals(opinionVO.getCreate_no())){ %>
																<button type="button" class="type_01 ml15" onclick="javascript:deleteFileAttach('<%=fileAttachvo.getFile_seq()%>'); return false;">삭제</button>
																	<%} %>
																</p>
																
																<% 
																	}
																}
															}
														
														%>
														</div>
														<div id="pro"></div>
													</td>
													<td class="bdrn">
														<% if(i == 1) {
															if(memberSessionVO.getEmp_no().equals(opinionVO.getCreate_no())){ %>
														<button type="button" onclick="javascript:uploadFileOpinion('<%=opinionVO.getOpinion_seq()%>'); return false;">첨부파일 저장</button>
														<%} 
														}%>
													</td>	
												</tr>
											<%
													} 
												}else{
										    %>
												<tr>
													<td colspan="5" class="bdrn">데이터가 없습니다.</td>
												</tr>
											<%
												}	
											%>
											</tbody>
										</table>
									</div>
								</td>								
							</tr>													
						</tbody>						
					</table>
				</div>
			</div>
		<%--CHOE 20151204 해당 값이 있으면 오히려 사이즈가 안 맞음
		</div> --%>
		<button type="button" class="close" onclick="parent.layerClose();"><span class="blind">닫기</span></button>
		<div class="box_close" ><button type="button" onclick="parent.layerClose();">닫기</button></div>
	</div>	
</div>	
</body>
</html>