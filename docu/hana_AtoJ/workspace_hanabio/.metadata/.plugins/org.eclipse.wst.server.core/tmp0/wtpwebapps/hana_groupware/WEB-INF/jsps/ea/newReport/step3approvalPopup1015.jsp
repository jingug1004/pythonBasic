<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3approvalPopup.jsp
 * @메뉴명 : step3신규문서 최종 확인
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ include file ="/common/path.jsp" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.AccidentVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ImplDeptVO" %>

<%
	/* 첨부파일 */
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList");
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	/* 결재 */
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
	/* 시행부서 */
	List<ImplDeptVO> implDeptDetailList = (List<ImplDeptVO>)request.getAttribute("implDeptDetailList");
	
	String docu_cd = StringUtil.nvl((String)request.getAttribute("docu_cd"));	//문서코드
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));	//결재 문서번호	
	String includePage = "step3"+docu_cd+".jsp";	//신규문서 파일명
	Integer winWidth = 0;
	Integer winResize = 0;	
	
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
	
	/*사고보고서  보고 날짜 셋팅*/
	AccidentVO accidentVO = (AccidentVO)request.getAttribute("accidentVO");
	if(accidentVO == null){
		accidentVO = new AccidentVO();
	}
	String bogo_day = StringUtil.nvl(accidentVO.getBogo_day());		//보고일
	String bogo_month = StringUtil.nvl(accidentVO.getBogo_month()); //보고월
	String bogo_year = StringUtil.nvl(accidentVO.getBogo_year());	//보고년
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	winWidth = $(window).width();
	winResize = 1446 - winWidth;  //CHOE 20160113 화면이 1120보다 커지지 않는다. 1268 + 178
	//alert("winResize : "+winResize);
	<%if("E01015".equals(docu_cd)|| "E01016".equals(docu_cd)) {%>		
		$(document).ready(function() {
			window.resizeBy(winResize, 0);			
		});		
	<%
	}%>
	
	var subtmit=false;		//submit flag
	
	/**
	 * 결재문서 삭제 한다.
	 */
	function deleteApprpval(){
		if(confirm("삭제하시겠습니까?") == true){
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/deleteApprovalDocument.do";
			document.getElementById("frm").submit();
		}
	}
	
	/**
	 * 이전 결재 팝업으로 이동한다.
	 */
	function goStep2(){
		document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/step2approvalPopup.do";
		document.getElementById("frm").submit();
	}
	
	/**
	 * 작성완료
	 */
	function goStep4(){
		if(confirm("작성완료하시겠습니까?") == true){
			document.frm.action='<%=GROUP_ROOT%>/ea/newReport/insertDocumentComplete.do';
			document.frm.submit();
		}
	}
	
	/**
	 * 요청
	 */
	function goApproval(){
		if(confirm("요청하시겠습니까?") == true){
			document.frm.action='<%=GROUP_ROOT%>/ea/newReport/approvalComplete.do';
			document.frm.submit();
		}
	}
	</script>
