/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : 조성민
*@LastVersion    : 1.0
*    2007-10-01    조성민
*        1.0       최초 생성
==============================================================================*/

package com.posdata.glue.miplatform.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.posdata.glue.PosException;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.biz.activity.PosDatasetGenerator;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.VariableList;


/**
 * 클라이언트에서 발생한 MiPlatform specific한 Data Structure(DatasetList, VariableList)를 
 * Glue Framework의 PosDataset 구조로 변경시켜 주는 어댑터 클래스. <br>
 * 서블릿 필터로 구현이 되어있으므로 web.xml에 다음 필터 정보를 추가해야 한다. <br>
 * 
 * <xmp>
 * <filter>
 *     <filter-name>GlueHttpAdaptor4MiPlatform</filter-name>
 *     <filter-class>com.posdata.glue.miplatform.web.PosGlueHttpAdaptor</filter-class> 
 *     <init-param>
 *         <param-name>CharSet</param-name>
 *         <param-value>euc-kr</param-value>
 *     </init-param>
 *     <init-param>
 *         <param-name>DumpDatasetInfo</param-name>
 *         <param-value>true</param-value>
 *     </init-param>
 * </filter>
 * <filter-mapping>
 *     <filter-name>GlueHttpAdaptor4MiPlatform</filter-name>
 *     <url-pattern>*.do</url-pattern>
 * </filter-mapping>
 * <filter-mapping>
 *     <filter-name>GlueHttpAdaptor4MiPlatform</filter-name>
 *     <url-pattern>*.jsp</url-pattern>
 * </filter-mapping>
 * 
 * 파라미터 'CharSet'의 의미는 Request 된 문자열의 Character Set 이다. 디폴트는 "euc-kr".
 * 파라미터 'DumpDatasetInfo'를 true로 설정하면 MiPlatform 클라이언트에서 발생한 
 * 정보(DataSet, Variable)를 표준출력스트림으로 출력한다. (디버그용)
 * 
 * </xmp>
 * 
 * @author 조성민
 */
public class PosGlueHttpAdaptor implements Filter 
{
    /** Default Character Set */
    private static final String DEFAULT_CHARSET = "euc-kr";
    
    /** Dataset, Varible Dump 여부 */
    private boolean isDump;
    
    /** Logger */
    protected PosLog log = PosLogFactory.getLogger(getClass());
    
    /** Character set */
    private String charset;
    
    /** Filter Config */
    private FilterConfig filterConfig;
    
    /**
     * 'CharSet', 'DumpDatasetInfo', 'StaticResultKeyFile' 파라미터를 설정한다. <br>
     *  - 'CharSet': PlatformRequest 생성자의 아규먼트 <br>
     *  - 'DumpDatasetInfo': 클라이언트에서 발생한 정보(Dataset, Variable)를 
     *                      표준출력으로 출력할지 여부를 결정 <br>
     * 
     * @throws javax.servlet.ServletException
     * @param config
     */
    public void init(FilterConfig config) throws ServletException
    {
        this.filterConfig = config;
        String charset = config.getInitParameter("CharSet");
        this.charset = (charset != null) ? charset : DEFAULT_CHARSET;
        
        String isDumpDS = config.getInitParameter("DumpDatasetInfo");         
        this.isDump = ("true".equalsIgnoreCase(isDumpDS)) ? true : false;
        
        log.logInfo("Filter is initialized");
    }

