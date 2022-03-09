<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : boardInsertForm.jsp
 * @메뉴명 : 게시판등록폼
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<CodeVO> codeList = (List<CodeVO>)request.getAttribute("codeList");
	
	String reply_grp=StringUtil.nvl((String)request.getAttribute("reply_grp")); //연관된 글을 묶어줌(사용안함)
	String reply_level=StringUtil.nvl((String)request.getAttribute("reply_level")); //글의 정렬 순서(사용안함)
	String cd=StringUtil.nvl((String)request.getAttribute("cd")); //게시판종류
	String m_cd=StringUtil.nvl((String)request.getAttribute("m_cd")); //게시판종류
	String start_ymd=StringUtil.nvl((String)request.getAttribute("start_ymd")); //게시기간
	String end_ymd=StringUtil.nvl((String)request.getAttribute("end_ymd")); //게시기간
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>게시판</title>
<%@include file="/include/head.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/styles.css" />
<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/formCheck.js" ></script>	
<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/fileDrag.js"></script>
<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/nicEdit/nicEdit.js"></script>
<script type="text/javascript">
	
	var subtmit=false;		//submit flag
	
	/* 에디터폼 */
	bkLib.onDomLoaded(function() {
		new nicEditor({maxHeight : 310}).panelInstance('contents');
	});
	
	$(function(){
		/* 파일업로드 drag & drop 초기화 */ 
		if (window.File && window.FileList && window.FileReader) {
			fileDragInit();
		}
	});
	
	/**
	 * 게시글 저장
	 * @returns
	 */
	function saveBoard(){
		nicEditors.findEditor('contents').saveContent();
		var editContent = nicEditors.findEditor('contents').getContent();
		var subject = $('#subject');
		var start_ymd = $('#start_ymd');
		var end_ymd = $('#end_ymd');
		
		if(formCheck.isNullStr(subject.val())){
			alert("제목을 넣어주세요");
			subject.focus();
			return
		}else if(formCheck.getByteLength(subject.val()) > 100){
			alert("제목은 한글 50자 (영문 100자) 이상 입력할수 없습니다");
			return;
		}else if(formCheck.isNullStr(start_ymd.val())){
			alert("게시 시작일을 넣어주세요");
			start_date.focus();
			return
		}else if(formCheck.isNullStr(end_ymd.val())){
			alert("게시 종료일을 넣어주세요");
			start_date.focus();
			return
		}else if(formCheck.dateChk(start_ymd.val(),end_ymd.val())<0){
			alert("게시 시작 날짜가 게시 종료 날짜 보다 이후 입니다.");
			btn_date.focus();
			return
		}else if($.trim(editContent.replace(/&nbsp;/gi, '')) == "" || $.trim(editContent.replace(/&nbsp;/gi, '')) == "<br>"){
			alert("보내실 내용을 입력해 주세요.");
			return;
		}else if($("#target_empNos").length == 0){
			alert("대상자를 추가해 주세요.");
			getMember();
			return;
		}
		
		if(subtmit==false){
			if(confirm("등록 하시겠습니까?") == true){
				if (window.File && window.FileList && window.FileReader) {
					var fileCount = fileUploadCnt();
					if(fileCount > 0) {
						loadingBar();
						uploadNext();
					}
					$('#pro').bPopup().close();
					var frm = document.frm;
					
					frm.action="<%=GROUP_ROOT%>/of/board/insertBoard.do";
					frm.submit();
				} else {
					if($("#fileselect").val() != ""){
						fileUploadByIframe('frm', '/hanagw/imgMultiFileUploadAction.do', 'fileselect[]', 'fileUploadByIframeCallback');
					} else {
						var $frm = $('#frm');
						$frm.attr('action','<%=GROUP_ROOT%>/of/board/insertBoard.do');
						$frm.removeAttr('target');
						$frm.removeAttr('enctype');
						$frm[0].submit();
					}
					
				}
				subtmit=true;
			}
		}else{
			alert("저장중입니다.");
		}
	}
	
	/**
	 * ex8파일업로드iframe
	 * @param formId
	 * @param url
	 * @param fileParamName
	 * @param callbackFuncName
	 * @returns
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
	 * ex8파일업로드iframe callback함수
	 * @param fileNum
	 * @returns
	 */
	function fileUploadByIframeCallback(fileNum){
		$('#fileNum').val($('#fileNum').val()+fileNum+",");
		frm.action="<%=GROUP_ROOT%>/of/board/insertBoard.do";
		frm.target='';
		frm.submit();
	}
	
	/**
	 * 로딩바
	 * @returns
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
	 * 목록으로 돌아가기
	 * @returns
	 */
	function goList(){
		var frm = document.frm;
		frm.action="<%=GROUP_ROOT%>/of/board/boardList.do";
		frm.submit();
	}
	
	/**
	 * 대상가져오는 팝업
	 * @returns
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
			loadUrl:'<%=GROUP_ROOT%>/pe/member/memberSelectPopup.do?type=BOARD',
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
	 * @returns
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
			   	objCel1.innerHTML=memberListValueArr[1] + "<button type='button' class='type_01 ml35' name=cmdDelete onClick='removeRow(this);'>삭제</button>";
			   	objRow.appendChild(objCel1);
		    }
	    } 
		$('#getMember').bPopup().close();
		tableScroll();
	}
	
	/**
	 * 레이어 팝업 닫기
	 * @returns
	 */
	function layerClose(){ 
		$('#getMember').bPopup().close();
	}
	
	/**
	 * 대상자 삭제
	 * @param r
	 * @returns
	 */
	function removeRow(r){ 
	 	var i=r.parentNode.parentNode.rowIndex;
	 	document.getElementById('tbl').deleteRow(i);
	}
