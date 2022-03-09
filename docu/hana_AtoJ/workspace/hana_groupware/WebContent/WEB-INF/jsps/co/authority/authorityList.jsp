<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : authorityList.jsp
 * @메뉴명 : 권한리스트
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %> 
<%@ page import="com.hanaph.gw.co.authority.vo.AuthorityVO" %>     
<%@ include file ="/common/path.jsp" %>
<%
	List<AuthorityVO> authorityList = (List<AuthorityVO>)request.getAttribute("authorityList");	//권한리스트
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색키워드
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"auth_nm"); //검색타입
	int cnt = ((Integer)request.getAttribute("cnt")).intValue(); //권한리스트 총카운트
	
	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	$(window).load(function() {
		$("#searchType > option[value=<%=searchType%>]").attr("selected","true");
	});
	
	/**
	 * 검색
	 */
	function goSearch(){
		
		if(formCheck.containsChars(document.getElementById("searchKeyword").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		
		document.searchForm.action = "<%=GROUP_ROOT%>/co/authority/authorityList.do";
		document.searchForm.submit();
	}
	
	/**
	 * 임직원 선택 레이어 팝업
	 * @param auth_seq
	 */
	function selectMember(auth_seq){
		document.frm.auth_seq.value = auth_seq;
		$('#layerAuthority').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="600px" height="707px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/pe/member/memberSelectPopup.do?seq=' + auth_seq + '&type=AUTH',
		});
	}
	
	/**
	 * 메뉴선택 레이어 팝업
	 * @param auth_seq
	 */
	function selectMenu(auth_seq){
		$('#layerAuthority').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="450px" height="600px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/co/authority/authorityMenuListPopup.do?auth_seq=' + auth_seq + '&',
		});
	}
	
	/**
	 * 레이어 팝업 닫기
	 */
	function layerClose(){ 
		$('#layerAuthority').bPopup().close();
	}
	
	/**
	 * 체크박스 전체 선택
	 */
	function allCheck3(){
		$("input[name=allCheck]:checkbox").each(function() {
    		if($(this).is(':checked')) {
                $("input[name=authCheck]").prop("checked", true);
            } else {
                $("input[name=authCheck]").prop("checked", false);
            }
		});
	}
	
	/**
	 * 권한 삭제
	 */
	function deleteAuthority(){
		var auth_seq = "";
		
		$("input[name='authCheck']:checkbox:checked").each(function(){
			auth_seq += $(this).val()+"|";
		});
		if($("input[name='authCheck']:checkbox:checked").length < 1){
			alert("삭제할 대상을 선택해 주세요.");
			return;
		}
		if(confirm("삭제 하시겠습니까?") == true){
			document.frm.auth_seq.value = auth_seq;
			document.frm.action = "<%=GROUP_ROOT%>/co/authority/deleteAuthority.do";
			document.frm.submit();
		}
	}
	
	/**
	 * 반드시 addMemberRow 로 function 만들어 테이블 상황에 맞게 테이블 생성한다.
	 * @param obj
	 */
	function addMemberRow(obj){
		var emp_no = "";
		 for(var i=0; i<obj.length; i++){
	    	if(obj[i].checked){
		    	var objParam = obj[i].value;
				var memberListValueArr = objParam.split("|");
				
				emp_no += memberListValueArr[0]+"|";
		    }
	    } 
		document.frm.emp_no.value = emp_no;
		document.frm.action = "<%=GROUP_ROOT %>/co/authority/insertAuthority.do";
		document.frm.submit();
	}
	
	/**
	 * 권한에 맵핑된 임직원 초기화
	 * @param auth_seq
	 * @param auth_nm
	 */
	function resetMember(auth_seq, auth_nm){
		if(confirm("\"" +auth_nm + "\" 권한에 맵핑된 직원들을 초기화 하시겠습니까?")){
			document.frm.auth_seq.value = auth_seq;
			document.frm.action = "<%=GROUP_ROOT %>/co/authority/resetAuthMember.do";
			document.frm.submit();
		}
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
			<div class="cont float_left">
				<h2>권한관리</h2>
				<div class="wrap_authority">
					<div class="wrap_search mb7">
					<form id="searchForm" name="searchForm" method="post">
						<div class="search">
							<select id="searchType" name="searchType">
								<option value="auth_nm">권한이름</option>
							</select>
							<input type="text" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" />
						</div>
						<div class="wrap_btn">
							<button class="btn_search" type="button" onclick="javascript:goSearch();">검색</button>
						</div>
					</form>
					</div>
					<div class="wrap_list">
						<div class="tableA">
						<form id="frm" name="frm" method="post">
						<input type="hidden" id="auth_seq" name="auth_seq" />
						<input type="hidden" id="emp_no" name="emp_no" />
							<table>
							<colgroup>
								<col width="50px" />
								<col width="50px" />
								<col width="200px" />
								<col width="200px" />
								<col width="130px" />
								<col width="150px" />
							</colgroup>
								<thead>
									<tr>
										<th><input type="checkbox" id="allCheck" name="allCheck" onclick="javascript:allCheck3();" /></th>
										<th>NO.</th>
										<th>권한이름</th>
										<th>권한설명</th>
										<th>메뉴선택</th>
										<th>임직원선택</th>
									</tr>
								</thead>
								<tbody>
								<%
									if(authorityList.size()!=0){
										for(int i=0; authorityList.size()>i; i++){
											AuthorityVO authVO = new AuthorityVO();
											authVO=authorityList.get(i);
								%>
									<tr>
										<td><input type="checkbox" id="authCheck" name="authCheck" value="<%=authVO.getAuth_seq()%>"/></td>
										<td><%=authVO.getRnum()%></td>
										<td><div style="word-wrap:break-word; white-space:normal; display:inline-block; width:200px;"><%=authVO.getAuth_nm()%></div></td>
										<td><div style="word-wrap:break-word; white-space:normal; display:inline-block; width:200px;"><%=authVO.getDescr()%></div></td>
										<td><button type="button" class="type_01" onclick="javascript:selectMenu('<%=authVO.getAuth_seq()%>');">메뉴선택</button></td>
										<td>
											<button type="button" class="type_01" onclick="javascript:selectMember('<%=authVO.getAuth_seq()%>');" >임직원선택</button>
											<button type="button" class="type_01" onclick="javascript:resetMember('<%=authVO.getAuth_seq()%>','<%=authVO.getAuth_nm()%>');" >초기화</button>
										</td>
									</tr>
								<%
										}
									}else{
								%>
									<tr><td colspan="6">데이터가 없습니다.</td></tr>	
								<%
										}
									%>
								</tbody>
							</table>
						</form>
						</div>
						<div class="paging">
							<div class="wrap_total">
								* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(cnt)%></span>건
							</div>
							<div class="wrap_paging">
								<%@ include file ="/common/paging.jsp" %>
							</div>
							<div class="wrap_btn">
								<button type="button" class="type_01" onclick="javascript:deleteAuthority();">삭제</button>
								<button type="button" class="type_01" onclick="javascript:selectMenu('');">등록</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id='layerAuthority' style='display:none; width:auto; height:auto; '>
		<div class='member_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>