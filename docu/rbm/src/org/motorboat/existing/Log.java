package org.motorboat.existing;
//package org.sosfo.framework.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * log4J를 구현한 클래스
 * <p>Title: 192.168.2.10 DB 서버</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: www.krmra.or.kr</p>
 * @author yunkidon@sosfo.or.kr
 * @version 1.0
 */
public class Log {

    private static Logger logger = null;

    static {
        String conf = "/testdb/DevDB/devuser/log.properties";
        PropertyConfigurator.configureAndWatch(conf);
    }

    public Log() {
    }

    /**
     * @deprecated Method debug is deprecated
     */

    public static void debug(Object pStr) {
        logger = Logger.getRootLogger();
        logger.debug(pStr);
    }

    public static void debug(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.debug(pCaller.getName() + " - " + pStr);
    }

    public static void debug(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.debug(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * @deprecated Method error is deprecated
     */

    public static void error(Object pStr) {
        logger = Logger.getRootLogger();
        logger.error(pStr);
    }

    public static void error(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.error(pCaller.getName() + " - " + pStr);
    }

    public static void error(String pCat, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.error(pStr);
    }

    public static void error(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.error(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * @deprecated Method fatal is deprecated
     */

    public static void fatal(Object pStr) {
        logger = Logger.getRootLogger();
        logger.fatal(pStr);
    }

    public static void fatal(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.fatal(pCaller.getName() + " - " + pStr);
    }

    public static void fatal(String pCat, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.fatal(pStr);
    }

    public static void fatal(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.fatal(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * @deprecated Method info is deprecated
     */

    public static void info(Object pStr) {
        logger = Logger.getRootLogger();
        logger.info(pStr);
    }

    public static void info(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.info(pCaller.getName() + " - " + pStr);
    }

    public static void info(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.info(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * @deprecated Method warn is deprecated
     */

    public static void warn(Object pStr) {
        logger = Logger.getRootLogger();
        logger.warn(pStr);
    }

    public static void warn(String pCat, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.warn(pStr);
    }

    public static void warn(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.warn(pCaller.getName() + " - " + pStr);
    }

    public static void warn(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.warn(pCaller.getClass().getName() + " - " + pStr);
    }
}
