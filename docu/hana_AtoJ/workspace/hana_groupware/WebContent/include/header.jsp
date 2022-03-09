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
<%--<script type="text/javascript">--%>
    <%--function logOut() {--%>
        <%--if (confirm("로그아웃 하시겠습니까?")) {--%>
            <%--location.href = "<%=GROUP_ROOT%>/co/login/logOut.do";  // 이동할주소--%>

        <%--}--%>
	<%--}--%>
<%--</script>--%>

<div class="wrap_header">
	<div class="header">
		<h1><a href="#" onclick="location.href='<%=GROUP_ROOT%>/main/main.do'"><img src="<%=GROUP_WEB_ROOT%>/img/header_logo.jpg" alt="하나제약 그룹웨어" /></a></h1>
		<div class="wrap_menu">
			<span class="connect"><%=headerMemberVO.getEmp_ko_nm() %></span>
			<ul class="menu">

				<li class="first"><a href="#" onclick="location.href='<%=GROUP_ROOT%>/pe/member/memberDetail.do'">내정보</a></li>
				<li><a href="#" onclick="javascript:Commons.popupOpen('passChg', '<%=GROUP_ROOT%>/co/login/passwordChangeForm.do', '290', '239'); return false;">비밀번호변경</a></li>
				<li><a href="#" onclick="javascript:logOut(); return false;">로그아웃</a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/of/notice/noticeList.do?search_read_yn=N'">공지 <%=headerMemberVO.getNoticeCountNoread() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/of/message/messageList.do?type=1&search_read_yn=N'">받은쪽지 <%=headerMemberVO.getMessageReceiveCount() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/receive/receiveList.do?search_appr_state=E03001'">문서결재 <%=headerMemberVO.getReceiveCount() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/implement/implementList.do?approval_type=E03001&search_read_yn=N'">시행결재중 <%=headerMemberVO.getImplementOngoingCount() %></a></li>
				<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/implement/implementList.do?approval_type=E03002&search_read_yn=N'">시행완료 <%=headerMemberVO.getImplementCompleteCount() %></a></li>

			</ul>
			<!-- 20150609 웹메일 클릭 시 로그인 되지 않도록 수정 -->
			<!-- 원복 시 해당 주석 삭제 -->
			<%-- <a href="http://gwmail.hanaph.co.kr/index.php?_task=login&_action=login&_user=<%=headerMemberVO.getE_mail() %>&_pass=<%=headerMemberVO.getPass_word() %>" target="_blank" class="webmail" >WEB MAIL</a> --%>
			<a href="http://gwmail.hanaph.co.kr/index.php?_user=<%=headerMemberVO.getE_mail() %>" target="_blank" class="webmail" >WEB MAIL</a>
		</div>
	</div>
</div>

<form id="formCheckMessageCnt" name="formCheckMessageCnt" method="post">
	<input type="hidden" name="checkMessageCnt" id="checkMessageCnt" value="<%=headerMemberVO.getMessageReceiveCount() %>">
</form>

<script>

	/* ml180125.ml01_T78 김진국 - header.jsp에 전 쪽지 수보다 현 쪽지수가 더 많으면 팝업 알림 - '시의필' 실행하면 실시간으로 쪽지수가 증가되어 확인 바라는 알림창 띄우기 위해서! Start */
    var beforeMessageCnt  =  <%=headerMemberVO.getBeforeMessageReceiveCount() %>;
    var currentMessageCnt  =  <%=headerMemberVO.getMessageReceiveCount() %>;

    if (currentMessageCnt > beforeMessageCnt && beforeMessageCnt != 0) {
        alert("지금 새 쪽지가 도착하였습니다.")
	}

    function OnLoadFocus() {
		var tempUrl = "<%=GROUP_ROOT%>/pe/member/beforeMessageReceiveCount.do";
		$.ajax({
			type: "POST",
			url: tempUrl,
			async: false,
			data: $('#formCheckMessageCnt').serialize(),
			success: function () {
            },
			error: function () {
            }
		});
    }
    /* ml180125.ml01_T78 김진국 - header.jsp에 전 쪽지 수보다 현 쪽지수가 더 많으면 팝업 알림 - '시의필' 실행하면 실시간으로 쪽지수가 증가되어 확인 바라는 알림창 띄우기 위해서! End */

    $(document).ready(function () {
        OnLoadFocus();
    });


    function logOut() {
        if (confirm("로그아웃 하시겠습니까?")) {
            location.href = "<%=GROUP_ROOT%>/co/login/logOut.do";  // 이동할주소
        }
    }

</script>