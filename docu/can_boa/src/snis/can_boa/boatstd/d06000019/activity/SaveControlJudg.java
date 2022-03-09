/*================================================================================
 * �ý���			: �л����
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d06000019.activity.SaveControlJudg.java
 * ���ϼ���		: �����Ǳ���
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000019.activity;

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

public class SaveControlJudg extends SnisActivity 
{
	public SaveControlJudg() { }
	
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
		PosDataset ds4;	
		PosDataset ds5;
		PosDataset ds6;
		PosDataset ds7;
		PosDataset ds8;
		PosDataset ds9;
		PosDataset ds10;
		PosDataset ds11;

        int    nSize1 = 0;
        int    nSize2 = 0;
        int    nSize3 = 0;
        int    nSize4 = 0; 
        int    nSize5 = 0;
        int    nSize6 = 0;
        int    nSize7 = 0;
        int    nSize8 = 0;
        int    nSize9 = 0;
        int    nSize10 = 0;
        int    nSize11 = 0;
        String     sDsName = "";
   
        //���׸�
        sDsName = "dsPilotItemCd1";
        if ( ctx.get(sDsName) != null ) {
        	ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
        }
        
        //��ŸƮŸ��,��ȸŸ�ӹ���
        sDsName = "dsPilotRoundBas2";
        if ( ctx.get(sDsName) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName);
	        nSize2= ds2.getRecordCount();
        }   
                
        //��ȸŸ�� 
        sDsName = "dsPilotCcitTimeBas3";
        if ( ctx.get(sDsName) != null ) {
	        ds3   = (PosDataset)ctx.get(sDsName);
	        nSize3= ds3.getRecordCount();
        }   
        
        //��ŸƮŸ��
        sDsName = "dsPilotStTimeBas4";
        if ( ctx.get(sDsName) != null ) {
	        ds4    = (PosDataset)ctx.get(sDsName);
	        nSize4 = ds4.getRecordCount();
        }         

        //��ǥ
        sDsName = "dsPilotEstmItem5";
        if ( ctx.get(sDsName) != null ) {
	        ds5    = (PosDataset)ctx.get(sDsName);
	        nSize5 = ds5.getRecordCount();
        }   
        
        //�����Ǳ���ǥ ���� ����
        sDsName = "dsPilotEstmDetl6";
        if ( ctx.get(sDsName) != null ) {
	        ds6    = (PosDataset)ctx.get(sDsName);
	        nSize6 = ds6.getRecordCount();
        }   
        
        //��ȸŸ�� ������ǥ
        sDsName = "dsPilotEstmTimeDetlScr1";
        if ( ctx.get(sDsName) != null ) {
	        ds7    = (PosDataset)ctx.get(sDsName);
	        nSize7 = ds7.getRecordCount();
        }    
        
        //��ŸƮŸ�� ������ǥ 
        sDsName = "dsPilotEstmTimeDetlScr2";
        if ( ctx.get(sDsName) != null ) {
	        ds8    = (PosDataset)ctx.get(sDsName);
	        nSize8 = ds8.getRecordCount();
        }           
        
        //��ȸŸ�� ���α����ǥ 
        sDsName = "dsPilotEstmTimeRec1";
        if ( ctx.get(sDsName) != null ) {
	        ds9    = (PosDataset)ctx.get(sDsName);
	        nSize9 = ds9.getRecordCount();
        }           
        
        //��ŸƮŸ�� ���α����ǥ 
        sDsName = "dsPilotEstmTimeRec2";
        if ( ctx.get(sDsName) != null ) {
	        ds10    = (PosDataset)ctx.get(sDsName);
	        nSize10 = ds10.getRecordCount();
        }   
        
       //��ŸƮŸ�� ���α����ǥ ���� 
        sDsName = "dsPilotItemDt";
        if ( ctx.get(sDsName) != null ) {
	        ds11    = (PosDataset)ctx.get(sDsName);
	        nSize11 = ds11.getRecordCount();
        }  
       
                
        if(nSize1 > 0){
        	saveStatePilot_Item_Cd(ctx); 
        }else if(nSize2 > 0){
        	saveStatePilot_Round_Bas(ctx); 
        }else if(nSize3 > 0){
        	saveStatePilot_Ccit_Time_Bas(ctx);         	
        }else if(nSize4 > 0){
        	saveStatePilot_St_Time_Bas(ctx); 
        }else if(nSize5 > 0){
        	saveStatePilot_Estm_Item(ctx);
        }else if(nSize6 > 0){
        	saveStatePilot_Estm_Detl(ctx);  
        }else if(nSize7 > 0){
        	saveStatePilot_Estm_Ccit_Time(ctx);     
        }else if(nSize8 > 0){
        	saveStatePilot_Estm_St_Time(ctx);             	
        }else if(nSize9 > 0){
        	saveStatePilot_Estm_Ccit_Time_Rec(ctx);             	
        }else if(nSize10 > 0){
        	saveStatePilot_Estm_St_Time_Rec(ctx);             	
        }else if(nSize11 > 0){
        	saveStatePilot_Dt(ctx);             	
        }

        return PosBizControlConstants.SUCCESS; 
    }
    
	/**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	����� ���׸�
     * @return  none
     * @throws  none
     */
	protected void saveStatePilot_Item_Cd(PosContext ctx) 
	{
		int nSaveCount   = 0; 
     	int nDeleteCount = 0; 
     	PosDataset ds;
     	
        int nSize        = 0;
        int nTempCnt     = 0;
        
        //���׸�����       
        ds   = (PosDataset) ctx.get("dsPilotItemCd1");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
        	PosRecord record = ds.getRecord(i);
             
        	if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE )
        	{
        		nTempCnt = updatePilot_Item_Cd(record);
        		nSaveCount = nSaveCount + nTempCnt;
        	}
        	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
        	{          	 
        		nTempCnt = insertPilot_Item_Cd(record);
        		nSaveCount = nSaveCount + nTempCnt;
        	}
        	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
        	{
        		// delete
             	nDeleteCount = nDeleteCount + deletePilot_Item_Cd(record);
        	}
        }
                  
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
	}
     
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ���κ�����
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Round_Bas(PosContext ctx) 
	{
		int nSaveCount   = 0; 
      	int nDeleteCount = 0; 
      	PosDataset ds;
      	
      	int nSize        = 0;
      	int nTempCnt     = 0;
         
      	// ���׸�����       
      	ds   = (PosDataset) ctx.get("dsPilotRoundBas2");
      	nSize = ds.getRecordCount();
      	for ( int i = 0; i < nSize; i++ ) 
      	{
      		PosRecord record = ds.getRecord(i);
              
      		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE )
      		{
      			nTempCnt = updatePilot_Round_Bas(record);
      			nSaveCount = nSaveCount + nTempCnt;
      		}
      		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
      		{          	 
      			nTempCnt = insertPilot_Round_Bas(record);
      			nSaveCount = nSaveCount + nTempCnt;
      		}
              
      		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
      		{
      			// delete
              	nDeleteCount = nDeleteCount + deletePilot_Round_Bas(record);
      		}
      	}
                   
      	Util.setSaveCount  (ctx, nSaveCount     );
      	Util.setDeleteCount(ctx, nDeleteCount   );
	}   
      
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ��ȸŸ��
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Ccit_Time_Bas(PosContext ctx) 
	{
		int nSaveCount   = 0; 
       	int nDeleteCount = 0; 
       	PosDataset ds;
       	
       	int nSize        = 0;
       	int nTempCnt     = 0;
          
       	// �ð�����ǥ ����       
       	ds   = (PosDataset) ctx.get("dsPilotCcitTimeBas3");
       	nSize = ds.getRecordCount();
       	for ( int i = 0; i < nSize; i++ ) 
       	{
       		PosRecord record = ds.getRecord(i);
               
       		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
       		{          	 
       			nTempCnt = updatePilot_Ccit_Time_Bas(record);
       			nSaveCount = nSaveCount + nTempCnt;
       		}
            if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
            {
   				nTempCnt = insertPilot_Ccit_Time_Bas(record);
       			nSaveCount = nSaveCount + nTempCnt;
            }
       		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
       		{
       			// delete
               	nDeleteCount = nDeleteCount + deletePilot_Ccit_Time_Bas(record);
       		}
       	}
                    
       	Util.setSaveCount  (ctx, nSaveCount     );
       	Util.setDeleteCount(ctx, nDeleteCount   );
	}      
     
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ��ŸƮŸ��
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_St_Time_Bas(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		// �ð�����ǥ ����       
		ds   = (PosDataset) ctx.get("dsPilotStTimeBas4");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
			{
				nTempCnt = updatePilot_St_Time_Bas(record);
				nSaveCount = nSaveCount + nTempCnt;
			}
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				nTempCnt = insertPilot_St_Time_Bas(record);
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_St_Time_Bas(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}         
        
        
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ��ǥ
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Item(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
         	
		int nSize        = 0;
		int nTempCnt     = 0;
            
		//  ����       
		ds   = (PosDataset) ctx.get("dsPilotEstmItem5");
		nSize = ds.getRecordCount();

		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                 
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Item(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Item(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}

			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Item(record);
			}
		}
                      
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}   
	
	protected void saveStatePilot_Dt(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		PosDataset ds;
         	
		int nSize        = 0;
            
		//  ����       
		ds   = (PosDataset) ctx.get("dsPilotItemDt");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                 
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
			{     
				logger.logInfo("updatePilot_Estm_Item_Dt start============================");
		     	PosParameter param = new PosParameter();       					
		     	int j = 0;
				
		        param.setValueParamter(j++, Util.trim(record.getAttribute("DT")));     	

				j = 0;    
				param.setWhereClauseParameter(j++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
				param.setWhereClauseParameter(j++, record.getAttribute("ROUND"));

				int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_item_un002", param);

				logger.logInfo("updatePilot_Estm_Item_Dt end============================");
				nSaveCount = nSaveCount + dmlcount;
			}

		}
		Util.setSaveCount  (ctx, nSaveCount);
	}
             
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� �����Ǳ���ǥ ����
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Detl(PosContext ctx) 
	{
		int nSaveCount   = 0; 
      	int nDeleteCount = 0; 
      	PosDataset ds;
      	
      	int nSize        = 0;
      	int nTempCnt     = 0;
         
      	//  ����       
      	ds   = (PosDataset) ctx.get("dsPilotEstmDetl6");
      	nSize = ds.getRecordCount();
      	for ( int i = 0; i < nSize; i++ ) 
      	{
      		PosRecord record = ds.getRecord(i);
              
      		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
      				|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
      		{          	 
      			if ( (nTempCnt = updatePilot_Estm_Detl(record)) == 0 ) {
      				nTempCnt = insertPilot_Estm_Detl(record);
      			}            	
      			nSaveCount = nSaveCount + nTempCnt;
      		}
              
      		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
      		{
      			// delete
      			nDeleteCount = nDeleteCount + updatePilot_Estm_Detl(record);
      		}
      	}
                   
      	Util.setSaveCount  (ctx, nSaveCount     );
      	Util.setDeleteCount(ctx, nDeleteCount   );
	}           
       
      
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ��ȸŸ�� ������ǥ ����
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Ccit_Time(PosContext ctx) 
	{
		int nSaveCount   = 0; 
       	int nDeleteCount = 0; 
       	PosDataset ds;
       	
       	int nSize        = 0;
       	int nTempCnt     = 0;
          
       	//  ����       
       	ds   = (PosDataset) ctx.get("dsPilotEstmTimeDetlScr1");
       	nSize = ds.getRecordCount();
       	for ( int i = 0; i < nSize; i++ ) 
       	{
       		PosRecord record = ds.getRecord(i);
               
       		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
       				|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
       		{          	 
       			if ( (nTempCnt = updatePilot_Estm_Ccit_Time(record)) == 0 ) {
       				nTempCnt = insertPilot_Estm_Ccit_Time(record);
       			}            	
       			nSaveCount = nSaveCount + nTempCnt;
       		}
               
       		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
       		{
       			// delete
       			nDeleteCount = nDeleteCount + deletePilot_Estm_Ccit_Time(record);
       		}
       	}
                    
       	Util.setSaveCount  (ctx, nSaveCount     );
       	Util.setDeleteCount(ctx, nDeleteCount   );
	}  
       
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ��ȸŸ�� ������ǥ ����
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Ccit_Time_Rec(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		//  ����       
		ds   = (PosDataset) ctx.get("dsPilotEstmTimeRec1");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Time_Rec(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Time_Rec(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Time_Rec(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}  
       
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ��ȸŸ�� ������ǥ ����
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_St_Time_Rec(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		//  ����       
		ds   = (PosDataset) ctx.get("dsPilotEstmTimeRec2");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Time_Rec(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Time_Rec(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Time_Rec(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}  
       
	/**
	 * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	 * @param   ctx		PosContext	����� ��ŸƮŸ�� ������ǥ 
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_St_Time(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		//  ����       
		ds = (PosDataset) ctx.get("dsPilotEstmTimeDetlScr2");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Ccit_Time(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Ccit_Time(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Ccit_Time(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}        
       
              
      
	/**
	 * <p> ���׸� �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_Item_Cd(PosRecord record) 
	{
		logger.logInfo("insertPilot_Item_Cd start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
          
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL")));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_LIMIT"));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, SESSION_USER_ID);
         
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_item_cd_in001", param);
         
		logger.logInfo("insertPilot_Item_Cd end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> ���׸� ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_Item_Cd(PosRecord record) 
	{
		logger.logInfo("updatePilot_Item_Cd start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));     	
        param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL")));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_LIMIT"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_item_cd_un001", param);

		logger.logInfo("updatePilot_Item_Cd end============================");
		return dmlcount;
	}

	/**
	 * <p> ���׸�  ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_Item_Cd(PosRecord record) 
	{
		logger.logInfo("deletePilot_Item_Cd start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_item_cd_dn001", param);
         
		logger.logInfo("deletePilot_Item_Cd end============================");
		return dmlcount;
	}
     
	/**
	 * <p> ���κ����� �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_Round_Bas(PosRecord record) 
	{
		logger.logInfo("insertPilot_Round_Bas start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
                    
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, record.getAttribute("ST_TIME_SCR"));
		param.setValueParamter(i++, record.getAttribute("CCIT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
                    
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_round_bas_in001", param);
         
		logger.logInfo("insertPilot_Round_Bas end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> ���κ����� ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_Round_Bas(PosRecord record) 
	{
		logger.logInfo("updatePilot_Round_Bas start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		

        param.setValueParamter(i++, record.getAttribute("ST_TIME_SCR"));
        param.setValueParamter(i++, record.getAttribute("CCIT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));		
		int dmlcount = this.getDao("candao").update("tbdn_pilot_round_bas_un001", param);

		logger.logInfo("updatePilot_Round_Bas end============================");
		return dmlcount;
	}
     
 
	/**
	 * <p> ���κ�����  ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_Round_Bas(PosRecord record) 
	{
		logger.logInfo("deletePilot_Round_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_round_bas_dn001", param);
         
		logger.logInfo("deletePilot_Round_Bas end============================");
		return dmlcount;
	}
     
	/**
	 * <p> ��ȸŸ�� �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_Ccit_Time_Bas(PosRecord record) 
	{
    	 logger.logInfo("insertPilot_Ccit_Time_Bas start ============================");
    	 PosParameter param = new PosParameter();       					
         int i = 0;
               
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, record.getAttribute("ROUND"));
         param.setValueParamter(i++, record.getAttribute("UPPER_FROM"));
         param.setValueParamter(i++, record.getAttribute("UPPER_TO"));
         param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
         param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                    
         int dmlcount = this.getDao("candao").insert("tbdn_pilot_ccit_time_bas_in001", param);
         
         logger.logInfo("insertPilot_Ccit_Time_Bas end ============================");
         return dmlcount;
	}
     
	/**
	 * <p> ��ȸŸ�� ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_Ccit_Time_Bas(PosRecord record) 
	{
		logger.logInfo("updatePilot_Ccit_Time_Bas start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));     	
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("UPPER_FROM"));
        param.setValueParamter(i++, record.getAttribute("UPPER_TO"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_ccit_time_bas_un001", param);

		logger.logInfo("updatePilot_Ccit_Time_Bas end============================");
		return dmlcount;
	}
     
 
	/**
	 * <p> ��ȸŸ�� ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_Ccit_Time_Bas(PosRecord record) 
	{
		logger.logInfo("deletePilot_Ccit_Time_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_ccit_time_bas_dn001", param);
         
		logger.logInfo("deletePilot_Ccit_Time_Bas end============================");
		return dmlcount;
	}    
     
	/**
	 * <p> ��ŸƮŸ�� �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_St_Time_Bas(PosRecord record) 
	{
		logger.logInfo("insertPilot_St_Time_Bas start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
		param.setValueParamter(i++, SESSION_USER_ID);
                    
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_st_time_bas_in001", param);
         
		logger.logInfo("insertPilot_St_Time_Bas end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> ��ŸƮŸ�� ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_St_Time_Bas(PosRecord record) 
	{
		logger.logInfo("updatePilot_St_Time_Bas start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));        
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_st_time_bas_un001", param);

		logger.logInfo("updatePilot_St_Time_Bas end============================");
		return dmlcount;
	}
     
 
	/**
	 * <p> ��ŸƮŸ�� ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_St_Time_Bas(PosRecord record) 
	{
		logger.logInfo("deletePilot_St_Time_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_st_time_bas_dn001", param);
         
		logger.logInfo("deletePilot_St_Time_Bas end============================");
		return dmlcount;
	}     
     
	/**
	 * <p> ��ǥ �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_Estm_Item(PosRecord record) 
	{
		logger.logInfo("insertPilot_Estm_Item start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("DCSN")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);
                    
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_item_in001", param);
         
		logger.logInfo("insertPilot_Estm_Item end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> ��ǥ ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_Estm_Item(PosRecord record) 
	{
		logger.logInfo("updatePilot_Estm_Item start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));     	
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DCSN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
        param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));		
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_item_un001", param);

		logger.logInfo("updatePilot_Estm_Item end============================");
		return dmlcount;
	} 
 
	/**
	 * <p> ��ǥ ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_Estm_Item(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Item start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));        
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_item_dn001", param);

		logger.logInfo("deletePilot_Estm_Item end============================");
		return dmlcount;
	}     
	
	/**
	 * <p> �����Ǳ���ǥ ���� �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_Estm_Detl(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;
 
		param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		
		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);
			   
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_detl_in001", param);

		return dmlcount;
	}

	/**
	 * <p> �����Ǳ���ǥ ���� ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_Estm_Detl(PosRecord record) 
	{
		logger.logInfo("updatePilot_Estm_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		
		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));		
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_detl_un001", param);

		logger.logInfo("updatePilot_Estm_Detl end============================");
		return dmlcount;
	}


	/**
	 * <p> �����Ǳ���ǥ ���� ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_Estm_Detl(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));		
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_detl_dn001", param);

		logger.logInfo("deletePilot_Estm_Detl end============================");
		return dmlcount;
	}	
	
	/**
	 * <p> ��ȸŸ��/��ŸƮŸ�� ������ǥ �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_Estm_Ccit_Time(PosRecord record) 
	{
		logger.logInfo("insertPilot_Estm_Ccit_Time start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("TIME_GBN")));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_ccit_time_in001", param);

		logger.logInfo("insertPilot_Estm_Ccit_Time end ============================");
		return dmlcount;
	}	
	
	/**
	 * <p> ��ȸŸ��/��ŸƮŸ�� ������ǥ ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_Estm_Ccit_Time(PosRecord record) 
	{
		logger.logError("updatePilot_Estm_Ccit_Time start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_ccit_time_un001", param);

		logger.logError("updatePilot_Estm_Ccit_Time end============================");
		return dmlcount;
	}

	/**
	 * <p> ��ȸŸ��/��ŸƮŸ�� ������ǥ ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_Estm_Ccit_Time(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Ccit_Time start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_ccit_time_dn001", param);

		logger.logInfo("deletePilot_Estm_Ccit_Time end============================");
		return dmlcount;
	}	
	
	/**
	 * <p> ��ȸŸ��/��ŸƮŸ�� ���� ��� �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */
	protected int insertPilot_Estm_Time_Rec(PosRecord record) 
	{
		logger.logInfo("insertPilot_Estm_Time_Rec start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("TIME_GBN")));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, record.getAttribute("ESTM_CNT"));
		param.setValueParamter(i++, record.getAttribute("ESTM_REC"));
		param.setValueParamter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_time_rec_in001", param);

		logger.logInfo("insertPilot_Estm_Time_Rec end ============================");
		return dmlcount;
	}	
	
	/**
	 * <p> ��ȸŸ��/��ŸƮŸ�� ���� ��� ����  </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		update ���ڵ� ����
	 * @throws  none
	 */
	protected int updatePilot_Estm_Time_Rec(PosRecord record) 
	{
		logger.logError("updatePilot_Estm_Time_Rec start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("ESTM_REC"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, record.getAttribute("ESTM_CNT"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_time_rec_un001", param);

		logger.logError("updatePilot_Estm_Time_Rec end============================");
		return dmlcount;
	}

	/**
	 * <p> ��ȸŸ��/��ŸƮŸ�� ���� ��� ����</p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		delete ���ڵ� ����
	 * @throws  none
	 */
	protected int deletePilot_Estm_Time_Rec(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Time_Rec start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, record.getAttribute("ESTM_CNT"));
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_time_rec_dn001", param);

		logger.logInfo("deletePilot_Estm_Time_Rec end============================");
		return dmlcount;
	}	
}
