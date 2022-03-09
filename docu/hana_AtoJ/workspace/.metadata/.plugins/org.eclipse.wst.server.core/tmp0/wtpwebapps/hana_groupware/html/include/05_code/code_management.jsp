<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
	<%@include file="../../include/header.jsp"%>
	<%@include file="../../include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="../../include/location.jsp"%>
			<%@include file="../../include/lnb.jsp"%>
			
			<!-- ######## start ####### -->
<div class="cont float_left">
	<h2>코드관리</h2>
	<div class="management_box">
		<div class="cont_box_l fl">
		<div>
			<div class="serch_box">
				<select>
					<option value="">코드명</option>
					<option value="">코드명A</option>
					<option value="">코드명B</option>
				</select>
				<input type="text" />
				<button>검색</button>
			</div>
		</div>
		<div class="cont_box01">				
			<table>
				<colgroup>
					<col width="18%" />
					<col width="18%" />
					<col width="18%" />
					<col width="28%" />
					<col width="18%" />
				</colgroup>
				<tbody>
				<tr>
					<th>코드</th>
					<th>메인코드</th>
					<th>서브코드</th>
					<th>코드명</th>
					<th class="br_none">사용여부</th>
				</tr>
				<tr>
					<td class="bb_none">A01000</td>
					<td class="bb_none">A01</td>
					<td class="bb_none fc_re">000</td>
					<td class="bb_none">자유게시판</td>
					<td class="br_none bb_none">Y</td>
				</tr>
				</tbody>
			</table>
		</div>
		
		<div class="paging01">
			<div class="wrap_paging">
				<button class="prev01"><span class="blind">이전 10페이지</span></button>
				<button class="prev02"><span class="blind">이전 페이지</span></button>
				
				<ul>
					<li><a href="#" class="active">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">6</a></li>
					<li><a href="#">7</a></li>
					<li><a href="#">8</a></li>
					<li><a href="#">9</a></li>
					<li><a href="#">10</a></li>
				</ul>
				
				<button class="next02"><span class="blind">다음 페이지</span></button>
				<button class="next01"><span class="blind">다음 10페이지</span></button>
			</div>
		</div>		
		
		</div>
		<div class="menu_r fr">
			<iframe src="<%=GROUP_ROOT%>/html/include/05_code/code_management_ifr.jsp" frameborder="0" border="0" width="408px" height="485px" style="overflow:hidden"></iframe>
					
		
		</div>
	</div>
</div>


<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>