package org.sosfo.framework.config;

import java.util.Hashtable;

import org.sosfo.framework.exception.PropNotFoundException;

/**
 * <pre>
 * Title: ȯ�漳�� ConfigFactroy�� ���� Config�� �����Ѵ�.
 * Description: (commons-configuration-1.2.jar)
 * Copyright: Copyright (c) 2006
 * Company: www.UbiwareLab.com
 * @author yunkidon@hotmail.com
 * @version 1.0
 * </pre>
 */

/**
 * ȯ�� ����,�޽����� ������ ������ Key�� ���� ���� ���� �� �� �ִ� interface�� �����Ѵ�. <br>
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
     * �⺻ ������
     */
    private ConfigFactory() {

    }

    /**
     * Double Check Lock ����
     * @return <code>org.sosfo.framework.config.ConfigFactory</code> - ������Ƽ ����
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
     * ������Ƽ ����
     * @param <code>String</code> ��û ������Ƽ�� Ǯ�̸�(base.properties)
     * @return <code>org.sosfo.framework.config.Config</code> - ��û Config
     * @throws <code>org.sosfo.framework.exception.PropertiesNotFoundException</code> - �����б� ����
     */
    public Config getConfiguration(String prop_full_name) throws PropNotFoundException {

	try {
	    if (!configurationGroup.containsKey(prop_full_name)) {
		configurationGroup.put(prop_full_name, new Config(prop_full_name));
	    }
	} catch (Exception ex) {
	    System.err.print(ex);
	    throw new PropNotFoundException("properties �б� ����");
	}

	return (Config) configurationGroup.get(prop_full_name);
    }
}
