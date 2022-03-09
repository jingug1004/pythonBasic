<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01011.jsp
 * @메뉴명 : step1신규문서작성 - 시간외근무내역서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI       
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.AccidentVO" %>
<%
	AccidentVO accidentVO = (AccidentVO)request.getAttribute("accidentVO");
	if(accidentVO == null){
		accidentVO = new AccidentVO();
	}
%>
<script type="text/javascript">
	/**
	 * 벨리데이션 체크
	 * @returns {Boolean}
	 */
	function saveStep1(){
		if(formCheck.isNull(document.frm.subject, "제목을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.subject.value) > 100){
			alert("제목은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.subject.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cust_nm.value) > 100){
			alert("거래처명은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.cust_nm.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cust_cd.value) > 100){
			alert("거래코드는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.cust_cd.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.branch_office.value) > 100){
			alert("지점은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.branch_office.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.incharge.value) > 100){
			alert("담당자는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.incharge.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.accident_kind.value) > 100){
			alert("사고구분 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.accident_kind.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.total_sale.value) > 100){
			alert("총매출은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.total_sale.focus();
			return;
		}else if(formCheck.isNumer(document.frm.total_sale.value)){
			alert("총매출은 숫자만 입력가능 합니다.");
			document.frm.total_sale.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.total_collect.value) > 100){
			alert("총수금은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.total_collect.focus();
			return;
		}else if(formCheck.isNumer(document.frm.total_collect.value)){
			alert("총수금은 숫자만 입력가능 합니다.");
			document.frm.total_collect.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cust1.value) > 100){
			alert("거래처 이름/상호는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.cust1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cust2.value) > 100){
			alert("거래처 주민/사업자번호는 숫자,\"-\" 포함 100자 이상 입력할 수 없습니다");
			document.frm.cust2.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.cust2.value)){
			alert("거래처 주민/사업자번호는 숫자,\"-\"만 입력가능 합니다 ");
			document.frm.cust2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cust3.value) > 300){
			alert("거래처 주소/소재지는 한글 150자 (영문 300자) 이상 입력할 수 없습니다");
			document.frm.cust3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.ceo1.value) > 100){
			alert("대표자 이름/상호는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.ceo1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.ceo2.value) > 100){
			alert("대표자 주민/사업자번호는 숫자,\"-\" 포함 100자 이상 입력할 수 없습니다");
			document.frm.ceo2.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.ceo2.value)){
			alert("대표자 주민/사업자번호는 숫자,\"-\"만 입력가능 합니다 ");
			document.frm.ceo2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.ceo3.value) > 300){
			alert("대표자 주소/소재지는 한글 150자 (영문 300자) 이상 입력할 수 없습니다");
			document.frm.ceo3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.surety11.value) > 100){
			alert("연대보증인1 이름/상호는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.surety11.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.surety12.value) > 100){
			alert("연대보증인1 주민/사업자번호는 숫자,\"-\" 포함 100자 이상 입력할 수 없습니다");
			document.frm.surety12.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.surety12.value)){
			alert("연대보증인1 주민/사업자번호는 숫자,\"-\"만 입력가능 합니다 ");
			document.frm.surety12.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.surety13.value) > 300){
			alert("연대보증인1 주소/소재지는 한글 150자 (영문 300자) 이상 입력할 수 없습니다");
			document.frm.surety13.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.surety21.value) > 100){
			alert("연대보증인2 이름/상호는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.surety21.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.surety22.value) > 100){
			alert("연대보증인2 주민/사업자번호는 숫자,\"-\" 포함 100자 이상 입력할 수 없습니다");
			document.frm.surety22.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.surety22.value)){
			alert("연대보증인2 주민/사업자번호는 숫자,\"-\"만 입력가능 합니다");
			document.frm.surety22.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.surety23.value) > 300){
			alert("연대보증인2 주소/소재지는 한글 150자 (영문 300자) 이상 입력할 수 없습니다");
			document.frm.surety23.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.credit_amt.value) > 100){
			alert("외상물품대금은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.credit_amt.focus();
			return;
		}else if(formCheck.isNumer(document.frm.credit_amt.value)){
			alert("외상물품대금은 숫자만 입력가능 합니다");
			document.frm.credit_amt.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.notcome_bill.value) > 100){
			alert("미도래어음은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.notcome_bill.focus();
			return;
		}else if(formCheck.isNumer(document.frm.notcome_bill.value)){
			alert("미도래어음은 숫자만 입력가능 합니다");
			document.frm.notcome_bill.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bankrupt_amt.value) > 100){
			alert("부도채권총액은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.bankrupt_amt.focus();
			return;
		}else if(formCheck.isNumer(document.frm.bankrupt_amt.value)){
			alert("부도채권총액은 숫자만 입력가능 합니다");
			document.frm.bankrupt_amt.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.property1.value) > 100){
			alert("부동산 금액은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.property1.focus();
			return;
		}else if(formCheck.isNumer(document.frm.property1.value)){
			alert("부동산 금액은 숫자만 입력가능 합니다");
			document.frm.property1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.property2.value) > 10){
			alert("부동산 만기일은 숫자, \"-\" 포함 이상 입력할 수 없습니다");
			document.frm.property2.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.property2.value)){
			alert("부동산 만기일은 숫자, \"-\" 만 입력가능 합니다");
			document.frm.property2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.property3.value) > 100){
			alert("부동산 소재지/발행인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.property3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.property4.value) > 100){
			alert("배서인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.property4.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.supyo1.value) > 100){
			alert("어음/수표 금액은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.supyo1.focus();
			return;
		}else if(formCheck.isNumer(document.frm.supyo1.value)){
			alert("어음/수표 금액은 숫자만 입력가능 합니다");
			document.frm.supyo1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.supyo2.value) > 10){
			alert("어음/수표 만기일은 숫자, \"-\" 포함 이상 입력할 수 없습니다");
			document.frm.supyo2.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.supyo2.value)){
			alert("어음/수표 만기일은 숫자, \"-\" 만 입력가능 합니다");
			document.frm.supyo2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.supyo3.value) > 100){
			alert("어음/수표 소재지/발행인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.supyo3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.supyo4.value) > 100){
			alert("어음/수표 배서인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.supyo4.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.boheom1.value) > 100){
			alert("보증보험 금액은 숫자 포함 100자 이상 입력할 수 없습니다");
			document.frm.boheom1.focus();
			return;
		}else if(formCheck.isNumer(document.frm.boheom1.value)){
			alert("보증보험 금액은 숫자만 입력가능 합니다.");
			document.frm.boheom1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.boheom2.value) > 10){
			alert("보증보험 만기일은 숫자, \"-\" 포함 10자 이상 입력할 수 없습니다.");
			document.frm.boheom2.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.boheom2.value)){
			alert("보증보험 만기일은 숫자, \"-\" 만 입력가능 합니다");
			document.frm.boheom2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.boheom3.value) > 100){
			alert("보증보험 소재지/발행인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.boheom3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.boheom4.value) > 100){
			alert("보증보험 배서인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.boheom4.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bojeung1.value) > 100){
			alert("지급보증 금액은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.bojeung1.focus();
			return;
		}else if(formCheck.isNumer(document.frm.bojeung1.value)){
			alert("지급보증 금액은 숫자만 입력가능 합니다");
			document.frm.bojeung1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bojeung2.value) > 10){
			alert("지급보증 만기일은 숫자, \"-\" 포함 100자 이상 입력할 수 없습니다");
			document.frm.bojeung2.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.bojeung2.value)){
			alert("지급보증 만기일은 숫자, \"-\" 만 입력가능 합니다");
			document.frm.bojeung2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bojeung3.value) > 100){
			alert("지급보증 소재지/발행인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.bojeung3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bojeung4.value) > 100){
			alert("지급보증 배서인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.bojeung4.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.etc1.value) > 100){
			alert("기타 금액은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.etc1.focus();
			return;
		}else if(formCheck.isNumer(document.frm.etc1.value)){
			alert("기타 금액은 숫자만 입력가능 합니다.");
			document.frm.etc1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.etc2.value) > 10){
			alert("기타 만기일은 숫자, \"-\" 포함 10자 이상 입력할 수 없습니다");
			document.frm.etc2.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.etc2.value)){
			alert("기타 만기일은 숫자, \"-\" 만 입력가능 합니다");
			document.frm.etc2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.etc3.value) > 100){
			alert("기타 소재지/발행인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.etc3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.etc4.value) > 100){
			alert("기타 배서인은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.etc4.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.return_collection_amt.value) > 100){
			alert("반품회수액은 숫자 100자 이상 입력할 수 없습니다");
			document.frm.return_collection_amt.focus();
			return;
		}else if(formCheck.isNumer(document.frm.return_collection_amt.value)){
			alert("반품회수액은 숫자만 입력가능 합니다.");
			document.frm.return_collection_amt.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.money_collection_amt.value) > 100){
			alert("금전회수액은 숫자, \",\" 포함 100자 이상 입력할 수 없습니다");
			document.frm.money_collection_amt.focus();
			return;
		}else if(formCheck.isNumer(document.frm.money_collection_amt.value)){
			alert("금전회수액은 숫자만 입력가능 합니다.");
			document.frm.money_collection_amt.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bogo_content.value) > 2000){
			alert("보고내용 및 진행사항은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.bogo_content.focus();
			return;
		}else if(formCheck.isNull(document.frm.bogo_year, "보고년도를 입력해 주세요.")){
			document.frm.bogo_year.focus();
			return;
		}else if(formCheck.isNumer(document.frm.bogo_year.value)){
			alert("보고년도는 숫자만 입력 가능합니다.");
			document.frm.bogo_year.focus();
			return;
		}else if(document.frm.bogo_year.value.length != 4){
			alert("보고년도는 4자만 입력 가능합니다.");
			document.frm.bogo_year.focus();
			return;
		}else if(formCheck.isNull(document.frm.bogo_month, "보고월을 입력해 주세요.")){
			document.frm.bogo_month.focus();
			return;
		}else if(document.frm.bogo_month.value.length != 2){
			alert("보고월은 2자만 입력 가능합니다.");
			document.frm.bogo_month.focus();
			return;
		}else if(formCheck.isNumer(document.frm.bogo_month.value)){
			alert("보고월은 숫자만 입력 가능합니다.");
			document.frm.bogo_month.focus();
			return;
		}else if(formCheck.isNull(document.frm.bogo_day, "보고일을 입력해 주세요.")){
			document.frm.bogo_day.focus();
			return;
		}else if(document.frm.bogo_day.value.length != 2){
			alert("보고일은 2자만 입력 가능합니다.");
			document.frm.bogo_day.focus();
			return;
		}else if(formCheck.isNumer(document.frm.bogo_day.value)){
			alert("보고일은 숫자만 입력 가능합니다.");
			document.frm.bogo_day.focus();
			return;
		}
			
		return true;
	}
