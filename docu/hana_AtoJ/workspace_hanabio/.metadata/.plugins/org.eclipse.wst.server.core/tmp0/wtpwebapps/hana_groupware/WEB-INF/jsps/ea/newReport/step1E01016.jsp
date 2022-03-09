<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01016.jsp
 * @메뉴명 : step1신규문서작성-원부자재 납품확인서
 * @최초작성일 : 2016/01/25            
 * @author : 전산팀                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.DeliveryVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<DeliveryVO> deliveryDetailList = (List<DeliveryVO>)request.getAttribute("deliveryDetail");

	Integer listSize = 0; 	
	listSize = deliveryDetailList.size();
	
	String search_ymd = StringUtil.nvl((String)request.getAttribute("search_ymd")); //입고일	
	
	String search_slip_no = StringUtil.nvl((String)request.getAttribute("search_slip_no")); //전표번호	
	
%>
<script type="text/javascript">
	$(window).load(function() {
		$("#search_slip_no > option[value=<%=search_slip_no%>]").attr("selected","true");
	});
	
	/**
	 * 벨리데이션 체크
	 * @returns {Boolean}
	 */
	function saveStep1(){	
		if(formCheck.isNull(document.frm.search_ymd, "입고일이 없습니다. 먼저 입고일을 선택해 주세요.")){
			search_ymd.focus();
			return			
		}
		
		if(document.getElementById("search_slip_no").value == ""){
			alert("전표번호을 선택해 주세요.(선택할  전표번호가 없는 경우 전표번호조회 버튼 누르시기 바랍니다.)");
			search_slip_no.focus();
			return			
		}
		
		if(formCheck.isNull(document.frm.material_id0, "자료가 없습니다. 전표자료조회 버튼을 이용하여 자료를 조회하시기 바랍니다.")){			
			search_slip_no.focus();
			return			
		}
		return true;
	}
	 
	/*CHOE 20160126 날짜에 따른 원부자재 납품확인서 slip_no 조회
	*/
	function searchSlipNo(){		
		if(formCheck.isNull(document.frm.search_ymd, "입고일이 없습니다. 입고일을 선택해 주세요.")){
			search_ymd.focus();
			return			
		}	
		//입고일을 다시 조회 하면 전표번호를 비운다. : 다른 입고일을 조회 하기 위해서 -> NewReportController 2-확인-2. 입고일은 있고 전표번호만 없는 경우 이동함	
		document.getElementById("search_slip_no").value = "";
		
		document.frm.action='<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do';
		document.frm.submit();			
	}
	 
	/*CHOE 20160126 slip_no에 따라 원부자재 납품확인서 조회
	*/
	function searchSlipData(){
		
		if(formCheck.isNull(document.frm.search_ymd, "입고일이 없습니다. 먼저 입고일을 선택해 주세요.")){
			search_ymd.focus();
			return			
		}	
		
		if (document.getElementById("search_slip_no").value == ""){
			alert("전표번호을 선택해 주세요.(선택할 전표번호가 없는 경우 전표번호조회 버튼 누르시기 바랍니다.)");
			search_slip_no.focus();			
			return			
		}		
		
		document.frm.action='<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do';
		document.frm.submit();			
	}
</script>
<h5>
	<span class="w_date1">입고일자 :</span>
	<span class="w_date2">전표번호 :</span>
	<span class="w_date3">거래처 :</span>	
