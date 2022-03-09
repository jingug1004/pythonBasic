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
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
   	
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

        saveState(ctx);

        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
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
     * <p> Ȩ�� �湮���� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
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
        String sColNm = "column2";	//column1:�湮�ڼ�, column2:�湮��
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
        			case 3:		//��� Ȩ������
        				cvistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 4 :	// ��� �����
        				cmobile_vistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 5 :	// ���� Ȩ������
        				mvistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 6 :	// ���� �����
        				mmobile_vistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        			case 7 :	// ������� Ȩ������
        				race_vistr_cnt = Integer.parseInt((String)(map.get(sColNm)));
        				break;
        		}        		        		
        	}        	
        }
    	PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, sDate);			// ��������
        param.setValueParamter(i++, cvistr_cnt);			// ��� Ȩ������ �湮�ڼ�   
        param.setValueParamter(i++, cmobile_vistr_cnt);		// ��� �����Ȩ������ �湮�ڼ�	
        param.setValueParamter(i++, mvistr_cnt);			// ���� Ȩ������ �湮�ڼ�
        param.setValueParamter(i++, mmobile_vistr_cnt);		// ���� �����Ȩ������ �湮�ڼ�	
        param.setValueParamter(i++, race_vistr_cnt);		// ������� Ȩ������ �湮�ڼ�
        param.setValueParamter(i++, SESSION_USER_ID);		// �ۼ���        
        
        dmlcount = this.getDao("rbmdao").update("rsy4050_u02", param);
        
        return dmlcount;
    }
    
    

	/*
	 * ������ ���缱 2013.02.02 15:00 PC���� ���ε忡 ����ϱ� ���� ���� - �������� �б�
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
