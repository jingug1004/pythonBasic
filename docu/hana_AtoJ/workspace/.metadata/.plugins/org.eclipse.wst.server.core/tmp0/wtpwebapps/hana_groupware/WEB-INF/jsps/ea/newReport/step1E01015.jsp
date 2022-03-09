<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01015.jsp
 * @메뉴명 : step1신규문서작성-물품 구입 청구서
 * @최초작성일 : 2015/12/11            
 * @author : 전산팀                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.RequisitionVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<RequisitionVO> requisitionDetailList = (List<RequisitionVO>)request.getAttribute("requisitionDetail");
	//List<RequisitionVO> searchReqNoList = (List<RequisitionVO>)request.getAttribute("searchReqNo");
	
	String search_req_ymd = StringUtil.nvl((String)request.getAttribute("search_req_ymd")); //청구일
	//search_req_ymd = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", search_req_ymd));
	
	String search_req_no = StringUtil.nvl((String)request.getAttribute("search_req_no")); //청구번호
	//search_req_no = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", search_req_no));
	
%>
<script type="text/javascript">
	$(window).load(function() {
		$("#search_req_no > option[value=<%=search_req_no%>]").attr("selected","true");
	});
	
	/**
	 * 벨리데이션 체크
	 * @returns {Boolean}
	 */
	function saveStep1(){	
		if(formCheck.isNull(document.frm.search_req_ymd, "청구일이 없습니다. 먼저 청구일을 선택해 주세요.")){
			search_req_ymd.focus();
			return			
		}
		
		if(document.getElementById("search_req_no").value == ""){
			alert("청구번호을 선택해 주세요.(선택할 청구번호가 없는 경우 청구번호조회 버튼 누르시기 바랍니다.)");
			search_req_no.focus();
			return			
		}
		
		if(formCheck.isNull(document.frm.material_id0, "자료가 없습니다. 청구자료조회 버튼을 이용하여 자료를 조회하시기 바랍니다.")){			
			search_req_no.focus();
			return			
		}
		return true;
	}
	 
	/*CHOE 20151214 날짜에 따른 물품 구입 청구서 req_no 조회
	*/
	function searchReqNo(){		
		if(formCheck.isNull(document.frm.search_req_ymd, "청구일이 없습니다. 청구일을 선택해 주세요.")){
			search_req_ymd.focus();
			return			
		}	
		//청구일을 다시 조회 하면 청구번호를 비운다. : 다른 청구일을 조회 하기 위해서 -> NewReportController 2-확인-2. 청구일은 있고 청구 번호만 없는 경우 이동함	
		document.getElementById("search_req_no").value = "";
		
		document.frm.action='<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do';
		document.frm.submit();			
	}
	 
	/*CHOE 20151214 req_no에 따라 물품 구입 청구서 조회
	*/
	function searchReqData(){
		//alert("------- 자료 2 -------"+document.frm.getElementById("search_req_no").value);//값 조회 불가
		//alert("------- 자료 2 -------"+StringUtil.nvl(document.frm.search_req_no));//값 조회 불가
		//alert("------- 자료 2 -------"+StringUtil.nvl((String)request.getAttribute("search_req_no")));  //오류
		//alert("------- 자료 2 -------"+search_req_no);  //조회 불가
		//alert("------- 자료 2 -------"+document.getElementById("search_req_no").value);	//조회 가능	
		
		if(formCheck.isNull(document.frm.search_req_ymd, "청구일이 없습니다. 먼저 청구일을 선택해 주세요.")){
			search_req_ymd.focus();
			return			
		}	
		
		if (document.getElementById("search_req_no").value == ""){
			alert("청구번호을 선택해 주세요.(선택할 청구번호가 없는 경우 청구번호조회 버튼 누르시기 바랍니다.)");
			search_req_no.focus();			
			return			
		}		
		
		document.frm.action='<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do';
		document.frm.submit();			
	}
</script>
<h5>
	<span class="w_date1">청구일 :</span>
	<span class="w_date2">청구번호 :</span>	
