package snis.rbm.system.rsy4050.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class ImportWeblog extends SnisActivity {
	public ImportWeblog(){	
		
	}
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

        saveState(ctx);

        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	String sDsWebId  = "";
    	String sFilePath = "";
    	
    	PosDataset ds;
    	PosDataset dsWebId = null;
    	
    	int nSize        = 0;
        int nTempCnt     = 0;

        sDsName  = "dsWebStat";
        sDsWebId = "dsWebId";
        
        sFilePath = (String)ctx.get("FILE_PATH");
        
        if ( ctx.get(sDsWebId) != null ) {
	        dsWebId		 = (PosDataset) ctx.get(sDsWebId);
        }
      	
        if ( ctx.get(sDsName) != null && ctx.get(sDsWebId) != null) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( "1".equals(record.getAttribute("CHK").toString())) {
		        	
		        	nTempCnt = migrateWebLog(record, dsWebId, sFilePath);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }		        		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> 홈피 방문정보 입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int migrateWebLog(PosRecord record, PosDataset dsWebId, String sFilePath) 
    {
    	Integer cvistr_cnt = 0;
    	Integer cmobile_vistr_cnt = 0;
    	Integer mvistr_cnt = 0;
    	Integer mmobile_vistr_cnt = 0;
    	Integer race_vistr_cnt = 0;
    	
        String sfileName = "";
        String sDate = "";
        String sColNm = "column2";	//column1:방문자수, column2:방문수
        int dmlcount = 0; 
    	int nWebIdSize   = 0;  
    	String sWebId = "";
    	String sWebNo = "";
        nWebIdSize   = dsWebId.getRecordCount();
        for (int nRow = 0; nRow < nWebIdSize; nRow++ ) {
        	sWebId = dsWebId.getRecord(nRow).getAttribute("CD").toString();
        	sWebNo = dsWebId.getRecord(nRow).getAttribute("CD_TERM1").toString();
        	
        	sDate = record.getAttribute("RACE_DAY_DOT").toString();
        	sfileName = sFilePath + "/" + sWebId + "/" + sDate + "/report/summary3.txt";
        	ArrayList list = new ArrayList();
        	list = alReadFile(sfileName);
        	
        	String[] sArrMap   = new String[list.size()];
        	for(int i=0; i < list.size() ;i++) {
        		HashMap map = (HashMap)list.get(i);
        		String linetype = (String)map.get("column0");
        		
        		if (!"total".equals(linetype)) continue;

        		switch (Integer.parseInt(sWebNo)) {
        			case 3:		//경륜 홈페이지
        				cvistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 4 :	// 경륜 모바일
        				cmobile_vistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 5 :	// 경정 홈페이지
        				mvistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 6 :	// 경정 모바일
        				mmobile_vistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 7 :	// 사업본부 홈페이지
        				race_vistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        		}        		        		
        	}        	
        }
    	PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, sDate);			// 경주일자
        param.setValueParamter(i++, cvistr_cnt);			// 경륜 홈페이지 방문자수   
        param.setValueParamter(i++, cmobile_vistr_cnt);		// 경륜 모바일홈페이지 방문자수	
        param.setValueParamter(i++, mvistr_cnt);			// 경정 홈페이지 방문자수
        param.setValueParamter(i++, mmobile_vistr_cnt);		// 경정 모바일홈페이지 방문자수	
        param.setValueParamter(i++, race_vistr_cnt);		// 사업본부 홈페이지 방문자수
        param.setValueParamter(i++, SESSION_USER_ID);		// 작성자        
        
        dmlcount = this.getDao("rbmdao").update("rsy4050_u02", param);
        
        return dmlcount;
    }
    
    

	/*
	 * 수정자 신재선 2013.02.02 15:00 PC파일 업로드에 사용하기 위해 생성 - 선택파일 읽기
	 */
	public ArrayList alReadFile(String sFile) {		 
		ArrayList list = new ArrayList();
		
		try {
			File file = new File(sFile);

			int cnt = 0;
			String[] splitData = null;
			if (file.isFile()) {
				
				BufferedReader fin = null;
				fin = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

				String line = "";
				while (fin.ready()) {
					cnt++;
					line = fin.readLine();
					//if (line.trim().equals("") || line.trim().length() < 10 || "#".equals(line.substring(0,1))) {
					//	continue;
					//}
					if (!"total".equals(line.substring(0,5))) {
						continue;
					}
					splitData = line.split("\t");
					HashMap param = new HashMap();
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
