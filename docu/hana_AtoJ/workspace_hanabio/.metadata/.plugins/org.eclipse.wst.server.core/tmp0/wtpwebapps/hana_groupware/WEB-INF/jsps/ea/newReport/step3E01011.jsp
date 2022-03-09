<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01011.jsp
 * @메뉴명 : 문서 상세조회 팝업 - 시간외근무내역서
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
%>
<div class="inner_box no_scroll ie8sn">
	<strong class="tit_s tit_sample">
		내 역
		<span class="refer">* 첨부서류 : 거래처카드</span>
	</strong>
	<table class="tbl_accident">
		<colgroup>
			<col style="width:86px">
			<col style="width:">
			<col style="width:">
			<col style="width:">
			<col style="width:">
			<col style="width:">
		</colgroup>
		<tbody>
			<tr>
				<th>거래처명</th>
				<td colspan="2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust_nm())%></td>
				<th>거래처코드</th>
				<td colspan="2" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust_cd())%></td>
			</tr>
			<tr>
				<th>지점</th>
				<td colspan="2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBranch_office())%></td>
				<th>담당자</th>
				<td colspan="2" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getIncharge())%></td>
			</tr>
			<tr>
				<th>사고구분</th>
				<td colspan="2"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getAccident_kind())%></td>
				<th>총매출 / 총수금</th>
				<td colspan="2" class="account bdrn"><%=StringUtil.nvl(accidentVO.getTotal_sale())%><%if(!"".equals(StringUtil.nvl(accidentVO.getTotal_sale()))){%> <span class="swung">/</span> <%}%><%=StringUtil.nvl(accidentVO.getTotal_collect())%></td>
				
			</tr>
			<tr>
				<th>사고발생일</th>
				<td colspan="2" class="date">
					<%=StringUtil.nvl(accidentVO.getOccurrence_ymd())%>
				</td>
				<th>최종거래일</th>
				<td colspan="2" class="bdrn date">
					<%=StringUtil.nvl(accidentVO.getLast_trade_ymd())%>
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
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust1())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust2())%></td>
				<td colspan="2" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCust3())%></td>				
			</tr>
			<tr>
				<th>대표자</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo1())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo2())%></td>
				<td colspan="2" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getCeo3())%></td>				
			</tr>
			<tr>
				<th>연대보증인1</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety11())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety12())%></td>
				<td colspan="2" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety13())%></td>				
			</tr>
			<tr>
				<th>연대보증인2</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety21())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety22())%></td>
				<td colspan="2" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSurety23())%></td>				
			</tr>
			<tr>
				<th rowspan="2">채권내역</th>
				<th>외상물품대금</th>
				<td><%=StringUtil.nvl(accidentVO.getCredit_amt())%></td>
				<th>미도래어음</th>
				<td colspan="2" class="bdrn"><%=StringUtil.nvl(accidentVO.getNotcome_bill())%></td>
			</tr>
			<tr>
				<th>부도채권총액</th>
				<td colspan="4" class="amount bdrn"><%=StringUtil.nvl(accidentVO.getBankrupt_amt())%></td>
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
				<td><%=StringUtil.nvl(accidentVO.getProperty1())%></td>
				<td><%=StringUtil.nvl(accidentVO.getProperty2())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getProperty3())%></td>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getProperty4())%></td>
			</tr>
			<tr>
				<th>어음/수표</th>
				<td><%=StringUtil.nvl(accidentVO.getSupyo1())%></td>
				<td><%=StringUtil.nvl(accidentVO.getSupyo2())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSupyo3())%></td>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getSupyo4())%></td>
			</tr>
			<tr>
				<th>보증보험</th>
				<td><%=StringUtil.nvl(accidentVO.getBoheom1())%></td>
				<td><%=StringUtil.nvl(accidentVO.getBoheom2())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBoheom3())%></td>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBoheom4())%></td>
			</tr>
			<tr>
				<th>지급보증</th>
				<td><%=StringUtil.nvl(accidentVO.getBojeung1())%></td>
				<td><%=StringUtil.nvl(accidentVO.getBojeung2())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBojeung3())%></td>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBojeung4())%></td>
			</tr>
			<tr>
				<th>기타</th>
				<td><%=StringUtil.nvl(accidentVO.getEtc1())%></td>
				<td><%=StringUtil.nvl(accidentVO.getEtc2())%></td>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getEtc3())%></td>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", accidentVO.getEtc4())%></td>
			</tr>
			<tr>
				<th>회수내용</th>
				<th>반품회수액</th>
				<td colspan="2"><%=StringUtil.nvl(accidentVO.getReturn_collection_amt())%></td>
				<th>금전회수액</th>
				<td class="bdrn"><%=StringUtil.nvl(accidentVO.getMoney_collection_amt())%></td>
			</tr>
			<tr>
				<th>보고내용<br />및<br />진행사항</th>
				<td colspan="5" class="ta bdrn">
					<%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", accidentVO.getBogo_content()))%>
				</td>
			</tr>
		</tbody>
	</table>
</div>