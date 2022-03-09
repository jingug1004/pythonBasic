package org.motorboat.common;

import org.apache.log4j.Logger;
import org.motorboat.db.*;
import org.motorboat.mng.*;
import org.motorboat.dto.*;
import org.motorboat.*;
import org.motorboat.existing.*;

/**
 * 공통적으로 사용되는 함수들을 static형태의 함수로 관리함
 * 
 * @author 김원겸 대상정보기술
 *
 */
public class SDL_Common {
	
	public static final String SYS_LOGGER = "MAIN";
	public static final String SDL_LOGGER = "SDLDATA";
	
	public static final Logger logger = Logger.getLogger(SDL_Common.SYS_LOGGER);
	public static final Logger dataLogger = Logger.getLogger(SDL_Common.SDL_LOGGER);
	
	public static final int POOL_CD_WIN = 10101;		// 단승식
	public static final int POOL_CD_PLC = 10102;		// 연승식
	public static final int POOL_CD_QU = 10103;			// 복승식
	public static final int POOL_CD_EX = 10104;			// 쌍승식
	public static final int POOL_CD_TRA = 10105;		// 삼복승
	public static final int POOL_CD_DD = 10106;			// 중승식
	
	/*
	 * -> 로그기록 함수를 사용할 경우 SDL_Common.debug(String message) 형태의 함수 보다는 
	 * SDL_Common.logger.debug(String message) 형태로 사용할 것을 권장함.
	 * -> SDL_Common.debug(String message)의 형태로 사용할 경우 로그기록을 시도한 파일의 라인 위치가
	 * SDL_Common.debug()함수의 라인위치로 표기가 되기 때문에 이를 해결하기 위해 SDL_Common.logger.debug()
	 * 형식으로 사용하여야 함
	 */
	
	/**
	 * Log4J를 이용하여 Debug 레벨의 로그를 기록함 (사용 권장안함)
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		SDL_Common.logger.debug(message);
	}
	
	/**
	 * Log4J를 이용하여 Info 레벨의 로그를 기록함 (사용 권장안함)
	 * 
	 * @param message
	 */
	public static void info(String message) {
		SDL_Common.logger.info(message);
	}
	
	/**
	 * Log4J를 이용하여 Warn 레벨의 로그를 기록함 (사용 권장안함)
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		SDL_Common.logger.warn(message);
	}
	
	/**
	 * Log4J를 이용하여 Error 레벨의 로그를 기록함 (사용 권장안함)
	 * 
	 * @param message
	 */
	public static void error(String message) {
		SDL_Common.logger.error(message);
	}
	
	/**
	 * Log4J를 이용하여 Fatal 레벨의 로그를 기록함 (사용 권장안함)
	 * 
	 * @param message
	 */
	public static void fatal(String message) {
		SDL_Common.logger.fatal(message);
	}
	
	/**
	 * Log4J를 이용하여 Serial Device로 부터 입력된 데이터를 Debug 레벨로 기록함
	 * 
	 * @param message
	 */
	public static void dataDebug(String message) {
		SDL_Common.dataLogger.debug(message);
	}
	
	/**
	 * Log4J를 이용하여 Serial Device로 부터 입력된 데이터를 Info 레벨로 기록함
	 * 
	 * @param message
	 */
	public static void dataInfo(String message) {
		SDL_Common.dataLogger.info(message);
	}
	
	/**
	 * Log4J를 이용하여 Serial Device로 부터 입력된 데이터를 Warn 레벨로 기록함
	 * 
	 * @param message
	 */
	public static void dataWarn(String message) {
		SDL_Common.dataLogger.warn(message);
	}
	
	/**
	 * Log4J를 이용하여 Serial Device로 부터 입력된 데이터를 Error 레벨로 기록함
	 * 
	 * @param message
	 */
	public static void dataError(String message) {
		SDL_Common.dataLogger.error(message);
	}
	
	/**
	 * Log4J를 이용하여 Serial Device로 부터 입력된 데이터를 Fatal 레벨로 기록함
	 * 
	 * @param message
	 */
	public static void dataFatal(String message) {
		SDL_Common.dataLogger.fatal(message);
	}

	/**
	 * packet에서 pos 위치 부터 len의 길이만큼 추출하여 String 형태로 반환함
	 * 
	 * @param packet
	 * @param pos
	 * @param len
	 * @return
	 */
	public static String getPacketFraction(String packet, int pos, int len) {
		
		String result = null;
		try {
			result = packet.substring(pos, pos + len);
		} catch(Exception ex) {
			return null;
		}
		return result; 

	}
	
	/**
	 * packet에서 pos 위치 부터 len의 길이만큼 추출하여 Int 형태로 반환함
	 * 
	 * @param packet
	 * @param pos
	 * @param len
	 * @return
	 */
	public static int getPacketFractionToInt(String packet, int pos, int len) {
		int result = -1;
		try {
			result = Integer.parseInt(packet.substring(pos, pos + len));
		} catch(Exception ex) {
			return -1;
		}
		return result;
	}

	/**
	 * packet으로 부터 len의 길이만큼 추출하여 byte[] 형태로 반환함
	 * 
	 * @param packet
	 * @param len
	 * @return
	 */
	public static byte[] splitPacket(byte[] packet, int len) {
		
		byte[] bb = new byte[len];
		
		byte[] cc = new byte[packet.length - len];

		System.arraycopy(packet, 0, bb, 0, len);
		System.arraycopy(packet, len, cc, 0, packet.length - len);
		
		packet = cc;
		
		return bb; 
	}
	
	/**
	 * src가 null 일 경우 defaultSrc를 반환하며 그렇지 않을 경우 src를 반환함
	 * 
	 * @param src
	 * @param defaultSrc
	 * @return
	 */
	public static String replaceNull(String src, String defaultSrc) {
		if (src == null)
			return defaultSrc;
		return src;
	}
	
	/**
	 * "MMDDYYYY"형태의 날자 문자열을 "YYYYMMDD" 형태로 변환하여 String형태로 반환함
	 * 
	 * @param src
	 * @return
	 */
	public static String convertDateMMDDYYYYToYYYYMMDD(String src) {
		String dest = src.substring(4);
		dest += src.substring(0, 4);
		return dest;
	}
	
	/**
	 * poolCD에 설정된 승식 영문문자열을 해당 코드번호로 변환하여 String 형태로 반환함 
	 * 
	 * @param poolCD
	 * @return
	 */
	public static String getPoolCDCode(String poolCD) {
		if (poolCD.equals("WIN")) {
			return "001";
		} else if (poolCD.equals("PLC")) {
			return "002";
		} else if (poolCD.equals("QU ")) {
			return "005";
		} else if (poolCD.equals("EX ")) {
			return "004";
		} else if (poolCD.equals("TLA")) {
			return "006";
		} else {
			return null;
		}
	}
	
	/**
	 * poolCDCode에 설정된 승식 코드번호를 해당 승식 영문문자열로 변환하여 String형태로 반환함
	 * 
	 * @param poolCDCode
	 * @return
	 */
	public static String getPoolCDName(String poolCDCode) {
		if (poolCDCode.equals("001")) {
			return "WIN";
		} else if (poolCDCode.equals("002")) {
			return "PLC";
		} else if (poolCDCode.equals("005")) {
			return "QU ";
		} else if (poolCDCode.equals("004")) {
			return "EX ";
		} else if (poolCDCode.equals("006")) {
			return "TRA";
		} else {
			return null;
		}
	}
}
