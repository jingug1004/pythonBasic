
/*================================================================================
 * �ý���			: ��� ����  ����
 * �ҽ����� �̸�	: snis.can.system.d02000032.activity.SaveTbdbRaceRacePoint.java
 * ���ϼ���		: ��� ���� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2008-03-31   
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
* �����Ͽ� ���� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTbdbRaceRacePoint  extends SnisActivity
{    
	
    public SaveTbdbRaceRacePoint()
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

            	nTempCnt = updateTbdbRacePoint(record);
                
            	// tbdb_race_point(����ȸ�����������) table�� update ���н� inser��Ų��. 
            	if (nTempCnt == 0 ) {
                	nTempCnt = insertTbdbRacePoint(record);            		
            	}
            	
	            nSaveCount += nTempCnt;
	        } // end for     		      
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
 
    /**
     * <p> ���� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
    */ 
    protected int updateTbdbRacePoint(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		

	        param.setValueParamter(i++, record.getAttribute("SCR")); 
   
			param.setValueParamter(i++, SESSION_USER_ID);

		//	i = 0;			
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));

			dmlcount += this.getDao("candao").update("tbdb_race_point_001", param);    
       
        return dmlcount;
    }
   

    /**
     * <p> ����  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbRacePoint(PosRecord record) 
    {
     
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("insertTbdbRacePoint ======>");	        

	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, record.getAttribute("SCR")); 
     
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_race_point_002", param);
     
        return dmlcount;
    }
    
    
    
}
