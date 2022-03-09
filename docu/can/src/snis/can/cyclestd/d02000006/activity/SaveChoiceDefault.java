/*================================================================================
 * �ý���			: ���躰���ص��
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000007.activity.SaveChoiceDefault.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000006.activity;

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


public class SaveChoiceDefault extends SnisActivity 
{
	public SaveChoiceDefault() { }
	
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
		PosDataset ds2;
		PosDataset ds3;
        int    nSize1 = 0;
        int    nSize2 = 0;
        int    nSize3 = 0;
        String     sDsName = "";
   
        sDsName = "dsExamGbn";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsExamGbn------------------->"+record);
	        }
        }
        
        sDsName = "dsExam";
        if ( ctx.get(sDsName) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName);
	        nSize2= ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsExam------------------->"+record);
	        }
        }   
        
        sDsName = "dsExamChng";
        if ( ctx.get(sDsName) != null ) {
	        ds3    = (PosDataset)ctx.get(sDsName);
	        nSize3 = ds3.getRecordCount();
	        for ( int i = 0; i < nSize3; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	            logger.logInfo("dsExamChng------------------->"+record);
	        }
        }         
       logger.logInfo("nSize1------------------->"+nSize1);
       logger.logInfo("nSize2------------------->"+nSize2);
       logger.logInfo("nSize3------------------->"+nSize3);
        
        if(nSize1 > 0){
        	saveStateExam_Bas_Item_Gbn(ctx); 
        }else if(nSize2 > 0){
        	saveStateExam_Bas_Item(ctx); 
        }else if(nSize3 > 0){
        	saveStateExam_Bas_Chng_Bas(ctx); 
        }
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	PosContext ���񱸺� �����
     * @return  none
     * @throws  none
     */
     protected void saveStateExam_Bas_Item_Gbn(PosContext ctx) 
     {
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 

		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //���񱸺� ����       
        ds1   = (PosDataset) ctx.get("dsExamGbn");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
             	nDeleteCount = nDeleteCount + deleteExam_Bas_Item_Gbn(record);
             }else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.NORMAL){	
             }else{
            	 try{
	                 nTempCnt = insertExam_Bas_Item_Gbn(record);            	 
	            	 nSaveCount = nSaveCount + nTempCnt;
            	 }catch(Exception e){
            
             		Util.setSvcMsg(ctx, "�̹� ��ϵ� �ڷᰡ �����մϴ�1");
             	}
         
             }
             
         }
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
      * @param   ctx PosContext	���� �����
      * @return  none
      * @throws  none
      */
 	protected void saveStateExam_Bas_Item(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 

		PosDataset ds2;

		int nSize        = 0;
		int nTempCnt     = 0;

		//���� ����       
		ds2   = (PosDataset) ctx.get("dsExam");
		logger.logInfo("ds2------------------->"+ds2);        
		nSize = ds2.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds2.getRecord(i);

			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
				nDeleteCount = nDeleteCount + deleteExam_Bas_Item(record);
			}else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.NORMAL){
			}else{
				 try{
						nTempCnt = insertExam_Bas_Item(record);
						nSaveCount = nSaveCount + nTempCnt;
            	 }catch(Exception e){
            
             		Util.setSvcMsg(ctx, "�̹� ��ϵ� �ڷᰡ �����մϴ�1");
             	}
			}

		}
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}    
 	
 	
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	 PosContext	ȯ�����ǥ �����
     * @return  none
     * @throws  none
     */
	protected void saveStateExam_Bas_Chng_Bas(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 

		PosDataset ds3;

		int nSize        = 0;
		int nTempCnt     = 0;

		//ȯ�����ǥ ����       
		ds3   = (PosDataset) ctx.get("dsExamChng");
		nSize = ds3.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds3.getRecord(i);

			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{
				if ( (nTempCnt = updateExam_Bas_Chng_Bas(record)) == 0 ) {
					nTempCnt = insertExam_Bas_Chng_Bas(record);
				}             	       	 
				nSaveCount = nSaveCount + nTempCnt;
			}

			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deleteExam_Bas_Chng_Bas(record);
			}
		}
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}     	
     
     /**
      * <p> ���񱸺� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertExam_Bas_Item_Gbn(PosRecord record) 
     {
		logger.logInfo("insertExam_Bas_Item_Gbn start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
 
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, record.getAttribute("FST_ASSIGN_SCR_RATE"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, record.getAttribute("FST_ASSIGN_SCR_RATE"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));		
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdb_exam_bas_item_gbn_ib001", param);

		logger.logInfo("insertExam_Bas_Item_Gbn end============================");
		return dmlcount;
     }
     
     /**
      * <p> ���񱸺� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateExam_Bas_Item_Gbn(PosRecord record) 
     {
		logger.logInfo("updateExam_Bas_Item_Gbn start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, record.getAttribute("FST_ASSIGN_SCR_RATE"));
		param.setValueParamter(i++, record.getAttribute("SND_ASSIGN_SCR_RATE"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));

		int dmlcount = this.getDao("candao").update("tbdb_exam_bas_item_gbn_ub001", param);

		logger.logInfo("updateExam_Bas_Item_Gbn end============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ���񱸺�  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteExam_Bas_Item_Gbn(PosRecord record) 
     {
		logger.logInfo("deleteExam_Bas_Item_Gbn start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));

		int dmlcount = this.getDao("candao").delete("tbdb_exam_bas_item_gbn_db001", param);

		logger.logInfo("deleteExam_Bas_Item_Gbn end============================");
		return dmlcount;
     }


     /**
      * <p> ���� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertExam_Bas_Item(PosRecord record) 
     {
		logger.logInfo("insertExam_Bas_Item start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
 
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("FST_ESTM_YN")));
		param.setValueParamter(i++, record.getAttribute("ARRAY_ORD"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("FST_ESTM_YN")));
		param.setValueParamter(i++, record.getAttribute("ARRAY_ORD"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdb_exam_bas_item_ib001", param);

		logger.logInfo("insertExam_Bas_Item end============================");
		return dmlcount;
     }
     
     /**
      * <p> ���� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateExam_Bas_Item(PosRecord record) 
     {
		logger.logInfo("updateExam_Bas_Item start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("FST_ESTM_YN")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("SND_ESTM_YN")));
		param.setValueParamter(i++, record.getAttribute("ARRAY_ORD"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));

		int dmlcount = this.getDao("candao").update("tbdb_exam_bas_item_ub001", param);

		logger.logInfo("updateExam_Bas_Item end============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ����  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteExam_Bas_Item(PosRecord record) 
     {
		logger.logInfo("deleteExam_Bas_Item start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));

		int dmlcount = this.getDao("candao").delete("tbdb_exam_bas_item_db001", param);

		logger.logInfo("deleteExam_Bas_Item end============================");
		return dmlcount;
     }

     /**
      * <p> ȯ�����ǥ �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertExam_Bas_Chng_Bas(PosRecord record) 
     {
		logger.logInfo("insertExam_Bas_Chng_Bas start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setValueParamter(i++, record.getAttribute("EXAM_CD"));
		param.setValueParamter(i++, record.getAttribute("ITEM_GBN_CD"));
		param.setValueParamter(i++, record.getAttribute("ITEM_CD"));
		param.setValueParamter(i++, record.getAttribute("BAS_SCR"));
		param.setValueParamter(i++, record.getAttribute("START_REC"));
		param.setValueParamter(i++, record.getAttribute("END_REC"));
		param.setValueParamter(i++, record.getAttribute("RATE"));
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdb_exam_bas_chng_bas_ib001", param);

		logger.logInfo("insertExam_Bas_Chng_Bas end============================");
		return dmlcount;
     }
     
     /**
      * <p> ȯ�����ǥ ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateExam_Bas_Chng_Bas(PosRecord record) 
     {
		logger.logInfo("updateExam_Bas_Chng_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++, record.getAttribute("START_REC"));
		param.setValueParamter(i++, record.getAttribute("END_REC"));
		param.setValueParamter(i++, record.getAttribute("RATE"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setWhereClauseParameter(i++, record.getAttribute("EXAM_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("ITEM_GBN_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("BAS_SCR"));

		int dmlcount = this.getDao("candao").update("tbdb_exam_bas_chng_bas_ub001", param);

		logger.logInfo("updateExam_Bas_Chng_Bas end============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ȯ�����ǥ  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteExam_Bas_Chng_Bas(PosRecord record) 
     {
		logger.logInfo("deleteExam_Bas_Chng_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setWhereClauseParameter(i++, record.getAttribute("EXAM_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("ITEM_GBN_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("BAS_SCR"));

		int dmlcount = this.getDao("candao").delete("tbdb_exam_bas_chng_bas_db001", param);

		logger.logInfo("deleteExam_Bas_Chng_Bas end============================");
		return dmlcount;
     }

}