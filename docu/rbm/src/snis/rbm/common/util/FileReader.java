package snis.rbm.common.util;

import java.io.*;
import java.util.*;

public class FileReader {

	ArrayList list = new ArrayList();
	public FileReader() {
	}

	public ArrayList executeInterface(String dir) {
		File file = null;
		HashMap param = null;

		try {
			// path folder file list read
			String line = "";
			String[] splitData = null;

			File[] files = new File(dir).listFiles();
			String filename = "";
			int cnt = 0;
			for (int i = 0; i < files.length; i++) {
				file = files[i];
				alReadFile(file.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}

	/*
	 * 수정자 이기석 2011.10.16 15:00 PC파일 업로드에 사용하기 위해 생성 - 선택파일 읽기
	 */
	public ArrayList alReadFile(String sFile) {

		 
		
		try {
			File file = new File(sFile);

			String filename = file.getName();
			int cnt = 0;
			String[] splitData = null;
			if (file.isFile()) {
				BufferedReader fin = null;

				fin = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

				String line = "";
				while (fin.ready()) {
					cnt++;
					line = fin.readLine();
					if (line.trim().equals("") || line.trim().length() < 10) {
						continue;
					}
					splitData = line.split(",");
					HashMap param = new HashMap();
					param.put("FILENAME", filename);
					param.put("LINE", cnt + "");
					for (int j = 0; j < splitData.length; j++) {
						param.put("column" + j, splitData[j]);
					}
					list.add(param);
				}
				fin.close();
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
