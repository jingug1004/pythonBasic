<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>

		<div class="menu_box w405">
				<div class="cont_tit_box">
					<h3 class="mt_5 fl">프로그램</h3>
					<div class="fr">
						<button class="type_01">신규</button>
						<button class="type_01">삭제</button>
					</div>
				</div>
				<div class="cont_box cont_table03">
					<form>
						<table>
						<colgroup>
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
						</colgroup>
							<tbody>
								<tr>
									<th>코드</th>
									<td>A01000</td>
									<th><label>코드명</label></th>
									<td><input type="text" /></td>										
								</tr>
								<tr>
									<th><label>메인코드</label></th>
									<td><input type="text" /></td>
									<th><label>서브코드</label></th>
									<td><input type="text" /></td>										
								</tr>
								<tr>
									<th><label>코드설명</label></th>
									<td colspan="3" class="url_box pr14"><input type="text" class="w_reset" /></td>	
								</tr>
								<tr>
									<th class="bb_none"><label>사용여부</label></th>
									<td class="bb_none">
										<select>
											<option value="">Y</option>
											<option value="">N</option>
										</select>
									</td>
									<th class="bb_none"><label>정렬</label></th>
									<td class="bb_none">
										<select>
											<option value="">Y</option>
											<option value="">N</option>
										</select>
									</td>										
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<div class="btn_save_r">
					<button class="type_01">저장</button>
				</div>					
			</div>
			
			<div class="menu_box mt_20">
				<div class="cont_tit_box">
					<h3 class="mt5">하위목록</h3>
				</div>
				<div class="cont_box02 cont_table04">
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
							<td>A01000</td>
							<td>A01</td>
							<td class="fc_re">000</td>
							<td>자유게시판</td>
							<td class="br_none">Y</td>
						</tr>
						<tr>
							<td>A01000</td>
							<td>A01</td>
							<td class="fc_re">000</td>
							<td>자유게시판</td>
							<td class="br_none">Y</td>
						</tr>						
						</tbody>
					</table>
				</div>
		</div>
			
</body>
</html>