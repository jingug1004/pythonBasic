<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01007.jsp
 * @메뉴명 : step1신규문서작성-sample신청서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI       
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.SampleVO" %>
<%
	//샘플신청서
	List<SampleVO> sampleDetailList = (List<SampleVO>)request.getAttribute("sampleDetailList");
%>
	<script type="text/javascript">
	/*CHOE 20150626 전산팀장님 지시 사항 안내 MSG 출력*/
	$(window).load(function() {
		var alertMessage = "샘플신청서는 시행 부서의 의견이 모두 등록 되어야";
		alertMessage += "\n\n1차 결재자에게 문서가 보입니다.";
		alertMessage += "\n\n1차 결재자에게 문서가 보이지 않는 경우 시행 부서의";
		alertMessage += "\n\n의견이 등록 되었는지 확인해 보시기 바랍니다.";
		
		alert(alertMessage);
	});
	
	
	/**
	 * 행 카운트
	 */
	function checkCount() {
	 	var objTable = document.getElementById("tbl");
	 	var lastRow = objTable.rows.length-1;
	 	var rtnCnt = 0;
	
	 	for(var i = lastRow; i > 0; i--){
	   		if(document.getElementsByTagName("TR")[i]) rtnCnt++;
	 	}
	 	return rtnCnt;
	}
	
	/**
	 * 행추가
	 */
	function addRow() {
		var count = checkCount();
		if(count > 29){
			alert("등록 가능한 최대 개수는 30개 입니다.");
			return;
		}
		
	    var objRow = document.getElementById('tbl').insertRow(1); // row 생성
	    
	    var objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);	   	
  		objCel1.innerHTML="<textarea name='responsible' class='ta_sample'></textarea>";
  		
	    objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);	   	
  		objCel1.innerHTML="<textarea name='cust_nm' class='ta_sample'></textarea>";
	   	
		objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='dr_nm' class='ta_sample'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='product_nm' class='ta_sample'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='packing_unit' class='ta_sample'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='qty' class='ta_sample'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='yongdo' class='ta_sample'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='address' class='ta_sample'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='call_number' class='ta_sample'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "bdrn btn");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<button type='button' name=cmdDelete onClick='removeRow(this);'>삭제</button>"; 
	   	
	   	cnt++;
	}
	
	/**
	 * 행삭제
	 */
	function removeRow(r){ 
	 	var i=r.parentNode.parentNode.rowIndex;
	 	document.getElementById('tbl').deleteRow(i);
	}
	
	/**
	 * STEP1저장
	 */
	function saveStep1(){
		if(formCheck.isNull(document.getElementById("subject"), "제목을 입력해주세요.")){
			return;
		}
		var responsible = document.getElementsByName("responsible");
		var cust_nm = document.getElementsByName("cust_nm");
		var dr_nm = document.getElementsByName("dr_nm");
		var product_nm = document.getElementsByName("product_nm");
		var packing_unit = document.getElementsByName("packing_unit");
		var qty = document.getElementsByName("qty");
		var yongdo = document.getElementsByName("yongdo");
		var address = document.getElementsByName("address");
		var call_number = document.getElementsByName("call_number");
		if(address.length == 0 ){
			alert("내역을 입력해 주세요.");
			addRow();
			return;
		}
		
		for(var i=0; i<address.length; i++){
			if(formCheck.isNull(responsible[i], "담당자명을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNull(cust_nm[i], "거래처명을 입력해주세요.")){
				return;
			}
			
			if(formCheck.getByteLength(cust_nm[i].value) > 100){
				alert("50자 이하로 입력해 주세요.");
				cust_nm[i].focus();
				return;
			}
			
			if(formCheck.isNull(dr_nm[i], "Dr.명을 입력해주세요.")){
				return;
			}
			
			if(formCheck.getByteLength(dr_nm[i].value) > 100){
				alert("50자 이하로 입력해 주세요.");
				dr_nm[i].focus();
				return;
			}
			
			if(formCheck.isNull(product_nm[i], "품명을 입력해주세요.")){
				return;
			}
			
			if(formCheck.getByteLength(product_nm[i].value) > 100){
				alert("50자 이하로 입력해 주세요.");
				product_nm[i].focus();
				return;
			}
			
			if(formCheck.isNull(packing_unit[i], "포장단위를 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNull(qty[i], "수량을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNumer(qty[i].value)){
				alert("숫자만 입력해주세요.");
				qty[i].value = "";
				qty[i].focus();
				return;
			}
			
			if(formCheck.getByteLength(qty[i].value) > 100){
				alert("100자 이하로 입력해 주세요.");
				qty[i].focus();
				return;
			}
			
			if(formCheck.getByteLength(yongdo[i].value) > 200){
				alert("100자 이하로 입력해 주세요.");
				yongdo[i].focus();
				return;
			}
			
			if(formCheck.isNull(address[i], "배송지를 입력해주세요.")){
				return;
			}
			
			if(formCheck.getByteLength(address[i].value) > 200){
				alert("100자 이하로 입력해 주세요.");
				address[i].focus();
				return;
			}
			
			if(formCheck.isNull(call_number[i], "전화번호를 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNumDash(call_number[i].value)){
				alert("전화번호 형식이 올바르지 않습니다.\n하이픈(-)만 사용할 수 있습니다.");
				return;
			}
		}
		
		return true;
	}
	</script>
							<div class="inner_box no_scroll">
								<strong class="tit_s tit_sample">
									내 역
									<button type="button" class="btn_row lhfx" onclick="javascript:addRow(); return false;">+ 행추가</button>
								</strong>
								<table class="tbl_sample" id="tbl">
								<colgroup>
										<col style="width:50px">
										<col style="width:110px">
										<col style="width:50px">
										<col style="width:50px">
										<col style="width:45px">
										<col style="width:45px">
										<col style="width:73px">
										<col style="">
										<col style="width:65px">
										<col style="width:47px">
									</colgroup>
									<thead>
										<th>담당자</th>
										<th>거래처</th>
										<th>DR.명</th>
										<th>품명</th>
										<th>포장단위</th>
										<th>수량</th>
										<th>용도</th>
										<th>배송지</th>
										<th>전화번호</th>
										<th class="bdrn">삭제</th>
									</thead>
									<tbody>
										<%
										if(sampleDetailList.size() > 0){
											for(int i=0; sampleDetailList.size()>i;i++){
												SampleVO sampleVO = new SampleVO();
												sampleVO = sampleDetailList.get(i);
										%>
										<tr>
											<td><textarea name="responsible" class="ta_sample"><%=StringUtil.nvl(sampleVO.getResponsible()) %></textarea></td>
											<td><textarea name="cust_nm" class="ta_sample"><%=StringUtil.nvl(sampleVO.getCust_nm()) %></textarea></td>
											<td><textarea name="dr_nm" class="ta_sample"><%=StringUtil.nvl(sampleVO.getDr_nm()) %></textarea></td>
											<td><textarea name="product_nm" class="ta_sample"><%=StringUtil.nvl(sampleVO.getProduct_nm()) %></textarea></td>
											<td><textarea name="packing_unit" class="ta_sample"><%=StringUtil.nvl(sampleVO.getPacking_unit()) %></textarea></td>
											<td><textarea name="qty" class="ta_sample"><%=StringUtil.nvl(sampleVO.getQty()) %></textarea></td>
											<td><textarea name="yongdo" class="ta_sample"><%=StringUtil.nvl(sampleVO.getYongdo()) %></textarea></td>
											<td><textarea name="address" class="ta_sample"><%=StringUtil.nvl(sampleVO.getAddress()) %></textarea></td>
											<td><textarea name="call_number" class="ta_sample"><%=StringUtil.nvl(sampleVO.getCall_number()) %></textarea></td>
											<td class="bdrn btn"><button type="button" onClick='removeRow(this);'>삭제</button></td>
										</tr>
										<%		
											}
										}else{	
										%>
										<tr>
											<td><textarea name="responsible" class="ta_sample"></textarea></td>
											<td><textarea name="cust_nm" class="ta_sample"></textarea></td>
											<td><textarea name="dr_nm" class="ta_sample"></textarea></td>
											<td><textarea name="product_nm" class="ta_sample"></textarea></td>
											<td><textarea name="packing_unit" class="ta_sample"></textarea></td>
											<td><textarea name="qty" class="ta_sample"></textarea></td>
											<td><textarea name="yongdo" class="ta_sample"></textarea></td>
											<td><textarea name="address" class="ta_sample"></textarea></td>
											<td><textarea name="call_number" class="ta_sample"></textarea></td>
											<td class="bdrn btn"><button type="button" onClick='removeRow(this);'>삭제</button></td>
										</tr>
										<%} %>
									</tbody>
								</table>
							</div>	