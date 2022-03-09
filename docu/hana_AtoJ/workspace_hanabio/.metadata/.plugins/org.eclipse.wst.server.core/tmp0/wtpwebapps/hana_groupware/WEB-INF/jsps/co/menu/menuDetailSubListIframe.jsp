<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : menuDetailSubListIframe.jsp
 * @메뉴명 : 하위메뉴 상세 정보 리스트
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.menu.vo.MenuVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	List<MenuVO> menuSubList = (List<MenuVO>)request.getAttribute("menuSubList"); //하위메뉴리스트
	MenuVO menuDetail = (MenuVO)request.getAttribute("menuDetail"); //메뉴상세
	
	String menu_parents = ""; //부모메뉴
	String menu_child = "00"; //하위메뉴
	String menu_nm = ""; //메뉴이름
	String use_yn = ""; //사용여부
	String ordering = ""; //정렬
	String url = ""; //메뉴url
	String descr = ""; //메뉴상세
	String status = ""; //메뉴 중복체크 상태값(신규인지, 재등록인지, 이미등록된 메뉴인지..)
	
	/* 트리메뉴에서 클릭했을시 해당메뉴 상세정보를 셋팅을 한다. */
	if(menuDetail != null){
		menu_parents = StringUtil.nvl(menuDetail.getMenu_parents());
		menu_child = StringUtil.nvl(menuDetail.getMenu_child());
		menu_nm = StringUtil.nvl(menuDetail.getMenu_nm());
		use_yn = StringUtil.nvl(menuDetail.getUse_yn());
		url = StringUtil.nvl(menuDetail.getUrl());
		ordering = StringUtil.nvl(menuDetail.getOrdering());
		descr = StringUtil.nvl(menuDetail.getDescr());
		status = "3";
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		/**
		 * 메뉴 상세 정보
		 * @param i
		 * @returns
		 */
		function menuDetail(i){
			var menu_parents = $("#menu_parents"+i).text();
			var menu_child = $("#menu_child"+i).text();
			var menu_nm = $("#menu_nm"+i).text();
			var use_yn = $("#use_yn"+i).text();
			var url = $("#url"+i).text();
			var ordering = $("#ordering"+i).text();
			var descr = $("#descr"+i).text();
			
			$("#menu_parents").val(menu_parents);
			$("#menu_child").val(menu_child);
			$("#menu_nm").val(menu_nm);
			$("#use_yn").val(use_yn);
			$("#url").val(url);
			$("#ordering").val(ordering);
			$("#descr").val(descr);
		}
		
		var status = '<%=status%>';	//삭제된메뉴번호 구분상태
		
		/**
		 * 메뉴 상세 정보 필드 초기화
		 * @returns
		 */
		function menuCreate(){
			$("#td_menu_parents").text("00");
			document.frm.menu_child.value = "";
			document.frm.menu_nm.value = "";
			document.frm.use_yn.value = "Y";
			document.frm.url.value = "";
			document.frm.ordering.value = "";
			document.frm.descr.value = "";
			document.frm.menu_nm.focus();
			status = "";
		}
		
		/**
		 * 메뉴코드 중복체크
		 * @returns
		 */
		function menuCheck(){
			var menu_parents = "";
			var menu_child = "";
			
			if(document.frm.menu_child.value==""){
				document.frm.menu_child.value = $("#td_menu_parents").text();
				$("#td_menu_parents").text("");
			}else{
				menu_parents = $("#td_menu_parents").text();
				menu_child = document.frm.menu_child.value;
			}
			
			var url = "<%=GROUP_ROOT %>/co/menu/menuCheck.do";
				$.ajax({
					type: "POST",
					url: url,
					async : false,
					data:{
							menu_parents:menu_parents,
							menu_child:menu_child
						 },
					success: function(result){
						if(result==1){			//1.사용가능한메뉴번호
							status = 1;
						}else if(result==2){	//2.삭제된 메뉴Menu 재사용
							status = 2;
						}
					}
				});
			if(status == 1){
				return true;
			}else if(status == 2){
				status = 2;
				return true;
			}else{
				return false;
			}
		}
		
		/**
		 * 메뉴 등록
		 * @returns
		 */
		function insertMenu(){
			var menu_parents = $("#td_menu_parents").text();
			var menu_child = document.frm.menu_child.value;
			var menu_nm = document.frm.menu_nm.value;
			var url =  document.frm.url.value;
			var ordering = document.frm.ordering.value;
			var descr = document.frm.descr.value;
			var use_yn = document.frm.use_yn.value;
			var num_check=/^[0-9]*$/;
			var menu_cd_check= "";
			
			var temp_parents = 0;
			
			if(menu_child=="" && menu_parents != "00"){	
				document.frm.menu_child.value = $("#td_menu_parents").text();
				$("#td_menu_parents").text("");
			}else if(menu_parents == "00"){
				menu_cd_check=/^[0-9]{2,2}$/;
				if((!menu_cd_check.test(menu_child))){
					alert("숫자 2자리만 가능합니다.");
					document.frm.menu_child.focus();
					return;
				}
			/* 현재메뉴번호가 2자리(1depth)라면 하위메뉴번호는 4자리가 되고, 현재메뉴번호앞2자리와 같아야 된다. */
			}else if(menu_parents.length == 2){
				menu_cd_check=/^[0-9]{4,4}$/;
				temp_child = menu_child.substring(0,2);
				
				if((menu_parents != temp_child)){
					alert("현재메뉴번호 앞2자리 똑같아야 합니다..");
					document.frm.menu_child.focus();
					return;
				}else if((!menu_cd_check.test(menu_child))){
					alert("숫자4자리만 됩니다.");
					document.frm.menu_child.focus();
					return;
				} 
			/* 현재메뉴번호가 4자리(2depth)라면 하위메뉴번호는 6자리가 되고, 현재메뉴번호앞4자리와 같아야 된다. */
			}else if(menu_parents.length == 4){
				menu_cd_check=/^[0-9]{6,6}$/;
				temp_child = menu_child.substring(0,4);
				
				if((menu_parents != temp_child)){
					alert("현재메뉴번호 앞4자리 똑같아야 합니다..");
					document.frm.menu_child.focus();
					return;
				}else if((!menu_cd_check.test(menu_child))){
					alert("숫자6자리만 됩니다.");
					document.frm.menu_child.focus();
					return;
				} 
			}
			if(menu_nm == ""){
				alert("메뉴 이름을  입력해 주세요.");
				document.frm.menu_nm.focus();
				return;
			}else if(formCheck.getByteLength(menu_nm) > 50){
				alert("메뉴이름은 한글 25자 (영문 50자) 이상 입력할수 없습니다");
				document.frm.menu_nm.focus();
				return;
			}else if(formCheck.getByteLength(descr) > 200){
				alert("메뉴설명은 한글 100자 (영문 200자) 이상 입력할수 없습니다");
				document.frm.descr.focus();
				return;
			}else if(ordering == ""){
				alert("순서를 입력해 주세요");
				document.frm.ordering.focus();
				return;
			}else if(!num_check.test(ordering)){
				alert("숫자만 입력 가능합니다.");
				document.frm.ordering.value="";
				document.frm.ordering.focus();
				return;
			}else if(formCheck.getByteLength(url) > 200){
				alert("url이 너무 깁니다.");
				document.frm.url.focus();
				return;
			}else if(url == ""){
				alert("url를 입력해 주세요.");
				document.frm.url.focus();
				return;
			}
			if(!menuCheck()){ //이미 등록된 메뉴번호라면 수정을 한다.
				if(status != "3"  ){
					alert("이미등록된 메뉴번호 입니다.");
					document.frm.menu_child.focus();
					return;				
				}else if(status =="3"){
					if(menu_parents.length == 2 && use_yn == 'N'){
						if(confirm("이미등록된 메뉴번호 입니다.\n하위메뉴들이 전부 사용여부 N 으로 됩니다.\n수정 하시겠습니까?") == true){
							document.frm.menu_parents.value = $("#td_menu_parents").text();
							document.frm.action = "<%=GROUP_ROOT %>/co/menu/insertMenu.do?status=3";
							document.frm.submit();	
						}else{
							document.location.reload();	
						}
					}else if(confirm("이미등록된 메뉴번호 입니다.\n수정 하시겠습니까?") == true){
						document.frm.menu_parents.value = $("#td_menu_parents").text();
						document.frm.action = "<%=GROUP_ROOT %>/co/menu/insertMenu.do?status=1";
						document.frm.submit();
					}else{
						document.location.reload();
					}
				}
			}else if(menuCheck() && status == 2){//삭제되었던 메뉴번호라면 저장을 묻는다.
				if(confirm("저장 하시겠습니까?") == true){
					document.frm.menu_parents.value = $("#td_menu_parents").text();
					document.frm.action = "<%=GROUP_ROOT %>/co/menu/insertMenu.do?status=2";
					document.frm.submit();
				}else{
					document.location.reload();
				}
			}else if(menuCheck()){
				if(confirm("저장 하시겠습니까?") == true){
					document.frm.menu_parents.value = $("#td_menu_parents").text();
					document.frm.action = "<%=GROUP_ROOT %>/co/menu/insertMenu.do?status=0";
					document.frm.submit();
				}else{
					document.location.reload();
				}
			}
		}
		
		/**
		 * 메뉴 삭제
		 * @returns
		 */
		function deleteCode(){
			if(<%=menu_child%> == "00" ){
				alert("삭제할 수 없습니다.");
				return;
			}
			if(confirm("삭제 하시겠습니까?") == true){
				if(confirm("하위 메뉴들이 전부 삭제됩니다.\n그래도 삭제 하시겠습니까?") == true){
					document.frm.menu_parents.value = "<%=menu_parents%>";
					document.frm.menu_child.value = $("#td_menu_parents").text();
					document.frm.action = "<%=GROUP_ROOT %>/co/menu/deleteMenu.do";
					document.frm.submit();
				}
			}
		}
	</script>
</head>
<body>
	<div class="menu_box">
		<div class="cont_tit_box w502">
			<h3 class="mt_5 fl fs_14">메뉴</h3>
			<div class="fr">
				<button type="button" class="type_01" onclick="javascript:menuCreate();">신규</button>
				<button type="button" class="type_01" onclick="javascript:deleteCode();">삭제</button>
			</div>
		</div>
		<div class="cont_box cont_table01">
			<form id="frm" name="frm" method="post">
			<input type="hidden" id="menu_parents" name="menu_parents" />
			<table>
			<colgroup>
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
			</colgroup>
				<tbody>
					<tr>
						<th><label>현재메뉴번호</label></th>
						<td id="td_menu_parents"><%=menu_child %></td>
						<th><label>메뉴명</label></th>
						<td><input type="text" id="menu_nm" name="menu_nm" value="<%=menu_nm %>" /></td>										
					</tr>
					<tr>
						<th><label>하위메뉴번호</label></th>
						<td>
							<input type="text" id="menu_child" name="menu_child" value="" />
						</td>
						<th><label>메뉴설명</label></th>
						<td><input type="text" id="descr" name="descr" value="<%=descr %>" /></td>
					</tr>
					<tr>
						<th><label>사용여부</label></th>
						<td>
							<select id="use_yn" name="use_yn" >
								<option value="Y" <%if("Y".equals(use_yn)|| "".equals(use_yn)){%>selected="selected"<% }else{}%>>Y</option>
								<option value="N" <%if("N".equals(use_yn)){%>selected="selected"<% }else{}%>>N</option>
							</select>
						</td>
						<th><label>순서</label></th>
						<td><input type="text" id="ordering" name="ordering" value="<%=ordering %>" maxlength="2" /></td>										
					</tr>
					<tr>
						<th class="bb_none"><label>URL</label></th>
						<td colspan="3" class="url_box bb_none pr13"><input type="text" id="url" name="url" value="<%=url %>" /></td>
					</tr>																									
				</tbody>
			</table>
			</form>
		</div>
		<div class="btn_save_r w502">
			<button class="type_01" onclick="javascript:insertMenu();">저장</button>
		</div>				
	</div>
	<div class="menu_box mt_20">
		<div class="cont_tit_box">
			<h3 class="mt5 pt_5 fs_14">하위목록</h3>
		</div>
		<div class="cont_box cont_table02">
			<table>
			<colgroup>
				<col style="width:60px"/>
				<col style="width:100px"/>
				<col style="width:60px"/>
				<col style="width:30px"/>
				<col />			
			</colgroup>
				<tbody>
				<tr>
					<th>메뉴번호</th>
					<th>메뉴명</th>
					<th>사용여부</th>
					<th>순서</th>
					<th class="br_none">url</th>
				</tr>
				<%
				if(menuSubList.size()!=0){
					for(int i=0; menuSubList.size()>i; i++){
						MenuVO menuSubListVO = new MenuVO();
						menuSubListVO=menuSubList.get(i);
				%>
				<tr onclick="javascript:menuDetail(<%=i%>);" style='cursor:pointer;' class="bg_d">
					<td id="menu_parents<%=i%>" style="display: none"><%=menuSubListVO.getMenu_parents() %></td>
					<td id="menu_child<%=i%>"><%=menuSubListVO.getMenu_child() %></td>
					<td id="menu_nm<%=i%>" class="color_re"><%=menuSubListVO.getMenu_nm() %></td>
					<td id="use_yn<%=i%>"><%=menuSubListVO.getUse_yn() %></td>
					<td id="ordering<%=i%>"><%=menuSubListVO.getOrdering() %></td>
					<td id="url<%=i%>" class="list_txt br_none"><%=menuSubListVO.getUrl() %></td>
					<td id="descr<%=i%>" style="display: none"><%=menuSubListVO.getDescr() %></td>
				</tr>
				<%	
					}
				}
				%>
			</tbody>
			</table>
		</div>
	</div>
</body>
</html>