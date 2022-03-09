<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewYearendtaxDetail.jsp
 * @메뉴명 : 연말정산등록 > 우편번호 팝업
 * @최초작성일 : 2015/02/16
 * @author : 정진묵               
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="java.util.List" %>
<%@page import="java.util.ArrayList"%>
<%@ include file ="/common/path.jsp" %>
<%
	@SuppressWarnings("unchecked")
	List<YearendtaxAddressVO> yearendtaxAddressList = (List<YearendtaxAddressVO>)request.getAttribute("yearendtaxAddressList");
	
	String emp_no = StringUtil.nvl((String)request.getAttribute("emp_no")); //로그인한 임직원 번호
	String type = StringUtil.nvl((String)request.getAttribute("type"));
	
	if(yearendtaxAddressList == null){
		yearendtaxAddressList = new ArrayList<YearendtaxAddressVO>();
	}
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
		var seq = 1;
	
		/* 행 추가 */
		function addRow(){
			
			var seq = $("#addressList tr").size() + 1;
			var html = '';
			html += '<tr id="addressList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td><input type="text" name="emp_no" id="emp_no_'+seq+'" value="<%=emp_no%>" class="ipt_num ipt_disable" readonly /></td>';
			html += '	<td><input type="text" name="tel_no" id="tel_no_'+seq+'" class="ipt_tel" /></td>';
			html += '	<td class="search">';
			html += '		<span class="find_box">';
			html += '			<input type="text" name="zip_cd" id="zip_cd_'+seq+'" class="serch_date" onclick="javascript:zipcodeLayerPop('+seq+');" readonly="readonly" />';
			html += '			<button type="button" onclick="javascript:zipcodeLayerPop('+seq+');" class="btn_find"><span class="blind">찾아보기</span></button>';
			html += '		</span>';
			html += '	</td>';
			html += '	<td><input type="text" name="address1" id="address1_'+seq+'" class="ipt_adrs" /></td>';
			html += '	<td><input type="text" name="address2" id="address2_'+seq+'" class="ipt_adrs2" /></td>';
			html += '	<td class="del_btn_cnt"><a href="javascript:removeRow('+seq+');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>';
			html += '</tr>';
			
			$("#addressList").append(html); // 행추가
		   	
		}
		
		/* 우편번호 레이어팝업 열기 */
		
		function zipcodeLayerPop(seq){
			$("#zipcodeLayerPop").bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="no" frameborder="0" width="348px" height="470px"',
				follow: [true, true],
				contentContainer:'.zipcode_content',
				modalClose: false,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxZipcodePopup.do?seq='+seq,
			});
		}
		
		/**
		 * 행삭제
		 */
		function removeRow(seq){
			if(1==$("#addressList tr").length){
				alert("최소 1건의 데이터는 있어야 됩니다.");
				return;
			}
			$("#addressList_"+seq).remove();
		}
		
		/**
		 * 레이어팝업 닫기
		 */
		function layerClose(){
			$("#zipcodeLayerPop").bPopup().close();
		}
		
		/**
		 * 저장
		 */
		function insertYearendtaxAddress(){
			
			if (0 == $("#addressList tr").length) {
				alert("최소 1건의 데이터는 있어야 됩니다.");
				return false;
			}
			
			/* 유효성 체크 */
			for (var i = 1; i <= $("#addressList tr").length; i++) {
				var tel_no = $("#tel_no_"+i);
				var zip_cd = $("#zip_cd_"+i);
				var address1 = $("#address1_"+i);
				var address2 = $("#address2_"+i);
				
				if (formCheck.isEmpty(tel_no.val())) {
					alert("휴대전화번호를 입력해 주세요.");
					tel_no.focus();
					return;
				}
				if(formCheck.isNumDash(tel_no.val())){
					alert("전화번호 형식이 아닙니다.\n하이픈(-)만 사용할 수 있습니다.");
					tel_no.focus();
					return false;
				}
				if (formCheck.isEmpty(zip_cd.val())) {
					alert("우편번호를 입력해 주세요.");
					zipcodeLayerPop(i);
					return false;
				}
				if (formCheck.isEmpty(address1.val())) {
					alert("주소1을 입력해 주세요.");
					zipcodeLayerPop(i);
					return false;
				}
				if (formCheck.isEmpty(address2.val())) {
					alert("상세주소를 입력해 주세요.");
					address2.focus();
					return false;
				}

			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/insertYearendtaxAddress.do",
				dataType:"json",
				success:function(data){
					alert("저장되었습니다.");
					
					if ("layer" == "<%=type%>") {
						window.top.defaultInfo(data.addressCnt);
						window.top.layerClose();
					} else {
						window.opener.defaultInfo(data.addressCnt);
						self.close();
					}
				}
			});
		}
		
		function closePopup(type) {
			if ("layer" == type) {
				window.top.goYearendtaxList();
			} else {
				window.close();
			}
		}
	</script>
