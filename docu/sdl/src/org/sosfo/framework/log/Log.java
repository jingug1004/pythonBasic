package org.sosfo.framework.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * log4J�� ������ Ŭ����
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * Copyright: Copyright (c) 2006 Company: www.UbiwareLab.com
 * @author yunkidon@hotmail.com
 * @version 1.0
 * 
 * <pre>
 * Usage
 *   ConfigFactory cf = ConfigFactory.getInstance();
 *  Config config = cf.getConfiguration(&quot;base.properties&quot;);
 *  Log.debug(&quot;&quot;,&quot;&quot;,config.getString(&quot;ldap.server.primary.host&quot;)); --&gt;base.log
 *  Log.debug(&quot;TRACE&quot;,&quot;&quot;,config.getString(&quot;ldap.server.primary.host&quot;));--&gt;trace.log
 *  Log.debug(&quot;ERROR&quot;,&quot;&quot;,config.getString(&quot;ldap.server.primary.host&quot;));--&gt;error.log
 * </pre>
 */

public class Log {

    private static Logger logger = null;

    static {
	try {
	    PropertyConfigurator.configureAndWatch("log4j.properties");
	} catch (Exception ex) {
	    System.err.print("Log System not configured!");
	}

    }

    public Log() {
    }

    /**
     * ������ �α�
     * @param Object ��� ��Ʈ��
     */
    public static void debug(Object pStr) {
	logger = Logger.getRootLogger();
	logger.debug(pStr);
    }

    /**
     * ������ �α�
     * @param String �α����ϸ�
     * @param Object ��� ��Ʈ��
     */
    public static void debug(String pCat,  Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.debug(pStr);
    }

    /**
     * ��� �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void debug(String pCat, Object pCaller, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.debug(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * ���� �α�
     * @param Object ��� ��Ʈ��
     */
    public static void error(Object pStr) {
	logger = Logger.getRootLogger();
	logger.error(pStr);
    }

    /**
     * ���� �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û Ŭ����
     * @param Object ��� ��Ʈ��
     */
    public static void error(String pCat, Class pCaller, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.error(pCaller.getName() + " - " + pStr);
    }

    /**
     * ���� �α�
     * @param String �α����ϸ�
     * @param Object ��� ��Ʈ��
     */
    public static void error(String pCat, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.error(pStr);
    }

    /**
     * ���� �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void error(String pCat, Object pCaller, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.error(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * Fatal �α�
     * @param Object ��� ��Ʈ��
     */
    public static void fatal(Object pStr) {
	logger = Logger.getRootLogger();
	logger.fatal(pStr);
    }

    /**
     * Fatal �α�
     * @param String �α����ϸ�
     * @param Object ��� ��Ʈ��
     */
    public static void fatal(String pCat,Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.fatal( pStr);
    }

    /**
     * Fatal �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void fatal(String pCat, Object pCaller, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.fatal(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * ������ �α�
     * @param Object ��� ��Ʈ��
     */
    public static void info(Object pStr) {
	logger = Logger.getRootLogger();
	logger.info(pStr);
    }

    /**
     * ������ �α�
     * @param String �α����ϸ�
     * @param Object ��� ��Ʈ��
     */
    public static void info(String pCat, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.info(pStr);
    }

    /**
     * ������ �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void info(String pCat, Object pCaller, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.info(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * ��� �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void warn(Object pStr) {
	logger = Logger.getRootLogger();
	logger.warn(pStr);
    }

    /**
     * ��� �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void warn(String pCat, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.warn(pStr);
    }

    /**
     * ��� �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void warn(String pCat, Class pCaller, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.warn(pCaller.getName() + " - " + pStr);
    }

    /**
     * ��� �α�
     * @param String �α����ϸ�
     * @param Object ��¿�û ��ü
     * @param Object ��� ��Ʈ��
     */
    public static void warn(String pCat, Object pCaller, Object pStr) {
	logger = Logger.getLogger(pCat);
	logger.warn(pCaller.getClass().getName() + " - " + pStr);
    }
}
