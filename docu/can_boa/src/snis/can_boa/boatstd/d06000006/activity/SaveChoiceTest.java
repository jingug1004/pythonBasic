/*================================================================================
 * �ý���			: ���α�������
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000006.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/

public class SaveChoiceTest extends SnisActivity 
{
	public SaveChoiceTest() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
//   	 ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
   	
        PosDataset ds1;
        PosDataset ds2;
        PosDataset ds3;
        PosDataset ds4;
        PosDataset ds5;
        PosDataset ds6;
        PosDataset ds7;
        PosDataset ds8;
        
        int nSize1 = 0;
        int nSize2 = 0;
        int nSize3 = 0;
        int nSize4 = 0;
        int nSize5 = 0;
        int nSize6 = 0;
        int nSize7 = 0;
        int nSize8 = 0;
        
        String sDsName = "";
 
        //���߱�������
        sDsName = "dsBasItem";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsBasItem------------------->"+record);
	        }
        }
        
        //��ü�˻�
        sDsName = "dsBodyInsp";
        if ( ctx.get(sDsName) != null ) {
	        ds2 = (PosDataset)ctx.get(sDsName);
	        nSize2 = ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	            logger.logInfo("dsBodyInsp------------------->"+record);
	        }
        }   
        
        //����ü������
        sDsName = "dsStrtMesur";
        if ( ctx.get(sDsName) != null ) {
	        ds3 = (PosDataset)ctx.get(sDsName);
	        nSize3 = ds3.getRecordCount();
	        for ( int i = 0; i < nSize3; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	            logger.logInfo("dsStrtMesur------------------->"+record);
	        }
        }  
        
        //�ſ���ȸ
        sDsName = "dsIdentInq";
        if ( ctx.get(sDsName) != null ) {
	        ds4 = (PosDataset)ctx.get(sDsName);
	        nSize4 = ds4.getRecordCount();
	        for ( int i = 0; i < nSize4; i++ ) 
	        {
	            PosRecord record = ds4.getRecord(i);
	            logger.logInfo("dsIdentInq------------------->"+record);
	        }
        }    
        
        //�ʱ�
        sDsName = "dsWrtn";
        if ( ctx.get(sDsName) != null ) {
	        ds5 = (PosDataset)ctx.get(sDsName);
	        nSize5 = ds5.getRecordCount();
	        for ( int i = 0; i < nSize5; i++ ) 
	        {
	            PosRecord record = ds5.getRecord(i);
	            logger.logInfo("dsWrtn------------------->"+record);
	        }
        }    
        
        //�μ��˻�
        sDsName = "dsHmntrInsp";
        if ( ctx.get(sDsName) != null ) {
	        ds6 = (PosDataset)ctx.get(sDsName);
	        nSize6 = ds6.getRecordCount();
	        for ( int i = 0; i < nSize6; i++ ) 
	        {
	            PosRecord record = ds6.getRecord(i);
	            logger.logInfo("dsHmntrInsp------------------->"+record);
	        }
        }    
        
        //����
        sDsName = "dsInvw";
        if ( ctx.get(sDsName) != null ) {
	        ds7 = (PosDataset)ctx.get(sDsName);
	        nSize7 = ds7.getRecordCount();
	        for ( int i = 0; i < nSize7; i++ ) 
	        {
	            PosRecord record = ds7.getRecord(i);
	            logger.logInfo("dsInvw------------------->"+record);
	        }
        }    
        
        //�������
        sDsName = "dsEndRslt";
        if ( ctx.get(sDsName) != null ) {
	        ds8 = (PosDataset)ctx.get(sDsName);
	        nSize8 = ds8.getRecordCount();
	        for ( int i = 0; i < nSize8; i++ ) 
	        {
	            PosRecord record = ds8.getRecord(i);
	            logger.logInfo("dsEndRslt------------------->"+record);
	        }
        }    
           
		logger.logInfo("nSize1------------------->"+nSize1);
		logger.logInfo("nSize2------------------->"+nSize2);
		logger.logInfo("nSize3------------------->"+nSize3);
		logger.logInfo("nSize4------------------->"+nSize4);
		logger.logInfo("nSize5------------------->"+nSize5);
		logger.logInfo("nSize6------------------->"+nSize6);
		logger.logInfo("nSize7------------------->"+nSize7);
		logger.logInfo("nSize8------------------->"+nSize8);

		if(nSize1 > 0){
			saveStateSelt_Bas_Item(ctx); 
		}else if(nSize2 > 0){
			saveStateSelt_Body_Insp(ctx); 
		}else if(nSize3 > 0){
			saveStateSelt_Strt_Mesur(ctx);         	
		}else if(nSize4 > 0){
			saveStateSelt_Ident_Inq(ctx); 
		}else if(nSize5 > 0){
			saveStateSelt_Wrtn(ctx); 
		}else if(nSize6 > 0){
			saveStateSelt_Hmntr_Insp(ctx); 
		}else if(nSize7 > 0){
			saveStateSelt_Invw(ctx); 
		}else if(nSize8 > 0){
			saveStateSelt_End_Rslt(ctx); 			
		}        
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	����� : ���߱�������
     * @return  none
     * @throws  none
     */
     protected void saveStateSelt_Bas_Item(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsBasItem");
        nSize = ds.getRecordCount();
       
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updateSelt_Bas_Item(record)) == 0 ) {
                   	nTempCnt = insertSelt_Bas_Item(record);
                 }    	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteSelt_Bas_Item(record);
             }
         }
                  
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
      * @param   ctx		PosContext	����� : ��ü�˻�
      * @return  none
      * @throws  none
      */
      protected void saveStateSelt_Body_Insp(PosContext ctx) 
      {
      	int nSaveCount   = 0; 
      	int nDeleteCount = 0; 

      	PosDataset ds;
      	
         int nSize    	= 0;
         int nTempCnt    = 0;
         
         ds = (PosDataset) ctx.get("dsBodyInsp");
         nSize = ds.getRecordCount();
         logger.logInfo("nSize------------------->"+nSize);
   
         for ( int i = 0; i < nSize; i++ ) 
         {
              PosRecord record = ds.getRecord(i);
              
              if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
              {
             	 if ( (nTempCnt = updateSelt_Body_Insp(record)) == 0 ) {
                    	nTempCnt = insertSelt_Body_Insp(record);
                  }    	 
             	 nSaveCount = nSaveCount + nTempCnt;
              }
              
              if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
              {
                  // delete
              	nDeleteCount = nDeleteCount + deleteSelt_Body_Insp(record);
              }
          }
                   
          Util.setSaveCount  (ctx, nSaveCount     );
          Util.setDeleteCount(ctx, nDeleteCount   );
     }     
      
      
  	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ����ü������
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateSelt_Strt_Mesur(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsStrtMesur");
  		nSize = ds.getRecordCount();
  		logger.logInfo("nSize------------------->"+nSize);

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateSelt_Strt_Mesur(record)) == 0 ) {
  					nTempCnt = insertSelt_Strt_Mesur(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteSelt_Strt_Mesur(record);
  			}
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
  	
  	
 	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : �ſ���ȸ
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateSelt_Ident_Inq(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsIdentInq");
  		nSize = ds.getRecordCount();
  		logger.logInfo("nSize------------------->"+nSize);

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateSelt_Ident_Inq(record)) == 0 ) {
  					nTempCnt = insertSelt_Ident_Inq(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteSelt_Ident_Inq(record);
  			}
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}     	
  	
  	
 	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx	PosContext	����� : �ʱ�
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateSelt_Wrtn(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsWrtn");
  		nSize = ds.getRecordCount();
  		logger.logInfo("nSize------------------->"+nSize);

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateSelt_Wrtn(record)) == 0 ) {
  					nTempCnt = insertSelt_Wrtn(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteSelt_Wrtn(record);
  			}
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}     	  
  	
  	
 	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx	PosContext	����� : �μ��˻�
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateSelt_Hmntr_Insp(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsHmntrInsp");
  		nSize = ds.getRecordCount();
  		logger.logInfo("nSize------------------->"+nSize);

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateSelt_Hmntr_Insp(record)) == 0 ) {
  					nTempCnt = insertSelt_Hmntr_Insp(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteSelt_Hmntr_Insp(record);
  			}
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}      	
     
  	
 	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx	PosContext	����� : ����
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateSelt_Invw(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsInvw");
  		nSize = ds.getRecordCount();
  		logger.logInfo("nSize------------------->"+nSize);

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateSelt_Invw(record)) == 0 ) {
  					nTempCnt = insertSelt_Invw(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteSelt_Invw(record);
  			}
  		}
  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}   
  	
  	
 	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx	PosContext	����� : �������
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateSelt_End_Rslt(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsEndRslt");
  		nSize = ds.getRecordCount();
  		logger.logInfo("nSize------------------->"+nSize);

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateSelt_End_Rslt(record)) == 0 ) {
  					nTempCnt = insertSelt_End_Rslt(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteSelt_End_Rslt(record);
  			}
  		}
  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}   	
     
     /**
      * <p> ���߱������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_Bas_Item(PosRecord record) 
     {
     	logger.logInfo("insertSelt_Bas_Item ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM")));
         param.setValueParamter(i++, record.getAttribute("ASSGN_SCR_RATE"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_selt_bas_item_in001", param);
         
         logger.logInfo("insertSelt_Bas_Item ============================");
         return dmlcount;
     }
     
     /**
      * <p> ���߱������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_Bas_Item(PosRecord record) 
     {
     	logger.logInfo("updateSelt_Bas_Item ============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
		param.setValueParamter(i++, record.getAttribute("ASSGN_SCR_RATE"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ITEM"));

		int dmlcount = this.getDao("candao").update("tbdn_selt_bas_item_un001", param);

		logger.logInfo("updateSelt_Bas_Item ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ���߱������� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_Bas_Item(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_Bas_Item start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("ITEM"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_selt_bas_item_dn001", param);
         
         logger.logInfo("deleteSelt_Bas_Item end============================");
         return dmlcount;
     }
     
     
     /**
      * <p> ��ü�˻� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_Body_Insp(PosRecord record) 
     {
     	logger.logInfo("insertSelt_Body_Insp start ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPT_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HGHT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("WGHT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_LEFT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_RIGHT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_PRES")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HEAR_LEFT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HEAR_RIGHT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("COLOR_WEAK")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ECG")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CARDIAC_TEST")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("IRREG_RSN")));
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_selt_body_insp_in001", param);
         
         logger.logInfo("insertSelt_Body_Insp end ============================");
         return dmlcount;
     }
     
     /**
      * <p> ��ü�˻� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_Body_Insp(PosRecord record) 
     {
     	logger.logInfo("updateSelt_Body_Insp start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HGHT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WGHT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_LEFT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_RIGHT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_PRES")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HEAR_LEFT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HEAR_RIGHT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("COLOR_WEAK")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ECG")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CARDIAC_TEST")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("IRREG_RSN")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));

		int dmlcount = this.getDao("candao").update("tbdn_selt_body_insp_un001", param);

		logger.logInfo("updateSelt_Body_Insp end ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ��ü�˻� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_Body_Insp(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_Body_Insp start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_selt_body_insp_dn001", param);
         
         logger.logInfo("deleteSelt_Body_Insp end============================");
         return dmlcount;
     }     
     
     
     
     /**
      * <p> ����ü������ �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_Strt_Mesur(PosRecord record) 
     {
     	 PosParameter param = new PosParameter();       					
         int i = 0;
         
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPT_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_01")));
         param.setValueParamter(i++, record.getAttribute("SCR_01"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_02")));
         param.setValueParamter(i++, record.getAttribute("SCR_02"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_03")));
         param.setValueParamter(i++, record.getAttribute("SCR_03"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_04")));
         param.setValueParamter(i++, record.getAttribute("SCR_04"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_05")));
         param.setValueParamter(i++, record.getAttribute("SCR_05"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_06")));
         param.setValueParamter(i++, record.getAttribute("SCR_06"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_07")));
         param.setValueParamter(i++, record.getAttribute("SCR_07"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_08")));
         param.setValueParamter(i++, record.getAttribute("SCR_08"));
         //param.setValueParamter(i++, Util.trim(record.getAttribute("REC_09")));
         //param.setValueParamter(i++, record.getAttribute("SCR_09"));
         //param.setValueParamter(i++, Util.trim(record.getAttribute("REC_10")));
         //param.setValueParamter(i++, record.getAttribute("SCR_10"));
//         param.setValueParamter(i++, Util.trim(record.getAttribute("REC_11")));
//         param.setValueParamter(i++, record.getAttribute("SCR_11"));
        
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_selt_strt_mesur_in001", param);
      
         return dmlcount;
     }
     
     /**
      * <p> ����ü������ ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_Strt_Mesur(PosRecord record) 
     {
     	logger.logInfo("updateSelt_Strt_Mesur start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_01")));
        param.setValueParamter(i++, record.getAttribute("SCR_01"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_02")));
        param.setValueParamter(i++, record.getAttribute("SCR_02"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_03")));
        param.setValueParamter(i++, record.getAttribute("SCR_03"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_04")));
        param.setValueParamter(i++, record.getAttribute("SCR_04"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_05")));
        param.setValueParamter(i++, record.getAttribute("SCR_05"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_06")));
        param.setValueParamter(i++, record.getAttribute("SCR_06"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_07")));
        param.setValueParamter(i++, record.getAttribute("SCR_07"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_08")));
        param.setValueParamter(i++, record.getAttribute("SCR_08"));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("REC_09")));
        //param.setValueParamter(i++, record.getAttribute("SCR_09"));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("REC_10")));
        //param.setValueParamter(i++, record.getAttribute("SCR_10"));
//        param.setValueParamter(i++, Util.trim(record.getAttribute("REC_11")));
//        param.setValueParamter(i++, record.getAttribute("SCR_11"));
         
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));

		int dmlcount = this.getDao("candao").update("tbdn_selt_strt_mesur_un001", param);

		logger.logInfo("updateSelt_Strt_Mesur end ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ����ü������ ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_Strt_Mesur(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_Strt_Mesur start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_selt_strt_mesur_dn001", param);
         return dmlcount;
     }
     
     /**
      * <p> �ſ���ȸ �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_Ident_Inq(PosRecord record) 
     {
     	logger.logInfo("insertSelt_Ident_Inq start ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPT_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DIS_RSN")));
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_selt_ident_inq_in001", param);
         
         logger.logInfo("insertSelt_Ident_Inq end ============================");
         return dmlcount;
     }
     
     /**
      * <p> �ſ���ȸ ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_Ident_Inq(PosRecord record) 
     {
     	logger.logInfo("updateSelt_Ident_Inq start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DIS_RSN")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));

		int dmlcount = this.getDao("candao").update("tbdn_selt_ident_inq_un001", param);

		logger.logInfo("updateSelt_Ident_Inq end ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> �ſ���ȸ ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_Ident_Inq(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_Ident_Inq start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_selt_ident_inq_dn001", param);
         
         logger.logInfo("deleteSelt_Ident_Inq end============================");
         return dmlcount;
     }  
     
     
     /**
      * <p> �ʱ� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_Wrtn(PosRecord record) 
     {
     	logger.logInfo("insertSelt_Wrtn start ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;

         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPT_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, record.getAttribute("SCR"));
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_selt_wrtn_in001", param);
         
         logger.logInfo("insertSelt_Wrtn end ============================");
         return dmlcount;
     }
     
     /**
      * <p> �ʱ� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_Wrtn(PosRecord record) 
     {
     	logger.logInfo("updateSelt_Wrtn start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;

        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, record.getAttribute("SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));

		int dmlcount = this.getDao("candao").update("tbdn_selt_wrtn_un001", param);

		logger.logInfo("updateSelt_Wrtn end ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> �ʱ� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_Wrtn(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_Wrtn start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_selt_wrtn_dn001", param);
         
         logger.logInfo("deleteSelt_Wrtn end============================");
         return dmlcount;
     }   
     
     
     /**
      * <p> �μ��˻� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_Hmntr_Insp(PosRecord record) 
     {
     	logger.logInfo("insertSelt_Hmntr_Insp start ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;

         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPT_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SCR")));
//         param.setValueParamter(i++, record.getAttribute("RSLT"));
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_selt_hmntr_insp_in001", param);
         
         logger.logInfo("insertSelt_Hmntr_Insp end ============================");
         return dmlcount;
     }
     
     /**
      * <p> �μ��˻� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_Hmntr_Insp(PosRecord record) 
     {
     	logger.logInfo("updateSelt_Hmntr_Insp start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;

        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SCR")));
//        param.setValueParamter(i++, record.getAttribute("RSLT"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));

		int dmlcount = this.getDao("candao").update("tbdn_selt_hmntr_insp_un001", param);

		logger.logInfo("updateSelt_Hmntr_Insp end ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> �μ��˻� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_Hmntr_Insp(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_Hmntr_Insp start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_selt_hmntr_insp_dn001", param);
         
         logger.logInfo("deleteSelt_Hmntr_Insp end============================");
         return dmlcount;
     }     
     
     
     /**
      * <p> ���� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_Invw(PosRecord record) 
     {
     	logger.logInfo("insertSelt_Invw start ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;

         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPT_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, record.getAttribute("SCR"));
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_selt_invw_in001", param);
         
         logger.logInfo("insertSelt_Invw end ============================");
         return dmlcount;
     }
     
     /**
      * <p> ���� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_Invw(PosRecord record) 
     {
     	logger.logInfo("updateSelt_Invw start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;

        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
        //param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, record.getAttribute("SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));

		int dmlcount = this.getDao("candao").update("tbdn_selt_invw_un001", param);

		logger.logInfo("updateSelt_Invw end ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ���� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_Invw(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_Invw start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
 		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
                 
         int dmlcount = this.getDao("candao").delete("tbdn_selt_invw_dn001", param);
         
         logger.logInfo("deleteSelt_Invw end============================");
         return dmlcount;
     }  
     
     
     /**
      * <p> ������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertSelt_End_Rslt(PosRecord record) 
     {
    	 logger.logInfo("insertSelt_End_Rslt start ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
         int dmlcount = 0;

         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("REPT_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
         param.setValueParamter(i++, record.getAttribute("STRT_MESUR_SCR"));
         param.setValueParamter(i++, record.getAttribute("STRT_MESUR_CHNG_SCR"));
         param.setValueParamter(i++, record.getAttribute("WRTN_SCR"));
         param.setValueParamter(i++, record.getAttribute("WRTN_CHNG_SCR"));
         param.setValueParamter(i++, record.getAttribute("INVW_SCR"));
         param.setValueParamter(i++, record.getAttribute("INVW_CHNG_SCR"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HMNTR_INSP")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("HMNTR_INSP_CHNG_SCR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PASS_YN")));
         param.setValueParamter(i++, SESSION_USER_ID);
         dmlcount = this.getDao("candao").insert("tbdn_selt_end_rslt_in001", param);

         logger.logInfo("insertSelt_End_Rslt end ============================");
         return dmlcount;
     }
     
     /**
      * <p> ������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateSelt_End_Rslt(PosRecord record) 
     {
     	logger.logInfo("updateSelt_End_Rslt start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
        int dmlcount = 0;

        //param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        param.setValueParamter(i++, record.getAttribute("STRT_MESUR_SCR"));
        param.setValueParamter(i++, record.getAttribute("STRT_MESUR_CHNG_SCR"));
        param.setValueParamter(i++, record.getAttribute("WRTN_SCR"));
        param.setValueParamter(i++, record.getAttribute("WRTN_CHNG_SCR"));
        param.setValueParamter(i++, record.getAttribute("INVW_SCR"));
        param.setValueParamter(i++, record.getAttribute("INVW_CHNG_SCR"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HMNTR_INSP")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HMNTR_INSP_CHNG_SCR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PASS_YN")));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
		dmlcount = this.getDao("candao").update("tbdn_selt_end_rslt_un001", param);
	
		logger.logInfo("updateSelt_End_Rslt end ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> ������� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteSelt_End_Rslt(PosRecord record) 
     {
    	 logger.logInfo("deleteSelt_End_Rslt start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
         int dmlcount = 0;
 
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
         dmlcount = this.getDao("candao").delete("tbdn_selt_end_rslt_dn001", param);
         
         logger.logInfo("deleteSelt_End_Rslt end============================");
         return dmlcount;
     }       
}
