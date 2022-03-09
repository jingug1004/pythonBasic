package org.sosfo.sdl.cra.server.serial;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TooManyListenersException;
import java.util.Enumeration;
import java.util.Vector;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.cmd.SDLFactory;
import org.sosfo.sdl.cra.server.broadcast.BroadMultiCast;
import org.sosfo.sdl.cra.server.monitor.JVMMonitor;

/**
 * <pre>
 * 경륜 SDL (Serial Data Link) 처리
 * 1. 수신 이벤트 인식
 * 2. 버퍼링을 이용한 수신
 * 3. STX - ETX 체크
 * 4. 쓰레드 처리
 * 5. Multicast 여부 확인 및 처리
 * </pre>
 * 
 * @see http://java.sun.com/products/javacomm/reference/api/javax/comm/package-summary.html
 * @author yunkidon@sosfo.or.kr
 */
public class ToteRead implements SerialPortEventListener {

	// 버퍼링으로 SDL 수신
	private BufferedInputStream	bufferedInputStream;

	// Communications port management.
	private CommPortIdentifier	portId;

	// A communications port.
	private SerialPort			serialPort;

	// 수신할 Serial Port 명시
	private static String		COM_PORT_ID;

	// SDL 수신 임시 저장용
	private static StringBuffer	sb			= new StringBuffer();

	/**
	 * Properties 객체
	 */
	private static Config		conf		= null;

	// whether Broadcast SDL or not
	private static boolean		broadcast	= false;

	// TCP관련 설정 (2014.01.10)
	private static int tcpPort;
	private static int bufferSize;
	
	static {
		try {

			conf = ConfigFactory.getInstance().getConfiguration("sdl.properties");
			broadcast = conf.getBoolean("sdl.cra.broadcast.yes");
			COM_PORT_ID = conf.getString("sdl.cra.rs-232c.com_port");
			
			tcpPort = Integer.parseInt(conf.getString("sdl.cra.nio.server_tcp_socket_port"));
			bufferSize = Integer.parseInt(conf.getString("sdl.cra.nio.server_tcp_buffer_size"));
			
			Log.info("", "", "broadcast status ----> " + broadcast);
		} catch (Exception e) {
			System.err.print(e);
			Log.error("ERROR", "", "at BroadMultiCast : " + e);
		}
	}

