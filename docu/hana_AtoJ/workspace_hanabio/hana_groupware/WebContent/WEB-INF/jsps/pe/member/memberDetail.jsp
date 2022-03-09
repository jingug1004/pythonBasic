<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : memberDetail.jsp
 * @메뉴명 : 개인정보 상세
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	MemberVO memberDetailVO = (MemberVO)request.getAttribute("memberDetail");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<div class="cont float_left">
				<h2>개인정보</h2>
				<div class="wrap_personal_info_edit">
					<table>
						<colgroup>
							<col style="width:110px;" />
							<col style="width:230px;" />
							<col style="width:110px;" />
							<col />
							<col />
						</colgroup>
						<tr class="no_bd">
							<th>사원번호</th>
							<td class="photo_pd"><%=memberDetailVO.getEmp_no() %><button type="button" class="type_01 ml35" onclick="javascript:Commons.popupOpen('passChg', '<%=GROUP_ROOT%>/co/login/passwordChangeForm.do', '290', '239'); return false;">비밀번호 변경</button></td>
							<th>성명</th>
							<td><%=StringUtil.nvl(memberDetailVO.getEmp_ko_nm()) %></td>
							<td rowspan="4" class="photo"><img src="<%=GROUP_ROOT %>/pe/member/memberPhoto/<%=StringUtil.nvl(memberDetailVO.getEmp_no()) %>.do"  onerror="this.src='<%=GROUP_WEB_ROOT%>/img/character.gif'" alt="사진" alt="사진"  /></td>
						</tr>
						<tr>
							<th>성명(영문)</th>
							<td><%=StringUtil.nvl(memberDetailVO.getEmp_en_nm()) %></td>
							<th>부서</th>
							<td><%=StringUtil.nvl(memberDetailVO.getDept_ko_nm()) %></td>
						</tr>
						<tr>
							<th>직급</th>
							<td><%=StringUtil.nvl(memberDetailVO.getGrad_ko_nm()) %></td>
							<th>진급일자</th>
							<td><%=StringUtil.nvl(memberDetailVO.getProcm_dt()) %></td>
						</tr>
						<tr>
							<th>입사일자</th>
							<td><%=StringUtil.nvl(memberDetailVO.getEncmpy_dt()) %></td>
							<th>생년월일</th>
							<td><%=StringUtil.nvl(memberDetailVO.getBirth_dt()) %></td>
						</tr>
					</table>
					<table>
						<colgroup>
							<col style="width:110px;" />
							<col style="width:230px;" />
							<col style="width:110px;" />
							<col />
						<tr class="no_bd">
							<th colspan="4" class="ta_c">추가정보</th>
						</tr>
						<%
							String zip_cd = StringUtil.nvl(memberDetailVO.getZip_cd(), "") ;
							/* CHOE 20150730 우편번호 변경으로 아래 코들 막는다
							if(!zip_cd.equals("") && zip_cd!= null){
								zip_cd  = zip_cd.substring(0, 3) +"-"+zip_cd.substring(3, 6) ;								
							}*/
						%>
						<tr>
							<th>우편번호</th>
							<td colspan="3">
								<span class="txt"><%=zip_cd %></span>
							</td>
						</tr>
						<tr>
							<th>상세주소</th>
							<td colspan="3"><%=StringUtil.nvl(memberDetailVO.getAddr1()) %> <%=StringUtil.nvl(memberDetailVO.getAddr2()) %></td>
						</tr>
						<tr>
							<th>영문주소</th>
							<td colspan="3"><%=StringUtil.nvl(memberDetailVO.getAddr_en()) %></td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td><%=StringUtil.nvl(memberDetailVO.getTel_no()) %></td>
							<th>사내전화번호</th>
							<td><%=StringUtil.nvl(memberDetailVO.getIn_tel()) %></td>
						</tr>
						<tr>
							<th>모바일전화</th>
							<td><%=StringUtil.nvl(memberDetailVO.getPager_no()) %></td>
							<th class="bdnone">이메일</th>
							<td class="bdnone"><%=StringUtil.nvl(memberDetailVO.getE_mail()) %></td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="3" class="last"><%=StringUtil.nvl(memberDetailVO.getAuth_nm()) %></td>
						</tr>
					</table>
					
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>