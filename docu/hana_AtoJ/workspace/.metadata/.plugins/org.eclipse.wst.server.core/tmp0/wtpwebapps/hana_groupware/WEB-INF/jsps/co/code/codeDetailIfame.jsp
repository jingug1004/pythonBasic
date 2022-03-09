<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : codeDetailIfame.jsp
 * @메뉴명 : 코드상세정보
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");		//s_cd list
	CodeVO mCodeVo = (CodeVO)request.getAttribute("detailCode");					//m_cd data
	
	String cd = ""; //코드
	String cd_nm = ""; //코드이름
	String m_cd = ""; //메인코드
	String s_cd = ""; //서브코드
	String use_yn = ""; //사용여부
	String ordering = ""; //정렬
	String descr = ""; //설명
	boolean safe = false; //신규인지 수정인지 확인하는 플래그
	
	/* 코드목록에서 클릭했을시 해당코드 상세정보를 셋팅을 한다. */
	if(mCodeVo != null){
		cd = StringUtil.nvl(mCodeVo.getCd());
		cd_nm = StringUtil.nvl(mCodeVo.getCd_nm());
		m_cd = StringUtil.nvl(mCodeVo.getM_cd());
		s_cd = StringUtil.nvl(mCodeVo.getS_cd());
		use_yn = StringUtil.nvl(mCodeVo.getUse_yn());
		ordering = StringUtil.nvl(mCodeVo.getOrdering());
		descr = StringUtil.nvl(mCodeVo.getDescr());
		safe = true;
	}
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	$(window).load(function() {
		$("#s_cd_dis").val("000");	
	});
		/**
		 * 코드 삭제
		 * @returns
		 */
		function deleteCode(){
			var cd = $("#td_cd").text();
			var s_cd = $("#s_cd_dis").val();
			var m_cd = $("#m_cd").val();
			if(cd == ""){
				alert("코드를 선택해 주세요.");
				return;
			}
			if(confirm(cd + "를 삭제 하시겠습니까?") == true){
				if(s_cd == '000'){	//서브코드 일괄 삭제
					if(confirm(m_cd + "하위 코드가 전부 삭제 됩니다.\n그래도 삭제 하시겠습니까?") == true){
						document.frm.m_cd.value = m_cd;
						document.frm.s_cd.value = s_cd;
						document.frm.action="<%=GROUP_ROOT%>/co/code/deleteCode.do";
						document.frm.submit();					
					}
				}else{
					document.frm.m_cd.value = m_cd;
					document.frm.s_cd.value = s_cd;
					document.frm.action="<%=GROUP_ROOT%>/co/code/deleteCode.do";
					document.frm.submit();
				}
			}
		}
		
		/**
		 * 코드 수정
		 * @returns
		 */
		function updateCode(){
			var cd = $("#td_cd").text();
			if(cd == ""){
				alert("코드를 선택해 주세요.");
				return;
			}
			
			if(confirm(cd + "를 수정 하시겠습니까?") == true){
				document.frm.action="<%=GROUP_ROOT%>/co/code/updateCode.do";
				document.frm.submit();
			}
		}
		
		/**
		 * 코드 저장
		 * @returns
		 */
		function codeSave(){
			var cd = $("#td_cd").text();
			var cd_nm = document.frm.cd_nm.value;
			var m_cd = document.frm.m_cd_dis.value;
			var s_cd = document.frm.s_cd_dis.value;
			var use_yn = document.frm.use_yn.value;
			var descr = document.frm.descr.value;
			var ordering = document.frm.ordering.value;
			
			document.frm.m_cd.value = m_cd;
			document.frm.s_cd.value = s_cd;
			
			if(m_cd == ""){
				alert("메인코드를 입력해 주세요.");
				document.frm.m_cd_dis.focus();
				return;
			}else if(!check_m_cd()){
				return;
			}else if(s_cd == ""){
				alert("서브코드을 입력해 주세요.");
				document.frm.s_cd.focus();
				return;
			}if(cd_nm == ""){
				alert("코드명을 입력해 주세요.");
				document.frm.cd_nm.focus();
				return;
			}else if(formCheck.getByteLength(cd_nm) > 50){
				alert("코드명은 한글 25자 (영문 50자) 이상 입력할수 없습니다");
				document.frm.cd_nm.focus();
				return;
			}else if(!check_s_cd()){
				return;
			}else if(formCheck.getByteLength(descr) > 200){
				alert("코드설명은 한글 100자 (영문 200자) 이상 입력할수 없습니다");
				document.frm.descr.focus();
				return;
			}else if(use_yn == ""){
				alert("사용여부를 선택해 주세요.");
				document.frm.use_yn.focus();
				return;
			}else if(ordering == ""){
				alert("정렬번호를 입력해 주세요.");
				document.frm.ordering.focus();
				return;
			}else{
				if(status == 0 ){
					if(confirm("저장 하시겠습니까?") == true){
						document.frm.action="<%=GROUP_ROOT%>/co/code/insertCode.do?status=0";
						document.frm.submit();
					}else{
						document.location.reload();
					}
				}else if(status == 1){
					if(confirm("저장 하시겠습니까?") == true){
						document.frm.action="<%=GROUP_ROOT%>/co/code/insertCode.do?status=1";
						document.frm.submit();
					}else{
						document.location.reload();
					}
				}else if(status !=4 && status == 2){
					if(confirm("수정 하시겠습니까?") == true){
						document.frm.action="<%=GROUP_ROOT%>/co/code/insertCode.do?status=2";
						document.frm.submit();
					}else{
						document.location.reload();
					}
				}else if(status == 4 && status == 2){
					alert("이미 등록된 코드 입니다.");
					document.frm.m_cd_dis.focus();
					return;
				}else{
					alert("등록이 불가능 합니다.");
					return;
				}
			}
		}
		
		/**
		 * 코드 입력값 초기화
		 * @returns
		 */
		function createCode(){
			status = 3;
			document.frm.m_cd_dis.value = "";
			document.frm.m_cd_dis.disabled = false;
			document.frm.s_cd.value = "000";
			document.frm.s_cd_dis.value = "000";
			document.frm.s_cd_dis.disabled = true;
			document.frm.cd_nm.value = "";
			document.frm.descr.value = "";
			document.frm.use_yn.value = "Y";
			document.frm.ordering.value = "";
			$("#td_cd").html("");
			document.frm.m_cd_dis.focus();
		}
		
		/**
		 * 코드 정렬 필드 숫자만 입력 가능
		 * @returns
		 */
		function check_ordering(){
			var szNum = document.frm.ordering.value;
			var num_check=/^[0-9]*$/;
			if(!num_check.test(szNum)){
				document.frm.ordering.value = "";
				document.frm.ordering.focus();
				return false;
			}
		}
		
		/**
		 * 코드 상세정보
		 * @param i
		 * @returns
		 */
		function codeView(i){
			var cd = $("#cd"+i).text();
			var s_cd = $("#s_cd"+i).text();
			var cd_nm = $("#cd_nm"+i).text();
			var descr = $("#descr"+i).text();
			var use_yn = $("#use_yn"+i).text();
			var ordering = $("#ordering"+i).text();
			$("#td_cd").text(cd);
			$("#s_cd_dis").val(s_cd);
			$("#cd_nm").val(cd_nm);
			$("#descr").val(descr);
			$("#ordering").val(ordering);
			$("#use_yn > option[value=" + use_yn + "]").attr("selected","true");
		}
		
		/**
		 * 메인코드 벨리데이션 체크
		 * @returns
		 */
		function check_m_cd(){
			var m_cd = $("#m_cd").val();
			var s_cd = $("#s_cd").val();
			var cd = m_cd + s_cd;
			var filter = /^[A-Z]{1}[A-Z0-9]{2,3}$/;
			
			if(filter.test(m_cd)){
				if(s_cd != ""){	//서브코드가 입력되어 있어야만 코드셋팅한다.(메인코드+서브코드) 
					$("#td_cd").text(cd);
				}
				return true;
			}else{
				alert("첫글자는 대문자 영어로 시작되어야 하고 나머지 숫자 2자리가 되야 합니다.");
				$("#m_cd").val("");
				$("#m_cd").focus();
				if(s_cd != "" && filter.test(m_cd)){	//서브코드가 입력되어 있어야만 코드셋팅한다.(메인코드+서브코드) 
					$("#td_cd").text(cd);
				}
				return false;
			}
		}
		
		var status = "3";	// 코드 상태(등록된건지, 이미등록된건지..)
	
		/**
		 * 서브코드 벨리데이션 체크
		 * @returns
		 */
		function check_s_cd(){
			var m_cd = $("#m_cd").val();
			var s_cd = $("#s_cd").val();
			var cd = m_cd + s_cd;
			var filter = /^[0-9]{3,3}$/;
			var url = "<%=GROUP_ROOT %>/co/code/checkCode.do?cd=" + cd;
			if(filter.test(s_cd)){
				$.ajax({
					type: "POST",
					url: url,
					async : false,
					data: cd,
					success: function(result){
						var str = /^[A-Z]{1}[A-Z0-9]{5,6}$/;
						if(result == 0 && str.test(cd)){	//등록 가능한 코드이면서 코드가 첫글자는 대문자영어로, 그뒤 영어대문자,숫자 5자리가 맞는지
							$("#td_cd").text(cd);
							status = 0;		
						}else if(result == 1 && str.test(cd)){ //status 0.사용가능, 1.재사용, 2.이미등록된코드 
							$("#td_cd").text(cd);
							status = 1;	
						}else if(result == 2 && str.test(cd)){
							$("#td_cd").text(cd);
							status = 2;
						}
					}
				});
				if(status == 0){		//0.등록가능
					status = 0;
					return true;	
				}else if(status == 1){	//1.재사용
					status = 1;
					return true;
				}else if(status == 2){	//2.이미등록된코드
					if(!<%=safe%>){
						alert("이미등록된 코드입니다.");
						document.frm.m_cd_dis.focus();
						return;
					}else{
						status = 2;
						return true;
					}
				}
			}else{
				alert("숫자 3자리만 가능합니다.");
				$("#s_cd").val("");
				$("#s_cd").focus();
				document.location.reload();
				return false;
			}
		}
	</script>
