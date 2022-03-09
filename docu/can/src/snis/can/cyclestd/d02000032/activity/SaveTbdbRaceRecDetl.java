
/*================================================================================
 * �ý���			: ������ֱ������Detail ����
 * �ҽ����� �̸�	: snis.can.system.d02000032.activity.SaveTbdbRaceRecDetl.java
 * ���ϼ���		: ������ֱ������Detail ����
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
* �����Ͽ� ������ֱ������Detail ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTbdbRaceRecDetl  extends SnisActivity
{    
	
    public SaveTbdbRaceRecDetl()
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
        
        sDsName = "dsTbdbRaceRecDetl";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsTbdbRaceRecDetl ============>"+record);
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
        sDsName = "dsTbdbRaceRecDetl";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateTbdbRaceRecDetl(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertTbdbRaceRecDetl(record);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteTbdbRaceRecDetl(record);
               }      
	            nSaveCount += nTempCnt;
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> ������ֱ������Detail  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbRaceRecDetl(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ������Detail   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("insertTbdbRaceRecDetl ======>");	        
        	
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, record.getAttribute("RANK"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_200M")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DIST_DIF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("STRATEGY")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("PRE_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("HS_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("BS_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ABS_GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GOAL_GBN")));
	     //   param.setValueParamter(i++, record.getAttribute("SCR"));
     
	        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_race_rec_detl_ib001", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> ������ֱ������Detail  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTbdbRaceRecDetl(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("updateTbdbRaceRecDetl ������Ʈ======>");        	

	        param.setValueParamter(i++, record.getAttribute("RANK"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_200M")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DIST_DIF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("STRATEGY")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("PRE_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("HS_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("BS_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ABS_GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GOAL_GBN")));
	    //    param.setValueParamter(i++, record.getAttribute("SCR"));   
            
			param.setValueParamter(i++, SESSION_USER_ID);

        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));

			dmlcount += this.getDao("candao").update("tbdb_race_rec_detl_ib002", param);    
       
        return dmlcount;
    }

    
    
    /**
     * <p> ������ֱ������detail  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbRaceRecDetl(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
    	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        
        int dmlcount  = this.getDao("candao").update("tbdb_race_rec_detl_ib003", param);
        	
        
        return dmlcount;
    }    
}
