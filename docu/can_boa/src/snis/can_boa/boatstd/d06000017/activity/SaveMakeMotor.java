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

public class SaveMakeMotor extends SnisActivity 
{
	public SaveMakeMotor() { }
	
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
        sDsName = "dsMotorItem";
        sDsName_1 = "dsMotorItem_1";
        sDsName_2 = "dsMotorItem_2";
        
        PosRecord record;
        
        if ( ctx.get(sDsName) != null ) {
	        ds1 = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();

	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            record = ds1.getRecord(i);
	        }
        }  
        
        if(nSize1 > 0){
			saveStateMotor_Assem_Cd(ctx); 
		}
        
        if ( ctx.get(sDsName_1) != null ) {
	        ds2 = (PosDataset)ctx.get(sDsName_1);
	        nSize1 = ds2.getRecordCount();

	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            record = ds2.getRecord(i);
	        }
        }  
                
		if(nSize1 > 0){
			saveStateMotor_Assem_Cd_1(ctx); 
		}      
		
		if ( ctx.get(sDsName_2) != null ) {
	        ds3 = (PosDataset)ctx.get(sDsName_2);
	        nSize1 = ds3.getRecordCount();

	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            record = ds3.getRecord(i);
	        }
        }  
                
		if(nSize1 > 0){
			saveStateMotor_Assem_Cd_2(ctx); 
		}      
        
        return PosBizControlConstants.SUCCESS;
    }
     
      
  	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ���ں� �а���
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateMotor_Assem_Cd(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;
  		
  		int nSize 	= 0;
  		int nTempCnt    = 0;
  		
  		String ESTM_ITEM_CD = "201";

  		ds = (PosDataset) ctx.get("dsMotorItem");
  		nSize = ds.getRecordCount();
  		
  		//System.out.println("kbh--nSize : " + nSize);
  		
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteMotor_Assem_Cd(record);
  			}
  		}
  			
		for ( int i = 0; i < nSize; i++ ) 
  		{
			PosRecord record = ds.getRecord(i);
  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateMotor_Assem_Cd(record)) == 0 ) {
  					nTempCnt = insertMotor_Assem_Cd(record,ESTM_ITEM_CD);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
  	
  	protected void saveStateMotor_Assem_Cd_1(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;
  		
  		String ESTM_ITEM_CD = "202";

  		ds = (PosDataset) ctx.get("dsMotorItem_1");
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);
  			
  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteMotor_Assem_Cd(record);
  			}
  			
  		}
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);
  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateMotor_Assem_Cd(record)) == 0 ) {
  					nTempCnt = insertMotor_Assem_Cd(record,ESTM_ITEM_CD);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}
  	
  	protected void saveStateMotor_Assem_Cd_2(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;


  		int nSize 	= 0;
  		int nTempCnt    = 0;
  		
  		String ESTM_ITEM_CD = "203";

  		ds = (PosDataset) ctx.get("dsMotorItem_2");
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteMotor_Assem_Cd(record);
  			}
  			
  		}	
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);
  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateMotor_Assem_Cd(record)) == 0 ) {
  					nTempCnt = insertMotor_Assem_Cd(record,ESTM_ITEM_CD);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
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
     protected int insertMotor_Assem_Cd(PosRecord record, String estm_item_cd) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     	param.setValueParamter(i++, Util.trim(estm_item_cd));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL_CD")));
     	param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_LIMIT"));
        param.setValueParamter(i++, record.getAttribute("VERY_SUPER_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("SUPER_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("NORM_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("INSUF_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("VERY_INSUF_ASSIGN_SCR"));

     	int dmlcount = this.getDao("candao").insert("tbdn_motor_assem_cd_in001", param);

     	return dmlcount;
     }
     
     /**
      * <p> ���ں� �а��� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateMotor_Assem_Cd(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_LIMIT"));
        param.setValueParamter(i++, record.getAttribute("VERY_SUPER_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("SUPER_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("NORM_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("INSUF_ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("VERY_INSUF_ASSIGN_SCR"));
        
		//param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL_CD")));

		int dmlcount = this.getDao("candao").update("tbdn_motor_assem_cd_un001", param);

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
