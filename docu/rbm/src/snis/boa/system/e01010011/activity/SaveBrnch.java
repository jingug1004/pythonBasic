/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.boa.system.e01010011.activity.SaveBrnch.java
 * ���ϼ���		: �������/����
 * �ۼ���			: ȫ����
 * ����			: 1.0.0
 * ��������		: 2008-08-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �迵ö
* @version 1.0
*/
public class SaveBrnch extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveBrnch()
    {
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

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        sDsName = "dsBrnchGrp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsBrnchList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsBrnchGrp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateBrnchGrp(record)) == 0 ) {
		        		nTempCnt = insertBrnchGrp(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteBrnchGrp(record);	            	
	            }		        
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteBrnchGrp(record);	            	
	            }		        
	        }	        
        }

        sDsName = "dsBrnchList";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateBrnchList(record)) == 0 ) {
		        		nTempCnt = insertBrnchList(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
	        }
	         
	         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteBrnchList(record);	            	
		            }		        
		        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> ����ü �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertBrnchGrp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ASSC_NO"));
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSC_NM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        int dmlcount = this.getDao("boadao").update("tbea_brnch_ia001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> ����ü ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateBrnchGrp(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ASSC_NM"));
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ASSC_NO" ));

        int dmlcount = this.getDao("boadao").update("tbea_brnch_ua001", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ����ü ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteBrnchGrp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ASSC_NO" ));
        
        int dmlcount = this.getDao("boadao").update("tbea_brnch_da001", param);
        return dmlcount;
    }
    
    
    /**
     * <p> ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertBrnchList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNCH_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSC_NO"));
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("BRNCH_NM"));
        param.setValueParamter(i++, record.getAttribute("CRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("MRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        int dmlcount = this.getDao("boadao").update("tbea_brnch_ia002", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateBrnchList(PosRecord record)
    {
    	/*
    	String delYn = "";
    	if(record.getAttribute("DEL_YN").equals("���")) delYn = "N";
    	else if(record.getAttribute("DEL_YN").equals("�̻��")) delYn = "Y";
    	*/
        PosParameter param = new PosParameter();
        int i = 0;
        //param.setValueParamter(i++, delYn);
        param.setValueParamter(i++, record.getAttribute("ASSC_NO"));
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));
        param.setValueParamter(i++, record.getAttribute("BRNCH_NM"));
        param.setValueParamter(i++, record.getAttribute("CRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("MRA_ORD"));
        param.setValueParamter(i++, record.getAttribute("RMK"));

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNCH_CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_brnch_ua002", param);
        
        return dmlcount;
    }    
    
    
    /**
     * <p> ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteBrnchList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNCH_CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_brnch_da002", param);
        
        return dmlcount;
    }
           
}
