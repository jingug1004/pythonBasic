package org.sosfo.framework.config;

import java.util.Hashtable;

import org.sosfo.framework.exception.PropNotFoundException;

/**
 * <pre>
 * Title: 환경설정 ConfigFactroy를 통해 Config를 접근한다.
 * Description: (commons-configuration-1.2.jar)
 * Copyright: Copyright (c) 2006
 * Company: www.UbiwareLab.com
 * @author yunkidon@hotmail.com
 * @version 1.0
 * </pre>
 */

/**
 * 환경 정보,메시지등 정적인 정보를 Key에 의해 쉽게 접근 할 수 있는 interface를 제공한다. <br>
 * 
 * <pre>
 * try {
 * 	ConfigFactory cf = ConfigFactory.getInstance();
 * 	Config config = cf.getConfiguration(&quot;base.properties&quot;);
 * 	System.out.print(config.getString(&quot;ldap.server.primary.host&quot;));
 * 	ConfigFactory cf1 = ConfigFactory.getInstance();
 * 	Config config1 = cf.getConfiguration(&quot;sso.properties&quot;);
 * 	System.out.print(&quot;\n&quot; + config1.getString(&quot;ldap.schema.root&quot;));
 * } catch (PropertiesNotFoundException e) {
 * 
 * }
 * </pre>
 */

public class ConfigFactory {

    private static Hashtable     configurationGroup = new Hashtable();

    private static ConfigFactory theInstance;

    /**
     * 기본 생성자
     */
    private ConfigFactory() {

    }

    /**
     * Double Check Lock 구현
     * @return <code>org.sosfo.framework.config.ConfigFactory</code> - 프로퍼티 공장
     */
    public static ConfigFactory getInstance() {
	if (theInstance == null) {
	    synchronized (ConfigFactory.class) {
		if (theInstance == null) {
		    theInstance = new ConfigFactory();
		}
	    }
	}
	return theInstance;
    }

    /**
     * 프로퍼티 접근
     * @param <code>String</code> 요청 프로퍼티의 풀이름(base.properties)
     * @return <code>org.sosfo.framework.config.Config</code> - 요청 Config
     * @throws <code>org.sosfo.framework.exception.PropertiesNotFoundException</code> - 파일읽기 실패
     */
    public Config getConfiguration(String prop_full_name) throws PropNotFoundException {

	try {
	    if (!configurationGroup.containsKey(prop_full_name)) {
	    	configurationGroup.put(prop_full_name, new Config(prop_full_name));
	    }
	} catch (Exception ex) {
	    System.err.print(ex);
	    throw new PropNotFoundException("properties 읽기 실패");
	}

	return (Config) configurationGroup.get(prop_full_name);
    }
}
