<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1approvalPopup.jsp
 * @메뉴명 : step1신규문서작성
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ include file ="/common/path.jsp" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.AccidentVO" %>

<%
	/* 첨부파일 */
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList");
	/* 문서 기본정보 */
	DocumentInfoVO documentInfoDetail = (DocumentInfoVO)request.getAttribute("documentInfoDetail");
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	
	String make_dt = StringUtil.nvl((String)request.getAttribute("make_dt")); //작성일시
	String dept_ko_nm = StringUtil.nvl((String)request.getAttribute("dept_ko_nm")); //작성부서
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm")); //작성자
	String docu_cd = StringUtil.nvl((String)request.getAttribute("docu_cd")); //문서코드
	String docu_nm = StringUtil.nvl((String)request.getAttribute("docu_nm")); //문서이름
	String docu_seq = StringUtil.nvl((String)request.getAttribute("docu_seq")); //문서번호
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq")); //결재번호
	String security_yn = StringUtil.nvl(documentInfoDetail.getSecurity_yn(),"N"); //대외비여부
	String decide_yn = StringUtil.nvl(documentInfoDetail.getDecide_yn(),"N"); //전결가능
	String includePage = "step1"+docu_cd+".jsp";
	
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
	String str = "";
	if("".equals(approval_seq)){
		str = "저장";
	}else{
		str = "수정";
	}
	
	/*사고보고서  보고 날짜 셋팅*/
	AccidentVO accidentVO = (AccidentVO)request.getAttribute("accidentVO");
	if(accidentVO == null){
		accidentVO = new AccidentVO();
	}
	String bogo_day = StringUtil.nvl(accidentVO.getBogo_day());		//보고일
	String bogo_month = StringUtil.nvl(accidentVO.getBogo_month()); //보고월
	String bogo_year = StringUtil.nvl(accidentVO.getBogo_year());	//보고년
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/styles.css" />
	<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/fileDrag.js"></script>
	<script type="text/javascript">
	
	var subtmit=false;		//submit flag
	
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
		var delFileSeq = $('#delFileSeq').val();
	
		if(delFileSeq==''){
			 $('#delFileSeq').val(seq);
		}else{
			$('#delFileSeq').val(delFileSeq+','+seq);
		}
		$('#file_'+seq).remove();
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
	
	/**
	 * step1 저장후 step2로 이동 
	 */
	function goStep2(){
		if(saveStep1() && subtmit==false){
			if(confirm("<%=str%> 하시겠습니까? STEP2로 넘어갑니다.") == true){
				if (window.File && window.FileList && window.FileReader) {
					var fileCount = fileUploadCnt();
					if(fileCount > 0) {
						loadingBar();
						uploadNext();
					}
					$('#pro').bPopup().close();
					document.frm.action='<%=GROUP_ROOT%>/ea/newReport/insertDocument.do';
					document.frm.submit();
				}else{
					if($("#fileselect").val() != ""){
						fileUploadByIframe('frm', '/hanagw/approvalMultiFileUploadAction.do', 'fileselect[]', 'fileUploadByIframeCallback');
					} else {
						var $frm = $('#frm');
						$frm.attr('action','<%=GROUP_ROOT%>/ea/newReport/insertDocument.do');
						$frm.removeAttr('target');
						$frm.removeAttr('enctype');
						$frm[0].submit();
					}
				}
				
				subtmit=true;
				
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
	 * 레이어 팝업 닫기
	 * @param id
	 */
	function layerClose(id){ 
		var layerID = "#"+id;
		$(layerID).bPopup().close();
	}
	
	/**
	 * 파일업로드
	 */
	function uploadNext() {
		for (var int = 0; int < file_list.length; int++) {
			var nextFile = file_list[int];
			multiUploadFile(nextFile);	//ajax 파일업로드
		}
	}
	
	/**
	 * 파일업로드
	 * @param file
	 */
	function multiUploadFile(file) {
		
		var fd = new FormData(); // https://hacks.mozilla.org/2011/01/how-to-develop-a-html5-image-uploader/
		fd.append("image", file);
		fd.append("approval_seq", $('#approval_seq').val());
		fd.append("filePathKind", $('#filePathKind').val());
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/hanagw/approvalMultiFileUploadAction.do", false);
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
			<h3>신규문서작성<span> STEP1</span></h3>
			<button type="button" onclick="window.close();" class="close"><span class="blind">닫기</span></button>
			<div>
				<div class="fr">
					<button class="type2" type="button" onclick="javascript:goStep2();">결재라인지정</button>
				</div>
			</div>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"><em>STEP 1</em>. 문서내용작성</span>
					<p>해당 문서 내용을 작성하고 완료가 되면 결재라인 지정 버튼을 클릭하시기 바랍니다. STEP2로 이동합니다. </p>
				</div>
				<form id="frm" name="frm" method="post">
				<!--	파일첨부기능시 이름 변경하면 안됨 -->
				<input type="hidden" id="delFileSeq" name="delFileSeq" />
				<input type="hidden" id="filePathKind" name="filePathKind" value="<%=docu_cd %>"/>
				<!--	//파일첨부기능시 이름 변경하면 안됨 -->
				<input type="hidden" id="docu_cd" name="docu_cd" value="<%=docu_cd %>" />
				<input type="hidden" id="docu_seq" name="docu_seq" value="<%=docu_seq %>" />
				<input type="hidden" id="decide_yn" name="decide_yn" value="<%=decide_yn %>" />
				<input type="hidden" id="make_dt" name="make_dt" value="<%=make_dt %>" />
				<input type="hidden" id="approval_seq" name="approval_seq" value="<%=approval_seq %>" />
				
				<h4>
					<%if("Y".equals(security_yn)){ %>
					<span class="check">
						<input type="checkbox" name="security_yn" id="security_yn" <%if("Y".equals(approvalMasterVO.getSecurity_yn())){%>checked<%}%> />
						<label for="security_yn">대외비입니다.</label>
					</span>
					<%} %>
					<strong><%=documentInfoDetail.getDocu_nm() %></strong>
					<%if("Y".equals(decide_yn)){ %>
					<span>전결 가능합니다.</span>
					<%} %>
				</h4>
				<table>
					<colgroup>
						<col class="cb_col1">
						<col style="width:260px">
						<col style="width:86px">
						<col style="width:260px">
					</colgroup>
					<tbody>
						<tr>
							<th>문서번호</th>
							<td><%if(!"".equals(approval_seq)){%><%=approval_seq%><%}else{%><%=docu_cd %><%=make_dt.replace("-","").substring(0,8)%>XXX<%}%></td>
							
							<th>작성일자</th>
							<td><%=make_dt.substring(0, 16) %></td>
						</tr>
						<tr>
							<th>작성부서</th>
							<td><%=dept_ko_nm %></td>
							<th>작성자</th>
							<td><%=emp_ko_nm %></td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3" class="subject"><input type="text" name="subject" id="subject" value="<%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %>" 
								<%if("E01004".equals(docu_cd)){%>readonly="readonly"<%}%>/>
							</td>
						</tr>
						<tr>
							<td colspan="4" class="pdn">
								<jsp:include page="<%=includePage%>"/>
							</td>
						</tr>
						<tr>
							<th class="append_tit">
								첨부파일
								<%if("E01011".equals(docu_cd)){ %>
								<span class="ref">※ 첨부서류 :<br />&nbsp;&nbsp;&nbsp;거래처카드</span>
								<%} %>
							</th>
							<td colspan="7" class="append_file br_none">
								<div class="append_search">
									<input type="file" id="fileselect" name="fileselect[]" multiple="multiple" />
									<input type="hidden" name="fileNum" id="fileNum" />
									<div id="filedrag">마우스로 파일을 끌어 놓으세요.</div>
								</div>
								<div id="messages">
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
				<%if("E01011".equals(docu_cd)){ %>
				<div class="box_accident_report">
					<input type="text" name="bogo_year" id="bogo_year" class="ipt_year" value="<%=bogo_year%>" />년 <input type="text" name="bogo_month" id="bogo_month" class="ipt_month" value="<%=bogo_month%>" />월 <input type="text" name="bogo_day" id="bogo_day" class="ipt_day" value="<%=bogo_day%>" />일
					<p>상기와 같이 보고합니다.</p>
				</div>
				<%} %>
				</form>
			</div>
		</div>
		<div id='pro' style='display:none; width:auto; height:auto; '>
			<img alt='loading' src='/hanagw/asset/img/ajax-loader.gif' />
		</div>
		<div id='getMember' style='display:none; width:auto; height:auto; '>
			<div class='member_content'></div> 
		</div>
		<div id='pop_memberSearch' style='display:none; width:auto; height:auto; '>
			<div class='member_content'></div> 
		</div>
	</div>	
</div>
</body>
</html>