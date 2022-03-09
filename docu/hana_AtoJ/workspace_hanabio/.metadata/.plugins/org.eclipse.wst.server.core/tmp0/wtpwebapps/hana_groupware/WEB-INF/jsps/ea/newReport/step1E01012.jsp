<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01012.jsp
 * @메뉴명 : step1신규문서작성 - 시간외근무내역서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI       
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.OvertimeVO" %>
<%@ include file ="/common/path.jsp" %>
<%	//시간외근무내역서 
	List<OvertimeVO> overtimeDetailList = (List<OvertimeVO>)request.getAttribute("overtimeDetailList");
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));
	String approval_seq_old = StringUtil.nvl((String)request.getAttribute("approval_seq_old"));
	
	//근무구분코드
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	String bigo = "<option value=''>근무구분 선택</option>";
	if(sCodeList.size()!=0){
		for(int i=0; sCodeList.size()>i;i++){
			CodeVO codeVO = new CodeVO();
			codeVO = sCodeList.get(i);
			bigo += "<option value='"+codeVO.getCd()+"'>"+codeVO.getCd_nm()+"</option>";
		}
	}
%>	
	<script type="text/javascript">
	/**
	 * 행카운트
	 */
	var cnt = 1+<%=overtimeDetailList.size()%>;

	/**
	 * 행카운트
	 */
	function checkCount() {
	 	var objTable = document.getElementById("tbl");
	 	var lastRow = objTable.rows.length-1;
	 	var rtnCnt = 0;
	
	 	for(var i = lastRow; i > 0; i--){
	   		if(document.getElementsByTagName("TR")[i]) rtnCnt++;
	 	}
	 	return rtnCnt;
	}
	
	/**
	 * 행추가
	 */
	function addRow() {
		var count = checkCount();
		if(count > 29){
			alert("등록 가능한 최대 개수는 30개 입니다.");
			return;
		}
		
	    var objRow = document.getElementById('tbl').insertRow(1); // row 생성
	    
	    var objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);	   	
	   	objCel1.innerHTML="<textarea name='dept_nm' id='dept_nm"+cnt+"' class='ta_overtime' onclick='javascript:memberSearchPopup(\""+cnt+"\");' readonly></textarea><input type='hidden' name='dept_cd' id='dept_cd"+cnt+"'>";
	   	
		objCel1 = document.createElement('TD');
		objCel1.setAttribute("class", "name");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='emp_nm' id='emp_nm"+cnt+"' class='ipt_overtime' onclick='javascript:memberSearchPopup(\""+cnt+"\");' readonly/><input type='hidden' name='emp_no' id='emp_no"+cnt+"' value=''><button type='button' onclick='javascript:memberSearchPopup(\""+cnt+"\");'>조회</button>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<select name='bigo' id='bigo' class='sel_certificate'><%=bigo%></select>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<textarea name='biz_content' class='ta_overtime'></textarea>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "date");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<span class='date_box'><input type='text' class='serch_date' name='work_due_ymd' readonly/><button type='button' class='btn_date'><span class='blind'>날짜선택</span></button></span>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "time");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='work_start_hh' id='work_start_hh"+cnt+"' class='ipt_time' onchange='workRealTime("+cnt+");'/> : <input type='text' name='work_start_mi' id='work_start_mi"+cnt+"' class='ipt_time' onchange='workRealTime("+cnt+");'/>";
	  
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "time");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='work_end_hh' id='work_end_hh"+cnt+"' class='ipt_time' onchange='workRealTime("+cnt+");'/> : <input type='text' name='work_end_mi' id='work_end_mi"+cnt+"' class='ipt_time' onchange='workRealTime("+cnt+");'/>";
	  
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "time");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='work_real_hh' id='work_real_hh"+cnt+"' class='ipt_time' onchange='workRealTime("+cnt+");' readonly /> : <input type='text' name='work_real_mi' id='work_real_mi"+cnt+"' class='ipt_time' onchange='workRealTime("+cnt+");' readonly />";
	   	
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "bdrn btn");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<button type='button' name=cmdDelete onClick='removeRow(this);'>삭제</button>"; 
	   	
	   	/*달력활성화*/
	   	$(".btn_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
	   	datepicker();
	   	/*달력활성화*/
	   	cnt++;
	}

	/**
	 * STEP1저장
	 */
	function checkCount() {
	 	var objTable = document.getElementById("tbl");
	 	var lastRow = objTable.rows.length-1;
	 	var rtnCnt = 0;
	
	 	for(var i = lastRow; i > 0; i--){
	   		if(document.getElementsByTagName("TR")[i]) rtnCnt++;
	 	}
	 	return rtnCnt;
	}
	
	/**
	 * 행삭제
	 */
	function removeRow(r){ 
	 	var i=r.parentNode.parentNode.rowIndex;
	 	document.getElementById('tbl').deleteRow(i);
	}
	
	/**
	 * STEP1저장
	 */
	function saveStep1(){
		if(formCheck.isNull(document.getElementById("subject"), "제목을 입력해주세요.")){
			return;
		}
		
		var dept_nm = document.getElementsByName("dept_nm");
		var emp_nm = document.getElementsByName("emp_nm");
		var biz_content = document.getElementsByName("biz_content");
		var work_due_ymd = document.getElementsByName("work_due_ymd");
		var work_start_hh = document.getElementsByName("work_start_hh");
		var work_start_mi = document.getElementsByName("work_start_mi");
		var work_end_hh = document.getElementsByName("work_end_hh");
		var work_end_mi = document.getElementsByName("work_end_mi");
		var work_real_hh = document.getElementsByName("work_real_hh");
		var work_real_mi = document.getElementsByName("work_real_mi");
		var bigo = document.getElementsByName("bigo");
		
		if(work_due_ymd.length == 0 ){
			alert("내역을 입력해 주세요.");
			addRow();
			return;
		}
		for(var i=0; i<work_due_ymd.length; i++){
			if(formCheck.isNull(dept_nm[i], "부서명을 입력해 주세요.")){
				return;
			}
			
			if(formCheck.isNull(emp_nm[i], "성명을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNull(bigo[i], "근무구분을 선택해 주세요.")){
				return;
			}
			
			if(formCheck.isNull(biz_content[i], "업무내용을 입력해 주세요.")){
				return;
			}
			
			if(formCheck.getByteLength(biz_content[i].value) > 400){
				alert("200자 이하로 입력해 주세요.");
				biz_content[i].focus();
				return;
			}
			
			if(formCheck.isNull(work_due_ymd[i], "근무예정일을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNull(work_start_hh[i], "근무시작시간을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNumer(work_start_hh[i].value)){
				alert("숫자만 입력해주세요");
				work_start_hh[i].value = "";
				work_start_hh[i].focus();
				return;
			}
			
			if(formCheck.isNull(work_start_mi[i], "근무시작시간을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNumer(work_start_mi[i].value)){
				alert("숫자만 입력해주세요");
				work_start_mi[i].value = "";
				work_start_mi[i].focus();
				return;
			}
			
			if(formCheck.isNull(work_end_hh[i], "근무종료시간을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNumer(work_end_hh[i].value)){
				alert("숫자만 입력해주세요");
				work_end_hh[i].value = "";
				work_end_hh[i].focus();
				return;
			}
			
			if(formCheck.isNull(work_end_mi[i], "근무종료시간을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNumer(work_end_mi[i].value)){
				alert("숫자만 입력해주세요");
				work_end_mi[i].value = "";
				work_end_mi[i].focus();
				return;
			}
			
			//workRealTime(i);
			if(formCheck.isNull(work_real_hh[i], "실근무시간을 입력해주세요.")){
				return;
			}
			
			if(formCheck.isNull(work_real_mi[i], "실근무시간을 입력해주세요.")){
				return;
			}
		}
		
		return true;
	}
	
	/**
	 * 회원정보 선택팝업
	 */
	function memberSearchPopup(target){
		$('#pop_memberSearch').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="330px" height="340px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/pe/member/memberListPopup.do?target='+target,
		});
	}
	
	/**
	 * 회원정보 set
	 */
	function setEmp_nm(target, value){
		var valueArr = value.split("|");
		document.getElementById("dept_cd"+target).value = valueArr[0];
		document.getElementById("dept_nm"+target).value = valueArr[1];
		document.getElementById("emp_no"+target).value = valueArr[2];
		document.getElementById("emp_nm"+target).value = valueArr[3];
	}
	
	/**
	 *  실근무시간 자동생성
	 */
	function workRealTime(target){
		
		var work_start_hh = document.getElementById("work_start_hh"+target);
		var work_start_mi = document.getElementById("work_start_mi"+target);
		var work_end_hh = document.getElementById("work_end_hh"+target);
		var work_end_mi = document.getElementById("work_end_mi"+target);
		
		if(!formCheck.isNullStr(work_start_hh.value)){
			document.getElementById("work_start_hh"+target).value = formCheck.leadingZeros(work_start_hh.value, 2);
		}
		if(!formCheck.isNullStr(work_start_mi.value)){
			document.getElementById("work_start_mi"+target).value = formCheck.leadingZeros(work_start_mi.value, 2);
		}
		if(!formCheck.isNullStr(work_end_hh.value)){
			document.getElementById("work_end_hh"+target).value = formCheck.leadingZeros(work_end_hh.value, 2);
		}	
		if(!formCheck.isNullStr(work_end_mi.value)){
			document.getElementById("work_end_mi"+target).value = formCheck.leadingZeros(work_end_mi.value, 2);
		}	
		
		if(formCheck.isNumer(work_start_hh.value)){
			alert("숫자만 입력해주세요");
			work_start_hh.focus();
			work_start_hh.value = "";
			return;
		}
		
		if(work_start_hh.value > 24){
			alert("시간은 24시 이후가 될수 없습니다.");
			work_start_hh.focus();
			work_start_hh.value = "";
			return;
		}
		
		if (formCheck.getByteLength(work_start_hh.value) > 2){
			alert("시간 입력 범위 오류입니다.");
			work_start_hh.focus();
			work_start_hh.value = "";
			return;
		}
		
		if(formCheck.isNumer(work_start_mi.value)){
			alert("숫자만 입력해주세요");
			work_start_mi.value = "";
			work_start_mi.focus();
			return;
		}
		
		if(work_start_mi.value > 60){
			alert("분은 60분 이후가 될수 없습니다.");
			work_start_mi.focus();
			work_start_mi.value = "";
			return;
		}
		
		if (formCheck.getByteLength(work_start_mi.value) > 2){
			alert("시간 입력 범위 오류입니다.");
			work_start_mi.focus();
			work_start_mi.value = "";
			return;
		}
		
		if(formCheck.isNumer(work_end_hh.value)){
			alert("숫자만 입력해주세요");
			work_end_hh.value = "";
			work_end_hh.focus();
			return;
		}
		
		if(work_end_hh.value > 24){
			alert("시간은 24시 이후가 될수 없습니다.");
			work_end_hh.value = "";
			work_end_hh.focus();
			return;
		}
		
		if (formCheck.getByteLength(work_end_hh.value) > 2){
			alert("시간 입력 범위 오류입니다.");
			work_end_hh.focus();
			work_end_hh.value = "";
			return;
		}
		
		if(formCheck.isNumer(work_end_mi.value)){
			alert("숫자만 입력해주세요");
			work_end_mi.value = "";
			work_end_mi.focus();
			return;
		}
		
		if(work_end_mi.value > 60){
			alert("분은 60분 이후가 될수 없습니다.");
			work_end_mi.value = "";
			work_end_mi.focus();
			return;
		}
		
		if (formCheck.getByteLength(work_end_mi.value) > 2){
			alert("시간 입력 범위 오류입니다.");
			work_end_mi.focus();
			work_end_mi.value = "";
			return;
		}
		
		if(work_end_hh.value != null && work_end_hh.value !="" && work_end_mi.value != null && work_end_mi.value !=""){
			
			var work_start_hh_value = Number(work_start_hh.value);
			var work_start_mi_value = Number(work_start_mi.value);
			var work_end_hh_value = Number(work_end_hh.value);
			var work_end_mi_value = Number(work_end_mi.value);
	
			var work_start_value = (work_start_hh_value*60)+work_start_mi_value;
			var work_end_value = (work_end_hh_value*60)+work_end_mi_value;
		
			if(work_start_value >= work_end_value){
				alert("근무종료시간은 근무시작시간과 같거나 빠를 수 없습니다. ");
				document.getElementById("work_start_hh"+target).value = "";
				document.getElementById("work_start_mi"+target).value = "";
				document.getElementById("work_end_hh"+target).value = "";
				document.getElementById("work_end_mi"+target).value = "";
				document.getElementById("work_real_hh"+target).value = "";
				document.getElementById("work_real_mi"+target).value = "";
				document.getElementById("work_start_hh"+target).focus();
				return;
			}
			
			var work_real_mi_value = work_end_value - work_start_value;
			var hours = parseInt(work_real_mi_value / 60 );
			var minutes = parseInt(work_real_mi_value % 60 );
	
			
			if( 12 >= work_start_hh_value && 13 <= work_end_hh_value){//12-13시를 포함할 경우 점심시간 1시간 마이너스 한다.
				document.getElementById("work_real_hh"+target).value = formCheck.leadingZeros(hours-1, 2);
				document.getElementById("work_real_mi"+target).value = formCheck.leadingZeros(minutes, 2);
			}else{
				document.getElementById("work_real_hh"+target).value = formCheck.leadingZeros(hours, 2);
				document.getElementById("work_real_mi"+target).value = formCheck.leadingZeros(minutes, 2);
			}
		}
	}	
		
	</script>
							<input type="hidden" name="approval_seq_old" id="approval_seq_old" value="<%=approval_seq_old %>" >
							<div class="inner_box no_scroll">
								<strong class="tit_s tit_sample">
									내 역
									<button type="button" class="btn_row lhfx" onclick="javascript:addRow(); return false;">+ 행추가</button>
								</strong>
								<table class="tbl_overtime" id="tbl">
									<colgroup>
										<col style="width:61px">
										<col style="width:48px">
										<col style="width:105px">
										<col style="">
										<col style="width:98px">
										<col style="width:70px">
										<col style="width:70px">
										<col style="width:70px">
										<col style="width:46px">
									</colgroup>
									<thead>
										<th>부서명</th>
										<th>성명</th>
										<th>근무구분</th>
										<th>업무내용</th>
										<th>근무일</th>
										<th>근무시작시간</th>
										<th>근무종료시간</th>
										<th>실근무시간</th>
										<th class="bdrn">삭제</th>
									</thead>
									<tbody>
										<%
										if(overtimeDetailList.size() > 0){
											for(int i=0; overtimeDetailList.size()>i;i++){
												OvertimeVO overtimeVO = new OvertimeVO();
												overtimeVO = overtimeDetailList.get(i);
										%>
										<tr>
											<td><textarea name="dept_nm" id="dept_nm<%=i%>" class="ta_overtime" readonly><%=overtimeVO.getDept_nm() %></textarea><input type="hidden" name="dept_cd" id="dept_cd<%=i%>" value="<%=overtimeVO.getDept_cd() %>" ></td>
											<td class="name"><input type="text" name="emp_nm" id="emp_nm<%=i%>" value="<%=overtimeVO.getEmp_nm() %>" class="ipt_overtime" readonly/><input type="hidden" name="emp_no" id="emp_no<%=i%>" value="<%=overtimeVO.getEmp_no() %>" ></td>
											<td>
											<select name="bigo" id="bigo" class="sel_certificate">
													<option value="">근무구분 선택</option>
												<%
												if(sCodeList.size()!=0){
													for(int j=0; sCodeList.size()>j;j++){
														CodeVO codeVO = new CodeVO();
														codeVO = sCodeList.get(j);
												%>
													<option value="<%=codeVO.getCd()%>" <%if(codeVO.getCd().equals(StringUtil.nvl(overtimeVO.getBigo()))){%>selected<%} %>><%=codeVO.getCd_nm() %></option>
												<%
													}
												}	
												%>	
											</select>
											</td>
											<td><textarea name="biz_content" class="ta_overtime"><%=RequestFilterUtil.toHtmlTagReplace("", overtimeVO.getBiz_content()) %></textarea></td>
											<td class="date">
												<span class="date_box">
													<input type="text" class="serch_date" name="work_due_ymd" value="<%=overtimeVO.getWork_due_ymd() %>" readonly/>
													<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
												</span>
											</td>
											<td class="time"><input type="text" name="work_start_hh" id="work_start_hh<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_start_hh())) %>" class="ipt_time" onchange="workRealTime('<%=i%>');"/> : <input type="text" name="work_start_mi" id="work_start_mi<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_start_mi())) %>" class="ipt_time" onchange="workRealTime('<%=i%>');"/></td>
											<td class="time"><input type="text" name="work_end_hh" id="work_end_hh<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_end_hh())) %>" class="ipt_time" onchange="workRealTime('<%=i%>');"/> : <input type="text" name="work_end_mi" id="work_end_mi<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_end_mi())) %>" class="ipt_time" onchange="workRealTime('<%=i%>');"/></td>
											<%if(!"".equals(approval_seq)){ %>
											<td class="time"><input type="text" name="work_real_hh" id="work_real_hh<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_real_hh())) %>"  class="ipt_time" readonly /> : <input type="text" name="work_real_mi" id="work_real_mi<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_real_mi())) %>"  class="ipt_time" readonly/></td>
											<%} else{ %>
											<td class="time"><input type="text" name="work_real_hh" id="work_real_hh<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_due_hh())) %>"  class="ipt_time" readonly /> : <input type="text" name="work_real_mi" id="work_real_mi<%=i%>" value="<%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_due_mi())) %>"  class="ipt_time" readonly/></td>
											<%} %>
											<td class="bdrn btn"><button type="button" onClick='removeRow(this);'>삭제</button></td>
										</tr>
										<%		
											}
										}	
										%>
									</tbody>
								</table>
							</div>	
							