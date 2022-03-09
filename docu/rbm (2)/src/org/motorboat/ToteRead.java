package org.motorboat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.motorboat.common.PacketM;
import org.motorboat.common.SDL_Common;
import org.motorboat.common.SDLException;
import org.motorboat.db.DbMNG;
import org.motorboat.dto.DatabaseDTO;
import org.motorboat.dto.MeetsDTO;
import org.motorboat.dto.SerialDeviceDTO;
import org.motorboat.dto.RaceDayOrdDTO;
import org.motorboat.mng.AB_MNG;
import org.motorboat.mng.DT_MNG;
import org.motorboat.mng.FN_MNG;
import org.motorboat.mng.MT_MNG;
import org.motorboat.mng.PB_MNG;
import org.motorboat.mng.PT_MNG;
import org.motorboat.mng.RS_MNG;
import org.motorboat.mng.SB_MNG;
import org.motorboat.mng.SDL_MNG;
import org.motorboat.mng.TM_MNG;
import org.motorboat.mng.TR_MNG;

/**
 * SDL 수신 프로그램의 main한수를 포함하고 있는 Class이며 초기화화 프로그램 구동을 담당함
 * 
 * @author 김원겸 대상정보기술
 *
 */
public class ToteRead implements SerialPortEventListener {

    private static CommPortIdentifier portId;

    private BufferedInputStream bufferedInputStream;

    private SerialPort serialPort;

    private StringBuffer sbBuffer = new StringBuffer();

    private DbMNG dbMNG;									// 데이터베이스와의 데이터 입출력 및 Connection을 총괄 관리하는 객체

    private String homeDirectory;

    private XMLOutputter output;

    private SerialDeviceDTO serialDeviceDTO;

    private DatabaseDTO databaseDTO;						// 데이터베이스연결과 관련된 설정정보를 관리하는 데이터 객체

    private MeetsDTO meetsDTO;								// 등록된 Meets 번호를 관리하는 데이터 객체  
    
    private RaceDayOrdDTO raceDayOrdDTO;
    
    private String absolutelyPath;
    
    private boolean isSerailDeviceMode = true;

    /**
	 * 프로그램을 구동하는 엔트리 포인트
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
		System.out.println("");
		ToteRead reader = new ToteRead();
		reader.lauchProgram();
    }

    /**
     * default constructor
     * 
     */
    public ToteRead() {
    	
    }
    
    private void lauchProgram() {
    	try {
	    	initConfigInfo();
	    	doProcess();
//	    	doTest();
//	    	doLab();
    	} catch(Exception ex) { 
    		SDL_Common.logger.error(ex.toString());
    	}
    }

    public boolean isSerailDeviceMode() {
    	return isSerailDeviceMode;
    }
    
    public RaceDayOrdDTO getRaceDayOrdDTO() {
    	return raceDayOrdDTO;
    }
    
    public void applyHomeDirectory(String homeDirectory) {
    	System.setProperty("user.dir", homeDirectory);
    }
    
