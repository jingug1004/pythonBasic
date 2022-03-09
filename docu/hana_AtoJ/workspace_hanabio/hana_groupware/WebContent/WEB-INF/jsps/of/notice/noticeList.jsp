<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : noticeList.jsp
 * @메뉴명 : 공지사항 리스트
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ page import="com.hanaph.gw.of.notice.vo.NoticeVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%
	List<NoticeVO> noticeList = (List<NoticeVO>)request.getAttribute("noticeList");	//공지사항 리스트
	List<CodeVO> codeList = (List<CodeVO>)request.getAttribute("codeList");	//공지사항 구분
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList"); //삭제 권한을 가진 임직원
	
	String emp_no = StringUtil.nvl((String)request.getAttribute("emp_no")); //로그인한 임직원 번호
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색 키워드
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"subject"); //검색 타입
	String search_noti_kind = (String)request.getAttribute("search_noti_kind"); //검색 공지 구분
	String search_start_ymd = StringUtil.nvl((String)request.getAttribute("search_start_ymd")); //검색 공지 기간
	String search_end_ymd = StringUtil.nvl((String)request.getAttribute("search_end_ymd")); //검색 공지 기간
	int cnt = ((Integer)request.getAttribute("cnt")).intValue(); //공지사항 카운트
	String search_read_yn = StringUtil.nvl((String)request.getAttribute("search_read_yn")); //조회여부 날짜
	
	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		$(window).load(function() {
			$("#searchType > option[value=<%=searchType%>]").attr("selected","true");
			$("#search_noti_kind > option[value=<%=search_noti_kind%>]").attr("selected","true");
		});
	
		/**
		 * 검색
		 */
		function goSearch(){
			
			if(formCheck.dateChk($("#search_start_ymd").val(),$("#search_end_ymd").val())<0){
				alert("공지기간 시작 날짜가 공지기간 종료 날짜 보다 이후 입니다.");
				search_start_ymd.focus();
				return
			}
			
			if(formCheck.containsChars(document.getElementById("searchKeyword").value, "%")){
				 alert("특수문자를 사용할수 없습니다");
				 return;
			}
			
			document.searchForm.action = "<%=GROUP_ROOT%>/of/notice/noticeList.do";
			document.searchForm.submit();
		}
		
		/**
		 * 검색 상세 정보
		 * @param seq
		 */
		function noticeDetail(seq){
			document.frm.seq.value = seq;
			document.frm.action="<%=GROUP_ROOT%>/of/notice/noticeDetail.do";
			document.frm.submit();
		}
		
		/**
		 * 체크박스 전체 선택
		 */
		function allCheck2(){
			$("input[name=allCheck]:checkbox").each(function() {
	    		if($(this).is(':checked')) {
	                $("input[name=check]").prop("checked", true);
	            } else {
	                $("input[name=check]").prop("checked", false);
	            }
			});
		}
		
		/**
		 * 공지사항 삭제
		 */
		function deleteNotice(){
			var seq = "";
			$("input[name='check']:checkbox:checked").each(function(){
				seq += $(this).val()+"|";
			});
			
			if($("input[name='check']:checkbox:checked").length < 1){
				alert("삭제할 대상을 선택해 주세요.");
				return;
			}
			if(confirm("삭제 하시겠습니까?") == true){
				document.frm.seq.value = seq;
				document.frm.action = "<%=GROUP_ROOT%>/of/notice/deleteNotice.do";
				document.frm.submit();
			}
		}
				
		/**
		 * 공지사항 읽은 직원 조회 레이어 팝업
		 * @param notice_seq
		 */
		function noticeReadData(notice_seq){
			$('#noticeReadData').bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="no" frameborder="0" width="550px" height="295px"',
				follow: [true, true],
				contentContainer:'.notice_content',
				modalClose: true,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/of/common/commonReadData.do?seq=' + notice_seq + '&type=NOTICE',
			});
		}
		
		/**
		 * 레이어 팝업 닫기
		 */
		function layerClose(){ 
			$('#noticeReadData').bPopup().close();
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
					<div class="serch_box">
					<form id="searchForm" name="searchForm" method="post">
						<ul class="serch_con01">
							<li>
								<span class="sc_txt">공지기간</span>
								<span class="serch_date_box">
									<input type="text" class="serch_date" id="search_start_ymd" name="search_start_ymd" readonly="readonly" value="<%=search_start_ymd%>" />
									<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
								</span> ~ 
								<span class="serch_date_box">
									<input type="text" class="serch_date" id="search_end_ymd" name="search_end_ymd" readonly="readonly" value="<%=search_end_ymd%>" />
									<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
								</span>
								<span class="sc_txt">공지구분</span>
								<select class="serch_select01" id="search_noti_kind" name="search_noti_kind">
									<option value="all">전체</option>
									<%
									if(codeList.size()!=0){
										for(int i=0;i<codeList.size();i++){
											CodeVO codeVO = new CodeVO();
											codeVO = codeList.get(i);
											if("Y".equals(codeVO.getUse_yn())){
									%>
											<option value="<%=codeVO.getCd()%>"><%=codeVO.getCd_nm()%></option>
									<%
											}
										}
								   	}
									%>
								</select>
							</li>
							<li class="cont02">
								<span class="sc_txt">공지물 검색</span>
								<select class="serch_select02" id="searchType" name="searchType">
									<option value="subject">제목</option>
									<option value="createNo">작성자</option>
								</select>
								<input type="text" class="serch_txt" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword%>" maxlength="32" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" >
							</li>
						</ul>
						<span class="serch_btn" onclick="javascript:goSearch();"><button type="button">검색</button></span>
					</form>
					</div>
					<div class="list_btn">
						<div class="list_button">
							<button type="button" class="type_01" onclick="location.href='<%=GROUP_ROOT%>/of/notice/insertNoticeForm.do'">신규</button>
							<%
							if(authorityMemberList != null){
								for(int i=0; authorityMemberList.size()>i;i++){
									MemberVO memberVO = new MemberVO();
									memberVO = authorityMemberList.get(i);
									if(emp_no.equals(memberVO.getEmp_no())){
							%>
							<button type="button" class="type_01" onclick="javascript:deleteNotice();">삭제</button>
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
						<input type="hidden" id="searchType" name="searchType" value="<%=searchType %>"/>
						<input type="hidden" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword %>"/>
						<input type="hidden" id="search_start_ymd" name="search_start_ymd" value="<%=search_start_ymd %>" />
						<input type="hidden" id="search_end_ymd" name="search_end_ymd" value="<%=search_end_ymd %>"/>
						<input type="hidden" id="search_noti_kind" name="search_noti_kind" value="<%=search_noti_kind %>"/>
						<input type="hidden" id="search_read_yn" name="search_read_yn" value="<%=search_read_yn %>"/>
						<input type="hidden" id="seq" name="seq" />
						
						<table>
						<colgroup>
							<col width="5%" />
							<col width="36%" />
							<col width="24%" />
							<col width="10%" />
							<col width="10%" />
							<col width="8%" />
							<col width="7%" />
						</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" id="allCheck" name="allCheck" onclick="javascript:allCheck2();" /></th>
									<th>제목(댓글수)</th>
									<th>공지기간</th>
									<th>공지자</th>
									<th>공지구분</th>
									<th>읽은수</th>
									<th class="br_none">첨부</th>										
								</tr>
							</thead>
							<tbody>
							<%
							if(noticeList.size()!=0){
								for(int i=0; noticeList.size()>i; i++){
									NoticeVO noticeVO = new NoticeVO();
									noticeVO=noticeList.get(i);
							%>
								<tr>
									<td><input type="checkbox" id="check" name="check" value="<%=noticeVO.getSeq()%>" /></td>
									<td onclick="javascript:noticeDetail('<%=noticeVO.getSeq()%>');" class="list_txt">
										<div <%if(!"Y".equals(noticeVO.getRead_yn())){%>class="none_check"<%}%> style="word-wrap:break-word; white-space:normal; display:inline-block; width:240px;">
											<%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", noticeVO.getSubject())) %>
											<span class="count">(<%=noticeVO.getComment_cnt()%>)</span>
										</div>
									</td>
									<td>
										<span><%=StringUtil.nvl(noticeVO.getStart_ymd())%></span> ~ <span><%=StringUtil.nvl(noticeVO.getEnd_ymd())%></span>
									</td>
									<td onclick="javascript:Commons.memberPopup('<%=noticeVO.getCreate_no()%>');"><%=noticeVO.getEmp_ko_nm()%></td>
									<td><%=noticeVO.getNoti_kind()%></td>
									<td onclick="javascript:noticeReadData('<%=noticeVO.getSeq()%>');"><%=noticeVO.getRead_cnt()%></td>
									<td class="br_none">
									<%
										if(noticeVO.getAttach_cnt() > 0){
									%>
										<img src="<%=GROUP_WEB_ROOT%>/img/img_file.png" alt="파일첨부" />										
									<%
										}
									%>
									</td>
								</tr>
							<%
										}
							}else{
							%>
								<tr><td class="br_none" colspan="7">공지사항이 없습니다.</td></tr>
							<%
								}
							%>
							</tbody>
						</table>
						</form>
					</div>
					<div class="paging">
						<div class="wrap_total">
							* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(cnt)%></span>건
						</div>
						<div class="wrap_paging">
							<%@ include file ="/common/paging.jsp" %>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id='noticeReadData' style='display:none; width:auto; height:auto; '>
		<div class='notice_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>