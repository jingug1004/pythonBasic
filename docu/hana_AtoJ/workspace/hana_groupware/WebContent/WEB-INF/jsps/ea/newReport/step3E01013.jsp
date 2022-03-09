<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.NewMadicineVO" %>
<%
	/* �ž��û�� */
	NewMadicineVO newmadicineVO = (NewMadicineVO)request.getAttribute("newmadicineVO");
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	if(newmadicineVO == null){
		newmadicineVO = new NewMadicineVO();
	}
%>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">�� ��</strong>
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
			<th>��������</th>
			<td colspan="6">
					<%=StringUtil.nvl(newmadicineVO.getImposition_ymd()) %>				
			</td>
		</tr>						
		<tr>
			<th>1. ������</th>
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getHospital_nm()) %>
			</td>
		</tr>
		<tr>
			<th>2. ����</th>
			<td colspan="3">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getMedical_nm()) %>
			</td>
			<th>3. DR.����/��å</th>
			<td colspan="2">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getDoctor_nm()) %>
			</td>
		</tr>	
		<tr>
			<th>4. ��ǰ��</th>
			<td colspan="3">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getItem_nm()) %>
			</td>
			<th>5. ������ ����</th>
			<td colspan="2">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getSide_div()) %>
			</td>
		</tr>
		<tr>
			<th rowspan="3" >6. ��ü/�߰�����</th>											
			<th rowspan='2'>��ü</th>			
			<th>��üǰ��</th>	
			<td colspan="4">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_item()) %>
			</td>					
		</tr>
		<tr>
			<th>��ü���</th>
			<td colspan="4">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_object()) %>
			</td>
		</tr>	
		<tr>												
			<th colspan="2">�߰�ǰ��</th>
			<td colspan="4">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getAdd_item()) %>
			</td>
		</tr>				
		<tr>
			<th class="nm_bold" >7. ��û����<br />(* �ּ� 2����<br />��û �ʼ�)</th>				
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getTime_limit_dt()) %>
			</td>
		</tr>		
		<tr>
			<th>8. D/C����</th>
			<td colspan="3">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getDischarge_dt()) %>
			</td>
			<th>9. ó�氳����</th>
			<td colspan="2">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getBegin_dt()) %>
			</td>
		</tr>
		<tr>
			<th rowspan="2">10. ���������</th>
			<th>ǰ��</th>
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
			<th>�ݾ�(����)</th>
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
			<th>11. ��Ÿ �ʿ��ڷ�</th>
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getOther_paper()) %>
			</td>
		</tr>
		<tr>
			<th>12. Ư�̻���</th>
			<td colspan="6">
				<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getUnusual()) %>
			</td>
		</tr>
			<tr>
			<th>13. �������</th>
			<th colspan="6" class="nm_bold">����� File�� ÷���� �ֽʽÿ�</th>	
		</tr>
	</tbody>
</table>