</script>
</head>
<body>
<div id="wrap">
<%@include file="/include/header.jsp"%>
<%@include file="/include/gnb.jsp"%>
	<div class="wrap_con">
		<div class="content">
		<%@include file="/include/location.jsp"%>
		<%@include file="/include/lnb.jsp"%>
			<div class="cont float_left">
				<h2>
				<% if(codeList.size()!=0){
						for(int i=0;i<codeList.size();i++){
							CodeVO codeVO = new CodeVO();
							codeVO = codeList.get(i);
							if(cd.equals(codeVO.getCd())){%>
							<%=codeVO.getCd_nm()%>
							<%
							}
						}
					}
					%>
				</h2>
				<div class="noticeboard_box">
				<form id="frm" name="frm" method="post">
					<input type="hidden" name="uploadUrl" id="uploadEditorUrl" value="<%=GROUP_ROOT%>/imgEditorFileUpload.do" />
					<!--	파일첨부기능시 이름 변경하면 안됨 -->
					<input type="hidden" id="m_cd" name="m_cd" value="<%=m_cd%>"/>
					<input type="hidden" id="filePathKind" name="filePathKind" value="board"/>
					<!--	//파일첨부기능시 이름 변경하면 안됨 -->
					<input type="hidden" id="reply_grp" name="reply_grp" value="<%=reply_grp %>"/>
					<input type="hidden" id="reply_level" name="reply_level" value="<%=reply_level %>"/>
					<input type="hidden" id="boardType" name="boardType" value="<%=cd %>"/>
					<input type="hidden" id="cd" name="cd" value="<%=cd %>"/>
					<div class="list_btn">
						<div class="list_button">
							<button type="button" class="type_01" onclick="javascript:saveBoard();">저장</button>
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
								<th class="bt_none bc_re br_none" colspan="4">
								<% if(codeList.size()!=0){
										for(int i=0;i<codeList.size();i++){
											CodeVO codeVO = new CodeVO();
											codeVO = codeList.get(i);
											if(cd.equals(codeVO.getCd())){%>
											<%=codeVO.getCd_nm()%>
											<%
											}
										}
									}
									%>
								</th>
							</tr>
							<tr>
								<th>제목</th>
								<td colspan="3" class="txt_field br_none"><input type="text" id="subject" name="subject"/></td>
							</tr>
							<tr>
								<th>게시기간</th>
								<td colspan="3" class="date_search br_none">
									<div class="date_position">
										<input type="text" name="start_ymd" id="start_ymd" maxlength="10" size="10" value="<%= start_ymd %>" readonly="readonly" />
										<button type="button" class="btn_date" >날짜찾기</button>
									</div>
									~
									<div class="date_position">
										<input type="text" name="end_ymd" id="end_ymd" maxlength="10" size="10" value="<%= end_ymd %>" readonly="readonly" />
										<button type="button" class="btn_date" id="btn_date">날짜찾기</button>	
									</div>							
								</td>
							</tr>
							<tr>
								<td colspan="4" class="txt_box01 br_none">
									<textarea id="contents" name="contents"></textarea>
								</td>
							</tr>
							<tr>
								<th class="append_tit">첨부파일</th>
								<td colspan="3" class="append_file br_none">
									<div class="append_search">
										<input type="file" id="fileselect" name="fileselect[]" multiple="multiple" />
										<input type="hidden" name="fileNum" id="fileNum" value=""/>
										<!-- <button>찾아보기</button> -->
										<div id="filedrag">마우스로 파일을 끌어 놓으세요.</div>
									</div>
									<!-- <div class="append_list">
										<span>aaa.jpg</span>
										<a href=""></a>
									</div>-->
									<div class="append_list" id="messages">
									</div>
								</td>
							</tr>																								
						</tbody>
					</table>
					</div>
					<div class="append_object_box">
						<button type="button" class="type_01" onclick="javascript:getMember(); return false;">게시 대상 추가</button>
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
										<td colspan="3" class="br_none">
											<a href="#" onclick="javascript:getMember();">대상을 추가 하세요.</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
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
</div>
</body>