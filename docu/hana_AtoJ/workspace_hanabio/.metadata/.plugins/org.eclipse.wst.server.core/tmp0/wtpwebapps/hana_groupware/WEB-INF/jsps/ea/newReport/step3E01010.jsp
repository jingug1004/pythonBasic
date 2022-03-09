<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01010.jsp
 * @메뉴명 : 문서 상세조회 팝업 - 증명서발급신청서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.ea.newReport.vo.CertificateVO" %>
<%
	//증명서발급신청서
	List<CertificateVO> certificateDetailList = (List<CertificateVO>)request.getAttribute("certificateDetailList");
%>
							<div class="inner_box no_scroll non_ipt">
								<strong class="tit_s tit_sample">
									내 역
								</strong>
								<table class="tbl_certificate" id="tbl">
									<colgroup>
										<col style="width:127px">
										<col style="width:72px">
										<col />
									</colgroup>
									<thead>
										<th>증명서 종류</th>
										<th>수량</th>
										<th class="bdrn">용도</th>
									</thead>
									<tbody>
										<%
										if(certificateDetailList.size() > 0){
											for(int j=0; certificateDetailList.size()>j;j++){
												CertificateVO certificateVO = new CertificateVO();
												certificateVO = certificateDetailList.get(j);
										%>
										<tr>
											<td><%=StringUtil.nvl(certificateVO.getCerti_nm())%></td>
											<td><%=StringUtil.makeMoneyType(Integer.parseInt(certificateVO.getQty()))%></td>
											<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", certificateVO.getYongdo())%></td>
										</tr>
										<%		
											}
										}	
										%>
									</tbody>
								</table>
							</div>	