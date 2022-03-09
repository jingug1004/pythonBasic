/*================================================================================
 * �ý���			: �ڵ� ����
 * �ҽ����� �̸�	: snis.rbm.system.rsy0010.activity.SaveCode.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-08-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.system.rsy1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

/**
* 
* @auther �̿���
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
        /*
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        sDsName = "dsGrpCd";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	        }
        }
	        
        sDsName = "dsSpecCd";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	        }
        }
	    */
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

        sDsName = "dsGrpCd";
        
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
        }

        sDsName = "dsSpecCd";
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
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteSpecCode(record);	            	
	            }	
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
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
        
        int dmlcount = this.getDao("rbmdao").update("rsy1010_i01", param);
        
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
        param.setValueParamter(i++, record.getAttribute("GRP_NM"));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD" ));

        int dmlcount = this.getDao("rbmdao").update("rsy1010_u01", param);
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
        
        int dmlcount = this.getDao("rbmdao").update("rsy1010_d01", param);
        
        PosParameter param1 = new PosParameter();
        int j = 0;
        param1.setWhereClauseParameter(j++, record.getAttribute("GRP_CD" ));
        param1.setWhereClauseParameter(j++, "");
        
        dmlcount = dmlcount + this.getDao("rbmdao").update("rsy1010_d02", param1);
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
        param.setValueParamter(i++, record.getAttribute("CD_NM2"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM1"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM2"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM3"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM4"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM5"));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
                
        int dmlcount = this.getDao("rbmdao").update("rsy1010_i02", param);
        
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
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("CD_NM"));
        param.setValueParamter(i++, record.getAttribute("CD_NM2"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM1"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM2"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM3"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM4"));
        param.setValueParamter(i++, record.getAttribute("CD_TERM5"));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));        
        param.setValueParamter(i++, record.getAttribute("DEL_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RMK"));
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("CD" ));

        int dmlcount = this.getDao("rbmdao").update("rsy1010_u02", param);
        
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
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("CD"));

        int dmlcount = this.getDao("rbmdao").update("rsy1010_d02", param);
        
        return dmlcount;
    }
           
}
