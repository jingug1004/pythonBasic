<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
	<div class="popup" title="Main">
		
		<!-- ##### content start ##### -->
		<!-- window size : 480 * 655 -->
			<h1 class="tit">사원조회</h1>
			
			<div class="wrap_pop_search">
				<select class="w100">
					<option>부서</option>
				</select>
				<input type="text" class="w100" />
				<input type="text" class="w150" />
				
				<div class="wrap_pop_search_btn">
					<button>조회</button>
					<button>확인</button>
					<button>취소</button>
				</div>
			</div>
			
			<div class="pop_grid box_type01 h440">
				
			</div>
			
			<button class="close"><span class="blind">닫기</span></button>
		
		<!-- ##### content end ##### -->
	
	</div>
	
	<%@include file="../../include/footer_pop.jsp"%>
</body>
</html>