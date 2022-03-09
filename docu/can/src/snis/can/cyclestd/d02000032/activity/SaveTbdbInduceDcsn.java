
/*================================================================================
 * �ý���			: ��� ���������  ����
 * �ҽ����� �̸�	: snis.can.system.d02000032.activity.SaveTbdbInduceDcsn.java
 * ���ϼ���		: ��� ��������� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2008-03-27   
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
* �����Ͽ� ��������� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTbdbInduceDcsn  extends SnisActivity
{    
	
    public SaveTbdbInduceDcsn()
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
        	
        sDsName = "dsTbdbInduceDcsn";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsTbdbInduceDcsn============>"+record);
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
        sDsName = "dsTbdbInduceDcsn";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	         	
	            PosRecord record = ds.getRecord(i);  
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateTbdbInduceDcsn(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertTbdbInduceDcsn(record);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteTbdbInduceDcsn(record);
               }      
	            nSaveCount += nTempCnt;
	        } // end for     		      
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> ���������  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbInduceDcsn(PosRecord record) 
    {
   	    logger.logInfo("==========================  ���������   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;  

	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RECORD_MAN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_CONT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE_DESC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("AVOID_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_CAUSE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_DEAL")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_EFFECT")));
		        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_induce_dcsn_002", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> ���������  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
    */ 
    protected int updateTbdbInduceDcsn(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("updateTbdbInduceDcsn ������Ʈ======>");        	

	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RECORD_MAN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_CONT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE_DESC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("AVOID_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_CAUSE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_DEAL")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_EFFECT")));    
   
			param.setValueParamter(i++, SESSION_USER_ID);

		//	i = 0;			
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));

			dmlcount += this.getDao("candao").update("tbdb_induce_dcsn_003", param);    
       
        return dmlcount;
    }
   
    
    
    /**
     * <p> ��������� �� ���ڵ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbInduceDcsn(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        
        int dmlcount  = this.getDao("candao").update("tbdb_induce_dcsn_004", param);
        	
        return dmlcount;
    }    
    
}
