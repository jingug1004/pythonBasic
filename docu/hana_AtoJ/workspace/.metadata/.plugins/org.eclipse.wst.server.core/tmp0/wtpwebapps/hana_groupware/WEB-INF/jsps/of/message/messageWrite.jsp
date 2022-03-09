<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : messageWrite.jsp
 * @메뉴명 : 쪽지 쓰기
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.of.message.vo.MessageVO" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>

<%
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList"); //첨부파일리스트
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
		/* 파일업로드 drag & drop 초기화 */ 
		if (window.File && window.FileList && window.FileReader) {
			fileDragInit();
		}
	});
	
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
	 * 쪽지 보내기
	 */
	function insertMessage(){
		
		nicEditors.findEditor('contents').saveContent();
		var editContent = nicEditors.findEditor('contents').getContent();
		if($("#subject").val() == ""){
			alert("제목을 입력해 주세요.");
			return;
		}else if(formCheck.getByteLength(document.frm.subject.value) > 100){
			alert("제목은 한글 50자 (영문 100자) 이상 입력할수 없습니다");
			document.frm.subject.focus();
			return;
		}else if($.trim(editContent.replace(/&nbsp;/gi, '')) == "" || $.trim(editContent.replace(/&nbsp;/gi, '')) == "<br>"){
			alert("보내실 내용을 입력해 주세요.");
			return;
		}else if($("#target_empNos").length == 0){
			alert("대상자를 추가해 주세요.");
			getMember();
			return;
		}
		
		if(subtmit==false){
			if(confirm("쪽지를 보내시겠습니까?") == true){
				if (window.File && window.FileList && window.FileReader) {
					var fileCount = fileUploadCnt();
					if(fileCount > 0) {
						loadingBar();
						uploadNext();
					}
					$('#pro').bPopup().close();
					
					document.frm.action="<%=GROUP_ROOT%>/of/message/insertMessage.do";
					document.frm.submit();
				} else {
					if($("#fileselect").val() != ""){
						fileUploadByIframe('frm', '/hanagw/imgMultiFileUploadAction.do', 'fileselect[]', 'fileUploadByIframeCallback');
					} else {
						var $frm = $('#frm');
						$frm.attr('action','<%=GROUP_ROOT%>/of/message/insertMessage.do');
						$frm.removeAttr('target');
						$frm.removeAttr('enctype');
						$frm[0].submit();
					}
					
				}
				subtmit=true;
			}
		}else{
			alert("보내는중 입니다.");
		}
	}
	
	/**
	 * 익스8 파일업로드 iframe
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
	 * 익스8 파일업로드 iframe
	 * @param fileNum
	 */
	function fileUploadByIframeCallback(fileNum){
		$('#fileNum').val($('#fileNum').val()+fileNum+",");
		var $frm = $('#frm');
		$frm.attr('action','<%=GROUP_ROOT%>/of/message/insertMessage.do');
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
			iframeAttr:'scrolling="yes" frameborder="0" width="617px" height="'+$(window).innerHeight()+'px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT %>/pe/member/memberSelectPopup.do?type=MESSAGE',
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

	/* 반드시 addMemberRow 로 function 만들어 테이블 상황에 맞게 테이블 생성한다. */
	/**
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
	 * 임직원 대상자 삭제
	 * @param r
	 */
	function removeRow(r){ 
	 	var i=r.parentNode.parentNode.rowIndex;
	 	document.getElementById('tbl').deleteRow(i);
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
				<h2>쪽지 쓰기</h2>
				<div class="noticeboard_box">
					<div class="list_btn">
						<div class="list_button">
							<button type="button" class="type_01" onclick="javascript:insertMessage();">보내기</button>
							<button type="button" class="type_01" onclick="location.href='<%=GROUP_ROOT %>/of/message/messageList.do?type=1'">목록</button>
						</div>
					</div>
					<form id="frm" name="frm" method="post">
					<!--	파일첨부기능시 이름 변경하면 안됨 -->
					<input type="hidden" name="uploadUrl" id="uploadEditorUrl" value="<%=GROUP_ROOT %>/imgEditorFileUpload.do" />
					<input type="hidden" id="cd" name="cd" value="O03000" /> <!-- 쪽지 코드 변경되면 안됨 -->
					<input type="hidden" id="filePathKind" name="filePathKind" value="message"/>
					<!--	//파일첨부기능시 이름 변경하면 안됨 -->
					<input type="hidden" id="emp_no" name="emp_no" />
					<input type="hidden" id="delFileSeq" name="delFileSeq" value=""/>
					<div class="cont_box cont_table06">
						<table>
							<colgroup>
								<col width="20%" />
								<col width="80%" />
							</colgroup>
								<tbody>
									<tr>
										<th class="bt_none bc_re br_none" colspan="2">쪽지쓰기</th>
									</tr>
									<tr>
										<th class="">제목</th>
										<td class="txt_field br_none">
											<input type="text" id="subject" name="subject" />
											<input type="text" style="display: none;"/>
										</td>
									</tr>
									<tr>
										<td colspan="2" class="txt_box01 br_none">
											<textarea id="contents" name="contents"></textarea>
										</td>
									</tr>
									<tr>
										<th class="append_tit">첨부파일</th>
										<td class="append_file br_none">
											<div class="append_search">
												<input type="file" id="fileselect" name="fileselect[]" multiple="multiple" />
												<input type="hidden" name="fileNum" id="fileNum" value=""/>
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
													<p id="file_<%=fileAttachvo.getFile_seq()%>"><%=fileAttachvo.getOrigin_file_nm() %> (<%=fileAttachvo.getFile_size() %>byte)
													<button type="button" class="type_01 ml15" onclick="javascript:deleteFileAttach('<%=fileAttachvo.getFile_seq()%>');">삭제</button>
													</p>
													
													<% 
													}
												}
											
											%>
											</div>
											<div id="pro"></div>
										</td>
									</tr>																							
								</tbody>
						</table>
					</div>
					<div class="append_object_box">
						<button class="type_01" type="button" onclick="javascript:getMember();">받는이</button>
						<div class="cont_box cont_table07">
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
									<tr>
										<td colspan="4" class="br_none">
											<a href="#" onclick="javascript:getMember();">대상을 추가하세요.</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					</form>
					<!--  popup "note_box_staff.jsp" 삽입 -->
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