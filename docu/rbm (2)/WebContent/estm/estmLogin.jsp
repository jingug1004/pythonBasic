<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> 지원직 및 일용직계약 평가시스템 </title>
<meta http-equiv="Content-Type" contentType="text/html; charset=euc-kr"/>
<link rel="stylesheet" href="css/default.css" type="text/css" />
<![If IE 7]>
<link rel="stylesheet" href="css/ie7.css" type="text/css" />
<![endif]>
<script src="zoom.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">


function estmLogin(){
    if(estmForm.userId.value == "null" ||  estmForm.userId.value == ""){
        alert("아이디를 입력하여 주세요");
        estmForm.userId.focus();
        return;        
    }

    if(estmForm.pswd.value == "null" ||  estmForm.pswd.value == ""){
        alert("비밀번호를 입력하여 주세요");
        estmForm.usrId.focus();
        return;        
    }

      
       estmForm.action="estmLoginSave.jsp";
       estmForm.submit();
        
}

function formInit(){

    estmForm.userId.focus();
}


function fcTxtOnkeyDown(e){
    if(window.event){
        e = window.event
    }
    
   
   if(e.keyCode == "13"){
         estmLogin();
   }
}

</script>
</head>
<body onload="formInit()">
<form name="estmForm" method="post" action="" >
    <div class="wrap">
        <div class="logo">
    
                        <img src="img/logo.jpg" alt="KSPO 경륜경정사업본부/지원직 및 일용계약직 평가시스템" />
                        <p><a href="javascript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
                        <img src="img/btn_m.gif" alt="축소"/></a> <a href="javascript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()">
                        <img src="img/btn_p.gif" alt="확대"/></a></p>
        </div>
        <div class="body">
            <div class="login_page">
                <img src="img/logo_login.jpg" alt="KSPO/지원직 및 일용계약직 평가시스템" />
                <div class="login">
                    <div>
                        <img class="fleft" src="img/img_login.jpg" alt="" />
                        <table>
                            <tbody>
                                <tr>
                                    <td><input style="margin-bottom:4px;" type="text"  name="userId" tabindex="1" /></td>
                                    <td rowspan="2"><a href="javascript:estmLogin()"><img src="img/btn_login.jpg"  alt="로그인" /></a></td>
                                </tr>
                                <tr>
                                    <td><input style="margin-top:4px;" type="password" name="pswd" tabindex="2" onkeydown="fcTxtOnkeyDown(event)" /></td>
                                </tr>
                            </tbody>
                        </table>
                        <p>최초 비밀번호는 주민번호 뒷자리(7자리) 입니다.<br/>로그인 후 비밀번호를 변경할 수 있습니다.</p>
                    </div>
                </div>
                <img style="float:left; margin-top:10px; margin-bottom:14px; margin-right:3px;" src="img/notice.jpg" alt="!" />
                <p style="display:block">본 시스템은 내부업무용으로 허가 받지 않은 사용자의 접근을 금합니다.<br/>비밀번호 분실 시 지점 및 부서 담당자에게 문의 하시기 바랍니다.</p>
            </div><!-- //login_page -->
        </div><!-- //body -->
        <div class="copy">
        <p>Copyright (c) <strong>경륜경정사업본부</strong> All rights Reserved.</p>
        </div>
    </div><!-- //wrap -->
    </form>
</body>
</html>
