package org.sosfo.framework.log;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.LogManager;

/**
 * log4J를 구현한 클래스 퍼포먼스 저하시 풀링 해야합니다. 퍼포먼스 저하될걸로 예상합니다.
 * 
 * @author yunkidon@hotmail.com
 * @version 1.2
 */
public class SDLLOG {

	private static PatternLayout	layout		= new PatternLayout();


	/**
	 * default Constructor
	 */
	public SDLLOG() {
	}

	/**
	 * Debug level of SDL Logging
	 * 
	 * @param Stirng race_day - 20090104
	 * @param Stirng race_no - 01, 02, 03...
	 * @param Stirng type -DT,SP,WR....
	 * @param Object pStr -Original sdl
	 */
	public static void debug(String race_day, String race_no, String type, Object pStr) {
		FileAppender appender = null;
		Logger logger = Logger.getLogger(SDLLOG.class);
		
		try {
			appender = getAppender(race_day, race_no, type);
			logger.addAppender(appender);
			logger.setLevel((Level) Level.DEBUG);
			logger.debug(pStr);
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		} finally {
			try {
				logger.removeAppender(appender);
				appender.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * Info level of SDL Logging
	 * 
	 * @param Stirng race_day - 20090104
	 * @param Stirng race_no - 01, 02, 03...
	 * @param Stirng type -DT,SP,WR....
	 * @param Object pStr -Original sdl
	 */
	public static void info(String race_day, String race_no, String type, Object pStr) {
		FileAppender appender = null;
		Logger logger = Logger.getLogger(SDLLOG.class);
		try {
			appender = getAppender(race_day, race_no, type);
			logger.addAppender(appender);
			logger.setLevel((Level) Level.INFO);
			logger.info(pStr);
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		} finally {
			try {
				logger.removeAppender(appender);
				appender.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * Create a FileAppender
	 * 
	 * @param race_day
	 * @param raceno
	 * @param type
	 * @return FileAppender
	 * @throws Exception
	 */
	private static FileAppender getAppender(String race_day, String race_no, String type) throws Exception {
		FileAppender appender = null;
		try {
			appender = new FileAppender(layout, "../logs/" + race_day + "/" + race_no + "/" + type + ".log", true);
			appender.setName(race_day + "_" + race_no + "_" + type);
			return appender;
		} catch (Exception e) {
			Log.error("ERROR", "", e);
			throw e;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			SDLLOG.debug("20090104", "03", "SP", "test................." + i);
			SDLLOG.debug("20090105", "01", "DT", "test................" + i);
			SDLLOG.debug("20090106", "02", "WR", "test..............." + i);
			SDLLOG.debug("20090107", "03", "FN", "test................." + i);
		}

		
		/*
		Enumeration em = logger.getAllAppenders();

		int i = 1;
		while (em.hasMoreElements()) {
			Appender app = (Appender) em.nextElement();
			Log.debug("\n appenders :  " + app.getName() + "   adderss : " + (i++));
		}
		*/
	}
}
