<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : approvalPreviewPopup.jsp
 * @메뉴명 : 결재문서 미리보기
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.AccidentVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ImplDeptVO" %>
<%@ include file ="/common/path.jsp" %>

<%
	/* 첨부파일 */
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList");
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	/* 결재 */
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
		
	String state = StringUtil.nvl((String)request.getAttribute("state"));
	String docu_cd = StringUtil.nvl((String)request.getAttribute("docu_cd"));	//문서코드
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));	//결재 문서번호	
	String includePage = "preview"+docu_cd+".jsp";	//신규문서 파일명
	
	Integer winWidth = 0;  //CHOE 20160114 물품구입청구확인서 리사이즈 용 변수
	Integer winResize = 0;
	
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/styles.css" />
	<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/fileDrag.js"></script>
	<script type="text/javascript">
	
	winWidth = $(window).width();
	winResize = 1446 - winWidth;  //CHOE 20160113 화면이 1120보다 커지지 않는다.
	//alert("winResize : "+winResize);
	<%if("E01015".equals(docu_cd)|| "E01016".equals(docu_cd)) {%>		
		$(document).ready(function() {
			window.resizeBy(winResize, 0);			
		});		
	<%
	}%>
	
	
	</script>
</head>
<body>

<div class="wrap_pop_bg2 no_bg">
<div class="wrap_receive_document_h wrap_document_print_h" id="appPrint">
	<div class="top appPrint">
		<h3>인쇄<span> <%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></span></h3>
		<button class="close" onclick="javascript:window.close();"><span class="blind">닫기</span></button>
		<div>
			<div class="fr">
				<button type="button" class="type2" onclick="javascripr:Commons.directPreviewOpen('appPrint', '785', '768');">인쇄</button>
			</div>
		</div>
	</div>
	<div class="inner">
		<div class="cont_box cont_table06">
			<h4><strong><%=approvalMasterVO.getDocu_nm() %></strong></h4>
			<div class="refer">
				<ul>
					<li><%if("Y".equals(approvalMasterVO.getDecide_yn())){ %>전결 가능합니다.<%} %></li>
					<li><%if("Y".equals(approvalMasterVO.getSecurity_yn())){ %>대외비입니다.<%} %></li>
				</ul>
			</div>		
			<div class="confirm">
				<table class="over">
					<colgroup>
					<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
					</colgroup>
					<tbody>
						<tr>
							<th>문서번호</th>
							<td><%=approval_seq %></td>
							<th style="border-bottom: none;">작성일자</th>
							<td><%=StringUtil.nvl(approvalMasterVO.getMake_dt())%></td>
							<th>작성부서</th>
							<td><%=StringUtil.nvl(approvalMasterVO.getDept_ko_nm())%></td>
							<th>작성자</th>
							<td class="br_none"><%=StringUtil.nvl(approvalMasterVO.getEmp_ko_nm())%></td>
						</tr>
						<tr>
							<th>청구결재</th>
							<td colspan="3" class="confirm_box">
						      	<table>
								  <colgroup>
								    <col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							      </colgroup>
								  <tbody>
									<tr>
								    <% 
										if(approvalDetailList.size() > 0){
											for(int i=0; approvalDetailList.size()>i;i++){
												ApprovalVO approvalVO = new ApprovalVO();
												approvalVO = approvalDetailList.get(i);
												/*CHOE 201151230 청구결재는 7칸만 사용 한다.
												getGroup_div == "1"인 경우는 청구결재에 넣고 2인 경우는 넣지 않는다.
												넣지 않는 빈칸는 <td>로 채운다.
												*/
												if (i < 7) {
													if ("1".equals(approvalVO.getGroup_div())){
										%>			
										<th><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></th>
										<%			} else { %>
										<th></th>							
										<%			}
												}
											} 
											/*CHOE 20160121 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
											for(int j=7; approvalDetailList.size() < j;j--) {%>
										<th></th>			
											<%}		
										}%>
							        </tr>
								    <tr>
								    	<%
											if(approvalDetailList.size() > 0){
												for(int i=0; approvalDetailList.size()>i;i++){
													ApprovalVO approvalVO = new ApprovalVO();
													approvalVO = approvalDetailList.get(i);
													/*CHOE 201151230 청구결재는 7칸만 사용 한다.
													getGroup_div == "1"인 경우는 청구결재에 넣고 2인 경우는 넣지 않는다.
													넣지 않는 빈칸는 <td>로 채운다.
													*/
													if (i < 7) {
														if ("1".equals(approvalVO.getGroup_div())){
											%>			
											<td><%=i+1 %><br /><br /><%=StringUtil.nvl(approvalVO.getEmp_ko_nm() )%></td>
											<%			} else { %>
											<td></td>							
											<%			}
													}
												} 
												/*CHOE 20160121 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
												for(int j=7; approvalDetailList.size() < j;j--) {%>
											<td></td>			
												<%}	
											}%>
							        </tr>
							      </tbody>
								</table>
					      	</td>
					      	<%if("E01015".equals(docu_cd)){ %>						
							<th>확인결재</th>
							<td colspan="3" class="confirm_box">
								<table>
								  <colgroup>
								   	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							      </colgroup>
								  <tbody>
								    <tr>
								    	<%
											if(approvalDetailList.size() > 0){
												for(int i=0; approvalDetailList.size()>i;i++){
													ApprovalVO approvalVO = new ApprovalVO();
													approvalVO = approvalDetailList.get(i);
													/*CHOE 201151230 확인결재는 7칸만 사용 한다.
													getGroup_div == "2"인 경우는 확인결재에 넣고 1인 경우는 넣지 않는다.
															"2"인 경우 정보를 미리 채우고 남은(2칸 :확인결재5명) 빈칸는 <td>로 채운다.
													*/
													if (i < 14) {
														if ("2".equals(approvalVO.getGroup_div())){
											%>			
											<th><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></th>
											<%			} 
													}
												}%>
											<% } %>
											<th></th><th></th><th></th><th></th>
							        </tr>
								    <tr>
								      <%
										if(approvalDetailList.size() > 0){
											int k = 1;
											for(int i=0; approvalDetailList.size()>i;i++){
												ApprovalVO approvalVO = new ApprovalVO();
												approvalVO = approvalDetailList.get(i);
												/*CHOE 201151230 확인결재는 7칸만 사용 한다.
												getGroup_div == "2"인 경우는 확인결재에 넣고 1인 경우는 넣지 않는다.
												"2"인 경우 정보를 미리 채우고 남은(2칸 :확인결재5명) 빈칸는 <td>로 채운다.
												*/
												if (i < 14) {
													if ("2".equals(approvalVO.getGroup_div())){
										%>						
										<td><%=k %><br /><br /><%=StringUtil.nvl(approvalVO.getEmp_ko_nm() )%></td>
										<%			k += 1;
													}
												}
											} %>								
										<% } %>
										<td></td><td></td><td></td><td></td>
							        </tr>
							      </tbody>
								</table>
							</td>
							<%}else{/*원부자재 납품확인서*/ %>
							<th></th>
							<td colspan="3" class="confirm_box">								
							</td>
							<%} %>
						</tr>							
						<tr>
							<th>제목</th>
							<td colspan="7"><%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></td>
						</tr>
					</tbody>
				</table>
				
				<jsp:include page="<%=includePage%>"/>
			</div>			
		</div>
	</div>	
</div>
</div>
</body>
</html>