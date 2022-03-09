/*================================================================================
 * �ý���			: �а���
 * �ҽ����� �̸�	: snis.can.system.d06000015.activity.SaveSubjectJudg.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000015.activity;

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

public class SaveSubjectJudg extends SnisActivity 
{
	public SaveSubjectJudg() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String	sucess ���ڿ�
     * @throws  none
     */    
	    
    public String runActivity(PosContext ctx)
    {
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        
    	PosDataset ds;
        int nSize = 0;
        String sDsName = "";
                
        //���ں� �а���
        sDsName = "dsWrtnEstm1";
        if ( ctx.get(sDsName) != null ) {
        	ds = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logDebug(record);
	        }
        }
        //���ں� �а���
        sDsName = "dsWrtnEstm2";
        if ( ctx.get(sDsName) != null ) {
        	ds = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logDebug(record);
	        }
        }
        
        //������ �а���
        sDsName = "dsWrtnEstm5";
        if ( ctx.get(sDsName) != null ) {
        	ds = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logDebug(record);
	        }
        }
        
        //������ �а���
        sDsName = "dsDtRound";
        if ( ctx.get(sDsName) != null ) {
        	ds = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logDebug(record);
	        }
        }
        
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }
     
      
  	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ���ں� �а���
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveState(PosContext ctx) 
  	{
  		
        int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsWrtnEstm1");
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) 
  			{
  				nDeleteCount = nDeleteCount + deleteDtWrtnEstm(record);
  			}
  		}
  		
  		int nMinItemCd = 101;
  		int nMaxItemCd = 103;
  		
  		ds = (PosDataset) ctx.get("dsWrtnEstm2");
  		nSize = ds.getRecordCount();
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			for ( int j = nMinItemCd; j <= nMaxItemCd; j++ ) {
	  			if ( (nTempCnt = updateDtWrtnEstm(record, j)) == 0 ) 
	  			{
	  				nTempCnt = insertDtWrtnEstm(record, j);
	  			}
	  			
	  			nSaveCount = nSaveCount + nTempCnt;
  			}
  		}

  		ds = (PosDataset) ctx.get("dsWrtnEstm5");
  		nSize = ds.getRecordCount();
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			for ( int j = nMinItemCd; j <= nMaxItemCd; j++ ) {
	  			if ( (nTempCnt = updateRoundWrtnEstm(record, j, ctx)) == 0 ) 
	  			{
	  				nTempCnt = insertRoundWrtnEstm(record, j, ctx);
	  			}
	  			
	  			nSaveCount = nSaveCount + nTempCnt;
  			}
  		}
  			
  		ds = (PosDataset) ctx.get("dsDtRound");
  		nSize = ds.getRecordCount();
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

			nTempCnt = updateRoundWrtnEstmDate(record, ctx);
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
	protected int insertDtWrtnEstm(PosRecord record, int nItem)
	{
	 	PosParameter param = new PosParameter();       					
	 	int i = 0;
	
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO       ".trim()));
		param.setValueParamter(i++, record.getAttribute("DT            ".trim()));
		param.setValueParamter(i++, nItem + "");
		param.setValueParamter(i++, record.getAttribute("ROUND         ".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR_" + nItem         ));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").insert("tbdn_dt_wrtn_estm_in001", param);
	
	 	return dmlcount;
	}
     
     /**
      * <p> ���ں� �а��� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
    protected int updateDtWrtnEstm(PosRecord record, int nItem) 
    {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		param.setValueParamter(i++, record.getAttribute("ROUND         ".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR_" + nItem         ));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
	 	param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO       ".trim()));
		param.setWhereClauseParameter(i++, record.getAttribute("DT            ".trim()));
		param.setWhereClauseParameter(i++, nItem + "");

		int dmlcount = this.getDao("candao").update("tbdn_dt_wrtn_estm_un001", param);

		return dmlcount;
    }
 
     /**
      * <p> ���ں� �а��� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
    protected int deleteDtWrtnEstm(PosRecord record) 
    {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
     	param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setWhereClauseParameter(i++, record.getAttribute("DT            ".trim()));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_dt_wrtn_estm_dn001", param);
		 
		return dmlcount;
    }
 
     /**
      * <p> ������ �а��� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
    protected int insertRoundWrtnEstm(PosRecord record, int nItem, PosContext ctx) 
    {
	 	PosParameter param = new PosParameter();       					
	 	int i = 0;
	
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO       ".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND         ".trim()));
		param.setValueParamter(i++, nItem + "");
		param.setValueParamter(i++, ctx   .get         ("ROUND_DT      ".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR_" + nItem         ));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").insert("tbdn_round_wrtn_estm_in001", param);
	
	 	return dmlcount;
    }
     
     /**
      * <p> ������ �а��� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
    protected int updateRoundWrtnEstm(PosRecord record, int nItem, PosContext ctx) 
    {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		param.setValueParamter(i++, ctx   .get         ("ROUND_DT      ".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR_" + nItem         ));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
	 	param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO       ".trim()));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND         ".trim()));
		param.setWhereClauseParameter(i++, nItem + "");

		int dmlcount = this.getDao("candao").update("tbdn_round_wrtn_estm_un001", param);
	
	 	return dmlcount;
    }
    
    /**
     * <p> ������ �а��� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
   protected int updateRoundWrtnEstmDate(PosRecord record, PosContext ctx) 
   {
    	PosParameter param = new PosParameter();       					
    	int i = 0;
		param.setValueParamter(i++, record.getAttribute("DT             ".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
	 	param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
		param.setWhereClauseParameter(i++, ctx.get("ROUND         ".trim()));

		int dmlcount = this.getDao("candao").update("tbdn_round_wrtn_estm_un002", param);
	
	 	return dmlcount;
   }
}
