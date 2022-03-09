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

public class SaveMakeMoter extends SnisActivity 
{
	public SaveMakeMoter() { }
	
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
        int nSize1 = 0;
        String sDsName = "";
                
        //���ں� �а���
        sDsName = "dsWrtnEstm2";
        if ( ctx.get(sDsName) != null ) {
	        ds1 = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	        }
        }  
                
		if(nSize1 > 0){
			saveStateDt_Wrtn_Estm(ctx); 
		}      
        
        return PosBizControlConstants.SUCCESS;
    }
     
      
  	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ���ں� �а���
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateDt_Wrtn_Estm(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsWrtnEstm2");
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateDt_Wrtn_Estm2(record)) == 0 ) {
  					nTempCnt = insertDt_Wrtn_Estm2(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteDt_Wrtn_Estm2(record);
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
     protected int insertDt_Wrtn_Estm2(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;

     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
     	param.setValueParamter(i++, record.getAttribute("ROUND"));
     	param.setValueParamter(i++, record.getAttribute("SCR"));
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
     protected int updateDt_Wrtn_Estm2(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));

		int dmlcount = this.getDao("candao").update("tbdn_dt_wrtn_estm_un001", param);

		return dmlcount;
     }
 
     /**
      * <p> ���ں� �а��� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteDt_Wrtn_Estm2(PosRecord record) 
     {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_dt_wrtn_estm_dn001", param);
		 
		return dmlcount;
     }
      
}
