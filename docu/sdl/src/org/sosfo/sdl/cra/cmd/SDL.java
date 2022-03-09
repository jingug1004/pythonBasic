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
 * 1. 공통 Header 정보 파싱
 * 2. 해당일의 경주 정보 맵핑
 * </pre>
 * 
 * @author yunkidon@hotmail.com
 */
public class SDL {

	/**
	 * Properties 객체
	 */
	protected Config	sdl_conf		= null;			// 환경설정 파일

	/**
	 * Properties 객체
	 */
	protected Config	format_conf		= null;			// 전문 포멧 config

	/**
	 * 원본 sdl
	 */
	private String		strSDL			= null;

	/**
	 * 경주 종류
	 */
	private String		default_meet_cd	= null;

	/**
	 * 공통정보 설정, 하위 클래스에서 세부정보 설정 message code, meet name, perf number.....
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

			parseHeaderSDL();// 공통 header 정보
			/*
			if("DT".equals(sdl.substring(0, 2))) {
				Date today = new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
				setTray("race_day", date.format(today));
			} else {
				loadRaceInfo();// 해당 경주일의 경주 정보
			}
			*/
			loadRaceInfo();// 해당 경주일의 경주 정보
			
			// SDL Logging
			SDLLOG.info(getTray().getString("race_day"), getTray().getString("race_no"), getTray().getString("msg_cd"), getTray().getString("sdl"));

			Log.debug("", this, "상위클래스 파싱 정보" + this.getTray());
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
	 * 입력된 SDL을 파싱하여 공통 정보를 추출한다.
	 * 
	 * @param sdl TCP/IP, RS-232C, Multicast를 통해 받은 문자열
	 * @throws AppException
	 */
	private void parseHeaderSDL() throws Exception {
		/**
		 * # 공통정보 컬럼 sdl.header.columns = msg_cd,race_type,meet_nm,perf_no,perf_type,race_no,msg_length sdl.header.columns.size = 2,3,27,4,4,2,4
		 */
		String columnArr[] = format_conf.getStringArray("sdl.header.columns");
		String sizeArr[] = format_conf.getStringArray("sdl.header.columns.size");
		int arrCnt = 0;

		if (columnArr.length == sizeArr.length) {
			arrCnt = columnArr.length;
		} else {
			throw new PropNotFoundException("properties 읽기 실패");
		}

		try {
			int idx = 0;
			for (int i = 0; i < arrCnt; i++) {
				// setTray(columnArr[i], strSDL.substring(idx, idx += Integer.parseInt(sizeArr[i])));
				setTray(columnArr[i], StringUtil.substring(strSDL, idx, idx += StringUtil.parseInt(sizeArr[i], 0)));
			}

			Tray tempTray = getTray();
			tempTray.setString("sdl", this.getSDL()); // 원본 저장

			// meet_cd 셋팅			
			tempTray.setString("meet_cd", CodeManager.getMeetCD( StringUtil.rplc( tempTray.getString("meet_nm"),  " ",  "" ) ) );
			
			// TEST 시 데이타 적용 예외처리
			/*
			if( !(	"REAL".equals(tempTray.getString("perf_type"))|| "ITSP".equals(tempTray.getString("perf_type"))	) ){
				throw new AppException("sdl.cra.status.not_found", new Exception("perf_type이 REAL OR ITSP 이 아닙니다. 현재의 타입은 : "+tempTray.getString("perf_type")));
			}
			*/
			
			

		} catch (Exception e) {
			Log.error("ERROR", this, e);
			throw new AppException("sdl.cra.header.parsing", e);
		}
	}

	
	/**
	 * 해당 경주 정보 설정
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
	 * 공통정보를 설정한다.
	 * 
	 * @param key
	 * @param value
	 * @throws AppException
	 */
	protected void setTray(String key, String value) throws AppException {
		sdlTray.set(key, value);
	}

	/**
	 * 파싱된 정보를 Tray에 담아 놓는다.
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
	 * 추출된 정보를 처리한다.
	 * 
	 * @return status 수행여부
	 * @throws AppException
	 */
	protected boolean doProcess() throws AppException {
		throw new AppException("하위 클래스에서 구현 해야합니다.");
	}

}
