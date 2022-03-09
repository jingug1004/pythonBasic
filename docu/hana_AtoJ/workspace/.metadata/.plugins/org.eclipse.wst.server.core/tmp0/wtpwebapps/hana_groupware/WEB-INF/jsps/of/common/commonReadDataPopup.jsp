<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : commonReadDataPopup.jsp
 * @메뉴명 : 조회 상세 데이터 팝업
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.of.common.vo.CommonTargetVO" %>  
<%@ include file ="/common/path.jsp" %>
<%
	//읽은데이터리스트
	List<CommonTargetVO> memberReadDataList = (List<CommonTargetVO>)request.getAttribute("memberReadDataList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
</head>
<body>
	<div class="noti_pop">
		<div class="pop_box">
			<p class="btn_del"><button onclick="window.parent.layerClose();"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_close01.gif" alt="닫기" /></button></p>
			<div class="cont_box03" style="width: 500px;">
				<table>
					<colgroup>
						<col width="20%" />
						<col width="15%" />
						<col width="10%" />
						<col width="25%" />
					</colgroup>
					<tbody>
						<tr>
							<th>부서명</th>
							<th>사용자</th>
							<th>확인여부</th>
							<th class="br_none">확인일자</th>
						</tr>
						<tr>
						<%
							if(memberReadDataList.size()!=0){
								for(int i=0; memberReadDataList.size()>i;i++){
									CommonTargetVO commonTargetVO = new CommonTargetVO();
									commonTargetVO = memberReadDataList.get(i);
						%>
						<tr>
							<td><%=StringUtil.nvl(commonTargetVO.getDept_ko_nm()) %></td>
							<td><%=StringUtil.nvl(commonTargetVO.getEmp_ko_nm()) %></td>
							<td><%if(!"Y".equals(commonTargetVO.getRead_yn())){%>N<%}else{%>Y<%} %></td>
							<td class="br_none"><%=StringUtil.nvl(commonTargetVO.getRead_dt()) %></td>
						</tr>
						<%
							}
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
