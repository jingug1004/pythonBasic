<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01008.jsp
 * @메뉴명 : step1신규문서작성-구매신청서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI       
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.PurchaseVO" %>
<%	//구매신청서 
	List<PurchaseVO> purchaseDetailList = (List<PurchaseVO>)request.getAttribute("purchaseDetailList");
%>	
	<script type="text/javascript">
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
		if(count > 49){
			alert("등록 가능한 최대 개수는 50개 입니다.");
			return;
		}
		
	    var objRow = document.getElementById('tbl').insertRow(1); // row 생성
	    
	    var objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='product_nm' class='ipt_purchase' maxlength='50'>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='standard' class='ipt_purchase' maxlength='50'>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='qty' class='ipt_purchase' maxlength='100'>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "date");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<span class='date_box'><input type='text' name='deliver_req_ymd' class='serch_date' readonly><button type='button' class='btn_date'><span class='blind'>날짜선택</span></button></span>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='purpose' class='ipt_purchase' maxlength='200'>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='bigo' class='ipt_purchase' maxlength='100'>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "bdrn btn");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<button type='button' name=cmdDelete onClick='removeRow(this);'>삭제</button>"; 
	   	
	   	/*달력활성화*/
		$(".btn_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
		datepicker();
		/*달력활성화*/
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
		
		var product_nm = document.getElementsByName("product_nm");
		var qty = document.getElementsByName("qty");
		
		if(product_nm.length == 0 ){
			alert("내역을 입력해 주세요.");
			addRow();
			return;
		}
		for(var i=0; i<product_nm.length; i++){
			if(formCheck.isNull(product_nm[i], "품명을 입력해주세요.")){
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
		}
		
		return true;
	}
	</script>
							<div class="inner_box no_scroll">
								<strong class="tit_s tit_sample">
									내 역
									<button type="button" class="btn_row lhfx" onclick="javascript:addRow(); return false;">+ 행추가</button>
								</strong>
								<table class="tbl_purchase" id="tbl">
									<colgroup>
										<col style="width:111px">
										<col style="width:50px">
										<col style="width:71px">
										<col style="width:97px">
										<col style="">
										<col style="width:118px">
										<col style="width:46px">
									</colgroup>
									<thead>
										<th>품명</th>
										<th>규격</th>
										<th>수량</th>
										<th>납품요구일</th>
										<th>사무 및 목적</th>
										<th>비고</th>
										<th class="bdrn">삭제</th>
									</thead>
									<tbody>
										<%
										if(purchaseDetailList.size() > 0){
											for(int i=0; purchaseDetailList.size()>i;i++){
												PurchaseVO purchaseVO = new PurchaseVO();
												purchaseVO = purchaseDetailList.get(i);
										%>
										<tr>
											<td><input type="text" name="product_nm" id="" class="ipt_purchase" value="<%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getProduct_nm())%>" maxlength="50"/></td>
											<td><input type="text" name="standard" id="" class="ipt_purchase" value="<%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getStandard())%>" maxlength="50"/></td>
											<td><input type="text" name="qty" id="" class="ipt_purchase" value="<%=StringUtil.nvl(purchaseVO.getQty())%>" maxlength="100"/></td>
											<td class="date">
												<span class="date_box">
													<input type="text" name="deliver_req_ymd" id="" value="<%=StringUtil.nvl(purchaseVO.getDeliver_req_ymd())%>" readonly/>
													<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
												</span>
											</td>
											<td><input type="text" name="purpose" id="" value="<%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getPurpose())%>" class="ipt_purchase"  maxlength="200"/></td>
											<td><input type="text" name="bigo" id="" value="<%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getBigo())%>" class="ipt_purchase"  maxlength="100"/></td>
											<td class="bdrn btn"><button type="button" onClick='removeRow(this);'>삭제</button></td>
										</tr>
										<%		
											}
										}else{	
										%>
										
										<tr>
											<td><input type="text" name="product_nm" id="" class="ipt_purchase" maxlength="50"/></td>
											<td><input type="text" name="standard" id="" class="ipt_purchase" maxlength="50"/></td>
											<td><input type="text" name="qty" id="" class="ipt_purchase" maxlength="100"/></td>
											<td class="date">
												<span class="date_box">
													<input type="text" name="deliver_req_ymd" id="" readonly/>
													<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
												</span>
											</td>
											<td><input type="text" name="purpose" id="" class="ipt_purchase" maxlength="200"/></td>
											<td><input type="text" name="bigo" id="" class="ipt_purchase" maxlength="100"/></td>
											<td class="bdrn btn"><button type="button" onClick='removeRow(this);'>삭제</button></td>
										</tr>
										<%} %>
									</tbody>
								</table>
							</div>	