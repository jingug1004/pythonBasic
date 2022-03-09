<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : boardList.jsp
 * @메뉴명 : 게시판리스트
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>   
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %> 
<%@ page import="com.hanaph.gw.of.board.vo.BoardVO" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>

<%
	List<BoardVO> boardList = (List<BoardVO>)request.getAttribute("boardList"); //게시판리스트
	List<CodeVO> codeList = (List<CodeVO>)request.getAttribute("codeList"); //게시판종류
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList"); //삭제권한을 가진 직원
	
	String emp_no = StringUtil.nvl((String)request.getAttribute("emp_no")); //현재 로그인한 직원번호
	String cd = StringUtil.nvl((String)request.getAttribute("cd"),"O01001"); //게시판 종류
	String search_start_ymd = StringUtil.nvl((String)request.getAttribute("search_start_ymd")); //게시시작기간
	String search_end_ymd = StringUtil.nvl((String)request.getAttribute("search_end_ymd")); //게시마지막기간
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"subject"); //검색타입
	String searchKeyword = StringUtil.nvl((String)request.getAttribute("searchKeyword")); //검색키워드
	int totalCnt = (Integer)request.getAttribute("totalCnt"); //게시글 총 카운트
	
	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));

%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>게시판</title>
<%@include file="/include/head.jsp"%>
<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/formCheck.js" ></script>	
<script type="text/javascript">
	
	$(window).load(function() {
		$("#searchType > option[value=<%=searchType%>]").attr("selected","true");
		$("#cd > option[value=<%=cd%>]").attr("selected","true");
	});
	
	/**
	 * 검색
	 */
	function goSearch(){
		if(formCheck.containsChars(document.getElementById("searchKeyword").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		
		if(formCheck.dateChk($("#search_start_ymd").val(),$("#search_end_ymd").val())<0){
			alert("공지기간 시작 날짜가 공지기간 종료 날짜 보다 이후 입니다.");
			start_ymd.focus();
			return
		}
		
		document.searchForm.action="<%=GROUP_ROOT%>/of/board/boardList.do";;
		document.searchForm.submit();
	}
	
	/**
	 * 상세보기 page 이동
	 * @param seq
	 */
	function boardDetail(seq){
		document.frm.seq.value = seq;
		document.frm.action="<%=GROUP_ROOT%>/of/board/boardDetail.do";
		document.frm.submit();
	}
	
	/**
	 * 게시판 삭제
	 */
	function deleteBoard(){
		var seq = "";
		
		$("input[name='delete_seq']:checkbox:checked").each(function(){
			seq += $(this).val()+"|";
		});
		
		if($("input[name='delete_seq']:checkbox:checked").length < 1){
			alert("삭제할 대상을 선택해 주세요.");
			return;
		}
		
		if(confirm("삭제 하시겠습니까?") == true){
			document.frm.seq.value = seq;
			document.frm.action = "<%=GROUP_ROOT%>/of/board/deleteBoard.do";
			document.frm.submit();
		}
	}
	
	/**
	 * 게시판 등록 폼으로 이동
	 */
	function insertForm(){
		document.frm.action="<%=GROUP_ROOT%>/of/board/insertBoardForm.do";
		document.frm.submit();
	}
	
	/**
	 * 게시글 읽은 임직원 레이어 팝업
	 * @param board_seq
	 */
	function boardReadData(board_seq){
		$('#boardReadData').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="550px" height="295px"',
			follow: [true, true],
			contentContainer:'.board_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
            loadUrl:'<%=GROUP_ROOT%>/of/common/commonReadData.do?seq=' + board_seq + '&type=BOARD',
		});
	}
	
	/**
	 * 레이어 팝업 닫기
	 */
	function layerClose(){ 
		$('#boardReadData').bPopup().close();
	}
	
	/**
	 * 체크박스 전체 선택
	 */
	function allCheck2(){
		$("input[name=allCheck]:checkbox").each(function() {
    		if($(this).is(':checked')) {
                $("input[name=delete_seq]").prop("checked", true);
            } else {
                $("input[name=delete_seq]").prop("checked", false);
            }
		});
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
				<%
					if(codeList.size()!=0){
							for(int i=0;i<codeList.size();i++){
								CodeVO codeVO = new CodeVO();
								codeVO = codeList.get(i);
								if(cd.equals(codeVO.getCd())){
				%>
						<%=codeVO.getCd_nm()%>
				<%
					}
							}
						}
				%>
				</h2>
				<div class="noticeboard_box">
					<form id="searchForm" name="searchForm" method="post">
					<div class="serch_box">
						<ul class="serch_con01">
							<li>
								<span class="sc_txt">게시기간</span>
								<span class="serch_date_box">
									<input type="text" class="serch_date" name="search_start_ymd" id="search_start_ymd" value="<%=search_start_ymd%>" readonly="readonly"/>
									<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
								</span> ~ 
								<span class="serch_date_box">
									<input type="text" class="serch_date" name="search_end_ymd" id="search_end_ymd" value="<%=search_end_ymd%>" readonly="readonly"/>
									<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
								</span>
								<span class="sc_txt">게시판종류</span>
								<select class="serch_select01" id="cd" name="cd">
								<%
								if(codeList.size()!=0){
									for(int i=0;i<codeList.size();i++){
										CodeVO codeVO = new CodeVO();
										codeVO = codeList.get(i);
										if("Y".equals(codeVO.getUse_yn())){
								%>
												<option value="<%=codeVO.getCd()%>" <%=cd.equals(codeVO.getCd())? "selected": ""%>  ><%=codeVO.getCd_nm()%></option>
								<%
										}
									}
								}
								%>
								</select>
							</li>
							<li class="cont02">
								<span class="sc_txt">게시물 검색</span>
								<select class="serch_select02" id="searchType" name="searchType">
									<option value="subject">제목</option>
									<option value="create_no">작성자</option>
								</select>
								<input type="text" class="serch_txt" id="searchKeyword" value="<%=searchKeyword%>" maxlength="32" name="searchKeyword" value="<%=searchKeyword%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" />
							</li>
						</ul>
						<span class="serch_btn" onclick="javascript:goSearch();"><button type="button">검색</button></span>
					</div>
					</form>
					<div class="list_btn">
						<div class="list_button">
							<button type="button" class="type_01" onclick="javascript:insertForm();">신규</button>
							<%
							if(authorityMemberList != null){
								for(int i=0; authorityMemberList.size()>i;i++){
									MemberVO memberVO = new MemberVO();
									memberVO = authorityMemberList.get(i);
									if(emp_no.equals(memberVO.getEmp_no())){
							%>
							<button type="button" class="type_01" onclick="javascript:deleteBoard();">삭제</button>
							<%
									}
								}
							}
							%>
						</div>
						<span class="list_t">* 안 읽은 글은 제목이 <span class="none_check">파란색</span>으로 표시됩니다.</span>
					</div>
					<div class="cont_box cont_table05">
						<form id="frm" name="frm" method="post">
							<input type="hidden" id="search_start_ymd" name="search_start_ymd" value="<%=search_start_ymd %>"/>
							<input type="hidden" id="search_end_ymd" name="search_end_ymd" value="<%=search_end_ymd %>"/>
							<input type="hidden" id="searchType" name="searchType" value="<%=searchType %>"/>
							<input type="hidden" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword %>"/>
							<input type="hidden" id="cd" name="cd" value="<%=cd %>"/>
							<input type="hidden" id="seq" name="seq" />
							<table>
							<colgroup>
								<col width="5%" />
								<col width="50%" />
								<col width="20%" />
								<col width="10%" />
								<col width="8%" />
								<col width="7%" />
							</colgroup>
								<tbody>
									<tr>
										<th><input type="checkbox" id="allCheck" name="allCheck" onclick="javascript:allCheck2();"/></th>
										<th>제목(댓글수)</th>
										<th>게시일</th>
										<th>게시자</th>
										<th>읽은수</th>
										<th class="br_none">첨부</th>										
									</tr>
									<%
										if(boardList.size()!=0){
											for(int i=0; boardList.size()>i;i++){
												BoardVO boardVO = new BoardVO();
												boardVO=boardList.get(i);
									%>
									<tr>
										<td><input type="checkbox" name="delete_seq" id="delete_seq" value="<%=boardVO.getSeq()%>"/></td>
										<td onclick="javascript:boardDetail('<%=boardVO.getSeq()%>')" class="list_txt">
											<div <%if(!"Y".equals(boardVO.getRead_yn())){%>class="none_check"<%}%> style="word-wrap:break-word; white-space:normal; display:inline-block; width:350px;">
												<%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", boardVO.getSubject())) %>
												<span class="count">(<%=StringUtil.nvl(boardVO.getComment_cnt(),"0")%>)</span>
											</div>
										</td>
										<td><%=boardVO.getCreate_dt()%></td>
										<td onclick="javascript:Commons.memberPopup('<%=boardVO.getCreate_no()%>');"><%=boardVO.getEmp_ko_nm()%></td>
										<td onclick="javascript:boardReadData('<%=boardVO.getSeq()%>')"><%=boardVO.getRead_cnt()%></td>
										<td class="br_none">
										<%
											if(boardVO.getAttach_cnt() > 0){
										%>
											<img src="<%=GROUP_WEB_ROOT%>/img/img_file.png" alt="파일첨부" /></td>										
										<%
											}
										%>
									</tr>	
									<%
											}
									}else{
										%>	
									<tr><td class="br_none" colspan="6">게시물이 없습니다.</td></tr>
									<%
									}
									%>		
								</tbody>
							</table>
						</form>
					</div>
					<div class="paging">
						<div class="wrap_total">
							* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(totalCnt)%></span>건
						</div>
						<div class="wrap_paging">
							<%@ include file ="/common/paging.jsp" %>
						</div>
					</div>				
				</div>
			</div>
		</div>
	</div>
	<div id='boardReadData' style='display:none; width:auto; height:auto; '>
		<div class='board_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</div>
</body>