<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01011.jsp - 시간외근무내역서
 * @메뉴명 : 결재문서 미리보기
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵           
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.AccidentVO" %>
<%
	/* 사고보고서 */
	AccidentVO accidentVO = (AccidentVO)request.getAttribute("accidentVO");
	if(accidentVO == null){
		accidentVO = new AccidentVO();
	}
	
	String bogo_day = StringUtil.nvl(accidentVO.getBogo_day());		//보고일
	String bogo_month = StringUtil.nvl(accidentVO.getBogo_month()); //보고월
	String bogo_year = StringUtil.nvl(accidentVO.getBogo_year());	//보고년
%>
<table class="tbl_accident mt20">
	<colgroup>
		<col style="width:99px">
		<col style="">
		<col style="width:99px">
		<col style="">
	</colgroup>
	<tbody>
		<tr class="bdts">
			<th class="bdr">거래처명</th>
			<td class="bdr"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust_nm())%></td>
			<th class="bdr">거래처코드</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust_cd())%></td>
		</tr>				
		<tr>
			<th class="bdr">지점</th>
			<td class="bdr"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBranch_office())%></td>
			<th class="bdr">담당자</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getIncharge())%></td>
		</tr>	
		<tr>
			<td colspan="4" class="divide"></td>
		</tr>
		<tr class="bdts">
			<th class="bdr">사고구분</th>
			<td class="bdr"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getAccident_kind())%></td>
			<th class="bdr">총매출/총수금</th>
			<td class="bdr2"><%=StringUtil.nvl(accidentVO.getTotal_sale())%> <span class="swung">/</span> <%=StringUtil.nvl(accidentVO.getTotal_collect())%></td>
		</tr>				
		<tr>
			<th class="bdr">사고발생일</th>
			<td class="bdr"><%=StringUtil.nvl(accidentVO.getOccurrence_ymd())%></td>
			<th class="bdr">최종거래일</th>
			<td class="bdr2"><%=StringUtil.nvl(accidentVO.getLast_trade_ymd())%></td>
		</tr>	
		<tr>
			<td colspan="4" class="divide bdbn"></td>
		</tr>
	</tbody>
</table>

<table class="tbl_accident">
	<colgroup>
		<col style="width:99px"/>
		<col style="width:112px"/>
		<col style=""/>
		<col style=""/>
		<col style=""/>
	</colgroup>
	<tbody>
		<tr class="bdts">
			<th rowspan="5" class="bdr">거래처<br />상세내역</th>
			<th>구분</th>
			<th>이름/상호</th>
			<th>주민/사업자번호</th>
			<th class="bdr2">주소/소재지</th>
		</tr>				
		<tr>
			<th>거래처</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust1())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust2())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust3())%></td>
		</tr>
		<tr>
			<th>대표자</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo1())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo2())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo3())%></td>
		</tr>
		<tr>
			<th>연대보증인1</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety11())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety12())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety13())%></td>
		</tr>
		<tr>
			<th>연대보증인2</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety21())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety22())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety23())%></td>
		</tr>
		<tr>
			<td colspan="5" class="divide bdbn"></td>
		</tr>
	</tbody>
</table>

<table class="tbl_accident">
	<colgroup>
		<col style="width:99px">
		<col style="width:99px">
		<col style="">
		<col style="width:99px">
		<col style="">
	</colgroup>
	<tbody>
		<tr class="bdts">
			<th rowspan="2" class="bdr">채권내역</th>
			<th>외상물품대금</th>
			<td><%=StringUtil.nvl(accidentVO.getCredit_amt())%></td>
			<th>미도래어음</th>
			<td class="bdr2"><%=StringUtil.nvl(accidentVO.getNotcome_bill())%></td>
		</tr>				
		<tr>
			<th>부도채권총액</th>
			<td colspan="3" class="bdr2"><%=StringUtil.nvl(accidentVO.getBankrupt_amt())%></td>
		</tr>
		<tr>	
			<td colspan="5" class="divide bdbn"></td>
		</tr>
	</tbody>
</table>
<table class="tbl_accident">
	<colgroup>
		<col style="width:99px"/>
		<col style="width:96px"/>
		<col style=""/>
		<col style=""/>
		<col style="width:184px"/>
		<col style=""/>
	</colgroup>
	<tbody>
		<tr class="bdts">
			<th rowspan="6" class="bdr">담보현황</th>
			<th></th>
			<th>금액</th>
			<th>만기일</th>
			<th>부동산소재지/발행인</th>
			<th class="bdr2">배서인</th>
		</tr>				
		<tr>
			<th>부동산</th>
			<td><%=StringUtil.nvl(accidentVO.getProperty1())%></td>
			<td><%=StringUtil.nvl(accidentVO.getProperty2())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getProperty3())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getProperty4())%></td>
		</tr>
		<tr>
			<th>어음/수표</th>
			<td><%=StringUtil.nvl(accidentVO.getSupyo1())%></td>
			<td><%=StringUtil.nvl(accidentVO.getSupyo2())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSupyo3())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSupyo4())%></td>
		</tr>
		<tr>
			<th>보증보험</th>
			<td><%=StringUtil.nvl(accidentVO.getBoheom1())%></td>
			<td><%=StringUtil.nvl(accidentVO.getBoheom2())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBoheom3())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBoheom4())%></td>
		</tr>
		<tr>
			<th>지급보증</th>
			<td><%=StringUtil.nvl(accidentVO.getBojeung1())%></td>
			<td><%=StringUtil.nvl(accidentVO.getBojeung2())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBojeung3())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBojeung4())%></td>
		</tr>
		<tr>
			<th>기타</th>
			<td><%=StringUtil.nvl(accidentVO.getEtc1())%></td>
			<td><%=StringUtil.nvl(accidentVO.getEtc2())%></td>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getEtc3())%></td>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getEtc4())%></td>
		</tr>
		<tr>
			<td colspan="6" class="divide bdbn"></td>
		</tr>
	</tbody>
</table>
<table class="tbl_accident bdbs">
	<colgroup>
	<col style="width:99px"/>
	<col style="width:99px"/>
	<col style=""/>
	<col style="width:99px"/>
	<col style=""/>
	</colgroup>
	<tbody>
		<tr class="bdts">
			<th class="bdr">회수내용</th>
			<th>반품회수액</th>
			<td><%=StringUtil.nvl(accidentVO.getReturn_collection_amt())%></td>
			<th>금전회수액</th>
			<td class="bdr2"><%=StringUtil.nvl(accidentVO.getMoney_collection_amt())%></td>
		</tr>
		<tr>
			<td colspan="5" class="divide"></td>
		</tr>
		<tr class="bdts">
			<th class="bdr">보고내용<br />및<br />진행사항</th>
			<td colspan="4" class="ta bdr2">
				<div><%=StringUtil.nl2br(StringUtil.nvl(accidentVO.getBogo_content()))%></div>
			</td>
		</tr>
	</tbody>
</table>
<p class="ref_accident">※첨부서류 : 거래처카드</p>
<div class="box_accident_report">
	<span><%=bogo_year%></span>.<span><%=bogo_month%></span>.<span><%=bogo_day%></span>
	<p>상기와 같이 보고합니다.</p>
</div>
