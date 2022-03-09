<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : noticeInsertForm.jsp
 * @메뉴명 : 공지사항 등록 폼
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.of.notice.vo.NoticeVO" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%
	NoticeVO noticeVO = (NoticeVO)request.getAttribute("noticeVO"); //공지사항 상세 정보
	List<CodeVO> codeList = (List<CodeVO>)request.getAttribute("codeList"); //공지사항 구분 코드
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList"); //첨부 파일 리스트
	List<MemberVO> memberNoticeList = (List<MemberVO>)request.getAttribute("memberNoticeList"); //등록된 대상자 리스트
	
	String currentPage = StringUtil.nvl(request.getParameter("currentPage"),"1"); //현재 페이지 번호
	String start_ymd = (String)request.getAttribute("start_ymd"); //공지 기간
	String end_ymd = (String)request.getAttribute("end_ymd"); //공지 기간
	
	String search_start_ymd = (String)request.getAttribute("search_start_ymd"); //검색 시작 날짜
	String search_end_ymd = (String)request.getAttribute("search_end_ymd"); //검색 마지막 날짜
	String search_noti_kind = (String)request.getAttribute("search_noti_kind"); //검색 공지 구분
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색 키워드
	String searchType = (String)StringUtil.nvl(request.getParameter("searchType"),"subject"); //검색 타입
	
	String seq = ""; //공지사항 시퀀스
	String subject = ""; //공지사항 제목
	String contents = ""; //공지사항 내용
	String update = ""; //공지사항 수정,등록 인지 구분코드
	String noti_kind = ""; //공지사항 구분
	String str = "저장"; //공지사항 수정,등록 문구
	
	if(noticeVO != null){
		seq = noticeVO.getSeq();
		subject = noticeVO.getSubject();
		contents = noticeVO.getContents();
		start_ymd = noticeVO.getStart_ymd();
		end_ymd = noticeVO.getEnd_ymd();
		noti_kind = noticeVO.getNoti_kind();
		update = "update";
		str = "수정";
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/styles.css" />
	<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/fileDrag.js"></script>
	<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/nicEdit/nicEdit.js"></script>
	<script type="text/javascript">
		var subtmit=false;		//submit flag
	
		/* 에디터폼 */
		bkLib.onDomLoaded(function() {
			new nicEditor({maxHeight : 310}).panelInstance('contents');
		});
		
		/* 파일업로드 */
		$(function(){
			// 파일업로드 drag & drop 초기화 
			if (window.File && window.FileList && window.FileReader) {
				fileDragInit();
			}
		});
		
		/**
		 * 공지사항 등록
		 */
		function insertNotice(){
			nicEditors.findEditor('contents').saveContent();
			var editContent = nicEditors.findEditor('contents').getContent();
			var subject = document.frm.subject.value;
			var contents = document.frm.contents.value;
			var start_ymd = document.frm.start_ymd.value;
			var end_ymd = document.frm.end_ymd.value;
			if (subject == ""){
				alert("제목을 넣어주세요");
				document.frm.subject.focus();
				return;
			}else if(formCheck.getByteLength(subject) > 100){
				alert("제목은 한글 50자 (영문 100자) 이상 입력할수 없습니다");
				document.frm.subject.focus();
				return;
			}else if(start_ymd == ""){
				alert("공지기간을 선택해 주세요");
				document.frm.start_ymd.focus();
				return;
			}else if(end_ymd == ""){
				alert("공지기간을 선택해 주세요");
				document.frm.end_ymd.focus();
				return;
			}else if(formCheck.dateChk(start_ymd, end_ymd)<0){
				alert("공지기간 시작 날짜가 공지기간 종료 날짜 보다 이후 입니다.");
				start_ymd.focus();
				return
			}else if(contents == ""){
				alert("내용을 입력해 주세요");
				document.frm.contents.focus();
				return;
			}else if($.trim(editContent.replace(/&nbsp;/gi, '')) == "" || $.trim(editContent.replace(/&nbsp;/gi, '')) == "<br>"){
				alert("내용을 입력해 주세요.");
				return;
			
			//2015.04.10 kta 공지사항 대상자 없이 등록
// 			}else if($("#target_empNos").length == 0){
// 				alert("대상자를 추가해 주세요.");
// 				getMember();
// 				return;
			}
			
			if(subtmit==false){
				if(confirm("<%=str%>" + " 하시겠습니까?") == true){
					if (window.File && window.FileList && window.FileReader) {
						var fileCount = fileUploadCnt();
						if(fileCount > 0) {
							loadingBar();
							uploadNext();
						}
						$('#pro').bPopup().close();
						
						document.frm.action="<%=GROUP_ROOT%>/of/notice/insertNotice.do";
						document.frm.submit();
					} else {
						if($("#fileselect").val() != ""){
							fileUploadByIframe('frm', '/hanagw/imgMultiFileUploadAction.do', 'fileselect[]', 'fileUploadByIframeCallback');
						} else {
							var $frm = $('#frm');
							$frm.attr('action','<%=GROUP_ROOT%>/of/notice/insertNotice.do');
							$frm.removeAttr('target');
							$frm.removeAttr('enctype');
							$frm[0].submit();
						}
					}
					subtmit=true;
				}
			}else{
					alert("<%=str%>" + "중 입니다.");
			}
		}	
		
		/**
		 * ex8 파일업로드 iframe
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
		 * ex8 파일업로드 iframe 콜백 함수 
		 * @param fileNum
		 */
		function fileUploadByIframeCallback(fileNum){
			$('#fileNum').val($('#fileNum').val()+fileNum+",");
			var $frm = $('#frm');
			$frm.attr('action','<%=GROUP_ROOT%>/of/notice/insertNotice.do');
			$frm.removeAttr('target');
			$frm.removeAttr('enctype');
			$frm[0].submit();
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
		 * 대상가져오는 팝업 
		 */
		function getMember(){
		 	
			$('#getMember').bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				//iframeAttr:'scrolling="no" frameborder="0" width="600px" height="800px"',
				iframeAttr:'scrolling="yes" frameborder="0" width="617px" height="'+$(window).innerHeight()+'px"',
				follow: [true, true],
				contentContainer:'.member_content',
				modalClose: true,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/pe/member/memberSelectPopup.do?type=NOTICE<%if(update == "update"){%>&seq=<%=seq%><%}else{ }%>',
			});
		}
		
		/*
		 *기존 row 삭제 
		 */
		function memberRemove(){
	 		var html =	'<tr><th>부서</th><th>직급</th><th class="br_none">이름</th></tr>';
	 		    html +=	'<tr><td colspan="3" class="br_none"><a href="#" onclick="javascript:getMember();">대상을 추가 하세요.</a></td></tr>';
	 		    
		 	$('#tbl tr').remove();
		 	$("#tbl").html(html);
		}
		
		/**
		 * 반드시 addMemberRow 로 function 만들어 테이블 상황에 맞게 테이블 생성한다.
		 * @param obj
		 */
		function addMemberRow(obj){
			memberRemove();
			 for(var i=0; i<obj.length; i++){
		    	if(obj[i].checked){
			    	var objParam = obj[i].value;
					var memberListValueArr = objParam.split("|");
					var objTable = document.getElementById("tbl");
					var objRow = document.getElementById('tbl').insertRow(1); // row 생성
					var objCel1 = document.createElement('TD');
					var objHidden = document.createElement('input');
					
					objHidden.type = "hidden";
					objHidden.name="target_empNos";
					objHidden.id="target_empNos";
					objHidden.value = memberListValueArr[0];
					objRow.appendChild(objHidden);
					
				   	objCel1 = document.createElement('TD');
				   	objCel1.setAttribute("width", "261"); // 넓이
				   	objCel1.innerHTML=memberListValueArr[2];
				   	objRow.appendChild(objCel1);
				   	
				   	objCel1 = document.createElement('TD');
				   	objCel1.setAttribute("width", "262"); // 넓이
				   	objCel1.innerHTML=memberListValueArr[3];
				   	objRow.appendChild(objCel1);
				   	
				   	objCel1 = document.createElement('TD');
				   	objCel1.setAttribute("width", "260"); // 넓이
				   	objCel1.setAttribute("class", "br_none");
				   	objCel1.innerHTML=memberListValueArr[1] + " <button type='button' class='type_01 ml35' name=cmdDelete onClick='removeRow(this);'>삭제</button>";
				   	objRow.appendChild(objCel1);
			    }
		    } 
			$('#getMember').bPopup().close();
			tableScroll();
		}
		
		/**
		 * 레이어 팝업 닫기
		 */
		function layerClose(){ 
			$('#getMember').bPopup().close();
		}
		
		/**
		 * 삭제한 첨부파일 정보
		 * @param seq
		 */
		function deleteFileAttach(seq){
			var delFileSeq = $('#delFileSeq').val();
		
			if(delFileSeq==''){
				 $('#delFileSeq').val(seq);
			}else{
				$('#delFileSeq').val(delFileSeq+','+seq);
			}
			$('#file_'+seq).remove();
		}
		
		/**
		 * 대상자 삭제
		 * @param r
		 */
		function removeRow(r){ 
		 	var i=r.parentNode.parentNode.rowIndex;
		 	document.getElementById('tbl').deleteRow(i);
		}
		
		/**
		 * 공지사항 목록으로 이동
		 */
		function goList(){
			var frm = document.frm;
			frm.action="<%=GROUP_ROOT %>/of/notice/noticeList.do";
			frm.submit();
		}
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<div class="cont float_left">
				<h2>공지사항</h2>
				<div class="noticeboard_box">
					<form id="frm" name="frm" method="post">
					<!--	파일첨부기능시 이름 변경하면 안됨 -->
					<input type="hidden" name="uploadUrl" id="uploadEditorUrl" value="<%=GROUP_ROOT%>/imgEditorFileUpload.do" />
					<input type="hidden" id="delFileSeq" name="delFileSeq" value=""/>
					<input type="hidden" id="filePathKind" name="filePathKind" value="notice"/>
					<input type="hidden" id="cd" name="cd" value="O01000"/> <!-- 공지사항 코드명 하드코딩 -->
					<!--	//파일첨부기능시 이름 변경하면 안됨 -->
					<input type="hidden" id="update" name="update" value="<%=update %>" />
					<input type="hidden" id="seq" name="seq" value="<%=seq %>" />
					<input type="hidden" id="currentPage" name="currentPage" value="<%=currentPage %>"/>
					<input type="hidden" id="search_start_ymd" name="search_start_ymd" value="<%=search_start_ymd %>"/>
					<input type="hidden" id="search_end_ymd" name="search_end_ymd" value="<%=search_end_ymd %>"/>
					<input type="hidden" id="search_noti_kind" name="search_noti_kind" value="<%=search_noti_kind %>"/>
					<input type="hidden" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword %>"/>
					<input type="hidden" id="searchType" name="searchType" value="<%=searchType %>"/>
					
					<div class="list_btn">
						<div class="list_button">
							<button type="button" class="type_01" onclick="javascript:insertNotice();"><%=str %></button>
							<button type="button" class="type_01" onclick="javascript:goList();">목록</button>
						</div>
					</div>
					<div class="cont_box cont_table06">
						<table>
							<colgroup>
								<col width="160" />
								<col width="" />
								<col width="" />
								<col width="" />
							</colgroup>
								<tbody>
									<tr>
										<th class="bt_none bc_re br_none" colspan="4">공지사항</th>
									</tr>
									<tr>
										<th>제목</th>
										<td colspan="3" class="txt_field br_none">
											<input type="text" id="subject" name="subject" value="<%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", subject))%>" />
										</td>
									</tr>
									<tr>
										<th>공지기간</th>
										<td colspan="3" class="date_search br_none">
											<div class="date_position">
												<input type="text" id="start_ymd" name="start_ymd" readonly="readonly" value="<%=start_ymd %>" />
												<button type="button" class="btn_date">날짜찾기</button>
											</div>
											~
											<div class="date_position">
												<input type="text" id="end_ymd" name="end_ymd" readonly="readonly" value="<%=end_ymd %>" />
												<button type="button" class="btn_date">날짜찾기</button>	
											</div>							
										</td>
									</tr>
									<tr>
										<th>공지구분</th>
										<td colspan="3" class="txt_field br_none">
											<select id="noti_kind" name="noti_kind" class="seltype1">
												<% if(codeList.size()!=0){
													for(int i=0;i<codeList.size();i++){
														CodeVO codeVO = new CodeVO();
														codeVO = codeList.get(i);
														if("Y".equals(codeVO.getUse_yn())){
												%>
														<option value="<%=codeVO.getCd()%>" <%if(codeVO.getCd().equals(noti_kind)){%>selected="selected"<%}%> ><%=codeVO.getCd_nm()%></option>
												<%
														}
													}
												   }
												%>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="4" class="txt_box01 br_none">
											<textarea id="contents" name="contents"><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", contents))%></textarea>
										</td>
									</tr>
									<tr>
										<th class="append_tit">첨부파일</th>
										<td colspan="3" class="append_file br_none">
											<div class="append_search">
												<input type="file" id="fileselect" name="fileselect[]" multiple="multiple" />
												<input type="hidden" name="fileNum" id="fileNum" />
												<!-- <button>찾아보기</button> -->
												<div id="filedrag">마우스로 파일을 끌어 놓으세요.</div>
											</div>
											<div class="append_list" id="messages">
											<%
												if(attachList!=null){
													for(int i=0; i<attachList.size(); i++){
														FileAttachVO fileAttachvo = new FileAttachVO();
														fileAttachvo = attachList.get(i);
													
													%>
													<input type="hidden" name="file_seq" id="file_seq" value="<%=fileAttachvo.getFile_seq()%>" />
													<p id="file_<%=fileAttachvo.getFile_seq()%>"><%=fileAttachvo.getOrigin_file_nm() %> (<%=fileAttachvo.getFile_size() %>byte)
														<button type="button" class="type_01 ml15" onclick="javascript:deleteFileAttach('<%=fileAttachvo.getFile_seq()%>');">삭제</button>
													</p>
													
													<% 
													}
												}
											
											%>
											</div>
										</td>
									</tr>																							
								</tbody>
						</table>
					</div>
					<%--//2015.04.10 kta 공지사항 대상자 없이 등록
					 <div class="append_object_box">
						<button type="button" class="type_01" onclick="javascript:getMember(); return false;">공지 대상 추가</button>
						<div class="cont_box cont_table07" style="width:100%; overflow-x:hidden;">
							<table id="tbl">
								<colgroup>
									<col width="261px"/>
									<col width=""/>
									<col width="261px"/>
								</colgroup>
								<thead>
									<tr>
										<th>부서</th>
										<th>직급</th>
										<th class="br_none">이름</th>
									</tr>
								</thead>
								<tbody>
								<% if(memberNoticeList.size()!=0){
									for(int i=0;i<memberNoticeList.size();i++){
										MemberVO memberVO = new MemberVO();
										memberVO = memberNoticeList.get(i);
								%>
								<tr>
									<td><%=memberVO.getDept_ko_nm() %></td>
									<td><%=memberVO.getGrad_ko_nm() %></td>
									<td>
										<%=memberVO.getEmp_ko_nm() %>
										<button type='button' class='type_01 ml35' name=cmdDelete onClick='removeRow(this);'>삭제</button>
										<input type="hidden" id="target_empNos" name="target_empNos" value="<%=memberVO.getEmp_no() %>" />
									</td>
								</tr>
								<%	}
								   }
								%>
									<tr>
										<td colspan="3" class="br_none" >
											<a href="#" onclick="javascript:getMember();">대상을 추가 하세요.</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div> --%>
				</form>
				</div>
			</div>
		</div>
	</div>
	<div id='pro' style='display:none; width:auto; height:auto; '>
		<img alt='loading' src='/hanagw/asset/img/ajax-loader.gif' />
	</div>
	<div id='getMember' style='display:none; width:auto; height:auto; '>
		<div class='member_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>