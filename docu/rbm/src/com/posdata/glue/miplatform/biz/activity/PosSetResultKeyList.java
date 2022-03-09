/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071026
*@LastModifier   : 조성민
*@LastVersion    : 1.1
*    2007-10-01    조성민
*        1.0       최초 생성
*    2007-10-26    조성민
*        1.1       Result Key를 담고 있는 String Array를 java.util.List로 변경
*                   - Custom 개발 시 편의성 제공
*                  Static Result Key 기능 지원
*                   - Java Property 파일을 로딩해서 Result Key를 저장
==============================================================================*/

package com.posdata.glue.miplatform.biz.activity;

import com.posdata.glue.PosException;
import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.biz.activity.PosMiPlatformConstants;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Miplatform으로 데이터를 XML로 Reponse 할 때 대상체를 지정해주는 클래스. <br>
 * 서비스 파일에 보통 컨텍스트 키를 지정한다. 다음 예제를 참조한다.
 * <xmp>
 *      <activity name="SetResultKeys" class="com.posdata.glue.miplatform.biz.activity.PosSetResultKeyList">  
 *          <property name="resultKeyList" value="ds_emp|UpdatedCount|InsertedCount" />
 *          <property name="staticResultKeyFile" value="resultkey.properties" />
 *          <transition name="success" value="EventRouter" />
 *      </activity>
 *      
 *      << 프로퍼티 설명 >>
 *      1. resultKeyList: 지정할 값이 여러 개인 경우는 '|'로 구분하여 설정한다.
 *      2. staticResultKeyFile: Static Result Key 파일명을 설정한다. (eg, resultkey.properties)
 *                              프로퍼티 파일은 클래스패스 루트에 위치해야 한다.
 *          ( resultkey.properties 내용 )
 *           rkey1=rkey1
 *           rkey2=rkey2
 *           anotherKey=anotherKey
 *           ...
 * </xmp>
 * 
 * @author 조성민
 */
public class PosSetResultKeyList extends PosActivity
{
    private PosResultKeyFileHandler handler;
    
    public String runActivity(PosContext ctx)
    {
        // File Handler 확인
        if (this.handler == null) 
        {
            this.handler = new PosResultKeyFileHandler(this.getProperty("staticResultKeyFile"));
        }
    
        String resultKeys = this.getProperty(PosMiPlatformConstants.RESULT_KEY_LIST);
        if (resultKeys == null)
            throw new PosException("Define result key list in Service XML File: " + getClass().getName());
        
        String [] resultKeyArr = resultKeys.split(PosMiPlatformConstants.RESULT_KEY_LIST_DELIMITER);        
        // String[] 대신에 java.util.List 형태로 변경
        // ctx.put(PosMiPlatformConstants.RESULT_KEY_LIST, resultKeyArr);
                    
        List resultList = new ArrayList(resultKeyArr.length);
        for (int i=0; i<resultKeyArr.length; i++) 
        {
            resultList.add(resultKeyArr[i]);
        }
        
        if (this.handler != null)
        {
            // add static result key list 
            List staticResultKeyList = handler.getStaticResultKeyList();
            resultList.addAll(staticResultKeyList);
        }
        
        ctx.put(PosMiPlatformConstants.RESULT_KEY_LIST, resultList);
        
        return PosBizControlConstants.SUCCESS;
    }
    
    private static class PosResultKeyFileHandler
    {
        private String filename;
        private List keyList;
        private PosLog log = PosLogFactory.getLogger(getClass());
        
        public PosResultKeyFileHandler(String name) 
        {
            this.filename = name;
        }
        
        public List getStaticResultKeyList() 
        {
            if (this.keyList == null)
                this.keyList = loadingFromProperties();
            return this.keyList;
        }
        
        private synchronized List loadingFromProperties() 
        {
            Properties prop = new Properties();
            List list = new ArrayList();
            try 
            {             
                InputStream is = 
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(this.filename);
                prop.load(is);
                Enumeration enumer = prop.propertyNames();                
                
                while (enumer.hasMoreElements()) 
                {
                    String name = (String)enumer.nextElement();
                    list.add(prop.getProperty(name));
                }
                log.logInfo("Loaded Static Result Key List: " + list);
            } 
            catch (NullPointerException ne) 
            {
                log.logWarn("Does not exist Result Key Properties File. File Name: [" + this.filename + "]");
            }
            catch (Exception ex) 
            {
                log.logWarn("Fail to load result key property file [" 
                            + this.filename + "]: " + ex.getMessage());
            }            
            return list;
        }
    }
}