</head>
<body>
<div class="wrap_pop_bg2">
	<div class="wrap_confirm">
		<div class="top">
			<h3><%=StringUtil.nvl(approvalMasterVO.getDocu_nm()) %><span> STEP3</span></h3>
			<button type="button" class="close" onclick="javascript:window.close();"><span class="blind">닫기</span></button>
			<div>
				<button type="button" class="type2" onclick="javascript:deleteApprpval(); return false;">삭제</button>
				<div class="fr">
					<button type="button" class="type2" onclick="javascript:goStep2(); return false;">이전</button>
					<button type="button" class="type2" onclick="javascript:goStep4(); return false;">작성완료</button>
					<button type="button" class="type2" onclick="javascript:goApproval(); return false;">요청</button>
				</div>
			</div>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"><em>STEP	3</em>. 최종확인</span>
					<p>문서 내용과 결재라인을 최종 확인하시고 요청버튼을 클릭하시면 상신됩니다. 작성완료버튼을 클릭하시면 내가 올린 문서에 작성 중 상태로 등록됩니다. </p>
				</div>
				<form id="frm" name="frm" method="post">
				<input type="hidden" id="docu_cd" name="docu_cd" value="<%=docu_cd %>" />
				<input type="hidden" id="docu_seq" name="docu_seq" value="<%=approvalMasterVO.getDocu_seq() %>" />
				<input type="hidden" id="approval_seq" name="approval_seq" value="<%=approval_seq %>" />
				
				<h4>
					<%if("Y".equals(approvalMasterVO.getSecurity_yn())){ %><span>대외비입니다.</span><%} %>
					<strong><%=approvalMasterVO.getDocu_nm() %></strong>
					<%if("Y".equals(approvalMasterVO.getDecide_yn())){ %><span>전결 가능합니다.</span><%} %>
				</h4>
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
								<%if("E01015".equals(docu_cd)){ %>
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
						      <%}else{ /*원부자재 납품확인서*/%>
								<th>결재</th>
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
								    <%if(approvalDetailList.size() > 0){
										for(int i=0; approvalDetailList.size()>i;i++){
											ApprovalVO approvalVO = new ApprovalVO();
											approvalVO = approvalDetailList.get(i);
											if (i < 7) {
									%>
										<th><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></th>
											<%} else { %>
											<th></th>						
											<%}
										}
										/*CHOE 20160125 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
										for(int j=7; approvalDetailList.size() < j;j--) {%>
										<th></th>		
										<%}	
									}%>
								    </tr>
								    <tr> 
								    <%if(approvalDetailList.size() > 0){
										for(int i=0; approvalDetailList.size()>i;i++){
											ApprovalVO approvalVO = new ApprovalVO();
											approvalVO = approvalDetailList.get(i);
											if (i < 7) {
									%>
										<td><%=i+1 %><br /><br /><%=StringUtil.nvl(approvalVO.getEmp_ko_nm() )%></td>
											<%} else { %>
										<td></td>						
											<%}
										}
										/*CHOE 20160125 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
										for(int j=7; approvalDetailList.size() < j;j--) {%>
										<td></td>		
										<%}	
								    }%>									
								    </tr>								   
							      </tbody>
								</table>							  
						      	</td>	
							  <%} %>	
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
										<td><%=k%><br /><br /><%=StringUtil.nvl(approvalVO.getEmp_ko_nm() )%></td>
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
								<th>시행부서</th>
								<td colspan="7">
									<%
									if(implDeptDetailList.size()!=0){
										for(int i=0; implDeptDetailList.size()>i;i++){
											ImplDeptVO implDeptVO = new ImplDeptVO();
											implDeptVO = implDeptDetailList.get(i);
									%>
									<%=StringUtil.nvl(implDeptVO.getDept_ko_nm()) %> 
									<%	} 
									}%>	
								</td>
							</tr>
							<tr>
								<th>제목</th>
								<td colspan="7"><%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<jsp:include page="<%=includePage%>"/>
				
				<table class="over" style="margin-top:3px;">
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
							<th class="append_tit">
								첨부파일
							</th>
							<td colspan="7" class="append_file br_none">
								<div class="append_search">
									<input type="hidden" name="fileNum" id="fileNum" />
								</div>
								<div id="messages" class="attachList">
								<%
									if(attachList!=null){
										for(int i=0; i<attachList.size(); i++){
											FileAttachVO fileAttachvo = new FileAttachVO();
											fileAttachvo = attachList.get(i);
										
										%>
										<p>
											<a href="<%=GROUP_ROOT%>/fileDownload.do?file_seq=<%=fileAttachvo.getFile_seq()%>&filename=<%=fileAttachvo.getFile_nm()%>&filePath=<%=fileAttachvo.getFile_path()%>&type=appr">
												<%=fileAttachvo.getOrigin_file_nm()%>
											</a>
										</p>
										
										<%
										}
									}
									
									if(attachList.size() > 1) {
									%>
										<p><button type="button" onclick="javascript:Commons.fileDownloadAll(0);">전체 다운로드</button></p>
									<%
									}
									%>
								</div>
								<div id="pro"></div>
							</td>
						</tr>						
					</tbody>
				</table>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>