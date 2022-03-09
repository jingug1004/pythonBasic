<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01004.jsp
 * @메뉴명 : 문서 상세조회 팝업-기화기기안서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO"%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.VaporizeVO" %>
<%
	/* 기화기기안서 */
	VaporizeVO vaporizeVO = (VaporizeVO)request.getAttribute("vaporizeVO");
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	if(vaporizeVO == null){
		vaporizeVO = new VaporizeVO();
	}
%>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_vaporizer">
		<colgroup>
			<col class="cb_w86_1">
			<col style="">
			<col style="width:86px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>시행일자</th>
				<td colspan="3" class="date bdrn">
					<%=StringUtil.nvl(vaporizeVO.getImposition_ymd()) %>
				</td>
			</tr>
			<tr>
				<th>종류</th>
				<td colspan="3" class="bdrn">
					<ul>
					<%
					if(sCodeList.size()!=0){
						for(int i=0; sCodeList.size()>i; i++){
							CodeVO codeVo = new CodeVO();
							codeVo=sCodeList.get(i);
					%>
						<li <%if(i+1==sCodeList.size() || i+1==sCodeList.size()-1){%> class="mbn"<%} %>>
							<input type="radio" name="kind_cd" id="kind_cd" onclick="javascript:inner_text('<%=codeVo.getCd_nm() %>');" value="<%=codeVo.getCd()%>" <%if(StringUtil.nvl(vaporizeVO.getKind_cd()).equals(codeVo.getCd())){%>checked<%}%> disabled/>
							<label for="kind_cd"><%=codeVo.getCd_nm() %></label>
						</li>	
					<%
						}
					}
					%>
					</ul>
				</td>
			</tr>
			<tr>
				<th>1. 거래처명</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCust_nm()) %></td>
				<th>2. 거래처코드</th>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCust_cd()) %></td>
			</tr>
			<tr>
				<th>3. 대표자명</th>
				<td colspan="3" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getCeo_nm()) %></td>
			</tr>
			<tr>
				<th>4. 기종 및 수량</th>
				<td><%=StringUtil.nvl(vaporizeVO.getModel_qty()) %></td>
				<th>5. 월사용수량</th>
				<td class="bdrn"><%=StringUtil.nvl(vaporizeVO.getMonth_use_qty()) %></td>
			</tr>
			<tr>
				<th>6. 내용</th>
				<td colspan="3" class="bdrn con"><%=RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getContent()) %></td>
			</tr>
			<tr>
				<th>7. 특이사항</th>
				<td colspan="3" class="ta bdrn">
					<%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", vaporizeVO.getUnusual())) %>
				</td>
			</tr>
		</tbody>
	</table>
</div>