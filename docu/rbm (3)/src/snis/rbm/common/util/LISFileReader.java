package snis.rbm.common.util;

import java.io.*;
import java.util.*;

public class LISFileReader {


	/**
	 * @(#)FileReader	1.00 2010/06/20
	 *
	 * Copyright (c) 2010-2010 RedFila.
	 * All rights reserved.
	 *
	 * This software is TextReader for Interface Data set to DB.
	 */
	private HashMap hashFileInfo = null;
	/**
	 * @uml.property  name="arrColumnSize5"
	 * @uml.associationEnd  qualifier="valueOf:java.lang.Integer java.lang.String"
	 */
	private String[] arrColumnSize5 = null;
	
	
	public LISFileReader(){
		super();
	}  
	
	public ArrayList readFolder(String dir) {  
		File   file          =  null; 
		HashMap param = new HashMap(); 
		ArrayList folderlist = new ArrayList();
		
		try { 
			 
			//path folder file list read
			String line = "";
			String[] splitData = null;
			
			File[] files = new File(dir).listFiles(); 
			for(int i=0;i<files.length;i++){   
				file = files[i]; 
				folderlist.add(readFile(file.getAbsolutePath()));
			} 
        	System.out.println(folderlist.size());
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally{ 
		}
		return folderlist;
	}	
	
	/* 2013.5.31 ������Ī����
	String DIVISION[] = {"ATL WINDOWS", "SUWON",   "SANGBONG", "ILSAN",    "BUNDANG",
            "DONGDAEMUN",  "JANGAN",  "SANBON",   "BUCHEON",  "GWANAK",
            "GIREUM",      "DANGSAN", "YUSEONG",  "INCHEON",  "SIHEUNG",
            "NONHYEON",    "CHEONAN", "OLYMPIC",  "UIJEONGBU","VIP",  
    */        
	String DIVISION[] = {"ATL WINDOWS", "SUWON",   "JUNGNANG", "ILSAN",    "BUNDANG",
			             "DONGDAEMUN",  "JANGAN",  "SANBON",   "BUCHEON",  "GWANAK",
			             "SEONGBUK",      "YEONGDEUNGPO", "DAEJEON",  "INCHEON",  "SIHEUNG",
			             "GANGNAM",    "CHEONAN", "OLYMPIC",  "UIJEONGBU","VIP",  
			             
			             "SECOND FLOOR",    "THIRD FLOOR", "FOURTH FLOOR", // ���� ����
			             // "A PART","SAM" �� ������ ó���ǹǷ� ���� division�� �߰��Ǹ� ���� �߰�..
			             "A PART","SAM"};
    
	public ArrayList readFile(String fileName) {  
		File   file          =  new File(fileName);; 
		HashMap param = null; 
		ArrayList list = new ArrayList();
		try {
			 
			//path folder file list read
			String line = "";
			String fName = file.getName().substring(0,file.getName().indexOf("."));
		    BufferedReader fin = null;
	        fin = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8")); 
	        line = "";
 
	        String time = ""; // time 
	        String temp = ""; 
	        boolean isUIJEONGBU = false;
	        boolean isATLWINDOW = false;

	        int    groupcnt        = 0;  // group count
	        int    associationcnt  = 0;  // ASSOCIATION count
	        
        	while (fin.ready()){
	        	line      = fin.readLine();
	        	if(isUIJEONGBU){// ������ ���� ���� ���κ��ʹ� �հ�üũ.
	        		if(line.indexOf("GROUP") != -1  || line.indexOf("ASSOCIATION") != -1
	        				 || line.indexOf("A PART") != -1
	        				 || line.indexOf("SAM") != -1){ 
	        			if(fin.ready()){
		        			param = new HashMap();
			        		param.put("TIME", time);
			        		param.put("FILENAME", fName);
		        			if(line.indexOf("GROUP") != -1){
			        			groupcnt++; 
				        		param.put("DIVISION", "GROUP"+groupcnt);	
				        		param.put("SELL_CD", "01");	
		        			}else if(line.indexOf("ASSOCIATION") != -1){
		        				associationcnt++; 
				        		param.put("DIVISION", "ASSOCIATION"+associationcnt);	 
				        		param.put("SELL_CD", "01");	
		        			}else if(line.indexOf("A PART") != -1){
				        		param.put("DIVISION", "A PART");	
				        		param.put("SELL_CD", "03");				// �Ǹ�ó : ����(SELL_CD:03)
		        			}else if(line.indexOf("SAM") != -1){
				        		param.put("DIVISION", "SAM");	
				        		param.put("SELL_CD", "03");				// �Ǹ�ó : ����(SELL_CD:03)	
		        			}
	        	        	line      = fin.readLine(); 	
			        		param = getAmtMap(line, param);
			        		list.add(param);
	        			}
        			} 
	        	}else{
		        	if(line.indexOf("[0=0]") != -1){ // time substirng ..
		        		time = line.substring(line.indexOf(",")-9,line.indexOf(",") );
		        		//System.out.println("time :"+time);
		        	} 
		        	// a part, sam�� �����ϰ� �д´�.
		        	// ������ �Ʒ��� ��ġ�ϹǷ� ���� �������� �аԵȴ�. - ����
		        	for( int i = 0 ;  i < DIVISION.length -2 ; i++){  
		        		if(line.trim().equals(DIVISION[i])){     // ���� ��
		        			if(line.trim().equals("ATL WINDOWS")){// ATL WINDOWS �Ʒ� sam �б�..
		        				isATLWINDOW = true;
		        			}else{
		        				isATLWINDOW = false;
		        			}
		        			
		        			if(DIVISION[i].equals("UIJEONGBU")){ // ������ ������ ��� üũ��
		        				isUIJEONGBU = true;
		        			}
		        			param = new HashMap();
			        		param.put("TIME", time);
			        		param.put("FILENAME", fName);
		        			if(fin.ready()){
		        	        	line      = fin.readLine(); 
				        		param.put("DIVISION", DIVISION[i]);
				        		param.put("SELL_CD", "01");	
				        		param = getAmtMap(line, param);
		        			}
		        			list.add(param);
		        		}else if(line.trim().equals("SAM") && isATLWINDOW){
		        		//}else if(line.trim().equals("SAM")){		//�ӽ�.�׽�Ʈ��
			        		param = new HashMap();
			        		param.put("TIME", time);
			        		param.put("FILENAME", fName);
		        			if(fin.ready()){
		        	        	line      = fin.readLine(); 
				        		param.put("DIVISION", "SAM");
				        		param.put("SELL_CD", "01");	
				        		param = getAmtMap(line, param);
		        			}
		        			list.add(param);
		        			//isUIJEONGBU = true; ///�ӽ�
		        		}
		        	}
	        	}
        	} 
        	
        	
        	/*   ������ �α� ���  start     */
			System.out.println("");
			System.out.println("fileName =="+fileName );
			System.out.print( "SELL_CD          " );
        	System.out.print( "DIVISION         " );
			for( int k = 0 ; k < pos.length ; k++){
				System.out.print(mapkey[k]+rpad(mapkey[k],14) );
			}
			System.out.println("");

			for( int k = 0 ; k < list.size() ; k++){
				param = (HashMap)list.get(k);
				System.out.print(param.get("SELL_CD")+rpad(param.get("SELL_CD")+"",17));
				System.out.print(param.get("DIVISION")  +rpad(param.get("DIVISION")+"",17));
				for( int i = 0 ; i < mapkey.length ; i++){
					System.out.print(param.get(mapkey[i]) + rpad(param.get(mapkey[i])+"",14) );
				}
				System.out.println("           TIME: "+param.get("TIME"));
				System.out.println("      FILENAME: "+param.get("FILENAME"));
			} 
			/*   ������ �α� ���  end    */
			
			fin.close();     
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally{ 
		}
		return list;
	}	

    /*
     *  �ݾ�¥���� �κ�..  �ݾ��� �ڸ����� �ڸ�..
     *  pos, mapkey �� �׻� �迭�� ������ ���ƾ� ��..
    */
    int pos[] = {33,13,13,14,14,12,12,12}; 
    String mapkey[] = {"GROSS SALES", "CANCELS", "TOT SALES", "CASHES", 
    		           "NET INCOME",  "DRAWS",   "RETURNS",   "BALANCE"};
    
	public HashMap getAmtMap(String str, HashMap param){
		int start = 0;
		String samt = "";
		for( int k = 0 ; k < pos.length ; k++){
			samt = str.substring(start, start+pos[k]-1).trim();
			start = start +pos[k]; 
    		param.put(mapkey[k], samt);
		} 
		return param;
	}
	
	public String rpad(String str, int num){
		String val = "";
		int lng = str.length();
		for( int i = 0 ; i < num - lng ; i ++){
			val += " "; 
		}
		return val;
	}
}
