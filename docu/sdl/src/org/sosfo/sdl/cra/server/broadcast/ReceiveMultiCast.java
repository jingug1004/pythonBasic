package org.sosfo.sdl.cra.server.broadcast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.cmd.SDLFactory;

/**
 * SDL 입력 라인이 없는 장소에서는 Multicast를 통해서 SDL를 받아서
 * 모니터링, SDL 개발 작업을 가능케 한다.
 * @author Administrator
 *
 */
public class ReceiveMultiCast extends Thread{
	
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

			Log.info("", "multicast ip:port ----> " + CRA_DATAGRAM_IP + ":" + CRA_DATAGRAM_PORT);

			socket = new MulticastSocket(CRA_DATAGRAM_PORT);
			address = InetAddress.getByName(CRA_DATAGRAM_IP);
			socket.joinGroup(address);
			
			// socket.leaveGroup(address);
			// socket.close();			
		} catch (Exception e) {
			System.err.print(e);
			Log.error("ERROR", "", "at BroadMultiCast : " + e);
		}
	}	
	
	
	/**
	 * save and process Received SDL
	 */
	public void run() {
		
		DatagramPacket packet;

		byte message[] = new byte[9999];
		packet = new DatagramPacket(message, message.length);
		String data = null;

		while (true) {
			try {
				socket.receive(packet);
			} catch (Exception e) {
				Log.error("ERROR",this,e);
			}

			data = new String(packet.getData(), 0, packet.getLength());
			Log.info("BROARDCAST",data.trim());
			
			        
			/**
			 * 데이타별 처리
			 */
			try {
				SDLFactory.getInstance().doProcess(data.trim());
			} catch (Exception e) {
				Log.error("ERROR",this,e);
			}
		}
	}	
	
	
	public static void main(String args[]) throws IOException {
		ReceiveMultiCast rmc = new ReceiveMultiCast();
		rmc.start();
	}
}
