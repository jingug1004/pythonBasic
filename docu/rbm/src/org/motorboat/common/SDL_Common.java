package org.motorboat.common;

import org.apache.log4j.Logger;
import org.motorboat.db.*;
import org.motorboat.mng.*;
import org.motorboat.dto.*;
import org.motorboat.*;
import org.motorboat.existing.*;

/**
 * ���������� ���Ǵ� �Լ����� static������ �Լ��� ������
 * 
 * @author ����� ����������
 *
 */
public class SDL_Common {
	
	public static final String SYS_LOGGER = "MAIN";
	public static final String SDL_LOGGER = "SDLDATA";
	
	public static final Logger logger = Logger.getLogger(SDL_Common.SYS_LOGGER);
	public static final Logger dataLogger = Logger.getLogger(SDL_Common.SDL_LOGGER);
	
	public static final int POOL_CD_WIN = 10101;		// �ܽ½�
	public static final int POOL_CD_PLC = 10102;		// ���½�
	public static final int POOL_CD_QU = 10103;			// ���½�
	public static final int POOL_CD_EX = 10104;			// �ֽ½�
	public static final int POOL_CD_TRA = 10105;		// �ﺹ��
	public static final int POOL_CD_DD = 10106;			// �߽½�
	
	/*
	 * -> �αױ�� �Լ��� ����� ��� SDL_Common.debug(String message) ������ �Լ� ���ٴ� 
	 * SDL_Common.logger.debug(String message) ���·� ����� ���� ������.
	 * -> SDL_Common.debug(String message)�� ���·� ����� ��� �αױ���� �õ��� ������ ���� ��ġ��
	 * SDL_Common.debug()�Լ��� ������ġ�� ǥ�Ⱑ �Ǳ� ������ �̸� �ذ��ϱ� ���� SDL_Common.logger.debug()
	 * �������� ����Ͽ��� ��
	 */
	
	/**
	 * Log4J�� �̿��Ͽ� Debug ������ �α׸� ����� (��� �������)
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		SDL_Common.logger.debug(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Info ������ �α׸� ����� (��� �������)
	 * 
	 * @param message
	 */
	public static void info(String message) {
		SDL_Common.logger.info(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Warn ������ �α׸� ����� (��� �������)
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		SDL_Common.logger.warn(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Error ������ �α׸� ����� (��� �������)
	 * 
	 * @param message
	 */
	public static void error(String message) {
		SDL_Common.logger.error(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Fatal ������ �α׸� ����� (��� �������)
	 * 
	 * @param message
	 */
	public static void fatal(String message) {
		SDL_Common.logger.fatal(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Serial Device�� ���� �Էµ� �����͸� Debug ������ �����
	 * 
	 * @param message
	 */
	public static void dataDebug(String message) {
		SDL_Common.dataLogger.debug(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Serial Device�� ���� �Էµ� �����͸� Info ������ �����
	 * 
	 * @param message
	 */
	public static void dataInfo(String message) {
		SDL_Common.dataLogger.info(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Serial Device�� ���� �Էµ� �����͸� Warn ������ �����
	 * 
	 * @param message
	 */
	public static void dataWarn(String message) {
		SDL_Common.dataLogger.warn(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Serial Device�� ���� �Էµ� �����͸� Error ������ �����
	 * 
	 * @param message
	 */
	public static void dataError(String message) {
		SDL_Common.dataLogger.error(message);
	}
	
	/**
	 * Log4J�� �̿��Ͽ� Serial Device�� ���� �Էµ� �����͸� Fatal ������ �����
	 * 
	 * @param message
	 */
	public static void dataFatal(String message) {
		SDL_Common.dataLogger.fatal(message);
	}

	/**
	 * packet���� pos ��ġ ���� len�� ���̸�ŭ �����Ͽ� String ���·� ��ȯ��
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
	 * packet���� pos ��ġ ���� len�� ���̸�ŭ �����Ͽ� Int ���·� ��ȯ��
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
	 * packet���� ���� len�� ���̸�ŭ �����Ͽ� byte[] ���·� ��ȯ��
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
	 * src�� null �� ��� defaultSrc�� ��ȯ�ϸ� �׷��� ���� ��� src�� ��ȯ��
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
	 * "MMDDYYYY"������ ���� ���ڿ��� "YYYYMMDD" ���·� ��ȯ�Ͽ� String���·� ��ȯ��
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
	 * poolCD�� ������ �½� �������ڿ��� �ش� �ڵ��ȣ�� ��ȯ�Ͽ� String ���·� ��ȯ�� 
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
	 * poolCDCode�� ������ �½� �ڵ��ȣ�� �ش� �½� �������ڿ��� ��ȯ�Ͽ� String���·� ��ȯ��
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
