<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>

<div class="wrap_authority_menu">
	<div class="authority_menu">
		<h3>메뉴선택</h3>
		<div class="wrap_list">
			<table>
				<colgroup>
					<col style="width:115px" />
					<col style=""/>
					<col style="" />
				</colgroup>
				<tr>
					<th>권한제목</th>
					<td colspan="2"><input type="text" /></td>
				</tr>
				<tr>
					<th>권한설명</th>
					<td colspan="2"><input type="text" /></td>
				</tr>
				<tr>
					<td colspan="3" class="inner">
						<div>
							<table>
								<colgroup>
									<col style="width:115px"/>
									<col style="width:244px"/>
									<col style=""/>
								</colgroup>
								<tr>
									<th rowspan="8">공지/게시/쪽지</th>
									<td>공지사항</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>하나제약게시판</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>제품교육자료</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>신약신청서자료</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>제품자료신청</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>하나제약 야구단</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>테스트용 자료실</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>쪽지함</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<th rowspan="7">전자결재</th>
									<td>신규문서작성</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>내가올린문서</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>내가받은문서</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>개인결재라인</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>문서목록(관리)</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>양식정보관리(관리)</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>문서수발자관리(관리)</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<th>인사관리</th>
									<td>인원현황조회</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<th rowspan="7">개인정보</th>
									<td>개인정보수정</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>연차사용계획</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>연차사용내역</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>연차지정통보서</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>급여조회</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>건강보험료</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>연봉계약서</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<th>연말정산</th>
									<td>연말정산조회</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<th rowspan="3">코드/권한/메뉴</th>
									<td>메뉴관리</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>코드관리</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<td>권한관리</td>
									<td><input type="checkbox" /></td>
								</tr>
								<tr>
									<th>인사등록</th>
									<td>조직관리</td>
									<td><input type="checkbox" /></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="wrap_box">
			<button class="type_01">저장</button>
		</div>
		
		<button class="close" onclick="Custombox.close();"><span class="blind">닫기</span></button>
	</div>
</div>

</body>
</html>