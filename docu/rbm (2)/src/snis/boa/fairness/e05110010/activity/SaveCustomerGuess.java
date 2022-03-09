/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03010017.activity.SaveRacerReference.java
 * ���ϼ���		: ���� �������(��������, ���ֿ, ��������, �������) ���� �� ����
 * �ۼ���		: ������
 * ����			:
 * ��������		: 2015-09-12
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05110010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �λ󼱼������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
*/
public class SaveCustomerGuess extends SnisActivity
{    
	
	protected String sStndYear      = "";
	protected String sMbrCd   	    = "";
	protected String sTms           = "";
	protected String sDayOrd   	    = "";
	protected String sSessionUserId = "";
	
    public SaveCustomerGuess()
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
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsRaceRslt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if(logger.isDebugEnabled())	logger.logDebug(record);
        }
        
        ds    = (PosDataset)ctx.get("dsSpecialDocu");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
        	PosRecord record = ds.getRecord(i);
        	if(logger.isDebugEnabled())	logger.logDebug(record);
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

     	PosDataset ds;
        
     	int nSize        = 0;
        int nTempCnt     = 0;

        sStndYear = (String) ctx.get("STND_YEAR");
        sMbrCd 	  = (String) ctx.get("MBR_CD");
        sTms      = (String) ctx.get("TMS");
        sDayOrd   = (String) ctx.get("DAY_ORD");
        
         ds    = (PosDataset) ctx.get("dsRaceRslt");
         nSize = ds.getRecordCount();

         for ( int i = 0; i < nSize; i++ ) 
         {
             PosRecord record = ds.getRecord(i);
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
             {
             	nSaveCount = nSaveCount + saveCustomerGuess(record);
             }
         }
         
         ds    = (PosDataset) ctx.get("dsSpecialDocu");
         nSize = ds.getRecordCount();
         
         for ( int i = 0; i < nSize; i++ ) 
         {
        	 PosRecord record = ds.getRecord(i);
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
        		 nSaveCount = nSaveCount + deleteRaceSpecialDocu(record);        		 
        	 }
        	 
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
        		 nSaveCount = nSaveCount + updateRaceSpecialDocu(record);        		 
        	 }
        	 
        	 if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
        		 nSaveCount = nSaveCount + insertRaceSpecialDocu(record);
        	 }
         }
         
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }  

     
     /**
      * <p>�� ������� ���� </p>
      * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  effectedRowCnt int		update ���ڵ� ����
      * @throws  none
      */
     protected int saveCustomerGuess(PosRecord record)
     {
     	int effectedRowCnt = 0;
     	effectedRowCnt =insertCustomerGuess(record);

         return effectedRowCnt;    	
     }

     
     /**
      * <p> �� ������� ���� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int insertCustomerGuess(PosRecord record) 
     {
         PosParameter param = new PosParameter();
         int i = 0;

         param.setValueParamter(i++, sStndYear);
         param.setValueParamter(i++, sMbrCd);
         param.setValueParamter(i++, sTms);
         param.setValueParamter(i++, sDayOrd);
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("BET_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_INFO".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );
         param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));      
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));        
         param.setValueParamter(i++, Util.trim(record.getAttribute("BET_CON".trim())));
         param.setValueParamter(i++, Util.trim(record.getAttribute("ETC_INFO".trim())));
         param.setValueParamter(i++, SESSION_USER_ID );

         int dmlcount = this.getDao("boadao").update("tbee_customer_guess_in001", param);
         
         return dmlcount;  
     }   


     /**
      * <p> ��������-���ֻ�Ȳ���� Ư�̻��� update </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int insertRaceSpecialDocu(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
    	 param.setValueParamter(i++, sStndYear);
    	 param.setValueParamter(i++, sMbrCd);
    	 param.setValueParamter(i++, sTms);
    	 param.setValueParamter(i++, sDayOrd);
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("SPECIAL_DOC".trim())));
    	 param.setValueParamter(i++, SESSION_USER_ID );
    	 
    	 int dmlcount = this.getDao("boadao").update("tbee_special_docu_in001", param);
    	 
    	 return dmlcount;  
     }     
     
     
     /**
      * <p> ��������-���ֻ�Ȳ���� Ư�̻��� update </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateRaceSpecialDocu(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
    	 param.setValueParamter(i++, Util.trim(record.getAttribute("SPECIAL_DOC".trim())));
    	 param.setValueParamter(i++, SESSION_USER_ID );
    	 param.setValueParamter(i++, sStndYear);
    	 param.setValueParamter(i++, sMbrCd);
    	 param.setValueParamter(i++, sTms);
    	 param.setValueParamter(i++, sDayOrd);
    	 
    	 int dmlcount = this.getDao("boadao").update("tbee_special_docu_up001", param);
    	 
    	 return dmlcount;  
     }        

     
     /**
      * <p> ��������-���ֻ�Ȳ���� Ư�̻��� delete </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int deleteRaceSpecialDocu(PosRecord record) 
     {
    	 PosParameter param = new PosParameter();
    	 int i = 0;
    	 
    	 param.setValueParamter(i++, sStndYear);
    	 param.setValueParamter(i++, sMbrCd);
    	 param.setValueParamter(i++, sTms);
    	 param.setValueParamter(i++, sDayOrd);
    	 
    	 int dmlcount = this.getDao("boadao").update("tbee_special_docu_de001", param);
    	 
    	 return dmlcount;  
     }        
}    