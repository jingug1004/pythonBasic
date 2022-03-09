<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>전달하기</h3>
				<div class="pop_con_all pop_register">
					<div class="box_sign_send">
						<p class="ques"><span >우정아</span>님, 담당자를 선택하여 문서를 전달하시겠습니까?</p>
						<p class="info"><span class="tit">부서</span> : <span>영업기획팀</span><span class="tit ml15">문서수발자</span> : <span>우정아</span></p>					
					</div>
					<div class="search_box03 tac">
						<span>사원명</span>
						<input type="text">
						<button class="search2">검색</button>
					</div>
					<div class="box_scroll">
						<table>
							<colgroup>
								<col style="width:28px"/>
								<col style="width:112px"/>
								<col style="width:90px"/>
								<col style=""/>
							</colgroup>
							<thead>
								<tr>
									<th></th>
									<th>부서</th>
									<th>직급</th>
									<th class="bdrn">사원명</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><input type="radio" name="charge" id="" /></td>
									<td>경영기획팀</td>
									<td>부장</td>
									<td class="bdrn">이수연</td>
								</tr>
								<tr>
									<td><input type="radio" name="charge" id="" /></td>
									<td>경영기획팀</td>
									<td>과장</td>
									<td class="bdrn">강웅</td>
								</tr>
								<tr>
									<td><input type="radio" name="charge" id="" /></td>
									<td>경영기획팀</td>
									<td>부장</td>
									<td class="bdrn">최일지</td>
								</tr>
								<tr>
									<td><input type="radio" name="charge" id="" /></td>
									<td>경영기획팀</td>
									<td>부장</td>
									<td class="bdrn">이정민</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="btn_pop">
					<button class="type_01">확인</button>
					<button class="type_01">취소</button>
				</div>
				<button class="close"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->
</body>
</html>