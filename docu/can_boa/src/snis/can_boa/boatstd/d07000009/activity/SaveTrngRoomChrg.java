/*================================================================================
 * �ý���		    : 
 * �ҽ����� �̸�	: snis.can.system.d07000009.activity.SaveTrngRoomChrg.java
 * ���ϼ���		: �Ʒü��ں� �Է�/����/����
 * �ۼ���		    : ���缱
 * ����			: 1.0.0
 * ��������		: 2013-01-12
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000009.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���缱
* @version 1.0
*/

public class SaveTrngRoomChrg extends SnisActivity 
{
	public SaveTrngRoomChrg() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String	sucess ���ڿ�
     * @throws  none
     */    
	    
    public String runActivity(PosContext ctx)
    {
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
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
  		
        String sDsName      = "";
  		int nSize 	= 0;

        sDsName = "dsTrngRoomChrg";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteTrngRoomChrg(record);
	 			}

	             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
	             {      
	            	 nSaveCount += mergeUpdateTrngRoomChrg(record);
	             }
	        }
        }

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
     /**
      * <p> �Ʒü��ں� ���� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int deleteTrngRoomChrg(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
        
     	int dmlcount = this.getDao("candao").delete("d07000009_d01", param);
        
     	return dmlcount;
     }

     /**
      * <p> �Ʒü��ں� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int mergeUpdateTrngRoomChrg(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, record.getAttribute("SEQ"));
     	param.setValueParamter(i++, record.getAttribute("APLY_STR_DT"));
     	param.setValueParamter(i++, record.getAttribute("APLY_END_DT"));
     	param.setValueParamter(i++, record.getAttribute("TRNG_DAYS"));
     	param.setValueParamter(i++, record.getAttribute("COMP_EDU_CD"));
     	param.setValueParamter(i++, record.getAttribute("ROOM_CHRG_AMT"));
     	param.setValueParamter(i++, record.getAttribute("RMK"));
     	param.setValueParamter(i++, record.getAttribute("COMMUTE_YN"));
     	param.setValueParamter(i++, record.getAttribute("TRNG_RSN_CD"));
        param.setValueParamter(i++, SESSION_USER_ID);
		
     	int dmlcount = this.getDao("candao").update("d07000009_m01", param);
        
     	return dmlcount;
     }
}
