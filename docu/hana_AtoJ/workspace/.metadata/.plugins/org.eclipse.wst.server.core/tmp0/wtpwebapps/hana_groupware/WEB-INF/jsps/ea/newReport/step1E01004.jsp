<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E010041.jsp
 * @메뉴명 : step1신규문서작성-기화기기안서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.VaporizeVO" %>
<%
	VaporizeVO vaporizeVO = (VaporizeVO)request.getAttribute("vaporizeVO");
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	if(vaporizeVO == null){
		vaporizeVO = new VaporizeVO();
	}
%>
<script type="text/javascript">

<%--ml180118.ml11_T33 김진국 - step1E01004.jsp에 var alertMessage = "기화기 기안서는 시행 부서의 의견이 모두 등록 되어야" 주석처리 - 모든 문서가 공통적으로 변해서 이제 기화기기 샘플 해당 없음--%>
///*CHOE 20150626 전산팀장님 지시 사항 안내 MSG 출력*/
//$(window).load(function() {
//	var alertMessage = "기화기 기안서는 시행 부서의 의견이 모두 등록 되어야";
//	alertMessage += "\n\n1차 결재자에게 문서가 보입니다.";
//	alertMessage += "\n\n1차 결재자에게 문서가 보이지 않는 경우 시행 부서의";
//	alertMessage += "\n\n의견이 등록 되었는지 확인해 보시기 바랍니다.";
//
//	alert(alertMessage);
//});


	/**
	 * 벨리데이션 체크
	 * @returns {Boolean}
	 */
	function saveStep1(){
		if(formCheck.getByteLength(document.frm.subject.value) > 100){
			alert("제목은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.subject.focus();
			return;
		}else if(formCheck.isNull(document.frm.imposition_ymd, "시행일자를 선택해 주세요.")){
			$(".btn_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
			$(".btn_date").prev().focus();
			return;
		}else if(formCheck.isNullStr($('input:radio[name="kind_cd"]:checked').val())){
			alert("종류를 선택해 주세요.");
			document.frm.kind_cd[0].focus();
			return;
		}else if(formCheck.isNull(document.frm.cust_nm, "거래처명을 입력해 주세요.")){
			document.frm.cust_nm.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cust_nm.value) > 100){
			alert("거래처명은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.cust_nm.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cust_cd.value) > 100){
			alert("거래처 코드는 한글50자 영문100자 이상 입력할 수 없습니다.");
			document.frm.cust_cd.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.ceo_nm.value) > 100){
			alert("대표자명은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.ceo_nm.focus();
			return;
		}else if(formCheck.isNull(document.frm.model_qty, "수량을 입력해 주세요.")){
			document.frm.model_qty.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.model_qty.value) > 100){
			alert("기종 및 수량은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.model_qty.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.month_use_qty.value) > 100){
			alert("월사용수량은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.month_use_qty.focus();
			return;
		}else if(formCheck.isNull(document.frm.month_use_qty, "월사용수량을 입력해 주세요.")){
			document.frm.month_use_qty.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.unusual.value) > 2000){
			alert("특이사항은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.unusual.focus();
			return;
		}
		return true;
	}
	
	var kind_cd = "";
	
	/**
	 * 텍스트 문구 동적 추가
	 * @param obj
	 */
	function inner_text(obj){
		kind_cd = obj;
		
		document.frm.subject.value = document.frm.cust_nm.value + " 세보프란전용 기화기 " + kind_cd + " 요청건";
		document.frm.content.value = document.frm.cust_nm.value + " 세보프란전용 기화기 " + kind_cd + " 요청하오니 검토 후 재가 바랍니다.";
	}
	
	/**
	 * 종류 자동 선택
	 */
	function inner_kind(){
		if(document.frm.kind_cd.value == ""){
			alert("종류를 선택해 주세요.");
			document.frm.cust_nm.value = "";
			return;
		}
		document.frm.subject.value = document.frm.cust_nm.value + " 세보프란전용 기화기 " + kind_cd + " 요청건";
		document.frm.content.value = document.frm.cust_nm.value + " 세보프란전용 기화기 " + kind_cd + " 요청하오니 검토 후 재가 바랍니다.";
	}
	
	
</script>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_vaporizer">
		<colgroup>
			<col class="cb_w87">
			<col style="">
			<col style="width:86px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>시행일자</th>
				<td colspan="3" class="date bdrn">
					<span class="date_box">
						<input type="text" class="serch_date" id="imposition_ymd" name="imposition_ymd" readonly="readonly" value="<%=StringUtil.nvl(vaporizeVO.getImposition_ymd()) %>" />
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>
			<tr>
				<th>종류</th>
				<td colspan="3" class="bdrn">
					<ul>
					<%
					if(sCodeList.size()!=0){
						for(int i=0; sCodeList.size()>i; i++){
							CodeVO codeVo = new CodeVO();
							codeVo=sCodeList.get(i);
					%>
						<li <%if(i+1==sCodeList.size() || i+1==sCodeList.size()-1){%> class="mbn"<%} %>>
							<input type="radio" name="kind_cd" id="kind_cd<%=i%>" onclick="javascript:inner_text('<%=codeVo.getCd_nm() %>');" value="<%=codeVo.getCd()%>" <%if(StringUtil.nvl(vaporizeVO.getKind_cd()).equals(codeVo.getCd())){%>checked<%} %> />
							<label for="kind_cd<%=i%>"><%=codeVo.getCd_nm() %></label>
						</li>	
					<%
						}
					}
					%>
					</ul>
				</td>
			</tr>
			<tr>
				<th>1. 거래처명</th>
				<td><input type="text" name="cust_nm" id="cust_nm" class="ipt_code" onkeyup="javascript:inner_kind();" value="<%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCust_nm()) %>" /></td>
				<th>2. 거래처코드</th>
				<td class="bdrn"><input type="text" name="cust_cd" id="cust_cd" class="ipt_code" value="<%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCust_cd()) %>" /></td>
			</tr>
			<tr>
				<th>3. 대표자명</th>
				<td colspan="3" class="bdrn"><input type="text" name="ceo_nm" id="ceo_nm" value="<%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCeo_nm()) %>" /></td>
			</tr>
			<tr>
				<th>4. 기종 및 수량</th>
				<td><input type="text" name="model_qty" id="model_qty" class="ipt_code" value="<%=StringUtil.nvl(vaporizeVO.getModel_qty()) %>" /></td>
				<th>5. 월사용수량</th>
				<td class="bdrn"><input type="text" name="month_use_qty" id="month_use_qty" class="ipt_code" value="<%=StringUtil.nvl(vaporizeVO.getMonth_use_qty()) %>" /></td>
			</tr>
			<tr>
				<th>6. 내용</th>
				<td colspan="3" class="bdrn con"><input type="text" name="content" id="content" class="ipt_content" value="<%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getContent()) %>" /></td>
			</tr>
			<tr>
				<th>7. 특이사항</th>
				<td colspan="3" class="ta bdrn">
					<textarea name="unusual" id="unusual" class="ta_vaporizer" ><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getUnusual()) %></textarea>
				</td>
			</tr>
		</tbody>
	</table>
</div>	
						