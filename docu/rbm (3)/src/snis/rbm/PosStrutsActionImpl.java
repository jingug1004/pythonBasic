package snis.rbm;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.posdata.glue.biz.control.PosBizControlIF;
import com.posdata.glue.biz.control.PosBizProvider;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.web.control.PosMultipartRequest;
import com.posdata.glue.web.control.PosStrutsAction;

public class PosStrutsActionImpl extends PosStrutsAction {

	protected PosMultipartRequest processUploadFile(HttpServletRequest request)
			throws Exception {
		return super.processUploadFile(request);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		System.out.println("session id =====>"+request.getSession().getId());
		System.out.println(request.getParameter("ServiceName"));
		
		//세션종료 시간 설정(초) 
		request.getSession().setMaxInactiveInterval(10);
		
		Cookie[] cookies = request.getCookies(); 
        String jsession=""; 
        if ( cookies == null ) jsession = "null"; 
        else { 
	        for (int z = 0; z < cookies.length; z++) 
	        { 
		        Cookie thisCookie = cookies[z]; 
		        if (thisCookie.getName().equals("JSESSIONID")) 
		        { 
			        jsession = thisCookie.getValue(); 
			        System.out.println("jsession ===> " + jsession);
			        String jession1 = request.getSession().getId();
			        System.out.println("jession1 ===> " + jession1);
			        
			        if(!jsession.equals(jession1)){
			        	
			        	ActionForward forward = null;
			            PosContext ctx = null;
			            try
			            {
			              String serviceName = null;
			              String contentType = request.getContentType();
			              PosMultipartRequest multiPart = null;
			              Map param = request.getParameterMap();
			              if ((contentType != null) && (contentType.toLowerCase().startsWith("multipart/form-data")))
			              {
			                multiPart = processUploadFile(request);
			                param = multiPart.getParameterMap();
			                String[] serviceNameParam = (String[])param.get("ServiceName");
			                if (serviceNameParam != null)
			                {
			                  serviceName = serviceNameParam[0];
			                }
			              }
			              else {
			                serviceName = request.getParameter("ServiceName");
			              }
			              forward = mapping.findForward("success");
			            }
			            catch (Throwable ex)
			            {
			              if (ctx != null)
			              {
			                ctx.setException(ex);
			              }
			              forward = mapping.findForward("failure");
			              if (forward == null) forward = mapping.findForward("success");
			            }
			            return forward;
			        }else{
			        	return super.execute(mapping, form, request, response);
			        }
		        }
	        }
        }
        return super.execute(mapping, form, request, response);
	}
}
