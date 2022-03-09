package org.sosfo.sdl.cra.server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.PropNotFoundException;
import org.sosfo.framework.log.Log;
import org.sosfo.sdl.cra.cmd.SDLFactory;
import org.sosfo.sdl.cra.server.broadcast.BroadMultiCast;

/**
 * <pre>
 * Title: Non-Blacking을 구현하는 소켓 서버를 구현
 * Core Java Technologies Tech Tips 의 NIO관련 참조
 * Description: jdk1.4.2 이상에서만 수행하기 바랍니다.
 * 따라서 실행중에 또 띄워지지 않는다.
 * $ nohup java some.Daemon [-shutdown]&amp;
 * 초기시행작업이 필요하므로 쉘이나 기타 시작프로그램을 작성해야함.
 * 현재 시간을 계속해서 보여주는 데몬이다.
 * Copyright: Copyright (c) 2006
 * Company: www.UbiwareLab.com
 * 
 * @author John Zukowski
 * @version 1.1 --->reversioning yunkidon@hotmail.com
 * 
 *          </pre>
 */
public class NIOServer extends Thread {

    private static int PORT = 5000;
    private static int BUFFER_SIZE = 1024;

    private static Selector selector = null;
    private static ServerSocket serverSocket = null;

    // US-ASCII ,ISO-8859-1,UTF-8 ,UTF-16BE ,UTF-16LE ,UTF-16
    private static Charset charset = Charset.forName("UTF-8");

    /**
     * Properties 객체
     */
    private static Config conf = null;

    // whether Broadcast SDL or not
    private static boolean broadcast = false;

    private static StringBuffer sb = new StringBuffer();

    static {
	try {
	    conf = ConfigFactory.getInstance().getConfiguration("sdl.properties");
	    PORT = conf.getInt("sdl.cra.nio.server_socket_port");
	    BUFFER_SIZE = conf.getInt("sdl.cra.nio.server_socket_buffer");
	    Log.debug("", "", "NIOServer at static block port : " + PORT + "  buffer_size : " + BUFFER_SIZE);

	    broadcast = conf.getBoolean("sdl.cra.broadcast.yes");
	    Log.info("", "", "broadcast status ----> " + broadcast);
	} catch (PropNotFoundException pe) {
	    System.err.println("Can't read the properties file. Make sure sdl.properties is in the CLASSPATH");
	} catch (Exception e) {
	    System.err.println("find sdl.properties. but other problems are here.");
	}
    }

    /**
     * 초기화
     */
    public NIOServer() {
	try {
	    getEnv();

	    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
	    serverSocketChannel.configureBlocking(false);

	    serverSocket = serverSocketChannel.socket();
	    InetSocketAddress inetSocketAddress = new InetSocketAddress(PORT);
	    serverSocket.bind(inetSocketAddress);

	    selector = Selector.open();
	    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

	} catch (IOException e) {
	    Log.error("ERROR", this, "Unable to setup environment\n shutdown the Daemon" + e);
	    System.exit(-1);
	}
    }

    public static void main(String args[]) {
	NIOServer nioServer = new NIOServer();
	nioServer.start();
    }

    /**
     * 데몬 작업수행 :
     */
    public void run() {
	try {

	    while (true) {
		int count = selector.select();
		// nothing to process
		if (count == 0) {
		    continue;
		}
		Set keySet = selector.selectedKeys();
		Iterator itor = keySet.iterator();

		while (itor.hasNext()) {
		    SelectionKey selectionKey = (SelectionKey) itor.next();
		    itor.remove();
		    Socket socket = null;
		    SocketChannel channel = null;
		    // accept to register read in channel
		    if (selectionKey.isAcceptable()) {
			isAcceptable(socket, channel, selectionKey);
		    }
		    // read
		    if (selectionKey.isReadable()) {
			isReadbale(selectionKey);
		    }
		}
	    }
	} catch (IOException e) {
	    Log.error("ERROR", this, "Error during select()");
	    e.printStackTrace();
	}
    }

    /**
     * 사용자 Read
     * 
     * @param selectionKey SelectionKey
     */
    private void isReadbale(SelectionKey selectionKey) {
	SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
	ByteBuffer sharedBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
	sharedBuffer.clear();

	int bytes = -1;
	String conts = "";
	CharBuffer charBuffer = null;
	try {
	    while ((bytes = socketChannel.read(sharedBuffer)) > 0) {
		sharedBuffer.flip();

		charBuffer = charset.decode(sharedBuffer);
		conts += charBuffer.toString();

		sharedBuffer.clear();
	    }

	    stringOutput(conts);

	} catch (IOException e) {
	    Log.error("ERROR", this, "Error writing back bytes" + e);
	    e.printStackTrace();
	} finally {
	    selectionKey.cancel();
	    try {
		socketChannel.close();
	    } catch (Exception ex) {}
	}
    }

    /**
     * 버퍼링으로 읽어들인 string를 재구성해서 전송
     * 
     * @param sdl
     */
    public void stringOutput(String sdl) {

	try {
	    for (int i = 0; i < sdl.length(); i++) {
		if (sdl.charAt(i) == 0x02) { // STX
		    // sb.append(sdl.charAt(i));
		} else if (sdl.charAt(i) == 0x03) { // ETX
		    // sb.append(sdl.charAt(i));
		    try {
			Log.info("NIO", sb.toString());
			SDLFactory.getInstance().doProcess(sdl);
		    } catch (Exception e) {
			Log.error("ERROR", this, e);
		    }
		    // whether Broadcast SDL or not
		    if (broadcast) {
			BroadMultiCast cast = new BroadMultiCast(sb.toString());
			cast.start();
		    }
		    sb.delete(0, sb.length());
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
     * 사용자 Accept to read
     * 
     * @param socket Socket
     * @param channel SocketChannel
     * @param selectionKey SelectionKey
     */
    private void isAcceptable(Socket socket, SocketChannel channel, SelectionKey selectionKey) {
	try {
	    socket = serverSocket.accept();
	    Log.info("", this, "at isAcceptable() Connection from: " + socket);
	    channel = socket.getChannel();
	} catch (IOException e) {
	    Log.error("ERROR", this, "Unable to accept channel");
	    e.printStackTrace();
	    selectionKey.cancel();
	}

	if (channel != null) {
	    try {
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_READ);
	    } catch (IOException e) {
		Log.error("ERROR", this, "Unable to use channel");
		e.printStackTrace();
		selectionKey.cancel();
	    }
	}
    }

    /**
     * 지원가능한 인코딩 보기
     */
    public void getEnv() {
	Map avil_charset = Charset.availableCharsets();
	Set key = avil_charset.keySet();
	String charset = "";
	for (Iterator it = key.iterator(); it.hasNext();) {
	    charset += "\n" + it.next();
	}
	Log.debug("We can use these CharSet--->" + charset);
    }

    /**
     * ShowTime 종료시 실행할 내용들.
     */
    public void shutdown() {
	Log.fatal("ERROR", this, "서버를 종료합니다.");
    }
}
