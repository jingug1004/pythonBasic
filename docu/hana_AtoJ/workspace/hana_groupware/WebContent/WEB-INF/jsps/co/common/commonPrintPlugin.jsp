<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : commonPrint.jsp
 * @메뉴명 : 공통 프린트(플러그인 설치)
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>하나제약 그룹웨어</title>
	<style>
		/*a4용지*/
		@page a4sheet { size: 21.0cm 29.7cm } 
		.a4 { page: a4sheet; page-break-after: always } 
	</style>
	<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
	
		try {
			// css 복제
			$("link", opener.document).each(function() {
				var css = document.createElement("link");
				css.setAttribute("rel", $(this).attr("rel"));
				css.setAttribute("type", $(this).attr("type"));
				css.setAttribute("href", $(this).attr("href"));
				document.getElementsByTagName("head")[0].appendChild(css);
			});	
		} catch (e) {
			// 잘못된 접근일 경우 메인으로 redirect
			location.href = "<%=GROUP_ROOT%>/main/main.do";
		}
	
		// 인쇄
		function printPage(){
			$("#buttonArea").hide();
			if(confirm("인쇄하시겠습니까?")) {
				try{ 
					factory.printing.header = ""; // Header에 들어갈 문장
					factory.printing.footer = ""; // Footer에 들어갈 문장
					factory.printing.portrait = true; //true는 세로인쇄, false는 가로인쇄 
					factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈
					factory.printing.topMargin = 1.0; // 위 여백 사이즈
					factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈
					factory.printing.bottomMargin = 1.0;  // 아래 여백 사이즈
					factory.printing.Print(true); //프린트대화상자를 열땐 true, 아니면 false 
				}catch(e){ 
					window.print(); 
				}
			}
			$("#buttonArea").show();
		}
		
		$(document).ready(function(){
			try {
				// 인쇄영역 복제
				var originArea = opener.document.getElementById(self.name);
				var printArea = document.createElement("div");
				printArea.className = originArea.className + " a4";
				printArea.setAttribute("id", self.name);
				printArea.innerHTML = originArea.innerHTML;
				document.body.appendChild(printArea);

				$("." + self.name).remove();
			} catch (e) {
				// 잘못된 접근일 경우 메인으로 redirect
				location.href = "<%=GROUP_ROOT%>/main/main.do";
			}
		});
	
	</script>
</head>
<body>
<!-- MeadCo ScriptX --> 
<object id=factory style="display:none" 
classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" 
codebase="<%=GROUP_WEB_ROOT%>/plugin/smsx.cab#Version=7,5,0,20 "> 
</object>

<div id="buttonArea" class="wrap_btn" style="margin:15px 10px 10px; text-align:right; display:block;">
	<button type="button" class="type_01" onclick="javascript:printPage();" >인쇄</button>
	<button type="button" class="type_01" onclick="javascript:self.close();" >닫기</button>
</div>
</body>
</html>