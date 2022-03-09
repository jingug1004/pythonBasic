<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>

<div class="wrap_authority_staff">
	<div class="authority_staff">
		<h3>임직원선택</h3>
		
		<div class="tree_wrap">
			
			<div class="wrap_tree">
				<div class="wrap_tree_btn" style="width:92px; margin:10px auto">
					<button class="type_01">펼치기</button>
					<button class="type_01">접기</button>
				</div>
			</div>
		</div>
		
		<div class="wrap_group">
			<iframe src="<%=GROUP_ROOT%>/html/include/popup/authority_staff_ifr.jsp" width="303px;" height="260px" border="0" frameborder="0"></iframe>
			<iframe src="<%=GROUP_ROOT%>/html/include/popup/authority_staff_ifr02.jsp" width="303px;" height="345px" border="0" frameborder="0"></iframe>			
		</div>

		<button class="close" onclick="Custombox.close();"><span class="blind">닫기</span></button>
	</div>
</div>

</body>
</html>