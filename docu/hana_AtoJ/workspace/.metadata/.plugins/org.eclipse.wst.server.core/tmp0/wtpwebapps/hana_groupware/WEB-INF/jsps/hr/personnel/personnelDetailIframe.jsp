<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : personnelDetailIframe.jsp
 * @메뉴명 : 인사현황조회 iframe
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ page import="com.hanaph.gw.hr.personnel.vo.PersonnelVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	//인사현황
	List<PersonnelVO> personnelCountList = (List<PersonnelVO>)request.getAttribute("personnelCountList");
	//발령현황
	List<PersonnelVO> appointmentCountList = (List<PersonnelVO>)request.getAttribute("appointmentCountList");
	//사용자
	List<MemberVO> memberList = (List<MemberVO>)request.getAttribute("memberList");
	//사용자 검색
	String emp_ko_nm = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("emp_ko_nm"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		function goSearch(){
			if(formCheck.isNull(document.getElementById("emp_ko_nm"), "성명을 입력하여 주십시오.")){
				return;
			}
			
			if(formCheck.containsChars(document.getElementById("emp_ko_nm").value, "%")){
				 alert("특수문자를 사용할수 없습니다");
				 return;
			}
			document.getElementById("frm").submit();
		}
	</script>
</head>
<body>

	<div class="wrap_personnel">
		<h4>인사현황</h4>
		<div class="personnel">
			<table>
				<%
				if(personnelCountList.size()!=0){
					for(int i=0; personnelCountList.size()>i;i++){
						PersonnelVO personnelVO = new PersonnelVO();
						personnelVO = personnelCountList.get(i);
				%>
				<tr>
				    <th><%= StringUtil.nvl(personnelVO.getDept_nm())%></th>
				    <td><%= personnelVO.getPersonnel_cnt()%></td>
			   	</tr>
		   	<%} 
			
			}%>
			</table>
		</div>
	</div>
	<div class="wrap_appointment">
		<h4>발령현황</h4>
		<div class="appointment">
			<table>
				<thead>
					<tr>
						<th>구분</th>
						<th>오늘</th>
						<th>이달</th>
						<th>올해</th>
						<th>지난해</th>
					</tr>
				</thead>
				<tbody>
				<%
				if(appointmentCountList.size()!=0){
					for(int i=0; appointmentCountList.size()>i;i++){
						PersonnelVO personnelVO = new PersonnelVO();
						personnelVO = appointmentCountList.get(i);
				%>
								
					<tr>
				   		<td><%= StringUtil.nvl(personnelVO.getEncmpy_cd())%></td>
					    <td><%= personnelVO.getToday_cnt()%></td>
					    <td><%= personnelVO.getMonth_cnt()%></td>
					    <td><%= personnelVO.getYear_cnt()%></td>
					    <td><%= personnelVO.getLast_year_cnt()%></td>
				   	</tr>
			   	<%} 
				
				}%>
				</tbody>
			</table>
		</div>
	</div>
	<div class="wrap_attach">
		<div class="attach_box">
			<h4 class="fl">소속사용자</h4>
			<form name="frm" id="frm" method="post" onsubmit="return false;" action="<%=GROUP_ROOT %>/hr/personnel/personnelDetailIframe.do"> 
			<div class="search_box04 fr">
				<input type="text" name="emp_ko_nm" id="emp_ko_nm" value="<%=emp_ko_nm%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}"/ >
				<button class="search" onclick="javascript:goSearch(); return false;">검색</button>
			</div>
			</form>
		</div>
		<div class="attach">
			<table>
				<colgroup>
					<col style="width:108px"/>
					<col style="width:70px"/>
					<col style="width:52px"/>
					<col style="width:74px"/>
					<col style="width:71px"/>
					<col style=""/>
				</colgroup>
				<thead>
					<tr>
						<th class="nbd_l">부서</th>
						<th>사원번호</th>
						<th>성명</th>
						<th>직급</th>
						<th>이메일</th>
						<th class="nbd_r">사내전화번호</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="6" class="inner">
							<div class="inner_box">
								<table>
									<colgroup>
										<col style="width:108px"/>
										<col style="width:70px"/>
										<col style="width:52px"/>
										<col style="width:74px"/>
										<col style="width:71px"/>
										<col style=""/>
									</colgroup>
									<tbody>
										<%
										if(memberList.size()!=0){
											for(int i=0; memberList.size()>i;i++){
												MemberVO memberVO = new MemberVO();
												memberVO = memberList.get(i);
										%>
														
										<tr>
											<td class="nbd_l"><%= StringUtil.nvl(memberVO.getDept_ko_nm())%></td>
										   	<td><%= StringUtil.nvl(memberVO.getEmp_no())%></td>
										    <td><%= StringUtil.nvl(memberVO.getEmp_ko_nm())%></td>
										    <td><%= StringUtil.nvl(memberVO.getGrad_ko_nm())%></td>
										    <td><%= StringUtil.nvl(memberVO.getE_mail())%></td>
										    <td class="nbd_r"><%= StringUtil.nvl(memberVO.getIn_tel())%></td>
									   	</tr>
									   	<%} 
										
										}%>
									</tbody>
								</table>
							</div>
						</td>
					</tr>	
				</tbody>
			</table>
		</div>
		<div class="wrap_result">
			<span><%=memberList.size() %>명</span>
		</div>	
	</div>

			<!-- ######## end ######### -->
</body>
</html>