</h5>
<div class="confirm">
	<table class="over">
		<colgroup>			
			<col style="width:9%;">			
			<col style="width:17%;">			
			<col style="width:7%;">
			<col style="width:8%;">
			<col style="width:7%;">
			<col style="width:7%;">
			<col style="width:9%;">
			<col style="width:9%;">
			<col style="width:9%;">
			<col style="width:9%;">
			<col style="width:9%;">						
		</colgroup>
		<%
		if(deliveryDetailList.size() > 0){
			for(int i=0; deliveryDetailList.size()>i;i++){
				DeliveryVO deliVO = new DeliveryVO();
				deliVO = deliveryDetailList.get(i);
				if (i==0){
		%>
		<thead>
			<tr>
				<th>입고일</th>
				<td colspan="4" class="date bdrn">				
					<span class="serch_date_box">
						<input class="serch_date" type="text" id="search_ymd" name="search_ymd" value="<%=StringUtil.nvl(deliVO.getYmd()) %>" readonly="readonly">																													
						<button type="button" class="btn_date">
							<span class="blind">날짜선택
							</span>
						</button>										
					</span>											
					<button type="button" onclick="javascript:searchSlipNo(); return false;">전표번호조회</button>								
				</td>				
				<th colspan="2">전표번호</th>
				<td colspan="4">																
					<select colspan="7" class="serch_select02 w96" id="search_slip_no" name="search_slip_no" onchange="readonlyCheck();" >
					<%
					for(int j=0; deliveryDetailList.size()>j;j++){
						DeliveryVO deliListVO = new DeliveryVO();
						deliListVO = deliveryDetailList.get(j);
					%>
					<option value="<%=deliListVO.getSlip_no()%>"><%=deliListVO.getSlip_no()%>-<%=deliListVO.getSawon_nm()%></option>
					<%
					}
					%>							
					</select>
					<input type="hidden" id="search_slip_no" name="search_slip_no" value="<%=search_slip_no %>" />
					<button type="button" onclick="javascript:searchSlipData(); return false;">전표자료조회</button>
				</td>
			</tr>
		<thead>
				<%
						}
					if ("".equals(StringUtil.nvl(deliVO.getMaterial_id())) ){
						if (i==0){
					%>
		<thead>						
			<tr>			  
				<th style="border-bottom: none;">원부자재코드</th>
				<th>원부자재명</th>			 
			  	<th>규격</th>	
			  	<th>입고수량</th>		 
			  	<th>단위</th>		  	  										
				<th>단가</th>
				<th>금액</th>		
				<th>공급가액</th>
				<th>부가세</th>
				<th>합계</th>
				<th>비고</th>							  
			</tr>
		</thead>
		<tbody>			
			<tr class="in_table">
				<td class="br_none">&nbsp
					<input type="hidden" name="material_id" id="material_id0" value=""/>					
					<input type="hidden" name="cust_id" id="cust_id0" value=""/>
					<input type="hidden" name="cust_nm" id="cust_nm0" value=""/>
					<input type="hidden" name="balju_no" id="balju_no0" value=""/>					
				</td>
				<td class="br_none"><input type="hidden" name="material_nm" id="material_nm0" value=""/></td>
				<td class="br_none"><input type="hidden" name="standard" id="standard0" value=""/></td>					
				<td class="txtright"><input type="hidden" name="qty" id="qty0" value=""/></td>
				<td class="br_none"><input type="hidden" name="unit" id="unit0" value=""/></td>
				<td class="txtright"><input type="hidden" name="danga" id="danga0" value=""/></td>
				<td class="txtright"><input type="hidden" name="amt" id="amt0" value=""/></td>
				<td class="txtright"><input type="hidden" name="amt_sum" id="amt_sum0" value=""/></td>
				<td class="txtright"><input type="hidden" name="vat_sum" id="vat_sum0" value=""/></td>	
				<td class="txtright"><input type="hidden" name="tot_sum" id="tot_sum0" value=""/></td>	
				<td class="br_none"><input type="hidden" name="bigo" id="bigo0" value=""/></td>													
			</tr>
		</tbody>						
				<%
						}
					}else{						
						if (i==0){
				%>
		</thead>					
			<tr>			  
				<th style="border-bottom: none;">원부자재코드</th>
				<th>원부자재명</th>			 
			  	<th>규격</th>	
			  	<th>입고수량</th>		 
			  	<th>단위</th>		  	  										
				<th>단가</th>
				<th>금액</th>		
				<th>공급가액</th>
				<th>부가세</th>
				<th>합계</th>
				<th>비고</th>			  
			</tr>
		</thead>
					<% 	} %>
		<tbody>
			<tr>
				<td class="br_none"><%= StringUtil.nvl(deliVO.getMaterial_id()) %>
					<input type="hidden" name="material_id" id="material_id<%=i%>" value="<%= StringUtil.nvl(deliVO.getMaterial_id()) %>"/>
				</td>
				<td class="br_none"><%= StringUtil.nvl(deliVO.getMaterial_nm()) %>
					<input type="hidden" name="material_nm" id="material_nm<%=i%>" value="<%= StringUtil.nvl(deliVO.getMaterial_nm()) %>"/>					
					<input type="hidden" name="cust_nm" id="cust_nm<%=i%>" value="<%= StringUtil.nvl(deliVO.getCust_nm()) %>"/>
					<input type="hidden" name="cust_id" id="cust_id<%=i%>" value="<%= StringUtil.nvl(deliVO.getCust_id()) %>"/>
					<input type="hidden" name="balju_no" id="balju_no<%=i%>" value="<%= StringUtil.nvl(deliVO.getBalju_no()) %>"/>
				</td>				
				<td class="br_none"><%= StringUtil.nvl(deliVO.getStandard()) %>
					<input type="hidden" name="standard" id="standard<%=i%>" value="<%= StringUtil.nvl(deliVO.getStandard()) %>"/>
				</td>
				<td class="txtright"><%= StringUtil.nvl(deliVO.getQty()) %>
					<input type="hidden" name="qty" id="qty<%=i%>" value="<%= StringUtil.nvl(deliVO.getQty()) %>"/>
				</td>
				<td class="br_none"><%= StringUtil.nvl(deliVO.getUnit()) %>
					<input type="hidden" name="unit" id="unit<%=i%>" value="<%= StringUtil.nvl(deliVO.getUnit()) %>"/>
				</td>
				<td class="txtright"><%= StringUtil.nvl(deliVO.getDanga()) %>
					<input type="hidden" name="danga" id="danga<%=i%>" value="<%= StringUtil.nvl(deliVO.getDanga()) %>"/>
				</td>				
				<td class="txtright"><%= StringUtil.nvl(deliVO.getAmt()) %>					
					<input type="hidden" name="amt" id="amt<%=i%>" value="<%=StringUtil.nvl(deliVO.getAmt()) %>"/>
					<input type="hidden" name="amt_sum" id="amt_sum<%=i%>" value="<%=StringUtil.nvl(deliVO.getAmt_sum()) %>"/>	
					<input type="hidden" name="vat_sum" id="vat_sum<%=i%>" value="<%=StringUtil.nvl(deliVO.getVat_sum()) %>"/>
					<input type="hidden" name="tot_sum" id="tot_sum<%=i%>" value="<%=StringUtil.nvl(deliVO.getTot_sum()) %>"/>
				</td>						
				<%if (i == 0){ %>				
				<td rowspan=<%=listSize%> class="txtright"><%= StringUtil.nvl(deliVO.getAmt_sum()) %></td>
				<td rowspan=<%=listSize%> class="txtright"><%= StringUtil.nvl(deliVO.getVat_sum()) %></td>
				<td rowspan=<%=listSize%> class="txtright"><%= StringUtil.nvl(deliVO.getTot_sum()) %></td>
				<%}else {%>	
				<td></td>
				<td></td>
				<td></td>
				<%} %>	
				<td class="br_none"><%= StringUtil.nvl(deliVO.getBigo()) %>
					<input type="hidden" name="bigo" id="bigo<%=i%>" value="<%= StringUtil.nvl(deliVO.getBigo()) %>"/>
				</td>										
			</tr>
		</tbody>					
					<%	
					}					
				}
		}else{					
		%>
		<thead>	
			<tr>
				<th>입고일</th>
				<td colspan="4" class="date bdrn">				
					<span class="serch_date_box">
						<input class="serch_date" type="text" id="search_ymd" name="search_ymd" value="" readonly="readonly">																													
						<button type="button" class="btn_date">
							<span class="blind">날짜선택
							</span>
						</button>										
					</span>
					<button type="button" onclick="javascript:searchSlipNo(); return false;">전표번호조회</button>	
				</td>			
				<th colspan="2">전표번호</th>
				<td colspan="4">									
					<select colspan="7" class="serch_select02 w96" id="search_slip_no" name="search_slip_no" >						
					</select>
					<button type="button" onclick="javascript:searchSlipData(); return false;">전표자료조회</button>
				</td>
			</tr>	
			<tr>			  
				<th style="border-bottom: none;">원부자재코드</th>
				<th>원부자재명</th>				 
			  	<th>규격</th>	
			  	<th>입고수량</th>		 
			  	<th>단위</th>		  	  										
				<th>단가</th>
				<th>금액</th>	
				<th>공급가액</th>
				<th>부가세</th>
				<th>합계</th>
				<th>비고</th>				  
			</tr>
		</thead>
		<tbody>
			<tr class="in_table">				
				<td class="br_none">&nbsp
					<input type="hidden" name="material_id" id="material_id0" value=""/>					
					<input type="hidden" name="cust_id" id="cust_id0" value=""/>
					<input type="hidden" name="cust_nm" id="cust_nm0" value=""/>
					<input type="hidden" name="balju_no" id="balju_no0" value=""/>					
				</td>
				<td class="br_none"><input type="hidden" name="material_nm" id="material_nm0" value=""/></td>
				<td class="br_none"><input type="hidden" name="standard" id="standard0" value=""/></td>					
				<td class="txtright"><input type="hidden" name="qty" id="qty0" value=""/></td>
				<td class="br_none"><input type="hidden" name="unit" id="unit0" value=""/></td>
				<td class="txtright"><input type="hidden" name="danga" id="danga0" value=""/></td>
				<td class="txtright"><input type="hidden" name="amt" id="amt0" value=""/></td>
				<td class="txtright"><input type="hidden" name="amt_sum" id="amt_sum0" value=""/></td>
				<td class="txtright"><input type="hidden" name="vat_sum" id="vat_sum0" value=""/></td>
				<td class="txtright"><input type="hidden" name="tot_sum" id="tot_sum0" value=""/></td>	
				<td class="br_none"><input type="hidden" name="bigo" id="bigo0" value=""/></td>			
		<%} 
		%>
		</tbody>
	</table>
</div>	