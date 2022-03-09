<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="common/comDBManager.jsp" %>
<html>
<head>
<title>TF Search</title>
<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>
<link rel='stylesheet' href='css/table.css'>
<link rel='stylesheet' href='css/button.css'>
<link rel="stylesheet" type="text/css" media="all" href="css/datechooser.css">
<script language="javascript" src="script/datechooser.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="script/common.js"></SCRIPT>
<script language="javascript" src="script/calendar_open.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="script/validations.js"></SCRIPT>
<script>
<!--
function submitForm(fom) {
    unformatForm(fom);
    isSubmit(fom);
}
function navigate_search() {
    unformatForm(document.searchForm);
    if(isSubmit(document.searchForm)==false){
       return;
    }       
    
    var t_win_no = trim(document.searchForm.win_no.value);
    var t_tsn = trim(document.searchForm.tsn.value);
    var t_race_day = trim(document.searchForm.race_day.value);
    
    if(t_win_no=='' && t_tsn=='' ){
        alert('창구번호 또는 TSN은 필수 입력 항목 입니다.');
        return;
    }
    
    if( t_tsn=='' && t_race_day =='' ){
        alert('경주일자는  필수 입력 항목 입니다.');
        return;
    }
    
    
    with(document.searchForm) {
        searchForm.action ="tfSearchList.jsp";
        searchForm.target = "ListIFrame";
        submit();
    }
}
//-->
</script>
</head>
<body>
<center>
<br>
<table width="100%" height="15" border="0" cellpadding="0" cellspacing="0">
    <tr align="right">
        <td>
            <div class="buttonwrapper" onClick="return false">
            <a class="squarebutton" href="#" onClick="navigate_search();" style="margin-right: 10px"><span>검색</span></a>
            </div>
        </td>
    </tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
  <tr>
    <td width="10" height="20" align="left" valign="top" bgcolor="feb016"><img src="image/sub_01_31.gif" width="10" height="20"></td>
    <td width="90" bgcolor="feb016" align="center"><font color="#FFFFFF">검 색</font></td>
    <td width="*"></td>
  </tr>
  <tr bgcolor="feb016"> 
    <td height="2" colspan="3"> <p align="center"></p></td>
  </tr>
</table>

<table width="98%" cellspacing="1" cellpadding="1" border="0" class="tab">
<form name="searchForm"  method="post" action="">
  <tr height="22">
        <td class="td_g" align="center">첫자리 확인하기</td>
        <td class='td_w_ce' colspan="5">  
            TSN(ABCD-EFGH-IJKL)=>
            <input type=text name=temp_tsn size=15 onFocus="doSelection(searchForm.temp_tsn);">
            <input type=button value='첫자리확인하기' onClick='p_getTSN(this.form)'>
            <font color=blue>확인결과(첫자리포함TSN)=><font><input type=text name=newtsn size=20 disabled>
        </td>   
  </tr>
  <tr height="22">
        <td class="td_g" align="center">날짜</td>
        <td class='td_w_ce'>  
            <input type="text" name="race_day" id="race_day" value="" size="10" class="datechooser dc-dateformat='Y-m-d' dc-iconlink='image/datechooser.png' dc-onupdate='race_day' ; input_r"  mytype="DATE" desc="경주일자">
        </td>       
        <td class="td_g" align="center">창구</td>
        <td class='td_w_ce'>
            <input type="text" name="win_no" size="10" class="input_r" mytype="NUMFORMAT" desc="창구번호">(4자리)
            <input type="hidden" name="cmd">
            <input type="hidden" name="command" value="">
        </td>  
        <td class="td_g" align="center">TSN</td>
        <td class='td_w_ce'>
            <input type="text" name="tsn" size="17" maxlength="16" class="input_r" desc="TSN">(F-0123-4567-89AB)  
        </td>   
  </tr>
</form>
</table>
<br>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
   <tr> 
     <td width="10" height="2" align="left" valign="top" bgcolor="feb016"><img src="image/sub_01_31.gif" width="10" height="20"></td>
     <td width="90" height="20" bgcolor="feb016"><div align="center"><font color="#FFFFFF">결 과</font></div></td>
     <td width="*"></td>
   </tr>
   <tr> 
     <td height="2" colspan="3" bgcolor="feb016"> <p align="center"></p></td>
   </tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
   <tr> 
    <td>
        <table width="100%" cellspacing="1" cellpadding="2" border="0" class="tab">
            <tr>
                <td width="110" class="td_g_ce">발행시간</td>
                <td width="50"  class="td_g_ce">구분</td>
                <td width="160" class="td_g_ce">tsn</td>
                <td width="40" class="td_g_ce">창구</td>
                <td width="60" class="td_g_ce">발매원</td>         
                <td width="50" class="td_g_ce">TYPE</td>
                <td width="35" class="td_g_ce">PERF</td>
                <td width="35" class="td_g_ce">MEET</td>
                <td width="50" class="td_g_ce">RACE</td>
                <td width="85" class="td_g_ce">STATUS</td>
                <td width="80" class="td_g_ce">금액</td>      
                <td width="" class="td_g_ce">구매내역</td>
            </tr>
        </table>    
    </td>
    </tr>
    <tr>
     <td valign="top">
        <iframe name="ListIFrame" src="" width="100%" height="450" frameborder="0" scrolling="auto"></iframe>
     </td>
   </tr>   
</table>
<table>
<tr>
    <td align="center" valign="top">
    <!---footer 디스플레이 시작--->
    <br><strong><sosfotag:footer/></strong>
    <!---footer 디스플레이 끝--->
    </td>
</tr>
</table>
<script type="text/javascript">
        function race_day(objDate){
            var ndExample1 = document.getElementById('race_day');
            return true;
        }
   
        //navigate_search();
        
                //TSN의 첫짜리 구하기
        function p_getTSN(frm) {    
            var checksum = 0;
            var digit = 0;
            var tsn_org = frm.temp_tsn.value;
            var tsn = tsn_org;
            var re = /-/g;
            tsn = tsn.replace(re,'');
            var re = / /g;
            tsn = tsn.replace(re,'');

            if (tsn == '' || tsn.length != 12) {
                alert('TSN이 정확한지 다시 확인하세요.');
                return;
            }

            for(i=0; i<tsn.length;i++) {
                digit = 0;          
                cUnit = tsn.charAt(i);
                if ((cUnit >= '0') && (cUnit <= '9')) { digit = cUnit.charCodeAt(0) - 48; }
                else if ((cUnit >= 'A') && (cUnit <= 'F')) { digit = cUnit.charCodeAt(0) - 65 + 10; }
                else if ((cUnit >= 'a') && (cUnit <= 'f')) { digit = cUnit.charCodeAt(0) - 97 + 10; }
                checksum = checksum + (digit * (i+1));
            }
            
            checksum = checksum%16;
            if (checksum >= 10) { checksum = checksum + 65 - 10 ; }
            else { checksum = checksum + 48; }  
            frm.newtsn.value = String.fromCharCode(checksum) + '-' + tsn_org;
            frm.tsn.value = String.fromCharCode(checksum) + '-' + tsn_org;
        }
</script>
</center>
</body>
</html>
