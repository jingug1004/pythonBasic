<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : annualPlanFormPopup.jsp
 * @메뉴명 : 연차휴가사용계획서 등록 팝업
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%
	String rq_fr_dt = (String)request.getAttribute("rq_fr_dt");	
	String startMonth = (String)request.getAttribute("startMonth");	
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	String CodeCd;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		function goSave(){
			if(formCheck.isNull(document.getElementById("vacat_cd"), "증명서 종류를 선택해 주세요.")){
				return;
			}
			
			/*if(confirm("저장하시겠습니까?") == true){ CHOE 20160705 안상윤 차장 요청 등록시 확인 버튼이 너무 많다*/
				document.getElementById("frm").action = "<%=GROUP_ROOT %>/pe/member/annualPlanInsert.do";
				document.getElementById("frm").submit();
			/*}*/
		}
	</script>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>연차사용계획등록</h3>
				<div class="pop_con_all pop_register">
					<div class="pop_tit">
						<p class="tit"><%=rq_fr_dt %> 연차사용계획등록</p>
					</div>
					<form name="frm" id="frm" method="post" >
					<input type ="hidden" id="rq_fr_dt" name="rq_fr_dt" value="<%=rq_fr_dt%>">
					<input type ="hidden" id="startMonth" name="startMonth" value="<%=startMonth%>">
					<div class="search_box03">
						<select name="vacat_cd" id="vacat_cd" class="sel_certificate">
							<option value="">선택</option>
							<%
							if(sCodeList.size()!=0){
								for(int j=0; sCodeList.size()>j;j++){
									CodeVO codeVO = new CodeVO();
									codeVO = sCodeList.get(j);
									/*CHOE 20160701 연차와 반차만 등록 하도록*/
									CodeCd = codeVO.getCd();										
									if ( "E06020".equals(StringUtil.nvl(codeVO.getCd())) || "E06220".equals(StringUtil.nvl(codeVO.getCd())) ){
							%>
							<option value="<%=codeVO.getCd()%>"><%=codeVO.getCd_nm() %></option>
							<%
									}
								}
							}	
							%>	
						</select>
						<input class="w200" type="text" id="remark" name="remark" value="" />
					</div>
					</form>
				</div>
				<div class="btn_pop">
					<button type="button" class="type_01"  onclick="javascript:goSave(); return false;">확인</button>
					<button type="button" class="type_01" onclick="javascript:parent.layerClose(); return false;">취소</button>
				</div>
				<button type="button" class="close" onclick="javascript:parent.layerClose(); return false;"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->
</body>
</html>