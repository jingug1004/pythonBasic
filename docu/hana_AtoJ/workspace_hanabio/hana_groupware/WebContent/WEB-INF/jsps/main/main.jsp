<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : main.jsp
 * @메뉴명 : 메인페이지    
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.WebUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ page import="com.hanaph.gw.of.notice.vo.NoticeVO" %>
<%@ page import="com.hanaph.gw.of.message.vo.MessageVO" %>
<%@ page import="com.hanaph.gw.ea.receive.vo.ReceiveVO" %>
<%@ page import="com.hanaph.gw.ea.report.vo.ReportVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>

<%@ include file ="/common/path.jsp" %>
<%

	//http:// 로 들어왔을 경우 https://로 redirect
	String url = request.getRequestURL().toString();

	//https:// 로 시작하고, 운영 url일 경우에만 redirect	
	if( url.startsWith("https://") && ( url.indexOf("gw.") > 1) ) {
		response.sendRedirect(GROUP_ROOT + "/main/main.do");
	}

	/* 쪽지함 */
	List<MessageVO> messageList = (List<MessageVO>)request.getAttribute("messageList");
	
	/* 공지사항 */
	List<NoticeVO> noticeList = (List<NoticeVO>)request.getAttribute("noticeList");
	
	/* 내가올린문서 */
	List<ReportVO> reportList = (List<ReportVO>)request.getAttribute("reportList");
	
	/* 내가받은문서 */
	List<ReceiveVO> receiveList = (List<ReceiveVO>)request.getAttribute("receiveList");
	
	/* 비밀번호 변경 대상체크 */
	boolean pass_change_chk = (Boolean)request.getAttribute("pass_change_chk");
	
	/*내가올린문서 카운트*/
	List<ApprovalMasterVO> mainReportCnt = (List<ApprovalMasterVO>)request.getAttribute("mainReportCnt");
	
	/*내가받은문서 카운트*/
	List<ApprovalMasterVO> mainReceiveCnt = (List<ApprovalMasterVO>)request.getAttribute("mainReceiveCnt");
	
	/*공유문서 카운트*/
	int shareTotalCount = (Integer)request.getAttribute("shareTotalCount");
	
	/*장기미결재 카운트*/
	int longTermReceiveCount = (Integer)request.getAttribute("longTermReceiveCount");
	
	/*일주일 전날짜 가져오기*/
	String searchDate[] = WebUtil.dateCal1(-7);
	
	String chgPasswordReason = session.getAttribute("chgPasswordReason") == null ? "" : (String)session.getAttribute("chgPasswordReason");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		/**
		 * 화면 시작시 패스워드 변경 대상일 경우 레이어팝업 열기
		 */
		$(document).ready(function() {
			if(<%=pass_change_chk%>){
				$('#pop_pass_change').bPopup({
					content:'iframe', //'ajax', 'iframe' or 'image'
					iframeAttr:'scrolling="no" frameborder="0" width="290px" height="239px"',
					follow: [true, true],
					contentContainer:'.pass_content',
					modalClose: false,
		            opacity: 0.6,
		            positionStyle: 'fixed',
					loadUrl:'<%=GROUP_ROOT%>/co/login/passwordChangeForm.do',
				}, function(){
					if ("<%=chgPasswordReason%>" != "") {
						alert("<%=chgPasswordReason%>");
					}
				});
			}
		});
		
		/**
		 * 레이어팝업 닫기
		 */
		function layerClose(){ 
			$('#pop_pass_change').bPopup().close();
		}
		
		/**
		 * 쪽지함으로 이동
		 * @param seq
		 */
		function goMessage(seq){
			document.getElementById("seq").value = seq;
			document.getElementById("frm").action ="<%=GROUP_ROOT%>/of/message/messageList.do?type=1";
			document.getElementById("frm").submit();
		}
	
		/**
		 * 공지사항으로 이동
		 * @param seq
		 */
		function goNotice(seq){
			document.getElementById("seq").value = seq;
			document.getElementById("frm").action ="<%=GROUP_ROOT %>/of/notice/noticeDetail.do";
			document.getElementById("frm").submit();
		}
		
		/**
		 * 내가올린문서 이동 후 팝업 띄움
		 * @param approval_seq
		 */
		function goReportApprovalPopup(approval_seq){
			location.href='<%=GROUP_ROOT%>/ea/report/reportList.do';
			Commons.popupOpen('appPop','<%=GROUP_ROOT%>/ea/newReport/approvalPopup.do?approval_seq='+approval_seq+'&menu=0202','820','850');
		}
		
		/**
		 * 내가받은문서 이동 후 팝업 띄움
		 * @param approval_seq
		 */
		function goReceiveApprovalPopup(approval_seq, docu_state){
			location.href='<%=GROUP_ROOT%>/ea/receive/receiveList.do';
			Commons.popupOpen('appPop','<%=GROUP_ROOT%>/ea/newReport/approvalPopup.do?docu_state='+docu_state+'&approval_seq='+approval_seq+'&menu=0203','820','850');
		}
		
		/**
		 * 내가올린문서 > 스텝3 팝업
		 * @param approval_seq
		 * @param docu_seq
		 */
		function goStep3Pop(approval_seq, docu_seq){
			location.href='<%=GROUP_ROOT%>/ea/report/reportList.do';
			Commons.popupOpen('step3Pop','<%=GROUP_ROOT%>/ea/newReport/step3approvalPopup.do?approval_seq='+approval_seq+'&docu_seq='+docu_seq,'820','850');
		}
		
		/**
		 * 내가올린문서 > 스텝2 팝업
		 * @param approval_seq
		 * @param docu_seq
		 * @param docu_cd
		 */
		function goStep2Pop(approval_seq, docu_seq, docu_cd){
			location.href='<%=GROUP_ROOT%>/ea/report/reportList.do';
			Commons.popupOpen('step3Pop','<%=GROUP_ROOT%>/ea/newReport/step2approvalPopup.do?approval_seq='+approval_seq+'&docu_cd='+docu_cd+'&docu_seq='+docu_seq,'820','850');
		}
		
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	<form id="frm" name="frm" method="post">
		<input type="hidden" id="seq" name="seq" value="">
		<input type="hidden" id="cd" name="cd" value="">
	</form>
	<div class="wrap_con">
		<div class="content">
			
			<!-- ######## start ####### -->
			<div class="cont">
				<div class="wrap_main">
					<div class="wrap_list">
						<div class="float_l mBox">
							<h2>공지사항</h2>
							<ul>
							<%
							if(noticeList.size()!=0){
								for(int i=0; noticeList.size()>i;i++){
									NoticeVO noticeVO = new NoticeVO();
									noticeVO = noticeList.get(i);
									String subject = noticeVO.getSubject();
									if(subject.length() > 30){
										subject = noticeVO.getSubject().substring(0,30) + "...";
									}
							%>
								<li<%if(!"Y".equals(noticeVO.getRead_yn())){%> class="on"<%}%>>
									<a href="#" onclick="javascript:goNotice('<%=noticeVO.getSeq()%>'); return false;">
										<span class="tit"><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", subject)) %></span>
										<span class="date"><%=StringUtil.substring(StringUtil.nvl(noticeVO.getCreate_dt()), 10)%></span>
									</a>
								</li>
							<%	} 
							}%>
							</ul>
							<%
							if(noticeList.size()!=0){
							%>	
							<p class="read_more"><a href="javascript:location.href='<%=GROUP_ROOT%>/of/notice/noticeList.do'">+ 더보기</a></p>
							<%
							}
							%>
						</div>
						<div class="float_l mBox ml30">
							<h2>쪽지</h2>
							<ul>
							<%
							if(messageList.size()!=0){
								for(int i=0; messageList.size()>i;i++){
									MessageVO messageVO = new MessageVO();
									messageVO = messageList.get(i);
									String subject = messageVO.getSubject();
									if(subject.length() > 30){
										subject = messageVO.getSubject().substring(0,30) + "...";
									}
							%>
								<li<%if(!"Y".equals(messageVO.getRead_yn())){%> class="on"<%}%>>
									<a href="#" onclick="javascript:goMessage('<%=messageVO.getMessage_seq()%>'); return false;">
										<span class="tit"><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", subject)) %></span>
										<span class="date"><%=StringUtil.substring(StringUtil.nvl(messageVO.getCreate_dt()), 10) %></span>
									</a>
								</li>
							<%	} 
							}%>
							</ul>
							<%
							if(messageList.size()!=0){
							%>	
							<p class="read_more"><a href="javascript:location.href='<%=GROUP_ROOT%>/of/message/messageList.do?type=1'">+ 더보기</a></p>
							<%
							}
							%>
						</div>
					</div>

					<div class="settlement_cont mt40">
						<div class="settlement_box01">
							<div class="fl">
								<h2>내가올린문서</h2>
								<div class="tableB cont_table06 table_width03">
									<table>
										<colgroup>
											<col style="width:186px"/>
											<col style="width:186px"/>
										</colgroup>
										<tbody>
											<%
											if(mainReportCnt.size()!=0){
												for(int i=0; mainReportCnt.size()>i;i++){
													ApprovalMasterVO approvalMasterVO = new ApprovalMasterVO();
													approvalMasterVO = mainReportCnt.get(i);
											%>
											<tr>
												<th class="bt_none"><%=approvalMasterVO.getState_nm()%></th>
												<td class="bt_none">
													<a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/report/reportList.do?search_docu_state=<%=approvalMasterVO.getState()%>'">
														<%=StringUtil.nvl(String.valueOf(approvalMasterVO.getCnt()),"0")%>건
													</a>
												</td>
											</tr>
											<%
												}
											}
											%>
										</tbody>
									</table>
								</div>
								<div class="case">
									<h2>장기미결재</h2>
									<p class="settle_txt"><a href="javascript:location.href='<%=GROUP_ROOT%>/ea/receive/receiveList.do?search_appr_state=E03001&search_start_ymd=2015-01-01&search_end_ymd=<%=searchDate[0]%>'"><%=longTermReceiveCount%></a>&nbsp;건</p>
								</div>
							</div>
							
							<div class="fr">
								<h2>내가받은문서</h2>
								<div class="tableB cont_table06 table_width03">
									<table>
										<colgroup>
											<col style="width:186px"/>
											<col style="width:186px"/>
										</colgroup>
										<tbody>
											<tr>
												<th class="bt_none"></th>
												<th class="bt_none">결재</th>
											</tr>
											<%
											if(mainReceiveCnt.size()!=0){
												for(int i=0; mainReceiveCnt.size()>i;i++){
													ApprovalMasterVO approvalMasterVO = new ApprovalMasterVO();
													approvalMasterVO = mainReceiveCnt.get(i);
											%>
											<tr>
												<th><%=approvalMasterVO.getState_nm()%></th>
												<td><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/receive/receiveList.do?search_docu_state=<%=approvalMasterVO.getState()%>'"><%=approvalMasterVO.getCnt() %>건</a></td>
											</tr>
											<%
												}
											}
											%>
										</tbody>
									</table>
								</div>
								<div class="case">
									<h2>공유문서</h2>
									<p class="settle_txt"><a href="#" onclick="location.href='<%=GROUP_ROOT%>/ea/share/shareList.do?search_start_dt=2015-01-01&search_read_yn=N'"><%=shareTotalCount%></a>&nbsp;건</p>
								</div>					
							</div>
						</div>
						<div class="settlement_box02">
							<div class="settle_tit_box">
								<h2 class="fl">내가올린문서</h2>
								<%
								if(reportList.size()!=0){
								%>	
								<p class="more fr"><a href="javascript:location.href='<%=GROUP_ROOT%>/ea/report/reportList.do'">+ 더보기</a></p>
								<%
								}
								%>
							</div>
							<div class="tableB cont_table06 table_width02 rpx">
								<table class="tr_over">
									<colgroup>
										<col style="width:135px"/>
										<col style="width:120px"/>
										<col />
										<col style="width:70px"/>
										<col style="width:90px"/>
										<col style="width:90px"/>
										<col style="width:80px"/>
									</colgroup>
									<thead>
										<tr>
											<th class="bt_none">문서번호</th>
											<th class="bt_none">문서종류</th>
											<th class="bt_none">제목</th>
											<th class="bt_none">기안자</th>
											<th class="bt_none">기안일</th>
											<th class="bt_none">문서상태</th>
											<th class="bt_none">처리자</th>
										</tr>
									</thead>
									<tbody>
										<%
										if(reportList.size()!=0){
											for(int i=0; reportList.size()>i;i++){
												ReportVO reportVO = new ReportVO();
												reportVO = reportList.get(i);
												String[] last_line_nm = new String[2]; 
												if(!"".equals(StringUtil.nvl(reportVO.getLast_line_nm()))){
													last_line_nm = reportVO.getLast_line_nm().split("\\^");
												}
										%>
											<%if("1".equals(reportVO.getStep_state())){ %>
											<tr onclick="javascript:goStep2Pop('<%=reportVO.getApproval_seq()%>','<%=reportVO.getDocu_seq()%>','<%=reportVO.getDocu_cd()%>'); return false;"
												<%if("E02003".equals(reportVO.getState())){%>class="fc_blue"<%}else if("E02005".equals(reportVO.getState())){%>class="fc_red"<%}else{%><%}%>>
											<%}else if("E02001".equals(reportVO.getState())){ %>
											<tr onclick="javascript:goStep3Pop('<%=reportVO.getApproval_seq()%>','<%=reportVO.getDocu_seq()%>'); return false;"
												<%if("E02003".equals(reportVO.getState())){%>class="fc_blue"<%}else if("E02005".equals(reportVO.getState())){%>class="fc_red"<%}else{%><%}%>>
											<%}else{%>
											<tr onclick="javascript:goReportApprovalPopup('<%=reportVO.getApproval_seq() %>'); return false;"
												<%if("E02003".equals(reportVO.getState())){%>class="fc_blue"<%}else if("E02005".equals(reportVO.getState())){%>class="fc_red"<%}else{%><%}%>>
											<%}%>
											<td><%=StringUtil.nvl(reportVO.getApproval_seq())%></td>
											<td><%=StringUtil.nvl(reportVO.getDocu_nm())%></td>
											<td><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", reportVO.getSubject()))%></td>
											<td><%=StringUtil.nvl(reportVO.getCreate_no())%></td>
											<td><%=StringUtil.substring(StringUtil.nvl(reportVO.getReq_dt()), 10)%></td>
											<td><%=StringUtil.nvl(reportVO.getState_nm())%></td>
											<td><%=StringUtil.nvl(last_line_nm[1])%></td>
										</tr>
										<%	
											} 
										}else{
										%>
										<tr>
											<td colspan="7">데이터가 없습니다.</td>
										</tr>
										<%
										}
										%>
									</tbody>
								</table>
							</div>
						</div>
						<div class="settlement_box02">
							<div class="settle_tit_box">
								<h2 class="fl">내가받은문서</h2>
								<%
								if(reportList.size()!=0){
								%>	
								<p class="more fr"><a href="javascript:location.href='<%=GROUP_ROOT%>/ea/receive/receiveList.do'">+ 더보기</a></p>
								<%
								}
								%>
							</div>
							<div class="tableB cont_table06 table_width02 rpx">
								<table class="tr_over">
									<colgroup>
										<col style="width:135px"/>
										<col style="width:120px"/>
										<col />
										<col style="width:70px"/>
										<col style="width:90px"/>
										<col style="width:90px"/>
										<col style="width:80px"/>
									</colgroup>
									<thead>
										<tr>
											<th class="bt_none">문서번호</th>
											<th class="bt_none">문서종류</th>
											<th class="bt_none">제목</th>
											<th class="bt_none">기안자</th>
											<th class="bt_none">기안일</th>
											<th class="bt_none">문서상태</th>
											<th class="bt_none">처리자</th>
										</tr>
									</thead>
									<tbody>
										<%
										if(receiveList.size()!=0){
											for(int i=0; receiveList.size()>i;i++){
												ReceiveVO receiveVO = new ReceiveVO();
												receiveVO = receiveList.get(i);
												String[] last_line_nm = new String[2]; 
												if(!"".equals(StringUtil.nvl(receiveVO.getLast_line_nm()))){
													last_line_nm = receiveVO.getLast_line_nm().split("\\^");
												}
										%>
										<tr onclick="javascript:goReceiveApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"
										 <%if("E02005".equals(receiveVO.getDocu_cd())){%>class="fc_red"<%}else if("E02003".equals(receiveVO.getDocu_cd())){%>class="fc_blue"<%}%>>
											<td><%=StringUtil.nvl(receiveVO.getApproval_seq())%></td>
											<td><%=StringUtil.nvl(receiveVO.getDocu_nm())%></td>
											<td><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", receiveVO.getSubject()))%></td>
											<td><%=StringUtil.nvl(receiveVO.getReq_nm())%></td>
											<td><%=StringUtil.substring(StringUtil.nvl(receiveVO.getReq_dt()), 10)%></td>
											<td><%=StringUtil.nvl(receiveVO.getDocu_state())%></td>
											<td><%=StringUtil.nvl(last_line_nm[1])%></td>
										</tr>
										<%	
											} 
										}else{
										%>
										<tr>
											<td colspan="7">데이터가 없습니다.</td>
										</tr>
										<%
										}
										%>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				
				</div>
			</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	<!-- 비번변경 layer -->
	<div id='pop_pass_change' style='display:none; width:auto; height:auto; '>
		<div class='pass_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
	<!-- 2015-02-06 추가 -->
	<script type="text/javascript"> 
	$(document).ready(function(){
	
		$('table.tr_over tbody tr').mouseover(function(){
			$(this).addClass('hightlight');
		});
	
		$('table.tr_over tbody tr').mouseout(function(){
			$(this).removeClass('hightlight');
		});
	});
	</script>
	<!-- //2015-02-06 추가 -->
</body>
</html>