<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxFamilyPopup.jsp
 * @메뉴명 : 연말정산 등록 > 부양가족 팝업
 * @최초작성일 : 2015/02/16
 * @author : 윤범진                  
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ include file ="/common/path.jsp" %>
<%
	@SuppressWarnings("unchecked")
	List<YearendtaxVO> familyList = (List<YearendtaxVO>)request.getAttribute("familyList");
	
	String year = (String)request.getAttribute("year");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
		$(document).ready(function(){
			$("#familyList input[type='checkbox']").click(function() {
				checkedValue($(this));
			});
		});
	
		var deleteDataArr = []; // delete용 배열
	
		/* 행 추가 */
		function addRow(){
			
			var seq = $("#familyList tr").size() + 1;
			var html = '';
			html += '<tr id="familyList_'+seq+'" style="height:35px;">';
			html += '	<th></th>';
			html += '	<td>';
			html += '		<select name="foreign_cd" id="foreign_cd_'+seq+'">';
			html += '			<option value="1" selected>내국인</option>';
			html += '			<option value="9" >외국인</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input style="width:56px;" type="text" name="rel_nm" id="rel_nm_'+seq+'" /></td>';
			html += '	<td>';
			html += '		<select name="rel_cd" id="rel_cd_'+seq+'" class="sec_code" onchange="javascript:checkRelCd(this, \''+seq+'\');">';
			html += '			<option value="0" selected>소득자본인</option>';
			html += '			<option value="1" >소득자의직계존속</option>';
			html += '			<option value="2" >배우자의직계존속</option>';
			html += '			<option value="3" >배우자</option>';
			html += '			<option value="4" >직계비속(자녀,입양자)</option>';
			html += '			<option value="5" >직계비속(위 항목 제외)</option>';
			html += '			<option value="6" >형제자매</option>';
			html += '			<option value="7" >수급자</option>';
			html += '			<option value="8" >위탁아동</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="text" name="rel_jumin_no" id="rel_jumin_no_'+seq+'" />';
			html += '		<input type="hidden" name="org_rel_jumin_no" id="org_rel_jumin_no_'+seq+'" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="choose_yn" id="choose_yn_'+seq+'" checked />';
			html += '		<input type="hidden" name="val_choose_yn" id="val_choose_yn_'+seq+'" value="Y" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="pensioner_yn" id="pensioner_yn_'+seq+'" />';
			html += '		<input type="hidden" name="val_pensioner_yn" id="val_pensioner_yn_'+seq+'" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="foster_child_yn" id="foster_child_yn_'+seq+'" />';
			html += '		<input type="hidden" name="val_foster_child_yn" id="val_foster_child_yn_'+seq+'" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="respect_aged_yn" id="respect_aged_yn_'+seq+'" />';
			html += '		<input type="hidden" name="val_respect_aged_yn" id="val_respect_aged_yn_'+seq+'" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="disabled_person_yn" id="disabled_person_yn_'+seq+'" />';
			html += '		<input type="hidden" name="val_disabled_person_yn" id="val_disabled_person_yn_'+seq+'" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="woman_yn" id="woman_yn_'+seq+'" />';
			html += '		<input type="hidden" name="val_woman_yn" id="val_woman_yn_'+seq+'" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="single_parents_yn" id="single_parents_yn_'+seq+'" />';
			html += '		<input type="hidden" name="val_single_parents_yn" id="val_single_parents_yn_'+seq+'" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="insulance_yn" id="insulance_yn_'+seq+'" checked />';
			html += '		<input type="hidden" name="val_insulance_yn" id="val_insulance_yn_'+seq+'" value="Y" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="medical_yn" id="medical_yn_'+seq+'" checked />';
			html += '		<input type="hidden" name="val_medical_yn" id="val_medical_yn_'+seq+'" value="Y" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="education_yn" id="education_yn_'+seq+'" checked />';
			html += '		<input type="hidden" name="val_education_yn" id="val_education_yn_'+seq+'" value="Y" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="card_yn" id="card_yn_'+seq+'" checked />';
			html += '		<input type="hidden" name="val_card_yn" id="val_card_yn_'+seq+'" value="Y" />';
			html += '	</td>';
			html += '	<td>';
			html += '		<input type="checkbox" name="contribution_yn" id="contribution_yn_'+seq+'" checked />';
			html += '		<input type="hidden" name="val_contribution_yn" id="val_contribution_yn_'+seq+'" value="Y" />';
			html += '	</td>';
			html += '	<td class="del_btn_cnt">';
			html += '		<a href="javascript:removeRow(\''+seq+'\');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a>';
			html += '		<input type="hidden" name="delete_yn" id="delete_yn_'+seq+'" />';
			html += '	</td>';
			html += '</tr>';
			
			$("#familyList").append(html); // 행추가
			$("#familyList_" + seq + " input[type='checkbox']").click(function() { // 유효성 체크 적용
				checkedValue($(this));
			});
		}
		
		/* 행 삭제 */
		function removeRow(seq){
			if ("0" == $("#rel_cd_"+seq).val()) {
				alert("소득자본인은 삭제 할 수 없습니다.");
				return;
			}
			
			$("#familyList_"+seq).hide();
			$("#delete_yn_"+seq).val("Y");
		}
		
		/* 저장 */
		function procYearendtaxFamilyAjax(){
			
			/* 소득자 본인은 1명만 가능하도록 체크 */
			var relCdCount = 0;
			
			$("select[name='rel_cd']").each(function(){
				if ("0" == $(this).val()) {
					relCdCount++;
				}
			});
			
			if (relCdCount > 1) {
				alert("소득자본인이 2명 이상입니다.");
				return false;
			} else if (relCdCount == 0) {
				alert("소득자본인이 없습니다.");
				return false;
			}
			
			/* 유효성 체크 */
			for (var i = 1; i <= $("#familyList tr").length; i++) {
				if ($("#delete_yn_"+i).val() != "Y") {
					
					var rel_nm = $("#rel_nm_"+i);
					var rel_jumin_no = $("#rel_jumin_no_"+i);
					
					if (formCheck.isEmpty(rel_nm.val())) {
						alert("성명을 입력해주세요.");
						rel_nm.focus();
						return false;
					}
					
					if (formCheck.getByteLength(rel_nm.val()) > 20) {
						alert("성명은 한글 10자 혹은 영문 20자 이내로 입력해주세요.(20byte)");
						rel_nm.focus();
						return false;
					}
					
					if (formCheck.getByteLength(formCheck.removeChar(rel_jumin_no.val())) != 13) {
						alert("올바른 관계인주민번호를 입력해주세요.");
						rel_jumin_no.focus();
						return false;
					}
				}
			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxFamilyAjax.do",
				dataType:"json",
				success:function(data){
					if (data.result == "success") {
						alert("저장되었습니다.");
						opener.getYearendtaxInfoAjax('family');
						self.close();
					} else {
						alert(data.result);
					}
				}
			});
		}
		
		/* 관계코드 선택 */
		function checkRelCd(obj, seq){
			
			/* 초기화 */
			$("#woman_yn_" + seq).prop("checked", false);
			$("#single_parents_yn_" + seq).prop("checked", false);
			$("#val_woman_yn_" + seq).val("");
			$("#val_single_parents_yn_" + seq).val("");
			
			/* 소득자 본인인 경우 부녀자, 한부모 컬럼 노출 */
			if ("0" == $(obj).val()) {
				$("#woman_yn_" + seq).show();
				$("#single_parents_yn_" + seq).show();
			} else {
				$("#woman_yn_" + seq).hide();
				$("#single_parents_yn_" + seq).hide();
			}
		}
		
		/* 체크박스 선택 시 hidden value 셋팅 */
		function checkedValue(obj){
			
			var checked = obj.prop("checked");
			var id = obj.attr("id");
			
			if (checked) {
				$("#val_" + id).val("Y");
			} else {
				$("#val_" + id).val("N");
			}
		}
	</script>
