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
* @auther ����
* @version 1.0
*/

public class saveWrtnEstm extends SnisActivity 
{
	public saveWrtnEstm() { }
	
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
                
        //�а��� ����
        sDsName = "dsDateList";
        if ( ctx.get(sDsName) != null ) {
        	ds = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logDebug(record);
	        }
        }
        //�а����ں� �����а� ��
        sDsName = "dsWrtExam_Detail";
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

  		ds = (PosDataset) ctx.get("dsDateList");
  		nSize = ds.getRecordCount();

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) 
  			{
  				nDeleteCount = nDeleteCount + deleteWrtnEstm(record);
  			}
  		}
  		
  		ds = (PosDataset) ctx.get("dsWrtExam_Detail");
  		nSize = ds.getRecordCount();
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);
            if ( record.getType() != com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nTempCnt = mergeWrtnEstm(record);
                nSaveCount = nSaveCount + nTempCnt;
            }
  		}
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
  		
  	}


    /**
     * <p> ���ں� �а��� ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
   protected int deleteWrtnEstm(PosRecord record) 
   {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
    	param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
    	param.setWhereClauseParameter(i++, record.getAttribute("ROUND".trim()));
		param.setWhereClauseParameter(i++, record.getAttribute("DT".trim()));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_wrt_exam_detail_de001", param);
		 
		return dmlcount;
   }
   
   
   /**
    * <p> ���ں� �а��� �Է� </p>
    * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
    * @return  dmlcount	int		insert ���ڵ� ����
    * @throws  none
    */
	protected int mergeWrtnEstm(PosRecord record)
	{
	 	PosParameter param = new PosParameter();       					
	 	int i = 0;
	
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdn_wrtn_estm_detail_mer001", param);
	
	 	return dmlcount;
	}   


   
   
   
     /**
      * <p> ���ں� �а��� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
	protected int insertWrtnEstm(PosRecord record)
	{
	 	PosParameter param = new PosParameter();       					
	 	int i = 0;
	
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").insert("tbdn_wrtn_estm_detail_in001", param);
	
	 	return dmlcount;
	}
     
	/**
	 * <p> ���ں� �а��� �Է� </p>
	 * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return  dmlcount	int		insert ���ڵ� ����
	 * @throws  none
	 */

	
	/**
      * <p> ���ں� �а��� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
    protected int updateWrtnEstm(PosRecord record) 
    {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));

		int dmlcount = this.getDao("candao").update("tbdn_wrtn_estm_detail_up001", param);

		return dmlcount;
    }
}
