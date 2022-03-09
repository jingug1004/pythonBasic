<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>

<div class="wrap_pop_bg2 no_bg">
	<div class="wrap_receive_document_h wrap_document_print_h">
		<!-- 인쇄화면 설정시 : <div class="top">부터 주석처리 해주세요.
		<div class="top">
			<h3>인쇄<span> 물품구입청구 및 확인서</span></h3>
			<button class="close"><span class="blind">닫기</span></button>
			<div>
				<div class="fr">
					<button class="type2">인쇄</button>
				</div>
			</div>
		</div>
		<!-- 인쇄화면 설정시 : </div>여기까지 주석처리 해주세요.-->
		<div class="inner">
			<div class="cont_box cont_table06">
				<h4><strong>물품구입청구 및 확인서</strong></h4>
				<table class="tbl_register">
				  <colgroup>
				    <col style="width:12%;" />
				    <col style="width:13%;" />
				    <col style="width:12%;" />
				    <col style="width:13%;" />
				    <col style="width:12%;" />
				    <col style="width:13%;" />
				    <col style="width:12%;" />
				    <col style="width:13%;" />
			      </colgroup>
				  <tbody>
				    <tr>
				      <th>문서번호</th>
				      <td>123456789</td>
				      <th style="border-bottom: none;">작성일자</th>
				      <td>2015. 01. 15</td>
				      <th>작성부서</th>
				      <td>구매부</td>
				      <th>작성자</th>
				      <td class="bdr2">홍길동</td>
			        </tr>
				    <tr>
				      <th>청구결재</th>
				      <td colspan="3" class="confirm_box">
				      	<table>
						  <colgroup>
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
					      </colgroup>
						  <tbody>
						    <tr>
						      <th>test</th>
						      <th></th>
						      <th></th>
						      <th></th>
						      <th></th>
					        </tr>
						    <tr>
						      <td>test</td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
					        </tr>
					      </tbody>
						</table>
				      </td>
				      <th>확인결재</th>
				      <td colspan="3" class="confirm_box bdr2">
				      	<table>
						  <colgroup>
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
					      </colgroup>
						  <tbody>
						    <tr>
						      <th>test</th>
						      <th></th>
						      <th></th>
						      <th></th>
						      <th></th>
					        </tr>
						    <tr>
						      <td>test</td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
					        </tr>
					      </tbody>
						</table>
				      </td>
			        </tr>
				    <tr>
				      <th>시행부서</th>
				      <td colspan="7" class="bdr2 text_l">구매부</td>
			        </tr>
				    <tr>
				      <th>제        목</th>
				      <td colspan="7" class="bdr2 text_l">결재올립니다.</td>
			        </tr>
			      </tbody>
				</table>

				<h5>
					<span class="w_date1">청구번호 : 12345</span>
					<span class="w_date2">구매부접수일 : 2015. 01. 07</span>
				</h5>

				<table class="tbl_register2">
					<colgroup>
						<col style="width:15%;">
						<col style="width:15%;">
						<col style="width:20%;">
						<col style="width:10%;">
						<col style="width:10%;">
						<col style="width:10%;">
						<col style="width:10%;">
						<col style="width:10%;">
					</colgroup>
					<thead>
						<tr>
						  <th>코드</th>
						  <th colspan="3">원부자재명</th>
						  <th colspan="3">규격</th>
						  <th class="bdr2">납품일자</th>
					  </tr>
						<tr>
							<th>사용목적</th>
							<th>납기요구일 </th>							
							<th style="border-bottom: none;">거래처</th>
							<th>현재고</th>				
							<th>수량</th>				
							<th>단위</th>
							<th>추정단가</th>				
							<th class="bdr2">납품확인자</th>			
						</tr>
					</thead>	
					<tbody>	
						<tr class="in_table">
							<td>abcd</td>
							<td>abcd</td>
							<td>abcd</td>
							<td>abcd</td>
							<td>abcd</td>
							<td>abcd</td>
							<td>abcd</td>
							<td class="bdr2">abcd</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr><tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="bdr2">&nbsp;</td>
						</tr>
						<tr class="in_table">
							<th class="append_tit text_l">첨부파일</th>
							<td colspan="7" class="append_tit bdr2 text_l">aaa.jpg</td>
						</tr>
					</tbody>
				</table>							

			</div>
	
		</div>
	</div>
</div>

</body>
</html>