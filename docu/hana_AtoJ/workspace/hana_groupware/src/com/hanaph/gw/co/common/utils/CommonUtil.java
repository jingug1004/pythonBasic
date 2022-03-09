/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.common.utils;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <pre>
 * Class Name : ModelAndViewCommon.java
 * 설명 : 공통 Util
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 20. JaeKap Kim		생성
 * </pre>
 * 
 * @version 1.0
 * @author slawin(@irush.co.kr)
 * @since 2014. 10. 20.
 */
public class CommonUtil {
	
	/**
	 * <pre>
	 * 1. 개요     : 커스터마이징 ModelAndView 
	 * 2. 처리내용 : 메시지 스크립트 출력 후 script값 script수행
	 * </pre>
	 * @Method Name : getMessageView
	 * @param msg
	 * @param script
	 * @return
	 */		
	public static ModelAndView getMessageView(final String msg, final String script, final String popupYn) {
		View view = new AbstractView() {
			
			@Override
			protected void renderMergedOutputModel(Map<String, Object> arg0,
					HttpServletRequest request, HttpServletResponse response) throws Exception {
				
				Environment env = new Environment();
				String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
				
				String popup = StringUtil.nvl(popupYn, "N");
				/*		
				response.setContentType("text/html; charset=EUC-KR");
				response.setCharacterEncoding("EUC-KR");
				ServletOutputStream outs = response.getOutputStream();
				outs.println("<script type=\"text/javascript\" charset=\"utf-8\" src=\""+GROUP_WEB_ROOT+"/js/jquery-1.11.1.min.js\"></script>");
				outs.println("<script type=\"text/javascript\" charset=\"utf-8\" src=\""+GROUP_WEB_ROOT+"/js/common.js\"></script>");
				outs.println("<script type=\"text/javascript\">");
				outs.println("alert(\"" + new String(msg.getBytes(), "ISO_8859_1") + "\");");
				outs.println(new String(script.getBytes(), "ISO_8859_1"));
				
				//팝업 페이지인 경우 본창 닫기.
				if("Y".equals(popup)){
					outs.println("window.self.close();");
					outs.println("opener."+new String(script.getBytes(), "ISO_8859_1"));
				}
				
				outs.println("</script>");
				
				outs.flush();
				*/
				
				response.setContentType("text/html; charset=utf-8");
				PrintWriter outs = response.getWriter();
				
				outs.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\""+GROUP_WEB_ROOT+"/js/jquery-1.11.1.min.js\"></script>");
				outs.write("<script type=\"text/javascript\" charset=\"utf-8\" src=\""+GROUP_WEB_ROOT+"/js/common.js\"></script>");
				outs.write("<script type=\"text/javascript\">");
				outs.write("alert(\"" + msg + "\");");
				outs.write(script);
				
				//팝업 페이지인 경우 본창 닫기.
				if("Y".equals(popup)){
					outs.write("window.self.close();");
					outs.write("opener."+script);
				}
				
				outs.write("</script>");
				
				outs.flush();
			}
		};
		return new ModelAndView(view);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : StringBuffer에 스크립트 담아서 뷰단으로 이동
	 * 2. 처리내용 : 포스트 방식으로 returnURL로 이동한다.
	 * </pre>
	 * @Method Name : postMessageView
	 * @param msg, returnParamMap
	 * @param returnParamMap
	 * @return
	 */
	public static String postMessageView(final String msg, final Map<String, String> returnParamMap) {
		Environment env = new Environment();
		String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
		StringBuffer outs = new StringBuffer();
		
		outs.append("<html>");
		outs.append("<head>");
		outs.append("<script type=\"text/javascript\" src=\""+GROUP_WEB_ROOT+"/js/jquery-1.11.1.min.js\"></script>");
		outs.append("<script type=\"text/javascript\" src=\""+GROUP_WEB_ROOT+"/js/common.js\"></script>");
		outs.append("<script type=\"text/javascript\">");
		outs.append("$(document).ready(function(){");
		outs.append("alert(\"" + msg + "\");");
		outs.append("var $form = $('#frm');");
		outs.append("if($form.size() < 1) {");
		outs.append("$form = $('<form/>').attr({id:'frm', name:'frm', method:'POST', action:'"+returnParamMap.get("returnURL")+"' });");
		outs.append("$(document.body).append($form);");
		outs.append("}");
		outs.append("$form.empty();");
		for (Map.Entry<String, String> entry: returnParamMap.entrySet()) {
			outs.append("$('<input></input>').attr({type:'hidden', id:'"+entry.getKey()+"',name:'"+entry.getKey()+"', value:'"+entry.getValue()+"'}).appendTo($form);");
		}
		outs.append("if(parent && window!=window.top){");
		outs.append("	$('#frm').attr('target','_parent');");
		outs.append("}");
		outs.append("$('#frm').submit();");
		outs.append("});");
		outs.append("</script>");
		outs.append("</head>");
		outs.append("<body>");
		outs.append("</body>");
		outs.append("</html>");
		return outs.toString();
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 현재 웹요청이 ajax요청인지 아닌지 구분.
	 * 2. 처리내용 : header에 특정 프로퍼티가 있을 경우.
	 * </pre>
	 * @Method Name : isAjaxRequest
	 * @param req
	 * @return
	 */		
	public static boolean isAjaxRequest(HttpServletRequest req) {
		String[] ajaxHaeder = {"X-Requested-With", "AJAX"};
		for(String compStr : ajaxHaeder){
			if(req.getHeader(compStr) != null){
				return true;
			}
		}
		return false;
	}
}