</head>
<body>
	<form id="frm" name="frm" method="post">
	<input type="hidden" id="cd" name="cd" />
	<input type="hidden" id="m_cd" name="m_cd" value="<%=m_cd %>"/>
	<input type="hidden" id="s_cd" name="s_cd" value="<%=s_cd %>"/>
	<input type="hidden" id="cd" name="cd" />
	<div class="menu_box w405">
		<div class="cont_tit_box">
			<h3 class="mt_5 fl">프로그램</h3>
			<div class="fr">
				<button type="button" class="type_01" onClick="javascript:createCode();">신규</button>
				<button type="button" class="type_01" onClick="javascript:deleteCode();">삭제</button>
			</div>
		</div>
		<div class="cont_box cont_table03">
			<table>
			<colgroup>
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
			</colgroup>
				<tbody>
					<tr>
						<th>코드</th>
						<td colspan="3" id="td_cd"><%=cd %></td>
					</tr>
					<tr>
						<th><label>메인코드</label></th>
						<td><input type="text" id="m_cd_dis" name="m_cd_dis" value="<%=m_cd %>" maxlength="3" <%if(safe){%>disabled="disabled"<%} %>/></td>
						<th><label>서브코드</label></th>
						<td><input type="text" id="s_cd_dis" name="s_cd_dis" value="" maxlength="3" <%if(!safe){%>disabled="disabled"<%} %>/></td>										
					</tr>
					<tr>
						<th><label id="td_cd_nm">코드명</label></th>
						<td colspan="3" class="url_box pr14"><input type="text" class="w_reset" id="cd_nm" name="cd_nm" value="<%=cd_nm %>" /></td>
					</tr>
					<tr>
						<th><label>코드설명</label></th>
						<td colspan="3" class="url_box pr14"><input type="text" class="w_reset" id="descr" name="descr" value="<%=descr %>" /></td>	
					</tr>
					<tr>
						<th class="bb_none"><label>사용여부</label></th>
						<td class="bb_none">
							<select id="use_yn" name="use_yn">
								<option value="Y" <%if("Y".equals(use_yn)||"".equals(use_yn)){%>selected="selected"<% }else{}%> >Y</option>
								<option value="N" <%if("N".equals(use_yn)){%>selected="selected"<% }else{}%> >N</option>
							</select>
						</td>
						<th class="bb_none"><label>정렬</label></th>
						<td class="bb_none">
							<input type="text" id="ordering" name="ordering" value="<%=ordering %>" onkeyup="javascript:check_ordering();" maxlength="2" />
						</td>										
					</tr>
				</tbody>
			</table>
		</div>
		<div class="btn_save_r">
			<button type="button" class="type_01" onclick="javascript:codeSave();">저장</button>
		</div>					
	</div>
	<div class="menu_box mt_20">
		<div class="cont_tit_box">
			<h3 class="mt5">하위목록</h3>
		</div>
		<div class="cont_box02 cont_table04">
			<table>
				<colgroup>
					<col width="18%" />
					<col width="18%" />
					<col width="33%" />
					<col width="18%" />
					<col width="13%" />
				</colgroup>
				<tbody>
				<tr>
					<th>코드</th>
					<th>서브코드</th>
					<th>코드명</th>
					<th>사용여부</th>
					<th class="br_none">정렬</th>
				</tr>
				<%
				if(sCodeList.size()!=0){
					for(int i=0; sCodeList.size()>i; i++){
						CodeVO codeVo = new CodeVO();
						codeVo=sCodeList.get(i);
				%>
				<tr onclick="javascript:codeView(<%=i%>);" style='cursor:pointer;' class="bg_d">
					<td id="cd<%=i%>"><%=codeVo.getCd() %></td>
					<td id="s_cd<%=i%>" class="fc_re"><%=codeVo.getS_cd()%></td>
					<td id="cd_nm<%=i%>"><%=codeVo.getCd_nm()%></td>
					<td id="use_yn<%=i%>"><%=codeVo.getUse_yn()%></td>
					<td id="ordering<%=i%>" class="br_none"><%=codeVo.getOrdering() %></td>
					<td id="descr<%=i%>" style="display: none;"><%=StringUtil.nvl(codeVo.getDescr())%></td>
				</tr>
				<%	
					}
				}
				%>					
				</tbody>
			</table>
		</div>
	</div>
	</form>
</body>
</html>