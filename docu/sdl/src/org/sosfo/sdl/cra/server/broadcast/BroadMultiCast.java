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
 * 경륜 SDL을 multicast 방식으로 뿌린다.
 * sdl 라인을 다 plug-in할수 없으므로 개발자 또는 모니터링시 
 * 유용하게 받아서 보기 바란다.
 * </pre>
 * 
 * @author yunkidon@sosfo.or.kr
 */
public class BroadMultiCast extends Thread {

	/**
	 * Properties 객체
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
	 * 초기 환경설정 로드
	 */
	public BroadMultiCast(String sdl) {
		this.sdl = sdl;
	}

	/**
	 * 프로그램을 구동하는 엔트리 포인트
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
