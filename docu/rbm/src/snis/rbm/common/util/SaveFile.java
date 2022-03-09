/*
 * ================================================================================
 * �ý��� : ÷������ ���� 
 * �ҽ����� �̸� : snis.rbm.common.util.SaveFile 
 * ���ϼ��� : ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011- 
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.common.util;

import java.io.File;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveFile {
	public SaveFile(){}
    
    /**
     * <p> ÷������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	public static int insertFileMana(PosRecord record, PosGenericDao dao) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//÷������Ű
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//÷������Ű
        param.setValueParamter(i++, record.getAttribute("TBL_NM"));			//���̺��
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));		//���ϸ�        
        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));		//���ϰ��
        
        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));	//�����ϸ�
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));			//���ļ���
        param.setValueParamter(i++, record.getAttribute("INST_ID"));  
        
        int dmlcount = dao.update("common_file_i01", param);

        return dmlcount;
    }
    
    
    
    /**
     * <p> ÷������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
	public static int updateFileMana(PosRecord record, PosGenericDao dao)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("TBL_NM"));					//���̺��
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));				//���ϸ�
        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));				//���ϰ��
        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));			//�����ϸ�
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));					//���ļ���
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY" ));	//÷������Ű
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));			//����
        
        int dmlcount = dao.update("common_file_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ÷������  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	public static int deleteFileMana(PosRecord record, PosGenericDao dao) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//÷������Ű
        param.setValueParamter(i++, record.getAttribute("SEQ"         ));	//����
        
        int dmlcount = dao.update("common_file_d01", param);
        
        
        String delYn =  (String) record.getAttribute("FILE_DEL_YN");
        if(delYn == null) delYn = "";

        if(dmlcount >0 && !delYn.equals("N")){

	    	try {				

	    		deleteFile(record);				
			} catch (Exception e) {				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        return dmlcount;
    }

	/**
     * ÷�������� �����ϴ� �޼ҵ�
     * @param   request		HttpServletRequest
     * @param   response		HttpServletResponse
     * @return	none
     * @throws Exception
     */
    public static void deleteFile(PosRecord record) throws Exception {        

    	String physicalFileName = (String) record.getAttribute("FILE_PATH");

        String rtn_val = "FILE_DELETE^SUCC"; 
        
        File file = null;
        
        if(physicalFileName == null || physicalFileName == ""){
            return;
        }
    
        try{
            file = new File(physicalFileName);
            if (file.exists())
            	file.delete();
        }catch(Exception e){
        	rtn_val = "FILE_DELETE^FAIL&Exception Message="+e.getMessage();
        }   
    }

    /**
     * <p> ÷������  KEY ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	public static String getFileManaMaxKey(PosGenericDao dao) 
    {
        String rtnKey = "";
        PosRowSet keyRecord = dao.find("common_file_s02");        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("ATT_FILE_KEY"));	//÷������Ű
   
        return rtnKey;
    }
	
	/**
     * <p> ÷������ ���� ��ȸ  </p>
     * @param   PosGenericDao dao			DB Connect ����
     *          String        sAttFileKey	FileKey
     * @return  nRtnKey	int			        sAttFileKey�� ���� ÷������ ����
     * @throws  none
     */
	public static int getFileCount(String sAttFileKey, PosGenericDao dao)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sAttFileKey);	//÷������Ű
        
        PosRowSet keyRecord = dao.find("common_file_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String sRtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        int nRtnKey    = Integer.parseInt(sRtnKey);
        
        return nRtnKey;
    }
	
	/**
     * <p> ÷������ ���ڵ� �������� </p>
     * @param   record	  PosRecord	 ����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnRocord PosRowSet  Return Record
     * @throws  none
     */
	public static PosRowSet selectFileRecord(PosRecord record, PosGenericDao dao)
    {
    	PosParameter param = new PosParameter();
        int i = 0;  
        
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY"));
        
        PosRowSet rtnRocord = dao.find("common_file_s01", param);

        return rtnRocord;
    }
	
	/**
     * <p> ÷������ ���ڵ� �����ϱ� </p>
     * @param   record	  PosRecord	 ����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnRocord PosRowSet  Return Record
     * @throws  none
     */
	public static int deleteFile(PosRecord record, PosGenericDao dao)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        int nTempDelete = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY"));
        
        Double dAttFileKey = (Double)record.getAttribute("ATT_FILE_KEY");

    	if( dAttFileKey == null ) {
    		return nTempDelete;
    	}
    	
        PosRowSet pFile = dao.find("common_file_s01", param);
       
		for(int nRow = 0; nRow <  pFile.count(); nRow++) {
			PosRow pr[] = pFile.getAllRow();
    		
    		String sFileKey  = String.valueOf(pr[nRow].getAttribute("ATT_FILE_KEY"));
    		String sSeq      = String.valueOf(pr[nRow].getAttribute("SEQ"));
    		String sFilePath = String.valueOf(pr[nRow].getAttribute("FILE_PATH"));
    		
    		record.setAttribute("ATT_FILE_KEY", sFileKey);
    		record.setAttribute("SEQ"         , sSeq);
    		record.setAttribute("FILE_PATH"   , sFilePath);
    		
    		nTempDelete += SaveFile.deleteFileMana(record, dao);
        }
		
		return nTempDelete;
    }
}
