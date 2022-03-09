/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03040010.activity.SaveEquipmentArrange.java
 * ���ϼ���		: ����/��Ʈ���� ���
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-12-23
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

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
* @auther ���ȭ
* @version 1.0
*/
public class SaveEquipmentArrange extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveEquipmentArrange()
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
    	int nSaveCount   	= 0; 
    	int nDeleteCount 	= 0; 

    	PosDataset ds;
        int nSize        	= 0;
        PosRecord record  	= null;

        ds    = (PosDataset) ctx.get("dsOutRacerArrange");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
                  {
            		nSaveCount = nSaveCount + saveRacerArrange(record);
                  }
        }


        ds    = (PosDataset) ctx.get("dsOutRacerArrange_0");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
                  {
            		nSaveCount = nSaveCount + saveRacerArrange(record);
                  }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ����/��Ʈ����  Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerArrange(PosRecord record)
    {
    	updateEquipPrt(record);
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertRacerArrange(record);
        return effectedRowCnt;
    }

 
    /**
     * <p>����/��Ʈ����  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerArrange(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO")));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO")));
        
        return this.getDao("boadao").update("tbeb_arrange_uc001", param);        
    }

    
    /**
     * <p>����/��Ʈ����  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected void updateEquipPrt(PosRecord record) 
    {
    	if ( record.getAttribute("ORG_RACER_NO") == null ) return;
    	if ( record.getAttribute("RACER_NO"    ) == null ) return;
    	if ( record.getAttribute("ORG_RACER_NO").equals("") ) return;
    	if ( record.getAttribute("RACER_NO"    ).equals("") ) return;
    	
    	if ( record.getAttribute("ORG_RACER_NO").equals(record.getAttribute("RACER_NO"    ))) return;

    	
    	PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"   ));
        param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"      ));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS"         ));
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_RACER_NO"));
        
        this.getDao("boadao").update("tbec_equip_prt_uc001", param);        
    }

}


