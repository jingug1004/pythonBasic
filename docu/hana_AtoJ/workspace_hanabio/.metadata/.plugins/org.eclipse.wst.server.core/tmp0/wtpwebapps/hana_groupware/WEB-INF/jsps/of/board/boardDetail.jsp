<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : boardDetail.jsp
 * @메뉴명 : 게시판상세정보
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.of.board.vo.BoardVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.of.board.vo.BoardCommentVO" %>

<%@ include file ="/common/path.jsp" %>
<%
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList"); //첨부파일 리스트
	BoardVO boardDetail = (BoardVO)request.getAttribute("boardDetail"); //게시글 상세정보
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList"); //수정,삭제 권한을 가진 임직원
	
	String emp_no = (String)request.getAttribute("emp_no");	//현재 사용중인 직원번호
	int currentPage = (Integer)request.getAttribute("currentPage"); //현재 페이지
	
	String cd = StringUtil.nvl((String)request.getAttribute("cd"),"O02001"); //게시판종류
	String search_start_ymd = StringUtil.nvl((String)request.getAttribute("search_start_ymd")); //게시기간
	String search_end_ymd = StringUtil.nvl((String)request.getAttribute("search_end_ymd")); //게시기간
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"subject"); //검색타입
	String searchKeyword = StringUtil.nvl((String)request.getAttribute("searchKeyword"),""); //검색키워드
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/formCheck.js" ></script>	
<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/styles.css" />
<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/fileDrag.js"></script>
<script type="text/javascript">

	/*현재 등록된 댓글 가져옴*/
	$(document).ready(function(){
		ajaxSelectComment();
	});
	
	/**
	 * 게시대상보기 레이어 팝업
	 */
	function getMember(){
		$('#getMember').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="350px" height="295px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/pe/member/memberTargetListPopup.do?seq=<%=boardDetail.getSeq()%>&type=BOARD',
		});
	}
	
	/**
	 * 레이어 팝업 닫기
	 */
	function layerClose(){ 
		$('#getMember').bPopup().close();
	}
	
	/**
	 * 게시판 목록으로 이동
	 */
	function goList(){
		var frm = document.frm;
		frm.action="<%=GROUP_ROOT%>/of/board/boardList.do";
		frm.submit();
	}
	
	/**
	 * 게시판 수정폼으로 이동
	 */
	function updateForm(){
		var frm = document.frm;
		frm.action="<%=GROUP_ROOT%>/of/board/boardUpdateForm.do";
		frm.submit();
		
	}

	/**
	 * 게시글 삭제
	 */
	function deleteBoard(){
		if(confirm("게시글을 삭제하시겠습니까?")){
			var frm = document.frm;
			frm.action="<%=GROUP_ROOT%>/of/board/deleteBoard.do";
			frm.submit();
		}else{
			return;
		}
	}
	
	/**
	 * 댓글 삭제
	 * @param commentSeq
	 */
	function deleteComment(commentSeq){
		if(confirm("삭제 하시겠습니까?") == true){
			$.ajax({
				type:"POST",
				url:"<%=GROUP_ROOT%>/of/board/deleteComment.do",
				data:{
					seq:<%=boardDetail.getSeq()%>,
					comment_seq:commentSeq
					},
				dataType:"json",
				success:function(data){
					if(data.result==1){
						alert("댓글이 삭제 되었습니다.");
						ajaxSelectComment();
						$('#comments').val('');
						$('#comment_seq').val('');
					}
					
				},
				error : function(xhr, status, error) {
					alert("댓글 ajax에러");
					console.log(error);
				}
			});
		}			
	}
	
	var subtmit=false;		//submit flag
	
	/**
	 * 댓글 등록 
	 */
	function commentWrite(){
		var comments = $('#comments');
		
		if (formCheck.getByteLength(comments.val()) > 140){
			alert("제목은 한글 70자 (영문 140자) 이상 입력할수 없습니다");
			return;
		}else if (formCheck.isNullStr(comments.val())){
			alert("댓글을 입력해 주세요");
			comments.focus();
			return;
		}
		if(subtmit==false){
			$.ajax({
				type:"POST",
				url:"<%=GROUP_ROOT%>/of/board/insertComment.do",
				data:{
					seq:$('#seq').val(),
					comments:$('#comments').val()
					},
				dataType:"json",
				success:function(data){
					if(data.result==1){
						alert("댓글이 등록 되었습니다.");
						ajaxSelectComment();
						$('#comments').val('');
						$('#comment_seq').val('');
					}
					
				},
				error : function(xhr, status, error) {
					alert("댓글 ajax에러");
					console.log(error);
				}
			});
			subtmit=true;
		}
	}
	
	/**
	 * 댓글 페이지
	 * @param currentPage
	 */
	function ajaxSelectComment(currentPage){
		$('#commentTbl').html('');
		
		$.ajax({
			type:"POST",
			url:"<%=GROUP_ROOT%>/of/board/selectComment.do",
			data:{ seq:$('#seq').val(),
				   currentPage:currentPage
				 },
			dataType:"json",
			success:function(data){
				var str='<colgroup><col width=\"10%\" /><col width=\"20%\" /><col width=\"65%\" /><col width=\"5%\" /></colgroup>';
				if(data.list!=null){
					for(var i=0; i<data.list.length;i++){
						if(i==0){
							str+='<tr class="first_list" id=\"comment_'+data.list[i].comment_seq+'\"><th>';
						}else{
							str+='<tr id=\"comment_'+data.list[i].comment_seq+'\"><th>';
						}
						str+=data.list[i].create_no;
						str+='</th><td class=\"date_txt\">';
						str+=data.list[i].create_dt;
						str+='</td><td class=\"tit_txt\">';
						str+=Commons.XSSfilter(data.list[i].comments).replace(/\n/g,'<br/>');
						str+='</td><td>';
						if(data.list[i].emp_no == '<%=emp_no%>'){
							str+='<a href=\"javascript:deleteComment('+data.list[i].comment_seq+');\"><img src=\"<%=GROUP_ROOT%>/asset/img/popup_btn_close01.gif\" alt=\"삭제\" /></a>';
						}
						str+='</td></tr>';
					}
					str = str.replace(/<br>/g, "\n");
				}
				$('#commentTbl').html(str);
				$('.wrap_total').html('* 총 건수 : <span class="cnt">'+formCheck.numberFormat(data.commentCnt)+'</span>건');
				$('.wrap_paging').html(data.pagingStr);
				var $form = $("#frm");
				if($form.length > 0) {
					$("<input></input>").attr({type:"hidden", name:"currentPage",id:"currentPage", value:$.trim(data.currentPage)}).appendTo($form);
				}
				
			},complete : function(data) {
				
			},error : function(xhr, status, error) {
				console.log(error);
				alert("댓글 load에 실패하였습니다.");
			}
		});
	}
	
	/**
	 * 댓글 현재 페에지번호 넘김
	 * @param currentPage
	 */
	function goPage(currentPage){
		ajaxSelectComment(currentPage);
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
				<h2><%=boardDetail.getCd_nm()%></h2>
				<div class="noticeboard_box">
				<form id="frm" name="frm" method="post" >
					<input type="hidden" id="seq" name="seq" value="<%=boardDetail.getSeq()%>"/>
					<input type="hidden" id="reply_grp" name="reply_grp" value="<%=boardDetail.getSeq()%>"/>
					<input type="hidden" id="reply_level" name="reply_level" value="<%=boardDetail.getReply_level()%>"/>
					<input type="hidden" id="cd" name="cd" value="<%=boardDetail.getCd()%>"/>
					<input type="hidden" id="comment_seq" name="comment_seq" />
					<!-- 검색파라미터 -->
					<input type="hidden" id="searchType" name="searchType" value="<%=searchType%>"/>
					<input type="hidden" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword%>"/>
					<input type="hidden" id="search_start_ymd" name="search_start_ymd" value="<%=search_start_ymd%>"/>
					<input type="hidden" id="search_end_ymd" name="search_end_ymd" value="<%=search_end_ymd%>"/>
					<input type="hidden" id="currentPage" name="currentPage" value="<%=currentPage%>"/>
					
					<div class="list_btn">
						<div class="list_button">
						<%
							if(emp_no.equals(boardDetail.getCreate_no())){
						%>
							<button class="type_01" onclick="javascript:updateForm();">수정</button>
							<button class="type_01" onclick="javascript:deleteBoard();">삭제</button>
						<%
							}else if(authorityMemberList != null){
								for(int i=0; authorityMemberList.size()>i;i++){
									MemberVO memberVO = new MemberVO();
									memberVO = authorityMemberList.get(i);
									if(emp_no.equals(memberVO.getEmp_no())){
						%>
							<button class="type_01" onclick="javascript:updateForm();">수정</button>
							<button class="type_01" onclick="javascript:deleteBoard();">삭제</button>						
						<%
									}
								}
							}
												%>
							<button class="type_01" onclick="javascript:goList();">목록</button>
							<button type="button" class="type_01 selectTarget" onclick="javascript:getMember();">게시대상보기</button>
						</div>
					</div>
					<div class="cont_box cont_table06">
						<table class="tlfixed"> <!-- 2015-02-24 수정 -->
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
								<tbody>
									<tr>
										<th class="bt_none">제목</th>
										<td colspan="3" class="bt_none"><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", boardDetail.getSubject()))%></td>
									</tr>
									<tr>
										<th>게시자</th>
										<td><%=boardDetail.getEmp_ko_nm()%></td>
										<th>조회수</th>
										<td><%=boardDetail.getRead_cnt()%></td>							
									</tr>
									<tr>
										<th>게시일</th>
										<td><%=boardDetail.getCreate_dt()%></td><!-- <span class="time_color">20:20:20</span></td> -->
										<th>게시기간</th>
										<td><span><%=boardDetail.getStart_ymd()%></span>~<span><%=com.hanaph.gw.co.common.utils.StringUtil.nvl(boardDetail.getEnd_ymd())%></span></td>							
									</tr>
									<tr>
										<td colspan="4" class="txt_box"><%=StringUtil.nvl(boardDetail.getContents())%></td>
									</tr>
									<tr>
										<th class="append_tit">첨부파일</th>
										<td colspan="3" class="append_file attachList">
										<%
											if(attachList!=null){
												for(int i=0; i<attachList.size(); i++){
													FileAttachVO fileAttachvo = new FileAttachVO();
													fileAttachvo = attachList.get(i);
										%>
											<p><a href="<%=GROUP_ROOT%>/fileDownload.do?file_seq=<%=fileAttachvo.getFile_seq()%>&filename=<%=fileAttachvo.getFile_nm()%>&filePath=<%=fileAttachvo.getFile_path()%>"><%=fileAttachvo.getOrigin_file_nm()%></a></p>
											<%
											}
										}
										
										if(attachList.size() > 1) {
										%>
											<p><button type="button" onclick="javascript:Commons.fileDownloadAll(0);">전체 다운로드</button></p>
										<%
										}
										%></td>
									</tr>																								
								</tbody>
						</table>
					</div>
					<div class="coment_box">
						<textarea class="coment" id="comments" name="comments"></textarea>
						<button type="button" class="btn_coment" onclick="javascript:commentWrite();">댓글입력</button>
					</div>
					<div class="coment_list_box">
						<table id="commentTbl" style="table-layout: fixed;"></table>
					</div>	
					<div class="paging">
						<div class="wrap_total"></div>
						<div class="wrap_paging"></div>
					</div>	
				</form>
				</div>
			</div>
		</div>
	</div>
	<div id='getMember' style='display:none; width:auto; height:auto; '>
		<div class='member_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>