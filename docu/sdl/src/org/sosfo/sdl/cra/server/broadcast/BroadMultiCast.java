package org.sosfo.sdl.cra.server.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.log.Log;

/**
 * <pre>
 * ��� SDL�� multicast ������� �Ѹ���.
 * sdl ������ �� plug-in�Ҽ� �����Ƿ� ������ �Ǵ� ����͸��� 
 * �����ϰ� �޾Ƽ� ���� �ٶ���.
 * </pre>
 * 
 * @author yunkidon@sosfo.or.kr
 */
public class BroadMultiCast extends Thread {

	/**
	 * Properties ��ü
	 */
	private static Config			conf	= null;
	private static int				CRA_DATAGRAM_PORT;
	private static String			CRA_DATAGRAM_IP;

	private static MulticastSocket	socket;
	private static InetAddress		address;

	static {
		try {

			conf = ConfigFactory.getInstance().getConfiguration("sdl.properties");
			CRA_DATAGRAM_PORT = conf.getInt("sdl.cra.broadcast.cra_datagram_socket_port");
			CRA_DATAGRAM_IP = conf.getString("sdl.cra.broadcast.id_address");

			Log.info("", "", "multicast ip:port ----> " + CRA_DATAGRAM_IP + ":" + CRA_DATAGRAM_PORT);

			socket = new MulticastSocket(CRA_DATAGRAM_PORT);
			address = InetAddress.getByName(CRA_DATAGRAM_IP);

		} catch (Exception e) {
			System.err.print(e);
			Log.error("ERROR", "", "at BroadMultiCast : " + e);
		}
	}

	// SDL 
	private String					sdl		= "";

	/**
	 * �ʱ� ȯ�漳�� �ε�
	 */
	public BroadMultiCast(String sdl) {
		this.sdl = sdl;
	}

	/**
	 * ���α׷��� �����ϴ� ��Ʈ�� ����Ʈ
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		int i =0;
		while (true) {
			
			String date = new Date().toString();
			
			if(i%2==1){
				date+="   append test strings";
			}
			
			BroadMultiCast cast = new BroadMultiCast(date);
			try {
				cast.start();
				cast.sleep(10000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			i++;
		}
	}

	public void run() {

		Log.debug("", this, "send multicast : "+sdl);
		DatagramPacket hi = new DatagramPacket(sdl.getBytes(), sdl.getBytes().length, address, CRA_DATAGRAM_PORT);
		try {
			socket.send(hi);
		} catch (IOException ex) {
			ex.printStackTrace();
			Log.error("ERROR", this, ex);
		}
	}
}
