/*================================================================================
 * �ý���			: 2�� ���߽���  �ʱ�   ����
 * �ҽ����� �̸�	: snis.can.system.d02000008.activity.SaveSndSeltExam2.java
 * ���ϼ���		: 2�� ���߽��� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-02-20
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000008.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� 2�� ���߽��� �ʱ� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveSndSeltExam2  extends SnisActivity
{    
	
    public SaveSndSeltExam2()
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
   	
    	
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsSndSeltExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSndSeltExam2============>"+record);
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        sDsName = "dsSndSeltExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) 
	            {
	  			    nTempCnt = insertSaveSndSeltExam2(record);
	  				nSaveCount = nSaveCount + nTempCnt;
	    	    }
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    
    /**
     * <p> 2�����߽��� �ʱ� �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveSndSeltExam2(PosRecord record) 
    {
   	    logger.logInfo("==========================  2�� ���߽��� �ʱ�   �Է�   ============================");
                
   	    PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;

    	param = new PosParameter();			
		param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setValueParamter(i++, record.getAttribute("EXAM_CD"));
		param.setValueParamter(i++, record.getAttribute("ITEM_GBN_CD"));
		param.setValueParamter(i++, "101");
		param.setValueParamter(i++, record.getAttribute("CAND_NO"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setValueParamter(i++, record.getAttribute("EXAM_CD"));
		param.setValueParamter(i++, record.getAttribute("ITEM_GBN_CD"));
		param.setValueParamter(i++, "101");
		param.setValueParamter(i++, record.getAttribute("CAND_NO"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
			 	
		dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        return dmlcount;
    }
}

