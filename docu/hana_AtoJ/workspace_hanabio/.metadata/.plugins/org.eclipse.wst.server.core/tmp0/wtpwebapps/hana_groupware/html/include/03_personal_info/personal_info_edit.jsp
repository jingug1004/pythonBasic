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
	<h2>개인정보수정</h2>
	<div class="wrap_personal_info_edit">
		<table>
			<colgroup>
				<col style="width:110px;" />
				<col style="width:124px;" />
				<col style="width:110px;" />
				<col style="width:124px;" />
				<col style="width:110px;" />
				<col style="width:124px;" />
				<col />
			</colgroup>
			<tr class="no_bd">
				<th>사용자번호</th>
				<td colspan="5" class="photo_pd">ADMIN 관리자<button class="type_01 ml35">비밀번호변경</button></td>
				<td rowspan="4" class="photo">
					<img src="<%=GROUP_ROOT%>/asset/img/character.gif" alt="사진" />
				</td>
			</tr>
			<tr>
				<th>로그인ID</th>
				<td>ADMIN</td>
				<th>비밀번호</th>
				<td>********</td>
				<th>비밀번호확인</th>
				<td>********</td>
			</tr>
			<tr>
				<th>한글이름</th>
				<td>관리자</td>
				<th>영문이름</th>
				<td>ADMINISTRATOR</td>
				<th>직급</th>
				<td>직원외</td>
			</tr>
			<tr>
				<th>주민번호</th>
				<td>101206-0100809</td>
				<th>사원번호</th>
				<td>ADMIN</td>
				<th>생일</th>
				<td>2010.12.06</td>
			</tr>
		</table>
		<table>
			<colgroup>
				<col style="width:110px;" />
				<col style="width:124px;" />
				<col style="width:110px;" />
				<col />
			<tr class="no_bd">
				<th colspan="4" class="ta_c">추가정보</th>
			</tr>
			<tr>
				<th>우편번호</th>
				<td>
					<span class="txt">123-456</span>
					<button class="find"><span class="blind">찾기</span></button>
				</td>
				<th>우편번호 주소</th>
				<td>서울시 강남구 역삼동 747-29 로케트 빌딩</td>
			</tr>
			<tr>
				<th>사무실전화-국가</th>
				<td></td>
				<th>부속주소</th>
				<td></td>
			</tr>
			<tr>
				<th>사무실전화-상세</th>
				<td></td>
				<th>이메일</th>
				<td>ADMIN</td>
			</tr>
			<tr>
				<th>내선번호</th>
				<td>025595791</td>
				<th>모바일전화</th>
				<td></td>
			</tr>
			<tr>
				<th>참조문서설정</th>
				<td colspan="3">
					<select>
						<option>참조등록버튼이용</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3">관리자</td>
			</tr>
		</table>
		<table>
			<colgroup>
				<col />
				<col />
				<col />
				<col />
				<col />
				<col />
				<col />
				<col style="width:124px;" />
			</colgroup>
			<tr class="no_bd">
				<th colspan="8" class="ta_c">관리정보</th> <!-- 11.17 수정 -->
			</tr>
			<tr>
				<th>등록일자</th>
				<td>
					0000.00.00
				</td>
				<th>종료일자</th>
				<td>
					9999.99.99
				</td>
				<th>조직구분</th>
				<td>운영사</td>
				<th>발주담당거래처</th>
				<td>
					<span class="txt"></span>
					<button class="find"><span class="blind">찾기</span></button>
				</td>
			</tr>
		</table>
	</div>
</div>

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>