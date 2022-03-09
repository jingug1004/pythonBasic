package org.sosfo.sdl.cra.common;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.PropNotFoundException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.util.StringUtil;

public class CodeManager {

	private static Config	conf	= null; // 전문 포멧 config

	static {
		try {
			conf = ConfigFactory.getInstance().getConfiguration("sdl.properties");
		} catch (PropNotFoundException e) {
			Log.error("ERROR", "CodeManager.getConfig : Couldn't find [sdl.properties] \n" + e);
		}
	}

	private CodeManager() {}

	/**
	 * pool name (WIN, PLC....) 을 sdl.properties에 정의된 코드로 바꿔준다.
	 * 
	 * @param key : pool name
	 * @return
	 */
	public static String getPoolCD(String key) {
		try {
			String propertyKey = "sdl.cra.pool_nm." + StringUtil.rplc(key, " ", "");
			return conf.getString(propertyKey).trim();
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * meet name (CRA, MRA) 을 sdl.properties에 정의된 코드로 바꿔준다.
	 * 
	 * @param key : meet name
	 * @return
	 */
	public static String getMeetCD(String key) {
		try {
			String propertyKey = "sdl.cra.meet_cd." + StringUtil.rplc(key, " ", "");
			return conf.getString(propertyKey).trim();
		} catch (Exception e) {
			return "";
		}
	}

}
