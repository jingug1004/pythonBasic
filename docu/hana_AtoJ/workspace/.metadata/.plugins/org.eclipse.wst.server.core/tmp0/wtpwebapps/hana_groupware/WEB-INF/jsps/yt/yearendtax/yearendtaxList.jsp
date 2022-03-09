<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxList.jsp
 * @메뉴명 : 연말정산 목록
 * @최초작성일 : 2015/02/16
 * @author : 윤범진                  
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO" %>   
<%@ include file ="/common/path.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	/* 원천징수영수증 */
	function goYearendtaxDetail(year){
		document.getElementById("year").value = year;
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxDetail.do";
		document.getElementById("frm").submit();
	}
	
	/* 등록화면 */
	function goYearendtaxInsertForm(year){
		document.getElementById("year").value = year;
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxInsertForm.do";
		document.getElementById("frm").submit();
	}
	
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<!-- ######## start ####### -->
			<div class="cont float_left">
				<div class="wrap_search_yearend">
					<h2>연말정산조회</h2>
					<div class="noticeboard_box mt20">
						<form name="frm" id="frm" method="post">
							<input type="hidden" name="year" id="year" />
						</form>
						
						<div class="cont_box cont_table05">
							<table>
							<colgroup>
								<col width="34%">
								<col width="33%">
								<col width="33%">
							</colgroup>
								<thead>
									<tr>
										<th>연도</th>
										<th>등록</th>
										<th>미리보기</th>										
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>2014</td>
										<td><button type="button" class="type_01" onclick="javascript:goYearendtaxInsertForm('2014')">등록</button></td>
										<td><button type="button" class="type_01" onclick="javascript:goYearendtaxDetail('2014')">미리보기</button></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- 페이징 주석 -->
						<!-- <div class="paging">
							
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
						</div> -->
					</div>
				</div>
			</div>
			<!-- ######## end ######### -->
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>
