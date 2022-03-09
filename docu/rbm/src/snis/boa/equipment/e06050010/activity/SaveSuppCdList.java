/*================================================================================
 * �ý���			: �Ҹ�ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06050010.activity.SaveSuppCdList.java
 * ���ϼ���		: �Ҹ�ǰ�ڵ带 �����Ѵ�. 
 * �ۼ���			: ���缱 
 * ����			: 1.0.0
 * ��������		: 2014-06-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06050010.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
* ��ǰ �ⳳ�Ϻ��� ��ȸ�Ѵ�. 
* @auther ������ 
* @version 1.0
*/
public class SaveSuppCdList extends SnisActivity
{    
    public SaveSuppCdList()
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
        saveState(ctx);
        
    	return PosBizControlConstants.SUCCESS;
    }

    /**
    *  <p> �Ҹ�ǰ �ڵ� ���� </p>
    *  <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
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

        sDsName = "dsSuppCdList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {	            	
		        	nDeleteCount = nDeleteCount + deleteSuppCdList(record);	   
		        	
	            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
	            	
	        		nTempCnt = updateSuppCdList(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        }
	        }	        
        }

        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    
    /**
     * <p> �Ҹ�ǰ�ڵ� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSuppCdList(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));
        param.setValueParamter(i++, record.getAttribute("SUPP_NM"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CD"));
        param.setValueParamter(i++, record.getAttribute("DANGA"));
        param.setValueParamter(i++, record.getAttribute("SAFE_JAEGO"));
        param.setValueParamter(i++, record.getAttribute("LOAD_PLACE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("e06050010_m01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> �Ҹ�ǰ�ڵ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSuppCdList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SUPP_CD" ));
        
        int dmlcount = this.getDao("boadao").update("e06050010_d01", param);
        return dmlcount;
    }
    
}

