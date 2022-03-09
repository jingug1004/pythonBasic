/*
 * ================================================================================
 * �ý��� : ��ǰ ���� �ҽ����� 
 * �̸� : snis.rbm.business.rbo4010.activity..java 
 * ���ϼ��� : �𵨰��� 
 * �ۼ��� : ���ѳʿ�
 * ���� : 1.0.0 
 * �������� : 2011-10-21
 * ������������ : 
 * ���������� : ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbo4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePartsModel extends SnisActivity {
	public SavePartsModel(){
		
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
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
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
		int nSaveCount 	 = 0;
		int nDeleteCount = 0;
		String sDsName 	 = "";
		
		PosDataset ds;
		int nSize 		 = 0;
		int nTempCnt	 = 0;
		int nDelCnt		 = 0;
		
		int nUploadFileSize = 0;
		sDsName = "dsUploadFile";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		    = (PosDataset) ctx.get(sDsName);
	        nUploadFileSize = ds.getRecordCount();
        }

        String sAttFileKey = "";
        
        
		sDsName = "dsList";
		
		if( ctx.get(sDsName) != null )
		{
			ds		= (PosDataset) ctx.get(sDsName);
			nSize 	= ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++ )
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
				  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
				{
					if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT && nUploadFileSize > 0){
		        		sAttFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);		        		
		        	}
					
					if ( (nTempCnt = updatePartsModel(record)) == 0 )
					{
						nTempCnt = insertPartsModel(record);
						nSaveCount = nSaveCount + nTempCnt;
					}
					continue;
				}
				else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
				{
					//÷�������� ������ ��, ÷������ ����
	            	SaveFile.deleteFile(record, this.getDao("rbmdao"));
					nDeleteCount = nDeleteCount + deletePartsModel(record);
				}
			}
		}
		
		sDsName = "dsUploadFile";
        String tmpFileKey = "";

        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
		             record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	        		
	            	if ( (nTempCnt = SaveFile.updateFileMana(record,this.getDao("rbmdao"))) == 0 ) {
		        		//��Ͻ� ÷������ Ű ���� 
		        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
		        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
		        		} else {
		        			//÷�������� ������ ��ϵǴ°��
		        			if(record.getAttribute ("ATT_FILE_KEY") == null || record.getAttribute("ATT_FILE_KEY").equals("")){ 			
		        				if(tmpFileKey == null || tmpFileKey.equals("")){
	        						tmpFileKey = SaveFile.getFileManaMaxKey(this.getDao("rbmdao"));
	        						
	        						// ÷������ Key update
	        						record.setAttribute("ATT_FILE_KEY", tmpFileKey);
	        						record.setAttribute("PARTS_CD", ctx.get("PARTS_CD"));
		        					
	        						updatePartsStrAttKey(record);
		        				}
		        				record.setAttribute("ATT_FILE_KEY", tmpFileKey);
		        			}
		        		}
		        		
		        		record.setAttribute("TBL_NM", "TBRB_PARTS_MODEL");
		        		record.setAttribute("INST_ID", SESSION_USER_ID);
		        		
			        	nTempCnt   = SaveFile.insertFileMana(record, this.getDao("rbmdao"));
	            	}
	            	nSaveCount = nSaveCount + nTempCnt;
			        continue;
            	}
  
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + SaveFile.deleteFileMana(record, this.getDao("rbmdao"));
				}
	         } //for
        } //if
        
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
		
	}
    
    
    /**
     * <p> �𵨰��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int insertPartsModel(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_LCD"		));	// 1. �߸ű��ڵ�
		param.setValueParamter(i++, record.getAttribute("PARTS_MCD"		));	// 2. ��ǰ�׷��ڵ�
		param.setValueParamter(i++, record.getAttribute("PARTS_NM"		));	// 3. ��ǰ��		
		param.setValueParamter(i++, record.getAttribute("MADE_GBN"		));	// 4. ����������
		param.setValueParamter(i++, record.getAttribute("USE_YN"		));	// 5. ��뿩��	

		param.setValueParamter(i++, SESSION_USER_ID					 	 );	// 7. �ۼ���ID

		int dmlcount = this.getDao("rbmdao").update("rbo4010_i01", param);
		
		return dmlcount;
	}
	
	
	/**
     * <p> ÷������ ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePartsStrAttKey(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          	        
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY")	);
        param.setValueParamter(i++, SESSION_USER_ID					   	);
        param.setValueParamter(i++, record.getAttribute("PARTS_CD")		);

        int dmlcount = this.getDao("rbmdao").update("rbo4010_u02", param);
        return dmlcount;
    }
    
	
    /**
     * <p> �𵨰��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int updatePartsModel(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_LCD"	));	// 1. �߸ű��ڵ�
		param.setValueParamter(i++, record.getAttribute("PARTS_MCD"	));	// 2. ��ǰ�׷��ڵ�
		param.setValueParamter(i++, record.getAttribute("PARTS_NM"	));	// 3. ��ǰ��		
		param.setValueParamter(i++, record.getAttribute("MADE_GBN"	));	// 4. ����������
		param.setValueParamter(i++, record.getAttribute("USE_YN"	));	// 5. ��뿩��		
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 6. ������ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 7. ��ǰ�ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4010_u01", param);
		return dmlcount;
	}
	
	
    /**
     * <p> �𵨰��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int deletePartsModel(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. ��ǰ�ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4010_d01", param);
		return dmlcount;
	}
}
