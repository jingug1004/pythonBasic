<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>
<!-- ######## start ####### -->
		<!--  popup start -->
		<div class="noti_pop">
			<div class="pop_box">
				<p class="btn_del"><button onclick="Custombox.close();"><img src="<%=GROUP_ROOT%>/asset/img/popup_btn_close01.gif" alt="닫기" /></button></p>
				<h3 class="pop_tit">게시대상</h3>
				<div class="cont_box03">
					<table>
						<colgroup>
							<col width="10%" />
							<col width="30%" />
							<col width="30%" />
							<col width="30%" />
						</colgroup>
						<tbody>
							<tr>
								<th><input type="checkbox" /></th>
								<th>대상종류</th>
								<th>대상자(조직)</th>
								<th class="br_none">이름</th>
							</tr>
							<tr>
								<td><input type="checkbox" /></td>
								<td>조직</td>
								<td>00001</td>
								<td class="br_none">하나제약</td>
							</tr>
							<tr>
								<td><input type="checkbox" /></td>
								<td>조직</td>
								<td>00001</td>
								<td class="br_none">하나제약</td>
							</tr>
							<tr>
								<td><input type="checkbox" /></td>
								<td>조직</td>
								<td>00001</td>
								<td class="br_none">하나제약</td>
							</tr>
							<tr>
								<td><input type="checkbox" /></td>
								<td>조직</td>
								<td>00001</td>
								<td class="br_none">하나제약</td>
							</tr>
							<tr>
								<td><input type="checkbox" /></td>
								<td>조직</td>
								<td>00001</td>
								<td class="br_none">하나제약</td>
							</tr>																																			
						</tbody>
					</table>
				</div>
				<div class="list_btn01">
					<div class="list_button">
						<button class="type_01">선택완료</button>
						<button class="type_01" onclick="Custombox.close();">닫기</button>
					</div>
				</div>				
			</div>
		</div>
		<!--  popup end -->


<!-- ######## end ######### -->

</body>
</html>
