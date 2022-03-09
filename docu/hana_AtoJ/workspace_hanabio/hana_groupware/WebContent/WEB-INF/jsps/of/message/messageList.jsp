<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : messageList.jsp
 * @메뉴명 : 쪽지함
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%@ page import="com.hanaph.gw.of.message.vo.MessageVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.WebUtil" %>

<%
	List<MessageVO> messageList = (List<MessageVO>)request.getAttribute("messageList"); //쪽지 리스트
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList"); //첨부파일 리스트
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList"); //삭제,DB삭제 권한을 가진 임직원
	String emp_no = StringUtil.nvl((String)request.getAttribute("emp_no"));
	
	String seq = StringUtil.nvl((String)request.getAttribute("seq")); //메세지 시퀀스
	String search_read_yn = (String)request.getAttribute("search_read_yn"); //검색키워드
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색키워드
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"contents"); //검색타입
	String start_ymd = StringUtil.nvl((String)request.getAttribute("start_ymd")); //조회기간
	String end_ymd = StringUtil.nvl((String)request.getAttribute("end_ymd")); //조회기간
	String type = StringUtil.nvl((String)request.getAttribute("type")); //받은,보낸쪽지함 구분
	String str = ""; //받은,보낸 문구
	
	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));
	
	String searchDate[] = WebUtil.dateCal(0);
	
	if("1".equals(type)){
		str = "받은";
	}else{
		str = "보낸";
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
		var fileArr = [];
	
		$(window).load(function() {
			<%if(!"".equals(seq)){%>
				messageDetail('<%=seq%>');
			<%}%>
			$("#search_read_yn > option[value=<%=search_read_yn%>]").attr("selected","true");
			$("#searchType > option[value=<%=searchType%>]").attr("selected","true");
		});
		
		/**
		 * 쪽지함 검색
		 */
		function goSearch(){
			if(formCheck.containsChars(document.getElementById("searchKeyword").value, "%")){
				 alert("특수문자를 사용할수 없습니다");
				 return;
			}
			
			if(formCheck.dateChk($("#start_ymd").val(),$("#end_ymd").val())<0){
				alert("쪽지기간 시작 날짜가 쪽지기간 종료 날짜 보다 이후 입니다.");
				start_ymd.focus();
				return
			}
			
			document.frm.type.value = "<%=type%>";
			document.frm.action = "<%=GROUP_ROOT%>/of/message/messageList.do";
			document.frm.submit();
		}
		
		/**
		 * 쪽지함 전체선택
		 */
		function allCheck2(){
			$("input[name=allCheck]:checkbox").each(function() {
	    		if($(this).is(':checked')) {
	                $("input[name=check]").prop("checked", true);
	            } else {
	                $("input[name=check]").prop("checked", false);
	            }
			});
		}
		
		/**
		 * 쪽지함 삭제(플래그처리)
		 */
		function deleteMessage(){
			var message_seq = "";
			$("input[name='check']:checkbox:checked").each(function(){
				message_seq += $(this).val()+"|";
			});
			
			if($("input[name='check']:checkbox:checked").length < 1){
				alert("삭제할 대상을 선택해 주세요.");
				return;
			}
			if(confirm("삭제 하시겠습니까?") == true){
				document.frm.message_seq.value = message_seq;
				document.frm.action = "<%=GROUP_ROOT%>/of/message/deleteMessage.do";
				document.frm.submit();
			}
		}
		/**
		 * 쪽지함 DB삭제
		 */
		function deleteMessageDB(){
			var message_seq = "";
			$("input[name='check']:checkbox:checked").each(function(){
				message_seq += $(this).val()+"|";
			});
			
			if($("input[name='check']:checkbox:checked").length < 1){
				alert("삭제할 대상을 선택해 주세요.");
				return;
			}
			if(confirm("DB에서 데이터를 삭제합니다.\n그래도 삭제 하시겠습니까?") == true){	
				document.frm.message_seq.value = message_seq;
				document.frm.action = "<%=GROUP_ROOT%>/of/message/deleteMessageDB.do";
				document.frm.submit();
			}
		}
		
		/**
		 * 쪽지함 상세정보
		 * @param message_seq
		 */
		function messageDetail(message_seq){
			$("#contents"+message_seq).attr("class","bt_none list_txt");
			var receiveNm = "";
			var file_nm = "";
			var file_list= '<th class="bb_none">첨부파일</th><td class="append_file01 br_none"><div class="append_list"><span id="fileNm" class="attachList"></span></div></td>';
			var file_list_print= '<th class="bb_none">첨부파일</th><td class="append_file01 br_none"><div class="append_list"><span id="fileNmPrint"></span></div></td>';
			$.ajax({
				type: "POST",
				url: "<%=GROUP_ROOT%>/of/message/messageDetail.do",
				async : false,
				data:{message_seq:message_seq},
				success: function(data){
					for(var i=0; i<data.list.length;i++){
						//receiveNm += data.list[i].emp_ko_nm+"("+data.list[i].dept_ko_nm +" / "+ data.list[i].grad_ko_nm+")";
						receiveNm += data.list[i].emp_ko_nm;
						if(data.list.length-1 != i ){
							receiveNm += ", ";
						}						
					}
					for(var j=0; j<data.attachList.length; j++){
						file_nm += "<p><img src='<%=GROUP_WEB_ROOT%>/img/img_file.png' />&nbsp;<a href='<%=GROUP_ROOT%>/fileDownload.do?file_seq="+data.attachList[j].file_seq+"&filename="+data.attachList[j].file_nm+"&filePath="+data.attachList[j].file_path+"'>"+data.attachList[j].origin_file_nm+"</a><p>";
					}
					
					if (data.attachList.length > 1) {
						file_nm += "<p><button type='button' onclick=\"javascript:Commons.fileDownloadAll(0);\">전체 다운로드</button><p>";
					}
					
					if(data.attachList.length == 0){
						file_list = "";
						file_list_print = "";
					}
					
					$("#fileList").html(file_list);
					$("#receiveNm").text(receiveNm);
					$("#sendNm").text(data.emp_ko_nm);
					$("#contents").html(data.contents);
					$("#fileNm").html(file_nm);
					/* 인쇄 영역 */
					$("#sendNmPrint").text(data.emp_ko_nm);
					$("#fileListPrint").html(file_list_print);
					$("#fileNmPrint").html(file_nm);
					$("#createDtPrint").text(data.create_dt);
					$("#contentsPrint").html(data.contents);
				}
			});
		}
		/**
		 * 쪽지 인쇄 팝업
		 */
		function printPopup(){
			if($("#contents").text() == ""){
				alert("쪽지 제목을 선택해 주세요.");
				return;
			}
			Commons.previewOpen('message', '803', '768');
		}
		
		/**
		 * 쪽지함 읽은 임직원 레이어 팝업
		 * @param message_seq
		 */
		function messageReadData(message_seq){
			$('#messageReadData').bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="no" frameborder="0" width="550px" height="295px"',
				follow: [true, true],
				contentContainer:'.message_content',
				modalClose: true,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT %>/of/common/commonReadData.do?seq=' + message_seq + '&type=MESSAGE',
			});
		}
		
		/**
		 * 레이어팝업 닫기
		 */
		function layerClose(){ 
			$('#messageReadData').bPopup().close();
			$('#getMember').bPopup().close();
		}
		
		/**
		 * 메세지 전달시 대상가져오는 팝업 - type=MESSAGE_DELIVER
		 */
		function getMember(message_seq){
			document.frm.message_seq.value = message_seq;
			$('#getMember').bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="yes" frameborder="0" width="617px" height="'+$(window).innerHeight()+'px"',
				follow: [true, true],
				contentContainer:'.member_content',
				modalClose: true,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT %>/pe/member/memberSelectPopup.do?type=MESSAGE_DELIVER&seq='+message_seq,
			});
		}
		
		/* 반드시 addMemberRow 로 function 만들어 테이블 상황에 맞게 테이블 생성한다. */
		/**
		 * @param obj
		 */
		function addMemberRow(obj){
			var target_empNo = "";
			 for(var i=0; i<obj.length; i++){
		    	if(obj[i].checked){
			    	var objParam = obj[i].value;
					var memberListValueArr = objParam.split("|");
					target_empNo += memberListValueArr[0]+"|"; 
			    }
		    }
			 
			if(confirm("전달 하시겠습니까?") == true){
				document.frm.target_empNo.value = target_empNo;
				document.frm.type.value = 3;
				document.frm.action="<%=GROUP_ROOT%>/of/message/insertMessage.do";
				document.frm.submit();
			}
			window.parent.layerClose();
		}
			 
		
		/*
		 *기존 row 삭제 
		 */
		function memberRemove(){
		 	$('#tbl tr input').remove();
		}
		
		function replyMessage(seq){
			document.frm.message_seq.value = seq;
			document.frm.action="<%=GROUP_ROOT%>/of/message/messageReplyWrite.do";
			document.frm.submit();
		}
		
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<div class="cont float_left">
				<h2><%=str %> 쪽지함</h2>
				<div class="noticeboard_box">
				<form id="frm" name="frm" method="post">
				<input type="hidden" id="message_seq" name="message_seq" />
				<input type="hidden" id="type" name="type" value="<%=type %>" />
				<input type="hidden" id="target_empNo" name="target_empNo" />
				<div class="serch_box">
					<ul class="serch_con01">
						<li>
							<span class="sc_txt">조회기간</span>
							<span class="serch_date_box">
								<input type="text" id="start_ymd" name="start_ymd" class="serch_date" value="<%=start_ymd %>" readonly="readonly"/>
								<button class="btn_date" type="button"><span class="blind">날짜선택</span></button>
							</span> ~ 
							<span class="serch_date_box">
								<input type="text" id="end_ymd" name="end_ymd" class="serch_date" value="<%=end_ymd %>" readonly="readonly"/>
								<button class="btn_date" type="button"><span class="blind">날짜선택</span></button>
							</span>
							<%if("1".equals(type)){ %>
							<span class="sc_txt">열람여부</span>
								<select class="serch_select01" id="search_read_yn" name="search_read_yn">
									<option value="all">전체</option>
									<option value="Y">읽음</option>
									<option value="N">안읽음</option>
								</select>
							<%}%>
						</li>
						<li class="cont02">
							<span class="sc_txt">쪽지함 검색</span>
							<select class="serch_select02" id="searchType" name="searchType">
								<option value="subject">제목</option>
								<option value="createNo">작성자</option>
							</select>
							<input type="text" class="serch_txt" id="searchKeyword" value="<%=searchKeyword%>" name="searchKeyword" maxlength="32" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" >
						</li>
					</ul>
					<span class="serch_btn" onclick="javascript:goSearch();"><button type="button">검색</button></span>
				</div>	
				<div class="list_btn">
					<div class="list_button">
						<button class="type_01" type="button" onclick="javascript:deleteMessage();">삭제</button>
						<%
						if(authorityMemberList != null){
							for(int i=0; authorityMemberList.size()>i;i++){
								MemberVO memberVO = new MemberVO();
								memberVO = authorityMemberList.get(i);
								if(emp_no.equals(memberVO.getEmp_no())){
						%>
						<button class="type_01" type="button" onclick="javascript:deleteMessageDB();">DB삭제</button>
						<%
								}
							}
						} 
						%>
						<button class="type_01" type="button" onclick="javascripr:printPopup();">미리보기</button>
					</div>
					<span class="list_t">* 총 건수:<span class="none_check"><%=StringUtil.makeMoneyType(messageList.size()) %></span>건</span>
				</div>
				<div class="cont_box cont_table05">
				<table class="over">
					<colgroup>
						<col style="width:31px;" />
						<col style="width:72px;" />
						<col style="width:338px;" />
						<col style="width:154px;" />
						<%if("1".equals(type)){ %>
						<col style="width:61px;" />
						<col style="width:61px;" />
						<%}else{ %>
						<col style="width:81px;" />
						<%} %>
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" id="allCheck" name="allCheck" onclick="javascript:allCheck2();" /></th>
							<th>보낸이</th>							
							<th style="border-bottom: none;">제목</th>
							<th>보낸일시</th>
							<%if("1".equals(type)){ %>
							<th>전달</th>
							<th>답장</th>
							<%}else{ %>
							<th>읽은 수</th>
							<%} %>
							<th class="br_none">첨부</th>										
						</tr>
					</thead>	
					<tbody>	
						<tr class="in_table">
							<td colspan="7" class="br_none">
								<div class="cont_table05 ofy_scroll">
									<table>
									<colgroup>
										<col style="width:31px;" />
										<col style="width:72px;" />
										<col style="width:338px;" />
										<col style="width:154px;" />
										<%if("1".equals(type)){ %>
										<col style="width:61px;" />
										<col style="width:61px;" />
										<%}else{ %>
										<col style="width:81px;" />
										<%} %>
									</colgroup>
										<tbody>	
									<%
									if(messageList.size()!=0){
										String contents = "";
										for(int i=0; messageList.size()>i; i++){
											MessageVO messageVO = new MessageVO();
											messageVO=messageList.get(i);
											contents = messageVO.getContents();
									%>
											<tr>
												<td <%if(0 == i){%> class="bt_none"<%}%>><input type="checkbox" id="check" name="check" value="<%=messageVO.getMessage_seq() %>" /></td>
												<td onclick="javascript:Commons.memberPopup('<%=messageVO.getCreate_no() %>');" <%if(0 == i){%> class="bt_none"<%}%>><%=messageVO.getEmp_ko_nm() %></td>
												<td class="txtleft">
													<div style="word-wrap:break-word; white-space:normal; display:inline-block; width:317px;" id="contents<%=messageVO.getMessage_seq()%>" onclick="javascript:messageDetail('<%=messageVO.getMessage_seq()%>');" <%if("N".equals(messageVO.getRead_yn()) && "1".equals(type)){%>class="<%if(0==i){%>bt_none <%}%> list_txt none_check"
												<%}else{%>class="<%if(0==i){%>bt_none <%}%>list_txt"<%}%>><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", messageVO.getSubject())) %></div>
												</td>
												<td <%if(0 == i){%> class="bt_none"<%}%>>
													<span><%=messageVO.getCreate_dt() %></span>
												</td>
												<%if("1".equals(type)){ %>
												<td <%if(0 == i){%> class="bt_none"<%}%>><button type="button" class="type_01" onclick="javascript:getMember('<%=messageVO.getMessage_seq()%>');">전달</button></td>
												<td <%if(0 == i){%> class="bt_none"<%}%>><button type="button" class="type_01" onclick="javascript:replyMessage('<%=messageVO.getMessage_seq()%>');">답장</button></td>
												<%}else{ %>
												<td onclick="javascript:messageReadData('<%=messageVO.getMessage_seq() %>')" <%if(0 == i){%> class="bt_none"<%}%>><%=messageVO.getRead_cnt() %></td>
												<%} %>
												<td <%if(0 == i){%> class="bt_none br_none"<%}%> class="br_none">
												<%if(messageVO.getAttach_cnt() > 0){%>
													<img src="<%=GROUP_WEB_ROOT%>/img/img_file.png" alt="파일첨부" />
												<%}%>
												</td>
											</tr>
									<%
										}
									}else{
									%>
											<tr><td class="br_none" colspan="6">쪽지가 없습니다.</td></tr>
									<%} %>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
						</tbody>
					</table>
					<table style="table-layout: fixed;">
						<colgroup>
							<col width="82px;"/>
							<col width="492px;"/>
						</colgroup>
						<tbody>
							<tr class="ts_reset">
								<th>보낸이</th>
								<td id="sendNm" class="br_none"></td>
							</tr>
							<tr class="ts_reset">
								<th>받는이</th>
								<td class="max_height br_none"><div id="receiveNm"></div></td>
							</tr>
							<tr class="txt_box_re">
								<td colspan="2" class="br_none">
									<div id="contents"></div>
								</td>
							</tr>
							<tr class="ts_reset" id="fileList">
							</tr>
						</tbody>
					</table>
				</div>	
				<!-- 인쇄 영역 -->
				<div class="cont_box cont_table06" id="message" style="display: none;">
					<table>
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
							<tbody>
								<tr>
									<th colspan="4">받 은 쪽 지</th>
								</tr>
								<tr>
									<th>보 낸 이  : </th>
									<td colspan="3" id="sendNmPrint" class="br_none"></td>
								</tr>
								<tr>
									<th>보낸 시간 : </th>
									<td id="createDtPrint"></td>
									<th>출력 일자 : </th>
									<td class="br_none"><%=searchDate[0] %></td>							
								</tr>
								<tr class="txt_box_re">
									<td colspan="4" class="br_none">
										<div id="contentsPrint" style="word-wrap:break-word; white-space:normal; width:745px;"></div>
									</td>
								</tr>
								<tr class="ts_reset" id="fileListPrint"></tr>	
							</tbody>
					</table>
				</div>
				<!-- 인쇄 영역 끝-->
				</form>
				</div>
			</div>
		</div>
	</div>
	<div id='messageReadData' style='display:none; width:auto; height:auto; '>
		<div class='message_content'></div> 
	</div>
	<div id='getMember' style='display:none; width:auto; height:auto; '>
		<div class='member_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>