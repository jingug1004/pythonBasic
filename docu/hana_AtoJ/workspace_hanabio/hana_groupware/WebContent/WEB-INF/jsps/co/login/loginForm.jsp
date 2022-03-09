<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : loginForm.jsp
 * @메뉴명 : 로그인 form
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
    <%@ include file ="/common/path.jsp" %>
<%
	//http:// 로 들어왔을 경우 https://로 redirect
	String url = request.getRequestURL().toString();

	//http:// 로 시작하고, 운영 url일 경우에만 redirect
	if( url.startsWith("http://") && ( url.indexOf("gw.") > 1) ) {
		response.sendRedirect(HTTPS_GROUP_ROOT + "/co/login/login.do");
	}

	String user_id = (String)request.getAttribute("user_id");
	String save_chk = (String)request.getAttribute("save_chk");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/headLoginForm.jsp"%>
	<script type="text/javascript" src="<%=HTTPS_GROUP_WEB_ROOT%>/js/formCheck.js" ></script>
	<script language="javascript" type="text/javascript">
	function doLogin(){
		
		if(formCheck.isNull(document.getElementById("user_id"), "ID를 입력해주세요.")){
			return;
		}
		
		if(formCheck.isNull(document.getElementById("user_pw"), "Password를 입력해주세요.")){
			return;
		}
		
		document.getElementById("frm").submit();
	}
	
	function checkExistsValue(id, msg){
		try {
			if(document.getElementById(id).value == ""){
				alert(msg);
				document.getElementById(id).focus();
				return false;
			} else {
				return true;
			}
		} catch(e) {}
	}
	
	function onFocusCursor(id){
		document.getElementById(id).focus();
	}
	</script>
</head>
<body>
	<div class="wrap_header">
		<div class="header">
			<h1><a href="#"><img src="<%=HTTPS_GROUP_ROOT%>/asset/img/header_logo.jpg" alt="" /></a></h1>
		</div>
	</div>
	<div class="wrap_con login_bg">
		<div class="wrap_login">
			<div class="login">
			<form name="frm" id="frm" method="post" onsubmit="return false;" action="<%=HTTPS_GROUP_ROOT %>/co/login/getLogin.do"> 
				<div class="inner">
					<p class="slogan">
						For the Best Medicine, Better Life
					</p>
					<h1>
						하나제약 주식회사 <span>그룹웨어</span>
					</h1>
					<p class="txt">
						하나제약 주식회사 그룹웨어입니다.<br />
						그룹웨어 시스템을 이용하시려면 로그인을 하시기 바랍니다.
					</p>
					<div class="wrap_log">
						<div class="wrap_input">
							<p class="line" onClick="onFocusCursor('user_id')">
								<label>아이디</label> <input type="text" name="user_id" id="user_id" value="<%=user_id %>" onKeyPress="if(event.keyCode=='13'){doLogin(); return false;}"/>
							</p>
							<p class="line" onClick="onFocusCursor('user_pw')">
								<label>비밀번호</label> <input type="password" name="user_pw" id="user_pw" onKeyPress="if(event.keyCode=='13'){doLogin(); return false;}"/>
							</p>
							<p class="chk">
								<input type="checkbox" name="save_chk" id="save_chk" value="Y" <%if(save_chk.equals("Y")){ %>checked<%} %> class="save_id_chk" /> <label for="save_id" class="save_id">아이디 저장</label>
							</p>
						</div>
						<div class="wrap_btn">
							<button class="btn_login" onclick="javascript:doLogin(); return false;">로그인</button>
						</div>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>