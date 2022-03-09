<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : memberListPopup.jsp
 * @메뉴명 : 임직원 리스트 팝업
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	List<MemberVO> memberList = (List<MemberVO>)request.getAttribute("memberList");
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm"));
	String target = StringUtil.nvl((String)request.getAttribute("target"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	/**
	 * 검색
	 */
	function goSearch(){
		document.getElementById("frm").submit();
	}
	
	/**
	 * 부모창에 정보set 한다.
	 */
	function insertEmp_nm(){
		var emp_no = document.getElementsByName("emp_no");
		if(!formCheck.hasCheckedRadio(emp_no)){
			alert("사원을 선택해 주세요.");
			return;
		}
		var emp_no_value = $(':radio[name="emp_no"]:checked').val();
		parent.setEmp_nm(<%=target%>, emp_no_value);
		parent.layerClose('pop_memberSearch');
	}
	</script>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>사원조회</h3>
				<div class="pop_con_all pop_register">
					<div class="box_sign_overtime">
						<span class="tit">사원조회</span>				
					</div>
					<form name="frm" id="frm" method="post" onsubmit="return false;" action="<%=GROUP_ROOT %>/pe/member/memberListPopup.do"> 
					<input type="hidden" name="target" id="target" value="<%=target%>">
					<div class="search_box03 tac">
						<span>사원명</span>
						<input type="text" name="emp_ko_nm" id="emp_ko_nm" value="<%=emp_ko_nm%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}">
						<button class="search2" type="button" onclick="javascript:goSearch();">검색</button>
					</div>
					</form>
					<div class="box_scroll sop_tbl">
						<table class="sop_tbl2">
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
									<td colspan="4" class="box_scroll sop_tbl2_td">
										<div class="sop_tbl2_dv">
											<table class="tbl_record sop_tbl3">
											<colgroup>
												<col style="width:28px"/>
												<col style="width:112px"/>
												<col style="width:90px"/>
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
														<td><input type="radio" id="emp_no" name="emp_no" value="<%=memberVO.getDept_cd() %>|<%=memberVO.getDept_ko_nm() %>|<%=memberVO.getEmp_no() %>|<%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %>"></td>
														<td><%=StringUtil.nvl(memberVO.getDept_ko_nm()) %></td>
														<td><%=StringUtil.nvl(memberVO.getGrad_ko_nm()) %></td>
														<td class="bdrn"><%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %></td>
													</tr>
												<%
													}
												}else{
												%>
													<tr>
														<td colspan="4">데이터가 없습니다.</td>
													</tr>
												<%} %>
											</tbody>
											</table>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="btn_pop">
					<button type="button" class="type_01" onclick="javascript:insertEmp_nm(); return false;">확인</button>
					<button type="button" class="type_01" onclick="javascript:parent.layerClose('pop_memberSearch'); return false;">취소</button>
				</div>
				<button type="button" class="close" onclick="javascript:parent.layerClose('pop_memberSearch'); return false;"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->
</body>
</html>