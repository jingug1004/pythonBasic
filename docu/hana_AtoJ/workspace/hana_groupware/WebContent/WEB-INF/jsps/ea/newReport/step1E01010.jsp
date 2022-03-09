<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01010.jsp
 * @메뉴명 : step1신규문서작성 - 증명서발급신청서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI       
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.CertificateVO" %>
<%
	//증명서발급신청서
	List<CertificateVO> certificateDetailList = (List<CertificateVO>)request.getAttribute("certificateDetailList");

	//증명서코드
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	String certi_cd = "<option value=''>증명서 선택</option>";
	if(sCodeList.size()!=0){
		for(int i=0; sCodeList.size()>i;i++){
			CodeVO codeVO = new CodeVO();
			codeVO = sCodeList.get(i);
			certi_cd += "<option value='"+codeVO.getCd()+"|"+codeVO.getCd_nm()+"'>"+codeVO.getCd_nm()+"</option>";
		}
	}
	
%>
	<script type="text/javascript">
	var cnt = 1;
	
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
	   	objCel1.innerHTML="<select name='certi_cd' id='' class='sel_certificate'><%=certi_cd%></select>";
	   	
		objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='qty' id='' class='ipt_certificate' maxlength='100'/>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<input type='text' name='yongdo' id='' class='ipt_certificate' maxlength='200'/>";
	   	
	   	objCel1 = document.createElement('TD');
	   	objCel1.setAttribute("class", "bdrn btn");
	   	objRow.appendChild(objCel1);
	   	objCel1.innerHTML="<button type='button' name=cmdDelete onClick='removeRow(this);'>삭제</button>"; 
	   	
	   	cnt++;
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
		
		var certi_cd = document.getElementsByName("certi_cd");
		var qty = document.getElementsByName("qty");
		var yongdo = document.getElementsByName("yongdo");
		
		if(certi_cd.length == 0 ){
			alert("내역을 입력해 주세요.");
			addRow();
			return;
		}
		
		for(var i=0; i<certi_cd.length; i++){

			if(formCheck.isNull(certi_cd[i], "증명서 종류를 선택해 주세요.")){
				return;
			}
			if(formCheck.isNull(qty[i], "수량을 입력해주세요.")){
				return;
			}
			if(formCheck.isNumer(qty[i].value)){
				alert("숫자만 입력해주세요");
				qty[i].value = "";
				qty[i].focus();
				return;
			}
			
			if(qty[i].value < 1){
				alert("수량은 1이상 입력해 주세요. ");
				qty[i].value = "";
				qty[i].focus();
				return;
			}
			
			if(formCheck.isNull(yongdo[i], "용도를 입력해주세요.")){
				return;
			}
		}
		
		return true;
	}
	</script>
							<div class="inner_box no_scroll">
								<strong class="tit_s tit_sample">
									내 역
									<button type="button" class="btn_row lhfx" onclick="javascript:addRow(); return false;">+ 행추가</button>
								</strong>
								<table class="tbl_certificate" id="tbl">
									<colgroup>
										<col style="width:127px">
										<col style="width:62px">
										<col style="width:449px">
										<col style="width:52x">
									</colgroup>
									<thead>
										<th>증명서 종류</th>
										<th>수량</th>
										<th>용도</th>
										<th class="bdrn">삭제</th>
									</thead>
									<tbody>
										<%
										if(certificateDetailList.size() > 0){
											for(int j=0; certificateDetailList.size()>j;j++){
												CertificateVO certificateVO = new CertificateVO();
												certificateVO = certificateDetailList.get(j);
										%>
										<tr>
											<td>
												<select name="certi_cd" id="" class="sel_certificate">
													<option value="">증명서 선택</option>
												<%
												if(sCodeList.size()!=0){
													for(int i=0; sCodeList.size()>i;i++){
														CodeVO codeVO = new CodeVO();
														codeVO = sCodeList.get(i);
												%>
													<option value="<%=codeVO.getCd()%>|<%=codeVO.getCd_nm() %>" <%if((codeVO.getCd()+"|"+codeVO.getCd_nm()).equals(StringUtil.nvl(certificateVO.getCerti_cd())+"|"+StringUtil.nvl(certificateVO.getCerti_nm()))){%>selected<%} %>><%=codeVO.getCd_nm() %></option>
												<%
													}
												}	
												%>	
												</select>
											</td>
											<td><input type="text" name="qty" id="" value="<%=StringUtil.nvl(certificateVO.getQty())%>" class="ipt_certificate" maxlength="100"/></td>
											<td><input type="text" name="yongdo" id="" value="<%=RequestFilterUtil.toHtmlTagReplace("", certificateVO.getYongdo())%>" class="ipt_certificate" maxlength="200"/></td>
											<td class="bdrn btn"><button type="button" onClick='removeRow(this);'>삭제</button></td>
										</tr>
										<%		
											}
										}else{	
										%>
										<tr>
											<td>
												<select name="certi_cd" id="" class="sel_certificate">
													<option value="">증명서 선택</option>
												<%
												if(sCodeList.size()!=0){
													for(int i=0; sCodeList.size()>i;i++){
														CodeVO codeVO = new CodeVO();
														codeVO = sCodeList.get(i);
												%>
													<option value="<%=codeVO.getCd()%>|<%=codeVO.getCd_nm() %>"><%=codeVO.getCd_nm() %></option>
												<%
													}
												}	
												%>	
												</select>
											</td>
											<td><input type="text" name="qty" id="" class="ipt_certificate" maxlength="100"/></td>
											<td><input type="text" name="yongdo" id="" class="ipt_certificate" maxlength="200"/></td>
											<td class="bdrn btn"><button type="button" onClick='removeRow(this);'>삭제</button></td>
										</tr>
										<%} %>
									</tbody>
								</table>
							</div>	