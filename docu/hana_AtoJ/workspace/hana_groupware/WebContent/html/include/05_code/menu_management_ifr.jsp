<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>


			<div class="menu_box">
				<div class="cont_tit_box w502">
					<h3 class="mt_5 fl fs_14">메뉴</h3>
					<div class="fr">
						<button class="type_01">신규</button>
						<button class="type_01">삭제</button>
					</div>
				</div>
				<div class="cont_box cont_table01">
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
									<th><label>프로그램번호</label></th>
									<td>
										<select>
											<option value="">0123</option>
											<option value="">4567</option>
											<option value="">8900</option>
											<option value="">0000</option>
										</select>
									</td>
									<th><label>프로그램명</label></th>
									<td><input type="text" /></td>										
								</tr>
								<tr>
									<th><label>종류</label></th>
									<td>
										<select>
											<option value="">전체</option>
											<option value="">4567</option>
											<option value="">8900</option>
											<option value="">0000</option>
										</select>
									</td>
									<th><label>윈도우명</label></th>
									<td><input type="text" /></td>										
								</tr>
								<tr>
									<th><label>프로그램사용여부</label></th>
									<td>
										<select>
											<option value="">Y</option>
											<option value="">N</option>
										</select>
									</td>
									<th><label>메뉴사용여부</label></th>
									<td>
										<select>
											<option value="">Y</option>
											<option value="">N</option>
										</select>
									</td>										
								</tr>
								<tr>
									<th class="bb_none"><label>URL</label></th>
									<td colspan="3" class="url_box bb_none pr13"><input type="text" /></td>
								</tr>																									
							</tbody>
						</table>
					</form>
				</div>
				<div class="btn_save_r w502">
					<button class="type_01">저장</button>
				</div>				
			</div>
			
			<div class="menu_box mt_20">
				<div class="cont_tit_box">
					<h3 class="mt5 pt_5 fs_14">하위목록</h3>
				</div>
				<div class="cont_box cont_table02">
					<form>
						<table>
						<colgroup>
							<col width="18%" />
							<col width="22%" />
							<col width="18%" />
							<col width="12%" />
							<col width="8%" />							
							<col width="9%" />
							<col width="23%" />
						</colgroup>
							<tbody>
								<tr>
									<th>프로그램번호</th>
									<th>프로그램/메뉴명</th>
									<th>윈도우명</th>
									<th>종류</th>
									<th>메뉴</th>
									<th>순서</th>
									<th class="br_none">계층번호</th>
								</tr>
								<tr>
									<td>10245</td>
									<td class="color_re">연말정산 개인등록</td>
									<td>W_hanay_inte</td>
									<td>프로그램</td>
									<td><input type="checkbox" /></td>
									<td>1</td>
									<td class="br_none">060801</td>
								</tr>
								<tr>
									<td>10245</td>
									<td class="color_re">연말정산 개인등록</td>
									<td>W_hanay_inte</td>
									<td>프로그램</td>
									<td><input type="checkbox" /></td>
									<td>1</td>
									<td class="br_none">060801</td>
								</tr>	
								<tr>
									<td>10245</td>
									<td class="color_re">연말정산 개인등록</td>
									<td>W_hanay_inte</td>
									<td>프로그램</td>
									<td><input type="checkbox" /></td>
									<td>1</td>
									<td class="br_none">060801</td>
								</tr>
								<tr>
									<td>10245</td>
									<td class="color_re">연말정산 개인등록</td>
									<td>W_hanay_inte</td>
									<td>프로그램</td>
									<td><input type="checkbox" /></td>
									<td>1</td>
									<td class="br_none">060801</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
</body>
</html>