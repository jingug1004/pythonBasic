<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : implementList.jsp
 * @메뉴명 : 시행문서조회
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.implement.vo.ImplementReportVO" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	//시행문서 목록 리스트
	List<ImplementReportVO> implementList = (List<ImplementReportVO>)request.getAttribute("implementList");
	int implementTotalCount = (Integer)request.getAttribute("implementTotalCount");
	//문서정보 검색 리스트
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	String approval_type = StringUtil.nvl((String)request.getAttribute("approval_type"));
	String search_start_dt = StringUtil.nvl((String)request.getAttribute("search_start_dt"));
	String search_end_dt = StringUtil.nvl((String)request.getAttribute("search_end_dt"));
	String search_docu_cd = StringUtil.nvl((String)request.getAttribute("search_docu_cd"));
	String search_condition = StringUtil.nvl((String)request.getAttribute("search_condition"));
	String search_text = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("search_text"));
	String search_read_yn = StringUtil.nvl((String)request.getAttribute("search_read_yn"));
	String search_interest_yn = StringUtil.nvl((String)request.getAttribute("search_interest_yn"));
	String menu = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("menu"));

	String checkMustOpinion = StringUtil.nvl((String)request.getAttribute("checkMustOpinion")); //시행부서 의견 필수

%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">

	$(window).load(function() {
		$("#checkMustOpinion > option[value=<%=checkMustOpinion%>]").attr("selected","true");
	});

	var subtmit = false;		//submit flag
	
	function goReload(){
		document.getElementById("search_frm").action ="<%=GROUP_ROOT%>/ea/implement/implementList.do";
		document.getElementById("search_frm").submit();
	}
	
	/**
	 * 검색
	 */
	function goSearch(){
		if(formCheck.dateChk($("#search_start_dt").val(),$("#search_end_dt").val())<0){
			alert("시작 날짜가 종료 날짜 보다 이후 입니다.");
			return;
		}
		
		if(formCheck.containsChars(document.getElementById("search_text").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		document.getElementById("search_frm").action ="<%=GROUP_ROOT%>/ea/implement/implementList.do";
		document.getElementById("search_frm").submit();
	}
	
	/**
	 * 결재문서 상세 보기 팝업
     */
	function goApprovalPopup(approval_seq){
		var height = 0;
		if(850 < $(window).innerHeight()){
			height = 850; 
		}else{
			height = $(window).innerHeight();
		}
		Commons.popupOpen('appPop','<%=GROUP_ROOT%>/ea/newReport/approvalPopup.do?menu=<%=menu%>&approval_seq='+approval_seq,'820',height);
	}
	
	/**
	 * 공유 대상 보기 팝업
	 * @param approval_seq
	 */
	function goShareTargetPopup(approval_seq){
		$('#targetData').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="550px" height="275px"',
			follow: [true, true],
			contentContainer:'.target_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/ea/share/shareTargetListPopup.do?approval_seq='+approval_seq,
		});
	}
	
	/**
	 * 시행완료 대상 보기 팝업
	 * @param approval_seq
	 */
	function goImplementCompletePopup(approval_seq){
		$('#targetData').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="550px" height="275px"',
			follow: [true, true],
			contentContainer:'.target_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/ea/newReport/implCompletePopup.do?approval_seq='+approval_seq,
		});
	}
	/**
	 * 레이어 팝업 닫기
	 */
	function layerClose(){ 
		$('#targetData').bPopup().close();
	}
	
	/**
	 * 관심문서 저장
	 */
	function goInterest_yn(){
		
		var approval_seqCheckBoxArr = document.getElementsByName("interest_yn"); //파일 체크박스
	    var approval_seqCheckBoxCnt = document.getElementsByName("interest_yn").length;
		if(approval_seqCheckBoxCnt > 0){
		    var implDeptEmp= new Array();
	    	var implDeptEmpInterest= new Array();
	    	if(approval_seqCheckBoxCnt> 0){
			    for(var i=0; i<approval_seqCheckBoxCnt; i++){
			    	if(approval_seqCheckBoxArr[i].checked){
			    		implDeptEmpInterest.push(approval_seqCheckBoxArr[i].value);
				    }else{
				    	implDeptEmp.push(approval_seqCheckBoxArr[i].value);
				    }
			    } 
			    document.getElementById("implDeptEmpInterest").value = implDeptEmpInterest;
		    	document.getElementById("implDeptEmp").value = implDeptEmp;
	    	}	
		    
			if(subtmit == false){
				if(confirm("관심문서로 저장하시겠습니까?") == true){
					document.getElementById("frm").action = "<%=GROUP_ROOT %>/ea/newReport/insertInterestDocument.do";
					document.getElementById("frm").submit();
					subtmit=true;
				}
			}	
		}else{
			alert("저장대상이 없습니다.");
		}	
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
			
			<!-- ######## start ####### -->
<div class="cont float_left">
	<div class="wrap_receive_document_collabo">
		<h2>시행문서조회 <span class="type"><%if(approval_type.equals("E03001")){ %>결재중<%}else{ %>결재완료<%} %></span></h2>
		<div class="noticeboard_box">
		<form name="search_frm" id="search_frm" method="post">
		<input type="hidden" name="approval_type" id="approval_type" value="<%=approval_type%>">
		<input type="hidden" name="menu" id="menu" value="<%=menu%>">
		<div class="serch_box">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt02">기안일</span>
					<span class="serch_date_box">
						<input id="search_start_dt" name="search_start_dt" class="search_date" type="text" value="<%=search_start_dt%>">
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="serch_date_box">
						<input id="search_end_dt" name="search_end_dt" class="search_date" type="text" value="<%=search_end_dt%>">
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
					<span class="sc_txt02">문서종류</span>
					<select id="search_docu_cd" name="search_docu_cd" class="serch_select02 w96">
						<option value="">전체</option>
						<%
							if(sCodeList.size()!=0){
								for(int i=0; sCodeList.size()>i;i++){
									CodeVO codeVO = new CodeVO();
									codeVO = sCodeList.get(i);
						%>
						<option value="<%=codeVO.getCd()%>" <%if(codeVO.getCd().equals(search_docu_cd)){%>selected<%}%>><%=codeVO.getCd_nm()%></option>
						<%
								}
							}
						%>
					</select>
					<span class="sc_txt02">열람여부</span>
					<select id="search_read_yn" name="search_read_yn" class="serch_select02 w96">
						<option value="">전체</option>
						<option value="Y" <%if("Y".equals(search_read_yn)){%>selected<%}%>>Y</option>						
						<option value="N" <%if("N".equals(search_read_yn)){%>selected<%}%>>N</option>
					</select>
				</li>
				<li class="cont02">
					<span class="sc_txt">
						<select id="search_condition" name="search_condition" class="search_select02 w116">
							<option value="SEQ" <%if(search_condition.equals("SEQ")){%>selected<%}%>>문서번호</option>
							<option value="SBJECT" <%if(search_condition.equals("SBJECT")){%>selected<%}%>>제목</option>
							<option value="NAME" <%if(search_condition.equals("NAME")){%>selected<%}%>>기안자</option>
						</select>
					</span>
					
					<%--<input id="search_text" name="search_text" class="serch_txt03 w305" type="text" value="<%=search_text%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}">--%>
					<input id="search_text" name="search_text" class="serch_txt04 w127" type="text" value="<%=search_text%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}">

					<span class="sc_txt02">관심여부</span>
					<select class="serch_select02 w96" id="search_interest_yn" name="search_interest_yn">
						<option value="">전체</option>						
						<option value="Y" <%if("Y".equals(search_interest_yn)){%>selected<%}%>>Y</option>
						<option value="N" <%if("N".equals(search_interest_yn)){%>selected<%}%>>N</option>
					</select>

					<span class="sc_txt">
						<select class="search_select02 w148" id="checkMustOpinion" name="checkMustOpinion">
							<option value="">전체</option>
							<option value="N">일반 문서</option>
							<option value="Y">시행부서 의견 필수</option>
						</select>
					</span>

				</li>
			</ul>
			<span class="serch_btn"><button type="button" onclick="javascript:goSearch(); return false;">검색</button></span>
		</div>
		</form>		
		<div class="list_btn">
			<div class="list_button">
				<button type="button" class="type_01" onclick="javascript:goInterest_yn();">관심문서저장</button>
			</div>
		</div>		
		<div class="cont_box cont_table05">
			<form id="frm" name="frm" method="post">
				<input type="hidden" id="implDeptEmpInterest" name="implDeptEmpInterest">
				<input type="hidden" id="implDeptEmp" name="implDeptEmp">
				<input type="hidden" id="approval_type" name="approval_type" value="<%=approval_type%>"/>
				<input type="hidden" id="search_start_dt" name="search_start_dt" value="<%=search_start_dt%>"/>
				<input type="hidden" id="search_end_dt" name="search_end_dt" value="<%=search_end_dt%>"/>
				<input type="hidden" id="search_docu_cd" name="search_docu_cd" value="<%=search_docu_cd%>"/>
				<input type="hidden" id="search_condition" name="search_condition" value="<%=search_condition%>"/>
				<input type="hidden" id="search_text" name="search_text" value="<%=search_text%>"/>
				<input type="hidden" id="search_read_yn" name="search_read_yn" value="<%=search_read_yn%>"/>
				<input type="hidden" id="search_interest_yn" name="search_interest_yn" value="<%=search_interest_yn%>"/>
				<input type="hidden" id="checkMustOpinion" name="checkMustOpinion" value="<%=checkMustOpinion %>" />
				<table class="implement">
				<colgroup>					
					<col width="6%">
					<col width="6%">					
					<col width="6%">
					<col width="16%">
					<col width="11%">
					<col width="">
					<col width="8%">
					<col width="12%">
					<col width="10%">
					<col width="8%">
				</colgroup>
					<thead>
						<tr>						
							<th>관심문서</th>							
							<th>열람</th>							
							<th>의견</th>							
							<th>문서번호</th>
							<th>종류</th>
							<th>제목</th>
							<th>기안자</th>
							<th>기안일</th>
							<%if("020701".equals(menu)){ %>
							<th>문서상태</th>
							<th class="br_none">처리자</th>	
							<%}else{ %>
							<th>공유</th>
							<th class="br_none">시행여부</th>	
							<%} %>
						</tr>
					</thead>
					<tbody>
					<%
						if(implementList.size()!=0){
							for(int i=0; implementList.size()>i;i++){
								ImplementReportVO implementVO = new ImplementReportVO();
								implementVO = implementList.get(i);
								String last_line_nm = "";
								if(!"".equals(StringUtil.nvl(implementVO.getLast_line_nm()))){
									last_line_nm = implementVO.getLast_line_nm().split("\\^")[1];
								}
					%>
						<tr<%if("E02005".equals(implementVO.getState())){ %> class="no"<%}else if("E02002".equals(implementVO.getState()) || "E02003".equals(implementVO.getState())){ %> class="ing"<%} %>>							
							<td><input type="checkbox" name="interest_yn" value="<%=implementVO.getApproval_seq() %>" <%if("Y".equals(StringUtil.nvl(implementVO.getInterest_yn()))){ %>checked<%} %>></td>							
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.nvl(implementVO.getRead_yn()) %></td>							
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.nvl(implementVO.getOption_read_yn()) %></td>							
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.nvl(implementVO.getApproval_seq()) %></td>
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.nvl(implementVO.getDocu_nm()) %></td>
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%if(StringUtil.nvl(implementVO.getOvertime_detail_seq()) != ""){%>[<%=implementVO.getOvertime_detail_seq() %>]<%}%><%=RequestFilterUtil.toHtmlTagReplace("", implementVO.getSubject()) %></td>
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.nvl(implementVO.getEmp_ko_nm()) %></td>
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.nvl(implementVO.getMake_dt()) %></td>
							<%if("020701".equals(menu)){ %>
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.nvl(implementVO.getState_nm()) %></td>
							<td onclick="javascript:goApprovalPopup('<%=implementVO.getApproval_seq() %>'); return false;" class="br_none"><%=last_line_nm %></td>			
							<%}else{ %>
							<td onclick="javascript:goShareTargetPopup('<%=implementVO.getApproval_seq() %>'); return false;"><%=StringUtil.makeMoneyType(implementVO.getShare_target_cnt()) %>(<%=implementVO.getShare_target_read_cnt()%>)</td>
							<td onclick="javascript:goImplementCompletePopup('<%=implementVO.getApproval_seq() %>'); return false;" class="br_none<%if("N".equals(implementVO.getImpl_dept_complete_yn())){ %> ing<%}%>"><%=implementVO.getImpl_dept_cnt() %>(<%=implementVO.getImpl_dept_read_cnt() %>)</td>			
							<%} %>
						</tr>
					<%
							}
						}else{
					%>	
						<tr>
							<td colspan="9" class="br_none">데이터가 없습니다.</td>
						</tr>
					<%
						}
					%>	
					</tbody>
				</table>
			</form>
		</div>
		<div class="paging">
			<div class="wrap_total">
				* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(implementTotalCount)%></span>건
			</div>
			<%@ include file ="/common/paging.jsp" %>
		</div>				
	</div>
	</div>
</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
	<div id='targetData' style='display:none; width:auto; height:auto; '>
		<div class='target_content'></div> 
	</div>
</body>
</html>