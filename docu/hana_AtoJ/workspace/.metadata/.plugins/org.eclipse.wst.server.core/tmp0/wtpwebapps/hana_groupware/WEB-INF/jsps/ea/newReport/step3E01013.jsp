<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.NewMadicineVO" %>
<%
	/* 신약신청서 */
	NewMadicineVO newmadicineVO = (NewMadicineVO)request.getAttribute("newmadicineVO");
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	if(newmadicineVO == null){
		newmadicineVO = new NewMadicineVO();
	}
%>
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
					<%=StringUtil.nvl(newmadicineVO.getImposition_ymd()) %>				
			</td>
		</tr>						
		<tr>
			<th>1. 병원명</th>
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getHospital_nm()) %>
			</td>
		</tr>
		<tr>
			<th>2. 과명</th>
			<td colspan="3">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getMedical_nm()) %>
			</td>
			<th>3. DR.성명/직책</th>
			<td colspan="2">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getDoctor_nm()) %>
			</td>
		</tr>	
		<tr>
			<th>4. 제품명</th>
			<td colspan="3">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getItem_nm()) %>
			</td>
			<th>5. 원내외 구분</th>
			<td colspan="2">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getSide_div()) %>
			</td>
		</tr>
		<tr>
			<th rowspan="3" >6. 교체/추가여부</th>											
			<th rowspan='2'>교체</th>			
			<th>교체품목</th>	
			<td colspan="4">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_item()) %>
			</td>					
		</tr>
		<tr>
			<th>교체대상</th>
			<td colspan="4">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_object()) %>
			</td>
		</tr>	
		<tr>												
			<th colspan="2">추가품목</th>
			<td colspan="4">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getAdd_item()) %>
			</td>
		</tr>				
		<tr>
			<th class="nm_bold" >7. 요청기한<br />(* 최소 2주전<br />신청 필수)</th>				
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getTime_limit_dt()) %>
			</td>
		</tr>		
		<tr>
			<th>8. D/C일자</th>
			<td colspan="3">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getDischarge_dt()) %>
			</td>
			<th>9. 처방개시일</th>
			<td colspan="2">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getBegin_dt()) %>
			</td>
		</tr>
		<tr>
			<th rowspan="2">10. 예상월매출</th>
			<th>품명</th>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_1()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_2()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_3()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_4()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_item_5()) %>
			</td>
		</tr>
		<tr>			
			<th>금액(만원)</th>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_1()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_2()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_3()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_4()) %>
			</td>
			<td>
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getEstimated_sales_amt_5()) %>
			</td>
		</tr>	
		<tr>
			<th>11. 기타 필요자료</th>
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getOther_paper()) %>
			</td>
		</tr>
		<tr>
			<th>12. 특이사항</th>
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getUnusual()) %>
			</td>
		</tr>
			<tr>
			<th>13. 서류양식</th>
			<th colspan="6" class="nm_bold">양식은 File로 첨부해 주십시오</th>	
		</tr>
	</tbody>
</table>