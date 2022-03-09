
/*================================================================================
 * �ý���			: ������ֱ����������Ÿ ����
 * �ҽ����� �̸�	: snis.can.system.d02000032.activity.SaveTbdbRaceRecMst.java
 * ���ϼ���		: ������ֱ����������Ÿ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2008-03-21 
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/


package snis.can.cyclestd.d02000032.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������ֱ����������Ÿ ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTbdbRaceRecMst  extends SnisActivity
{    
	
    public SaveTbdbRaceRecMst()
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
        String     sDsName      = "";
        
        sDsName = "dsTbdbRaceRecMst";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsTbdbRaceRecMst============>"+record);
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
        sDsName = "dsTbdbRaceRecMst";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateTbdbRaceRecMst(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertTbdbRaceRecMst(record);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteTbdbRaceRecMst(record);
               }      
	            nSaveCount += nTempCnt;
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> ������ֱ����������Ÿ  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbRaceRecMst(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ����������Ÿ   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("insertTbdbRaceRecMst======>");
        	
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("WEATHER")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_DIRC")));
	        param.setValueParamter(i++, record.getAttribute("WIND_SPD"));     
	        param.setValueParamter(i++, record.getAttribute("TEMP"));         
	        param.setValueParamter(i++, record.getAttribute("HUMID"));        
	        param.setValueParamter(i++, record.getAttribute("RAIN"));         
	        param.setValueParamter(i++, Util.trim(record.getAttribute("START_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("FNL_RACE_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("FNL_RACE_TIME_HALF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("HIGH_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("M200_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("FNL_KEEP")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_STAT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DESC")));       
	        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_race_rec_mst_ib001", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> ������ֱ����������Ÿ  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTbdbRaceRecMst(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("insertTbdbRaceRecMst ������Ʈ======>");        	

	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("WEATHER")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("WIND_DIRC")));
	        param.setValueParamter(i++, record.getAttribute("WIND_SPD"));     
	        param.setValueParamter(i++, record.getAttribute("TEMP"));         
	        param.setValueParamter(i++, record.getAttribute("HUMID"));        
	        param.setValueParamter(i++, record.getAttribute("RAIN"));         
	        param.setValueParamter(i++, Util.trim(record.getAttribute("START_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("FNL_RACE_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("FNL_RACE_TIME_HALF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("HIGH_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("M200_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("FNL_KEEP")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_STAT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_DESC")));       
   
            
			param.setValueParamter(i++, SESSION_USER_ID);

			i = 0;			
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_YY")));
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));

			dmlcount += this.getDao("candao").update("tbdb_race_rec_mst_ib002", param);    
       
        return dmlcount;
    }

    
    
    /**
     * <p> ������ֱ����������Ÿ  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbRaceRecMst(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_YY")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));
        
        int dmlcount  = this.getDao("candao").update("tbdb_race_rec_mst_ib003", param);
        	
        
        return dmlcount;
    }    
}
