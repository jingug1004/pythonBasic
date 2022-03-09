<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01008.jsp
 * @메뉴명 : 문서 상세조회 팝업-구매신청서
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
							<div class="inner_box no_scroll non_ipt">
								<strong class="tit_s tit_sample">
									내 역
								</strong>
								<table class="tbl_purchase" id="tbl">
									<colgroup>
										<col style="width:111px">
										<col style="width:50px">
										<col style="width:71px">
										<col style="width:97px">
										<col style="">
										<col style="width:118px">
									</colgroup>
									<thead>
										<th>품명</th>
										<th>규격</th>
										<th>수량</th>
										<th>납품요구일</th>
										<th>사무 및 목적</th>
										<th class="bdrn">비고</th>
									</thead>
									<tbody>
										<%
										if(purchaseDetailList.size() > 0){
											for(int i=0; purchaseDetailList.size()>i;i++){
												PurchaseVO purchaseVO = new PurchaseVO();
												purchaseVO = purchaseDetailList.get(i);
										%>
										<tr>
											<td><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getProduct_nm())%></td>
											<td><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getStandard())%></td>
											<td><%=StringUtil.makeMoneyType(Integer.parseInt(purchaseVO.getQty()))%></td>
											<td class="date">
												<span class="date_box">
													<%=StringUtil.nvl(purchaseVO.getDeliver_req_ymd())%>
												</span>
											</td>
											<td><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getPurpose())%></td>
											<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getBigo())%></td>
										</tr>
										<%		
											}
										}	
										%>
									</tbody>
								</table>
							</div>	