    public void applyFileDataWithOption(String meetCode, String stnd_year, int tms, int day_ord, String absolutelyPath, 
    		boolean allRace, int raceNo, boolean isDT, boolean isFN, boolean isPB, boolean isPT, boolean isRS, boolean isTM, boolean isTR) {
    	
     	initConfigInfo();
    	
    	isSerailDeviceMode = false;
    	
    	raceDayOrdDTO = new RaceDayOrdDTO();
    	
    	raceDayOrdDTO.setIsAvailable(true);
    	raceDayOrdDTO.setStndYear(stnd_year);
    	raceDayOrdDTO.setTms(tms);
    	raceDayOrdDTO.setDayOrd(day_ord);
	    	
    	this.absolutelyPath = absolutelyPath;
	    	
    	RandomAccessFile file = null;
    	try {
    		file = new RandomAccessFile(absolutelyPath, "r");
    	} catch(Exception ex) { }
    	String lineString;
    	
    	byte[] b_stx = new byte[1];
    	byte[] b_etx = new byte[1];
    	
    	b_stx[0] = 02;
    	b_etx[0] = 03;
    	
    	String stx = new String(b_stx);
    	String etx = new String(b_etx);
    	
    	String bufferString = null;
    	
    	int lineCount = 0;
    	
    	if (allRace) {
    		int raceCount = dbMNG.getRaceCount(meetCode, stnd_year, tms, day_ord);

	    	if (isDT) {
	    		for (int i = 0; i < raceCount; i++) {
	    			dbMNG.initDT(meetCode, stnd_year, tms, day_ord, i);
	    		}
	    	}
	    	if (isPT) {
	    		for (int i = 0; i < raceCount; i++) {
	    			dbMNG.initPT(meetCode, stnd_year, tms, day_ord, i);
	    		}
	    	}
    	} else {
	    	if (isDT)
	    		dbMNG.initDT(meetCode, stnd_year, tms, day_ord, raceNo);
	    	
	    	if (isPT)
	    		dbMNG.initPT(meetCode, stnd_year, tms, day_ord, raceNo);
    	}
    	
    	try {
	    	while ((lineString = file.readLine()) != null) {
	    		lineCount++;
	    		
	    		if (lineString.length() < 40)
	    			continue;
	    		
	    		if (!lineString.startsWith(stx))
	    			lineString = stx + lineString;

	    		if (!lineString.endsWith(etx))
	    			lineString = lineString + etx;
	    		
	    		bufferString = lineString;
	    		
	    		int race_no = 0;
	    		if (!allRace) {
	    			race_no = SDL_Common.getPacketFractionToInt(lineString, 41, 2);
	    			if (raceNo != race_no)
	    				continue;
	    		}
	    		
	    		String dataType = SDL_Common.getPacketFraction(lineString, 1, 2);
	    		
	    		if (dataType.equals("DT")) {
	    			if (!isDT)
	    				continue;
	    		} else if (dataType.equals("FN")) {
	    			if (!isFN)
	    				continue;
	    		} else if (dataType.equals("PB")) {
	    			if (!isPB)
	    				continue;
	    		} else if (dataType.equals("PT")) {
	    			if (!isPT)
	    				continue;
	    		} else if (dataType.equals("RS")) {
	    			if (!isRS)
	    				continue;
	    		} else if (dataType.equals("TM")) {
	    			if (!isTM)
	    				continue;
	    		} else if (dataType.equals("TR")) {
	    			if (!isTR)
	    				continue;
	    		}

	    		new PacketUnitLauncher(lineString).run();
	    	}
	    	
	    	dbMNG.releaseConnection();
    	} catch(Exception ex) {
    		SDL_Common.logger.error("[" + lineCount + "] : [" + bufferString + "]");
    		SDL_Common.logger.error(ex.toString());
    	}
    }
    
