<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> ������ �� �Ͽ������ �򰡽ý��� </title>
<meta http-equiv="Content-Type" contentType="text/html; charset=euc-kr"/>
<link rel="stylesheet" href="css/default.css" type="text/css" />
<![If IE 7]>
<link rel="stylesheet" href="css/ie7.css" type="text/css" />
<![endif]>
<script src="zoom.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">


function estmLogin(){
    if(estmForm.userId.value == "null" ||  estmForm.userId.value == ""){
        alert("���̵� �Է��Ͽ� �ּ���");
        estmForm.userId.focus();
        return;        
    }

    if(estmForm.pswd.value == "null" ||  estmForm.pswd.value == ""){
        alert("��й�ȣ�� �Է��Ͽ� �ּ���");
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
    
                        <img src="img/logo.jpg" alt="KSPO ��������������/������ �� �Ͽ����� �򰡽ý���" />
                        <p><a href="javascript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
                        <img src="img/btn_m.gif" alt="���"/></a> <a href="javascript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()">
                        <img src="img/btn_p.gif" alt="Ȯ��"/></a></p>
        </div>
        <div class="body">
            <div class="login_page">
                <img src="img/logo_login.jpg" alt="KSPO/������ �� �Ͽ����� �򰡽ý���" />
                <div class="login">
                    <div>
                        <img class="fleft" src="img/img_login.jpg" alt="" />
                        <table>
                            <tbody>
                                <tr>
                                    <td><input style="margin-bottom:4px;" type="text"  name="userId" tabindex="1" /></td>
                                    <td rowspan="2"><a href="javascript:estmLogin()"><img src="img/btn_login.jpg"  alt="�α���" /></a></td>
                                </tr>
                                <tr>
                                    <td><input style="margin-top:4px;" type="password" name="pswd" tabindex="2" onkeydown="fcTxtOnkeyDown(event)" /></td>
                                </tr>
                            </tbody>
                        </table>
                        <p>���� ��й�ȣ�� �ֹι�ȣ ���ڸ�(7�ڸ�) �Դϴ�.<br/>�α��� �� ��й�ȣ�� ������ �� �ֽ��ϴ�.</p>
                    </div>
                </div>
                <img style="float:left; margin-top:10px; margin-bottom:14px; margin-right:3px;" src="img/notice.jpg" alt="!" />
                <p style="display:block">�� �ý����� ���ξ��������� �㰡 ���� ���� ������� ������ ���մϴ�.<br/>��й�ȣ �н� �� ���� �� �μ� ����ڿ��� ���� �Ͻñ� �ٶ��ϴ�.</p>
            </div><!-- //login_page -->
        </div><!-- //body -->
        <div class="copy">
        <p>Copyright (c) <strong>��������������</strong> All rights Reserved.</p>
        </div>
    </div><!-- //wrap -->
    </form>
</body>
</html>
