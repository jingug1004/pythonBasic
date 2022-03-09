<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : passwordChangeForm.jsp
 * @메뉴명 : 비밀번호 변경    
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%
	//비밀번호 변경 대상체크
	boolean pass_change_chk = (Boolean)request.getAttribute("pass_change_chk");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file ="/include/head.jsp" %>
<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/formCheck.js" ></script>
<script type="text/javascript">
	
	function passwordChange(){
		
		/*
			2015-06-04 비밀번호 유효성 체크는 서버단에서 프로시저 호출하여 체크하는 것으로 변경함
			클라이언트단에서는 value 유무 판단과 비교만 하도록 수정
		*/
		
		if(formCheck.isNull(document.getElementById("old_password"), "현재 비밀번호를 입력 하세요.")){
			return;
		}
		
		if(formCheck.isNull(document.getElementById("new_password"), "새 비밀번호를 입력 하세요.")){
			return;
		}
		
		/* if(!/^[a-zA-Z0-9]{5,12}$/.test(document.getElementById("new_password").value)){
		  	alert('비밀번호는 숫자와 영문자 조합으로 5~12자리를 사용해야 합니다.');
		  	document.getElementById("new_password").value = "";
			document.getElementById("new_rePassword").value = "";
		  	return;
		}
		
		var chk_num = document.getElementById("new_password").value.search(/[0-9]/g); 
	    var chk_eng = document.getElementById("new_password").value.search(/[a-z]/ig);
	    if(chk_num < 0 || chk_eng < 0)
	    { 
	        alert('비밀번호는 숫자와 영문자를 혼용하여야 합니다.'); 
	        return false;
	    } */
		    
		if(document.getElementById("old_password").value == document.getElementById("new_password").value){
			alert("현재 비밀번호와 새 비밀번호가 동일 합니다.");
			document.getElementById("new_password").value = "";
			document.getElementById("new_rePassword").value = "";
			newPassword.focus();
			return;
		}
		
		if(formCheck.isNull(document.getElementById("new_rePassword"), "새 비밀번호 확인을 입력 하세요.")){
			return;
		}
		
		if(document.getElementById("new_password").value != document.getElementById("new_rePassword").value){
			alert("새 비밀번호가 일치 하지 않습니다.");
			document.getElementById("new_password").value = "";
			document.getElementById("new_rePassword").value = "";
			newPassword.focus();
			return;
		}
		
		document.getElementById("passFrm").action="<%=GROUP_ROOT%>/co/login/updatePassword.do";
		document.getElementById("passFrm").submit();
		
	}
	
	//비번 강제변경시
	function logOut(){
		if(confirm("비빌번호를 변경하지 않으면 사이트 이용이 제한됩니다.")){
			window.parent.location.href = "<%=GROUP_ROOT%>/co/login/logOut.do";  // 이동할주소
			window.parent.layerClose();
		}
	}
</script>
</head>
<body>
	<div class="wrap_reset_pass">
	<div class="pass_menu">
		<h3>비밀번호변경</h3>
		<form name="passFrm" id="passFrm" method="post">
		<ul class="input_txt">
			<li>
				<span class="txt_name">현재비밀번호</span>
				<input type="password" name="old_password" id="old_password" onKeyPress="if(event.keyCode=='13'){passwordChange(); return false;}"/>
			</li>
			<li>
				<span class="txt_name">새비밀번호</span>
				<input type="password" name="new_password" id="new_password" onKeyPress="if(event.keyCode=='13'){passwordChange(); return false;}"/>
			</li>
			<li>
				<span class="txt_name">새비밀번호확인</span>
				<input type="password" name="new_rePassword" id="new_rePassword" onKeyPress="if(event.keyCode=='13'){passwordChange(); return false;}"/> 
			</li>
		</ul>
		</form>
		<div class="btn_box mt20">
			<button type="button" class="type_02" onclick="javascript:passwordChange();">확인</button>
		<%if(pass_change_chk){ %>	
			<button type="button" class="type_02" onclick="javascript:logOut();">취소</button>
		<%} else { %>
			<button type="button" class="type_02" onclick="javascript:self.close();">취소</button>	
		<%} %>	
		</div>
		<%if(pass_change_chk){ %>	
		<button type="button" class="close" onclick="javascript:logOut();"><span class="blind">닫기</span></button>
		<%} else { %>
		<button type="button" class="close" onclick="javascript:self.close();"><span class="blind">닫기</span></button>
		<%} %>
	</div>
</div>
</body>
</html>