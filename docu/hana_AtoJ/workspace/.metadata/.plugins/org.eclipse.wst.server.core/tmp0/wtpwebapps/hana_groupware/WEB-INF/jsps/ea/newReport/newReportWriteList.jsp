<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : newReportWriteList.jsp
 * @메뉴명 : 신규문서작성
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<DocumentInfoVO> documentInfoList = (List<DocumentInfoVO>)request.getAttribute("documentInfoList");
	List<DepartmentVO> deptList = (List<DepartmentVO>)request.getAttribute("deptList");
	int documentInfoTotalCount = (Integer)request.getAttribute("documentInfoTotalCount");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
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
		<h2>신규문서작성</h2>
		<div class="settlement_box">
			<p class="list_t">* 신규문서 작성하기 위해 하단의 양식명을 클릭하시면 <span class="none_check">STEP1</span>으로 이동합니다.</p>
			<div class="cont_box cont_table05">
				<form id="frm" name="frm" method="post">
				<table>
					<colgroup>
						<col width="55px" />
						<col width="160px" />
						<col width="563px" />
					</colgroup>
						<thead>
							<tr>
								<th>NO.</th>
								<th>양식명</th>							
								<th class="br_none">설명</th>
							</tr>
						</thead>	
						<tbody>	
						<%
						if(documentInfoList.size()!=0){
							for(int i=0; documentInfoList.size()>i;i++){
								DocumentInfoVO documentInfoVO = new DocumentInfoVO();
								documentInfoVO = documentInfoList.get(i);
								/* 시간외근무내역서는 안 보여준다. */
								if(!"E01012".equals(documentInfoVO.getDocu_cd())){
						%>
								<tr style="cursor:pointer;" onclick="javascript:Commons.popupOpen('newReportWrite','<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do?docu_seq=<%=documentInfoVO.getDocu_seq() %>','820','850'); return false;">
									<td><%= documentInfoVO.getRnum() %></td>
									<td><%=RequestFilterUtil.toHtmlTagReplace("", documentInfoVO.getDocu_nm()) %></td>
									<td class="br_none"><%=RequestFilterUtil.toHtmlTagReplace("", documentInfoVO.getContents()) %></td>
								</tr>
						<%
								}
							}
						}else{
						%>
							<tr>
								<td colspan="3">데이터가 없습니다.</td>
							</tr>
						<%
						}
						%>
						</tbody>
					</table>
				</form>
			</div>		
			<div class="paging">
				<div class="wrap_total">
					* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(documentInfoTotalCount)%></span>건
				</div>
			</div>
		</div>
	</div>


<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>