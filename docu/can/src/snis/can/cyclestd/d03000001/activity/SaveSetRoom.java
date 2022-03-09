/*================================================================================
 * �ý���			: ����ĺ�����������
 * �ҽ����� �̸�	: snis.can.cyclestd.d03000001.activity.SaveClass.java
 * ���ϼ���		: ����ĺ����������� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2007-01-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d03000001.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ĺ������������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/

public class SaveSetRoom extends SnisActivity 
{
	public SaveSetRoom() { }
	
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
    	
        PosDataset ds1;
        int    nSize1 = 0;
        String     sDsName = "";
   
        sDsName = "dsCandDormAssign";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsCandDormAssign------------------->"+record);
	        }
        }

    	saveState(ctx);         
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	PosContext ���񱸺� �����
     * @return  none
     * @throws  none
     */
     protected void saveState(PosContext ctx) 
     {
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 

		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //�������� ����       
        ds1   = (PosDataset) ctx.get("dsCandDormAssign");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updateCandDormAssign(record)) == 0 ) {
                  	nTempCnt = insertCandDormAssign(record);
                 }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteCandDormAssign(record);
             }
         }
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
     }
     	
     
     /**
      * <p> ����ĺ����������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertCandDormAssign(PosRecord record) 
     {
		logger.logInfo("insertCandDormAssign start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
        
        
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("BUILD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CHIEF_YN")));	
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdc_cand_dorm_assign_ib001", param);

		logger.logInfo("insertCandDormAssign end============================");
		return dmlcount;
     }
     
     /**
      * <p> ����ĺ��� ����� ����  ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateCandDormAssign(PosRecord record) 
     {
		logger.logInfo("updateCandDormAssign start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("BUILD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CHIEF_YN")));
		param.setValueParamter(i++, SESSION_USER_ID);
		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		
		int dmlcount = this.getDao("candao").update("tbdc_cand_dorm_assign_ub001", param);

		logger.logInfo("updateCandDormAssign end============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ����ĺ��� ����� ���� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteCandDormAssign(PosRecord record) 
     {
		logger.logInfo("deleteCandDormAssign start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("BUILD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ROOM_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		
		int dmlcount = this.getDao("candao").delete("tbdc_cand_dorm_assign_db001", param);

		logger.logInfo("deleteCandDormAssign end============================");
		return dmlcount;
     }

}