</script>
<div class="inner_box no_scroll ie8sn">
	<strong class="tit_s tit_sample">
		내 역
		<span class="refer">* 첨부서류 : 거래처카드</span>
	</strong>
	<table class="tbl_accident">
		<colgroup>
			<col style="width:52px">
			<col style="width:60px">
			<col style="width:72px">
			<col style="width:72px">
			<col style="width:88px">
			<col style="width:72px">
		</colgroup>
		<tbody>
			<tr>
				<th>거래처명</th>
				<td colspan="2"><input type="text" name="cust_nm" id="cust_nm" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust_nm())%>" /></td>
				<th>거래처코드</th>
				<td colspan="2" class="bdrn"><input type="text" name="cust_cd" id="cust_cd" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust_cd())%>" /></td>
			</tr>
			<tr>
				<th>지점</th>
				<td colspan="2"><input type="text" name="branch_office" id="branch_office" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBranch_office())%>" /></td>
				<th>담당자</th>
				<td colspan="2" class="bdrn"><input type="text" name="incharge" id="incharge" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getIncharge())%>" /></td>
			</tr>
			<tr>
				<th>사고구분</th>
				<td colspan="2"><input type="text" name="accident_kind" id="accident_kind" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getAccident_kind())%>"/></td>
				<th>총매출 / 총수금</th>
				<td colspan="2" class="account bdrn"><input type="text" name="total_sale" id="total_sale" value="<%=StringUtil.nvl(accidentVO.getTotal_sale())%>"/> <span class="swung">/</span> <input type="text" name="total_collect" id="total_collect" value="<%=StringUtil.nvl(accidentVO.getTotal_collect())%>"/></td>
				
			</tr>
			<tr>
				<th>사고발생일</th>
				<td colspan="2" class="date">
					<span class="date_box">
						<input type="text" class="serch_date" name="occurrence_ymd" id="occurrence_ymd" readonly="readonly" value="<%=StringUtil.nvl(accidentVO.getOccurrence_ymd())%>"/>
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
				<th>최종거래일</th>
				<td colspan="2" class="bdrn date">
					<span class="date_box">
						<input type="text" class="serch_date" name="last_trade_ymd" id="last_trade_ymd" readonly="readonly" value="<%=StringUtil.nvl(accidentVO.getLast_trade_ymd())%>"/>
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>
			<tr>
				<th rowspan="5">거래처<br />상세내역</th>
				<th>구분</th>
				<th>이름/상호</th>
				<th>주민/사업자번호</th>
				<th colspan="2" class="bdrn">주소/소재지</th>
			</tr>
			<tr>
				<th>거래처</th>
				<td><input type="text" name="cust1" id="cust1" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust1())%>" /></td>
				<td><input type="text" name="cust2" id="cust2" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust2())%>" /></td>
				<td colspan="2" class="bdrn"><input type="text" name="cust3" id="cust3" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust3())%>" /></td>				
			</tr>
			<tr>
				<th>대표자</th>
				<td><input type="text" name="ceo1" id="ceo1" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo1())%>" /></td>
				<td><input type="text" name="ceo2" id="ceo2" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo2())%>" /></td>
				<td colspan="2" class="bdrn"><input type="text" name="ceo3" id="ceo3" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo3())%>" /></td>				
			</tr>
			<tr>
				<th>연대보증인1</th>
				<td><input type="text" name="surety11" id="surety11" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety11())%>" /></td>
				<td><input type="text" name="surety12" id="surety12" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety12())%>" /></td>
				<td colspan="2" class="bdrn"><input type="text" name="surety13" id="surety13" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety13())%>" /></td>				
			</tr>
			<tr>
				<th>연대보증인2</th>
				<td><input type="text" name="surety21" id="surety21" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety21())%>" /></td>
				<td><input type="text" name="surety22" id="surety22" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety22())%>" /></td>
				<td colspan="2" class="bdrn"><input type="text" name="surety23" id="surety23" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety23())%>" /></td>				
			</tr>
			<tr>
				<th rowspan="2">채권내역</th>
				<th>외상물품대금</th>
				<td><input type="text" name="credit_amt" id="credit_amt" value="<%=StringUtil.nvl(accidentVO.getCredit_amt())%>" /></td>
				<th>미도래어음</th>
				<td colspan="2" class="bdrn"><input type="text" name="notcome_bill" id="notcome_bill" value="<%=StringUtil.nvl(accidentVO.getNotcome_bill())%>" /></td>
			</tr>
			<tr>
				<th>부도채권총액</th>
				<td colspan="4" class="amount bdrn"><input type="text" name="bankrupt_amt" id="bankrupt_amt" value="<%=StringUtil.nvl(accidentVO.getBankrupt_amt())%>" /></td>
			</tr>
			<tr>
				<th rowspan="6">담보현황</th>
				<th></th>
				<th>금액</th>
				<th>만기일</th>
				<th>부동산소재지/발행인</th>
				<th class="bdrn">배서인</th>
			</tr>
			<tr>
				<th>부동산</th>
				<td><input type="text" name="property1" id="property1" value="<%=StringUtil.nvl(accidentVO.getProperty1())%>" /></td>
				<td><input type="text" name="property2" id="property2" value="<%=StringUtil.nvl(accidentVO.getProperty2())%>" /></td>
				<td><input type="text" name="property3" id="property3" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getProperty3())%>" /></td>
				<td class="bdrn"><input type="text" name="property4" id="property4" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getProperty4())%>" /></td>
			</tr>
			<tr>
				<th>어음/수표</th>
				<td><input type="text" name="supyo1" id="supyo1" value="<%=StringUtil.nvl(accidentVO.getSupyo1())%>" /></td>
				<td><input type="text" name="supyo2" id="supyo2" value="<%=StringUtil.nvl(accidentVO.getSupyo2())%>" /></td>
				<td><input type="text" name="supyo3" id="supyo3" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSupyo3())%>" /></td>
				<td class="bdrn"><input type="text" name="supyo4" id="supyo4" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSupyo4())%>" /></td>
			</tr>
			<tr>
				<th>보증보험</th>
				<td><input type="text" name="boheom1" id="boheom1" value="<%=StringUtil.nvl(accidentVO.getBoheom1())%>" /></td>
				<td><input type="text" name="boheom2" id="boheom2" value="<%=StringUtil.nvl(accidentVO.getBoheom2())%>" /></td>
				<td><input type="text" name="boheom3" id="boheom3" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBoheom3())%>" /></td>
				<td class="bdrn"><input type="text" name="boheom4" id="boheom4" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBoheom4())%>" /></td>
			</tr>
			<tr>
				<th>지급보증</th>
				<td><input type="text" name="bojeung1" id="bojeung1" value="<%=StringUtil.nvl(accidentVO.getBojeung1())%>" /></td>
				<td><input type="text" name="bojeung2" id="bojeung2" value="<%=StringUtil.nvl(accidentVO.getBojeung2())%>" /></td>
				<td><input type="text" name="bojeung3" id="bojeung3" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBojeung3())%>"/></td>
				<td class="bdrn"><input type="text" name="bojeung4" id="bojeung4" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBojeung4())%>" /></td>
			</tr>
			<tr>
				<th>기타</th>
				<td><input type="text" name="etc1" id="etc1" value="<%=StringUtil.nvl(accidentVO.getEtc1())%>" /></td>
				<td><input type="text" name="etc2" id="etc2" value="<%=StringUtil.nvl(accidentVO.getEtc2())%>" /></td>
				<td><input type="text" name="etc3" id="etc3" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getEtc3())%>" /></td>
				<td class="bdrn"><input type="text" name="etc4" id="etc4" value="<%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getEtc4())%>" /></td>
			</tr>
			<tr>
				<th>회수내용</th>
				<th>반품회수액</th>
				<td colspan="2"><input type="text" name="return_collection_amt" id="return_collection_amt" value="<%=StringUtil.nvl(accidentVO.getReturn_collection_amt())%>" /></td>
				<th>금전회수액</th>
				<td class="bdrn"><input type="text" name="money_collection_amt" id="money_collection_amt" value="<%=StringUtil.nvl(accidentVO.getMoney_collection_amt())%>" /></td>
			</tr>
			<tr>
				<th>보고내용<br />및<br />진행사항</th>
				<td colspan="5" class="ta bdrn">
					<textarea name="bogo_content" id="bogo_content"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBogo_content())%></textarea>
				</td>
			</tr>
		</tbody>
	</table>
</div>		