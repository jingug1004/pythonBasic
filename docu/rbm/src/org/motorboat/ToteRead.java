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
 * SDL ���� ���α׷��� main�Ѽ��� �����ϰ� �ִ� Class�̸� �ʱ�ȭȭ ���α׷� ������ �����
 * 
 * @author ����� ����������
 *
 */
public class ToteRead implements SerialPortEventListener {

    private static CommPortIdentifier portId;

    private BufferedInputStream bufferedInputStream;

    private SerialPort serialPort;

    private StringBuffer sbBuffer = new StringBuffer();

    private DbMNG dbMNG;									// �����ͺ��̽����� ������ ����� �� Connection�� �Ѱ� �����ϴ� ��ü

    private String homeDirectory;

    private XMLOutputter output;

    private SerialDeviceDTO serialDeviceDTO;

    private DatabaseDTO databaseDTO;						// �����ͺ��̽������ ���õ� ���������� �����ϴ� ������ ��ü

    private MeetsDTO meetsDTO;								// ��ϵ� Meets ��ȣ�� �����ϴ� ������ ��ü  
    
    private RaceDayOrdDTO raceDayOrdDTO;
    
    private String absolutelyPath;
    
    private boolean isSerailDeviceMode = true;

    /**
	 * ���α׷��� �����ϴ� ��Ʈ�� ����Ʈ
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
     * Serial Device���� �̿��Ͽ� ������ Device�̸� Communication Port Management ��ü��
     * portID�� �����Ѵ�.
     * 
     * @return Serial Device�� ��� �����ϸ� true, ��� �Ұ����� Device�� ��� false�� ��ȯ�Ѵ�.
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
     * portID�� �̿��Ͽ� ���������� serialPort�� ��üȭ �ϸ� serialPort ��ü�� �ɼ��� �����Ѵ�. Serial
     * Port�� �����Ͱ� ���ŵǾ��� ��� �߻��Ǵ� Event�� �ν��� �� �ֵ��� SerialPortEventListener
     * interface�� ��üȭ�� this�� Listener�� ����Ѵ�.
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
     * XPath Query�� ����� Element ��ü�� ���·� ���ԵǾ� �ִ� List��ü�� ������ Element�� �����Ͽ�
     * �α����Ͽ� ����Ѵ�.
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
	 * ������ Element�� Text Contents�� ��ȯ�Ѵ�.
	 * 
	 * @param element ��ȸ�ϰ��� �ϴ� element ��ü
	 * @return element�� Text Contents
	 */
	private String getElementText(Element element) {
		return element.getText();
    }

	/**
	 * Serial�� ���� �����Ͱ� ���ŵǾ��� ��� SerialPortEvent�� �߻��ϸ� �̶� ȣ��Ǵ� 
	 * �������̽��̴�.
	 * 
	 * @param event �߻��� event ��ü
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
	 * �Ķ���ͷ� ������ ���ڿ����� '0x02'�� ���۵Ǹ� '0x03'���� ������ ���� ���ڿ��� �����Ͽ� Data Log ���Ͽ�
	 * ����ϸ� Thread Ŭ������ ��ӹ��� PacketUnitLauncher�� �����ڿ� ����� ���ڿ��� �����Ͽ� run()�Լ��� ȣ���Ѵ�.
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
		    } else if (beadang.charAt(i) == 0x00) { // null üũ
		    } else {
		    	sbBuffer.append(beadang.charAt(i));
		    }
		}
    }

    /**
	 * �������� ������ �ľ��Ͽ� �ش� ���μ����� �����ϴ� �Լ��̸� ���� �׽�Ʈ�����θ� ���ǰ� �ִ�.
	 * 
	 * @param token Serial Device�� ���� �Էµ� ����
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
	 * Serial Device�� ���� �Էµ� �����͸� ������ ���Ͽ� �����ϴ� �Լ��̸� Log4J�� ��ü�����ν� 
	 * ������ �ʴ� �Լ��̴�.
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
	 * ��������(config.xml)�� ������ XPath�� �̿��Ͽ� �о�鿩 ���ú����� �����Ѵ�.
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
         * Serial Device ���� �ʱ�ȭ
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
         * Database ���� �ʱ�ȭ
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
		     * MNG ���� �ʱ�ȭ
		     */
		    dbMNG = new DbMNG(this, databaseDTO);
	
		} catch (Exception ex) {
		    SDL_Common.logger.error(ex.toString());
		}
	
		/**
         * ����ó(Meet) ���� �ʱ�ȭ
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
	 * ���α׷� ���߰������� ���Ƿ� �����͸� �Է��Ͽ� �׽�Ʈ�� �����ϱ� ���� ���� �Լ��̸�
	 * ���� ���񽺿����� ������ �ʴ�.
	 * 
	 * @param packet ���Ƿ� ������ ������ ��Ŷ
	 */
    private void processLabPacket(String packet) {
		separ(packet);
    }

    /**
	 * ���α׷� ���߰������� ������ �׽�Ʈ�� �����ϱ� ���� Ȱ��� �Լ��̸� ���� ���񽺿�����
	 * ������ �ʴ�.
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
     * ������ �׽�Ʈ�� �����ϱ� ���� �Լ��̸� �ʿ信 ���� �׽�Ʈ�� �ڵ带 �����Ͽ� ToteRead �����ڿ��� ȣ���� �� �ֵ���
     * �Ѵ�.
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
     * Serial Device�� ���� ������ ������ ����� ��� ���� �Է� �����͸� �ϵ��ڵ��Ͽ� �׽�Ʈ�� �� ������
     * ToteRead �����ڿ��� ȣ���� �� �ֵ��� �Ѵ�.
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
	 * Serial Device�� ���� �����Ͱ� �ԷµǾ��� ��� �̸� Thread�� �̿��Ͽ� ó���� �� �ֵ���
	 * ������ Class�̸� ������ �ϳ��� PacketUnitLauncher�� �ϳ� ������ �Ǿ� Thread �������
	 * �����ͺ��̽��� �Է��� �ǵ��� �����Ǿ���.
	 * 
	 * @author �����
	 *
	 */
    class PacketUnitLauncher extends Thread {

		private String packet = null;
	
		public PacketUnitLauncher(String packet) {
		    this.packet = packet;
		}
	
		/**
		 * PacketUnitLauncher.packet�� ������ �������� ������ �����Ͽ� �ش� ���μ����� ȣ���Ѵ�.
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
			 * �����͸� �м��Ͽ� ������ ��ü�� �׸񺰷� ������ �ǵ��� ��
			 */
		    sdl_MNG.parsePacket();
		    /*
			 * �����Ͱ�ü�� �׸񺰷� ����� �����͸� �����ͺ��̽��� ������ �ǵ��� ���μ�����
			 */
		    sdl_MNG.doProcess();
		}
    }
}
