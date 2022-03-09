package com.beauty.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sweettracker {
	  public static void main(String[] args) throws IOException {
	        String url = "http://tracking.sweettracker.net/tracking"; //서비스URL
	        String key = "kYIFKGSg5zWktMe7beapWg"; //신청해서 받은 키
	        String code = "08";					// 택배코드
	        String invoice = "400765819101";		// 송장번호
//	    	$service_url = 'http://tracking.sweettracker.net/tracking?t_key=kYIFKGSg5zWktMe7beapWg&t_code=04&t_invoice=6016804636';
	        String furl = String.format("%s/?t_key=%s&t_code=%s&t_invoice=%s", url, key, code, invoice);
	        
	        URL obj = new URL(furl);
	        HttpURLConnection httpConn  = (HttpURLConnection) obj.openConnection();
	
	        // 전송방식 GET 지정
	        httpConn .setRequestMethod("GET");
	
	        // 요청헤더 추가
	        httpConn .setRequestProperty("User-Agent", "Mozilla/5.0");
	        httpConn .setRequestProperty("Accept", "application/json");
	        
	        int responseCode = httpConn.getResponseCode();
	        // 응답코드 확인(정상호출시 200)
	        System.out.println("Response Code : " + responseCode);
	        
	        
	        
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(httpConn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	
	        //결과 확인
	        System.out.println(response.toString());        
	      }	  
		  
}

