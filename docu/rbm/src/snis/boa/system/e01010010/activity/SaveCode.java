/*================================================================================
 * �ý���			: �ڵ� ����
 * �ҽ����� �̸�	: snis.boa.system.e01010030.activity.UserManager.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010010.activity;

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
public class SaveCode extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveCode()
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
        sDsName = "dsGrpCodeListValue";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
	        
        sDsName = "dsSpecCodeListValue";
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

        sDsName = "dsGrpCodeListValue";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateGrpCode(record)) == 0 ) {
		        		nTempCnt = insertGrpCode(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteGrpCode(record);	            	
	            }		        
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteGrpCode(record);	            	
	            }		        
	        }	        
        }

        sDsName = "dsSpecCodeListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateSpecCode(record)) == 0 ) {
		        		nTempCnt = insertSpecCode(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
	        }
	         
	         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteSpecCode(record);	            	
		            }		        
		        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> �׷��ڵ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertGrpCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("GRP_NM"));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        int dmlcount = this.getDao("boadao").update("tbea_grp_cd_ia001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> �׷��ڵ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateGrpCode(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_NM"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_grp_cd_ua001", param);
        return dmlcount;
    }

    
    
    /**
     * <p> �׷��ڵ�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteGrpCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, "Y" );
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        
        int dmlcount = this.getDao("boadao").update("tbea_grp_cd_da001", param);
        return dmlcount;
    }
    
    
    /**
     * <p> ���ڵ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertSpecCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRP_CD"));
        param.setValueParamter(i++, record.getAttribute("CD"));
        param.setValueParamter(i++, record.getAttribute("CD_NM"));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, record.getAttribute("SORT_ODR"));
        param.setValueParamter(i++, record.getAttribute("RMK2"));
        
        int dmlcount = this.getDao("boadao").update("tbea_spec_cd_ia001", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> ���ڵ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSpecCode(PosRecord record)
    {
    	String delYn = "";
    	if(record.getAttribute("DEL_YN").equals("���")) delYn = "N";
    	else if(record.getAttribute("DEL_YN").equals("�̻��")) delYn = "Y";
    	
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("CD_NM"      ));
        param.setValueParamter(i++, delYn);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, record.getAttribute("SORT_ODR"));
        param.setValueParamter(i++, record.getAttribute("RMK2"));
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_spec_cd_ua001", param);
        
        return dmlcount;
    }    
    
    
    /**
     * <p> ���ڵ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSpecCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("boadao").update("tbea_spec_cd_da001", param);
        
        return dmlcount;
    }
           
}
