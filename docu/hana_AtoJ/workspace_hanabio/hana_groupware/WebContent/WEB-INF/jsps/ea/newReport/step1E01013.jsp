<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.NewMadicineVO" %>
<%
	/*�ž��û��*/
	NewMadicineVO newmadicineVO = (NewMadicineVO)request.getAttribute("newmadicineVO");
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	if(newmadicineVO == null){
		newmadicineVO = new NewMadicineVO();
	}
%>
<script type="text/javascript">

/*CHOE 20150413 yearendtaxcreditcardpopupup.jsp ���� by ������*/
$(document).ready(function(){
	
	/* �ݾ� �Է� ���� ��ȿ�� üũ */
	$("input[type='text'].onlyNumber").keyup(function(event) {
		var keyCode = event.keyCode;
	    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:ȭ��ǥ
	    	$(this).val(formCheck.onlyNumber($(this).val()));	
		}
	});
});

function saveStep1(){
	/*	CHOE 20150330 1.���� ���� 2.�Է�Ȯ��(��¥ ���ռ�)3.�Է�(NULL)Ȯ��  
	*/
	if(formCheck.isNull(document.frm.subject, "������ �Է��� �ּ���.")){
		return;
	}else if(formCheck.getByteLength(document.frm.subject.value) > 100){
		alert("������ �ѱ� 50�� (���� 100��) �̻� �Է��� �� �����ϴ�");
		document.frm.subject.focus();
		return;
	}else if(formCheck.isNull(document.frm.imposition_ymd, "�������ڸ� ������ �ּ���.")){
		//$(".imposition_ymd_button").prev().datepicker({"dateFormat":"yy-mm-dd"});		
		$("imposition_ymd").focus();
		//document.frm.imposition_ymd.focus();
		return;
	}else if(formCheck.isNull(document.frm.hospital_nm, "1.�������� �ѱ��� �̻� �����ּ���.")){		
		document.frm.hospital_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.hospital_nm.value) > 120){
		alert("1.�������� �ѱ� 60�� (���� 120��) �̻� �Է��� �� �����ϴ�");
		document.frm.hospital_nm.focus();
		return;
	}else if(formCheck.isNull(document.frm.medical_nm, "2.������ �ѱ��� �̻� �����ּ���.")){		
		document.frm.medical_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.medical_nm.value) > 100){
		alert("2.������ �ѱ� 50�� (���� 100��) �̻� �Է��� �� �����ϴ�");
		document.frm.medical_nm.focus();
		return;
	}else if(formCheck.isNull(document.frm.doctor_nm, "3.Dr.����/��å�� �ѱ��� �̻� �����ּ���.")){		
		document.frm.doctor_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.doctor_nm.value) > 100){
		alert("3.Dr.����/��å���� �ѱ� 50�� (���� 100��) �̻� �Է��� �� �����ϴ�");
		document.frm.doctor_nm.focus();
		return;	
	}else if(formCheck.isNull(document.frm.item_nm, "4.��ǰ���� �ѱ��� �̻� �����ּ���.")){		
		document.frm.item_nm.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.item_nm.value) > 75){
		alert("4.��ǰ���� �ѱ� 35�� (���� 75��) �̻� �Է��� �� �����ϴ�");
		document.frm.item_nm.focus();
		return;	
	}else if(formCheck.isNull(document.frm.side_div, "5.�����ܱ����� �ѱ��� �̻� �����ּ���.(���� �Ǵ� ����)")){		
		document.frm.side_div.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.side_div.value) > 20){
		alert("5.�����ܱ����� �ѱ� 10�� (���� 20��) �̻� �Է��� �� �����ϴ�");
		document.frm.side_div.focus();
		return;	
	}else if(formCheck.isNull(document.frm.change_item, "6.��üǰ���� �ѱ��� �̻� �����ּ���.")){		
		document.frm.change_item.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.change_item.value) > 75){
		alert("6.��üǰ���� �ѱ� 35�� (���� 75��) �̻� �Է��� �� �����ϴ�");
		document.frm.change_item.focus();
		return;	
	}else if(formCheck.isNull(document.frm.change_object, "6.��ü����� �ѱ��� �̻� �����ּ���.")){		
		document.frm.change_object.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.change_object.value) > 75){
		alert("6.��ü����� �ѱ� 35�� (���� 75��) �̻� �Է��� �� �����ϴ�");
		document.frm.change_object.focus();
		return;	
	}else if(formCheck.isNull(document.frm.add_item, "6.�߰�ǰ���� �ѱ��� �̻� �����ּ���.")){		
		document.frm.add_item.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.add_item.value) > 75){
		alert("6.�߰�ǰ���� �ѱ� 35�� (���� 75��) �̻� �Է��� �� �����ϴ�");
		document.frm.add_item.focus();
		return;	
	}else if(formCheck.isNull(document.frm.time_limit_dt, "7.��û���Ѹ� ������ �ּ���.")){
		//$(".item_limit_dt_button").prev().datepicker({"dateFormat":"yy-mm-dd"});
		//$(".item_limit_dt_button").prev().focus();
		$("time_limit_dt").focus();
		//document.frm.time_limit_dt.focus();
		return;
	}else if(formCheck.isNull(document.frm.discharge_dt, "8.D/C���ڸ� ������ �ּ���.")){
		//$(".discharge_dt_button").prev().datepicker({"dateFormat":"yy-mm-dd"});
		//$(".discharge_dt_button").prev().focus();
		$("discharge_dt").focus();
		//document.frm.discharge_dt.focus();
		return;
	}else if(formCheck.isNull(document.frm.begin_dt, "9.ó�氳���ϸ� ������ �ּ���.")){
		//$(".begin_dt_button").prev().datepicker({"dateFormat":"yy-mm-dd"});
		//$(".begin_dt_button").prev().focus();
		$("begin_dt").focus();
		//document.frm.begin_dt.focus();
		return;
	}else if(formCheck.isNull(document.frm.estimated_sales_item_1, "10.��������� 1�� ǰ���� �ѱ��� �̻� �����ּ���.")){		
		document.frm.estimated_sales_item_1.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_1.value) > 20){
		alert("10.��������� 1�� ǰ���� �ѱ� 10�� (���� 20��) �̻� �Է��� �� �����ϴ�.");
		document.frm.estimated_sales_item_1.focus();
		return;	
	}else if(formCheck.isNull(document.frm.estimated_sales_amt_1, "10.��������� 1�� �ݾ��� �Է��� �ּ���.")){
		document.frm.estimated_sales_amt_1.focus();  /*CHOE 20150330 1�� �׸��� �� �־�� �Ѵ�.*/
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_1.value) > 10){
		alert("10.��������� 1�� �ݾ��� 10�� �̻� �Է��� �� �����ϴ�"); 
		document.frm.estimated_sales_amt_2.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_2.value) > 20){
		alert("10.��������� 2�� ǰ���� �ѱ� 10�� (���� 20��) �̻� �Է��� �� �����ϴ�"); 
		document.frm.estimated_sales_item_2.focus();
		return;			
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_2.value) > 10){
		alert("10.��������� 2�� �ݾ��� 10�� �̻� �Է��� �� �����ϴ�"); 
		document.frm.estimated_sales_amt_2.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_3.value) > 20){
		alert("10.��������� 3�� ǰ���� �ѱ� 10�� (���� 20��) �̻� �Է��� �� �����ϴ�");
		document.frm.estimated_sales_item_3.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_3.value) > 10){
		alert("10.��������� 2�� �ݾ��� 10�� �̻� �Է��� �� �����ϴ�"); 
		document.frm.estimated_sales_amt_3.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_4.value) > 20){
		alert("10.��������� 4�� ǰ���� �ѱ� 10�� (���� 20��) �̻� �Է��� �� �����ϴ�");
		document.frm.estimated_sales_item_4.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_4.value) > 10){
		alert("10.��������� 2�� �ݾ��� 10�� �̻� �Է��� �� �����ϴ�"); 
		document.frm.estimated_sales_amt_4.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_item_5.value) > 20){
		alert("10.��������� 5�� ǰ���� �ѱ� 10�� (���� 20��) �̻� �Է��� �� �����ϴ�");
		document.frm.estimated_sales_item_5.focus();
		return;	
	}else if(formCheck.getByteLength(document.frm.estimated_sales_amt_5.value) > 10){
		alert("10.��������� 2�� �ݾ��� 10�� �̻� �Է��� �� �����ϴ�"); 
		document.frm.estimated_sales_amt_5.focus();
		return;
	}else if(formCheck.isNull(document.frm.other_paper, "11.��Ÿ �ʿ��ڷ�� �ѱ��� �̻� �����ּ���.")){		
		document.frm.other_paper.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.other_paper.value) > 2000){
		alert("11.��Ÿ �ʿ��ڷ�� �ѱ� 1000�� (���� 2000��) �̻� �Է��� �� �����ϴ�");
		document.frm.other_paper.focus();
		return;
	}else if(formCheck.getByteLength(document.frm.unusual.value) > 2000){
		alert("12.Ư�̻����� �ѱ� 1000�� (���� 2000��) �̻� �Է��� �� �����ϴ�");
		document.frm.unusual.focus();
		return;	
	}	
	return true;
}
</script>
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
					<span class="date_box">
						<input type="text" class="serch_date" id="imposition_ymd" name="imposition_ymd" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getImposition_ymd())  %>" />
						<button type="button" class="btn_date"><span class="blind">��¥����</span></button>
					</span>
				</td>
			</tr>						
			<tr>
				<th>1. ������</th>
				<td colspan="6"> 
					<input type="text" name="hospital_nm" id="hospital_nm" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getHospital_nm()) %>" />
				</td>
			</tr>
			<tr>
				<th>2. ����</th>
				<td colspan="3">
					<input type="text" class="nm_halfline" name="medical_nm" id="medical_nm"  value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getMedical_nm()) %>" />
				</td>				
				<th>3. DR.����/��å</th>
				<td colspan="2">
					<input type="text" name="doctor_nm" id="doctor_nm" class="nm_halfline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getDoctor_nm()) %>" />
				</td>
			</tr>	
			<tr>
				<th>4. ��ǰ��</th>
				<td colspan="3">
					<input type="text" name="item_nm" id="item_nm" class="nm_halfline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getItem_nm()) %>" />
				</td>				
				<th>5. ������ ����</th>
				<td colspan="2">
					<input type="text" name="side_div" id="side_div" class="nm_halfline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getSide_div()) %>" />
				</td>
			</tr>
			<tr>
				<th rowspan="3" >6. ��ü/�߰�����</th>											
				<th rowspan='2'>��ü</th>			
				<th>��üǰ��</th>	
					<td colspan="4">
						<input type="text" name="change_item" id="change_item" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_item()) %>" />
					</td>					
			</tr>
			<tr>
				<th>��ü���</th>
				<td colspan="4">
					<input type="text" name="change_object" id="change_object" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getChange_object()) %>" />
				</td>
			</tr>	
			<tr>												
				<th colspan='2'>�߰�ǰ��</th>
				<td colspan="4">
					<input type="text" name="add_item" id="add_item" class="nm_3quartline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getAdd_item()) %>" />
				</td>
			</tr>				
			<tr>				
				<th class="nm_bold">7. ��û����<br />(* �ּ� 2����<br />��û �ʼ�)</th>			
				<td colspan="6">
					<span class="date_box">
						<input type="text" class="serch_date" id="time_limit_dt" name="time_limit_dt" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getTime_limit_dt()) %>" />
						<button type="button" class="btn_date"><span class="blind">��¥����</span></button>
					</span>
				</td>
			</tr>			
			<tr>
				<th>8. D/C����</th>
				<td colspan="3">
					<span class="date_box">
						<input type="text" class="serch_date" name="discharge_dt" id="discharge_dt" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getDischarge_dt()) %>" />
						<button type="button" class="btn_date"><span class="blind">��¥����</span></button>
					</span>
				</td>				
				<th>9. ó�氳����</th>
				<td colspan="2">
					<span class="date_box">
						<input type="text" class="serch_date" name="begin_dt" id="begin_dt" readonly="readonly" value="<%=StringUtil.nvl(newmadicineVO.getBegin_dt()) %>" />
						<button type="button" class="btn_date"><span class="blind">��¥����</span></button>
					</span>
				</td>
			</tr>
			<tr>
				<th rowspan='2'>10. ���������</th>
				<th>ǰ��</th>
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
				<th>�ݾ�(����)</th>
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
				<th>11. ��Ÿ �ʿ��ڷ�</th>
				<td colspan="6">
					<input type="text" name="other_paper" id="other_paper" class="nm_oneline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getOther_paper()) %>" />
				</td>
			</tr>
			<tr>
				<th>12. Ư�̻���</th>
				<td colspan="6">
					<input type="text" name="unusual" id="unusual" class="nm_oneline" value="<%=RequestFilterUtil.toHtmlTagReplace("", newmadicineVO.getUnusual()) %>" />
				</td>
			</tr>
			<tr>
				<th>13. �������</th>				
				<th colspan="6" class="nm_bold">����� File�� ÷���� �ֽʽÿ�</th>				
			</tr>
		</tbody>
	</table>
</div>	