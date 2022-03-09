<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : authorityMenuListPopup.jsp
 * @메뉴명 : 권한메뉴리스트
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.menu.vo.MenuVO" %>
<%@ page import="com.hanaph.gw.co.authority.vo.AuthorityVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%
	AuthorityVO authorityDetail = (AuthorityVO)request.getAttribute("authorityDetail");	//권한상세
	List<MenuVO> menuList = (List<MenuVO>)request.getAttribute("menuList");	//메뉴리스트
	List<MenuVO> rowSpan = (List<MenuVO>)request.getAttribute("authorityMenuRow");	//메뉴html tr태그 로우스팬값
	List<AuthorityVO> menuCodeList = (List<AuthorityVO>)request.getAttribute("menuCodeList"); //메뉴코드리스트
	
	String auth_seq = StringUtil.nvl((String)request.getAttribute("auth_seq"));	//권한 번호
	String auth_nm = ""; //권한이름
	String descr = ""; //권한설명
	if(authorityDetail != null){
		auth_nm = authorityDetail.getAuth_nm();
		descr = authorityDetail.getDescr();
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	/**
	 * 권한 등록
	 * @returns
	 */
	function insertAuthority(){
		var menu_cds = "";
		var auth_seq = "<%=auth_seq%>"; 
		if(auth_seq == ""){
			if(document.frm.auth_nm.value == ""){
				alert("권한이름을 입력해 주세요.");
				document.frm.auth_nm.focus();
				return;
			}else if(document.frm.descr.value == ""){
				alert("권한설명을 입력해 주세요.");
				document.frm.descr.focus();
				return;
			}else{
				$("input[name='menu_child']:checkbox:checked").each(function(){
					menu_cds += $(this).val()+"|";
				});
				document.frm.status.value = 1;
				document.frm.menu_cd.value = menu_cds;
				document.frm.action = "<%=GROUP_ROOT %>/co/authority/insertAuthority.do";
				document.frm.submit();
			}
		}else{
			if($("input[name='menu_child']:checkbox:checked").length < 1){
				alert("메뉴를 선택해 주세요.");
				return;
			}else if(document.frm.auth_nm.value == ""){
				alert("권한이름을 입력해 주세요.");
				document.frm.auth_nm.focus();
				return;
			}else if(document.frm.descr.value == ""){
				alert("권한설명을 입력해 주세요.");
				document.frm.descr.focus();
				return;
			}
			$("input[name='menu_child']:checkbox:checked").each(function(){
				menu_cds += $(this).val()+"|";
			});
			/* 레이어팝업 메뉴리스트 > form name을 가져와 파라미터 데이터 셋팅한다. */
			document.frm.auth_seq.value = "<%=auth_seq%>";
			document.frm.menu_cd.value = menu_cds;
			document.frm.status.value = 2;
			document.frm.action = "<%=GROUP_ROOT %>/co/authority/insertAuthority.do";
			document.frm.submit();
		}
	}
	
	/**
	 * 권한메뉴 3뎁스 자동체크
	 * @param obj
	 */
	function depthCheck(obj){
		var child =  obj.substr(0,4);
		if(obj.length == 6){
			if($("input[value="+obj+"]").is(":checked")){
				$("input[value="+child+"]").prop("checked",true);
				$("input[value="+child+"]").attr("disabled",true);
			}else{
				$("input[value="+child+"]").attr("disabled",false);
				
				$("input[name='menu_child']:checkbox:checked").each(function(){
					if(($(this).val().length == 6) && ($(this).val().substr(0,4) == child)){
						$("input[value="+child+"]").attr("disabled",true);
						$("input[value="+child+"]").prop("checked",true);
					}else{
						$("input[value="+child+"]").prop("checked",true);
					}
				});
			}
		}
	}
	</script>
</head>
<body>
<div class="wrap_authority_menu">
	<div class="authority_menu">
		<h3>메뉴선택</h3>
		<form id="frm" name="frm" method="post">
		<input type="hidden" id="status" name="status" />
		<input type="hidden" id="auth_seq" name="auth_seq" />
		<input type="hidden" id="menu_cd" name="menu_cd" />
		<div class="wrap_list">
			<table>
				<colgroup>
					<col style="width:116px;" />
					<col style="" />
					<col style="" />
				</colgroup>
				<tr>
					<th>권한이름</th>
					<td colspan="2"><input id="auth_nm" name="auth_nm" type="text" size="30" value="<%=auth_nm %>" /></td>
				</tr>
				<tr>
					<th>권한설명</th>
					<td colspan="2"><input id="descr" name="descr" type="text" size="30" value="<%=descr %>" /></td>
				</tr>
				<tr>
					<td colspan="3" class="inner">
					<div>
						<table>
							<colgroup>
								<col style="width:116px"/>
								<col style="width:245px"/>
								<col style=""/>
							</colgroup>
							<%
							int rowSpanCnt = 0;							
							for(int i = 0; menuList.size() > i; i++){
								MenuVO menuVO = new MenuVO();
								menuVO = menuList.get(i);
							%>
								<tr>
								<%if(menuVO.getMenu_child().length()==2){%>
								<%
								for(int k = 0; rowSpan.size() > k; k++){
									MenuVO menuVO1 = new MenuVO();
									menuVO1 = rowSpan.get(k);
									if(menuVO.getMenu_child().equals(menuVO1.getMenu_parents())){
										rowSpanCnt++;		
								 %>
									<th rowspan="<%=menuVO1.getCnt()+1%>">
								<%
									}
								}%>
								<%if(rowSpanCnt==0){ %>
									<th><%=menuVO.getMenu_nm() %></th><td></td><td></td>
								<%}else{
									rowSpanCnt = 0;
								%>
									<%=menuVO.getMenu_nm() %></th>
								<%}%>
								
								<%}else if(menuVO.getMenu_child().length()==6 || menuVO.getMenu_child().length()==4){%>					
									<td><%=menuVO.getMenu_nm() %></td>
									<td><input type="checkbox" name="menu_child" id="menu_child" value="<%=menuVO.getMenu_child() %>" onclick="javascript:depthCheck('<%=menuVO.getMenu_child() %>');"
										<%
										if(menuCodeList != null){
											for(int z=0; menuCodeList.size() > z; z++){
												AuthorityVO authorityVO = new AuthorityVO();
												authorityVO = menuCodeList.get(z);
												if(menuVO.getMenu_child().equals(authorityVO.getMenu_cd())){
													%>checked="checked"<%
												}
											}
										}
										%>/>
									</td>
								<%} %>
								</tr>
							<%
							}
							%>
						</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="wrap_box">
			<button type="button" class="type_01" onclick="javascript:insertAuthority();">저장</button>
		</div>
		</form>
		<button class="close" onclick="window.parent.layerClose();"><span class="blind">닫기</span></button>
	</div>
</div>

</body>
</html>