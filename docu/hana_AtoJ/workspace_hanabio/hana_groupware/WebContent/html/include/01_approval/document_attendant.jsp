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
	<div class="wrap_send_document">
		<h2>문서수발자 관리</h2>
		<div class="attendant_box">
			<div class="wrap_search mbn">
				<div class="search">
					<ul class="serch_con01">
						<li>
							<span class="sc_txt">부서</span>
							<select>
								<option>영업부</option>
								</select>
						</li>
						<li>
							<span class="sc_txt">양식명</span>
							<input type="text">
						</li>
					</ul>
				</div>
				<div class="wrap_btn">
					<button class="btn_search">검색</button>
				</div>
			</div>
		
		
		<div class="cont_box cont_table05">
			<form>
				<table>
				<colgroup>
					<col style="width:96px">
					<col style="width:333px">
					<col style="width:270px">
					<col style="">
				</colgroup>
					<thead>
						<tr>
							<th>NO.</th>
							<th>부서</th>
							<th>문서수발자</th>
							<th class="br_none"></th>										
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>10</td>
							<td>영업부</td>
							<td>이수연</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>9</td>
							<td>영업부</td>
							<td>강웅</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>8</td>
							<td>영업부</td>
							<td>최일지</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>7</td>
							<td>영업부</td>
							<td>이수연</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>6</td>
							<td>영업부</td>
							<td>강웅</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>5</td>
							<td>영업부</td>
							<td>최일지</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>4</td>
							<td>영업부</td>
							<td>이수연</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>3</td>
							<td>영업부</td>
							<td>강웅</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>2</td>
							<td>영업부</td>
							<td>최일지</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
						<tr>
							<td>1</td>
							<td>영업부</td>
							<td>이수연</td>
							<td class="br_none"><button>수정</button></td>										
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="paging">
			<div class="wrap_total">
				* 총 건수 : <span class="cnt">99</span>건
			</div>
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
	</div>
</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>