</head>
<body>
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>전화번호와 주소정보 입력 (미입력시 연말정산 등록화면으로 넘어가지 않습니다.)</h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<form id="frm" name="frm" method="post">
				<input type="hidden" id="addressCnt" name="addressCnt" value="<%=yearendtaxAddressList.size()%>" />
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend zipcode">
						<tr>
							<td class="box">
								<div>
									<button type="button" class="btn_row letter" onclick="javascript:addRow();">+행추가</button>
									<table class="tbl_yearend mgn" id="tbl">
										<colgroup>
											<col style="width:22px"/>
											<col style="width:65px"/>
											<col style="width:98px"/>
											<col style="width:82px"/>
											<col style="width:196px"/>
											<col style="width:196px"/>
											<col style="width:32px" />
										</colgroup>
										<thead>
											<tr>
												<th></th>
												<th>사원번호</th>
												<th>휴대전화번호</th>
												<th>우편번호</th>
												<th>주소1</th>
												<th>상세주소</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="addressList">
											<%
											if(yearendtaxAddressList.size()!=0){
												for(int i=0; yearendtaxAddressList.size()>i; i++){
													YearendtaxAddressVO yearendtaxAddressVO = new YearendtaxAddressVO();
													yearendtaxAddressVO=yearendtaxAddressList.get(i);
													String zip_cd = yearendtaxAddressVO.getZip_cd().substring(0, 3) + "-" + yearendtaxAddressVO.getZip_cd().substring(3, 6);
											%>
											<tr id="addressList_<%=i+1%>">
												<th></th>
												<td><input type="text" name="emp_no" id="emp_no_<%=i+1%>" class="ipt_num ipt_disable" value="<%=yearendtaxAddressVO.getEmp_no()%>" readonly /></td>
												<td><input type="text" name="tel_no" id="tel_no_<%=i+1%>" class="ipt_tel" value="<%=yearendtaxAddressVO.getTel_no()%>" /></td>
												<td class="search">
													<span class="find_box">
														<input type="text" name="zip_cd" id="zip_cd_<%=i+1%>" class="serch_date" value="<%=zip_cd%>" onclick="javascript:zipcodeLayerPop();" readonly="readonly" />
														<button type="button" onclick="javascript:zipcodeLayerPop();" class="btn_find"><span class="blind">찾아보기</span></button>
													</span>
												</td>
												<td><input type="text" name="address1" id="address1_<%=i+1%>" value="<%=yearendtaxAddressVO.getAddress1()%>" class="ipt_adrs" /></td>
												<td><input type="text" name="address2" id="address2_<%=i+1%>" value="<%=yearendtaxAddressVO.getAddress2()%>" class="ipt_adrs2" /></td>
												<td class="del_btn_cnt"><a href="javascript:removeRow('<%=i+1%>');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>
											</tr>
											<%
												}
											}
											%>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
				</form>
			</div>
		</div>
		<div class="box_btn">
			<button type="button" onclick="javascript:insertYearendtaxAddress();">저장</button>
			<button type="button" onclick="javascript:closePopup('<%=type%>');"><% if("layer".equals(type)){%>목록<%} else {%>닫기<%} %></button>
			<button type="button" class="close" onclick="javascript:closePopup('<%=type%>');"></button>
		</div>
		<!-- 우편번호 조회 팝업 -->
		<div id="zipcodeLayerPop" style="display:none; width:auto; height:auto;">
			<div class="zipcode_content"></div> 
		</div>
	</div>
</div>

</body>
</html>