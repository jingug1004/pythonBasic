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
 * ��� SDL (Serial Data Link) ó��
 * 1. ���� �̺�Ʈ �ν�
 * 2. ���۸��� �̿��� ����
 * 3. STX - ETX üũ
 * 4. ������ ó��
 * 5. Multicast ���� Ȯ�� �� ó��
 * </pre>
 * 
 * @see http://java.sun.com/products/javacomm/reference/api/javax/comm/package-summary.html
 * @author yunkidon@sosfo.or.kr
 */
public class ToteRead implements SerialPortEventListener {

	// ���۸����� SDL ����
	private BufferedInputStream	bufferedInputStream;

	// Communications port management.
	private CommPortIdentifier	portId;

	// A communications port.
	private SerialPort			serialPort;

	// ������ Serial Port ���
	private static String		COM_PORT_ID;

	// SDL ���� �ӽ� �����
	private static StringBuffer	sb			= new StringBuffer();

	/**
	 * Properties ��ü
	 */
	private static Config		conf		= null;

	// whether Broadcast SDL or not
	private static boolean		broadcast	= false;

	// TCP���� ���� (2014.01.10)
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
	 * �ʱ� ȯ�漳�� �ε�
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
			Log.error("ERROR", this, "��Ʈ�� �̹� ������Դϴ�. COM PORT Ȯ�� �ϼ���^^" + pe);
			pe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			PortList pl = new PortList();
			//Log.error("ERROR", this, "�ش� ��Ʈ�� �ƴմϴ�. "+COM_PORT_ID+"�� open�� �� �����ϴ�." + pl.toString());
			Log.error("ERROR", this, "�ش� ��Ʈ�� �ƴմϴ�. "+COM_PORT_ID+"�� open�� �� �����ϴ�." + e.toString());
		}

		try {
			bufferedInputStream = new BufferedInputStream(serialPort.getInputStream());
		} catch (IOException e) {
			Log.error("ERROR", this, "InputStream �б� ���� �߻� " + e);
			e.printStackTrace();
		}

		try {
			serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			Log.error("ERROR", this, "add Serial EventListener ���� �߻� " + e);
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
	 * �ø���/TCP ��� �ʱ�ȭ
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
				Log.error("ERROR", this, "��Ʈ�� �̹� ������Դϴ�. COM PORT Ȯ�� �ϼ���^^" + pe);
				pe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				PortList pl = new PortList();
				//Log.error("ERROR", this, "�ش� ��Ʈ�� �ƴմϴ�. "+COM_PORT_ID+"�� open�� �� �����ϴ�." + pl.toString());
				Log.error("ERROR", this, "�ش� ��Ʈ�� �ƴմϴ�. "+COM_PORT_ID+"�� open�� �� �����ϴ�." + e.toString());
			}

			try {
				bufferedInputStream = new BufferedInputStream(serialPort.getInputStream());
			} catch (IOException e) {
				Log.error("ERROR", this, "InputStream �б� ���� �߻� " + e);
				e.printStackTrace();
			}

			try {
				serialPort.addEventListener(this);
			} catch (TooManyListenersException e) {
				Log.error("ERROR", this, "add Serial EventListener ���� �߻� " + e);
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
			//TCP ��� ���� ����
			ServerSocket serverSocket = null;
			
			try{
				serverSocket = new ServerSocket(tcpPort);
				
				System.out.println("���� �����...");

				while(true){																		//���� �������� �����ϱ� ���� ���ѷ���
					Socket sock = serverSocket.accept();											//Client�� �����Ҷ����� Hang ���·� ���
					System.out.println("[Ŭ���̾�Ʈ IP '" + sock.getInetAddress() + "' ���ӵ� ]");
					
					InputStream in = sock.getInputStream();											//���ӵ� Client�� ���� ���� �Ҵ�
					BufferedInputStream bs = new BufferedInputStream(in);							//Buffer�� ����ϱ� ���� BufferedInputStream�� ����
					byte[] buff = new byte[bufferSize];												//���� ���۹��� �����Ͱ� ���̴� byte���� �迭 (sdl.properties�� ������ ����)
					
					int bData = 0;
					
					while((bData = bs.read(buff)) != -1){											//���� �����Ͱ� ���������� Buffer�� �����͸� ����
						stringOutput(new String(buff));												//�������� ȣ�� (���� ��� �� �������� �� �ּ��� ������ ��)
					}
					
					//IO Resource �ݳ�
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
	 * ���α׷��� �����ϴ� ��Ʈ�� ����Ʈ
	 * 
	 * @param args
	 */
	public static void main(String[] args) {		
		//�߰� 2014.01.09
		if(args.length == 0){												//���ڰ� ���� ��� ���� Serial ��ƾ ����
			ToteRead reader = new ToteRead();
		} else if (!"SERIAL".equals(args[0]) && !"TCP".equals(args[0])){	//TCP�� SERIAL�� �ƴ� ���ڿ��� ��� ���� �߻�
			System.out.println("!!!!!!!!!!!! �Է����� ����...");
			return;
		} else {
			ToteRead reader = new ToteRead(args[0]);						//���ڰ� �ִ°�� �����ε��� ������ ����
		}
	}

	/**
	 * Serial�� ���� �����Ͱ� ���ŵǾ��� ��� SerialPortEvent�� �߻��ϸ� �̶� ȣ��Ǵ� �������̽��̴�.
	 * 
	 * @param event �߻��� event ��ü
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
	 * �Ķ���ͷ� ������ ���ڿ����� '0x02'�� ���۵Ǹ� '0x03'���� ������ ���� ���ڿ��� �����Ͽ� Data Log ���Ͽ� ����ϸ� Thread Ŭ������ ��ӹ��� PacketUnitLauncher�� �����ڿ� ����� ���ڿ��� �����Ͽ� run()�Լ��� ȣ���Ѵ�.
	 * 
	 * @param sdl serial �� ���� �Էµ� ��Ʈ��
	 */
	public void stringOutput(String sdl) {
		try {

			for (int i = 0; i < sdl.length(); i++) {
				if (sdl.charAt(i) == 0x02) { // STX
					// sb.append(sdl.charAt(i));
				} else if (sdl.charAt(i) == 0x03) { // ETX
					// sb.append(sdl.charAt(i));
					/********************************************
					 * SDL ó�� Thread start......
					 ********************************************/
					System.out.println(sb.toString());
					
					//**************���� ���μ��� ����... ���� ��� �� �� �κ��� �ּ��� Ǯ����մϴ�.**************
					//new ProcessLauncher(sb.toString()).start();
					
					sb.delete(0, sb.length());
					/********************************************
					 * SDL ó�� Thread start......
					 ********************************************/
				} else if (sdl.charAt(i) == 0x00) { // null üũ

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
	 * Serial Device�� ���� �����Ͱ� �ԷµǾ��� ��� �̸� Thread�� �̿��Ͽ� ó���� �� �ֵ��� ������ Class�̸� ������ �ϳ��� ParseLauncher�� �ϳ� ������ �Ǿ� Thread ������� �����ͺ��̽��� �Է��� �ǵ��� �����Ǿ���.
	 */
	public class ProcessLauncher extends Thread {

		/**
		 * ������ �ϳ��� SDL
		 */
		private String	sdl	= "";

		public ProcessLauncher(String sdl) {
			this.sdl = sdl;
		}

		/**
		 * ������ ó��
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
