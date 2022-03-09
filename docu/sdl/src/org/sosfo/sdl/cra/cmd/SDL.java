package org.sosfo.sdl.cra.cmd;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.exception.PropNotFoundException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.log.SDLLOG;
import org.sosfo.framework.tray.NewTray;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.util.StringUtil;
import org.sosfo.sdl.cra.common.CodeManager;
import org.sosfo.sdl.cra.gate.RaceInfoGATE;

/**
 * <pre>
 * 1. ���� Header ���� �Ľ�
 * 2. �ش����� ���� ���� ����
 * </pre>
 * 
 * @author yunkidon@hotmail.com
 */
public class SDL {

	/**
	 * Properties ��ü
	 */
	protected Config	sdl_conf		= null;			// ȯ�漳�� ����

	/**
	 * Properties ��ü
	 */
	protected Config	format_conf		= null;			// ���� ���� config

	/**
	 * ���� sdl
	 */
	private String		strSDL			= null;

	/**
	 * ���� ����
	 */
	private String		default_meet_cd	= null;

	/**
	 * �������� ����, ���� Ŭ�������� �������� ���� message code, meet name, perf number.....
	 */
	protected Tray		sdlTray			= new NewTray();

	/*
	 * This is Constructor.
	 */
	public SDL(String sdl) throws AppException {
		this.strSDL = sdl;

		try { 
			sdl_conf = ConfigFactory.getInstance().getConfiguration("sdl.properties");
			default_meet_cd = sdl_conf.getString("sdl.default.meet_cd");

			format_conf = ConfigFactory.getInstance().getConfiguration("format.properties");

			parseHeaderSDL();// ���� header ����
			/*
			if("DT".equals(sdl.substring(0, 2))) {
				Date today = new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
				setTray("race_day", date.format(today));
			} else {
				loadRaceInfo();// �ش� �������� ���� ����
			}
			*/
			loadRaceInfo();// �ش� �������� ���� ����
			
			// SDL Logging
			SDLLOG.info(getTray().getString("race_day"), getTray().getString("race_no"), getTray().getString("msg_cd"), getTray().getString("sdl"));

			Log.debug("", this, "����Ŭ���� �Ľ� ����" + this.getTray());
		} catch (PropNotFoundException e) {
			Log.error("ERROR", this, "at SDL Constructor : \nsdl : Couldn't find [format.properties] \n" + e);
			throw new AppException("", e);
		} catch (AppException app) {
			throw app;			
		} catch (Exception e) {
			Log.error("ERROR", this, "at SDL Constructor : \nsdl : " + sdl + "\n" + e);
			throw new AppException("", e);
		}
	}

	/**
	 * �Էµ� SDL�� �Ľ��Ͽ� ���� ������ �����Ѵ�.
	 * 
	 * @param sdl TCP/IP, RS-232C, Multicast�� ���� ���� ���ڿ�
	 * @throws AppException
	 */
	private void parseHeaderSDL() throws Exception {
		/**
		 * # �������� �÷� sdl.header.columns = msg_cd,race_type,meet_nm,perf_no,perf_type,race_no,msg_length sdl.header.columns.size = 2,3,27,4,4,2,4
		 */
		String columnArr[] = format_conf.getStringArray("sdl.header.columns");
		String sizeArr[] = format_conf.getStringArray("sdl.header.columns.size");
		int arrCnt = 0;

		if (columnArr.length == sizeArr.length) {
			arrCnt = columnArr.length;
		} else {
			throw new PropNotFoundException("properties �б� ����");
		}

		try {
			int idx = 0;
			for (int i = 0; i < arrCnt; i++) {
				// setTray(columnArr[i], strSDL.substring(idx, idx += Integer.parseInt(sizeArr[i])));
				setTray(columnArr[i], StringUtil.substring(strSDL, idx, idx += StringUtil.parseInt(sizeArr[i], 0)));
			}

			Tray tempTray = getTray();
			tempTray.setString("sdl", this.getSDL()); // ���� ����

			// meet_cd ����			
			tempTray.setString("meet_cd", CodeManager.getMeetCD( StringUtil.rplc( tempTray.getString("meet_nm"),  " ",  "" ) ) );
			
			// TEST �� ����Ÿ ���� ����ó��
			/*
			if( !(	"REAL".equals(tempTray.getString("perf_type"))|| "ITSP".equals(tempTray.getString("perf_type"))	) ){
				throw new AppException("sdl.cra.status.not_found", new Exception("perf_type�� REAL OR ITSP �� �ƴմϴ�. ������ Ÿ���� : "+tempTray.getString("perf_type")));
			}
			*/
			
			

		} catch (Exception e) {
			Log.error("ERROR", this, e);
			throw new AppException("sdl.cra.header.parsing", e);
		}
	}

	
	/**
	 * �ش� ���� ���� ����
	 * 
	 * @throws Exception
	 */
	public void loadRaceInfo() throws Exception {

		RaceInfoGATE gate = new RaceInfoGATE(sdlTray);

		try {
			
			Tray raceTray = gate.findByPrimaryKey(sdlTray);

			setTray("stnd_year", raceTray.getString("stnd_year"));
			setTray("tms", raceTray.getString("tms"));
			setTray("day_ord", raceTray.getString("day_ord"));
			setTray("race_day", raceTray.getString("race_day"));
			setTray("race_yoil", raceTray.getString("race_yoil"));
			
		} catch (AppException app) {
			throw app;
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * ���������� �����Ѵ�.
	 * 
	 * @param key
	 * @param value
	 * @throws AppException
	 */
	protected void setTray(String key, String value) throws AppException {
		sdlTray.set(key, value);
	}

	/**
	 * �Ľ̵� ������ Tray�� ��� ���´�.
	 * 
	 * @return
	 * @throws AppException
	 */
	protected Tray getTray() {
		return sdlTray;
	}

	protected String getSDL() {
		return strSDL;
	}

	/**
	 * ����� ������ ó���Ѵ�.
	 * 
	 * @return status ���࿩��
	 * @throws AppException
	 */
	protected boolean doProcess() throws AppException {
		throw new AppException("���� Ŭ�������� ���� �ؾ��մϴ�.");
	}

}