	/**
	 * 초기 환경설정 로드
	 */
	public ToteRead() {
		try {
			JVMMonitor mon = new JVMMonitor();
			mon.start();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			portId = CommPortIdentifier.getPortIdentifier(COM_PORT_ID);
			serialPort = (SerialPort) portId.open("ToteReadApp", 2000);
		} catch (PortInUseException pe) {
			Log.error("ERROR", this, "포트가 이미 사용중입니다. COM PORT 확인 하세요^^" + pe);
			pe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			PortList pl = new PortList();
			//Log.error("ERROR", this, "해당 포트가 아닙니다. "+COM_PORT_ID+"를 open할 수 없습니다." + pl.toString());
			Log.error("ERROR", this, "해당 포트가 아닙니다. "+COM_PORT_ID+"를 open할 수 없습니다." + e.toString());
		}

		try {
			bufferedInputStream = new BufferedInputStream(serialPort.getInputStream());
		} catch (IOException e) {
			Log.error("ERROR", this, "InputStream 읽기 에러 발생 " + e);
			e.printStackTrace();
		}

		try {
			serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			Log.error("ERROR", this, "add Serial EventListener 에러 발생 " + e);
			e.printStackTrace();
		}

		serialPort.notifyOnDataAvailable(true);

		try {
			// 9600 baud, 8 data bits, 1 stop bit, no parity
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 시리얼/TCP 통신 초기화
	 * 2014.01.09
	 */
	private ToteRead(String flag){
		if("SERIAL".equals(flag)){
			try {
				JVMMonitor mon = new JVMMonitor();
				mon.start();
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				portId = CommPortIdentifier.getPortIdentifier(COM_PORT_ID);
				serialPort = (SerialPort) portId.open("ToteReadApp", 2000);
			} catch (PortInUseException pe) {
				Log.error("ERROR", this, "포트가 이미 사용중입니다. COM PORT 확인 하세요^^" + pe);
				pe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				PortList pl = new PortList();
				//Log.error("ERROR", this, "해당 포트가 아닙니다. "+COM_PORT_ID+"를 open할 수 없습니다." + pl.toString());
				Log.error("ERROR", this, "해당 포트가 아닙니다. "+COM_PORT_ID+"를 open할 수 없습니다." + e.toString());
			}

			try {
				bufferedInputStream = new BufferedInputStream(serialPort.getInputStream());
			} catch (IOException e) {
				Log.error("ERROR", this, "InputStream 읽기 에러 발생 " + e);
				e.printStackTrace();
			}

			try {
				serialPort.addEventListener(this);
			} catch (TooManyListenersException e) {
				Log.error("ERROR", this, "add Serial EventListener 에러 발생 " + e);
				e.printStackTrace();
			}

			serialPort.notifyOnDataAvailable(true);

			try {
				// 9600 baud, 8 data bits, 1 stop bit, no parity
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (UnsupportedCommOperationException e) {
				e.printStackTrace();
			}
		} else if ("TCP".equals(flag)){
			//TCP 방식 서버 설정
			ServerSocket serverSocket = null;
			
			try{
				serverSocket = new ServerSocket(tcpPort);
				
				System.out.println("서버 대기중...");

				while(true){																		//데몬 형식으로 동작하기 위해 무한루프
					Socket sock = serverSocket.accept();											//Client가 접속할때까지 Hang 상태로 대기
					System.out.println("[클라이언트 IP '" + sock.getInetAddress() + "' 접속됨 ]");
					
					InputStream in = sock.getInputStream();											//접속된 Client로 부터 소켓 할당
					BufferedInputStream bs = new BufferedInputStream(in);							//Buffer를 사용하기 위해 BufferedInputStream을 생성
					byte[] buff = new byte[bufferSize];												//실제 전송받은 데이터가 쌓이는 byte단위 배열 (sdl.properties에 사이즈 설정)
					
					int bData = 0;
					
					while((bData = bs.read(buff)) != -1){											//전송 데이터가 끝날때까지 Buffer에 데이터를 쌓음
						stringOutput(new String(buff));												//업무로직 호출 (실제 사용 시 업무로직 내 주석을 지워야 함)
					}
					
					//IO Resource 반납
					in.close();	
					bs.close();
					sock.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 프로그램을 구동하는 엔트리 포인트
	 * 
	 * @param args
	 */
	public static void main(String[] args) {		
		//추가 2014.01.09
		if(args.length == 0){												//인자가 없는 경우 기존 Serial 루틴 실행
			ToteRead reader = new ToteRead();
		} else if (!"SERIAL".equals(args[0]) && !"TCP".equals(args[0])){	//TCP도 SERIAL도 아닌 문자열인 경우 오류 발생
			System.out.println("!!!!!!!!!!!! 입력인자 에러...");
			return;
		} else {
			ToteRead reader = new ToteRead(args[0]);						//인자가 있는경우 오버로딩된 생성자 실행
		}
	}

	/**
	 * Serial로 부터 데이터가 수신되었을 경우 SerialPortEvent가 발생하며 이때 호출되는 인터페이스이다.
	 * 
	 * @param event 발생한 event 객체
	 */
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
			case SerialPortEvent.BI :
			case SerialPortEvent.OE :
			case SerialPortEvent.FE :
			case SerialPortEvent.PE :
			case SerialPortEvent.CD :
			case SerialPortEvent.CTS :
			case SerialPortEvent.DSR :
			case SerialPortEvent.RI :
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
				break;
			case SerialPortEvent.DATA_AVAILABLE :
				try {
					byte[] readBuffer = new byte[bufferedInputStream.available()];
					if (bufferedInputStream.read(readBuffer) > 0) {
						stringOutput(new String(readBuffer));
					}
				} catch (Exception e) {
					Log.error("ERROR", this, e);
				}
				break;
		}
	}

	/**
	 * 파라미터로 지정된 문자열에서 '0x02'로 시작되며 '0x03'으로 끝나는 구간 문자열을 도출하여 Data Log 파일에 기록하며 Thread 클래스를 상속받은 PacketUnitLauncher의 생성자에 도출된 문자열을 지정하여 run()함수를 호출한다.
	 * 
	 * @param sdl serial 로 부터 입력된 스트링
	 */
	public void stringOutput(String sdl) {
		try {

			for (int i = 0; i < sdl.length(); i++) {
				if (sdl.charAt(i) == 0x02) { // STX
					// sb.append(sdl.charAt(i));
				} else if (sdl.charAt(i) == 0x03) { // ETX
					// sb.append(sdl.charAt(i));
					/********************************************
					 * SDL 처리 Thread start......
					 ********************************************/
					System.out.println(sb.toString());
					
					//**************실제 프로세스 진행... 실제 사용 시 이 부분의 주석을 풀어야합니다.**************
					//new ProcessLauncher(sb.toString()).start();
					
					sb.delete(0, sb.length());
					/********************************************
					 * SDL 처리 Thread start......
					 ********************************************/
				} else if (sdl.charAt(i) == 0x00) { // null 체크

				} else {
					sb.append(sdl.charAt(i));
				}
			}
		} catch (Exception e) {
			Log.error("ERROR", this, e);
			e.printStackTrace();
		}
	}

	/**
	 * Serial Device로 부터 데이터가 입력되었을 경우 이를 Thread를 이용하여 처리할 수 있도록 구현된 Class이며 데이터 하나당 ParseLauncher가 하나 생성이 되어 Thread 방식으로 데이터베이스에 입력이 되도록 구성되었다.
	 */
	public class ProcessLauncher extends Thread {

		/**
		 * 복원된 하나의 SDL
		 */
		private String	sdl	= "";

		public ProcessLauncher(String sdl) {
			this.sdl = sdl;
		}

		/**
		 * 쓰레드 처리
		 */
		public void run() {
			// do process
			try {
				Log.info("SDL", sdl);
				SDLFactory.getInstance().doProcess(sdl);
			} catch (Exception e) {
				Log.error("ERROR", this, e);
			}

			// whether Broadcast SDL or not
			if (broadcast) {
				BroadMultiCast cast = new BroadMultiCast(sdl);
				cast.start();
			}

		}
	}
}
