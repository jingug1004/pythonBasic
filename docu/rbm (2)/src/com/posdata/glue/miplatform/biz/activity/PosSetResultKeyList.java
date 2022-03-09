/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071026
*@LastModifier   : ������
*@LastVersion    : 1.1
*    2007-10-01    ������
*        1.0       ���� ����
*    2007-10-26    ������
*        1.1       Result Key�� ��� �ִ� String Array�� java.util.List�� ����
*                   - Custom ���� �� ���Ǽ� ����
*                  Static Result Key ��� ����
*                   - Java Property ������ �ε��ؼ� Result Key�� ����
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
 * Miplatform���� �����͸� XML�� Reponse �� �� ���ü�� �������ִ� Ŭ����. <br>
 * ���� ���Ͽ� ���� ���ؽ�Ʈ Ű�� �����Ѵ�. ���� ������ �����Ѵ�.
 * <xmp>
 *      <activity name="SetResultKeys" class="com.posdata.glue.miplatform.biz.activity.PosSetResultKeyList">  
 *          <property name="resultKeyList" value="ds_emp|UpdatedCount|InsertedCount" />
 *          <property name="staticResultKeyFile" value="resultkey.properties" />
 *          <transition name="success" value="EventRouter" />
 *      </activity>
 *      
 *      << ������Ƽ ���� >>
 *      1. resultKeyList: ������ ���� ���� ���� ���� '|'�� �����Ͽ� �����Ѵ�.
 *      2. staticResultKeyFile: Static Result Key ���ϸ��� �����Ѵ�. (eg, resultkey.properties)
 *                              ������Ƽ ������ Ŭ�����н� ��Ʈ�� ��ġ�ؾ� �Ѵ�.
 *          ( resultkey.properties ���� )
 *           rkey1=rkey1
 *           rkey2=rkey2
 *           anotherKey=anotherKey
 *           ...
 * </xmp>
 * 
 * @author ������
 */
public class PosSetResultKeyList extends PosActivity
{
    private PosResultKeyFileHandler handler;
    
    public String runActivity(PosContext ctx)
    {
        // File Handler Ȯ��
        if (this.handler == null) 
        {
            this.handler = new PosResultKeyFileHandler(this.getProperty("staticResultKeyFile"));
        }
    
        String resultKeys = this.getProperty(PosMiPlatformConstants.RESULT_KEY_LIST);
        if (resultKeys == null)
            throw new PosException("Define result key list in Service XML File: " + getClass().getName());
        
        String [] resultKeyArr = resultKeys.split(PosMiPlatformConstants.RESULT_KEY_LIST_DELIMITER);        
        // String[] ��ſ� java.util.List ���·� ����
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