</head>
<body>

<!-- pop up 가로 사이즈 820px -->
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>기본공제 <span>(가족사항)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend family">
						<tr>
							<td class="box">
								<div>
									<button type="button" class="btn_row letter" onclick="javascript:addRow();">+ 행추가</button>
									<form name="frm" id="frm" method="post">
										<input type="hidden" name="year" id="year" value="<%=year %>" />
										
										<table class="tbl_yearend mgn">
											<colgroup>
												<col style="width:22px"/>
												<col style="width:70px"/>
												<col style="width:70px;" />
												<col style="width:140px"/>
												<col style=""/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:22px"/>
												<col style="width:24px" />
											</colgroup>
											<thead>
												<tr>
													<th></th>
													<th class="type01">내외국인</th>
													<th class="type02">성명</th>
													<th class="type01">관계코드</th>
													<th class="type01">관계인주민번호</th>
													<th class="type02">기본</th>
													<th class="type02">수급자</th>
													<th class="type02">위탁아동</th>
													<th class="type03">경로우대</th>
													<th class="type03">장애인</th>
													<th class="type03">부녀자</th>
													<th class="type03">한부모</th>
													<th class="type04">보험료</th>
													<th class="type04">의료비</th>
													<th class="type04">교육비</th>
													<th class="type04">신용카드</th>
													<th class="type04">기부금</th>
													<th></th>
												</tr>
											</thead>
											<tbody id="familyList">
											<%
												for(int i = 0; i < familyList.size(); i++){
													
													YearendtaxVO familyDetail = familyList.get(i);
													
													int foreign_cd = familyDetail.getForeign_cd();
													String rel_nm = StringUtil.nvl(familyDetail.getRel_nm());
													String rel_cd = StringUtil.nvl(familyDetail.getRel_cd());
													String rel_jumin_no = StringUtil.nvl(familyDetail.getRel_jumin_no());
													String choose_yn = StringUtil.nvl(familyDetail.getChoose_yn());
													String pensioner_yn = StringUtil.nvl(familyDetail.getPensioner_yn());
													String foster_child_yn = StringUtil.nvl(familyDetail.getFoster_child_yn());
													String respect_aged_yn = StringUtil.nvl(familyDetail.getRespect_aged_yn());
													String disabled_person_yn = StringUtil.nvl(familyDetail.getDisabled_person_yn());
													String woman_yn = StringUtil.nvl(familyDetail.getWoman_yn());
													String single_parents_yn = StringUtil.nvl(familyDetail.getSingle_parents_yn());
													String insulance_yn = StringUtil.nvl(familyDetail.getInsulance_yn());
													String medical_yn = StringUtil.nvl(familyDetail.getMedical_yn());
													String education_yn = StringUtil.nvl(familyDetail.getEducation_yn());
													String card_yn = StringUtil.nvl(familyDetail.getCard_yn());
													String contribution_yn = StringUtil.nvl(familyDetail.getContribution_yn());
													
													if (!"".equals(rel_jumin_no) && rel_jumin_no.length() == 13) {
														rel_jumin_no = rel_jumin_no.substring(0, 6) + "-" + rel_jumin_no.substring(6, 13);
													}
											%>
												<tr id="familyList_<%=i+1 %>">
													<th><%=i+1 %></th>
													<td>
														<select name="foreign_cd" id="foreign_cd_<%=i+1 %>">
															<option value="1" <% if(1 == foreign_cd) { %>selected<% } %>>내국인</option>
															<option value="9" <% if(9 == foreign_cd) { %>selected<% } %>>외국인</option>
														</select>
													</td>
													<td><input style="width:56px;" type="text" name="rel_nm" id="rel_nm_<%=i+1 %>" value="<%=rel_nm %>" /></td>
													<td>
														<select name="rel_cd" id="rel_cd_<%=i+1 %>" class="sec_code" onchange="javascript:checkRelCd(this, '<%=i+1 %>');">
															<option value="0" <% if("0".equals(rel_cd)) { %>selected<% } %>>소득자본인</option>
															<option value="1" <% if("1".equals(rel_cd)) { %>selected<% } %>>소득자의직계존속</option>
															<option value="2" <% if("2".equals(rel_cd)) { %>selected<% } %>>배우자의직계존속</option>
															<option value="3" <% if("3".equals(rel_cd)) { %>selected<% } %>>배우자</option>
															<option value="4" <% if("4".equals(rel_cd)) { %>selected<% } %>>직계비속(자녀,입양자)</option>
															<option value="5" <% if("5".equals(rel_cd)) { %>selected<% } %>>직계비속(위 항목 제외)</option>
															<option value="6" <% if("6".equals(rel_cd)) { %>selected<% } %>>형제자매</option>
															<option value="7" <% if("7".equals(rel_cd)) { %>selected<% } %>>수급자</option>
															<option value="8" <% if("8".equals(rel_cd)) { %>selected<% } %>>위탁아동</option>
														</select>
													</td>
													<td>
														<input type="text" name="rel_jumin_no" id="rel_jumin_no_<%=i+1 %>" value="<%=rel_jumin_no %>" />
														<input type="hidden" name="org_rel_jumin_no" id="org_rel_jumin_no_<%=i+1 %>" value="<%=rel_jumin_no %>"/>
													</td>
													<td>
														<input type="checkbox" name="choose_yn" id="choose_yn_<%=i+1 %>" <% if("Y".equals(choose_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_choose_yn" id="val_choose_yn_<%=i+1 %>" value="<%=choose_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="pensioner_yn" id="pensioner_yn_<%=i+1 %>" <% if("Y".equals(pensioner_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_pensioner_yn" id="val_pensioner_yn_<%=i+1 %>" value="<%=pensioner_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="foster_child_yn" id="foster_child_yn_<%=i+1 %>" <% if("Y".equals(foster_child_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_foster_child_yn" id="val_foster_child_yn_<%=i+1 %>" value="<%=foster_child_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="respect_aged_yn" id="respect_aged_yn_<%=i+1 %>" <% if("Y".equals(respect_aged_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_respect_aged_yn" id="val_respect_aged_yn_<%=i+1 %>" value="<%=respect_aged_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="disabled_person_yn" id="disabled_person_yn_<%=i+1 %>" <% if("Y".equals(disabled_person_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_disabled_person_yn" id="val_disabled_person_yn_<%=i+1 %>" value="<%=disabled_person_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="woman_yn" id="woman_yn_<%=i+1 %>" <% if(!"0".equals(rel_cd)) { %>style="display:none;"<% } %><% if("Y".equals(woman_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_woman_yn" id="val_woman_yn_<%=i+1 %>" value="<%=woman_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="single_parents_yn" id="single_parents_yn_<%=i+1 %>" <% if(!"0".equals(rel_cd)) { %>style="display:none;"<% } %><% if("Y".equals(single_parents_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_single_parents_yn" id="val_single_parents_yn_<%=i+1 %>" value="<%=single_parents_yn%>"/>
													</td>	
													<td>
														<input type="checkbox" name="insulance_yn" id="insulance_yn_<%=i+1 %>" <% if("Y".equals(insulance_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_insulance_yn" id="val_insulance_yn_<%=i+1 %>" value="<%=insulance_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="medical_yn" id="medical_yn_<%=i+1 %>" <% if("Y".equals(medical_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_medical_yn" id="val_medical_yn_<%=i+1 %>" value="<%=medical_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="education_yn" id="education_yn_<%=i+1 %>" <% if("Y".equals(education_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_education_yn" id="val_education_yn_<%=i+1 %>" value="<%=education_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="card_yn" id="card_yn_<%=i+1 %>" <% if("Y".equals(card_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_card_yn" id="val_card_yn_<%=i+1 %>" value="<%=card_yn%>"/>
													</td>
													<td>
														<input type="checkbox" name="contribution_yn" id="contribution_yn_<%=i+1 %>" <% if("Y".equals(contribution_yn)) { %>checked<% } %>/>
														<input type="hidden" name="val_contribution_yn" id="val_contribution_yn_<%=i+1 %>" value="<%=contribution_yn%>"/>
													</td>
													<td class="del_btn_cnt">
														<a href="javascript:removeRow('<%=i+1 %>');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a>
														<input type="hidden" name="delete_yn" id="delete_yn_<%=i+1 %>" />
													</td>
												</tr>
											<%	
												}
											%>
											</tbody>
										</table>
									</form>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<button type="button" class="close" onclick="javascript:self.close();"><span class="blind">닫기</span></button>
		<div class="box_btn">
			<button type="button" onclick="javascript:procYearendtaxFamilyAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
	</div>
</div>

</body>
</html>