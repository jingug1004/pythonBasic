/*================================================================================
 * �ý���			: �а���
 * �ҽ����� �̸�	: snis.can.system.d06000015.activity.SaveSubjectJudg.java
 * ���ϼ���		: ���� ���� ���� �� �׸� 
 * �ۼ���			: 
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000017.activity;

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

public class SaveMakeMotorEstm extends SnisActivity 
{
	public SaveMakeMotorEstm() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String	sucess ���ڿ�
     * @throws  none
     */    
	    
    public String runActivity(PosContext ctx)
    {
    	
//    	����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	PosDataset ds1;
    	PosDataset ds2;
    	PosDataset ds3;
    	
        int nSize1 = 0;
        
        String sDsName = "";
        String sDsName_1 = "";
        String sDsName_2 = "";
                
        //���ں� �а���
        sDsName 	= "dsTab1Main";
        sDsName_1 	= "dsTab1Sub1";
        sDsName_2 	= "dsTab1Sub2";
        
        PosRecord record;
        
        //���ͺ������� ��ǥ ����
        if ( ctx.get(sDsName) != null ) {
	        ds1 = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();

	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            record = ds1.getRecord(i);
	        }
        }  
        
        if(nSize1 > 0){
			saveStateMotor_Assem_Estm(ctx); 
		}
        
        //���ͺ������� ��ǥ ����-���õ����� ����
        if ( ctx.get(sDsName_1) != null ) {
	        ds2 = (PosDataset)ctx.get(sDsName_1);
	        nSize1 = ds2.getRecordCount();

	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            record = ds2.getRecord(i);
	        }
        }  
                
		if(nSize1 > 0){
			saveStateMotor_Assem_Estm_1(ctx); 
		}     
		
		//���ͺ������� ��ǥ ����-�������� �ð� ���� ����
		if ( ctx.get(sDsName_2) != null ) {
	        ds3 = (PosDataset)ctx.get(sDsName_2);
	        nSize1 = ds3.getRecordCount();

	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            record = ds3.getRecord(i);
	        }
        }  
                
		if(nSize1 > 0){
			saveStateMotor_Assem_Estm_2(ctx); 
		}      
        
        return PosBizControlConstants.SUCCESS;
    }
     
      
  	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ���ں� �а���
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateMotor_Assem_Estm(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;
  		
  		int nSize 	= 0;
  		int nTempCnt    = 0;
  		
  		ds = (PosDataset) ctx.get("dsTab1Main");
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateMotor_Assem_Estm(record)) == 0 ) {
  					nTempCnt = insertMotor_Assem_Estm(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			
  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteMotor_Assem_Cd(record);
  			}
  			
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
  	
  	protected void saveStateMotor_Assem_Estm_1(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;
  		
  		ds = (PosDataset) ctx.get("dsTab1Sub1");
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateMotor_Assem_Estm_Sub1(record)) == 0 ) {
  					nTempCnt = insertMotor_Assem_Estm_Sub1(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteMotor_Assem_Cd(record);
  			}
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}
  	
  	protected void saveStateMotor_Assem_Estm_2(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;


  		int nSize 	= 0;
  		int nTempCnt    = 0;
  		
  		ds = (PosDataset) ctx.get("dsTab1Sub2");
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateMotor_Assem_Estm_Sub2(record)) == 0 ) {
  					nTempCnt = insertMotor_Assem_Estm_Sub2(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteMotor_Assem_Cd(record);
  			}
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}
  		
         
     /**
      * <p> ���ں� �а��� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertMotor_Assem_Estm(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     	param.setValueParamter(i++, record.getAttribute("ROUND"));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     	 param.setValueParamter(i++, SESSION_USER_ID);
     	param.setValueParamter(i++, record.getAttribute("DT"));
        param.setValueParamter(i++, record.getAttribute("ASSEM_SCR"));
        param.setValueParamter(i++, record.getAttribute("ASSEM_TIME_SCR"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("DCSN"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
     	int dmlcount = this.getDao("candao").insert("tbdn_motor_assem_estm_in001", param);

     	return dmlcount;
     }
     
     /**
      * <p> ���ں� �а��� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateMotor_Assem_Estm(PosRecord record) 
     {
        PosParameter param = new PosParameter();       					
     	int i = 0;
     	
        param.setValueParamter(i++, record.getAttribute("DT"));
        param.setValueParamter(i++, record.getAttribute("ASSEM_SCR"));
        param.setValueParamter(i++, record.getAttribute("ASSEM_TIME_SCR"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("DCSN"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		
		                                             
		int dmlcount = this.getDao("candao").update("tbdn_motor_assem_estm_un001", param);

		return dmlcount;
     }
     
     protected int insertMotor_Assem_Estm_Sub1(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     	param.setValueParamter(i++, record.getAttribute("ROUND"));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     	param.setValueParamter(i++, SESSION_USER_ID);
     	param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_CD"));
        param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_DETL_CD"));
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
     	int dmlcount = this.getDao("candao").insert("tbdn_motor_assem_estm_in002", param);

     	return dmlcount;
     }
     
     
     protected int updateMotor_Assem_Estm_Sub1(PosRecord record) 
     {
    	 
    	 PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL_CD")));
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("tbdn_motor_assem_estm_un002", param);

		return dmlcount;
     }
 
     protected int insertMotor_Assem_Estm_Sub2(PosRecord record) 
     {
     	 
    	 PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     	param.setValueParamter(i++, record.getAttribute("ROUND"));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     	param.setValueParamter(i++, SESSION_USER_ID); 
     	param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_CD"));
        param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_DETL_CD"));
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, record.getAttribute("ESTM_REC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
     	int dmlcount = this.getDao("candao").insert("tbdn_motor_assem_estm_in003", param);

     	return dmlcount;
     }
     
     protected int updateMotor_Assem_Estm_Sub2(PosRecord record) 
     {
    	PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
        param.setValueParamter(i++, record.getAttribute("ESTM_REC"));
        param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL_CD")));
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("tbdn_motor_assem_estm_un003", param);

		return dmlcount;
     }
 
     
     /**
      * <p> ���ں� �а��� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteMotor_Assem_Cd(PosRecord record) 
     {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL_CD")));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_motor_assem_estm_dn001", param);
		 
		return dmlcount;
     }
      
}
