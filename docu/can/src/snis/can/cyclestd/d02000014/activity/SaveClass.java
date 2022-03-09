/*================================================================================
 * �ý���			: ���α�������
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000007.activity.SaveChoiceDefault.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000014.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/

public class SaveClass extends SnisActivity 
{
	public SaveClass() { }
	
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
   
        sDsName = "dsClsOrgan";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsClsOrgan------------------->"+record);
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
 	
        //���񱸺� ����       
        ds1   = (PosDataset) ctx.get("dsClsOrgan");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	// if ( (nTempCnt = updateCls_Organ_Detl(record)) == 0 ) {
                  	nTempCnt = insertCls_Organ_Detl(record);
               //  }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteCls_Organ_Detl(record);
             }
         }
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
     }
     	
     
     /**
      * <p> ������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertCls_Organ_Detl(PosRecord record) 
     {
		logger.logInfo("insertCls_Organ_Detl start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
        int dmlcount = 0;
              

        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));	
        param.setValueParamter(i++, SESSION_USER_ID);
        	
    	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));	
        param.setValueParamter(i++, SESSION_USER_ID);

        dmlcount = this.getDao("candao").insert("tbdb_cls_organ_detl_ib000", param);
        
		logger.logInfo("insertCls_Organ_Detl end============================");
		return dmlcount;
     }
     
     /**
      * <p> ������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertCls_Organ_Detl1(PosRecord record) 
     {
		logger.logInfo("insertCls_Organ_Detl start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
 
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));	
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdb_cls_organ_detl_ib001", param);

		logger.logInfo("insertCls_Organ_Detl end============================");
		return dmlcount;
     }
     
     /**
      * <p> ������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateCls_Organ_Detl(PosRecord record) 
     {
		logger.logInfo("updateCls_Organ_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
		
//		String test1 = Util.trim(record.getAttribute("RACER_PERIO_NO"));
//		String test2 = Util.trim(record.getAttribute("CLS"));
//		String test3 = Util.trim(record.getAttribute("CAND_NO"));
//		String test4 = Util.trim(record.getAttribute("GUD_LECTR_ID"));
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		
		int dmlcount = this.getDao("candao").update("tbdb_cls_organ_detl_ub001", param);

		logger.logInfo("updateCls_Organ_Detl end============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ������� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteCls_Organ_Detl(PosRecord record) 
     {
		logger.logInfo("deleteCls_Organ_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		
		int dmlcount = this.getDao("candao").delete("tbdb_cls_organ_detl_db001", param);

		logger.logInfo("deleteCls_Organ_Detl end============================");
		return dmlcount;
     }

}