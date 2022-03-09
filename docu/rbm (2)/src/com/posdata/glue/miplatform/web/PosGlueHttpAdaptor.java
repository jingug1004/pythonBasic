/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : ������
*@LastVersion    : 1.0
*    2007-10-01    ������
*        1.0       ���� ����
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
 * Ŭ���̾�Ʈ���� �߻��� MiPlatform specific�� Data Structure(DatasetList, VariableList)�� 
 * Glue Framework�� PosDataset ������ ������� �ִ� ����� Ŭ����. <br>
 * ���� ���ͷ� ������ �Ǿ������Ƿ� web.xml�� ���� ���� ������ �߰��ؾ� �Ѵ�. <br>
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
 * �Ķ���� 'CharSet'�� �ǹ̴� Request �� ���ڿ��� Character Set �̴�. ����Ʈ�� "euc-kr".
 * �Ķ���� 'DumpDatasetInfo'�� true�� �����ϸ� MiPlatform Ŭ���̾�Ʈ���� �߻��� 
 * ����(DataSet, Variable)�� ǥ����½�Ʈ������ ����Ѵ�. (����׿�)
 * 
 * </xmp>
 * 
 * @author ������
 */
public class PosGlueHttpAdaptor implements Filter 
{
    /** Default Character Set */
    private static final String DEFAULT_CHARSET = "euc-kr";
    
    /** Dataset, Varible Dump ���� */
    private boolean isDump;
    
    /** Logger */
    protected PosLog log = PosLogFactory.getLogger(getClass());
    
    /** Character set */
    private String charset;
    
    /** Filter Config */
    private FilterConfig filterConfig;
    
    /**
     * 'CharSet', 'DumpDatasetInfo', 'StaticResultKeyFile' �Ķ���͸� �����Ѵ�. <br>
     *  - 'CharSet': PlatformRequest �������� �ƱԸ�Ʈ <br>
     *  - 'DumpDatasetInfo': Ŭ���̾�Ʈ���� �߻��� ����(Dataset, Variable)�� 
     *                      ǥ��������� ������� ���θ� ���� <br>
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
     * MiPlatform�� DatasetList, VariableList�� PosDataset ������Ʈ�� 
     * ��ȯ�����ִ� ���͸޼ҵ�.
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
            // varList ���� �� request attribute�� ����
            saveVariableList(varList, httpRequest);            
            // dataset ���� �� request attribute�� ����
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
        
        // Client�� XML�� ��������
        new PosDatasetGenerator(
            (HttpServletRequest)request, 
            (HttpServletResponse)response,
            this.charset).processXMLGeneration();
    }
    
    
    
    /**
     * �α��� ���� üũ
     */
    public boolean isExpiredSession(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	//session.setMaxInactiveInterval(10); //�׽�Ʈ (�ʴ���)
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
     * MiPlatform DatasetList�� PosDataset ������Ʈ�� 
     * ��ȯ�� �� HttpRequest�� Attribute�� �����Ѵ�.
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
     * MiPlatform VariableList�� �� �׸��� �����ؼ�  
     * HttpRequest�� Attribute�� �����Ѵ�.
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