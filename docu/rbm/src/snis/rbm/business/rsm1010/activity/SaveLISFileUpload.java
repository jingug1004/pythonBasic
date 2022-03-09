/*================================================================================
 * �ý���			: LIS FILE ���ε�
 * �ҽ����� �̸�	: snis.rbm.business.rsm1010.activity.SaveLISFileUpload.java
 * ���ϼ���		: LIS FILE ����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm1010.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import snis.rbm.common.util.LISFileReader;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveLISFileUpload extends SnisActivity {
	public SaveLISFileUpload(){}
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
	String MEETCD = "";
	String YEAR = "";
    public String runActivity(PosContext ctx)
    {	   	
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	        
        saveState(ctx);
    	
        /* 
         *  �ϰ��� ���� �ø��� ��� meetcd, ���� ��θ� ���� 
		String dir = "E:\\LIS\\2011\\CRA\\lis";
		MEETCD = "001";
		YEAR   = "2011";
    	readFolder(dir,"");
    	*/
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
    	String sDsName   = "";
    	String sFileCd = "002";
    	String sResult = "F";
    	PosDataset ds;
    	PosRow[] pr = null;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsLISFile";
        HashMap hMap = null;
        
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            String FILE_LOCAL_PATH = (String)record.getAttribute("FILE_LOCAL_PATH");
	            String FILE_PATH = (String)record.getAttribute("FILE_PATH");
	            String FILE_NM = (String)record.getAttribute("FILE_NM");
	            
	            LISFileReader lisfReader = new LISFileReader();
	            LISFile lisf = new LISFile(ctx,record, this.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);
	            
	            // �Է��Ϸ���  �⵵, ���ڿ� �ش��ϴ� DAS ������ �˻�
	            //pr = lisf.searchDasLIS(this.getDao("rbmdao"),record);	// commented by TOMMY(2013.8.30)
	    		
	            // �Է��Ϸ���  �⵵, ���ڿ� �ش��ϴ� LIS FILE ������ ���� ����
	            lisf.deleteLISFile(this.getDao("rbmdao"),record);
	            
	            ArrayList aList = null;
	            int dmlLISFile =0;
	            if(!"".equals(FILE_LOCAL_PATH)&&FILE_LOCAL_PATH!=null )
	            {    
		            aList = lisfReader.readFile(FILE_PATH);
//		            dmlLISFile = lisf.saveLISFile(aList,pr); // LIS ���̺� ������ ����
		            dmlLISFile = lisf.saveLISFile(aList); // LIS ���̺� ������ ����
	            }
	            
	            nSaveCount = nSaveCount + dmlLISFile;	            
	            aList = null;
	            
	            sResult = "T";
	            
	            sFileCd = FILE_NM!=null&&!"".equals(FILE_NM)?"001":"002";
		    }	        
        }

        
        Util.setReturnParam(ctx, "FILE_CD", sFileCd); // ���ε� ���� ���
        Util.setReturnParam(ctx, "RESULT", sResult); // ���
    }

    
    protected ArrayList readFolder(String dir,String date) {  
		File   file          =  null; 
		HashMap param = new HashMap(); 
		ArrayList folderlist = new ArrayList();
		
		try { 
			HashMap hMap = null;
			String[] ar= null;
			File[] files = new File(dir).listFiles(); 
			for(int i=0;i<files.length;i++){   
				file = files[i]; 
				if(file.isDirectory()){
					ar = file.getName().split("_");
					System.out.println("dir:"+file.getName()+" ar1:"+ar[1]);
					readFolder(file.getAbsolutePath(),ar[1]);
				}else{
					if(file.getName().equals("divstatrpt.lis")){
						hMap = new HashMap();
				        hMap.put("FILE_LOCAL_PATH",file.getAbsolutePath());
				        hMap.put("FILE_PATH",file.getAbsolutePath());
				        hMap.put("FILE_NM",file.getName());
				        hMap.put("MEET_CD",MEETCD);
				        hMap.put("STND_YEAR",YEAR);
				        hMap.put("DT",date);
				        saveAllState(hMap);
						System.out.println("file:"+file.getAbsolutePath()+ "  "+hMap);
					}
				}
			} 
        	
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally{ 
		}
		return folderlist;
	}
    
    
	
    protected void saveAllState(HashMap hMap) 
    {
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	String sFileCd = "002";
    	String sResult = "F";
    	PosDataset ds;
    	PosRow[] pr =null;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsLISFile"; 
          
        
        String FILE_LOCAL_PATH = (String)hMap.get("FILE_LOCAL_PATH");
        String FILE_PATH       = (String)hMap.get("FILE_PATH");
        String FILE_NM         = (String)hMap.get("FILE_NM");
        
        LISFileReader lisfReader = new LISFileReader();
        LISFile lisf = new LISFile( this.getRbmDao("rbmjdbcdao"), SESSION_USER_ID);
        
        ArrayList aList = null; 
        aList = (ArrayList)lisfReader.readFile(FILE_PATH);
        lisf.saveAllLISFile(aList,hMap); // LIS ���̺� ������ ����        
         
    }
    
}
