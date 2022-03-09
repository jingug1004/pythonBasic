<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01012.jsp
 * @메뉴명 : 문서 상세조회 팝업 - 시간외근무내역서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.OvertimeVO" %>
<%
	//시간외근무내역서
	List<OvertimeVO> overtimeDetailDetailList = (List<OvertimeVO>)request.getAttribute("overtimeDetailDetailList");
	String approval_seq_old = StringUtil.nvl((String)request.getAttribute("approval_seq_old"));
%>
							<input type="hidden" name="approval_seq_old" id="approval_seq_old" value="<%=approval_seq_old %>" >
							<div class="inner_box no_scroll non_ipt">
								<strong class="tit_s tit_sample">
									내 역
								</strong>
								<table class="tbl_overtime" id="tbl">
									<colgroup>
										<col style="width:61px">
										<col style="width:48px">
										<col style="width:65px">
										<col style="">
										<col style="width:86px">
										<col style="width:70px">
										<col style="width:70px">
										<col style="width:70px">
									</colgroup>
									<thead>
										<th>부서명</th>
										<th>성명</th>
										<th>근무구분</th>
										<th>업무내용</th>
										<th>근무일</th>
										<th>근무시작시간</th>
										<th>근무종료시간</th>
										<th class="bdrn">실근무시간</th>
									</thead>
									<tbody>
										<%
										if(overtimeDetailDetailList.size() > 0){
											for(int i=0; overtimeDetailDetailList.size()>i;i++){
												OvertimeVO overtimeVO = new OvertimeVO();
												overtimeVO = overtimeDetailDetailList.get(i);
										%>
										<tr>
											<td><%=StringUtil.nvl(overtimeVO.getDept_nm()) %></td>
											<td><%=StringUtil.nvl(overtimeVO.getEmp_nm()) %></td>
											<td><%=StringUtil.nvl(overtimeVO.getBigo_nm()) %></td>
											<td><%=RequestFilterUtil.toHtmlTagReplace("", overtimeVO.getBiz_content()) %></td>
											<td class="date">
												<span class="date_box">
													<%=StringUtil.nvl(overtimeVO.getWork_due_ymd()) %>
												</span>
											</td>
											<td><%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_start_hh())) %> : <%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_start_mi())) %></td>
											<td><%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_end_hh())) %> : <%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_end_mi())) %></td>
											<td class="bdrn"><%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_real_hh())) %> : <%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_real_mi())) %></td>
										</tr>
										<%		
											}
										}	
										%>
									</tbody>
								</table>
							</div>	
							