    public void applyFileData(String stnd_year, int tms, int day_ord, String absolutelyPath) {
    	
    	initConfigInfo();
    	
    	isSerailDeviceMode = false;
    	
    	raceDayOrdDTO = new RaceDayOrdDTO();
    	
    	raceDayOrdDTO.setIsAvailable(true);
    	raceDayOrdDTO.setStndYear(stnd_year);
    	raceDayOrdDTO.setTms(tms);
    	raceDayOrdDTO.setDayOrd(day_ord);
	    	
    	this.absolutelyPath = absolutelyPath;
	    	
    	RandomAccessFile file = null;
    	try {
    		file = new RandomAccessFile(absolutelyPath, "r");
    	} catch(Exception ex) { }
    	String lineString;
    	
    	byte[] b_stx = new byte[1];
    	byte[] b_etx = new byte[1];
    	
    	b_stx[0] = 02;
    	b_etx[0] = 03;
    	
    	String stx = new String(b_stx);
    	String etx = new String(b_etx);
    	
    	String bufferString = null;
    	
    	int lineCount = 0;
    	
		int raceCount = dbMNG.getRaceCount("001", stnd_year, tms, day_ord);

   		for (int i = 0; i < raceCount; i++) {
   			dbMNG.initDT("001", stnd_year, tms, day_ord, i);
   		}

   		for (int i = 0; i < raceCount; i++) {
   			dbMNG.initPT("001", stnd_year, tms, day_ord, i);
   		}

   		try {
	    	while ((lineString = file.readLine()) != null) {
	    		lineCount++;
	    		
	    		if (lineString.length() < 40)
	    			continue;
	    		
	    		if (!lineString.startsWith(stx))
	    			lineString = stx + lineString;

	    		if (!lineString.endsWith(etx))
	    			lineString = lineString + etx;
	    		
	    		bufferString = lineString;
	    		
	    		new PacketUnitLauncher(lineString).run();
	    	}
	    	dbMNG.releaseConnection();
    	} catch(Exception ex) {
    		SDL_Common.logger.error("[" + lineCount + "] : [" + bufferString + "]");
    		SDL_Common.logger.error(ex.toString());
    	}
    }
    
    public void applyFileData(String meet_code, String stnd_year, int tms, int day_ord, String absolutelyPath) {
    	
    	initConfigInfo();
    	
    	isSerailDeviceMode = false;
    	
    	raceDayOrdDTO = new RaceDayOrdDTO();
    	
    	raceDayOrdDTO.setIsAvailable(true);
    	raceDayOrdDTO.setStndYear(stnd_year);
    	raceDayOrdDTO.setTms(tms);
    	raceDayOrdDTO.setDayOrd(day_ord);
	    	
    	this.absolutelyPath = absolutelyPath;
	    	
    	RandomAccessFile file = null;
    	try {
    		file = new RandomAccessFile(absolutelyPath, "r");
    	} catch(Exception ex) { }
    	String lineString;
    	
    	byte[] b_stx = new byte[1];
    	byte[] b_etx = new byte[1];
    	
    	b_stx[0] = 02;
    	b_etx[0] = 03;
    	
    	String stx = new String(b_stx);
    	String etx = new String(b_etx);
    	
    	String bufferString = null;
    	
    	int lineCount = 0;
		int raceCount = dbMNG.getRaceCount(meet_code, stnd_year, tms, day_ord);

   		for (int i = 0; i < raceCount; i++) {
   			dbMNG.initDT(meet_code, stnd_year, tms, day_ord, i);
   		}

   		for (int i = 0; i < raceCount; i++) {
   			dbMNG.initPT(meet_code, stnd_year, tms, day_ord, i);
   		}

   		try {
	    	while ((lineString = file.readLine()) != null) {
	    		lineCount++;
	    		
	    		if (lineString.length() < 40)
	    			continue;
	    		
	    		if (!lineString.startsWith(stx))
	    			lineString = stx + lineString;

	    		if (!lineString.endsWith(etx))
	    			lineString = lineString + etx;
	    		
	    		bufferString = lineString;
	    		
	    		new PacketUnitLauncher(lineString).run();
	    	}
	    	dbMNG.releaseConnection();
    	} catch(Exception ex) {
    		SDL_Common.logger.error("[" + lineCount + "] : [" + bufferString + "]");
    		SDL_Common.logger.error(ex.toString());
    	}
    }

    /**
     * Serial Device명을 이용하여 가용한 Device이면 Communication Port Management 객체인
     * portID를 지정한다.
     * 
     * @return Serial Device가 사용 가능하면 true, 사용 불가능한 Device인 경우 false를 반환한다.
     */
    private boolean checkSerialDevice() {
		String serialPortName = null;
		try {
		    serialPortName = serialDeviceDTO.getDevice();
		    portId = CommPortIdentifier.getPortIdentifier(serialPortName);
		} catch (Exception ex) {
		    SDL_Common.logger.error(ex.toString());
		}
	
		if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
		    if (portId.getName().equals(serialPortName)) {
			return true;
		    }
		}
	