    /**
     * MiPlatform의 DatasetList, VariableList를 PosDataset 오브젝트로 
     * 변환시켜주는 필터메소드.
     * 
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @param filterChain FilterChain
     * @param response ServletReponse
     * @param request ServletRequest
     */
    public void doFilter(ServletRequest request, 
                        ServletResponse response, 
                        FilterChain filterChain) 
                        throws IOException, ServletException
    {
    	
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        System.out.println("====================================================");
        System.out.println(this.log.isDebugEnabled());
        System.out.println(httpRequest.getSession().getMaxInactiveInterval());
        System.out.println("====================================================");
        PlatformRequest platReq = new PlatformRequest(httpRequest, this.charset);
        platReq.receiveData();
        
        VariableList varList = platReq.getVariableList();
        DatasetList datasetList = platReq.getDatasetList();
        
        try 
        {
            // varList 추출 후 request attribute로 저장
            saveVariableList(varList, httpRequest);            
            // dataset 추출 후 request attribute로 저장
            saveDatasetList(datasetList, httpRequest);            
        } 
        catch (Exception ex) 
        {
            String errorMessage = 
                "Fail to extract Dataset information to Glue stuff. Detail Error Message: " 
                + ex.getMessage();
            log.logFatal(errorMessage, ex);
            throw new PosException(errorMessage);
        }
        
        
        filterChain.doFilter(httpRequest, response);
        
        if(isExpiredSession((HttpServletRequest)request)) {
        	request.setAttribute("PosContext", null);
        }
        
        // Client로 XML을 내려보냄
        new PosDatasetGenerator(
            (HttpServletRequest)request, 
            (HttpServletResponse)response,
            this.charset).processXMLGeneration();
    }
    
    
    
    /**
     * 로그인 세션 체크
     */
    public boolean isExpiredSession(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	//session.setMaxInactiveInterval(10); //테스트 (초단위)
    	String serviceName = request.getParameter("ServiceName");
    	if(serviceName.equals("common_login-service")) {
    		PosContext ctx = (PosContext)request.getAttribute("PosContext");
            if(ctx!=null && ctx.get("IS_LOGINED")!=null && ctx.get("IS_LOGINED").equals("true")){
            	session.setAttribute("IS_LOGINED", true);
            }
    		return false;
    	}
    	
    	
    	if(session.getAttribute("IS_LOGINED")!=null && session.getAttribute("IS_LOGINED").equals(true)) return false;
    	
    	
    	return true;
    }
    
    
    /**
     * MiPlatform DatasetList를 PosDataset 오브젝트로 
     * 변환한 뒤 HttpRequest의 Attribute에 저장한다.
     * 
     * @param request HttpServletRequest
     * @param datasetList MiPlatform DatasetList
     */
    protected void saveDatasetList(DatasetList datasetList, HttpServletRequest request) 
    {
        Map dslistMap = datasetList.getDatasetMap();
        Iterator dslistIter = dslistMap.keySet().iterator();
        while (dslistIter.hasNext()) 
        {
            Object key = dslistIter.next();
            Dataset ds = (Dataset)dslistMap.get(key);
            if (isDump) 
                ds.dump();  // for debug
            request.setAttribute(key.toString(), new PosDataset(ds));
            log.logInfo("Dataset ID: [" + key +"] is saved...");
        }
    }
    
    /**
     * MiPlatform VariableList의 각 항목을 추출해서  
     * HttpRequest의 Attribute에 저장한다.
     * 
     * @param request HttpServletRequest
     * @param varList MiPlatform VariableList
     */
    protected void saveVariableList(VariableList varList, HttpServletRequest request) 
    {
        Iterator iter = varList.idIterator();
        while (iter.hasNext()) 
        {
            String id = (String)iter.next();
            Object value = varList.getValueAsObject(id);            
            request.setAttribute(id, value);
            
            if (log.isDebugEnabled()) 
            {
                if (value == null)
                    value = "null";
                //log.logDebug( new StringBuffer(50).append("VarList id: ")
                //                .append(id).append(", value:").append(value.toString())
                //                .append(", type:").append(value.getClass().getName()));
            }
        }
    }
    
    /**
     * Clean up inner properties
     */
    public void destroy()
    {
        this.filterConfig = null;
        this.charset = null;
        this.isDump = false;
        log.logInfo("Filter is destroyed");
    }
}