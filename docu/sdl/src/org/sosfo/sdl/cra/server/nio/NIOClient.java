package org.sosfo.sdl.cra.server.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

public class NIOClient {
    private static final int LOOP_COUNT = 10;
    private static final int SLEEP_TIME = 5000;
    private static final int PORT = 9876;

    public static void main(String args[]) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", PORT);
        InputStream is = socket.getInputStream();
        
         
        OutputStream os = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(os, "UTF-8");
        PrintWriter out = new PrintWriter(writer, true);
        
        out.println("클라이언트에서 메시지 보냅니다.");
        
        /**
         * 서버에 전송할 내용을 넣는다.
         */
        
        
        BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String line;

        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        socket.close();
    }
}