		SDL_Common.logger.warn("[" + serialPortName + "] not found..");
	
		return false;
    }

    /**
     * portID를 이용하여 전역변수인 serialPort를 객체화 하며 serialPort 객체의 옵션을 설정한다. Serial
     * Port로 데이터가 수신되었을 경우 발생되는 Event를 인식할 수 있도록 SerialPortEventListener
     * interface를 구체화한 this를 Listener에 등록한다.
     */
    private void doProcess() {
		if (!checkSerialDevice())
		    return;
		try {
		    serialPort = (SerialPort) portId.open("ToteReadApp", 2000);
		} catch (PortInUseException ex) {
		    SDL_Common.logger.error(ex.toString());
		}
	
		try {
	
		    bufferedInputStream = new BufferedInputStream(serialPort.getInputStream());
	
		} catch (IOException ex) {
		    SDL_Common.logger.error(ex.toString());
		}
	
		try {
		    serialPort.addEventListener(this);
		} catch (TooManyListenersException ex) {
		    SDL_Common.logger.error(ex.toString());
		}
	
		serialPort.notifyOnDataAvailable(true);
	
		try {
		    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException ex) {
		    SDL_Common.logger.error(ex.toString());
		}
    }

    /**
     * XPath Query의 결과가 Element 객체의 형태로 포함되어 있는 List객체를 각각의 Element로 분할하여
     * 로그파일에 출력한다.
     * 
     * @param elementList
     */
    private void showXPathElementResult(List elementList) {

		Iterator i = elementList.iterator();
		SDL_Common.logger.info("----------------------------");
		while (i.hasNext()) {
		    Element element = (Element) i.next();
		    try {
	
			String outString = output.outputString(element);
			SDL_Common.logger.info(outString);
		    } catch (Exception ex) {
		    }
		    SDL_Common.logger.info("");
		    SDL_Common.logger.info("----------------------------");
		}
	}
	
    /**
	 * 지정된 Element의 Text Contents를 반환한다.
	 * 
	 * @param element 조회하고자 하는 element 객체
	 * @return element의 Text Contents
	 */
	private String getElementText(Element element) {
		return element.getText();
    }

	/**
	 * Serial로 부터 데이터가 수신되었을 경우 SerialPortEvent가 발생하며 이때 호출되는 
	 * 인터페이스이다.
	 * 
	 * @param event 발생한 event 객체
	 */
    public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
		    break;
		case SerialPortEvent.DATA_AVAILABLE:
		    try {
	
			byte[] readBuffer = new byte[bufferedInputStream.available()];
	
			if (bufferedInputStream.read(readBuffer) > 0) {
			    stringOutput(new String(readBuffer));
			    // System.out.println(new String(readBuffer));
			}
		    } catch (Exception e) {
		    }
		    break;
		}
    }

    /**
	 * 파라미터로 지정된 문자열에서 '0x02'로 시작되며 '0x03'으로 끝나는 구간 문자열을 도출하여 Data Log 파일에
	 * 기록하며 Thread 클래스를 상속받은 PacketUnitLauncher의 생성자에 도출된 문자열을 지정하여 run()함수를 호출한다.
	 * 
	 * @param beadang
	 */
    public void stringOutput(String beadang) {

		for (int i = 0; i < beadang.length(); i++) {
		    if (beadang.charAt(i) == 0x02) { // STX
				sbBuffer = new StringBuffer();
				sbBuffer.append(beadang.charAt(i));
		    } else if (beadang.charAt(i) == 0x03) { // ETX
				sbBuffer.append(beadang.charAt(i));
				// SDL_Common.dataLogger.info(new String(sbBuffer));
				SDL_Common.dataLogger.info(new String(sbBuffer));
				new PacketUnitLauncher(new String(sbBuffer)).start();
				// separ(new String(sbBuffer));
		    } else if (beadang.charAt(i) == 0x00) { // null 체크
		    } else {
		    	sbBuffer.append(beadang.charAt(i));
		    }
		}
    }

    /**
	 * 데이터의 종류를 파악하여 해당 프로세스를 구동하는 함수이며 현재 테스트용으로만 사용되고 있다.
	 * 
	 * @param token Serial Device로 부터 입력된 데이
	 */
    public void separ(String token) {
		SDL_MNG sdl_MNG = null;
	
		// SDL_Common.dataLogger.info(token);
	
		if (SDL_Common.getPacketFraction(token, 1, 2).equals("TM")) {
		    sdl_MNG = new TM_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("PT")) {
		    sdl_MNG = new PT_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("PB")) {
		    sdl_MNG = new PB_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("TR")) {
		    sdl_MNG = new TR_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("FN")) {
		    sdl_MNG = new FN_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("RS")) {
		    sdl_MNG = new RS_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("AB")) {
		    sdl_MNG = new AB_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("SB")) {
		    sdl_MNG = new SB_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("MT")) {
		    sdl_MNG = new MT_MNG(dbMNG, meetsDTO, token);
		} else if (SDL_Common.getPacketFraction(token, 1, 2).equals("DT")) {
		    sdl_MNG = new DT_MNG(dbMNG, meetsDTO, token);
		} else {
		    SDL_Common.logger.warn("Not Applicable");
		}
		if (!sdl_MNG.isAvailableMNG()) {
		    SDL_Common.logger.warn("Not Applicable");
		    return;
		}
	
		sdl_MNG.parsePacket();
		sdl_MNG.doProcess();
    }

    /**
	 * Serial Device로 부터 입력된 데이터를 데이터 파일에 저장하는 함수이며 Log4J를 대체함으로써 
	 * 사용되지 않는 함수이다.
	 *
	 */
    public void fileOutput() {
		FileWriter fout = null;
		try {
		    // System.out.println(" LogWrite :");
		    // fout = new FileWriter("/testdb/SDL.txt", true);
	
		    StringBuffer fileNameBuffer = new StringBuffer();
		    fileNameBuffer.append(homeDirectory);
	
		    String lineSeparator = System.getProperty("line.separator");
	
		    char c = homeDirectory.charAt(homeDirectory.length() - 1);
		    if ((c != 0x5c) && (c != 0x2f))
			fileNameBuffer.append('/');
	
		    fileNameBuffer.append("data/");
	
		    File dataDirectory = new File(new String(fileNameBuffer));
		    if (!dataDirectory.exists()) {
			dataDirectory.mkdirs();
		    }
	
		    fileNameBuffer.append("SDL_");
	
		    Calendar rightNow = Calendar.getInstance();
		    int year = rightNow.get(Calendar.YEAR);
		    int month = rightNow.get(Calendar.MONTH) + 1;
		    int date = rightNow.get(Calendar.DATE);
	
		    fileNameBuffer.append(year);
		    if (month < 10)
			fileNameBuffer.append('0');
		    fileNameBuffer.append(month);
		    if (date < 10)
			fileNameBuffer.append('0');
		    fileNameBuffer.append(date);
	
		    fileNameBuffer.append(".txt");
	
		    fout = new FileWriter(new String(fileNameBuffer), true);
		    fout.write(new String(sbBuffer).trim() + lineSeparator);
		    fout.flush();
		    fout.close();
	
		} catch (IOException e) {
		    SDL_Common.logger.error(" LogWrite Error : " + e.getMessage());
		}
    }

    
    /**
	 * 설정파일(config.xml)의 내용을 XPath를 이용하여 읽어들여 관련변수를 설정한다.
	 *
	 */
    private void initConfigInfo() {
		Document doc = null;
		XPath configXPath = null;
	
		homeDirectory = System.getProperty("home");
		if (homeDirectory == null) {
		    homeDirectory = System.getProperty("user.dir");
		}
	
		String envFileName = homeDirectory + "/config/config.xml";
		File envFile = new File(envFileName);
	
		SAXBuilder builder = new SAXBuilder(false);
		output = new XMLOutputter();
		output.setFormat(Format.getPrettyFormat().setEncoding("euc-kr"));
	
		try {
		    doc = builder.build(envFile);
		} catch (Exception ex) {
		    SDL_Common.logger.error(ex.toString());
		}
	
		Element root = doc.getRootElement();
	
		/**
         * Serial Device 정보 초기화
         */
		try {
		    serialDeviceDTO = new SerialDeviceDTO();
	
		    configXPath = XPath.newInstance("/motorboat/serial_port/device");
		    List elementList = configXPath.selectNodes(doc);
		    Iterator i = elementList.iterator();
		    while (i.hasNext()) {
				Element element = (Element) i.next();
				serialDeviceDTO.setDevice(element.getText());
		    }
		} catch (Exception ex) {
		}
	
		/**
         * Database 정보 초기화
         */
		try {
		    databaseDTO = new DatabaseDTO();
	
		    configXPath = XPath.newInstance("/motorboat/database");
		    List elementList = configXPath.selectNodes(doc);
		    Iterator i = elementList.iterator();
		    while (i.hasNext()) {
				Element element = (Element) i.next();
				databaseDTO.setIp(element.getChildText("ip"));
				int port = Integer.parseInt(element.getChildText("port"));
				databaseDTO.setPort(port);
				databaseDTO.setSid(element.getChildText("sid"));
				databaseDTO.setUser(element.getChildText("user"));
				databaseDTO.setPassword(element.getChildText("password"));
				databaseDTO.setJdbc_driver(element.getChildText("jdbc_driver"));
				databaseDTO.setDriver_type(element.getChildText("driver_type"));
		    }
		    
		    /**
		     * MNG 설정 초기화
		     */
		    dbMNG = new DbMNG(this, databaseDTO);
	
		} catch (Exception ex) {
		    SDL_Common.logger.error(ex.toString());
		}
	
		/**
         * 시행처(Meet) 정보 초기화
         */
		try {
		    meetsDTO = new MeetsDTO();
	
		    configXPath = XPath.newInstance("/motorboat/meets/meet");
		    List elementList = configXPath.selectNodes(doc);
		    Iterator i = elementList.iterator();
		    while (i.hasNext()) {
				Element element = (Element) i.next();
				meetsDTO.setMeet(element.getChildText("number"), element.getChildText("name"));
		    }
		} catch (Exception ex) {
		}
    }    
    
    
    /**
	 * 프로그램 개발과정에서 임의로 데이터를 입력하여 테스트를 수행하기 위해 사용된 함수이며
	 * 실제 서비스에서는 사용되지 않다.
	 * 
	 * @param packet 임의로 지정된 데이터 패킷
	 */
    private void processLabPacket(String packet) {
		separ(packet);
    }

    /**
	 * 프로그램 개발과정에서 간단한 테스트를 수행하기 위해 활용된 함수이며 실제 서비스에서는
	 * 사용되지 않다.
	 *
	 */
    private void lab_001() {
		try {
		    byte[] bb;
		    byte[] cc = new String("ABCDEFG").getBytes();
	
		    PacketM packetM = new PacketM(cc);
		    int cPos = packetM.getCurrentPos();
		    SDL_Common.logger.info("cPos = [" + cPos + "]");
	
		    packetM.seekPos(4);
		    cPos = packetM.getCurrentPos();
		    SDL_Common.logger.info("cPos = [" + cPos + "]");
	
		    packetM.resetPos();
		    cPos = packetM.getCurrentPos();
		    SDL_Common.logger.info("cPos = [" + cPos + "]");
	
		    bb = packetM.splitPacket(2);
		    cPos = packetM.getCurrentPos();
		    SDL_Common.logger.info("[" + new String(bb) + "][" + cPos + "]");
	
		    bb = packetM.splitPacket(3);
		    cPos = packetM.getCurrentPos();
		    SDL_Common.logger.info("[" + new String(bb) + "][" + cPos + "]");
	
		    bb = packetM.splitPacket(3);
		    cPos = packetM.getCurrentPos();
		    SDL_Common.logger.info("[" + new String(bb) + "][" + cPos + "]");
		} catch (Exception ex) {
		    SDL_Common.logger.info(ex.toString());
		}
    }    
    
    
    /**
     * 간단한 테스트를 수행하기 위한 함수이며 필요에 따라 테스트할 코드를 구현하여 ToteRead 생성자에서 호출할 수 있도록
     * 한다.
     * 
     */
    private void doLab() {
		try {
		    lab_001();
		} catch (Exception ex) {
		    SDL_Common.logger.error(ex.toString());
		}
    }

    /**
     * Serial Device를 통한 데이터 수신이 어려운 경우 직접 입력 데이터를 하드코딩하여 테스트할 수 있으며
     * ToteRead 생성자에서 호출할 수 있도록 한다.
     * 
     */
    private void doTest() {
		try {
		    String packet = "FNMRA                           0123REAL200022030101020201030301040001077";
		    processLabPacket(packet);
	
		} catch (Exception ex) {
		    SDL_Common.logger.error(ex.toString());
		}
    }

    
    /**
	 * Serial Device로 부터 데이터가 입력되었을 경우 이를 Thread를 이용하여 처리할 수 있도록
	 * 구현된 Class이며 데이터 하나당 PacketUnitLauncher가 하나 생성이 되어 Thread 방식으로
	 * 데이터베이스에 입력이 되도록 구성되었다.
	 * 
	 * @author 김원겸
	 *
	 */
    class PacketUnitLauncher extends Thread {

		private String packet = null;
	
		public PacketUnitLauncher(String packet) {
		    this.packet = packet;
		}
	
		/**
		 * PacketUnitLauncher.packet에 설정된 데이터의 종류를 구분하여 해당 프로세스를 호출한다.
		 */
		public void run() {
		    SDL_MNG sdl_MNG = null;
	
		    // SDL_Common.dataLogger.info(packet);
	
		    if (SDL_Common.getPacketFraction(packet, 1, 2).equals("TM")) {
		    	sdl_MNG = new TM_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("PT")) {
		    	sdl_MNG = new PT_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("PB")) {
		    	sdl_MNG = new PB_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("TR")) {
		    	sdl_MNG = new TR_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("FN")) {
		    	sdl_MNG = new FN_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("RS")) {
		    	sdl_MNG = new RS_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("AB")) {
		    	sdl_MNG = new AB_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("SB")) {
		    	sdl_MNG = new SB_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("MT")) {
		    	sdl_MNG = new MT_MNG(dbMNG, meetsDTO, packet);
		    } else if (SDL_Common.getPacketFraction(packet, 1, 2).equals("DT")) {
		    	sdl_MNG = new DT_MNG(dbMNG, meetsDTO, packet);
		    } else {
		    	SDL_Common.logger.warn("Not Applicable");
		    }
		    if (!sdl_MNG.isAvailableMNG()) {
		    	return;
		    }
	
		    /*
			 * 데이터를 분석하여 데이터 객체의 항목별로 저장이 되도록 함
			 */
		    sdl_MNG.parsePacket();
		    /*
			 * 데이터객체의 항목별로 저장된 데이터를 데이터베이스에 저장이 되도록 프로세싱함
			 */
		    sdl_MNG.doProcess();
		}
    }
}
