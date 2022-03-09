<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%
	HttpSession headerSession = request.getSession(false);	
	MemberVO headerMemberVO = (MemberVO) headerSession.getAttribute("gwUser");
%>
<%
	String user_id ="";
	String pass="";
%>
<script type="text/javascript">
	function logOut(){
		if(confirm("로그아웃 하시겠습니까?")){
			 location.href = "<%=GROUP_ROOT%>/co/login/logOut.do";  // 이동할주소

		}
	}
</script>

<div class="wrap_header">
	<div class="header">
		<h1><a href="#" onclick="location.href='<%=GROUP_ROOT%>/main/main.do'"><img src="<%=GROUP_WEB_ROOT%>/img/header_logo.jpg" alt="하나제약 그룹웨어" /></a></h1>
		<div class="wrap_menu">
			<span class="connect"><%=headerMemberVO.getEmp_ko_nm() %></span>
			<ul class="menu">
				<li class="first"><a href="#" onclick="location.href='<%=GROUP_ROOT%>/pe/member/memberDetail.do'">내정보</a></li>
				<li><a href="#" onclick="javascript:Commons.popupOpen('passChg', '<%=GROUP_ROOT%>/co/login/passwordChangeForm.do', '290', '239'); return false;">비밀번호변경</a></li>
				<li><a href="#" onclick="javascript:logOut(); return false;">로그아웃</a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/of/notice/noticeList.do?search_start_ymd=2015-01-01&search_read_yn=N'">공지 <%=headerMemberVO.getNoticeCountNoread() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/of/message/messageList.do?start_ymd=2015-01-01&type=1&search_read_yn=N'">받은쪽지 <%=headerMemberVO.getMessageReceiveCount() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/receive/receiveList.do?search_start_ymd=2015-01-01&search_appr_state=E03001'">문서결재 <%=headerMemberVO.getReceiveCount() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/implement/implementList.do?search_start_dt=2015-01-01&approval_type=E03001&search_read_yn=N'">시행결재중 <%=headerMemberVO.getImplementOngoingCount() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/implement/implementList.do?search_start_dt=2015-01-01&approval_type=E03002&search_read_yn=N'">시행완료 <%=headerMemberVO.getImplementCompleteCount() %></a></li>
			</ul>
			<!-- 20150609 웹메일 클릭 시 로그인 되지 않도록 수정 -->
			<!-- 원복 시 해당 주석 삭제 -->
			<%-- <a href="http://gwmail.hanaph.co.kr/index.php?_task=login&_action=login&_user=<%=headerMemberVO.getE_mail() %>&_pass=<%=headerMemberVO.getPass_word() %>" target="_blank" class="webmail" >WEB MAIL</a> --%>
			<a href="http://gwmail.hanaph.co.kr/index.php?_user=<%=headerMemberVO.getE_mail() %>" target="_blank" class="webmail" >WEB MAIL</a>
		</div>
	</div>
</div>