</h5>
<div class="confirm">
	<table class="over">
		<colgroup>			
			<col style="width:17%;">
			<col style="width:11%;">
			<col style="width:9%;">
			<col style="width:10%;">
			<col style="width:10%;">
			<col style="width:7%;">
			<col style="width:9%;">
			<col style="width:8%;">
			<col style="width:8%;">
			<col style="width:12%;">	
			<col style="width:10%;">
			<col style="width:10%;">					
		</colgroup>
		<%
		if(requisitionDetailList.size() > 0){
			for(int i=0; requisitionDetailList.size()>i;i++){
				RequisitionVO reqVO = new RequisitionVO();
				reqVO = requisitionDetailList.get(i);
				if (i==0){
		%>
		<thead>
			<tr>
				<th>청구일</th>
				<td colspan="3" class="date bdrn">				
					<span class="serch_date_box">
						<input class="serch_date" type="text" id="search_req_ymd" name="search_req_ymd" value="<%=StringUtil.nvl(reqVO.getReq_ymd()) %>" readonly="readonly">																													
						<button type="button" class="btn_date">
							<span class="blind">날짜선택
							</span>
						</button>										
					</span>											
					<button type="button" onclick="javascript:searchReqNo(); return false;">청구번호조회</button>								
				</td>				
				<th colspan="3">청구번호</th>
				<td colspan="5">																
					<select colspan="7" class="serch_select02 w96" id="search_req_no" name="search_req_no" onchange="readonlyCheck();" >
					<%
					for(int j=0; requisitionDetailList.size()>j;j++){
						RequisitionVO reqListVO = new RequisitionVO();
						reqListVO = requisitionDetailList.get(j);
					%>
					<option value="<%=reqListVO.getReq_no()%>"><%=reqListVO.getReq_no()%>-<%=reqListVO.getSawon_nm()%></option>
					<%
					}
					%>							
					</select>
					<input type="hidden" id="search_req_no" name="search_req_no" value="<%=search_req_no %>" />
					<button type="button" onclick="javascript:searchReqData(); return false;">청구자료조회</button>
				</td>
			</tr>
		<thead>
				<%
						}
					if ("".equals(StringUtil.nvl(reqVO.getMaterial_id())) ){
						if (i==0){
					%>
		<thead>						
			<tr>			  
				<th>원부자재명</th>			 
			  	<th>규격</th>	
			  	<th>사용목적</th>		 
			  	<th>납기요구일</th>		  	  										
				<th style="border-bottom: none;">거래처</th>
				<th>현재고</th>				
				<th>청구수량</th>				
				<th>단위</th>
				<th>예정단가</th>			
				<th>비고</hr>	
				<th>납품일자</th>			
				<th>납품확인자</hr>			  
			</tr>
		</thead>
		<tbody>			
			<tr class="in_table">
				<td class="br_none">&nbsp
					<input type="hidden" name="material_id" id="material_id0" value=""/>
					<input type="hidden" name="material_nm" id="material_nm0" value=""/>
				</td>
				<td class="br_none"><input type="hidden" name="standard" id="standard0" value=""/></td>						
				<td class="br_none"><input type="hidden" name="usage" id="usage0" value=""/></td>
				<td class="br_none"><input type="hidden" name="ipgo_ymd" id="ipgo_ymd0" value=""/></td>
				<td class="br_none">
					<input type="hidden" name="cust_id" id="cust_id0" value=""/>
					<input type="hidden" name="cust_nm" id="cust_nm0" value=""/>
				</td>
				<td class="txtright"><input type="hidden" name="hyunjaego_qty" id="hyunjaego_qty0" value=""/></td>
				<td class="txtright"><input type="hidden" name="qty" id="qty0" value=""/></td>
				<td class="br_none"><input type="hidden" name="unit" id="unit0" value=""/></td>
				<td class="txtright"><input type="hidden" name="danga" id="danga0" value=""/></td>
				<td>
					<input type="hidden" name="bigo" id="bigo0" value=""/>
					<input type="hidden" name="import_yn" id="import_yn0" value=""/>
					<input type="hidden" name="amt" id="amt0" value=""/>				
				</td>
				<td class="br_none"><input type="hidden" name="tax_ymd" id="tax_ymd0" value=""/></td>
				<td class="br_none"><input type="hidden" name="sawon_id" id="sawon_id0" value=""/></td>
				<td class="br_none"><input type="hidden" name="requestno" id="requestno0" value=""/></td>									
			</tr>
		</tbody>						
				<%
						}
					}else{						
						if (i==0){
				%>
		</thead>					
			<tr>			  
				<th>원부자재명</th>			 
			  	<th>규격</th>	
			  	<th>사용목적</th>		 
			  	<th>납기요구일</th>		  	  										
				<th style="border-bottom: none;">거래처</th>
				<th>현재고</th>				
				<th>청구수량</th>				
				<th>단위</th>
				<th>예정단가</th>			
				<th>비고</hr>	
				<th>납품일자</th>			
				<th>납품확인자</hr>			  
			</tr>
		</thead>
					<% 	} %>
		<tbody>
			<tr>
				<td class="br_none"><%= StringUtil.nvl(reqVO.getMaterial_nm()) %>
					<input type="hidden" name="material_nm" id="material_nm<%=i%>" value="<%= StringUtil.nvl(reqVO.getMaterial_nm()) %>"/>
					<input type="hidden" name="material_id" id="material_id<%=i%>" value="<%= StringUtil.nvl(reqVO.getMaterial_id()) %>"/>
				</td>				
				<td class="br_none"><%= StringUtil.nvl(reqVO.getStandard()) %>
					<input type="hidden" name="standard" id="standard<%=i%>" value="<%= StringUtil.nvl(reqVO.getStandard()) %>"/>
				</td>				
				<td class="br_none"><%= StringUtil.nvl(reqVO.getUsage()) %>
					<input type="hidden" name="usage" id="usage<%=i%>" value="<%= StringUtil.nvl(reqVO.getUsage()) %>"/>
				</td>
				<td class="br_none"><%= StringUtil.nvl(reqVO.getIpgo_ymd()) %>
					<input type="hidden" name="ipgo_ymd" id="ipgo_ymd<%=i%>" value="<%= StringUtil.nvl(reqVO.getIpgo_ymd()) %>"/>
				</td>
				<td class="br_none"><%= StringUtil.nvl(reqVO.getCust_nm()) %>
					<input type="hidden" name="cust_nm" id="cust_nm<%=i%>" value="<%= StringUtil.nvl(reqVO.getCust_nm()) %>"/>
					<input type="hidden" name="cust_id" id="cust_id<%=i%>" value="<%= StringUtil.nvl(reqVO.getCust_id()) %>"/>
				</td>
				<td class="txtright"><%= StringUtil.nvl(reqVO.getHyunjaego_qty()) %>
					<input type="hidden" name="hyunjaego_qty" id="hyunjaego_qty<%=i%>" value="<%= StringUtil.nvl(reqVO.getHyunjaego_qty()) %>"/>
				</td>
				<td class="txtright"><%= StringUtil.nvl(reqVO.getQty()) %>
					<input type="hidden" name="qty" id="qty<%=i%>" value="<%= StringUtil.nvl(reqVO.getQty()) %>"/>
				</td>
				<td class="br_none"><%= StringUtil.nvl(reqVO.getUnit()) %>
					<input type="hidden" name="unit" id="unit<%=i%>" value="<%= StringUtil.nvl(reqVO.getUnit()) %>"/>
				</td>
				<td class="txtright"><%= StringUtil.nvl(reqVO.getDanga()) %>
					<input type="hidden" name="danga" id="danga<%=i%>" value="<%= StringUtil.nvl(reqVO.getDanga()) %>"/>
				</td>				
				<td class="br_none"><%= StringUtil.nvl(reqVO.getBigo()) %>
					<input type="hidden" name="bigo" id="bigo<%=i%>" value="<%=StringUtil.nvl(reqVO.getBigo()) %>"/>
					<input type="hidden" name="import_yn" id="import_yn<%=i%>" value="<%=StringUtil.nvl(reqVO.getImport_yn()) %>"/>
					<input type="hidden" name="amt" id="amt<%=i%>" value="<%=StringUtil.nvl(reqVO.getAmt()) %>"/>
				</td>
				<td class="br_none"><%= StringUtil.nvl(reqVO.getTax_ymd()) %>
					<input type="hidden" name="tax_ymd" id="tax_ymd<%=i%>" value="<%=StringUtil.nvl(reqVO.getTax_ymd()) %>"/>	
				</td>
				<td class="br_none"><%= StringUtil.nvl(reqVO.getSawon_id()) %>
					<input type="hidden" name="sawon_id" id="sawon_id<%=i%>" value="<%=StringUtil.nvl(reqVO.getSawon_id()) %>"/>
					<input type="hidden" name="requestno" id="requestno<%=i%>" value="<%=StringUtil.nvl(reqVO.getRequestno()) %>"/>	
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
				<th>청구일</th>
				<td colspan="3" class="date bdrn">				
					<span class="serch_date_box">
						<input class="serch_date" type="text" id="search_req_ymd" name="search_req_ymd" value="" readonly="readonly">																													
						<button type="button" class="btn_date">
							<span class="blind">날짜선택
							</span>
						</button>										
					</span>
					<button type="button" onclick="javascript:searchReqNo(); return false;">청구번호조회</button>	
				</td>			
				<th colspan="3">청구번호</th>
				<td colspan="5">									
					<select colspan="7" class="serch_select02 w96" id="search_req_no" name="search_req_no" >						
					</select>
					<button type="button" onclick="javascript:searchReqData(); return false;">청구자료조회</button>
				</td>
			</tr>	
			<tr>			  
				<th>원부자재명</th>			 
			  	<th>규격</th>	
			  	<th>사용목적</th>		 
			  	<th>납기요구일</th>		  	  										
				<th style="border-bottom: none;">거래처</th>
				<th>현재고</th>				
				<th>청구수량</th>				
				<th>단위</th>
				<th>예정단가</th>			
				<th>비고</hr>	
				<th>납품일자</th>			
				<th>납품확인자</hr>			  
			</tr>
		</thead>
		<tbody>
			<tr class="in_table">
				<td class="br_none">&nbsp
					<input type="hidden" name="material_nm" id="material_nm0" value=""/>
					<input type="hidden" name="material_id" id="material_id0" value=""/>
				</td>				
				<td class="br_none"><input type="hidden" name="standard" id="standard0" value=""/></td>		
				<td class="br_none"><input type="hidden" name="usage" id="usage0" value=""/></td>
				<td class="br_none"><input type="hidden" name="ipgo_ymd" id="ipgo_ymd0" value=""/></td>	
				<td class="br_none">
					<input type="hidden" name="cust_id" id="cust_id0" value=""/>
					<input type="hidden" name="cust_nm" id="cust_nm0" value=""/>
				</td>											
				<td class="txtright"><input type="hidden" name="hyunjaego_qty" id="hyunjaego_qty0" value=""/></td>
				<td class="txtright"><input type="hidden" name="qty" id="qty0" value=""/></td>
				<td class="br_none"><input type="hidden" name="unit" id="unit0" value=""/></td>
				<td class="txtright"><input type="hidden" name="danga" id="danga0" value=""/>
				<td class="br_none">
					<input type="hidden" name="bigo" id="bigo0" value=""/>
					<input type="hidden" name="import_yn" id="import_yn0" value=""/>
					<input type="hidden" name="amt" id="amt0" value=""/>					
				</td>
				<td class="br_none"><input type="hidden" name="tax_ymd" id="tax_ymd0" value=""/></td>
				<td class="br_none"><input type="hidden" name="sawon_id" id="sawon_id0" value=""/>
				<td class="br_none"><input type="hidden" name="requestno" id="requestno0" value=""/>									
			</tr>
		<%} 
		%>
		</tbody>
	</table>
</div>	