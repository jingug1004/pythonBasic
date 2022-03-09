<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.NewMadicineVO" %>
<%
	/*신약신청서*/
	NewMadicineVO newmadicineVO = (NewMadicineVO)request.getAttribute("newmadicineVO");
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	if(newmadicineVO == null){
		newmadicineVO = new NewMadicineVO();
	}
%>
<script type="text/javascript">

/*CHOE 20150413 yearendtaxcreditcardpopupup.jsp 참조 by 윤범진*/
$(document).ready(function(){
	
	/* 금액 입력 영역 유효성 체크 */
	$("input[type='text'].onlyNumber").keyup(function(event) {
		var keyCode = event.keyCode;
	    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
	    	$(this).val(formCheck.onlyNumber($(this).val()));	
		}
	});
});

function saveStep1(){
	/*	CHOE 20150330 1.길이 제한 2.입력확인(날짜 정합성)3.입력(NULL)확인  
	*/
	if(formCheck.isNull(document.frm.subject, "제목을 입력해 주세요.")){
		return;
	}else if(formCheck.getByteLength(document.frm.subject.value) > 100){
		alert("제목은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
		document.frm.subject.focus();
		return;
	}else if(formCheck.isNull(document.frm.imposition_ymd, "시행일자를 선택해 주세요.")){
		//$(".imposition_ymd_button").prev().datepicker({"dateFormat":"yy-mm-dd"});		
		$("imposition_ymd").focus();
		//document.frm.imposition_ymd.focus();
		return;
	}else if(formCheck.isNull(document.frm.hospital_nm, "1.병원명은 한글자 이상 적어주세요.")){		
		document.frm.hospital_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.hospital_nm.value) > 120){
		alert("1.병원명은 한글 60자 (영문 120자) 이상 입력할 수 없습니다");
		document.frm.hospital_nm.focus();
		return;
	}else if(formCheck.isNull(document.frm.medical_nm, "2.과명은 한글자 이상 적어주세요.")){		
		document.frm.medical_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.medical_nm.value) > 100){
		alert("2.과명은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
		document.frm.medical_nm.focus();
		return;
	}else if(formCheck.isNull(document.frm.doctor_nm, "3.Dr.성명/직책은 한글자 이상 적어주세요.")){		
		document.frm.doctor_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.doctor_nm.value) > 100){
		alert("3.Dr.성명/직책은은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
		document.frm.doctor_nm.focus();
		return;	
	}else if(formCheck.isNull(document.frm.item_nm, "4.제품명은 한글자 이상 적어주세요.")){		
		document.frm.item_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.item_nm.value) > 75){
		alert("4.제품명은 한글 35자 (영문 75자) 이상 입력할 수 없습니다");
		document.frm.item_nm.focus();
		return;	
	}else if(formCheck.isNull(document.frm.side_div, "5.원내외구분은 한글자 이상 적어주세요.(원내 또는 원외)")){		
		document.frm.side_div.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.side_div.value) > 20){
		alert("5.원내외구분은 한글 10자 (영문 20자) 이상 입력할 수 없습니다");
		document.frm.side_div.focus();
		return;	
	}else if(formCheck.isNull(document.frm.change_item, "6.교체품목은 한글자 이상 적어주세요.")){		
		document.frm.change_item.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.change_item.value) > 75){
		alert("6.교체품목은 한글 35자 (영문 75자) 이상 입력할 수 없습니다");
		document.frm.change_item.focus();
		return;	
	}else if(formCheck.isNull(document.frm.change_object, "6.교체대상은 한글자 이상 적어주세요.")){		
		document.frm.change_object.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.change_object.value) > 75){
		alert("6.교체대상은 한글 35자 (영문 75자) 이상 입력할 수 없습니다");
		document.frm.change_object.focus();
		return;	
	}else if(formCheck.isNull(document.frm.add_item, "6.추가품목은 한글자 이상 적어주세요.")){		
		document.frm.add_item.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.add_item.value) > 75){
		alert("6.추가품목은 한글 35자 (영문 75자) 이상 입력할 수 없습니다");
		document.frm.add_item.focus();
		return;	
	}else if(formCheck.isNull(document.frm.time_limit_dt, "7.요청기한를 선택해 주세요.")){
		//$(".item_limit_dt_button").prev().datepicker({"dateFormat":"yy-mm-dd"});
		//$(".item_limit_dt_button").prev().focus();
		$("time_limit_dt").focus();
		//document.frm.time_limit_dt.focus();
		return;
	}else if(formCheck.isNull(document.frm.discharge_dt, "8.D/C일자를 선택해 주세요.")){
		//$(".discharge_dt_button").prev().datepicker({"dateFormat":"yy-mm-dd"});
		//$(".discharge_dt_button").prev().focus();
		$("discharge_dt").focus();
		//document.frm.discharge_dt.focus();
		return;
	}else if(formCheck.isNull(document.frm.begin_dt, "9.처방개시일를 선택해 주세요.")){
		//$(".begin_dt_button").prev().datepicker({"dateFormat":"yy-mm-dd"});
		//$(".begin_dt_button").prev().focus();
		$("begin_dt").focus();
		//document.frm.begin_dt.focus();
		return;
	}else if(formCheck.isNull(document.frm.estimated_sales_item_1, "10.예상월매출 1번 품목은 한글자 이상 적어주세요.")){		
		document.frm.estimated_sales_item_1.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_1.value) > 20){
		alert("10.예상월매출 1번 품명은 한글 10자 (영문 20자) 이상 입력할 수 없습니다.");
		document.frm.estimated_sales_item_1.focus();
		return;	
	}else if(formCheck.isNull(document.frm.estimated_sales_amt_1, "10.예상월매출 1번 금액을 입력해 주세요.")){
		document.frm.estimated_sales_amt_1.focus();  /*CHOE 20150330 1번 항목은 꼭 있어야 한다.*/
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_1.value) > 10){
		alert("10.예상월매출 1번 금액은 10자 이상 입력할 수 없습니다"); 
		document.frm.estimated_sales_amt_2.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_2.value) > 20){
		alert("10.예상월매출 2번 품명은 한글 10자 (영문 20자) 이상 입력할 수 없습니다"); 
		document.frm.estimated_sales_item_2.focus();
		return;			
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_2.value) > 10){
		alert("10.예상월매출 2번 금액은 10자 이상 입력할 수 없습니다"); 
		document.frm.estimated_sales_amt_2.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_3.value) > 20){
		alert("10.예상월매출 3번 품명은 한글 10자 (영문 20자) 이상 입력할 수 없습니다");
		document.frm.estimated_sales_item_3.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_3.value) > 10){
		alert("10.예상월매출 2번 금액은 10자 이상 입력할 수 없습니다"); 
		document.frm.estimated_sales_amt_3.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_4.value) > 20){
		alert("10.예상월매출 4번 품명은 한글 10자 (영문 20자) 이상 입력할 수 없습니다");
		document.frm.estimated_sales_item_4.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_4.value) > 10){
		alert("10.예상월매출 2번 금액은 10자 이상 입력할 수 없습니다"); 
		document.frm.estimated_sales_amt_4.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_5.value) > 20){
		alert("10.예상월매출 5번 품명은 한글 10자 (영문 20자) 이상 입력할 수 없습니다");
		document.frm.estimated_sales_item_5.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_5.value) > 10){
		alert("10.예상월매출 2번 금액은 10자 이상 입력할 수 없습니다"); 
		document.frm.estimated_sales_amt_5.focus();
		return;
	}else if(formCheck.isNull(document.frm.other_paper, "11.기타 필요자료는 한글자 이상 적어주세요.")){		
		document.frm.other_paper.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.other_paper.value) > 2000){
		alert("11.기타 필요자료는 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
		document.frm.other_paper.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.unusual.value) > 2000){
		alert("12.특이사항은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
		document.frm.unusual.focus();
		return;	
	}	
	return true;
}
</script>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_newmadicine">
		<colgroup>
			<col class="cb_w87">
			<col style="">
			<col style="">
			<col style="">
			<col style="">
			<col style="">
			<col style="">   
		</colgroup>
		<tbody>
			<tr>
				<th>시행일자</th>
				<td colspan="6">
					<span class="date_box">
						<input type="text" class="serch_date" id="imposition_ymd" name="imposition_ymd" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getImposition_ymd())  %>" />
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>						
			<tr>
				<th>1. 병원명</th>
				<td colspan="6"> 
					<input type="text" name="hospital_nm" id="hospital_nm" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getHospital_nm()) %>" />
				</td>
			</tr>
			<tr>
				<th>2. 과명</th>
				<td colspan="3">
					<input type="text" class="nm_halfline" name="medical_nm" id="medical_nm"  value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getMedical_nm()) %>" />
				</td>				
				<th>3. DR.성명/직책</th>
				<td colspan="2">
					<input type="text" name="doctor_nm" id="doctor_nm" class="nm_halfline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getDoctor_nm()) %>" />
				</td>
			</tr>	
			<tr>
				<th>4. 제품명</th>
				<td colspan="3">
					<input type="text" name="item_nm" id="item_nm" class="nm_halfline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getItem_nm()) %>" />
				</td>				
				<th>5. 원내외 구분</th>
				<td colspan="2">
					<input type="text" name="side_div" id="side_div" class="nm_halfline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getSide_div()) %>" />
				</td>
			</tr>
			<tr>
				<th rowspan="3" >6. 교체/추가여부</th>											
				<th rowspan='2'>교체</th>			
				<th>교체품목</th>	
					<td colspan="4">
						<input type="text" name="change_item" id="change_item" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_item()) %>" />
					</td>					
			</tr>
			<tr>
				<th>교체대상</th>
				<td colspan="4">
					<input type="text" name="change_object" id="change_object" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_object()) %>" />
				</td>
			</tr>	
			<tr>												
				<th colspan='2'>추가품목</th>
				<td colspan="4">
					<input type="text" name="add_item" id="add_item" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getAdd_item()) %>" />
				</td>
			</tr>				
			<tr>				
				<th class="nm_bold">7. 요청기한<br />(* 최소 2주전<br />신청 필수)</th>			
				<td colspan="6">
					<span class="date_box">
						<input type="text" class="serch_date" id="time_limit_dt" name="time_limit_dt" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getTime_limit_dt()) %>" />
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>			
			<tr>
				<th>8. D/C일자</th>
				<td colspan="3">
					<span class="date_box">
						<input type="text" class="serch_date" name="discharge_dt" id="discharge_dt" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getDischarge_dt()) %>" />
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>				
				<th>9. 처방개시일</th>
				<td colspan="2">
					<span class="date_box">
						<input type="text" class="serch_date" name="begin_dt" id="begin_dt" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getBegin_dt()) %>" />
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>
			<tr>
				<th rowspan='2'>10. 예상월매출</th>
				<th>품명</th>
				<td>
					<input type="text" name="estimated_sales_item_1" id="estimated_sales_item_1" class="nm_itemboxL" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_1()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_item_2" id="estimated_sales_item_2" class="nm_itemboxL" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_2()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_item_3" id="estimated_sales_item_3" class="nm_itemboxL" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_3()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_item_4" id="estimated_sales_item_4" class="nm_itemboxL" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_4()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_item_5" id="estimated_sales_item_5" class="nm_itemboxL" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_5()) %>" />
				</td>
			</tr>
			<tr>			
				<th>금액(만원)</th>
				<td>
					<input type="text" name="estimated_sales_amt_1" id="estimated_sales_amt_1" class="nm_itemboxR onlyNumber" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_1()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_amt_2" id="estimated_sales_amt_2" class="nm_itemboxR onlyNumber" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_2()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_amt_3" id="estimated_sales_amt_3" class="nm_itemboxR onlyNumber" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_3()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_amt_4" id="estimated_sales_amt_4" class="nm_itemboxR onlyNumber" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_4()) %>" />
				</td>
				<td>
					<input type="text" name="estimated_sales_amt_5" id="estimated_sales_amt_5" class="nm_itemboxR onlyNumber" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_5()) %>" />
				</td>
			</tr>			
			<tr>
				<th>11. 기타 필요자료</th>
				<td colspan="6">
					<input type="text" name="other_paper" id="other_paper" class="nm_oneline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getOther_paper()) %>" />
				</td>
			</tr>
			<tr>
				<th>12. 특이사항</th>
				<td colspan="6">
					<input type="text" name="unusual" id="unusual" class="nm_oneline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getUnusual()) %>" />
				</td>
			</tr>
			<tr>
				<th>13. 서류양식</th>				
				<th colspan="6" class="nm_bold">양식은 File로 첨부해 주십시오</th>				
			</tr>
		</tbody>
	</table>
</div>	