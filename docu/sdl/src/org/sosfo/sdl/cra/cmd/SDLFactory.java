package org.sosfo.sdl.cra.cmd;

import java.lang.reflect.Constructor;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import java.lang.reflect.Constructor;

/**
 * SDL TYPE 구분, 처리
 * 
 * @author yunkidon@sosfo.or.kr
 * 
 * <pre>
 * 
 * 1.  Win Odds             
 * 2.  WPS Runner Totals     [WR]
 * 3.  WPS Probables         [SP]
 * 4.  2Leg Runner Totals    [FR]
 * 5.  2Leg Probables        [PB]
 * 6.  TLA Runner Totals     [TR]
 * 7.  Dbl Willpays         
 * 8.  Pk3 Willpays         
 * 9.  Finishers             [FN]
 * 10.  Prices                [RS]
 * 11.  Time Info             [TM]
 * 12.  7 MTP Chimes         
 * 13.  Card Control          [CC]
 * 14.  DIV Totals            [DT]
 * 15.  TRI Probs            
 * 16.  TLA Probs             [TP]
 * 17.  OMN Probs            
 * 18.  Running Order         [RO]
 * 19.  Pool Totals           [PT]
 * 20.  Race Betting Info     [BI]
 * </pre>
 */
public class SDLFactory {
	/**
	 * Properties 객체
	 */
	private static SDLFactory	instance	= null;

	private SDLFactory() {	}

	/**
	 * Double Check Lock 구현
	 * 
	 * @return PoolManager
	 */
	public static SDLFactory getInstance() {
		if (instance == null) {
			synchronized (SDLFactory.class) {
				if (instance == null) {
					instance = new SDLFactory();
				}
			}
		}
		return instance;
	}

	/**
	 * 공통정보와 세부정보를 파싱하고 타입별로 처리
	 * 
	 * @param sdl
	 * @return ture or false
	 * @throws Exception
	 */
	public boolean doProcess(String sdl) throws AppException {
		SDL instance = null;
		boolean success = false;
		try {
			instance = getBusiness(sdl);
			success = instance.doProcess();
			return success;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("ERROR", this, e);
			throw new AppException(e.getMessage());
		}
	}

	/**
	 * 각 Business별 하위 클래스를 생성한다.
	 * 
	 * @param sdl
	 * @return SDL Interface
	 */
	private SDL getBusiness(String sdl) throws Exception {
		String business = null;

		try {
			
			//서비스하는 클래스만 생성하도록 수정요!!!
			business = "org.sosfo.sdl.cra.cmd." + sdl.substring(0, 2);
			Log.debug("", this, business + " 클래스 생성");
			Log.debug("", this, business + " 클래스 생성 base");
			Class businessClass = Class.forName(business);
			Class[] paramTypes = new Class[]{String.class};
			Constructor con = businessClass.getConstructor(paramTypes);
			Object[] initArgs = new Object[]{sdl};
			Object businessInstance = con.newInstance(initArgs);

			return (SDL) businessInstance;

		} catch (ClassNotFoundException e) {
			Log.error("ERROR", this, business + "클래스가 존재하지 않습니다.");
			Log.error("ERROR", this, sdl);
			Log.error("ERROR", this, "at SDLFactory.getBusiness - " + e);
			throw e;
		} catch (Exception e) {
			Log.error("ERROR", this, sdl);
			Log.error("ERROR", this, "at SDLFactory.getBusiness - " + e);
			throw e;
		}
	}

	public static void main(String args[]) {
		/*
				for (int i = 0; i < 10; i++) {
					SDLFactory test = SDLFactory.getInstance();
					try {
						test.doProcess("this is test strings..... > " + i);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
		*/
		// String sdl = "SPCRA - GWANGMYEONG 0991TEST030110WINO071111122222333334444455555666667777700000000000000PLCO07777776666655555444443333322222111110000000000000006159";
		//String sdl = "WRCRA - GWANGMYEONG             0991TEST030180WINO07111111111122222222223333333333444444444455555555556666666666777777777700000000000000PLCO0777777777776666666666555555555544444444443333333333222222222211111111110000000000000006159";
		// String sdl = "FRCRA - GWANGMYEONG 0991TEST020538EX
		// O070107..........11111111112222222222333333333344444444445555555555666666666602071111111111..........22222222223333333333444444444455555555556666666666030711111111112222222222..........33333333334444444444555555555566666666660407111111111122222222223333333333..........44444444445555555555666666666605071111111111222222222233333333334444444444..........55555555556666666666060711111111112222222222333333333344444444445555555555..........66666666660707111111111122222222223333333333444444444455555555556666666666..........0000000000000025844";
		String sdl = "TMCRA - GWANGMYEONG             0991TEST020022122720084001024028002701141";
		try {
			SDLFactory test = SDLFactory.getInstance();
			test.doProcess(sdl);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
